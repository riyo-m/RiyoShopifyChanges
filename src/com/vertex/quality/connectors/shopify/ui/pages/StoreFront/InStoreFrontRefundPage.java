package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InStoreFrontRefundPage extends ShopifyPage
{
	protected By orderRefund = By.id("refund-restock");
	protected By enterValue = By.xpath(
		"//div[@class='Polaris-TextField_1spwi Polaris-TextField--hasValue_1mx8d']/child::input[@class='Polaris-TextField__Input_30ock Polaris-TextField__Input--suffixed_1tsyu']");
	protected By refundFullAmount = By.xpath(
		"(//span[contains(text(),'Refund')])[2]");

	protected By refundValueConfirmation = By.xpath("(//span[text()='Total']/parent::span/following-sibling::div/descendant::span)[3]");
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public InStoreFrontRefundPage( final WebDriver driver )
	{
		super(driver);
	}
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
		scroll.scrollElementIntoView(refundFullAmount);
		click.clickElement(refundFullAmount);
	}
	public double verifyAmountAfterRefund(){
		waitForPageLoad();
		//		DecimalFormat format = new DecimalFormat("0.00");
		scroll.scrollElementIntoView(refundValueConfirmation);
		text.getElementText(refundValueConfirmation);

		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(refundValueConfirmation))
			.replace("$", "")
			.replace(",", ""));

	}

	public void refundOrderPage( String textField ) throws InterruptedException
	{
		clickOrderRefund();
		clickEnterValue(textField);
		clickRefundFullAmount();
	}
}
