package com.vertex.quality.connectors.sitecorexc.admin.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Login page of SiteCoreXC Admin.
 *
 * @author Vivek.Kumar
 */
public class SiteCoreXCAdminLoginPage extends VertexPage {
    public SiteCoreXCAdminLoginPage(WebDriver driver) {
        super(driver);
    }

    protected By usernameTextInput = By.xpath("//input[@name='UserName']");
    protected By passwordTextInput = By.xpath("//input[@name='Password']");
    protected By signInButton = By.id("LogInBtn");

    /**
     * Inputs username for SiteCoreXC connector login
     *
     * @param username for login into admin
     */
    public void enterUsername(String username) {
        WebElement usernameInput = wait.waitForElementDisplayed(usernameTextInput);
        text.enterText(usernameInput, username);
    }

    /**
     * Inputs password for SiteCoreXC connector login
     *
     * @param password for login into admin
     */
    public void enterPassword(String password) {
        WebElement passwordInput = wait.waitForElementDisplayed(passwordTextInput);
        text.enterText(passwordInput, password);
    }

    /**
     * clicks the login button for the SiteCoreXC connector
     *
     * @return homePage SiteCoreXCHomePage for the connector.
     */
    public SiteCoreXCAdminHomePage clickLogin() {
        WebElement login = wait.waitForElementDisplayed(signInButton);
        click.clickElementCarefully(login);

        SiteCoreXCAdminHomePage homePage = initializePageObject(SiteCoreXCAdminHomePage.class);

        return homePage;
    }

    /**
     * login method for the SiteCoreXC connector
     *
     * @param username for login into connector admin
     * @param password for login into connector login
     * @return homePage SiteCoreXCHomePage.
     */
    public SiteCoreXCAdminHomePage loginAsUser(final String username, final String password) {
        SiteCoreXCAdminHomePage homePage;

        enterUsername(username);
        enterPassword(password);

        homePage = clickLogin();

        return homePage;
    }
}