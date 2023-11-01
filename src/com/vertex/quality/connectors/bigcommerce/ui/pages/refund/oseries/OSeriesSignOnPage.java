package com.vertex.quality.connectors.bigcommerce.ui.pages.refund.oseries;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the admin sign on page and contains all the methods necessary to interact with it.
 *
 * @author rohit-mogane
 */
public class OSeriesSignOnPage extends OSeriesBasePage
{
	public OSeriesSignOnPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By userNameTextField = By.xpath("//input[@id='username']");
	protected final By passwordTextField = By.xpath("//input[@id='password']");
	protected final By signInButton = By.xpath("//button[@id='Login_button']");
	protected final By changePassword = By.xpath("//a[@id='changePasswordLink']");
	protected final By homeMenuLink = By.xpath("//*[contains(text(),'Home')]");

	/**
	 * locates and clicks the login button to log into the site
	 *
	 * @return new instance of the O-Series login page
	 */

	public OSeriesSignOnPage clickLoginButton( )
	{
		WebElement loginButton = wait.waitForElementEnabled(signInButton);
		click.clickElement(loginButton);
		OSeriesSignOnPage loginPage = initializePageObject(OSeriesSignOnPage.class);
		return loginPage;
	}

	/**
	 * checks to see if the username field is present and displayed
	 *
	 * @return whether the username field is present and displayed
	 */
	public boolean isUserNameFiledDisplayed( )
	{
		boolean isUserNameFieldPresent = element.isElementDisplayed(userNameTextField);
		return isUserNameFieldPresent;
	}

	/**
	 * checks to see if the username field is present and displayed
	 *
	 * @return whether the home option field is present and displayed
	 */
	public boolean isHomeMenuLinkDisplayed( )
	{
		boolean isHomeMenuLinkPresent = element.isElementDisplayed(homeMenuLink);
		return isHomeMenuLinkPresent;
	}

	/**
	 * checks to see if the password field is present and displayed
	 *
	 * @return whether the password field is present and displayed
	 */
	public boolean isPasswordFieldDisplayed( )
	{
		boolean isPasswordFieldPresent = element.isElementDisplayed(passwordTextField);
		return isPasswordFieldPresent;
	}

	/**
	 * locates the password field WebElement and then clears it and sends in the password
	 *
	 * @param password the password string which will be entered in order to log in
	 */
	public void enterPassword( String password )
	{
		WebElement passwordField = wait.waitForElementEnabled(passwordTextField);
		text.enterText(passwordField, password);
	}

	/**
	 * locates the username field and then clears it and types in the user name
	 *
	 * @param username the username string which will be entered in order to log in
	 */
	public void enterUserName( String username )
	{
		WebElement userNameField = wait.waitForElementEnabled(userNameTextField);
		text.enterText(userNameField, username);
	}
}
