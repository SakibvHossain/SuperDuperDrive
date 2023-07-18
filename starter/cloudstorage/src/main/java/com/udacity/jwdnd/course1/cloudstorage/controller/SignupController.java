package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewSignupPage(){
        return "signup";
    }

    @PostMapping
    public String signupOperations(Model model, @ModelAttribute UserModel userModel){

//        System.out.println("ID: "+userModel.getId());
//        System.out.println("Firstname: "+userModel.getFirstname());
//        System.out.println("Lastname: "+userModel.getLastname());
//        System.out.println("Username: "+userModel.getUsername());
//        System.out.println("Password: "+userModel.getPassword());

        String signupError = null;

        System.out.println(userService.isUserAvailable(userModel.getUsername()));

        if(!userService.isUserAvailable(userModel.getUsername())){
            System.out.println(userModel.getUsername());
//            System.out.println(userServices.whatIsThis("a"));
            signupError = "User Already Exits";
            model.addAttribute("signupError", true);
            model.addAttribute("message",signupError);
        }

        if(signupError == null){
            int isRowsAdded_to_1 = userService.createUser(userModel);
            if(isRowsAdded_to_1 < 0){
                signupError = "There is a problem";
                model.addAttribute("signupError", true);
                model.addAttribute("message",signupError);
            }else{
                model.addAttribute("signupSuccess",true);
//                return "redirect:/login";
            }
        }
        return "signup";
    }
}
