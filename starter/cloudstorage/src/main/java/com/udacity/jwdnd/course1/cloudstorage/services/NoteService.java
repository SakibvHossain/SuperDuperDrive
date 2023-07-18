package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }
    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NoteService bean");
    }
    public int addNote(NoteForm noteForm) {
        Notes note = new Notes();
        note.setUserId(noteForm.getUId());
        note.setDescription(noteForm.getDescription());
        note.setTitle(noteForm.getTitle());
        System.out.println("Note is Submitted Note is Submitted Note is Submitted Note is Submitted Note is Submitted Note is Submitted");
        return noteMapper.insert(note);
    }
    public void updateNote(NoteForm noteForm) {
        Notes note = new Notes();
        note.setId(noteForm.getId());
        note.setUserId(noteForm.getUId());
        note.setDescription(noteForm.getDescription());
        note.setTitle(noteForm.getTitle());
        noteMapper.update(note);
        System.out.println("Note is Submitted Note is Submitted Note is Submitted Note is Submitted Note is Submitted Note is Submitted");
    }
    public void deleteNote(Integer id) {
        noteMapper.delete(id);
    }
    public List<Notes> getNotes() {
        return noteMapper.getNotes();
    }
}
