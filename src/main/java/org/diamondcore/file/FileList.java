/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.file;

import java.io.File;

/**
 * Used to store the files for the software
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class FileList {
	
	private FileList() {}
	
	protected static String Base = "./";
	static boolean debug = false;
	
	public static void setDebug(boolean mode) {
		debug = mode;
		refreshDirs();
	}
	
	// Start
	public static File startFileWin = new File(Base + "start.bat");
	public static File startFileOsx = new File(Base + "start.cmd");
	
	// Player related
	public static File whitelist = new File(Base + "whitelist.json");
	public static File operators = new File(Base + "ops.json");
	public static File bannedPlayerList = new File(Base + "banned-players.json");
	public static File bannedIPList = new File(Base + "banned-ip.json");
	public static File playerFolder = new File(Base + "players");
	
	// Server Related
	public static File serverProperties = new File(Base + "server.properties");
	public static File serverFavicon = new File(Base + "server-icon.png");
	public static File serverLog = new File(Base + "server.log");
	public static File worldFolder = new File(Base + "worlds");
	public static File pluginFolder = new File(Base + "plugins");
	
	// Other
	public static File libFolder = new File(Base + "lib");
	public static File loggerConfig = new File("./lib/log4j2.xml");
	public static File readMe = new File(Base + "README.md");
	public static File license = new File(Base + "LICENSE");
	public static File logo = new File(Base + "logo.txt");
	
	public static void refreshDirs() {
		if(debug) {
			Base = "./debug/";
			new File(Base).mkdir();
		}
		startFileWin = new File(Base + "start.bat");
		startFileOsx = new File(Base + "start.cmd");
		whitelist = new File(Base + "whitelist.json");
		operators = new File(Base + "ops.json");
		bannedPlayerList = new File(Base + "banned-players.json");
		bannedIPList = new File(Base + "banned-ip.json");
		playerFolder = new File(Base + "players");
		serverProperties = new File(Base + "server.properties");
		serverFavicon = new File(Base + "server-icon.png");
		serverLog = new File(Base + "server.log");
		worldFolder = new File(Base + "worlds");
		pluginFolder = new File(Base + "plugins");
		readMe = new File(Base + "README.md");
		license = new File(Base + "LICENSE");
		logo = new File(Base + "logo.txt");
		libFolder = new File(Base + "lib");
		loggerConfig = new File(libFolder + "/log4j2.xml");
	}
	
}
