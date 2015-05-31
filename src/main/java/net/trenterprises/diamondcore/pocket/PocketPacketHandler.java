/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
*/

package net.trenterprises.diamondcore.pocket;

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

import net.trenterprises.diamondcore.DiamondCoreServer;
import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.pocket.handlers.JoinRequestStage1Response;
import net.trenterprises.diamondcore.pocket.handlers.JoinRequestStage2Response;
import net.trenterprises.diamondcore.pocket.handlers.ServerListPingResponse;
import net.trenterprises.diamondcore.pocket.other.PocketDisconnectPacket;
import net.trenterprises.diamondcore.pocket.utils.PocketPacketUtils;

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
							byte[] test = PocketPacketUtils.toByteArray(new PocketDisconnectPacket("lol").encode().getInputStream());
							socket.send(new DatagramPacket(test, test.length, pocketPacket.getAddress(), pocketPacket.getPort()));
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