package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Mario Saint-Fleur
 * This class is all tests related to Purchase Requisition
 */
@Listeners(TestRerunListener.class)
public class DFinancePurchaseRequisitionTests extends DFinanceBaseTest {

    /**
     * @TestCase CD365-1963
     * @Description - Create Purchase Requisition Using Procurement Category With Maintain Charges
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createPurchaseRequisitionUsingProcurementCategoryWithMaintainChargesTest() {

        final String procurementCategory = "Cleaning";
        final String productName = "CR_001";
        final String quantity = "2";
        final String unit = "ea";
        final String unitPrice = "1000";
        final Double expectedTaxAmount = 120.60;
        final String procurementAndSourcing = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING.getData();
        final String purchaseRequisitions = DFinanceLeftMenuNames.PURCHASE_REQUISITIONS.getData();
        final String allPurchaseRequisitions = DFinanceLeftMenuNames.ALL_PURCHASE_REQUISITIONS.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

        settingsPage.selectCompany(usmfCompany);

        //Navigate to Purchase Requisition
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.expandModuleCategory(purchaseRequisitions);
        homePage.selectModuleTabLink(allPurchaseRequisitions);

        //Create a new PR
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String purchaseRequisitionName = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setPurchaseRequisitionName(purchaseRequisitionName);
        allSalesOrdersPage.clickOnOKBtn();
        String purchaseRequisitionNumber = createPurchaseOrderPage.getDocumentNumber("1");

        //Add a line item
        createPurchaseOrderPage.addItemLineForPurchaseRequisition(procurementCategory, productName, quantity, unit, unitPrice, 0);

        //Add Sales and Item Tax Group
        createPurchaseOrderPage.clickLineDetailsTab("tabLineDetails");
        createPurchaseOrderPage.selectItemSalesGroupType("TaxItemGroup", allItemSalesTaxGroup);
        createPurchaseOrderPage.selectItemSalesGroupType("TaxGroup", salesTaxGroupVertexAP);

        //Navigate to Financials and select maintain charges
        createPurchaseOrderPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false, "Fixed", "10", "VertexAP", "All");
        allSalesOrdersPage.closeCharges();

        //Navigate to Financials and select Sales Tax and verify Sales Tax
        createPurchaseOrderPage.clickOnFinancials();
        createPurchaseOrderPage.clickSalesTaxPurchaseRequisition();
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("140.70"),
                "'Total actual sales tax amount' value is not expected. Actual:"+actualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Submit Workflow
        createPurchaseOrderPage.clickWorkflowForPurchaseRequisition();
        allSalesOrdersSecondPage.clickOnSubmit();
        allSalesOrdersSecondPage.clickOnSubmit();
        settingsPage.navigateToDashboardPage();

        //Navigate to Release Approved Purchase Requisitions and create PO
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROCUREMENT_AND_SOURCING, DFinanceModulePanelCategory.PURCHASE_REQUISITIONS,
                DFinanceModulePanelCategory.APPROVED_PURCHASE_REQUISITION_PROCESSING, DFinanceModulePanelLink.RELEASE_APPROVED_PURCHASE_REQUISITIONS);

        createPurchaseOrderPage.searchCreatedOrder("Purchase requisition", purchaseRequisitionNumber);
        createPurchaseOrderPage.clickPurchaseOrderForPurchaseRequisition();
        createPurchaseOrderPage.setVendorAccountNumberForPurchaseOrderRequisition("1001");
        allSalesOrdersPage.clickOnOKBtn();

        //Get PO number from the confirmation
        String purchaseOrderNumber = createPurchaseOrderPage.getPurchaseOrderNumberFromConfirmation();

        allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Filter PO to find recent PO and verify sales tax
        allPurchaseOrdersPage.searchPurchaseOrder(purchaseOrderNumber);
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertEquals(Double.parseDouble(calculatedSalesTaxAmount), expectedTaxAmount,
                "'Total calculated sales tax amount' value is not expected");
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertEquals(Double.parseDouble(actualSalesTaxAmount), expectedTaxAmount,
                "'Total actual sales tax amount' value is not expected");
    }


    /**
     * @TestCase CD365-1964
     * @Description - Create Purchase Requisition Using Item Number And Verify Flex Field
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
    public void createPurchaseRequisitionUsingItemNumberAndVerifyFlexFieldTest(){

        final String itemNumber = "C0001";
        final Double expectedTaxAmount = 3.10;
        final String procurementAndSourcing = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING.getData();
        final String purchaseRequisitions = DFinanceLeftMenuNames.PURCHASE_REQUISITIONS.getData();
        final String allPurchaseRequisitions = DFinanceLeftMenuNames.ALL_PURCHASE_REQUISITIONS.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(usmfCompany);

        //Navigate to Purchase Requisition
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.expandModuleCategory(purchaseRequisitions);
        homePage.selectModuleTabLink(allPurchaseRequisitions);

        //Create a new PR
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String purchaseRequisitionName = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setPurchaseRequisitionName(purchaseRequisitionName);
        allSalesOrdersPage.clickOnOKBtn();
        String purchaseRequisitionNumber = createPurchaseOrderPage.getDocumentNumber("1");

        //Add a line item
        createPurchaseOrderPage.addItemUsingItemNumberPurchaseRequisition(itemNumber, 1);

        //Navigate to Financials and select Sales Tax and verify Sales Tax
        createPurchaseOrderPage.clickOnFinancials();
        createPurchaseOrderPage.clickSalesTaxPurchaseRequisition();
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("3.10"),
                "'Total actual sales tax amount' value is not expected. Actual: "+actualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Submit Workflow
        createPurchaseOrderPage.clickWorkflowForPurchaseRequisition();
        allSalesOrdersSecondPage.clickOnSubmit();
        allSalesOrdersSecondPage.clickOnSubmit();

        createPurchaseOrderPage.clickRefreshButton("2");

        //Get PO number from details tab of line details
        createPurchaseOrderPage.clickLineDetailsTab("Details");
        String purchaseOrderNumber = createPurchaseOrderPage.getPurchaseOrderNumberFromPurchaseRequisition();

        allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Filter PO to find recent PO and verify sales tax
        allPurchaseOrdersPage.searchPurchaseOrder(purchaseOrderNumber);
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                "'Total calculated sales tax amount' value is not expected");
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                "'Total actual sales tax amount' value is not expected");

        //Navigate to Vertex XML Inquiry and validate Flex Fields
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(purchaseRequisitionNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<FlexibleFields>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"1\">GTester</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"2\">0</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"3\">USD</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"4\">0</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"5\">0</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"6\">13</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"7\">331 10th Ave SE\n" +
                "Minneapolis, MN 55414-19</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"8\">123000</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleNumericField fieldId=\"1\">0</FlexibleNumericField>\n" +
                "\t\t\t\t<FlexibleDateField fieldId=\"1\">2021-12-15</FlexibleDateField>\n" +
                "\t\t\t\t<FlexibleDateField fieldId=\"2\">2021-12-16</FlexibleDateField>\n" +
                "\t\t\t</FlexibleFields>"));
    }

    /**
     * @TestCase CD365-2447
     * @Description - Create Purchase Requisition Using Procurement Category And Verify Unit of Measure
     * @Author Vivek Olumbe
     */
    @Test(groups = {"FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void createPurchaseRequisitionUsingProcurementCategoryAndVerifyUnitOfMeasureTest() {

        final String procurementCategory = "Cleaning";
        final String productName = "CR_001";
        final String quantity = "2";
        final String unit = "bucket";
        final String unitPrice = "1000";
        final Double expectedTaxAmount = 120.60;
        final String procurementAndSourcing = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING.getData();
        final String purchaseRequisitions = DFinanceLeftMenuNames.PURCHASE_REQUISITIONS.getData();
        final String allPurchaseRequisitions = DFinanceLeftMenuNames.ALL_PURCHASE_REQUISITIONS.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(usmfCompany);

        //Navigate to Purchase Requisition
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.expandModuleCategory(purchaseRequisitions);
        homePage.selectModuleTabLink(allPurchaseRequisitions);

        //Create a new PR
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String purchaseRequisitionName = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setPurchaseRequisitionName(purchaseRequisitionName);
        allSalesOrdersPage.clickOnOKBtn();
        String purchaseRequisitionNumber = createPurchaseOrderPage.getDocumentNumber("1");

        //Add a line item
        createPurchaseOrderPage.addItemLineForPurchaseRequisition(procurementCategory, productName, quantity, unit, unitPrice, 0);

        //Add Sales and Item Tax Group
        createPurchaseOrderPage.clickLineDetailsTab("tabLineDetails");
        createPurchaseOrderPage.selectItemSalesGroupType("TaxItemGroup", allItemSalesTaxGroup);
        createPurchaseOrderPage.selectItemSalesGroupType("TaxGroup", salesTaxGroupVertexAP);

        //Navigate to Financials and select maintain charges
        createPurchaseOrderPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false, "Fixed", "10", "VertexAP", "All");
        allSalesOrdersPage.closeCharges();

        //Navigate to Financials and select Sales Tax and verify Sales Tax
        createPurchaseOrderPage.clickOnFinancials();
        createPurchaseOrderPage.clickSalesTaxPurchaseRequisition();
        String actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("140.70"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();

        //Submit Workflow
        createPurchaseOrderPage.clickWorkflowForPurchaseRequisition();
        allSalesOrdersSecondPage.clickOnSubmit();
        allSalesOrdersSecondPage.clickOnSubmit();
        settingsPage.navigateToDashboardPage();

        //Navigate to Vertex XML Inquiry and validate Unit of Measure
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(purchaseRequisitionNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Quantity unitOfMeasure=\"buc\">2</Quantity>"), "Unit of Measure Validation Failed");

        //Navigate to Release Approved Purchase Requisitions and create PO
        homePage.navigateToPage(
                DFinanceLeftMenuModule.PROCUREMENT_AND_SOURCING, DFinanceModulePanelCategory.PURCHASE_REQUISITIONS,
                DFinanceModulePanelCategory.APPROVED_PURCHASE_REQUISITION_PROCESSING, DFinanceModulePanelLink.RELEASE_APPROVED_PURCHASE_REQUISITIONS);

        createPurchaseOrderPage.searchCreatedOrder("Purchase requisition", purchaseRequisitionNumber);
        createPurchaseOrderPage.clickPurchaseOrderForPurchaseRequisition();
        createPurchaseOrderPage.setVendorAccountNumberForPurchaseOrderRequisition("1001");
        allSalesOrdersPage.clickOnOKBtn();

        //Get PO number from the confirmation
        String purchaseOrderNumber = createPurchaseOrderPage.getPurchaseOrderNumberFromConfirmation();

        allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Filter PO to find recent PO and verify sales tax
        allPurchaseOrdersPage.searchPurchaseOrder(purchaseOrderNumber);
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertEquals(Double.parseDouble(calculatedSalesTaxAmount), expectedTaxAmount,
                "'Total calculated sales tax amount' value is not expected");
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertEquals(Double.parseDouble(actualSalesTaxAmount), expectedTaxAmount,
                "'Total actual sales tax amount' value is not expected");

        //Navigate to Vertex XML Inquiry and validate Unit of Measure
        homePage.navigateToPage(
                DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
                DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(purchaseOrderNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<Quantity unitOfMeasure=\"buc\">2.0</Quantity>"), "Unit of Measure Validation Failed");
    }
}
