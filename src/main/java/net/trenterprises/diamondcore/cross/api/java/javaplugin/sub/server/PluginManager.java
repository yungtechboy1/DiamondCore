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

import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.api.java.JavaPlugin;
import net.trenterprises.diamondcore.cross.api.java.event.Listener;
import net.trenterprises.diamondcore.cross.api.java.javaplugin.sub.Server;

/**
 * This class is used for for managing plugins and doing things <br>
 * such as registering listeners for events.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class PluginManager {
	
	public PluginManager(Server s) {}

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

}
