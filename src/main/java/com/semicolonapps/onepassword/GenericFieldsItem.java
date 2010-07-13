package com.semicolonapps.onepassword;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class GenericFieldsItem extends Item {
    String notes = "";
    Map<String, String> fields = new HashMap<String, String>();

    public GenericFieldsItem(RawItem raw) {
        super(raw);
    }

    public void setContent(RawContent content) throws Exception {
        JSONObject json = new JSONObject(new JSONTokener(content.getDecryptedData()));

        for(Iterator it = json.keys(); it.hasNext();) {
            String key = it.next().toString();
            String value = json.get(key).toString();

            if(key.equals("notesPlain")) {
                notes = value;
            }
            else {
                fields.put(key, value);
            }
        }
    }

    public String getNotes() {
        return notes;
    }

    public Map<String, String> getFields() {
        return fields;
    }
}