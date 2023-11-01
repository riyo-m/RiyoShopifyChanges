package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Lightning Commerce Page.
 *
 * @author brenda.johnson
 */
public class SalesForceB2BBuyerPage extends SalesForceBasePage
{
	protected By LINK_HOME = By.xpath(".//community_navigation-multi-level-navigation/nav/ul/li[1]/a/span");
	protected By SKU_INPUT = By.xpath("//input[contains(@id,'skuNumber-')]");
	protected By QUANTITY_INPUT = By.xpath("//input[contains(@id,'quantity-')]");
	protected By BUTTON_ADD_TO_CART = By.xpath(".//button[contains(@class, 'addToCartButton')]");
	protected By BUTTON_VIEW_CART = By.xpath(".//a[contains(text(),'View Cart')]");
	protected By BUTTON_PROCEED_TO_CHECKOUT = By.xpath(
		"//commerce_checkout-checkout-button/button[@title='Proceed To Checkout']");
	protected By DELIVERY_METHOD_AMOUNT = By.xpath(
		".//span[text()='Test Delivery Group Method']/../following-sibling::b2b_buyer_pricing-formatted-price");
	protected By BUTTON_NEXT = By.xpath(".//lightning-button/button[text()='Next']");
	protected By ESTIMATED_TAX = By.xpath(
		".//div[contains(text(), 'Estimated Tax')]/.//b2b_buyer_pricing-formatted-price");
	protected By BUTTON_SUBMIT_PAYMENT = By.xpath("//*[text()='Submit Payment']");
	protected By PROFILE_IMG = By.xpath(
		"//community_user-user-profile-menu/.//span[contains(@class, 'slds-avatar')]/img");
	protected By LINK_LOGOUT = By.xpath(".//li[contains(@class, 'textMenuItem')]/a/span[text()='Log Out']");
	protected By LINK_CLEAR_CART = By.xpath(".//footer/lightning-button/button[text()='Clear Cart']");
	protected By LINK_CANCEL_CHECKOUT = By.xpath(".//b2b_buyer_cart-async-alert/div/h2/a[text()='Cancel Checkout']");
	protected By LINK_CART = By.xpath(".//b2b_buyer_cart-badge/span/a/span");
	protected By BUTTON_CLEAR_CART = By.xpath("//*[contains(@id,'modal-content')]/div/slot/div/div[2]/button");
	protected By ERROR_MESSAGE = By.xpath(".//div//p/span[contains(text(), \"Error\")]/../span[2]");
	protected By CANCEL_CART_BUTTON = By.xpath(".//commerce_checkout-cancel-button/a");
	protected By NAVIGATE_TO_CART_BUTTON = By.xpath(".//span[@title='Navigate to cart.']");

	public SalesForceB2BBuyerPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Navigate Home
	 */
	public void navigateHome( )
	{
		wait.waitForElementEnabled(LINK_HOME);
		click.clickElement(LINK_HOME);
		waitForPageLoad();
	}

	/**
	 * Set Quick Order Item Sku
	 *
	 * @param itemSku
	 */
	public void setQuickOrderItemSku( String itemSku )
	{
		wait.waitForElementDisplayed(SKU_INPUT);
		text.enterText(SKU_INPUT, itemSku);
	}

	/**
	 * Set Quick Order Quantity
	 *
	 * @param itemSku
	 * @param lineNumber
	 */
	public void setQuickOrderSkuLine( String itemSku, int lineNumber )
	{
		waitForSalesForceLoaded();
		String lineNumberQuantity = String.format("//li[%s]//input[contains(@id,'skuNumber-')]", lineNumber);
		By lineQuantity = By.xpath(lineNumberQuantity);
		wait.waitForElementDisplayed(lineQuantity);
		text.enterText(lineQuantity, itemSku + Keys.TAB);
	}

	/**
	 * Set Quick Order Quantity
	 *
	 * @param quantity
	 */
	public void setQuickOrderQuantity( String quantity )
	{
		wait.waitForElementDisplayed(QUANTITY_INPUT);
		text.enterText(QUANTITY_INPUT, quantity + Keys.TAB);
	}

	/**
	 * Set Quick Order Quantity
	 *
	 * @param quantity
	 * @param lineNumber
	 */
	public void setQuickOrderQuantityLine( String quantity, int lineNumber )
	{
		String lineNumberQuantity = String.format("//li[%s]//input[contains(@id,'quantity-')]", lineNumber);
		By lineQuantity = By.xpath(lineNumberQuantity);
		wait.waitForElementDisplayed(lineQuantity);
		text.enterText(lineQuantity, quantity + Keys.TAB);
	}

	/**
	 * click Quick Order Add to Cart
	 */
	public void clickQuickOrderAddToCart( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(BUTTON_ADD_TO_CART);
		click.clickElement(BUTTON_ADD_TO_CART);
		waitForPageLoad();
	}

	/**
	 * click view cart
	 */
	public void clickViewCart( )
	{
		wait.waitForElementDisplayed(BUTTON_VIEW_CART);
		wait.waitForElementEnabled(BUTTON_VIEW_CART);
		click.javascriptClick(BUTTON_VIEW_CART);
		waitForPageLoad();
	}

	/**
	 * click proceed to checkout
	 */
	public void clickProceedToCheckout( )
	{
		wait.waitForElementDisplayed(BUTTON_PROCEED_TO_CHECKOUT);
		wait.waitForElementEnabled(BUTTON_PROCEED_TO_CHECKOUT);
		click.clickElement(BUTTON_PROCEED_TO_CHECKOUT);
		waitForPageLoad();
	}

	/**
	 * Select Shipping Address Radio Button
	 *
	 * @param address
	 */
	public void selectShippingAddress( String address )
	{
		VertexLogger.log(address);
		String selector = String.format(
			"//commerce_checkout-order-address/div/div/commerce_checkout-address-selector/lightning-radio-group/fieldset/div/span/label/span[contains(text(), '%s')]",
			address);
		wait.waitForElementDisplayed(By.xpath(selector));
		wait.waitForElementEnabled(By.xpath(selector));
		click.clickElement(By.xpath(selector));
		clickNextButton();
		waitForDeliveryMethod();
	}

	/**
	 * wait for Delivery Method page to display
	 */
	public void waitForDeliveryMethod( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(DELIVERY_METHOD_AMOUNT);
	}

	/**
	 * click next button
	 */
	public void clickNextButton( )
	{
		wait.waitForElementDisplayed(BUTTON_NEXT);
		// if the next button is on the page click it, keeps qa org in line with with dev org
		if ( driver.findElement(BUTTON_NEXT) != null )
		{
			click.clickElement(BUTTON_NEXT);
			waitForPageLoad();
			waitForSalesForceLoaded();
		}
	}

	/**
	 * click Submit Payment button
	 */
	public void clickSubmitPaymentButton( )
	{
		wait.waitForElementDisplayed(BUTTON_SUBMIT_PAYMENT);
		wait.waitForElementEnabled(BUTTON_SUBMIT_PAYMENT);
		click.clickElement((BUTTON_SUBMIT_PAYMENT));
		waitForPageLoad();
	}

	/**
	 * click user profile
	 */
	public void clickUserProfile( )
	{
		wait.waitForElementDisplayed(PROFILE_IMG);
		wait.waitForElementEnabled(PROFILE_IMG);
		click.javascriptClick((PROFILE_IMG));
	}

	/**
	 * click Logout
	 */
	public void clickLogout( )
	{
		wait.waitForElementDisplayed(LINK_LOGOUT);
		wait.waitForElementEnabled(LINK_LOGOUT);
		click.javascriptClick((LINK_LOGOUT));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	public String getErrorMessage()
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ERROR_MESSAGE);
		wait.waitForElementEnabled(ERROR_MESSAGE);
		String sectionText = text.getElementText(ERROR_MESSAGE);
		VertexLogger.log(sectionText);
		return sectionText;
	}

	/**
	 * get Estimated Tax field
	 *
	 * @return estimated Tax
	 */
	public String getEstimatedTax( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ESTIMATED_TAX);
		wait.waitForElementEnabled(ESTIMATED_TAX);
		String sectionText = text.getElementText(ESTIMATED_TAX);
		return sectionText;
	}

	/**
	 * Navigates back to cart and clears checkout
	 */
	public void navigateToCartIcon()
	{
		wait.waitForElementDisplayed(CANCEL_CART_BUTTON);
		click.javascriptClick(CANCEL_CART_BUTTON);
		cancelAndClearCheckout();
	}

	/**
	 * click Cancel Checkout link
	 */
	public void cancelCheckout( )
	{
		navigateToCart();
		wait.waitForElementDisplayed(LINK_CANCEL_CHECKOUT);
		wait.waitForElementEnabled(LINK_CANCEL_CHECKOUT);
		click.javascriptClick((LINK_CANCEL_CHECKOUT));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * click Cart link
	 */
	public void navigateToCart( )
	{
		wait.waitForElementDisplayed(LINK_CART);
		wait.waitForElementEnabled(LINK_CART);
		click.javascriptClick((LINK_CART));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * clear out cart
	 */
	public void clearCart( )
	{
		navigateToCart();
		jsWaiter.sleep(3000);
		waitForSalesForceLoaded();
		if ( element.isElementDisplayed(LINK_CLEAR_CART) )
		{
			wait.waitForElementDisplayed(LINK_CLEAR_CART);
			wait.waitForElementEnabled(LINK_CLEAR_CART);
			click.javascriptClick((LINK_CLEAR_CART));
			waitForPageLoad();
			wait.waitForElementDisplayed(BUTTON_CLEAR_CART);
			wait.waitForElementEnabled(BUTTON_CLEAR_CART);
			click.javascriptClick((BUTTON_CLEAR_CART));
		}
		waitForSalesForceLoaded();
	}

	/**
	 * cancel checkout and clear existing cart
	 */
	public void cancelAndClearCheckout( )
	{
		try
		{
			waitForSalesForceLoaded();
			if ( element.isElementDisplayed(LINK_CANCEL_CHECKOUT) )
			{
				cancelCheckout();
			}
			clearCart();
			navigateHome();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Set Quick Order fields
	 *
	 * @param itemSku
	 * @param quantity
	 */
	public void setQuickOrder( String itemSku, String quantity )
	{
		waitForPageLoad();
		window.switchToWindow();
		setQuickOrderItemSku(itemSku);
		setQuickOrderQuantity(quantity);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Quick Order fields
	 *
	 * @param itemSku
	 * @param quantity
	 * @param lineNumber
	 */
	public void setQuickOrderMultiple( String itemSku, String quantity, int lineNumber )
	{
		waitForPageLoad();
		window.switchToWindow();
		setQuickOrderSkuLine(itemSku, lineNumber);
		setQuickOrderQuantityLine(quantity, lineNumber);
	}

	/**
	 * Go back to cart from checkout page
	 */
	public void navigateBackToCart(){
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(NAVIGATE_TO_CART_BUTTON);
		click.clickElement(NAVIGATE_TO_CART_BUTTON);
	}
}