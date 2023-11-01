package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SalesForceBillingCreditNotePage extends SalesForceBasePage {

    protected By CREDIT_NOTE_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");
    protected By CREDIT_NOTE_EDIT_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Edit']");
    protected By CREDIT_NOTE_NEW_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'New']");
    protected By CREDIT_NOTE_DELETE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Delete']");
    protected By CREDIT_NOTE_ESTIMATE_TAX_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Estimate Tax']");
    protected By CREDIT_NOTE_APPLY_TAX_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Apply Tax']");
    protected By CREDIT_NOTE_ALLOCATE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Allocate']");
    protected By NEW_CREDIT_NOTE_LINE_BUTTON = By.xpath(".//input[@value='New Credit Note Line']");
    protected By INVOICE_ALLOCATE_CHECKBOX = By.xpath("//div[text()='FAXI1']/../preceding-sibling::td[2]//input[@type='checkbox']");
    protected By INVOICE_ALLOCATE_AMOUNT = By.xpath("//div[text()='FAXI1']/../following-sibling::td[6]/div//input[@type='number']");
    protected By ALLOCATE_IN_ALLOCATE_BUTTON = By.xpath("//button[@id='allocateBtn']");
    protected By CANCEL_IN_ALLOCATE = By.xpath("//button[text()='Cancel']");
    protected By CANCEL_BUTTON_ON_ALERT_POPUP = By.xpath(".//button[@class='sfdc-dialog-button-primary' and text() = 'Cancel']");

    protected By ACCOUNT_NAME = By.xpath(
            "//label[text()='Account']/../following-sibling::td//span[@class='lookupInput']//input");
    protected By SOURCE_INVOICE = By.xpath(
            "//label[text()='Source Invoice']/../following-sibling::td//span[@class='lookupInput']//input");
    protected By SOURCE_ACTION_DROPDOWN = By.xpath(
            "//label[text()='Credit Note Source Action']/../following-sibling::td/span/select");
    protected By CREDIT_NOTE_DATE_INPUT = By.xpath(
            "//label[text()='Credit Note Date']/../following-sibling::td//span[@class='dateFormat']");
    protected By TAX_DATE_INPUT = By.xpath(
            "//label[text()='Effective Tax Date']/../following-sibling::td//span[@class='dateFormat']");
    protected By CREDIT_NOTE_LINE_IN_ALLOCATE = By.xpath("//div[text()='FAXI1']/../preceding-sibling::td[2]//input[@name='crditLineRadio']");
    protected By TAX_STREET_INPUT = By.xpath("//label[text()='Tax Street 1']/../following-sibling::td/input");
    protected By TAX_CITY_INPUT = By.xpath("//label[text()='Tax City']/../following-sibling::td/input");
    protected By TAX_STATE_INPUT = By.xpath("//label[text()='Tax State']/../following-sibling::td/input");
    protected By TAX_POSTAL_CODE_INPUT = By.xpath("//label[text()='Tax Postal Code']/../following-sibling::td/input");
    protected By TAX_COUNTRY_INPUT = By.xpath("//label[text()='Tax Country']/../following-sibling::td/input");

    protected By CREDIT_NOTE_TAX = By.xpath("//td[text()='Tax']/following-sibling::td/div[contains(text(), 'USD')]");
    protected By CREDIT_NOTE_NUMBER = By.xpath("//td[text()='Credit Note Number']/following-sibling::td/div[contains(text(), 'CN')]");

    protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");

    public SalesForceBillingCreditNotePage(WebDriver driver) {super(driver);}


    /**
     * Click edit button
     */
    public void clickEditButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_EDIT_BUTTON);
        click.clickElement(CREDIT_NOTE_EDIT_BUTTON);
    }

    /**
     * Click save button
     */
    public void clickSaveButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_SAVE_BUTTON);
        click.clickElement(CREDIT_NOTE_SAVE_BUTTON);
    }

    /**
     * Set account name
     */
    public void setAccountName(String accountName)
    {
        wait.waitForElementDisplayed(ACCOUNT_NAME);
        text.enterText(ACCOUNT_NAME, accountName);
    }
    /**
     * Set Source Incoice name
     */
    public void setSourceInvoice(String invoiceName)
    {
        wait.waitForElementDisplayed(SOURCE_INVOICE);
        text.enterText(SOURCE_INVOICE, invoiceName);
    }
    /**
     * Set Invoice Allocate Amount
     */
    public void setInvoiceAllocateAmount(String invoiceAllocateAmount)
    {
        wait.waitForElementDisplayed(INVOICE_ALLOCATE_AMOUNT);
        text.enterText(INVOICE_ALLOCATE_AMOUNT, invoiceAllocateAmount);
    }
    /**
     * Set action
     * @param action
     */
    public void setSourceAction( String action )
    {
        wait.waitForElementDisplayed(SOURCE_ACTION_DROPDOWN);
        dropdown.selectDropdownByValue(SOURCE_ACTION_DROPDOWN, action);
        waitForPageLoad();
    }

    /**
     * Set Credit Note Date
     */
    public void setCreditNoteDate( )
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_DATE_INPUT);
        click.clickElement(CREDIT_NOTE_DATE_INPUT);
        waitForSalesForceLoaded();
    }

    /**
     * Set Effective Tax Date
     */
    public void setEffectiveTaxDate( )
    {
        wait.waitForElementDisplayed(TAX_DATE_INPUT);
        click.clickElement(TAX_DATE_INPUT);
        waitForSalesForceLoaded();
    }

    /**
     * Set tax street
     * @param street
     */
    private void setStreet(String street)
    {
        wait.waitForElementDisplayed(TAX_STREET_INPUT);
        text.enterText(TAX_STREET_INPUT, street);
    }

    /**
     * Set tax city
     * @param city
     */
    private void setCity(String city)
    {
        wait.waitForElementDisplayed(TAX_CITY_INPUT);
        text.enterText(TAX_CITY_INPUT, city);
    }

    /**
     * Set tax state
     * @param state
     */
    private void setState(String state)
    {
        wait.waitForElementDisplayed(TAX_STATE_INPUT);
        text.enterText(TAX_STATE_INPUT, state);
    }

    /**
     * Set tax postal code
     * @param postalCode
     */
    private void setPostalCode(String postalCode) {
        wait.waitForElementDisplayed(TAX_POSTAL_CODE_INPUT);
        text.enterText(TAX_POSTAL_CODE_INPUT, postalCode);
    }

    /**
     * Set tax country
     * @param country
     */
    private void setCountry(String country)
    {
        wait.waitForElementDisplayed(TAX_COUNTRY_INPUT);
        text.enterText(TAX_COUNTRY_INPUT, country);
    }

    /**
     * Set tax address
     * @param street
     * @param city
     * @param state
     * @param postalCode
     * @param country
     */
    public void setTaxAddress(String street, String city, String state, String postalCode, String country)
    {
        setStreet(street);
        setCity(city);
        setState(state);
        setPostalCode(postalCode);
        setCountry(country);
    }

    /**
     * Get tax amount
     * @return total tax amount for credit note
     */
    public double getTax( )
    {
        String tax;
        waitForPageLoad();
        wait.waitForElementDisplayed(CREDIT_NOTE_TAX);
        tax = trimCurrencySubstring(text.getElementText(CREDIT_NOTE_TAX));
        int i=0;
        System.out.println(tax);
        while ((tax.equals("") || tax.startsWith("USD 0")) && i < 5)
        {
            refreshPage();
            wait.waitForElementDisplayed(CREDIT_NOTE_TAX);
            tax = text.getElementText(CREDIT_NOTE_TAX);
            i++;
        }
        double taxValue = VertexCurrencyUtils.cleanseCurrencyString(tax);
        return taxValue;
    }

    /**
     * Select Credit Note line in allocate page
     *
     */
    public void selectCreditNoteline()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_LINE_IN_ALLOCATE);
        click.clickElement(CREDIT_NOTE_LINE_IN_ALLOCATE);
        waitForSalesForceLoaded();
    }
    /**
     * Set invoiceAllocate Checkbox
     * @param invoiceAllocate
     */
    public void setInvoiceLineInAllocate(Boolean invoiceAllocate)
    {
        wait.waitForElementDisplayed(INVOICE_ALLOCATE_CHECKBOX);
        checkbox.setCheckbox(INVOICE_ALLOCATE_CHECKBOX, invoiceAllocate);
    }


    /**
     * Get credit note number
     * @return current credit note number
     */
    public String getCreditNoteNumber()
    {
        String creditNoteNum;
        wait.waitForElementDisplayed(CREDIT_NOTE_NUMBER);
        creditNoteNum = text.getElementText(CREDIT_NOTE_NUMBER);
        return creditNoteNum;
    }

    /**
     * Click new credit note button
     * @param accountName
     * @param action
     * @param street
     * @param city
     * @param state
     * @param postalCode
     * @param country
     */
    public void createNewCreditNote(String accountName, String action, String street, String city, String state, String postalCode, String country)
    {
        clickNewButton();
        setAccountName(accountName);
        setSourceAction(action);
        setCreditNoteDate();
        setEffectiveTaxDate();
        setTaxAddress(street, city, state, postalCode, country);
    }
    /**
     * Click new credit note button
     * @param accountName
     * @param action
     * @param street
     * @param city
     * @param state
     * @param postalCode
     * @param country
     * @param invoice
     */
    public void createNewCreditNote(String accountName,String invoice, String action, String street, String city, String state, String postalCode, String country)
    {
        clickNewButton();
        setAccountName(accountName);
        setSourceInvoice(invoice);
        setSourceAction(action);
        setCreditNoteDate();
        setEffectiveTaxDate();
        setTaxAddress(street, city, state, postalCode, country);
    }

    /**
     * Click the New Credit Note Button
     */
    public void clickNewButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_NEW_BUTTON);
        click.clickElement(CREDIT_NOTE_NEW_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click the New Credit Note Line Button
     */
    public void clickNewCreditNoteLineButton()
    {
        wait.waitForElementDisplayed(NEW_CREDIT_NOTE_LINE_BUTTON);
        click.clickElement(NEW_CREDIT_NOTE_LINE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click the Estimate Tax Button
     */
    public void clickEstimateTaxButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_ESTIMATE_TAX_BUTTON);
        click.clickElement(CREDIT_NOTE_ESTIMATE_TAX_BUTTON);
        clickCancelButtonOnAlertPopup(DEFAULT_TIMEOUT);
        waitForPageLoad();
    }

    /**
     * Click the Apply Tax Button
     */
    public void clickApplyTaxButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_APPLY_TAX_BUTTON);
        click.clickElement(CREDIT_NOTE_APPLY_TAX_BUTTON);
        clickCancelButtonOnAlertPopup(DEFAULT_TIMEOUT);
        waitForPageLoad();
    }

    /**
     * Click Cancel button on alert pop up
     * @param defaultTimeout
     */
    public void clickCancelButtonOnAlertPopup(int defaultTimeout){
        wait.waitForElementDisplayed(CANCEL_BUTTON_ON_ALERT_POPUP, defaultTimeout);
        click.clickElement(CANCEL_BUTTON_ON_ALERT_POPUP);
    }

    /**
     * Click the Allocate Button
     */
    public void clickAllocateButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_ALLOCATE_BUTTON);
        click.clickElement(CREDIT_NOTE_ALLOCATE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click the Allocate In Allocate Button
     */
    public void clickAllocateInAllocateButton()
    {
        wait.waitForElementDisplayed(ALLOCATE_IN_ALLOCATE_BUTTON);
        click.clickElement(ALLOCATE_IN_ALLOCATE_BUTTON);
        waitForPageLoad();
    }
    /**
     * Click the Cancel In Allocate
     */
    public void clickCancelInAllocate()
    {
        jsWaiter.sleep(5000);
        wait.waitForElementDisplayed(CANCEL_IN_ALLOCATE);
        click.clickElement(CANCEL_IN_ALLOCATE);
        waitForPageLoad();
    }


    /**
     * Click delete button
     */
    public void clickDeleteButton( )
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_DELETE_BUTTON);
        click.clickElement(CREDIT_NOTE_DELETE_BUTTON);
        alert.acceptAlert(DEFAULT_TIMEOUT);
        waitForPageLoad();
    }

    /**
     * Clicks go button to search for order
     */
    public void clickGoButton( )
    {
        wait.waitForElementDisplayed(VIEW_GO_BUTTON);
        click.clickElement(VIEW_GO_BUTTON);
        waitForPageLoad();
    }



    /**
     * To check if credit note exists
     *
     * @param creditNoteNum
     *
     * @return true is order exists and false if not
     */
    public boolean checkForCreditNote(String creditNoteNum )
    {
        String noteLookup = String.format("//table/tbody/tr/td/div/a/span[text()='%s']", creditNoteNum);
        try
        {
            wait.waitForElementDisplayed(By.xpath(noteLookup), 10);
            click.clickElement(By.xpath(noteLookup));
            waitForPageLoad();
        }
        catch ( Exception e )
        {
            return false;
        }
        return true;
    }

    /**
     * Deletes credit note
     *
     * @param creditNoteNum
     */
    public void deleteCreditNote(String creditNoteNum )
    {
        clickGoButton();
        if ( checkForCreditNote(creditNoteNum) )
        {
            // Deactivates order so it can be deleted
            clickDeleteButton();
        }
    }

    /**
     * Navigates to Credit Note based on Credit Note Number
     */
    public void navigateToCreditNote(String creditNoteNum)
    {
        clickGoButton();
        checkForCreditNote(creditNoteNum);
    }

    /**
     * Method to find table row associated with specific product
     *
     * @param productName
     *
     * @return table row with relevant product as a WebElement
     */
    private WebElement getCreditNoteLineTableRowByProductName(String productName )
    {
        String orderRow = String.format(".//tbody/tr/*/a[contains(text(),'%s')]/../parent::tr", productName);
        WebElement tableRow = element.getWebElement(By.xpath(orderRow));
        return tableRow;
    }

    /**
     * Navigates to Credit Note Line based on product name
     *
     * @param productName
     */
    public void navigateToCreditNoteLine(String productName)
    {
        waitForSalesForceLoaded();
        WebElement row = getCreditNoteLineTableRowByProductName(productName);
        WebElement productLink = row.findElement(By.tagName("th"));
        wait.waitForElementDisplayed(productLink);
        click.clickElement(productLink);
    }

}
