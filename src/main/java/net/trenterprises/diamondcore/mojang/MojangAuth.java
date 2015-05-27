package net.trenterprises.diamondcore.mojang;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.trenterprises.diamondcore.cross.file.FileUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class MojangAuth {
	
	private MojangAuth() {}
	
	protected static JSONObject authData = null;
	
	/**
	 * Used to see if the authentication server is online
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server state
	 */
	public static boolean isOnline() {
		JSONObject data = getAuthData(true);
		if(data != null) return data.get("Status").toString().equalsIgnoreCase("OK");
		else return false;
	}
	
	/**
	 * Used to get the production mode of the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server production mode
	 */
	public static String getRuntimeMode() {
		if(isOnline()) return getAuthData().get("Runtime-Mode").toString();
		else return null;
	}
	
	/**
	 * Used to get the application owner of the server
	 * 
	 * @author Trent Summerlin
	 * @version Mojang
	 * @return Auth-Server application owner
	 */
	public static String getApplicationAuthor() {
		if(isOnline()) return getAuthData().get("Application-Author").toString();
		else return null;
	}
	
	/**
	 * Used to get the application description of the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server application description
	 */
	public static String getApplicationDescription() {
		if(isOnline()) return getAuthData().get("Application-Description").toString();
		else return null;
	}
	
	/**
	 * Used to get the specification version of the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server specification version
	 */
	public static String getSpecificationVersion() {
		if(isOnline()) return getAuthData().get("Specification-Version").toString();
		else return null;
	}
	
	/**
	 * Used to get the application name of the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server application name
	 */
	public static String getApplicationName() {
		if(isOnline()) return getAuthData().get("Application-Name").toString();
		else return null;
	}
	
	/**
	 * Used to get the implementation version of the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server implementation version
	 */
	public static String getImplementationVersion() {
		if(isOnline()) return getAuthData().get("Implementation-Version").toString();
		else return null;
	}
	
	/**
	 * Used to get the application owner of the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @return Auth-Server application owner
	 */
	public static String getApplicationOwner() {
		if(isOnline()) return getAuthData().get("Application-Owner").toString();
		else return null;
	}
	
	protected static JSONObject getAuthData() {
		return getAuthData(false);
	}
	
	/**
	 * Used to get the auth data from the server
	 * 
	 * @author Trent Summerlin
	 * @author Mojang
	 * @param refresh
	 * @return Auth-Server json data
	 */
	protected static JSONObject getAuthData(boolean refresh) {
		try {
			if(!refresh) {
				if(authData == null) {
					String data = FileUtils.readFromURL(new URL("https://sessionserver.mojang.com/"));
					authData = (JSONObject) new JSONParser().parse(data);
					return authData;
				} else {
					return authData;
				}
			}
			else {
				String data = FileUtils.readFromURL(new URL("https://sessionserver.mojang.com/"));
				authData = (JSONObject) new JSONParser().parse(data);
				return authData;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
