package com.vertex.quality.connectors.dynamics365.retail.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceHomePage;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class DRetailLoginPage extends DFinanceBasePage
{

	protected By USERNAME_INPUT = By.name("loginfmt");
	protected By PASSWORD_INPUT = By.name("passwd");
	protected By CLOSE_BUTTON = By.xpath("closeDialogButton");
	protected By CLOSE_TUTORIAL = By.xpath("//div[@class='bubble-close']");
	protected By LOGIN_BUTTON = By.cssSelector("[id*='idSIButton']");
	protected By DYNAMICS365_ROOTLINK = By.id("rootLcsLnk");
	protected By SECOND_NEXTBUTTON = By.xpath("//button[@data-ax-bubble='guidedActivationView_nextButton']");
	protected By FIRST_NEXTBUTTON = By.id("nextButton");
	protected By FIRST_SUBMIT = By.xpath("//input[@type='submit']");
	protected By SECOND_SUBMIT = By.xpath("//input[@type='submit']");
	protected By THIRD_SUBMIT = By.xpath("//input[@type='submit']");
	protected By STORE_DROPDOWN = By.xpath("//select[@id='storeSelector']");
	protected By STORE_SELECT = By.xpath("//select[@id='storeSelector']/option[contains(text(), 'Houston')]");
	protected By THIRD_NEXTBUTTON = By.id("nextButton");
	protected By STORE_REGISTER_DROPDOWN = By.xpath("//select[@id='registerAndDeviceSelector']");
	protected By SELECT_STORE_REGISTER = By.xpath("//select[@id='registerAndDeviceSelector']/option[contains(text(), 'HOUSTON-17 : HOUSTON-17 : Activated')]");
	protected By ACTIVATE_BUTTON = By.id("activateButton");
	protected By CONTINUE_BUTTON = By.id("retryActivationButton");
	protected By OPERATOR_ID = By.id("operatorTextBox");
	protected By OPERATOR_PWD = By.id("passwordBox");
	protected By SIGNIN_BUTTON = By.id("signInButton");
	protected By SIGNOUT = By.xpath("//div[@data-test-id=\"svcvtest@vertexinc.com\"]");



	public DRetailLoginPage( WebDriver driver )
	{
		super(driver);
	}

	final String musername = "svcvtest@vertexinc.com";
	final String mpassword = "C0nnect0r@ut0";


	/** Activate device HOUSTON-17 in Houston store  */

	public void activateStore( )
	{
		wait.waitForElementDisplayed(FIRST_NEXTBUTTON);
		click.clickElementCarefully(FIRST_NEXTBUTTON);
		List<WebElement> buttonList = wait.waitForAllElementsPresent(SECOND_NEXTBUTTON);
		WebElement secondNext = buttonList.get(buttonList.size() - 1);
		click.clickElementCarefully(secondNext);
		wait.waitForElementDisplayed(USERNAME_INPUT);
		click.clickElementCarefully(USERNAME_INPUT);
		text.enterText(USERNAME_INPUT, musername);
		wait.waitForElementDisplayed(FIRST_SUBMIT);
		click.clickElementCarefully(FIRST_SUBMIT);

		wait.waitForElementDisplayed(PASSWORD_INPUT);
		click.clickElementCarefully(PASSWORD_INPUT);
		text.enterText(PASSWORD_INPUT, mpassword);
		wait.waitForElementDisplayed(SECOND_SUBMIT);
		click.clickElementCarefully(SECOND_SUBMIT);

		wait.waitForElementDisplayed(THIRD_SUBMIT);
		click.clickElementCarefully(THIRD_SUBMIT);
		wait.waitForElementDisplayed(STORE_DROPDOWN);
		click.clickElementCarefully(STORE_DROPDOWN);
		wait.waitForElementDisplayed(STORE_SELECT);
		click.clickElementCarefully(STORE_SELECT);

		wait.waitForElementDisplayed(THIRD_NEXTBUTTON);
		click.clickElementCarefully(THIRD_NEXTBUTTON);
		wait.waitForElementDisplayed(STORE_REGISTER_DROPDOWN);
		click.clickElementCarefully(STORE_REGISTER_DROPDOWN);
		wait.waitForElementDisplayed(SELECT_STORE_REGISTER);
		click.clickElementCarefully(SELECT_STORE_REGISTER);

		wait.waitForElementDisplayed(ACTIVATE_BUTTON);
		click.clickElementCarefully(ACTIVATE_BUTTON);
		wait.waitForElementDisplayed(CONTINUE_BUTTON);
		click.clickElementCarefully(CONTINUE_BUTTON);
		wait.waitForElementDisplayed(SIGNOUT);
		click.clickElementCarefully(SIGNOUT);

		waitForPageLoad();

	}

	/**
	 * Go to activated device in given store
	 * @param storeName
	 */
	public void activateStore(String storeName) {
		wait.waitForElementDisplayed(FIRST_NEXTBUTTON);
		click.clickElementCarefully(FIRST_NEXTBUTTON);
		List<WebElement> buttonList = wait.waitForAllElementsPresent(SECOND_NEXTBUTTON);
		WebElement secondNext = buttonList.get(buttonList.size() - 1);
		click.clickElementCarefully(secondNext);
		wait.waitForElementDisplayed(USERNAME_INPUT);
		click.clickElementCarefully(USERNAME_INPUT);
		text.enterText(USERNAME_INPUT, musername);
		wait.waitForElementDisplayed(FIRST_SUBMIT);
		click.clickElementCarefully(FIRST_SUBMIT);

		wait.waitForElementDisplayed(PASSWORD_INPUT);
		click.clickElementCarefully(PASSWORD_INPUT);
		text.enterText(PASSWORD_INPUT, mpassword);
		wait.waitForElementDisplayed(SECOND_SUBMIT);
		click.clickElementCarefully(SECOND_SUBMIT);

		wait.waitForElementDisplayed(THIRD_SUBMIT);
		click.clickElementCarefully(THIRD_SUBMIT);
		selectStore(storeName);

		wait.waitForElementDisplayed(THIRD_NEXTBUTTON);
		click.clickElementCarefully(THIRD_NEXTBUTTON);
		selectActivatedDevice();

		wait.waitForElementDisplayed(ACTIVATE_BUTTON);
		click.clickElementCarefully(ACTIVATE_BUTTON);
		wait.waitForElementDisplayed(CONTINUE_BUTTON);
		click.clickElementCarefully(CONTINUE_BUTTON);
		wait.waitForElementDisplayed(SIGNOUT);
		click.clickElementCarefully(SIGNOUT);

		waitForPageLoad();
	}

	/**
	 * Select store for CPOS
	 * @param name
	 */
	public void selectStore(String name) {
		wait.waitForElementDisplayed(STORE_DROPDOWN);
		click.clickElementCarefully(STORE_DROPDOWN);

		By storeOption = By.xpath(String.format("//select[@id='storeSelector']/option[contains(text(), '%s')]", name));
		wait.waitForElementDisplayed(storeOption);
		click.clickElementCarefully(storeOption);

		waitForPageLoad();
	}

	/**
	 * Select activated device for store
	 */
	public void selectActivatedDevice() {
		wait.waitForElementDisplayed(STORE_REGISTER_DROPDOWN);
		click.clickElementCarefully(STORE_REGISTER_DROPDOWN);

		By deviceOption = By.xpath("//select[@id='registerAndDeviceSelector']/option[contains(text(), 'Activated')]");
		wait.waitForElementDisplayed(deviceOption);
		click.clickElementCarefully(deviceOption);

		waitForPageLoad();
	}

	/**
	 * Click on 'Sign In' button for navigating to login page
	 */
	public void clickOnSignInButton( )
	{
		wait.waitForElementDisplayed(SIGNIN_BUTTON);
		click.clickElement(SIGNIN_BUTTON);
		waitForPageLoad();
	}

	/***
	 * Enter user name
	 * @param username
	 */
	public void setUsername( final String username )
	{
		wait.waitForElementDisplayed(OPERATOR_ID);
		text.enterText(OPERATOR_ID, username);
		waitForPageLoad();
	}

	/**
	 * Close the initial dialogue box
	 */
	public void closeButton( )
	{
		if(element.isElementDisplayed(CLOSE_BUTTON))
		{
			click.clickElementCarefully(CLOSE_BUTTON);
		}
		waitForPageLoad();
	}

	/**
	 * Close the tutorial dialogue
	 */
	public void closeTutorial( )
	{
		click.clickElementCarefully(CLOSE_TUTORIAL);
	}

	/***
	 * Enter password
	 * @param password
	 */
	public void setPassword( final String password )
	{
		wait.waitForElementEnabled(OPERATOR_PWD);
		text.enterText(OPERATOR_PWD, password);
		waitForPageLoad();
	}

	/***
	 * Click on login button
	 */
	public void clickLoginButton( )
	{
		//TODO probably not neeeded
		VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE);

		click.clickElement(SIGNIN_BUTTON);
		waitForPageLoad();
	}
}