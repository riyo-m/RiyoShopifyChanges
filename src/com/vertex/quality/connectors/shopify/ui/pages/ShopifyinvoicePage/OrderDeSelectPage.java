package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class OrderDeSelectPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public OrderDeSelectPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By fulfillItem = By.xpath("//span[text()='Fulfill items']");
	protected By deSelectOrder = By.xpath(
		"(//div[@class='Polaris-TextField_1spwi Polaris-TextField--hasValue_1mx8d'])[2]/child::input");
	protected By reduceOrder = By.xpath("(//div[@class='Polaris-TextField__Segment_xdd2a'])[4]");
	protected By reduceOrder2 = By.xpath("(//div[@class='Polaris-TextField__Segment_xdd2a'])[6]");
	protected By deSelectOrder2 = By.xpath(
		"(//div[@class='Polaris-TextField_1spwi Polaris-TextField--hasValue_1mx8d'])[3]/child::input");
	protected By clickFulfillItem = By.xpath("//span[text()='Fulfill item']");

	public void enterFulfillItem( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(fulfillItem);
	}

	public void enterDeSelectOrder() throws InterruptedException
	{
		waitForPageLoad();
		click.clickElement(deSelectOrder);
		Thread.sleep(1000);
	}

	public void clickReduceOrder( )
	{
		waitForPageLoad();
		click.clickElement(reduceOrder);
	}

	public void enterDeSelectOrder2() throws InterruptedException
	{
		waitForPageLoad();
		click.clickElement(deSelectOrder2);
		Thread.sleep(1000);
	}

	public void clickReduceOrder2( )
	{
		waitForPageLoad();
		click.clickElement(reduceOrder2);
	}

	public void makeFulfillment( )
	{
		waitForPageLoad();
		click.clickElement(clickFulfillItem);
	}

	public void fulfillPartialOrder( ) throws InterruptedException
	{
		enterFulfillItem();
		enterDeSelectOrder();
		clickReduceOrder();
		enterDeSelectOrder2();
		clickReduceOrder2();
		makeFulfillment();
	}
}
