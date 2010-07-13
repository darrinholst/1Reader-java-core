package com.semicolonapps.onepassword;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoginItemTest extends OnePasswordTestCase {
    @Test
    public void testThatItGetsAllTheInfoFromALoginItem() throws Exception {
        OnePasswordKeychain keychain = getUnlockedKeychain();
        LoginItem login = keychain.logins().get(0);
        assertEquals("login 1", login.getName());
        assertEquals("login1.com", login.getDomain());
        keychain.decrypt(login);
        assertEquals("http://www.login1.com", login.getUrl());
        assertEquals("login1", login.getUsername());
        assertEquals("password1", login.getPassword());
    }
}
