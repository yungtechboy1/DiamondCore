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
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;

import net.trenterprises.diamondcore.cross.Diamond;
import net.trenterprises.diamondcore.cross.file.FileUtils;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * This class is used to handle HTML plugins for DiamondCore
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
@SuppressWarnings("restriction")
public class HTMLSession implements HttpHandler {
	
	// Techincal
	public static ArrayList<HTMLSession> sessionList = new ArrayList<HTMLSession>();
	protected static ArrayList<String> nameList = new ArrayList<String>();
	protected static HttpServer server = null;
	
	// Session related
	protected File htmlFile;
	protected String htmlName;
	protected boolean hidden;
	protected Document pluginDoc;
	
	// Other
	protected final String forbiddenPage;
	
	public HTMLSession(File htmlFile) throws IOException {
		// Activate server if it isn't bound yet
		if(server == null && ServerSettings.getWebPort() > 1024) {
			server = HttpServer.create(new InetSocketAddress(ServerSettings.getWebPort()), 0);
			server.setExecutor(null);
	        server.start();
		}
		
		// Load plugin
		this.htmlFile = htmlFile;
		this.htmlName = this.htmlFile.getName().substring(0, this.htmlFile.getName().length() - 5);
		if(nameList.indexOf(this.htmlName) == -1) nameList.add(this.htmlName);
		else {} // Throw exception and stop whole thing
		this.pluginDoc = Jsoup.parse(FileUtils.readFromFile(this.htmlFile), "UTF-8", Parser.xmlParser());
		this.hidden = Boolean.parseBoolean(pluginDoc.select("hidden").text());
		this.forbiddenPage = "<html><head><title>Forbidden</title><style type=\"text/css\"></style></head><body><h1>Forbidden</h1>" + this.htmlName + " is hidden from direct access by a web browser</body></html>";
		if(server != null) server.createContext(("/" + this.htmlName), this); // Create context
		
		// Set the document name
		//this.pluginDoc.title("DiamondCore - " + this.htmlName);
		
		// Remove the "hidden" tag so it doesnt show up on webpages
		this.pluginDoc.select("hidden").remove();
	}
	
	/**
	 * Used to get the HTML page as a string, mostly used by the
	 * <br>
	 * HTML session itself to respond to browsers
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Raw HTML page as a string
	 */
	public String getPage() {
		this.pluginDoc.select("onlineplayers").before(Diamond.getOnlinePlayers().size() + "");
		this.pluginDoc.select("maxplayers").before(ServerSettings.getMaxPlayers() + "");
	    return this.pluginDoc.toString();
	}
	
	/**
	 * Used to unload the current session, in essence, does nothing
	 * <br>
	 * this just makes sure that when using the "reload" command that
	 * <br>
	 * the html pages are refreshed without conflicts
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 */
	public void unloadSession() {
		int index = sessionList.indexOf(this);
		sessionList.remove(index);
		nameList.remove(index);
	}
	
	// Handle HTML request from browsers
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String response = (this.hidden ? forbiddenPage : this.getPage());
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}
	
}
