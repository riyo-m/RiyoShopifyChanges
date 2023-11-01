package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class represents the page on which we see the items in the cart
 * contains all the methods necessary to interact with the elements in the page
 *
 * @author osabha
 */
public class KiboStoreFrontCartPage extends VertexPage
{
	protected By maxineHeadingLoc = By.id("store-branding");
	protected By maxineHeadingTag = By.tagName("a");
	protected By checkoutButtonLoc = By.id("cart-checkout");
	protected By cartForm = By.id("cartform");
	protected By emptyCartMSG = By.xpath(".//*[@id='cartform']/div[normalize-space(.)='You do not have any items in your cart.']");
	protected By emptyCartButton = By.xpath(".//a[normalize-space(.)='Empty Cart']");
	protected By yourCartLabel = By.xpath(".//h2[contains(normalize-space(.),'Your Cart')]");
	protected By applyCouponBox = By.id("coupon-code");
	protected By applyCouponButton = By.id("cart-coupon-code");

	public KiboStoreFrontCartPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * this method locates and clicks the Maxine heading in the cart page, which redirects the user
	 * back to the store to shop for more items
	 *
	 * @return maxine heading in the cart page
	 */
	public KiboStoreFrontPage clickMaxineHeading( )
	{
		WebElement maxineHeadingContainer = wait.waitForElementPresent(maxineHeadingLoc);
		WebElement maxineHeading = wait.waitForElementPresent(maxineHeadingTag, maxineHeadingContainer);
		maxineHeading.click();
		return new KiboStoreFrontPage(driver);
	}

	/**
	 * locates and clicks on the check out button
	 *
	 * @return new instance of the maxine live check out page class
	 */
	public KiboStoreFrontCheckoutPage goToCheckoutPage( )
	{
		WebElement checkoutButton = wait.waitForElementPresent(checkoutButtonLoc);
		new WebDriverWait(driver, 5)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(checkoutButton));
		checkoutButton.click();
		waitForPageLoad();
		return new KiboStoreFrontCheckoutPage(driver);
	}

	/**
	 * Clears the cart if any item exists
	 */
	public void clearCart() {
		waitForPageLoad();
		wait.waitForElementPresent(cartForm);
		wait.waitForElementPresent(yourCartLabel);
		if (!element.isElementPresent(emptyCartMSG)) {
			click.moveToElementAndClick(wait.waitForElementPresent(emptyCartButton));
		}
		waitForPageLoad();
		wait.waitForElementPresent(emptyCartMSG);
	}

	/**
	 * Applies coupon code to the order to avail discount
	 *
	 * @param couponName name of coupon which is to be intended to avail discount.
	 */
	public void applyDiscountCoupon(String couponName) {
		waitForPageLoad();
		wait.waitForElementPresent(yourCartLabel);
		WebElement couponBox = wait.waitForElementPresent(applyCouponBox);
		text.enterText(couponBox, couponName);
		click.moveToElementAndClick(wait.waitForElementPresent(applyCouponButton));
	}
}
