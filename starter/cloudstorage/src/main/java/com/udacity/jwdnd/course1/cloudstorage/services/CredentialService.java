package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int createCredential(Credential credential) {
        return credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(),credential.getKey(), credential.getPassword(), credential.getUserId()));
    }

    public List<Credential> getCredentials(Integer userId){
        return credentialMapper.getCredentials(userId);
    }

    public int updateCredential(Credential credential) {
        return credentialMapper.updateCredential(new Credential(credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                credential.getKey(),
                credential.getPassword(),
                credential.getUserId()
        ));
    }
    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }

    public Credential getCredentialById(int credentialId) {
        return this.credentialMapper.getCredentialById(credentialId);
    }
}

