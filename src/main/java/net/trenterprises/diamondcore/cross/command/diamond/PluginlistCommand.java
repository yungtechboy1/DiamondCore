package net.trenterprises.diamondcore.cross.command.diamond;

import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.PluginSession;
import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

public class PluginlistCommand extends Command {
	
	@Override
	public String getName() {
		return "Plugins";
	}
	
	@Override
	public String getDescription() {
		return "used to get the list of plugins being ran on the server at the moment";
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
		StringBuilder pluginBuilder = new StringBuilder();
		ArrayList<PluginSession> plugins = PluginSession.sessionList;
		pluginBuilder.append("Plugins(" + plugins.size() + "): ");
		for(int i = 0; i < plugins.size(); i++) {
			pluginBuilder.append(plugins.get(i).getPluginName());
			if(i+1 != plugins.size()) pluginBuilder.append(", ");
		}
		logger.info(pluginBuilder.toString());
	}

}
