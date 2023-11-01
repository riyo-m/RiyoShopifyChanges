package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SettingsPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public SettingsPage( final WebDriver driver )
	{
		super(driver);
	}
	protected By enterSettings = By.xpath("//span[text()='Settings']");
	protected By taxAndDuties = By.xpath("//span[text()='Taxes and duties']");
	protected By checkProdActive = By.xpath("//span[text()='Active']");
	protected By backToStore = By.className("XptyV");

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

	public void checkProductActive( )
	{
		waitForPageLoad();
		text.verifyText(By.xpath("//span[text()='Active']"), "Active", "Active");
	}

	public void enterBackToStore( )
	{
		waitForPageLoad();
		click.clickElement(backToStore);
	}

	public void settingsTaxPage( ) throws InterruptedException
	{
		enterSettingsPage();
		enterTaxAndDuties();
		checkProductActive();
		enterBackToStore();
	}
}
