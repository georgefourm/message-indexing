package com.georgesdoe;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEncrypter {
	
	Cipher c;
	SecretKeySpec k;
	public byte[] IV;
	
	public AESEncrypter(String key){
		try {
			c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			k= new SecretKeySpec(key.getBytes(), "AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.err.println("Cipher error: "+e.getMessage());
		}
	}

	/**
	 * Encrypts a string using AES encryption with the currently initialized key
	 * @param plaintext The String to be encrypted
	 * @return The encrypted Array of bytes
	 * @throws Exception
	 */
	public byte[] encryptString(String plaintext) throws Exception{
		c.init(Cipher.ENCRYPT_MODE, k);
		IV=c.getIV();
		byte[] encryptedData = c.doFinal(plaintext.getBytes());
		return encryptedData;
	}
	
	/**
	 * Decrypts a  <b>UTF-8 encoded</b> String from an array of bytes
	 * @param ciphertext The encrypted byte array
	 * @param iv The current Initialization Vector
	 * @return The decrypted String
	 */
	
	public String decryptString(byte[] ciphertext,byte[] iv){
		String plaintext = null;
		try {
			c.init(Cipher.DECRYPT_MODE, k, new IvParameterSpec(iv));
			plaintext=new String(c.doFinal(ciphertext),"UTF-8");
		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.err.println("Decrypting exception: "+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.err.println("String Encoding Exception: "+e.getMessage());
		}
		return plaintext;
	}
}
