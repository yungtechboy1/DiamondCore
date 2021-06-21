/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.api.event;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.diamondcore.api.plugin.PluginManager;
import org.diamondcore.exception.DiamondException;

public class EventFactory {
	
	/**
	 * Used to easily throw an event for plugins
	 * 
	 * @param throwable
	 * @param event
	 * @throws DiamondException
	 * @author Trent Summerlin
	 */
	public static void throwEvent(Event event) throws DiamondException {
		for(Listener listener : PluginManager.getListeners()) {
			for(Method method : getValidMethods(listener, event)) {
				try {
					method.invoke(listener, event);
				} catch (Exception e) {
					throw new DiamondException("There was a error running a event method! Cause: " + e.getMessage());
				}
			}
		}
			
	}
	
	public static Method[] getValidMethods(Listener listener, Event event) {
		ArrayList<Method> validMethods = new ArrayList<Method>();
		for(Method method : listener.getClass().getDeclaredMethods()) {
			method.setAccessible(true); // Make the method accessible, even if it might be private/protected
			boolean isHandler = method.isAnnotationPresent(EventHandler.class);
			boolean hasValidParams = method.getParameters().length == 1 && method.getParameterTypes()[0].equals(event.getClass());
			if(isHandler && hasValidParams)
				validMethods.add(method);
		}
		Method[] list = new Method[validMethods.size()];
		for(int i = 0; i < list.length; i++)
			list[i] = validMethods.get(i);
		return list;
	}
}
