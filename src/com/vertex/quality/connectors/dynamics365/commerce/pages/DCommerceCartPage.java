package com.vertex.quality.connectors.dynamics365.commerce.pages;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.dynamics365.commerce.pages.base.DCommerceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Contains methods for interacting with cart page
 */
public class DCommerceCartPage extends DCommerceBasePage {

    protected By STORE_SEARCH_INPUT = By.xpath("//form[@class='ms-store-select__search-form']//input");
    protected By STORE_SEARCH_BUTTON = By.xpath("//form[@class='ms-store-select__search-form']//button");
    protected By PICK_UP_HERE_BUTTON = By.xpath("//div[@class='ms-store-select__location-line-pickup']//button");
    protected By IN_STORE_OPTION = By.xpath("//a[@aria-label='In Store pick up']");

    protected By REMOVE_BUTTON = By.xpath("//button[@title='Remove']");
    protected By PROMO_CODE_INPUT = By.className("msc-promo-code__input-box");
    protected By APPLY_PROMO_BUTTON = By.cssSelector("[title='Apply']");
    protected By CHECKOUT_BUTTON = By.cssSelector("[title='Checkout']");
    protected By GUEST_CHECKOUT_BUTTON = By.cssSelector("[title='Guest Checkout']");

    protected By TOTAL_AMOUNT = By.xpath("//p[@class='msc-order-summary__line-total']//span[@class='msc-price__actual']");

    public DCommerceCartPage(WebDriver driver )
    {
        super(driver);
    }


    /**
     * Click 'pick this up' button for product at given index
     * @param index
     */
    public void clickPickUpForProduct(String index) {
        refreshPage();
        By pickUpButtonLoc = By.xpath(String.format("(//button[text()='Pick this up'])[%s]", index));
        if (!element.isElementDisplayed(pickUpButtonLoc)) {
            scroll.scrollBottom();
        }
        WebElement pickUpButton = wait.waitForElementDisplayed(pickUpButtonLoc);
        actions.moveToElement(pickUpButton).click().perform();
        waitForPageLoad();
        jsWaiter.sleep(2000);

    }

    /**
     * Search for a store to pick up from
     * @param name
     */
    public void searchPickUpStore(String name) {
        wait.waitForElementDisplayed(STORE_SEARCH_INPUT);
        text.selectAllAndInputText(STORE_SEARCH_INPUT, name);

        wait.waitForElementDisplayed(STORE_SEARCH_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(STORE_SEARCH_BUTTON);

        waitForPageLoad();
    }

    /**
     * Click 'Pick up here' on results window from searching for a store
     */
    public void clickPickUpHere() {
        WebElement pickUpHereButton = wait.waitForElementDisplayed(PICK_UP_HERE_BUTTON);
        actions.moveToElement(pickUpHereButton).click().perform();
        waitForPageLoad();
    }

    /**
     * Click 'In Store pick up' in dropdown from clicking 'Pick up here'
     */
    public void clickInStorePickUp() {
        WebElement storePickUpOption = wait.waitForElementDisplayed(IN_STORE_OPTION);
        actions.moveToElement(storePickUpOption).click().perform();
        jsWaiter.sleep(2000);
    }

    /**
     * Click 'pick this up', searches for store, clicks 'pick up here' for store and 'in store pick up'
     */
    public void pickProductUpInStore(String storeName) {
        clickPickUpForProduct("1");
        searchPickUpStore(storeName);
        clickPickUpHere();
        clickInStorePickUp();
        waitForPageLoad();
    }

    /**
     * Remove product at given index from cart
     * @param index
     */
    public void removeProduct(String index) {
        By removeButtonLoc = By.xpath(String.format("(//button[@title='Remove'])[%s]", index));
        WebElement removeButton = wait.waitForElementPresent(removeButtonLoc);
        click.clickElementIgnoreExceptionAndRetry(removeButton);
    }

    /**
     * Remove all products from cart
     */
    public void removeAllProducts() {
        if (!element.isAnyElementDisplayed(REMOVE_BUTTON)) return;

        List<WebElement> removeButtons = wait.waitForAllElementsPresent(REMOVE_BUTTON);
        for(WebElement button : removeButtons) {
            try{
                actions.moveToElement(button).doubleClick().perform();
                waitForPageLoad();
            } catch (StaleElementReferenceException e) { }
        }

        refreshPage();
        waitForPageLoad();

        if(element.isAnyElementDisplayed(REMOVE_BUTTON)) {
            removeButtons = wait.waitForAllElementsPresent(REMOVE_BUTTON);
            removeButtons.forEach(button -> {
                actions.moveToElement(button).doubleClick().perform();
                waitForPageLoad();
            });
        }

        refreshPage();
    }

    /**
     * Get total amount of cart
     * @return total
     */
    public String getTotalAmount() {
        WebElement totalAmountField = wait.waitForElementDisplayed(TOTAL_AMOUNT);
        String total = parseNumericAmount(totalAmountField.getText());
        return  total;
    }

    /**
     * Enter promo code
     * @param code
     */
    public void enterPromoCode(String code) {
        WebElement promoCodeInput = wait.waitForElementDisplayed(PROMO_CODE_INPUT);
        text.selectAllAndInputText(promoCodeInput, code);
        applyPromoCode();
    }

    /**
     * Click apply button for promo code
     */
    public void applyPromoCode() {
        wait.waitForElementEnabled(APPLY_PROMO_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(APPLY_PROMO_BUTTON);
        jsWaiter.sleep(2000);
    }

    /**
     * Click on checkout button and return an instance of DCommerceCheckoutPage
     * @return checkoutPage
     */
    public DCommerceCheckoutPage navigateToCheckoutPage() {
        wait.waitForElementDisplayed(CHECKOUT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(CHECKOUT_BUTTON);

        waitForPageLoad();

        DCommerceCheckoutPage checkoutPage = new DCommerceCheckoutPage(driver);
        return checkoutPage;
    }

    /**
     * Click on checkout button and return an instance of DCommerceCheckoutPage
     * @return checkoutPage
     */
    public DCommerceCheckoutPage navigateToCheckoutPageAsGuest() {
        wait.waitForElementDisplayed(GUEST_CHECKOUT_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(GUEST_CHECKOUT_BUTTON);

        waitForPageLoad();

        DCommerceCheckoutPage checkoutPage = new DCommerceCheckoutPage(driver);
        return checkoutPage;
    }
}