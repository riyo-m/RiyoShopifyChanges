package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.*;

public class DFinanceVertexInvoicePostingQueuePage extends DFinanceBasePage{
    protected By RUN_INVOICE_POSTING_REQUEST_PROCESS = By.xpath("//span[text()='Run invoice posting request process']");
    protected By FILTER_SEARCH_BOX = By.xpath("//input[@name='QuickFilter_Input']");
    protected By FILTER_SEARCH_BOX_2 = By.xpath("(//input[@name='QuickFilter_Input'])[2]");
    protected By BATCH_PROCESSING = By.xpath("(//label[text()='Batch processing']//..//div//span)[1]");
    protected By INVOICE = By.xpath("//span[text()='Invoice']");
    protected By INVOICE_2 = By.xpath("(//span[text()='Invoice'])[2]");
    protected By INVOICE_REQUEST_STATUS = By.xpath("//input[@aria-label='Request status']");
    protected By INVOICE_REQUEST_STATUS_2 = By.xpath("(//input[@aria-label='Request status'])[2]");

    public DFinanceVertexInvoicePostingQueuePage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Verifies that the invoice is present and is set to waiting, posted, or failed
     * @param invoiceNumber
     * @param invoiceStatus
     */
    public boolean verifyInvoiceIsPresentAndInvoiceStatus(String invoiceStatus, String invoiceNumber){
        WebElement invoiceStatusType = wait.waitForElementDisplayed(By.xpath("//li[text()='"+invoiceStatus+" invoice requests']"));
        click.clickElementCarefully(invoiceStatusType);

        jsWaiter.sleep(5000);
        if(element.isElementDisplayed(FILTER_SEARCH_BOX_2)){
            text.enterText(FILTER_SEARCH_BOX_2, invoiceNumber);
        }else if(element.isElementPresent(FILTER_SEARCH_BOX)){
            text.enterText(FILTER_SEARCH_BOX, invoiceNumber);
        }

        if(element.isElementDisplayed(INVOICE_2)){
            click.clickElementCarefully(INVOICE_2);
        }else if(element.isElementPresent(INVOICE)){
            click.clickElementCarefully(INVOICE);
        }

        waitForPageLoad();
        boolean invoiceStatusValue = false;
        String requestStatus = "";
        if(element.isElementPresent(INVOICE_REQUEST_STATUS)) {
           requestStatus = attribute.getElementAttribute(INVOICE_REQUEST_STATUS, "value");
        }else if(element.isElementPresent(INVOICE_REQUEST_STATUS_2)){
            requestStatus = attribute.getElementAttribute(INVOICE_REQUEST_STATUS_2, "value");
        }
        if(requestStatus.equals(invoiceStatus)){
            invoiceStatusValue = true;
        }
        return invoiceStatusValue;
    }

    /**
     * Runs the invoice posting queue process
     */
    public void runInvoicePostingQueueRequestProcess(){
        wait.waitForElementDisplayed(RUN_INVOICE_POSTING_REQUEST_PROCESS);
        click.clickElementCarefully(RUN_INVOICE_POSTING_REQUEST_PROCESS);

        wait.waitForElementDisplayed(BATCH_PROCESSING);
        click.clickElementCarefully(BATCH_PROCESSING);

    }
}
