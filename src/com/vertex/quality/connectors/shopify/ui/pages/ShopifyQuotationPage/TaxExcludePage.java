package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TaxExcludePage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public TaxExcludePage( final WebDriver driver )
	{
		super(driver);
	}

	protected By productMfPage = By.xpath("//span[text()='Products']");
	protected By productSnowboard = By.xpath("//span[text()='The 3p Fulfilled Snowboard']");

	protected By taxExclude = By.xpath(
		"//span[text()='Charge tax on this product']/parent::span/preceding-sibling::span/child::span");
	protected By saveTaxExclude = By.xpath("(//span[text()='Save'])[1]");

	public void clickProductPage( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(productMfPage);
	}

	public void clickProductSnowboard( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		scroll.scrollElementIntoView(productSnowboard);
		click.clickElement(productSnowboard);
		Thread.sleep(5000);
	}

	public void clickTaxExclude( )
	{
		waitForPageLoad();
		scroll.scrollElementIntoView(taxExclude);
		click.clickElement(taxExclude);
	}

	public void clickSaveTaxExclude( ) throws InterruptedException
	{
		waitForPageLoad();
		click.clickElement(saveTaxExclude);
		Thread.sleep(2000);
	}

	public void markTaxExclude( ) throws InterruptedException
	{
		clickProductPage();
		clickProductSnowboard();
		clickTaxExclude();
		clickSaveTaxExclude();
	}
}

