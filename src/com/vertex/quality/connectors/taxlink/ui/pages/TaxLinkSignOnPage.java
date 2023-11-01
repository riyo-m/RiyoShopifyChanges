package com.vertex.quality.connectors.taxlink.ui.pages;

import com.vertex.quality.connectors.taxlink.ui.base.TaxLinkBasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

/**
 * This class contains Login function for TaxLink UI
 *
 * @author mgaikwad
 */

public class TaxLinkSignOnPage extends TaxLinkBasePage
{

	public TaxLinkSignOnPage( WebDriver driver )
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@id='username']")
	private WebElement userNameTextField;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement passwordTextField;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//a[@href='/myclient/settings']")
	private WebElement settings;

	@FindBy(xpath = "//ul[@class='nav nav-list nav-submenu settings']")
	private WebElement settingsSubMenu;

	@FindBy(xpath = "//a[@href='/adapters']")
	private WebElement viewAllConnectors;

	@FindBy(xpath = "//a[@href='/Adapters/OracleErpCloudPortal']")
	private WebElement acceleratorLink;

	@FindBy(xpath = "//button[@id='ssoLogin']")
	private WebElement entLoginButtonORA901;

	@FindBy(xpath = "//input[@inputmode='email']")
	private WebElement entLoginEmailORA901;

	@FindBy(xpath = "//input[@id='okta-signin-username']")
	private WebElement userNameTextFieldORA901;

	@FindBy(xpath = "//input[@id='okta-signin-password']")
	private WebElement passwordTextFieldORA901;

	@FindBy(xpath = "//input[@id='okta-signin-submit']")
	private WebElement entSignInButtonORA901;

	@FindBy(xpath = "//a[@href='/vertex-tl-web-ui/logout']")
	private WebElement logoutButton;

	/**
	 * @param emailID
	 * @param password
	 *
	 * @return
	 */
	public void userLogin( String emailID, String password )
	{
		WebDriverWait expWait = new WebDriverWait(driver, 150);
		expWait.until(ExpectedConditions.visibilityOfAllElements(userNameTextField));
		wait.waitForElementDisplayed(userNameTextField);
		text.enterText(userNameTextField, emailID);
		wait.waitForElementDisplayed(passwordTextField);
		text.enterText(passwordTextField, password);
		click.clickElement(loginButton);
	}

	/**
	 * Method to login to the VCS
	 *
	 * @param emailID
	 * @param password
	 *
	 * @return
	 */
	public void userLogin_VCS( String emailID, String password )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(userNameTextField));
		wait.waitForElementDisplayed(userNameTextField);
		text.enterText(userNameTextField, emailID);
		click.clickElement(loginButton);
		wait.waitForElementDisplayed(passwordTextField);
		text.enterText(passwordTextField, password);
		click.clickElement(loginButton);
	}

	/**
	 * Method to navigate through VCS to
	 * Launch accelerator on stage environment
	 */
	public void launchStageAccelerator( )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(settings));

		actionPageDown
			.moveToElement(settings)
			.perform();
		if ( settingsSubMenu.isDisplayed() )
		{
			expWait.until(ExpectedConditions.visibilityOfAllElements(viewAllConnectors));
			actionPageDown
				.moveToElement(viewAllConnectors)
				.click()
				.perform();
			jsWaiter.sleep(10000);

			click.clickElement(acceleratorLink);
			jsWaiter.sleep(5000);
			ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
			driver
				.switchTo()
				.window(tabs.get(1));
		}
	}

	/**
	 * Method to click on the Enterprise login button
	 * on the ORA901 VOD environment
	 *
	 */
	public void clickOnEntLoginButton()
	{
		wait.waitForElementDisplayed(entLoginButtonORA901);
		click.clickElement(entLoginButtonORA901);
	}

	/**
	 * Method to login to the ORA901 VOD environment
	 * with primary email address
	 *
	 * @param emailID
	 */
	public void entLoginWithEmailORA901( String emailID )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(entLoginEmailORA901));
		text.enterText(entLoginEmailORA901, emailID);
		click.clickElement(loginButton);
	}

	/**
	 * Method to login to the ORA901 VOD environment
	 *
	 * @param emailID
	 * @param password
	 *
	 * @return
	 */
	public void userLogin_VOD_ORA901( String emailID, String password )
	{
		expWait.until(ExpectedConditions.visibilityOfAllElements(userNameTextFieldORA901));
		wait.waitForElementDisplayed(userNameTextFieldORA901);
		text.enterText(userNameTextFieldORA901, emailID);
		wait.waitForElementDisplayed(passwordTextFieldORA901);
		text.enterText(passwordTextFieldORA901, password);
		click.clickElement(entSignInButtonORA901);
	}

	/**
	 * Method to check if user has been logged out
	 * from Taxlink Application and login page is visible
	 *
	 * @return loggedOutFlag
	 */
	public boolean userLogout()
	{
		boolean loggedOutFlag = false;
		expWait.until(ExpectedConditions.visibilityOf(logoutButton));
		click.clickElement(logoutButton);
		expWait.until(ExpectedConditions.visibilityOf(userNameTextField));
		if(userNameTextField.isDisplayed())
		{
			loggedOutFlag = true;
		}
		return loggedOutFlag;
	}
}
