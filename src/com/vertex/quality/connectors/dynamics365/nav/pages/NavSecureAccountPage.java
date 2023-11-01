package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessSignOnPage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the secure account page, which will prompt the user
 * to enter account verification/recovery methods
 *
 * @author bhikshapathi
 */
public class NavSecureAccountPage extends NavBasepage
{
    protected By cancelButtonLoc = By.id("CancelLinkButton");

    public NavSecureAccountPage( WebDriver driver ) { super(driver); }

    /**
     * clicks the cancel button to return to the sign on process
     *
     * @return sign on page
     */
    public NavSignOnPage clickCancel( )
    {
        WebElement cancelButton = wait.waitForElementEnabled(cancelButtonLoc);
        click.clickElement(cancelButton);
        wait.waitForElementNotDisplayedOrStale(cancelButton, 10);

        return initializePageObject(NavSignOnPage.class);
    }
}
