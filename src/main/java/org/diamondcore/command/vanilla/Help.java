package org.diamondcore.command.vanilla;

import java.util.HashMap;
import java.util.Iterator;

import org.diamondcore.command.Command;
import org.diamondcore.command.CommandExecutor;
import org.diamondcore.command.CommandSender;
import org.diamondcore.command.Console;

public class Help implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		StringBuilder builder = new StringBuilder();
		
		builder.append((sender instanceof Console ? "\n" : "") + "----------\n");
		HashMap<String, Command> commands = Command.getCommands();
		Iterator<String> ic = commands.keySet().iterator();
		while(ic.hasNext()) {
			String key = ic.next();
			String description = commands.get(key).getDescription();
			builder.append(key + ": " + description + "\n");
		}
		
		sender.sendMessage(builder.toString());
		
		return true;
	}
	
}
