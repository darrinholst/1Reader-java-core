package com.semicolonapps.onepassword;

public abstract class Item {
    private RawItem raw;

    public Item(RawItem raw) {
        this.raw = raw;
    }

    public boolean isTrashed() {
        return raw.isTrashed();
    }

    public String getId() {
        return raw.getId();
    }

    public String getName() {
        return raw.getName();
    }

    public String getDomain() {
        return raw.getDomain();
    }

    public abstract void setContent(RawContent content) throws Exception;
}
