package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Search result page of Magento
 *
 * @author Shivam.Soni
 */
public class M2StorefrontSearchResultPage extends MagentoStorefrontPage {
    /**
     * Parameterized constructor
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontSearchResultPage(WebDriver driver) {
        super(driver);
    }

    protected By searchForLabel = By.xpath(".//span[contains(text(),'Search results for:')]");
    protected By recordLimiterDropdown = By.id("limiter");
    protected By allSearchedResult = By.xpath(".//ol[@class='products list items product-items']//li");
    protected String productName = ".//strong[contains(normalize-space(.),'<<text_replace>>')]";
    protected String productAddToCartButton = ".//strong[contains(normalize-space(.),'<<text_replace>>')]//following-sibling::div//button[normalize-space(.)='Add to Cart']";

    /**
     * Loads maximum count of the records on the page.
     */
    public void loadMaximumRecords() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(searchForLabel);
        WebElement limiter = wait.waitForElementPresent(recordLimiterDropdown);
        dropdown.selectDropdownByDisplayName(limiter, "36");
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Add product(s) to the cart
     *
     * @param product product name which is to be added to the cart.
     */
    public void addProductToTheCart(String product) {
        waitForSpinnerToBeDisappeared();
        wait.waitForAllElementsPresent(allSearchedResult);
        hover.hoverOverElement(wait.waitForElementPresent(By.xpath(productName.replace("<<text_replace>>", product))));
        click.javascriptClick(wait.waitForElementPresent(By.xpath(productAddToCartButton.replace("<<text_replace>>", product))));
        waitForSpinnerToBeDisappeared();
    }
}
