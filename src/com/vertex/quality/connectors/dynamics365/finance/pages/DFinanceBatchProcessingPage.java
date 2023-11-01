package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * This Class represent Batch Processing in Finance and Operation
 * @author Dhruv.Patel
 */
public class DFinanceBatchProcessingPage extends DFinanceBasePage {

    /**
     * constructor
     */
    public DFinanceBatchProcessingPage(WebDriver driver)
    {
        super(driver);
    }

    protected By FULFILLMENT_PROFILE = By.cssSelector("[id*='Fld1_1_input']");
    protected By OK = By.xpath("//span[text()='OK']");

    /**
     * It selects Fulfillment profile
     * @param profile
     */
    public void selectFulfillmentProfile (String profile)
    {
        text.enterText(FULFILLMENT_PROFILE,profile);
        click.clickElementCarefully(OK);
        jsWaiter.sleep(20000); //Wait till Batch Processing Completed
    }
}
