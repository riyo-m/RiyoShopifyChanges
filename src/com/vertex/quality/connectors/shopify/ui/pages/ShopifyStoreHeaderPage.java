package com.vertex.quality.connectors.shopify.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Shopify store's banner page - contains methods & variables of banner page
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreHeaderPage extends ShopifyPage {

    protected By storeLogo = By.cssSelector(".header__heading-link");
    protected By headerSearchIcon = By.xpath(".//*[@class='header__search']");
    protected By headerSearchBox = By.id("Search-In-Modal");
    protected By accountLoginIcon = By.xpath(".//*[@class='header__search']/following-sibling::a[@href='/account/login']");
    protected By cartIcon = By.xpath("(.//a[@href='/cart'])[1]");
    protected By cartItemCount = By.xpath(".//div[@class='cart-count-bubble']//span[1]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStoreHeaderPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Clicks on Store Logo & navigates to Store Home page
     */
    public void gotoStoreHomePage() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(storeLogo));
        waitForPageLoad();
    }

    /**
     * Clicks on Account Login link
     */
    public void clickOnLoginAccount() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(accountLoginIcon));
        waitForPageLoad();
    }

    /**
     * Clicks on Search Icon
     */
    public void clickOnSearch() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(headerSearchIcon));
        waitForPageLoad();
    }

    /**
     * Search the item in the entire store from header
     *
     * @param searchValue value of the search item
     * @return Search result page
     */
    public ShopifyStoreSearchResultPage searchInEntireStore(String searchValue) {
        waitForPageLoad();
        WebElement search = wait.waitForElementPresent(headerSearchBox);
        text.enterText(search, searchValue);
        text.pressEnter(search);
        waitForPageLoad();
        return new ShopifyStoreSearchResultPage(driver);
    }

    /**
     * Gets the item count from the cart
     *
     * @return count of cart items
     */
    public int getCartItemCount() {
        int count = 0;
        waitForPageLoad();
        wait.waitForElementPresent(cartIcon);
        if (element.isElementDisplayed(cartItemCount)) {
            count = Integer.parseInt(text.getElementText(cartIcon));
        }
        return count;
    }

    /**
     * Clicks on the cart Icon
     *
     * @return Cart page
     */
    public ShopifyStoreCartPage clickOnCartIcon() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(cartIcon));
        waitForPageLoad();
        return new ShopifyStoreCartPage(driver);
    }
}
