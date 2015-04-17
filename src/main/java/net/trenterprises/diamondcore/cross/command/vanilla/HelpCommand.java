/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.command.vanilla;

import net.trenterprises.diamondcore.cross.api.chat.ChatColor;
import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.command.custom.CustomCommand;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

public class HelpCommand extends Command {
	
	private final String newLine = (ChatColor.YELLOW + "\n----------\n");
	
	@Override
	public String getName() {
		return "Help";
	}

	@Override
	public String getDescription() {
		return "used to list all of the commands available on the server";
	}

	@Override
	public String[] getDefaultParameters() {
		return null;
	}

	@Override
	public String[] getParameters() {
		return null;
	}

	@Override
	public void execute(CommandSender sender, DiamondLogger logger) {
		Command[] commands = Command.commandList;
		StringBuilder builder = new StringBuilder();
		builder.append(ChatColor.RED + "Commands: ");
		builder.append(newLine);
		for(int i = 0; i < commands.length; i++){
			builder.append(ChatColor.DARK_GREEN + commands[i].getName() + ": " + commands[i].getDescription() + "\n");
		}
		for(int k = 0; k < CustomCommand.commands.size(); k++) {
			CustomCommand command = CustomCommand.commands.get(k);
			builder.append(ChatColor.DARK_GREEN + command.getName() + ": " + command.getDescription());
			if(k+1 != CustomCommand.commands.size()) builder.append("\n");
		}
		builder.append(newLine);
		builder.append(ChatColor.RED + "End of commands");
		String finalResult = builder.toString();
		if(sender.equals(CommandSender.CONSOLE)) finalResult = ChatColor.stripColors(builder.toString());
		logger.info(finalResult);
	}

}
