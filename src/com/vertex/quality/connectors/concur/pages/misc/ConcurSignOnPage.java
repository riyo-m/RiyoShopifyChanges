package com.vertex.quality.connectors.concur.pages.misc;

import com.vertex.quality.connectors.concur.pages.base.ConcurBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the sign on page for SAP Concur
 *
 * @author alewis
 */
public class ConcurSignOnPage extends ConcurBasePage
{
	protected final By usernameFieldLoc = By.id("username-input");
	protected final By passwordFieldLoc = By.id("password");
	protected final By loginButtonLoc = By.id("btnSubmit");

	public ConcurSignOnPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * enters a string into the 'username' text box
	 *
	 * @param username the string that is entered into the 'username' text box
	 */
	public void enterUsername( final String username )
	{
		WebElement field = wait.waitForElementEnabled(usernameFieldLoc);

		text.enterText(field, username);

		WebElement nextButton = wait.waitForElementDisplayed(loginButtonLoc);

		click.clickElementCarefully(nextButton);

		waitForPageLoad();
	}

	/**
	 * enters a string into the 'password' text box
	 *
	 * @param password the string that is entered into the 'password' text box
	 */
	public ConcurHomePage enterPassword( final String password )
	{
		WebElement field = wait.waitForElementEnabled(passwordFieldLoc);

		text.enterText(field, password);

		WebElement nextButton = wait.waitForElementDisplayed(loginButtonLoc);

		click.clickElementCarefully(nextButton);

		waitForPageLoad();

		ConcurHomePage homePage = initializePageObject(ConcurHomePage.class);

		return homePage;
	}

	/**
	 * clicks login button on login page
	 *
	 * @return returns homepage that is loaded
	 */
	public ConcurHomePage login( )
	{
		ConcurHomePage homePage = null;

		WebElement button = wait.waitForElementEnabled(loginButtonLoc);
		click.clickElement(button);

		homePage = initializePageObject(ConcurHomePage.class);
		return homePage;
	}

	/**
	 * enters username and password and signs into page
	 *
	 * @param signInUsername concur login username
	 * @param signInPassword concur login password
	 *
	 * @return home page loaded after login button is clicked
	 */
	public ConcurHomePage loginToConcurHomePage( final String signInUsername, final String signInPassword )
	{
		ConcurHomePage homePage = new ConcurHomePage(driver);
		enterUsername(signInUsername);
		enterPassword(signInPassword);

		return homePage;
	}

	/**
	 * checks if the username field is enabled
	 *
	 * @return true if username field is enabled and false otherwise
	 */
	public boolean isUsernameFieldEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(usernameFieldLoc);
		return isDisplayedEnabled;
	}

	/**
	 * checks if the password field is enabled
	 *
	 * @return true if password field is enabled and false otherwise
	 */
	public boolean isPasswordFieldEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(passwordFieldLoc);
		return isDisplayedEnabled;
	}

	/**
	 * checks if the login button is enabled
	 *
	 * @return true if login button is enabled and false otherwise
	 */
	public boolean isLoginButtonEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(loginButtonLoc);
		return isDisplayedEnabled;
	}
}
