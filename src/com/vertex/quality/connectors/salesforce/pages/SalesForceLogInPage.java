package com.vertex.quality.connectors.salesforce.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.lb2b.SalesForceLB2BPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.lom.SalesForceLOMPostLogInPage;
import com.vertex.quality.connectors.salesforce.pages.ob2b.SalesForceOB2BPostLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Login Page.
 *
 * @author
 */
public class SalesForceLogInPage extends SalesForceBasePage
{
	protected By USERNAME_INPUT = By.id("username");
	protected By PASSWORD_INPUT = By.id("password");
	protected By LOGIN_BUTTON = By.id("Login");

	public SalesForceLogInPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * To Set Username
	 *
	 * @param username
	 */
	public void setUsername( String username )
	{
		text.enterText(USERNAME_INPUT, username);
		waitForPageLoad();
	}

	/**
	 * To Set password
	 *
	 * @param password
	 */
	public void setPassword( String password )
	{
		text.enterText(PASSWORD_INPUT, password);
		waitForPageLoad();
	}

	/**
	 * Click on Login button
	 */
	public void clickLoginButton( )
	{
		VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE, SalesForceLogInPage.class);
		click.clickElement(LOGIN_BUTTON);
		wait.waitForElementNotDisplayed(LOGIN_BUTTON, DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * Login as B2B user
	 *
	 * @param username
	 * @param password
	 */
	public SalesForceLB2BPostLogInPage logInAsLB2BUser( String username, String password )
	{
		setUsername(username);
		setPassword(password);
		clickLoginButton();
		SalesForceLB2BPostLogInPage postLogInPage = initializePageObject(SalesForceLB2BPostLogInPage.class);
		return postLogInPage;
	}

	/**
	 * Login as LOM user
	 *
	 * @param username
	 * @param password
	 */
	public SalesForceLOMPostLogInPage logInAsLOMUser( String username, String password )
	{
		setUsername(username);
		setPassword(password);
		clickLoginButton();
		SalesForceLOMPostLogInPage postLogInPage = initializePageObject(SalesForceLOMPostLogInPage.class);
		return postLogInPage;
	}

	/**
	 * Login as CRM User
	 *
	 * @param username
	 * @param password
	 */
	public SalesForceCPQPostLogInPage logInAsCPQUser( String username, String password )
	{
		setUsername(username);
		setPassword(password);
		clickLoginButton();
		SalesForceCPQPostLogInPage postLogInPage = initializePageObject(SalesForceCPQPostLogInPage.class);
		return postLogInPage;
	}

	/**
	 * Login as CRM User
	 *
	 * @param username
	 * @param password
	 */
	public SalesForceCRMPostLogInPage logInAsCRMUser( String username, String password )
	{
		setUsername(username);
		setPassword(password);
		clickLoginButton();
		SalesForceCRMPostLogInPage postLogInPage = initializePageObject(SalesForceCRMPostLogInPage.class);
		return postLogInPage;
	}

	/**
	 * Login as OB2B User
	 *
	 * @param username
	 * @param password
	 */
	public SalesForceOB2BPostLogInPage logInAsOB2BUser( String username, String password )
	{
		setUsername(username);
		setPassword(password);
		clickLoginButton();
		SalesForceOB2BPostLogInPage postLogInPage = initializePageObject(SalesForceOB2BPostLogInPage.class);
		return postLogInPage;
	}
}
