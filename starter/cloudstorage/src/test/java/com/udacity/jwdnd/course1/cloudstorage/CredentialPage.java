package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CredentialPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credential_tab;
    @FindBy(id = "create_new_credential")
    private WebElement create_Credential;
    @FindBy(id = "credential-url")
    private WebElement credential_Url;
    @FindBy(id = "credential-username")
    private WebElement credential_username;
    @FindBy(id = "credential-password")
    private WebElement credential_password;
    @FindBy(id = "save_changes_credential")
    private WebElement save_credentials;
    ///////////////////////////////////////Saved Credentials
    @FindBy(id = "url_credential")
    private WebElement saved_url;
    @FindBy(id = "username_credential")
    private WebElement saved_username;
    @FindBy(id = "password_credential")
    private WebElement saved_password;
    ///////////////////////////////////////Editing Field
    @FindBy(id = "credential_edit_button")
    private WebElement edit_credentials;
    @FindBy(id = "edit-credential-url")
    private WebElement edit_Url;
    @FindBy(id = "edit-credential-username")
    private WebElement edit_username;
    @FindBy(id = "edit-credential-password")
    private WebElement edit_password;
    ///////////////////////////////////////Deleting Field
    @FindBy(id = "want_to_delete")
    private WebElement view_Delete;
    @FindBy(id = "deleteCredentialSubmit")
    private WebElement delete_credentials;
    @FindBy(id = "credentialTable")
    private WebElement tableCredential;
    private static String username = "Tanha";
    private static String url = "localhost:8080/takeLove";
    private static String password = "T4nha";

    WebDriverWait wait;
    @Autowired
    private CredentialService credentialService;

    private EncryptionService encryptionService;

    public CredentialPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,2);
        encryptionService = new EncryptionService();
    }


    //Write a test that creates a set of credentials
    public void creatingCredential(){
        //Click on credential tab
        wait.until(ExpectedConditions.visibilityOf(credential_tab));
        credential_tab.click();
        //Create credential
        wait.until(ExpectedConditions.visibilityOf(create_Credential));
        create_Credential.click();
        //Credential Url
        wait.until(ExpectedConditions.visibilityOf(credential_Url));
        credential_Url.click();
        credential_Url.sendKeys(url);
        //Credential username
        wait.until(ExpectedConditions.visibilityOf(credential_username));
        credential_username.click();
        credential_username.sendKeys(username);
        //Credential password
        wait.until(ExpectedConditions.visibilityOf(credential_password));
        credential_password.click();
        credential_password.sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(save_credentials));
        save_credentials.click();
    }
    //Verifies that they are displayed
    public void verifying_Credentials_Displayed(){
        //Click on credential tab
        wait.until(ExpectedConditions.visibilityOf(credential_tab));
        credential_tab.click();
        //saved url
        wait.until(ExpectedConditions.visibilityOf(saved_url));
        Assertions.assertEquals(url,saved_url.getText());
        wait.until(ExpectedConditions.visibilityOf(saved_username));
        Assertions.assertEquals(username,saved_username.getText());
        wait.until(ExpectedConditions.visibilityOf(saved_password));
        //There is no id except 1 because 1 credential will be created that's for
        Credential credential = this.credentialService.getCredentialById(1);
        Assertions.assertEquals(encryptionService.encryptValue(password,credential.getKey()),saved_password.getText());
    }
}
