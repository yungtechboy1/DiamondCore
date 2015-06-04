/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.api;

import java.io.IOException;
import java.util.Map;

import org.diamondcore.Server;
import org.diamondcore.diamond.Diamond;
import org.diamondcore.logging.DiamondLogger;
import org.diamondcore.logging.Log4j2Logger;
import org.yaml.snakeyaml.Yaml;

/**
 * Extended by the main class of plugins
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class JavaPlugin {
	
	DiamondLogger logger;
	
	/**
	 * Used to get the plugin logger
	 * 
	 * @return Plugin logger
	 * @author Trent Summerlin
	 */
	public DiamondLogger getLogger() {
		if(logger == null)
			this.logger = new Log4j2Logger(this.getName());
		return this.logger;
	}
	
	/**
	 * Used to get the server
	 * 
	 * @return Server object
	 * @author Trent Summerlin
	 */
	public Server getServer() {
		return Diamond.getServer();
	}
	
	/**
	 * Used to get the name of the plugin
	 * 
	 * @return Plugin name
	 * @author Trent Summerlin
	 */
	public final String getName() {
		return this.yaml().get("name");
	}
	
	/**
	 * Used to get the author of the plugin
	 * 
	 * @return Plugin author
	 * @author Trent Summerlin
	 */
	public final String getAuthor() {
		return this.yaml().get("author");
	}
	
	/**
	 * Used to get the version of the plugin
	 * 
	 * @return Plugin version
	 * @author Trent Summerlin
	 */
	public final String getVersion() {
		return this.yaml().get("version");
	}
	
	/**
	 * Used to get the plugin YAML properties
	 * 
	 * @return Plugin YML
	 * @author Trent Summerlin
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> yaml() {
		try {
			return (Map<String, String>) new Yaml().load(this.getClass().getResource("/plugin.yml").openStream());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
