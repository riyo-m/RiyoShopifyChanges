package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;

public class SalesForceBillingOrderProductPage extends SalesForceBasePage {

    protected By ORDER_NUMBER_LINK = By.xpath("//td[text()='Order']/following-sibling::td/div/a");
    protected By SHIP_TO_STREET_INPUT = By.xpath("//td/label[text()='Tax Street 1 (Override)']/../following-sibling::td/input");
    protected By SHIP_TO_CITY_INPUT = By.xpath("//td/label[text()='Tax City (Override)']/../following-sibling::td/input");
    protected By SHIP_TO_STATE_INPUT = By.xpath("//td/label[text()='Tax State (Override)']/../following-sibling::td/input");
    protected By SHIP_TO_POSTAL_CODE_INPUT = By.xpath("//td/label[text()='Tax Postal Code (Override)']/../following-sibling::td/input");
    protected By SHIP_TO_COUNTRY_INPUT = By.xpath("//td/label[text()='Tax Country (Override)']/../following-sibling::td/input");

    protected By DISCOUNT_PERCENT_INPUT = By.xpath(
            "//label[contains(text(),'DiscountPercent')]//parent::td/following-sibling::td/input");
    protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
            "//label[contains(text(),'DiscountAmount')]//parent::td/following-sibling::td/input");

    protected By ORDER_PROD_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");
    protected By ORDER_PROD_EDIT_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Edit']");

    protected By TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Tax Codes']/following-sibling::td/div");
    protected By VERTEX_TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Vertex Tax Codes']/following-sibling::td/div");
    protected By INVOICE_TEXT_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Invoice Text Codes']/following-sibling::td/div");

    public SalesForceBillingOrderProductPage( WebDriver driver )
    {
        super(driver);
    }


    /**
     * Edit Address on order page based on Address Type
     *
     * @param street
     * @param city
     * @param state
     * @param postalCode
     * @param country
     */
    public void editOrderProductAddress( String street, String city, String state, String postalCode, String country)
    {
        waitForSalesForceLoaded();
        clickEditButton();
        waitForPageLoad();
        setStreet(street);
        setCity(city);
        setState(state);
        setPostalCode(postalCode);
        setCountry(country);
        clickSaveButton();
    }

    /**
     * Set Street Value based on AddressType
     *
     * @param street
     */
    private void setStreet( String street )
    {

        text.enterText(SHIP_TO_STREET_INPUT, street);
    }

    /**
     * Set City Value based on AddressType
     *
     * @param city
     */
    private void setCity( String city )
    {
        text.enterText(SHIP_TO_CITY_INPUT, city);
    }

    /**
     * Set State Value based on AddressType
     *
     * @param state
     */
    private void setState( String state )
    {
        text.enterText(SHIP_TO_STATE_INPUT, state);
    }

    /**
     * Set Postal Code Value based on AddressType
     *
     * @param postalCode
     */
    private void setPostalCode( String postalCode )
    {
        text.enterText(SHIP_TO_POSTAL_CODE_INPUT, postalCode);
    }

    /**
     * Set Country Value based on AddressType
     *
     * @param country
     */
    private void setCountry( String country )
    {
        text.enterText(SHIP_TO_COUNTRY_INPUT, country);
    }

    /**
     * Set discount percent
     * @param discountPercent
     */
    public void setDiscountPercent(String discountPercent)
    {
        if(!discountPercent.equals(""))
        {
            clickEditButton();
            wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
            text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
            clickSaveButton();
            waitForPageLoad();
        }
    }

    /**
     * Set discount amount
     * @param discountAmount
     */
    public void setDiscountAmount(String discountAmount)
    {
        if(!discountAmount.equals(""))
        {
            clickEditButton();
            wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
            text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
            clickSaveButton();
            waitForPageLoad();
        }
    }

    /**
     * CLick on Edit button
     */
    public void clickEditButton()
    {
        wait.waitForElementDisplayed(ORDER_PROD_EDIT_BUTTON);
        click.clickElement(ORDER_PROD_EDIT_BUTTON);
        waitForSalesForceLoaded();
    }
    /**
     * Click on Save button
     */
    public void clickSaveButton( )
    {
        wait.waitForElementDisplayed(ORDER_PROD_SAVE_BUTTON);
        click.clickElement(ORDER_PROD_SAVE_BUTTON);
        waitForSalesForceLoaded();
    }

    /**
     * Navigates back to order page
     */
    public void navigateBackToOrder()
    {
        wait.waitForElementDisplayed(ORDER_NUMBER_LINK);
        click.clickElement(ORDER_NUMBER_LINK);
        waitForPageLoad();
        waitForSalesForceLoaded();
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
