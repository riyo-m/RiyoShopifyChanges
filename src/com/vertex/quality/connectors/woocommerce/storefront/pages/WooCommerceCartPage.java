package com.vertex.quality.connectors.woocommerce.storefront.pages;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * shows cart page for WooCommerce which lists the products that have been selected in the
 * current order
 *
 * @author rohit.mogane
 */
public class WooCommerceCartPage extends WooCommerceProductsPage {
    public WooCommerceCartPage(WebDriver driver) {
        super(driver);
    }

    protected By cartLabel = By.xpath(".//h1[text()='Cart']");
    protected By removeAllProduct = By.xpath(".//td[@class='product-remove']");
    protected String removeProductByName = ".//tr//td[normalize-space(.)='<<text_replace>>']/preceding-sibling::td[@class='product-remove']";

    @FindBy(xpath = "//*[contains(text(),'Proceed to checkout')]")
    protected WebElement proceedToCheckOut;

    protected final By checkOut = By.xpath("//a[contains(text(),'Proceed to checkout')]");
    @FindAll({
            @FindBy(className = "remove"),
            @FindBy(linkText = "x")
    })
    private List<WebElement> removeProductsElement;

    /**
     * deletes all items in the cart
     */
    public void clearCart() {
        removeProductsElement
                .stream()
                .findFirst()
                .get()
                .click();
        VertexLogger.log("Cart cleared");
    }

    /**
     * clicks on proceed to checkout option
     */
    public void goToCheckout() {
        WebElement proceedToCheckOut = wait.waitForElementDisplayed(checkOut);
        click.javascriptClick(proceedToCheckOut);
    }

    /**
     * deletes particular item by matched name of Item in the cart
     * example: wooCommerceCartPage.deleteSingleLineItem("Belt");
     *
     * @param itemName pass value for item which should be removed from cart
     */
    public void deleteSingleLineItem(String itemName) {
        WebElement removeItem = wait.waitForElementDisplayed(By.xpath(removeProductByName.replace("<<text_replace>>", itemName)));
        click.performDoubleClick(removeItem);
        waitForPageLoad();
    }

    /**
     * deletes all items in the cart
     * example: wooCommerceCartPage.deleteAllLineItems();
     */
    public void deleteAllLineItems() {
        List<WebElement> removeProductsElement = element.getWebElements(removeAllProduct);
        for (int i = 0; i < removeProductsElement.size(); i++) {
            wait.waitForElementPresent(cartLabel);
            click.moveToElementAndClick(removeAllProduct);
            waitForPageLoad();
        }
    }
}
