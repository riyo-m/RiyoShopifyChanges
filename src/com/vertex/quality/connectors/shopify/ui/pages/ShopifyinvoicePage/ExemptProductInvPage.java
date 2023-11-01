package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ExemptProductInvPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ExemptProductInvPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By createNewOrder = By.xpath("//span[text()='Create order']");

	protected By searchExemptProduct = By.name("productPicker");
	protected By selectShopifyExemptProduct = By.xpath(
		"(//span[contains(text(),'Shopify Exempted Product')])[2]");
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

	public void addExemptProductPage( String product )
	{
		enterNewOrder();
		createOrder();
		clickSearchExemptProduct(product);
		clickSelectShopifyExemptProduct();
		addProduct();
	}
}
