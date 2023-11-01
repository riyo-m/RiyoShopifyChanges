package com.vertex.quality.connectors.dynamics365.retail.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailAllSalesOrderPage;
import com.vertex.quality.connectors.dynamics365.retail.pages.DRetailHomePage;
import com.vertex.quality.connectors.dynamics365.retail.tests.base.DRetailBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * All tests related to POS customer and tax validation in headquarters
 */


public class DRetailValidateTaxForCustomerTests extends DRetailBaseTest {


    /**
     * @TestCase CD365R-392
     * @Description - Create a POS Order with Two Items and Complete Cash and Carry
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration", "POSWithTwoItems"}, priority = 22)
    public void createPOSOrderAddTwoItemsAndCompleteCashAndCarryTaxTest(){
        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        String expectedAmount = "20.63";

        //Add customer and add products
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Owen Tolley");
        allRetailSalesOrderPage.addProduct();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();
        allRetailSalesOrderPage.transactionButton();

        allRetailSalesOrderPage.productPage();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();
        allRetailSalesOrderPage.transactionButton();

        //Create customer order
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.shipAllSelectedStandardOvernightShipping();

        //Verify total tax
        String actualTaxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(actualTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount: " + expectedAmount);

        //Select Pay in Cash
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();
    }

    public class DFinanceVerifyTaxForPOSOrderTests extends DFinanceBaseTest {
        /**
         * @TestCase CD365-1982
         * @Description - Verify Tax for POS Order When Creating Two Invoices
         * @Author Mario Saint-Fleur
         */
        @Test(groups = { "FO_Integration", "POSWithTwoItems" }, priority = 23)
        public void verifyTaxForPOSOrderWhenCreatingTwoInvoicesTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
            DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

            settingPage.selectCompany(usrtCompany);

            //Navigate to All Sales Order
            allSalesOrdersPage= homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                    DFinanceModulePanelLink.ALL_SALES_ORDERS);
            DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
            allSalesOrdersPage.searchSalesOrderByCustomerName("Owen Tolley");
            allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
            allSalesOrdersSecondPage.clickOnLatestOrderCreated();

            //Post only one invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.generateInvoice();
            allSalesOrdersSecondPage.selectLinesToGenerateInvoice(0);
            allSalesOrdersSecondPage.deleteSelectedLine();
            allSalesOrdersPage.clickOnYesPopUp();
            allSalesOrdersPage.clickOnOKBtn();
            allSalesOrdersPage.clickOnOKPopUp();

            //Click on Posted Sales Tax and get Invoice Number
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.journalInvoice();
            String invoiceNumber1 = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");

            //Navigate to All Sales Order
            allSalesOrdersPage= homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                    DFinanceModulePanelLink.ALL_SALES_ORDERS);
            allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
            allSalesOrdersPage.searchSalesOrderByCustomerName("Owen Tolley");
            allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
            allSalesOrdersSecondPage.clickOnLatestOrderCreated();

            //Post second invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.generateInvoice();
            allSalesOrdersPage.clickOnOKBtn();
            allSalesOrdersPage.clickOnOKPopUp();

            //Click on Posted Sales Tax and get Invoice Number
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.selectInvoiceNumberForInvoiceJournal(2);
            String invoiceNumber2 = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("2");
            String[] splitInvoiceNum = invoiceNumber2.split("-");
            int invoiceNum2 = Integer.parseInt(splitInvoiceNum[1]);
            String invoiceNumString = String.valueOf(invoiceNum2);
            String finalInvoiceNum = "CIV" + "-0" + invoiceNumString;

            //Navigate to Vertex XML Inquiry and verify total tax for both invoices
            homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            xmlInquiryPage.getDocumentID(invoiceNumber1);
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>10.88</TotalTax>"));

            xmlInquiryPage.getDocumentID(finalInvoiceNum);
            xmlInquiryPage.clickOnFirstResponse();
            response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<TotalTax>9.75</TotalTax>"));

        }
    }
}
