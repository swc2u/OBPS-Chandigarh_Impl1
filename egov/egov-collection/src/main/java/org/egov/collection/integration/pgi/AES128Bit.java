package org.egov.collection.integration.pgi;

import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES128Bit {
	private static final String characterEncoding       = "UTF-8";
    private static final String cipherTransformation    = "AES/CBC/PKCS5PADDING";
    private static final String aesEncryptionAlgorithem = "AES";
    
    public static String encrypt(String plainText, String encryptionKey) {
        String encryptedText = "";
        try {
            Cipher cipher   = Cipher.getInstance(cipherTransformation);
            byte[] key      = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivparameterspec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF8"));
            Base64.Encoder encoder = Base64.getEncoder();
            encryptedText = encoder.encodeToString(cipherText);

        } catch (Exception E) {
             System.err.println("Encrypt Exception : "+E.getMessage());
        }
        return encryptedText;
    }

    /**
     * Method For Get encryptedText and Decrypted provided String
     * @param encryptedText
     * @return decryptedText
     */
    public static String decrypt(String encryptedText, String encryptionKey) {
        String decryptedText = "";
        try {
            Cipher cipher = Cipher.getInstance(cipherTransformation);
            byte[] key = encryptionKey.getBytes(characterEncoding);
            SecretKeySpec secretKey = new SecretKeySpec(key, aesEncryptionAlgorithem);
            IvParameterSpec ivparameterspec = new IvParameterSpec(key);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivparameterspec);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] cipherText = decoder.decode(encryptedText.getBytes("UTF8"));
            decryptedText = new String(cipher.doFinal(cipherText), "UTF-8");

        } catch (Exception E) {
            System.err.println("decrypt Exception : "+E.getMessage());
        }
        return decryptedText;
    }
    
    public static void main(String[] args) {
    	String secretKey = "Adfhj#$@56677745";
		
		String encryptedEmpCode = AES128Bit.encrypt("1975010001Z", secretKey);
		System.out.println("encryptedEmpCode :: " + encryptedEmpCode);
		String encryptedMonth = AES128Bit.encrypt("03", secretKey);
		System.out.println("encryptedMonth :: " + encryptedMonth);
		String encryptedYear = AES128Bit.encrypt("2018", secretKey);
		System.out.println("encryptedYear :: " + encryptedYear);
		
		String dycriptedEmpCode = AES128Bit.decrypt(encryptedEmpCode, secretKey);
		System.out.println("dycriptedEmpCode :: " + dycriptedEmpCode);
		String dycriptedMonth = AES128Bit.decrypt(encryptedMonth, secretKey);
		System.out.println("dycriptedMonth :: " + dycriptedMonth);
		String dycriptedYear = AES128Bit.decrypt(encryptedYear, secretKey);
		System.out.println("dycriptedYear :: " + dycriptedYear);       
    }
}
