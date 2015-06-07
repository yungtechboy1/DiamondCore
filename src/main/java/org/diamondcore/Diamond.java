/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.diamondcore.exception.DiamondException;
import org.diamondcore.logging.DiamondLogger;
import org.diamondcore.logging.Log4j2Logger;

/**
 * Used to get server info like online players and
 * other info like the whitelist
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class Diamond {
	
	private Diamond() {}

	// Minecraft Desktop
	public static final String desktopVersionTag = "DiamondCore Unstable 1.8";
	public static final String desktopVersion = "1.8";
	public static final int desktopProtocol = 47;
	
	// Minecraft: Pocket Edition
	public static final String pocketVersionTag = "DiamondCore Unstable";
	public static final String pocketVersion = "0.11.0";
	public static final int pocketProtocol = 5;
	
	// DiamondCore
	static Server server = null;
	public static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	/**
	 * Used to set the Server object for the Diamond class
	 * <br>
	 * NOTE: Once it has been set, it can not be set again
	 * 
	 * @author Trent Summerlin
	 * @throws DiamondException 
	 */
	public static void setServer(Server newServer) throws DiamondException {
		if(server != null)
			throw new DiamondException("The server object has already been set!");
		else
			server = newServer;
	}
	
	/**
	 * Used to retrieve the server socket
	 * 
	 * @return Server object
	 * @author Trent Summerlin
	 */
	public static Server getServer() {
		return server;
	}
	
	/**
	 * Used to load a server icon from a Java File object
	 * 
	 * @return Server base64 Icon
	 * @author Trent Summerlin
	 */
	public static final String loadServerIcon(File icon) {
		try {
			return ("data:image/png;base64," + Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(icon)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
