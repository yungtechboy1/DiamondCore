/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
 */

package net.trenterprises.diamondcore.desktop.network.handshake;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.PlayerType;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.event.EventDispatcher;
import net.trenterprises.diamondcore.cross.api.java.event.player.PlayerLoginEvent;
import net.trenterprises.diamondcore.cross.utils.SecurityUtils;
import net.trenterprises.diamondcore.cross.utils.VarInt;
import net.trenterprises.diamondcore.desktop.network.packet.ClientDisconnectPacket;
import net.trenterprises.diamondcore.desktop.network.utils.PacketUtils;

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
		
		// Response not completed yet
		if(!event.getLoginCancelled()) {
			if(Diamond.getOnlinePlayers().indexOf(this.username) != -1 && Diamond.getOnlinePlayers().size() < ServerSettings.getMaxPlayers()) Diamond.getOnlinePlayers().add(this.username);
			if(Diamond.getOnlinePlayers().size() >= ServerSettings.getMaxPlayers()) event.cancelLogin("The server is full!");
		}
		
		if(!event.getLoginCancelled()) {
			// Create key
			byte[] publicKey = null;
			byte[] verifyToken = null;
			try {
				KeyPair pair = SecurityUtils.generateKeyPair();
				publicKey = SecurityUtils.generateX509Key(pair.getPublic()).getEncoded();
				verifyToken = SecurityUtils.generateVerifyToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Write data
			ByteArrayOutputStream data = new ByteArrayOutputStream();
			data.write((byte) 0x01);
			data.write(VarInt.writeUnsignedVarInt(1));
			data.write(" ".getBytes());
			data.write(VarInt.writeUnsignedVarInt(publicKey.length));
			data.write(publicKey);
			data.write(VarInt.writeUnsignedVarInt(verifyToken.length));
			data.write(verifyToken);
			data.flush();
			
			// Send packet
			output.write(VarInt.writeUnsignedVarInt(data.size()));
			output.write(data.toByteArray());
			output.flush();
			
			// Wait for response, and verify tokens
			while(input.available() == 0);
			VarInt.readUnsignedVarInt(input, true); // Read len
			int packetID = VarInt.readUnsignedVarInt(input.readByte());
			if(packetID == (byte) 0x02) {
				byte[] rPublic = PacketUtils.readBytes(input, VarInt.readUnsignedVarInt(input, true));
				byte[] rVerify = PacketUtils.readBytes(input, VarInt.readUnsignedVarInt(input, true));
				// Create new player
			} else {
				// Disconnect the client
				ClientDisconnectPacket packet = new ClientDisconnectPacket(playerSocket, "Your client seems to have sent the invalid data in the login process!");
				packet.setShouldLog(true);
				packet.setUsername(username);
				packet.sendResponse();
			}
		}
		
	}

}
