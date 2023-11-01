package com.vertex.quality.connectors.orocommerce.pages.admin;

import com.vertex.quality.connectors.orocommerce.pages.base.OroAdminBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the Oro commerce admin site homepage
 * contains all the methods necessary to interact with the page
 *
 * @author alewis
 */
public class OroAdminHomePage extends OroAdminBasePage {
    By errorMessage = By.xpath("//div[@class='flash-messages-holder']");
    By closeErrorMessage = By.xpath("//div[@class='flash-messages-holder']//button[@aria-label='Close']");

    public OroAdminHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * gets the current page title
     *
     * @return page title as a string
     */
    public String getPageTitle() {
        String pageTitle;
        pageTitle = driver.getTitle();
        return pageTitle;
    }

    /**
     * Checks the error message in admin page is displaying or not
     *
     * @return checkErrorField true or false
     */
    public boolean checkErrorMessageInAdminHomePage() {
        jsWaiter.waitForLoadAll();
        WebElement errorMessageField = wait.waitForElementDisplayed(errorMessage);
        boolean checkErrorField = errorMessageField.isDisplayed();
        return checkErrorField;
    }

    /**
     * close the error message in admin page
     */
    public void closeErrorMessageInAdminHomePage() {
        WebElement errorMessageField = wait.waitForElementPresent(closeErrorMessage);
        click.javascriptClick(errorMessageField);
    }


}
