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
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.diamondcore.desktop.handlers.HandshakeResponse;
import org.diamondcore.diamond.Diamond;
import org.diamondcore.utils.VarInt;

/**
 * Used to listen for connections and respond to them
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class DesktopPacketHandler extends Thread {
	
	protected final ServerSocket ss;
	
	public DesktopPacketHandler(ServerSocket ss) throws IOException {
		this.ss = ss;
		
	}
	
	public void run() {
		while(true) {
			try {
				// Initialize
				Socket socket = ss.accept();
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
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
