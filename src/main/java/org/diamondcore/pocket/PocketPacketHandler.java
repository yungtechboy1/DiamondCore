/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
*/

package org.diamondcore.pocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.blockserver.io.BinaryReader;
import org.diamondcore.PlayerSession;
import org.diamondcore.diamond.Diamond;
import org.diamondcore.pocket.handlers.JoinRequestResponse;
import org.diamondcore.pocket.handlers.ServerListPingResponse;
import org.diamondcore.utils.ServerSettings;

/**
 * This is the listener for packets sent by Minecraft Pocket Edition clients
 * 
 * @author Trent Summerlin
 * @author jython234
 * @version 0.1.0-SNAPSHOT
 */
public class PocketPacketHandler extends Thread {
	
	private DatagramSocket socket;
	private long ServerID = new Random().nextLong();
	private HashMap<String, PlayerSession> sessions = new HashMap<String, PlayerSession>();
	
	public PocketPacketHandler(DatagramSocket socket/*, DiamondCoreServer server*/) throws IOException {
		// Set socket Settings
		this.socket = socket;
		socket.setBroadcast(true);
		socket.setSendBufferSize(65535);
		socket.setReceiveBufferSize(65535);
		if(!socket.isBound()) socket.bind(new InetSocketAddress(ServerSettings.getServerIP(), ServerSettings.getPEPort()));
	}
	
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
				BinaryReader in = new BinaryReader(new ByteArrayInputStream(pocketBuffer));
				byte packetID = in.readByte();
				
				if(sessions.containsKey(pocketPacket.getSocketAddress().toString())){
					// Check if player is a MCPE player
				} else {
					switch(packetID) {
						case PocketPacketIDList.ID_CONNECTED_PING_OPEN_CONNECTIONS:
							new ServerListPingResponse(socket, pocketPacket, ServerID);
							break;
						case PocketPacketIDList.ID_OPEN_CONNECTION_REQUEST_1:
							new JoinRequestResponse(socket, pocketPacket, ServerID, 1);
							break;
						case PocketPacketIDList.ID_OPEN_CONNECTION_REQUEST_2:
							new JoinRequestResponse(socket, pocketPacket, ServerID, 2);
							break;
						case -124:
							System.out.println("----");
							ByteBuffer bb = ByteBuffer.wrap(pocketPacket.getData());
							bb.get(); bb.get();
							short length = (short) (bb.getShort()/8);
							System.out.println(((pocketPacket.getData().length - length) == 12) + " -- " + (pocketPacket.getData().length - length));
							System.out.println("----");
					       // Diamond.logger.info("NUMBER: " + test1);
							break;
						default:
							Diamond.logger.warn("RECEIVED UNKNOWN PACKET ID: " + pocketPacket.getData()[0]);
							break;
					}
				}
				
				in.close();
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