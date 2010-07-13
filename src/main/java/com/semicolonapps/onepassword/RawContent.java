package com.semicolonapps.onepassword;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RawContent {
    private JSONObject json;
    private String decryptedData;

    public RawContent(JSONObject json) {
        this.json = json;
    }

    public String getSecurityLevel() {
        try {
            JSONObject openContents = (JSONObject) json.get("openContents");
            return openContents.get("securityLevel").toString();
        }
        catch(JSONException e) {
            return "SL5";
        }
    }

    public String getAttribute(String name) {
        try {
            return json.get(name).toString();
        }
        catch(JSONException e) {
            return null;
        }
    }

    public void setDecryptedData(String data) {
        this.decryptedData = data;
    }

    public String getDecryptedData() {
        return decryptedData;
    }

    public String getFieldDesignatedAs(String name) throws Exception {
        JSONObject encryptedData = new JSONObject(new JSONTokener(getDecryptedData()));
        JSONArray fields = (JSONArray) encryptedData.get("fields");

        for(int i = 0; i < fields.length(); i++) {
            JSONObject field = (JSONObject) fields.get(i);

            try {
                if(name.equalsIgnoreCase(field.get("designation").toString())) {
                    return field.get("value").toString();
                }
            }
            catch(JSONException e) {
                //designation or value not found, ignore it
            }
        }

        return null;
    }
}
