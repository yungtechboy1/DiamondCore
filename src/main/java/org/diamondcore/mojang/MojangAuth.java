/*
 _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ _______ 
|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|\     /|
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
| |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | | |   | |
| |D  | | |i  | | |a  | | |m  | | |o  | | |n  | | |d  | | |C  | | |o  | | |r  | | |e  | |
| +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ | +---+ |
|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|/_____\|
                                                                                                 
*/

package org.diamondcore.mojang;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Used for ease in player authentication
 * 
 * @author Trent Summerlin
 * @version 0.1.0-SNAPSHOT
 */
public final class MojangAuth {
	
	private MojangAuth() {}
	
	/**
	 * Used to get the JSON Object for the player data,
	 * <br>
	 * such as the UUID, Skin Blob, etc.
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return JSONObject of player data
	 */
	public static JSONObject getPlayerData(String username, String serverHash) {
		try {
			URL login = new URL(MojangURL.hasJoined + ("username=" + username) + ("&serverId=" + serverHash));
			String loginData = FileUtils.readFromURL(login);
			return (JSONObject) new JSONParser().parse(loginData);
		} catch(IOException | ParseException e) {
			return null;
		}
	}
	
	/**
	 * Used to decrypt the shared secret sent by Minecraft
	 *  
	 * @author Trent Summerlin
	 * @version 1.0
	 * @return Decrypted shared secret key
	 */
	public static SecretKeySpec decryptSecret(KeyPair pair, byte[] sharedSecret) {
		try {
			Cipher secretCipher = Cipher.getInstance("RSA");
			secretCipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
			return new SecretKeySpec(secretCipher.doFinal(sharedSecret), "AES");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Used to decrypt the verify token sent by Minecraft
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param pair
	 * @param verifyToken
	 * @return Decrypted verify token
	 */
	public static byte[] decryptVerify(KeyPair pair, byte[] verifyToken) {
		try {
			Cipher secretCipher = Cipher.getInstance("RSA");
			secretCipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
			return secretCipher.doFinal(verifyToken);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Used to get the server hash from the shared secret and public key
	 * 
	 * @author Trent Summerlin
	 * @version 1.0
	 * @param sharedSecret
	 * @param publicKey
	 * @return Server hash for authentication
	 */
	public static String getServerHash(SecretKey sharedSecret, PublicKey publicKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(" ".getBytes());
            digest.update(sharedSecret.getEncoded());
            digest.update(publicKey.getEncoded());
            return new BigInteger(digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * Used to insert the dashes back into a UUID string
	 * 
	 * @author Quackster
	 * @version 1.0
	 */
	public static String insertDashUUID(String uuid) {
		StringBuffer sb = new StringBuffer(uuid);
		sb.insert(8, "-");
		 
		sb = new StringBuffer(sb.toString());
		sb.insert(13, "-");
		 
		sb = new StringBuffer(sb.toString());
		sb.insert(18, "-");
		 
		sb = new StringBuffer(sb.toString());
		sb.insert(23, "-");
		 
		return sb.toString();
	}
}
