package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyStoreOrderConfirmationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class StoreFrontTaxAndPaymentPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontTaxAndPaymentPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By sameBillShipRadioButton = By.id("billing_address_selector-shipping_address");
	protected By differentBillShipRadioButton = By.id("billing_address_selector-custom_billing_address");
	protected By subtotalLabel = By.xpath(".//div[normalize-space(.)='Subtotal']");
	protected By subtotalAmount = By.xpath(".//div[normalize-space(.)='Subtotal']/following-sibling::div/span");
	protected By shippingLabel = By.xpath("(.//div[normalize-space(.)='Shipping'])[last()]");
	protected By shippingAmount = By.xpath(".//div[normalize-space(.)='Shipping']/following-sibling::div/span");
	protected By taxLabel = By.xpath(".//div[normalize-space(.)='Estimated taxes']");
	protected By taxAmount = By.xpath(".//div[normalize-space(.)='Estimated taxes']/following-sibling::div//span");
	protected By totalLabel = By.xpath(".//div[normalize-space(.)='Total']");
	protected By totalAmount = By.xpath(".//div[normalize-space(.)='Total']/following-sibling::div//strong");
	protected By payNowButton = By.xpath(".//button[contains(@class,'janiy')]//span[text()='Pay now']");
	protected By completeOrderButton = By.xpath("(.//button[normalize-space(.)='Complete order'])[1]");
	protected By addNewBillingAddressDropdown = By.xpath(".//select[@id='Select3']");
	protected By shippingCountryDropdown = By.name("countryCode");
	protected By shippingAddressLine1Box = By.xpath("(.//input[@id='address1'])[1]");
	protected By shippingCityBox = By.xpath("(.//input[@name='city'])[1]");
	protected By shippingStateDropdown = By.xpath("(.//select[@name='zone'])[1]");
	protected By shippingPostalBox = By.xpath("(.//input[@name='postalCode'])[1]");

	public double calculatePercentTaxBeforeOrderPlace( double taxAmount )
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

	public double calculatePercentBasedTaxBeforeOrderPlaceUpRounding( double taxAmount )
	{
		DecimalFormat format = new DecimalFormat("0.00");
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
		format.setRoundingMode(RoundingMode.UP);
		return Double.parseDouble(format.format(expectedTax));
	}

	public double getTaxFrmUIBeforeOrderPlace( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		wait.waitForElementPresent(taxLabel);
		waitTillCalculatingTaxDisappears();
		return Double.parseDouble(text
			.getElementText(wait.waitForElementPresent(taxAmount))
			.replace("$", "")
			.replace(",", ""));
	}
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

	public boolean isTaxPresentOnUIBeforeOrderPlace( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(subtotalLabel);
		wait.waitForElementPresent(shippingLabel);
		waitTillCalculatingTaxDisappears();
		return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
	}

	public boolean isTaxPresentOnUIBeforeOrderPlaceDigitalProducts( )
	{
		waitForPageLoad();
		wait.waitForElementPresent(totalLabel);
		wait.waitForElementPresent(totalAmount);
		waitTillCalculatingTaxDisappears();
		return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
	}

	public void clickOrderPayNow( )
	{
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementEnabled(payNowButton));
		waitForPageLoad();
//		return new ShopifyStoreOrderConfirmationPage(driver);
	}
}
