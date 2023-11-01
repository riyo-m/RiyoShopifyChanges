package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify store order confirmation page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreOrderConfirmationPage extends ShopifyPage
{

	protected By orderConfirmation = By.xpath(".//h2[contains(text(),'Thank you')]");
	protected By orderNoField = By.xpath(".//span[@class='os-order-number']");
	protected By subtotalLabel = By.xpath(".//div[normalize-space(.)='Subtotal']");
	protected By subtotalAmount = By.xpath(".//div[normalize-space(.)='Subtotal']/following-sibling::div/span");
	protected By shippingLabel = By.xpath("(.//div[normalize-space(.)='Shipping'])[last()]");
	protected By shippingAmount = By.xpath(".//div[normalize-space(.)='Shipping']/following-sibling::div/span");
	protected By taxLabel = By.xpath(".//div[normalize-space(.)='Estimated taxes']");
	protected By calculatingTax = By.xpath(
		".//div[normalize-space(.)='Estimated taxes']/following-sibling::div//span[contains(text(),'Calculating')]");
	protected By taxAmount = By.xpath(".//div[normalize-space(.)='Estimated taxes']/following-sibling::div//span");
	protected By totalLabel = By.xpath(".//div[normalize-space(.)='Total']");
	protected By totalAmount = By.xpath(".//div[normalize-space(.)='Total']/following-sibling::div//strong");

	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public ShopifyStoreOrderConfirmationPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Reads order number from UI.
	 *
	 * @return Order No.
	 */
	public String getOrderNoFromUI( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderConfirmation);
		refreshPage();
		waitForPageLoad();
		WebElement order = wait.waitForElementPresent(orderNoField);
		return text
			.getElementText(order)
			.replace("Order ", "");
	}

	/**
	 * Calculates percentage based or expected tax
	 *
	 * @param taxAmount percent of tax
	 *
	 * @return calculated percent based tax
	 */
	public double calculatePercentBasedTaxAfterOrderPlace( double taxAmount )
	{
		double subtotal = 0;
		String ship;
		double shipping = 0;
		double expectedTax = 0;
		waitForPageLoad();
		wait.waitForElementPresent(orderConfirmation);
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
	 * Calculates percentage based or expected tax & rounding the tax in UP Rounding mode
	 *
	 * @param taxAmount percent of tax
	 *
	 * @return calculated percent based tax
	 */
	public double calculatePercentBasedTaxAfterOrderPlaceUpRounding( double taxAmount )
	{
		double subtotal = 0;
		String ship;
		double shipping = 0;
		double expectedTax = 0;
		waitForPageLoad();
		wait.waitForElementPresent(orderConfirmation);
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
	 * Gets tax amount from the UI
	 *
	 * @return tax amount from UI.
	 */
	public double getTaxFromUIAfterOrderPlace( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderConfirmation);
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		wait.waitForElementPresent(taxLabel);
		waitTillCalculatingTaxDisappears();
		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(taxAmount))
			.replace("$", "")
			.replace(",", ""));
	}

	/**
	 * Validates the tax amount & tax label is present or not on UI after placing an order
	 *
	 * @return true or false based on condition match
	 */
	public boolean isTaxPresentOnUIAfterOrderPlace( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(orderConfirmation);
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		waitTillCalculatingTaxDisappears();
		return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
	}
}
