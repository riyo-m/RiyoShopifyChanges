package com.vertex.quality.connectors.oseriesfinal.ui.pages.taxpayers;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.oseriesfinal.api.enums.OSeriesFinalData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Login page actions of O-Series
 *
 * @author Rohit.Mogane
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
        driver.get(OSeriesFinalData.O_SERIES_URL.data);
        waitForPageLoad();
    }

    /**
     * Login to O-Series with O-Series Hybris credentials
     * example: oSeriesLoginPage.loginToOSeries();
     */
    public void loginToOSeries() {
        waitForPageLoad();
        oSeriesEnterUsername(OSeriesFinalData.O_SERIES_USER.data);
        oSeriesEnterPassword(OSeriesFinalData.O_SERIES_PASSWORD.data);
        oSeriesLogin();
        waitForPageLoad();
    }

    /**
     * Login to O-Series with O-Series Hybris credentials
     * example: oSeriesLoginPage.loginToOSeries("testUser", "testPW@123");
     *
     * @param username O-series Final login Username
     * @param password O-series Final login Password
     */
    public void loginToOSeries(String username, String password) {
        waitForPageLoad();
        oSeriesEnterUsername(username);
        oSeriesEnterPassword(password);
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
                && element.isElementDisplayed(oSeriesUsername)
                && element.isElementDisplayed(oSeriesPassword)
                && element.isElementDisplayed(oSeriesSignInButton);
    }

    /**
     * locates the username field and then clears it and types in the username
     * example: oSeriesLoginPage.oSeriesEnterUsername("testUser");
     *
     * @param username the username string which will be entered in order to log in
     */
    private void oSeriesEnterUsername(String username) {
        waitForPageLoad();
        WebElement userNameField = wait.waitForElementEnabled(oSeriesUsername);
        text.enterText(userNameField, username);
    }

    /**
     * locates the password field WebElement and then clears it and sends in the password
     * example: oSeriesLoginPage.oSeriesEnterPassword("testPassword@123");
     *
     * @param password the password string which will be entered in order to log in
     */
    private void oSeriesEnterPassword(String password) {
        WebElement passwordField = wait.waitForElementEnabled(oSeriesPassword);
        text.enterText(passwordField, password);
    }

    /**
     * locates and clicks the login button to log onto the O-Series
     * example: oSeriesLoginPage.oSeriesLogin();
     */
    private void oSeriesLogin() {
        WebElement loginButton = wait.waitForElementEnabled(oSeriesSignInButton);
        click.clickElement(loginButton);
    }
}
