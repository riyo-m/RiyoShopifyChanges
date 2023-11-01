package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * represents the page to perform operations on Electronics Store Page - AddToCart Page
 * i.e. Search for Product, Add to Cart and proceed to Checkout
 *
 * @author Nagaraju Gampa
 */
public class HybrisEStorePage extends HybrisBasePage
{
	public HybrisEStorePage( WebDriver driver )
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
	protected By ADDTOCART_INPUT = By.id("pdpAddtoCartInput");
	protected By COUPON_CODE_BOX = By.xpath(".//input[@id='js-voucher-code-text']");
	protected By APPLY_COUPON_BUTTON = By.xpath(".//button[@id='js-voucher-apply-btn']");
	protected By APPLIED_COUPONS_MESSAGE = By.cssSelector(".js-voucher-validation-container");
	protected By APPLIED_COUPONS = By.xpath(".//span[@class='js-release-voucher voucher-list__item-box']");
	protected By SUBTOTAL_LABEL = By.xpath(".//div[normalize-space(.)='Subtotal:']");
	protected By SUBTOTAL_AMOUNT = By.xpath(".//div[normalize-space(.)='Subtotal:']/following-sibling::div[1]");
	protected By ORDER_DISCOUNT_AMOUNT = By.xpath(".//div[normalize-space(.)='Order Discounts:']/following-sibling::div[1]");
	protected By ORDER_TOTAL_LABEL = By.xpath(".//div[normalize-space(.)='Order Total']");
	protected String REMOVE_COUPON = ".//span[normalize-space(.)='<<text_replace>>']/child::span";

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
	public HybrisEStoreCartPage navigateToCart( )
	{
		wait.waitForElementEnabled(CART_ICON);
		click.clickElement(CART_ICON);
		wait.waitForElementDisplayed(CART_CHECKOUT);
		click.clickElement(CART_CHECKOUT);
		HybrisEStoreCartPage cartPage = initializePageObject(HybrisEStoreCartPage.class);
		return cartPage;
	}

	/***
	 * Search for ProductID and Navigate to Product
	 * @param productID - Enter the ID of the product which will be added to Cart
	 */
	public void searchAndSelectProduct( String productID )
	{
		searchProductID(productID);
		selectProductID();
		wait.waitForElementDisplayed(ADDTOCART_PRODUCTPAGE);
	}

	/***
	 * Add the Product To Cart
	 */
	public void addProductToCart( )
	{
		wait.waitForElementDisplayed(ADDTOCART_PRODUCTPAGE);
		click.clickElement(ADDTOCART_PRODUCTPAGE);
		waitForPageLoad();
	}

	/***
	 * Search for ProductID and Add to Cart
	 * @param productID - Enter the ID of the product which will be added to Cart
	 */
	public void searchAndAddProductToCart( String productID )
	{
		searchAndSelectProduct(productID);
		addProductToCart();
		waitForPageLoad();
	}

	/***
	 * updateProductQuantity - Increase or Decrease Product Quantity
	 * @param quantity - Quantity to increase or decrease
	 *
	 */
	public void updateProductQuantity( String quantity )
	{
		wait.waitForElementDisplayed(ADDTOCART_INPUT);
		click.performDoubleClick(wait.waitForElementDisplayed(ADDTOCART_INPUT));
		text.enterText(ADDTOCART_INPUT, quantity, false);
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
	public HybrisEStoreGuestLoginPage proceedToCheckout( )
	{
		click.clickElement(CHECKOUT_BUTTON);
		waitForPageLoad();
		if ( element.isElementDisplayed(CONTINUE_CHECKOUT_BUTTON) )
		{
			click.clickElement(CONTINUE_CHECKOUT_BUTTON);
			waitForPageLoad();
		}
		final HybrisEStoreGuestLoginPage eStoreGuestLoginPage = initializePageObject(HybrisEStoreGuestLoginPage.class);
		return eStoreGuestLoginPage;
	}

	/**
	 * Method to proceed check out by selecting checkout
	 */
	public void doCheckout() {
		click.clickElement(CHECKOUT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Method to click on continue.
	 */
	public void clickContinue() {
		if (element.isElementDisplayed(CONTINUE_CHECKOUT_BUTTON)) {
			click.clickElement(CONTINUE_CHECKOUT_BUTTON);
			waitForPageLoad();
		}
	}

	/**
	 * Add coupon and apply to order
	 *
	 * @param coupon enter coupon which is to be applied to the order
	 */
	public void addCouponAndApply(String coupon) {
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(COUPON_CODE_BOX);
		text.enterText(COUPON_CODE_BOX, coupon);
		wait.waitForElementPresent(APPLY_COUPON_BUTTON);
		click.moveToElementAndClick(APPLY_COUPON_BUTTON);
		hybrisWaitForPageLoad();
	}

	/**
	 * Remove applied coupon from the order
	 *
	 * @param coupon enter coupon which is to be applied to the order
	 */
	public void removeAppliedCoupon(String coupon) {
		hybrisWaitForPageLoad();
		WebElement remove = wait.waitForElementPresent(By.xpath(REMOVE_COUPON.replace("<<text_replace>>", coupon)));
		click.moveToElementAndClick(remove);
		hybrisWaitForPageLoad();
	}

	/**
	 * Verify that whether coupon is applied or not?
	 *
	 * @param coupon enter coupon which is to be applied to the order
	 * @return true or false based on condition
	 */
	public boolean verifyAppliedCoupons(String coupon) {
		boolean isVerified = false;
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(SUBTOTAL_LABEL);
		wait.waitForElementPresent(ORDER_TOTAL_LABEL);
		if (element.isElementPresent(APPLIED_COUPONS_MESSAGE)) {
			if (text.getElementText(APPLIED_COUPONS).contains(coupon.toUpperCase(Locale.ROOT))
			&& text.getElementText(APPLIED_COUPONS_MESSAGE).contains(coupon)) {
				isVerified = true;
			}
		}
		return isVerified;
	}

	/**
	 * Takes subtotal amount from UI
	 *
	 * @return sub-total amount
	 */
	public double getSubTotalAmount() {
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(SUBTOTAL_LABEL);
		return Double.parseDouble(text.getElementText(SUBTOTAL_AMOUNT).replace("$", ""));
	}

	/**
	 * Takes discount amount of order from the UI
	 *
	 * @return discounted amount
	 */
	public double getDiscountAmount() {
		double discountAmount = 0;
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(SUBTOTAL_LABEL);
		if (element.isElementPresent(ORDER_DISCOUNT_AMOUNT)) {
			discountAmount =
					Double.parseDouble(String.format("%.2f",
							Double.parseDouble(text.getElementText(ORDER_DISCOUNT_AMOUNT).trim().replace("-", "").replace("$", ""))));
		}
		return discountAmount;
	}

	/**
	 * Compares actual discount from the UI & expected discount from the test data.
	 *
	 * @param expectedDiscount intended discount amount
	 * @return true or false based on condition
	 */
	public boolean verifyAppliedCouponDiscount(double expectedDiscount) {
		boolean isVerified = false;
		double actualDiscount = 0;
		hybrisWaitForPageLoad();
		wait.waitForElementPresent(SUBTOTAL_LABEL);
		actualDiscount = getDiscountAmount();
		if (actualDiscount == expectedDiscount) {
			isVerified = true;
		}
		return isVerified;
	}

	/**
	 * Calculates the expected discount amount for Percentage based discount coupons.
	 *
	 * @param productAmountOrTotalAmount Product amount or Total amount on which coupon is applicable
	 * @param discountPercent            coupon's percentage
	 * @return expected calculated discount amount
	 */
	public double calculatePercentBasedDiscount(double productAmountOrTotalAmount, double discountPercent) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.DOWN);
		return Double.parseDouble(df.format(productAmountOrTotalAmount * (discountPercent / 100)));
	}
}
