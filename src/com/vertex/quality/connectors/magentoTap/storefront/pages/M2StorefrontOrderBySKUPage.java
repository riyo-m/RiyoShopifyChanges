package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Order By SKU page
 *
 * @author alewis
 */
public class M2StorefrontOrderBySKUPage extends MagentoStorefrontPage {
    protected By skuFieldId = By.id("id-items0sku");
    protected By skuQuantityId = By.id("id-items0qty");

    protected By addToCartButtonClass = By.className("tocart");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontOrderBySKUPage(WebDriver driver) {
        super(driver);
    }

    /**
     * inputs SKU number for item
     *
     * @param inputString SKU value
     */
    public void inputSku(String inputString) {
        WebElement field = wait.waitForElementEnabled(skuFieldId);

        field.sendKeys(inputString);
    }

    /**
     * inputs quantity of item to add to the cart
     *
     * @param inputString quantity value
     */
    public void inputQuantity(String inputString) {
        WebElement field = wait.waitForElementEnabled(skuQuantityId);

        field.sendKeys(inputString);
    }

    /**
     * clicks the Add To Cart button
     *
     * @return shopping cart page
     */
    public M2StorefrontShoppingCartPage clickAddToCartButton() {
        WebElement button = wait.waitForElementEnabled(addToCartButtonClass);

        button.click();

        M2StorefrontShoppingCartPage shoppingCartPage = initializePageObject(M2StorefrontShoppingCartPage.class);

        return shoppingCartPage;
    }
}
