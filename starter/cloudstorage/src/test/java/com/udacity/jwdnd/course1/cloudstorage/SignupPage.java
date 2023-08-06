package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    WebDriverWait wait;
    @FindBy(id = "inputFirstName")
    WebElement firstName;
    @FindBy(id = "inputLastName")
    WebElement lastName;
    @FindBy(id = "inputUsername")
    WebElement userName;
    @FindBy(id = "inputPassword")
    WebElement password;
    @FindBy(id = "buttonSignUp")
    WebElement signUp;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver,this);
        wait = new WebDriverWait(driver,2);
    }

    public void doMockSignUp(String firstname, String lastname, String username, String passWord){
        //firstname
        wait.until(ExpectedConditions.visibilityOf(firstName));
        firstName.click();
        firstName.sendKeys(firstname);
        //lastname
        wait.until(ExpectedConditions.visibilityOf(lastName));
        lastName.click();
        lastName.sendKeys(lastname);
        //username
        wait.until(ExpectedConditions.visibilityOf(userName));
        userName.click();
        userName.sendKeys(username);
        //password
        wait.until(ExpectedConditions.visibilityOf(password));
        password.click();
        password.sendKeys(passWord);
        //submit
        wait.until(ExpectedConditions.visibilityOf(signUp));
        signUp.click();
    }
}
