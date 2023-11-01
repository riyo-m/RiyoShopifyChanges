package com.vertex.quality.connectors.tradeshift.tests.ui.buyer;

import com.vertex.quality.connectors.tradeshift.tests.ui.base.TradeshiftUIBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TradeshiftBuyerInvoiceTests extends TradeshiftUIBaseTest {

    /**
     * Create a basic invoice as a buyer
     *
     * CTRADESHI-356
     * */
    @Test(groups = {"tradeshift"})
    public void defaultInvoiceTest(){
        createInvoice();
    }

    /**
     * Create a credit Note as a buyer
     *
     * CTRADESHI-359
     * */
    @Test(groups = {"tradeshift"})
    public void creditNoteTest(){
        createCreditNote();
    }

    /**
     * Create a purchase order as a buyer
     *
     * CTRADESHI-358
     * */
    @Test(groups = {"tradeshift"})
    public void purchaseOrderTest(){
        createPurchaseOrder();
    }

    /**
     * Create an invoice from purchase order
     * as a buyer
     *
     * CTRADESHI-357
     * */
    @Test(groups = {"tradeshift"})
    public void invoiceFromPOTest(){
        createInvoiceFromPO();
    }

    /**
     * Creates a multiline invoice as a buyer
     *
     * no ticket
     * */
    public void multiLineInvoiceTest(){
        createMultiLineInvoice();
    }

    /**
     * Tests the recalculation of invoice task
     * after changing the amount of items in the order
     * sd s buyer
     *
     * no ticket
     * */
    public void invoiceTaxRecalcTest(){
        String calculatedTax = invoiceTaxRecalc();
        String expectedTax = "114.00";
        assertEquals(calculatedTax,expectedTax);
    }

}
