package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * This Class represent Distribution schedule Page
 */
public class DFinanceDistributionSchedulePage extends DFinanceBasePage {

    //Constructor
    public DFinanceDistributionSchedulePage( WebDriver driver )
    {
        super(driver);
    }

    protected By RUN_NOW = By.xpath("//span[text()='Run now']");
    protected By OK = By.xpath("//span[text()='OK']");
    protected By FILTER = By.name("QuickFilterControl_Input");
    protected By SEARCH_BUTTON = By.xpath("(//li[contains(@class,'quickFilter-listItem flyout-menuItem')])[1]");
    protected By YES_BUTTON = By.xpath(".//*[contains(@id,'Yes_label')]");

    /**
     * It filters Job to run
     * @param jobName
     */
    public void filterJobToRun(String jobName)
    {
        text.setTextFieldCarefully(FILTER,jobName+ Keys.ENTER);
        jsWaiter.waitForLoadAll();
//        wait.waitForElementDisplayed(SEARCH_BUTTON);
//        click.clickElement(SEARCH_BUTTON);
    }

    /**
     * It clicks on Run now
     * @param jobName
     */
    public void initiateTheJob(String jobName)
    {
        filterJobToRun(jobName);
        WebElement runNow = wait.waitForElementEnabled(RUN_NOW);
        click.performDoubleClick(runNow);
    }

    /**
     * Click on Yes button
     */
    public void clickOnYes( )
    {
        wait.waitForElementDisplayed(YES_BUTTON);
        click.clickElement(YES_BUTTON);
        wait.waitForElementNotDisplayed(YES_BUTTON);
    }

    /**
     * It clicks on OK
     */
    public void clickOK()
    {
        if(element.isElementDisplayed(YES_BUTTON))
            clickOnYes();
        click.clickElementCarefully(OK);
        wait.waitForElementNotDisplayed(OK);
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
