package org.egov.collection.integration.pgi;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AES256Bit {
    private static String res;
    private static byte[] iv = null;

    public AES256Bit() {
    }

    public static String encrypt(String s, SecretKeySpec secretkeyspec) {
        String s1 = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivparameterspec = new IvParameterSpec(iv);
            cipher.init(1, secretkeyspec, ivparameterspec);
            byte[] abyte0 = cipher.doFinal(s.getBytes("UTF-8"));

            s1 = new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(abyte0));
        } catch (Exception var7) {
        }

        return s1;
    }

    public static String decrypt(String s, SecretKeySpec secretkeyspec) {
        String s1 = "";

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivparameterspec = new IvParameterSpec(iv);
            cipher.init(2, secretkeyspec, ivparameterspec);
            byte[] abyte0 = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(s);
            byte[] abyte1 = cipher.doFinal(abyte0);
            s1 = new String(abyte1);
        } catch (Exception var8) {
        }

        return s1;
    }

    public static String asHex(byte[] abyte0) {
        StringBuffer stringbuffer = new StringBuffer();

        for(int i = 0; i < abyte0.length; ++i) {
            stringbuffer.append(Integer.toHexString(256 + (abyte0[i] & 255)).substring(1));
        }

        return stringbuffer.toString();
    }

    public static SecretKeySpec readKeyBytes(String s) {
        SecretKeySpec secretkeyspec = null;
        int i = 0;
        byte[] abyte0 = new byte[16];

        try {
            res = s;
            String s1 = res;
            byte[] abyte1 = s1.getBytes("UTF8");
            byte[] abyte2 = s1.getBytes();

            for(int j = 0; j < 16; ++i) {
                boolean flag1 = false;

                while(i < abyte1.length) {
                    if (j == i) {
                        flag1 = true;
                        break;
                    }
                }

                if (flag1) {
                    abyte0[j] = abyte1[j];
                }

                ++j;
            }

            iv = abyte0;
            secretkeyspec = new SecretKeySpec(abyte0, "AES");
        } catch (Exception var9) {
        }

        return secretkeyspec;
    }

    public static String byteToHex(byte byte0) {
        char[] ac = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ac1 = new char[]{ac[byte0 >> 4 & 15], ac[byte0 & 15]};
        return new String(ac1);
    }

    public static String generateNewKey() {
        String newKey = null;

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(256);
            SecretKey skey = kgen.generateKey();
            byte[] raw = skey.getEncoded();
            newKey = new String(org.apache.tomcat.util.codec.binary.Base64.encodeBase64(raw));
            newKey = newKey.replace("+", "/");
        } catch (Exception var4) {
        }

        return newKey;
    }
}
