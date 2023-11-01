package com.vertex.quality.connectors.dynamics365.finance.tests.ap_vat;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.testng.Assert.assertTrue;

@Listeners(TestRerunListener.class)
public class DFinanceAPVatAdvancedTaxGroupTests extends DFinanceBaseTest {

    Boolean advTaxGroup = false;
    final String procurementAndSourcing = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING.getData();
    final String setup = DFinanceLeftMenuNames.SETUP.getData();
    final String procurementAndSourcingParameters = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING_PARAMETERS.getData();

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        advTaxGroup = false;
        DFinanceHomePage homePage = new DFinanceHomePage(driver);

        //Disable Workflow
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        homePage.navigateToAccountsPayableWorkflowsPage();

        createPurchaseOrderPage.updateWorkFlowStatus(true);
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.expandModuleCategory(setup);
        homePage.selectModuleTabLink(procurementAndSourcingParameters);
        createPurchaseOrderPage.activateChangeManagementStatus(true);


    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {DFinanceHomePage homePage = new DFinanceHomePage(driver);
        if (advTaxGroup) {
            homePage.refreshPage();
            toggleONOFFAdvanceTaxGroup(false);
        }
    }

    /**
     * @TestCase CD365-1194
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

        DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);

        settingsPage.selectCompany(germanCompanyDEMF);

        //Disable Workflow
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        homePage.navigateToAccountsPayableWorkflowsPage();
        createPurchaseOrderPage.updateWorkFlowStatus(false);

        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

        createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(procurementAndSourcing);
        homePage.expandModuleCategory(setup);
        homePage.selectModuleTabLink(procurementAndSourcingParameters);
        createPurchaseOrderPage.activateChangeManagementStatus(false);

        settingsPage.navigateToDashboardPage();
        homePage.navigateToAllPurchaseOrdersPage();
        //Create a new Purchase Order
        createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
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
        System.out.println(salesTaxCodeType);
        assertTrue(salesTaxCodeType.contains(vsdoSalesTaxCode), "Sales Tax Code not contain V_SDO_19_G");
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
     * @TestCase CD365-1195
     * @Description - Verify Tax Amount for Invoice Journal Germany to France using Advanced Sales Tax Group
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxAmountUsingAdvancedTaxGroupForGermanyToFranceAPInvoiceJournalTest(){

        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        settingsPage.selectCompany(germanCompanyDEMF);

        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(journal);

        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvoice");
        invoicePage.clickJournal();

        invoicePage.setAccount(franceVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String invoiceNumber = invoicePage.getInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","299");
        invoicePage.setOffsetAccount("110130");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(vertexNonRecoverable);

        //Save the Invoice Journal
        invoicePage.clickSaveButton();
        invoicePage.invoiceApprovedBy("000013");

        //Reverse charge Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOnOKBtn();

        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        createPurchaseOrderPage.navigateToSalesTaxPage();
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains("V_RC_G"), "Sales Tax Code not contain V_RC_G");
        createPurchaseOrderPage.clickOnSalesTaxOkButton();
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and postToJournal
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>-56.81</TotalTax>"),
                "Total tax amount is not as expected");
        assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
    }

    /**
     * @TestCase CD365-1196
     * @Description - Verify Tax Amount for Invoice Register/Approval Germany to Germany using Advanced Sales Tax Group
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyTaxAmountUsingAdvancedTaxGroupForGermanyToGermanyInvoiceRegisterAndInvoiceApprovalTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
        final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

        settingsPage.selectCompany(germanCompanyDEMF);

        toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
        //Navigate to Invoice Register
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(register);

        //Create a new Invoice
        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvReg");
        invoicePage.clickJournal();
        invoicePage.setAccount(germanyVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String invoiceNo = invoicePage.getInvoiceNo();
        String voucherNo = invoicePage.voucherNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setSaleTaxInvoiceRegister(vertexAdvancedTaxGroup);
        invoicePage.setItemTaxInvoiceRegister(vertexPartialTaxGroup);
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Over Charge Actual Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("200.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("200.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOnOKBtn();


        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        invoicePage.postInvoice();

        createPurchaseOrderPage.navigateToSalesTaxPage();
        String salesTaxCodeType = openVendorInvoicesPage.getSalesTaxCode(0);
        assertTrue(salesTaxCodeType.contains(vsdoSalesTaxCode), "Sales Tax Code not contain V_SDO_19_G");

        //Navigate to "Invoice approval"
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(approval);

        //Create a new Invoice Approval
        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvApp");
        invoicePage.clickJournal();
        invoicePage.clickFindVouchers();
        invoicePage.getVoucherId(voucherNo);
        invoicePage.clickSelectBtn();
        invoicePage.clickOkBtn();

        //Verify the "Sales tax amount"
        String actualSalesTaxAmount1 = invoicePage.getActualSalesTaxAmountInvoiceApproval();
        assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("200.00"),
                assertionTotalCalculatedSalesTaxAmount);

        //Set the Account Num and post the Invoice
        invoicePage.setAccountNum("200125-001-022");
        invoicePage.postInvoice();

        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from cd list and verify total tax
        xmlInquiryPage.getDocumentID(invoiceNo);
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<ExtendedPrice>-899</ExtendedPrice>"));

    }

    /**
     * @TestCase CD365-1921
     * @Description - Verify Accrual For Invoice Register And Approval When Using France Vendor
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualForInvoiceRegisterAndApprovalWhileUnderchargingWhenUsingFranceVendorTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

        //Data declaration for page navigation
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
        final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(register);

        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvReg");
        invoicePage.clickJournal();

        //Get the voucher number
        String docId = invoicePage.voucherNo();

        //Line Detail Information
        invoicePage.setAccount(franceVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String invoiceNumber = invoicePage.getInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setSaleTaxInvoiceRegister(vertexAdvancedTaxGroup);
        invoicePage.setItemTaxInvoiceRegister(allItemSalesTaxGroup);

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOnOKBtn();

        //Validate the invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Post the invoice
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        //Navigate to "Invoice approval"
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(approval);

        //Create a new invoice
        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvApp");
        invoicePage.clickJournal();

        //Select the invoice that was registered
        invoicePage.clickFindVouchers();
        invoicePage.getVoucherId(docId);
        invoicePage.clickSelectBtn();
        invoicePage.clickOkBtn();

        //Verify "Actual sales tax amount" and set Sales/Item Tax Group
        invoicePage.setAccountNum("200125-001-022");
        invoicePage.setSalesAndItemTaxGroupInvoiceApproval(vertexAdvancedTaxGroup, allItemSalesTaxGroup);
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();

        //Post the "Invoice approval"
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and postToJournal
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>170.81</TotalTax>"),
                "Total tax amount is not expected");
        assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
    }
}
