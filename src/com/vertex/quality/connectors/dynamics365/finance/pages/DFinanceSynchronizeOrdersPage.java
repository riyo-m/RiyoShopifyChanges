package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceSynchronizeOrdersDialog;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinancePostSignOnPage;
import org.openqa.selenium.WebDriver;

/**
 * Contains methods for running jobs on Synchronize Orders page
 */
public class DFinanceSynchronizeOrdersPage extends DFinancePostSignOnPage {

    public DFinanceSynchronizeOrdersDialog dialog;

    public DFinanceSynchronizeOrdersPage( WebDriver driver )
    {
        super(driver);
    }

    /**
     * Add organization and run job to synchronize its orders
     * @param organization
     */
    public void runSynchronizeOrdersJob(String organization) {
        this.dialog = new DFinanceSynchronizeOrdersDialog(driver, this);
        this.dialog.selectOrganizationNode(organization);
        this.dialog.clickAddButton();
        this.dialog.clickOKButton();
    }

    /**
     * Statically wait for Batch Job to Process
     * @param timeToWait
     */
    public void waitStaticallyForBatchJobToRun(int timeToWait)
    {
        jsWaiter.sleep(timeToWait); // Statically waits till Job is done
    }
}
