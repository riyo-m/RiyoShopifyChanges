package com.vertex.quality.connectors.mirakl.ui.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Login page of Mirakl connector
 *
 * @author rohit.mogane
 */
public class MiraklLoginPage extends VertexPage
{
	public MiraklLoginPage( WebDriver driver )
	{
		super(driver);
	}

	protected By usernameTextInput = By.id("username");
	protected By passwordTextInput = By.id("password");
	protected By signInButton = By.id("submit");
	protected By clickHereToLogin = By.xpath("//b/a[contains(text(),'here')]");
	protected By miraklDevUrl = By.xpath("//*[contains(text(),'vertexfr-dev.mirakl.net')]");
	protected By confirmUrl = By.xpath("//*[@id='confirm-mp-btn']");
	protected By confirmPermissions = By.xpath("//button[contains(text(),'Confirm') and @type='submit']");
	protected By connectorUserNameInput = By.xpath("//*[@id='email-login']");
	protected By connectorPasswordInput = By.xpath("//*[@id='password']");
	protected By nextBtn = By.xpath("//*[@value='Next']");
	protected By signBtn = By.xpath("//*[@name='login']");

	/**
	 * Inputs username for Mirakl connector login
	 *
	 * @param username username to login
	 */
	public void enterUsername( String username )
	{
		WebElement usernameInput = wait.waitForElementDisplayed(usernameTextInput);
		text.clickElementAndEnterText(usernameInput, username);
	}

	/**
	 * Inputs password for Mirakl connector login
	 *
	 * @param password password to login
	 */
	public void enterPassword( String password )
	{
		WebElement passwordInput = wait.waitForElementDisplayed(passwordTextInput);
		text.clickElementAndEnterText(passwordInput, password);
	}

	/**
	 * clicks the login button for the Mirakl connector
	 *
	 * @return Mirakl connector status page object
	 */
	public MiraklConnectorStatusPage clickLogin( )
	{
		WebElement login = wait.waitForElementDisplayed(signInButton);
		click.clickElementCarefully(signInButton);

		MiraklConnectorStatusPage homePage = initializePageObject(MiraklConnectorStatusPage.class);

		return homePage;
	}

	/**
	 * login method for the Mirakl connector
	 *
	 * @param username username to login
	 * @param password password to login
	 *
	 * @return Mirakl connector status page object
	 */
	public MiraklConnectorStatusPage loginAsUser( final String username, final String password )
	{
		MiraklConnectorStatusPage homePage;

		enterUsername(username);
		enterPassword(password);

		homePage = clickLogin();

		return homePage;
	}

	/**
	 * Inputs username for Mirakl connector login
	 *
	 * @param username connector username to authorize
	 */
	public void enterConnectorUsername( String username )
	{
		WebElement usernameInputElement = wait.waitForElementDisplayed(connectorUserNameInput);
		text.clickElementAndEnterText(usernameInputElement, username);
	}

	/**
	 * Inputs password for Mirakl connector login
	 *
	 * @param password connector password to login
	 */
	public void enterConnectorPassword( String password )
	{
		WebElement usernamePasswordElement = wait.waitForElementDisplayed(connectorPasswordInput);
		text.clickElementAndEnterText(usernamePasswordElement, password);
	}

	/**
	 * selects Mirakl Dev url for Mirakl connector
	 */
	public void clickMiraklDevUrl( )
	{
		WebElement miraklDevUrlElement = wait.waitForElementDisplayed(miraklDevUrl);
		click.javascriptClick(miraklDevUrlElement);
	}

	/**
	 * to click confirm url
	 */
	public void clickConfirmUrl( )
	{
		WebElement confirmBtnElement = wait.waitForElementDisplayed(confirmUrl);
		click.javascriptClick(confirmBtnElement);
	}

	/**
	 * to click confirm permissions
	 */
	public void clickConfirmPermissions( )
	{
		WebElement confirmPermissionsElement = wait.waitForElementDisplayed(confirmPermissions);
		click.javascriptClick(confirmPermissionsElement);
	}

	/**
	 * to click on next after confirm url
	 */
	public void clickNext( )
	{
		WebElement nextElement = wait.waitForElementDisplayed(nextBtn);
		click.javascriptClick(nextElement);
	}

	/**
	 * to click on sign in after entering password
	 */
	public void clickSignIn( )
	{
		WebElement signInElement = wait.waitForElementDisplayed(signBtn);
		click.javascriptClick(signInElement);
	}

	/**
	 * to get home page title for Mirakl connector
	 *
	 * @return String title for home page of Mirakl connector
	 */
	public String getHomePageTitle( )
	{
		waitForPageLoad();
		return driver.getTitle();
	}
}