package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shopify store's login page that contains all the login related methods & variables
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreLoginPage extends ShopifyPage {

    static String storeUserEmail = ShopifyDataUI.StoreData.VTX_QA_STORE_USER.text;
    static String storeUserPassword = ShopifyDataUI.StoreData.VTX_QA_STORE_USER_KEY.text;

    protected By emailBox = By.id("CustomerEmail");
    protected By passwordBox = By.id("CustomerPassword");
    protected By signInButton = By.xpath(".//button[contains(text(),'Sign in')]");

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStoreLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters email id
     *
     * @param email value of email
     */
    private void enterEmail(String email) {
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(emailBox), email);
    }

    /**
     * Enters password
     *
     * @param pwd value of email
     */
    private void enterPassword(String pwd) {
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(passwordBox), pwd);
    }

    /**
     * Clicks on SignIn button
     */
    private void clickSignInButton() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(signInButton));
        waitForPageLoad();
    }

    /**
     * Log In to the store
     *
     * @param email email value
     * @param pwd   password value
     */
    public void loginToStore(String email, String pwd) {
        waitForPageLoad();
        enterEmail(email);
        enterPassword(pwd);
        clickSignInButton();
        waitForPageLoad();
    }

    /**
     * Log In to the store with default user.
     */
    public void loginToStore() {
        waitForPageLoad();
        loginToStore(storeUserEmail, storeUserPassword);
        waitForPageLoad();
    }
}
