package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Common functions for anything related to Salesforce Lightning Contacts Page.
 *
 * @author brenda.johnson
 */
public class SalesForceB2BContactsPage extends SalesForceBasePage
{
	protected By ACCOUNT_DROPDOWN_ACTIONS = By.xpath(
		".//force-highlights2/div/div/div/div/runtime_platform_actions-actions-ribbon/ul/li/lightning-button-menu/button/lightning-primitive-icon");
	protected By LOGIN_TO_COMMUNITY_LINK_DROPDOWN = By.xpath("//*[contains(text(),'Log in to Experience')]");
	protected By LOGIN_TO_COMMUNITY_BUTTON = By.xpath(".//button[@name='LoginToNetworkAsUser']");

	SalesForceB2BOpportunityPage opportunityPage;
	SalesForceLB2BPostLogInPage postLogInPage;

	public SalesForceB2BContactsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on Login To Community link
	 */
	public void clickLoginToCommunity( )
	{
		waitForSalesForceLoaded();

		try{
			wait.waitForElementDisplayed(LOGIN_TO_COMMUNITY_BUTTON);
			click.clickElement(LOGIN_TO_COMMUNITY_BUTTON);
		} catch (Exception ex) {
			List<WebElement> elementsList = element.getWebElements(ACCOUNT_DROPDOWN_ACTIONS);

			for (WebElement elementItem : elementsList) {
				if (element.isElementDisplayed(elementItem)) {
					wait.waitForElementDisplayed(elementItem);
					click.javascriptClick(elementItem);
					waitForSalesForceLoaded();
					wait.waitForElementDisplayed(LOGIN_TO_COMMUNITY_LINK_DROPDOWN);
					click.clickElement(LOGIN_TO_COMMUNITY_LINK_DROPDOWN);
					waitForSalesForceLoaded();
					return;
				}
			}
		}
	}

	public void clickSubTab( String subTabName )
	{
		String selector = String.format(
			"//div/div/.//ul/li[contains(@class, 'slds-sub-tabs__item')]/a/span[contains(text(), '%s')]", subTabName);
		try
		{
			wait.waitForElementDisplayed(By.xpath(selector), 15);
			click.clickElement(By.xpath(selector));
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void clickContact( String contact )
	{
		String selector = String.format(
			".//div/div/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr/th/span/a[@title='%s']", contact);
		wait.waitForElementDisplayed(By.xpath(selector));
		click.clickElement(By.xpath(selector));
		waitForSalesForceLoaded();
	}

	/**
	 * click login to Store
	 *
	 * @param storeName
	 */
	public void clickLoginToStore( String storeName )
	{
		String selector = String.format(
			"//div[contains(@class, 'modal-body')]/div/ul/li/a/span[contains(text(), '%s')]", storeName);
		wait.waitForElementDisplayed(By.xpath(selector));
		click.clickElement(By.xpath(selector));

	}
}
