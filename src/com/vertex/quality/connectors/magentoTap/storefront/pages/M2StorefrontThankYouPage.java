package com.vertex.quality.connectors.magentoTap.storefront.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the Thank You Page
 *
 * @author alewis
 */
public class M2StorefrontThankYouPage extends MagentoStorefrontPage {
    protected By checkoutSuccessClass = By.className("checkout-success");
    protected By orderNumber = By.className("order-number");
    protected By orderNumberClass = By.className("order-id");
    protected By spanTag = By.tagName("span");
    protected By strongTag = By.tagName("strong");

    By maskClass = By.className("loading-mask");

    protected String continueShop = "Continue Shopping";

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public M2StorefrontThankYouPage(WebDriver driver) {
        super(driver);
    }

    /**
     * When there are multiple order numbers on the page,
     * such as when there are multiple delivery addresses,
     * locate all of them
     *
     * @return order numbers
     */
    protected List<String> getMultipleOrderNumbers() {
        List<WebElement> allOrderNumbers = wait.waitForAllElementsDisplayed(orderNumberClass);

        List<String> orderNumberStrings = new ArrayList<>();

        for (WebElement orderNumber : allOrderNumbers) {
            String number = orderNumber
                    .getText()
                    .trim();
            orderNumberStrings.add(number);
        }

        return orderNumberStrings;
    }

    /**
     * gets the order number for guest
     *
     * @return a String of the order number
     */
    public String getOrderNumberGuest() {
        WebElement checkoutSuccess = wait.waitForElementPresent(checkoutSuccessClass);
        List<WebElement> spans = wait.waitForAllElementsPresent(spanTag, checkoutSuccess);
        String orderNumber = null;

        for (WebElement span : spans) {
            String spanText = span.getText();

            if (spanText != null && !spanText.equals(continueShop)) {
                orderNumber = spanText;
            }
        }

        return orderNumber;
    }

    /**
     * gets the order number
     *
     * @return a String of the order number
     */
    public String getOrderNumber() {
        wait.waitForElementNotDisplayed(maskClass);
        WebElement checkoutSuccess = wait.waitForElementPresent(checkoutSuccessClass);
        waitForPageLoad();
        List<WebElement> spans = wait.waitForAllElementsPresent(spanTag, checkoutSuccess);
        String orderNumber = null;

        for (WebElement span : spans) {
            String spanText = span.getText();

            if (spanText != null && !spanText.equals(continueShop)) {
                orderNumber = spanText;
            }
        }

        List<WebElement> strongs = wait.waitForAllElementsPresent(strongTag, checkoutSuccess);

        for (WebElement strong : strongs) {
            String spanText = strong.getText();

            if (spanText != null && !spanText.equals(continueShop)) {
                orderNumber = spanText;
            }
        }

        return orderNumber;
    }

    /**
     * Gets an order number from the list
     * when there are multiple order numbers on the page
     *
     * @param index index value
     * @return the order number
     */
    public String getOrderNumberFromList(int index) {
        List<String> allNumbers = getMultipleOrderNumbers();

        String orderNumber = allNumbers.get(index);

        return orderNumber;
    }

    /**
     * clicks Order Number Button
     *
     * @return Order Number Page
     */
    public M2StorefrontOrderNumberPage clickOrderNumber() {
        waitForPageLoad();
        WebElement orderNumberButton = wait.waitForElementPresent(orderNumber);
        orderNumberButton.click();

        return initializePageObject(M2StorefrontOrderNumberPage.class);
    }
}