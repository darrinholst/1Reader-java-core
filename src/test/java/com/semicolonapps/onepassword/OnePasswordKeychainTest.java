package com.semicolonapps.onepassword;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class OnePasswordKeychainTest extends OnePasswordTestCase {
    @Test
    public void testThatItCanBeUnlocked() {
        assertTrue(getKeychain().unlock("password"));
    }

    @Test
    public void testThatInvalidPasswordDoesNotUnlock() {
        assertFalse(getKeychain().unlock("wrong password"));
    }

    @Test
    public void testThatItCanRetrieveAListOfLogins() {
        List<LoginItem> logins = getUnlockedKeychain().logins();
        assertEquals(1, logins.size());
    }

    @Test
    public void testThatItCanRetrieveAListOfIdentities() {
        List<IdentityItem> identities = getUnlockedKeychain().identities();
        assertEquals(1, identities.size());
    }

    @Test
    public void testThatItCanRetrieveAListOfNotes() {
        List<NoteItem> notes = getUnlockedKeychain().notes();
        assertEquals(1, notes.size());
    }

    @Test
    public void testThatItCanRetrieveAListOfPasswords() {
        List<PasswordItem> passwords = getUnlockedKeychain().passwords();
        assertEquals(1, passwords.size());
    }

    @Test
    public void testThatItCanRetrieveAListOfWalletItems() {
        List<WalletItem> walletItems = getUnlockedKeychain().walletItems();
        assertEquals(2, walletItems.size());
    }

    @Test
    public void testThatItCanRetrieveAListOfLicenses() {
        List<LicenseItem> licenses = getUnlockedKeychain().licenses();
        assertEquals(1, licenses.size());
    }

    @Test
    public void testThatItCanRetrieveTheTrash() {
        List<Item> trash = getUnlockedKeychain().trash();
        assertEquals(1, trash.size());
    }

    @Test
    public void testThatUnlockingMultipleTimesDoesNotMultiplyTheItems() {
        OnePasswordKeychain keychain = getKeychain();
        keychain.unlock(PASSWORD);
        keychain.unlock(PASSWORD);
        assertEquals(1, keychain.logins().size());
    }
}
