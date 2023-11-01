package com.vertex.quality.connectors.netsuite.scis.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetsuiteSCISLoginPage extends VertexPage
{
	protected By email = By.id("email");
	protected By passwordField = By.id("password");
	protected By loginButton = By.className("login-register-submit");

	public NetsuiteSCISLoginPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * inputs "email" into the email text field
	 *
	 * @param username string inputted into the text field
	 */
	public void enterEmail( String username )
	{
		text.enterText(email, username);
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
	 * Attempts to sign on and gets sent to the store page
	 *
	 * @return the store page after login
	 */
	public NetsuiteSCISStorePage loginToStorePage( )
	{
		click.clickElement(loginButton);
		return initializePageObject(NetsuiteSCISStorePage.class);
	}
}
