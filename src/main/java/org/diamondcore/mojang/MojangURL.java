/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.mojang;

/**
 * Used by classes for ease of access to
 * links that are required for the function
 * to run properly
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class MojangURL {
	
	// Account
	public static final String accountInfo = "https://api.mojang.com/users/profiles/minecraft/";
	public static final String profileInfo = "https://sessionserver.mojang.com/session/minecraft/profile/";
	
	// Authentication
	//public static final String authServer = "https://sessionserver.mojang.com/";
	  public static final String authServer = "https://authserver.mojang.com/";
          public static final String hasJoined = "https://sessionserver.mojang.com/session/minecraft/hasJoined?";
}
