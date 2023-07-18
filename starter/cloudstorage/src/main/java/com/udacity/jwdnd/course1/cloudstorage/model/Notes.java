package com.udacity.jwdnd.course1.cloudstorage.model;

public class Notes {
    private Integer noteid;
    private String notetitle;
    private String notedescription;
    private Integer userid;

    //    public Notes(Integer credentialid, String url, String username, String key, String password, Integer userid) {
//        this.credentialid = credentialid;
//        this.url = url;
//        this.username = username;
//        this.key = key;
//        this.password = password;
//        this.userid = userid;
//    }
    public Integer getId(){
        return this.noteid;
    }
    public void setId(Integer noteid) {
        this.noteid = noteid;
    }
    public String getTitle(){
        return this.notetitle;
    }
    public void setTitle(String notetitle) {
        this.notetitle = notetitle;
    }
    public String getDescription(){
        return this.notedescription;
    }
    public void setDescription(String notedescription) {
        this.notedescription = notedescription;
    }
    public Integer getUserId(){
        return this.userid;
    }
    public void setUserId(Integer userid) {
        this.userid = userid;
    }

}
