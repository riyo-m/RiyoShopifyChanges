package com.vertex.quality.connectors.sitecorexc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Cart page of SitecoreXC
 *
 * @author Shivam.Soni
 */
public class SitecoreXCCartPage extends SitecoreXCPage {

    protected By orderProcessStepsBar = By.xpath(".//ul[@class='steps']");
    protected By lineItemDetail = By.xpath(".//div[@class='shopping-cart-lines']");
    protected String quantityBox = ".//div[normalize-space(.)='<<text_replace>>']/parent::div/following-sibling::div//input";
    protected String deleteLineItemButton = ".//div[normalize-space(.)='<<text_replace>>']/parent::div/following-sibling::div//a[@class='remove-line']";
    protected By discountCodeBox = By.xpath(".//input[@class='promo-code-input']");
    protected By addDiscountCodeButton = By.xpath(".//button[@class='add-promo-code-button']");
    protected By addedDiscountCode = By.xpath(".//span[@class='added-promotion-code']");
    protected String removeDiscountCodeButton = "(.//a[text()='Remove'])";
    protected By checkoutButton = By.xpath(".//a[text()='Checkout']");
    protected String couponSpecificRemove = ".//span[text()='<<text_remove>>']/parent::td/following-sibling::td/a";

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCCartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Updates the quantity of product
     *
     * @param productName name of product which qty should be updated
     * @param qty         value of qty
     */
    public void updateProductQuantity(String productName, String qty) {
        waitForPageLoad();
        wait.waitForElementPresent(orderProcessStepsBar);
        wait.waitForElementPresent(lineItemDetail);
        WebElement quantity = wait.waitForElementPresent(By.xpath(quantityBox.replace("<<text_replace>>", productName)));
        click.performDoubleClick(quantity);
        text.enterText(quantity, qty, false);
        text.pressTab(quantity);
        waitForPageLoad();
    }

    /**
     * Deletes the product/ line item from the cart
     *
     * @param productName name of product which is to be deleted
     */
    public void deleteLineItem(String productName) {
        waitForPageLoad();
        wait.waitForElementPresent(orderProcessStepsBar);
        wait.waitForElementPresent(lineItemDetail);
        WebElement delete = wait.waitForElementPresent(By.xpath(deleteLineItemButton.replace("<<text_replace>>", productName)));
        click.moveToElementAndClick(delete);
        waitForPageLoad();
    }

    /**
     * Adds discount code
     *
     * @param code discount code to be added
     */
    public void addDiscountCode(String code) {
        waitForPageLoad();
        wait.waitForElementPresent(discountCodeBox);
        text.enterText(discountCodeBox, code);
        wait.waitForElementPresent(addDiscountCodeButton);
        click.moveToElementAndClick(addDiscountCodeButton);
    }

    /**
     * Validates applied discount code
     *
     * @param code discount code to be added
     * @return true or false based on condition
     */
    public boolean verifyAppliedDiscountCode(String code) {
        boolean isMatched = false;
        waitForPageLoad();
        wait.waitForElementPresent(discountCodeBox);
        wait.waitForElementPresent(addDiscountCodeButton);
        wait.waitForElementPresent(addedDiscountCode);
        List<WebElement> allCodes = element.getWebElements(addedDiscountCode);
        for (WebElement discount : allCodes) {
            if (code.equalsIgnoreCase(text.getElementText(discount))) {
                isMatched = true;
                break;
            }
        }
        return isMatched;
    }

    /**
     * Checks whether any Discount Code is applied or not?
     *
     * @return true or false based on condition match
     */
    public boolean checkIfAnyDiscountCouponApplied() {
        waitForPageLoad();
        wait.waitForElementPresent(discountCodeBox);
        wait.waitForElementPresent(addDiscountCodeButton);
        return element.isElementDisplayed(addedDiscountCode) && element.isElementDisplayed(By.xpath(removeDiscountCodeButton));
    }

    /**
     * Removes the applied discount code
     */
    public void removeAllDiscountCodes() {
        waitForPageLoad();
        if (checkIfAnyDiscountCouponApplied()) {
            String removeCouponXpath;
            wait.waitForElementPresent(discountCodeBox);
            wait.waitForElementPresent(addDiscountCodeButton);
            wait.waitForElementPresent(addedDiscountCode);
            wait.waitForAllElementsPresent(By.xpath(removeDiscountCodeButton));
            int size = element.getWebElements(By.xpath(removeDiscountCodeButton)).size();
            for (int i = size; i > 0; i--) {
                removeCouponXpath = removeDiscountCodeButton + "[" + i + "]";
                click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(removeCouponXpath)));
                new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(removeCouponXpath)));
                waitForPageLoad();
            }
            waitForPageLoad();
        }
    }

    /**
     * Removes the applied discount code
     *
     * @param discountCode Discount/ Coupon code which is to be removed
     */
    public void removeDiscountCode(String discountCode) {
        waitForPageLoad();
        wait.waitForElementPresent(discountCodeBox);
        wait.waitForElementPresent(addDiscountCodeButton);
        wait.waitForElementPresent(addedDiscountCode);
        wait.waitForElementPresent(By.xpath(removeDiscountCodeButton));
        click.moveToElementAndClick(By.xpath(couponSpecificRemove.replace("<<text_replace>>", discountCode)));
        waitForPageLoad();
    }

    /**
     * Clicks on checkout button
     */
    public void clickCheckout() {
        waitForPageLoad();
        wait.waitForElementPresent(checkoutButton);
        click.moveToElementAndClick(checkoutButton);
        waitForPageLoad();
    }
}
