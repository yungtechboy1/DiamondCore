/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.event.server;

import java.net.InetAddress;

import net.trenterprises.diamondcore.cross.api.java.event.Event;
import net.trenterprises.diamondcore.cross.api.java.event.TriggerCause;

/**
 * This event is thrown whenever someone on MCPE pings the server
 * from his local network. This is impossible to run if the server
 * is not running on port 19132 unless the client is modded to look
 * for servers on another port.
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public final class ServerListPingEvent extends Event {
	
	private final TriggerCause cause;
	private final InetAddress address;
	private final int port;
	private String motd = null; // Not final because it can be changed
	private String favicon = null; // Not final because it can be changed
	
	public ServerListPingEvent(TriggerCause cause, InetAddress address, int port, String motd, String favicon) {
		this.cause = cause;
		this.address = address;
		this.port = port;
		this.motd = motd;
		this.favicon = favicon;
	}
	
	public String getName() {
		return "ServerListPingEvent";
	}
	
	/**
	 * Used to get the player's InetAddress
	 * 
	 * @return Player's InetAddress
	 * @author Trent Summerlin
	 */
	public InetAddress getAddress() {
		return address;
	}
	
	/**
	 * Used to get the player's port
	 * 
	 * @return Player's port
	 * @author SuperstarGamer
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Used to get the MOTD that is being sent by the server
	 * 
	 * @return MOTD being sent by the server
	 * @author SuperstarGamer
	 */
	public String getMOTD() {
		return motd;
	}
	
	/**
	 * Used to set the MOTD to something else for that specific ping
	 * <br>
	 * NOTE: This will not set the MOTD in the server properties
	 * 
	 * @param New server MOTD
	 * @author Trent Summerlin
	 */
	public void setMOTD(String motd) {
		this.motd = motd;
	}
	
	/**
	 * Used to get the favicon represented in a Base64.
	 * <br>
	 * This will return null if the player is on Pocket
	 * 
	 * @return Base64 Favicon
	 */
	public String getFavicon() {
		if(cause == TriggerCause.DESKTOP) return favicon;
		else return null;
	}
	
	/**
	 * Used to set the favicon shown in the server list
	 * <br>
	 * This will only execute if the player is on Desktop
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}
	
}
