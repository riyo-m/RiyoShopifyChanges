package com.vertex.quality.connectors.orocommerce.pages.admin;

import com.vertex.quality.connectors.orocommerce.pages.base.OroAdminBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Selenium page to interact with Warehouse web-page.
 *
 * @author Shivam.Soni
 */
public class OroAdminWarehousesPage extends OroAdminBasePage {
    public OroAdminWarehousesPage(WebDriver driver) {
        super(driver);
    }

    By allWarehousesLabel = By.xpath(".//h1[text()='All Warehouses']");
    By allWarehousesRows = By.xpath(".//tbody[@class='grid-body']//tr");
    By activeWarehouseEditLoc = By.xpath(".//tbody[@class='grid-body']//td//li//a[@title='Edit']");
    By warehousesLabel = By.xpath(".//a[text()='Warehouses']");
    By warehouseAddress = By.xpath(".//h4[text()='Warehouse Address']");
    By editWarehouseButton = By.xpath(".//a[@title='Edit Warehouse']");
    By useShippingOriginChkBx = By.xpath(".//input[@name='oro_warehouse[warehouse_address][system]']");
    By saveAndCloseButton = By.xpath("(.//button[normalize-space(.)='Save and Close'])[1]");

    /**
     * Selects active warehouse to edit address or details of warehouse
     */
    public void editActiveWarehouseAddress() {
        waitForPageLoad();
        wait.waitForElementPresent(allWarehousesLabel);
        wait.waitForAllElementsPresent(allWarehousesRows);
        WebElement activeWH = wait.waitForElementPresent(activeWarehouseEditLoc);
        click.moveToElementAndClick(activeWH);
        waitForPageLoad();
        wait.waitForElementPresent(warehousesLabel);
        wait.waitForElementPresent(warehouseAddress);
        if (element.isElementPresent(editWarehouseButton)) {
            click.moveToElementAndClick(editWarehouseButton);
            waitForPageLoad();
            wait.waitForElementPresent(warehousesLabel);
            wait.waitForElementPresent(warehouseAddress);
        }
    }

    /**
     * Selects or de-selects Use Shipping Origin check box
     *
     * @param select pass true to select & false to de-select
     */
    public void selectOrDeselectUseShippingOrigin(boolean select) {
        waitForPageLoad();
        wait.waitForElementPresent(warehousesLabel);
        wait.waitForElementPresent(warehouseAddress);
        WebElement useShippingOrigin = wait.waitForElementPresent(useShippingOriginChkBx);
        if (select && !useShippingOrigin.isSelected()) {
            click.moveToElementAndClick(useShippingOrigin);
        } else if (!select && useShippingOrigin.isSelected()) {
            click.moveToElementAndClick(useShippingOrigin);
        }
    }

    /**
     * Clicks on Save and Close button to save settings on Warehouse page.
     */
    public void clickSaveAndClose() {
        waitForPageLoad();
        WebElement save = wait.waitForElementPresent(saveAndCloseButton);
        click.moveToElementAndClick(save);
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOf(save));
    }
}
