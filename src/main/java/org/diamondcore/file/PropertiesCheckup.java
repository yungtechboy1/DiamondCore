/**
*  ____  _                                 _  ____
* |  _ \(_) __ _ _ __ ___   ___  _ __   __| |/ ___|___  _ __ ___
* | | | | |/ _` | '_ ` _ \ / _ \| '_ \ / _` | |   / _ \| '__/ _ \
* | |_| | | (_| | | | | | | (_) | | | | (_| | |__| (_) | | |  __/
* |____/|_|\__,_|_| |_| |_|\___/|_| |_|\__,_|\____\___/|_|  \___|     
*
*/

package org.diamondcore.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Properties;

import org.diamondcore.Diamond;
import org.diamondcore.lang.Lang;

/**
 * This class is used for making sure all the server-properties
 * are in-tact, and will generate new ones if necessary.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class PropertiesCheckup {
	
	private Properties properties = new Properties();
	private int propertiesAdded = 0;
	
	public PropertiesCheckup() throws IOException {
		Diamond.lang.info("properties.checkupStart");
		
		/* Start Properties File Checkup */
		properties.load(new FileInputStream(FileList.serverProperties));
		
		// Check whitelist
		if(properties.get("whitelist") == null) {
			properties.setProperty("whitelist", "false");
			propertiesAdded++;
		}
		
		if(properties.get("announce-player-achievements") == null) {
			properties.setProperty("announce-player-achievements", "true");
			propertiesAdded++;
		}
		
		if(properties.get("pvp") == null) {
			properties.setProperty("pvp", "true");
			propertiesAdded++;
		}
		
		if(properties.get("max-players") == null) {
			properties.setProperty("max-players", "20");
			propertiesAdded++;
		}
		
		if(properties.get("player-idle-timeout") == null) {
			properties.setProperty("player-idle-timeout", "0");
			propertiesAdded++;
		}
		
		if(properties.get("op-permission-level") == null) {
			properties.setProperty("op-permission-level", "4");
			propertiesAdded++;
		}
		
		if(properties.get("allow-flight") == null) {
			properties.setProperty("allow-flight", "false");
			propertiesAdded++;
		}
		
		
		if(properties.get("motd-pe") == null) {
			properties.setProperty("motd-pe", Lang.get("motd.pocket"));
			propertiesAdded++;
		}
		
		if(properties.get("motd-pc") == null) {
			properties.setProperty("motd-pc", Lang.get("motd.desktop"));
			propertiesAdded++;
		}
		
		
		if(properties.get("force-gamemode") == null) {
			properties.setProperty("force-gamemode", "false");
			propertiesAdded++;
		}
		
		if(properties.get("gamemode") == null) {
			properties.setProperty("gamemode", "0");
			propertiesAdded++;
		}
		
		if(properties.get("server-port-pc") == null) {
			properties.setProperty("server-port-pc", "25565");
			propertiesAdded++;
		}
		
		if(properties.get("server-port-pe") == null) {
			properties.setProperty("server-port-pe", "19132");
			propertiesAdded++;
		}
		
		if(properties.get("resource-pack") == null) {
			properties.setProperty("resource-pack", "");
			propertiesAdded++;
		}
		
		if(properties.get("server-ip") == null) {
			properties.setProperty("server-ip", "");
			propertiesAdded++;
		}
		
		if(properties.get("online-mode") == null) {
			properties.setProperty("online-mode", "true");
			propertiesAdded++;
		}
		
		// World related
		if(properties.get("spawn-protection") == null) {
			properties.setProperty("spawn-protection", "16");
			propertiesAdded++;
		}
		
		if(properties.get("spawn-animals") == null) {
			properties.setProperty("spawn-animals", "true");
			propertiesAdded++;
		}
		
		if(properties.get("spawn-mobs") == null) {
			properties.setProperty("spawn-mobs", "true");
			propertiesAdded++;
		}
		
		if(properties.get("spawn-npcs") == null) {
			properties.setProperty("spawn-npcs", "true");
			propertiesAdded++;
		}
		
		if(properties.get("generate-structures") == null) {
			properties.setProperty("generate-structures", "true");
			propertiesAdded++;
		}
		
		if(properties.get("hardcore") == null) {
			properties.setProperty("hardcore", "false");
			propertiesAdded++;
		}
		
		if(properties.get("allow-nether") == null) {
			properties.setProperty("allow-nether", "true");
			propertiesAdded++;
		}
		
		if(properties.get("allow-end") == null) {
			properties.setProperty("allow-end", "true");
			propertiesAdded++;
		}
		
		if(properties.get("difficulty") == null) {
			properties.setProperty("difficulty", "0");
			propertiesAdded++;
		}
		
		if(properties.get("generator-settings") == null) {
			properties.setProperty("generator-settings", "");
			propertiesAdded++;
		}
		
		if(properties.get("level-type") == null) {
			properties.setProperty("level-type", Lang.get("world.default"));
			propertiesAdded++;
		}
		
		if(properties.get("level-seed") == null) {
			properties.setProperty("level-seed", "");
			propertiesAdded++;
		}
		
		if(properties.get("level-name") == null) {
			properties.setProperty("level-name", Lang.get("settings.defaultWorld"));
			propertiesAdded++;
		}
		
		if(properties.get("auto-save") == null) {
			properties.setProperty("auto-save", "true");
			propertiesAdded++;
		}
		
		if(properties.get("enable-query") == null) {
			properties.setProperty("enable-query", "false");
			propertiesAdded++;
		}
		
		if(properties.get("enable-rcon") == null) {
			properties.setProperty("enable-rcon", "false");
			propertiesAdded++;
		}
		
		if(Boolean.parseBoolean(properties.get("enable-rcon").toString()) == true) {
			properties.setProperty("rcon.password", new BigInteger(130, new SecureRandom()).toString(32));
			propertiesAdded++;
		}
		
		properties.store(new FileOutputStream(FileList.serverProperties), null);
		
		/* End of checkup */
		if(propertiesAdded == 0)
			Diamond.lang.info("properties.checkupFinishNoError");
		else
			Diamond.lang.info("properties.checkupFinishError", propertiesAdded);
	}
	
	
	
}
