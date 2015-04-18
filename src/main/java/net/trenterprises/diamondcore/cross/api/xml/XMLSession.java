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

import net.trenterprises.diamondcore.cross.file.FileUtils;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

public class XMLSession {
	
	protected static DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	// Plugin List
	public static ArrayList<XMLSession> xmlSessions = new ArrayList<XMLSession>();
	
	// Advanced plugin info
	protected File xmlFile;
	protected Document pluginDoc;
	
	// General plugin info
	protected String pluginName;
	protected String pluginVersion;
	protected String pluginAuthor;
	
	// Plugin methods
	protected final String enableMethod = "onenable";
	protected final String disableMethod = "ondisable";
	
	
	public XMLSession(File xmlFile) throws IOException {
		this.xmlFile = xmlFile;
		this.pluginDoc = Jsoup.parse(FileUtils.readFromFile(xmlFile), "UTF-8", Parser.xmlParser());
		this.loadPluginInfo();
	}
	
	private void loadPluginInfo() {
		boolean foundInfo = false;
		for(Element e : getParents()) {
			if(!foundInfo) {
				if(e.nodeName().equals("info")) {
					
					foundInfo = true;
				}
			} else break;
		}
		if(!foundInfo){} // Throw exception
		System.out.println(this.pluginName);
	}
	
	private ArrayList<Element> getParents() {
		ArrayList<Element> parentElements = new ArrayList<Element>();
		for(Element e : this.pluginDoc.getAllElements()) {
			if(e.parent() != null) {
				if(!parentElements.contains(e.parent().nodeName())) parentElements.add(e.parent());
			}
		}
		return parentElements;
	}
	
	public static void main(String[] args) throws IOException {
		new XMLSession(new File("/Users/Trent/Desktop/test.xml"));
	}
	
}
