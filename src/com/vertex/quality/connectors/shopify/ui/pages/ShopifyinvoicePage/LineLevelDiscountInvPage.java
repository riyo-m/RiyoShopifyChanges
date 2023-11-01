package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LineLevelDiscountInvPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public LineLevelDiscountInvPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By lineLevelDiscount = By.xpath("//span[text()='Add discount']");
	protected By lineLevelAmount = By.id("discountFixedAmount");
	protected By applyLineLevelDiscount = By.xpath("//span[text()='Apply']");

	public void clickLineLevelDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(4000);
		click.clickElement(lineLevelDiscount);
	}

	public void enterLineLevelAmount( String discount ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		text.enterText(wait.waitForElementDisplayed(lineLevelAmount), discount);
	}

	public void clickApplyLineLevelDiscount( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(1000);
		click.clickElement(applyLineLevelDiscount);
		Thread.sleep(4000);
	}

	public void applyLineLevelDiscountPage( String discount ) throws InterruptedException
	{
		clickLineLevelDiscount();
		enterLineLevelAmount(discount);
		clickApplyLineLevelDiscount();
	}
}
