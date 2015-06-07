package org.diamondcore.api.plugin;

import java.util.ArrayList;

import org.diamondcore.api.JavaPlugin;
import org.diamondcore.api.event.Listener;

public class PluginManager {
	
	private static ArrayList<Listener> listeners = new ArrayList<Listener>();
	
	public void registerEvents(JavaPlugin plugin, Listener listener) {
		listeners.add(listener);
	}
	
	public static Listener[] getListeners() {
		Listener[] list = new Listener[listeners.size()];
		for(int i = 0; i < list.length; i++)
			list[i] = listeners.get(i);
		return list;
	}
	
}
