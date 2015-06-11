/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package org.diamondcore;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

import org.diamondcore.api.JavaPluginLoader;
import org.diamondcore.api.exception.PluginException;
import org.diamondcore.api.plugin.PluginManager;
import org.diamondcore.block.Block;
import org.diamondcore.command.InputReader;
import org.diamondcore.desktop.TCPPacketHandler;
import org.diamondcore.desktop.utils.TCPBroadcaster;
import org.diamondcore.exception.DiamondException;
import org.diamondcore.file.FileCheckup;
import org.diamondcore.file.FileList;
import org.diamondcore.file.PropertiesCheckup;
import org.diamondcore.lang.Lang;
import org.diamondcore.lang.exeption.LangException;
import org.diamondcore.logging.DiamondLogger;
import org.diamondcore.logging.DiamondTranslator;
import org.diamondcore.logging.Log4j2Logger;
import org.diamondcore.logging.Log4j2Translator;
import org.diamondcore.mojang.MojangAuthServer;
import org.diamondcore.pocket.UDPPacketHandler;
import org.diamondcore.utils.ServerSettings;
import org.diamondcore.utils.Ticker;
import org.diamondcore.world.time.WorldTime;
import org.fusesource.jansi.AnsiConsole;

/* NOTE: In order to load the server in debug mode in eclipse,
 * go to run configurations and add "true" in arguments! */

/**
 * This is the second-to-main class for the software
 * and can only be called by the "run" class.
 */
public class Server {
	
	// Plugins
	private final PluginManager manager = new PluginManager();
	private final TCPBroadcaster broadcaster;
	
	// Running variable
	private boolean running = false;
	private boolean debug = false;
	
	// Logger
	private final DiamondLogger logger = new Log4j2Logger("DiamondCore");
	private final DiamondTranslator lang = new Log4j2Translator("DiamondCore");
	
	public Server(boolean shouldDebug) throws IOException, InterruptedException, PluginException, DiamondException, LangException {
		// Predefine console data
		AnsiConsole.systemInstall();
		Lang.setLang("de_GM"); // TODO: Add ability to change language
		
		// Start server
		debug = shouldDebug;
		Diamond.setServer(this);
		lang.info("server.start");
		if(debug) lang.info("server.debugOn");
		else lang.info("server.debugOff");
		
		// Make sure Mojang Auth Server is online
		lang.info("mojang.checkAuth");
		if(MojangAuthServer.isOnline()) lang.info("mojang.authOnline", MojangAuthServer.getImplementationVersion());
		else lang.warn("mojang.authOffline");
		
		// Check Files and Properties
		FileList.setDebug(shouldDebug);
		new FileCheckup();
		new PropertiesCheckup();
		
		// Load Server Settings
		ServerSettings.load();
		
		// Initialize everything
		Block.registerBlocks();
		
		// Finish startup
		new MainTicker().start();
		new UDPPacketHandler(new DatagramSocket(ServerSettings.getPEPort())).start();
		new TCPPacketHandler(new ServerSocket(ServerSettings.getPCPort())).start();
		this.broadcaster = new TCPBroadcaster(InetAddress.getByName("127.0.0.1"), 4445);
		this.broadcaster.start();
		new InputReader().start();
		
		// Load plugins after everything is initialized
		JavaPluginLoader.loadPlugins();
		
		running = true;
		lang.info("server.started");
	}
	
	/**
	 * Used to get the plugin manager
	 * 
	 * @return Plugin manager
	 * @author Trent Summerlin
	 */
	public final PluginManager getPluginManager() {
		return manager;
	}
	
	/**
	 * Used to see if the DiamondCore server software is running
	 * 
	 * @return Server (running) state
	 * @author Trent Summerlin
	 */
	public final boolean isRunning() {
		return this.running;
	}
	
	/**
	 * Used to retrieve the internal sever logger
	 * 
	 * @return Server logger
	 * @author Trent Summerlin
	 */
	public final DiamondLogger getLogger() {
		return this.logger;
	}
	
	/**
	 * Used to retrieve the internal server translator
	 * 
	 * @return Server translator
	 * @author Trent Summerlin
	 */
	public final DiamondTranslator getTranslator() {
		return this.lang;
	}
	
	/**
	 * Used to retrieve the Local Server Broadcaster
	 * for Minecraft
	 * 
	 * @return The local server broadcaster
	 * @author Trent Summerlin
	 */
	public final TCPBroadcaster getBroadcaster() {
		return this.broadcaster;
	}
	
	/**
	 * Used to see if DiamondCore is in debug mode
	 * 
	 * @return Debug mode state
	 * @author Trent Summerlin
	 */
	public final boolean isDebug() {
		return this.debug;
	}
	
}

// Main ticker
class MainTicker extends Thread implements Runnable {
	
	// Initialize everything before startup
	private Ticker ticker = new Ticker(20);
	private DiamondTranslator lang = new Log4j2Translator("CONSOLE");
	
	public void run() {
		ticker.start();
		int lastTick = -1;
		int ticksRanSlow = -1;
		int tickReset = 1200;
		boolean runningSlow = false;
		while(Diamond.getServer().isRunning()) {
			int currentTick = ticker.getTick();
			if(currentTick != lastTick) {
				WorldTime.tick();
				if(currentTick > lastTick+2) {
					int skippedTicks = (currentTick - lastTick - 1); /* Subtract 1 more because the current tick will ALWAYS be 1 or more than the last one */
					if(!runningSlow || ticksRanSlow >= tickReset)
						lang.warn("server.runningSlow", skippedTicks);
					if(ticksRanSlow >= tickReset)
						ticksRanSlow = 0;
					runningSlow = true;
				}
				if(runningSlow)
					ticksRanSlow++;
				else {
					ticksRanSlow = -1;
					runningSlow = false;
				}
				lastTick = currentTick;
			}
			if(!Diamond.getServer().isRunning()) JavaPluginLoader.unloadPlugins();
		}
	}
}
