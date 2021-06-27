/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.pocket;

/**
 * This class is used for easily retrieving RakNet ID's without having to memorize them
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public abstract class PacketIDList {
	
	/**
	 * This is the current protocol ID for Minecraft: Pocket
	 * <br>
	 * Edition clients use when connecting to a server to ensure
	 * <br>
	 * that the server is the right version
	 * 
	 * S <-> C : Not a packet ID
	 */
	public static final int PROTOCOL_ID = 440;
	
	// MAGIC
        public static final byte[] MAGIC = new byte[] {(byte) 0x00, (byte) 0xff, (byte) 0xff, 0x00, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfe, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0xfd, (byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78};

	/* Login Packets */
	/**
	 * C -> S : 0x01 : Sent from the client when pinging for servers on port 19132.
	 **/
	public final static byte ID_CONNECTED_PING_OPEN_CONNECTIONS = (byte) 0x01;
	
	/**
	 * C- > S : 0x05 : Sent from the client when trying to join the server
	 **/
	public final static byte ID_OPEN_CONNECTION_REQUEST_1 = (byte) 0x05;
	
	/**
	 * S -> C : 0x06 : Sent from the server in response to ID_OPEN_CONNECTION_REQUEST_1.
	 **/
	public final static byte ID_OPEN_CONNECTION_REPLY_1 = (byte) 0x06;
	
	/**
	 * C -> S : 0x07 : Sent from the client in response to ID_OPEN_CONNECTION_REPLY_1.
	 **/
	public final static byte ID_OPEN_CONNECTION_REQUEST_2 = (byte) 0x07;
	
	/**
	 * S -> C : 0x08 : Final packet sent from the server or client in the login Process.
	 * Note: This packet is in response to ID_OPEN_CONNECTION_REQUEST_2.
	 **/
	public final static byte ID_OPEN_CONNECTION_REPLY_2 = (byte) 0x08;
	
	/**
	 *  S -> C: 0x84 : Sent by the server when disconnecting a client
	 */
	public final static byte DISCONNECT_PACKET = (byte) 0x05;
	
	
	/**
	 * S -> C : 0x1a : Sent from the server protocol version does not match the client 
	 **/
	public final static byte ID_INCOMPATIBLE_PROTCOL_VERSION = (byte) 0x1a;
	
	/**
	 * S -> C : 0x1c : Response from the server when pinged by a client.
	 **/
	public final static byte ID_UNCONNECTED_PING_OPEN_CONNECTIONS = (byte) 0x1c;
	
	/**
	 * S <-> C: 0x8X : Custom Packet List, uses Port number from ACK to send.
	 * Note: To use, 
	 */
	public final static byte[] CUSTOM_PACKET_LIST = {(byte) 0x80, (byte) 0x81, (byte) 0x82, (byte) 0x83, (byte) 0x84, (byte) 0x85, (byte) 0x86, (byte) 0x87, (byte) 0x88, (byte) 0x89, (byte) 0x8A, (byte) 0x8B, (byte) 0x8C, (byte) 0x8D, (byte) 0x8E, (byte) 0x8F};
	
	/**
	 * S <-> C : 0xA0 : Sent when a packet is lost between the server and/or the client.
	 **/
	public final static byte NACK = (byte) 0xa0;
	
	/**
	 * S <-> C: 0xC0 : Sent after a 0x8X to Acknowledge (ACK) the receiving of packets.
	 * Note: Packet Number #2 is only sent when the Additional Packet boolean is false.
	 **/
	public final static byte ACK = (byte) 0xc0;
	
	/**
	 * (Two Way) RakNet custom packet min.
	 **/
	public final static byte RAKNET_CUSTOM_PACKET_MIN = (byte) 0x80;
	
	/**
	 * (Two Way) RakNet custom packet max. 
	 **/
	public final static byte RAKNET_CUSTOM_PACKET_MAX = (byte) 0x8F;
	
	/* End of Login Packets */
	
}
