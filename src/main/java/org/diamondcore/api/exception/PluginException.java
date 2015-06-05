/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.api.exception;

/**
 * Thrown whenever there is a plugin error
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class PluginException extends Exception {
	
	private static final long serialVersionUID = -6057450613825764343L;
	
	public PluginException() {
		super("There was a error in running/loading a plugin!");
		return;
	}

	public PluginException(String error) {
		super("There was a error in running/loading a plugin!\nCause: " + error);
		return;
	}
	
}
