package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.fail;

/**
 * Shopify Store Shipping Info Page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreShippingInfoPage extends ShopifyPage {

    protected By shippingContactBox = By.xpath("(.//input[@id='email'])[1]");
    protected By shippingCountryDropdown = By.name("countryCode");
    protected By shippingFirstNameBox = By.xpath("(.//input[@name='firstName'])[1]");
    protected By shippingLastNameBox = By.xpath("(.//input[@name='lastName'])[1]");
    protected By shippingAddressLine1Box = By.xpath("(//input[@name='address1'])[1]");
    protected By shippingCityBox = By.xpath("(.//input[@name='city'])[1]");
    protected By shippingStateDropdown = By.xpath("(.//select[@name='zone'])[1]");
    protected By shippingPostalBox = By.xpath("(.//input[@name='postalCode'])[1]");
    protected By saveShippingInfoCheckbox = By.xpath("(.//input[@id='save_shipping_information'])[1]");
    protected By continueToShippingButton = By.name("shipping_methods");
    protected String shippingMethodRadio = ".//div[normalize-space(.)='<<text_replace>>']/parent::div/preceding-sibling::div";
    protected By continueToPaymentButton = By.xpath("(.//button[normalize-space(.)='Continue to payment'])[1]");
    protected By subtotalLabel = By.xpath(".//div[normalize-space(.)='Subtotal']");
    protected By subtotalAmount = By.xpath(".//div[normalize-space(.)='Subtotal']/following-sibling::div");
    protected By shippingLabel = By.xpath("(.//div[normalize-space(.)='Shipping'])[2]");
    protected By shippingAmount = By.xpath("(.//div[normalize-space(.)='Shipping']/following-sibling::div)[2]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStoreShippingInfoPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Sets customer's destination address/ Ship To Address
     * Must follow the sequence of parameters: Country, Street1, City, State, Postal
     * example: setShippingAddress(true, "United States", "123 street", "Los Angeles", "California", "90030");
     *
     * @param isLoggedInUser true for Logged-In user, false for not Logged-In user.
     * @param address        address of customer's destination
     */
    public void setShippingAddress(boolean isLoggedInUser, String... address) {
        if (address.length != 5) {
            VertexLogger.log("Missed mandatory parameters or Parameters are not correct, do check JavaDoc", VertexLogLevel.ERROR);
            fail();
        }
        waitForPageLoad();
        if (!isLoggedInUser) {
            text.enterText(wait.waitForElementPresent(shippingContactBox),
                    ShopifyDataUI.StoreData.DEFAULT_CUSTOMER_EMAIL.text);
        }
        text.enterText(wait.waitForElementPresent(shippingFirstNameBox),
                ShopifyDataUI.StoreData.DEFAULT_CUSTOMER_FIRST_NAME.text);
        text.enterText(wait.waitForElementPresent(shippingLastNameBox),
                ShopifyDataUI.StoreData.DEFAULT_CUSTOMER_LAST_NAME.text);
        dropdown.selectDropdownByDisplayName(wait.waitForElementEnabled(shippingCountryDropdown), address[0]);
        WebElement line1 = wait.waitForElementEnabled(shippingAddressLine1Box);
        click.performDoubleClick(line1);
        text.selectAllAndInputText(line1, address[1]);
        text.selectAllAndInputText(wait.waitForElementEnabled(shippingCityBox), address[2]);
        dropdown.selectDropdownByDisplayName(wait.waitForElementEnabled(shippingStateDropdown), address[3]);
        text.selectAllAndInputText(wait.waitForElementEnabled(shippingPostalBox), address[4]);
        if (element.isElementDisplayed(saveShippingInfoCheckbox)) {
            checkbox.setCheckbox(saveShippingInfoCheckbox, false);

        }
        if (element.isElementDisplayed(shippingContactBox)) {
            text.enterText(shippingContactBox, ShopifyDataUI.StoreData.DEFAULT_CUSTOMER_EMAIL.text);
        }
    }

    /**
     * Sets customer's destination address/ Ship To Address
     * Must follow the sequence of parameters: Country, Street1, City, State, Postal
     * example: setShippingAddress(true, "United States", "123 street", "Los Angeles", "California", "90030");
     *
     * @param address address of customer's destination
     */
    public void setShippingAddress(String... address) {
        setShippingAddress(false, address);
    }

    /**
     * Clicks on Continue to shipping button
     */
    public void clickOnContinueShipping() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(continueToShippingButton));
        waitForPageLoad();
    }

    /**
     * Selects the shipping method
     *
     * @param shipMethod name of shipping method
     */
    public void selectShippingMethod(String shipMethod) {
        waitForPageLoad();
        click.moveToElementAndClick(
                wait.waitForElementEnabled(By.xpath(shippingMethodRadio.replace("<<text_replace>>", shipMethod))));
        waitForPageLoad();
    }

    /**
     * Clicks on the Continue to payment button
     *
     * @return Store Payment Page
     */
    public ShopifyStorePaymentPage clickContinuePayment() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementEnabled(continueToPaymentButton));
        waitForPageLoad();
        return new ShopifyStorePaymentPage(driver);
    }
}
