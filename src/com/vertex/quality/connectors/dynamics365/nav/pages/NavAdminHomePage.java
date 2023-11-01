package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.connectors.dynamics365.nav.components.NavHomePageMainNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the home page of NAV environment after the login process
 *
 * @author bhikshapathi
 */
public class NavAdminHomePage extends NavBasepage
{
    public NavHomePageMainNavMenu mainNavMenu;

    protected By headerTitle = By.tagName("title");
    protected By mainFrameLoc = By.className("designer-client-frame");
    protected By dialogBoxLoc = By.className("ms-nav-content-box");
    protected By actionsCon = By.cssSelector("div[class*='stacked-command-bar-container']");

    protected By dialogBoxTitleLoc = By.className("dialog-title");
    protected By dialogBoxButtonCon = By.className("dialog-action-bar");
    protected By vertexAdminButtonLoc = By.cssSelector("a[aria-label='Vertex Admin']");
    protected By customersButtonLoc = By.xpath("//li/a[@title='Customers']");
    protected By salesButtonLoc = By.xpath("(//span[@aria-label='Sales'])[1]");

    public NavAdminHomePage( WebDriver driver )
    {
        super(driver);
        this.mainNavMenu = new NavHomePageMainNavMenu(driver, this);
    }

    /**
     * Returns the page's title for verification
     *
     * @return page title
     */
    public String verifyHomepageHeader( )
    {
        String titleTxt = getPageTitle();

        return titleTxt;
    }

    /**
     * If a popup appears warning the user of a license expiration, click OK to bypass
     */
    public void checkForCautionPopup( )
    {
        try
        {
            driver
                    .switchTo()
                    .frame(wait.waitForElementPresent(mainFrameLoc, 30));

            WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc, 15);
            WebElement textContent = wait.waitForElementDisplayed(dialogBoxTitleLoc, dialog);

            String text = textContent.getText();

            if ( text.contains("Caution: Your program license expires in ") )
            {
                dialogBoxClickOk();
            }
        }
        catch ( TimeoutException e )
        {
            // if popup doesn't appear, no need to do anything
        }
    }

    /**
     * Clicks the link to the customers list page
     *
     * @return customers list page
     */
    public NavCustomersListPage navigateToCustomersListPage( )
    {
        WebElement button = wait.waitForElementEnabled(customersButtonLoc);
        click.clickElementCarefully(button);
        waitForPageLoad();

        return initializePageObject(NavCustomersListPage.class);
    }
    /**
     * Navigates to the vertex admin configurations page by clicking the link on the home page
     *
     * @return vertex admin page
     */
    public VertexAutomationObject navigateToVertexAdminPage( )
    {
        window.switchToFrame(mainFrameLoc,30);
        WebElement actionsContainer = wait.waitForElementPresent(actionsCon);
        WebElement vertexAdminButton = wait.waitForElementEnabled(vertexAdminButtonLoc, actionsContainer);
        click.clickElement(vertexAdminButton);

        return initializePageObject(NavVertexAdminPage.class);
    }

}
