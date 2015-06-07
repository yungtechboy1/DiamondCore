/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore.desktop.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This class is used to broadcast the server over the local network
 * for Desktop players that happen to be on the same network. This helps
 * increase convenience for people that just want to "plug & play"
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class LocalServerBroadcaster extends Thread {
	
	// Data
	protected final DatagramSocket socket;
	protected final InetAddress address;
	protected final int port;
	
	// Management
	protected boolean shouldStop = false;
	
	// Data change-able by plugins
	protected String motd;
	
	public LocalServerBroadcaster(InetAddress address, int port) throws IOException {
		this.socket = new DatagramSocket(null);
		this.socket.setReuseAddress(true);
		this.address = address;
		this.port = port;
		this.motd = ("Local DiamondCore World (" + InetAddress.getLocalHost().getHostName() + ")");
	}
	
	public void setMOTD(String newMotd) {
		this.motd = newMotd;
	}
	
	public void run() {
		while(true) {
			try {
				if(shouldStop) {
					socket.close();
					break;
				}
				byte[] data = ("[MOTD]" + motd + "[/MOTD][AD]" + port + "[/AD]").getBytes();
				socket.send(new DatagramPacket(data, data.length, address, port));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
