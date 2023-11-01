package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * represents the sales credit memo page
 *
 * @author bhikshapathi
 */
public class NavSalesCreditMemoPage extends NavSalesBasePage
{
    protected By dialogBoxLoc = By.className("ms-nav-content-box");

    protected By orderDateFieldLoc = By.cssSelector("input[aria-label*='Order Date,']");
    protected By postingDateFieldLoc = By.cssSelector("input[aria-label*='Posting Date,']");

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    String transactionDate = sdf.format(date);

    public NavSalesCreditMemoPage( WebDriver driver ) { super(driver); }

    /**
     * locates the order date field and enters today's date in it.
     */
    public void setOrderDate( )
    {
        WebElement orderDateField = wait.waitForElementPresent(orderDateFieldLoc);
        text.enterText(orderDateField, transactionDate);
        text.enterText(orderDateField, transactionDate);
    }

    /**
     * locates the order posting date field and enters today's date in it.
     */
    public void setPostingDate( )
    {
        WebElement postDateField = wait.waitForElementPresent(postingDateFieldLoc);
        text.enterText(postDateField, transactionDate);
        text.enterText(postDateField, transactionDate);
    }
}
