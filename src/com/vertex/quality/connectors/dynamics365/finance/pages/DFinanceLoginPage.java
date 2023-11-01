package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Login page common methods and object declaration page
 *
 * @author Saidulu Kodadala
 */
public class DFinanceLoginPage extends DFinanceBasePage
{
	//TODO fix these locators
	protected By USERNAME_INPUT = By.name("loginfmt");
	protected By PASSWORD_INPUT = By.name("passwd");
	protected By SIGNIN_BUTTON = By.cssSelector("[class='loginLink'] [class='lnkAnkr']");
	protected By NEXT_BUTTON = By.cssSelector("[id*='idSIButton']");
	protected By LOGIN_BUTTON = By.cssSelector("[id*='idSIButton']");
	protected By YES_BUTTON = By.cssSelector("[id*='idSIButton9']");
	protected By DYNAMICS365_ROOTLINK = By.id("rootLcsLnk");
	protected By USER_PROFILE_BUTTON = By.id("UserBtn");

	public DFinanceLoginPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on 'Sign In' button for navigating to login page
	 */
	public void clickOnSignInButton( )
	{
		wait.waitForElementDisplayed(SIGNIN_BUTTON);
		click.clickElement(SIGNIN_BUTTON);
		waitForPageLoad();
	}

	/***
	 * Enter user name
	 * @param username
	 */
	public void setUsername( final String username )
	{
		wait.waitForElementDisplayed(USERNAME_INPUT);
		text.enterText(USERNAME_INPUT, username);
		waitForPageLoad();
	}

	/**
	 * Click on next button
	 */
	public void clickNextButton( )
	{
		wait.waitForElementDisplayed(NEXT_BUTTON);
		click.clickElement(NEXT_BUTTON);
		waitForPageLoad();
	}

	/***
	 * Enter password
	 * @param password
	 */
	public void setPassword( final String password )
	{
		wait.waitForElementDisplayed(LOGIN_BUTTON);
		wait.waitForElementEnabled(PASSWORD_INPUT);
		text.enterText(PASSWORD_INPUT, password);
		waitForPageLoad();
	}

	/***
	 * Click on login button
	 */
	public void clickLoginButton( )
	{
		//TODO probably not neeeded
		VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE);

		click.clickElement(LOGIN_BUTTON);
		waitForPageLoad();
	}

	/***
	 * Click on yes button
	 *
	 * @return the home page of the d365 finance site after signing in
	 */
	public DFinanceHomePage clickYesButton( )
	{
		DFinanceHomePage homePage = null;
		wait.waitForElementDisplayed(YES_BUTTON);
		click.clickElement(YES_BUTTON);
		homePage = initializePageObject(DFinanceHomePage.class);
		return homePage;
	}

	/**
	 * Returns boolean that is true if there is already a logged in user; false otherwise
	 * @return loggedIn
	 */
	public boolean isLoggedIn() {
		waitForPageLoad();
		boolean loggedIn = element.isElementDisplayed(USER_PROFILE_BUTTON);

		return loggedIn;
	}
}

