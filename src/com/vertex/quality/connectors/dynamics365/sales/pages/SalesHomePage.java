package com.vertex.quality.connectors.dynamics365.sales.pages;

import com.vertex.quality.connectors.dynamics365.sales.pages.SalesOrdersPage;
import com.vertex.quality.connectors.dynamics365.sales.pages.base.SalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * represents the Sales Hub page
 * @author Vivek Olumbe
 */
public class SalesHomePage extends SalesBasePage {

    protected By ORDERS_TAB = By.xpath("//li[@aria-label='Orders']");
    protected By QUOTES_TAB = By.xpath("//li[@aria-label='Quotes']");
    protected By INVOICES_TAB = By.xpath("//li[@aria-label='Invoices']");
    protected By ACCOUNTS_TAB = By.xpath("//li[@aria-label='Accounts']");


    public SalesHomePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Go to Orders page
     * @return SalesOrdersPage
     */
    public SalesOrdersPage navigateToSalesOrdersPage() {
        click.clickElementIgnoreExceptionAndRetry(ORDERS_TAB);
        SalesOrdersPage salesOrdersPage = new SalesOrdersPage(driver);

        waitForLoadingScreen();

        return salesOrdersPage;
    }

    /**
     * Go to Quotes page
     * @return SalesQuotesPage
     */
    public SalesQuotesPage navigateToSalesQuotesPage() {
        click.clickElementIgnoreExceptionAndRetry(QUOTES_TAB);
        SalesQuotesPage salesQuotesPage = new SalesQuotesPage(driver);

        waitForLoadingScreen();
        return salesQuotesPage;
    }

    /**
     * Go to Invoices page
     * @return SalesInvoicesPage
     */
    public SalesInvoicesPage navigateToSalesInvoicesPage() {
        click.clickElementIgnoreExceptionAndRetry(INVOICES_TAB);
        SalesInvoicesPage salesInvoicesPage = new SalesInvoicesPage(driver);

        waitForLoadingScreen();
        return salesInvoicesPage;
    }

    /**
     * Go to Vertex Admin
     * @return SalesVertexAdminPage
     */
    public SalesVertexAdminPage navigateToVertexAdminPage() {
        navigateToApp("Sales Hub", "Vertex");
        waitForLoadingScreen();
        SalesVertexAdminPage adminPage = new SalesVertexAdminPage(driver);
        return adminPage;
    }
}
