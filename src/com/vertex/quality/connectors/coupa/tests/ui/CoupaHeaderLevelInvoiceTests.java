package com.vertex.quality.connectors.coupa.tests.ui;

import com.vertex.quality.connectors.coupa.tests.ui.base.CoupaUIBaseTest;
import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Contains various Coupa UI Invoice tests
 *
 * @author alewis
 */
public class CoupaHeaderLevelInvoiceTests extends CoupaUIBaseTest {

    /**
     * Test for submitting a header level invoice
     * from a Purchase order
     *
     * CCOUPA-1892
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void submitHeaderInvoiceFromPOTest() {
        String vertexTax = submitHeaderLevelInvoiceFromPO();
        String expectedTax = "60.00";

        assertEquals(vertexTax,expectedTax);
    }

    /**
     * Non-PO Invoice, Vertex California bill-to address, user generated PA tax amount
     *
     * CCOUPA-1774
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void nonPOInvoiceTest() {
        String expectedPATax = "60.00";

        submitInvoice("6");

        String userTaxValue = getUserTax();

        assertEquals(userTaxValue,expectedPATax);
    }

    /**
     * Full credit note, multi-line, non-PO based, with header level taxes
     *
     * CCOUPA-1762
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void NonPOFullCreditNoteHeaderTest() {
        String expectedPATax = "-84.00";

        submitCreditNote();

        String vertexTaxValue = getVertexTaxTwo();

        assertEquals(vertexTaxValue,expectedPATax);
    }

    /**
     * Partial credit note, multi-line invoice, refund on one item, non-PO based, with header level taxes
     *
     * CCOUPA-1763
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void NonPOPartialCreditNoteHeaderLevelTest() {
        String expectedPATax = "-24.00";

        submitPartialCreditNoteHeaderLevel();

        String vertexTaxValue = getVertexTax();

        assertEquals(vertexTaxValue,expectedPATax);
    }

    /**
     * No charged tax, Invoice, full credit note, non-PO based, with header-level taxation
     *
     * CCOUPA-
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void NonPONoChargedTaxTest(){
        String expectedPATax = "-84.00";

        submitInvoiceWithoutChargedTax();

        String vertexTaxValue = getVertexTaxTwo();

        assertEquals(vertexTaxValue,expectedPATax);
    }

    /**
     * Submits an invoice with a commodity code, makes sure passed correctly in xml
     *
     * CCOUPA-1578
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void commodityCodeTest() {
        String expectedCATax = "90.00";
        String expectedPATax = "60.00";

        commodityCodeInvoice();

        System.out.println("We got here");
    }

    /**
     * Submits invoices via file upload
     *
     * Will only work locally
     *
     * CCOUPA-1846
     * */
    @Test(groups = {"coupa_local"})
    public void bulkInvoiceUploadTest(){
        String expectedVertexTaxValue = "2.70";
        String actualVertexTaxValue = bulkInvoiceUpload();

        assertEquals(actualVertexTaxValue, expectedVertexTaxValue);
    }

    /**
     * Submits an invoice with Summary Charges checkbox disabled
     *
     * CCOUPA-1849
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void summaryChargesNotTaxableTest(){
        String expectedTax = "60.90";
        String actualTax = submitInvoiceSummaryChargesNotTaxable();

        assertEquals(actualTax,expectedTax);
    }

    /**
     * Submits an invoice with Summary Charges checkbox enabled
     *
     * CCOUPA-1848
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void summaryChargesTaxableTest(){
        String expectedTax = "60.90";
        String actualTax = submitInvoiceSummaryChargesTaxable();

        assertEquals(actualTax,expectedTax);
    }

    /**
     * Submits a header level invoice with no user entered
     * tax but with shipping,handling,and misc costs
     *
     * CCOUPA-1817
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void headerLevelNoUserTaxWithMiscCostsTest(){
        String expectedTax = "60.90";
        String actualTax = submitInvoiceNoTaxButWithMiscCharges();

        assertEquals(actualTax,expectedTax);
    }

    /**
     * Insures the tax calc changes when
     * an invoice item price is edited
     *
     * CCOUPA-1911
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void recalcInvoicePriceTest(){
        String calculatedTax = recalcInvoicePriceHeader();
        String expectedTax = "120.00";

        assertEquals(calculatedTax,expectedTax);
    }

    /**
     * Insures the tax calc changes when
     * an invoice item quantity is edited
     *
     * CCOUPA-1912
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void recalcInvoiceQuantityTest(){
        String calculatedTax = recalcInvoiceQuantityHeader();
        String expectedTax = "120.00";

        assertEquals(calculatedTax,expectedTax);
    }

    /**
     * Confirms invoice approval when Vertex tax calc and
     * user entered tax are matched
     *
     * CCOUPA-1913
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void confirmInvoiceMatchTest(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        String confirmationMessage = confirmInvoiceMatchFromPO(invoiceNumber);
        String expectedMessage = "Invoice #"+invoiceNumber +" is approved.";

        assertEquals(confirmationMessage,expectedMessage);
    }

    /**
     * Confirms an invoice is not approved when Vertex tax calc and
     * user entered tax are not matched
     *
     * CCOUPA-1914
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void confirmInvoiceDoesNotMatchTest(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        String errorMessage = confirmInvoiceDoesNotMatchFromPO(invoiceNumber);
        String expectedMessage = "Whoops! The invoice could not be approved as...";

        assertEquals(errorMessage,expectedMessage);
    }

    /**
     * Validates there is no tax expected when a
     * vendor exempt invoice is submitted
     *
     * CCOUPA-1919
     * */
    @Test
    public void taxVendorExemptInvoiceTest(){
        String calulatedTax = taxExemptInvoice();
        String expectedTax = "0.00";

        assertEquals(calulatedTax,expectedTax);
    }
}