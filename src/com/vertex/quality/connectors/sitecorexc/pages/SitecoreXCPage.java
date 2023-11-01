package com.vertex.quality.connectors.sitecorexc.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * SitecoreXC page that contains common helper methods used across the SitecoreXC connector.
 *
 * @author Shivam.Soni
 */
public class SitecoreXCPage extends VertexPage {

    protected By sitecoreLoader = By.className("loading-bar");
    protected By siteCoreSpinner = By.className("scSpinner");
    protected By storePrivacyWarningPopup = By.className("privacy-warning permisive");
    protected By storePrivacyWarningClose = By.xpath(".//div[@class='close']//a");
    protected By storePrivacyWarningConfirm = By.xpath(".//div[@class='submit']//a");

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Waits till loaders are exists
     */
    public void waitForLoaderToBeDisappeared() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(sitecoreLoader));
        waitForPageLoad();
    }

    /**
     * Waits till spinners are exists
     */
    public void waitForSpinnerToBeDisappeared() {
        waitForPageLoad();
        new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(siteCoreSpinner));
        waitForPageLoad();
    }

    /**
     * Accept or Reject cookies' privacy warning
     *
     * @param accept true to accept, false to reject
     */
    public void acceptOrRejectPrivacyWarning(boolean accept) {
        if (accept) {
            confirmPrivacyWarning();
        } else {
            closePrivacyWarning();
        }
    }

    /**
     * Closes the Privacy/ Cookies warning pop-up
     */
    public void closePrivacyWarning() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(storePrivacyWarningClose));
        waitForPageLoad();
    }

    /**
     * Accepts cookies' privacy warning
     */
    public void confirmPrivacyWarning() {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(storePrivacyWarningConfirm));
        waitForPageLoad();
    }
}
