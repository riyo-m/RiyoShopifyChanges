package com.vertex.quality.connectors.orocommerce.pages.admin;

import com.vertex.quality.connectors.orocommerce.pages.base.OroStoreFrontBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author alewis
 */
public class OroAdminLoginPage extends OroStoreFrontBasePage {

    protected final By USERNAME = By.id("prependedInput");
    protected final By PASSWORD = By.id("prependedInput2");
    protected final By login = By.id("_submit");

    public OroAdminLoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enter the specified username
     *
     * @param username the username to attempt to sign in as
     */
    public void enterUsername(final String username) {
        WebElement userElement = wait.waitForElementDisplayed(USERNAME);
        text.enterText(userElement, username);
    }

    /**
     * Enter the specified Password
     *
     * @param password the password to attempt to sign in with
     */
    public void enterPassword(final String password) {
        /* Type the given text into the password field */
        wait.waitForElementDisplayed(PASSWORD);
        text.enterText(PASSWORD, password);
    }

    /**
     * locates and clicks on the sign in button
     *
     * @return in stance of the Oro Commerce HomePage
     */
    public OroAdminHomePage clickSignIntButton() {

        WebElement submitButton = wait.waitForElementEnabled(login);
        click.clickElementCarefully(submitButton);
        waitForPageLoad();
        OroAdminHomePage homePage = initializePageObject(OroAdminHomePage.class);
        return homePage;
    }

    /**
     * locates the username and password fields
     * enters the user credentials and clicks signIn
     *
     * @return new object of the oro admin home page
     */
    public OroAdminHomePage loginAsUser(final String username, final String password) {
        OroAdminHomePage homePage;

        enterUsername(username);
        enterPassword(password);

        homePage = clickSignIntButton();

        return homePage;
    }
}



