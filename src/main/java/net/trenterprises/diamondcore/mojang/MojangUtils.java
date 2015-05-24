package net.trenterprises.diamondcore.mojang;

import java.net.URL;

import net.trenterprises.diamondcore.cross.file.FileUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MojangUtils {
	
	public static String getUUID(String username) {
		try {
			String result = FileUtils.readFromURL(new URL(MojangURL.accountInfo + username));
			JSONObject info = (JSONObject) new JSONParser().parse(result);
			return info.get("id").toString();
		} catch(Exception e) {
			return null;
		}
	}
	
	public static String getUsername(String uuid) {
		try {
			String result = FileUtils.readFromURL(new URL(MojangURL.profileInfo + uuid));
			JSONObject info = (JSONObject) new JSONParser().parse(result);
			return info.get("name").toString();
		} catch(Exception e) {
			return null;
		}
	}
	
}
