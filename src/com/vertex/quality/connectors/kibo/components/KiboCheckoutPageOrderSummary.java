package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This class represent the order summary section of the checkout page for the maxine live front
 * store
 * contains the methods to interact with the elements within to run automated test cases
 *
 * @author osabha
 */
public class KiboCheckoutPageOrderSummary extends VertexComponent
{
	protected By taxSummaryContainerLoc = By.className("mz-ordersummary-tax");
	protected By summaryLoc = By.className("mz-ordersummary-totalvalue");
	protected By shippingSummaryContainerLoc = By.className("mz-ordersummary-shippingtotal");
	protected By orderSummaryLabel = By.xpath(".//h3[contains(normalize-space(),'Order Summary')]");
	protected By subtotalAmount = By.xpath(".//span[text()='Subtotal:']/following-sibling::span");
	protected By shippingHandlingAmount = By.xpath(".//span[text()='Shipping & Handling:']/following-sibling::span");

	public KiboCheckoutPageOrderSummary( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * this method is used to get the calculated tax amount after order is placed in the order
	 * summary section of the checkout page for the maxine live front store
	 *
	 * @return String actual tax amount
	 */
	public String getTaxAmount( )
	{
		WebElement taxSummaryContainer = wait.waitForElementPresent(taxSummaryContainerLoc);

		WebElement taxSummary = wait.waitForElementPresent(summaryLoc, taxSummaryContainer);

		String calculatedTax = taxSummary.getText();

		return calculatedTax;
	}

	/**
	 * this method is used to verify the calculated shipping cost after order is placed in the order
	 * summary section of the checkout page for the maxine live front store
	 *
	 * @param expectedShipping cost in a string
	 *
	 * @return boolean , true when expected matches calculated and false if different
	 */
	public boolean verifyShippingAmount( String expectedShipping )
	{
		boolean correctShipping = false;
		WebElement shippingSummaryContainer = wait.waitForElementPresent(shippingSummaryContainerLoc);
		WebElement shippingSummary = wait.waitForElementPresent(summaryLoc, shippingSummaryContainer);

		String calculatedShipping = shippingSummary.getText();
		if ( expectedShipping.equals(calculatedShipping) )
		{
			correctShipping = true;
		}

		return correctShipping;
	}

	/**
	 * Gets tax amount from the UI.
	 *
	 * @return tax amount from UI.
	 */
	public double getTaxFromUI() {
		waitForPageLoad();
		wait.waitForElementPresent(orderSummaryLabel);
		return Double.parseDouble(getTaxAmount().replace("$", "").replace(",", ""));
	}

	/**
	 * Calculates percentage based tax
	 *
	 * @param taxPercent percentage of tax on which total tax should be calculated
	 * @return calculated tax amount
	 */
	public double calculatePercentBasedTax(double taxPercent) {
		double subtotal = 0;
		double shippingAndHandling = 0;
		double tax = 0;
		waitForPageLoad();
		wait.waitForElementPresent(orderSummaryLabel);
		subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("$", "").replace(",", ""));
		shippingAndHandling = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingHandlingAmount)).replace("$", "").replace(",", ""));
		tax = (subtotal + shippingAndHandling) * (taxPercent / 100);
		return Double.parseDouble(String.format("%.2f", tax));
	}
}
