package com.vertex.quality.connectors.magentoTap.storefront.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.magentoTap.storefront.pages.M2StorefrontBagsPage;
import com.vertex.quality.connectors.magentoTap.storefront.pages.M2StorefrontGearPage;
import com.vertex.quality.connectors.magentoTap.storefront.pages.M2StorefrontHomePage;
import com.vertex.quality.connectors.magentoTap.storefront.pages.M2StorefrontShoppingCartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Navigation Panel on top of page
 *
 * @author alewis
 */
public class StorefrontNavigationPanel extends VertexComponent {
    protected By gearButtonID = By.id("ui-id-6");
    protected By logoClass = By.className("logo");
    protected By miniCart = By.id("minicart-content-wrapper");

    protected By searchId = By.id("search");

    protected By shoppingCartFullClass = By.className("details-qty");
    protected By shoppingCartPopupId = By.id("minicart-content-wrapper");
    protected By shoppingCartButtonClass = By.className("showcart");
    protected By emptyShoppingCart = By.xpath("//strong[@class='subtitle empty'][contains(text(),'You have no items in your shopping cart.')]");
    protected By shoppingCartDeleteClass = By.className("delete");
    protected By shoppingCartViewAndEditClass = By.className("viewcart");
    protected By acceptClass = By.className("action-accept");
    By maskClass = By.className("loading-mask");
    By cartItems = By.xpath("//div[@class='minicart-wrapper']//*//span[@class='counter qty']");

    protected By confirmationPopupClass = By.className("modal-footer");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver
     */
    public StorefrontNavigationPanel(WebDriver driver, VertexPage parent) {
        super(driver, parent);
    }

    /**
     * clicks the Gear Button on navigation panel
     *
     * @return the Gear Page
     */
    public M2StorefrontGearPage clickGearButton() {
        WebElement vertexServicesButton = findGearButton();

        if (vertexServicesButton != null) {
            vertexServicesButton.click();
        } else {
            String errorMsg = "Gear button not found";
            throw new RuntimeException(errorMsg);
        }

        return initializePageObject(M2StorefrontGearPage.class);
    }

    /**
     * Locates the Cache Management Button on the navigation panel
     *
     * @return the Cache Management Button
     */
    protected WebElement findGearButton() {
        WebElement gearButton = wait.waitForElementPresent(gearButtonID);

        return gearButton;
    }

    /**
     * click Logo button
     */
    public void clickLogoButton() {
        waitForPageLoad();

        WebElement logo = wait.waitForElementPresent(logoClass);

        logo.click();
    }

    /**
     * clears shopping cart from currently inside shopping cart
     */
    public void clearShoppingCartFromSC() {
        waitForPageLoad();
        clickLogoButton();
        clearShoppingCart();
    }

    /**
     * Checks shopping cart is empty or not
     *
     * @return if shopping cart is empty returns True or else returns false
     */
    public boolean verifyShoppingCart() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(miniCart);
        boolean emptyCart = element.isElementDisplayed(emptyShoppingCart);
        return emptyCart;
    }

    /**
     * Checks shopping cart is empty or not
     *
     * @return if shopping cart is empty returns True or else returns false
     */
    public boolean verifyCartItems() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        wait.waitForElementDisplayed(miniCart);
        boolean emptyCart = element.isElementDisplayed(cartItems);
        return emptyCart;
    }

    /**
     * clears shopping cart before test
     */
    public void clearShoppingCart() {
        waitForPageLoad();
        wait.waitForElementNotDisplayed(maskClass);
        parent.refreshPage();
        wait.waitForElementEnabled(shoppingCartButtonClass);
        WebElement showCartIcon = wait.waitForElementDisplayed(shoppingCartButtonClass);
        click.clickElement(showCartIcon);
        wait.waitForElementDisplayed(miniCart);
        if (verifyShoppingCart()) {
            System.out.println("The shopping is empty");
        } else {
            waitForPageLoad();
            wait.waitForElementDisplayed(miniCart);
            List<WebElement> shoppingCartFull = wait.waitForAllElementsPresent(shoppingCartFullClass);
            if (shoppingCartFull.size() >= 1) {
                for (WebElement deleteClass : shoppingCartFull) {
                    waitForPageLoad();
                    deleteClass = wait.waitForElementEnabled(shoppingCartDeleteClass);

                    deleteClass.click();

                    waitForPageLoad();
                    WebElement accept = wait.waitForElementDisplayed(acceptClass);
                    accept.click();
                }
            }
        }
    }

    /**
     * Opens the mini cart via the popup and if it is not already empty,
     * delete all items
     * Waits for confirmation window to close to avoid errors
     */
    public void emptyCartAndWait() {
        WebElement cartIcon = wait.waitForElementEnabled(shoppingCartButtonClass);

        cartIcon.click();

        WebElement cartPopup = wait.waitForElementDisplayed(shoppingCartPopupId);

        String popupText = cartPopup.getAttribute("innerText");

        if (!popupText.contains("You have no items in your shopping cart.")) {
            List<WebElement> deleteList = wait.waitForAllElementsDisplayed(shoppingCartDeleteClass, cartPopup);

            for (WebElement deleteBtn : deleteList) {
                wait.waitForElementEnabled(deleteBtn);
                click.clickElement(deleteBtn);
                WebElement confirmationPopup = wait.waitForElementDisplayed(confirmationPopupClass);
                WebElement okButton = wait.waitForElementEnabled(acceptClass, confirmationPopup);
                click.clickElement(okButton);
                wait.waitForElementNotDisplayedOrStale(confirmationPopup, 5);
            }
        }
    }

    /**
     * clears the second shopping cart item after clearing the first
     */
    public void clearSecondShoppingCartItem() {
        WebElement showCartIcon = wait.waitForElementPresent(shoppingCartButtonClass);
        showCartIcon.click();
        showCartIcon.click();

        List<WebElement> shoppingCartFull = wait.waitForAllElementsPresent(shoppingCartFullClass);

        if (shoppingCartFull.size() >= 1) {
            WebElement deleteClass = wait.waitForElementPresent(shoppingCartDeleteClass);

            deleteClass.click();

            waitForPageLoad();
            WebElement accept = wait.waitForElementDisplayed(acceptClass);
            accept.click();
        }
    }

    /**
     * clicks the view and edit cart button on the cart popup\
     *
     * @return shopping cart page
     */
    public M2StorefrontShoppingCartPage clickViewAndEditCart() {
        WebElement cartIcon = wait.waitForElementEnabled(shoppingCartButtonClass);

        cartIcon.click();

        WebElement cartPopup = wait.waitForElementDisplayed(shoppingCartPopupId);

        WebElement viewAndEditButton = wait.waitForElementEnabled(shoppingCartViewAndEditClass, cartPopup);

        viewAndEditButton.click();

        M2StorefrontShoppingCartPage shoppingCartPage = initializePageObject(M2StorefrontShoppingCartPage.class);

        return shoppingCartPage;
    }

    /**
     * Search storefront product
     *
     * @param prodText product name
     * @return M2StorefrontBagsPage
     */
    public M2StorefrontBagsPage searchStorefrontForProd(String prodText) {
        M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);
        homePage.refreshPage();

        waitForPageLoad();
        homePage.refreshPage();
        WebElement search = wait.waitForElementDisplayed(searchId);

        text.setTextFieldCarefully(search, prodText);
        text.pressEnter(search);

        return initializePageObject(M2StorefrontBagsPage.class);
    }
}