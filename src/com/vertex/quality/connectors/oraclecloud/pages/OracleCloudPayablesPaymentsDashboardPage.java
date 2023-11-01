package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;

/**
 * Payables payments dashboard page for application
 *
 * @author Tanmay Mody
 */

public class OracleCloudPayablesPaymentsDashboardPage extends OracleCloudBasePage {

    protected By blockingPlane = By.className("AFBlockingGlassPane");
    protected By approveButtonLoc = By.cssSelector("div[id*='ct2'][role='presentation']");
    protected By approveOkButtonLoc = By.cssSelector("button[id*='Payab1:0:r1:0:at1:cb1']");
    public OracleCloudPayablesPaymentsDashboardPage( WebDriver driver ) { super(driver);}

    /**
     * Approves the invoice from payments dashboard page
     *
     * @param invoiceNumber the invoice number to be approved
     * @param expectedAmount the amount in the notification
     */
    public void approveInvoice(String invoiceNumber, String expectedAmount)
    {
        jsWaiter.sleep(12000);
        
        wait.waitForElementNotEnabled(blockingPlane);

        By notificationLoc = By.xpath("//table[@summary='Requiring My Approval']/tbody/tr[td//text()[contains(., 'Approval of Invoice "+invoiceNumber+"')]]");
        By invoiceApprovalLoc = By.xpath("//table[@summary='Requiring My Approval']/tbody/tr[td//text()[contains(., 'Approval of Invoice "+invoiceNumber+" from MCC Calif ("+expectedAmount+" USD)')]]");

        WebElement approveLink = wait.waitForElementDisplayed(invoiceApprovalLoc);
        click.clickElement(approveLink);

        wait.waitForElementEnabled(approveButtonLoc);
        jsWaiter.sleep(5000);
        click.clickElementCarefully(approveButtonLoc);

        wait.waitForElementDisplayed(approveOkButtonLoc);
        click.clickElement(approveOkButtonLoc);
        wait.waitForElementNotDisplayed(approveOkButtonLoc);
    }
}
