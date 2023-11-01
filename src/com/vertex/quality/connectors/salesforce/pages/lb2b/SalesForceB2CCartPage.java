package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class SalesForceB2CCartPage extends SalesForceBasePage {

    protected By CART_CHECKOUT_BUTTON = By.xpath(".//button[@title='Checkout']");

    protected By GUEST_EMAIL = By.xpath(".//input[@name='guest-email']");
    protected By GUEST_FIRST_NAME = By.xpath(".//input[@name='firstname']");
    protected By GUEST_LAST_NAME = By.xpath(".//input[@name='lastname']");
    protected By GUEST_PHONE = By.xpath(".//input[@name='phonenumber']");

    protected By MEMBER_NEW_ADDRESS_BUTTON = By.xpath(".//button[text() ='New Address']");
    protected By MEMBER_EDIT_DEFAULT_ADDRESS_BUTTON = By.xpath("(.//button[contains(@aria-label, 'Edit')])[1]");

    protected By SHIPPING_STREET_INPUT = By.xpath(".//textarea[@name='street']");
    protected By SHIPPING_CITY_INPUT = By.xpath(".//input[@name='city']");
    protected By SHIPPING_STATE_PICKLIST = By.xpath("(.//select[@name='province'])[1]");
    protected By SHIPPING_ZIP_INPUT = By.xpath(".//input[@name='postalCode']");
    protected By SHIPPING_COUNTRY_INPUT = By.xpath(".//input[@name='country']");
    protected By Province_Text_Input = By.xpath("(.//input[@name='province'])[1]");

    protected By NEXT_SHIPPING_BUTTON = By.xpath(".//button[text()='Next: Shipping']");
    protected By NEXT_PAYMENT_BUTTON = By.xpath(".//button[text()='Next: Payment']");

    protected By SUMMARY_TAX_VALUE = By.xpath(".//span[text() = 'Tax']/../following-sibling::*");

    protected By SAME_AS_SHIPPING_ADDRESS_BUTTON = By.xpath(".//span[text() = 'Same as shipping address']/preceding-sibling::span[contains(@class, 'radio')]");
    protected By DIFFERENT_BILLING_ADDRESS_BUTTON = By.xpath(".//span[text() = 'Use a different billing address']/preceding-sibling::span[contains(@class, 'radio')]");
    protected By PLACE_ORDER_BUTTON = By.xpath(".//button[text() = 'Place Order']");

    protected By CARD_NUMBER_INPUT = By.xpath(".//input[@id='card-number']");
    protected By CARD_NAME_INPUT = By.xpath(".//input[@id='cardholder-name']");
    protected By CARD_EXPIRATION_DATE_INPUT = By.xpath(".//input[@id='expiry-date']");
    protected By CARD_SECURITY_CODE_INPUT = By.xpath(".//input[@id='cvv']");

    protected String VERTEX_SETTINGS_TAB = "Vertex__VertexSettings | Salesforce";

    public SalesForceB2CCartPage(WebDriver driver) {super(driver);}

    /**
     * Click checkout button on cart page
     */
    public void clickCheckoutButton(){
        wait.waitForElementDisplayed(CART_CHECKOUT_BUTTON);
        click.clickElement(CART_CHECKOUT_BUTTON);
    }

    /**
     * Set guest checkout information
     * @param email
     * @param firstName
     * @param lastName
     * @param phoneNumber
     */
    public void enterGuestLoginInformation(String email, String firstName, String lastName, String phoneNumber) {
        setGuestEmail(email);
        setGuestFirstName(firstName);
        setGuestLastName(lastName);
        setGuestPhone(phoneNumber);
    }

    /**
     * Set member checkout information
     * @param firstName
     * @param lastName
     */
    public void enterMemberCheckoutInformation(String firstName, String lastName) {
        clickEditDefaultAddressButton();
        setGuestFirstName(firstName);
        setGuestLastName(lastName);
    }

    /**
     * Set guest email address
     * @param email
     */
    public void setGuestEmail(String email){
        wait.waitForElementDisplayed(GUEST_EMAIL);
        text.enterText(GUEST_EMAIL, email);
    }

    /**
     * Set guest first name
     * @param firstName
     */
    public void setGuestFirstName(String firstName){
        wait.waitForElementDisplayed(GUEST_FIRST_NAME);
        waitForSalesForceLoaded(10000);
        text.enterText(GUEST_FIRST_NAME, firstName, true);
    }

    /**
     * Set guest last name
     * @param lastName
     */
    public void setGuestLastName(String lastName){
        wait.waitForElementDisplayed(GUEST_LAST_NAME);
        text.enterText(GUEST_LAST_NAME, lastName, true);
    }

    /**
     * Set guest phone number
     * @param phoneNum
     */
    public void setGuestPhone(String phoneNum){
        wait.waitForElementDisplayed(GUEST_PHONE);
        text.enterText(GUEST_PHONE, phoneNum);
    }

    /**
     * Set shipping address information
     *
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param country
     */
    public void enterShippingAddress(String street, String city, String state, String zip, String country){
        setShippingStreet(street);
        setShippingCity(city);
        setShippingState(state);
        setShippingZip(zip);

    }

    /**
     * Set shipping address information
     *
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param country
     */
    public void enterMemberShippingAddress(String street, String city, String state, String zip, String country){
        setShippingStreet(street);
        setShippingCity(city);
        setShippingState(state);
        setShippingZip(zip);
    }

    /**
     * Set shipping address information
     *
     * @param street
     * @param city
     * @param state
     * @param zip
     */
    public void enterVatShippingAddress(String street, String city, String state, String zip) {
        this.setShippingStreet(street);
        this.setShippingCity(city);
        this.setVatShippingState(state);
        this.setShippingZip(zip);
    }

    /**
     * Set shipping address street
     *
     * @param street
     */
    public void setShippingStreet(String street){
        wait.waitForElementDisplayed(SHIPPING_STREET_INPUT);
        text.enterText(SHIPPING_STREET_INPUT, street, true);

    }

    /**
     * Set shipping address city
     *
     * @param city
     */
    public void setShippingCity(String city){
        wait.waitForElementDisplayed(SHIPPING_CITY_INPUT);
        text.enterText(SHIPPING_CITY_INPUT, city, true);

    }

    /**
     * Set shipping address state
     *
     * @param state
     */
    public void setShippingState(String state){
        wait.waitForElementDisplayed(SHIPPING_STATE_PICKLIST);
        dropdown.selectDropdownByValue(SHIPPING_STATE_PICKLIST, state);
    }

    /**
     * Set shipping address state/province
     *
     * @param state
     */
    public void setVatShippingState(String state) {
        wait.waitForElementDisplayed(Province_Text_Input);
        text.enterText(Province_Text_Input, state);
    }

    /**
     * Set shipping address zip code
     *
     * @param zip
     */
    public void setShippingZip(String zip){
        wait.waitForElementDisplayed(SHIPPING_ZIP_INPUT);
        text.enterText(SHIPPING_ZIP_INPUT, zip, true);
    }

    /**
     * Click Edit Default Address link for existing B2C member
     */
    public void clickEditDefaultAddressButton(){
        waitForSalesForceLoaded(3000);
        wait.waitForElementDisplayed(MEMBER_EDIT_DEFAULT_ADDRESS_BUTTON);
        click.clickElement(MEMBER_EDIT_DEFAULT_ADDRESS_BUTTON);
    }

    /**
     * Click New Address button for existing B2C member
     */
    public void clickNewAddressButton(){
        waitForSalesForceLoaded(3000);
        wait.waitForElementDisplayed(MEMBER_NEW_ADDRESS_BUTTON);
        click.clickElement(MEMBER_NEW_ADDRESS_BUTTON);
    }

    /**
     * Click Next: Shipping button
     */
    public void clickNextShippingButton(){
        wait.waitForElementDisplayed(NEXT_SHIPPING_BUTTON);
        click.clickElement(NEXT_SHIPPING_BUTTON);
    }

    /**
     * Click Next: Payment button
     */
    public void clickNextPaymentButton(){
        waitForSalesForceLoaded(2000);
        wait.waitForElementDisplayed(NEXT_PAYMENT_BUTTON);
        click.clickElement(NEXT_PAYMENT_BUTTON);
    }

    /**
     * Get tax from order summary
     */
    public String getTaxValue() {
		wait.waitForElementDisplayed(SUMMARY_TAX_VALUE);
		jsWaiter.sleep(20000);

        return text.getElementText(SUMMARY_TAX_VALUE);
    }

    /**
     * Select same billing address option
     * @param sameBillAndShipAddress use same billing and shipping address OR enter different billing address
     */
    public void selectSameBillingAndShippingAddressOption(boolean sameBillAndShipAddress)
    {
        if(!sameBillAndShipAddress){
            wait.waitForElementDisplayed(DIFFERENT_BILLING_ADDRESS_BUTTON);
			click.clickElement(DIFFERENT_BILLING_ADDRESS_BUTTON);
        }
    }

    /**
     * Enter payment information on payment step
     *
     * @param cardNum
     * @param cardName
     * @param expirationDate
     * @param securityCode
     */
    public void enterPaymentInformation(String cardNum, String cardName, String expirationDate, String securityCode){
        waitForSalesForceLoaded(2000);
        setCardNumber(cardNum);
        setCardName(cardName);
        setCardExpirationDate(expirationDate);
        setCardSecurityCode(securityCode);
    }

    /**
     * Set card number for payment
     *
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber){
		WebElement shadowParent = wait.waitForElementDisplayed(By.xpath("//div/h3[contains(text(),'Credit Card')]"));
		click.clickElementCarefully(shadowParent);

		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.TAB).perform();
		actions.sendKeys(cardNumber).perform();

    }

    /**
     * Set card name for payment
     *
     * @param cardName
     */
    public void setCardName(String cardName){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.TAB).perform();
		actions.sendKeys(cardName).perform();
    }

    /**
     * Set card expiration date for payment
     *
     * @param expirationDate
     */
    public void setCardExpirationDate(String expirationDate){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.TAB).perform();
		actions.sendKeys(expirationDate).perform();
    }

    /**
     * Set card security code for payment
     *
     * @param securityCode
     */
    public void setCardSecurityCode(String securityCode){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.TAB).perform();
		actions.sendKeys(securityCode).perform();
    }


    /**
     * Click Place Order button
     */
    public void clickPlaceOrderButton(){
    	jsWaiter.sleep(5000);
        wait.waitForElementEnabled(PLACE_ORDER_BUTTON);
        click.clickElement(PLACE_ORDER_BUTTON);
    }

    /**
     * Exit storefront tab and go back to Vertex Config page
     */
    public void exitB2CStorefront(){
        window.switchToWindowTextInTitle(VERTEX_SETTINGS_TAB);
    }
}
