package com.vertex.quality.connectors.orocommerce.components.storefront.base;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroProductSearchResultsPage;
import com.vertex.quality.connectors.orocommerce.pages.storefront.OroStoreFrontHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the middle bar of the base page header section
 * contains all the methods necessary to interact with it
 *
 * @author alewis
 */
public class pageHeaderMiddleBar extends VertexComponent {

    public pageHeaderMiddleBar(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    By quickAccessButtonsContLoc = By.className("quick-access-container");
    By middleBarContainerLoc = By.className("middlebar");
    By searchFieldLoc = By.xpath("//*[@placeholder='Search for a product']");
    By searchButtonLoc = By.xpath("//button[@aria-label='Start Searching']");

    /**
     * locates and returns the search for product field webElement
     *
     * @return search for product field webElement
     */
    public WebElement findSearchForProductField() {
        WebElement field = wait.waitForElementPresent(searchFieldLoc);

        return field;
    }

    /**
     * Pass product name as parameter and this method will search product from the top search bar
     * @param productName
     * @return
     */
    public OroProductSearchResultsPage searchForProduct(String productName) {
        waitForPageLoad();
        OroProductSearchResultsPage searchResultsPage;
        WebElement searchField = findSearchForProductField();
        OroStoreFrontHomePage homepage = new OroStoreFrontHomePage(driver);
        homepage.enterJavascriptText(productName, searchField);
        WebElement searchButton = wait.waitForElementPresent(searchButtonLoc);
        click.javascriptClick(searchButton);
        waitForPageLoad();
        searchResultsPage = initializePageObject(OroProductSearchResultsPage.class);
        return searchResultsPage;
    }
}
