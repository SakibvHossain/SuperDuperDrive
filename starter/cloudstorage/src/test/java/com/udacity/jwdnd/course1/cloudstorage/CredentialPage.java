package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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

    public CredentialPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
}
