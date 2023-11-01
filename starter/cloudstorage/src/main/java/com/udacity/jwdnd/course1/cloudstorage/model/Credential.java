package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private final Integer credentialId;
    private final String url;
    private final String username;
    private String key;
    private String password;
    private Integer userId;

    public Credential(Integer credentialId, String url, String username, String key, String password, Integer userId){
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }
    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUsername() {
        return username;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKey() { return key;
    }

    public String getPassword() { return password;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public void setPassword(String password) {
        this.password= password;
    }
}
