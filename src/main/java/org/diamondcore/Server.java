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
import org.diamondcore.desktop.utils.LocalServerBroadcaster;
import org.diamondcore.exception.DiamondException;
import org.diamondcore.file.FileCheckup;
import org.diamondcore.file.FileList;
import org.diamondcore.file.PropertiesCheckup;
import org.diamondcore.logging.DiamondLogger;
import org.diamondcore.logging.Log4j2Logger;
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
	
	// Running Variable
	private boolean running = false;
	private boolean debug = false;
	
	// Logger
	private final DiamondLogger logger = new Log4j2Logger("DiamondCore");
	
	public Server(boolean shouldDebug) throws IOException, InterruptedException, PluginException, DiamondException {
		// Just install ANSI, it'll say itself if something went wrong
		AnsiConsole.systemInstall();
		
		// Start server
		debug = shouldDebug;
		Diamond.setServer(this);
		logger.info("Starting Server!");
		logger.info("Debug: " + (debug ? "activated" : "de-activated"));
		
		// Make sure Mojang Auth Server is online
		logger.info("Making sure Mojang Auth server is online...");
		if(MojangAuthServer.isOnline()) logger.info("Server is online! [" + MojangAuthServer.getImplementationVersion() + "]");
		else logger.warn("The Mojang Auth server is currently offline, players can not join the server until it is back up!");
		
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
		new LocalServerBroadcaster(InetAddress.getByName("127.0.0.1"), 4445).start();
		new InputReader().start();
		
		// Load plugins after everything is initialized
		JavaPluginLoader.loadPlugins();
		
		running = true;
		logger.info("Started Server!");
	}
	
	/**
	 * Used to get the plugin manager
	 * 
	 * @return Plugin manager
	 * @author Trent Summerlin
	 */
	public PluginManager getPluginManager() {
		return manager;
	}
	
	/**
	 * Used to see if the DiamondCore server software is running
	 * 
	 * @return Server (running) state
	 * @author Trent Summerlin
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	 * Used to retrieve the internal sever logger
	 * 
	 * @return Server logger
	 * @author Trent Summerlin
	 */
	public DiamondLogger getLogger() {
		return this.logger;
	}
	
	/**
	 * Used to see if DiamondCore is in debug mode
	 * 
	 * @return Debug mode state
	 * @author Trent Summerlin
	 */
	public boolean isDebug() {
		return this.debug;
	}
	
}

// Main ticker
class MainTicker extends Thread implements Runnable {
	
	// Initialize everything before startup
	private Ticker ticker = new Ticker(20);
	private DiamondLogger logger = new Log4j2Logger("CONSOLE");
	
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
					if(!runningSlow || ticksRanSlow >= tickReset) logger.info("Can't keep up! Did the system time change, or is the server overloaded? Skipping " + skippedTicks + " ticks");
					if(ticksRanSlow >= tickReset) ticksRanSlow = 0;
					runningSlow = true;
				}
				if(runningSlow) ticksRanSlow++;
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
