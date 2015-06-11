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
	
	int filesCreated = 0;
	
	public FileCheckup() throws IOException {
		Diamond.logger.translate("file.checkupStart");
		
		/* Start Checkup */
		// Check BannedIPList
		if(!FileList.bannedIPList.exists()) {
			FileList.bannedIPList.createNewFile();
			FileWriter Writer = new FileWriter(FileList.bannedIPList);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.translatewarn("file.fileMissing", FileList.bannedIPList.getName());
			filesCreated++;
		}
		
		// Check BannedPlayerLust
		if(!FileList.bannedPlayerList.exists()) {
			FileList.bannedPlayerList.createNewFile();
			FileWriter Writer = new FileWriter(FileList.bannedPlayerList);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.translatewarn("file.fileMissing", FileList.bannedPlayerList.getName());
			filesCreated++;
		}
		
		// Check License
		if(!FileList.license.exists()) {
			try {
				Files.copy(this.getClass().getResource("files/LICENSE").openStream(), FileList.license.toPath());
				Diamond.logger.translatewarn("file.fileMissing", "LICENSE");
			}
			catch(NullPointerException E) {
				Diamond.logger.translatewarn("file.copyError", "LICENSE");
			}
			filesCreated++;
		}
		
		// Check operator List
		if(!FileList.operators.exists()) {
			FileList.operators.createNewFile();
			FileWriter Writer = new FileWriter(FileList.operators);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.translatewarn("file.fileMissing", FileList.operators.getName());
			filesCreated++;
		}
		
		// Check PlayerFolder
		if(!FileList.playerFolder.exists()) {
			FileList.playerFolder.mkdir();
			Diamond.logger.translatewarn("file.folderMissing", FileList.playerFolder.getName());
			filesCreated++;
		}
		
		// Check PluginFolder
		if(!FileList.pluginFolder.exists()) {
			FileList.pluginFolder.mkdir();
			Diamond.logger.translatewarn("file.folderMissing", FileList.pluginFolder.getName());
			filesCreated++;
		}
		
		// Check Lib Folder
		if(!FileList.libFolder.exists()) {
			FileList.libFolder.mkdir();
			Diamond.logger.translatewarn("file.folderMissing", FileList.libFolder.getName());
			filesCreated++;
		}
		
		// Check ReadMe file
		if(!FileList.readMe.exists()) {
			try {
				Files.copy(this.getClass().getResource("files/README.md").openStream(), FileList.readMe.toPath());
				Diamond.logger.translatewarn("file.fileMissing", "README.md");
			}
			catch(NullPointerException E) {
				Diamond.logger.translatewarn("file.copyError", "README.md");
			}
			filesCreated++;
		}
		
		// Check ServerLog
		if(!FileList.serverLog.exists()) {
			FileList.serverLog.createNewFile();
			Diamond.logger.translatewarn("file.fileMissing", "server.log");
			filesCreated++;
		}
		
		// Check ServerProperties
		if(!FileList.serverProperties.exists()) {
			FileList.serverProperties.createNewFile();
			Diamond.logger.translatewarn("file.fileMissing", "server.properties");
			filesCreated++;
		}
		
		// Check WhiteList
		if(!FileList.whitelist.exists()) {
			FileList.whitelist.createNewFile();
			FileWriter Writer = new FileWriter(FileList.whitelist);
			Writer.write("{}");
			Writer.flush();
			Writer.close();
			Diamond.logger.translatewarn("file.folderMissing", "whitelist");
			filesCreated++;
		}
		
		// Check WorldFolder
		if(!FileList.worldFolder.exists()) {
			FileList.worldFolder.mkdir();
			Diamond.logger.translatewarn("file.folderMissing", "world");
			filesCreated++;
		}
		/* End of Checkup */
		
		if(filesCreated == 0)
			Diamond.logger.translate("file.checkupFinishNoError");
		else
			Diamond.logger.translate("file.checkupFinishError", filesCreated);
	}
	
}
