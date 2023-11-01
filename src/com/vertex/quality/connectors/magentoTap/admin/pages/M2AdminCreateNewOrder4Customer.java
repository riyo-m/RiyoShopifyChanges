package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.WebDriver;

/**
 * Page where products and account info are added to the order. Order is
 * submitted on this page
 *
 * @author alewis
 */
public class M2AdminCreateNewOrder4Customer extends MagentoAdminPage {

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminCreateNewOrder4Customer(WebDriver driver) {
        super(driver);
    }
}
