package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Order Number Page
 *
 * @author alewis
 */
public class M2StorefrontOrderNumberPage extends MagentoStorefrontPage {
    protected By grandTotalClass = By.className("grand_total");
    protected By grandTotalClassIncl = By.className("grand_total_incl");
    protected By shippingClass = By.className("shipping");
    protected By shippingInclClass = By.className("shipping_incl");
    protected By markClass = By.className("mark");
    protected By labelClass = By.className("label");
    protected By amountClass = By.className("amount");
    protected By priceClass = By.className("price");

    protected By subtotalExclClassWO = By.className("subtotal_excl");
    protected By subtotalInclClassWO = By.className("subtotal_incl");
    protected By salesUseClass = By.className("details-1");
    protected By totalsTaxSummaryClass = By.className("totals-tax-summary");
    protected String grandTotalExclString = "Grand Total (Excl.Tax)";
    protected String grandTotalInclString = "Grand Total (Incl.Tax)";
    protected String shippingExclTaxString = "Shipping & Handling (Excl.Tax)";
    protected String shippingInclTaxString = "Shipping & Handling (Incl.Tax)";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontOrderNumberPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the subtotal excluding tax of the whole order
     *
     * @return a double of the subtotal of the whole order
     */
    public double getSubtotalExclTaxWholeOrder() {
        WebElement subtotalExcl = wait.waitForElementPresent(subtotalExclClassWO);
        WebElement price = wait.waitForElementPresent(priceClass, subtotalExcl);
        String priceText = price.getText();
        String parsedTaxValue = priceText.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * Gets the subtotal including tax of the whole order
     *
     * @return a double of the subtotal of the whole order
     */
    public double getSubtotalInclTaxWholeOrder() {
        WebElement subtotalIncl = wait.waitForElementPresent(subtotalInclClassWO);
        WebElement price = wait.waitForElementPresent(priceClass, subtotalIncl);
        String priceText = price.getText();
        String parsedTaxValue = priceText.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * Gets the shipping excluding tax of the whole order
     *
     * @return a string of the shipping excluding tax of the whole order
     */
    public double getShippingExclTax() {
        WebElement shipping = wait.waitForElementPresent(shippingClass);

        WebElement price = wait.waitForElementPresent(priceClass, shipping);
        String priceText = price.getText();

        String parsedTaxValue = priceText.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);
        return priceDouble;
    }

    /**
     * Gets the shipping including tax of the whole order
     *
     * @return a string of the shipping including tax of the whole order
     */
    public double getShippingInclTax() {
        WebElement shipping = wait.waitForElementPresent(shippingInclClass);
        WebElement price = wait.waitForElementPresent(priceClass, shipping);
        String priceText = price.getText();

        String parsedTaxValue = priceText.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);
        return priceDouble;
    }

    /**
     * Gets the grand total not including tax on order made to a PA address
     *
     * @return a double of grand total excluding tax
     */
    public double getGrandTotalExclTax() {
        String priceString = null;
        List<WebElement> grandTotals = wait.waitForAllElementsPresent(grandTotalClass);

        WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, grandTotalExclString);
        if (grandTotal != null) {
            WebElement amount = grandTotal.findElement(amountClass);
            WebElement price = amount.findElement(priceClass);
            priceString = price.getText();
        }
        String parsedTaxValue = priceString.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }

    /**
     * Gets the price of the sales and use tax in the 'Items Ordered' section
     *
     * @return a string of the price of the sales and use tax
     */
    public String getSalesUseTax() {
        WebElement salesUse = wait.waitForElementPresent(salesUseClass);
        WebElement price = wait.waitForElementPresent(priceClass, salesUse);
        String priceText = price.getText();

        return priceText;
    }

    /**
     * Gets the price of the total tax of the order in the 'Items Ordered' section
     *
     * @return a string of the price of the total tax
     */
    public String getTaxTotal() {
        WebElement totalsTax = wait.waitForElementPresent(totalsTaxSummaryClass);
        WebElement price = wait.waitForElementPresent(priceClass, totalsTax);
        String priceText = price.getText();

        return priceText;
    }

    /**
     * Gets the grand total including tax of the order made to a PA address
     *
     * @return a double of grand total including tax
     */
    public double getGrandTotalInclTax() {
        String priceString = null;
        List<WebElement> grandTotals = wait.waitForAllElementsPresent(grandTotalClassIncl);

        WebElement grandTotal = element.selectElementByNestedLabel(grandTotals, markClass, grandTotalInclString);
        if (grandTotal != null) {
            WebElement amount = grandTotal.findElement(amountClass);
            WebElement price = amount.findElement(priceClass);
            priceString = price.getText();
        }
        String parsedTaxValue = priceString.substring(1);
        double priceDouble = Double.parseDouble(parsedTaxValue);

        return priceDouble;
    }
}
