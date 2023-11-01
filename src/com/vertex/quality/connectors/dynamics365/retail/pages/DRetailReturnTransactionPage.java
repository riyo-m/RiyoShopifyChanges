package com.vertex.quality.connectors.dynamics365.retail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Contains methods for interacting with Order Fulfillment pages (Orders to pick up/ship)
 */
public class DRetailReturnTransactionPage extends DRetailOrderFulfillmentPage {

    protected By RETURN_BAR_BUTTON = By.xpath("(//button[contains(@data-ax-bubble,'returnButton')])[1]");
    protected By SEARCH_INPUT = By.xpath("//input[@aria-label='Scan or enter a number']");
    protected By CLEAR_BUTTON = By.xpath("//button[@aria-label='Clear' and @tabindex='0']");

    protected By RETURN_QUANTITY_BUTTON = By.xpath("//div[contains(@data-bind,'Quantity')]/following-sibling::div");
    protected By RETURN_REASON_BUTTON = By.xpath("//div[text()='Select a reason']/following-sibling::div[2]");
    protected By OBSOLETE_REASON = By.xpath("//div[text()='Obsolete']");

    protected Actions actions = new Actions(driver);
    public DRetailReturnTransactionPage(WebDriver driver ) { super(driver); }


    /**
     * Clear field and enter order number
     * @param orderNo
     */
    public void searchOrder(String orderNo) {
        if (element.isElementDisplayed(CLEAR_BUTTON)) {
            click.clickElementIgnoreExceptionAndRetry(CLEAR_BUTTON);
        }

        wait.waitForElementDisplayed(SEARCH_INPUT);
        text.selectAllAndInputText(SEARCH_INPUT, orderNo);

        clickOnEnterButton();
    }

    /**
     * Clicks on return button in button bar
     */
    public void clickOnReturnBarButton() {
        wait.waitForElementDisplayed(RETURN_BAR_BUTTON);
        click.clickElementIgnoreExceptionAndRetry(RETURN_BAR_BUTTON);
    }

    /**
     * CLick on the line for the specified product
     * @param productName
     */
    public void clickOnLine(String productName) {
        By lineLoc = By.xpath(String.format("//div[@aria-label='PRODUCT %s']/../../../..", productName));

        wait.waitForElementPresent(lineLoc);
        click.clickElementIgnoreExceptionAndRetry(lineLoc);
        waitForPageLoad();
    }

    /**
     * Enter return quantity
     * @param quantity
     */
    public void enterReturnQuantity(String quantity) {
        WebElement returnQuantityButton = wait.waitForElementDisplayed(RETURN_QUANTITY_BUTTON);
        actions.moveToElement(returnQuantityButton).click().perform();

        waitForPageLoad();

        enterQuantity(quantity);
        clickOnEnterButton();
    }

    /**
     * Select a return reason
     */
    public void selectReturnReason() {
        WebElement returnReasonButton = wait.waitForElementDisplayed(RETURN_REASON_BUTTON);
        actions.moveToElement(returnReasonButton).click().perform();

        wait.waitForElementDisplayed(OBSOLETE_REASON);
        click.clickElementIgnoreExceptionAndRetry(OBSOLETE_REASON);

        waitForDRetailPageLoad();
    }



}
