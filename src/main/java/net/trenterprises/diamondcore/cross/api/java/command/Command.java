/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api.java.command;

import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.java.JavaSession;
import net.trenterprises.diamondcore.cross.api.java.command.exception.ExistentCommandException;
import net.trenterprises.diamondcore.cross.api.java.command.exception.InvalidCommandException;

/**
 * This class is used to create a store custom commands
 * which are created by plugins so they can be executed
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 * @throws InvalidCommandException
 */
public final class Command {
	
	public static ArrayList<Command> commands = new ArrayList<Command>();
	
	// Final values, do not change
	private final String commandName;
	private final String commandUsage;
	private final String commandDescription;
	private final JavaSession pluginSession;
	
	// Subject to change
	private CommandExecutor executor = null;
	
	public Command(JavaSession session, String commandName, String commandUsage, String commandDescription) throws InvalidCommandException {
		this.pluginSession = session;
		this.commandName = commandName;
		this.commandUsage = commandUsage;
		this.commandDescription = commandDescription;
		
		// Check to make sure the command is valid
		for(int i = 0; i < commands.size(); i++) {
			Command command = commands.get(i);
			if(command.getName().equalsIgnoreCase(this.getName())) throw new ExistentCommandException(this.getName());
		}
		commands.add(this);
	}
	
	/**
	 * Used to set the executor for the command
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}
	
	/**
	 * Use
	 */
	
	/**
	 * Used to get the name of the command which
	 * <br>
	 * plugin users would use to run the command itself
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Command name
	 */
	public String getName() {
		return this.commandName;
	}
	
	/**
	 * Used to get the description of the command
	 * <br>
	 * which is used in the "help" command
	 * <br>
	 * Note: if it is null, the description will be "UNKNOWN-DESCRIPTION"
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Command description
	 */
	public String getDescription() {
		return this.commandDescription;
	}
	
	/**
	 * Used to get the usage of the command
	 * <br>
	 * which is used in the "help" command
	 * <br>
	 * Note: if it is null, the description will be "/<command>"
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Command usage
	 */
	public String getUsage() {
		return this.commandUsage;
	}
	
	/**
	 * Used to get the command executor
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public CommandExecutor getExecutor() {
		return this.executor;
	}
	
	/**
	 * Used to get the plugin session that the command
	 * <br>
	 * is stored in. If null is returned, try using getHTMLSession()
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Command plugin session
	 */
	public JavaSession getPluginSession() {
		return this.pluginSession;
	}
}
