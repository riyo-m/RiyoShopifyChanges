package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the create account page
 *
 * @author alewis
 */
public class M2StorefrontCreateAccountPage extends MagentoStorefrontPage
{
	protected By firstNameId = By.id("firstname");
	protected By lastNameId = By.id("lastname");

	protected By emailId = By.id("email_address");
	protected By passwordId = By.id("password");
	protected By passwordConfirmId = By.id("password-confirmation");

	protected By createAccountButtonClass = By.className("submit");

	public M2StorefrontCreateAccountPage( WebDriver driver ) { super(driver); }

	/**
	 * inputs the first name for the customer
	 *
	 * @param firstName
	 */
	public void inputFirstName( String firstName )
	{
		WebElement field = wait.waitForElementEnabled(firstNameId);

		field.sendKeys(firstName);
	}

	/**
	 * inputs the last name for the customer
	 *
	 * @param lastName
	 */
	public void inputLastName( String lastName )
	{
		WebElement field = wait.waitForElementEnabled(lastNameId);

		field.sendKeys(lastName);
	}

	/**
	 * inputs the email/username for the new account
	 *
	 * @param email
	 */
	public void inputEmail( String email )
	{
		WebElement field = wait.waitForElementEnabled(emailId);

		field.sendKeys(email);
	}

	/**
	 * inputs the password for the new account
	 *
	 * @param password
	 */
	public void inputPassword( String password )
	{
		WebElement field = wait.waitForElementEnabled(passwordId);

		field.sendKeys(password);
	}

	/**
	 * confirms the password
	 *
	 * @param password
	 */
	public void inputPasswordConfirmation( String password )
	{
		WebElement field = wait.waitForElementEnabled(passwordConfirmId);

		field.sendKeys(password);
	}

	/**
	 * clicks the Create Account button
	 *
	 * @return my account page
	 */
	public M2StorefrontMyAccountPage clickCreateAccountButton( )
	{
		WebElement button = wait.waitForElementEnabled(createAccountButtonClass);

		click.clickElementCarefully(button);

		M2StorefrontMyAccountPage myAccountPage = initializePageObject(M2StorefrontMyAccountPage.class);

		return myAccountPage;
	}
}
