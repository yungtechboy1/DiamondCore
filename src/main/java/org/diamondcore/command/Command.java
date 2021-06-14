/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.command;

import java.util.HashMap;

import org.diamondcore.command.diamond.PluginList;
import org.diamondcore.command.vanilla.Help;
import org.diamondcore.command.vanilla.Say;
import org.diamondcore.command.vanilla.Time;
import org.diamondcore.entity.player.Player;

/**
 * This class is used to represent a command.
 * <br><br>
 * This model is better than the last, as it
 * replaces the old model where the executor
 * would have to cycle through every possible
 * command type possible, and if it made a match
 * it would first have to make sure a plugin didn't
 * override it, this makes it so a plugin can easily
 * override a native command such as "ban" or "plugins"
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class Command {
	
	private static HashMap<String, Command> commands = new HashMap<String, Command>();
	
	private CommandExecutor executor;
	private final String label;
	private final String usage;
	private final String description;
	
	public Command(CommandExecutor executor, String label, String usage, String description) {
		this.executor = executor;
		this.label = label;
		this.usage = usage;
		this.description = description;
	}
	
	public Command(CommandExecutor executor, String label, String description) {
		this(executor, label, null, description);
	}
	
	/**
	 * Used to get a command object by it's label
	 * 
	 * @param label
	 * 		- The command's label
	 * @return The command object
	 * @author Trent Summerlin
	 */
	public static Command getByName(String label) {
		return commands.get(label);
	}
	
	/**
	 * Used to add a command to the command list
	 * 
	 * @param c
	 * 		- The command to be added
	 * @author Trent Summerlin
	 */
	public static void addCommand(Command c) {
		commands.put(c.getLabel(), c);
	}
	
	/**
	 * Used to get all the commands including both
	 * server and plugin commands
	 * 
	 * @return All command's registered
	 */
	public static HashMap<String, Command> getCommands() {
		return commands;
	}
	
	/**
	 * Used to register the native commands.
	 * <br><br>
	 * Once they have been registered, this
	 * method will not run again.
	 * 
	 * @author Trent Summerlin
	 */
	public static void registerNatives() {
		if(commands.isEmpty()) {
			// Create command objects
			Command say = new Command(new Say(), "say", "/say <message>", "Used to broadcast a message to the console and server");
			Command help = new Command(new Help(), "help", "/help", "Used to display the registered commands");
			Command time = new Command(new Time(), "time", "/time <add|set> <value>", "Used to manage world time on the server");
			Command plugins = new Command(new PluginList(), "/plugins", "Used to display the active plugins");
			
			// Add them to the hashmap
			commands.put("say", say);
			commands.put("help", help);
			commands.put("time", time);
			commands.put("plugins", plugins);
                        commands.put("pl", plugins);
		}
	}
	
	/**
	 * Used to set the executor for when a command
	 * is ran through the console or by a player
	 * 
	 * @param executor
	 * 		- The executor to handle the command
	 * @author Trent Summerlin
	 */
	public void setExecutor(CommandExecutor executor) {
		this.executor = executor;
	}
	
	/**
	 * Used to run the command
	 * 
	 * @param sender
	 * 		- The command sender (The one who requested to run the command)
	 * @param args
	 * 		- The command arguments
	 * @author Trent Summerlin
	 */
	public void execute(CommandSender sender, String[] args) {
		boolean success = executor.onCommand(sender, label, args);
		if(!success) {
			if(sender instanceof Console)
				((Console) sender).sendMessage(this.getUsage());
			else if(sender instanceof Player)
				((Player) sender).sendMessage(this.getUsage());
		}
	}
	
	/**
	 * Used to get the command label
	 * 
	 * @return The command label
	 * @author Trent Summerlin
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Used to get the command usage
	 * 
	 * @return The command usage
	 * @author Trent Summerlin
	 */
	public String getUsage() {
		return this.usage;
	}
	
	/**
	 * Used to get the description of the command
	 * 
	 * @return The command description
	 * @author Trent Summerlin
	 */
	public String getDescription() {
		return this.description;
	}
	
}
