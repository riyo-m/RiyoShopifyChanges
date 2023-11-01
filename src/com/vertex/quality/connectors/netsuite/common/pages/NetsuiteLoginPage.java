package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * represents the page to sign onto the Netsuite homepage
 *
 * @author hho
 */
public class NetsuiteLoginPage extends NetsuitePage
{
	protected By usernameField = By.id("email");
	protected By passwordField = By.id("password");
	protected By loginButton = By.id("submitButton");

	public NetsuiteLoginPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * inputs "username" into the username text field
	 *
	 * @param username string inputted into the text field
	 */
	public void enterUsername( String username )
	{
		text.enterText(usernameField, username);
	}

	/**
	 * inputs "password" into the password text field
	 *
	 * @param password string inputted into the text field
	 */
	public void enterPassword( String password )
	{
		text.enterText(passwordField, password);
	}

	/**
	 * Attempts to sign on and gets sent to the authentication page
	 *
	 * @return the authentication page check after login
	 */
	public NetsuiteAuthenticationPage loginToAuthPage( )
	{

		click.clickElement(loginButton);
		waitForPageLoad();
		return initializePageObject(NetsuiteAuthenticationPage.class);
	}

	/**
	 * Attempts to sign on using stored cookies
	 *
	 * @return the homepage after login
	 */
	public NetsuiteHomepage quickLogin( )
	{
		click.clickElement(loginButton);
		return initializePageObject(NetsuiteHomepage.class);
	}
}
