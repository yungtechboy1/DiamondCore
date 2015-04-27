/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.exception;

/**
 * Thrown by the PluginSession class when a plugin has the same
 * <br>
 * main package as another.
 */
public class PluginDuplicateException extends PluginException {
	
	private static final long serialVersionUID = 5568304385346404038L;
	
	private final String pluginName;
	private final String mainClass;
	public PluginDuplicateException(String pluginName, String mainClass) {
		super("The plugin with the name \"" + "\" has a main class that conflicts with another!");
		this.pluginName = pluginName;
		this.mainClass = mainClass;
	}
	
	/**
	 * Used to get the name of the plugin that caused the error
	 * 
	 * @return The name of the plugin that caused the error
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public String getPluginName() {
		return this.pluginName;
	}
	
	/**
	 * Used to get the main class of the plugin that caused the error
	 * 
	 * @return The main class of the plugin that caused the error
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	@Deprecated
	public String getMainClass() {
		return this.mainClass;
	}
	
}
