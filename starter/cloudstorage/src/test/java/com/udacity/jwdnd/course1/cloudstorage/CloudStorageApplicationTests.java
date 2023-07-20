package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.UserModel;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;


	private WebDriver driver;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	/**
	 * 1. Write tests for user signup, login, and unauthorized access restrictions.
	 */

	@Test
	public void Verifying(){
		checkingUnauthorizedAccessTest();
		authorizingUserTest("ai","ai","ai","ai");
	}

	//Point - 1: Write a test that verifies that an unauthorized user can only access the login and signup pages.
	private void checkingUnauthorizedAccessTest(){
		driver.get("http://localhost:"+this.port+"/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:"+this.port+"/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:"+this.port+"/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	//Point - 2: Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible.
	private void authorizingUserTest(String firstName, String lastName, String userName, String password){
		//We need to wait because web can view few minutes later right that can be a reason for our test failure.
		WebDriverWait wait = new WebDriverWait(driver,2);
		signup_Login(firstName,lastName,userName,password);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_button")));
		WebElement logout_button = driver.findElement(By.id("logout_button"));
		logout_button.click();
		wait.until(ExpectedConditions.titleContains("Login"));

		driver.get("http://localhost:"+this.port+"/home");
		wait.until(ExpectedConditions.titleContains("Login"));

	}

	//Universal login signup method for testing :)
	private void signup_Login(String firstName, String lastName, String userName, String password){
		WebDriverWait wait = new WebDriverWait(driver,2);
		driver.get("http://localhost:"+this.port+"/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("redirectToLogin")));
		WebElement redirectToLogin = driver.findElement(By.id("redirectToLogin"));
		redirectToLogin.click();

		wait.until(ExpectedConditions.titleContains("Login"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUsername = driver.findElement(By.id("inputUsername"));
		loginUsername.click();
		loginUsername.sendKeys(userName);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement login_button = driver.findElement(By.id("login-button"));
		login_button.click();

		wait.until(ExpectedConditions.titleContains("Home"));
	}

	/**
	 * 2. Write tests for note creation, viewing, editing, and deletion.
	 */

	@Test
	public void NoteTesting(){
		signup_Login("as", "as", "as", "as");
		creating_verifying_creating_deleting_Notes();
	}
	private void creating_verifying_creating_deleting_Notes(){
		WebDriverWait wait = new WebDriverWait(driver,2);

		note_Basic_Input("SuperDuperNote","Lets write super duper notes!",true);
		//Point - 1: Write a test that creates a note, and verifies it is displayed.
		checkNotesAssertions("SuperDuperNote","Lets write super duper notes!");

		//Point - 2: Write a test that edits an existing note and verifies that the changes are displayed.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_editing")));
		WebElement note_editing = driver.findElement(By.id("note_editing"));
		note_editing.click();

		note_Basic_Input("Hey","Whats up",false);
		checkNotesAssertions("Hey","Whats up");
		//Done!
		//Point - 3: Write a test that deletes a note and verifies that the note is no longer displayed.
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_deleting")));
		WebElement note_deleting = driver.findElement(By.id("note_deleting"));
		note_deleting.click();

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			WebElement find = driver.findElement(By.id("note_deleting"));
		});
	}
	private void note_Basic_Input(String title, String description, Boolean isTrue){
		WebDriverWait wait = new WebDriverWait(driver,2);
		//Tab on Notes
		if(isTrue){
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
			WebElement nav_notes_tab = driver.findElement(By.id("nav-notes-tab"));
			nav_notes_tab.click();
			//Click to create Notes
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("open-notes-form")));
			WebElement open_notes_form = driver.findElement(By.id("open-notes-form"));
			open_notes_form.click();
		}
		//Write something on title
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement note_title = driver.findElement(By.id("note-title"));
		note_title.click();
		note_title.clear();
		note_title.sendKeys(title);
		//Write something on Description
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement note_description = driver.findElement(By.id("note-description"));
		note_description.click();
		note_description.clear();
		note_description.sendKeys(description);
		//Save Changes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submitting")));
		WebElement noteSubmit = driver.findElement(By.id("note-submitting"));
		noteSubmit.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement nav_notes_tab2 = driver.findElement(By.id("nav-notes-tab"));
		nav_notes_tab2.click();
	}
	private void checkNotesAssertions(String match_title, String match_description){
		WebDriverWait wait = new WebDriverWait(driver,2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_title_test")));
		WebElement note_title_test = driver.findElement(By.id("note_title_test"));
		String getThe_Title = note_title_test.getText();

		Assertions.assertEquals(match_title, getThe_Title);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_description_test")));
		WebElement note_description_test = driver.findElement(By.id("note_description_test"));
		String getThe_description = note_description_test.getText();

		Assertions.assertEquals(match_description, getThe_description);
	}

	/**
	 * Write tests for credential creation, viewing, editing, and deletion.
	 */

	//Best test
	@Test
	public void CredentialTesting(){
		WebDriverWait wait = new WebDriverWait(driver,2);
		signup_Login("ds","ds","ds","ds");
		credentialCreation(wait,"www.localhost.com","Tanha","t4nha");
	}

	//Credential Creation
	private void credentialCreation(WebDriverWait wait, String url, String userName, String passWord){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement nav_credentials_tab = driver.findElement(By.id("nav-credentials-tab"));
		nav_credentials_tab.click();

		//create_new_credential button has been clicked
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("create_new_credential")));
		WebElement create_new_credential = driver.findElement(By.id("create_new_credential"));
		create_new_credential.click();

		//Let's put some value in it.

		//URL
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url_credential = driver.findElement(By.id("credential-url"));
		url_credential.click();
		url_credential.sendKeys(url);

		//Username
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement username_credential = driver.findElement(By.id("credential-username"));
		username_credential.click();
		username_credential.sendKeys(userName);

		//Password
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement password_credential = driver.findElement(By.id("credential-password"));
		password_credential.click();
		password_credential.sendKeys(passWord);
		//Save Changes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save_changes_credential")));
		WebElement save_changes_credential = driver.findElement(By.id("save_changes_credential"));
		save_changes_credential.click();
		//Verifies that they are displayed
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement nav_credentials_tab2 = driver.findElement(By.id("nav-credentials-tab"));
		nav_credentials_tab2.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("url_credential")));
		WebElement user_url = driver.findElement(By.id("url_credential"));
		String getting_Url = user_url.getText();
		Assertions.assertNotNull(getting_Url);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username_credential")));
		WebElement user_name = driver.findElement(By.id("username_credential"));
		String getting_name = user_name.getText();
		Assertions.assertNotNull(getting_name);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password_credential")));
		WebElement user_password = driver.findElement(By.id("password_credential"));
		String getting_password = user_password.getText();
		Assertions.assertNotNull(getting_password);

		//Verifies that the displayed password is encrypted
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password_credential")));
//		WebElement encryptedPassword = driver.findElement(By.id("password_credential"));
//		String gettingEncryptedPassword = user_password.getText();
//
//		//Need database value
//		EncryptionService encryptionService = new EncryptionService();
//		String decryptedPassword = encryptionService.decryptValue("2","1");
//
		//t4nha
		//String gettingDecodedSalt =

	}
	//Credential Viewing
	private void credentialViewing(WebDriverWait wait){
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement nav_credentials_tab = driver.findElement(By.id("nav-credentials-tab"));
		nav_credentials_tab.click();
	}
	//Credential Editing
	private void credentialEditing(){

	}
	//Credential Deleting
	private void credentialDeleting(){

	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("redirectToLogin")));
		WebElement gotoLogin = driver.findElement(By.id("redirectToLogin"));
		gotoLogin.click();
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a successful sign-up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the login page.



		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertTrue(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertTrue(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}
}
