package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.cpq.SalesForceCPQPostLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SalesForceBillingInvoicePage extends SalesForceBasePage {

    protected By INVOICE_NUMBER = By.id("Name_ileinner");
    protected By INVOICE_NUMBER_LINK = By.xpath("//td[text()='Invoice']/following-sibling::td/div/a");

    protected By INVOICE_DELETE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Delete']");
    protected By INVOICE_EDIT_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Edit']");
    protected By INVOICE_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");
    protected By CANCEL_AND_REBILL_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Cancel and Rebill']");
    protected By CREDIT_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Credit']");
    protected By CONVERT_NEGATIVE_LINES = By.xpath("//div[@class = 'pbHeader']//input[@title= 'Convert Negative Lines']");
    protected By BACK_TO_INVOICE_BUTTON = By.xpath(".//input[@value='Back To Invoice']");
    protected By NEGATIVE_LINE_SUBMIT = By.xpath("//button[text()='Submit']");
    protected By PROCEED_BUTTON = By.xpath("//button[text()='Proceed']");
    protected By NEGATIVE_LINE_CHECKBOX = By.xpath("//div[text()='CNSW1']/parent::th/parent::tr/td//span[@class='slds-checkbox--faux']");

    protected By TAX = By.xpath("//div[@class='pbSubsection']/table/tbody/tr/td/span[text()='Tax']/../following-sibling::td/div");
    protected By TAX_STATUS = By.xpath("//div[@class='pbSubsection']/table/tbody/tr/td[text()='Tax Status']/following-sibling::td/div");
    protected By INVOICE_STATUS = By.xpath("//div[@class='pbSubsection']/table/tbody/tr/td/label[text()='Status']/../following-sibling::td/span/select");

    protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");
    protected By CREDIT_NOTE_LINK = By.xpath(".//tbody/tr/td/a[contains(text(), 'CN')]");

    public SalesForceBillingInvoicePage( WebDriver driver )
    {
        super(driver);
    }

    private SalesForceCPQPostLogInPage postLogInPage = new SalesForceCPQPostLogInPage(driver);
    /**
     * Get tax value for invoice
     *
     * @return tax
     */
    public double getTax( )
    {
        String tax;
        wait.waitForElementDisplayed(TAX);
        tax = text.getElementText(TAX);
        int i=0;
        while ((tax == "" || tax.startsWith("$0")) && i < 5)
        {
            refreshPage();
            wait.waitForElementDisplayed(TAX);
            tax = text.getElementText(TAX);
            i++;
        }
        String noCurrency = trimCurrencySubstring(tax);
        double taxValue = VertexCurrencyUtils.cleanseCurrencyString(noCurrency);
        return taxValue;
    }

    /**
     * Get invoice number
     *
     * @return invoiceNumber
     */
    public String getInvoiceNumber( )
    {
        waitForSalesForceLoaded();
        String invoiceNum;
        wait.waitForElementDisplayed(INVOICE_NUMBER);
        invoiceNum = text.getElementText(INVOICE_NUMBER);
        return invoiceNum;
    }

    /**
     * Click edit button
     */
    public void clickEditButton()
    {
        wait.waitForElementDisplayed(INVOICE_EDIT_BUTTON);
        click.clickElement(INVOICE_EDIT_BUTTON);
    }

    /**
     * Click save button
     */
    public void clickSaveButton()
    {
        wait.waitForElementDisplayed(INVOICE_SAVE_BUTTON);
        click.clickElement(INVOICE_SAVE_BUTTON);
    }

    /**
     * Click cancel and rebill button so that invoice can be deleted
     */
    public void clickCancelAndRebillButton()
    {
        wait.waitForElementDisplayed(CANCEL_AND_REBILL_BUTTON);
        click.clickElement(CANCEL_AND_REBILL_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click Credit button to generate Credit Note for Posted Invoice
     */
    public void clickCreditButton(){
        wait.waitForElementDisplayed(CREDIT_BUTTON);
        click.clickElement(CREDIT_BUTTON);
        waitForPageLoad();
        clickBackToInvoiceButton();
        waitForSalesForceLoaded();
    }

    /**
     * Click ConverNegativeLine button to generate Credit Note for Posted Invoice
     */
    public void clickConvertNegativeLine(){
        wait.waitForElementDisplayed(CONVERT_NEGATIVE_LINES);
        click.clickElement(CONVERT_NEGATIVE_LINES);
        waitForPageLoad();
        //clickBackToInvoiceButton();
        waitForSalesForceLoaded();
    }

    /**
     * Set NegativelineInInvoice Checkbox
     * @param checkNegativeLine
     * */

    public void setNegativeLineInInvoice(Boolean checkNegativeLine)
    {
        wait.waitForElementDisplayed(NEGATIVE_LINE_CHECKBOX);
        checkbox.setCheckbox(NEGATIVE_LINE_CHECKBOX, checkNegativeLine);
    }

    /**
     * Click setNegativeLineSubmit button
     *
     * */

    public void clickNegativeLineSubmit()
    {
        wait.waitForElementDisplayed(NEGATIVE_LINE_SUBMIT);
        click.clickElement(NEGATIVE_LINE_SUBMIT);
        waitForPageLoad();
    }

    /**
     * Click Proceed button
     *
     * */

    public void clickProceedButton()
    {
        jsWaiter.sleep(5000);
        wait.waitForElementDisplayed(PROCEED_BUTTON);
        click.clickElement(PROCEED_BUTTON);
        waitForPageLoad();
    }
    /**
     * Click back to invoice button to return to invoice page
     */
    public void clickBackToInvoiceButton()
    {
        wait.waitForElementDisplayed(BACK_TO_INVOICE_BUTTON);
        click.clickElement(BACK_TO_INVOICE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click delete button
     */
    public void clickDeleteButton( )
    {
        wait.waitForElementDisplayed(INVOICE_DELETE_BUTTON);
        click.clickElement(INVOICE_DELETE_BUTTON);
        alert.acceptAlert(DEFAULT_TIMEOUT);
        waitForPageLoad();
    }

    /**
     * Change status of invoice so invoice can be deleted
     */
    public void changeInvoiceStatus( String status )
    {
        clickEditButton();
        wait.waitForElementDisplayed(INVOICE_STATUS);
        dropdown.selectDropdownByDisplayName(INVOICE_STATUS, status);
        waitForSalesForceLoaded();
        clickSaveButton();
    }

    /**
     * To Edit quote created
     */
    public void clickGoButton( )
    {
        wait.waitForElementDisplayed(VIEW_GO_BUTTON);
        click.clickElement(VIEW_GO_BUTTON);
        waitForPageLoad();
    }

    /**
     * To check if invoice exists
     *
     * @param invoiceNum
     *
     * @return true is invoice exists and false if not
     */
    public boolean checkForInvoice( String invoiceNum )
    {
        String invoiceLookup = String.format("//table/tbody/tr/th/a[text()='%s']", invoiceNum);
        try
        {
            wait.waitForElementDisplayed(By.xpath(invoiceLookup), 10);
            click.clickElement(By.xpath(invoiceLookup));
            waitForPageLoad();
        }
        catch ( Exception e )
        {
            return false;
        }
        return true;
    }

    /**
     * To check if invoice exists
     *
     * @param invoiceNum
     */
    public void navigateToInvoice( String invoiceNum )
    {
        String invoiceLookup = String.format("//table/tbody/tr/th/a[text()='%s']", invoiceNum);
        try
        {
            wait.waitForElementDisplayed(By.xpath(invoiceLookup), 10);
            click.clickElement(By.xpath(invoiceLookup));
            waitForPageLoad();
        }
        catch ( Exception e )
        {
            VertexLogger.log("Invoice: " + invoiceNum + " was not found");
        }
    }

    /**
     * Clicks credit note link that takes to credit note generate for Invoice
	 */
    public void clickCreditNoteLink()
    {
        waitForSalesForceLoaded();
        jsWaiter.sleep(7000);
        refreshPage();
        wait.waitForElementDisplayed(CREDIT_NOTE_LINK);
        click.clickElement(CREDIT_NOTE_LINK);
    }

    /**
     * Deletes invoice created by current order
     *
     * @param invoiceNum
     */
    public void deleteInvoice( String invoiceNum )
    {
        if ( checkForInvoice(invoiceNum) )
        {
            waitForSalesForceLoaded();
            deleteInvoiceOnPage();
        }
    }

    /**
     * Delete invoices if already on the page of the Invoice
     */
    public void deleteInvoiceOnPage()
    {
        clickCancelAndRebillButton();
        waitForSalesForceLoaded();
        waitForPageLoad();
        clickBackToInvoiceButton();
        clickDeleteButton();
    }

    /**
     * Method to find table row associated with specific product
     *
     * @param productName
     *
     * @return table row with relevant product as a WebElement
     */
    private WebElement getInvoiceProductTableRowByProductName(String productName )
    {
        String orderRow = String.format(".//tbody/tr/*/a[contains(text(),'%s')]/../parent::tr", productName);
        WebElement tableRow = element.getWebElement(By.xpath(orderRow));
        return tableRow;
    }

    /**
     * Navigates to Invoice Product based on product name
     */
    public void navigateToInvoiceProduct(String productName)
    {
        waitForSalesForceLoaded();
        WebElement row = getInvoiceProductTableRowByProductName(productName);
        WebElement productLink = row.findElement(By.xpath("th/a"));
        wait.waitForElementDisplayed(productLink);
        click.clickElement(productLink);
    }

    /**
     * Navigates back to Order by clicking invoice number link
     */
    public void navigateBackToInvoice()
    {
        wait.waitForElementDisplayed(INVOICE_NUMBER_LINK);
        click.clickElement(INVOICE_NUMBER_LINK);
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

    /**
     * Navigates to invoice using the SF ID of the Invoice
     */
    public void navigateToInvoiceUsingId(String invoiceId)
    {
        waitForPageLoad();
        driver.get("https://d2e000001fwtvuak-dev-ed.my.salesforce.com/" + invoiceId);
        postLogInPage.closeLightningExperienceDialog();
        waitForPageLoad();
        waitForSalesForceLoaded();
    }

}
