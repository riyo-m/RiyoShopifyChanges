package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify Store search result page - contains common helper methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreSearchResultPage extends ShopifyPage {

    protected String searchedResultLink = ".//h3[@class='card__heading h5']//a[contains(text(),'<<text_replace>>')]";

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStoreSearchResultPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on the searched result item.
     *
     * @return Item Page
     */
    public ShopifyStoreItemPage clickOnSearchedResult(String item) {
        waitForPageLoad();
        WebElement searchedResult = wait.waitForElementPresent(By.xpath(searchedResultLink.replace("<<text_replace>>", item)));
        click.moveToElementAndClick(searchedResult);
        waitForPageLoad();
        return new ShopifyStoreItemPage(driver);
    }
}
