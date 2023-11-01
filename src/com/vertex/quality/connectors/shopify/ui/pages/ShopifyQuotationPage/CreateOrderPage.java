package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateOrderPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public CreateOrderPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By createNewOrder = By.xpath("//span[text()='Create order']");
	protected By browseProduct = By.xpath("//span[text()='Browse']");

	protected By searchProduct = By.xpath("//div[text()='Popular products']");

	protected By selectFirstProduct = By.xpath("//div[@class='txPIe']//span[text()='The 3p Fulfilled Snowboard']");
	protected By hydrogenSnowboard = By.xpath(
		"//div[@class='txPIe']//span[text()='The Collection Snowboard: Hydrogen']");
	protected By the3dSnowboard = By.xpath("//div[@class='txPIe']//span[text()='The 3D Modeled Snowboard']");
	protected By addSelectedProduct = By.xpath("//span[text()='Add']");

	protected By productSearchField = By.xpath("(//input[@name='productPicker'])[2]");

	protected By selectFirstProd = By.xpath("(//span[text()='The 3p Fulfilled Snowboard'])[2]");

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

	public void browseNewProduct( )
	{
		waitForPageLoad();
		click.clickElement(browseProduct);
	}

	public void searchNewProduct( )
	{
		waitForPageLoad();
		click.clickElement(searchProduct);
	}



	public void selectHydroProd( )
	{
		waitForPageLoad();
		click.clickElement(hydrogenSnowboard);
	}

	public void selectThe3dSnowboard( )
	{
		waitForPageLoad();
		click.clickElement(the3dSnowboard);
	}

	public void enterProductSearchField( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(productSearchField), product);
	}

	public void selectEnteredProduct( )
	{
		waitForPageLoad();
		click.clickElement(selectFirstProduct);
	}
	public void selectProduct( )
	{
		waitForPageLoad();
		click.clickElement(selectFirstProd);
	}

	public void addProduct( )
	{
		waitForPageLoad();
		click.clickElement(addSelectedProduct);
	}

	public void createNewOrderPage( )
	{
		enterNewOrder();
		createOrder();
		browseNewProduct();
		enterProductSearchField(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		selectProduct();
		//		selectHydroProd();
		//		selectThe3dSnowboard();
		addProduct();
	}
}
