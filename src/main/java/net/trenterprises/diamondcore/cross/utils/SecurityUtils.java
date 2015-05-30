package net.trenterprises.diamondcore.cross.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import net.trenterprises.diamondcore.cross.Diamond;

/**
 * Utility class for performing encrypted authentication
 * 
 * @author GlowstoneTeam
 * @see https://raw.githubusercontent.com/GlowstoneMC/Glowstone/master/src/main/java/net/glowstone/util/SecurityUtils.java
 */
public final class SecurityUtils {

	private static SecureRandom random = new SecureRandom();

	private SecurityUtils() {}

	/**
	 * Generate a RSA key pair
	 */
	public static KeyPair generateKeyPair() {
		KeyPair keyPair = null;
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(1024);

			keyPair = generator.generateKeyPair();
		} catch (NoSuchAlgorithmException ex) {
			Diamond.logger.err("Unable to generate RSA key pair");
		}
		return keyPair;
	}

	/**
	 * Generate a random verify token
	 */
	public static byte[] generateVerifyToken() {
		byte[] token = new byte[4];
		random.nextBytes(token);
		return token;
	}

	/**
	 * Generates an X509 formatted key used in authentication
	 */
	public static Key generateX509Key(Key base) {
		Key key = null;
		try {
			X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(base.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			key = keyFactory.generatePublic(encodedKeySpec);
		} catch (Exception ex) {
			Diamond.logger.err("Unable to generate X509 encoded key");
		}
		return key;
	}
}
