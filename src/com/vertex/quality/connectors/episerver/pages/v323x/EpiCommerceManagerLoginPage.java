package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Episerver Commerce Manager Login Page - contains all re-usable methods specific to this page.
 *
 * @author Shivam.Soni
 */
public class EpiCommerceManagerLoginPage extends VertexPage {
    /**
     * Parameterized construction of the class
     *
     * @param driver object of WebDriver
     */
    public EpiCommerceManagerLoginPage(WebDriver driver) {
        super(driver);
    }

    protected By USERNAME_INPUT = By.id("LoginCtrl_UserName");
    protected By PASSWORD_INPUT = By.id("LoginCtrl_Password");
    protected By LOGIN_BUTTON = By.id("LoginCtrl_LoginButton");

    /**
     * Login to Episerver Commerce Manager
     *
     * @param username Username of login user
     * @param password Password of login user
     */
    public void loginToCommerceManager(String username, String password) {
        waitForPageLoad();
        WebElement user = wait.waitForElementPresent(USERNAME_INPUT);
        text.enterText(user, username);
        WebElement pass = wait.waitForElementPresent(PASSWORD_INPUT);
        text.enterText(pass, password);
        WebElement login = wait.waitForElementPresent(LOGIN_BUTTON);
        click.moveToElementAndClick(login);
        waitForPageLoad();
    }
}
