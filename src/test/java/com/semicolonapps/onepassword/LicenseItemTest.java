package com.semicolonapps.onepassword;

import org.junit.Test;

import static org.junit.Assert.*;

public class LicenseItemTest extends OnePasswordTestCase {
    @Test
    public void testThatItGetsAllTheInfoFromAnIdentitItem() throws Exception {
        OnePasswordKeychain keychain = getUnlockedKeychain();
        LicenseItem item = keychain.licenses().get(0);
        assertEquals("software 1", item.getName());
        keychain.decrypt(item);
        assertEquals("notes", item.getNotes());
        assertEquals("license key", item.getFields().get("reg_code"));
    }
}