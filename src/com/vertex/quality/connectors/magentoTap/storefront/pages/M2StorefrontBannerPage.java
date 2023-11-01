package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Banner page of Magento which accessible from the header bar
 *
 * @author Shivam.Soni
 */
public class M2StorefrontBannerPage extends MagentoStorefrontPage {

    /**
     * Parameterized constructor
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontBannerPage(WebDriver driver) {
        super(driver);
    }

    protected By storeLogoOnBanner = By.xpath(".//a[@aria-label='store logo']");
    protected By searchBox = By.id("search");
    protected By cartSymbol = By.xpath(".//a[contains(@class,'action showcart')]");
    protected By cartItemsCount = By.xpath(".//span[@class='count']");
    protected By cartDialogBox = By.id("minicart-content-wrapper");
    protected By closeCartDialogButton = By.xpath(".//button[@id='btn-minicart-close']");
    protected String deleteAllItem = "(.//*[@class='action delete'])";
    protected By deleteOkButton = By.xpath(".//button[normalize-space(.)='OK']");
    protected By proceedToCheckoutButton = By.id("top-cart-btn-checkout");
    protected By viewAndCartLink = By.className("action viewcart");

    /**
     * Navigates to Store Home Page by clicking on Store's logo from the banner
     */
    public void clickOnStoreLogo() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        click.javascriptClick(wait.waitForElementPresent(storeLogoOnBanner));
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Search Product from the Search Entire store search box
     *
     * @param searchItem name of Item to be searched.
     */
    public void searchFromEntireStoreSearch(String searchItem) {
        waitForSpinnerToBeDisappeared();
        WebElement search = wait.waitForElementPresent(searchBox);
        text.enterText(search, searchItem);
        text.pressEnter(search);
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Find the total No of Items from the cart
     *
     * @return Quantities available in the cart.
     */
    public int getCountOfItemsInTheCart() {
        int count = 0;
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(cartSymbol);
        wait.waitForElementPresent(cartDialogBox);
        if (element.isElementDisplayed(proceedToCheckoutButton)) {
            count = Integer.parseInt(text.getElementText(wait.waitForElementPresent(cartItemsCount)));
        }
        closeCartDialog();
        return count;
    }

    /**
     * Close the cart dialog window
     */
    public void closeCartDialog() {
        waitForSpinnerToBeDisappeared();
        wait.waitForElementPresent(cartSymbol);
        if (element.isElementDisplayed(closeCartDialogButton)) {
            click.moveToElementAndClick(closeCartDialogButton);
        }
    }

    /**
     * Navigates to the cart from the global banner
     */
    public void proceedToCheckout() {
        waitForSpinnerToBeDisappeared();
        WebElement cart = wait.waitForElementPresent(cartSymbol);
        if (!element.isElementDisplayed(proceedToCheckoutButton)) {
            click.moveToElementAndClick(cart);
        }
        click.moveToElementAndClick(wait.waitForElementPresent(proceedToCheckoutButton));
        waitForSpinnerToBeDisappeared();
    }

    /**
     * Delete all the items from the cart
     */
    public void clearTheCart() {
        waitForSpinnerToBeDisappeared();
        WebElement cart = wait.waitForElementPresent(cartSymbol);
        click.moveToElementAndClick(cart);
        wait.waitForElementPresent(cartDialogBox);
        if (getCountOfItemsInTheCart() > 0) {
            if (!element.isElementDisplayed(proceedToCheckoutButton)) {
                click.moveToElementAndClick(cart);
            }
            wait.waitForElementPresent(proceedToCheckoutButton);
            wait.waitForAllElementsPresent(By.xpath(deleteAllItem));
            int size = element.getWebElements(By.xpath(deleteAllItem)).size();
            for (int i = size; i >= 1; i--) {
                click.javascriptClick(wait.waitForElementPresent(By.xpath(deleteAllItem + "[" + i + "]")));
                waitForSpinnerToBeDisappeared();
                click.javascriptClick(wait.waitForElementPresent(deleteOkButton));
                new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(deleteAllItem + "[" + i + "]")));
                waitForPageLoad();
                waitForSpinnerToBeDisappeared();
            }
        }
        closeCartDialog();
    }

    /**
     * Navigates to Cart from Banner Page -> Cart Symbol -> Cart Dialog Box -> View And Edit cart.
     *
     * @return Cart Page.
     */
    public M2StorefrontShoppingCartPage clickViewAndEditCart() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(cartSymbol));
        wait.waitForElementPresent(cartDialogBox);
        click.javascriptClick(wait.waitForElementPresent(viewAndCartLink));
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        return new M2StorefrontShoppingCartPage(driver);
    }
}
