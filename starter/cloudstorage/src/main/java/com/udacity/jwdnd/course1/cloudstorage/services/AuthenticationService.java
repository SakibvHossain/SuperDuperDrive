package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    private final UserMapper mapper;
    private final HashService hashService;

    //Injecting for using them
    public AuthenticationService(UserMapper mapper, HashService hashService) {
        this.mapper = mapper;
        this.hashService = hashService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Taken user submitted value from Login page
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //This line is used for mapping to the database on username and watching is there any user exist or not
        //by assigning on User Model. If there is no user it will be null else the given user will be assigned on the same reference.
        User user = mapper.getUser(username);

        //Time to check the given user exist or not
        if(user!=null){ //By default, it is false if it is not null it will be true
            //Getting the existed salt to check if the given password is ok or not.
            String encodedSalt = user.getSalt();
            //We hashed again with given password
            String hashedPassword = hashService.getHashedValue(password,encodedSalt);

            //if password matched that means time to log in to home page
            if(user.getPassword().equals(hashedPassword)){
                //We are return an object. This object is used when
                //password matched now let the user be authenticated

                //The token is return to indicate that the authentication
                //Was successful.

                //The third argument has empty ArrayList<>() which is used for
                //authorization purpose. This means that the user is considered to have the default or minimal level of access.
                return new UsernamePasswordAuthenticationToken(username,password, new ArrayList<>());
            }
        }
        return null;
    }

    /*
    The supports method checks if the authentication parameter is an instance of the
    UsernamePasswordAuthenticationToken class. If it is, the method returns true,
    indicating that the AuthenticationProvider supports this type of authentication token.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        /*
        If the supports method returns false for a given authentication token, the
        Spring Security framework will skip this provider and move on to the next
        one in the authentication chain.
         */

        //Else if all ok then return true if it is UsernamePasswordAuthenticationToken
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
