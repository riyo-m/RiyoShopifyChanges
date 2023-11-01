package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderLevelDiscountInvPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public OrderLevelDiscountInvPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By orderLevelDiscount = By.xpath("//span[text()='$2,629.95']");

	protected By enterDiscountAmount = By.xpath("//input[@id='discountFixedAmount']");
	protected By applyDiscount = By.xpath("//button[@class='Polaris-Button_r99lw']/following-sibling::button");

	public void clickOrderLevelDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		click.clickElement(orderLevelDiscount);
	}

	public void clickEnterDiscountAmount( String discount ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		text.enterText(wait.waitForElementDisplayed(enterDiscountAmount), discount);
	}

	public void clickApplyDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		click.clickElement(applyDiscount);
	}
	public void orderLevelInvPage() throws InterruptedException
	{
		clickOrderLevelDiscount();
		clickEnterDiscountAmount("100");
		clickApplyDiscount();
	}
}
