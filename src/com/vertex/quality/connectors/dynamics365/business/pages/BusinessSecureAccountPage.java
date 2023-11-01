package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the secure account page, which will prompt the user
 * to enter account verification/recovery methods
 *
 * @author cgajes
 */
public class BusinessSecureAccountPage extends BusinessBasePage
{
	protected By cancelButtonLoc = By.id("CancelLinkButton");

	public BusinessSecureAccountPage( WebDriver driver ) { super(driver); }

	/**
	 * clicks the cancel button to return to the sign on process
	 *
	 * @return sign on page
	 */
	public BusinessSignOnPage clickCancel( )
	{
		WebElement cancelButton = wait.waitForElementEnabled(cancelButtonLoc);
		click.clickElement(cancelButton);
		wait.waitForElementNotDisplayedOrStale(cancelButton, 10);

		return initializePageObject(BusinessSignOnPage.class);
	}
}
