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

import org.diamondcore.ChatColor;

/**
 * This class is used to broadcast the server over the local network
 * for Desktop players that happen to be on the same network. This helps
 * increase convenience for people that just want to "plug & play"
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class TCPBroadcaster extends Thread {
	
	// Data
	private final DatagramSocket socket;
	private final InetAddress address;
	private final int port;
	
	// Data change-able by plugins
	protected String motd;
	private String ad;
	
	public TCPBroadcaster(InetAddress address, int port) throws IOException {
		this.socket = new DatagramSocket(null);
		this.socket.setReuseAddress(true);
		this.address = address;
		this.port = port;
		this.ad = (""+port+"");
		this.motd = ("Local DiamondCore World (" + InetAddress.getLocalHost().getHostName() + ")");
	}
	
	/**
	 * Used to get the MOTD shown to the
	 * player on the same network as the
	 * TCP broadcaster
	 * 
	 * @author Trent Summerlin
	 */
	public final String getMOTD(String motd) {
		return this.motd;
	}
	
	/**
	 * Used to get the advertisement that
	 * is shown beside the address of the
	 * server shown to the player on the
	 * same network as the TCP broadcaster
	 * 
	 * @author Trent Summerlin
	 */
	public final String getAD(String ad) {
		return this.ad;
	}
	
	/**
	 * Used to get the InetAddress that the
	 * TCP broadcaster is running on
	 * 
	 * @author Trent Summerlin
	 */
	public final InetAddress getAddress() {
		return this.address;
	}
	
	/**
	 * Used to get the port that the
	 * TCP broadcaster is running on
	 * 
	 * @author Trent Summerlin
	 */
	public final int getPort() {
		return this.port;
	}
	
	/**
	 * Used to set the MOTD shown to the
	 * player on the same network as the
	 * TCP broadcaster
	 * 
	 * @param motd
	 * 		- The new MOTD to be used
	 * @author Trent Summerlin
	 */
	public final void setMOTD(String motd) {
		this.motd = ChatColor.stripColors(motd);
	}
	
	/**
	 * Used to set the advertisement that
	 * is shown beside the address of the
	 * server shown to the player on the
	 * same network as the TCP broadcaster
	 * 
	 * @param ad
	 * 		- The new advertisement
	 * @author Trent Summerlin
	 */
	public final void setAD(String ad) {
		this.ad = ChatColor.stripColors(ad);
	}
	
	public void run() {
		while(true) {
			try {
				byte[] data = ("[MOTD]" + motd + "[/MOTD][AD]" + ad + "[/AD]").getBytes();
				socket.send(new DatagramPacket(data, data.length, address, port));
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
