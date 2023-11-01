package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.connectors.dynamics365.sales.pages.base.SalesDocumentBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the sales invoice page
 *
 * @author Shruti
 */
public class SalesInvoicesPage extends SalesDocumentBasePage {

    protected By POST_INVOICE_BUTTON = By.cssSelector("[aria-label='Post Invoice']");

    public SalesInvoicesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fill in billing address info for invoice
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillBillToAddressInfo(String streetAddress, String city, String zipCode, String country) {
        fillBillToAddressInfo("Invoice", streetAddress, city, zipCode, country);
    }

    /**
     * Fill in shipping address info for invoice
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillShipToAddressInfo(String streetAddress, String city, String zipCode, String country) {
        fillShipToAddressInfo("Invoice", streetAddress, city, zipCode, country);
    }

    /**
     * Get auto-generated ID of invoice
     * @return
     */
    public String getID() {
        return getID("Invoice");
    }

    /**
     * Get auto-generated ID and set name of invoice as ID
     * @return
     */
    public String getIDAndUpdateName() {
        return getIDAndUpdateName("Invoice");
    }

    /**
     * Add product to invoice
     * @param productName
     * @param quantity
     * @param discountAmount
     */
    public void addProduct(String productName, String quantity, String discountAmount) {
        clickAddProduct();
        enterProductName(productName);
        enterQuantity(quantity);

        jsWaiter.sleep(2500);

        if (discountAmount != null) {
            enterDiscount(discountAmount);
        }

        clickSaveAndClose();
    }

    /**
     * Clicks on Post Invoice
     */
    public void clickPostInvoice() {
        waitForLoadingScreen();
        wait.waitForElementDisplayed(POST_INVOICE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(POST_INVOICE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Clicks on Yes to confirm post invoice
     */
    public void clickYes() {
        WebElement container = wait.waitForElementDisplayed(By.id("FullPageWebResource"));
        driver.switchTo().frame(container);

        wait.waitForElementEnabled(YES_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(YES_BUTTON);
        waitForPageLoad();
    }
}
