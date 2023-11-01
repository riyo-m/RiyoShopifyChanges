package com.vertex.quality.connectors.ariba.connector.pages.misc;

import com.vertex.quality.common.enums.SpecialCharacter;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the page for signing into this configuration site
 * Note- this is the default page that loads when you first access the configuration site
 * with the standard URL
 *
 * @author ssalisbury
 */
public class AribaConnSignOnPage extends AribaConnBasePage
{
	protected final By usernameFieldLoc = By.id("username");
	protected final By passwordFieldLoc = By.id("password");
	protected final By loginButtonLoc = By.id("Login_button");
	protected final By hereButtonLoc = By.xpath("//*/div[@id='loginLink']/h2/strong/a[contains(text(),'here')]");

	public AribaConnSignOnPage( WebDriver driver )
	{
		super(driver, "Vertex" + SpecialCharacter.REGISTERED_TRADEMARK +
					  " Indirect Tax for Procurement for SAP Ariba Login Page");
	}

	/**
	 * beyond validating the title of the main content of the page,
	 * this also verifies the current page's identity as the login page
	 * by checking whether the current page contains username and
	 * password input fields & a login button
	 */
	@Override
	public boolean isCurrentPage( )
	{
		boolean isCurrentPage = super.isCurrentPage();
		clickHereToLogin();
		boolean isUsernameFieldDisplay = isUsernameFieldDisplayedEnabled();
		boolean isPasswordFieldDisplayed = isPasswordFieldDisplayedEnabled();
		boolean isLoginButtonDisplayed = isLoginButtonDisplayedEnabled();

		boolean isCurrPage = (isCurrentPage && isUsernameFieldDisplay && isPasswordFieldDisplayed &&
							  isLoginButtonDisplayed);
		return isCurrPage;
	}

	/**
	 * enters a string into the 'username' text box
	 *
	 * @param username the string that is entered into the 'username' text box
	 *
	 * @author ssalisbury
	 */
	public void enterUsername( final String username )
	{
		WebElement field = wait.waitForElementDisplayed(usernameFieldLoc);
		text.enterText(field, username);
		waitForPageLoad();
	}

	/**
	 * Clicks the "here" link on the initial login screen
	 * */
	public void clickHereToLogin( )
	{
		WebElement hereButton = wait.waitForElementDisplayed(hereButtonLoc);
		click.clickElementCarefully(hereButton);
		waitForPageLoad();
	}

	/**
	 * Checks if the "here" button is available to make sure we were redirected to the login screen
	 * */
	public boolean isLoginAvailable( )
	{

		boolean isHereButtonDisplayed = isHereButtonDisplayedEnabled();

		boolean isCurrPage = (isHereButtonDisplayed);
		return isCurrPage;
	}

	/**
	 * enters a string into the 'password' text box
	 *
	 * @param password the string that is entered into the 'password' text box
	 *
	 * @author ssalisbury
	 */
	public void enterPassword( final String password )
	{
		WebElement field = wait.waitForElementDisplayed(passwordFieldLoc);
		text.enterText(field, password);
		waitForPageLoad();
	}

	/**
	 * enters a string into the 'password' text box and presses enter
	 *
	 * @param password the string that is entered into the 'password' text box
	 *
	 * @author alewis
	 */
	public AribaConnHomePage enterPasswordPressEnter(final String password ) {
		AribaConnHomePage homePage;

		WebElement field = wait.waitForElementDisplayed(passwordFieldLoc);
		text.enterText(field, password);
		waitForPageLoad();
		text.pressEnter(field);

		homePage = initializePageObject(AribaConnHomePage.class);
		return homePage;
	}

	/**
	 * tries to log into this configuration site
	 *
	 * @return a representation of the page that loads right after successfully logging in
	 *
	 * @author ssalisbury
	 */
	public AribaConnHomePage login( )
	{
		AribaConnHomePage homePage;
		try{
			wait.waitForElementDisplayed(By.id("loggedInUser"));
		}catch(org.openqa.selenium.TimeoutException e){
			WebElement button = wait.waitForElementDisplayed(loginButtonLoc);
			click.clickElement(button);
		}



		homePage = initializePageObject(AribaConnHomePage.class);
		return homePage;
	}

	/**
	 * collects the contents of the 'username' text box
	 *
	 * @return the contents of the 'username' text box
	 *
	 * @author ssalisbury
	 */
	public String getUsername( )
	{
		WebElement field = wait.waitForElementDisplayed(usernameFieldLoc);
		String username = text.getElementText(field);

		String retrievedUsernameMessage = String.format("Retrieved Username Value - %s", username);
		VertexLogger.log(retrievedUsernameMessage, getClass());

		return username;
	}

	/**
	 * checks whether the 'username' text box is displayed and enabled
	 *
	 * @return whether the 'username' text box is visible/editable
	 *
	 * @author ssalisbury
	 */
	public boolean isUsernameFieldDisplayedEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(usernameFieldLoc);
		return isDisplayedEnabled;
	}

	/**
	 * checks whether the 'password' text box is displayed and enabled
	 *
	 * @return whether the 'password' text box is visible/editable
	 *
	 * @author ssalisbury
	 */
	public boolean isPasswordFieldDisplayedEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(passwordFieldLoc);
		return isDisplayedEnabled;
	}

	/**
	 * checks whether the login button is displayed and enabled
	 *
	 * @return whether the login button is visible/editable
	 *
	 * @author ssalisbury
	 */
	public boolean isLoginButtonDisplayedEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(loginButtonLoc);
		return isDisplayedEnabled;
	}

	/**
	 * Checks if the "here" button is on the current sceen for later validation
	 * */
	public boolean isHereButtonDisplayedEnabled( )
	{
		boolean isDisplayedEnabled = element.isElementEnabled(hereButtonLoc);
		return isDisplayedEnabled;
	}
}
