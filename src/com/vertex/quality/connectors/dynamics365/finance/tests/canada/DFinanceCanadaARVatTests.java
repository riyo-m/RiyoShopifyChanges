package com.vertex.quality.connectors.dynamics365.finance.tests.canada;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceCanadaARVatTests extends DFinanceBaseTest {

    /**
     * @TestCase CD365-62
     * @Description - create a sales order for US to CAN
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void usToCanadaSalesOrderTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

        final String itemNumber = "1000";
        final String site = "2";
        final String warehouse = "21";
        final String unitPrice = "1000";

        final Double expectedTaxAmount = 140.00;

        settingsPage.selectCompany(usmfCompany);

        //Navigate to All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //Add line items and add Canada destination
        allSalesOrdersPage.openNewSalesOrder();
        allSalesOrdersPage.setCustomerAccount("Test Canada");
        allSalesOrdersPage.clickOkBTN();
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);
        allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 1);

        //Validate sales tax amount
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Navigate to  XML inquiry verify Canadian address and tax amount
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
        DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
        xMLInquiryPage.getDocumentID(salesOrderNumber);
        xMLInquiryPage.clickResponse();
        String response = xMLInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<Destination taxAreaId=\"700030430\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>3790 Canada Way Suite 101</StreetAddress1>\n" +
                "\t\t\t\t<City>Burnaby</City>\n" +
                "\t\t\t\t<MainDivision>BC</MainDivision>\n" +
                "\t\t\t\t<PostalCode>V5G 1G4</PostalCode>\n" +
                "\t\t\t\t<Country>CAN</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyName=\"Canadian Dollar\" isoCurrencyCodeAlpha=\"CAD\" isoCurrencyCodeNum=\"124\">1.0</CurrencyConversion>\n" +
                "\t\t\t</Destination>"));
        assertTrue(response.contains("<TotalTax>140.0</TotalTax>"));
    }

    /**
     * @TestCase CD365-61
     * @Description - Create a sales order for CAN to US
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void canadaToUsSalesOrderTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

        final String itemNumber = "1000";
        final String site = "9";
        final String warehouse = "50";
        final String unitPrice = "1000";

        final Double expectedTaxAmount = 160.00;

        settingsPage.selectCompany(usmfCompany);

        //Navigate to All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //Add line items and add Canadian origin
        allSalesOrdersPage.openNewSalesOrder();
        allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
        allSalesOrdersPage.clickOkBTN();
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);
        allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 1);

        //Validate sales tax amount
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Click "Generate picking list"
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
        allSalesOrdersPage.openPickingList();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        allSalesOrdersPage.closeConfirmationWindow();

        //Click "Post packing slip"
        allSalesOrdersPage.postPackingSlip("1");
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        allSalesOrdersPage.closeConfirmationWindow();

        //Post the invoice
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        allSalesOrdersPage.closeConfirmationWindow();

        //Navigate to  XML inquiry verify Canadian address and tax amount
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
        DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
        xMLInquiryPage.getDocumentID(salesOrderNumber);
        xMLInquiryPage.clickResponse();
        String response = xMLInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<PhysicalOrigin taxAreaId=\"700150190\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>393 Ray Lawson Blvd</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Brampton</City>\n" +
                "\t\t\t\t\t<MainDivision>ON</MainDivision>\n" +
                "\t\t\t\t\t<PostalCode>L6Y 4C5</PostalCode>\n" +
                "\t\t\t\t\t<Country>CAN</Country>\n" +
                "\t\t\t\t</PhysicalOrigin>"));
        assertTrue(response.contains("<TotalTax>160.0</TotalTax>"));
    }

    /**
     * @TestCase CD365-64
     * @Description - Create a sales order for CAN to CAN with invoice
     * @Author Dhruv Patel
     */
    @Ignore
    @Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
    public void canadaToCanadaSalesOrderWithInvoiceTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

        final String itemNumber = "1000";
        final String site = "9";
        final String warehouse = "50";
        final String unitPrice = "1000";

        final Double expectedTaxAmount = 130.00;

        settingsPage.selectCompany(usmfCompany);

        //Navigate to All Sales Orders page
        DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
                DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
                DFinanceModulePanelLink.ALL_SALES_ORDERS);

        //Add line items and add Canadian delivery and origin address
        allSalesOrdersPage.openNewSalesOrder();
        allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
        allSalesOrdersPage.clickOkBTN();
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);
        allSalesOrdersSecondPage.updateDeliveryAddress("Autotest Canada");

        //Validate sales tax amount
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
        allSalesOrdersPage.openSalesTaxCalculation();
        String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

        //Click "Generate picking list"
        allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
        allSalesOrdersPage.openPickingList();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        allSalesOrdersPage.closeConfirmationWindow();

        //Click "Post packing slip"
        allSalesOrdersPage.postPackingSlip("1");
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        allSalesOrdersPage.closeConfirmationWindow();

        //Post the invoice
        allSalesOrdersSecondPage.clickOnInvoiceTab();
        allSalesOrdersSecondPage.generateInvoice();
        allSalesOrdersPage.clickOnOKBtn();
        allSalesOrdersPage.clickOnOKPopUp();
        //Navigate to  XML inquiry verify Canadian address and tax amount
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
        DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
        xMLInquiryPage.getDocumentID(salesOrderNumber);
        xMLInquiryPage.clickResponse();
        String response = xMLInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<PhysicalOrigin taxAreaId=\"700150190\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>393 Ray Lawson Blvd</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Brampton</City>\n" +
                "\t\t\t\t\t<MainDivision>ON</MainDivision>\n" +
                "\t\t\t\t\t<PostalCode>L6Y 4C5</PostalCode>\n" +
                "\t\t\t\t\t<Country>CAN</Country>\n" +
                "\t\t\t\t</PhysicalOrigin>"));
        assertTrue(response.contains("<TotalTax>130.0</TotalTax>"));
    }
}

