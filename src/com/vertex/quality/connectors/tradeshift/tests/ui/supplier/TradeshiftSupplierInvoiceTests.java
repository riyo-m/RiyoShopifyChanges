package com.vertex.quality.connectors.tradeshift.tests.ui.supplier;

import com.vertex.quality.connectors.tradeshift.tests.ui.base.TradeshiftUIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TradeshiftSupplierInvoiceTests extends TradeshiftUIBaseTest {

    /**
     * Create an invoice on Tradeshift
     * as a supplier
     *
     * CTRADESHI-362
     * */
    @Test(groups = {"tradeshift"})
    public void createInvoiceTest(){
        createInvoiceSupplier();
    }

    /**
     * Create a credit note on Tradeshift
     * as a supplier
     *
     * CTRADESHI-364
     * */
    @Test(groups = {"tradeshift"})
    public void createCreditNoteTest(){
        createCreditNoteSupplier();
    }

    /**
     * Create a purchase order on Tradeshift
     * as a supplier
     *
     * CTRADESHI-363
     * */
    @Test(groups = {"tradeshift"})
    public void createPOTest(){
        createPurchaseOrderSupplier();
    }

    /**
     * Tests the creation of a multiline invoice
     * as a tradeshift supplier
     *
     * no ticket yet
     * */
    @Test(groups = {"tradeshift"})
    public void multiLineInvoiceTest(){
        createMultiLineInvoiceSupplier();
    }

    /**
     * Tests the recalculation of invoice task
     * after changing the amount of items in the order
     * as a supplier
     *
     * no ticket
     * */
    @Test(groups = {"tradeshift"})
    public void invoiceTaxRecalcTest(){
        String calculatedTax = invoiceTaxRecalcSupplier();
        String expectedTax = "114.00";
        assertEquals(calculatedTax,expectedTax);
    }
}
