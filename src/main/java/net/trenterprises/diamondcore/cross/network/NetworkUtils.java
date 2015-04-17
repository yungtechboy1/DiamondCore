/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.network;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * This class is used for things that might take some code
 * in order to be done put down to one line, like getting the
 * smallest-largest MTU that the device can handle. 
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public abstract class NetworkUtils {
	
	/** Get the smallest MTU for the current device (Server Host)
	 * 
	 * @return The smallest MTU on the device possible
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws SocketException
	 */
	public static int getSmallestMTU() throws SocketException {
		
		// Get MTU of Every Network device
		Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();      
		ArrayList<Integer> MTUList = new ArrayList<Integer>();
		for (NetworkInterface netIf : Collections.list(nets)) {
			MTUList.add(netIf.getMTU());
		}
		
		// Get largest MTU
		int largest = MTUList.get(0);
		for(int i = 0; i < MTUList.size(); i++) {
			if(MTUList.get(i) > 0) {
				if (MTUList.get(i) > largest) {
					largest = MTUList.get(i);
				}
			}
		}
		
		// Now get the smallest MTU
		int smallest = largest;
		for(int i = 0; i < MTUList.size(); i++) {
			if(MTUList.get(i) > 0) {
				if (MTUList.get(i) < smallest) {
					smallest = MTUList.get(i);
				}
			}
		}
		
		return smallest;
	}
	
	/**
	 * Used to read a text file from a HTTP server
	 * 
	 * @param URL to the file to read from
	 * @return The text file from the HTTP server
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws IOException
	 */
	public static String readFromURL(URL Url) throws IOException {
		Scanner URLScanner = new Scanner(Url.openStream());
		StringBuilder Builder = new StringBuilder();
		while(URLScanner.hasNextLine()) {
			Builder.append(URLScanner.nextLine() + "\n");
		}
		URLScanner.close();
		return Builder.toString();
	}
	
}
