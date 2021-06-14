/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.desktop;

/**
 * Used to store packet ID's and access them with ease
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class PacketIDList {
	
	private PacketIDList() {}
	
	/**
	 * C <-> S: This is sent between the client and server in the process of logging in
	 */
	public static final byte HANDSHAKE_PACKET = (byte) 0x00;
	
	/**
	 * S -> C: Server list pong sent by the server for the MOTD
	 */
	public static final byte SERVER_PING_RESPONSE = HANDSHAKE_PACKET;
	
	/**
	 * S -> C: Sent by the server to disconnect the client
	 */
	public static final byte CLIENT_DISCONNECT = (byte) 0x00;

}
