package com.semicolonapps.onepassword;

import org.json.JSONArray;

import java.io.File;

public class ItemFactory {
    private static final String LOGIN = "webforms.WebForm";
    private static final String IDENTITY = "identities.Identity";
    private static final String NOTE = "securenotes.SecureNote";
    private static final String PASSWORD = "passwords.Password";
    private static final String WALLET = "wallet\\.(?!computer.License).*";
    private static final String SOFTWARE = "wallet\\.computer\\.License";

    public static Item create(JSONArray parts, File baseDir) {
        RawItem raw = new RawItem(parts);

        String type = raw.getType();

        if(type.matches(LOGIN)) {
            return new LoginItem(raw);
        }
        else if(type.matches(IDENTITY)) {
            return new IdentityItem(raw);
        }
        else if(type.matches(NOTE)) {
            return new NoteItem(raw);
        }
        else if(type.matches(PASSWORD)) {
            return new PasswordItem(raw);
        }
        else if(type.matches(WALLET)) {
            return new WalletItem(raw);
        }
        else if(type.matches(SOFTWARE)) {
            return new LicenseItem(raw);
        }

        return null;
    }
}
