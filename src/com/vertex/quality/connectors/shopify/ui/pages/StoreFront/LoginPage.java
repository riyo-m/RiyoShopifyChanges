package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends ShopifyPage
{

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public LoginPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By loginNav = By.xpath("//li[@class='leading-[0]']/preceding::a[text()='Log in']");
	protected By emailText = By.id("account_email");
	protected By continueWithEmail = By.name("commit");
	protected By passwordText = By.id("account_password");
	protected By loginBtn = By.name("commit");

	public void loginNewNavigation( )
	{
		waitForPageLoad();
		click.clickElement(loginNav);
	}

	public void enterEmail( String email )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(emailText), email);
	}

	public void enterContinue( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		click.clickElement(continueWithEmail);
	}

	public void enterPass( String pass )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(passwordText), pass);
	}

	public void loginButton( )
	{
		waitForPageLoad();
		click.clickElement(loginBtn);
	}

	public void loginTheApplication( String email, String pass ) throws InterruptedException
	{
		enterEmail(email);
		enterContinue();
		enterPass(pass);
		loginButton();
	}
}
