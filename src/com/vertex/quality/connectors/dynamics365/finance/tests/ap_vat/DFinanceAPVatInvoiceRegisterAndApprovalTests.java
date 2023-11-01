package com.vertex.quality.connectors.dynamics365.finance.tests.ap_vat;

import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceXMLValidation;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import java.util.*;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceAPVatInvoiceRegisterAndApprovalTests extends DFinanceBaseTest {

    Boolean advTaxGroup = false;

    @BeforeMethod(alwaysRun = true)
    public void setupTest( )
    {
        advTaxGroup = false;
    }

    @AfterMethod(alwaysRun = true)
    public void teardownTest( )
    {
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        if (advTaxGroup) {
            homePage.refreshPage();
            toggleONOFFAdvanceTaxGroup(false);
        }
    }

    /**
     * @TestCase CD365-1103
     * @Description - Create an AP VAT Invoice Approval for Germany to Germany customer
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void apVatGermanyToGermanyInvoiceRegisterAndInvoiceApprovalTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
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
        invoicePage.setAccount(germanyVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setSaleTaxInvoiceRegister(vertexAdvancedTaxGroup);
        invoicePage.setItemTaxInvoiceRegister("ALL");

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Validate the invoice
        invoicePage.clickOnValidateTab();

        invoicePage.validateInvoiceRegister();

        //Verify "Actual sales tax amount"
        String actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");

        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("170.81"),
                assertionTotalCalculatedSalesTaxAmount);

        //Post the invoice
        invoicePage.postInvoice();

        //Navigate to "XML Inquiry page"
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate that the Invoice is "Purchase Order" type
        String expectedType = "Purchase Order";

        xmlInquiryPage.getDocumentID(docId);

        String actualType = xmlInquiryPage.getDocumentType();

        assertTrue(actualType.equals(expectedType),
                documentTypeNotExpected);

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

        //Filter and find the most recent voucher
        invoicePage.getVoucherId(docId);

        //Click the select button
        invoicePage.clickSelectBtn();

        //Click ok to complete voucher
        invoicePage.clickOkBtn();

        //Verify the "Sales tax amount"
        String actualSalesTaxAmount1 = invoicePage.getActualSalesTaxAmountInvoiceApproval();

        assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("170.81"),
                assertionTotalActualSalesTaxAmount);

        //Set the Account Num
        invoicePage.setAccountNum("200125-001-022");

        //Post the "Invoice approval"
        invoicePage.postInvoice();

        //Navigate to XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xmlInquiryPage.getDocumentID(docId);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>-170.81</TotalTax>"));

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();

        //Validate that the Buyer, Vendor, etc is correct
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains(xmlValidation.getGermanyBuyerXML()),
                requestTypeNotExpected);
        assertTrue(request.contains(xmlValidation.getGermanyVendorClassXML()),
                requestTypeNotExpected);

    }

    /**
     * @TestCase CD365-1107
     * @Description - Create an AP VAT Invoice Approval for France to Germany customer
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void apVatFranceToGermanyInvoiceRegisterAndInvoiceApprovalTest(){
        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

        //Data declaration for page navigation
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
        final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();
        final int expectedLinesCount = 3;

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
        invoicePage.setItemTaxInvoiceRegister(vertexPartialTaxGroup);

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Validate the invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        int actualLinesCount = createPurchaseOrderPage.getTempSalesTaxTransactionLinesCount();
        assertTrue(actualLinesCount == expectedLinesCount,
                String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
                        expectedLinesCount, actualLinesCount));

        List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

        for (int i = 1; i <= actualLinesCount; i++) {
            Map<String, String> lineDataMap = createPurchaseOrderPage.getSalesTaxAmount(String.format("%s", i));

            System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

            allLinesDataList.add(lineDataMap);
        }

        List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line10.getLineDataMap());
        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line11.getLineDataMap());
        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line12.getLineDataMap());

        Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

        Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

        boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
                expectedAllLinesSet);

       if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
            assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
                    expectedAllLinesSet, actualAllLinesSet));
        }
        allSalesOrdersPage.clickOnOKBtn();

        //Post the invoice
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        //Navigate to "XML Inquiry page"
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate that the Invoice is "Purchase order" type
        String expectedType = "Purchase Order";

        xmlInquiryPage.getDocumentID(docId);

        String actualType = xmlInquiryPage.getDocumentType();

        assertTrue(actualType.equals(expectedType),
                documentTypeNotExpected);

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

        //Filter and find the most recent voucher
        invoicePage.getVoucherId(docId);

        //Click the select button
        invoicePage.clickSelectBtn();

        //Click ok to complete voucher
        invoicePage.clickOkBtn();

        //Verify the "Sales tax amount"
        String actualSalesTaxAmount1 = invoicePage.getActualSalesTaxAmountInvoiceApproval();

        assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("0.00"),
                assertionTotalActualSalesTaxAmount);

        //Set the Account Num and add sales/item tax group
        invoicePage.setAccountNum("200125-001-022");
        invoicePage.setSalesAndItemTaxGroupInvoiceApproval(vertexAdvancedTaxGroup, vertexPartialTaxGroup);

        //Update actual sales tax and verify tax lines
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        actualLinesCount = createPurchaseOrderPage.getTempSalesTaxTransactionLinesCount();
        assertTrue(actualLinesCount == expectedLinesCount,
                String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
                        expectedLinesCount, actualLinesCount));

        allLinesDataList = new ArrayList<Map<String, String>>();

        for (int i = 1; i <= actualLinesCount; i++) {
            Map<String, String> lineDataMap = createPurchaseOrderPage.getSalesTaxAmount(String.format("%s", i));

            System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

            allLinesDataList.add(lineDataMap);
        }

        expectedAllLinesDataList = new ArrayList<Map<String, String>>();

        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line14.getLineDataMap());
        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line15.getLineDataMap());
        expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line16.getLineDataMap());

        expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

        actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

        resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
                expectedAllLinesSet);

        if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
            assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
                    expectedAllLinesSet, actualAllLinesSet));
        }
        allSalesOrdersPage.clickOnOKBtn();

        //Post the invoice
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");

        //Navigate to XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Verify accrual call postToJournal and total tax
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(!response.contains("postToJournal=false"));
        assertTrue(response.contains("<TotalTax>170.81</TotalTax>"));

        DFinanceXMLValidation xmlValidation = new DFinanceXMLValidation();

        //Validate that the Buyer, Vendor, etc is correct
        xmlInquiryPage.clickOnFirstRequest();
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains(xmlValidation.getGermanyBuyerXML()),
                requestTypeNotExpected);
        assertTrue(request.contains("<VendorCode classCode=\"FR_SI_000001\">FR_SI_000001</VendorCode>\n" +
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
                        "\t\t\t</TaxRegistration>\n" +
                        "\t\t</Vendor>"),
                requestTypeNotExpected);
    }

    /**
     * @TestCase CD365-1811
     * @Description - Verifying invoice register/approval accrual verification when under charged
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_Not_Supported_Yet"}, retryAnalyzer = TestRerun.class)
    public void invoiceRegisterAndInvoiceApprovalAccrualVerificationUnderChargedGermanyToGermanyTest(){
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

        //Get voucher number
        String voucherNum = invoicePage.voucherNo();

        //Line Detail Information
        invoicePage.setAccount("DE_TX_001");
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();

        //Get the invoice number
        String invoiceNum = invoicePage.getInvoiceNo();

        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Debit","899");
        invoicePage.setSaleTaxInvoiceRegister(vertexAdvancedTaxGroup);
        invoicePage.setItemTaxInvoiceRegister(vertexPartialTaxGroup);

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected: "+actualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Validate the invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Post the invoice
        invoicePage.postInvoice();

        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax
        xmlInquiryPage.getDocumentID(invoiceNum);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("\t\t<TotalTax>0.0</TotalTax>\n"),
                "Total tax is not equal to what is expected");

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
        invoicePage.getVoucherId(voucherNum);
        invoicePage.clickSelectBtn();
        invoicePage.clickOkBtn();

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String actualSalesTaxAmount1 = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("0.00"),
                "Actual sales tax amount is not correct");
        allSalesOrdersPage.clickOnOKBtn();

        //Set the Account Num
        invoicePage.setAccountNum("200125-001-022");

        //Post the "Invoice approval"
        invoicePage.postInvoice();
    }

    /**
     * @TestCase CD365-1918
     * @Description - Verify Accrual For Invoice Register And Approval When Under Charging AP VAT
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyAccrualForInvoiceRegisterAndApprovalWhenUnderChargingAPVATTest(){
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
        invoicePage.setAccount(germanyVendor);
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
        createPurchaseOrderPage.updateActualSalesTaxAmount("20.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("20.00"),
                "'Total actual sales tax amount' value is not expected: "+actualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Validate the invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Post the invoice
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("20.00"),
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

        //Set the Account Num and Sales/Item Tax Group
        invoicePage.setAccountNum("200125-001-022");
        invoicePage.setSalesAndItemTaxGroupInvoiceApproval(vertexAdvancedTaxGroup, allItemSalesTaxGroup);

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("20.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("20.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();

        //Post the "Invoice approval"
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("20.00"),
                "'Total actual sales tax amount' value is not expected");

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and postToJournal
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>20.0</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
    }

    /**
     * @TestCase CD365-2037
     * @Description - Verify NonRecoverable For Invoice Register And Approval
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyNonRecoverableForInvoiceRegisterAndApprovalAPVATTest(){
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
        invoicePage.setItemTaxInvoiceRegister(vertexNonRecoverable);

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected: "+actualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Validate the invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Post the invoice
        invoicePage.postInvoice();

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate nonrecoverable amount and total tax
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>-170.81</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<UnrecoverableAmount>-170.81</UnrecoverableAmount>"));


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

        //Set the Account Num and Sales/Item Tax Group
        invoicePage.setAccountNum("200125-001-022");
        invoicePage.setSalesAndItemTaxGroupInvoiceApproval(vertexAdvancedTaxGroup, vertexNonRecoverable);

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();

        //Post the "Invoice approval"
        invoicePage.postInvoice();

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and postToJournal
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>170.81</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<UnrecoverableAmount>170.81</UnrecoverableAmount>"));
    }

    /**
     * @TestCase CD365-2039
     * @Description - Verify Partial Recoverable For Invoice Register And Approval Domestic Reverse Charge
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyPartialRecoverableForInvoiceRegisterAndApprovalDomesticReverseChargeTest(){
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
        invoicePage.setAccount(germanyVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String invoiceNumber = invoicePage.getInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setSaleTaxInvoiceRegister(vertexAdvancedTaxGroup);
        invoicePage.setItemTaxInvoiceRegister(vertexPartialTaxGroup);

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedByInvoiceRegister("000013");

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        Double expectedActualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        String actualAccrualSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
        Double expectedAccrualSalesTaxAmount = 170.81;
        assertTrue(Double.parseDouble(actualAccrualSalesTaxAmount) == expectedAccrualSalesTaxAmount,
                assertionTotalAccrualSalesTaxAmount);
        allSalesOrdersPage.clickOnOKBtn();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("0.00"),
                "'Total actual sales tax amount' value is not expected: "+actualSalesTaxAmount);

        //Validate the invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Post the invoice
        invoicePage.postInvoice();

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate unrecoverable amount and total tax
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>-170.81</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<UnrecoverableAmount>-34.16</UnrecoverableAmount>"));


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

        //Set the Account Num and Sales/Item Tax Group
        invoicePage.setAccountNum("200125-001-022");
        invoicePage.setSalesAndItemTaxGroupInvoiceApproval(vertexAdvancedTaxGroup, vertexPartialTaxGroup);

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        expectedActualSalesTaxAmount = 0.00;
        assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
                assertionTotalActualSalesTaxAmount);
        actualAccrualSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
        expectedAccrualSalesTaxAmount = 170.81;
        assertTrue(Double.parseDouble(actualAccrualSalesTaxAmount) == expectedAccrualSalesTaxAmount,
                assertionTotalAccrualSalesTaxAmount);
        allSalesOrdersPage.clickOk();

        //Post the "Invoice approval"
        invoicePage.postInvoice();

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and unrecoverable
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>170.81</TotalTax>"),
                "Total tax is not as expected");
        assertTrue(response.contains("<UnrecoverableAmount>34.16</UnrecoverableAmount>"));
    }
}
