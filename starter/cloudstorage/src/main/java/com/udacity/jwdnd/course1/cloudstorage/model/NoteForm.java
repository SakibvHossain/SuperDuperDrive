package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private Integer id;
    private String title;
    private String description;
    private Integer uid;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUId() {
        return this.uid;
    }

    public void setUId(Integer uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
