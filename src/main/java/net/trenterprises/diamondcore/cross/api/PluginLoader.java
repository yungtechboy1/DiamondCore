/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api;

import java.io.File;
import java.util.ArrayList;

import net.trenterprises.diamondcore.run;
import net.trenterprises.diamondcore.cross.api.exception.PluginDuplicateException;
import net.trenterprises.diamondcore.cross.file.FileList;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

/**
 * This class is used to load all of the plugins located inside
 * <br>
 * of the "plugin" folder
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public abstract class PluginLoader {
	
	static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	public static void loadPlugins() {
		logger.info("Loading plugins!");
		ArrayList<File> JarList = listJars();
		for(int i = 0; i < JarList.size(); i++) {
			try {
				new PluginSession(JarList.get(i));
			}
			catch(PluginDuplicateException PDE) {
				logger.err("The plugin " + PDE.getPluginName() + " was not loaded because it conflicts with another!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Finished loading plugins!");
	}
	
	public static void unloadPlugins() {
		ArrayList<PluginSession> sessions = PluginSession.sessionList;
		for(int i = 0; i < sessions.size(); i++) {
			try {
				sessions.get(i).unloadSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<File> listJars() {
		ArrayList<File> FileListArray = new ArrayList<File>();
		FileList.setDebug(run.getShouldDebug());
		String PathToPluginJars = FileList.pluginFolder.getAbsolutePath();
		File PluginFolderList = new File(PathToPluginJars);
		File[] PluginJars = PluginFolderList.listFiles(); 
		for (int i = 0; i < PluginJars.length; i++) {
			if (PluginJars[i].isFile()) {
				if(PluginJars[i].getName().endsWith(".jar")) FileListArray.add(PluginJars[i]);
			}
		}
		return FileListArray;
	}
}
