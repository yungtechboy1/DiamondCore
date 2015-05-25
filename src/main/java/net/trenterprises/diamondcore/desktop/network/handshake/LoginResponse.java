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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Random;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.event.TriggerCause;
import net.trenterprises.diamondcore.cross.api.java.event.player.PlayerLoginEvent;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server.PluginManager;
import net.trenterprises.diamondcore.cross.borrowed.RSA;
import net.trenterprises.diamondcore.cross.borrowed.VarInt;
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
	protected Socket s;
	protected DataInputStream input;
	protected DataOutputStream output;
	
	// Packet info
	protected String username;
	
	// Event info
	protected PlayerLoginEvent event;
	
	public LoginResponse(Socket s) throws IOException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Decode packet
		this.decode();
		
		// Throw event
		this.event = new PlayerLoginEvent(TriggerCause.DESKTOP, this.s, this.s.getInetAddress(), this.s.getPort());
		PluginManager.throwEvent(this.event);
		if(!Diamond.getOnlinePlayers().contains(this.username) && event.getLoginCancelled()) Diamond.getOnlinePlayers().add(this.username);
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
			if(Diamond.getOnlinePlayers().size() >= ServerSettings.getMaxPlayers()) new ClientDisconnectPacket(this.s, "The server is full!");
		}
		
		if(!event.getLoginCancelled()) {
			output.write(VarInt.writeUnsignedVarInt(0));
			output.write("".getBytes());
			BigInteger keyOne = RSA.generateKey(new Random().nextInt(10));
			output.write(VarInt.writeUnsignedVarInt(keyOne.toByteArray().length));
			output.write(keyOne.toByteArray());
			BigInteger keyTwo = RSA.generateKey(new Random().nextInt(10));
			output.write(VarInt.writeUnsignedVarInt(keyTwo.toByteArray().length));
			output.write(keyTwo.toByteArray());
			output.flush();
		}
	}

}
