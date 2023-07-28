package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        model.addAttribute("encryptionService",encryptionService);
        model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        return "home";
    }

    @PostMapping
    public String sendingData(Authentication authentication, FileForm fileForm, NoteForm noteForm, Model model){
        if(noteForm.getDescription() != null || noteForm.getTitle() != null){
            if(noteForm.getId() == null){
                User targetuser = this.userService.getUser(authentication.getName());
                noteForm.setUId(targetuser.getUserId());
                int queryResult = this.noteService.addNote(noteForm);
                if(queryResult == 1){
                    model.addAttribute("successOperation",true);
                    model.addAttribute("successMessage","Your note was added successfully!!!!");
                } else {
                    model.addAttribute("errorOperation",true);
                    model.addAttribute("errorMessage","Your note was not inserted. Please retry the operation!!!!");
                }
                noteForm.setTitle("");
                noteForm.setDescription("");
                model.addAttribute("noteList", this.noteService.getNotes());
                model.addAttribute("fileList", this.fileService.getFiles());
                System.out.println("Bitter");
                return "home";
            }
            else{
                // Note needs to be updated
                System.out.println("NOTE ID: " + noteForm.getId());
                System.out.println("NOTE TITLE: " + noteForm.getTitle());
                System.out.println("NOTE DESCRIPTION: " + noteForm.getDescription());
                User targetuser = this.userService.getUser(authentication.getName());
                noteForm.setUId(targetuser.getUserId());
                this.noteService.updateNote(noteForm);
                noteForm.setTitle("");
                noteForm.setDescription("");
                model.addAttribute("noteList", this.noteService.getNotes());
                model.addAttribute("fileList", this.fileService.getFiles());
                return "home";
            }
        }

        if(fileForm.getFileUpload() != null){
        try {
            String filename = fileForm.getFileUpload().getOriginalFilename();
            String contentType = fileForm.getFileUpload().getContentType();
            byte[] fileBytes = fileForm.getFileUpload().getBytes();
            String fileSize = String.valueOf(fileForm.getFileUpload().getSize());
            int userId = this.userService.getUser(authentication.getName()).getUserId();
            Files file = new Files(null, filename, contentType, fileSize, userId, fileBytes);
            this.fileService.addFile(file);
            System.out.println(Arrays.toString(fileBytes));
            model.addAttribute("fileList", this.fileService.getFiles());
            model.addAttribute("noteList", this.noteService.getNotes());
        } catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }
        return "home";
    }
    //Getting the request to view. Actually it downloads and view. For now lets say view.
    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<byte[]> viewed(@PathVariable("fileId") Integer fileId, Model model){
        System.out.println("File " + fileId + "is viewed");
        Files file = fileService.getFileById(fileId);
        String filename = file.getFilename();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+filename+"\"")
                .body(file.getFiledata());
    }
    //Getting the request to delete. This is just used to delete from file, note, or credential List.

    //Deleting Files
    @GetMapping("/file/delete/{fileId}")
    public String deleted(@PathVariable("fileId") Integer fileId, Model model){
        System.out.println("File " + fileId + " is deleted");
        this.fileService.deleteFile(fileId);
        model.addAttribute("fileList", this.fileService.getFiles());
        model.addAttribute("noteList", this.noteService.getNotes());
//        model.addAttribute("credentialList", this.credentialService.getCredentials());
        return "home";
    }

    //Deleting Notes
    @GetMapping("/note/delete/{noteId}")
    public String notedeleted(@PathVariable("noteId") Integer noteId, Model model){
        System.out.println("Note " + noteId + " is deleted");
        this.noteService.deleteNote(noteId);
        model.addAttribute("fileList", this.fileService.getFiles());
        model.addAttribute("noteList", this.noteService.getNotes());
//        model.addAttribute("credentialList", this.credentialService.getCredentials());
        return "home";
    }

    //Deleting Credentials
    //th:href="@{/home/credential/view/{passId}(passId=${cred.password})}"

//    @GetMapping("/credential/view/{credId}")
//    public String viewDecryptedPassword(@PathVariable("credId") Integer credentialid, Model model){
//        System.out.println("Encrypted Password Viewed As: "+ credentialid);
//        //this.credentialService.gettingTheCredential(credentialid);
//        model.addAttribute("fileList", this.fileService.getFiles());
//        model.addAttribute("noteList", this.noteService.getNotes());
//        model.addAttribute("credentialList", this.credentialService.getCredentials());
//        return "home/credential/view";
//    }
    //th:href="@{/home/credential/delete/{credId}(credId=${cred.credentialid})}">Delete</a>
//    @GetMapping("/credential/delete/{credId}")
//    public String creddeleted(@PathVariable("credId") Integer credId, Model model){
//        System.out.println("Credential " + credId + " is deleted");
//        this.credentialService.deleteCredential(credId);
//        model.addAttribute("fileList", this.fileService.getFiles());
//        model.addAttribute("noteList", this.noteService.getNotes());
//        return "home";
//    }




    //Defining the Models
    //File Form and List
    @ModelAttribute("fileForm")
    public FileForm getFileForm(){
        return new FileForm();
    }
    @ModelAttribute("oneFile")
    public Files getAFile(){
        return new Files();
    }
    @ModelAttribute("fileList")
    public List<Files> FileList(){
        return this.fileService.getFiles();
    }

    //Note Form and List
    @ModelAttribute("noteForm")
    public NoteForm getNoteForm(){
        return new NoteForm();
    }
    @ModelAttribute("noteList")
    public List<Notes> NoteList(){
        return this.noteService.getNotes();
    }

    //Credential Form and List

}
