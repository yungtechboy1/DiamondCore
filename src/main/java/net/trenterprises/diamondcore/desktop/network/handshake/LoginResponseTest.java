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
import java.net.Socket;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.desktop.network.utils.PacketUtils;

/**
 * Used to handle a client login packet
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class LoginResponseTest extends HandshakePacket {
	
	// Socket info
	protected Socket s;
	protected DataInputStream input;
	protected DataOutputStream output;
	
	// Packet info
	protected String username;
	
	public LoginResponseTest(Socket s) throws IOException {
		this.s = s;
		this.input = new DataInputStream(this.s.getInputStream());
		this.output = new DataOutputStream(this.s.getOutputStream());
		
		// Decode packet
		this.decode();
		if(!Diamond.getOnlinePlayers().contains(this.username)) Diamond.getOnlinePlayers().add(this.username);
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
		this.username = PacketUtils.readString(this.input);
	}

	@Override
	public void sendResponse() throws IOException {
		
	}

}
