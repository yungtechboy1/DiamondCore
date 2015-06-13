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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.diamondcore.Diamond;
import org.diamondcore.desktop.PacketIDList;
import org.diamondcore.utils.VarInt;
import org.json.simple.JSONObject;

/**
 * Packet used to disconnect a player
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class ClientDisconnectPacket {
	
	// Socket info
	protected final Socket playerSocket;
	protected final DataInputStream input;
	protected final DataOutputStream output;
	
	// Packet info
	protected String reason;
	
	// Extra info
	protected boolean shouldLog;
	protected String username;
	
	public ClientDisconnectPacket(Socket s, String reason) throws IOException {
		this.playerSocket = s;
		this.input = new DataInputStream(s.getInputStream());
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
		
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		data.write(PacketIDList.CLIENT_DISCONNECT);
		data.write(VarInt.writeUnsignedVarInt(object.toString().getBytes().length));
		data.write(object.toString().getBytes());
		data.flush();
		
		output.write(VarInt.writeUnsignedVarInt(data.size()));
		output.write(data.toByteArray());
		output.flush();
		
		if(shouldLog && username != null) Diamond.logger.info("Disconnected player " + username + " for \"" + reason + "\"");
		data.close();
	}
	
}