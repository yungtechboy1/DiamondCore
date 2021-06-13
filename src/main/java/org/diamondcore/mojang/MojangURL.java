/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
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
