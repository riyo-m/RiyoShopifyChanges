package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * represents the sales return order page
 */

public class BusinessSalesReturnOrderPage extends BusinessSalesBasePage
{
    protected By orderDateFieldLoc = By.xpath("//div[@controlname='Order Date']//input");
    protected By postingDateFieldLoc = By.xpath("//div[@controlname='Posting Date']//input");
    protected By sellToAddressCodeInput = By.xpath("//div[@controlname='VER_Ship-To-Address']//input");
    protected By salesOrderNumberLoc1 = By.xpath("//div[contains(@class, 'ms-nav-layout-head')]//div[@role='heading' and @aria-level='2' and contains(@class, 'title')]");
    protected By okayButton = By.xpath("//button/span[contains(.,'OK')]");
    protected By getPostedDocumentLinesToReverse=By.xpath("//span[contains(text(),'Get Posted Document Lines')]");

    protected By documentNoHeading=By.xpath("(//th[@abbr='Document No.'])[1]");
    protected By documentHeadingArrow=By.xpath("(//th[@abbr='Document No.'][1]/div/a)[2]");
    protected By documentFilter=By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
    protected By documentSearch=By.xpath("//div/p[contains(.,'Only show lines where \"Document No.\" is')]/../../../div/div/input[1]");
    protected By okay=By.xpath("(//button/span[contains(.,'OK')])[2]");

    protected By messageConLoc = By.cssSelector(".ms-nav-content-box.message-dialog");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By radioButtonContainer = By.className("radio-button-edit-container");
    protected By enableShowReversibleLines=By.xpath("//div[@aria-label='Show Reversible Lines Only, '][@aria-checked='false']");
    protected By enableReturnOriginalQuantity=By.xpath("//div[@aria-label='Return Original Quantity, '][@aria-checked='false']");

    protected By buttonLoc = By.tagName("button");
    protected By radioLoc = By.tagName("li");
    protected By inputLoc = By.tagName("input");

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    String transactionDate = sdf.format(date);

    public BusinessSalesReturnOrderPage(WebDriver driver) {
        super(driver);
    }
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

    /**
     * gets the order number from the top of the page
     * will only be present after at least clicking on the items table
     *
     * @return the order number
     */
    public String getOrderNumber( )
    {   waitForPageLoad();
        WebElement title = wait.waitForElementEnabled(salesOrderNumberLoc1);
        String fullText = text.getElementText(title);
        System.out.println(fullText);
        String orderNum = fullText.substring(0, fullText.indexOf(" "));
        return orderNum;
    }

    /**
     * click posted Documents line to reverse in Process tab
     */
    public void selectPostedDocumentLinesToReverse(){
        WebElement clickGetPosted = wait.waitForElementPresent(getPostedDocumentLinesToReverse);
        click.clickElementCarefully(clickGetPosted);
        waitForPageLoad();
    }

    /**
     * Switch on the show reversible lines toggle button
     */
    public void enableShowReversibleLines(){
        if(element.isElementDisplayed(enableShowReversibleLines)){
            click.clickElementCarefully(enableShowReversibleLines);
            waitForPageLoad();
        }
    }

    /**
     * Switch on the Return Original Quantity toggle button
     */
    public void enableReturnOriginalQuantity(){
        if(element.isElementDisplayed(enableReturnOriginalQuantity)){
            click.clickElementCarefully(enableReturnOriginalQuantity);
            waitForPageLoad();
        }
    }
    /**
     * filters the document number
     * @param postedInvoiceNo
     */
    public void filterDocuments(String postedInvoiceNo) {
        WebElement documentOpenMenu=wait.waitForElementDisplayed(documentNoHeading);
        click.clickElementCarefully(documentOpenMenu);
         waitForPageLoad();
        WebElement documentMenuArrow=wait.waitForElementDisplayed(documentHeadingArrow);
        click.javascriptClick(documentMenuArrow);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(documentSearch);
        text.enterText(docSearch,postedInvoiceNo);
        click.clickElementCarefully(okay);
        waitForPageLoad();
    }

    /**
     * update's the unit price for a line item in Return Order
     * @param amount
     * @param rowIndex
     */
    public void updateAmount(String amount, int rowIndex){
        rowIndex=rowIndex-1;
        BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
        WebElement amountField= salesPage.getTableCell(rowIndex,11);
        click.moveToElementAndClick(amountField);
        waitForPageLoad();
        text.setTextFieldCarefully(amountField, amount, false);
        text.pressTab(amountField);
    }

    /**
     * update's the quantity for a line item in Return Order
     * @param quantity
     * @param rowIndex
     */
    public void updateQuantity(String quantity, int rowIndex){
        rowIndex=rowIndex-1;
        BusinessSalesBasePage salesPage = new BusinessSalesBasePage(driver);
        WebElement quantityField= salesPage.getTableCell(rowIndex,9);
        click.moveToElementAndClick(quantityField);
        waitForPageLoad();
        text.setTextFieldCarefully(quantityField, quantity, false);
        text.pressTab(quantityField);
    }

    /**
     * Select ship-to Address Code under Sell-to section
     * @param shipToCode
     */
    public void selectSellToAddressCode(String shipToCode) {
        WebElement shipToCodeField = wait.waitForElementDisplayed(sellToAddressCodeInput);
        scroll.scrollElementIntoView(shipToCodeField);
        click.clickElementCarefully(shipToCodeField);
        text.enterText(shipToCodeField, shipToCode);
        text.pressTab(shipToCodeField);
    }
}
