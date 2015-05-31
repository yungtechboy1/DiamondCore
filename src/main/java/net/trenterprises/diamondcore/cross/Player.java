/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        
 
*/

package net.trenterprises.diamondcore.cross;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.trenterprises.diamondcore.cross.file.FileList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class Player {
	
	protected final String uuid;
	
	// Ban list
	protected JSONObject banObject = null;
	
	public Player(String uuid) throws FileNotFoundException, IOException, ParseException {
		this.uuid = uuid;
		
		// Load everything
		this.banObject = (JSONObject) new JSONParser().parse(new FileReader(FileList.bannedPlayerList));
	}
	
	public boolean isBanned() {
		return banObject.containsKey(uuid);
	}
	
}
