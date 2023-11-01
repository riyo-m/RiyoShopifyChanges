package com.vertex.quality.connectors.orocommerce.pages.storefront;

import com.vertex.quality.connectors.orocommerce.pages.base.OroStoreFrontBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author alewis
 */
public class OroShoppingListPage extends OroStoreFrontBasePage {
    public OroShoppingListPage(WebDriver driver) {
        super(driver);
    }

    By shoppingListHoverLoc = By.className("cart-widget-counter");
    By shoppingListDropDown = By.className("shopping-list-dropdown__details");
    By deleteShoppingListButtonLoc = By.cssSelector("a[title='Delete Shopping List']");
    By shoppingListActions = By.xpath(".//button[@aria-label='Actions of Shopping List']");
    By shoppingListActionMenu = By.xpath(".//div[@class='dropdown-menu show']");
    By deleteShoppingList = By.xpath(".//button[@title='Delete']");
    By deleteConfirmPopup = By.cssSelector("modal-content");
    By deleteConfirmOk = By.cssSelector("button[class~='ok']");

    /**
     * clear shopping list cart to place new cart items.
     */
    public void clearShoppingList() {
        if (element.isElementPresent(deleteShoppingListButtonLoc)) {
            WebElement deleteShoppingListButton = wait.waitForElementDisplayed(deleteShoppingListButtonLoc);
            click.javascriptClick(deleteShoppingListButton);
            WebElement confirmDeleteButton = wait.waitForElementDisplayed(deleteConfirmOk);
            click.javascriptClick(confirmDeleteButton);
        } else {
            deleteShoppingList();
        }
    }

    /**
     * Deletes shopping list if it is present.
     * This method is specifically for Oro 4.2 version.
     * Use this method only while executing Oro test-cases on Oro 4.2 version
     */
    public void deleteShoppingList() {
        waitForPageLoad();
        WebElement actions = wait.waitForElementPresent(shoppingListActions);
        if (!element.isElementPresent(shoppingListActionMenu)) {
            click.moveToElementAndClick(actions);
        }
        WebElement deleteList = wait.waitForElementPresent(deleteShoppingList);
        click.moveToElementAndClick(deleteList);
        WebElement confirmDeleteButton = wait.waitForElementDisplayed(deleteConfirmOk);
        click.javascriptClick(confirmDeleteButton);
        waitForPageLoad();
    }

    /**
     * click on continue shopping to proceed with the selected cart items.
     *
     * @return homepage after selecting items.
     */
    public OroStoreFrontHomePage continueShopping() {
        waitForPageLoad();
        WebElement homePageLogo = wait.waitForElementDisplayed(By.className("logo"));
        OroStoreFrontHomePage homePage;
        click.javascriptClick(homePageLogo);
        jsWaiter.sleep(1000);
        waitForPageLoad();
        homePage = initializePageObject(OroStoreFrontHomePage.class);
        return homePage;
    }

    /**
     * clicks on create order button.
     */
    public OroCheckoutPage clickCreateOrder() {
        waitForPageLoad();
        OroCheckoutPage checkoutPage;
        WebElement createOrderButton = wait.waitForElementPresent(
                By.xpath("(//*[contains(text(),'Create Order')])[2]"));
        click.javascriptClick(createOrderButton);
        wait.waitForElementPresent(By.xpath(".//label[text()='First name']/following-sibling::input"));
        waitForPageLoad();
        checkoutPage = initializePageObject(OroCheckoutPage.class);
        return checkoutPage;
    }

    /**
     * Clicks on create order button to process further
     * This method is used only for Oro 4.2 version
     *
     * @return checkout page
     */
    public OroCheckoutPage createOrder() {
        waitForPageLoad();
        WebElement createOrderButton = wait.waitForElementPresent(By.xpath("//*[contains(text(),'Create Order')]"));
        click.moveToElementAndClick(createOrderButton);
        wait.waitForElementPresent(By.xpath(".//label[text()='First name']/following-sibling::input"));
        waitForPageLoad();
        return new OroCheckoutPage(driver);
    }
}
