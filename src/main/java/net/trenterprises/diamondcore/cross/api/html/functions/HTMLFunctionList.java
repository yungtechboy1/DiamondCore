/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.html.functions;

/**
 * This class is used to get the names of all of
 * <br>
 * the functions for the HTML plugins in DiamondCore
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class HTMLFunctionList {
	
	// Block methods
	/**
	 * This method is ran whenever the plugin is enabled by it's session manager
	 */
	public static final String onEnable = "onenable";
	
	/**
	 * This method is ran whenever the plugin is disable by it's session manager
	 */
	public static final String onDisable = "ondisable";
	
	/**
	 * This method is ran whenever a valid command is ran by the console/client
	 */
	public static final String onCommand = "oncommand";
	
	/**
	 * This method is ran whenever a event is thrown by the server
	 */
	public static final String event = "event";
	
	// Internal methods
	/**
	 * This method is used to get the server logger
	 */
	public static final String getLogger = "logger";
}
