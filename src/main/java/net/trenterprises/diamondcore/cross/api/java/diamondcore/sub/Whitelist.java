/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.diamondcore.sub;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import net.trenterprises.diamondcore.cross.file.FileList;
import net.trenterprises.diamondcore.cross.file.FileUtils;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class is used to get and set info from the Whitelist
 * <br>
 * For example, I can remove a player from the whitelist, and add
 * <br>
 * a player from the whitelist if I need to.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class Whitelist {
	
	/**
	 * Used to see if the whitelist is enabled or not
	 * 
	 * @return Whitelist activation state
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final boolean activated() {
		return ServerSettings.getEnableWhitelist(); 
	}
	
	/**
	 * Used to get the server whitelist list.
	 * <br>
	 * NOTE: If the whitelist is disabled, it will return null.
	 * 
	 * @return Server whitelist
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final ArrayList<String> getWhitelistedPlayers() {
		if(activated()) {
			try {
				ArrayList<String> whitelist = new ArrayList<String>();
				JSONObject whitelistObject = (JSONObject) new JSONParser().parse(new FileReader(FileList.whitelist));
				for(Iterator<?> iterator = whitelistObject.keySet().iterator(); iterator.hasNext();) whitelist.add(iterator.next().toString());
				return whitelist;
			} catch (ParseException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Used to add a player to the whitelist
	 * 
	 * @param Player name
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public final void addPlayer(String player) {
		try {
			JSONObject whitelistObject = getWhitelist();
			whitelistObject.put(player, true);
			String whitelistString = whitelistObject.toJSONString();
			FileUtils.writeToFile(FileList.whitelist, whitelistString);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to remove a player from the whitelist
	 * 
	 * @param Player name
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public final void removePlayer(String player) {
		try {
			JSONObject whitelistObject = getWhitelist();
			whitelistObject.remove(player);
			String whitelistString = whitelistObject.toJSONString();
			FileUtils.writeToFile(FileList.whitelist, whitelistString);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used to get the whitelist in a JSONObject
	 * 
	 * @return Whitelist as a JSONObject
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	protected final JSONObject getWhitelist() {
		try {
			JSONObject whitelist = (JSONObject) new JSONParser().parse(new FileReader(FileList.whitelist));
			return whitelist;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
