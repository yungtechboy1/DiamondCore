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

import net.trenterprises.diamondcore.cross.api.java.exception.InvalidConstructorException;
import net.trenterprises.diamondcore.cross.api.java.exception.InvalidPluginDescriptorException;
import net.trenterprises.diamondcore.cross.api.java.exception.PluginDuplicateException;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.command.Command;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

import org.yaml.snakeyaml.Yaml;

/**
 * This class is used to handle plugins and initizalize them
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class JavaSession {
	
	protected static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	// Plugin list
	public static ArrayList<JavaSession> sessionList = new ArrayList<JavaSession>();
	protected static ArrayList<String> mainClassList = new ArrayList<String>();
	protected static ArrayList<String> pluginNames = new ArrayList<String>();
	
	// Advanced plugin info
	protected String mainClass;
	protected Object mainInstance;
	protected File jarFile;
	
	// General plugin info
	protected String pluginName;
	protected String pluginVersion;
	protected String pluginAuthor;
	
	// Plugin methods
	protected final String enableMethod = "onEnable";
	protected final String disableMethod = "onDisable";
	
	public JavaSession(File jarFile) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException, ClassNotFoundException, IOException, PluginDuplicateException, InvalidConstructorException, InvalidPluginDescriptorException {
		// Load Plugin Jar and Plugin Properties
		this.jarFile = jarFile;
		this.loadPluginYML();
		
		// See if the main class is already in use
		String mainClassCheck = null;
		try {
			int index = JavaSession.mainClassList.indexOf(mainClass);
			mainClassCheck = JavaSession.mainClassList.get(index);
		}
		catch(Exception E) {
			if(E instanceof IndexOutOfBoundsException || E instanceof ArrayIndexOutOfBoundsException);
			else E.printStackTrace();
		}
		
		// See if the main class is already in use #2
		if(mainClassCheck == null || JavaSession.mainClassList.size() == 0) {
			// Issue a warning if the main-class is different (Indicating it's a different plugin) but the name is taken by another plugin.
			if(pluginNames.contains(this.pluginName)) logger.warn("Warning: A there are multiple plugins with the name \"" + this.pluginName + "\"!");
			pluginNames.add(this.pluginName);
			
			// Load main class
			URL[] PluginURLs = { this.jarFile.getAbsoluteFile().toURI().toURL() };
		   	URLClassLoader classLoader = URLClassLoader.newInstance(PluginURLs);
		   	Class<?> main = classLoader.loadClass(this.mainClass);
			
		   	// Throw exception if class has more than one constructor
		   	if(main.getConstructors().length > 1) throw new InvalidConstructorException(main);
		   	
			// Try to load the instance into an object for later
			try {
			    this.mainInstance = main.newInstance();
			} catch(InstantiationException e) {
				throw new InvalidConstructorException(main);
			}
			
			// Add the plugin to the list
			sessionList.add(this);
			mainClassList.add(this.mainClass);
			logger.info("Loading plugin " + this.pluginName + " v" + this.pluginVersion + " by " + this.pluginAuthor);
			this.executeMethod(this.enableMethod);
		} else throw new PluginDuplicateException(this.pluginName, this.mainClass);
	}
	
	/**
	 * Used to load the plugin YAML and see if the plugin is valid at all
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws IOException
	 * @throws PluginDuplicateException
	 * @throws InvalidPluginDescriptorException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	protected void loadPluginYML() throws IOException, PluginDuplicateException, InvalidPluginDescriptorException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Load plugin YAML
		Yaml pluginYML = new Yaml();
		URL pluginURL = new URL("jar:file:" + this.jarFile.getAbsolutePath() + "!/plugin.yml");
		InputStream PluginInputStream = pluginURL.openStream();
		Map<?, ?> pluginMap = (Map<?, ?>) pluginYML.load(PluginInputStream);
		
		// Get plugin info, throw exceptions if neccessary
		mainClass = pluginMap.get("main").toString();
		pluginName = pluginMap.get("name").toString();
		pluginVersion = pluginMap.get("version").toString();
		pluginAuthor = pluginMap.get("author").toString();
		if(mainClass.equals(null)) throw new InvalidPluginDescriptorException("main");
		if(pluginName.equals(null)) throw new InvalidPluginDescriptorException("name");
		if(pluginVersion.equals(null)) throw new InvalidPluginDescriptorException("version");
		if(pluginAuthor.equals(null)) throw new InvalidPluginDescriptorException("author");
		
		// Register commands
		try {
			if(pluginMap.containsKey("commands")) {
				Map<?, ?> CommandMap = (Map<?, ?>) pluginMap.get("commands");
				Iterator<?> commandIterator = CommandMap.entrySet().iterator();
				while(commandIterator.hasNext()) {
					Map.Entry<?, ?> command = (Map.Entry<?, ?>) commandIterator.next();
					Map<?, ?> commandEntries = (Map<?, ?>) command.getValue();
					String name = command.getKey().toString();
					String description = (commandEntries.containsKey("description") ? commandEntries.get("description").toString() : "UNKNOWN-DESC");
					String usage = (commandEntries.containsKey("usage") ? commandEntries.get("usage").toString() : "/<command>");
					new Command(this, name, usage, description);
				}
			}
		}
		catch(Exception E) {
			logger.warn("There was a error registering the commands for the plugin \"" + this.pluginName + "\"!");
		}
	}
	
	/**
	 * Used to get the main class of the plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The main class of the plugin
	 */
	public String getMainClass() {
		return this.mainClass;
	}
	
	/**
	 * Used to get the name of the plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The name of the plugin
	 */
	public String getPluginName() {
		return this.pluginName;
	}
	
	/**
	 * Used to get the version of the plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The version of the plugin
	 */
	public String getPluginVersion() {
		return this.pluginVersion;
	}
	
	/**
	 * Used to get the author of the plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The author of the plugin
	 */
	public String getPluginAuthor() {
		return this.pluginAuthor;
	}
	
	/**
	 * Used to get the main jar file of the plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return The plugin JAR file
	 */
	@Deprecated
	public File getJar() {
		return this.jarFile;
	}
	
	/**
	 * Used to execute a command from the main class
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param classObject
	 * 		Class to execute method from
	 * @param methodName
	 * 		Name of the method
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 */
	public void executeCommand(CommandSender sender, String commandLabel, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method method = mainInstance.getClass().getMethod("onCommand", sender.getClass(), commandLabel.getClass(), args.getClass());
		method.invoke(this.mainInstance, sender, commandLabel, args);
	}
	
	
	/**
	 * Used to execute a method from the main class
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param classObject
	 * 		Class to execute method from
	 * @param methodName
	 * 		Name of the method
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws MalformedURLException 
	 * @throws ClassNotFoundException 
	 */
	public void executeMethod(String methodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, SecurityException, ClassNotFoundException, MalformedURLException {
		Method Method = this.mainInstance.getClass().getMethod(methodName);
		Method.invoke(this.mainInstance);
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
		try {
			this.executeMethod(this.disableMethod);
		}
		catch(NoSuchMethodException e) {
			e.printStackTrace();
		}
		int index = sessionList.indexOf(this);
		sessionList.remove(index);
		mainClassList.remove(index);
		pluginNames.remove(index);
	}
	
}