/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import net.trenterprises.diamondcore.cross.api.java.JavaPlugin;
import net.trenterprises.diamondcore.cross.api.java.JavaSession;
import net.trenterprises.diamondcore.cross.api.java.event.Event;
import net.trenterprises.diamondcore.cross.api.java.event.EventHandler;
import net.trenterprises.diamondcore.cross.api.java.event.Listener;
import net.trenterprises.diamondcore.cross.api.java.event.desktop.DesktopEvent;
import net.trenterprises.diamondcore.cross.api.java.event.pocket.PocketEvent;
import net.trenterprises.diamondcore.cross.api.xml.XMLSession;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

/**
 * This class is used for for managing plugins and doing things
 * <br>
 * such as registering listeners for events.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class PluginManager {
	
	protected static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	protected static ArrayList<Class<?>> Listeners = new ArrayList<Class<?>>();
	
	/**
	 * Used to add a listener to the list so it can execute when a event is fired.
	 * 
	 * @param Class extending JavaPlugin
	 * @param Class implementing Listener
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void regsiterEvents(JavaPlugin JavaPluginObjectClass, Object ListenerClass) {
		if(ListenerClass.getClass() instanceof Class) {
			Class<?>[] InterfacesList = ListenerClass.getClass().getInterfaces();
			ArrayList<Class<?>> InterfaceArray = new ArrayList<Class<?>>(Arrays.asList(InterfacesList));
			if(InterfaceArray.contains(Listener.class)) {
				if(ListenerClass.getClass().getConstructors().length == 1) Listeners.add(ListenerClass.getClass());
				else logger.warn("The class " + ListenerClass.getClass().getSimpleName() + " will not be added to the listeners because it has " + (ListenerClass.getClass().getConstructors().length-1) + " too many constructors! (It should not have any)");
			}
		}
	}
	
	
	/**
	 * Used to retrieve the listeners
	 * 
	 * @return Event listeners
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static ArrayList<Class<?>> getListeners() {
		return Listeners;
	}
	
	/**
	 * Used to throw a event for the plugins 
	 * 
	 * @param Event to throw
	 * @author Trent Summerlin
	 * @version 1.0
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws MalformedURLException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static void throwEvent(Event e) throws InstantiationException, IllegalAccessException, MalformedURLException, SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		
		// Java plugin event
		for(int i = 0; i < Listeners.size();) {
			boolean ran = false;
			int k = 0;
			while(!ran) {
				Class<?> classObject = null;
				try {
					JavaSession session = JavaSession.sessionList.get(k);
					classObject = session.getClassFromPlugin(Listeners.get(i).getName());
					Method[] ClassMethods = classObject.getMethods();
					for(int j = 0; j < ClassMethods.length;) {
						try {	
							if(ClassMethods[j].isAnnotationPresent(EventHandler.class)) {
								String methodName = ClassMethods[j].getName();
								if(e instanceof PocketEvent)  session.executeEvent(classObject, methodName, (PocketEvent) e);  // Execute event casted to PocketEvent
								if(e instanceof DesktopEvent) session.executeEvent(classObject, methodName, (DesktopEvent) e); // Execute event casted to DesktopEvent
							}
						}
						catch(NoSuchMethodException e1) {
							/*Ignore, might happen*/
						}
						j++;
					}
					ran = true;
				}
				catch(IndexOutOfBoundsException e2) {
					ran = true;
					break;
				}
				catch(ClassNotFoundException e3) {
					/*Ignore, might happen*/
					k++;
					if(k >= JavaSession.sessionList.size()) ran = true;
				}
			}
			i++;
		}
		
		// HTML plugin event
		ArrayList<XMLSession> html = XMLSession.sessionList;
		for(int k = 0; k < html.size(); k++) {
			html.get(k).throwEvent(e);
		}
		
	}
	
}
