package net.trenterprises.diamondcore.cross.utils;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

public class RSA {
	
	public final KeyPair pair;
	public final byte[] publicKey;
	public final byte[] privateKey;
	
	public RSA() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		this.pair = keyGen.genKeyPair();
		
        //Create ciper
        Cipher cipher = Cipher.getInstance("RSA");
        
        // Generate public ciper
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
        byte[] cipherPublic = cipher.doFinal("0123456789".getBytes());
        
        // Generate private ciper
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPrivate());
        byte[] cipherPrivate = cipher.doFinal("abcdefghik".getBytes());
        
        this.publicKey = (new Hex().encode(cipherPublic));
		this.privateKey = (new Hex().encode(cipherPrivate));
	}
	
}

enum KeyType {
	PUBLIC, PRIVATE
}
