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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DRetailPOSMultilineOrderAndValidateTaxTests extends DRetailBaseTest{

    /**
     * @TestCase - CD365R-409
     * @Description - Create POS Order With Multiline Items
     * @Author - Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration", "multiLineItem"}, priority = 24)
    public void addMultiLineItemPOSTest(){

        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        String expectedAmount = "14.10";

        //Select customer, and add item to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Test Auto");
        allRetailSalesOrderPage.addProduct();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();

        //Add 2nd item
        allRetailSalesOrderPage.productPage();
        allRetailSalesOrderPage.addMensShoes();
        allRetailSalesOrderPage.addItemButton();

        //Create and ship an order
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.clickCreateCustomerOrder();
        allRetailSalesOrderPage.shipAllSelectedStandardOvernightShipping();

        //Verify total tax
        String actualTaxAmount = allRetailSalesOrderPage.taxValidation();
        assertTrue(actualTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount: " + expectedAmount);

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }


    public class DFinanceVerifyPOSOrderHasNoTaxDuplicatesTests extends DFinanceBaseTest{

        /**
         * @TestCase CD365-2058 / CD365-2581
         * @Description - Verify POS Order Has No Tax Duplicates When Invoice
         * @Author Mario Saint-Fleur
         */
        @Test(groups = { "FO_Integration"}, dependsOnGroups = {"multiLineItem"}, priority = 25)
        public void verifyPOSOrderHasNoTaxDuplicatesWhenInvoicingTest(){
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
            DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

            settingPage.selectCompany(usrtCompany);

            DFinanceAllSalesOrdersPage allSalesOrdersPage= homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                    DFinanceModulePanelLink.ALL_SALES_ORDERS);

            //Click the most recent order
            allSalesOrdersPage.searchSalesOrderByCustomerName("Test Auto");
            allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
            allSalesOrdersSecondPage.clickOnLatestOrderCreated();
            String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

            //Validate Sales Tax amount
            allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
            allSalesOrdersPage.openSalesTaxCalculation();
            String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            String expectedAmount = "14.10";
            assertTrue(calculatedSalesTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount: " + expectedAmount);

            allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

            //Generate invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.generateInvoice();
            allSalesOrdersPage.clickOnOKBtn();
            allSalesOrdersPage.clickOnOKPopUp();

            //Journal invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.journalInvoice();

            //Click on the Posted Sales Tax tab and Verify tax lines are not duplicates and tax amount
            String postedSalesTaxInvoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
            allSalesOrdersSecondPage.clickOnPostedSalesTax();
            assertTrue(allSalesOrdersSecondPage.verifyPostedSalesTaxLines(), "The values in the posted sales tax lines are duplicates");
            String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            assertTrue(postedSalesTaxAmount.equalsIgnoreCase("14.10"),
                    postedSalesTaxAmount+" "+assertionTotalCalculatedSalesTaxAmount);

            homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Verify Total Tax for Invoice
            xmlInquiryPage.getDocumentID(postedSalesTaxInvoiceNumber);
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains(
                    "<TotalTax>14.1</TotalTax>"
            ));
        }
    }
}
