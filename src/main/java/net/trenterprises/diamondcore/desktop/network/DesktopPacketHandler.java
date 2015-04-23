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
import java.net.SocketException;
import java.net.SocketTimeoutException;

import net.trenterprises.diamondcore.DiamondCoreServer;
import net.trenterprises.diamondcore.cross.Player;
import net.trenterprises.diamondcore.cross.borrowed.VarInt;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;
import net.trenterprises.diamondcore.desktop.network.handlers.HandshakeResponse;
import net.trenterprises.diamondcore.desktop.network.handshake.LoginResponseTest;
import net.trenterprises.diamondcore.desktop.network.packet.ClientDisconnectPacket;

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
		if (!socket.isBound()) socket.bind(new InetSocketAddress(ServerSettings.getServerIP(),ServerSettings.getPCPort()));
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
				serverSocket.setSoTimeout(1);
				Socket socket = serverSocket.accept();
				DataInputStream input = new DataInputStream(socket.getInputStream());
				
				input.readByte(); // Bump up
				int packetID = VarInt.readUnsignedVarInt(input.readByte());
				
				switch(packetID) {
					case DesktopPacketIDList.HANDSHAKE_PACKET:
						HandshakeResponse packet = new HandshakeResponse(socket); // Also handles login as well
						if(packet.getHandshakePacket() instanceof LoginResponseTest) {
							LoginResponseTest test = (LoginResponseTest) packet.getHandshakePacket();
							Player player = new Player(test.getPlayerName());
							String reason = (player.isBanned() ? "You are banned!" : "This server is still in the works!");
							new ClientDisconnectPacket(socket, reason).sendResponse();
						
						}
						break;
					default:
						logger.info("Received unknown ID: " + packetID);
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
