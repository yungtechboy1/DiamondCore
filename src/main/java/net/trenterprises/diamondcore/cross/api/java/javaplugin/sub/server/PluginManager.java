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
import java.util.Collection;

import net.trenterprises.diamondcore.cross.api.java.JavaPlugin;
import net.trenterprises.diamondcore.cross.api.java.event.Event;
import net.trenterprises.diamondcore.cross.api.java.event.EventHandler;
import net.trenterprises.diamondcore.cross.api.java.event.Listener;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.Server;
import net.trenterprises.diamondcore.cross.api.xml.XMLSession;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

/**
 * This class is used for for managing plugins and doing things <br>
 * such as registering listeners for events.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class PluginManager {
	public PluginManager(Server s) {
	}

	protected static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	protected static ArrayList<Listener> listeners = new ArrayList<Listener>();

	/**
	 * Used to add a listener to the list so it can execute when a event is
	 * fired.
	 * 
	 * @param Class
	 *            extending JavaPlugin
	 * @param Class
	 *            implementing Listener
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void registerEvents(JavaPlugin plugin, Listener listener) {
		listeners.add(listener);
	}

	/**
	 * Used to retrieve the listeners
	 * 
	 * @return Event listeners
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public static ArrayList<Listener> getListeners() {
		return listeners;
	}

	/**
	 * Used to throw a event for the plugins
	 * 
	 * @param Event
	 *            to throw
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
	public static void throwEvent(Event event) {
		
		// HTML plugin event
		for (XMLSession session : XMLSession.sessionList) session.throwEvent(event);
		
		// Execute bridge event
		
		// Execute java plugin event
		for (Listener listener : listeners) {
			Collection<Method> methods = getValidEventMethods(listener, event);
			for (Method method : methods) {
				try {
					method.invoke(listener, event);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	protected static Collection<Method> getValidEventMethods(Listener listener, Event event) {
		Collection<Method> result = new ArrayList<Method>();
		for (Method method : listener.getClass().getDeclaredMethods()) {
			boolean isHandler = method.isAnnotationPresent(EventHandler.class);
			boolean hasValidParams = method.getParameters().length == 1 && method.getParameterTypes()[0].equals(event.getClass());
			if (isHandler && hasValidParams) result.add(method);
		}
		return result;
	}

}
