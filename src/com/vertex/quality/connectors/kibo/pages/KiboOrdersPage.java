package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the order page from which you can
 * create a new order and access the maxine back office store
 * contains all the methods necessary to interact with the page
 *
 * @author osabha
 */
public class KiboOrdersPage extends VertexPage {
    protected By createNewOrderClass = By.className("x-btn-action-primary-toolbar-medium");
    protected By maxineStoreClass = By.cssSelector("span[class='x-menu-item-text']");

    public KiboOrdersPage(WebDriver driver) {
        super(driver);
    }

    /**
     * getter method to locate the new order button
     *
     * @return new order button WebElement
     */
    protected WebElement getNewOrderButton() {
        WebElement createOrder = null;
        String expectedText = "Create New Order";

        List<WebElement> createOrderContainers = wait.waitForAllElementsPresent(createNewOrderClass);
        createOrder = element.selectElementByText(createOrderContainers, expectedText);
        return createOrder;
    }

    /**
     * clicks the create new order button
     * which  drops the list of stores
     */
    public void clickCreateNewOrder() {
        WebElement createOrder = getNewOrderButton();
        createOrder.click();
        waitForPageLoad();
    }

    /**
     * getter method to locate the maxine store button from the stores list
     *
     * @return maxine store button WebElement
     */
    protected WebElement getMaxineStoreButton() {
        WebElement maxineStoreButton = null;
        String expectedText = "Maxine";
        List<WebElement> maxineStoreContainers = wait.waitForAllElementsPresent(maxineStoreClass);
        maxineStoreButton = element.selectElementByText(maxineStoreContainers, expectedText);

        return maxineStoreButton;
    }

    /**
     * getter method to locate the maxine store button from the stores list
     *
     * @return maxine store button WebElement
     */
    protected WebElement getMysticSportsButton() {
        WebElement maxineStoreButton = null;
        String expectedText = "Mystic Sports";
        List<WebElement> maxineStoreContainers = wait.waitForAllElementsPresent(maxineStoreClass);
        maxineStoreButton = element.selectElementByText(maxineStoreContainers, expectedText);

        return maxineStoreButton;
    }

    /**
     * uses the getter method to locate the maxine store button and then clicks on it
     *
     * @return new instance of the Kibo maxine page class
     */
    public KiboBackOfficeStorePage goToMaxineStore() {
        WebElement maxineStoreButton = getMaxineStoreButton();

        maxineStoreButton.click();

        return new KiboBackOfficeStorePage(driver);
    }


    /**
     * uses the getter method to locate the maxine store button and then clicks on it
     *
     * @return new instance of the Kibo maxine page class
     */
    public KiboBackOfficeStorePage goToMysticSportsStore() {
        waitForPageLoad();
        WebElement maxineStoreButton = getMysticSportsButton();
        maxineStoreButton.click();
        waitForPageLoad();
        return new KiboBackOfficeStorePage(driver);
    }
}
