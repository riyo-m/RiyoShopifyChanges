package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaBasePage;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author saidulu kodadala
 * Login page actions/methods.
 */
public class AcumaticaLoginPage extends AcumaticaBasePage
{
	protected By USERNAME_INPUT = By.cssSelector("[class*='login_user']");
	protected By PASSWORD_INPUT = By.cssSelector("[class*='login_pass']");
	protected By LOGIN_BUTTON = By.id("btnLogin");
	protected By companyField = By.id("cmbCompany");

	public AcumaticaLoginPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Enter user name
	 *
	 * @param username
	 */
	public void setUsername( final String username )
	{
		text.enterText(USERNAME_INPUT, username);
		waitForPageLoad();
	}

	/**
	 * Enter password
	 *
	 * @param password
	 */
	public void setPassword( final String password )
	{
		text.enterText(PASSWORD_INPUT, password);
		waitForPageLoad();
	}

	/**
	 * Enter company
	 *
	 * @param company name
	 */
	public void setCompany( final String company )
	{
		dropdown.selectDropdownByDisplayName(companyField, company);
		waitForPageLoad();
	}

	/***
	 * Click on login button and loads the 'home' page on the site
	 *
	 * @return the home page of the Acumatica site
	 *
	 * @author ssalisbury
	 */
	public AcumaticaPostSignOnPage clickLoginButton( )
	{
		AcumaticaCommunicationHomePage communicationHomePage = null;

		click.clickElement(LOGIN_BUTTON);

		waitForPageLoad();

		try
		{
			wait.wait(4);
		}
		catch ( Exception e ) {

		}

		communicationHomePage = initializePageObject(AcumaticaCommunicationHomePage.class);

		return communicationHomePage;
	}
}
