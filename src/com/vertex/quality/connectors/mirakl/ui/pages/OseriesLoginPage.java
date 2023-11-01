package com.vertex.quality.connectors.mirakl.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Login page actions of O-Series
 *
 * @author rohit.mogane
 */
public class OseriesLoginPage extends MiraklHomePage
{

	public OseriesLoginPage( final WebDriver driver )
	{
		super(driver);
	}

	protected final By oseriesUsername = By.xpath("//input[@id='username']");
	protected final By oseriesPassword = By.xpath("//input[@id='password']");
	protected final By oseriesSignInButton = By.xpath("//button[@id='Login_button']");

	/**
	 * locates and clicks the login button to log onto the O-Series
	 */
	public void oseriesLogin( )
	{
		WebElement loginButton = wait.waitForElementEnabled(oseriesSignInButton);
		click.clickElement(loginButton);
	}

	/**
	 * locates the password field WebElement and then clears it and sends in the password
	 *
	 * @param password the password string which will be entered in order to log in
	 */
	public void oseriesEnterPassword( String password )
	{
		WebElement passwordField = wait.waitForElementEnabled(oseriesPassword);
		text.enterText(passwordField, password);
	}

	/**
	 * locates the username field and then clears it and types in the user name
	 *
	 * @param username the username string which will be entered in order to log in
	 */
	public void oseriesEnterUsername( String username )
	{
		waitForPageLoad();
		WebElement userNameField = wait.waitForElementEnabled(oseriesUsername);
		text.enterText(userNameField, username);
	}
}
