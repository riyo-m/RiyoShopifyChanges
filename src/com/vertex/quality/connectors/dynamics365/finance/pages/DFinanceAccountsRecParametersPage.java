package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DFinanceAccountsRecParametersPage extends DFinanceBasePage
{
    protected By TAX_ADJUSTMENT_DETAIL_TITLE = By.xpath("//label[@data-dyn-controlname='Posting_EnableSingleLineTaxAdjustment']//span[@class='toggle-value']");
    protected By TAX_ADJUSTMENT_DETAIL_ENABLED = By.xpath("//label[@data-dyn-controlname='Posting_EnableSingleLineTaxAdjustment']//span[@class='toggle-box']");
    protected By LEDGER_AND_SALES_TAX = By.xpath("//*[contains(@id,\"TabLedgerVAT_header\") and text()='Ledger and sales tax']");
    protected By SAVE_BUTTON = By.name("SystemDefinedSaveButton");

    public DFinanceAccountsRecParametersPage( WebDriver driver )
    {
        super(driver);
        // TODO Auto-generated constructor stub
    }

    /**
     * toggle Enable Vertex For Accounts Receivable to yes or no
     */
    public void toggleTaxAdjustmentDetail(boolean toggleState )
    {
        clickOnLedgerAndSalesTax();
        jsWaiter.sleep(60000);
        WebElement tog = driver.findElement(TAX_ADJUSTMENT_DETAIL_TITLE);
        WebElement list = driver.findElement(TAX_ADJUSTMENT_DETAIL_ENABLED);
        tog.getAttribute("title");
        if ( (toggleState && tog
                .getAttribute("title")
                .equals("No")) || (!toggleState && tog
                .getAttribute("title")
                .equals("Yes")))
        {
            list.click();
        }
        clickSaveButton();
    }

    /**
     * Click on "Save" option
     */
    public void clickSaveButton( )
    {
        wait.waitForElementEnabled(SAVE_BUTTON);
        click.clickElement(SAVE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click on "Ledge and sales tax" option
     */
    public void clickOnLedgerAndSalesTax( )
    {
        click.clickElementCarefully(LEDGER_AND_SALES_TAX);
        waitForPageLoad();
    }
}
