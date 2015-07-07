/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.desktop.packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.diamondcore.Diamond;
import org.diamondcore.desktop.DesktopPacket;
import org.diamondcore.desktop.PacketIDList;
import org.json.simple.JSONObject;

/**
 * Packet used to disconnect a player
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class ClientDisconnectPacket {
	
	// Socket info
	private final DataOutputStream output;
	
	// Packet info
	protected String reason;
	
	// Extra info
	protected boolean shouldLog;
	protected String username;
	
	public ClientDisconnectPacket(Socket s, String reason) throws IOException {
		this.output = new DataOutputStream(s.getOutputStream());
		this.reason = reason;
	}
	
	/**
	 * Sets the packet so it logs when the player was disconnected
	 * <br>
	 * Note: for this to take effect, the user should also issue {@code setUsername(username)}
	 * If it is not set, it will not log it.
	 * 
	 * @author Trent Summerlin
	 * @param shouldLog
	 */
	public void setShouldLog(boolean shouldLog) {
		this.shouldLog = shouldLog;
	}
	
	/**
	 * Set's the username used when logging the disconnect message
	 * 
	 * @author Trent Summerlin
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	@SuppressWarnings("unchecked")
	public void disconnect() throws IOException {
		JSONObject object = new JSONObject();
		object.put("text", this.reason.toString());
		DesktopPacket packet = new DesktopPacket(PacketIDList.CLIENT_DISCONNECT);
		packet.writeString(object.toString());
		
		output.write(packet.toByteArray());
		output.flush();
		
		if(shouldLog && username != null)
			Diamond.logger.info("Disconnected player " + username + " for \"" + reason + "\"");
	}
	
}
