package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.testng.Assert.fail;

/**
 * Shopify store payment page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyStorePaymentPage extends ShopifyPage {

    protected By creditCardPaymentRadioButton = By.id("basic-creditCards");
    protected By codPaymentRadioButton = By.id("basic-paymentOnDelivery");
    protected By cardNumberIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[1]");
    protected By nameOnCardIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[2]");
    protected By cardExpiryIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[3]");
    protected By cardSecureIframe = By.xpath("(.//iframe[@class='card-fields-iframe'])[4]");
    protected By cardNumberBox = By.xpath(".//input[@placeholder='Card number']");
    protected By nameOnCardBox = By.xpath(".//input[@placeholder='Name on card']");
    protected By cardExpiryBox = By.xpath(".//input[@placeholder='Expiration date (MM / YY)']");
    protected By cardSecureBox = By.xpath(".//input[@placeholder='Security code']");
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

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStorePaymentPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Calculates percentage based or expected tax for Colorado addresses as it has additional retail delivery fee
     * Separating this method tax calculation from regular tax calculation method to avoid confusion in future
     * Retail Delivery Fee is only applicable to Colorado and not for any other addresses
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax, Add fixed Delivery Fee & return combined amount
     */
    public double calculatePercentBasedTaxForColorado(double taxAmount, double coAdditionalTax) {
        double coRetailDeliveryFee = 0.27;
        double coTax = Double.parseDouble(String.format("%.2f", MagentoData.COLORADO_RETAIL_DELIVERY_FEE.value * (coAdditionalTax / 100)));
        return Double.parseDouble(String.format("%.2f", calculatePercentBasedTaxBeforeOrderPlace(taxAmount) + coRetailDeliveryFee + coTax));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTaxBeforeOrderPlace(double taxAmount) {
        double subtotal = 0;
        String ship;
        double shipping = 0;
        double expectedTax = 0;
        waitForPageLoad();
        wait.waitForElementPresent(subtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("$", "").replace(",", ""));
        ship = text.getElementText(wait.waitForElementPresent(shippingAmount));
        if (ship.equalsIgnoreCase("Free")) {
            shipping = 0;
        } else {
            shipping = Double.parseDouble(ship.replace("$", "").replace(",", ""));
        }
        expectedTax = (subtotal + shipping) * (taxAmount / 100);
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Calculates percentage based or expected tax & rounding the tax in UP Rounding mode
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTaxBeforeOrderPlaceUpRounding(double taxAmount) {
        DecimalFormat format = new DecimalFormat("0.00");
        double subtotal = 0;
        String ship;
        double shipping = 0;
        double expectedTax = 0;
        waitForPageLoad();
        wait.waitForElementPresent(subtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("$", "").replace(",", ""));
        ship = text.getElementText(wait.waitForElementPresent(shippingAmount));
        if (ship.equalsIgnoreCase("Free")) {
            shipping = 0;
        } else {
            shipping = Double.parseDouble(ship.replace("$", "").replace(",", ""));
        }
        expectedTax = (subtotal + shipping) * (taxAmount / 100);
        format.setRoundingMode(RoundingMode.UP);
        return Double.parseDouble(format.format(expectedTax));
    }

    /**
     * Gets tax amount from the UI
     *
     * @return tax amount from UI.
     */
    public double getTaxFromUIBeforeOrderPlace() {
        waitForPageLoad();
        wait.waitForElementPresent(subtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        wait.waitForElementPresent(taxLabel);
        waitTillCalculatingTaxDisappears();
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)));
//		.replace("$", "").replace(",", "")
    }

    /**
     * It will verify whether tax label & amount is present on UI or not - Used in customer exemption tests
     *
     * @return true or false based on condition match
     */
    public boolean isTaxPresentOnUIBeforeOrderPlace() {
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
    public boolean isTaxPresentOnUIBeforeOrderPlaceDigitalProducts() {
        waitForPageLoad();
        wait.waitForElementPresent(totalLabel);
        wait.waitForElementPresent(totalAmount);
        waitTillCalculatingTaxDisappears();
        return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
    }

    /**
     * Pay for the order with Credit Card payment method
     */
    public void enterCreditCardDetails() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(creditCardPaymentRadioButton));
        waitForPageLoad();
        window.switchToFrame(cardNumberIframe);
        text.enterTextByIndividualCharacters(wait.waitForElementEnabled(cardNumberBox), ShopifyDataUI.CreditCard.CARD_NUMBER.text);
        window.switchToDefaultContent();
        window.switchToFrame(nameOnCardIframe);
        text.enterText(wait.waitForElementEnabled(nameOnCardBox), ShopifyDataUI.CreditCard.NAME_ON_CARD.text);
        window.switchToDefaultContent();
        window.switchToFrame(cardExpiryIframe);
        text.enterTextByIndividualCharacters(wait.waitForElementEnabled(cardExpiryBox), ShopifyDataUI.CreditCard.EXPIRY.text);
        window.switchToDefaultContent();
        window.switchToFrame(cardSecureIframe);
        text.enterText(wait.waitForElementEnabled(cardSecureBox), ShopifyDataUI.CreditCard.SECRET_CODE.text);
        window.switchToDefaultContent();
    }

    /**
     * Pay for the order with COD payment method
     */
    public void clickCODPayment() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(codPaymentRadioButton));
        waitForPageLoad();
    }

    /**
     * Sets same billing & shipping addresses
     */
    public void setSameBillShipAddress() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(sameBillShipRadioButton));
        waitForPageLoad();
    }

    /**
     * Sets different billing & shipping addresses
     */
    public void setDifferentBillShipAddress() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(differentBillShipRadioButton));
        waitForPageLoad();
    }

    /**
     * Clicks on the Pay now button
     *
     * @return Order Confirmation Page
     */
    public ShopifyStoreOrderConfirmationPage clickPayNow() {
        waitForPageLoad();
		scroll.scrollElementIntoView(payNowButton);
        click.moveToElementAndClick(wait.waitForElementEnabled(payNowButton));
        waitForPageLoad();
        return new ShopifyStoreOrderConfirmationPage(driver);
    }

    /**
     * Clicks on the Complete order button
     *
     * @return Order Confirmation Page
     */
    public ShopifyStoreOrderConfirmationPage clickCompleteOrder() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(completeOrderButton));
        waitForPageLoad();
        return new ShopifyStoreOrderConfirmationPage(driver);
    }

    /**
     * Sets customer's destination address/ Ship To Address
     * Must follow the sequence of parameters: Country, Street1, City, State, Postal
     * example: setShippingAddress("United States", "123 street", "Los Angeles", "California", "90030");
     *
     * @param address address of customer's destination
     */
    public void setDifferentBillingAddress(String... address) {
        if (address.length != 5) {
            VertexLogger.log("Missed mandatory parameters or Parameters are not correct, do check JavaDoc", VertexLogLevel.ERROR);
            fail();
        }
        waitForPageLoad();
        dropdown.selectDropdownByDisplayName(wait.waitForElementEnabled(shippingCountryDropdown), address[0]);
        WebElement line1 = wait.waitForElementEnabled(shippingAddressLine1Box);
        click.performDoubleClick(line1);
        text.enterText(line1, address[1]);
        text.enterText(wait.waitForElementEnabled(shippingCityBox), address[2]);
        dropdown.selectDropdownByDisplayName(wait.waitForElementEnabled(shippingStateDropdown), address[3]);
        text.enterText(wait.waitForElementEnabled(shippingPostalBox), address[4]);
    }
}
