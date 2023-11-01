package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify Admin panel - Settings page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminSettingsPage extends ShopifyPage {

    protected By settingsDialog = By.id("SettingsDialog");
    protected String settingsOptions = ".//nav[@aria-label]//li[normalize-space(.)='<<text_replace>>']";

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminSettingsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the respective option from Settings dialog
     *
     * @param option option of left panel
     */
    public void navigateToSettingsOption(String option) {
        waitForPageLoad();
        wait.waitForElementPresent(settingsDialog);
        WebElement leftOption = wait.waitForElementEnabled(By.xpath(settingsOptions.replace("<<text_replace>>", option)));
        click.moveToElementAndClick(leftOption);
        waitForPageLoad();
    }
}
