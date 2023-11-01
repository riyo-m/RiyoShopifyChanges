package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.dynamics365.sales.pages.base.SalesDocumentBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents the sales quote page
 *
 * @author Shruti
 */
public class SalesQuotesPage extends SalesDocumentBasePage {
    public SalesQuotesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fill in billing address info of quote
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillBillToAddressInfo(String streetAddress, String city, String zipCode, String country) {
        fillBillToAddressInfo("Quote", streetAddress, city, zipCode, country);
    }

    /**
     * Fill in shipping address info of quote
     * @param streetAddress
     * @param city
     * @param zipCode
     * @param country
     */
    public void fillShipToAddressInfo(String streetAddress, String city, String zipCode, String country) {
        fillShipToAddressInfo("Quote", streetAddress, city, zipCode, country);
    }

    /**
     * Get auto-generated ID of quote
     * @return id
     */
    public String getID() {
        return getID("Quote");
    }

    /**
     * Get auto-generated ID and set name of quote as ID
     * @return id
     */
    public String getIDAndUpdateName() {
        return getIDAndUpdateName("Quote");
    }

}
