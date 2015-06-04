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
 * Thrown whenever a plugin with the same class
 * as another is loaded, this can cause errors
 * as the plugin could be a duplicate of another
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class PluginDuplicateException extends PluginException {
	
	private static final long serialVersionUID = -6424744149692732415L;
	
	public PluginDuplicateException(String mainClass) {
		super("The main class " + mainClass + " has been taken by another plugin!");
	}

}
