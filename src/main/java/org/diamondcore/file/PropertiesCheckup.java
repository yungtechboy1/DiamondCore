/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package org.diamondcore.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import org.diamondcore.Diamond;

/**
 * This class is used for making sure all the server-properties
 * are in-tact, and will generate new ones if necessary.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class PropertiesCheckup {
	
	Properties Properties = new Properties();
	int PropertiesAdded = 0;
	
	public PropertiesCheckup() throws IOException {
		Diamond.logger.info("Starting Server Properties checkup!");
		
		/* Start Properties File Checkup */
		Properties.load(new FileInputStream(FileList.serverProperties));
		
		// Check whitelist
		if(Properties.get("whitelist") == null) {
			Properties.setProperty("whitelist", "false");
			PropertiesAdded++;
		}
		
		if(Properties.get("announce-player-achievements") == null) {
			Properties.setProperty("announce-player-achievements", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("pvp") == null) {
			Properties.setProperty("pvp", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("max-players") == null) {
			Properties.setProperty("max-players", "20");
			PropertiesAdded++;
		}
		
		if(Properties.get("player-idle-timeout") == null) {
			Properties.setProperty("player-idle-timeout", "0");
			PropertiesAdded++;
		}
		
		if(Properties.get("op-permission-level") == null) {
			Properties.setProperty("op-permission-level", "4");
			PropertiesAdded++;
		}
		
		if(Properties.get("allow-flight") == null) {
			Properties.setProperty("allow-flight", "false");
			PropertiesAdded++;
		}
		
		
		if(Properties.get("motd-pe") == null) {
			Properties.setProperty("motd-pe", "A Minecraft: Pocket Edition server");
			PropertiesAdded++;
		}
		
		if(Properties.get("motd-pc") == null) {
			Properties.setProperty("motd-pc", "A Minecraft server");
			PropertiesAdded++;
		}
		
		
		if(Properties.get("force-gamemode") == null) {
			Properties.setProperty("force-gamemode", "false");
			PropertiesAdded++;
		}
		
		if(Properties.get("gamemode") == null) {
			Properties.setProperty("gamemode", "0");
			PropertiesAdded++;
		}
		
		if(Properties.get("server-port-pc") == null) {
			Properties.setProperty("server-port-pc", "25565");
			PropertiesAdded++;
		}
		
		if(Properties.get("server-port-pe") == null) {
			Properties.setProperty("server-port-pe", "19132");
			PropertiesAdded++;
		}
		
		if(Properties.get("resource-pack") == null) {
			Properties.setProperty("resource-pack", "");
			PropertiesAdded++;
		}
		
		if(Properties.get("server-ip") == null) {
			Properties.setProperty("server-ip", "");
			PropertiesAdded++;
		}
		
		if(Properties.get("online-mode") == null) {
			Properties.setProperty("online-mode", "true");
			PropertiesAdded++;
		}
		
		// World related
		if(Properties.get("spawn-protection") == null) {
			Properties.setProperty("spawn-protection", "16");
			PropertiesAdded++;
		}
		
		if(Properties.get("spawn-animals") == null) {
			Properties.setProperty("spawn-animals", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("spawn-mobs") == null) {
			Properties.setProperty("spawn-mobs", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("spawn-npcs") == null) {
			Properties.setProperty("spawn-npcs", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("generate-structures") == null) {
			Properties.setProperty("generate-structures", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("hardcore") == null) {
			Properties.setProperty("hardcore", "false");
			PropertiesAdded++;
		}
		
		if(Properties.get("allow-nether") == null) {
			Properties.setProperty("allow-nether", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("allow-end") == null) {
			Properties.setProperty("allow-end", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("difficulty") == null) {
			Properties.setProperty("difficulty", "0");
			PropertiesAdded++;
		}
		
		if(Properties.get("generator-settings") == null) {
			Properties.setProperty("generator-settings", "");
			PropertiesAdded++;
		}
		
		if(Properties.get("level-type") == null) {
			Properties.setProperty("level-type", "DEFAULT");
			PropertiesAdded++;
		}
		
		if(Properties.get("level-seed") == null) {
			Properties.setProperty("level-seed", "");
			PropertiesAdded++;
		}
		
		if(Properties.get("level-name") == null) {
			Properties.setProperty("level-name", "world");
			PropertiesAdded++;
		}
		
		if(Properties.get("auto-save") == null) {
			Properties.setProperty("auto-save", "true");
			PropertiesAdded++;
		}
		
		if(Properties.get("enable-query") == null) {
			Properties.setProperty("enable-query", "false");
			PropertiesAdded++;
		}
		
		if(Properties.get("enable-rcon") == null) {
			Properties.setProperty("enable-rcon", "false");
			PropertiesAdded++;
		}
		
		if(Boolean.parseBoolean(Properties.get("enable-rcon").toString()) == true) {
			Properties.setProperty("rcon.password", new BigInteger(130, new SecureRandom()).toString(32));
			PropertiesAdded++;
		}
		
		Properties.store(new FileOutputStream(FileList.serverProperties), null);
		
		/* End of checkup */
		if(PropertiesAdded <= 0) Diamond.logger.info("Finished Server Properties checkup with no errors!");
		else Diamond.logger.info("Finished Server Properties checkup and added " + PropertiesAdded + " missing properties");
	}
	
	
	
}
