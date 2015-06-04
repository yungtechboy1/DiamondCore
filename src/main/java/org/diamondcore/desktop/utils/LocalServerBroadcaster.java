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

import org.diamondcore.utils.ServerSettings;

/**
 * This class is used to broadcast the server over the local network
 * for Desktop players that happen to be on the same network. This helps
 * increase convenience for people that just want to "plug & play"
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class LocalServerBroadcaster extends Thread {
	
	// Presets
	protected final String rawData = "[MOTD]__motd__[/MOTD][AD]__ad__[/AD]";
	
	// Data
	protected final DatagramSocket socket;
	protected final InetAddress address;
	protected final int port;
	
	// Management
	protected boolean shouldStop = false;
	
	// Data change-able by plugins
	protected String motd;
	
	public LocalServerBroadcaster(InetAddress address, int port) throws IOException {
		this.socket = new DatagramSocket();
		this.address = address;
		this.port = port;
		this.motd = ("Local DiamondCore Server - " + InetAddress.getLocalHost().getHostName());
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
				byte[] data = this.rawData.replace("__motd__", this.motd).replace("__ad__", ServerSettings.getPCPort() + "").getBytes();
				socket.send(new DatagramPacket(data, data.length, address, port));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
