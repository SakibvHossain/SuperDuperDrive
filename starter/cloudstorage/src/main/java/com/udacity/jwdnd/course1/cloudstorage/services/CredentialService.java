package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;
    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredential(CredentialForm cform){
        Credentials credential = new Credentials();
        String theKey = encryptionService.generateKey();
        credential.setKey(theKey);
        credential.setUrl(cform.getUrl());
        credential.setUsername(cform.getUsername());
        credential.setPassword(encryptionService.encryptValue(cform.getPassword(), theKey));
        credential.setUserId(cform.getUserId());
        credentialMapper.insert(credential);
        System.out.println(credential.getCredentialid());
        System.out.println(credential.getUrl());
        System.out.println(credential.getUsername());
        System.out.println(credential.getPassword());
        System.out.println(credential.getUserId());
        System.out.println("Key " + credential.getKey());
    }
    public void updateCredential(CredentialForm cform){
        Credentials credential = new Credentials();
        String theKey = encryptionService.generateKey();
        credential.setKey(theKey);
        credential.setUrl(cform.getUrl());
        credential.setUsername(cform.getUsername());
        credential.setPassword(encryptionService.encryptValue(cform.getPassword(), theKey));
        credential.setUserId(cform.getUserId());
        credential.setCredentialid(cform.getId());
        credentialMapper.update(credential);
        System.out.println(credential.getCredentialid());
        System.out.println(credential.getUrl());
        System.out.println(credential.getUsername());
        System.out.println(credential.getPassword());
        System.out.println(credential.getUserId());
        System.out.println("Key " + credential.getKey());
    }

    public void deleteCredential(Integer id){
        credentialMapper.delete(id);
    }

    public void gettingTheCredential(CredentialForm credentialForm){
        Credentials credential = new Credentials();
        String key = credential.getKey();
        String password = credential.getPassword();
        String encryptedPassword = encryptionService.decryptValue(password,key);
        credential.setPassword(encryptedPassword);
    }


    public List<Credentials> getCredentials() {
        return credentialMapper.getCredentials();
    }
}

