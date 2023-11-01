package com.vertex.quality.connectors.sitecore.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Site-core login page class (common for both administration & store-front
 * applications)
 *
 * @author Shiva Mothkula, Daniel Bondi
 */

public class SitecoreLogInPage extends SitecoreBasePage
{

	protected By usernameInput = By.id("UserName");
	protected By passwordInput = By.id("Password");
	protected By loginButton = By.cssSelector("[class*='btn btn-primary'][type='submit']");
	protected By confirmStoreFrontLoginLoc = By.className("navbar-brand");
	protected By confirmLoginLoc = By.className("sc-list");

	public SitecoreLogInPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * This method is used to enter the user name in the login page
	 *
	 * @param username to enter
	 */
	public void enterUsername( final String username )
	{

		wait.waitForElementDisplayed(usernameInput);
		text.enterText(usernameInput, username);

		waitForPageLoad();
	}

	/**
	 * This method is used to enter the password
	 *
	 * @param password to enter
	 */
	public void enterPassword( final String password )
	{

		text.enterText(passwordInput, password);

		waitForPageLoad();
	}

	/**
	 * This method is used to click the login button
	 */
	public void clickLoginButtonStorefront( )
	{

		VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE, SitecoreAdminHomePage.class);

		click.clickElement(loginButton);

		wait.tryWaitForElementEnabled(confirmStoreFrontLoginLoc, FIVE_SECOND_TIMEOUT);
	}

	/**
	 * This method is used to click the login button
	 */
	public void clickLoginButton( )
	{

		VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE, SitecoreAdminHomePage.class);

		click.clickElement(loginButton);

		wait.tryWaitForElementEnabled(confirmLoginLoc, FIVE_SECOND_TIMEOUT);

	}
}
