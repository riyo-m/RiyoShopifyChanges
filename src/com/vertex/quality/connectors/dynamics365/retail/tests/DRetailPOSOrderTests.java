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

public class DRetailPOSOrderTests extends DRetailBaseTest {

    /**
     * @TestCase - CD365-1260
     * @Description - Create Transaction in Retail and Validate Tax
     * @Author - Mario Saint-Fleur
     */
    @Test(groups = {"FO_Integration", "salesTaxForRetailTransaction"}, priority = 6)
    public void validateSalesTaxForRetailTransactionTest(){

        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        String expectedAmount = "8.70";
        String expectedDepositDueAmount = "$153.70";

        //Select customer, and add item to cart
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Test Auto");
        allRetailSalesOrderPage.addProduct();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();

        //Create and ship an order
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.clickCreateCustomerOrder();
        allRetailSalesOrderPage.shipAllSelectedStandardOvernightShipping();

        //Verify total cost and tax
        String actualTaxAmount = allRetailSalesOrderPage.taxValidation();
        String actualDepositDue = allRetailSalesOrderPage.getDepositDue();
        assertTrue(actualTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount");
        assertTrue(actualDepositDue.contains(expectedDepositDueAmount), "Actual tax amount is not equal to the expected tax amount");

        //Pay cash and complete order
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();

    }

    public class DFinanceValidateAdministrativePhysicalCustomerAddressAndInvoiceResponseForRetailOrderTests extends DFinanceBaseTest {

        /**
         * @TestCase - CD365-1258
         * @Description - Validate that Retail Sales Order has proper address for F&O
         * @Author - Mario Saint-Fleur
         */
        @Test(groups = {"FO_Integration"}, dependsOnGroups = {"salesTaxForRetailTransaction"}, priority = 7)
        public void validateAdministrativePhysicalCustomerAddressAndInvoiceResponseForRetailOrderTest() {
            DFinanceHomePage homePage = new DFinanceHomePage(driver);
            DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);
            DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
            DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

            settingPage.selectCompany("USRT");

            //Navigate to All Sales Orders page
            DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                    DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                    DFinanceModulePanelLink.ALL_SALES_ORDERS);

            allSalesOrdersPage.searchSalesOrderByCustomerName("Test Auto");
            allSalesOrdersSecondPage.applyFilterOnStatus("Open order");
            allSalesOrdersSecondPage.clickOnLatestOrderCreated();

            //Validate Sales Tax amount
            allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
            allSalesOrdersPage.openSalesTaxCalculation();
            String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            String expectedAmount = "8.70";
            assertTrue(calculatedSalesTaxAmount.contains(expectedAmount), "Actual tax amount is not equal to the expected tax amount");

            allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

            //Generate invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.generateInvoice();
            allSalesOrdersPage.clickOnOKBtn();
            allSalesOrdersPage.clickOnOKPopUp();

            //Journal invoice
            allSalesOrdersSecondPage.clickOnInvoiceTab();
            allSalesOrdersSecondPage.journalInvoice();

            //Click on the Posted Sales Tax tab
            String postedSalesTaxInvoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
            allSalesOrdersSecondPage.clickOnPostedSalesTax();

            //Verify "Posted Sales Tax" is negative of original Sales Order
            String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
            assertTrue(postedSalesTaxAmount.equalsIgnoreCase("8.70"),
                    "'Total calculated sales tax amount' value is not expected");

            settingPage.navigateToDashboardPage();

            homePage.navigateToPage(
                    DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                    DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

            //Verify that InvoiceResponse is present in response and Total Tax is correct
            xmlInquiryPage.getDocumentID(postedSalesTaxInvoiceNumber);
            xmlInquiryPage.clickOnFirstResponse();
            String response = xmlInquiryPage.getLogResponseValue();
            assertTrue(response.contains("<InvoiceResponse"));
            assertTrue(response.contains("<TotalTax>8.7</TotalTax>"));
            assertTrue(response.contains("<PhysicalOrigin taxAreaId=\"140890090\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>1650 Premium Outlet Blvd Ste 565</StreetAddress1>\n" +
                    "\t\t\t\t<City>Aurora</City>\n" +
                    "\t\t\t\t<MainDivision>IL</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>60502-2948</PostalCode>\n" +
                    "\t\t\t\t<Country>USA</Country>\n" +
                    "\t\t\t</PhysicalOrigin>\n" +
                    "\t\t\t<AdministrativeOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>4807 San Jacinto St</StreetAddress1>\n" +
                    "\t\t\t\t<City>Houston</City>\n" +
                    "\t\t\t\t<MainDivision>TX</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>77004-5620</PostalCode>\n" +
                    "\t\t\t\t<Country>USA</Country>\n" +
                    "\t\t\t</AdministrativeOrigin>\n" +
                    "\t\t</Seller>\n" +
                    "\t\t<Customer>\n" +
                    "\t\t\t<CustomerCode classCode=\"30\">004021</CustomerCode>\n" +
                    "\t\t\t<Destination taxAreaId=\"390290000\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                    "\t\t\t\t<StreetAddress1>91 Apple Dr</StreetAddress1>\n" +
                    "\t\t\t\t<City>Exton</City>\n" +
                    "\t\t\t\t<MainDivision>PA</MainDivision>\n" +
                    "\t\t\t\t<PostalCode>19341</PostalCode>\n" +
                    "\t\t\t\t<Country>USA</Country>\n" +
                    "\t\t\t</Destination>"
            ));

            xmlInquiryPage.getDocumentID("");
            xmlInquiryPage.selectDocType("Retail transactions");
            xmlInquiryPage.clickOnFirstRequest();
            String request = xmlInquiryPage.getLogRequestValue();

            assertTrue(request.contains("<Product productClass=\"ALL\">81331</Product>\n" +
                    "\t\t\t<Quantity unitOfMeasure=\"Eac\">1</Quantity>\n" +
                    "\t\t\t<ExtendedPrice>130</ExtendedPrice>\n" +
                    "\t\t\t<Discount userDefinedDiscountCode=\"\">\n" +
                    "\t\t\t\t<DiscountAmount>0</DiscountAmount>\n" +
                    "\t\t\t</Discount>"));
            assertTrue(request.contains(
                    "\t\t\t<FlexibleFields>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"3\">91 Apple Dr\n" +
                    "Exton, PA 19341\n" +
                    "USA</FlexibleCodeField>\n"+
                    "\t\t\t\t<FlexibleCodeField fieldId=\"4\">USA</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"5\"></FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"6\">0</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"7\">Retail store</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"8\">100001</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"9\">HOUSTON</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"10\">052</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"11\">000017</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"12\">002</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"13\">016</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"15\">033</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"16\">Apparel</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"17\">000017</FlexibleCodeField>\n" +
                    "\t\t\t\t<FlexibleCodeField fieldId=\"18\">HOUSTON-17</FlexibleCodeField>\n" +
                    "\t\t\t</FlexibleFields>"));
        }
    }
}