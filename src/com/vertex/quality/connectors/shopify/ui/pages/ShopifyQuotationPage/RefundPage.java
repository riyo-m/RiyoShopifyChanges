package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RefundPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public RefundPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By orderRefund = By.id("refund-restock");
	protected By enterValue = By.xpath(
		"//div[@class='Polaris-TextField_1spwi Polaris-TextField--hasValue_1mx8d']/child::input[@class='Polaris-TextField__Input_30ock Polaris-TextField__Input--suffixed_1tsyu']");
	protected By refundFullAmount = By.xpath(
		"//span[text()='Send a '] /parent::span/parent::label/parent::div/preceding-sibling::div");

	public void clickOrderRefund( )
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(orderRefund);
		click.clickElement(orderRefund);
	}

	public void clickEnterValue( String textField ) throws InterruptedException
	{
		waitForPageLoad();

		Thread.sleep(1000);
		text.enterText(wait.waitForElementDisplayed(enterValue), textField);
	}

	public void clickRefundFullAmount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(refundFullAmount);
	}

	public void refundOrderPage( String textField ) throws InterruptedException
	{
		clickOrderRefund();
		clickEnterValue(textField);
		clickRefundFullAmount();
	}
}
