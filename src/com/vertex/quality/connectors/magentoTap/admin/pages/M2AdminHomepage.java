package com.vertex.quality.connectors.magentoTap.admin.pages;

import com.vertex.quality.connectors.magentoTap.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of this admin site before a particular page/menu has been
 * navigated to
 *
 * @author alewis
 */
public class M2AdminHomepage extends MagentoAdminPage {
    protected By loggedInHeaderTag = By.className("page-title");
    protected By messageHeaderId = By.className("modal-header");
    protected By closeMessageButton = By.className("action-close");
    protected By adminAccount = By.xpath("//a[@class='admin__action-dropdown']");
    protected By signout = By.className("account-signout");

    /**
     * Parameterized constructor of the class
     *
     * @param driver Object of WebDriver.
     */
    public M2AdminHomepage(WebDriver driver) {
        super(driver);
    }

    /**
     * checks whether the open page is the one that you reach right after signing in
     * successfully
     *
     * @return whether the open page is the one that you reach right after signing in successfully
     */
    public String checkJustLoggedIn() {
        String loggedInHeaderText = null;

        List<WebElement> headers = driver.findElements(loggedInHeaderTag);

        for (WebElement header : headers) {
            String headerText = header.getText();

            if (headerText != null) {
                loggedInHeaderText = headerText;
            }
        }

        if (loggedInHeaderText == null) {
            String errorMsg = "Banner of text for having just logged in is not found";
            throw new RuntimeException(errorMsg);
        }

        return loggedInHeaderText;
    }

    /**
     * closes any message popup that appears on screen,
     * which would block clicks to other elements on the page
     */
    public void closeMessagePopupIfPresent() {
        waitForPageLoad();
        waitForSpinnerToBeDisappeared();
        if (element.isElementDisplayed(messageHeaderId)) {
            WebElement message = wait.waitForElementDisplayed(messageHeaderId);
            WebElement closeButton = wait.waitForElementEnabled(closeMessageButton, message);

            click.clickElementCarefully(closeButton);
        }
    }

    /**
     * sign out from the admin page
     */
    public void clickSignOutAdminPage() {
        wait.waitForElementDisplayed(adminAccount);
        click.clickElement(adminAccount);
        wait.waitForElementDisplayed(signout);
        click.clickElement(signout);
    }
}
