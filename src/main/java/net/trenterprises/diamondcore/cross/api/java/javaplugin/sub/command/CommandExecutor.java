/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.command;

import net.trenterprises.diamondcore.cross.command.CommandSender;

/**
 * Extended by other classes in plugins in order
 * <br>
 * to let the compiler know that it would like to
 * <br>
 * handle commands received from the console
 * 
 * @author Trent Summerlin
 * @version 1.0
 *
 */
public abstract interface CommandExecutor {
	
	/**
	 * Function used to let the compiler know that it
	 * <br>
	 * would like to handle a command received
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param sender
	 * @param cmd
	 * @param label
	 * @param args
	 * @return Command success
	 */
	public abstract boolean onCommand(CommandSender sender, String label, String[] args);
	
}
