package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontPage( final WebDriver driver )
	{
		super(driver);
	}

//	protected By onlineStore = By.xpath("//span[text()='Online Store']");
//	protected By viewYourStore = By.xpath("//span[text()='View your store']");
	protected By enterSearch = By.className("header__search");
	protected By searchNewField = By.id("Search-In-Modal");
	protected By enterSearchBtn = By.xpath("(//button[@class='search__button field__button'])[1]");
	protected By selectNewProduct = By.xpath("(//div[@class='card__information']/child::h3)[4]");
	protected By addToCart = By.name("add");
	protected By checkOut = By.name("checkout");


//	public void clickOnlineStore( )
//	{
//		waitForPageLoad();
//		click.clickElement(onlineStore);
//	}
//
//	public void clickViewYourStore( ) throws InterruptedException
//	{
//		waitForPageLoad();
//		Thread.sleep(5000);
//		driver
//			.switchTo()
//			.frame(0);
//		click.clickElement(viewYourStore);
//	}

	public void clickEnterSearch( ) throws InterruptedException
	{
		waitForPageLoad();


		//		scroll.scrollElementIntoView(enterSearch);
		click.clickElement(enterSearch);
	}

	public void clickSearchField( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchNewField), product);

	}

	public void clickSearchBtn( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(enterSearchBtn);
	}

	public void clickSelectedProduct( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);

		scroll.scrollElementIntoView(selectNewProduct);
		click.clickElement(selectNewProduct);
	}

	public void addProductToCard( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(addToCart);
	}

	public void checkOutProduct( ) throws InterruptedException
	{
		waitForPageLoad();
		Thread.sleep(2000);
		click.clickElement(checkOut);
	}

	public void goToStore( String product ) throws InterruptedException
	{
//		clickOnlineStore();
//		clickViewYourStore();
		clickEnterSearch();
		clickSearchField(product);
		clickSearchBtn();
		clickSelectedProduct();
		addProductToCard();
		checkOutProduct();
	}
	public void taxExemptProduct() throws InterruptedException
	{
		clickEnterSearch();
		clickSearchField(ShopifyDataUI.Products.SHOPIFY_EXEMPTED_PRODUCT.text);
		clickSearchBtn();
		clickSelectedProduct();
		addProductToCard();
		checkOutProduct();
	}
}
