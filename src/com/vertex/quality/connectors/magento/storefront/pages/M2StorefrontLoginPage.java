package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the page for signing into this storefront site Note- this
 * is the default page that loads when you click the sign in button on the
 * Homepage
 *
 * @author alewis
 */
public class M2StorefrontLoginPage extends MagentoStorefrontPage
{
	protected By emailFieldID = By.id("email");
	protected By passwordFieldID = By.id("pass");
	protected By signInButtonID = By.id("send2");

	public M2StorefrontLoginPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * enters a string into the 'username' text box
	 *
	 * @param username the string that is entered into the 'username' text box
	 */
	public void enterUsername( String username )
	{
		WebElement field = driver.findElement(emailFieldID);
		field.clear();
		field.sendKeys(username);
	}

	/**
	 * enters a string into the 'password' text box
	 *
	 * @param password the string that is entered into the 'username' text box
	 */
	public void enterPassword( String password )
	{
		WebElement field = driver.findElement(passwordFieldID);
		field.clear();
		field.sendKeys(password);
	}

	/**
	 * clicks the Sign In Button
	 *
	 * @return Magento Storefront Homepage
	 */
	public M2StorefrontHomePage clickSignInButton( )
	{
		WebElement signIn = driver.findElement(signInButtonID);
		signIn.click();
		M2StorefrontHomePage homepage = initializePageObject(M2StorefrontHomePage.class);
		return homepage;
	}

	/**
	 * Log In to MagentoTap 244 environment store
	 * @param user Login username
	 * @param pwd  Login password
	 */
	public void signInToMagentoTap244Store(String user, String pwd) {
		waitForPageLoad();
		waitForSpinnerToBeDisappeared();
		enterUsername(user);
		enterPassword(pwd);
		clickSignInButton();
	}
}