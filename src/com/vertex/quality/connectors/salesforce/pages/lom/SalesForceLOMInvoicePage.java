package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SalesForceLOMInvoicePage extends SalesForceBasePage
{
	protected By FIRST_ORDER_RECORD = By.xpath(".//div[1]/div/div/ul/li[1]/a");
	protected By DETAILS_TAB = By.xpath(
		".//h1/div[text()='Invoice']/../../../../../../../../../.././/div/div/div/div/ul/li/a/span[text()='Details']");
	protected By RELATED_TAB = By.xpath("//*[@id=\"relatedListsTab__item\"][@aria-controls='tab-5']");
	protected By RECENTLY_VIEWED = By.xpath(
		".//div/div/div/div[2]/div/div[1]/div[2]/div[1]/span[@title='Recently Viewed']");

	protected By MORE_ACTIONS_DROPDOWN = By.xpath(
		".//h1/div[text()='Fulfillment Order']/../../../../../../../../../../..//*[contains(@title,'actions')]");
	protected By EDIT_ACTION = By.xpath(".//div/ul/li/a[@title='Edit']");

	protected By CASE_STATUS_DROPDOWN = By.xpath(".//span[text()='Status']/../../div/div/div/div/a");
	protected By CREATE_FULFILMENT_ORDER = By.xpath(".//div/ul/li/a[@title='Create Fulfillment Order']");
	protected By SET_FULFILLED = By.xpath(".//div/ul/li/a[@title='Fulfilled']");

	protected By SAVE_BUTTON = By.xpath(".//div/div/div[2]/div/div/div[2]/button/span[text()='Save']");

	SalesForceLOMPostLogInPage postLogInPage;

	public SalesForceLOMInvoicePage( WebDriver driver )
	{
		super(driver);
		postLogInPage = new SalesForceLOMPostLogInPage(driver);
	}

	/**
	 * Navigate to first order in All Orders
	 */
	public void navigateToFirstOrder( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(FIRST_ORDER_RECORD);
		click.javascriptClick(FIRST_ORDER_RECORD);
		waitForSalesForceLoaded();
	}

	/**
	 * Navigate to Details of order
	 */
	public void navigateToDetails( )
	{
		wait.waitForElementDisplayed(RECENTLY_VIEWED);
		wait.waitForElementDisplayed(DETAILS_TAB);
		click.clickElement(DETAILS_TAB);
		waitForSalesForceLoaded();
	}

	/**
	 * Navigate to Related of order
	 */
	public void navigateToRelated( )
	{
		wait.waitForElementDisplayed(RECENTLY_VIEWED);
		wait.waitForElementDisplayed(RELATED_TAB);
		click.clickElement(RELATED_TAB);
		waitForSalesForceLoaded();
	}

	/**
	 * select Item from list
	 *
	 * @param name
	 */
	public void selectItem( String name )
	{
		String selectItem = String.format(".//div/div/table/tbody/tr/th/span/a[@title='%s']", name);
		wait.waitForElementDisplayed(By.xpath(selectItem));
		click.clickElement(By.xpath(selectItem));
		waitForSalesForceLoaded();
	}

	/**
	 * Get estimated tax field with currency hardcoded to 'USD'
	 *
	 * @return estimated tax
	 */
	public String getEstimatedTax(){
		return getEstimatedTax("USD");
	}

	/**
	 * get Estimated Tax field with specific currency code
	 * @param isoCurrencyCode currency used for Order
	 *
	 * @return estimated Tax
	 */
	public String getEstimatedTax(String isoCurrencyCode)
	{
		String totalTaxLocator = String.format(".//h3/button/span[text()='Invoice Amount Information']/../../..//span[text()='Vertex Tax Total']/../..//*[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_TAX = By.xpath(totalTaxLocator);
		int i = 0;
		while (!element.isElementPresent(TOTAL_TAX) && i < 5)
		{
			refreshPage();
			waitForSalesForceLoaded(2000);
			i++;
		}

		wait.waitForElementEnabled(TOTAL_TAX);
		String sectionText = text.getElementText(TOTAL_TAX);
		return sectionText;
	}

	/**
	 * get Total with Tax field with currency hardcoded to 'USD'
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax()
	{
		return getTotalWithTax("USD");
	}

	/**
	 * get Total with Tax field based on specified currency
	 * @param isoCurrencyCode currency used for order
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax(String isoCurrencyCode)
	{
		String totalWithTaxLocator = String.format(".//h3/button/span[text()='Invoice Amount Information']/../../..//span[text()='Total with Tax']/../..//*[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_WITH_TAX = By.xpath(totalWithTaxLocator);
		wait.waitForElementDisplayed(TOTAL_WITH_TAX);
		wait.waitForElementEnabled(TOTAL_WITH_TAX);
		String sectionText = text.getElementText(TOTAL_WITH_TAX);
		return sectionText;
	}

	/**
	 * edit current case
	 */
	private void editCase( )
	{
		wait.waitForElementDisplayed(MORE_ACTIONS_DROPDOWN);
		click.clickElement(MORE_ACTIONS_DROPDOWN);
		wait.waitForElementDisplayed(EDIT_ACTION);
		click.clickElement(EDIT_ACTION);
	}
}
