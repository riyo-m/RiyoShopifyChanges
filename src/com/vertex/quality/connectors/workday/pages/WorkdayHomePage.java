package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class represents the homepage from which user can navigate to most of the other pages.
 * it contains all the methods necessary to interact with the page for navigation
 *
 * @author dPatel
 */
public class WorkdayHomePage extends VertexPage {

    public WorkdayHomePage(WebDriver driver) {
        super(driver);
    }

    protected By searchBox = By.xpath("//input[contains(@data-automation-id,'globalSearchInput')]");
    protected By searchButton = By.xpath("//div[@data-automation-id='searchInputSearchIcon']");
    protected By searchDeleteButton = By.xpath("//button[@data-automation-id='searchInputClearTextIcon']");
    protected By waitForSearchPage = By.xpath("//aside[@class='css-1aigrd2-leftColumn']");
    protected By findSuplInv = By.xpath("//div[@title='Find Supplier Invoices']");
    protected By launchScheduleIntegration = By.xpath("//*[contains(text(),'Launch / Schedule Integration')]");
    protected By searchOption = By.xpath("//*[@id=\"wd-autocomplete-result-0\"]/div");
    protected By createCustInv = By.xpath("//a[text()='Create Customer Invoice']");
    protected By createSuplInv = By.xpath("//a[text()='Create Supplier Invoice']");
    protected By viewIntegration = By.xpath("//div[@title='View Integration System']");
    protected By suplInvAdj = By.xpath("//div[@title='Create Supplier Invoice Adjustment']");
    protected By waitForInvoiceForm = By.xpath("//span[contains(.,'Invoice Information')]");


    /**
     * This method sends values to searchBox and then click on first suggestion from the populated suggestion drop down
     *
     * @param searchName name of the to do task/action for ex. "create customer Invoice"
     */
    public void searchFromHomePage(String searchName, boolean isDropdown) {
        WebDriverWait _wait = new WebDriverWait(driver, 5);

        wait.waitForElementDisplayed(searchBox);
        click.clickElementIgnoreExceptionAndRetry(searchBox);

        text.enterTextIgnoreExceptionsAndRetry(searchBox,searchName);

        if (isDropdown) {
            wait.waitForElementEnabled(searchOption).click();
        } else {
            _wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            click.javascriptClick(searchButton);
            wait.waitForElementDisplayed(waitForSearchPage);

            _wait.until(ExpectedConditions.elementToBeClickable(searchDeleteButton));
            click.clickElementIgnoreExceptionAndRetry(searchDeleteButton);

        }
    }

    /**
     * This method gets the form for creating customer invoice
     *
     * @return customerInvoice page object
     */
    public WorkdayCustomerInvoicePage getCustomerInvoiceForm() {

        waitForPageLoad();
        searchFromHomePage("create customer invoice", false);
        try {
            wait.waitForElementDisplayed(createCustInv, 8);
        } catch (TimeoutException e) {
            searchFromHomePage("create customer invoice", false);
        }
        // click.clickElementCarefully(createCustInv);
        click.javascriptClick(createCustInv);
        wait.waitForElementDisplayed(waitForInvoiceForm, 8);
        return initializePageObject(WorkdayCustomerInvoicePage.class);
    }

    /**
     * This method gets the form for creating supplier invoice
     *
     * @return SupplierInvoice page object
     */
    public WorkdaySupplierInvoicePage getSupplierInvoiceForm() {

        waitForPageLoad();
        searchFromHomePage("Create Supplier Invoice", false);
        try {
            wait.waitForElementDisplayed(createSuplInv, 2);
        } catch (TimeoutException e) {
            searchFromHomePage("Create Supplier Invoice", false);
        }
        click.clickElementCarefully(createSuplInv);
        return initializePageObject(WorkdaySupplierInvoicePage.class);
    }

    /**
     * This method gets the form for creating supplier invoice adjustment
     *
     * @return SupplierInvoice page object
     */
    public WorkdaySupplierInvoicePage getSupplierInvoiceAdjustmentForm() {
        waitForPageLoad();
        searchFromHomePage("Create supplier invoice adjustment", false);
        try {
            wait.waitForElementDisplayed(suplInvAdj, 2);
        } catch (TimeoutException e) {
            searchFromHomePage("Create supplier invoice adjustment", false);
            wait.waitForElementDisplayed(suplInvAdj, 2);
        }
        click.clickElementCarefully(suplInvAdj);

        return initializePageObject(WorkdaySupplierInvoicePage.class);
    }

    /**
     * This method gets the "Launch/Schedule Integration" page
     *
     * @return WorkdayLaunchIntegrationPage object
     */
    public WorkdayLaunchIntegrationPage getLaunchIntegrationPage() {
        waitForPageLoad();
        searchFromHomePage("launch / Schedule Integration ", false);
        try {
            wait.waitForElementDisplayed(launchScheduleIntegration, 2);
        } catch (TimeoutException e) {
            searchFromHomePage("launch / Schedule Integration ", false);
            wait.waitForElementDisplayed(launchScheduleIntegration, 2);
        }
        click.javascriptClick(launchScheduleIntegration);
        return initializePageObject(WorkdayLaunchIntegrationPage.class);
    }

    /**
     * This method gets the "Find Customer Invoice"  page
     *
     * @return WorkdayFindInvoicePage object
     */
    public WorkdayFindInvoicePage getFindInvoicePage() {
        searchFromHomePage("Find customer invoices", true);
        return initializePageObject(WorkdayFindInvoicePage.class);
    }

    /**
     * This method gets the "Find Customer Invoice"  page
     *
     * @return WorkdayFindInvoicePage object
     */
    public WorkdayFindInvoicePage getFindSupplierInvoicePage() {
        jsWaiter.sleep(5000); //Waits for Supplier Invoice to Get Processed
        searchFromHomePage("Find supplier invoices", false);
        click.clickElementCarefully(findSuplInv);
        return initializePageObject(WorkdayFindInvoicePage.class);
    }

    /**
     * This method gets the BatchQuoteCustomerInvoice Report page
     *
     * @return WorkdayInvoiceReportPage object
     */
    public WorkdayInvoiceReportPage getBatchQuoteReportPage() {
        searchFromHomePage("VtxBatchQuoteCustomerInvoiceRpt", true);
        return initializePageObject(WorkdayInvoiceReportPage.class);
    }

    /**
     * This method gets the BatchPostCustomerInvoice Report page
     *
     * @return WorkdayInvoiceReportPage object
     */
    public WorkdayInvoiceReportPage getBatchPostReportPage() {
        searchFromHomePage("VtxBatchPostCustomerInvoiceRpt", true);
        return initializePageObject(WorkdayInvoiceReportPage.class);
    }

    /**
     * This method gets the View integration page
     *
     * @return WorkdayIntegrationAttributesPage object
     */
    public WorkdayIntegrationAttributesPage getViewIntegrationPage() {
        waitForPageLoad();
        searchFromHomePage("view integration system", false);
        try {
            wait.waitForElementDisplayed(viewIntegration, 2);
        } catch (TimeoutException e) {
            searchFromHomePage("view integration system", false);
        }
        click.clickElementCarefully(viewIntegration);
        return initializePageObject(WorkdayIntegrationAttributesPage.class);
    }

}
