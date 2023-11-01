package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.DFinanceAllPurchaseOrdersPage;
import com.vertex.quality.connectors.dynamics365.sales.pages.base.SalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the home page of business central environment after the login process
 * @author Shruti
 */
public class SalesAdminHomePage extends SalesBasePage {
    protected By appContainer = By.id("AppLandingPage");
    protected By SALES_HUB_LOC = By.cssSelector("[title='Sales Hub']");
    protected By VERTEX_ADMIN_LOC = By.cssSelector("[title='Vertex']");

    public SalesAdminHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Go to home page of Sales Hub
     * @return SalesHomePage
     */
    public SalesHomePage navigateToSalesHomePage() {
        waitForPageLoad();
        waitForLoadingScreen();
        WebElement container = wait.waitForElementDisplayed(appContainer);

        driver.switchTo().frame(container);

        waitForPageLoad();

        wait.waitForElementEnabled(SALES_HUB_LOC);
        click.clickElementIgnoreExceptionAndRetry(SALES_HUB_LOC);

        waitForLoadingScreen();

        SalesHomePage salesHomePage = new SalesHomePage(driver);

        return salesHomePage;
    }

    /**
     * Go to Vertex Admin page
     * @return SalesVertexAdminPage
     */
    public SalesVertexAdminPage navigateToSalesVertexAdminPage() {
        waitForLoadingScreen();
        waitForPageLoad();

        WebElement container = wait.waitForElementDisplayed(appContainer);

        driver.switchTo().frame(container);

        waitForPageLoad();

        wait.waitForElementEnabled(VERTEX_ADMIN_LOC);
        click.clickElementIgnoreExceptionAndRetry(VERTEX_ADMIN_LOC);

        waitForLoadingScreen();

        SalesVertexAdminPage salesVertexAdminPage = new SalesVertexAdminPage(driver);

        return salesVertexAdminPage;
    }
}
