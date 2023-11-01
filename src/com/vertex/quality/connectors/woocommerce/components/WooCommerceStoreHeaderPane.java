package com.vertex.quality.connectors.woocommerce.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * handles interactions with elements which are at the top of many pages on WooCommerce store
 *
 * @author rohit.mogane
 */
public class WooCommerceStoreHeaderPane extends VertexComponent {

    public WooCommerceStoreHeaderPane(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    protected By shopLink = By.xpath("(//a[contains(text(),'Shop')])[1]");
    protected By searchButton = By.xpath("(//*[contains(text(),'Search')])[3]");
    protected By homeLink = By.xpath("(//*[contains(text(),'Home')])[1]");
    protected By searchInput = By.xpath("//*[@class='search-field']");

    /**
     * opens the home page
     */
    public void clickHomeLink() {
        WebElement homeElement = wait.waitForElementEnabled(homeLink);
        click.moveToElementAndClick(homeElement);
    }

    /**
     * searches the storefront for a product with the given name
     */
    public void searchForProduct(String productName) {
        WebElement searchElement = wait.waitForElementEnabled(searchButton);
        click.moveToElementAndClick(searchElement);
        WebElement searchInputElement = wait.waitForElementEnabled(searchInput);
        text.enterText(searchInputElement,productName);
        text.pressEnter(searchElement);
    }

    /**
     * opens shop page for WooCommerce
     */
    public void clickShopLink() {
        WebElement shopElement = wait.waitForElementEnabled(shopLink);
        click.moveToElementAndClick(shopElement);
    }
}
