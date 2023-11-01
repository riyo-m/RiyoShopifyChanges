package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Commerce -> Administration Home page
 *
 * @author Shivam.Soni
 */
public class EpiAdministrationHomePage extends EpiServer324Page {

    protected String ADMIN_SUBMENU_TABS = ".//a[normalize-space(.)='<<text_replace>>']";

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiAdministrationHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects or go to the tab from Administration header option
     *
     * @param tabName name of the Tab
     */
    public void goToTab(String tabName) {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(ADMIN_SUBMENU_TABS.replace("<<text_replace>>", tabName))));
        VertexLogger.log("Navigated to Administration Home -> " + tabName);
    }
}
