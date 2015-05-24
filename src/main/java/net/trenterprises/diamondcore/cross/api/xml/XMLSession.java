/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.java.event.Event;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.command.exception.InvalidCommandException;
import net.trenterprises.diamondcore.cross.api.xml.functions.XMLFunctionList;
import net.trenterprises.diamondcore.cross.command.CommandSender;
import net.trenterprises.diamondcore.cross.file.FileUtils;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

/**
 * Used to handle, initiate, and run methods in
 * <br>
 * a XML plugin for DiamondCore
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class XMLSession {
	
	protected static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	// Plugin List
	public static ArrayList<XMLSession> sessionList = new ArrayList<XMLSession>();
	
	// Advanced plugin info
	protected File xmlFile;
	protected Document pluginDoc;
	protected DiamondLogger pluginLogger;
	
	// General plugin info
	protected String pluginName;
	protected String pluginVersion;
	protected String pluginAuthor;
	
	public XMLSession(File xmlFile) throws IOException {
		this.xmlFile = xmlFile;
		this.pluginDoc = Jsoup.parse(FileUtils.readFromFile(xmlFile), "UTF-8", Parser.xmlParser());
		this.loadPluginInfo();
		this.executePluginMethod(XMLFunctionList.onEnable);
		this.getCommands();
		sessionList.add(this);
	}
	
	/**
	 * Used to load the plugin info for the XML plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	private void loadPluginInfo() {
		Element info = null;
		try {
			info = this.pluginDoc.select("info").first();
			this.pluginName = info.select("name").text();
			this.pluginAuthor = info.select("author").text();
			this.pluginVersion = info.select("version").text();
			this.pluginLogger = new Log4j2Logger(pluginName);
		} catch(NullPointerException e) {
			// Throw XML exception
		}
	}
	
	/**
	 * Used to grab commands and register them from
	 * <br>
	 * XML plugins
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws InvalidCommandException
	 */
	@SuppressWarnings("unused")
	private void getCommands() {
		try {
			for(Element command : this.pluginDoc.select("command")) {
				String commandName = command.attr("name");
				String commandDescription = command.attr("description");
				String commandUsage = command.attr("usage");
				try {
					//new Command(commandName, commandUsage, commandDescription);
				} catch (Exception e) {
					logger.warn("There was a error registering commands for the plugin \"" + this.pluginName + "\"!");
				}
			}
		} catch(NullPointerException e) {
			// Ignore, some plugins do not register commands
		}
	}
	
	/**
	 * Used to call a method from a XML plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param methodName
	 * 		Name of the method to be executed
	 */
	public void executePluginMethod(String methodName) {
		Element method = this.pluginDoc.select(methodName).first();
		if(method != null) {
			for(Element function : method.getAllElements()) {
				String functionName = function.nodeName();
				switch(functionName) {
					case XMLFunctionList.getLogger:
						String logLevel = function.attr("level");
						if(logLevel.equals("info")) pluginLogger.info(function.text());
						else if(logLevel.equals("warn")) pluginLogger.warn(function.text());
						else if(logLevel.equals("err")) pluginLogger.err(function.text());
						break;
					
					default:
						// Throw exception
						break;
				}
			}
		}
	}
	
	/**
	 * Used to throw a command from a XML plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void throwCommand(CommandSender sender, String commandLabel, String[] args) {
		for(Element command : this.pluginDoc.select(XMLFunctionList.onCommand)) {
			String label = command.attr("label");
			if(label.equals(commandLabel)) this.executePluginMethod(XMLFunctionList.onCommand);
		}
	}
	
	/**
	 * Used to throw a event from a XML plugin
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void throwEvent(Event e) {
		for(Element event : this.pluginDoc.select(XMLFunctionList.event)) {
			String eventType = event.attr("type");
			if(eventType.equals(e.getName())) this.executePluginMethod(XMLFunctionList.event);
		}
	}
	
	/**
	 * Used to unload a XML session
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void unloadSession() {
		this.executePluginMethod(XMLFunctionList.onDisable);
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
	
}
