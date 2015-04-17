/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import net.trenterprises.diamondcore.cross.api.diamondcore.sub.Whitelist;
import net.trenterprises.diamondcore.cross.api.diamondcore.sub.World;
import net.trenterprises.diamondcore.cross.file.FileList;

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
	
	public static final int PocketProtocol = 5;
	public static final int DesktopProtocol = -1;
	
	/**
	 * Used to get the online players
	 * 
	 * @return The online players
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static final ArrayList<String> getOnlinePlayers() {
		return null;
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
	
}
