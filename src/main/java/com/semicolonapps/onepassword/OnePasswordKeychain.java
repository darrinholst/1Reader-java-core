package com.semicolonapps.onepassword;

import com.semicolonapps.crypto.Aes;
import com.semicolonapps.crypto.PBKDF2;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.*;

public class OnePasswordKeychain {
    private File baseDir;
    private String masterPassword;
    private Map<String, byte[]> decryptedKeys = new HashMap<String, byte[]>();
    private List<Item> items = new ArrayList<Item>();

    public OnePasswordKeychain(File keychain) {
        this.baseDir = keychain;
    }

    public boolean unlock(String password) {
        try {
            decryptEncryptionKey("SL5", password);
            this.masterPassword = password;
            loadContents();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    public void decrypt(Item item) throws Exception {
        File contentFile = new File(baseDir, "data/default/" + item.getId() + ".1password");
        JSONObject content = new JSONObject(new JSONTokener(FileUtils.readFileToString(contentFile)));
        RawContent rawContent = new RawContent(content);

        byte[] key = getEncryptionKeyFor(rawContent.getSecurityLevel());
        byte[] decrypted = new Aes().decrypt(rawContent.getAttribute("encrypted"), key);
        rawContent.setDecryptedData(new String(decrypted));

        item.setContent(rawContent);
    }

    public List<LoginItem> logins() {
        return itemsOfType(LoginItem.class);
    }

    public List<IdentityItem> identities() {
        return itemsOfType(IdentityItem.class);
    }

    public List<NoteItem> notes() {
        return itemsOfType(NoteItem.class);
    }

    public List<PasswordItem> passwords() {
        return itemsOfType(PasswordItem.class);
    }

    public List<WalletItem> walletItems() {
        return itemsOfType(WalletItem.class);
    }

    public List<LicenseItem> licenses() {
        return itemsOfType(LicenseItem.class);
    }

    public List<Item> trash() {
        List items = new ArrayList();

        for(Item item : this.items) {
            if(item.isTrashed()) {
                items.add(item);
            }
        }

        return items;
    }

    private List itemsOfType(Class klass) {
        List items = new ArrayList();

        for(Item item : this.items) {
            if(!item.isTrashed() && item.getClass() == klass) {
                items.add(item);
            }
        }

        return items;
    }

    private void decryptEncryptionKey(String level, String password) throws Exception {
        File keyFile = new File(baseDir, "data/default/encryptionKeys.js");
        JSONObject keys = new JSONObject(new JSONTokener(FileUtils.readFileToString(keyFile)));
        JSONArray list = (JSONArray) keys.get("list");

        for(int i = 0; i < list.length(); i++) {
            JSONObject item = (JSONObject) list.get(i);

            if(item.get("identifier").equals(keys.get(level))) {
                byte[] decryptedKey = new PBKDF2().decrypt(item.get("data").toString(), password);
                byte[] verification = new Aes().decrypt(item.get("validation").toString(), decryptedKey);

                if(Arrays.equals(decryptedKey, verification)) {
                    this.decryptedKeys.put(level, decryptedKey);
                }
            }
        }
    }

    private void loadContents() throws Exception {
        File contentsFile = new File(baseDir, "data/default/contents.js");
        JSONArray items = new JSONArray(new JSONTokener(FileUtils.readFileToString(contentsFile)));

        this.items.clear();

        for(int i = 0; i < items.length(); i++) {
            Item item = ItemFactory.create((JSONArray) items.get(i), baseDir);

            if(item != null) {
                this.items.add(item);
            }
        }
    }

    private byte[] getEncryptionKeyFor(String securityLevel) throws Exception {
        byte[] key = decryptedKeys.get(securityLevel);

        if(key != null) {
            return key;
        }

        decryptEncryptionKey(securityLevel, masterPassword);

        return decryptedKeys.get(securityLevel);
    }
}
