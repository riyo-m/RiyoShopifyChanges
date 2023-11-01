package com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the login to dev cloud and its methods
 *
 * @author rohit.mogane
 */
public class OseriesCloudLoginPage extends OseriesCloudBasePage
{

	public OseriesCloudLoginPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By userNameTextField = By.xpath("//input[@name='username']");
	protected final By continueButton = By.xpath("//*[contains(text(),'Continue')]");
	protected final By passwordTextField = By.xpath("//input[@name='password']");
	protected final By loginButton = By.xpath("//button[@type='submit']");
	protected final By userIcon = By.xpath("//a[@href='#account']");
	protected final By logoutButton = By.id("logout");

	/**
	 * locates the username field and then clears it and types in the user name
	 *
	 * @param username the username string which will be entered in order to log in
	 */
	public void enterUserName( String username )
	{
		WebElement usernameField = wait.waitForElementEnabled(userNameTextField);
		text.enterText(usernameField, username);
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
	 * This method to click continue button after entering username and the  after password
	 */
	public void clickContinue( )
	{
		WebElement btnContinue = wait.waitForElementEnabled(continueButton);
		click.clickElement(btnContinue);
	}

	/**
	 * locates and clicks the login button to log into the site
	 *
	 * @return new instance of the login page on dev cloud.
	 */
	public OseriesCloudLoginPage clickLoginButton( )
	{
		WebElement signInButton = wait.waitForElementEnabled(loginButton);
		click.clickElement(signInButton);
		OseriesCloudLoginPage devLoginPage = initializePageObject(OseriesCloudLoginPage.class);
		return devLoginPage;
	}

	/**
	 * locates and click on logout button to log out from dev cloud.
	 */
	public void clickLogoutButton( )
	{
		WebElement userIconField = wait.waitForElementEnabled(userIcon);
		click.clickElement(userIconField);
		WebElement logoutButtonField = wait.waitForElementEnabled(logoutButton);
		click.clickElement(logoutButtonField);
	}
}
