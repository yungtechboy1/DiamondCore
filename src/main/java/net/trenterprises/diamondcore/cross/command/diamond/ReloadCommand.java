package net.trenterprises.diamondcore.cross.command.diamond;

import java.io.IOException;

import net.trenterprises.diamondcore.cross.api.html.HTMLLoader;
import net.trenterprises.diamondcore.cross.api.java.JavaLoader;
import net.trenterprises.diamondcore.cross.api.xml.XMLLoader;
import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.command.custom.CustomCommand;
import net.trenterprises.diamondcore.cross.file.PropertiesCheckup;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;

public class ReloadCommand extends Command {

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getDescription() {
		return "used to reload plugins quickly and easily, useful for plugin devs";
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
		logger.info("Reloading...");
		
		try {
			// Unload plugins
			JavaLoader.unloadPlugins();
			XMLLoader.unloadPlugins();
			HTMLLoader.unloadPlugins();
			
			// Clear custom commands
			CustomCommand.commands.clear();
			
			// Do a properties checkup
			new PropertiesCheckup();
						
			// Reload server settings
			ServerSettings.load();
						
			// Reload plugins
			JavaLoader.loadPlugins();
			XMLLoader.loadPlugins();
			HTMLLoader.loadPlugins();
			
			logger.info("Reload complete!");
		}
		catch(IOException e) {
			logger.warn("There was a error reloading the server! Error: " + e.getMessage());
		}
	}
	
}
