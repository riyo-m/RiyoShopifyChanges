package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * This class represents the header pane for the back office Maxine store
 * contains all the methods necessary to interact with the header pane elements
 *
 * @author osabha
 */
public class KiboBackOfficeStoreHeaderPane extends VertexComponent {
    protected By createNewOrderClass = By.className("x-btn-action-primary-toolbar-medium");
    protected By submitButtonClass = By.className("x-btn-action-primary-toolbar-medium-noicon");
    protected By cancelOrderButton = By.xpath(".//span[text()='Cancel']");

    public KiboBackOfficeStoreHeaderPane(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    /**
     * Finds the New Order Button and returns it
     *
     * @return new order button element
     */
    protected WebElement getNewOrderButton() {
        WebElement createOrder = null;
        final String expectedText = "Create New Order";

        List<WebElement> createOrderContainers = wait.waitForAllElementsDisplayed(createNewOrderClass);
        createOrder = element.selectElementByText(createOrderContainers, expectedText);
        return createOrder;
    }

    /**
     * uses the getter method to find the new order button element and then clicks on it
     */
    public void clickCreateNewOrder() {
        WebElement createOrder = getNewOrderButton();
        createOrder.click();
    }

    /**
     * gets the submit order button element
     *
     * @return submit order button element
     */
    public WebElement getSubmitOrderButton() {
        WebElement submitButton = null;
        final String expectedText = "Submit Order";
        List<WebElement> submitButtonClasses = driver.findElements(submitButtonClass);
        submitButton = element.selectElementByText(submitButtonClasses, expectedText);

        return submitButton;
    }

    /**
     * Uses the getter method to find the submit order button element
     * then clicks on it
     */
    public void clickSubmitOrder() {
        WebElement submitButton = getSubmitOrderButton();
        click.javascriptClick(submitButton);
        waitUntilElementInvisible(submitButton);
    }

    /**
     * Cancels order before checking-out
     */
    public void cancelOrder() {
        List<WebElement> cancelButton = element.getWebElements(cancelOrderButton);
        click.moveToElementAndClick(cancelButton.get(0));
        waitForPageLoad();
    }

    /**
     * Helper method to wait 5 seconds until a web element is invisible
     * ignoring all stale element exceptions
     *
     * @param element element to be invisible
     */
    public void waitUntilElementInvisible(WebElement element) {
        new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }
}

