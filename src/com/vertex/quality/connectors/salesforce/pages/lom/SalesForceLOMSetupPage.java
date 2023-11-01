package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Basic Setup Page.
 *
 * @author brendaj
 */
public class SalesForceLOMSetupPage extends SalesForceBasePage
{
	protected By DESCRIPTION = By.xpath(
		"//label[contains(text(),'Description')]/parent::td/following-sibling::td//input");

	protected By SAVE_BUTTON = By.cssSelector("#bottomButtonRow input[name='save']");
	protected By NEXT_BUTTON = By.cssSelector("input[title = 'Next']");

	public SalesForceLOMSetupPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * To click on next button
	 */
	public void clickNextButton( )
	{
		click.clickElement(NEXT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Enter Description to create Sales Process and Record Type
	 *
	 * @param description
	 */
	public void setDescription( String description )
	{
		wait.waitForElementDisplayed(DESCRIPTION);
		text.enterText(DESCRIPTION, description);
	}

	/**
	 * Click on Save button to complete creation of Sales Process and Record Type
	 */
	public void clickOnSaveButton( )
	{
		wait.waitForElementEnabled(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
		waitForPageLoad();
	}
}
