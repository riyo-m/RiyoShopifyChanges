package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExemptProductPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ExemptProductPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By createNewOrder = By.xpath("//span[text()='Create order']");

	protected By searchExemptProduct = By.name("productPicker");
	protected By selectShopifyExemptProduct = By.xpath(
		"//div[@class='txPIe']//span[text()='Shopify Exempted Product']");
	protected By addSelectedProduct = By.xpath("//span[text()='Add']");

	public void enterNewOrder( )
	{
		waitForPageLoad();
		click.clickElement(enterOrder);
	}

	public void createOrder( )
	{
		waitForPageLoad();
		click.clickElement(createNewOrder);
	}

	public void clickSearchExemptProduct( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchExemptProduct), product);
	}

	public void clickSelectShopifyExemptProduct( )
	{
		waitForPageLoad();
		click.clickElement(selectShopifyExemptProduct);
	}

	public void addProduct( )
	{
		waitForPageLoad();
		click.clickElement(addSelectedProduct);
	}

	public void exemptProductPage( String product )
	{
		enterNewOrder();
		createOrder();
		clickSearchExemptProduct(product);
		clickSelectShopifyExemptProduct();
		addProduct();
	}
}
