package com.semicolonapps.crypto;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenSslKey {
    private static final List<Integer> SIZES = Arrays.asList(128, 192, 256);
    private byte[] key;
    private byte[] iv;

    public OpenSslKey(byte[] password, byte[] salt, int size) {
        if(!SIZES.contains(size)) {
            throw new RuntimeException("Invalid key size");
        }

        try {
            int rounds = size == 128 ? 2 : 3;
            ArrayList<byte[]> hashes = new ArrayList<byte[]>();
            byte[] data = concat(password, salt);
            hashes.add(md5(data));
            byte[] result = hashes.get(0);

            for(int i = 1; i < rounds; i++) {
                hashes.add(md5(concat(hashes.get(hashes.size() - 1), data)));
                result = concat(result, hashes.get(hashes.size() - 1));
            }

            int keySize = 4 * (size / 32);
            key = Array.copy(result, 0, keySize);
            iv = Array.copy(result, keySize, keySize + 16);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getKey() {
        return key;
    }

    public byte[] getIv() {
        return iv;
    }

    private byte[] concat(byte[] array1, byte[] array2) {
        byte[] newArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);
        return newArray;
    }

    private byte[] md5(byte[] input) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return messageDigest.digest(input);
    }
}
