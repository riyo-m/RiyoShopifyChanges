package com.vertex.quality.connectors.oseriesEdge.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oseriesEdge.tests.base.OseriesEdgeBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This test set is for setting username and password to login.
 *
 * @author Brenda Johnson
 */
public class OseriesEdgeLoginPage extends OseriesEdgeBasePage
{
    protected By USERNAME_INPUT = By.id("username");
    protected By PASSWORD_INPUT = By.id("password");
    protected By LOGIN_BUTTON = By.xpath(".//button[text()='Continue']");

    public OseriesEdgeLoginPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * To Set Username
     *
     * @param username
     */
    public void setUsername( String username )
    {
        text.enterText(USERNAME_INPUT, username);
        waitForPageLoad();
    }

    /**
     * To Set password
     *
     * @param password
     */
    public void setPassword( String password )
    {
        wait.waitForElementDisplayed(PASSWORD_INPUT);
        text.enterText(PASSWORD_INPUT, password);
        waitForPageLoad();
    }

    /**
     * Click on Login button
     */
    public void clickLoginButton( )
    {
        VertexLogger.log("Clicking login button...", VertexLogLevel.TRACE, OseriesEdgeLoginPage.class);
        click.clickElement(LOGIN_BUTTON);
        waitForPageLoad();
    }

    /**
     * Login as Edge User
     *
     * @param username
     * @param password
     */
    public OseriesEdgeLoginPage logInAsEdgeUser(String username, String password )
    {
        setUsername(username);
        clickLoginButton();
        waitForPageLoad();
        setPassword(password);
        clickLoginButton();
        OseriesEdgePostLoginPage postLogInPage = initializePageObject(OseriesEdgePostLoginPage.class);
        return postLogInPage;
    }
}
