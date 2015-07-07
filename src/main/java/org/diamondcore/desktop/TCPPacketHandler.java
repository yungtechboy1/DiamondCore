/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|
                                                                                                 
*/

package org.diamondcore.desktop;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.diamondcore.Diamond;
import org.diamondcore.desktop.handlers.HandshakeResponse;
import org.diamondcore.exception.DiamondException;
import org.diamondcore.utils.VarInt;

/**
 * Used to listen for connections and respond to them
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class TCPPacketHandler extends Thread {
	
	protected final ServerSocket ss;
	
	public TCPPacketHandler(ServerSocket ss) throws IOException {
		this.ss = ss;
		
	}
	
	public void run() {
		while(true) {
			try {
				// Initialize
				Socket socket = ss.accept();
				DataInputStream input = new DataInputStream(socket.getInputStream());
				
				// Read packet data
				int len = VarInt.readUnsignedVarInt(input); // Read the VarInt sent by Minecraft
				int packetID = VarInt.readUnsignedVarInt(input.readByte());
				
				switch(packetID) {
					case PacketIDList.HANDSHAKE_PACKET:
						HandshakeResponse packet = new HandshakeResponse(socket);
						packet.sendResponse();
						break;
					default:
						Diamond.logger.info("Received unknown ID: " + packetID + "\nLength: " + len);
						break;
				}
			} catch (IOException | DiamondException e) {
				e.printStackTrace();
			}
		}
	}
	
}
