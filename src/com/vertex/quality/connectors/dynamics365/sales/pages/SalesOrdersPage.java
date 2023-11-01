package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.connectors.dynamics365.sales.pages.base.SalesDocumentBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents the sales order page
 *
 * @author Shruti
 */
public class SalesOrdersPage extends SalesDocumentBasePage {
    protected By CREATE_INVOICE_BUTTON = By.xpath("//button[@aria-label='Create Invoice']");

    public SalesOrdersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fill in billing address info of order
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillBillToAddressInfo(String streetAddress, String city, String zipCode, String country) {
        fillBillToAddressInfo("Order", streetAddress, city, zipCode, country);
    }

    /**
     * Fill in shipping address info of order
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillShipToAddressInfo(String streetAddress, String city, String zipCode, String country) {
        fillShipToAddressInfo("Order", streetAddress, city, zipCode, country);
    }

    /**
     * Get auto-generated ID of order
     * @return id
     */
    public String getID() {
        return getID("Order");
    }

    /**
     * Get auto-generated ID and set name of order as ID
     * @return id
     */
    public String getIDAndUpdateName() {
        return getIDAndUpdateName("Order");
    }

    /**
     * Invoice the order
     * @return SalesInvoicesPage
     */
    public SalesInvoicesPage clickCreateInvoice() {
        wait.waitForElementDisplayed(CREATE_INVOICE_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(CREATE_INVOICE_BUTTON);

        waitForPageTitleContains("Invoice");
        return new SalesInvoicesPage(driver);
    }

}
