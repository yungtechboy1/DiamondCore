package net.trenterprises.diamondcore.cross.command.custom;

import net.trenterprises.diamondcore.cross.command.CommandSender;

public interface CommandExecutor {
	
	public boolean onCommand(CommandSender sender, String commandLabel, String[] args);
	
}
