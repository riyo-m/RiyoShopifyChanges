package com.vertex.quality.connectors.episerver.pages.v324x;

import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Episerver's Order Management page where orders related things can be managed
 *
 * @author Shivam.Soni
 */
public class EpiOrderManagementHomePage extends EpiServer324Page {

    protected String ORDER_MANAGEMENT_TABS = ".//li[text()='<<text_replace>>']";

    /**
     * Parameterized constructor of the class
     *
     * @param driver object of WebDriver
     */
    public EpiOrderManagementHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects or go to the tab from Order Management header option
     *
     * @param tabName name of the Tab
     */
    public void goToTab(String tabName) {
        waitForSpinnerToBeDisappeared();
        click.moveToElementAndClick(wait.waitForElementPresent(By.xpath(ORDER_MANAGEMENT_TABS.replace("<<text_replace>>", tabName))));
        VertexLogger.log("Navigated to Order Management Home -> " + tabName);
    }
}
