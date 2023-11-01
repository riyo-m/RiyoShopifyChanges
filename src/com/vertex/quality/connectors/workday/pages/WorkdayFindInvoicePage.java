package com.vertex.quality.connectors.workday.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.*;

/**
 * this class represents the workday find invoice page
 * contains all the methods necessary to interact with the page
 * @author dpatel
 */
public class WorkdayFindInvoicePage extends VertexPage {

    public WorkdayFindInvoicePage(WebDriver driver) { super(driver); }

    protected By invNo = By.xpath("(//input[@data-automation-id='textInputBox'])[1]");
    protected By ok = By.cssSelector("span[title='OK']");
    protected By invNoView = By.xpath("(//div[@data-automation-id='textView'])[1]");
    protected By invDateOnAfter = By.xpath("(//input[@data-automation-id='dateWidgetInputBox'])[1]");
    protected By invDateOnBefore = By.xpath("(//input[@data-automation-id='dateWidgetInputBox'])[2]");
    protected By docNum = By.xpath("//span[text()='Gapless Document Number']");

    /**
     * This method locate and send values to "Invoice Number" field
     *
     * @param invNumber Invoice Number
     */
    public void enterInvoiceNumber(String invNumber)
    {
        text.enterText(wait.waitForElementDisplayed(invNo),invNumber);
    }

    /**
     * This method locate and click "OK" button and wait till the invoice is loaded
     *
     * @param invNumber Invoice Number
     *
     * @return WorkdayInvoiceReviewPage class object
     */
    public WorkdayInvoiceReviewPage clickOk(String invNumber)
    {
        click.clickElementCarefully(ok);
        wait.waitForTextInElement(invNoView,invNumber);
        return initializePageObject(WorkdayInvoiceReviewPage.class);
    }

    /**
     * This method locate and send values to Date After field
     */
    public void enterDateAfter()
    {
        WorkdayInvoiceReportPage report = new WorkdayInvoiceReportPage(driver);
        text.enterText(invDateOnAfter,report.getTodaysDate());
        waitForPageLoad();
    }

    /**
     * This method sends values to Date Before field
     *
     * @param date a String of the format mm/dd/yyyy
     */
    public void enterDateBefore(String date)
    {
        text.enterText(invDateOnBefore, date);
        waitForPageLoad();
    }

    /**
     * This method locate and click "OK" button and wait till the invoice is loaded
     *
     * @param invNumber Invoice Number
     *
     * @return WorkdayInvoiceReviewPage class object
     */
    public WorkdaySupplierInvoiceReviewPage clickOkAndNavigateToSupplierInvoice(String invNumber)
    {
        click.clickElementCarefully(ok);
        wait.waitForElementDisplayed(docNum);
        return initializePageObject(WorkdaySupplierInvoiceReviewPage.class);
    }

}
