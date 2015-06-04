/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore.api;

import java.io.File;
import java.io.IOException;

import org.diamondcore.api.exception.PluginException;
import org.diamondcore.file.FileList;

/**
 * Used to load all the plugins in the "plugins" folder
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class JavaPluginLoader {
	
	private JavaPluginLoader() {}
	
	public static void loadPlugins() throws IOException, PluginException {
		File[] pluginList = FileList.pluginFolder.listFiles();
		for(File plugin : pluginList)
			if(plugin.getName().endsWith(".jar"))
				new JavaPluginSession(plugin);
	}
	
	public static void unloadPlugins() {
		for(JavaPluginSession session : JavaPluginSession.getPluginSessions())
			session.unloadSession();
	}
	
}
