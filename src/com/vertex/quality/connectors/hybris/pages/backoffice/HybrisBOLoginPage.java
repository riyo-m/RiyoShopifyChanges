package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents the page to login to the Hybris Backoffice homepage
 *
 * @author Nagaraju Gampa
 */
public class HybrisBOLoginPage extends HybrisBasePage
{
	public HybrisBOLoginPage( WebDriver driver )
	{
		super(driver);
	}

	protected By USERNAME = By.name("j_username");
	protected By PASSWORD = By.name("j_password");
	protected By LOGIN_BUTTON = By.className("login_btn");

	/**
	 * This method is used to enter User name
	 */
	public void setUsername( String username )
	{
		wait.waitForElementDisplayed(USERNAME);
		text.enterText(USERNAME, username);
	}

	/**
	 * This method is used to enter Password
	 */
	public void setPassword( String password )
	{
		wait.waitForElementDisplayed(PASSWORD);
		text.enterText(PASSWORD, password);
	}

	/**
	 * This method is used to click on Login Button
	 */
	public void clickLogInButton( )
	{
		click.clickElement(LOGIN_BUTTON);
		hybrisWaitForPageLoad();
	}

	/**
	 * This method is to get text of Username field
	 */
	public String getUsername( )
	{
		hybrisWaitForPageLoad();
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
