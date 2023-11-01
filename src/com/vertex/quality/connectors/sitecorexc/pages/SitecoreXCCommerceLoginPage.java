package com.vertex.quality.connectors.sitecorexc.pages;

import com.vertex.quality.connectors.sitecorexc.common.enums.SiteCoreXCData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SitecoreXC Content Editor's Login Page
 *
 * @author Shivam.Soni
 */
public class SitecoreXCCommerceLoginPage extends SitecoreXCPage {

    protected By loginUsernameBox = By.xpath(".//input[@placeholder='Enter user name']");
    protected By loginPasswordBox = By.xpath(".//input[@placeholder='Enter password']");
    protected By loginButton = By.xpath(".//button[normalize-space(.)='Log in']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCCommerceLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Loads SitecoreXC Commerce's login page
     */
    public void loadCommerceLoginPage() {
        driver.get(SiteCoreXCData.COMMERCE_URL_30.data);
    }

    /**
     * Log-In to the SitecoreXC Commerce Manager site
     */
    public void logInToCommerceWithAdminUser() {
        waitForPageLoad();
        text.enterText(wait.waitForElementPresent(loginUsernameBox), SiteCoreXCData.COMMERCE_LOGIN_USERNAME.data);
        text.enterText(wait.waitForElementPresent(loginPasswordBox), SiteCoreXCData.COMMERCE_LOGIN_PASSWORD.data);
        click.moveToElementAndClick(wait.waitForElementPresent(loginButton));
        waitForPageLoad();
    }
}
