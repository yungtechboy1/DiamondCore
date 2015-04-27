/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.file;

import java.io.File;

/**
 * This is a list of directories used for the software in order to
 * access files this is good for use in-case a directory is changed,
 * this way, you wont have to change it everywhere in the program
 * 
 * @author Trent Summerlin
 * @version 1.1
 */
public abstract class FileList {
	
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
	public static File readMe = new File(Base + "README.md"); // NOTE: To get the ReadMe file to copy, you must use the following: this.getClass().getResource("/src/main/resources/files/README.md");
	public static File license = new File(Base + "LICENSE");  // NOTE: To get the LICENSE file to copy, you must use the following: this.getClass().getResource("/src/main/resources/files/LICENSE");
	
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
		libFolder = new File(Base + "lib");
		loggerConfig = new File(libFolder + "/log4j2.xml");
	}
	
}
