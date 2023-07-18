package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper mapper;
    private final HashService hashService;

    public UserService(UserMapper mapper, HashService hashService) {
        this.mapper = mapper;
        this.hashService = hashService;
    }

    //User is available or not
    public boolean isUserAvailable(String username){
        return mapper.getUser(username)==null;
    }

// Testing when we assign mapper on userModel what happens and why it happens
//    public boolean whatIsThis(String username){
//        UserModel userModel = mapper.getUser(username);
//
//        return userModel == null;
//    }

    //Here we are creating user and making the password encrypted. Passing with SecureRandom with 16 byte array salt value then encoding the salt value
    public int createUser(UserModel userModel){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String password = hashService.getHashedValue(userModel.getPassword(),encodedSalt);
        return mapper.insert(new UserModel(null, userModel.getFirstname(), userModel.getLastname(), userModel.getUsername(), password,encodedSalt));
    }

    public UserModel getUser(String username) {
        return mapper.getUser(username);
    }

}
