package net.trenterprises.diamondcore.cross.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSA {
	
	public final KeyPair pair;
	public final byte[] publicKey;
	public final byte[] privateKey;
	
	public RSA() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		this.pair = keyGen.genKeyPair();
		this.publicKey = pair.getPublic().getEncoded();
		this.privateKey = pair.getPrivate().getEncoded();
	}
	
}

enum KeyType {
	PUBLIC, PRIVATE
}
