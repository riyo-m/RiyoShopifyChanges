package com.vertex.quality.connectors.sitecorexc.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SitecoreXC Commerce's app launcher page
 *
 * @author Shivam.Soni
 */
public class SitecoreXCCommerceAppLauncherPage extends SitecoreXCPage {

    protected String appLauncherAppOption = ".//span[normalize-space(.)='<<text_replace>>']";

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public SitecoreXCCommerceAppLauncherPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Select an app from commerce app launcher's apps
     *
     * @param appName Application Name to be selected
     */
    public void selectAppFromAppLauncher(String appName) {
        waitForPageLoad();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(appLauncherAppOption.replace("<<text_replace>>", appName))));
        waitForPageLoad();
    }
}
