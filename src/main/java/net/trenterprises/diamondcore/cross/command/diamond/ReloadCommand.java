package net.trenterprises.diamondcore.cross.command.diamond;

import java.io.IOException;

import net.trenterprises.diamondcore.cross.api.PluginLoader;
import net.trenterprises.diamondcore.cross.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.command.custom.CustomCommand;
import net.trenterprises.diamondcore.cross.file.PropertiesCheckup;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;

public class ReloadCommand extends Command {

	@Override
	public String getName() {
		return "Reload";
	}

	@Override
	public String getDescription() {
		return "used to reload plugins quickly and easily, use for plugin devs";
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
			PluginLoader.unloadPlugins();
			
			// Clear custom commands
			CustomCommand.commands.clear();
			
			// Do a properties checkup
			new PropertiesCheckup();
						
			// Reload server settings
			ServerSettings.load();
						
			// Reload properties
			PluginLoader.loadPlugins();
			
			logger.info("Reload complete!");
		}
		catch(IOException e) {
			logger.warn("There was a error reloading the plugins! Error: " + e.getMessage());
		}
	}
	
}
