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

public class DRetailPOSAdministrativeOriginTests extends DRetailBaseTest{

    /**
     * @TestCase CD365-1549
     * @Description - Create POS order with next day delivery
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_Integration", "posInvoiceTransaction"}, priority = 10)
    public void posTransactionTest(){
        DRetailHomePage homePage = new DRetailHomePage(driver);
        DRetailAllSalesOrderPage allRetailSalesOrderPage = new DRetailAllSalesOrderPage(driver);

        //Navigate to the Transaction Screen, add customer, and product
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.addCustomer("Test Auto");
        allRetailSalesOrderPage.addProduct();
        allRetailSalesOrderPage.addSunglass();
        allRetailSalesOrderPage.selectPinkSunglass();
        allRetailSalesOrderPage.addItemButton();

        //Navigate to transaction page and select overnight shipping and pay cash
        allRetailSalesOrderPage.transactionButton();
        allRetailSalesOrderPage.clickOrders();
        allRetailSalesOrderPage.shipAllSelectedStandardOvernightShipping();
        allRetailSalesOrderPage.selectPayCash();
        allRetailSalesOrderPage.cashAccepted();
        allRetailSalesOrderPage.closeButton();
    }

    public class DFinanceVerifyPOSAdministrativeOriginAddressTests extends DFinanceBaseTest{

            /**
             * @TestCase CD365-1550
             * @Description - Verify Administrative Origin for POS order
             * @Author Mario Saint-Fleur
             */
            @Test(groups = { "FO_Integration"}, dependsOnGroups = {"posInvoiceTransaction"}, priority = 11)
            public void verifyPOSAdministrativeOriginAddressTest(){
                DFinanceHomePage homePage = new DFinanceHomePage(driver);
                DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
                DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
                DFinanceSettingsPage settingPage = new DFinanceSettingsPage(driver);

                settingPage.selectCompany(usrtCompany);

                allSalesOrdersPage= homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                        DFinanceModulePanelLink.ALL_SALES_ORDERS);
                DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

                //Click the most recent order
                allSalesOrdersPage.searchSalesOrderByCustomerName("Test Auto");
                allSalesOrdersSecondPage.selectReturnedOrder("true");
                String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

                //Post the invoice
                allSalesOrdersSecondPage.clickOnInvoiceTab();
                allSalesOrdersSecondPage.generateInvoice();
                allSalesOrdersPage.clickOnOKBtn();
                allSalesOrdersPage.clickOnOKPopUp();
                boolean isOperationCompleted = allSalesOrdersPage.messageBarConfirmation(operationCompleted);
                assertTrue(isOperationCompleted, "Generating Invoice Failed");

                homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                        DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

                //Verify Administrative Destination address
                xmlInquiryPage.getDocumentID(salesOrderNumber);
                xmlInquiryPage.clickOnFirstResponse();
                String response = xmlInquiryPage.getLogResponseValue();
                assertTrue(response.contains(
                                "<AdministrativeOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                                        "\t\t\t\t<StreetAddress1>4807 San Jacinto St</StreetAddress1>\n" +
                                        "\t\t\t\t<City>Houston</City>\n" +
                                        "\t\t\t\t<MainDivision>TX</MainDivision>\n" +
                                        "\t\t\t\t<PostalCode>77004-5620</PostalCode>\n" +
                                        "\t\t\t\t<Country>USA</Country>\n" +
                                        "\t\t\t</AdministrativeOrigin>"
                ));
            }
        }

}
