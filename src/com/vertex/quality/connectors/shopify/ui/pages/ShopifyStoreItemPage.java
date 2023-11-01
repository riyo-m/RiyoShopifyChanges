package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * After searching the result on clicking the item this Item page will be opened.
 * This page contains all the methods & variable of Item Page
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreItemPage extends ShopifyPage {

    protected By addToCartButton = By.xpath(".//button[@name='add']");
    protected By closeCheckoutPopup = By.xpath("(.//button[@aria-label='Close'])[2]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStoreItemPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on Add to cart button
     */
    public void clickAddToCart() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(addToCartButton));
        waitForPageLoad();
        if (element.isElementDisplayed(closeCheckoutPopup)) {
            click.moveToElementAndClick(closeCheckoutPopup);
        }
        waitForPageLoad();
    }
}
