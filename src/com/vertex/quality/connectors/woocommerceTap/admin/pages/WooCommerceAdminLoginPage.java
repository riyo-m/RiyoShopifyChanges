package com.vertex.quality.connectors.woocommerceTap.admin.pages;


import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Login page of WooCommerce Admin.
 *
 * @author Vivek.Kumar
 */
public class WooCommerceAdminLoginPage extends VertexPage {
    public WooCommerceAdminLoginPage(WebDriver driver) {
        super(driver);
    }

    protected By usernameTextInput = By.xpath("//input[@name='log']");
    protected By passwordTextInput = By.xpath("//input[@name='pwd']");
    protected By signInButton = By.id("wp-submit");

    /**
     * Inputs username for WooCommerce connector login
     *
     * @param username for login into admin
     */
    public void enterUsername(String username) {
        WebElement usernameInput = wait.waitForElementDisplayed(usernameTextInput);
        text.enterText(usernameInput, username);
    }

    /**
     * Inputs password for WooCommerce connector login
     *
     * @param password for login into admin
     */
    public void enterPassword(String password) {
        WebElement passwordInput = wait.waitForElementDisplayed(passwordTextInput);
        text.enterText(passwordInput, password);
    }

    /**
     * clicks the login button for the WooCommerce connector
     *
     * @return homePage WooCommerce for the connector.
     */
    public WooCommerceAdminHomePage clickLogin() {
        WebElement login = wait.waitForElementDisplayed(signInButton);
        click.clickElementCarefully(login);

        WooCommerceAdminHomePage homePage = initializePageObject(WooCommerceAdminHomePage.class);

        return homePage;
    }

    /**
     * login method for the WooCommerce connector
     *
     * @param username for login into connector admin
     * @param password for login into connector login
     * @return homePage WooCommerceHomePage.
     */
    public WooCommerceAdminHomePage loginAsUser(final String username, final String password) {
        WooCommerceAdminHomePage homePage;

        enterUsername(username);
        enterPassword(password);

        homePage = clickLogin();

        return homePage;
    }
}
