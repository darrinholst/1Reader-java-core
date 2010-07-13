package com.semicolonapps.onepassword;

import org.json.JSONArray;

public class RawItem {
    private static final int ID = 0;
    private static final int TYPE = 1;
    private static final int NAME = 2;
    private static final int DOMAIN = 3;
    private static final int TRASHED = 7;

    private String id;
    private String type;
    private boolean trashed;
    private String name;
    private String domain;

    public RawItem(JSONArray jsonArray) {
        try {
            this.id = jsonArray.get(ID).toString();
            this.type = jsonArray.get(TYPE).toString();
            this.name = jsonArray.get(NAME).toString();
            this.domain = jsonArray.get(DOMAIN).toString();
            this.trashed = jsonArray.get(TRASHED).toString().equalsIgnoreCase("y");
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getType() {
        return type;
    }

    public boolean isTrashed() {
        return trashed;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }
}
