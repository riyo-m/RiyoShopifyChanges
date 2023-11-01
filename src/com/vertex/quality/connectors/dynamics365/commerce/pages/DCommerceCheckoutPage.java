package com.vertex.quality.connectors.dynamics365.commerce.pages;
import com.vertex.quality.connectors.dynamics365.commerce.enums.CommerceDeliveryOptions;
import com.vertex.quality.connectors.dynamics365.commerce.pages.base.DCommerceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Contains methods for interacting with checkout page
 */
public class DCommerceCheckoutPage extends DCommerceBasePage {

    protected By CHANGE_ADDRESS_BUTTON = By.cssSelector("[aria-label='Change SHIPPING ADDRESS']");
    protected By ADD_ADDRESS_BUTTON = By.className("msc-address-select__button-add");
    protected By ADDRESS_NAME_INPUT = By.id("shipping_addressname");
    protected By SHIPPING_STREET_INPUT = By.id("shipping_addressstreet");
    protected By SHIPPING_CITY_INPUT = By.id("shipping_addresscity");
    protected By SHIPPING_STATE_DROPDOWN = By.id("shipping_addressstate");
    protected By SHIPPING_ZIPCODE_INPUT = By.id("shipping_addresszipcode");
    protected By SHIPPING_COUNTRY_DROPDOWN = By.id("shipping_addresscountry");

    protected By PAYMENT_IFRAME = By.xpath("//iframe[@aria-label='Payment iframe']");
    protected By GIFT_CARD_INPUT = By.xpath("//input[@aria-label='Gift card number']");
    protected By CARDHOLDER_INPUT = By.xpath("//input[@name='CardHolderNameTextBox']");
    protected By CARD_TYPE_DROPDOWN =By.xpath("//select[@name='CardTypeDropDownList']");
    protected By CARD_NUMBER_INPUT =By.xpath("//input[@name='CardNumberTextBox']");
    protected By EXPIRATION_MONTH_DROPDOWN =By.xpath("//select[@name='ExpirationMonthDropDownList']");
    protected By EXPIRATION_YEAR_DROPDOWN =By.xpath("//select[@name='ExpirationYearDropDownList']");
    protected By CARD_STREET_INPUT =By.xpath("//input[@name='StreetTextBox']");
    protected By CARD_CITY_INPUT =By.xpath("//input[@name='CityTextBox']");
    protected By CARD_STATE_INPUT =By.xpath("//input[@name='StateProvinceTextBox']");
    protected By CARD_ZIPCODE_INPUT =By.xpath("//input[@name='ZipTextBox1']");
    protected By CARD_COUNTRY_DROPDOWN =By.xpath("//select[@name='CountryRegionDropDownList']");

    protected By BILLING_NAME_INPUT = By.id("billing_addressname");
    protected By BILLING_STREET_INPUT = By.id("billing_addressstreet");
    protected By BILLING_CITY_INPUT = By.id("billing_addresscity");
    protected By BILLING_STATE_DROPDOWN = By.id("billing_addressstate");
    protected By BILLING_ZIPCODE_INPUT = By.id("billing_addresszipcode");
    protected By BILLING_COUNTRY_DROPDOWN = By.id("billing_addressthreeletterisoregionname");

    protected By GUEST_EMAIL_INPUT = By.xpath("//input[@aria-label='Email address for order questions']");

    protected By SAVE_BUTTON = By.cssSelector("[title='Save']");
    protected By SAVE_CONTINUE_BUTTON = By.xpath("//div[not(contains(@class, 'hidden'))]/div/button[@title='Save & continue']");
    protected By APPLY_BUTTON = By.xpath("//button[@aria-label='Apply']");
    protected By PLACE_ORDER_BUTTON = By.xpath("//div[@class='ms-checkout__main-control']//button[@title='Place order']");

    protected By SUBTOTAL_AMOUNT = By.xpath("//p[@class='msc-order-summary__line-sub-total']//span[@class='msc-price__actual']");
    protected By SHIPPING_AMOUNT = By.xpath("//p[@class='msc-order-summary__line-shipping']//span[@class='msc-price__actual']");
    protected By OTHER_CHARGES_AMOUNT = By.xpath("//p[@class='msc-order-summary__line-other-charges']//span[@class='msc-price__actual']");
    protected By TAX_AMOUNT = By.xpath("//p[@class='msc-order-summary__line-tax-amount']//span[@class='msc-price__actual']");
    protected By TOTAL_AMOUNT = By.xpath("//p[@class='msc-order-summary__line-total']//span[@class='msc-price__actual']");

    public DCommerceCheckoutPage(WebDriver driver )
    {
        super(driver);
    }

    /**
     * Enter a new shipping address
     * @param name
     * @param street
     * @param city
     * @param state (2-letter ISO format)
     * @param zipCode
     */
    public void enterGuestAddress(String name, String street, String city, String state, String zipCode) {
        enterAddressName(name);
        enterStreet(street);
        enterCity(city);
        selectState(state);
        enterZipCode(zipCode);
        clickSaveAndContinue();
    }

    /**
     * Enter name associated with shipping address
     * @param name
     */
    public void enterAddressName(String name) {
        WebElement inputField = wait.waitForElementPresent(ADDRESS_NAME_INPUT);
        text.selectAllAndInputText(inputField, name);
    }

    /**
     * Enter street into input for shipping address
     * @param street
     */
    public void enterStreet(String street) {
        WebElement inputField = wait.waitForElementPresent(SHIPPING_STREET_INPUT);
        text.selectAllAndInputText(inputField, street);
    }

    /**
     * Enter city into input for shipping address
     * @param city
     */
    public void enterCity(String city) {
        WebElement inputField = wait.waitForElementPresent(SHIPPING_CITY_INPUT);
        text.selectAllAndInputText(inputField, city);
    }

    /**
     * Select state in dropdown for shipping address
     * @param state (2-letter format)
     */
    public void selectState(String state) {
        wait.waitForElementPresent(SHIPPING_STATE_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(SHIPPING_STATE_DROPDOWN);

        By stateOptionLoc = By.xpath(String.format("//option[@value='%s']", state));
        wait.waitForElementPresent(stateOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(stateOptionLoc);
    }

    /**
     * Enter zip code into input for shipping address
     * @param zipCode
     */
    public void enterZipCode(String zipCode) {
        WebElement inputField = wait.waitForElementPresent(SHIPPING_ZIPCODE_INPUT);
        text.selectAllAndInputText(inputField, zipCode);
    }

    /**
     * Select country in dropdown
     * @param country (3-letter format)
     */
    public void selectCountry(String country) {
        wait.waitForElementPresent(SHIPPING_COUNTRY_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(SHIPPING_COUNTRY_DROPDOWN);

        By countryOptionLoc = By.xpath(String.format("//option[@value='%s']", country));
        wait.waitForElementPresent(countryOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(countryOptionLoc);
    }

    /**
     * Select shipping address
     * @param addressName
     */
    public void selectShippingAddress(String addressName) {
        wait.waitForElementDisplayed(CHANGE_ADDRESS_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(CHANGE_ADDRESS_BUTTON);

        By optionLoc = By.xpath(String.format("//div[@class='msc-address-detail']/span[text()='%s']/../../input", addressName));

        WebElement addressOptionButton = wait.waitForElementEnabled(optionLoc);
        actions.moveToElement(addressOptionButton).click().perform();
        clickSave();
    }
    /**
     * Select the delivery option (Standard, Standard overnight, 2 Day Guaranteed Delivery)
     * @param option
     */
    public void selectDeliveryOption(CommerceDeliveryOptions option) {
        By optionLoc = By.xpath(String.format("//input[@aria-label = '%s Delivery options']", option.value));

        WebElement deliveryOptionButton = wait.waitForElementEnabled(optionLoc);
        actions.moveToElement(deliveryOptionButton).click().perform();

        clickSaveAndContinue();
    }

    /**
     * Enter gift card number
     * @param giftCardNo
     */
    public void enterGiftCardNumber(String giftCardNo) {
        WebElement giftCardField = wait.waitForElementDisplayed(GIFT_CARD_INPUT);
        text.selectAllAndInputText(giftCardField, giftCardNo);
    }

    /**
     * Click Apply button
     */
    public void clickApply() {
        WebElement applyButton = wait.waitForElementPresent(APPLY_BUTTON);
        actions.moveToElement(applyButton).click().perform();
    }

    /**
     * Enter name of card holder
     * @param name
     */
    public void enterCardholderName(String name) {
        WebElement cardholderInputField = wait.waitForElementPresent(CARDHOLDER_INPUT);
        text.selectAllAndInputText(cardholderInputField, name);
    }

    /**
     * Select given card type
     * @param cardType
     */
    public void selectCardType(String cardType) {
        wait.waitForElementPresent(CARD_TYPE_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(CARD_TYPE_DROPDOWN);

        By cardTypeOptionLoc = By.xpath(String.format("//option[@value='%s']", cardType));
        wait.waitForElementDisplayed(cardTypeOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(cardTypeOptionLoc);
    }

    /**
     * Enter given card number
     * @param cardNo
     */
    public void enterCardNumber(String cardNo) {
        WebElement cardNumberInputField = wait.waitForElementPresent(CARD_NUMBER_INPUT);
        text.selectAllAndInputText(cardNumberInputField, cardNo);
    }

    /**
     * Open dropdown for expiration month and year and set card's expiration date
     * @param expirationMonth
     * @param expirationYear
     */
    public void setExpirationDate(String expirationMonth, String expirationYear) {
        wait.waitForElementPresent(EXPIRATION_MONTH_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(EXPIRATION_MONTH_DROPDOWN);

        By monthOptionLoc = By.xpath(String.format("//option[@value='%s']", expirationMonth));
        wait.waitForElementDisplayed(monthOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(monthOptionLoc);

        wait.waitForElementPresent(EXPIRATION_YEAR_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(EXPIRATION_YEAR_DROPDOWN);

        By yearOptionLoc = By.xpath(String.format("//option[@value='%s']", expirationYear));
        wait.waitForElementDisplayed(yearOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(yearOptionLoc);
    }

    /**
     * Enter given card details
     * @param cardHolderName
     * @param cardType
     * @param cardNo
     * @param expireMonth
     * @param expireYear
     */
    public void enterCardDetails(String cardHolderName, String cardType, String cardNo, String expireMonth, String expireYear) {
        waitForPageLoad();
        jsWaiter.sleep(2500);
        WebElement paymentFrame = wait.waitForElementDisplayed(PAYMENT_IFRAME);
        driver.switchTo().frame(paymentFrame);

        enterCardholderName(cardHolderName);
        selectCardType(cardType);
        enterCardNumber(cardNo);
        setExpirationDate(expireMonth, expireYear);

        driver.switchTo().defaultContent();
    }

    /**
     * Select given country in billing address under card details
     * @param country (2-letter ISO format)
     */
    public void selectCardCountry(String country) {
        wait.waitForElementPresent(CARD_COUNTRY_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(CARD_COUNTRY_DROPDOWN);

        By countryOptionLoc = By.xpath(String.format("//option[@value='%s']", country));
        wait.waitForElementDisplayed(countryOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(countryOptionLoc);
    }

    /**
     * Enter street in billing address under card details
     * @param street
     */
    public void enterCardStreet(String street) {
        WebElement streetInputField = wait.waitForElementPresent(CARD_STREET_INPUT);
        text.selectAllAndInputText(streetInputField, street);
    }

    /**
     * Enter city in billing address under card details
     * @param city
     */
    public void enterCardCity(String city) {
        WebElement cityInputField = wait.waitForElementPresent(CARD_CITY_INPUT);
        text.selectAllAndInputText(cityInputField, city);
    }

    /**
     * Enter state in billing address under card details
     * @param state
     */
    public void enterCardState(String state) {
        WebElement stateInputField = wait.waitForElementPresent(CARD_STATE_INPUT);
        text.selectAllAndInputText(stateInputField, state);
    }

    /**
     * Enter zipcode in billing address under card details
     * @param zipCode
     */
    public void enterCardZipCode(String zipCode) {
        WebElement zipCodeInputField = wait.waitForElementPresent(CARD_ZIPCODE_INPUT);
        text.selectAllAndInputText(zipCodeInputField, zipCode);
    }

    /**
     * Enter billing address details for card
     * @param street
     * @param city
     * @param state
     * @param zipCode
     * @param country (2-letter ISO format)
     */
    public void enterBillingAddressForCard(String street, String city, String state, String zipCode, String country) {
        waitForPageLoad();
        WebElement paymentFrame = wait.waitForElementDisplayed(PAYMENT_IFRAME);
        driver.switchTo().frame(paymentFrame);

        selectCardCountry(country);
        enterCardStreet(street);
        enterCardCity(city);
        enterCardState(state);
        enterCardZipCode(zipCode);

        driver.switchTo().defaultContent();
    }

    /**
     * Enter dummy card details and its billing address
     */
    public void enterDummyCardDetails() {
        waitForPageLoad();
        enterCardDetails("TestAutomation", "Visa", "4111 1111 1111 1111", "3", "2030");
        enterBillingAddressForCard("Madison 11", "Houston", "Texas", "10000", "US");
    }

    /**
     * Select given country in billing address
     * @param country (3-letter ISO format)
     */
    public void selectBillingCountry(String country) {
        wait.waitForElementPresent(BILLING_COUNTRY_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(BILLING_COUNTRY_DROPDOWN);

        By countryOptionLoc = By.xpath(String.format("//option[@value='%s']", country));
        wait.waitForElementPresent(countryOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(countryOptionLoc);

        text.pressTab(By.cssSelector("body"));
    }

    /**
     * Enter name for billing address
     * @param name
     */
    public void enterBillingName(String name) {
        WebElement nameInputField = wait.waitForElementPresent(BILLING_NAME_INPUT);
        text.selectAllAndInputText(nameInputField, name);
    }

    /**
     * Enter street in billing address
     * @param street
     */
    public void enterBillingStreet(String street) {
        WebElement streetInputField = wait.waitForElementPresent(BILLING_STREET_INPUT);
        text.selectAllAndInputText(streetInputField, street);
    }

    /**
     * Enter city in billing address
     * @param city
     */
    public void enterBillingCity(String city) {
        WebElement cityInputField = wait.waitForElementPresent(BILLING_CITY_INPUT);
        text.selectAllAndInputText(cityInputField, city);
    }

    /**
     * Enter state in billing address
     * @param state (2-letter ISO format)
     */
    public void selectBillingState(String state) {
        wait.waitForElementPresent(BILLING_STATE_DROPDOWN);
        click.clickElementIgnoreExceptionAndRetry(BILLING_STATE_DROPDOWN);

        By stateOptionLoc = By.xpath(String.format("//option[@value='%s']", state));
        wait.waitForElementPresent(stateOptionLoc);
        click.clickElementIgnoreExceptionAndRetry(stateOptionLoc);
    }

    /**
     * Enter zipcode in billing address
     * @param zipCode
     */
    public void enterBillingZipCode(String zipCode) {
        WebElement zipCodeInputField = wait.waitForElementPresent(BILLING_ZIPCODE_INPUT);
        text.selectAllAndInputText(zipCodeInputField, zipCode);
    }


    /**
     * Enter billing address details under Payment Method
     * @param name
     * @param street
     * @param city
     * @param state (2-letter ISO format)
     * @param zipCode
     * @param country (3-letter ISO format)
     */
    public void enterBillingAddress(String name, String street, String city, String state, String zipCode, String country) {
        enterBillingName(name);
        enterBillingStreet(street);
        enterBillingCity(city);
        selectBillingState(state);
        enterBillingZipCode(zipCode);
        selectBillingCountry(country);

        waitForPageLoad();
    }

    /**
     * Enter email address for guest checkout
     */
    public void enterGuestEmailAddress() {
        WebElement guestEmailInputField = wait.waitForElementPresent(GUEST_EMAIL_INPUT);
        text.selectAllAndInputText(guestEmailInputField, "svcvtest@vertexinc.com");

        clickSaveAndContinue();
    }


    /**
     * Click 'Save' button
     */
    public void clickSave() {
        waitForPageLoad();
        WebElement saveButton = wait.waitForElementPresent(SAVE_BUTTON);
        actions.moveToElement(saveButton).click().perform();
    }

    /**
     * Click 'Save & Continue' button
     */
    public void clickSaveAndContinue() {
        if (!element.isElementDisplayed(SAVE_CONTINUE_BUTTON)) {
            scroll.scrollBottom();
        }
        wait.waitForElementDisplayed(SAVE_CONTINUE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SAVE_CONTINUE_BUTTON);
    }

    /**
     * Click 'Place order' button
     */
    public void clickPlaceOrder() {
        waitForPageLoad();
        WebElement placeOrderButton = wait.waitForElementEnabled(PLACE_ORDER_BUTTON, 10);
        actions.moveToElement(placeOrderButton).doubleClick().perform();
        waitForPageTitleContains("Order confirmation");
    }

    /**
     * Get subtotal amount of order
     * @return amount
     */
    public String getSubtotalAmount() {
        WebElement subtotalField = wait.waitForElementDisplayed(SUBTOTAL_AMOUNT);
        String amount = parseNumericAmount(subtotalField.getText());
        return amount;

    }

    /**
     * Get shipping cost of order
     * @return cost
     */
    public String getShippingCost() {
        WebElement shippingCostField = wait.waitForElementDisplayed(SHIPPING_AMOUNT);
        String cost = parseNumericAmount(shippingCostField.getText());
        return cost;
    }

    /**
     * Get other charges of order
     * @return cost
     */
    public String getOtherCharges() {
        WebElement otherChargesField = wait.waitForElementDisplayed(OTHER_CHARGES_AMOUNT);
        String amount = parseNumericAmount(otherChargesField.getText());
        return amount;
    }

    /**
     * Get tax amount of order
     * @return amount
     */
    public String getTaxAmount() {
        WebElement taxField = wait.waitForElementDisplayed(TAX_AMOUNT);
        String amount = parseNumericAmount(taxField.getText());
        return  amount;
    }

    /**
     * Get total amount of order
     * @return total
     */
    public String getTotalAmount() {
        WebElement totalAmountField = wait.waitForElementDisplayed(TOTAL_AMOUNT);
        String total = parseNumericAmount(totalAmountField.getText());
        return  total;
    }
}
