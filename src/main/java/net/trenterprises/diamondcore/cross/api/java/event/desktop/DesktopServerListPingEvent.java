package net.trenterprises.diamondcore.cross.api.java.event.desktop;

import java.net.InetAddress;

import net.trenterprises.diamondcore.cross.api.java.event.Event;

public final class DesktopServerListPingEvent extends Event {
	
	private InetAddress address;
	private int port = 0;
	private String motd = null;
	private String favicon = null;
	
	public DesktopServerListPingEvent(net.trenterprises.diamondcore.desktop.network.handshake.ServerListPingResponse ServerListPingResponseClass, InetAddress Address, int Port, String MOTD, String Favicon) {
		this.address = Address;
		this.port = Port;
		this.motd = MOTD;
		this.favicon = Favicon;
	}
	
	/**
	 * Used to get the event name
	 */
	public String getName() {
		return "DesktopServerListPingEvent";
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
	 * @author Trent Summerlin
	 */
	public int getPort() {
		return port;
	}
	
	/**
	 * Used to get the MOTD that is being sent by the server
	 * 
	 * @return MOTD being sent by the server
	 * @author Trent Summerlin
	 */
	public String getMOTD() {
		return motd;
	}
	
	/**
	 * Used to get the Favicon that is being sent by the server
	 * 
	 * @return Favicon being sent by the server
	 * @author Trent Summerlin
	 */
	public String getFavicon() {
		return favicon;
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
	 * Used to set the Favicon to something else for that specific ping
	 * <br>
	 * It is possible to convert a image file to a base64 string by using
	 * <br>
	 * the following code: {@code Diamond.loadServerIcon(File);}
	 * 
	 * @param String
	 * 		The Base64 string to use as a favicon
	 * @author Trent Summerlin
	 */
	public void setFavicon(String base64) {
		favicon = base64;
	}
	
}
