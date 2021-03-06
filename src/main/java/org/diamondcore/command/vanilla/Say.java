package org.diamondcore.command.vanilla;

import org.diamondcore.command.CommandExecutor;
import org.diamondcore.command.CommandSender;

public class Say implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, String label, String[] args) {
		if(args.length >= 1) {
			StringBuilder builder = new StringBuilder();
			
			for(int i = 0; i < args.length; i++) {
				builder.append(args[i]);
				if(i+1 < args.length)
					builder.append(" ");
			}
			
			sender.sendMessage(builder.toString());
			// TODO: Cycle through each player on the server and send it to them as well
			
			return true;
		}
		return false;
	}

}
