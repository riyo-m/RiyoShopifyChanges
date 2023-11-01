package com.vertex.quality.connectors.tradeshift.pages.connector;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.concur.pages.misc.ConcurHomePage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroAdminHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Tradeshift Vertex Connector Sign in page
 *
 * @author alewis
 */
public class TradeshiftConnectorSignInPage extends VertexPage {

    protected By usernameTextInput = By.id("username");
    protected By passwordTextInput = By.id("password");
    protected By loginButton = By.id("Login_button");
    protected By clickHereToLogin = By.xpath("//b/a[contains(text(),'here')]");

    public TradeshiftConnectorSignInPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Inputs username for Tradeshift connector login
     * */
    public void enterUsername(String username){
        WebElement usernameInput = wait.waitForElementDisplayed(usernameTextInput);
        text.clickElementAndEnterText(usernameInput,username);
    }

    /**
     * Inputs password for Tradeshift connector login
     * */
    public void enterPassword(String password){
        WebElement passwordInput = wait.waitForElementDisplayed(passwordTextInput);
        text.clickElementAndEnterText(passwordInput,password);
    }

    /**
     * clicks the login button for the Tradeshift connector
     * */
    public TradeshiftConnectorHomePage clickLogin() {
        WebElement login = wait.waitForElementDisplayed(loginButton);
        click.clickElementCarefully(loginButton);

        TradeshiftConnectorHomePage homePage = initializePageObject(TradeshiftConnectorHomePage.class);

        return homePage;
    }

    /**
     * login method for the Tradeshift connector
     * */
    public TradeshiftConnectorHomePage loginAsUser(final String username, final String password )
    {
        TradeshiftConnectorHomePage homePage;

        WebElement here = wait.waitForElementDisplayed(clickHereToLogin);
        click.clickElementCarefully(here);
        enterUsername(username);
        enterPassword(password);

        homePage = clickLogin();

        return homePage;
    }
}