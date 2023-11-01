package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Shopify store cart page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreCartPage extends ShopifyPage
{

	protected By deleteAllIcon = By.cssSelector(".icon-remove");
	protected String individualCartItemQtyBox = ".//td[contains(normalize-space(.),'<<text_replace>>')]//input";
	protected String individualItemDeleteButton
		= ".//td[contains(normalize-space(.),'<<text_replace>>')]//cart-remove-button";
	protected By checkoutButton = By.id("checkout");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ShopifyStoreCartPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Deletes all the items from the cart
	 */
	public void deleteAllCartItems( )
	{
		waitForPageLoad();
		wait.waitForAllElementsPresent(deleteAllIcon);
		List<WebElement> deleteAll = element.getWebElements(deleteAllIcon);
		for ( WebElement deleteSingle : deleteAll )
		{
			click.moveToElementAndClick(wait.waitForElementPresent((By) deleteSingle));
			waitForPageLoad();
		}
	}

	/**
	 * Updates the qty of an individual item in the cart
	 *
	 * @param productName name of product which qty to be modified
	 * @param qty         qty value
	 */
	public void updateQuantity( String productName, String qty )
	{
		waitForPageLoad();
		WebElement quantity = wait.waitForElementPresent(
			By.xpath(individualCartItemQtyBox.replace("<<text_replace>>", productName)));
		text.enterText(quantity, qty);
		text.pressEnter(quantity);
		waitForPageLoad();
	}

	/**
	 * Deletes an item in the cart
	 *
	 * @param productName name of product which qty to be deleted
	 */
	public void deleteCartItem( String productName )
	{
		waitForPageLoad();
		WebElement quantity = wait.waitForElementPresent(
			By.xpath(individualItemDeleteButton.replace("<<text_replace>>", productName)));
		click.moveToElementAndClick(quantity);
		waitForPageLoad();
	}

	/**
	 * Clicks on checkout button from the cart page
	 *
	 * @return Shipping Info Page
	 */
	public ShopifyStoreShippingInfoPage clickOnCheckout( )
	{
		waitForPageLoad();

		click.moveToElementAndClick(wait.waitForElementPresent(checkoutButton));
		waitForPageLoad();
		return new ShopifyStoreShippingInfoPage(driver);
	}
}
