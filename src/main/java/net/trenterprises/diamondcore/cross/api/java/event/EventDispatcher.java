/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.server.PluginManager;
import net.trenterprises.diamondcore.cross.api.xml.XMLSession;

public final class EventDispatcher {
	
	private EventDispatcher() {}
	
	/**
	 * Used to throw a event for the plugins
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param Event
	 */
	public static void throwEvent(Event event) {
		
		// HTML plugin event
		for (XMLSession session : XMLSession.sessionList) session.throwEvent(event);
		
		// Execute bridge event
		
		// Execute java plugin event
		for (Listener listener : PluginManager.getListeners()) {
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
	
	/**
	 * Used to get the valid event methods that the dispatcher can execute
	 * 
	 * @author Trent Summerlin
	 * @version 1.1
	 * @param listener
	 * @param event
	 * @return Valid event methods
	 */
	protected static Collection<Method> getValidEventMethods(Listener listener, Event event) {
		Collection<Method> result = new ArrayList<Method>();
		for (Method method : listener.getClass().getDeclaredMethods()) {
			method.setAccessible(true); // Make the method accessible, even if it might be private/protected
			boolean isHandler = method.isAnnotationPresent(EventHandler.class);
			boolean hasValidParams = method.getParameters().length == 1 && method.getParameterTypes()[0].equals(event.getClass());
			if (isHandler && hasValidParams) result.add(method);
		}
		return result;
	}
	
}
