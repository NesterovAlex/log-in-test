package com.semantic_ua.test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	public static final String URL = "https://semantic-ui.com/examples/login.html";
	
	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//input[@placeholder='E-mail address']")
	private WebElement emailField;
	
	@FindBy(xpath = "//input[@placeholder='Password']")
	private WebElement passwordField;
	
	@FindBy(xpath = "//div[@class='ui fluid large teal submit button']")
	private WebElement loginButton;
	
	@FindBy(xpath = "//a[contains(text(),'Sign Up')]")
	private WebElement signUpButton;
	
	@FindBy(xpath = "//li[contains(text(),'Your password must be at least 6 characters')]")
	private WebElement validPasswordErrorElement;
	
	@FindBy(xpath = "//li[contains(text(),'Please enter a valid e-mail')]")
	private WebElement validEmailErrorElement;
	
	@FindBy(xpath = "//div[@class='ui error message']")
	private WebElement basicErrorTextElement;
	
	@FindBy(xpath = "//li[contains(text(),'Please enter your e-mail')]")
	private WebElement emailEnteredErrorTextElement;
	
	@FindBy(xpath = "//li[contains(text(),'Please enter your password')]")
	private WebElement typePasswordErrorTextElement;
	
	public LoginPage typeEmail(String email) {
		emailField.sendKeys(email);
		return this;
	}
	
	public LoginPage typePassword(String email) {
		passwordField.sendKeys(email);
		return this;
	}
	
	public void clickLoginButton() {
		 JavascriptExecutor js = (JavascriptExecutor)driver;	
		 js.executeScript("arguments[0].click();", loginButton);
	}
	
	public LoginPage registerCreds(String email, String password) {
		this.typeEmail(email);
		this.typePassword(password);
		this.clickLoginButton();
		return new LoginPage(driver);
	}
	
	public LoginPage registerWithoutEmail(String password) {
		this.typePassword(password);
		this.clickLoginButton();
		return new LoginPage(driver);
	}
	
	public String getValidPasswordErrorElementText() {
		return validPasswordErrorElement.getText();
	}
	
	public String getValidEmailErrorElementText() {
		return validEmailErrorElement.getText();
	}
	
	public String getBasicTextErrorElementText() {
		return basicErrorTextElement.getText();
	}
	
	public String getEmailEnteredErrorElementText() {
		return emailEnteredErrorTextElement.getText();
	}
	
	public String getTypePasswordErrorElementText() {
		return typePasswordErrorTextElement.getText();
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

}
