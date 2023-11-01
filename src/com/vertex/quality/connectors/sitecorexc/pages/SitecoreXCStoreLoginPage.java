package com.vertex.quality.connectors.sitecorexc.pages;

import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This page contains all methods to interact with UI Page of SitecoreXC's store login page.
 *
 * @author Shivam.Soni
 */
public class SitecoreXCStoreLoginPage extends SitecoreXCPage {

    protected By signInUsernameBox = By.xpath(".//label[@for='UserName']//following-sibling::input");
    protected By signInPasswordBox = By.xpath(".//label[@for='Password']//following-sibling::input");
    protected By signInButton = By.xpath(".//button[@id='SignInButton']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCStoreLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Loads SitecoreXC Storefront's login page
     */
    public void loadStoreLoginPage() {
        driver.get(SiteCoreXCData.STORE_LOGIN_URL_30.data);
    }

    /**
     * Sign-In to the SitecoreXC store
     */
    public void signInToStoreWithDefaultUser() {
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(signInUsernameBox), SiteCoreXCData.STORE_LOGIN_USERNAME.data);
        text.enterText(wait.waitForElementPresent(signInPasswordBox), SiteCoreXCData.STORE_LOGIN_PASSWORD.data);
        click.moveToElementAndClick(wait.waitForElementPresent(signInButton));
        waitForPageLoad();
        acceptOrRejectPrivacyWarning(true);
    }
}
