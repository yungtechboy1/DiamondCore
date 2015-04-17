package net.trenterprises.diamondcore.cross.command.vanilla;

import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

public class SayCommand extends Command {
	
	private final String[] args;
	
	public SayCommand(String[] args) {
		this.args = args;
	}
	
	@Override
	public String getName() {
		return "Say";
	}
	
	@Override
	public String getDescription() {
		return "used to broadcast a global message from the console";
	}

	@Override
	public String[] getDefaultParameters() {
		String[] params = new String[1];
		params[0] = "sentence";
		return params;
	}

	@Override
	public String[] getParameters() {
		return args;
	}

	@Override
	public void execute(CommandSender sender, DiamondLogger logger) {
		if(sender.equals(CommandSender.CONSOLE)) {
			StringBuilder builder = new StringBuilder();
			for(int i = 0; i < args.length; i++) {
				builder.append(args[i] + " ");
			}
			logger.info(builder.toString());
		} else if(sender.equals(CommandSender.POCKETPLAYER)) {
			// Change some code for MCPE
		} else if(sender.equals(CommandSender.DESKTOPPLAYER)) {
			// Change some code for MCPC
		}
	}
	
	
}
