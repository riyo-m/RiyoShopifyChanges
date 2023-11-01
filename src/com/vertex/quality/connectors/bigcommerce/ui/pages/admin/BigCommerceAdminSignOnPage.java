package com.vertex.quality.connectors.bigcommerce.ui.pages.admin;

import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.base.BigCommerceAdminBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the admin sign on page and contains all the methods necessary to interact with it.
 *
 * @author osabha
 */
public class BigCommerceAdminSignOnPage extends BigCommerceAdminBasePage
{
	public BigCommerceAdminSignOnPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By usernameField = By.id("user_email");
	protected final By passwordField = By.id("user_password");
	protected final By loginButtonLocator = By.className("login-form-button");

	/**
	 * locates and clicks the login button to log into the site
	 *
	 * @return new instance of the BigCommerce admin home page
	 */
	public BigCommerceAdminHomePage clickLoginButton( )
	{
		WebElement loginButton = wait.waitForElementEnabled(loginButtonLocator);

		click.clickElement(loginButton);

		BigCommerceAdminHomePage homePage = initializePageObject(BigCommerceAdminHomePage.class);

		return homePage;
	}

	/**
	 * checks to see if the username field is present and displayed
	 *
	 * @return whether the username field is present and displayed
	 */
	public boolean isUsernameFieldDisplayed( )
	{
		boolean isUsernameFieldPresent = element.isElementDisplayed(usernameField);

		return isUsernameFieldPresent;
	}

	/**
	 * checks to see if the password field is present and displayed
	 *
	 * @return whether the password field is present and displayed
	 */
	public boolean isPasswordFieldDisplayed( )
	{
		boolean isPasswordFieldPresent = element.isElementDisplayed(passwordField);

		return isPasswordFieldPresent;
	}

	/**
	 * locates the password field WebElement and then clears it and sends in the password
	 *
	 * @param password the password string which will be entered in order to log in
	 */
	public void enterPassword( final String password )
	{
		WebElement passwordTextField = wait.waitForElementEnabled(passwordField);
		text.enterText(passwordTextField, password);
	}

	/**
	 * locates the username field and then clears it and types in the user name
	 *
	 * @param username the username string which will be entered in order to log in
	 */
	public void enterUsername( final String username )
	{
		WebElement usernameTextField = wait.waitForElementEnabled(usernameField);
		text.enterText(usernameTextField, username);
	}
}
