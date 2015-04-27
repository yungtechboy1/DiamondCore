/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross.api.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.trenterprises.diamondcore.run;
import net.trenterprises.diamondcore.cross.file.FileList;
import net.trenterprises.diamondcore.cross.file.FileUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/**
 * Used to load get all of the XML plugins
 * <br>
 * ready for deployment in the server
 * 
 * @author Trent Summerlin
 * @verson 0.1.0-SNAPSHOT
 */
public abstract class XMLLoader {
	
	protected final static String DOCTYPE_VERSION = "<!doctype diamondcore>";
	
	public static void loadPlugins() throws IOException {
		ArrayList<File> xmlPluginFiles = listXMLFiles();
		for(int i = 0; i < xmlPluginFiles.size(); i++) {
			new XMLSession(xmlPluginFiles.get(i));
		}
	}
	
	public static void unloadPlugins() {
		ArrayList<XMLSession> sessions = XMLSession.sessionList;
		for(int i = 0; i < sessions.size(); i++) {
			try {
				sessions.get(i).unloadSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<File> listXMLFiles() throws IOException {
		ArrayList<File> fileListArray = new ArrayList<File>();
		FileList.setDebug(run.getShouldDebug());
		String pathToPlugins = FileList.pluginFolder.getAbsolutePath();
		File pluginFolderList = new File(pathToPlugins);
		File[] pluginFiles = pluginFolderList.listFiles(); 
		for (int i = 0; i < pluginFiles.length; i++) {
			if (pluginFiles[i].isFile()) {
				if(pluginFiles[i].getName().endsWith(".xml")){
					Document xml = Jsoup.parse(FileUtils.readFromFile(pluginFiles[i]), "UTF-8", Parser.xmlParser());
					String doctype = xml.childNodes().toArray()[0].toString();
					if(doctype.equals(DOCTYPE_VERSION)) fileListArray.add(pluginFiles[i]);
				}
			}
		}
		return fileListArray;
	}
	
}
