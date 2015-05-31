/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.command.diamond;

import java.io.IOException;

import net.trenterprises.diamondcore.cross.ServerSettings;
import net.trenterprises.diamondcore.cross.api.java.JavaLoader;
import net.trenterprises.diamondcore.cross.api.java.command.Command;
import net.trenterprises.diamondcore.cross.api.xml.XMLLoader;
import net.trenterprises.diamondcore.cross.command.CommandHandler;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.command.NativeCommand;
import net.trenterprises.diamondcore.cross.file.PropertiesCheckup;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;

public class ReloadCommand extends NativeCommand {
	
	public ReloadCommand(CommandHandler handler) {}
	public ReloadCommand() {}
	
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
			
			// Clear custom commands
			Command.commands.clear();
			
			// Do a properties checkup
			new PropertiesCheckup();
						
			// Reload server settings
			ServerSettings.load();
						
			// Reload plugins
			JavaLoader.loadPlugins();
			XMLLoader.loadPlugins();
			
			logger.info("Reload complete!");
		}
		catch(IOException e) {
			logger.warn("There was a error reloading the server! Error: " + e.getMessage());
		}
	}
	
}
