package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents all the pages accessed through the login process
 *
 * @author osabha
 */
public class BusinessSignOnPage extends VertexPage
{
	public BusinessSignOnPage( WebDriver driver )
	{
		super(driver);
	}

	protected By startFreeButtonLoc = By.id("cta-start-free-connect");
	protected By emailAddressFieldLoc = By.className("placeholderContainer");
	protected By signUpButtonLoc = By.className("mpl-button-span-rectangle-DynamicsOps");
	protected By textTitleLoc = By.className("text-title");
	protected By staySignedInNoButtonLoc = By.id("idBtn_Back");
	protected By staySignedInYesButtonLoc = By.className("btn-primary");
	protected By primaryClass = By.className("btn-primary");
	protected By passwordFieldLoc = By.className("form-control");
	protected By signInButtonLoc = By.id("idSIButton9");
	protected By emailInnerFieldLoc = By.className("ltr_override");

	/**
	 * navigates through the login pages until it get to the email address (username) field and enters
	 * the environment username
	 */
	public void enterEmailAddress( String username )
	{
		WebElement emailAddressField = wait.waitForElementEnabled(emailInnerFieldLoc);
		text.enterText(emailAddressField, username);
		WebElement nextButton = wait.waitForElementEnabled(signInButtonLoc);
		click.clickElement(nextButton);

		wait.waitForElementNotDisplayedOrStale(emailAddressField, 5);
	}

	/**
	 * navigates to the password field and enters the environment password
	 */
	public void enterPassword( String password )
	{
		WebElement passwordField = wait.waitForElementPresent(passwordFieldLoc);
		text.enterText(passwordField, password);
		WebElement signInButton = wait.waitForElementPresent(signInButtonLoc);
		click.clickElement(signInButton);

		wait.waitForElementNotDisplayedOrStale(passwordField, 5);
	}

	/**
	 * when prompted on whether to stay signed in to reduce the number of times asked to log in,
	 * click the No button
	 *
	 * @return business admin home page
	 */
	public BusinessAdminHomePage clickDoNotStayLoggedIn( )
	{
		WebElement noButton = wait.waitForElementEnabled(staySignedInNoButtonLoc);
		click.clickElement(noButton);
		return initializePageObject(BusinessAdminHomePage.class);
	}
}

