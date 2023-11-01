package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the sign on page for the test environment
 * contains all the code necessary to interact with the different fields and buttons on the page to
 * login and access the home page
 *
 * @author DPatel
 */
public class WorkdaySignOnPage extends VertexPage
{
	protected By usernameField = By.xpath("//input[@aria-label= 'Username' ]");
	protected By passwordField = By.xpath("//input[@aria-label= 'Password' ]");
	protected By loginButtonLocator = By.xpath("//button[@data-automation-id='goButton']");

	public WorkdaySignOnPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * locates and clicks the login button
	 *
	 * @return new instance of the workday admin home page
	 */
	public WorkdayHomePage clickLoginButton( )
	{
		WebElement loginButton = wait.waitForElementPresent(loginButtonLocator);
		loginButton.click();
		waitForPageLoad();
		return new WorkdayHomePage(driver);
	}

	/**
	 * checks to see if the username field is present
	 *
	 * @return boolean value
	 */
	public boolean isUsernameFieldPresent( )
	{
		boolean isUsernameFieldPresent;

		try
		{
			driver.findElement(usernameField);
			isUsernameFieldPresent = true;
		}
		catch ( Exception e )
		{
			isUsernameFieldPresent = false;
		}

		return isUsernameFieldPresent;
	}

	/**
	 * checks to see if the password field is present
	 *
	 * @return boolean value
	 */
	public boolean isPasswordFieldPresent( )
	{
		boolean isPasswordFieldPresent = false;

		try
		{
			driver.findElement(passwordField);
			isPasswordFieldPresent = true;
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return isPasswordFieldPresent;
	}

	/**
	 * locates the password field WebElement and then clears it and sends in the password
	 *
	 * @param password enter password
	 */
	public void enterPassword( String password )
	{
		WebElement passwordTextField = wait.waitForElementPresent(passwordField);
		text.enterText(passwordTextField, password);
	}

	/**
	 * locates the username field and then clears it and types in the user name
	 *
	 * @param username enter username
	 */
	public void enterUsername( String username )
	{
		WebElement usernameTextField = wait.waitForElementPresent(usernameField);
		text.enterText(usernameTextField, username);
	}

	/**
	 * Helper method to perform a complete sign on
	 *
	 * @param username the username to sign on as
	 * @param password the password to sign in with
	 *
	 * @return the resulting workday homepage
	 */
	public WorkdayHomePage loginAsUser( final String username, final String password )
	{
		WorkdayHomePage homePage;
		enterUsername(username);
		enterPassword(password);
		homePage = clickLoginButton();
		return homePage;
	}
}
