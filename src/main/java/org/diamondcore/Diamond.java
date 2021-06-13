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

import org.diamondcore.desktop.Favicon;
import org.diamondcore.exception.DiamondException;
import org.diamondcore.lang.Lang;
import org.diamondcore.lang.exeption.LangException;
import org.diamondcore.logging.DiamondLogger;
import org.diamondcore.logging.DiamondTranslator;
import org.diamondcore.logging.Log4j2Logger;
import org.diamondcore.logging.Log4j2Translator;

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
	public static final String desktopVersion = "1.17";
	public static final int desktopProtocol = 755;
	
	// Minecraft: Pocket Edition
	public static final String pocketVersion = "1.17.0.2";
	public static final int pocketProtocol = 440;
	
	// DiamondCore
	private static Server server = null;
	public static final String versionTag = "DiamondCore";
	public static final DiamondLogger logger = new Log4j2Logger("DiamondCore");
	public static final DiamondTranslator lang = new Log4j2Translator("DiamondCore");
	
	/**
	 * Used to set the Server object for the Diamond class
	 * <br>
	 * NOTE: Once it has been set, it can not be set again
	 * 
	 * @author Trent Summerlin
	 * @throws DiamondException 
	 * @throws LangException 
	 */
	public static void setServer(Server newServer) throws DiamondException, LangException {
		if(server != null)
			throw new DiamondException(Lang.get("diamond.exception.serverSet"));
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
	public static final Favicon loadServerIcon(File icon) {
		try {
			return Favicon.getInstance(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
