package com.vertex.quality.connectors.episerver.pages.oseries;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.episerver.common.enums.OSeriesData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Login page actions of O-Series
 *
 * @author Shivam.Soni
 */
public class OSeriesLoginPage extends VertexPage {

    public OSeriesLoginPage(final WebDriver driver) {
        super(driver);
    }

    protected final By oSeriesUsername = By.xpath("//input[@id='username']");
    protected final By oSeriesPassword = By.xpath("//input[@id='password']");
    protected final By oSeriesSignInButton = By.xpath("//button[@id='Login_button']");

    /**
     * Load O-Series Login Page
     * example: oSeriesLoginPage.loadOSeriesPage();
     */
    public void loadOSeriesPage() {
        driver.get(OSeriesData.O_SERIES_URL.data);
        waitForPageLoad();
    }

    /**
     * Login to O-Series with O-Series Hybris credentials
     * example: oSeriesLoginPage.loginToOSeries();
     */
    public void loginToOSeries() {
        waitForPageLoad();
        oSeriesEnterUsername(OSeriesData.O_SERIES_USER.data);
        oSeriesEnterPassword(OSeriesData.O_SERIES_PASSWORD.data);
        oSeriesLogin();
        waitForPageLoad();
    }

    /**
     * Helps to identify O-Series Login Page.
     * example: oSeriesLoginPage.verifyLoginPage();
     *
     * @return it will return true when all conditions matched else it will return false
     */
    public boolean verifyLoginPage() {
        waitForPageLoad();
        return driver.getCurrentUrl().endsWith("login")
                && element.isElementPresent(oSeriesUsername)
                && element.isElementPresent(oSeriesPassword)
                && element.isElementPresent(oSeriesSignInButton);
    }

    /**
     * locates the username field and then clears it and types in the username
     * example: oSeriesLoginPage.oSeriesEnterUsername("testUser");
     *
     * @param username the username string which will be entered in order to log in
     */
    private void oSeriesEnterUsername(String username) {
        waitForPageLoad();
        WebElement userNameField = wait.waitForElementPresent(oSeriesUsername);
        text.enterText(userNameField, username);
    }

    /**
     * locates the password field WebElement and then clears it and sends in the password
     * example: oSeriesLoginPage.oSeriesEnterPassword("testPassword@123");
     *
     * @param password the password string which will be entered in order to log in
     */
    private void oSeriesEnterPassword(String password) {
        WebElement passwordField = wait.waitForElementPresent(oSeriesPassword);
        text.enterText(passwordField, password);
    }

    /**
     * locates and clicks the login button to log onto the O-Series
     * example: oSeriesLoginPage.oSeriesLogin();
     */
    private void oSeriesLogin() {
        WebElement loginButton = wait.waitForElementPresent(oSeriesSignInButton);
        click.clickElement(loginButton);
    }
}
