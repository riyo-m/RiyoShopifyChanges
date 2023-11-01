package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents the page to perform operations on Electronics Store Page - AddToCart Page
 * i.e. Search for Product, Add to Cart and proceed to Checkout
 *
 * @author Nagaraju Gampa
 */
public class HybrisElectronicsStorePage extends HybrisBasePage
{
	public HybrisElectronicsStorePage( WebDriver driver )
	{
		super(driver);
	}

	protected By SEARCH_BOX = By.id("js-site-search-input");
	protected By SEARCH_BUTTON = By.cssSelector("[class$='js_search_button']");
	protected By PRODUCT_LIST = By.className("product__list--thumb");
	protected By ADDTOCART_PRODUCTPAGE = By.id("addToCartButton");
	protected By CHECKOUT_BUTTON = By.cssSelector("[class*='add-to-cart-button']");
	protected By CONTINUE_CHECKOUT_BUTTON = By.cssSelector("[class*='js-continue-checkout-button']");
	protected By CART_ICON = By.cssSelector("[class='yCmsComponent'] [class = 'nav-items-total']");
	protected By CART_CHECKOUT = By.cssSelector("[class$='mini-cart-checkout-button']");
	protected By CONTINUE_SHOPPING = By.cssSelector("[class$='js-mini-cart-close-button']");

	/***
	 * Search for ProductID
	 * @param productID - Enter ID of the Product to be searched
	 */
	public void searchProductID( String productID )
	{
		wait.waitForElementDisplayed(SEARCH_BOX);
		text.enterText(SEARCH_BOX, productID);
		click.clickElement(SEARCH_BUTTON);
	}

	/***
	 * Select ProductID from Search Results
	 */
	public void selectProductID( )
	{
		wait.waitForElementDisplayed(PRODUCT_LIST);
		click.clickElement(PRODUCT_LIST);
	}

	/***
	 * GetCartQuantity
	 * @return CartQuantity - Returns the quantity available in the cart
	 */
	public int getCartQuantity( )
	{
		wait.waitForElementDisplayed(CART_ICON);
		String itemCountStr = text
			.getElementText(CART_ICON)
			.trim();
		itemCountStr = itemCountStr.replace(" ITEMS", "");
		int cartQuantity = Integer.parseInt(itemCountStr);
		return cartQuantity;
	}

	/***
	 * navigateToCart
	 * Navigate to Cart Page by Clicking on Cart Icon
	 * @return to Cart Page
	 */
	public HybrisElectronicsStoreCartPage navigateToCart( )
	{
		wait.waitForElementEnabled(CART_ICON);
		click.clickElement(CART_ICON);
		wait.waitForElementDisplayed(CART_CHECKOUT);
		click.clickElement(CART_CHECKOUT);
		HybrisElectronicsStoreCartPage cartPage = initializePageObject(HybrisElectronicsStoreCartPage.class);
		return cartPage;
	}

	/***
	 * Search for ProductID and Add to Cart
	 * @param productID - Enter the ID of the product which will be added to Cart
	 */
	public void addProductToCart( String productID )
	{
		searchProductID(productID);
		selectProductID();
		wait.waitForElementDisplayed(ADDTOCART_PRODUCTPAGE);
		click.clickElement(ADDTOCART_PRODUCTPAGE);
		waitForPageLoad();
	}

	/***
	 * Continue Shopping from Shopping Cart Page
	 *
	 */
	public void continueShoppingFromCart( )
	{
		wait.waitForElementDisplayed(CONTINUE_SHOPPING);
		click.clickElement(CONTINUE_SHOPPING);
		waitForPageLoad();
	}

	/***
	 * Method to proceed check out by selecting checkout and continuecheckout buttons
	 */
	public HybrisElectronicsStoreGuestLoginPage proceedToCheckout( )
	{
		click.clickElement(CHECKOUT_BUTTON);
		waitForPageLoad();
		if ( element.isElementDisplayed(CONTINUE_CHECKOUT_BUTTON) )
		{
			click.clickElement(CONTINUE_CHECKOUT_BUTTON);
			waitForPageLoad();
		}
		final HybrisElectronicsStoreGuestLoginPage electronicsStoreGuestLoginPage = initializePageObject(
			HybrisElectronicsStoreGuestLoginPage.class);
		return electronicsStoreGuestLoginPage;
	}
}
