package com.vertex.quality.connectors.dynamics365.finance.tests.ap_vat;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceXMLValidation;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceAPVatPurchaseOrderTests extends DFinanceBaseTest {

    final String procurementAndSourcing = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING.getData();
    final String setup = DFinanceLeftMenuNames.SETUP.getData();
    final String procurementAndSourcingParameters = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING_PARAMETERS.getData();
    Boolean advTaxGroup = false;
    Boolean workFlowStatus = false;
    Boolean activateChangeManagement = false;


    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        advTaxGroup = false;
        workFlowStatus = false;
        activateChangeManagement = false;
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        settingsPage.selectCompany(germanCompanyDEMF);
        homePage.refreshPage();
        toggleONOFFAdvanceTaxGroup(false);
        settingsPage.toggleFetchMultipleRegistrationIDs(false);
        //Disable Workflow
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        homePage.navigateToAccountsPayableWorkflowsPage();
        createPurchaseOrderPage.updateWorkFlowStatus(false);

        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.expandModuleCategory(setup);
        homePage.selectModuleTabLink(procurementAndSourcingParameters);
        createPurchaseOrderPage.activateChangeManagementStatus(false);
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        if (advTaxGroup) {
            homePage.refreshPage();
            toggleONOFFAdvanceTaxGroup(false);
        }
        if(workFlowStatus) {
            //Disable Workflow
            DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
            homePage.navigateToAccountsPayableWorkflowsPage();
            createPurchaseOrderPage.updateWorkFlowStatus(false);
        }
        if(activateChangeManagement){
            DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
            homePage.clickOnNavigationPanel();
            homePage.selectLeftMenuModule(procurementAndSourcing);
            homePage.expandModuleCategory(setup);
            homePage.selectModuleTabLink(procurementAndSourcingParameters);
            createPurchaseOrderPage.activateChangeManagementStatus(false);
        }
    }
    
    /**
     * @TestCase CD365-1102
     * @Description - Create an AP VAT Purchase Order for Germany to Germany customer
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void apVatGermanyToGermanyPurchaseOrderAndVendorInvoiceTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 4.56;

        //================script implementation========================
        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Click on "New" option
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

        // enter vendor account number
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);

        // click Ok button
        createPurchaseOrderPage.clickOkButton();

        // click Header link
        createPurchaseOrderPage.clickOnHeader();

        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));

        // set Sales Tax Group as VertexAP
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);

        // click Lines link
        createPurchaseOrderPage.clickOnLines();

        // add a line item to the PO
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);

        //navigate to "Purchase"  --> Sales Tax page
        createPurchaseOrderPage.navigateToSalesTaxPage();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        // navigate to Confirmation page to confirm the PO
        createPurchaseOrderPage.navigateToConfirmationPage();

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();

        // set product number
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.navigateToInvoicePage();

        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();

        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        // Post Invoice
        createPurchaseOrderPage.clickPostOption();

        // validate invoice posting
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //navigate to "Purchase"  --> Sales Tax page
        createPurchaseOrderPage.navigateToSalesTaxPage();

        //Verify the "Total calculated sales tax amount" value
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        //Verify the "Total actual sales tax amount" value
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        // search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

        // click Posted Sales Tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        //Navigate to XML inquiry
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>4.56</TotalTax>"));

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();

        //Validate that the Buyer, Vendor, etc is correct
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains(xmlValidation.getGermanyBuyerXMLForPO()),
                requestTypeNotExpected);


        assertTrue(request.contains(xmlValidation.getGermanyVendorClassXML()),
                requestTypeNotExpected);
    }

    /**
     * @TestCase CD365-1105
     * @Description - Create an AP VAT Purchase Order for France to Germany customer
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void apVatFranceToGermanyPurchaseOrderAndVendorInvoiceTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        String salesTaxGroup = DFinanceConstantDataResource.VERTEXADVA.getData();

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";
        final double expectedTaxAmount = 0.00;

        //================script implementation========================
        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Click on "New" option
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

        // enter vendor account number
        createPurchaseOrderPage.setVendorAccountNumber(franceVendor);

        // click Ok button
        createPurchaseOrderPage.clickOkButton();

        // click Header link
        createPurchaseOrderPage.clickOnHeader();

        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));

        // set Sales Tax Group as VertexAdva
        createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

        // click Lines link
        createPurchaseOrderPage.clickOnLines();

        // add a line item to the PO
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);

        //navigate to "Purchase"  --> Sales Tax page
        createPurchaseOrderPage.navigateToSalesTaxPage();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                calculatedSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);

        //Verify the "Total actual sales tax amount" value
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                actualSalesTaxAmount+" : "+assertionTotalActualSalesTaxAmount);

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        // navigate to Confirmation page to confirm the PO
        createPurchaseOrderPage.navigateToConfirmationPage();

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();

        // set product number
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.navigateToInvoicePage();

        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();

        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Verify and set Accrual tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        createPurchaseOrderPage.clickAdjustmentSalesTax();
        String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();
        // Post Invoice
        createPurchaseOrderPage.clickPostOption();

        // validate invoice posting
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(franceVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //navigate to "Purchase"  --> Sales Tax page
        createPurchaseOrderPage.navigateToSalesTaxPage();

        //Verify the "Total calculated sales tax amount" value
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        //Verify the "Total actual sales tax amount" value
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);

        //Click on "OK" button
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        // search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

        // click Posted Sales Tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == 0.00,
                assertionTotalCalculatedSalesTaxAmount);

        //Navigate to XML inquiry
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate that the Invoice is "Vendor Invoice Verification" type
        String expectedType = accrualRequest;

        //Select the Correct Response from the list
        xmlInquiryPage.getDocumentID(invoiceNumber);
        String actualType = xmlInquiryPage.getDocumentType();
        assertTrue(actualType.equals(expectedType),
                documentTypeNotExpected);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>4.56</TotalTax>"));

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();

        //Validate that the Buyer, Vendor, etc is correct
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains(xmlValidation.getGermanyBuyerXMLForPO()),
                requestTypeNotExpected);

        assertTrue(request.contains("<Vendor>\n" +
                        "\t\t\t<VendorCode classCode=\"FR_SI_000001\">FR_SI_000001</VendorCode>\n" +
                        "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                        "\t\t\t\t<StreetAddress1>2 av Carnot</StreetAddress1>\n" +
                        "\t\t\t\t<City>Paris</City>\n" +
                        "\t\t\t\t<MainDivision>IF</MainDivision>\n" +
                        "\t\t\t\t<PostalCode>91940</PostalCode>\n" +
                        "\t\t\t\t<Country>FRA</Country>\n" +
                        "\t\t\t</PhysicalOrigin>\n" +
                        "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                        "\t\t\t\t<StreetAddress1>2 av Carnot</StreetAddress1>\n" +
                        "\t\t\t\t<City>Paris</City>\n" +
                        "\t\t\t\t<MainDivision>IF</MainDivision>\n" +
                        "\t\t\t\t<PostalCode>91940</PostalCode>\n" +
                        "\t\t\t\t<Country>FRA</Country>\n" +
                        "\t\t\t</AdministrativeOrigin>\n" +
                        "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"false\" isoCountryCode=\"FRA\">\n" +
                        "\t\t\t\t<TaxRegistrationNumber>12345678912</TaxRegistrationNumber>\n" +
                        "\t\t\t</TaxRegistration>"),
                requestTypeNotExpected);
    }

    /**
     * @TestCase CD365-4156
     * @Description - Create a Purchase Order and validate Buyer XML Request with Legal Entity Established enabled
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
    public void validateBuyerXmlRequestWithLegalEntityEstablishedForPurchaseOrderTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        String salesTaxGroup = DFinanceConstantDataResource.VERTEXADVA.getData();

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;

        //================script implementation========================
        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Click on "New" option
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

        // enter vendor account number
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);

        createPurchaseOrderPage.clickOkButton();

        createPurchaseOrderPage.clickOnHeader();

        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));

        // set Sales Tax Group as VertexAP
        createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

        createPurchaseOrderPage.clickOnLines();

        // add a line item to the PO
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);

        createPurchaseOrderPage.navigateToSalesTaxPage();

        //Verify the "Total calculated sales tax amount" value
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);

        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        // navigate to Confirmation page to confirm the PO
        createPurchaseOrderPage.navigateToConfirmationPage();

        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();

        // set product number
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));

        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.navigateToInvoicePage();

        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();

        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));


        //Verify and set Accrual tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("2.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 2.00;
        assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();
        // Post Invoice
        createPurchaseOrderPage.clickPostOption();

        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));


        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        // search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xmlInquiryPage.getDocumentID(invoiceNumber);

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();

        //Validate that the Buyer, Vendor, etc is correct
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains(xmlValidation.getGermanyBuyerXMLForPO()),
                requestTypeNotExpected);

        assertTrue(request.contains(xmlValidation.getGermanyVendorClassXML()),
                requestTypeNotExpected);
    }

    /**
     * @TestCase CD365-1181
     * @Description - Verify Administrative Destination for Purchase Order and Vendor Invoice for Germany to Germany
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyAdministrativeDestinationForGermanyToGermanyPurchaseOrderToVendorInvoiceTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        String salesTaxGroup = DFinanceConstantDataResource.VERTEXADVA.getData();

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Create a new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);

        //Verify Total tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        // navigate to Confirmation page to confirm the PO
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        // Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify Total tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        // search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

        // click Invoice number
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Request from the list
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Buyer>\n" +
                "\t\t\t<Company>DEMF</Company>\n" +
                "\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</Destination>\n" +
                "\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</AdministrativeDestination>\n" +
                "\t\t</Buyer>"
        ));
    }

    /**
     * @TestCase CD365-1301
     * @Description - Verify Tax Amount for Purchase Order Germany to Germany using Advanced Sales Tax Group
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyTaxAmountUsingAdvancedTaxGroupForGermanyToGermanyPurchaseOrderAndVendorInvoiceTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);


        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;
        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;
        //Create a new Purchase Order
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);

        //Verify Total tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        // navigate to Confirmation page to confirm the PO
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        // navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        // Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify Total tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains(vsdoSalesTaxCode), "Sales Tax Code not contain VAT_CODE");
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify the tax amount
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>4.56</TotalTax>"));
    }

    /**
     * @TestCase CD365-1304
     * @Description - Verify Vendor Over Charged With Fully Recoverable Charge
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorOverChargedWithFullyRecoverableChargeAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage =new DFinanceOpenVendorInvoicesPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Verify and set Accrual tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("100.55");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 100.55;
        assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        Double expectedPostedSalesTaxAmount = 100.55;
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedPostedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains(vsdoSalesTaxCode), "Sales Tax Code not contain V_SDO_19_G");

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        String expectedType = "Accrual";

        //Verify Accrual Request is called, Total tax, Input Tax Amount and postToJournal is not represent in Response
        xmlInquiryPage.getDocumentID(invoiceNumber);
        String actualType = xmlInquiryPage.getDocumentType();
        assertTrue(actualType.equals(expectedType),
                requestTypeNotExpected);

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>100.55</TotalTax>"));
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<AccrualRequest"));
        assertTrue(!request.contains("<AccrualRequest postJournal"));
        assertTrue(request.contains(xmlValidation.getGermanyInputTaxOverChargedXML()),
                "Input Tax out is not as expected");
    }

    /**
     * @TestCase CD365-1344
     * @Description - Verify Vendor Under Charged With Partially Recoverable Charge
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorUnderChargedWithPartiallyRecoverableChargeAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage =new DFinanceOpenVendorInvoicesPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;
        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Create new Purchase Order and set Item tax as VTXPartial
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexPartialTaxGroup);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexPartialTaxGroup);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Verify and set Accrual tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("5.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        createPurchaseOrderPage.clickAdjustmentSalesTax();
        createPurchaseOrderPage.clickApplyActualAmounts();
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 5.00;
        assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        Double expectedPostedSalesTaxAmount = 5.00;
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedPostedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains(vsdoSalesTaxCode), "Sales Tax Code not contain V_SDO_19_G");

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        String expectedRequestType = "Accrual";

        //Verify Accrual Request is called, Total tax, Input Tax Amount and postToJournal is not represent in Response
        xmlInquiryPage.getDocumentID(invoiceNumber);
        String actualType = xmlInquiryPage.getDocumentType();
        assertTrue(actualType.equals(expectedRequestType),
                requestTypeNotExpected);

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>5.0</TotalTax>"));
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<AccrualRequest"));
        assertTrue(!request.contains("<AccrualRequest postJournal"));
        assertTrue(request.contains(xmlValidation.getGermanyInputTaxXML()),
                "Input Tax out is not as expected");
    }

    /**
     * @TestCase CD365-1345
     * @Description - Verify Vendor Under Charged With Non-Recoverable Charge
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorUnderChargedWithNonRecoverableChargeAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;
        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Create new Purchase Order and set Item Sales Group as VTXNoRecov
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexNonRecoverable);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexNonRecoverable);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Verify and set Accrual tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("5.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        createPurchaseOrderPage.clickAdjustmentSalesTax();
        createPurchaseOrderPage.clickApplyActualAmounts();
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 5.00;
        assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        Double expectedPostedSalesTaxAmount = 5.00;
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedPostedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains(vsdoSalesTaxCode), "Sales Tax Code not contain V_SDO_19_G");

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        String expectedType = "Accrual";

        //Verify Accrual Request is called, Total tax, Input Tax Amount and postToJournal is not represent in Response
        xmlInquiryPage.getDocumentID(invoiceNumber);
        String actualType = xmlInquiryPage.getDocumentType();
        assertTrue(actualType.equals(expectedType),
                requestTypeNotExpected);

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>5.0</TotalTax>"));
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<AccrualRequest"));
        assertTrue(!request.contains("<AccrualRequest postJournal"));
        assertTrue(request.contains(xmlValidation.getGermanyInputTaxXML()),
                "Input Tax out is not as expected");
    }

    /**
     * @TestCase CD365-1346
     * @Description - Verify Vendor Reversed Charged With Partially Recoverable Charge
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_Inprogress" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorReversedChargedWithPartiallyRecoverableChargeAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 9.12;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(franceVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexPartialTaxGroup);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexPartialTaxGroup);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to totals and verify Input/Output VAT
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnTotalsButton();
        String inputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(7);
        String expectedAmountInputField = "9.12";
        assertTrue(inputFieldValue.contains(expectedAmountInputField),
                "Values do not match for Input Field");
        String outputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(8);
        String expectedAmountOutputField = "0.00";
        assertTrue(outputFieldValue.contains(expectedAmountOutputField),
                "Values do not match for Output Field");
        createPurchaseOrderPage.clickCloseBtn();

        //Navigate to Sales Tax and recalculate actual tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        createPurchaseOrderPage.clickAdjustmentSalesTax();
        createPurchaseOrderPage.clickApplyActualAmounts();
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        String actualAccrualSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
        Double expectedAccrualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualAccrualSalesTaxAmount) == expectedAccrualSalesTaxAmount,
                assertionTotalAccrualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Navigate to totals and verify Input/Output VAT
        createPurchaseOrderPage.clickOnTotalsButton();
        inputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(7);
        expectedAmountInputField = "7.30";
        assertTrue(inputFieldValue.contains(expectedAmountInputField),
                "Values do not match for Input Field");
        outputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(8);
        expectedAmountOutputField = "9.12";
        assertTrue(outputFieldValue.contains(expectedAmountOutputField),
                "Values do not match for Output Field");
        createPurchaseOrderPage.clickCloseBtn();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(franceVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        Double expectedPostedSalesTaxAmount = 0.00;
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedPostedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains(vrcgSalesTaxCode), "Sales Tax Code not contain V_RC_G");

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        String expectedType ="Accrual";

        //Verify Accrual Request is called, Total tax, Input Tax Amount and postToJournal is not represent in Response
        xmlInquiryPage.getDocumentID(purchaseOrder);
        String actualType = xmlInquiryPage.getRequestType();
        assertTrue(actualType.equals(expectedType),
                requestTypeNotExpected);

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>9.12</TotalTax>"));
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<AccrualRequest"));
        assertTrue(!request.contains("<AccrualRequest postJournal"));
        assertTrue(request.contains(xmlValidation.getGermanyInputTaxXMLForReversedCharge()),
                "Input Tax out is not as expected");
    }

    /**
     * @TestCase CD365-1462
     * @Description - Verify exempt class description for VTXPClass for purchase order AP Vat
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyExemptClassDescriptionForVTXPClassPurchaseOrderAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedTaxAmount = 0.00;
        final int expectedLinesCount = 2;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order with Item Sales Tax Group as VTXPClass
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vtxpClassItemSalesTaxGroup);
        createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vtxpClassItemSalesTaxGroup);

        //Validate Tax Lines and Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();

        int actualLinesCount = createPurchaseOrderPage.getTempSalesTaxTransactionLinesCount();
        assertTrue(actualLinesCount == expectedLinesCount,
                String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
                        expectedLinesCount, actualLinesCount));

        List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

        for (int i = 1; i <= actualLinesCount; i++) {
            Map<String, String> lineDataMap = createPurchaseOrderPage.getSalesTaxAmount(String.format("%s", i));

            VertexLogger.log(String.format("Line: %s -- %s", i, lineDataMap));

            allLinesDataList.add(lineDataMap);
        }

        List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line5.getLineDataMap());
        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line6.getLineDataMap());

        Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

        Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

        boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
                expectedAllLinesSet);

        if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
            assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
                    expectedAllLinesSet, actualAllLinesSet));
        }

        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Verify sales tax
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalAccrualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Set invoice number
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify sales tax amount
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        Double expectedPostedSalesTaxAmount = 0.00;
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedPostedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify tax amount
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));
    }

    /**
     * @TestCase `CD365-1461`
     * @Description - Verify VAT Number and freight charge for purchase class during accrual request purchase order AP Vat
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVATNumberAndFreightChargeForPurchaseClassDuringAccrualRequestPurchaseOrderAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedSalesTaxAmount = 8.36;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);

        createPurchaseOrderPage.clickOnTab("Purchase");
        createPurchaseOrderPage.clickHeaderMaintainCharges();
        allSalesOrdersPage.addCharges("INSTALL", false,"Fixed","10","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Recalculate Sales Tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String  actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) ==  expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        // Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Tax Registration is present for Install, Item, and Freight
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t\t<TaxRegistrationNumber>DE123456789</TaxRegistrationNumber>\n" +
                "\t\t\t\t</TaxRegistration>"));
        assertTrue(request.contains("<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t\t<TaxRegistrationNumber>DE123456789</TaxRegistrationNumber>\n" +
                "\t\t\t\t</TaxRegistration>\n" +
                "\t\t\t</Vendor>\n" +
                "\t\t\t<Purchase purchaseClass=\"All\">D0003</Purchase>"));
    }

    /**
     * @TestCase CD365-1538
     * @Description - Verify vendor exchange rate and invoice for AP VAT
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorExchangeRateAndInvoiceAmountForAPVATTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        final double expectedSalesTaxAmount = 4.56;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);

        //Validate Tax Lines and Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to totals and verify Input/Output VAT
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        createPurchaseOrderPage.clickOnTotalsButton();
        String inputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(7);
        String expectedAmountInputField = "4.56";
        assertTrue(inputFieldValue.contains(expectedAmountInputField),
                "Values do not match for Input Field");
        String outputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(8);
        String expectedAmountOutputField = "0.00";
        assertTrue(outputFieldValue.contains(expectedAmountOutputField),
                "Values do not match for Output Field");
        createPurchaseOrderPage.clickCloseBtn();

        //Navigate to Sales Tax and recalculate actual tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("2.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 2.00;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        String actualAccrualSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
        Double expectedAccrualSalesTaxAmount = 2.00;
        assertTrue(Double.parseDouble(actualAccrualSalesTaxAmount) == expectedAccrualSalesTaxAmount,
                assertionTotalAccrualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Navigate to totals and verify Input/Output VAT
        createPurchaseOrderPage.clickOnTotalsButton();
        inputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(7);
        expectedAmountInputField = "2.00";
        assertTrue(inputFieldValue.contains(expectedAmountInputField),
                "Values do not match for Input Field");
        outputFieldValue = createPurchaseOrderPage.getTotalsInputFieldValue(8);
        expectedAmountOutputField = "0.00";
        assertTrue(outputFieldValue.contains(expectedAmountOutputField),
                "Values do not match for Output Field");
        createPurchaseOrderPage.clickCloseBtn();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Currency Conversion is correct
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>"));
    }

    /**
     * @TestCase CD365-1536
     * @Description - Verify posting date and tax date for purchase order AP Vat
     * @Author - Shruti Jituri
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyTaxDatePostingDateAPVATTest(){
        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";
        final double expectedSalesTaxAmount = 6.46;
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        //Create a new purchase order
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT",false, "Fixed","10","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //navigate to "Purchase"  --> Sales Tax page
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String  actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) ==  expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        System.out.println("invoiceNumber"+invoiceNumber);

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Get Posting Date and Invoice Date
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();
        String postingDate = createPurchaseOrderPage.getPostingDate();
        String invoiceDate = createPurchaseOrderPage.getInvoiceDate();

        // Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));
        settingsPage.navigateToDashboardPage();

        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("taxDate=\""+getDesiredDate(0)+"\""));
        assertTrue(request.contains("postingDate=\""+getDesiredDate(0)+"\""));
    }


    /** @TestCase CD365-1537
     * @Description - Verify flex fields is present accrual request for PO
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyFlexFieldsIsPresentInAccrualRequestForPOTest() {
        String quantity = "2";
        String site = "1";
        String warehouse = "11";
        String unitPrice = "12";
        final double expectedTaxAmount = 4.56;

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);


        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        //Create new Purchase Order
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Confirm Purchase order
        createPurchaseOrderPage.navigateToConfirmationPage();
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Add Product Receipt
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Set invoice number
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify Posted Sales Tax on the Vendor Invoice
        createPurchaseOrderPage.clickOnTab("Invoice");
        allSalesOrdersSecondPage.journalInvoice();
        allSalesOrdersSecondPage.clickOnPostedSalesTax();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                "'Total calculated sales tax amount' value is not expected");

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify flex fields
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<FlexibleFields>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"1\">0</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"2\">DEU</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"3\">Amtsstrasse 23\n" +
                "10115 Berlin\n" +
                "DEU</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"5\">0</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleCodeField fieldId=\"6\">GHK-00&amp;000</FlexibleCodeField>\n" +
                "\t\t\t\t<FlexibleDateField fieldId=\"1\">2021-07-22</FlexibleDateField>\n" +
                "\t\t\t\t<FlexibleDateField fieldId=\"2\">2021-07-22</FlexibleDateField>\n" +
                "\t\t\t</FlexibleFields>"));
    }

    /**
     * @TestCase CD365-1563
     * @Description - Verify Posted Sales Tax is Zero for GB transaction
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyPostedSalesTaXForGBSICompanyTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String itemNumber = "S0001";

        final double expectedSalesTaxAmount = 0.00;

        settingsPage.selectCompany(britishCompanyGSBI);

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("GB_SI_000002");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup("RC-VAT");
        createPurchaseOrderPage.clickOnLines();
        allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType("RC-VAT");

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(britishVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify Sales Tax
        allSalesOrdersSecondPage.journalInvoice();
        String salesTaxAmount = createPurchaseOrderPage.getSalesTaxAmount();
        assertTrue(Double.parseDouble(salesTaxAmount) == 0.00,
                "'Sales tax amount' value is not expected");
    }

    /** @TestCase CD365-1569
     * @Description - Create a Vendor Invoice and Verify Sales Tax
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression","FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifySalesTaxForVendorInvoiceTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "1";
        final String site = "1";
        final String warehouse = "11";
        final double expectedTaxAmount = 142.50;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        //Add new line item
        openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
        openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOnLines();
        openVendorInvoicesPage.addItemLine("Advertising Services", quantity, site, warehouse,0);

        //Set invoice number
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersSecondPage.clickOnOK();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));
    }

    /**
     * @TestCase CD365-1534
     * @Description - Verify that Tax Registration is not present when disabled for EU to EU
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyTaxRegistrationIsNotPresentWhenDisabledEUToEUTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 61.13;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("DE-01001");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(D0003, site, wareHouse, quantity, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Tax Registration is not present for Vendor
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(!request.contains("<VendorCode classCode=\"DE-01002\">DE-01002</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Rebhuhnweg 561</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1.0</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<TaxRegistration isoCountryCode=\"DEU\" hasPhysicalPresenceIndicator=\"false\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>DE124363748</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>"));
    }

    /**
     * @TestCase CD365-1582
     * @Description - Verify that Tax Registration is present when enabled EU to EU
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyTaxRegistrationIsPresentWhenEnabledEUToEUTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 61.13;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("DE-01002");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(D0003, site, wareHouse, quantity, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Tax Registration is present for Vendor
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<VendorCode classCode=\"DE-01002\">DE-01002</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Rebhuhnweg 561</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>"));
        assertTrue(request.contains("<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>DE124363748</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>"));
    }

    /**
     * @TestCase CD365-1703
     * @Description - Verify that Tax Registration is present when enabled EU to EU
     * @Author DPatel
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyTaxRegistrationIsPresentWhenTaxRegIDNotPresentEUToEUTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 61.13;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("DE-001");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(D0003, site, wareHouse, quantity, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Tax Registration is present for Vendor
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<VendorCode classCode=\"DE-001\">DE-001</VendorCode>\n" +
                "\t\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>Rebhuhnweg 45</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t\t<MainDivision />\n" +
                "\t\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                "\t\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t\t</PhysicalOrigin>"));
    }

    /**
     * @TestCase CD365-1589
     * @Description - Verify Intrastat Code For PO Return Truncated Code For Accrual and PO Request
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyIntrastatCodeForPOReturnsTruncatedCodeForAccrualAndPORequestTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "2";
        final String itemNumber = "D0001";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 91.23;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(itemNumber, site, wareHouse, quantity, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify IntrastatCommodityCode for both PO and Accrual Request
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("intrastatCommodityCode=\"55878925\" "));
        assertTrue(request.contains("<FlexibleCodeField fieldId=\"6\">558789256301245</FlexibleCodeField>"));

        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("intrastatCommodityCode=\"55878925\" "));
        assertTrue(request.contains("<FlexibleCodeField fieldId=\"6\">558789256301245</FlexibleCodeField>"));
    }

    /**
     * @TestCase CD365-1611
     * @Description - Verify Vendor Exchange Rate Confirmation When Disabled
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorExchangeRateConfirmationWhenDisabledTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Verify sales tax amount
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 6.46;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOnOKBtn();

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify exchange rate for vendor
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Vendor>\n" +
                "\t\t\t<VendorCode classCode=\"DE_TX_001\">DE_TX_001</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t</AdministrativeOrigin>\n" +
                "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>DE123456789</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>\n" +
                "\t\t</Vendor>"));
    }

    /**
     * @TestCase CD365-1612
     * @Description - Verify Vendor Exchange Rate Confirmation When Enabled
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyVendorExchangeRateConfirmationWhenEnabledTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

        final String quantity = "2";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "12";

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Click sales tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        allSalesOrdersPage.clickOk();

        //Click Update Match Status link
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify exchange rate for vendor
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Vendor>\n" +
                "\t\t\t<VendorCode classCode=\"DE_TX_001\">DE_TX_001</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t</AdministrativeOrigin>\n" +
                "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>DE123456789</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>\n" +
                "\t\t</Vendor>"));
    }

    /**
     * @TestCase CD365-1699
     * @Description - Change and verify truncated commodity code message for PO
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_Inprogress" }, retryAnalyzer = TestRerun.class)
    public void changeAndVerifyTruncatedCodeMessageForPOTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "2";
        final String itemNumber = "D0001";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 91.23;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order and verify truncated commodity code
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(itemNumber, site, wareHouse, quantity, 0);
        allSalesOrdersSecondPage.clickLineDetailsTab("ForeignTrade");
        //Defect CD365-1702
        allSalesOrdersSecondPage.clickCommodityCodeAndEnterOption("GHK-00&000");
        boolean isCommodityCodeTruncated = createPurchaseOrderPage.messageBarConfirmation(commodityCodeTruncated);
        assertTrue(isCommodityCodeTruncated, "Commodity Code has not been truncated");

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify IntrastatCommodityCode for both PO and Accrual Request
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        //Defect CD365-1702
        assertTrue(request.contains("intrastatCommodityCode=\"GHK-00&amp;0\""));

        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("intrastatCommodityCode=\"GHK-00&amp;0\""));
    }

    /**
     * @TestCase CD365-1764
     * @Description - Verify EU-EU Open Vendor Invoices
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyEUToEUOpenVendorInvoiceTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "1";
        final String site = "1";
        final String warehouse = "11";
        final double expectedTaxAmount = 142.50;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
        openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber(germanyVendor);

        //Set invoice number
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Add new line item
        createPurchaseOrderPage.clickOnLines();
        openVendorInvoicesPage.addItemLine("Advertising Services", quantity, site, warehouse,0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersSecondPage.clickOnOK();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify that Response does not contains PostToJournal is false
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(!response.contains("postToJournal=false"));
    }

    /**
     * @TestCase CD365-1782
     * @Description - Verify Posted Sales Tax For PO When Completing Workflow
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyPostedSalesTaxForPOWhenCompletingWorkFlowTest() {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        final String itemNumber = "D0001";
        final String quantity = "2";
        final String site = "1";
        final String wareHouse = "11";
        final String unitPrice = "240.08";
        final double expectedSalesTaxAmount = 217.61;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

        //Navigate and Activate Change Management
        settingsPage.navigateToDashboardPage();
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.collapseAll();
        homePage.expandModuleCategory(setup);
        homePage.selectModuleTabLink(procurementAndSourcingParameters);
        createPurchaseOrderPage.activateChangeManagementStatus(true);
        activateChangeManagement = true;

        //Enable Workflow
        homePage.navigateToAccountsPayableWorkflowsPage();
        createPurchaseOrderPage.updateWorkFlowStatus(true);
        workFlowStatus = true;
        allSalesOrdersSecondPage.clickOnOk();

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber("DE-001");
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, wareHouse, unitPrice, 0);

        //Add maintain charges at header level
        createPurchaseOrderPage.clickOnTab("Purchase");
        createPurchaseOrderPage.clickHeaderMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","60","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","125","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Submit the Workflow and verify it is approved
        createPurchaseOrderPage.clickWorkFlow("1");
        allSalesOrdersSecondPage.clickOnSubmit();
        allSalesOrdersSecondPage.clickOnSubmit();
        createPurchaseOrderPage.clickWorkFlow("1");
        createPurchaseOrderPage.clickApproveWorkflow();
        createPurchaseOrderPage.clickRefreshButton("1");
        String workFlowStatus = createPurchaseOrderPage.validateWorkflowStatus();
        assertTrue(workFlowStatus.equals("Approved"), "The workflow status has not been approved.");

        //Request change and verify it is in draft
        createPurchaseOrderPage.clickOnTab("PurchOrder");
        createPurchaseOrderPage.clickRequestChange();
        workFlowStatus = createPurchaseOrderPage.validateWorkflowStatus();
        assertTrue(workFlowStatus.equals("Draft"), "The workflow status has not been changed to draft.");

        //Update line quantity
        createPurchaseOrderPage.updateItemQuantity(itemNumber, "4");

        //Submit the Workflow and verify it is approved
        createPurchaseOrderPage.clickWorkFlow("1");
        allSalesOrdersSecondPage.clickOnSubmit();
        allSalesOrdersSecondPage.clickOnSubmit();
        createPurchaseOrderPage.clickWorkFlow("1");
        createPurchaseOrderPage.clickApproveWorkflow();
        createPurchaseOrderPage.clickRefreshButton("1");
        workFlowStatus = createPurchaseOrderPage.validateWorkflowStatus();
        assertTrue(workFlowStatus.equals("Approved"), "The workflow status has not been approved.");

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Submit the Workflow
        createPurchaseOrderPage.clickWorkFlow("2");
        allSalesOrdersSecondPage.clickOnSubmit();
        allSalesOrdersSecondPage.clickOnSubmit();

        //Navigate to Pending Invoices
        homePage.navigateToPendingVendorInvoicesPage();

        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendinvoiceinfolistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("DE-001", invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        //Search vendor invoice and validate result
        isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));

        //Validate Posted Sales Tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
    }

    /**
     * @TestCase CD365-1879
     * @Description - Verify Accrual Request when connecting PO to Open Vendor Invoice
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyAccrualRequestWhenConnectingPOToOpenVendorInvoiceTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "2";
        final String itemNumber = "D0001";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 91.23;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order and verify truncated commodity code
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(itemNumber, site, wareHouse, quantity, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
        openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber(germanyVendor);

        //Connect PO to Open Vendor Invoice
        openVendorInvoicesPage.enterRelatedPurchaseOrderNumber(purchaseOrder);

        //Set invoice number
        cal = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersSecondPage.clickOnOK();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Search vendor invoice and validate result
        openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

        //Validate Posted Sales Tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Accrual Request
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("AccrualRequest"));
        assertTrue(!request.contains("postToJournal=false"));
        assertTrue(request.contains("<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>"));
        assertTrue(request.contains("<InputAmount>91.23</InputAmount>"));
    }

    /**
     * @TestCase CD365-2030
     * @Description - Verify Positive And Negative Extended Price For Freight Credit Charge When Creating PO
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyPositiveAndNegativeExtendedPriceForFreightCreditChargeWhenCreatingPOTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "2";
        final String itemNumber = "D0001";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 74.13;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(itemNumber, site, wareHouse, quantity, 0);

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAdva", "All");
        allSalesOrdersPage.addCharges("TESTB",false,"Fixed","100","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("DE_TX_001", invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        //Search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));

        //Validate Posted Sales Tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("2");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);

        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Positive and Negative values of FREIGHT and TESTB respectively
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Purchase purchaseClass=\"All\">FREIGHT</Purchase>\n" +
                "\t\t\t<Quantity>1</Quantity>\n" +
                "\t\t\t<ExtendedPrice>10</ExtendedPrice>"));
        assertTrue(request.contains("<Purchase purchaseClass=\"All\">TESTB</Purchase>\n" +
                "\t\t\t<Quantity>1</Quantity>\n" +
                "\t\t\t<ExtendedPrice>-100</ExtendedPrice>\n" +
                "\t\t\t<InputTax>"));
    }


    /**
     * @TestCase CD365-2036
     * @Description - Verify Partial Recovery When Creating A Open Vendor Invoice For External Vendor
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyPartialRecoveryWhenCreatingAOpenVendorInvoiceForExternalVendorTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);

        final String quantity = "2";
        final String itemNumber = "Advertising Services";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 0.00;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
        openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber(franceVendor);

        //Set invoice number
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Add new line item and change delivery address and item/sales tax
        openVendorInvoicesPage.addItemLine(itemNumber, quantity, site, wareHouse,0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexPartialTaxGroup);

        //Validate Sales Tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToSalesTaxPage();
        allSalesOrdersPage.clickOnOKBtn();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(franceVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        //Search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));

        //Click Posted Sales Tax and verify tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("2");
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                calculatedSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify invoice response tax
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>285.0</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<RecoverableAmount>228.0</RecoverableAmount>"));
    }

    /**
     * @TestCase CD365-2038
     * @Description - Verify NonRecovery When Creating A Open Vendor Invoice For Domestic Vendor Reserve Charge
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyNonRecoveryWhenCreatingAOpenVendorInvoiceForDomesticReverseChargeTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);

        final String quantity = "2";
        final String itemNumber = "Advertising Services";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 0.00;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
        openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber(germanyVendor);

        //Set invoice number
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        //Add new line item
        openVendorInvoicesPage.addItemLine(itemNumber, quantity, site, wareHouse,0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(vertexNonRecoverable);

        //Validate Sales Tax
        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        String actualAccrualSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
        Double expectedAccrualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualAccrualSalesTaxAmount) == expectedAccrualSalesTaxAmount,
                assertionTotalAccrualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Post the invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(germanyVendor, invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        //Search vendor invoice and validate result
        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));

        //Click Posted Sales Tax and verify tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("2");
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                calculatedSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify total tax and unrecoverable amount
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>0.0</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<UnrecoverableAmount>0.0</UnrecoverableAmount>"));
    }

    /**
     * @TestCase CD365-2061
     * @Description - Verify XML For Header And Line Level Miscellaneous Charges For PO Test
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void verifyXMLForHeaderAndLineLevelMiscellaneousChargesForPOTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        final String quantity = "2";
        final String itemNumber = "D0010";
        final String site = "1";
        final String wareHouse = "11";

        final double expectedSalesTaxAmount = 438.90;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

        //Create new Purchase Order
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.clickOnLines();
        createPurchaseOrderPage.fill1STItemsInfo(itemNumber, site, wareHouse, quantity, 0);
        allSalesOrdersSecondPage.clickOnSetUpTab();
        allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);

        //Enter Color and Location
        createPurchaseOrderPage.clickOnProductTab();
        createPurchaseOrderPage.clickAndEnterProductValue("Color", "Black");
        createPurchaseOrderPage.clickAndEnterProductValue("wMSLocationId", "L01");

        //Add maintain charges at header level
        createPurchaseOrderPage.clickOnTab("Purchase");
        createPurchaseOrderPage.clickHeaderMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","60","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Click on Financials and select Maintain charges
        allSalesOrdersPage.clickOnFinancials();
        allSalesOrdersPage.selectMaintainCharges();
        allSalesOrdersPage.addCharges("FREIGHT", false,"Pcs.","125","VertexAdva", "All");
        allSalesOrdersPage.closeCharges();

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Qty and Extended price of Freight Items
        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("Purchase purchaseClass=\"All\">FREIGHT</Purchase>\n" +
                        "\t\t\t<Quantity>1.0</Quantity>\n" +
                        "\t\t\t<ExtendedPrice>60.0</ExtendedPrice>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<Purchase purchaseClass=\"All\">FREIGHT</Purchase>\n" +
                "\t\t\t<Quantity>2.0</Quantity>\n" +
                "\t\t\t<ExtendedPrice>250.0</ExtendedPrice>"));


        homePage.navigateToAllPurchaseOrdersPage();
        createPurchaseOrderPage.searchCreatedPurchaseOrder(purchaseOrder);
        createPurchaseOrderPage.clickOnDisplayedPurchaseOrderNumber();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("700.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String updatedActualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(Double.parseDouble(updatedActualSalesTaxAmount) == 700.00,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Navigate to Pending Invoices
        homePage.navigateToPendingVendorInvoicesPage();

        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendinvoiceinfolistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("DE_TX_001", invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

        //Search vendor invoice and validate result
        isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));

        //Validate Posted Sales Tax
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
        openVendorInvoicesPage.clickVouchersTab();
        openVendorInvoicesPage.clickPostedSalesTax("1");
        calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == 700.00,
                assertionTotalCalculatedSalesTaxAmount);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify Qty and Extended price of Freight Items
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("Purchase purchaseClass=\"All\">FREIGHT</Purchase>\n" +
                        "\t\t\t<Quantity>1.0</Quantity>\n" +
                        "\t\t\t<ExtendedPrice>60.0</ExtendedPrice>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<Purchase purchaseClass=\"All\">FREIGHT</Purchase>\n" +
                "\t\t\t<Quantity>2.0</Quantity>\n" +
                "\t\t\t<ExtendedPrice>250.0</ExtendedPrice>"));

    }

    /** @TestCase CD365-2086
     * @Description - Create PO and validate Currency Conversion Is Present
     * @Author Mario Saint-Fleur
     */
    @Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
    public void validateCurrencyConversionDuringCreationOfPOAPVATTest() {
        final String itemNumber = "D0001";
        final String site = "1";
        final String warehouse = "11";
        final String unitPrice = "899";

        final double expectedTaxAmount = 341.62;

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        //Create new Purchase Order
        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
        createPurchaseOrderPage.setVendorAccountNumber(germanyVendor);
        createPurchaseOrderPage.clickOkButton();
        createPurchaseOrderPage.clickOnHeader();
        String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
        VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
        createPurchaseOrderPage.setSalesOrderTaxGroup(vertexAdvancedTaxGroup);
        createPurchaseOrderPage.clickOnLines();
        allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "2", unitPrice, 0);

        //Validate Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
        assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
                assertionTotalCalculatedSalesTaxAmount);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
                assertionTotalActualSalesTaxAmount);
        createPurchaseOrderPage.clickOnSalesTaxOkButton();

        //Verify Currency Conversion
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(purchaseOrder);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Currency isoCurrencyCodeAlpha=\"EUR\" />\n" +
                "\t\t<Buyer>\n" +
                "\t\t\t<Company>DEMF</Company>\n" +
                "\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</Destination>\n" +
                "\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</AdministrativeDestination>\n" +
                "\t\t</Buyer>"));

        assertTrue(request.contains("<Vendor>\n" +
                "\t\t\t<VendorCode classCode=\"DE_TX_001\">DE_TX_001</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t</AdministrativeOrigin>\n" +
                "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>DE123456789</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>"));

        //Verify Currency Conversion for Invoice Request
        homePage.navigateToAllPurchaseOrdersPage();
        createPurchaseOrderPage.searchCreatedPurchaseOrder(purchaseOrder);
        createPurchaseOrderPage.clickOnDisplayedPurchaseOrderNumber();

        //Navigate to confirm the Purchase Order
        createPurchaseOrderPage.navigateToConfirmPage();
        boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Receive --> GENERATE --> ProductReceipt page
        createPurchaseOrderPage.navigateToProductReceiptPage();
        DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
        Calendar cal = Calendar.getInstance();
        String productReceiptNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
        VertexLogger.log(String.format(productReceiptNumberString, productReceiptNumber));
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
        assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

        //Navigate to Invoice --> GENERATE --> Invoice page
        createPurchaseOrderPage.clickOnTab("Invoice");
        createPurchaseOrderPage.navigateToInvoicePage();
        cal = Calendar.getInstance();
        String invoiceNumber = dateFormat.format(cal.getTime());
        createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
        createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

        createPurchaseOrderPage.clickOnInvoiceTab("Financials");
        createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
        String updatedActualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(Double.parseDouble(updatedActualSalesTaxAmount) == 341.62,
                assertionTotalActualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Update Match Status
        createPurchaseOrderPage.clickUpdateMatchStatusButton();
        final String expectedMatchStatus = "Passed";
        String matchStatus = createPurchaseOrderPage.getMatchStatus();
        assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
                String.format(matchStatusString, expectedMatchStatus, matchStatus));

        //Navigate to Pending Invoices
        homePage.navigateToPendingVendorInvoicesPage();

        boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendinvoiceinfolistpage", invoiceNumber);
        assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));
        openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);

        //Post Invoice
        createPurchaseOrderPage.clickPostOption();
        boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("DE_TX_001", invoiceNumber);
        assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

        //Verify Currency Conversion
        settingsPage.navigateToDashboardPage();
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstRequest();
        request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<Currency isoCurrencyCodeAlpha=\"EUR\" />\n" +
                "\t\t<Buyer>\n" +
                "\t\t\t<Company>DEMF</Company>\n" +
                "\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</Destination>\n" +
                "\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</AdministrativeDestination>\n" +
                "\t\t</Buyer>"));

        assertTrue(request.contains("<Vendor>\n" +
                "\t\t\t<VendorCode classCode=\"DE_TX_001\">DE_TX_001</VendorCode>\n" +
                "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
                "\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t</AdministrativeOrigin>\n" +
                "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t<TaxRegistrationNumber>DE123456789</TaxRegistrationNumber>\n" +
                "\t\t\t</TaxRegistration>"));
    }

}
