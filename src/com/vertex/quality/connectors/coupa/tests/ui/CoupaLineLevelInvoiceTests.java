package com.vertex.quality.connectors.coupa.tests.ui;

import com.vertex.quality.connectors.coupa.tests.ui.base.CoupaUIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Contains various Coupa UI Invoice tests
 *
 * @author alewis
 */
public class CoupaLineLevelInvoiceTests extends CoupaUIBaseTest {

    /**
     * Test for submitting a line level invoice
     * from a Purchase order
     *
     * CCOUPA-1893
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void submitLineInvoiceFromPOTest() {
        String vertexTax = submitLineLevelInvoiceFromPO();
        String expectedTax = "60.00";

        assertEquals(vertexTax,expectedTax);
    }


    /**
     * Full credit note, multi-line, non-PO based, with line level taxes
     *
     * CCOUPA-1764
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void NonPOFullCreditNoteLineLevelTest() {
        String expectedPATax = "-60.00";

        submitCreditNoteLineLevel();

        String userTaxValue = getUserTax();

        assertEquals(userTaxValue,expectedPATax);
    }


    /**
     * Partial credit note, multi-line invoice, refund on one item, non-PO based, with line-level taxation
     *
     * CCOUPA-1765
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void NonPOPartialCreditNoteLineLevelTest() {
        String expectedPATax = "-24.00";

        submitPartialCreditNoteLineLevel();

        String vertexTaxValue = getFirstVertexTax();

        assertEquals(vertexTaxValue, expectedPATax);
    }

    /**
     * Set tolerance to 5% difference, submits invoice with over 5% difference between Vertex and User tax
     *
     * CCOUPA-1586
     */
    @Test(groups = {"coupa","coupa_ui"})
    public void placeOrderOnHoldTest() {
        String toleranceMessage = "Whoops! The invoice could not be approved as...";

        submitOnHoldInvoice();

        String message = getOnHoldMessage();

        assertEquals(message,toleranceMessage);
    }

    /**
     * Submits a line level invoice with no vendor tax
     *
     * CCOUPA-1841
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void lineLevelNoVendorTaxTest() {
        String expectedCalculatedTax = "60.00";
        String actualCalculatedTax = submitInvoiceNoVendorTax();

        assertEquals(actualCalculatedTax,expectedCalculatedTax);
    }

    /**
     * Test to ensure that when selecting line
     * level taxation, adding a header tax does not
     * mess up the tax calculation
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void lineLevelWithHeaderTaxationTest(){
        String expectedTax = "60.00";
        String actualTax = submitLineLevelWithHeaderCharge();

        assertEquals(expectedTax,actualTax);
    }

    /**
     * Creates and edits an invoice to insure the tax calc
     * changes when an invoice item's price is edited
     *
     * CCOUPA-1909
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void recalcInvoicePriceTest(){
        String calculatedTax = recalcInvoicePrice();
        String expectedTax = "120.00";

        assertEquals(calculatedTax,expectedTax);
    }

    /**
     * Creates and edits an invoice to insure the tax calc
     * changes when an invoice item's quantity is edited
     *
     * CCOUPA-1910
     * */
    @Test(groups = {"coupa","coupa_ui"})
    public void recalcInvoiceQuantityTest(){
        String calculatedTax = recalcInvoiceQuantity();
        String expectedTax = "120.00";

        assertEquals(calculatedTax,expectedTax);
    }
}