/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.java;

import java.io.File;
import java.util.ArrayList;

import net.trenterprises.diamondcore.run;
import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.api.java.exception.PluginDuplicateException;
import net.trenterprises.diamondcore.cross.file.FileList;

/**
 * This class is used to load all of the plugins located inside
 * <br>
 * of the "plugin" folder
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public abstract class JavaLoader {
	
	public static void loadPlugins() {
		Diamond.logger.info("Loading plugins!");
		ArrayList<File> jarList = listJars();
		for(int i = 0; i < jarList.size(); i++) {
			try {
				new JavaSession(jarList.get(i));
			}
			catch(PluginDuplicateException PDE) {
				Diamond.logger.err("The plugin " + PDE.getPluginName() + " was not loaded because it conflicts with another!");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		Diamond.logger.info("Finished loading plugins!");
	}
	
	public static void unloadPlugins() {
		ArrayList<JavaSession> sessions = JavaSession.sessionList;
		for(int i = 0; i < sessions.size(); i++) {
			try {
				sessions.get(i).unloadSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<File> listJars() {
		ArrayList<File> fileListArray = new ArrayList<File>();
		FileList.setDebug(run.getShouldDebug());
		String pathToPlugins = FileList.pluginFolder.getAbsolutePath();
		File pluginFolderList = new File(pathToPlugins);
		File[] pluginFiles = pluginFolderList.listFiles(); 
		for (int i = 0; i < pluginFiles.length; i++) {
			if (pluginFiles[i].isFile()) {
				if(pluginFiles[i].getName().endsWith(".jar")) fileListArray.add(pluginFiles[i]);
			}
		}
		return fileListArray;
	}
}
