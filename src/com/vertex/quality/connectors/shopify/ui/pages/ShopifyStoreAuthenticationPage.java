package com.vertex.quality.connectors.shopify.ui.pages;

import com.vertex.quality.connectors.shopify.common.ShopifyDataUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Shopify store's authentication page - this will ask store password everytime to the user who are visiting the store.
 *
 * @author Shivam.Soni
 */
public class ShopifyStoreAuthenticationPage extends ShopifyPage {

    protected By storePasswordBox = By.xpath(".//div[normalize-space(.)='Enter store password']/following-sibling::input");
    protected By enterButton = By.xpath(".//button[text()='Enter']");
	static String storeKey = ShopifyDataUI.StoreData.VTX_QA_STORE_USER_KEY.text;
//    static String storeKey = ShopifyDataUI.StoreData.VTX_QA_STORE_KEY.text;

    /**
     * Parameterized constructor of the class that helps to initialize the object & to access the parents.
     *
     * @param driver Object of WebDriver
     */
    public ShopifyStoreAuthenticationPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters store's password & enters into the store
     *
     * @param storeKey value of the store key
     */
    public void enterIntoStoreByStorePassword(String storeKey) {
        if (driver.getCurrentUrl().endsWith("password")) {
            enterStoreKey(storeKey);
            clickOnEnterStore();
        }
    }

    /**
     * Enters store's password & enters into the store
     */
    public void enterIntoStoreByStorePassword() {
        if (driver.getCurrentUrl().endsWith("password")) {
            enterStoreKey(storeKey);
            clickOnEnterStore();
        }
    }

    /**
     * Enters Shopify store's key
     *
     * @param storeKey value of the store key
     */
    public void enterStoreKey(String storeKey) {
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(storePasswordBox), storeKey);
    }

    /**
     * Enters Shopify store's key
     */
    public void enterStoreKey() {
        waitForPageLoad();
        enterStoreKey(storeKey);
    }

    /**
     * Clicks on Enter button to enter to the store
     */
    public void clickOnEnterStore() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(enterButton));
        waitForPageLoad();
    }
}
