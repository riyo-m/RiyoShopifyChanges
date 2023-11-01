package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * ShopifyPage - Parent of all page objects within the package.
 * Contains common helper methods for Page Objects across the .pages package
 *
 * @author Shivam.Soni
 */
public class ShopifyPage extends VertexPage {

    protected By calculatingTax = By.xpath(".//div[normalize-space(.)='Estimated taxes']/following-sibling::div//span[contains(text(),'Calculating')]");
    protected By updatingText = By.xpath(".//div[normalize-space(.)='Summary']//following-sibling::div//span[normalize-space(.)='Updating…']");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Waits until Calculating Tax... label gets disappears.
     */
    public void waitTillCalculatingTaxDisappears() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(calculatingTax));
    }

    /**
     * Waits until Updating... label gets disappears.
     */
    public void waitTillUpdatingDisappears() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(updatingText));
    }
}
