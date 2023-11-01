package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;

public class AdminOrderPage extends ShopifyPage

{
	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By orderSearchButton = By.xpath(".//button[@aria-label='Search and filter orders']");
	protected By orderSearchBox = By.xpath(".//input[@placeholder='Searching all orders']");
	protected By cancelOrderButton = By.xpath(".//button[normalize-space(.)='Cancel']");
	protected By selectOrderWithOrderId = By.xpath(
		".//table[contains(@class,'Polaris-IndexTable__Table')]/child::tbody/child::tr");
	protected By fulfillItem = By.xpath("//span[text()='Fulfill item']");
	protected By clickFulfillItem = By.xpath("//span[text()='Fulfill item']");

	protected By taxAfterFulfilled = By.xpath("(//span[text()='Show tax rates']//ancestor::div/child::span)[4]");
	protected By markFulfilledDialog = By.xpath(
		".//span[text()='Paid']/parent::span/following-sibling::span/descendant::span[text()='Fulfilled']");
	protected By orderPageHeader = By.xpath(".//h1[contains(@class,'Polaris-Header-Title')]");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public AdminOrderPage( final WebDriver driver )
	{
		super(driver);
	}

	public void fulfillOrderInAdmin( String orderId ) throws InterruptedException
	{
		waitForPageLoad();
		click.clickElement(enterOrder);
		waitForPageLoad();
		click.clickElement(orderSearchButton);
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(orderSearchBox), orderId);
		waitForPageLoad();
		click.clickElement(selectOrderWithOrderId);
		waitForPageLoad();
		click.clickElement(fulfillItem);
		waitForPageLoad();
		scroll.scrollElementIntoView(clickFulfillItem);
		click.clickElement(clickFulfillItem);
		waitForPageLoad();
		scroll.scrollElementIntoView(taxAfterFulfilled);
		text.getElementText(taxAfterFulfilled);

	}
	public double getTaxFromAdmin(){
		waitForPageLoad();
//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(taxAfterFulfilled);
		text.getElementText(taxAfterFulfilled);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(taxAfterFulfilled))
			.replace("$", "")
			.replace(",", ""));
	}
	public void getFulfilledDialog(){
		waitForPageLoad();
		String fulfilledText = text.getElementText(markFulfilledDialog);
		text.verifyText(markFulfilledDialog, fulfilledText, "Fulfilled");
		String orderIdText = text.getElementText(markFulfilledDialog);
		System.out.println(orderIdText);

	}
//	public String getOrderIdFromUIForAPICall( String orderNo )
//	{
//		waitForPageLoad();
//		wait.waitForElementPresent(By.xpath(orderIdInsideOrderDetails.replace("<<order_id>>", orderNo)));
//		String url = getCurrentUrl();
//		int index = url.lastIndexOf("/");
//		return url.substring(index + 1);
//	}
}
