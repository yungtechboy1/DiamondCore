/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.cross.file;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;

/**
 * This class is used for making sure all files needed for the
 * software are in-tact, and if not, create new ones.
 * 
 * @author Trent Summerlin
 * @version 1.0
 */
public class FileCheckup {
	
	DiamondLogger Logger = new Log4j2Logger("DiamondCore");
	
	int FilesCreated = 0;
	
	public FileCheckup() throws IOException {
		Logger.info("Starting file checkup!");
		
		/* Start Checkup */
		// Check BannedIPList
		if(!FileList.bannedIPList.exists()) {
			FileList.bannedIPList.createNewFile();
			FileWriter Writer = new FileWriter(FileList.bannedIPList);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Logger.warn("Banned IP list not found! Created a new one");
			FilesCreated++;
		}
		
		// Check BannedPlayerLust
		if(!FileList.bannedPlayerList.exists()) {
			FileList.bannedPlayerList.createNewFile();
			FileWriter Writer = new FileWriter(FileList.bannedPlayerList);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Logger.warn("Banned Player list not found! Created a new one");
			FilesCreated++;
		}
		
		// Check License
		if(!FileList.license.exists()) {
			try {
				Files.copy(this.getClass().getResourceAsStream("files/LICENSE"), FileList.license.toPath());
				Logger.warn("License not found! Created a new one");
			}
			catch(NullPointerException E) {
				Logger.warn("There was a error copying the LICENSE file!\nThis could be because the file itselft wasnt in the jar.\nMake sure to check if its in there (If you are a dev)");
			}
			FilesCreated++;
		}
		
		// Check operator List
		if(!FileList.operators.exists()) {
			FileList.operators.createNewFile();
			FileWriter Writer = new FileWriter(FileList.operators);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Logger.warn("Operators list not found! Created a new one");
			FilesCreated++;
		}
		
		// Check PlayerFolder
		if(!FileList.playerFolder.exists()) {
			FileList.playerFolder.mkdir();
			Logger.warn("Player Folder not found! Created a new one");
			FilesCreated++;
		}
		
		// Check PluginFolder
		if(!FileList.pluginFolder.exists()) {
			FileList.pluginFolder.mkdir();
			Logger.warn("Plugin folder not found! Created a new one");
			FilesCreated++;
		}
		
		// Check Lib Folder
		if(!FileList.libFolder.exists()) {
			FileList.libFolder.mkdir();
			Logger.warn("Lib folder not found! Created a new one");
			FilesCreated++;
		}
		
		// Check ReadMe file
		if(!FileList.readMe.exists()) {
			try {
				Files.copy(this.getClass().getResourceAsStream("files/README.md"), FileList.readMe.toPath());
				Logger.warn("README.md not found! Created a new one");
			}
			catch(NullPointerException E) {
				Logger.warn("There was a error copying the README.md file!\nThis could be because the file itself wasnt in the jar.\nMake sure to check if its in there (If you are a dev)");
			}
			FilesCreated++;
		}
		
		// Check ServerLog
		if(!FileList.serverLog.exists()) {
			FileList.serverLog.createNewFile();
			Logger.warn("Server Log not found! Created a new one");
			FilesCreated++;
		}
		
		// Check ServerProperties
		if(!FileList.serverProperties.exists()) {
			FileList.serverProperties.createNewFile();
			Logger.warn("Server Properties not found! Created a new one");
			FilesCreated++;
		}
		
		// Check WhiteList
		if(!FileList.whitelist.exists()) {
			FileList.whitelist.createNewFile();
			FileWriter Writer = new FileWriter(FileList.whitelist);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Logger.warn("Whitelist not found! Created a new one");
			FilesCreated++;
		}
		
		// Check WorldFolder
		if(!FileList.worldFolder.exists()) {
			FileList.worldFolder.mkdir();
			Logger.warn("World folder not found! Created a new one");
			FilesCreated++;
		}
		/* End of Checkup */
		
		if(FilesCreated <= 0) {
			Logger.info("Finished file checkup with no errors!");
		}
		else {
			Logger.info("Finished file checkup and created " + FilesCreated + " files");
		}
	}
	
}
