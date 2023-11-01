package com.vertex.quality.connectors.ariba.portal.pages.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page object reepresenting the login page for the Ariba portal site
 *
 * @auther dgorecki, ssalisbury
 */
public class AribaPortalLoginPage extends AribaPortalBasePage
{
	protected final By USERNAME = By.id("UserName");
	protected final By PASSWORD = By.id("Password");
	protected final By SUBMIT = By.cssSelector("input[title='Login to Ariba']");

	public AribaPortalLoginPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Enter the specified username
	 *
	 * @param username the username to attempt to sign in as
	 */
	public void enterUsername( final String username )
	{
		wait.waitForElementDisplayed(USERNAME);
		text.enterText(USERNAME, username);
	}

	/**
	 * Enter the specified Password
	 *
	 * @param password the password to attempt to sign in with
	 */
	public void enterPassword( final String password )
	{
		/* Type the given text into the password field */
		wait.waitForElementDisplayed(PASSWORD);
		text.enterText(PASSWORD, password);
	}

	/**
	 * Clicks the login button and returns the resulting AribaPortalHomePage
	 *
	 * @return in stance of the AribaPortalHomePage
	 */
	public AribaPortalPostLoginBasePage clickSubmitButton( )
	{
		AribaPortalPostLoginBasePage resultingPage;

		WebElement submitButton = wait.waitForElementEnabled(SUBMIT);
		click.clickElement(submitButton);

		resultingPage = initializePageObject(AribaPortalHomePage.class);
		return resultingPage;
	}

	/**
	 * Helper method to perform a complete sign on
	 *
	 * @param username the username to sign on as
	 * @param password the password to sign in with
	 *
	 * @return the resulting Portal Dashboard pae
	 */
	public AribaPortalPostLoginBasePage loginAsUser( final String username, final String password )
	{
		AribaPortalPostLoginBasePage loggedInDashboardPage;

		enterUsername(username);
		enterPassword(password);
		loggedInDashboardPage = clickSubmitButton();

		return loggedInDashboardPage;
	}
}
