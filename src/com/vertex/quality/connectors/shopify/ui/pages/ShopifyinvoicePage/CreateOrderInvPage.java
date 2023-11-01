package com.vertex.quality.connectors.shopify.ui.pages.ShopifyinvoicePage;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateOrderInvPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public CreateOrderInvPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By createNewOrder = By.xpath("//span[text()='Create order']");
	protected By browseProduct = By.xpath("//span[text()='Browse']");

	protected By searchPopularProduct = By.xpath("//div[text()='Popular products']");
	protected By productSearchField = By.xpath("(//input[@name='productPicker'])[2]");

	protected By selectFirstProduct = By.xpath("(//span[text()='The 3p Fulfilled Snowboard'])[2]");
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

	public void browseNewProduct( )
	{
		waitForPageLoad();
		click.clickElement(browseProduct);
	}

	public void enterProductSearchField( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(productSearchField), product);
	}

	public void searchNewProduct( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(3000);
		click.clickElement(searchPopularProduct);
	}

	public void selectProduct( )
	{
		waitForPageLoad();
		click.clickElement(selectFirstProduct);
	}

	public void addProduct( )
	{
		waitForPageLoad();
		click.clickElement(addSelectedProduct);
	}

	public void createNewOrderPage( ) throws InterruptedException
	{
		enterNewOrder();
		createOrder();
		browseNewProduct();
		searchNewProduct();
		enterProductSearchField(ShopifyDataUI.Products.THREE_P_FULFILLED_SNOWBOARD.text);
		selectProduct();
		addProduct();
	}
}
