package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Sign on page for application
 *
 * @author cgajes
 */
public class OracleCloudSignOnPage extends OracleCloudBasePage
{
	protected By usernameField = By.id("userid");
	protected By passwordField = By.id("password");

	protected By loginButtonLocator = By.xpath("//button[@type='submit'][contains(text(), 'Sign In')]");
	protected By accountButtonLocator = By.cssSelector("a[title='Settings and Actions']");
	protected By signOutLinkLocator = By.cssSelector("a[id='_FOpt1:_UISlg1']");
	protected By signOutLinkLocatorSupplierPortal = By.cssSelector("a[id='pt1:_UISlg1']");
	protected By confirmButtonLocator = By.cssSelector("button[id='Confirm']");
	protected By loginButtonUCMLocator = By.xpath("//a[contains(text(), 'Login')]");

	public OracleCloudSignOnPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * enters a string into the 'username' text box
	 *
	 * @param username the string that is entered into the 'username' text box
	 */
	public void enterUsername( String username )
	{
		WebElement field = wait.waitForElementEnabled(usernameField);
		text.clearText(field);
		text.enterText(field, username);
	}

	/**
	 * enters a string into the 'password' text box
	 *
	 * @param password the string that is entered into the 'password' text box
	 */
	public void enterPassword( String password )
	{
		WebElement field = wait.waitForElementEnabled(passwordField);
		text.clearText(field);
		text.enterText(field, password);
	}

	/**
	 * Submits credentials
	 *
	 * @return a representation of the page that loads right after successfully logging in
	 */
	public OracleCloudHomePage pressLoginButton( )
	{
		WebElement loginButton = wait.waitForElementEnabled(loginButtonLocator);
		click.clickElement(loginButton);

		OracleCloudHomePage homePage = initializePageObject(OracleCloudHomePage.class);
		return homePage;
	}

	/**
	 * Clicks on Login button for UCM server
	 *
	 */
	public void pressLoginButtonUCM( )
	{
		WebElement loginButton = wait.waitForElementEnabled(loginButtonUCMLocator);
		click.clickElement(loginButton);
	}

	/**
	 * Logs out of the application
	 */
	public void logOut( )
	{
		WebElement accountButton = wait.waitForElementEnabled(accountButtonLocator);
		click.clickElement(accountButton);

		WebElement signoutLink = wait.waitForElementEnabled(signOutLinkLocator);
		click.clickElement(signoutLink);

		WebElement confirmButton = wait.waitForElementEnabled(confirmButtonLocator);
		click.clickElement(confirmButton);
	}

	/**
	 * Logs out of the Supplier Portal
	 */
	public void logOutSupplierPortal()
	{
		WebElement accountButton = wait.waitForElementEnabled(accountButtonLocator);
		click.clickElement(accountButton);

		WebElement signoutLink = wait.waitForElementEnabled(signOutLinkLocatorSupplierPortal);
		click.clickElement(signoutLink);

		WebElement confirmButton = wait.waitForElementEnabled(confirmButtonLocator);
		click.clickElement(confirmButton);
	}
}
