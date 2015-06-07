/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.api.event.server;

import org.diamondcore.api.PlayerType;
import org.diamondcore.api.event.Event;

/**
 * Thrown when a player pings the server
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class ServerListPingEvent extends Event {
	
	private final PlayerType type;
	private String motd;
	
	public ServerListPingEvent(PlayerType type, String motd) {
		this.type = type;
		this.motd = motd;
	}
	
	@Override
	public String getName() {
		return "ServerListPingEvent";
	}

	@Override
	public PlayerType getPlayerType() {
		return this.type;
	}
	
	/**
	 * Used to set the MOTD that shows up for the player
	 * 
	 * @param motd
	 * @author Trent Summerlin
	 */
	public void setMOTD(String motd) {
		this.motd = motd;
	}
	
	/**
	 * Used to get the MOTD that shows up for the player
	 * 
	 * @return MOTD
	 * @author Trent SUmmerlin
	 */
	public String getMOTD() {
		return this.motd;
	}
	
}
