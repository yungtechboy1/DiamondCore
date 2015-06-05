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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Map;

import org.diamondcore.api.exception.InvalidPluginDescriptorException;
import org.diamondcore.api.exception.PluginDuplicateException;
import org.diamondcore.api.exception.PluginException;
import org.yaml.snakeyaml.Yaml;

/**
 * Used to run a plugin and get it's info
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class JavaPluginSession {

	// Session handling
	protected static ArrayList<JavaPluginSession> pluginSessionList = new ArrayList<JavaPluginSession>();
	protected static ArrayList<String> mainClassList = new ArrayList<String>();
	
	/**
	 * Used to get the java plugin sessions
	 * 
	 * @return JavaPluginSession list
	 * @author Trent Summerlin
	 */
	public static JavaPluginSession[] getPluginSessions() {
		JavaPluginSession[] sessions = new JavaPluginSession[pluginSessionList.size()];
		for(int i = 0; i < sessions.length; i++) sessions[i] = pluginSessionList.get(i);
		return sessions;
	}

	// Plugin info
	protected final String pluginName;
	protected final String pluginAuthor;
	protected final String pluginVersion;
	protected final String pluginMain;

	// Advanced plugin info
	protected final ClassLoader loader;
	protected Class<?> main; // Not final due to error
	protected Object mainInstance; // Not final due to error
	
	// Extra
	protected final static String ENABLE = "onEnable";
	protected final static String DISABLE = "onDisable";
	
	@SuppressWarnings("unchecked")
	public JavaPluginSession(File jarFile) throws IOException, PluginException {
		// Load plugin JAR and YAML
		this.loader = URLClassLoader.newInstance(new URL[] { jarFile.toURI().toURL() });
		Map<String, String> data = (Map<String, String>) new Yaml().load(this.loader.getResource("plugin.yml").openStream());

		// Set variables
		this.pluginName = data.get("name");
		this.pluginAuthor = data.get("author");
		this.pluginVersion = data.get("version");
		this.pluginMain = data.get("main");
		
		// Make sure all data is set
		if(this.pluginName == null) throw new InvalidPluginDescriptorException("plugin name");
		if(this.pluginAuthor == null) throw new InvalidPluginDescriptorException("plugin author");
		if(this.pluginVersion == null) throw new InvalidPluginDescriptorException("plugin version");
		if(this.pluginMain == null) throw new InvalidPluginDescriptorException("plugin main");
		
		// Make sure the plugin main class is not taken by another
		if(mainClassList.contains(this.pluginMain))
			throw new PluginDuplicateException(this.pluginMain);
		
		// Instatiate it, and store it's instance
		try {
			this.main = Class.forName(this.pluginMain, true, loader);
			if(!this.main.getSuperclass().equals(JavaPlugin.class))
				throw new PluginException("The main class does not extend JavaPlugin!");
			this.mainInstance = this.main.newInstance();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Run main method
		try {
			main.getMethod(ENABLE).invoke(mainInstance);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// Add plugin session to the session list
		pluginSessionList.add(this);
		mainClassList.add(this.pluginMain);
	}
	
	/**
	 * Used to get the name of the plugin
	 * 
	 * @return Plugin name
	 * @author Trent Summerlin
	 */
	public final String getName() {
		return this.pluginName;
	}
	
	/**
	 * Used to get the author of the plugin
	 * 
	 * @return Plugin author
	 * @author Trent Summerlin
	 */
	public final String getAuthor() {
		return this.pluginAuthor;
	}
	
	/**
	 * Used to get the version of the plugin
	 * 
	 * @return Plugin version
	 * @author Trent Summerlin
	 */
	public final String getVersion() {
		return this.pluginVersion;
	}
	
	/**
	 * Used to unload the plugin
	 * 
	 * @author Trent Summerlin
	 */
	public final void unloadSession() {
		// Run disable method
		try {
			main.getMethod(DISABLE).invoke(mainInstance);
		} catch (Exception e) {
			// Ignore, some plugins don't have this
		}
		
		// Remove it from plugin session list
		int index = pluginSessionList.indexOf(this);
		pluginSessionList.remove(index);
		mainClassList.remove(index);
	}

}
