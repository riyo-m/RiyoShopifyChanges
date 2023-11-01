package com.vertex.quality.connectors.sitecorexc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Banner page of SitecoreXC which accessible from the header bar
 *
 * @author Shivam.Soni
 */
public class SitecoreXCStoreBannerPage extends SitecoreXCPage {

    protected By storeLogoOnBanner = By.xpath(".//a[@title='Large Logo']");
    protected By searchBox = By.xpath(".//input[@class='search-box-input tt-input']");
    protected By cartSymbol = By.xpath(".//div[@class='basket']");
    protected By cartItemsCount = By.xpath(".//p[@class='cart-items-count']");
    protected By cartDialogBox = By.className("minicart");
    protected String deleteAllItem = "(.//*[@class='minicart-delete']//a)";
    protected By checkoutButton = By.className("checkout-button");
    protected By viewCartButton = By.className("view-cart-button");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCStoreBannerPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigates to Store Home Page by clicking on Store's logo from the banner
     */
    public void clickOnStoreLogo() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(storeLogoOnBanner));
        waitForPageLoad();
    }

    /**
     * Search Product from the Search Entire store search box
     *
     * @param searchItem name of Item to be searched.
     */
    public void searchFromEntireStoreSearch(String searchItem) {
        waitForPageLoad();
        WebElement search = wait.waitForElementPresent(searchBox);
        text.enterText(search, searchItem);
        text.pressEnter(search);
        waitForPageLoad();
    }

    /**
     * Find the total No of Items from the cart
     *
     * @return Quantities available in the cart.
     */
    public int getCountOfItemsInTheCart() {
        int count = 0;
        waitForPageLoad();
        wait.waitForElementPresent(cartSymbol);
        if (element.isElementDisplayed(cartItemsCount)) {
            count = Integer.parseInt(text.getElementText(wait.waitForElementPresent(cartItemsCount)));
        }
        return count;
    }

    /**
     * Navigates to the checkout from the global banner
     */
    public void proceedToCheckout() {
        waitForPageLoad();
        WebElement cart = wait.waitForElementPresent(cartSymbol);
        wait.waitForElementPresent(cartDialogBox);
        if (!element.isElementDisplayed(checkoutButton)) {
            click.moveToElementAndClick(cart);
        }
        click.moveToElementAndClick(wait.waitForElementPresent(checkoutButton));
        waitForPageLoad();
    }

    /**
     * Delete all the items from the cart
     */
    public void clearTheCart() {
        waitForPageLoad();
        WebElement cart = wait.waitForElementPresent(cartSymbol);
        click.moveToElementAndClick(cart);
        wait.waitForElementPresent(cartDialogBox);
        if (getCountOfItemsInTheCart() > 0) {
            if (!element.isElementDisplayed(checkoutButton)) {
                click.moveToElementAndClick(cart);
            }
            wait.waitForElementPresent(checkoutButton);
            wait.waitForAllElementsPresent(By.xpath(deleteAllItem));
            int size = element.getWebElements(By.xpath(deleteAllItem)).size();
            for (int i = size; i >= 1; i--) {
                click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(deleteAllItem + "[" + i + "]")));
                waitForLoaderToBeDisappeared();
                waitForPageLoad();
            }
        }
    }

    /**
     * Navigates to the cart from the global banner
     */
    public void clickViewCart() {
        waitForPageLoad();
        WebElement cart = wait.waitForElementPresent(cartSymbol);
        wait.waitForElementPresent(cartDialogBox);
        if (!element.isElementDisplayed(viewCartButton)) {
            click.moveToElementAndClick(cart);
        }
        click.moveToElementAndClick(wait.waitForElementPresent(viewCartButton));
        waitForPageLoad();
    }
}
