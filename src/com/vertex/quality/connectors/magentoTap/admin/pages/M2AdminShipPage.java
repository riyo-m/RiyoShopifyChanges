package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class M2AdminShipPage extends MagentoAdminPage {
    By submitButtonClass = By.className("submit-button");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminShipPage(final WebDriver driver) {
        super(driver);
    }

    /**
     * submits the shipment for the order
     */
    public void submitShipment() {
        WebElement submitButton = wait.waitForElementEnabled(submitButtonClass);
        click.clickElement(submitButton);
    }
}
