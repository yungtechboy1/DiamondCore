/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.command;

import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

/**
 * This class is extend off of by native commands
 * <br>
 * which are included in DiamondCore
 * 
 * @author Trent Summer
 * @version 1.0
 */
public abstract class NativeCommand {
	
	/**
	 * This is used to get the name of the
	 * <br>
	 * command if needed durin execution
	 * 
	 * @return Command name
	 */
	public abstract String getName();
	
	/**
	 * This i used to get the description
	 * <br>
	 * of the plugin and/or it's use
	 * 
	 * @return Plugin description
	 */
	public abstract String getDescription();
	
	/**
	 * This is used to get the default parameters
	 * <br>
	 * of the plugin, although not all of them are
	 * <br>
	 * required
	 * 
	 * @return Default parameters for the command
	 */
	public abstract String[] getDefaultParameters();
	
	/**
	 * Used to get the current parameters of
	 * <br>
	 * the command
	 * 
	 * @return Current command parameters
	 */
	public abstract String[] getParameters();
	
	/**
	 * Used to execute the command itself, it will
	 * <br>
	 * return a string response when doing so, causing
	 * <br>
	 * the logger to return the result of the method that
	 * <br>
	 * was ran
	 * 
	 * @param sender
	 * @return Command execution result
	 */
	public abstract void execute(CommandSender sender, DiamondLogger logger);
	
}
