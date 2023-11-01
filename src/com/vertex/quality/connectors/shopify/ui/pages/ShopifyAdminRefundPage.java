package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Shopify Admin panel refund page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminRefundPage extends ShopifyPage {

    protected By refundPageLabel = By.xpath(".//h1[text()='Refund']");
    protected By refundReturnedItemsLabel = By.xpath(".//h1[text()='Refund returned items']");
    protected String productName = ".//div[contains(@class,'Polaris-Box')]//a[text()='<<text_replace>>']";
    protected String productLevelRefundQtyBox = "(.//a[text()='<<text_replace>>']//parent::div//parent::div)[1]/parent::div/following-sibling::div//input";
    protected By refundShippingAmountBox = By.xpath(".//div[normalize-space(.)='Refund shipping']/following-sibling::div//input");
    protected By shippingAmount = By.xpath(".//span[contains(text(),'Shipping rate: ')]//span");
    protected By reasonForRefund = By.xpath(".//div[normalize-space(.)='Reason for refund']/following-sibling::div//input");
    protected By adjustmentAmountBox = By.xpath(".//div[normalize-space(.)='Summary']//following-sibling::div//input[@type='text']");
    protected By refundButton = By.xpath(".//button[contains(normalize-space(.),'Refund')]");
    protected By itemsSubtotalLabel = By.xpath(".//span[normalize-space(.)='Items subtotal']");
    protected By itemSubtotalAmount = By.xpath(".//span[normalize-space(.)='Items subtotal']/parent::div/parent::div/following-sibling::div/span");
    protected By shippingLabel = By.xpath(".//div[normalize-space(.)='Shipping']");
    protected By shippingAmountSummary = By.xpath(".//div[normalize-space(.)='Shipping']/following-sibling::div");
    protected By taxLabel = By.xpath(".//div[normalize-space(.)='Tax']");
    protected By taxAmount = By.xpath(".//div[normalize-space(.)='Tax']/following-sibling::div");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminRefundPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters quantity for the refund
     *
     * @param product product's name which is to be refunded
     * @param qty     No. of qty which is to be refunded
     */
    public void enterRefundQty(String product, String qty) {
        waitForPageLoad();
        wait.waitForElementPresent(refundPageLabel);
        wait.waitForElementPresent(By.xpath(productName.replace("<<text_replace>>", product)));
        WebElement qtyBox = wait.waitForElementPresent(By.xpath(productLevelRefundQtyBox.replace("<<text_replace>>", product)));
        text.selectAllAndInputText(qtyBox, qty);
        text.pressTab(qtyBox);
        waitForPageLoad();
    }

    /**
     * Read shipping amount from the UI
     *
     * @return shipping amount
     */
    public double getShippingAmountFromUI() {
        waitForPageLoad();
        wait.waitForElementPresent(refundPageLabel);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(shippingAmount)).replace("$", "").replace(",", ""));
    }

    /**
     * Enters shipping amount for refund
     *
     * @param amount value which is to be refunded
     */
    public void enterRefundShipping(String amount) {
        waitForPageLoad();
        wait.waitForElementPresent(refundPageLabel);
        WebElement refundAmtBox = wait.waitForElementPresent(refundShippingAmountBox);
        text.selectAllAndInputText(refundAmtBox, amount);
        text.pressTab(refundAmtBox);
        waitForPageLoad();
    }

    /**
     * Enters reason for refund
     */
    public void enterRefundReason() {
        waitForPageLoad();
        wait.waitForElementPresent(refundPageLabel);
        WebElement refundReason = wait.waitForElementPresent(reasonForRefund);
        text.enterText(refundReason, "Testing refunds");
        text.pressTab(refundReason);
        waitForPageLoad();
    }

    /**
     * Enters adjustment amount
     *
     * @param amount value which is to be adjusted
     */
    public void enterAdjustmentAmount(String amount) {
        waitForPageLoad();
        wait.waitForElementPresent(refundPageLabel);
        WebElement adjustment = wait.waitForElementPresent(adjustmentAmountBox);
        text.selectAllAndInputText(adjustment, amount);
        text.pressTab(adjustment);
        waitForPageLoad();
    }

    /**
     * It will verify whether tax label & amount is present on UI or not - Used in customer exemption tests
     *
     * @return true or false based on condition match
     */
    public boolean isTaxPresentOnUI() {
        waitForPageLoad();
        wait.waitForElementPresent(itemsSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        return element.isElementDisplayed(taxLabel) && element.isElementDisplayed(taxAmount);
    }

    /**
     * Clicks on Refund button
     *
     * @return Order details page
     */
    public ShopifyAdminOrderDetailsPage clickRefund() {
        waitForPageLoad();
        wait.waitForElementPresent(refundPageLabel);
        click.moveToElementAndClick(wait.waitForElementPresent(refundButton));
        waitForPageLoad();
        return new ShopifyAdminOrderDetailsPage(driver);
    }

    /**
     * Gets tax amount from the UI
     *
     * @return tax amount from UI.
     */
    public double getTaxFromUI() {
        waitForPageLoad();
        wait.waitForElementPresent(itemsSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        wait.waitForElementPresent(taxLabel);
        return Double.parseDouble(text.getElementText(wait.waitForElementPresent(taxAmount)).replace("$", "").replace(",", ""));
    }

    /**
     * Calculates percentage based or expected tax
     *
     * @param taxAmount percent of tax
     * @return calculated percent based tax
     */
    public double calculatePercentBasedTax(double taxAmount) {
        double subtotal = 0;
        String ship;
        double shipping = 0;
        double expectedTax = 0;
        waitForPageLoad();
        waitTillUpdatingDisappears();
        wait.waitForElementPresent(itemsSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(itemSubtotalAmount)).replace("$", "").replace(",", ""));
        ship = text.getElementText(wait.waitForElementPresent(shippingAmountSummary));
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
    public double calculatePercentBasedTaxDownRounding(double taxAmount) {
        DecimalFormat format = new DecimalFormat("0.00");
        double subtotal = 0;
        String ship;
        double shipping = 0;
        double expectedTax = 0;
        waitForPageLoad();
        wait.waitForElementPresent(itemsSubtotalLabel);
        wait.waitForElementPresent(shippingLabel);
        subtotal = Double.parseDouble(text.getElementText(wait.waitForElementPresent(itemSubtotalAmount)).replace("$", "").replace(",", ""));
        ship = text.getElementText(wait.waitForElementPresent(shippingAmount));
        if (ship.equalsIgnoreCase("Free")) {
            shipping = 0;
        } else {
            shipping = Double.parseDouble(ship.replace("$", "").replace(",", ""));
        }
        expectedTax = (subtotal + shipping) * (taxAmount / 100);
        format.setRoundingMode(RoundingMode.DOWN);
        return Double.parseDouble(format.format(expectedTax));
    }
}
