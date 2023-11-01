package com.vertex.quality.connectors.sitecorexc.pages;

import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * SitecoreXC checkout page
 *
 * @author Shivam.Soni
 */
public class SitecoreXCStoreCheckoutPage extends SitecoreXCPage {

    protected By shippingAddressLabel = By.xpath(".//div[@class='shipall-title']/following-sibling::div/h3");
    protected By deliveryPreferenceDropdown = By.xpath(".//select[@id='orderShippingPreference']");
    protected By shippingNameBox = By.xpath(".//label[@for='ShipAllItemsInput-Name']//following-sibling::input");
    protected By shippingCityBox = By.xpath(".//label[@for='ShipAllItemsInput-City']//following-sibling::input");
    protected By shippingCountryDropdown = By.xpath(".//label[@for='ShipAllItemsInput-Country']//following-sibling::select");
    protected By shippingStateBox = By.xpath(".//label[@for='ShipAllItemsInput-State']//following-sibling::input");
    protected By shippingAddressBox = By.xpath(".//label[@for='ShipAllItemsInput-Address']//following-sibling::input");
    protected By shippingZipcodeBox = By.xpath(".//label[@for='ShipAllItemsInput-Zipcode']//following-sibling::input");
    protected By viewShippingOptionsButton = By.xpath(".//button[@id='orderGetShippingMethods']");
    protected By allShippingOptionsRadio = By.xpath(".//div[@class='shipall-options-items']//input");
    protected String shippingOptionRadio = ".//label[normalize-space(.)='<<text_replace>>']//preceding-sibling::input";
    protected By continueToBillingButton = By.xpath(".//button[@id='ToBillingButton']");
    protected By orderSummarySection = By.xpath(".//div[@class='cart-total-title']/parent::div");
    protected By orderSummaryLabel = By.xpath(".//div[@class='cart-total-title']");
    protected By subtotalLabel = By.xpath(".//span[normalize-space(.)='Sub total']");
    protected By discountLabel = By.xpath(".//span[normalize-space(.)='Total discount']");
    protected By savingsLabel = By.xpath("(.//span[text()='Savings'])[last()]");
    protected By shippingLabel = By.xpath(".//span[normalize-space(.)='Shipping']");
    protected By taxesLabel = By.xpath(".//span[normalize-space(.)='Taxes']");
    protected By subtotalAmount = By.xpath(".//span[normalize-space(.)='Sub total']/following-sibling::span");
    protected By discountAmount = By.xpath(".//span[normalize-space(.)='Total discount']/following-sibling::span");
    protected By savingsAmount = By.xpath("(.//span[text()='Savings']/following-sibling::span)[last()]");
    protected By shippingAmount = By.xpath(".//span[normalize-space(.)='Shipping']/following-sibling::span");
    protected By taxesAmount = By.xpath(".//span[normalize-space(.)='Taxes']/following-sibling::span");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCStoreCheckoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects Delivery Preference
     *
     * @param preference delivery preference
     */
    public void selectDeliveryPreference(String preference) {
        waitForPageLoad();
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(deliveryPreferenceDropdown), preference);
    }

    /**
     * Enters shipping address
     * Please maintain the below sequence:
     * City, Country, State, Address Line, Zipcode
     * All the fields are mandatory also mentioned sequence is important to set data
     *
     * @param address address detail
     */
    public void enterShippingAddress(String... address) {
        if (address.length != 5) {
            Assert.fail("Parameters mismatched kindly check JavaDoc.");
        }
        waitForPageLoad();
        wait.waitForElementPresent(shippingAddressLabel);
        text.enterText(wait.waitForElementPresent(shippingNameBox), SiteCoreXCData.DEFAULT_CUSTOMER_NAME.data);
        text.enterText(wait.waitForElementPresent(shippingCityBox), address[0]);
        dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(shippingCountryDropdown), address[1]);
        text.enterText(wait.waitForElementPresent(shippingStateBox), address[2]);
        text.enterText(wait.waitForElementPresent(shippingAddressBox), address[3]);
        text.enterText(wait.waitForElementPresent(shippingZipcodeBox), address[4]);
        waitForPageLoad();
    }

    /**
     * Clicks on View Shipping Options button
     */
    private void clickOnViewShippingOptions() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(viewShippingOptionsButton));
        if (!element.isElementDisplayed(allShippingOptionsRadio)) {
            click.moveToElementAndClick(wait.waitForElementPresent(viewShippingOptionsButton));
        }
        wait.waitForAllElementsPresent(allShippingOptionsRadio);
        waitForPageLoad();
    }

    /**
     * Selects shipping option
     *
     * @param option shipping option
     */
    public void selectShippingOption(String option) {
        waitForPageLoad();
        clickOnViewShippingOptions();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(shippingOptionRadio.replace("<<text_replace>>", option))));
        waitForPageLoad();
    }

    /**
     * Clicks on Continue To Billing
     */
    public void clickOnContinueToBilling() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(continueToBillingButton));
        waitForPageLoad();
    }

    /**
     * Gets tax amount from the UI
     *
     * @return tax amount from UI.
     */
    public double getTaxFromUI() {
        String tax;
        waitForPageLoad();
        wait.waitForElementPresent(orderSummarySection);
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(subtotalLabel);
        wait.waitForElementPresent(discountLabel);
        wait.waitForElementPresent(shippingLabel);
        wait.waitForElementPresent(taxesLabel);
        tax = text.getElementText(wait.waitForElementPresent(taxesAmount)).replace("USD", "").replace(",", "").trim();
        return Double.parseDouble(tax);
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTax(double taxAmount) {
        double subtotal = 0;
        double discount = 0;
        double shipHandle = 0;
        double expectedTax = 0;
        waitForPageLoad();
        wait.waitForElementPresent(orderSummarySection);
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("USD", "").replace(",", "").trim());
        shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("USD", "").replace(",", "").trim());
        if (element.isElementDisplayed(savingsLabel)) {
            discount = Double.parseDouble(text.getElementText(wait.waitForElementPresent(savingsAmount)).replace("USD", "").replace(",", "").trim());
        }
        expectedTax = (subtotal + shipHandle - discount) * (taxAmount / 100);
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param isDiscountIncluded true to include discount in formula & false to ignore discount in the formula
     * @param taxPercent         percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTax(boolean isDiscountIncluded, double taxPercent) {
        double subtotal;
        double discount;
        double shipHandle;
        double expectedTax;
        waitForPageLoad();
        wait.waitForElementPresent(orderSummarySection);
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("USD", "").replace(",", "").trim());
        shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("USD", "").replace(",", "").trim());
        discount = Double.parseDouble(text.getElementText(wait.waitForElementPresent(savingsAmount)).replace("USD", "").replace(",", "").trim());
        if (isDiscountIncluded) {
            expectedTax = (subtotal + shipHandle - discount) * (taxPercent / 100);
        } else {
            expectedTax = (subtotal + shipHandle) * (taxPercent / 100);
        }
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param isDiscountIncluded true to include discount in formula & false to ignore discount in the formula
     * @param isShippingIncluded true to include shipping in formula & false to ignore shipping in the formula
     * @param taxPercent         percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTax(boolean isDiscountIncluded, boolean isShippingIncluded, double taxPercent) {
        double subtotal;
        double discount;
        double shipHandle;
        double expectedTax;
        waitForPageLoad();
        wait.waitForElementPresent(orderSummarySection);
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(subtotalAmount)).replace("USD", "").replace(",", "").trim());
        if (isDiscountIncluded) {
            discount = Double.parseDouble(text.getElementText(wait.waitForElementPresent(discountAmount)).replace("USD", "").replace(",", "").trim());
        } else {
            discount = 0;
        }
        if (isShippingIncluded) {
            shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("USD", "").replace(",", "").trim());
        } else {
            shipHandle = 0;
        }
        expectedTax = (subtotal + shipHandle - discount) * (taxPercent / 100);
        return Double.parseDouble(String.format("%.2f", expectedTax));
    }

    /**
     * Verifies the shipping & discount charges are equal
     * Based on App behavior if FreeShipping meets criteria then same as shipping amount discount will be applied automatically
     * Used in Discount tests for FreeShipping discount coupons & eligible criteria for free shipping tests
     *
     * @return true or false based on condition match
     */
    public boolean isShippingFree() {
        double discount;
        double shipHandle;
        waitForPageLoad();
        wait.waitForElementPresent(orderSummarySection);
        wait.waitForElementPresent(orderSummaryLabel);
        wait.waitForElementPresent(shippingLabel);
        shipHandle = Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("USD", "").replace(",", "").trim());
        discount = Double.parseDouble(text.getElementText(wait.waitForElementPresent(discountAmount)).replace("USD", "").replace(",", "").trim());
        return shipHandle == discount;
    }
}
