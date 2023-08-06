package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriverWait wait;

    @FindBy(id = "inputUsername")
    WebElement userName;
    @FindBy(id = "inputPassword")
    WebElement password;
    @FindBy(id = "login-button")
    WebElement login_button;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,2);
    }

    public void login(String username, String passWord){
        //Username
        wait.until(ExpectedConditions.visibilityOf(userName));
        userName.click();
        userName.sendKeys(username);
        //Password
        wait.until(ExpectedConditions.visibilityOf(password));
        password.click();
        password.sendKeys(passWord);
        //Submit
        wait.until(ExpectedConditions.visibilityOf(login_button));
        login_button.click();
    }
}
