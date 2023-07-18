package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
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
		creating_verifying_Notes();
	}

	//Point - 1: Write a test that creates a note, and verifies it is displayed.
	private void creating_verifying_Notes(){
		WebDriverWait wait = new WebDriverWait(driver,2);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement nav_notes_tab = driver.findElement(By.id("nav-notes-tab"));
		nav_notes_tab.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("open-notes-form")));
		WebElement open_notes_form = driver.findElement(By.id("open-notes-form"));
		open_notes_form.click();

		//Title
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement note_title = driver.findElement(By.id("note-title"));
		note_title.click();
		note_title.sendKeys("SuperDuperNote");
		//Description
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement note_description = driver.findElement(By.id("note-description"));
		note_description.click();
		note_description.sendKeys("Lets write super duper notes!");
		//Save Changes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-submitting")));
		WebElement noteSubmit = driver.findElement(By.id("note-submitting"));
		noteSubmit.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement nav_notes_tab2 = driver.findElement(By.id("nav-notes-tab"));
		nav_notes_tab2.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_title_test")));
		WebElement note_title_test = driver.findElement(By.id("note_title_test"));
		String getThe_Title = note_title_test.getText();

		Assertions.assertEquals("SuperDuperNote", getThe_Title);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_description_test")));
		WebElement note_description_test = driver.findElement(By.id("note_description_test"));
		String getThe_description = note_description_test.getText();

		Assertions.assertEquals("Lets write super duper notes!", getThe_description);
	}
	//Point - 2: Write a test that edits an existing note and verifies that the changes are displayed.
	//Point - 3: Write a test that deletes a note and verifies that the note is no longer displayed.

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
