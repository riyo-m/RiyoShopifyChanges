package com.vertex.quality.connectors.sitecore.pages.store;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreItem;
import com.vertex.quality.connectors.sitecore.common.enums.SitecoreItemCategory;
import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreItemValues;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Sitecore store-front home page - contains all re-usable methods related to
 * this page.
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreStorefrontHomePage extends SitecoreBasePage
{

	protected By logo = By.className("logo");
	protected By shoppingCart = By.className("toggle-cart");
	protected By cartQuantity = By.className("badge");
	protected By removeItem = By.className("delete-item");

	protected By noProductsInCartMsg = By.className("wrap-content-checkout");
	protected By backToShoppingButton = By.className("btn-info");

	protected By searchInput = By.cssSelector("input[name='searchTerm'][type='text']");
	protected By quantityInput = By.id("Quantity");
	protected By checkoutButton = By.cssSelector("button[id*='checkout']");

	protected By cartCheckoutQuantity = By.className("cart-quantity");
	protected By cartTotal = By.className("amount");
	protected By unitPriceLoc = By.className("cart-price");
	protected By flexFieldTextLoc = By.cssSelector("[id*=flexFieldText]");
	protected By flexFieldNumLoc = By.cssSelector("[id*=flexFieldNumber]");
	protected By flexFieldDateLoc = By.cssSelector("[id*=flexFieldDate]");
	protected By totalPriceLoc = By.className("cart-total");
	protected By categoryTableLoc = By.className("navbar-nav");
	protected By productLoc = By.className("photo");
	protected By addToCartCheckout = By.className("btn-success");
	protected By logoutLink = By.className("quicklink-space");

	public SitecoreStorefrontHomePage( WebDriver driver )
	{
		super(driver);
	}

	public void navigateToHomePage( )
	{
		wait.waitForElementEnabled(logo);
		click.clickElement(logo);
		waitForPageLoad();
	}

	/**
	 * This method is used to get the total cart current quantity
	 *
	 * @return an integer value
	 */
	public int getCartQuantity( )
	{

		int shoppingCarQuantity = -1;

		WebElement cartContainer = wait.waitForElementEnabled(shoppingCart);
		WebElement cartAmount = wait.waitForElementDisplayed(cartQuantity, cartContainer);
		String quantity = text.getElementText(cartAmount);

		shoppingCarQuantity = Integer.parseInt(quantity);

		return shoppingCarQuantity;
	}

	/**
	 * get pairs containing the expected and actual price, quantity, and subtotal values
	 *
	 * @param item             to get the price, quantity, and subtotal of
	 * @param expectedQuantity the expected quantity of the item
	 *
	 * @return pairs containing the expected and actual price, quantity, and subtotal values
	 */
	public Pair<SitecoreItemValues, SitecoreItemValues> getValues( final SitecoreItem item, final int expectedQuantity )
	{
		double expectedPrice = Double.valueOf(item.getPrice());
		double actualPrice = getItemUnitPrice(item.getName());

		double actualQuantity = (double) getItemQuantity(item.getName());
		double expectedQuantityDouble = (double) expectedQuantity;

		double expectedSubtotal = expectedPrice * expectedQuantity;
		double actualSubtotal = getItemTotal(item.getName());

		SitecoreItemValues actualItemValues = new SitecoreItemValues(actualPrice, actualQuantity, actualSubtotal);
		SitecoreItemValues expectedItemValues = new SitecoreItemValues(expectedPrice, expectedQuantityDouble,
			expectedSubtotal);

		Pair<SitecoreItemValues, SitecoreItemValues> actualExpectedItems = Pair.of(actualItemValues,
			expectedItemValues);

		return actualExpectedItems;
	}

	/**
	 * finds item and selects the item
	 *
	 * @param itemCategory    item category to select
	 * @param item            item to find
	 * @param itemQuantity    item quantity used to calculate subtotal
	 * @param expectedCartQty expected item quantity
	 *
	 * @return calculated subtotal for just the newly added item
	 */
	public double findItemAndAdd( final SitecoreItemCategory itemCategory, final SitecoreItem item,
		final int itemQuantity, final int expectedCartQty )
	{
		// select category as "BOOKS"
		selectCategory(itemCategory);

		// select an item and add to cart
		String itemName = item.getName();
		double itemUnitPrice = Double.parseDouble(item.getPrice());
		double subtotal = itemUnitPrice * itemQuantity;

		selectItem(itemName);
		clickAddToCartButton(Integer.toString(itemQuantity));

		return subtotal;
	}

	/**
	 * This method is used to click the Shopping Cart button/icon
	 */
	public void clickShoppingCartButton( )
	{
		wait.waitForElementEnabled(shoppingCart);
		click.clickElement(shoppingCart);
		wait.waitForElementEnabled(backToShoppingButton);
	}

	/**
	 * This method is used to remove all the existing items from the cart if any.
	 */
	public void removeAllItemsFromShoppingCart( )
	{
		wait.waitForElementEnabled(removeItem);

		for ( WebElement removeElement : element.getWebElements(removeItem) )
		{
			wait.waitForElementEnabled(removeElement);
			click.clickElement(removeElement);
			waitForPageLoad();
		}
		String noProductMsg = text.getElementText(noProductsInCartMsg);

		final String errMsg = "No products in cart.";
		if ( errMsg.equalsIgnoreCase(noProductMsg) )
		{
			VertexLogger.log("All items/products are removed from shopping cart", getClass());
		}
		else
		{
			VertexLogger.log(String.format("Shopping Cart Quantity: %s", getCartQuantity()), VertexLogLevel.ERROR,
				getClass());
		}
	}

	/**
	 * This method is used to click the Back To Shopping button
	 */
	public void clickBackToShoppingButton( )
	{
		wait.waitForElementEnabled(backToShoppingButton);
		click.javascriptClick(element.getWebElement(backToShoppingButton));
	}

	/**
	 * This method is used to select the required category
	 *
	 * @param categoryName - like "Books", "Electronics", etc.
	 */
	public void selectCategory( final SitecoreItemCategory categoryName )
	{

		WebElement categoryTableElementContainer = wait.waitForElementEnabled(categoryTableLoc);
		List<WebElement> categoryElements = wait.waitForAnyElementsDisplayed(By.tagName("a"),
			categoryTableElementContainer);

		WebElement targetCategory = element.selectElementByText(categoryElements, categoryName.getText());
		click.javascriptClick(targetCategory);
	}

	/**
	 * This method is used to select/click the given item image.
	 *
	 * @param itemName name of item to select
	 */
	public void selectItem( final String itemName )
	{
		List<WebElement> products = wait.waitForAllElementsPresent(productLoc);
		WebElement targetItem = null;
		for ( WebElement item : products )
		{
			item = element.getWebElement(By.tagName("img"), item);
			if ( itemName.equals(attribute.getElementAttribute(item, "title")) )
			{
				targetItem = item;
			}
		}
		if ( targetItem != null )
		{
			click.clickElement(targetItem);
		}
		else
		{
			VertexLogger.log(String.format("Item: %s not available", itemName), VertexLogLevel.ERROR, getClass());
		}
	}

	/**
	 * This method is used to add the item to cart with specified quantity.
	 *
	 * @param quantity quantity to enter for item in cart
	 */
	public void clickAddToCartButton( final String quantity )
	{

		int beforeQuantity = getCartQuantity();

		WebElement addToCart = element.getButtonByText("Add to cart");
		scroll.scrollElementIntoView(addToCart);
		wait.waitForElementEnabled(addToCart, 5);

		enterItemQuantity(quantity);
		wait.waitForElementEnabled(cartQuantity, 1);

		int afterQuantity = getCartQuantity();

		int totalQuantity = beforeQuantity + Integer.parseInt(quantity);
		if ( totalQuantity == afterQuantity )
		{
			VertexLogger.log("Item added to cart successfully", getClass());
		}
		else
		{
			VertexLogger.log("Item not added to cart", VertexLogLevel.ERROR, getClass());
		}
	}

	/**
	 * This method is used to set the item quantity
	 *
	 * @param quantity to enter for item
	 */
	public void enterItemQuantity( final String quantity )
	{
		wait.waitForElementEnabled(quantityInput);
		wait.waitForElementEnabled(shoppingCart);

		//For some reason on this page wait for javascript almost never works
		//text.enterText uses waitForPageLoad so sendkeys has to be used instead
		WebElement quantityElement = element.getWebElement(quantityInput);
		quantityElement.clear();

		quantityElement.sendKeys(quantity);
		wait.waitForElementEnabled(quantityElement);

		String addToCartText = "Add to cart";
		WebElement addToCartButton = element.getButtonByText(addToCartText);
		wait.waitForElementEnabled(addToCartButton);
		click.clickElement(addToCartButton);
		wait.waitForElementEnabled(quantityInput);
		wait.waitForAllElementsEnabled(addToCartCheckout);
	}

	/**
	 * This method is used to click the checkout button
	 *
	 * @return SitecoreCheckoutPage object
	 */
	public SitecoreCheckoutPage clickCheckoutButton( )
	{
		wait.waitForElementEnabled(checkoutButton);
		click.clickElement(checkoutButton);
		waitForPageLoad();

		SitecoreCheckoutPage checkoutPage = initializePageObject(SitecoreCheckoutPage.class);

		return checkoutPage;
	}

	/**
	 * This is method is used to logout from sitecore store front application.
	 */
	public void storeLogout( )
	{
		List<WebElement> logoutContainers = element.getWebElements(logoutLink);
		WebElement logout = null;

		final String logoutExpectedText = "Logout";
		for ( WebElement logoutElement : logoutContainers )
		{
			String logoutMessage = text.getElementText(logoutElement);
			if ( logoutMessage.equals(logoutExpectedText) )
			{
				logout = logoutElement;
			}
		}
		wait.waitForElementDisplayed(logout);
		click.javascriptClick(logout);
		waitForPageLoad();
	}

	/**
	 * This is method is used to logout from sitecore store front application if the
	 * user is already logged in.
	 */
	public void logoutFromStoreIfAlreadyLoggedIn( )
	{
		List<WebElement> logoutContainers = element.getWebElements(logoutLink);
		WebElement logout = element.selectElementByText(logoutContainers, "Logout");

		if ( logout != null && element.isElementDisplayed(logout) )
		{
			storeLogout();
		}
	}

	/**
	 * This method is used to update the specific item quantity
	 *
	 * @param itemName name of item to update quantity of
	 * @param quantity new quantity the item should have
	 */
	public void updateItemQuantity( final String itemName, final String quantity )
	{
		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> quantityContainers = wait.waitForAllElementsDisplayed(cartCheckoutQuantity);
		WebElement quantityContainer = quantityContainers.get(itemIndex);

		WebElement quantityInput = element.getWebElement(By.tagName("input"), quantityContainer);

		wait.waitForElementDisplayed(quantityInput);
		text.enterText(quantityInput, Keys.BACK_SPACE + quantity + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * This method is used to set value to the specific item text flex-filed
	 *
	 * @param itemName      name of item to set
	 * @param textFlexValue text to enter into flex field
	 */
	public void setItemTextFlexField( final String itemName, final String textFlexValue )
	{

		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> flexFieldTextInputs = wait.waitForAllElementsDisplayed(flexFieldTextLoc);
		WebElement flexFieldTextInput = flexFieldTextInputs.get(itemIndex);

		text.enterText(flexFieldTextInput, Keys.BACK_SPACE + textFlexValue + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * This method is used to set value to the specific item text flex-filed
	 *
	 * @param itemName      name of item to set number of
	 * @param textFlexValue value to enter into flex field
	 */
	public void setItemNumberFlexField( final String itemName, final String textFlexValue )
	{

		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> flexFieldNumInputs = wait.waitForAllElementsDisplayed(flexFieldNumLoc);
		WebElement flexFieldNumInput = flexFieldNumInputs.get(itemIndex);

		text.enterText(flexFieldNumInput, Keys.BACK_SPACE + textFlexValue + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * This method is used to set value to the specific item text flex-filed
	 *
	 * @param itemName      name of item to set the date of
	 * @param textFlexValue value to enter into flex field
	 */
	public void setItemDateFlexField( final String itemName, final String textFlexValue )
	{

		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> flexFieldDateInputs = wait.waitForAllElementsDisplayed(flexFieldDateLoc);
		WebElement flexFieldDateInput = flexFieldDateInputs.get(itemIndex);

		text.enterText(flexFieldDateInput, Keys.BACK_SPACE + textFlexValue + Keys.TAB);
		waitForPageLoad();
	}

	/**
	 * This method is used to get the specific item quantity
	 *
	 * @param itemName name of item to get quantity of
	 *
	 * @return quantity of item
	 */
	public int getItemQuantity( final String itemName )
	{

		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> quantityContainers = wait.waitForAllElementsDisplayed(cartCheckoutQuantity);
		WebElement quantityContainer = quantityContainers.get(itemIndex);

		WebElement quantityInput = element.getWebElement(By.tagName("input"), quantityContainer);

		String qty = attribute
			.getElementAttribute(quantityInput, "value")
			.trim();
		int q = Integer.parseInt(qty);
		return q;
	}

	/**
	 * This method is used to get the specific item unit price.
	 *
	 * @param itemName name of item to get unit price of
	 *
	 * @return item unit price
	 */
	public double getItemUnitPrice( final String itemName )
	{

		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> itemContainers = wait.waitForAllElementsDisplayed(unitPriceLoc);
		WebElement itemPrice = itemContainers.get(itemIndex);

		String price = text.getElementText(itemPrice);
		double unitPrice = VertexCurrencyUtils.cleanseCurrencyString(price);
		return unitPrice;
	}

	/**
	 * This method is used to get the specific item total amount.
	 *
	 * @param itemName name of item to get total cost of
	 *
	 * @return total of item
	 */
	public double getItemTotal( final String itemName )
	{

		int itemIndex = -1;
		itemIndex = getProductIndex(itemName);

		List<WebElement> itemContainers = wait.waitForAllElementsDisplayed(totalPriceLoc);
		WebElement totalPrice = itemContainers.get(itemIndex);

		String price = text.getElementText(totalPrice);
		double itemTotal = VertexCurrencyUtils.cleanseCurrencyString(price);
		return itemTotal;
	}

	/**
	 * This method is used to get the cart total amount
	 *
	 * @return total in card
	 */
	public double getCartTotal( )
	{

		String price = text.getElementText(cartTotal);
		double cartTotal = VertexCurrencyUtils.cleanseCurrencyString(price);
		return cartTotal;
	}

	/**
	 * return true if homepage is displayed false otherwise
	 *
	 * @return true if homepage is displayed false otherwise
	 */
	public boolean isHomePageDisplayed( )
	{

		boolean resultFlag = false;

		try
		{
			wait.waitForElementDisplayed(logo, FIVE_SECOND_TIMEOUT);
		}
		catch ( Exception e )
		{
			VertexLogger.log(String.format("Homepage logo not found"), getClass());
			e.printStackTrace();
		}

		resultFlag = element.isElementDisplayed(logo);

		return resultFlag;
	}
}
