/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.javaplugin.sub;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.api.java.JavaPlugin;
import net.trenterprises.diamondcore.cross.api.java.diamondcore.sub.Whitelist;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.command.Command;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server.PluginManager;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

/**
 * This class is used to do things like get server info, get its logger
 * <br>
 * and get its plugin manager
 * 
 * @author Trent SUmmelrin
 * @version 0.1.0-SNAPSHOT
 */
public class Server {
	
	public Server(JavaPlugin plugin) {}
	
	/**
	 * Used to retrieve the plugin manager
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The server plugin manager
	 */
	public PluginManager getPluginManager() {
		return new PluginManager(this);
	}
	
	/**
	 * Used to get a command by it's name.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param command
	 * @return Command object
	 */
	public Command getCommand(String commandLabel) {
		for(Command command : Command.commands) {
			if(command.getName().equalsIgnoreCase(commandLabel)) {
				return command;
			}
		}
		return null;
	}
	
	/**
	 * Used to retrieve the server logger
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The server logger
	 */
	public Log4j2Logger getLogger() {
		return new Log4j2Logger("DiamondCore");
	}
	
	/*
	 * The following code down below belongs to the Diamond Class
	 * Normally, the class "Diamond" would just be extended. But.
	 * Eclipse will throw a pesky warning at the user telling it should
	 * be accessed in a static way. So the info will be gotten like this. 
	 */
	
	/**
	 * This method is used to retrieve the server whitelist as a object with functions.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Server whitelist
	 */
	public Whitelist getWhitelist() {
		return Diamond.getWhitelist();
	}
	
}