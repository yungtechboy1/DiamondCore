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

import java.net.InetAddress;

import org.diamondcore.Diamond;
import org.diamondcore.api.PlayerType;
import org.diamondcore.api.event.Event;
import org.diamondcore.desktop.Favicon;
import org.diamondcore.utils.ServerSettings;

/**
 * This event is thrown whenever a client pings the server
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class ServerListPingEvent extends Event {
	
	// Player info
	private final PlayerType type;
	private final InetAddress address;
	private final int port;
	
	// Server info
	private Favicon favicon;
	private String motd;
	private String tag;
	private int protocol;
	private int max;
	private int online;
	
	public ServerListPingEvent(PlayerType type, InetAddress address, int port, String motd) {
		this.type = type;
		this.address = address;
		this.port = port;
		this.motd = motd;
		this.setExtra();
	}
	
	public ServerListPingEvent(PlayerType type, InetAddress address, int port, Favicon favicon, String motd) {
		this.type = type;
		this.address = address;
		this.port = port;
		this.favicon = favicon;
		this.motd = motd;
		this.favicon = null;
		this.setExtra();
	}
	
	/**
	 * This method is used to set the extra tid-bits
	 * of data which can be changed depending on the
	 * player type
	 * 
	 * @author Trent Summerlin
	 */
	private final void setExtra() {
		if(type == PlayerType.POCKET)
			this.protocol = Diamond.pocketProtocol;
		else if(type == PlayerType.DESKTOP) 
			this.protocol = Diamond.desktopProtocol;
		this.max = ServerSettings.getMaxPlayers();
		this.online = 0; // TODO: Set amount to actual online players when possible
		this.tag = Diamond.versionTag + " " + (type == PlayerType.DESKTOP ? Diamond.desktopVersion : Diamond.pocketVersion);
	}
	
	@Override
	public final String getName() {
		return "ServerListPingEvent";
	}

	@Override
	public final PlayerType getPlayerType() {
		return this.type;
	}
	
	/**
	 * Used to get the address the player's
	 * device is running on
	 * 
	 * @return The player's address
	 * @author Trent Summerlin
	 */
	public final InetAddress getAddress() {
		return this.address;
	}
	
	/**
	 * Used to get the port the player's
	 * device is running on
	 * 
	 * @return The player's port
	 * @author Trent Summeroin
	 */
	public final int getPort() {
		return this.port;
	}
	
	/**
	 * Used to get the favicon that 
	 * is shown for the player, if it
	 * is null, it is likely the player is
	 * running MCPE or the favicon does
	 * not exist
	 * 
	 * @return The server favicon
	 * @author Trent Summerlin
	 */
	public final Favicon getFavicon() {
		return this.favicon;
	}
	
	/**
	 * This function is used to get the MOTD
	 * that other players see when pinging the
	 * server, this can be changed by running
	 * {@code setMOTD(motd)}
	 * 
	 * @return Current MOTD
	 * @author Trent Summerlin
	 */
	public final String getMOTD() {
		return this.motd;
	}
	
	/**
	 * This function is used to get the
	 * protocol version name that is
	 * displayed to the player
	 * 
	 * @return The protocol version
	 * @author Trent Summerlin
	 */
	public final String getProtocolTag() {
		return this.tag;
	}
	
	/**
	 * This function is used to get the
	 * protocol ID being sent to the
	 * client that is pinging the server
	 * 
	 * @return The protocol ID
	 * @author Trent Summerlin
	 */
	public final int getProtocol() {
		return this.protocol;
	}
	
	/**
	 * This function is used to get the
	 * amount of players that can be online
	 * at once displayed to the user pinging
	 * the server
	 * 
	 * @return The amount of players that can be online displayed to the player
	 * @author Trent Summerlin
	 */
	public final int getMaxPlayers() {
		return this.max;
	}
	
	/**
	 * This function is used to get the
	 * amount of players online displayed
	 * to the user pinging the server
	 * 
	 * @return The amount of players online displayed to the player
	 * @author Trent Summerlin
	 */
	public final int getOnlinePlayers() {
		return this.online;
	}
	
	/**
	 * This function is used to set the Favicon
	 * displayed to the player that is pinging
	 * the server
	 * 
	 * @param favicon
	 * 		- The favicon to display
	 * @author Trent Summerlin
	 */
	public final void setFavicon(Favicon favicon) {
		this.favicon = favicon;
	}
	
	/**
	 * This function is used to set the MOTD
	 * for players that are pinging the server,
	 * it is useful as it can be used for plenty
	 * of things.
	 * 
	 * @param motd
	 * 		- The new MOTD to be used
	 * @author Trent Summerlin
	 */
	public final void setMOTD(String motd) {
		this.motd = motd;
	}
	
	/**
	 * This function is used to set the protocol
	 * tag that is displayed to the player that
	 * is pinging the server 
	 * 
	 * @param version
	 * 		- The new tag to show
	 * @author Trent Summerlin
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private final void setProtocolTag(String version) {
		this.tag = version;
	}
	
	/**
	 * This function is used to set the protocol
	 * that is sent to the client which is used
	 * to determine if the server and client are
	 * multiplayer compatible
	 * 
	 * @param protocol
	 * 		- The new protocol to send to the client
	 * @author Trent Summerlin
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private final void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * This function is used to set the max players
	 * that are displayed to the player, pinging
	 * the server, this will not change the amount
	 * of players that can actually join the server
	 * 
	 * @param max
	 * 		- The new amount of max players
	 * @author Trent Summerlin
	 */
	public final void setMaxPlayers(int max) {
		this.max = max;
	}
	
	/**
	 * This function is used to set the amount
	 * of online players that are displayed to
	 * the player pinging the sever
	 * 
	 * @param online
	 * 		- The new amount of online players
	 * @author Trent Summerlin
	 */
	public final void setOnlinePlayers(int online) {
		this.online = online;
	}
	
}
