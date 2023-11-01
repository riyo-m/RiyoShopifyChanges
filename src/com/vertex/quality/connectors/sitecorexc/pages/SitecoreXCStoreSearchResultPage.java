package com.vertex.quality.connectors.sitecorexc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Search result page of SitecoreXC
 *
 * @author Shivam.Soni
 */
public class SitecoreXCStoreSearchResultPage extends SitecoreXCPage {

    protected By allSearchedResult = By.xpath(".//ul[@class='search-result-list']//li");
    protected String productName = ".//ul[@class='search-result-list']//li//a[normalize-space(.)='<<text_replace>>']";
    protected String productID = "(.//ul[@class='search-result-list']//li//a[contains(@href,'<<text_replace>>')])[1]";
    protected By addToCartButton = By.xpath(".//button[@class='add-to-cart-btn']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCStoreSearchResultPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on product
     *
     * @param product product name which is to be opened
     */
    private void clickOnProduct(String product) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(allSearchedResult);
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(productName.replace("<<text_replace>>", product))));
        waitForPageLoad();
    }

    /**
     * Add product(s) to the cart
     *
     * @param product product name which is to be added to the cart.
     */
    public void addProductToTheCart(String product) {
        waitForPageLoad();
        clickOnProduct(product);
        click.moveToElementAndClick(wait.waitForElementPresent(addToCartButton));
        waitForPageLoad();
    }

    /**
     * Selects the product based on Product ID & add the product to the cart.
     *
     * @param prodID product's id which product needs to be added to the cart
     */
    public void addProductToTheCartByProductID(String prodID) {
        waitForPageLoad();
        wait.waitForAllElementsPresent(allSearchedResult);
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(productID.replace("<<text_replace>>", prodID))));
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(addToCartButton));
        waitForPageLoad();
    }
}
