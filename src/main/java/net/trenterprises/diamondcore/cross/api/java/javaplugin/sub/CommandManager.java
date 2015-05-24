/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api.java.javaplugin.sub;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.xml.XMLSession;
import net.trenterprises.diamondcore.cross.command.CommandSender;

/**
 * Used for handling commands in plugins
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class CommandManager {
	
	public static ArrayList<Class<?>> commandExecutors = new ArrayList<Class<?>>();
	
	/**
	 * Tells the compiler that a class would like
	 * <br>
	 * to have it's "onCommand()" method executed when
	 * <br>
	 * a valid command is ran
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param commandExecutor
	 */
	public void addExecutor(Object commandExecutor) {
		// Add executor
	}
	
	/**
	 * Used to execute a command to all of the classes
	 * <br>
	 * that handle the requested command
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param commandLabel
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws MalformedURLException  
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static void throwCommand(CommandSender sender, String commandLabel, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException, InstantiationException, ClassNotFoundException, NoSuchMethodException, SecurityException{
		// Execute java command
		
		// HTML plugin command
		ArrayList<XMLSession> html = XMLSession.sessionList;
		for(int j = 0; j < html.size(); j++) {
			html.get(j).throwCommand(sender, commandLabel, args);
		}
	}
	
}
