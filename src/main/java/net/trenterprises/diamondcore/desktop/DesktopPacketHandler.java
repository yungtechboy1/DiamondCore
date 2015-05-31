/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\| 
                                                                                                       
 */

package net.trenterprises.diamondcore.desktop;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import net.trenterprises.diamondcore.DiamondCoreServer;
import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.utils.VarInt;
import net.trenterprises.diamondcore.desktop.handlers.HandshakeResponse;
import net.trenterprises.diamondcore.desktop.handshake.LoginResponse;

/**
 * This class is used to handle packets sent by Minecraft desktop clients
 * 
 * @author Trent Summerlin
 * @author jython234
 * @version 1.0
 */
public class DesktopPacketHandler extends Thread implements Runnable {

	protected final ServerSocket serverSocket;
	
	public DesktopPacketHandler(ServerSocket socket, DiamondCoreServer server) throws IOException {
		// Set socket Settings
		this.serverSocket = socket;
		if (!socket.isBound()) socket.bind(new InetSocketAddress(ServerSettings.getServerIP(), ServerSettings.getPCPort()));
	}

	/**
	 * Used to check if any packets were received, and if any were, then respond
	 * to them.
	 * 
	 * @author Trent Summerlin
	 * @author jython234
	 * @version 1.1
	 */
	public void run() {
		while (true) {
			try {
				// Set socket data
				serverSocket.setSoTimeout(1);
				Socket socket = serverSocket.accept();
				DataInputStream input = new DataInputStream(socket.getInputStream());
				
				// Read packet data
				VarInt.readUnsignedVarInt(input, true); // Read the unused VarInt sent by Minecraft
				int packetID = VarInt.readUnsignedVarInt(input.readByte());
				
				switch(packetID) {
					case DesktopPacketIDList.HANDSHAKE_PACKET:
						HandshakeResponse packet = new HandshakeResponse(socket); // Also handles login as well
						break;
					default:
						Diamond.logger.info("Received unknown ID: " + packetID);
						break;
				}
			} catch (SocketTimeoutException e) {
				/* Ignore, this will happen sometimes. */
			} catch (IOException E) {
				E.printStackTrace();
			}
		}
	}

}
