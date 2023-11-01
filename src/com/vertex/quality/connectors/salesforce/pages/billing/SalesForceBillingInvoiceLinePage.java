package com.vertex.quality.connectors.salesforce.pages.billing;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;

public class SalesForceBillingInvoiceLinePage extends SalesForceBasePage {

    protected By TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Tax Codes']/following-sibling::td/div");
    protected By VERTEX_TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Vertex Tax Codes']/following-sibling::td/div");
    protected By INVOICE_TEXT_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Invoice Text Codes']/following-sibling::td/div");

    public SalesForceBillingInvoiceLinePage( WebDriver driver )
    {
        super(driver);
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
