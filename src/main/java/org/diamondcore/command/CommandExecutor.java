/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
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
public interface CommandExecutor {
	
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
	public boolean onCommand(CommandSender sender, String label, String[] args);
	
}
