/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.diamondcore.desktop.Favicon;
import org.diamondcore.file.FileList;
import org.diamondcore.world.GeneratorType;
import org.diamondcore.world.LevelType;

/**
 * This package is used for getting server-settings quickly
 * without a hassle.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public abstract class ServerSettings {
	
	protected static Properties Properties = new Properties();
	protected static Favicon serverIcon = null;
	
	/**
	 * Loads the properties folder into the system
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void load() throws FileNotFoundException, IOException {
		Properties.load(new FileInputStream(FileList.serverProperties));
		if(FileList.serverFavicon.exists()) serverIcon = Favicon.getInstance(FileList.serverFavicon);
	}
	
	// Player related
	/**
	 * See if the whitelist is enabled
	 * 
	 * @return Whitelist enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getEnableWhitelist() {
		return Boolean.parseBoolean(Properties.get("whitelist").toString());
	}
	
	/**
	 * See if the server should annouce player achievements
	 * 
	 * @return AnnouncePlayerAchievements enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getEnableAnnouncePlayerAchievements() {
		return Boolean.parseBoolean(Properties.get("announce-player-achievements").toString());
	}
	
	/**
	 * See if PVP is enabled
	 * 
	 * @return PVP enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getEnablePVP() {
		return Boolean.parseBoolean(Properties.get("pvp").toString());
	}
	
	/**
	 * Get the maximum amount of players that can be on the server at one time
	 * 
	 * @return Max players for the server
	 * @author Trent Summerlin
	 */
	public static int getMaxPlayers() {
		return Integer.parseInt(Properties.get("max-players").toString());
	}
	
	/**
	 * Get the time of being idle before a player is kicked
	 * 
	 * @return Idle time before a player is kicked
	 * @author Trent Summerlin
	 */
	public static int getPlayerIdleTimeout() {
		return Integer.parseInt(Properties.get("player-idle-timeout").toString());
	}
	
	/**
	 * Get the permission level for operators
	 * 
	 * @return Operator permission level
	 * @author Trent Summerlin
	 */
	public static int getOperatorPermissionLevel() {
		return Integer.parseInt(Properties.get("op-permission-level").toString());
	}
	
	/**
	 * See if the server should allow fly hacks or not
	 * 
	 * @return Server allow fly hacks
	 * @author Trent Summerlin
	 */
	public static boolean getAllowFlight() {
		return Boolean.parseBoolean(Properties.get("allow-flight").toString());
	}
	
	/**
	 * See if the server should force a gamemode upon its players
	 * 
	 * @return Server force-gamemode enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getForceGamemode() {
		return Boolean.parseBoolean(Properties.get("force-gamemode").toString());
	}
	
	/**
	 * Get the server default gamemode
	 * 
	 * @return Server default gamemode
	 * @author Trent Summerlin
	 */
	public static int getGamemode() {
		return Integer.parseInt(Properties.get("gamemode").toString());
	}
	
	/* Server Related */
	/**
	 * Get the server MCPC MOTD
	 * 
	 * @return Server MCPC MOTD
	 * @author Trent Summerlin
	 */
	public static String getPCMOTD() {
		char[] raw = Properties.get("motd-pc").toString().toCharArray();
		StringBuilder b = new StringBuilder();
		for(int i = 0; i <raw.length; i++) {
			if(raw[i] == '\u00A7' || raw[i] == '\u00C2') b.append('\u00C2' + "" + '\u00A7');
			else b.append(raw[i]);
		}
		return b.toString();
	}
	
	/**
	 * Get the MCPC server favicon (Will return null if there is no favicon)
	 * 
	 * @return Server MCPC favicon
	 * @throws IOException
	 * @author Trent Summerlin
	 */
	public static Favicon getServerFavicon() throws IOException {
		return serverIcon;
	}
	
	/**
	 * Get the server MCPE MOTD
	 * 
	 * @return Server MCPE MOTD
	 * @author Trent Summerlin
	 */
	public static String getPEMOTD() {
		char[] raw = Properties.get("motd-pe").toString().toCharArray();
		StringBuilder b = new StringBuilder();
		for(int i = 0; i <raw.length; i++) {
			if(raw[i] == '\u00A7' || raw[i] == '\u00C2') b.append('\u00C2' + "" + '\u00A7');
			else b.append(raw[i]);
		}
		return b.toString();
	}
	
	/**
	 * Get the Port for MCPC
	 * 
	 * @return Port for MCPC
	 * @author Trent Summerlin
	 */
	public static int getPCPort() {
		return Integer.parseInt(Properties.get("server-port-pc").toString());
	}
	
	/**
	 * Get the Port for MCPE
	 * 
	 * @return Port for MCPE
	 * @author Trent Summerlin
	 */
	public static int getPEPort() {
		return Integer.parseInt(Properties.get("server-port-pe").toString());
	}
	
	/**
	 * Get the resource pack for PC players
	 * 
	 * @return PC Resource pack for players to download
	 * @author Trent Summerlin
	 */
	public static String getResourcePack() {
		return Properties.get("resource-pack").toString();
	}
	
	/**
	 * Get server IP for MCPC and MCPE
	 * 
	 * @return Server IP
	 * @author Trent Summerlin
	 */
	public static String getServerIP() {
		return Properties.get("server-ip").toString();
	}
	
	/**
	 * See if the server is online mode for MCPC
	 * 
	 * @return Online Mode for MCPC
	 * @author Trent Summerlin
	 */
	public static boolean getOnlineMode() {
		return Boolean.parseBoolean(Properties.getProperty("online-mode").toString());
	}
	
	/**
	 * Get the amount of RAM the server has left
	 * 
	 * @return Server RAM
	 * @author Trent Summerlin
	 */
	public static long getRam() {
		return Runtime.getRuntime().maxMemory();
	}
	
	// World related
	/**
	 * Get the spawn protection size
	 *
	 * @return Spawn Protection
	 * @author Trent Summerlin
	 */
	public static int getSpawnProtection() {
		return Integer.parseInt(Properties.get("spawn-protection").toString());
	}
	
	/**
	 * See if the server should spawn animals
	 * 
	 * @return Spawn Animals enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getSpawnAnimals() {
		return Boolean.parseBoolean(Properties.get("spawn-animals").toString());
	}
	
	/**
	 * See if the server should spawn mobs
	 * 
	 * @return Spawn Mobs enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getSpawnMobs() {
		return Boolean.parseBoolean(Properties.get("spawn-mobs").toString());
	}
	
	/**
	 * See if the server should spawn NPCs
	 * 
	 * @return Spawn NCPs enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getSpawnNPCs() {
		return Boolean.parseBoolean(Properties.getProperty("spawn-npcs").toString());
	}
	
	/**
	 * See if the server should generate structures
	 * 
	 * @return Structure Generation enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getGenerateStructures() {
		return Boolean.parseBoolean(Properties.get("generate-structures").toString());
	}
	
	/**
	 * See if the server should be in hardcore mode
	 * 
	 * @return Hardcore enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getHardcore() {
		return Boolean.parseBoolean(Properties.get("hardcore").toString());
	}
	
	/**
	 * See if the server should enable the nether
	 * 
	 * @return Nether enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getAllowNether() {
		return Boolean.parseBoolean(Properties.getProperty("allow-nether").toString());
	}
	
	/**
	 * See if the server should enable the end
	 * 
	 * @return End enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getAllowEnd() {
		return Boolean.parseBoolean(Properties.getProperty("allow-end").toString());
	}
	
	/**
	 * Get the difficulty for the server
	 * 
	 * @return Server difficulty
	 * @author Trent Summerlin
	 */
	public static int getDifficulty() {
		return Integer.parseInt(Properties.get("difficulty").toString());
	}
	
	/**
	 * Get the server world generation type
	 * 
	 * @return Server world generation type
	 * @author Trent Summerlin
	 */
	public static GeneratorType getGeneratorSettings() {
		return getGeneratorType(Properties.get("generator-settings").toString());
	}
	
	/**
	 * Get the server world type
	 * 
	 * @return Server world type
	 * @author Trent Summerlin
	 */
	public static LevelType getLevelType() {
		return getLevelType(Properties.get("level-type").toString());
	}
	
	/**
	 * Get the server world seed
	 * 
	 * @return Server world seed
	 * @author Trent Summerlin
	 */
	public static String getLevelSeed() {
		return Properties.getProperty("level-seed").toString();
	}
	
	/**
	 * Get the server world name
	 * 
	 * @return Server world name
	 * @author Trent Summerlin
	 */
	public static String getLevelName() {
		return Properties.getProperty("level-name").toString();
	}
	
	/**
	 * See if the server should auto-save
	 * 
	 * @return Server auto-save state
	 * @author Trent Summerlin
	 */
	public static boolean getAutoSave() {
		return Boolean.parseBoolean(Properties.get("auto-save").toString());
	}
	
	// Other
	
	/**
	 * See if other websites should be able to query the server
	 * 
	 * @return Query Enabled State
	 * @author Trent Summerlin
	 */
	public static boolean getEnableQuery() {
		return Boolean.parseBoolean(Properties.get("enable-query").toString());
	}
	
	/**
	 * See if other applications should be able to control the server with a password
	 * 
	 * @return RCON Enabled state
	 * @author Trent Summerlin
	 */
	public static boolean getEnableRCON() {
		return Boolean.parseBoolean(Properties.get("enable-rcon").toString());
	}
	
	/**
	 * Get the password for RCON
	 * 
	 * @return RCON Password
	 * @author Trent Summerlin
	 */
	public static String getRCONPassword() {
		return Properties.get("rcon.passwrod").toString();
	}
	
	// The following functions down below were taken from a deprecated class
	/**
	 * Used to get the generator type from a String
	 * 
	 * @param Generator Type (in string form)
	 * @return GeneratorType
	 * @author Trent Summerlin
	 */
	protected static GeneratorType getGeneratorType(String GTS) {
		if(GTS.equals("DEFAULT")) return GeneratorType.DEFAULT;
		else if(GTS.equals("FLAT")) return GeneratorType.FLAT;
		return GeneratorType.UNKNOWN;
	}
	
	/**
	 * Used to get the level type form a String
	 * 
	 * @param Level Type (in string form)
	 * @return LevelType
	 * @author Trent Summerlin
	 */
	protected static LevelType getLevelType(String LTS) {
		if(LTS.equals("DEFAULT")) return LevelType.DEFAULT;
		else if(LTS.equals("FLAT")) return LevelType.FLAT;
		return LevelType.UNKNOWN;
	}
	
}
