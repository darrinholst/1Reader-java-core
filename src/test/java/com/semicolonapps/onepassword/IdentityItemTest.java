package com.semicolonapps.onepassword;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentityItemTest extends OnePasswordTestCase {
    @Test
    public void testThatItGetsAllTheInfoFromAnIdentityItem() throws Exception {
        OnePasswordKeychain keychain = getUnlockedKeychain();
        IdentityItem item = keychain.identities().get(0);
        assertEquals("identity 1", item.getName());
        keychain.decrypt(item);
        assertEquals("notes", item.getNotes());
        assertEquals("1976", item.getFields().get("birthdate_yy"));
        assertEquals("8", item.getFields().get("birthdate_mm"));
        assertEquals("it", item.getFields().get("occupation"));
        assertEquals("last", item.getFields().get("lastname"));
        assertEquals("5", item.getFields().get("birthdate_dd"));
        assertEquals("male", item.getFields().get("sex"));
        assertEquals("semicolonapps", item.getFields().get("company"));
        assertEquals("first", item.getFields().get("firstname"));
        assertEquals("middle", item.getFields().get("initial"));
        assertEquals("fumullins", item.getFields().get("department"));
        assertEquals("programmer", item.getFields().get("jobtitle"));
    }
}
