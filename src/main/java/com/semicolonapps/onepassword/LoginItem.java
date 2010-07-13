package com.semicolonapps.onepassword;

public class LoginItem extends Item {
    private String username;
    private String password;
    private String url;

    public LoginItem(RawItem raw) {
        super(raw);
    }

    public void setContent(RawContent content) throws Exception {
        this.url = content.getAttribute("location");
        this.username = content.getFieldDesignatedAs("username");
        this.password = content.getFieldDesignatedAs("password");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
