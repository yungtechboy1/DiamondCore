package net.trenterprises.diamondcore.cross;

import java.io.FileReader;

import net.trenterprises.diamondcore.cross.file.FileList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public final class Player {
	
	protected final String username;
	
	public Player(String username) {
		this.username = username;
	}
	
	public boolean isBanned() {
		try {
			JSONObject BanObject = (JSONObject) new JSONParser().parse(new FileReader(FileList.bannedPlayerList));
			return BanObject.containsKey(this.username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
