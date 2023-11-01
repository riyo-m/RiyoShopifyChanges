package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class CustomerExemptPage extends ShopifyPage

{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public CustomerExemptPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By searchNewCustomer = By.xpath(
		"//h2[text()='Customer']/parent::div/following-sibling::div/descendant::div[@class='Polaris-TextField_1spwi']/descendant::input");
	protected By selectExemptCustomer = By.xpath("//span[text()='rptest@sp.com']/parent::span/parent::div");

	public void searchCustomerField( String customer ) throws InterruptedException
	{
		waitForPageLoad();
		//	click.clickElement(searchNewCustomer);
		sleep(5000);
		text.enterText(wait.waitForElementDisplayed(searchNewCustomer), customer);
	}

	public void exemptCustomer( ) throws InterruptedException
	{
		waitForPageLoad();
		sleep(2000);
		click.clickElement(selectExemptCustomer);
	}

	public void customerExemptPage( String xmptCustomer ) throws InterruptedException
	{
		searchCustomerField(xmptCustomer);
		exemptCustomer();
	}
}