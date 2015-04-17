/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|                                                                                                        

*/

package net.trenterprises.diamondcore.mojang;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.trenterprises.diamondcore.cross.logging.DiamondLogger;
import net.trenterprises.diamondcore.cross.logging.Log4j2Logger;
import net.trenterprises.diamondcore.cross.network.NetworkUtils;
import net.trenterprises.diamondcore.mojang.exception.InvalidMojangAccountException;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * This class is used for doing Mojang-Account related things
 * such as seeing if the account is legitimate, or if it even
 * exists.
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public class MojangAccount {
	
	// Logger
	@SuppressWarnings("unused")
	private DiamondLogger Logger = new Log4j2Logger("Mojang");
	
	// URL's
	private String BaseURL = "https://sessionserver.mojang.com/session/minecraft/profile/";
	private String AccountURL = null;
	
	// Error's
	private String NotFoundBase = "Not Found";
	private String NotFoundErrorMessage = "The server has not found anything matching the request URI";
	
	public MojangAccount(String UsernameUUID) throws MalformedURLException, IOException, InvalidMojangAccountException {
		AccountURL = BaseURL + UsernameUUID;
		
		String AccountJSON = NetworkUtils.readFromURL(new URL(AccountURL));
		System.out.println(AccountJSON);
		Object AccountParser = JSONValue.parse(AccountJSON);
		JSONObject Account = (JSONObject) AccountParser;
		
		System.out.println(AccountJSON);
		if(Account.get("error") != null) {
			if(Account.get("error") == NotFoundBase) {
				if(Account.get("errormessage") != null) {
					if(Account.get("errormessage") == NotFoundErrorMessage) {
						throw new InvalidMojangAccountException(UsernameUUID);
					}
				}
			}
		}
	}
	
	public String getUUID() throws MalformedURLException, IOException {
		String AccountJSON = NetworkUtils.readFromURL(new URL(AccountURL));
		Object AccountParser = JSONValue.parse(AccountJSON);
		JSONObject Account = (JSONObject) AccountParser;
		return Account.get("id").toString();
	}
	
	public String getUsername() throws MalformedURLException, IOException {
		String AccountJSON = NetworkUtils.readFromURL(new URL(AccountURL));
		Object AccountParser = JSONValue.parse(AccountJSON);
		JSONObject Account = (JSONObject) AccountParser;
		return Account.get("name").toString();
	}
	
	public String isRealAccount(String PasswordEntered) throws MalformedURLException, IOException {
		HttpURLConnection client;
		client = (HttpURLConnection) new URL(AccountURL).openConnection();
		client.setRequestMethod("POST");
		client.setUseCaches(false);
		client.setDoInput(true);
		client.setDoOutput(true);
		
		client.setRequestProperty("authenticate", "{\"agent\": {\"name\": \"Minecraft\",\"version\": 1},\"username\": \"mojang account name\",\"password\": \"mojang account password\",\"clientToken\": \"client identifier\"}");
		
		DataOutputStream wr = new DataOutputStream(client.getOutputStream());
		wr.writeBytes("authenticate");
		wr.flush();
		wr.close();
		
		InputStream is = client.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new MojangAccount("SuperstarGamer").isRealAccount("ShootStopGun89"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMojangAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
