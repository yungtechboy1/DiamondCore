/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

import net.trenterprises.diamondcore.cross.api.java.diamondcore.sub.Whitelist;
import net.trenterprises.diamondcore.cross.api.java.diamondcore.sub.World;
import net.trenterprises.diamondcore.cross.file.FileList;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Used to get server info like online players and
 * <br>
 * other info like the whitelist
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class Diamond {
	
	// Minecraft Desktop
	public static final String desktopVersionTag = "DiamondCore Unstable 1.8";
	public static final String desktopVersion = "1.8";
	public static final int desktopProtocol = 47;
	
	// Minecraft: Pocket Edition
	public static final String pocketVersionTag = "DiamondCore Unstable";
	public static final String pocketVersion = "0.11.0";
	public static final int pocketProtocol = 5;
	
	// Global
	protected static ArrayList<String> players = new ArrayList<String>();
	
	private Diamond() {}
	
	/**
	 * Used to get the online players
	 * 
	 * @return The online players
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static final ArrayList<String> getOnlinePlayers() {
		return players;
	}
	
	/**
	 * Used to get the banned players
	 * 
	 * @return The banned players
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static final ArrayList<String> getBannedPlayers() {
		try {
			ArrayList<String> banList = new ArrayList<String>();
			JSONObject banObject = (JSONObject) new JSONParser().parse(new FileReader(FileList.bannedPlayerList));
			for(Iterator<?> iterator = banObject.keySet().iterator(); iterator.hasNext();) banList.add(iterator.next().toString());
			return banList;
		} catch (ParseException e) {
			return null;
		} catch(IOException E) {
			return null;
		}
	}
	
	/**
	 * Used to retrieve the whitelist as a object
	 * 
	 * @return The server whitelist
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static final Whitelist getWhitelist() {
		return new Whitelist();
	}
	
	/**
	 * Used to retrieve the world as a object
	 * 
	 * @return The server world
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static final World getWorld() {
		return new World();
	}
	
	/**
	 * Used to load a server icon from a Java File object
	 * 
	 * @return Server base64 Icon
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static final String loadServerIcon(File icon) {
		try {
			return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(icon));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
