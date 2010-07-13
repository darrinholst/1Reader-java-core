package com.semicolonapps.onepassword;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoteItemTest extends OnePasswordTestCase {
    @Test
    public void testThatItGetsAllTheInfoFromANoteItem() throws Exception {
        OnePasswordKeychain keychain = getUnlockedKeychain();
        NoteItem note = keychain.notes().get(0);
        assertEquals("secure note 1", note.getName());
        keychain.decrypt(note);
        assertEquals("secure notes", note.getNotes());
    }
}