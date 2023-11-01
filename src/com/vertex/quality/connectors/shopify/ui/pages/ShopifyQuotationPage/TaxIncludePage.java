package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TaxIncludePage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public TaxIncludePage( final WebDriver driver )
	{
		super(driver);
	}

	protected By enterSettings = By.xpath("//span[text()='Settings']");
	protected By taxAndDuties = By.xpath("//span[text()='Taxes and duties']");
	protected By taxInclude = By.xpath(
		"//p[text()='Include tax in prices']/parent::div/parent::span/parent::span/preceding-sibling::span/child::span");
	protected By saveTaxInclude = By.xpath("//span[text()='Save']");
	protected By backToStore = By.className("XptyV");

	public void clickTaxInclude( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		scroll.scrollElementIntoView(taxInclude);
		click.clickElement(taxInclude);
	}

	public void enterSettingsPage( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(enterSettings);
	}

	public void enterTaxAndDuties( )
	{
		waitForPageLoad();
		click.clickElement(taxAndDuties);
	}
	public void clickSaveTaxInclude(){
		waitForPageLoad();
		click.clickElement(saveTaxInclude);
	}

	public void enterBackToStore( )
	{
		waitForPageLoad();
		click.clickElement(backToStore);
	}
	public void enterTaxIncludePage() throws InterruptedException
	{
		enterSettingsPage();
		enterTaxAndDuties();
		clickTaxInclude();
		clickSaveTaxInclude();
		enterBackToStore();

	}
}
