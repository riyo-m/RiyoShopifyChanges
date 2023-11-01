package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Order Management page for application
 *
 * @author Tanmay Mody
 */

public class OracleCloudOrderManagementPage extends OracleCloudBasePage {

    protected By createOrderLoc = By.cssSelector("div[id*='createbtn']");
    protected By tasksLoc = By.cssSelector("div[role='presentation'][id*='m1']");
    protected By manageOrdersLoc = By.cssSelector("tr[id*='cmimo']");

    public OracleCloudOrderManagementPage( WebDriver driver ) { super(driver); }

    /**
     * Clicks on the Create Order button on the page
     */
    public OracleCloudCreateOrderPage clickCreateOrder( OracleCloudPageNavigationData page)
    {

        WebElement createOrder = wait.waitForElementEnabled(createOrderLoc);
        click.clickElementCarefully(createOrder);
        waitForLoadedPage("Create Order");

        Class pageClass = page.getPageClass();

        return initializePageObject(pageClass);
    }

    /**
     * Navigates to the Manage Orders page
     */
    public OracleCloudManageOrdersPage navigateToManageOrders( OracleCloudPageNavigationData page)
    {

        WebElement tasks = wait.waitForElementEnabled(tasksLoc);
        click.clickElementCarefully(tasks);
        WebElement manageOrders = wait.waitForElementEnabled(manageOrdersLoc);
        click.clickElementCarefully(manageOrders);
        waitForLoadedPage("Manage Orders");

        Class pageClass = page.getPageClass();

        return initializePageObject(pageClass);
    }
}
