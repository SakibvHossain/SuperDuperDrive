package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controller.HomeController;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	//After that on HomePage two methods have to be created:
	//1. checkForCredentialEncryptedPassword
	//2. getCredentialTableRowAlternative

	@LocalServerPort
	private int port;


	private WebDriver driver;
	private CredentialPage credentialPage;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private WebDriverWait wait;
	@Autowired
	private CredentialService credentialService;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		credentialPage = new CredentialPage(driver, credentialService);
		signupPage = new SignupPage(driver);
		loginPage = new LoginPage(driver);
		wait = new WebDriverWait(driver,2);
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
		driver.get("http://localhost:"+this.port+"/signup");
		signupPage.userRegistration("sk","sk","sk","sk");
		//Login
		loginPage.login("sk","sk");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_button")));
		WebElement logout_button = driver.findElement(By.id("logout_button"));
		logout_button.click();

		wait.until(ExpectedConditions.titleContains("Login"));
		driver.get("http://localhost:"+this.port+"/home");
		wait.until(ExpectedConditions.titleContains("Login"));
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

	//Universal login signup method for testing :)
	private void signup_Login(String firstName, String lastName, String userName, String password){
		wait = new WebDriverWait(driver,2);
		driver.get("http://localhost:"+this.port+"/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));

		//Signup
		signupPage.userRegistration(firstName,lastName,userName,password);
		//Login
		loginPage.login(userName,password);
		//Checking if this is the home page we have redirected
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
		wait = new WebDriverWait(driver,2);
		driver.get("http://localhost:"+this.port+"/signup");
		//Signup
		signupPage.userRegistration("ds","ds","ds","ds");
		//Login
		loginPage.login("ds","ds");
		wait.until(ExpectedConditions.titleContains("Home"));
		credentialCreation();
	}

	//Credential Creation
	private void credentialCreation(){
		credentialPage.creatingCredential();
		credentialPage.verifying_Credentials_Displayed();
		credentialPage.Views_existing_set_of_Credentials_and_password_unencrypted();
		credentialPage.delete_Credential();
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
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		//login
		loginPage.login(userName,password);
		wait.until(ExpectedConditions.titleContains("Home"));

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
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage.userRegistration("Redirection","Test","RT","123");
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
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		// Create a test account
		signupPage.userRegistration("URL","Test","UT","123");
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
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		// Create a test account
		signupPage.userRegistration("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
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
