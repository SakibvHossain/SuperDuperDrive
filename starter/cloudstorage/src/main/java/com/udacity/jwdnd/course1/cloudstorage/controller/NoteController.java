package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    public String noteError = null;
    public String noteSuccess = null;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String createNote(@ModelAttribute Note note, Authentication authentication, Model model){
        this.noteError = null;
        this.noteSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsAdded = noteService.createNote(note);
        if (rowsAdded < 0){
            this.noteError = "There was an error for adding a note. Please try again";
        }
        if (this.noteError == null) {
            model.addAttribute("noteSuccess", "You successfully added a new note");
        } else {
            model.addAttribute("noteError", this.noteError);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateNote(@ModelAttribute Note note, Authentication authentication, Model model){
        System.out.println("i m in update note");
        this.noteError = null;
        this.noteSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsUpdated = noteService.updateNote(note);
        if (rowsUpdated < 0){
            this.noteError = "There was an error for updating a note. Please try again";
        }
        if (this.noteError == null) {
            model.addAttribute("noteSuccess", "You successfully updated a note");
        } else {
            model.addAttribute("noteError", this.noteError);
        }

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteNote(@ModelAttribute Note note, Authentication authentication, Model model){
        System.out.println("i m in delete note");
        this.noteError = null;
        this.noteSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        int rowsUpdated = noteService.deleteNote(note.getNoteId());
        if (rowsUpdated < 0){
            this.noteError = "There was an error deleting a note. Please try again";
        }
        if (this.noteError == null) {
            model.addAttribute("noteSuccess", "You successfully deleted a note");
        } else {
            model.addAttribute("noteError", this.noteError);
        }

        return "redirect:/home";

    }
}
