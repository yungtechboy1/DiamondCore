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

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.diamondcore.Diamond;

/**
 * This class is used for making sure all files needed for the
 * software are in-tact, and if not, create new ones.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class FileCheckup {
	
	int FilesCreated = 0;
	
	public FileCheckup() throws IOException {
		Diamond.logger.info("Starting file checkup!");
		
		/* Start Checkup */
		// Check BannedIPList
		if(!FileList.bannedIPList.exists()) {
			FileList.bannedIPList.createNewFile();
			FileWriter Writer = new FileWriter(FileList.bannedIPList);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.warn("Banned IP list not found! Created a new one");
			FilesCreated++;
		}
		
		// Check BannedPlayerLust
		if(!FileList.bannedPlayerList.exists()) {
			FileList.bannedPlayerList.createNewFile();
			FileWriter Writer = new FileWriter(FileList.bannedPlayerList);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.warn("Banned Player list not found! Created a new one");
			FilesCreated++;
		}
		
		// Check License
		if(!FileList.license.exists()) {
			try {
				Files.copy(this.getClass().getResource("files/LICENSE").openStream(), FileList.license.toPath());
				Diamond.logger.warn("License not found! Created a new one");
			}
			catch(NullPointerException E) {
				Diamond.logger.warn("There was a error copying the LICENSE file!");
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
			Diamond.logger.warn("Operators list not found! Created a new one");
			FilesCreated++;
		}
		
		// Check PlayerFolder
		if(!FileList.playerFolder.exists()) {
			FileList.playerFolder.mkdir();
			Diamond.logger.warn("Player Folder not found! Created a new one");
			FilesCreated++;
		}
		
		// Check PluginFolder
		if(!FileList.pluginFolder.exists()) {
			FileList.pluginFolder.mkdir();
			Diamond.logger.warn("Plugin folder not found! Created a new one");
			FilesCreated++;
		}
		
		// Check Lib Folder
		if(!FileList.libFolder.exists()) {
			FileList.libFolder.mkdir();
			Diamond.logger.warn("Lib folder not found! Created a new one");
			FilesCreated++;
		}
		
		// Check ReadMe file
		if(!FileList.readMe.exists()) {
			try {
				Files.copy(this.getClass().getResource("files/README.md").openStream(), FileList.readMe.toPath());
				Diamond.logger.warn("README.md not found! Created a new one");
			}
			catch(NullPointerException E) {
				Diamond.logger.warn("There was a error copying the README.md file!\nThis could be because the file itself wasnt in the jar.\nMake sure to check if its in there (If you are a dev)");
			}
			FilesCreated++;
		}
		
		// Check ServerLog
		if(!FileList.serverLog.exists()) {
			FileList.serverLog.createNewFile();
			Diamond.logger.warn("Server Log not found! Created a new one");
			FilesCreated++;
		}
		
		// Check ServerProperties
		if(!FileList.serverProperties.exists()) {
			FileList.serverProperties.createNewFile();
			Diamond.logger.warn("Server Properties not found! Created a new one");
			FilesCreated++;
		}
		
		// Check WhiteList
		if(!FileList.whitelist.exists()) {
			FileList.whitelist.createNewFile();
			FileWriter Writer = new FileWriter(FileList.whitelist);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.warn("Whitelist not found! Created a new one");
			FilesCreated++;
		}
		
		// Check WorldFolder
		if(!FileList.worldFolder.exists()) {
			FileList.worldFolder.mkdir();
			Diamond.logger.warn("World folder not found! Created a new one");
			FilesCreated++;
		}
		/* End of Checkup */
		
		if(FilesCreated <= 0) {
			Diamond.logger.info("Finished file checkup with no errors!");
		}
		else {
			Diamond.logger.info("Finished file checkup and created " + FilesCreated + " files");
		}
	}
	
}
