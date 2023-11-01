package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Released Product page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceReleasedProductsPage extends DFinanceBasePage
{
	protected By SEARCH_INPUT = By.name("QuickFilter_Input");
	protected By QUICK_FILTER = By.xpath("//div[contains(@class,'quickFilter-dropdown')]//li");
	protected By SAVE_BUTTON = By.name("SystemDefinedSaveButton");
	protected By DELETE_BUTTON = By.cssSelector(".button-label[id*='DeleteButton']");

	protected By PURCHASE_HEADER = By.xpath("//div[contains(@id,'TabPagePurchase_header')]/button");
	protected By VENDOR_INPUT = By.name("PurchaseAdministration_PrimaryVendorId");

	//tab element
	protected String TAB_LOCATOR = "*[type='button'][id*='%s'][id*='productdetails'][id*='button']";

	public DFinanceReleasedProductsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on new released product button
	 */
	public DFinanceNewReleasedProductPage clickNewCustomerButton( )
	{
		wait.waitForElementEnabled(NEW_BUTTON);
		click.clickElement(NEW_BUTTON);
		waitForPageLoad();

		DFinanceNewReleasedProductPage newReleasedProductPage = initializePageObject(
			DFinanceNewReleasedProductPage.class);

		return newReleasedProductPage;
	}

	public boolean searchReleasedProduct( String productNumber )
	{
		wait.waitForElementDisplayed(SEARCH_INPUT);
		text.enterText(SEARCH_INPUT, productNumber);
		waitForPageLoad();
		wait.waitForElementDisplayed(QUICK_FILTER);
		WebElement itemNumber = element.getWebElements(QUICK_FILTER).get(0);
		click.clickElementCarefully(itemNumber);
		By searchResultBy = getCustomerAccountBy(productNumber);
		boolean result = element.isElementDisplayed(searchResultBy);

		return result;
	}

	private By getCustomerAccountBy( String customerAccount )
	{
		By customerAccountBy = By.cssSelector(
			String.format("[name='InventTable_ItemIdGrid'][title*='%s']", customerAccount));
		return customerAccountBy;
	}

	/**
	 * Click on delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementEnabled(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		waitForPageLoad();
	}

	public void clickDeleteYesButton( )
	{
		wait.waitForElementEnabled(DELETE_YES_BUTTON);
		click.clickElement(DELETE_YES_BUTTON);
		waitForPageLoad();
	}

	public void clickSaveButton( )
	{
		wait.waitForElementEnabled(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * set Vendor in Purchase tab
	 *
	 * @param 'vendor'
	 */
	public void setVendorInPurchaseTab( String vendor )
	{
		this.expandHeader(PURCHASE_HEADER);
		wait.waitForElementEnabled(VENDOR_INPUT);
		text.enterText(VENDOR_INPUT, vendor + Keys.TAB);
		waitForPageLoad();
	}
}
