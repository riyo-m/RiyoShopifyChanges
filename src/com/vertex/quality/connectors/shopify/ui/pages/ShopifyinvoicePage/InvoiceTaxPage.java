package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class InvoiceTaxPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public InvoiceTaxPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By showTaxRates = By.xpath("//span[text()='Show tax rates']");
	protected By closeTaxRate = By.xpath("//span[text()='Close']");

	public void clickShowTaxRates( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(showTaxRates);
	}

	public void clickClose( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(closeTaxRate);
	}

	public void quotationPage( ) throws InterruptedException
	{
		clickShowTaxRates();
		clickClose();
	}
}
