/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.file;

import java.io.File;

import org.diamondcore.lang.Lang;

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
	public static File startFileWin = new File(Base + Lang.get("file.startbat"));
	public static File startFileOsx = new File(Base + Lang.get("file.startcmd"));
	
	// Player related
	public static File whitelist = new File(Base + Lang.get("file.whitelistjson"));
	public static File operators = new File(Base + Lang.get("file.opsjson"));
	public static File bannedPlayerList = new File(Base + Lang.get("file.bannedplayersjson"));
	public static File bannedIPList = new File(Base + Lang.get("bannedipjson"));
	public static File playerFolder = new File(Base + Lang.get("folder.players"));
	
	// Server Related
	public static File serverProperties = new File(Base + Lang.get("file.serverproperties"));
	public static File serverFavicon = new File(Base + Lang.get("file.servericon"));
	public static File serverLog = new File(Base + Lang.get("file.serverlog"));
	public static File worldFolder = new File(Base + Lang.get("folder.worlds"));
	public static File pluginFolder = new File(Base + Lang.get("folder.plugins"));
	
	// Other (Are not modified by lang!)
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
		startFileWin = new File(Base + Lang.get("file.startbat"));
		startFileOsx = new File(Base + Lang.get("file.startcmd"));
		whitelist = new File(Base + Lang.get("file.whitelistjson"));
		operators = new File(Base + Lang.get("file.opsjson"));
		bannedPlayerList = new File(Base + Lang.get("file.bannedplayersjson"));
		bannedIPList = new File(Base + Lang.get("file.bannedipjson"));
		playerFolder = new File(Base + Lang.get("folder.players"));
		serverProperties = new File(Base + Lang.get("file.serverproperties"));
		serverFavicon = new File(Base + Lang.get("file.servericon"));
		serverLog = new File(Base + Lang.get("file.serverlog"));
		worldFolder = new File(Base + Lang.get("folder.worlds"));
		pluginFolder = new File(Base + Lang.get("folder.plugins"));
		
		// These are not modified by the lang
		readMe = new File(Base + "README.md");
		license = new File(Base + "LICENSE");
		logo = new File(Base + "logo.txt");
		libFolder = new File(Base + "lib");
		loggerConfig = new File(libFolder + "/log4j2.xml");
	}
	
}
