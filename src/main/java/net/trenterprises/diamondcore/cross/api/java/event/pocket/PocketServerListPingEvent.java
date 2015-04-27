/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.event.pocket;

import java.net.InetAddress;

/**
 * This event is thrown whenever someone on MCPE pings the server
 * from his local network. This is impossible to run if the server
 * is not running on port 19132 unless the client is modded to look
 * for servers on another port.
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public final class PocketServerListPingEvent extends PocketEvent {
	
	private InetAddress address;
	private int port = 0;
	private boolean broadcast = true;
	private String motd = null;
	
	public PocketServerListPingEvent(net.trenterprises.diamondcore.pocket.network.handlers.ServerListPingResponse ServerListPingResponseClass, InetAddress Address, int Port, String MOTD) {
		this.address = Address;
		this.port = Port;
		this.motd = MOTD;
	}
	
	/**
	 * Used to get the event name
	 */
	public String getName() {
		return "PocketServerListPingEvent";
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
	public void setMOTD(String NewMOTD) {
		motd = NewMOTD;
	}
	
	/**
	 * Used to see if the server is broadcasting the MOTD
	 * 
	 * @return Server MOTD broadcast state
	 * @author Trent Summerlin
	 */
	public boolean shouldBroadcast() {
		return broadcast;
	}
	
	/**
	 * Used to set whether or not it should even show up in the world list
	 * 
	 * @param Broadcast state
	 * @author Trent Summerlin
	 */
	public void setShouldBroadcast(boolean ShouldBroadcast) {
		broadcast = ShouldBroadcast;
	}
	
}
