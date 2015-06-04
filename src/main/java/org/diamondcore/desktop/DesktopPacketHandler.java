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

import org.diamondcore.utils.ServerSettings;

/**
 * Used to listen for connections and respond to them
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class DesktopPacketHandler extends Thread {
	
	protected final ServerSocket listener;
	
	public DesktopPacketHandler() throws IOException {
		this.listener = new ServerSocket(ServerSettings.getPCPort());
		
	}
	
	public void run() {
		while(true) {
			try {
				// Initialize
				Socket s = listener.accept();
				DataInputStream input = new DataInputStream(s.getInputStream());
				DataOutputStream output = new DataOutputStream(s.getOutputStream());
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
