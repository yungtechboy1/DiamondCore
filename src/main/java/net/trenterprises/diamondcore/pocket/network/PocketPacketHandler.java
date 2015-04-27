/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
*/

package net.trenterprises.diamondcore.pocket.network;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import net.trenterprises.diamondcore.DiamondCoreServer;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;
import net.trenterprises.diamondcore.pocket.network.handlers.JoinRequestStage1Response;
import net.trenterprises.diamondcore.pocket.network.handlers.JoinRequestStage2Response;
import net.trenterprises.diamondcore.pocket.network.handlers.ServerListPingResponse;
import net.trenterprises.diamondcore.pocket.network.other.PocketDisconnectPacket;
import net.trenterprises.diamondcore.pocket.network.utils.PocketPacketUtils;

import org.blockserver.io.BinaryReader;

/**
 * This is the listener for packets sent by Minecraft Pocket Edition clients
 * 
 * @author Trent Summerlin
 * @author jython234
 * @version 1.1
 */
public class PocketPacketHandler extends Thread implements Runnable {
	
	private DatagramSocket socket;
	private long ServerID = new Random().nextLong();
	private HashMap<String, PocketSession> sessions = new HashMap<String, PocketSession>();
	public DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	public PocketPacketHandler(DatagramSocket socket, DiamondCoreServer server) throws IOException {
		// Set socket Settings
		this.socket = socket;
		socket.setBroadcast(true);
		socket.setSendBufferSize(65535);
		socket.setReceiveBufferSize(65535);
		if(!socket.isBound()) socket.bind(new InetSocketAddress(ServerSettings.getServerIP(), ServerSettings.getPEPort()));
	}
	
	/**
	 * Used to check if any packets were received, and if any were, then respond to them.
	 * 
	 * @author Trent Summerlin
	 * @author jython234
	 * @version 1.1
	 */
	public void run() {
		while(true) {
			try {
				// Get ready to receive message
				byte[] pocketBuffer = new byte[1535];
				DatagramPacket pocketPacket = new DatagramPacket(pocketBuffer, pocketBuffer.length);
				socket.receive(pocketPacket);
				
				//Strip extra null bytes
				pocketPacket.setData(Arrays.copyOf(pocketPacket.getData(), pocketPacket.getLength()));
					
				// Now handle it
				byte packetID = pocketPacket.getData()[0];
				BinaryReader in = new BinaryReader(new ByteArrayInputStream(pocketBuffer));
				
				if(sessions.containsKey(pocketPacket.getSocketAddress().toString())){
					sessions.get(pocketPacket.getSocketAddress().toString()).handlePacket(in);
				} else {
					switch(packetID) {
						case PocketPacketIDList.ID_CONNECTED_PING_OPEN_CONNECTIONS:
							new ServerListPingResponse(socket, pocketPacket, ServerID);
							break;
						case PocketPacketIDList.ID_OPEN_CONNECTION_REQUEST_1:
							new JoinRequestStage1Response(socket, pocketPacket, ServerID);
							break;
						case PocketPacketIDList.ID_OPEN_CONNECTION_REQUEST_2:
							new JoinRequestStage2Response(socket, pocketPacket, ServerID);
							break;
						default:
							byte[] test = PocketPacketUtils.toByteArray(new PocketDisconnectPacket("lol").encode().getInputStream());
							socket.send(new DatagramPacket(test, test.length, pocketPacket.getAddress(), pocketPacket.getPort()));
							/*byte pid = (byte) PacketID;
							if(pid <= PocketPacketIDList.RAKNET_CUSTOM_PACKET_MAX){
								//Must be a 0x09, create a new session for it.
								PocketSession session = new PocketSession(server, this, pocketPacket.getSocketAddress());
								sessions.put(pocketPacket.getSocketAddress().toString(), session);
								session.handlePacket(in);
							} else {
								logger.warn("Received unkown PacketID: " + PacketID);
							}*/
							break;
					}
				}
			}
			catch(SocketTimeoutException e) {
				/*Ignore, this will happen sometimes.*/
			}
			catch(IOException E) {
				E.printStackTrace();
			}
		}
	}
	
}