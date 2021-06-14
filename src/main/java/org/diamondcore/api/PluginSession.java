/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.diamondcore.api.exception.InvalidPluginDescriptorException;
import org.diamondcore.api.exception.PluginDuplicateException;
import org.diamondcore.api.exception.PluginException;
import org.diamondcore.command.Command;
import org.yaml.snakeyaml.Yaml;

/**
 * Used to run a plugin and get it's info
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class PluginSession {

	// Session handling
	private static ArrayList<PluginSession> pluginSessionList = new ArrayList<PluginSession>();
	private static ArrayList<String> mainClassList = new ArrayList<String>();
	
	/**
	 * Used to get the java plugin sessions
	 * 
	 * @return JavaPluginSession list
	 * @author Trent Summerlin
	 */
	public static PluginSession[] getPluginSessions() {
		PluginSession[] sessions = new PluginSession[pluginSessionList.size()];
		for(int i = 0; i < sessions.length; i++) sessions[i] = pluginSessionList.get(i);
		return sessions;
	}

	// Plugin info
	private final String pluginName;
	private final String pluginAuthor;
	private final String pluginVersion;
	private final String pluginMain;
	private final ArrayList<Command> commands = new ArrayList<Command>();

	// Advanced plugin info
	private final ClassLoader loader;
	private final JavaPlugin plugin;
	
	@SuppressWarnings("unchecked")
	public PluginSession(File jarFile) throws IOException, PluginException {
		// Load plugin JAR and YAML
		this.loader = URLClassLoader.newInstance(new URL[] { jarFile.toURI().toURL() });
		Map<String, ?> data = (Map<String, ?>) new Yaml().load(this.loader.getResource("plugin.yml").openStream());

		// Set variables
		this.pluginName = data.get("name").toString();
		this.pluginAuthor = data.get("author").toString();
		this.pluginVersion = data.get("version").toString();
		this.pluginMain = data.get("main").toString();
		
		// Make sure all data is set
		if(this.pluginName == null) throw new InvalidPluginDescriptorException("plugin name");
		if(this.pluginAuthor == null) throw new InvalidPluginDescriptorException("plugin author");
		if(this.pluginVersion == null) throw new InvalidPluginDescriptorException("plugin version");
		if(this.pluginMain == null) throw new InvalidPluginDescriptorException("plugin main");
		
		// Make sure the plugin main class is not taken by another
		if(mainClassList.contains(this.pluginMain))
			throw new PluginDuplicateException(this.pluginMain);
		
		// Instantiate it, and store it's instance
		try {
			this.plugin = (JavaPlugin) loader.loadClass(this.pluginMain).newInstance();
		} catch(ClassNotFoundException e) {
			throw new PluginException("There was an error loading the main class! (" + this.pluginMain + ")");
		} catch (Exception e) {
			throw new PluginException("There was an unknown error " + e.getCause() + " was thrown when loading the main class!");
		}
		
		// Load commands, if the plugin has any
		try {
			if(data.containsKey("commands")) {
				Map<?, ?> CommandMap = (Map<?, ?>) data.get("commands");
				Iterator<?> commandIterator = CommandMap.entrySet().iterator();
				while(commandIterator.hasNext()) {
					Map.Entry<String, ?> command = (Map.Entry<String, ?>) commandIterator.next();
					Map<String, ?> commandEntries = (Map<String, ?>) command.getValue();
					String label = command.getKey().toString();
					String description = (commandEntries.containsKey("description") ? commandEntries.get("description").toString() : "UNKNOWN-DESC");
					String usage = (commandEntries.containsKey("usage") ? commandEntries.get("usage").toString() : ("/" + label));
					
					Command c = new Command(plugin, label, usage, description);
					Command.addCommand(c);
					commands.add(c);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Add plugin session to the session list
		pluginSessionList.add(this);
		mainClassList.add(this.pluginMain);
		
		// Run main method
		plugin.onEnable();
	}
	
	/**
	 * Used to get the plugin of the plugin session
	 * 
	 * @return Plugin tied to Plugin Session
	 */
	public final JavaPlugin getPlugin() {
		return this.plugin;
	}
	
	/**
	 * Used to unload the plugin
	 * 
	 * @author Trent Summerlin
	 */
	public final void unloadSession() {
		// Run disable method
		plugin.onDisable();
		
		// Remove it from plugin session list
		int index = pluginSessionList.indexOf(this);
		pluginSessionList.remove(index);
		mainClassList.remove(index);
	}

}
