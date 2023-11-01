package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * a generic representation of a CommerceTools SignOn Page.
 *
 * @author vivek-kumar
 */
public class CommerceToolsSignOnPage extends CommerceToolsBasePage
{

	public CommerceToolsSignOnPage( WebDriver driver )
	{

		super(driver);
	}

	protected final By emailIdTextField = By.xpath("//input[@id='text-field-1']");
	protected final By passwordTextField = By.name("password");
	protected final By loginButton = By.xpath("//button[@type='submit']");
	/**
	 * enter the email id in login ui
	 * @param emailID
	 */
	public void enterEmailID( final String emailID )
	{
		WebElement emailIDField = wait.waitForElementEnabled(emailIdTextField);
		text.enterText(emailIDField, emailID);
	}
	/**
	 * enter the password in login ui
	 * @param password
	 */
	public void enterPassword( final String password )
	{
		WebElement passwordField = wait.waitForElementEnabled(passwordTextField);
		text.enterText(passwordField, password);
	}
	/**
	 * click on sign in button.
	 * @return
	 */
	public CommerceToolsSignOnPage clickLoginButton( )
	{
		WebElement loginButtonField = wait.waitForElementEnabled(loginButton);
		click.moveToElementAndClick(loginButtonField);
		CommerceToolsSignOnPage CommerceToolLoginPage = initializePageObject(CommerceToolsSignOnPage.class);
		return CommerceToolLoginPage;
	}
}
