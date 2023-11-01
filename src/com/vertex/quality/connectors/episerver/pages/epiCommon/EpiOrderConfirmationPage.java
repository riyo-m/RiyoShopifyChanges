package com.vertex.quality.connectors.episerver.pages.epiCommon;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EpiOrderConfirmationPage extends VertexPage
{
	public EpiOrderConfirmationPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ORDER_NUMBER = By.xpath("//*[starts-with(text(), 'Order ID')]");
	protected By QUANTITY = By.xpath("//*[label[text()='Quantity']]");
	protected By PRICE = By.xpath("//*[label[text()='Price']]/span");
	protected By ADDITIONAL_DISCOUNTS = By.xpath(
		"//*[td[text()='Additional discounts']]/td[contains(@class, 'total-price')]");
	protected By HANDLING_COST = By.xpath("//*[td[text()='Handling cost']]/td[contains(@class, 'total-price')]");
	protected By SHIPPING_SUBTOTAL = By.xpath(
		"//*[td[text()='Shipping Subtotal']]/td[contains(@class, 'total-price')]");
	protected By SHIPPING_DISCOUNT = By.xpath(
		"//*[td[text()='Shipping Discount']]/td[contains(@class, 'total-price')]");
	protected By SHIPPING_COST = By.xpath(".//td[text()='Shipping cost']/following-sibling::td/span");
	protected By TAX_COST = By.xpath(".//td[text()='Tax cost']/following-sibling::td/span");
	protected By FINAL_TOTAL = By.xpath("//*[td/*[text()='Total']]/td[contains(@class, 'total-price')]");
	protected By SHIPPING_ITEM_TOTAL = By.xpath(".//td[text()='Shipping Items Total']/following-sibling::td/span");

	/**
	 * This method is used to get Order Number
	 *
	 * @return Order Number
	 */
	public String getOrderNumber( )
	{
		String actualOrderNumber = null;
		wait.waitForElementDisplayed(ORDER_NUMBER);
		String orderNumber = attribute.getElementAttribute(ORDER_NUMBER, "text");
		if ( orderNumber instanceof String )
		{
			orderNumber = orderNumber
				.replace("Order ID:", "")
				.trim();
		}
		if ( orderNumber != null )
		{
			VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber));
			actualOrderNumber = orderNumber;
		}
		else
		{
			VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber), VertexLogLevel.ERROR);
		}
		return actualOrderNumber;
	}

	/**
	 * This method is used to get Amount
	 *
	 * @return Amount
	 */
	public Double getAmount( WebElement amountElement )
	{
		wait.waitForElementDisplayed(amountElement);
		String amt = amountElement.getText();
		amt = amt
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double amount = Double.parseDouble(amt);
		return amount;
	}

	/**
	 * This method is used to get Quantity
	 *
	 * @return Quantity
	 */
	public int getQuantity( String productName )
	{
		EpiCheckoutPage order = new EpiCheckoutPage(driver);
		int qty = 0;
		if ( productName != null )
		{
			By quantityTuple = By.xpath(order.getProductXpath(productName) + "//*[label[text()='Quantity']]");
			wait.waitForElementPresent(quantityTuple);
			String qty_1 = attribute.getElementAttribute(quantityTuple, "textContent");
			qty_1 = qty_1
				.replace("Quantity", "")
				.trim()
				.replace(" ", "");
			qty = Integer.parseInt(qty_1);
		}
		return qty;
	}

	/**
	 * This method is used to get UnitPrice
	 *
	 * @return UnitPrice
	 */
	public Double getUnitPrice( String productName )
	{
		EpiCheckoutPage order = new EpiCheckoutPage(driver);
		Double unitPrice = null;
		if ( productName != null )
		{
			By UNITPRICE = By.xpath(order.getProductXpath(productName) + "//*[label[text()='Unit price']]");
			wait.waitForElementDisplayed(UNITPRICE);
			WebElement UNITPRICE_ELE = element.getWebElement(UNITPRICE);
			unitPrice = getAmount(UNITPRICE_ELE);
		}
		return unitPrice;
	}

	/**
	 * This method is used to get Price
	 *
	 * @return Price
	 */
	public Double getPrice( String productName )
	{
		EpiCheckoutPage order = new EpiCheckoutPage(driver);
		Double price = null;
		if ( productName != null )
		{
			By PRODUCT_PRICE = By.xpath(order.getProductXpath(productName) + "//*[label[text()='Price']]");
			wait.waitForElementDisplayed(PRODUCT_PRICE);
			WebElement priceEle = element.getWebElement(PRODUCT_PRICE);
			price = getAmount(priceEle);
		}
		return price;
	}

	/**
	 * This method is used to get Discount
	 *
	 * @return Discount
	 */
	public Double getDiscount( String productName )
	{
		EpiCheckoutPage order = new EpiCheckoutPage(driver);
		Double price = null;
		if ( productName != null )
		{
			By ITEM_DISCOUNT = By.xpath(order.getProductXpath(productName) + "//*[label[text()='Discount']]");
			wait.waitForElementDisplayed(ITEM_DISCOUNT);
			WebElement discountEle = element.getWebElement(ITEM_DISCOUNT);
			price = getAmount(discountEle);
		}

		return price;
	}

	/**
	 * This method is used to get Total Amount without Tax
	 *
	 * @return Total Amount without Tax
	 */
	public Double getTotalWithOutTax( String productName )
	{
		EpiCheckoutPage order = new EpiCheckoutPage(driver);
		Double total = null;
		if ( productName != null )
		{
			By ITEM_TOTAL = By.xpath(order.getProductXpath(productName) + "//*[label[text()='Total']]");
			wait.waitForElementDisplayed(ITEM_TOTAL);
			WebElement totalEle = element.getWebElement(ITEM_TOTAL);
			total = getAmount(totalEle);
		}
		return total;
	}

	/**
	 * This method is used to get Additional Discount
	 *
	 * @return Additional Discount
	 */
	public Double getAdditionalDiscounts( )
	{
		wait.waitForElementDisplayed(ADDITIONAL_DISCOUNTS);
		WebElement AddDiscounts = element.getWebElement(ADDITIONAL_DISCOUNTS);
		Double additionalDiscounts = getAmount(AddDiscounts);
		return additionalDiscounts;
	}

	/**
	 * This method is used to get Handling Cost
	 *
	 * @return Handling Cost
	 */
	public Double getHandlingCost( )
	{
		wait.waitForElementDisplayed(HANDLING_COST);
		WebElement HandlingCost = element.getWebElement(HANDLING_COST);
		Double handlingCost = getAmount(HandlingCost);
		return handlingCost;
	}

	/**
	 * This method is used to get Shipping Subtotal
	 *
	 * @return Shipping Subtotal
	 */
	public Double getShippingSubtotal( )
	{
		wait.waitForElementDisplayed(SHIPPING_SUBTOTAL);
		WebElement ShippingSubtotal = element.getWebElement(SHIPPING_SUBTOTAL);
		Double shippingSubtotal = getAmount(ShippingSubtotal);
		return shippingSubtotal;
	}

	/**
	 * This method is used to get Shipping Discount
	 *
	 * @return Shipping Discount
	 */
	public Double getShippingDiscount( )
	{
		wait.waitForElementDisplayed(SHIPPING_DISCOUNT);
		WebElement ShippingDiscount = element.getWebElement(SHIPPING_DISCOUNT);
		Double shippingDiscount = getAmount(ShippingDiscount);
		return shippingDiscount;
	}

	/**
	 * This method is used to get Shipping Cost
	 *
	 * @return Shipping Cost
	 */
	public Double getShippingCost( )
	{
		wait.waitForElementDisplayed(SHIPPING_COST);
		WebElement ShippingCost = element.getWebElement(SHIPPING_COST);
		Double shippingCost = getAmount(ShippingCost);
		return shippingCost;
	}

	/**
	 * This method is used to get Tax
	 *
	 * @return Shipping Tax
	 */
	public Double getTaxCost( )
	{
		wait.waitForElementDisplayed(TAX_COST);
		WebElement TaxCost = element.getWebElement(TAX_COST);
		Double taxCost = getAmount(TaxCost);
		return taxCost;
	}

	/**
	 * This method is used to get Final Total
	 *
	 * @return Shipping Final Total
	 */
	public Double getFinalTotal( )
	{
		wait.waitForElementDisplayed(FINAL_TOTAL);
		WebElement FinalTotal = element.getWebElement(FINAL_TOTAL);
		Double finalTotal = getAmount(FinalTotal);
		return finalTotal;
	}

	/**
	 * Calculates percentage based tax amount or calculates expected tax amount which should be applicable to the order
	 *
	 * @param taxPercent 	 need to pass percentage of percentage based tax
	 * @param isShipIncluded pass true to calculate tax on Item Amount + FREIGHT & false to ignore FREIGHT amount
	 * @return calculated tax amount based on percentage
	 */
	public double calculatePercentageBasedTax(double taxPercent, boolean isShipIncluded) {
		VertexLogger.log("Tax to be calculated on " + taxPercent + "%");
		double calculatedTax = 0;
		double subTotal = 0;
		double shipping = 0;
		waitForPageLoad();
		wait.waitForElementPresent(ORDER_NUMBER);
		WebElement shippingItemTotal = wait.waitForElementPresent(SHIPPING_ITEM_TOTAL);
		WebElement shippingCost = wait.waitForElementPresent(SHIPPING_COST);
		subTotal = Double.parseDouble(text.getElementText(shippingItemTotal).replace("$", ""));
		VertexLogger.log("Shipping item total = " + subTotal);
		if (isShipIncluded) {
			shipping = Double.parseDouble(text.getElementText(shippingCost).replace("$", ""));
			VertexLogger.log("Shipping amount to be added in Tax Calculation formula & Shipping cost " + shipping);
		}
		calculatedTax = (subTotal + shipping) * (taxPercent / 100);
		double roundOff = Double.parseDouble(String.format("%.2f", calculatedTax));
		VertexLogger.log("Calculated or expected tax amount: " + roundOff);
		return roundOff;
	}

	/**
	 * Calculates percentage based tax amount or calculates expected tax amount which should be applicable to the order
	 *
	 * @param taxPercent need to pass percentage of percentage based tax
	 * @return calculated tax amount based on percentage
	 */
	public double calculatePercentageBasedTax(double taxPercent) {
		return calculatePercentageBasedTax(taxPercent, true);
	}

	/**
	 * Calculates percentage base tax individually on Line item & on Shipping.
	 * In some test-cases some tax not applied to FREIGHT.
	 * Suppose, for some order Province Tax = 7% & GST = 5% so in such case, for some test-case GST is not applicable on FREIGHT
	 * So this method will take individual tax percentage for Line item & Shipping & it will calculate individual tax & at last, it will add both tax & will return the same.
	 *
	 * @param lineItemTax Tax percentage that should be applicable on Line item
	 * @param shippingTax Tax percentage that should be applicable on Shipping
	 * @return Summation of individually calculated tax on Line item & on Shipping
	 */
	public double calculateIndividualPercentBaseTaxesAndDoAddition(double lineItemTax, double shippingTax) {
		VertexLogger.log("Line item Tax to be calculated on " + lineItemTax + "%");
		VertexLogger.log("Shipping Tax to be calculated on " + shippingTax + "%");
		double calculatedLineItemTax = 0;
		double calculatedShippingTax = 0;
		double subTotal = 0;
		double shipping = 0;
		waitForPageLoad();
		wait.waitForElementPresent(ORDER_NUMBER);
		WebElement shippingItemTotal = wait.waitForElementPresent(SHIPPING_ITEM_TOTAL);
		WebElement shippingCost = wait.waitForElementPresent(SHIPPING_COST);
		subTotal = Double.parseDouble(text.getElementText(shippingItemTotal).replace("$", ""));
		VertexLogger.log("Shipping item total = " + subTotal);
		shipping = Double.parseDouble(text.getElementText(shippingCost).replace("$", ""));
		VertexLogger.log("Shipping cost = " + shipping);
		calculatedLineItemTax = Double.parseDouble(String.format("%.2f", subTotal * (lineItemTax / 100)));
		calculatedShippingTax = Double.parseDouble(String.format("%.2f", shipping * (shippingTax / 100)));
		VertexLogger.log("Calculated or expected tax amount on Line item: " + calculatedLineItemTax);
		VertexLogger.log("Calculated or expected tax amount on Shipping: " + calculatedShippingTax);
		return calculatedLineItemTax + calculatedShippingTax;
	}

	/**
	 * Take actual tax app;ied on the order from the UI/WebElement.
	 *
	 * @return tax applied to order
	 */
	public double getTaxCostFromUI() {
		waitForPageLoad();
		wait.waitForElementPresent(ORDER_NUMBER);
		WebElement taxCost = wait.waitForElementPresent(TAX_COST);
		String actualTax = text.getElementText(taxCost).replace("$", "");
		VertexLogger.log("Tax amount from UI or Actual Tax: " + actualTax);
		return Double.parseDouble(actualTax);
	}

	/**
	 * Get Order Number from UI
	 *
	 * @return Order Number
	 */
	public String getOrderNo() {
		waitForPageLoad();
		WebElement order = wait.waitForElementPresent(ORDER_NUMBER);
		String orderID = text.getElementText(order);
		VertexLogger.log(orderID);
		return orderID.replace("Order ID: ", "").trim();
	}
}
