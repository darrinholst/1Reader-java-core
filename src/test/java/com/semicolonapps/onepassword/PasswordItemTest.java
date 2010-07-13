package com.semicolonapps.onepassword;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordItemTest extends OnePasswordTestCase {
    @Test
    public void testThatItGetsAllTheInfoFromAPasswordItem() throws Exception {
        OnePasswordKeychain keychain = getUnlockedKeychain();
        PasswordItem item = keychain.passwords().get(0);
        assertEquals("Boxcar", item.getName());
        keychain.decrypt(item);
        assertEquals("buSXuPv0M1AWYFOu", item.getFields().get("password"));
    }
}
