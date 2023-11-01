package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class ItemFulfillmentQPage extends ShopifyPage
{
	protected By fulfillItem = By.xpath("//span[text()='Fulfill item']");
	protected By clickFulfillItem = By.xpath("//span[text()='Fulfill item']");
//	protected By showTaxRates = By.xpath("(//span[text()='Show tax rates']//ancestor::div/child::span)[4]");
	protected By markFulfilledDialog = By.xpath(
		".//span[text()='Paid']/parent::span/following-sibling::span/descendant::span[text()='Fulfilled']");
//	protected By fulfillItem = By.xpath("//span[text()='Fulfill item']");
//	protected By clickFulfillItem = By.xpath("//span[text()='Fulfill item']");
//	protected By showTaxRates = By.xpath("(//span[text()='Show tax rates']//ancestor::div/child::span)[4]");
//	protected By markFulfilledDialog = By.xpath(
//		".//span[text()='Paid']/parent::span/following-sibling::span/descendant::span[text()='Fulfilled']");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ItemFulfillmentQPage( final WebDriver driver )
	{
		super(driver);
	}


	public void enterFulfillItem( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(fulfillItem);
	}

	public void makeFulfillment( )
	{
		waitForPageLoad();
		click.clickElement(clickFulfillItem);
	}


	public void fulfillOrder( ) throws InterruptedException
	{
		enterFulfillItem();
		makeFulfillment();
	}
}
