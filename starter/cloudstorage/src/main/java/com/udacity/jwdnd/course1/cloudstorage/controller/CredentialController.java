package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    public String credentialError = null;
    public String credentialSuccess = null;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, Model model){
        System.out.println("i m in create cred");
        this.credentialError = null;
        this.credentialSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
//        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        int rowsAdded = credentialService.createCredential(credential);
        if (rowsAdded < 0){
            this.credentialError = "There was an error for adding a credential. Please try again";
        }
        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You successfully added a new note");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "redirect:/home";
    }

    @PostMapping("/edit")
    public String updateCredential(@ModelAttribute Credential credential, Authentication authentication, Model model){
        System.out.println("i m in update note");
        this.credentialError = null;
        this.credentialSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);



//        System.out.println(credential.getDecryptedPassword());
        System.out.println(model.getAttribute("credential-password"));
//        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
//        credential.setPassword(encryptedPassword);


        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey()));

        int rowsUpdated = credentialService.updateCredential(credential);
        if (rowsUpdated < 0){
            this.credentialError = "There was an error for updating a credential. Please try again";
        }
        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You successfully updated a note");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "redirect:/home";
    }

    @PostMapping("/delete")
    public String deleteCredential(@ModelAttribute Credential credential, Authentication authentication, Model model){
        System.out.println("i m in delete note");
        this.credentialError = null;
        this.credentialSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        int rowsUpdated = credentialService.deleteCredential(credential.getCredentialId());
        if (rowsUpdated < 0){
            this.credentialError = "There was an error deleting a credential. Please try again";
        }
        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You successfully deleted a credential");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "redirect:/home";

    }

}
