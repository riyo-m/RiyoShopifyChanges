package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;

public class SalesForceBillingCreditNoteLinePage extends SalesForceBasePage {

    protected By PRODUCT_NAME_INPUT = By.xpath("//label[text()='Product']/../following-sibling::td//span[@class='lookupInput']//input");
    protected By BILL_TO_ACCOUNT_INPUT = By.xpath("//label[text()='Bill To Contact']/../following-sibling::td//span[@class='lookupInput']//input");
    protected By LEGAL_ENTITY_INPUT = By.xpath("//label[text()='Legal Entity']/../following-sibling::td//span[@class='lookupInput']//input");
    protected By SUBTOTAL_INPUT = By.xpath("//label[text()='Subtotal']/../../following-sibling::td/input");
    protected By TAX_INPUT = By.xpath("//label[text()='Tax']/../../following-sibling::td/input");
    protected By CALCULATE_TAX_CHECKBOX = By.xpath("//label[text()='Calculate Tax?']/../following-sibling::td/input");
    protected By ISMANUAL_CHECKBOX = By.xpath("//label[text()='IsManual?']/../following-sibling::td/input");

    protected By TAX_DATE_INPUT = By.xpath(
            "//label[text()='Effective Tax Date']/../following-sibling::td//span[@class='dateFormat']");
    protected By START_DATE = By.xpath(
            "//label[text()='Start Date']/parent::span/parent::td/following-sibling::td//span[@class='dateFormat']");
    protected By TAX_STREET_INPUT = By.xpath("//label[text()='Tax Street']/parent::span/parent::td/following-sibling::td/input");
    protected By TAX_CITY_INPUT = By.xpath("//label[text()='Tax City']/parent::span/parent::td/following-sibling::td/input");
    protected By TAX_STATE_INPUT = By.xpath("//label[text()='Tax State']/parent::span/parent::td/following-sibling::td/input");
    protected By TAX_POSTAL_CODE_INPUT = By.xpath("//label[text()='Tax Zip Code']/parent::span/parent::td/following-sibling::td/input");
    protected By TAX_COUNTRY_INPUT = By.xpath("//label[text()='Tax Country']/parent::span/parent::td/following-sibling::td/input");

    protected By CREDIT_NOTE_LINE_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");
    protected By CREDIT_NOTE_LINE_EDIT_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Edit']");

    protected By TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Tax Codes']/following-sibling::td/div");
    protected By VERTEX_TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Vertex Tax Codes']/following-sibling::td/div");
    protected By INVOICE_TEXT_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Invoice Text Codes']/following-sibling::td/div");

    SalesForceBillingCreditNotePage creditNotePage = new SalesForceBillingCreditNotePage(driver);

    public SalesForceBillingCreditNoteLinePage(WebDriver driver) {super(driver);}

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
     * Set Start Date
     */
    public void setStartDate( )
    {
        wait.waitForElementDisplayed(START_DATE);
        click.clickElement(START_DATE);
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
     * Add new credit note line item to credit note records
     * @param productName
     * @param billToAccount
     * @param subtotal
     * @param tax
     * @param calculateTax
     */
    public void addNewCreditNoteLine(String productName, String billToAccount, String subtotal, String tax, Boolean calculateTax)
    {
        creditNotePage.clickNewCreditNoteLineButton();
        setProductName(productName);
        setBillToAccount(billToAccount);
        setSubtotal(subtotal);
        setTax(tax);
        setCalculateTaxCheckbox(calculateTax);
    }
    /**
     * Add new credit note line item to credit note records
     * @param productName
     * @param subtotal
     * @param calculateTax
     */
    public void addNewCreditNoteLine(String productName, Boolean isManual, String subtotal, Boolean calculateTax, String street, String city, String state, String postalCode, String country)
    {
        creditNotePage.clickNewCreditNoteLineButton();
        setProductName(productName);
        setSubtotal(subtotal);
        setIsManualCheckbox(isManual);
        setCalculateTaxCheckbox(calculateTax);
        setEffectiveTaxDate();
        setStartDate();
        setTaxAddress(street, city, state, postalCode, country);
    }

    /**
     * Set product name
     * @param productName
     */
    public void setProductName(String productName)
    {
        wait.waitForElementDisplayed(PRODUCT_NAME_INPUT);
        text.enterText(PRODUCT_NAME_INPUT, productName);
    }

    /**
     * Set Bill To Account
     * @param billToAccount
     */
    public void setBillToAccount(String billToAccount)
    {
        wait.waitForElementDisplayed(BILL_TO_ACCOUNT_INPUT);
        text.enterText(BILL_TO_ACCOUNT_INPUT, billToAccount);
    }

    /**
     * Set subtotal
     * @param subtotal
     */
    public void setSubtotal(String subtotal)
    {
        wait.waitForElementDisplayed(SUBTOTAL_INPUT);
        text.enterText(SUBTOTAL_INPUT, subtotal);
    }

    /**
     * Set Tax
     * @param tax
     */
    public void setTax(String tax)
    {
        wait.waitForElementDisplayed(TAX_INPUT);
        text.enterText(TAX_INPUT, tax);
    }

    /**
     * Set Calculate Tax Checkbox
     * @param calculateTax
     */
    public void setCalculateTaxCheckbox(Boolean calculateTax)
    {
        wait.waitForElementDisplayed(CALCULATE_TAX_CHECKBOX);
        checkbox.setCheckbox(CALCULATE_TAX_CHECKBOX, calculateTax);
    }
    /**
     * Set IsManual Tax Checkbox
     * @param IsManual
     */
    public void setIsManualCheckbox(Boolean IsManual)
    {
        wait.waitForElementDisplayed(ISMANUAL_CHECKBOX);
        checkbox.setCheckbox(ISMANUAL_CHECKBOX, IsManual);
    }

    /**
     * Click edit button
     */
    public void clickEditButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_LINE_EDIT_BUTTON);
        click.clickElement(CREDIT_NOTE_LINE_EDIT_BUTTON);
        waitForPageLoad();
    }

    /**
     * Click save button
     */
    public void clickSaveButton()
    {
        wait.waitForElementDisplayed(CREDIT_NOTE_LINE_SAVE_BUTTON);
        click.clickElement(CREDIT_NOTE_LINE_SAVE_BUTTON);
        waitForPageLoad();
    }

    /**
     * Validate that VAT Return Fields match expected output
     * Tax Codes, Vertex Tax Codes, Invoice Text Codes
     *
     * @param expectedTaxCodes tax codes expected to be returned to SF
     * @param expectedVertexTaxCodes vertex tax codes expected to be returned to SF
     * @param expectedInvoiceTextCodes invoice text codes expected to be returned to SF
     */
    public void validateVATReturnFields(String expectedTaxCodes, String expectedVertexTaxCodes, String expectedInvoiceTextCodes)
    {
        String taxCodes = getTaxCodes();
        String vertexTaxCodes = getVertexTaxCodes();
        String invoiceTextCodes = getInvoiceTextCodes();

        assertEquals(taxCodes, expectedTaxCodes);
        assertEquals(vertexTaxCodes, expectedVertexTaxCodes);
        assertEquals(invoiceTextCodes, expectedInvoiceTextCodes);
    }

    /**
     * Retrieve tax codes
     *
     * @return taxCodes
     */
    public String getTaxCodes()
    {
        wait.waitForElementDisplayed(TAX_CODES);
        String taxCodes = text.getElementText(TAX_CODES);
        return taxCodes;
    }

    /**
     * Retrieve vertex tax codes
     *
     * @return vertexTaxCodes
     */
    public String getVertexTaxCodes()
    {
        wait.waitForElementDisplayed(VERTEX_TAX_CODES);
        String vertexTaxCodes = text.getElementText(VERTEX_TAX_CODES);
        return vertexTaxCodes;
    }

    /**
     * Retrieve Invoice Text Codes
     *
     * @return invoiceTextCodes
     */
    public String getInvoiceTextCodes()
    {
        wait.waitForElementDisplayed(INVOICE_TEXT_CODES);
        String invoiceTextCodes = text.getElementText(INVOICE_TEXT_CODES);
        return invoiceTextCodes;
    }

}
