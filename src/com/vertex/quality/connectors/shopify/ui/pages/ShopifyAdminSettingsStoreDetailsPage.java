package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shopify admin panel -> Settings -> Store Details - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminSettingsStoreDetailsPage extends ShopifyPage {

    protected By editBillingInfoLabel = By.xpath("(.//div[normalize-space(.)='Billing information'])[1]");
    protected By editBillingInfoButton = By.xpath("(.//div[normalize-space(.)='Billing information'])[1]/following-sibling::div//button[normalize-space(.)='Edit']");
    protected By countryDropdown = By.xpath(".//select[@name='country']");
    protected By addressLine1Box = By.xpath(".//input[@name='address1']");
    protected By cityBox = By.xpath(".//input[@name='city']");
    protected By stateProvinceDropdown = By.xpath(".//select[@name='province']");
    protected By zipBox = By.xpath(".//input[@name='zip']");
    protected By saveButton = By.xpath("(.//button[normalize-space(.)='Save'])[last()]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminSettingsStoreDetailsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on edit billing info button
     */
    public void editBillingInformation() {
        waitForPageLoad();
        wait.waitForElementPresent(editBillingInfoLabel);
        click.clickElement(editBillingInfoButton);
        waitForPageLoad();
    }

    /**
     * Clicks on the Save button
     */
    public void clickSaveButton() {
        waitForPageLoad();
        click.clickElement(saveButton);
        waitForPageLoad();
    }
}
