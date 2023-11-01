package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Bags Page
 *
 * @author alewis
 */
public class M2StorefrontBagsPage extends MagentoStorefrontPage {
    protected By mainContentID = By.id("maincontent");
    protected By columnsClass = By.className("columns");
    protected By columnClass = By.className("column");
    protected By productsWrapperClass = By.className("products-grid");
    protected By productsDetailClass = By.className("product-items");
    protected By productDetailClass = By.className("product-item"); // multiple of these
    protected By productInfoClass = By.className("product-item-info");
    protected By productDetailsClass = By.className("product-item-details");
    protected By productNameClass = By.className("product-item-name");
    protected By productLinkClass = By.className("product-item-link");
    protected By productImageClass = By.className("product-image-photo");

    protected String duffleBagText = "Joust Duffle Bag";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2StorefrontBagsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * clicks product Button
     *
     * @return M2StorefrontProductDetailsPage
     */
    public M2StorefrontProductDetailsPage clickProductButton() {
        WebElement vertexServicesButton = findProductButton();

        if (vertexServicesButton != null) {
            vertexServicesButton.click();
        } else {
            WebElement searchField = wait.waitForElementDisplayed(By.id("search"));
            text.enterText(searchField, "joust");
            text.pressEnter(By.id("search"));
            WebElement bagButton = findProductButton();
            bagButton.click();
        }

        return initializePageObject(M2StorefrontProductDetailsPage.class);
    }

    /**
     * clicks product Button
     *
     * @return M2StorefrontProductDetailsPage
     */
    public M2StorefrontProductDetailsPage clickProductButtonSelectItem(String itemString) {
        List<WebElement> bagButtons = wait.waitForAllElementsPresent(productImageClass);

        for (WebElement bagButton : bagButtons) {

            try {
                bagButton.click();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return initializePageObject(M2StorefrontProductDetailsPage.class);
    }

    /**
     * Locates product Button
     *
     * @return a products Button
     */
    public WebElement findProductButton() {
        wait.waitForElementDisplayed(mainContentID);
        WebElement mainButton = wait.waitForElementPresent(mainContentID);
        WebElement columnsButton = mainButton.findElement(columnsClass);
        WebElement columnButton = columnsButton.findElement(columnClass);
        WebElement WrapperButton = columnButton.findElement(productsWrapperClass);
        WebElement productsButton = WrapperButton.findElement(productsDetailClass);
        List<WebElement> products = productsButton.findElements(productDetailClass);

        WebElement productButton = null;

        for (WebElement button : products) {
            WebElement InfoButton = wait.waitForElementDisplayed(productInfoClass, button);
            WebElement DetailsButton = wait.waitForElementDisplayed(productDetailsClass, InfoButton);
            WebElement nameButton = DetailsButton.findElement(productNameClass);
            WebElement linkButton = nameButton.findElement(productLinkClass);

            if (duffleBagText.equals(linkButton.getText())) {
                productButton = linkButton;
            }
        }
        return productButton;
    }
}
