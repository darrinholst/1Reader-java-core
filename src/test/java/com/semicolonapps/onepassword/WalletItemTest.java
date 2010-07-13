package com.semicolonapps.onepassword;

import org.junit.Test;

import static org.junit.Assert.*;

public class WalletItemTest extends OnePasswordTestCase {
    @Test
    public void testThatItGetsAllTheInfoFromAPasswordItem() throws Exception {
        OnePasswordKeychain keychain = getUnlockedKeychain();
        WalletItem item = keychain.walletItems().get(0);
        assertEquals("wallet 1", item.getName());
        keychain.decrypt(item);
        assertEquals("notes", item.getNotes());
        assertEquals("bank name", item.getFields().get("bankName"));
    }
}
