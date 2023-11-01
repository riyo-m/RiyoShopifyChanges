package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.BusinessSalesInvoicePage;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NavSalesInvoicePage extends NavSalesBasePage
{
    protected By postingDateFieldLoc = By.cssSelector("input[aria-label*='Posting Date,']");
    protected By postTab = By.xpath("(//span[@class='ms-cui-ctl-largelabel'][contains(.,'Post')])[1]");
    protected By yesButton = By.xpath("//button[@title='Yes']");

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    String transactionDate = sdf.format(date);

    public NavSalesInvoicePage( WebDriver driver ) { super(driver); }

    /**
     * locates the order posting date field and enters today's date in it.
     */
    public void setPostingDate( )
    {
        WebElement postDateField = wait.waitForElementPresent(postingDateFieldLoc);
        text.enterText(postDateField, transactionDate);
        text.enterText(postDateField, transactionDate);
    }
    /**
     * When posting a sales order, selects the Ship and Invoice option
     * then posts the order as an invoice
     *
     */
    public void salesOrderSelectShipAndInvoiceThenPost()
    {
       WebElement post=wait.waitForElementDisplayed(postTab);
       click.clickElementCarefully(post);
       waitForPageLoad();
       WebElement ok=wait.waitForElementDisplayed(yesButton);
       click.clickElementCarefully(ok);
       waitForPageLoad();
        clickYesInvoice();
    }
    /**
     * When posting a sales order, selects the Ship and Invoice option
     * then posts the order as an invoice
     *
     */
    public void clickYesInvoice( )
    {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);

    }
}
