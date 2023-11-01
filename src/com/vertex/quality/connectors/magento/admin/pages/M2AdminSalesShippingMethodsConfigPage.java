package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Sales Shipping Methods Configuration Page
 *
 * @author alewis
 */

public class M2AdminSalesShippingMethodsConfigPage extends MagentoAdminPage {

    By freeShippingEnabledField = By.id("carriers_freeshipping_active");
    By saveID = By.id("save");

    public M2AdminSalesShippingMethodsConfigPage(WebDriver driver) {
        super(driver);
    }

    /**
     * changes the value of Enabled Field (Yes/No) in Free Shipping tab
     *
     * @param freeShippingEnabledValue set value to Yes or No
     */
    public void changeFreeShippingEnabledField(String freeShippingEnabledValue) {
        WebElement freeShippingEnabled = wait.waitForElementPresent(freeShippingEnabledField);
        dropdown.selectDropdownByDisplayName(freeShippingEnabled, freeShippingEnabledValue);
    }

    /**
     * clicks on save configuration button
     */
    public void saveConfig() {
        wait.waitForElementPresent(saveID);
        click.clickElement(saveID);
        jsWaiter.waitForLoadAll();
    }
}