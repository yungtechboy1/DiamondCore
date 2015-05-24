/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api.java;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.Server;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

import org.yaml.snakeyaml.Yaml;

/**
 * This is a abstract class in which the main class of all plugins extend
 * and is the heart of pretty much all the functions for a plugin.
 */
public abstract class JavaPlugin {
	
	/**
	 * This function can be used to retrieve server info.
	 * <br>
	 * For example, if I did getServer().getLogger(), it would
	 * <br>
	 * return this server logger.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return DiamondCore server running
	 */
	public Server getServer() {
		return new Server(this);
	}
	
	/**
	 * This method can be used by plugins to easily
	 * <br>
	 * retrieve their plugin name without having to load
	 * <br>
	 * a InputStream and a YAML map.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0 
	 * @return Plugin name
	 */
	public String getName() {
		return this.yaml().get("name").toString();
	}
	
	/**
	 * This method can be used by plugins to easily
	 * <br>
	 * retrieve the plugin author without having to load
	 * <br>
	 * a InputStream and a YAML map.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Plugin Author
	 */
	public String getAuthor() {
		return this.yaml().get("author").toString();
	}
	
	/**
	 * This method can be used by plugins to easily
	 * <br>
	 * retrieve the plugin version without having to load
	 * <br>
	 * a InputStream and a YAML map.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Plugin Version
	 */
	public String getVersion() {
		return this.yaml().get("version").toString();
	}
	
	/**
	 * This method can be used by plugins to get their
	 * <br>
	 * assigned logger. And example looks like this:
	 * <br>
	 * [HOUR]:[MINUTE]:[SECOND] [LOGLEVEL] [PLUGINNAME] [PLUGIN MESSAGE]
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Plugin Logger
	 */
	public Log4j2Logger getLogger() {
		return new Log4j2Logger(this.yaml().get("name").toString());
	}
	
	/**
	 * This is a function to get the plugin YAML map. It can be used by
	 * <br>
	 * other plugins, but it is not recommended. as it is already used in
	 * <br>
	 * the functions such as getName(), getAuthor(), etc. for the plugin makers convenience.
	 *
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Plugin YAML map.
	 */
	protected Map<?, ?> yaml() {
		try {
			Yaml pluginYML = new Yaml();
			URL pluginURL = this.getClass().getResource("/plugin.yml");
			InputStream pluginInputStream = pluginURL.openStream();
			Map<?, ?> pluginMap = (Map<?, ?>) pluginYML.load(pluginInputStream);
			return pluginMap;
		}
		catch(Exception E) {
			return null;
		}
	}
	
	/**
	 * This method is ran whenever the server loads the plugin
	 * 
	 * @author Trent Summerlin
	 * @verion 1.0
	 */
	public abstract void onEnable();
	
	/**
	 * This method is ran whenever the server un-loads the plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public abstract void onDisable();
	
}
