package net.trenterprises.diamondcore.desktop.network;

/**
 * This class is used to store Minecraft desktop packet ID's
 * <br>
 * to make it easier to handle packets sent by Minecraft desktop
 * <br>
 * client's
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class DesktopPacketIDList {
	
	/**
	 * S <-> C : Used by the client and the server when logging in or doing other fancy things such as the MOTD
	 */
	public static final byte HANDSHAKE_PACKET = (byte) 0x00;
	
}
