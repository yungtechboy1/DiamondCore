/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
*/

package net.trenterprises.diamondcore.desktop.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import net.trenterprises.diamondcore.DiamondCoreServer;
import net.trenterprises.diamondcore.cross.borrowed.VarInt;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;
import net.trenterprises.diamondcore.desktop.network.handlers.ServerListPingResponse;

/**
 * This class is used to handle packets sent by Minecraft desktop clients
 * 
 * @author Trent Summerlin
 * @author jython234
 * @version 1.0
 */
public class DesktopPacketHandler extends Thread implements Runnable {
	
	private ServerSocket serverSocket;
	public DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	public DesktopPacketHandler(ServerSocket socket, DiamondCoreServer server) throws IOException {
		// Set socket Settings
		this.serverSocket = socket;
		if(!socket.isBound()) socket.bind(new InetSocketAddress(ServerSettings.getServerIP(), ServerSettings.getPCPort()));
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
				Socket socket = serverSocket.accept();
				DataInputStream input = new DataInputStream(socket.getInputStream());
				int receivedPacketLength = VarInt.readUnsignedVarInt(input.readByte()); // Read packet length
				byte packetID = (byte) VarInt.readUnsignedVarInt(input.readByte());
				
				if(packetID == DesktopPacketIDList.HANDSHAKE_PACKET) {
					new ServerListPingResponse(socket, receivedPacketLength, packetID);
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
