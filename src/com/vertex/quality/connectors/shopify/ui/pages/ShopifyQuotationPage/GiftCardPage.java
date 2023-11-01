package com.vertex.quality.connectors.shopify.ui.pages.ShopifyQuotationPage;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GiftCardPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public GiftCardPage( final WebDriver driver )
	{
		super(driver);
	}
	protected By enterOrder = By.xpath("//span[text()='Orders']");
	protected By createNewOrder = By.xpath("//span[text()='Create order']");
	protected By searchGiftCard = By.name("productPicker");
	protected By giftCard = By.xpath("//span[text()='Shopify Gift card']/parent::div");
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
	public void clickSearchGiftCard( String product )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(searchGiftCard), product);
	}
	public void selectGiftCard(){
		waitForPageLoad();
		click.clickElement(giftCard);
	}
	public void addProduct( )
	{
		waitForPageLoad();
		click.clickElement(addSelectedProduct);
	}
public void giftCard(){
		enterNewOrder();
		createOrder();
		clickSearchGiftCard(" Shopify Gift card");
		selectGiftCard();
		addProduct();

}


}
