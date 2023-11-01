package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Handles the common Things specific to Episerver's 3.2.4 version
 *
 * @author Shivam.Soni
 */
public class EpiServer324Page extends VertexPage {

    protected By EPI_COMMON_SPINNER = By.className("oui-spinner");
    protected By EPI_COMMON_PROGRESS_BAR = By.xpath(".//div[@role='progressbar']");
    protected By USER_AVATAR = By.xpath(".//*[@class='glyphicon glyphicon-user']");
    protected By EXPANDED_USER_AVATAR = By.xpath(".//*[@class='glyphicon glyphicon-user']/parent::a[@aria-expanded='true']");
    protected By USER_AVATAR_SIGN_OUT_OPTION = By.xpath(".//a[text()='Sign out']");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiServer324Page(WebDriver driver) {
        super(driver);
    }

    /**
     * Waits till spinner or loaders are exists
     */
    public void waitForSpinnerToBeDisappeared() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(EPI_COMMON_SPINNER));
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(EPI_COMMON_PROGRESS_BAR));
        waitForPageLoad();
    }

    /**
     * Sign out from Episerver Store
     */
    public void signOutOfEpiServerStore() {
        waitForPageLoad();
        wait.waitForElementPresent(USER_AVATAR);
        if (!element.isElementDisplayed(EXPANDED_USER_AVATAR)) {
            click.moveToElementAndClick(USER_AVATAR);
            wait.waitForElementPresent(EXPANDED_USER_AVATAR);
        }
        click.moveToElementAndClick(wait.waitForElementPresent(USER_AVATAR_SIGN_OUT_OPTION));
        waitForPageLoad();
        VertexLogger.log("Signed out of Episerver's store");
    }
}