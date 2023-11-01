package com.vertex.quality.connectors.tradeshift.tests.ui.base;

import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.connectors.tradeshift.pages.TradeshiftCreateInvoicePage;
import com.vertex.quality.connectors.tradeshift.pages.TradeshiftBuyerSignInPage;
import com.vertex.quality.connectors.tradeshift.pages.TradeshiftSupplierSignInPage;

/**
 * @author alewis
 */
public class TradeshiftUIBaseTest extends VertexUIBaseTest<TradeshiftBuyerSignInPage> {

    /**
     * Creates a default invoice
     * as a Tradeshift buyer
     * */
    public void createInvoice(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
        signInPage.toAlternativeInvoicePage();
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        invoicePage.switchToLegacyIframe();
        invoicePage.sendInvoiceTo();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop0","laptops","1","1000");
        invoicePage.submitInvoice();
    }

    /**
     * Creates an invoice with multiple line items as a buyer
     * */
    public void createMultiLineInvoice(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
        signInPage.toAlternativeInvoicePage();
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        invoicePage.switchToLegacyIframe();
        invoicePage.sendInvoiceTo();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop0","laptops","1","1000");
        invoicePage.addNewLineItem();
        invoicePage.fillSecondLineItem("monitor0","monitors","1","500");
        invoicePage.submitInvoice();
    }

    /**
     * Creates an invoice with multiple line items as a supplier
     * */
    public void createMultiLineInvoiceSupplier(){
        TradeshiftSupplierSignInPage signInPage = new TradeshiftSupplierSignInPage(driver);
		TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
		signInPage.tradeshiftSupplierSignIn();
		invoicePage.switchToMainIframeSupplier();
        signInPage.toInvoicePageSupplier();
        invoicePage.switchToLegacyIframe();
        invoicePage.fillOutToSupplier();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop","laptop","1","1000");
        invoicePage.addNewLineItem();
        invoicePage.fillSecondLineItem("monitor0","monitors","1","500");
        invoicePage.submitInvoice();
    }

    /**
     * Creates a default invoice as
     * a Tradeshift supplier
     * */
    public void createInvoiceSupplier(){
        TradeshiftSupplierSignInPage signInPage = new TradeshiftSupplierSignInPage(driver);
        signInPage.tradeshiftSupplierSignIn();

        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        invoicePage.switchToMainIframeSupplier();
		signInPage.toInvoicePageSupplier();
		invoicePage.switchToLegacyIframe();
		invoicePage.fillOutToSupplier();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop","laptop","1","1000");
        invoicePage.submitInvoice();
    }

    /**
     * Navigates to and creates a credit note
     * as a Tradeshift buyer
     * */
    public void createCreditNote(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
        signInPage.toCreditNotePage();
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        invoicePage.switchToLegacyIframe();
        invoicePage.sendInvoiceTo();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop0","laptops","1","1000");
        invoicePage.submitInvoice();
    }

    /**
     * Navigates to and creates a credit note
     * as a Tradeshift supplier
     * */
    public void createCreditNoteSupplier(){
        TradeshiftSupplierSignInPage signInPage = new TradeshiftSupplierSignInPage(driver);
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        signInPage.tradeshiftSupplierSignIn();
        invoicePage.switchToMainIframeSupplier();
		signInPage.toCreditNotePageSupplier();
		invoicePage.switchToLegacyIframe();
		invoicePage.fillOutToSupplier();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop","laptop","1","1000");
        invoicePage.submitInvoice();
    }

    /**
     * Navigates to and creates a purchase order
     * as a Tradeshift buyer
     * */
    public void createPurchaseOrder(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
        signInPage.toPurchaseOrderPage();
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        invoicePage.sendInvoiceTo();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop0","laptops","1","1000");
        invoicePage.submitInvoice();
    }

    /**
     * Navigates to and creates a purchase order
     * as a Tradeshift supplier
     * */
    public void createPurchaseOrderSupplier(){
        TradeshiftSupplierSignInPage signInPage = new TradeshiftSupplierSignInPage(driver);
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        signInPage.tradeshiftSupplierSignIn();
        invoicePage.switchToMainIframeSupplier();
        signInPage.toPurchaseOrderSupplier();
        invoicePage.switchToLegacyIframe();
        invoicePage.fillOutToSupplier();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop","laptop","1","1000");
        invoicePage.submitInvoice();
    }

    /**
     * Creates an invoice from a purchase order
     * as a Tradeshift buyer
     * */
    public void createInvoiceFromPO(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
        signInPage.toInvoiceFromPOPage();
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
		invoicePage.switchToMainIframeBuyer();
		invoicePage.clickPO();
        invoicePage.clickUseAsDraft();
        invoicePage.switchToLegacyIframe();
        invoicePage.fillOutToFromPO();
        invoicePage.fillInvoiceNumber();
        invoicePage.submitInvoice();
    }

    /**
     * creates and invoice and retrieves the tax calc
     * then changes the amount of items in the invoice
     * to ensure a new tax calculation as a buyer
     *
     * @return the updated tax
     * */
    public String invoiceTaxRecalc(){
        TradeshiftBuyerSignInPage signInPage = new TradeshiftBuyerSignInPage(driver);
        signInPage.tradeshiftSignIn();
        signInPage.toAlternativeInvoicePage();
        TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
        invoicePage.switchToLegacyIframe();
        invoicePage.sendInvoiceTo();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop0","laptops","1","1000");
        String firstTax = invoicePage.getInvoiceTax();
        invoicePage.addNewLineItem();
        invoicePage.fillSecondLineItem("monitor0","monitors","1","500");
        String secondTax = invoicePage.getInvoiceTax();
        invoicePage.submitInvoice();
        if(firstTax.equals(secondTax)){
            return firstTax;
        }
        return secondTax;
    }

    /**
     * creates and invoice and retrieves the tax calc
     * then changes the amount of items in the invoice
     * to ensure a new tax calculation as a supplier
     *
     * @return the updated tax
     * */
    public String invoiceTaxRecalcSupplier(){
        TradeshiftSupplierSignInPage signInPage = new TradeshiftSupplierSignInPage(driver);
		TradeshiftCreateInvoicePage invoicePage = new TradeshiftCreateInvoicePage(driver);
		signInPage.tradeshiftSupplierSignIn();
		invoicePage.switchToMainIframeSupplier();
        signInPage.toInvoicePageSupplier();
        invoicePage.switchToLegacyIframe();
        invoicePage.fillOutToSupplier();
        invoicePage.fillInvoiceNumber();
        invoicePage.fillItem("laptop","laptop","1","1000");
        String firstTax = invoicePage.getInvoiceTax();
        invoicePage.addNewLineItem();
        invoicePage.fillSecondLineItem("monitor0","monitors","1","500");
        String secondTax = invoicePage.getInvoiceTax();
        invoicePage.submitInvoice();
        if(firstTax.equals(secondTax)){
            return firstTax;
        }
        return secondTax;
    }
}
