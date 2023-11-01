package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Gear Page
 *
 * @author alewis
 */
public class M2StorefrontGearPage extends MagentoStorefrontPage {
    protected By bagsButtonID = By.id("maincontent");
    protected By columnsClass = By.className("columns");
    protected By sidebarMainClass = By.className("sidebar-main");
    protected By widgetClass = By.className("widget");
    protected By categoriesClass = By.className("categories-menu");
    protected By itemsClass = By.className("items");
    protected By itemClass = By.className("item");
    protected By bagsTag = By.tagName("a");

    protected String bagsButtonText = "Bags";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontGearPage(WebDriver driver) {
        super(driver);
    }

    /**
     * clicks the Bags Button on navigation panel
     *
     * @return the Bags Page
     */
    public M2StorefrontBagsPage clickBagsButton() {
        WebElement vertexServicesButton = findBagsButton();

        if (vertexServicesButton != null) {
            vertexServicesButton.click();
        } else {
            String errorMsg = "Bags button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2StorefrontBagsPage.class);
    }

    /**
     * Locates the Bags Button
     *
     * @return the Bags Button
     */
    public WebElement findBagsButton() {
        WebElement mainButton = wait.waitForElementPresent(bagsButtonID);
        WebElement columnsButton = mainButton.findElement(columnsClass);
        WebElement sidebarButton = columnsButton.findElement(sidebarMainClass);
        WebElement widgetButton = sidebarButton.findElement(widgetClass);
        WebElement categoriesButton = widgetButton.findElement(categoriesClass);
        WebElement itemsButton = categoriesButton.findElement(itemsClass);

        List<WebElement> items = itemsButton.findElements(itemClass);
        WebElement bagsButton = null;
        WebElement itemTag = null;

        WebElement bagsButtonElem = element.selectElementByText(items, bagsButtonText);
        if (bagsButtonElem != null) {
            itemTag = element.getWebElement(bagsTag, bagsButtonElem);
        }

        return itemTag;
    }
}
