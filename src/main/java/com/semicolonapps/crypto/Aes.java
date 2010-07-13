package com.semicolonapps.crypto;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes {
    private static byte[] ZERO_IV = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public byte[] decrypt(String data, byte[] key) {
        try {
            return decrypt(Base64.decodeBase64(data.getBytes()), key);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] binary, byte[] key) {
        try {
            byte[] iv = ZERO_IV;

            if(isSalted(binary)) {
                byte[] salt = Array.copy(binary, 8, 16);
                binary = Array.copy(binary, 16, binary.length);
                OpenSslKey openSslKey = new OpenSslKey(key, salt, 128);
                key = openSslKey.getKey();
                iv = openSslKey.getIv();
            }
            else {
                //TODO md5 key
            }

            return decrypt(binary, key, iv);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] decrypt(byte[] binary, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        return cipher.doFinal(binary);
    }

    private static boolean isSalted(byte[] bytes) {
        return ("Salted__".equals(new String(Array.copy(bytes, 0, 8))));
    }
}
