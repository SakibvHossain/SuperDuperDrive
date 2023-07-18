package com.udacity.jwdnd.course1.cloudstorage.model;


public class Credentials {
    private Integer credentialid;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userid;

    public Integer getCredentialid(){
        return this.credentialid;
    }
    public void setCredentialid(Integer credentialid) { this.credentialid = credentialid;}

    public String getUrl(){
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey(){
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId(){
        return this.userid;
    }
    public void setUserId(Integer userid) {
        this.userid = userid;
    }
}

