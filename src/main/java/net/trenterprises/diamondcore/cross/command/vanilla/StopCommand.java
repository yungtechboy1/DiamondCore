package net.trenterprises.diamondcore.cross.command.vanilla;

import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.PluginSession;
import net.trenterprises.diamondcore.cross.api.html.HTMLSession;
import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

public class StopCommand extends Command {

	@Override
	public String getName() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "used to stop the server";
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
		logger.info("Stopping the server...");
		
		// Unload Java plugins
		ArrayList<PluginSession> pluginSession = PluginSession.sessionList;
		for(int i = 0; i < pluginSession.size(); i++) {
			try {
				pluginSession.get(i).unloadSession();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		// Unload HTML plugins
		ArrayList<HTMLSession> htmlSession = HTMLSession.sessionList;
		for(int k = 0; k < htmlSession.size(); k++) htmlSession.get(k).unloadSession();
		
		// Save world
		
		logger.info("Server stopped!");
		
		// Exit program
		System.exit(0);
	}

}
