package com.vertex.quality.connectors.ariba.supplier.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents the supplier sign on page that has the username and password fields necessary to login
 * it contains all the methods necessary to interact with the page to login and create test cases
 *
 * @author osabha
 */
public class AribaSupplierSignOnPage extends AribaSupplierBasePage
{
	public AribaSupplierSignOnPage( WebDriver driver )
	{
		super(driver);
	}

	protected By usernameField = By.cssSelector("input[name='UserName']");
	protected By passwordField = By.id("Password");
	protected By loginButtonLocator = By.className("w-login-form-input-btn-space");

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

	public void enterUsername( String username )
	{
		WebElement usernameTextfield = wait.waitForElementPresent(usernameField);
		usernameTextfield.clear();
		usernameTextfield.sendKeys(username);
	}

	public void enterPassword( String password )
	{
		WebElement passwordTextField = wait.waitForElementPresent(passwordField);
		passwordTextField.clear();
		passwordTextField.sendKeys(password);
	}

	public AribaSupplierHomePage clickLoginButton( )
	{
		WebElement loginButton = wait.waitForElementPresent(loginButtonLocator);

		loginButton.click();
		waitForPageLoad();

		return new AribaSupplierHomePage(driver);
	}

	/**
	 * Helper method to perform a complete sign on
	 *
	 * @param username the username to sign on as
	 * @param password the password to sign in with
	 *
	 * @return the resulting Supplier Home pae
	 */
	public AribaSupplierHomePage loginAsUser( final String username, final String password )
	{
		AribaSupplierHomePage supplierHomePage;

		enterUsername(username);
		enterPassword(password);

		supplierHomePage = clickLoginButton();

		return supplierHomePage;
	}
}
