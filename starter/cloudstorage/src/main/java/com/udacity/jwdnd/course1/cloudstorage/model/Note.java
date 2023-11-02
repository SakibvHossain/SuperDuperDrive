package com.udacity.jwdnd.course1.cloudstorage.model;

public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Note(Integer noteId, String title, String description, Integer userId){
        this.noteId = noteId;
        this.noteTitle = title;
        this.noteDescription = description;
        this.userId = userId;
    }

    public String getTitle() {
        return noteTitle;
    }
    public Integer getNoteId() {
        return noteId;
    }

    public String getDescription() {
        return noteDescription;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
