package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NavSalesReturnOrderPage extends NavSalesBasePage
{
    protected By orderDateFieldLoc = By.cssSelector("input[aria-label*='Order Date,']");
    protected By getPostedDocumentLines = By.xpath("//span[contains(text(),'Get Posted Document Lines')]");
    protected By okayButton = By.xpath("//button/span[contains(.,'OK')]");
    protected By prepare = By.xpath("//a[@title='Prepare']");
    protected By messageConLoc = By.className("ms-nav-content");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By radioButtonContainer = By.className("radio-button-edit-container");
    protected By documentNoHeading=By.xpath("(//th[@abbr='Document No.'])[1]");
    protected By documentHeadingArrow=By.xpath("(//th[@abbr='Document No.'][1]/div/a)[2]");
    protected By documentFilter=By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
    protected By documentSearch=By.xpath("//div/p[contains(.,'Only show lines where \"Document No.\" is')]/../../../div/div/input[1]");
    protected By okay=By.xpath("(//button/span[contains(.,'OK')])[2]");
    protected By buttonLoc = By.tagName("button");
    protected By inputLoc = By.tagName("input");
    public NavSalesReturnOrderPage(WebDriver driver) { super(driver);}

    /**
     * Select Process Get Posted Document Lines to Reverse
     */
    public void selectProcessGetPostedDocumentLinestoReverse()
    {
        waitForPageLoad();
        if (!element.isElementDisplayed(getPostedDocumentLines))
        {
            click.clickElementCarefully(prepare);
        }
        WebElement reverseTab = wait.waitForElementPresent(getPostedDocumentLines);
        click.clickElementCarefully(reverseTab);
        waitForPageLoad();
    }
    /**
     * Fills line leval location code in the items table when creating a sales order/quote/memo/invoice
     * @param reasonCode
     * @param itemIndex
     */
    public void enterReasonCode(String reasonCode, int itemIndex)
    {
       int itemNumber = itemIndex - 1;
       NavSalesBasePage salesPage = new NavSalesBasePage(driver);
       salesPage.waitForPageLoad();
       salesPage.enterReasonCode(reasonCode, itemNumber);
       waitForPageLoad();
    }

    /**
     * filters the document number
     * @param postedInvoiceNo
     */
    public void filterDocuments(String postedInvoiceNo) {
        WebElement documentOpenMenu=wait.waitForElementDisplayed(documentNoHeading);
        click.clickElementCarefully(documentOpenMenu);
        WebElement documentMenuArrow=wait.waitForElementDisplayed(documentHeadingArrow);
        click.clickElementCarefully(documentMenuArrow);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(documentSearch);
        text.enterText(docSearch,postedInvoiceNo);
        click.clickElementCarefully(okay);
        waitForPageLoad();
    }
}
