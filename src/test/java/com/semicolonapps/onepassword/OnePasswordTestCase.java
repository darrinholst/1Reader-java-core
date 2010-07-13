package com.semicolonapps.onepassword;

import java.io.File;
import java.net.URL;

public abstract class OnePasswordTestCase {
    protected static final String PASSWORD = "password";

    protected OnePasswordKeychain getKeychain() {
        URL url = getClass().getClassLoader().getResource("1Password.agilekeychain");
        return new OnePasswordKeychain(new File(url.getPath()));
    }

    protected OnePasswordKeychain getUnlockedKeychain() {
        OnePasswordKeychain keychain = getKeychain();
        keychain.unlock(PASSWORD);
        return keychain;
    }
}
