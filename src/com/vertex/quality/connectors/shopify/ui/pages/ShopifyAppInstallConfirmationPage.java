package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shopify App install confirmation page -> contains all variables & helper methods
 *
 * @author Shivam.Soni
 */
public class ShopifyAppInstallConfirmationPage extends ShopifyPage {

    protected By installAppButton = By.xpath(".//div[contains(@class,'Polaris-Page-Header')]//button[normalize-space(.)='Install unlisted app']");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAppInstallConfirmationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on install unlisted app button
     *
     * @return Vertex On-boarding page
     */
    public ShopifyVertexOnBoardingPage clickOnInstallApp() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(installAppButton));
        waitForPageLoad();
        return new ShopifyVertexOnBoardingPage(driver);
    }
}