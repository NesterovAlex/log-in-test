package com.semantic_ua.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import static java.lang.System.lineSeparator;

class LoginPageTest {

	private WebDriver driver;
	private LoginPage loginPage;

	@BeforeEach
	void setUp() {
		ChromeDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(LoginPage.URL);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	void givenLoginPage_whenGetTitle_thenExpectedTitle() {
		String actual = driver.getTitle();
		String expected = "Login Example - Semantic";

		assertEquals(expected, actual);
	}

	@Test
	void givenLoginPage_whenTypeInvalidEmail_thenExpectedErrorTextReturned() {
		loginPage.registerCreds("malibu87", "SomePassword");
		String expected = "Please enter a valid e-mail";
		String actual = loginPage.getValidEmailErrorElementText();
		assertEquals(expected, actual);
	}

	@Test
	void givenLoginPage_whenTypeInvalidPassword_thenExpectedErrorTextReturned() {
		loginPage.registerCreds("malibu87@gmail.com", "Some");
		String expected = "Your password must be at least 6 characters";
		String actual = loginPage.getValidPasswordErrorElementText();

		assertEquals(expected, actual);
	}

	@Test
	void givenLoginPage_whenTypeInvalidPasswordAndEmail_thenExpectedErrorTextReturned() {
		loginPage.registerCreds("malibu87", "Some");
		String expected = "Please enter a valid e-mail" + lineSeparator()
				+ "Your password must be at least 6 characters";
		String actual = loginPage.getValidEmailErrorElementText() + lineSeparator()
				+ loginPage.getValidPasswordErrorElementText();

		assertEquals(expected, actual);
	}

	@Test
	void givenLoginPage_whenTypeWithoutPasswordAndInvalidEmail_thenExpectedErrorTextReturned() {
		loginPage.registerWithoutEmail("SomePassword");
		String expected = "Please enter your e-mail" + lineSeparator() + "Please enter a valid e-mail";
		String actual = loginPage.getEmailEnteredErrorElementText() + lineSeparator()
				+ loginPage.getValidEmailErrorElementText();

		assertEquals(expected, actual);
	}

	@Test
	void givenLoginPage_whenClickLoginWithoutEnteredCreds_thenExpectedErrorTextReturned() {
		loginPage.clickLoginButton();
		String expected = "Please enter your e-mail" + lineSeparator() + "Please enter a valid e-mail" + lineSeparator()
				+ "Please enter your password" + "Your password must be at least 6 characters";
		String actual = loginPage.getEmailEnteredErrorElementText() + lineSeparator()
				+ loginPage.getValidEmailErrorElementText() + lineSeparator()
				+ loginPage.getTypePasswordErrorElementText() + loginPage.getValidPasswordErrorElementText();

		assertEquals(expected, actual);
	}

	@Test
	void givenLoginPage_whenClickLoginWithCurrentCreds_thenExpectedUrlReturned() {
		String email = "malibu8770@gmail.com";
		String password = "SomePassword";
		String loginPageUrl = loginPage.getCurrentUrl();
		String expected = loginPageUrl + "?email=" + email.replace("@", "%40") + "&password=" + password;
		loginPage.typeEmail(email);
		loginPage.typePassword(password);
		loginPage.clickLoginButton();
		String actual = loginPage.getCurrentUrl();
		
		assertEquals(expected, actual);
	}

	@AfterEach
	void tearDown() {
		driver.quit();
	}
}
