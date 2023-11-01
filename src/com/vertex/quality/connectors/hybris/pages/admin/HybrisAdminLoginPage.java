package com.vertex.quality.connectors.hybris.pages.admin;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Login Page to Logsinto Hybris Admin page
 * i.e. page is to perform operations on Admin Login page and logs into Hybris Admin page
 *
 * @author Nagaraju Gampa
 */
public class HybrisAdminLoginPage extends HybrisBasePage
{
	public HybrisAdminLoginPage( WebDriver driver )
	{
		super(driver);
	}

	protected By USERNAME = By.name("j_username");
	protected By PASSWORD = By.name("j_password");
	protected By LOGIN_BUTTON = By.className("button");

	/**
	 * This method is used to enter User name
	 */
	public void setUsername( String username )
	{
		wait.waitForElementDisplayed(USERNAME);
		text.clearText(USERNAME);
		text.enterText(USERNAME, username);
	}

	/**
	 * This method is used to enter Password
	 */
	public void setPassword( String password )
	{
		wait.waitForElementDisplayed(PASSWORD);
		text.clearText(PASSWORD);
		text.enterText(PASSWORD, password);
	}

	/**
	 * This method is used to click on Login Button
	 */
	public void clickLoginButton( )
	{
		click.clickElement(LOGIN_BUTTON);
		waitForPageLoad();
	}

	/**
	 * This method is to get text of Username field
	 */
	public String getUsername( )
	{
		waitForPageLoad();
		final String text = this.text.getElementText(USERNAME);
		return text;
	}

	/**
	 * This method is used to verify the presence of Username field
	 */
	public boolean isUsernamePresent( )
	{
		boolean flag = false;
		if ( element.isElementDisplayed(USERNAME) )
		{
			flag = true;
		}
		return flag;
	}
}
