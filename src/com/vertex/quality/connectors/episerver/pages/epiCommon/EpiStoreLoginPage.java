package com.vertex.quality.connectors.episerver.pages.epiCommon;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Episerver Store Login Page - contains all re-usable methods specific to this page.
 *
 * @author Shivam.Soni
 */
public class EpiStoreLoginPage extends VertexPage {
    public EpiStoreLoginPage(WebDriver driver) {
        super(driver);
    }

    protected By USER_AVATAR = By.xpath(".//span[@class='glyphicon glyphicon-user']/following-sibling::span");
    protected By USER_AVATAR_SIGN_IN_OPTION = By.xpath(".//a[text()='Sign in']");
    protected By USERNAME_INPUT = By.xpath(".//label[text()='E-mail:']/following-sibling::input | .//label[text()='Username:']/following-sibling::input");
    protected By PASSWORD_INPUT = By.xpath(".//label[text()='Password:']/following-sibling::input");
    protected By LOGIN_BUTTON = By.xpath(".//button[text()='Login']");
    protected By cookiesBar = By.cssSelector(".jsCookies");
    protected By cookiesButton = By.cssSelector("button.jsCookiesBtn");

    /**
     * Navigates to login page if login credentials' input box are not loaded
     */
    public void navigateToLoginPage() {
        waitForPageLoad();
        WebElement userLogo = wait.waitForElementPresent(USER_AVATAR);
        if (!(element.isElementPresent(USERNAME_INPUT) | element.isElementPresent(PASSWORD_INPUT) | element.isElementPresent(LOGIN_BUTTON))) {
            click.moveToElementAndClick(userLogo);
            WebElement signInOption = wait.waitForElementPresent(USER_AVATAR_SIGN_IN_OPTION);
            click.moveToElementAndClick(signInOption);
            waitForPageLoad();
            wait.waitForElementPresent(USERNAME_INPUT);
            wait.waitForElementPresent(PASSWORD_INPUT);
            wait.waitForElementPresent(LOGIN_BUTTON);
        }
        VertexLogger.log("Navigated to the Episerver's Store Login page");
    }

    /**
     * This method is used to set Username
     */
    public void enterUsername(String username) {
        text.enterText(USERNAME_INPUT, username);
        waitForPageLoad();
        VertexLogger.log("Entered login username", VertexLogLevel.TRACE, EpiStoreLoginPage.class);
    }

    /**
     * This method is used to set Password
     */
    public void enterPassword(String password) {
        text.enterText(PASSWORD_INPUT, password);
        waitForPageLoad();
        VertexLogger.log("Entered login password", VertexLogLevel.TRACE, EpiStoreLoginPage.class);
    }

    /**
     * This method is used to click on Login Button
     */
    public void clickLoginButton() {
        VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE, EpiStoreLoginPage.class);
        click.clickElement(LOGIN_BUTTON);
        wait.waitForElementNotPresent(LOGIN_BUTTON, DEFAULT_TIMEOUT);
    }

    /**
     * Login to Epi-Server Store front
     *
     * @param username username or email of login user
     * @param password password of login user
     */
    public void loginToEpiStore(String username, String password) {
        navigateToLoginPage();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        waitForPageLoad();
        handleCookies();
        VertexLogger.log("Logged in to Episerver's Store");
    }

    /**
     * Handles the cookies bar
     */
    public void handleCookies() {
        waitForPageLoad();
        if (element.isAnyElementDisplayed(cookiesBar)) {
            click.javascriptClick(wait.waitForElementPresent(cookiesButton));
            waitForPageLoad();
            VertexLogger.log("cookies are handled...");
        }
    }
}
