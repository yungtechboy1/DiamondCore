/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|
                                                                                                    
*/

package org.diamondcore.command;

/**
 * Extended by other classes in plugins in order
 * to let the compiler know that it would like to
 * handle commands received from the console
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract interface CommandExecutor {
	
	/**
	 * Function used to let the compiler know that it
	 * would like to handle a command received
	 * 
	 * @param sender
	 * 		- The type of sender
	 * @param label
	 * 		- The command label
	 * @param args
	 * 		- The command arguments
	 * @return Command success
	 * @author Trent Summerlin
	 */
	public abstract boolean onCommand(CommandSender sender, String label, String[] args);
	
}
