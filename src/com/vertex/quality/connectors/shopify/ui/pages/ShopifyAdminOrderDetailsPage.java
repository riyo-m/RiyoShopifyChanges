package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shopify Admin panel order details page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminOrderDetailsPage extends ShopifyPage
{

	protected By orderDetailsPageHeader = By.xpath(".//h1[contains(@class,'Polaris-Header-Title')]");
	protected String orderIdInsideOrderDetails = ".//h1[text()='<<order_id>>']";
	protected String orderStatusInsideOrderDetails
		= ".//h1[text()='<<order_id>>']/following-sibling::div//span[text()='<<order_status>>']";
	protected By subtotalLabel = By.xpath("(.//span[normalize-space(.)='Subtotal'])[1]");
	protected By subtotalAmount = By.xpath(
		"((.//span[normalize-space(.)='Subtotal'])[1]/following-sibling::div//span)[last()]");
	protected By shippingLabel = By.xpath("(.//span[normalize-space(.)='Shipping'])[1]");
	protected By shippingAmount = By.xpath(
		"((.//span[normalize-space(.)='Shipping'])[1]/following-sibling::div//span)[last()]");
	protected By taxLabel = By.xpath("(.//span[normalize-space(.)='Tax'])[1]");
	protected By taxAmount = By.xpath("((.//span[normalize-space(.)='Tax'])[1]/following-sibling::div//span)[last()]");
	protected By refundButtonOnHeader = By.xpath("(.//span[text()='Refund'])[1]");
	protected By returnButtonOnHeader = By.xpath("(.//span[text()='Return'])[1]");
	protected By refundButtonOnBody = By.xpath("(.//span[text()='Refund'])[last()]");
	protected By refundItemsLabel = By.xpath(".//h1[contains(text(),'Refund')]");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ShopifyAdminOrderDetailsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Fetches the order ID from the URL to make API call
	 *
	 * @param orderNo Order number from the UI, which Order ID from the current URL to be fetched.
	 *
	 * @return order id from the URL.
	 */
	public String getOrderIdFromUIForAPICall( String orderNo )
	{
		waitForPageLoad();
		wait.waitForElementPresent(By.xpath(orderIdInsideOrderDetails.replace("<<order_id>>", orderNo)));
		String url = getCurrentUrl();
		int index = url.lastIndexOf("/");
		return url.substring(index + 1);
	}

	/**
	 * Verify the order's status
	 *
	 * @param orderNo        Order Number which status to be verified
	 * @param expectedStatus Order's expected status
	 *
	 * @return true or false based on condition match
	 */
	public boolean verifyOrderStatus( String orderNo, String expectedStatus )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderDetailsPageHeader);
		wait.waitForElementPresent(By.xpath(orderIdInsideOrderDetails.replace("<<order_id>>", orderNo)));
		return element.isElementDisplayed(By.xpath(orderStatusInsideOrderDetails
			.replace("<<order_id>>", orderNo)
			.replace("<<order_status>>", expectedStatus)));
	}

	/**
	 * It will verify whether tax label & amount is present on UI or not - Used in customer exemption tests
	 *
	 * @return true or false based on condition match
	 */
	public boolean isTaxPresentOnUI( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		waitTillCalculatingTaxDisappears();
		return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
	}

	/**
	 * It will verify whether tax label & amount is present on UI or not
	 * This is specific for digital goods, because once digital goods are in the basket then labels are different.
	 *
	 * @return true or false based on condition match
	 */
	public boolean isTaxPresentOnUIBeforeOrderPlaceDigitalProducts( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(subtotalAmount);
		waitTillCalculatingTaxDisappears();
		return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
	}

	/**
	 * Gets tax amount from the UI
	 *
	 * @return tax amount from UI.
	 */
	public double getTaxFromUI( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		wait.waitForElementPresent(taxLabel);
		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(taxAmount))
			.replace("$", "")
			.replace(",", ""));
	}

	/**
	 * Calculates percentage based or expected tax
	 *
	 * @param taxAmount percent of tax
	 *
	 * @return calculated percent based tax
	 */
	public double calculatePercentBasedTax( double taxAmount )
	{
		double subtotal = 0;
		String ship;
		double shipping = 0;
		double expectedTax = 0;
		waitForPageLoad();
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		subtotal = Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(subtotalAmount))
			.replace("$", "")
			.replace(",", ""));
		ship = text.getElementText(wait.waitForElementPresent(shippingAmount));
		if ( ship.equalsIgnoreCase("Free") )
		{
			shipping = 0;
		}
		else
		{
			shipping = Double.parseDouble(ship
				.replace("$", "")
				.replace(",", ""));
		}
		expectedTax = (subtotal + shipping) * (taxAmount / 100);
		return Double.parseDouble(String.format("%.2f", expectedTax));
	}

	/**
	 * Navigates to return page
	 *
	 * @return Shopify Return Page
	 */
	public ShopifyAdminReturnPage gotoReturnPage( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderDetailsPageHeader);
		click.clickElement(returnButtonOnHeader);
		waitForPageLoad();
		wait.waitForElementPresent(refundItemsLabel);
		return new ShopifyAdminReturnPage(driver);
	}

	/**
	 * Navigates to refund page
	 *
	 * @return Shopify Refund Page
	 */
	public ShopifyAdminRefundPage gotoRefundPage( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderDetailsPageHeader);
		click.clickElement(refundButtonOnHeader);
		waitForPageLoad();
		wait.waitForElementPresent(refundItemsLabel);
		return new ShopifyAdminRefundPage(driver);
	}

	/**
	 * CLicks on refund button
	 *
	 * @return Shopify Refund Page
	 */
	public ShopifyAdminRefundPage clickRefundButton( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderDetailsPageHeader);
		click.moveToElementAndClick(wait.waitForElementPresent(refundButtonOnBody));
		waitForPageLoad();
		wait.waitForElementPresent(refundItemsLabel);
		return new ShopifyAdminRefundPage(driver);
	}
}
