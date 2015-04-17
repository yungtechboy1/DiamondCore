/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore;

import java.io.IOException;
import java.net.DatagramSocket;

import net.trenterprises.diamondcore.cross.Ticker;
import net.trenterprises.diamondcore.cross.api.PluginLoader;
import net.trenterprises.diamondcore.cross.console.ConsoleInputReader;
import net.trenterprises.diamondcore.cross.file.FileCheckup;
import net.trenterprises.diamondcore.cross.file.FileList;
import net.trenterprises.diamondcore.cross.file.PropertiesCheckup;
import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;
import net.trenterprises.diamondcore.cross.settings.ServerSettings;
import net.trenterprises.diamondcore.cross.world.time.WorldTime;
import net.trenterprises.diamondcore.pocket.network.PocketPacketHandler;

/* NOTE: In order to load the server in debug mode in eclipse,
 * go to run configurations and add "true" in arguments! */

/**
 * This is the second-to-main class for the software
 * and can only be called by the "run" class.
 */
public class DiamondCoreServer {
	
	// Running Variable
	protected static boolean running = false;
	protected static boolean debug = false;
	
	// Logger
	private static DiamondLogger Logger = new Log4j2Logger("DiamondCore");
	
	// Threads
	static PocketPacketHandler PacketHandler;
	static DatagramSocket PocketSocket;
	
	public DiamondCoreServer(boolean shouldDebug) throws IOException, InterruptedException {
		debug = shouldDebug;
		Logger.info("Starting Pocket Server!");
		Logger.info(("Debug: " + (debug == true ? "activated" : "de-activated")));
		
		// Check Files and Properties
		FileList.setDebug(shouldDebug);
		new FileCheckup();
		new PropertiesCheckup();
		
		// Load Server Settings
		ServerSettings.load();
		
		// Open Socket
		PocketSocket = new DatagramSocket(ServerSettings.getPEPort());
		
		// Load plugins
		PluginLoader.loadPlugins();
		
		running = true;
		Logger.info("Started Pocket Server!");
		
		// Initialize everything
		ConsoleInputReader consoleInput = new ConsoleInputReader();
		PocketPacketHandler pocketListener = new PocketPacketHandler(DiamondCoreServer.PocketSocket, this);
		
		// Finish startup
		new MainTicker(consoleInput, pocketListener).start();
		new PocketPacketHandlerThread(this).start();
	}
	
	/**
	 * Used to see if the DiamondCore server software is running
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return DiamondCore server running state
	 */
	public static boolean isRunning() {
		return running;
	}
	
	/**
	 * Used to see if the server is running in debug mode
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Server debug state
	 */
	public static boolean isDebug() {
		return debug;
	}
	
}

class MainTicker extends Thread {
	// Initialize everything before startup
	Ticker ticker = new Ticker(20);
	ConsoleInputReader consoleInput = null;
	PocketPacketHandler pocketListener = null;
	
	public MainTicker(ConsoleInputReader consoleInput, PocketPacketHandler pocketListener) {
		this.consoleInput = consoleInput;
		this.pocketListener = pocketListener;
	}
	
	public void run() {
		ticker.start();
		int lastTick = -1;
		while(DiamondCoreServer.isRunning()) {
			int currentTick = ticker.getTick();
			if(currentTick != lastTick) {
				WorldTime.tick();
				consoleInput.tick();
				lastTick = currentTick;
			}
			if(!DiamondCoreServer.isRunning()) {
				PluginLoader.unloadPlugins();
			}
		}
	}
}

// Packet Thread
class PocketPacketHandlerThread extends Thread {
	private DiamondCoreServer server;

	public PocketPacketHandlerThread(DiamondCoreServer server){
		this.server = server;
	}

	public void run() {
		try {
			DiamondCoreServer.PacketHandler = new PocketPacketHandler(DiamondCoreServer.PocketSocket, server);
			while(DiamondCoreServer.isRunning()) {
				DiamondCoreServer.PacketHandler.tick();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
