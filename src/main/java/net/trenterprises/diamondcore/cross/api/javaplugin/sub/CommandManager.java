/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.api.javaplugin.sub;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.PluginSession;
import net.trenterprises.diamondcore.cross.api.javaplugin.sub.command.CommandExecutor;
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
		if(commandExecutor.getClass() instanceof Class) {
			Class<?> commandExecutorClass = commandExecutor.getClass();
			if(commandExecutorClass.isAssignableFrom(CommandExecutor.class)) commandExecutors.add((Class<?>) commandExecutor);
	
		}
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
		for(int i = 0; i < PluginSession.sessionList.size(); i++) {
			PluginSession session = PluginSession.sessionList.get(i);
			Class<?> mainClassObject = session.getClassFromPlugin(session.getMainClass());
			Method mainClassMethod = mainClassObject.getMethod("onCommand", sender.getClass(), commandLabel.getClass(), args.getClass());
			mainClassMethod.invoke(mainClassObject.newInstance(), sender, commandLabel, args);
			for(int k = 0; k < commandExecutors.size(); k++) {
				try {
					Class<?> classObject = session.getClassFromPlugin(commandExecutors.get(k).getName());
					try {
						Method commandMethod = classObject.getMethod("onCommand");
						commandMethod.invoke("onCommand", sender, commandLabel, args);
					} catch(NoSuchMethodException e) {
						// Ignore, will happen
					}
				} catch(ClassNotFoundException e) {
					// Ignore, will happen
				}
			}
		}
	}
	
}
