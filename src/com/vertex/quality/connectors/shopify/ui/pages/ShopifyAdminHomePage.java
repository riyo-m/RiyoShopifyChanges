package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify Admin's home page - contains all helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyAdminHomePage extends ShopifyPage {

    protected String leftPanelNavigationOption = ".//ul[contains(@class,'Polaris-Navigation__Section')]//span[text()='<<text_replace>>']";

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyAdminHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the respective option from left panel
     *
     * @param option option of left panel
     */
    public void navigateToLeftPanelOption(String option) {
        waitForPageLoad();
        WebElement leftOption = wait.waitForElementPresent(By.xpath(leftPanelNavigationOption.replace("<<text_replace>>", option)));
        click.moveToElementAndClick(leftOption);
        waitForPageLoad();
    }
}
