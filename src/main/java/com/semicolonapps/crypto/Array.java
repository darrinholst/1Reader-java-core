package com.semicolonapps.crypto;

public class Array {
    public static byte[] copy(byte[] original, int from, int to) {
        byte[] copy = new byte[to - from];
        System.arraycopy(original, from, copy, 0, copy.length);
        return copy;
    }
}
