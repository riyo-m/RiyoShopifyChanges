package com.vertex.quality.connectors.dynamics365.retail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Contains methods for interacting with Order Fulfillment pages (Orders to pick up/ship)
 */
public class DRetailOrderFulfillmentPage extends DRetailHomePage {

    protected By SORT_BAR_BUTTON = By.xpath("//button[@aria-label='Sort']");
    protected By ORDER_NUM_DESC_OPTION = By.xpath("//button[@aria-label='Order number - Descending']");
    protected By EDIT_BAR_BUTTON = By.xpath("//button[@aria-label='Edit']");
    protected By SHIP_BAR_BUTTON = By.xpath("//button[@aria-label='Ship']");
    protected By PICK_UP_BAR_BUTTON = By.xpath("//button[@aria-label='Pick up']");

    protected By QUANTITY_INPUT = By.xpath("//input[@aria-label='Enter quantity']");
    protected By PICKUP_BUTTON = By.xpath("//button[text()='Pick up']");
    protected By OK_BUTTON = By.xpath("//button[text()='OK']");

    protected By CLEAR_BUTTON = By.xpath("//button[@aria-label='Clear']");
    protected By ENTER_BUTTON = By.xpath("//button[@aria-label='Enter']");

    protected Actions actions = new Actions(driver);
    public DRetailOrderFulfillmentPage( WebDriver driver ) { super(driver); }

    /**
     * Sorts table by order number in descending order
     */
    public void sortOrders() {
        clickOnSortBarButton();

        if (!element.isElementDisplayed(ORDER_NUM_DESC_OPTION)) {
            clickOnSortBarButton();
        }

        wait.waitForElementDisplayed(ORDER_NUM_DESC_OPTION);
        click.clickElementIgnoreExceptionAndRetry(ORDER_NUM_DESC_OPTION);

        waitForDRetailPageLoad();
    }

    /**
     * Clicks on sort button in button bar
     */
    public void clickOnSortBarButton() {
        wait.waitForElementDisplayed(SORT_BAR_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SORT_BAR_BUTTON);
    }

    /**
     * Clicks on ship button in button bar
     */
    public void clickOnShipBarButton() {
        wait.waitForElementDisplayed(SHIP_BAR_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(SHIP_BAR_BUTTON);
    }

    /**
     * Clicks on pick up button in button bar
     */
    public void clickOnPickUpBarButton() {
        wait.waitForElementDisplayed(PICK_UP_BAR_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(PICK_UP_BAR_BUTTON);
    }

    /**
     * Clicks on the 'Pick up' now button for the specified product
     * @param productName
     */
    public void pickUpNowForProduct(String productName) {
        By pickUpNowButtonLoc = By.xpath(String.format("//div[text()='%s']/../../..//div[@class='pad12 clickable']", productName));

        wait.waitForElementDisplayed(pickUpNowButtonLoc);
        click.clickElementIgnoreExceptionAndRetry(pickUpNowButtonLoc);
    }

    /**
     * Clicks the 'Pick up' button after setting quantity for each item
     */
    public void clickPickUpForOrder() {
        wait.waitForElementDisplayed(PICKUP_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(PICKUP_BUTTON);
    }

    /**
     * Clicks the 'OK' button
     */
    public void clickOk() {
        wait.waitForElementDisplayed(OK_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(OK_BUTTON);
        waitForDRetailPageLoad();
    }
    /**
     * Clear field and enter quantity
     * @param quantity
     */
    public void enterQuantity(String quantity) {
        if (element.isElementDisplayed(CLEAR_BUTTON)) {
            click.clickElementIgnoreExceptionAndRetry(CLEAR_BUTTON);
        }

        wait.waitForElementDisplayed(QUANTITY_INPUT);
        text.selectAllAndInputText(QUANTITY_INPUT, quantity);
    }

    /**
     * Click on enter button in numpad
     */
    public void clickOnEnterButton() {
        wait.waitForElementDisplayed(ENTER_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(ENTER_BUTTON);

        waitForDRetailPageLoad();
    }

    /**
     * CLick on the line for the specified product under the specified order
     * @param orderNo
     * @param productName
     */
    public void clickOnLine(String orderNo, String productName) {
        String orderLineXPath = String.format("(//div[@aria-label='Order number %s']/../../../div[@class='dataListLine'])//div[@aria-label='Name %s']", orderNo, productName);

        WebElement lineRow = wait.waitForElementDisplayed(By.xpath(orderLineXPath));
        actions.moveToElement(lineRow).click().perform();

        waitForPageLoad();
    }


    /**
     * Clicks on 'Edit' button in button bar to show order of selected line
     * @return DRetailAllSalesOrderPage
     */
    public DRetailAllSalesOrderPage goToSelectedOrderPage() {
        wait.waitForElementDisplayed(EDIT_BAR_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(EDIT_BAR_BUTTON);

        waitForDRetailPageLoad();

        DRetailAllSalesOrderPage orderPage = new DRetailAllSalesOrderPage(driver);
        return orderPage;
    }

}
