/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import net.trenterprises.diamondcore.cross.api.event.Event;
import net.trenterprises.diamondcore.cross.api.exception.InvalidConstructorException;
import net.trenterprises.diamondcore.cross.api.exception.PluginDuplicateException;
import net.trenterprises.diamondcore.cross.command.custom.CustomCommand;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

import org.yaml.snakeyaml.Yaml;

/**
 * This class is used to handle plugins and initizalize them
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class PluginSession {
	
	static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	// Plugin list
	public static ArrayList<PluginSession> sessionList = new ArrayList<PluginSession>();
	private static ArrayList<String> mainClassList = new ArrayList<String>();
	private static ArrayList<String> mainPackageList = new ArrayList<String>();
	private static ArrayList<String> pluginNames = new ArrayList<String>();
	
	// Advanced Plugin Info
	protected String mainClass;
	protected String mainPackage;
	protected File jarFile;
	
	// General Plugin Info
	protected String pluginName;
	protected String pluginVersion;
	protected String pluginAuthor;
	
	// Plugin methods
	private final String enableMethod = "onEnable";
	private final String disableMethod = "onDisable";
	
	public PluginSession(File jarFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException, ClassNotFoundException, IOException, PluginDuplicateException, InvalidConstructorException {
		// Load Plugin Jar and Plugin Properties
		this.jarFile = jarFile;
		this.loadPluginYML();
		
		// See if the main class is already in use
		String mainClassCheck = null;
		try {
			int index = PluginSession.mainClassList.indexOf(mainClass);
			try {
				mainClassCheck = PluginSession.mainClassList.get(index);
			}
			catch(Exception e) {
				if(e instanceof IndexOutOfBoundsException || e instanceof ArrayIndexOutOfBoundsException); 
				else e.printStackTrace();
			}
		}
		catch(Exception E) {
			if(E instanceof IndexOutOfBoundsException || E instanceof ArrayIndexOutOfBoundsException);
			else E.printStackTrace();
		}
		
		// See if the main class is already in use #2
		if((mainClassCheck == null || PluginSession.mainClassList.size() == 0)) {
			// Issue a warning if the main-class is different (Indicating it's a different plugin) but the name is taken by another plugin.
			if(pluginNames.contains(this.pluginName)) logger.warn("Warning: A there are multiple plugins with the name \"" + this.pluginName + "\"!");
			
			if(mainPackageList.contains(this.mainPackage)) throw new PluginDuplicateException(this.pluginName, this.mainClass);
			else mainPackageList.add(this.mainPackage);
			pluginNames.add(this.pluginName);
			
			// Throw exception if class has more than one constructor
			if(this.getClassFromPlugin(this.mainClass).getConstructors().length > 1) throw new InvalidConstructorException(this.getClassFromPlugin(this.mainClass));
			
			// Try to instantianite the class
			try {
				this.getClassFromPlugin(this.mainClass).newInstance();
			} catch(InstantiationException e) {
				throw new InvalidConstructorException(this.getClassFromPlugin(this.mainClass));
			}
			
			// Add the plugin to the list
			sessionList.add(this);
			mainClassList.add(this.mainClass);
			logger.info("Loading plugin " + this.pluginName + " v" + this.pluginVersion + " by " + this.pluginAuthor);
			this.executeClassMethod(this.getClassFromPlugin(this.mainClass), this.enableMethod);
		}
		else throw new PluginDuplicateException(this.pluginName, this.mainClass);
	}
	
	/**
	 * Used to load the plugin YAML and see if the plugin is valid at all
	 * 
	 * @throws IOException
	 * @throws PluginDuplicateException
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	protected void loadPluginYML() throws IOException, PluginDuplicateException {
		Yaml PluginYML = new Yaml();
		URL PluginURL = new URL("jar:file:" + this.jarFile.getAbsolutePath() + "!/plugin.yml");
		InputStream PluginInputStream = PluginURL.openStream();
		Map<?, ?> PluginMap = (Map<?, ?>) PluginYML.load(PluginInputStream);
		mainClass = PluginMap.get("main").toString();
		String[] RawMainPackage = mainClass.split("\\.");
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < RawMainPackage.length-1; i++) {
			b.append((i == RawMainPackage.length-1 ? (RawMainPackage[i]+ ".") : (RawMainPackage[i])));
		}
		mainPackage = b.toString();
		pluginName = PluginMap.get("name").toString();
		pluginVersion = PluginMap.get("version").toString();
		pluginAuthor = PluginMap.get("author").toString();
		if(mainClass.equals(null)) throw new NullPointerException();
		if(pluginName.equals(null)) throw new NullPointerException();
		if(pluginVersion.equals(null)) throw new NullPointerException();
		if(pluginAuthor.equals(null)) throw new NullPointerException();
		
		// Register commands
		try {
			if(PluginMap.containsKey("commands")) {
				Map<?, ?> CommandMap = (Map<?, ?>) PluginMap.get("commands");
				Iterator<?> commandIterator = CommandMap.entrySet().iterator();
				while(commandIterator.hasNext()) {
					Map.Entry<?, ?> command = (Map.Entry<?, ?>) commandIterator.next();
					Map<?, ?> commandEntries = (Map<?, ?>) command.getValue();
					String name = command.getKey().toString();
					String description = (commandEntries.containsKey("description") ? commandEntries.get("description").toString() : "UNKNOWN-DESC");
					String usage = (commandEntries.containsKey("usage") ? commandEntries.get("usage").toString() : "/<command>");
					new CustomCommand(name, description, usage, this);
				}
			}
		}
		catch(Exception E) {
			logger.warn("There was a error registering the commands for the plugin \"" + this.getPluginName() + "\"!");
		}
	}
	
	/**
	 * Used to get the main class of the plugin
	 * 
	 * @return The main class of the plugin
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public String getMainClass() {
		return mainClass;
	}
	
	/**
	 * Used to get the main package of the plugin
	 * 
	 * @return The main package of the plugin
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public String getMainPackage() {
		return mainPackage;
	}
	
	/**
	 * Used to get the name of the plugin
	 * 
	 * @return The name of the plugin
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public String getPluginName() {
		return pluginName;
	}
	
	/**
	 * Used to get the version of the plugin
	 * 
	 * @return The version of the plugin
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public String getPluginVersion() {
		return pluginVersion;
	}
	
	/**
	 * Used to get the author of the plugin
	 * 
	 * @return The author of the plugin
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public String getPluginAuthor() {
		return pluginAuthor;
	}
	
	/**
	 * Used to get the main jar file of the plugin
	 * 
	 * @return The plugin JAR file
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public File getJar() {
		return this.jarFile;
	}
	
	/**
	 * Used to get a class from a specific plugin
	 * 
	 * @param Class to load
	 * @return Class object from plugin
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 */
	public Class<?> getClassFromPlugin(String ClassToLoad) throws ClassNotFoundException, MalformedURLException {
	    URL[] PluginURLs = { this.jarFile.getAbsoluteFile().toURI().toURL() };
	   	URLClassLoader classLoader = URLClassLoader.newInstance(PluginURLs);
	    Class<?> pluginClass = classLoader.loadClass(ClassToLoad);
	    return pluginClass;
	}
	
	/**
	 * Used to execute a method from a class
	 * 
	 * @param Class to execute method from
	 * @param Name of the method
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public void executeClassMethod(Class<?> Class, String MethodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException {
		 Method Method = Class.getMethod(MethodName);
		 Method.invoke(Class.newInstance());
	}
	
	/**
	 * Used to execute a event from a class
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param Class to execute event from
	 * @param Name of the method
	 * @param Name of the Event
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public void executeEvent(Class<?> Class, String MethodName, Event e) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Method Method = Class.getMethod(MethodName, e.getClass());
		Method.invoke(Class.newInstance(), e);
	}
	
	/**
	 * Used to unload the current session
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException 
	 */
	public void unloadSession() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, SecurityException, ClassNotFoundException, MalformedURLException {
		try {this.executeClassMethod(this.getClassFromPlugin(this.getMainClass()), this.disableMethod);}
		catch(NoSuchMethodException E) {/* Ignore, some plugins don't use this. */}
		int index = sessionList.indexOf(this);
		sessionList.remove(index);
		mainClassList.remove(index);
		mainPackageList.remove(index);
		pluginNames.remove(index);
	}
	
}
