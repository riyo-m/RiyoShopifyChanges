package com.vertex.quality.connectors.orocommerce.pages.storefront;

import com.vertex.quality.connectors.orocommerce.pages.base.OroStoreFrontBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the login page of the Oro Commerce environment
 * contains all the functions needed to login
 *
 * @author alewis
 */
public class OroStoreFrontLoginPage extends OroStoreFrontBasePage
{

	protected final By USERNAME = By.id("userNameSignIn");
	protected final By PASSWORD = By.id("passwordSignIn");
	protected final By SIGNIN = By.cssSelector("input[value='Sign In']");
	protected final By SIGN_IN_LINK = By.xpath(".//a[normalize-space(.)='Sign In']");
    protected final By cookiesPopup = By.xpath(".//div[@class='cookie-banner-view show']");
    protected final By yesAccept = By.xpath(".//button[normalize-space(.)='Yes, Accept']");
    protected final By close = By.xpath(".//button[@title='Close']");

	public OroStoreFrontLoginPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Enter the specified username
	 *
	 * @param username the username to attempt to sign in as
	 */
	public void enterUsername( final String username )
	{
		wait.waitForElementDisplayed(USERNAME);
		text.enterText(USERNAME, username);
	}

	/**
	 * Enter the specified Password
	 *
	 * @param password the password to attempt to sign in with
	 */
	public void enterPassword( final String password )
	{
		/* Type the given text into the password field */
		wait.waitForElementDisplayed(PASSWORD);
		text.enterText(PASSWORD, password);
	}

	/**
	 * click signIn button while storefront login.
	 *
	 * @return in stance of the Oro Commerce HomePage
	 */
	public OroStoreFrontHomePage clickSignIntButton( )
	{
		OroStoreFrontHomePage homePage;
		WebElement submitButton = wait.waitForElementEnabled(SIGNIN);
		click.performDoubleClick(submitButton);
		jsWaiter.sleep(1000);
		waitForPageLoad();
		homePage = initializePageObject(OroStoreFrontHomePage.class);
		return homePage;
	}

	/**
	 * Helper method to perform a complete sign on
	 *
	 * @param username the username to sign on as
	 * @param password the password to sign in with
	 *
	 * @return the resulting Home Page
	 */
	public OroStoreFrontHomePage loginAsUser( final String username, final String password )
	{
		OroStoreFrontHomePage homePage;
		WebElement signIn = wait.waitForElementPresent(SIGN_IN_LINK);
		click.javascriptClick(signIn);
		waitForPageLoad();
        handleCookiesPopup(true);
		enterUsername(username);
		enterPassword(password);
		homePage = clickSignIntButton();
		return homePage;
	}

    /**
     * Handle cookies pop-up if it is appeared.
     *
     * @param accept pass true to accept cookies & false to cancel pop-up.
     */
	public void handleCookiesPopup(boolean accept) {
		waitForPageLoad();
        if (element.isElementPresent(cookiesPopup)) {
            WebElement cookiePopup = wait.waitForElementPresent(cookiesPopup);
            WebElement acceptCookies = wait.waitForElementPresent(yesAccept, cookiePopup);
            WebElement cancelCookiesPopup = wait.waitForElementPresent(close, cookiePopup);
            if (accept) {
                click.javascriptClick(acceptCookies);
            } else {
                click.javascriptClick(cancelCookiesPopup);
            }
        }
	}
}
