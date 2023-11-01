package com.vertex.quality.connectors.orocommerce.pages.storefront;

import com.vertex.quality.connectors.orocommerce.pages.base.OroStoreFrontBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * represents the home page shown after login
 * contains all the functions and components of this page
 *
 * @author alewis
 */
public class OroStoreFrontHomePage extends OroStoreFrontBasePage {
    public OroStoreFrontHomePage(final WebDriver driver) {
        super(driver);
    }

    By shoppingListHoverLoc = By.className("cart-widget-counter");
    By shoppingListDropDown = By.className("shopping-list-dropdown__details");

    /**
     * gets the current page title
     *
     * @return page title as a string
     */
    public String getPageTitle() {

        String pageTitle;
        pageTitle = driver.getTitle();
        return pageTitle;
    }

    /**
     * check if shopping list is empty.
     *
     * @return isEmpty value true or false.
     */
    public boolean isShoppingListEmpty() {
        String expectedText = "No Items";
        boolean isEmpty = false;
        WebElement shoppingListCont = wait.waitForElementPresent(shoppingListHoverLoc);
        click.javascriptClick(shoppingListCont);
        if (element.isElementDisplayed(shoppingListDropDown)) {
            String detailsText = text.getElementText(shoppingListDropDown);
            if (expectedText.equals(detailsText.trim())) {
                isEmpty = true;
            }
        } else {
            isEmpty = true;
        }
        click.javascriptClick(shoppingListCont);
        return isEmpty;
    }

    /**
     * goto shipping list with added cart
     *
     * @return shippingListPage with added cart items.
     */
    public OroShoppingListPage goToShoppingListPage() {
        waitForPageLoad();
        OroShoppingListPage shoppingListPage;
        click.javascriptClick(shoppingListHoverLoc);
        WebElement viewDetailsButton = wait.waitForElementDisplayed(By.className("shopping-list-dropdown__info"));
        click.javascriptClick(viewDetailsButton);
        waitForPageLoad();
        shoppingListPage = initializePageObject(OroShoppingListPage.class);
        return shoppingListPage;
    }

    /**
     * start new order after clearing existing cart items.
     *
     * @return homePage after adding cart items.
     */
    public OroStoreFrontHomePage startNewOrder() {
        OroStoreFrontHomePage homePage = initializePageObject(this.getClass());
        jsWaiter.sleep(2000);
        waitForPageLoad();
        boolean istEmpty = this.isShoppingListEmpty();
        if (!istEmpty) {
            OroShoppingListPage shoppingListPage = this.goToShoppingListPage();
            shoppingListPage.clearShoppingList();
            homePage = shoppingListPage.continueShopping();
        }
        waitForPageLoad();
        return homePage;
    }

    /**
     * process new order after clearing existing cart items.
     * Specifically this method is used for Oro 4.2 test execution
     *
     * @return store-front home page
     */
    public OroStoreFrontHomePage processForNewOrder() {
        OroStoreFrontHomePage homePage = initializePageObject(this.getClass());
        waitForPageLoad();
        boolean istEmpty = this.isShoppingListEmpty();
        if (!istEmpty) {
            OroShoppingListPage shoppingListPage = this.goToShoppingListPage();
            shoppingListPage.deleteShoppingList();
            homePage = shoppingListPage.continueShopping();
        }
        waitForPageLoad();
        return homePage;
    }

    /**
     * method to insert Javascript Text
     *
     * @param Value to be entered in enter text box.
     * @element element in which value needs to be entered.
     */
    public void enterJavascriptText(String Value, WebElement element) {
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("arguments[0].value='" + Value + "';", element);
    }

    /**
     * pressRobot enter for javascript click.
     */
    public void pressRobotEnter() {
        Robot pressEnter = null;
        try {
            pressEnter = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        pressEnter.keyPress(KeyEvent.VK_ENTER);
    }
}
