package net.trenterprises.diamondcore.cross.command.diamond;

import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.java.JavaSession;
import net.trenterprises.diamondcore.cross.api.xml.XMLSession;
import net.trenterprises.diamondcore.cross.command.CommandHandler;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.command.NativeCommand;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

public class PluginlistCommand extends NativeCommand {
	
	public PluginlistCommand(CommandHandler handler) {}
	public PluginlistCommand() {}
	
	@Override
	public String getName() {
		return "plugins";
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
		ArrayList<JavaSession> plugins = JavaSession.sessionList;
		ArrayList<XMLSession> html = XMLSession.sessionList;
		pluginBuilder.append("Plugins(" + (plugins.size()+html.size()) + "): ");
		// Java plugins
		for(int i = 0; i < plugins.size(); i++) {
			pluginBuilder.append(plugins.get(i).getPluginName());
			if(html.size() > 0) pluginBuilder.append(", ");
			else {
				if(i+1 != plugins.size()) pluginBuilder.append(", ");
			}
		}
		// HTML Plugins
		for(int k = 0; k < html.size(); k++) {
			pluginBuilder.append(html.get(k).getPluginName());
			if(k+1 != plugins.size()) pluginBuilder.append(", ");
		}
		logger.info(pluginBuilder.toString());
	}

}
