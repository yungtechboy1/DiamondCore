/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
 */

package net.trenterprises.diamondcore.desktop.handshake;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.Entity;
import net.trenterprises.diamondcore.cross.PlayerType;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.event.EventDispatcher;
import net.trenterprises.diamondcore.cross.api.java.event.player.PlayerLoginEvent;
import net.trenterprises.diamondcore.cross.utils.VarInt;
import net.trenterprises.diamondcore.desktop.DesktopSession;
import net.trenterprises.diamondcore.desktop.packet.ClientDisconnectPacket;
import net.trenterprises.diamondcore.desktop.utils.PacketUtils;
import net.trenterprises.diamondcore.desktop.utils.SecurityUtils;
import net.trenterprises.diamondcore.mojang.MojangAuth;

import org.json.simple.JSONObject;

/**
 * Used to handle a client login packet
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class LoginResponse extends HandshakePacket {
	
	// Socket info
	protected Socket playerSocket;
	protected DataInputStream input;
	protected DataOutputStream output;
	
	// Packet info
	protected String username;
	
	// Event info
	protected PlayerLoginEvent event;
	
	public LoginResponse(Socket s) throws IOException {
		this.playerSocket = s;
		this.input = new DataInputStream(playerSocket.getInputStream());
		this.output = new DataOutputStream(playerSocket.getOutputStream());
		
		// Decode packet
		this.decode();
		
		// Throw event
		this.event = new PlayerLoginEvent(PlayerType.DESKTOP, this.playerSocket, this.playerSocket.getInetAddress(), this.playerSocket.getPort());
		EventDispatcher.throwEvent(this.event);
		if(!Diamond.getOnlinePlayers().contains(this.username) && !event.getLoginCancelled()) Diamond.getOnlinePlayers().add(this.username);
		
		this.sendResponse();
	}
	
	/**
	 * Used to retrieve the username of the player joining
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Username of player trying to join
	 * @throws IOException
	 */
	public String getPlayerName() {
		return this.username;
	}

	@Override
	public void decode() throws IOException {
		input.read(new byte[2]); // Read two unneeded bytes sent by the client
		this.username = PacketUtils.readString(this.input);
	}

	@Override
	public void sendResponse() throws IOException {
		
		if(!event.getLoginCancelled()) {
			// Create key
			byte[] publicKey = null;
			byte[] verifyToken = null;
			KeyPair pair = null;
			try {
				pair = SecurityUtils.generateKeyPair();
				publicKey = SecurityUtils.generateX509Key(pair.getPublic()).getEncoded();
				verifyToken = SecurityUtils.generateVerifyToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Write data
			ByteArrayOutputStream encryptRequest = new ByteArrayOutputStream();
			encryptRequest.write((byte) 0x01);
			encryptRequest.write(VarInt.writeUnsignedVarInt(1));
			encryptRequest.write(" ".getBytes());
			encryptRequest.write(VarInt.writeUnsignedVarInt(publicKey.length));
			encryptRequest.write(publicKey);
			encryptRequest.write(VarInt.writeUnsignedVarInt(verifyToken.length));
			encryptRequest.write(verifyToken);
			encryptRequest.flush();
			
			// Send packet
			output.write(VarInt.writeUnsignedVarInt(encryptRequest.size()));
			output.write(encryptRequest.toByteArray());
			output.flush();
			
			// Immediately read data, it's sent without pause
			VarInt.readUnsignedVarInt(input, true); // Read packet length
			int packetID = VarInt.readUnsignedVarInt(input.readByte()); // Just make sure the packet id is correct
			if(packetID == (byte) 0x01) {
				// Get shared secret and token sent by client
				SecretKeySpec receivedSecret = MojangAuth.decryptSecret(pair, PacketUtils.readBytes(input, VarInt.readUnsignedVarInt(input, true)));
				byte[] receivedToken = MojangAuth.decryptVerify(pair, PacketUtils.readBytes(input, VarInt.readUnsignedVarInt(input, true)));
				
				String serverHash = MojangAuth.getServerHash(receivedSecret, pair.getPublic());
				JSONObject playerData = MojangAuth.getPlayerData(this.username, serverHash);
				if(playerData == null) {
					ClientDisconnectPacket kick = new ClientDisconnectPacket(playerSocket, "Invalid login! Is your client in offline mode?");
					kick.setShouldLog(true);
					kick.setUsername(username);
					kick.disconnect();
				} else {
					if(Diamond.getOnlinePlayers().contains(this.username) == false && Diamond.getOnlinePlayers().size() < ServerSettings.getMaxPlayers()) {
						
						// Write "login success" packet
						ByteArrayOutputStream login = new ByteArrayOutputStream();
						DataOutputStream loginOutput = new DataOutputStream(login);
						UUID uuid = UUID.fromString(playerData.get("id").toString());
						loginOutput.writeByte((byte) 0x02);
						loginOutput.writeLong(uuid.getMostSignificantBits());
						loginOutput.writeLong(uuid.getLeastSignificantBits());
						loginOutput.write(VarInt.writeUnsignedVarInt(username.getBytes().length));
						loginOutput.write(username.getBytes());
						
						// Send "login success" packet
						output.write(VarInt.writeUnsignedVarInt(login.size()));
						output.write(login.toByteArray());
						output.flush();
						
						// Write "set compression" packet
						ByteArrayOutputStream compression = new ByteArrayOutputStream();
						compression.write((byte) 0x03);
						compression.write(VarInt.writeUnsignedVarInt(1535));
						
						// Send "set compression" packet
						output.write(VarInt.writeUnsignedVarInt(compression.size()));
						output.write(compression.toByteArray());
						output.flush();
						
						Diamond.getOnlinePlayers().add(this.username);
						new DesktopSession(playerData.get("name").toString(), UUID.fromString(playerData.get("id").toString()), playerSocket, receivedToken);
					}
					else if(Diamond.getOnlinePlayers().size() >= ServerSettings.getMaxPlayers()) event.cancelLogin("The server is full!");
				}
			
			} else {
				// Disconnect the client
				ClientDisconnectPacket packet = new ClientDisconnectPacket(playerSocket, "Your client seems to have sent the invalid data in the login process!");
				packet.setShouldLog(true);
				packet.setUsername(username);
				packet.disconnect();
			}
		}
		
	}

}
