package com.vertex.quality.connectors.episerver.pages.epiCommon;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EpiCartPage extends VertexPage
{
	public EpiCartPage( WebDriver driver )
	{
		super(driver);
	}

	protected By CART_ITEM_COUNT = By.cssSelector("[class$='btn-cart']>[class*='cartItemCountLabel']");
	protected By CART_BUTTON = By.cssSelector("[class$='btn-cart']");
	// Had to use xpath due to complex DOM Structure
	protected By REMOVE_ITEM_BUTTON = By.xpath(
		"//*[@id='MiniCart']//*[@class='cart-dropdown__product-container']//*[@role='button' and contains(@class,'RemoveCartItem')]");
	protected By SEARCH_INPUT = By.name("q");
	protected By PRODUCT_DIVISION = By.className("product-row__item__title");
	protected By ADD_TO_CART_BUTTON = By.cssSelector("[type='submit'][class$='btn-primary jsAddToCart']");
	protected By PROCEED_TO_CHECKOUT_BUTTON = By.xpath(
		"//*[@id='MiniCart']//button[@class='btn btn-block btn-primary']");
	protected By QUANTITY_INPUT = By.xpath("(.//input[@name='quantity'])[3]");
	protected String CLICK_PRODUCT = ".//img[@alt='<<text_replace>>']";
	protected String PRODUCT_QUANTITY_INPUT = ".//img[@alt='<<text_replace>>']/parent::a/parent::div/following-sibling::div//input[@id='quantity']";

	/**
	 * This method is used to get item count from cart
	 *
	 * @return cart count
	 */
	public int getItemCountInCart( )
	{
		int cartcount = 0;
		waitForPageLoad();
		wait.waitForElementDisplayed(CART_ITEM_COUNT);
		String countStr = text.getElementText(CART_ITEM_COUNT);
		if ( countStr != null )
		{
			countStr = countStr.trim();
			cartcount = Integer.valueOf(countStr);
		}
		return cartcount;
	}

	/**
	 * This method is used to clear the cart if items exists in cart
	 */
	public void clearAllItemsInCart( )
	{
		int itemCount = this.getItemCountInCart();
		VertexLogger.log(String.format("itemCount %s ", String.valueOf(itemCount)));
		if ( itemCount > 0 )
		{
			String isExpanded = attribute.getElementAttribute(CART_BUTTON, "aria-expanded");
			if ( isExpanded.equals("false") )
			{
				click.clickElement(CART_BUTTON);
				waitForPageLoad();
			}
			else
			{
				VertexLogger.log("Cart is already expanded");
			}
			List<WebElement> removeButtonElementsList = element.getWebElements(REMOVE_ITEM_BUTTON);
			if ( removeButtonElementsList.size() > 0 )
			{
				int count = removeButtonElementsList.size();
				for ( int i = 0 ; i < count ; i++ )
				{
					wait.waitForElementPresent(REMOVE_ITEM_BUTTON);
					removeButtonElementsList = element.getWebElements(REMOVE_ITEM_BUTTON);
					removeButtonElementsList
						.get(0)
						.click();
					VertexLogger.log("Item remove button clicked");
					waitForPageLoad();
				}
				itemCount = getItemCountInCart();
				VertexLogger.log(String.format("itemCount %s", String.valueOf(itemCount)));
				if ( itemCount == 0 )
				{
					VertexLogger.log("All items are removed from the Cart");
				}
				else
				{
					VertexLogger.log("Still there are some items available in the Cart to clear/delete",
						VertexLogLevel.ERROR);
				}
			}
			else
			{
				VertexLogger.log(
					"Either remove button element locator might be changed or no items present in the cart",
					VertexLogLevel.WARN);
			}
		}
		else
		{
			VertexLogger.log("No items are available in the Cart to clear/delete");
		}
	}

	/**
	 * This method is used to search for item based on product code
	 *
	 * @return true if item found false if item not found
	 */
	public boolean searchItemInEpiStore( String itemCode )
	{
		boolean itemfound = false;
		text.enterText(SEARCH_INPUT, itemCode + Keys.ENTER);
		waitForPageLoad();
		List<WebElement> productElementsList = element.getWebElements(PRODUCT_DIVISION);
		if ( productElementsList.size() > 0 )
		{
			VertexLogger.log(String.format("Search result found for the product: %s ", String.valueOf(itemCode)));
			itemfound = true;
		}
		else
		{
			VertexLogger.log(String.format("No search results found for the product:  %s ", String.valueOf(itemCode)));
			itemfound = false;
		}
		return itemfound;
	}

	/***
	 * Method to get product details
	 *
	 * @param productElement
	 *            productElement
	 * @return map productDetailsMap
	 */
	public Map<String, String> getProductDetails( WebElement productElement )
	{
		String productTitle = productElement
			.findElement(By.className("product-title"))
			.getText();
		String productBrand = productElement
			.findElement(By.className("product-brand"))
			.getText();
		String productPrice = productElement
			.findElement(By.className("product-price"))
			.getText();
		Map<String, String> productDetailsMap = new HashMap<String, String>();
		productDetailsMap.put("TITLE", productTitle);
		productDetailsMap.put("BRAND", productBrand);
		productDetailsMap.put("PRICE", productPrice);
		VertexLogger.log(String.format("Produt Details are as follows : %s ", String.valueOf(productDetailsMap)));
		return productDetailsMap;
	}

	/***
	 * Method to get product title
	 *
	 * @param itemCode
	 * @return string title
	 */
	public String searchAndSelectTheProductInEpiStore( String itemCode )
	{
		String title = null;
		if ( this.searchItemInEpiStore(itemCode) == true )
		{
			List<WebElement> productElementsList = element.getWebElements(PRODUCT_DIVISION);
			WebElement productElement = productElementsList.get(0);
			Map<String, String> productDetailsMap = getProductDetails(productElement);
			productElement.click();
			VertexLogger.log(String.format("Clicked on Product: %s", String.valueOf(itemCode)));
			waitForPageLoad();
			title = productDetailsMap.get("TITLE");
		}
		else
		{
			VertexLogger.log(String.format("No results found for the Product: ", String.valueOf(itemCode)));
		}
		return title;
	}

	/**
	 * Clicks on product to open product view page
	 *
	 * @param productCode product code
	 */
	public void clickOnProduct(String productCode) {
		waitForPageLoad();
		productCode = productCode.replace("SKU", "P");
		WebElement selectProduct = wait.waitForElementPresent(By.xpath(CLICK_PRODUCT.replace("<<text_replace>>", productCode)));
		click.moveToElementAndClick(selectProduct);
		waitForPageLoad();
	}

	/**
	 * This method is used to search for the product and Add to Cart if Search
	 *
	 * @param itemCode Product Code which is to be searched
	 */
	public void searchAndAddProductToCart(String itemCode) {
		this.searchItemInEpiStore(itemCode);
		this.clickOnProduct(itemCode);
		this.clickAddToCartButton();
	}

	/**
	 * This method is used to click on Add to Cart button to add the product to cart
	 */
	public void clickAddToCartButton( )
	{
		int itemCount = this.getItemCountInCart();
		wait.waitForElementDisplayed(ADD_TO_CART_BUTTON);
		scroll.scrollElementIntoView(element.getWebElement(ADD_TO_CART_BUTTON));
		click.clickElement(ADD_TO_CART_BUTTON);
		waitForPageLoad();
		scroll.scrollElementIntoView(element.getWebElement(CART_ITEM_COUNT));
		int item_count2 = getItemCountInCart();
		if ( item_count2 == itemCount + 1 )
		{
			VertexLogger.log("Product added successfully to the Cart");
		}
		else
		{
			VertexLogger.log("Product not added to the cart", VertexLogLevel.ERROR);
		}
	}

	/**
	 * This method is used to click on Cart Icon/Button
	 */
	public void clickOnCartButton( )
	{
		wait.waitForElementDisplayed(CART_BUTTON);
		click.clickElement(CART_BUTTON);
		waitForPageLoad();
	}

	/**
	 * This method is used to click on Proceed to Checkout button to continue the
	 * shopping
	 */
	public void proceedToCheckoutButton( )
	{
		wait.waitForElementDisplayed(PROCEED_TO_CHECKOUT_BUTTON);
		click.clickElement(PROCEED_TO_CHECKOUT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * This method is used for continue checkout with list of products which are
	 * added to cart
	 */
	public EpiCheckoutPage proceedToCheckout( )
	{
		EpiCheckoutPage checkoutpage = new EpiCheckoutPage(driver);
		this.clickOnCartButton();
		this.proceedToCheckoutButton();
		return checkoutpage;
	}

	/**
	 * This method is used to change quantity on Checkout page
	 *
	 * @param quantity quantity for product
	 */
	public void changeProductQuantity(String quantity) {
		waitForPageLoad();
		WebElement qty = wait.waitForElementPresent(QUANTITY_INPUT);
		click.performDoubleClick(qty);
		text.enterText(qty, quantity, false);
		text.pressTab(qty);
	}

	/**
	 * This method is used to change quantity on Checkout page
	 *
	 * @param productName Product Name which quantity/quantities to be updated
	 * @param quantity    Product quantity value
	 */
	public void changeProductQuantity(String productName, String quantity) {
		waitForPageLoad();
		WebElement qty = wait.waitForElementPresent(By.xpath(PRODUCT_QUANTITY_INPUT.replace("<<text_replace>>", productName)));
		click.performDoubleClick(qty);
		text.enterText(qty, quantity, false);
		text.pressTab(qty);
	}

	/**
	 * This method is used to get Product Name/Title based on product code
	 *
	 * @return Title of the Product
	 */
	public String getItemTitleWithProductCode( String itemCode )
	{
		// By item = By.xpath(String.format("//div[@class='row cart-row
		// ']/div/div//div/a[contains(@href, '%s')]", itemCode));
		By item = By.xpath(
			String.format("//div[contains(@class , 'row cart-row')]/div/div//div/a[contains(@href, '%s')]", itemCode));
		wait.waitForElementDisplayed(item);
		String productTitle = text.getElementText(item);
		return productTitle;
	}
}
