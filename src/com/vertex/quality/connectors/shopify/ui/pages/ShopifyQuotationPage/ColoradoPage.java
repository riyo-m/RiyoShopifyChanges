package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class ColoradoPage extends ShopifyPage
{

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ColoradoPage( final WebDriver driver )
	{
		super(driver);
	}
	protected By searchNewCustomer = By.xpath(
		"//h2[text()='Customer']/parent::div/following-sibling::div/descendant::div[@class='Polaris-TextField_1spwi']/descendant::input");
	protected By selectNewCustomer = By.xpath("//span[text()='riyoco@sp.com']/parent::span/parent::div");

	public void searchCustomerField( String customer ) throws InterruptedException
	{
		waitForPageLoad();
		//	click.clickElement(searchNewCustomer);
		sleep(5000);
		text.enterText(wait.waitForElementDisplayed(searchNewCustomer), customer);
	}

	public void selectCustomer( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(selectNewCustomer);
	}

	public void selectColoradoCustomer( String customer ) throws InterruptedException
	{
		searchCustomerField(customer);
		selectCustomer();
	}

}
