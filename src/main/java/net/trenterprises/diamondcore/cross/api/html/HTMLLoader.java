/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.trenterprises.diamondcore.run;
import net.trenterprises.diamondcore.cross.file.FileList;

/**
 * Used to load all of the HTML plugins for DiamondCore
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public final class HTMLLoader {
	
	public static void loadPlugins() throws IOException {
		ArrayList<File> htmlPluginFiles = listHTMLFiles();
		for(int i = 0; i < htmlPluginFiles.size(); i++) {
			new HTMLSession(htmlPluginFiles.get(i));
		}
	}
	
	public static void unloadPlugins() {
		ArrayList<HTMLSession> sessions = HTMLSession.sessionList;
		for(int i = 0; i < sessions.size(); i++) {
			sessions.get(i).unloadSession();
		}
	}
	
	private static ArrayList<File> listHTMLFiles() throws IOException {
		ArrayList<File> fileListArray = new ArrayList<File>();
		FileList.setDebug(run.getShouldDebug());
		String pathToPlugins = FileList.pluginFolder.getAbsolutePath();
		File pluginFolderList = new File(pathToPlugins);
		File[] pluginFiles = pluginFolderList.listFiles(); 
		for (int i = 0; i < pluginFiles.length; i++) {
			if (pluginFiles[i].isFile()) {
				if(pluginFiles[i].getName().endsWith(".html")) fileListArray.add(pluginFiles[i]);
			}
		}
		return fileListArray;
	}
	
}
