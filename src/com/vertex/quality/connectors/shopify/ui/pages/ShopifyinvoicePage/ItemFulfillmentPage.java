package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class ItemFulfillmentPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ItemFulfillmentPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By fulfillItem = By.xpath("//span[text()='Fulfill item']");
	protected By clickFulfillItem = By.xpath("//span[text()='Fulfill item']");

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
