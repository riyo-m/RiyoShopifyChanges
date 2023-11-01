package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentStoreFront extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public PaymentStoreFront( final WebDriver driver )
	{
		super(driver);
	}

	protected By creditCardPaymentRadioButton = By.id("basic-creditCards");
	protected By codPaymentRadioButton = By.id("basic-paymentOnDelivery");
	protected By cardNumberIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[1]");
	protected By cardExpiryIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[2]");
	protected By cardSecureIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[3]");
	protected By nameOnCardIframe = By.xpath("(//iframe[@class='card-fields-iframe'])[6]");
	protected By cardNumberBox = By.xpath(".//input[@placeholder='Card number']");
	protected By nameOnCardBox = By.xpath(".//input[@placeholder='Name on card']");
	protected By cardExpiryBox = By.xpath(".//input[@placeholder='Expiration date (MM / YY)']");
	protected By cardSecureBox = By.xpath(".//input[@placeholder='Security code']");

	public void enterCreditCardDetails( )
	{
//		waitForPageLoad();
//		scroll.scrollElementIntoView(creditCardPaymentRadioButton);
//		click.moveToElementAndClick(wait.waitForElementEnabled(creditCardPaymentRadioButton));

		waitForPageLoad();
		scroll.scrollElementIntoView(cardNumberIframe);
		window.switchToFrame(cardNumberIframe);
		text.enterTextByIndividualCharacters(wait.waitForElementEnabled(cardNumberBox),
			ShopifyDataUI.CreditCard.CARD_NUMBER.text);

		window.switchToDefaultContent();
		window.switchToFrame(cardExpiryIframe);
		text.enterTextByIndividualCharacters(wait.waitForElementEnabled(cardExpiryBox),
			ShopifyDataUI.CreditCard.EXPIRY.text);

		window.switchToDefaultContent();
		window.switchToFrame(cardSecureIframe);
		text.enterText(wait.waitForElementEnabled(cardSecureBox), ShopifyDataUI.CreditCard.SECRET_CODE.text);
		window.switchToDefaultContent();


//		window.switchToFrame(nameOnCardIframe);
//		text.enterText(wait.waitForElementEnabled(nameOnCardBox), ShopifyDataUI.CreditCard.NAME_ON_CARD.text);
	}
}
