package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Shopify admin order's page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminOrdersPage extends ShopifyPage
{

	protected By ordersTableHeader = By.xpath(".//div[contains(@class,'Polaris-IndexFilters__IndexFilters')]");
	protected By orderSearchButton = By.xpath(".//button[@aria-label='Search and filter orders']");
	protected By orderTable = By.xpath(".//table[contains(@class,'Polaris-IndexTable__Table')]");
	protected By allOrderRecords = By.xpath(".//table[contains(@class,'Polaris-IndexTable__Table')]//tr");
	protected By searchOrderBox = By.xpath(".//input[@placeholder='Searching all orders']");
	protected By cancelSearchBarButton = By.xpath(".//button[normalize-space(.)='Cancel']");
	protected String orderRecord = ".//tr//td[contains(normalize-space(.),'<<text_replace>>')]";
	protected String orderCheckBox
		= ".//tr//td[contains(normalize-space(.),'<<text_replace>>')]/preceding-sibling::td//input";
	protected By orderBulkAction = By.xpath("(.//div[contains(@class,'Polaris-BulkActions')])[2]");
	protected By markFulfilledButton = By.xpath(".//button[normalize-space(.)='Mark as fulfilled']");
	protected By markFulfilledDialog = By.xpath(
		".//span[text()='Paid']/parent::span/following-sibling::span/descendant::span[text()='Fulfilled']");
	protected By fulfillingOrderText = By.xpath(".//span[text()='Fulfilling order...']");
	protected By orderDetailsPageHeader = By.xpath(".//h1[contains(@class,'Polaris-Header-Title')]");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ShopifyAdminOrdersPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Removes all the filters on Order Records grid - if any filters are applied
	 */
	public void removeAllAppliedFiltersIfAny( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(ordersTableHeader);
		wait.waitForElementPresent(orderTable);
		wait.waitForAllElementsPresent(allOrderRecords);
		if ( element.isElementDisplayed(cancelSearchBarButton) )
		{
			click.clickElement(cancelSearchBarButton);
		}
	}

	/**
	 * Search for the Order
	 *
	 * @param orderNo value of order id which is to be searched
	 */
	public void selectOrder( String orderNo )
	{
		waitForPageLoad();
		removeAllAppliedFiltersIfAny();
		wait.waitForElementPresent(ordersTableHeader);
		wait.waitForElementPresent(orderTable);
		wait.waitForAllElementsPresent(allOrderRecords);
		click.moveToElementAndClick(wait.waitForElementEnabled(orderSearchButton));
		WebElement searchBox = wait.waitForElementPresent(searchOrderBox);
		text.enterText(searchBox, orderNo);
		waitForPageLoad();
		WebElement orderChkBx = wait.waitForElementEnabled(
			By.xpath(orderCheckBox.replace("<<text_replace>>", orderNo)));
		if ( !checkbox.isCheckboxChecked(orderChkBx) )
		{
			click.javascriptClick(orderChkBx);
		}
	}

	/**
	 * Marking the order as fulfilled
	 */
	public void markFulfillTheOrder( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(ordersTableHeader);
		wait.waitForElementPresent(orderTable);
		click.clickElement(markFulfilledButton);
		wait.waitForElementPresent(orderBulkAction);
		WebElement fulfillConfirmation = wait.waitForElementPresent(markFulfilledDialog);
		click.moveToElementAndClick(wait.waitForElementPresent(markFulfilledButton, fulfillConfirmation));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(
			ExpectedConditions.invisibilityOfElementLocated(fulfillingOrderText));
		waitForPageLoad();
	}

	/**
	 * Opens the order
	 *
	 * @param orderNo value of order number which is to be opened
	 *
	 * @return Shopify Order details page
	 */
	public ShopifyAdminOrderDetailsPage openOrderDetail( String orderNo )
	{
		refreshPage();
		waitForPageLoad();
		wait.waitForElementPresent(ordersTableHeader);
		wait.waitForElementPresent(orderTable);
		click.clickElement(By.xpath(orderRecord.replace("<<text_replace>>", orderNo)));
		waitForPageLoad();
		wait.waitForElementPresent(orderDetailsPageHeader);
		return new ShopifyAdminOrderDetailsPage(driver);
	}
}
