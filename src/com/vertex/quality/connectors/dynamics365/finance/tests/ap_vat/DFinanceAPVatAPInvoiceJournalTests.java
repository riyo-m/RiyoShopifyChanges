package com.vertex.quality.connectors.dynamics365.finance.tests.ap_vat;

import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceTaxValidation;
import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceXMLValidation;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class DFinanceAPVatAPInvoiceJournalTests extends DFinanceBaseTest {

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
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        //Navigate to general ledger, filter APInvReg and enable include tax option
        homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
                DFinanceModulePanelLink.JOURNAL_NAMES);

        settingsPage.clickOnEditButton();
        settingsPage.filterJournalNameAndEnableAmountSalesTax(false,"APInvoice");
        settingsPage.clickOnSaveButton();
    }

    /**
     * @TestCase CD365-1094
     * @Description - Create an AP VAT for Germany to Germany customer
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void apVatGermanyToGermanyApInvoiceJournalTest(){

        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        //Navigate to general ledger, filter APInvReg and enable include tax option
        homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
                DFinanceModulePanelLink.JOURNAL_NAMES);

        settingsPage.clickOnEditButton();
        settingsPage.filterJournalNameAndEnableAmountSalesTax(true,"APInvoice");
        settingsPage.clickOnSaveButton();

        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(journal);

        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvoice");
        invoicePage.clickJournal();

        invoicePage.setAccount(germanyVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","299");
        invoicePage.setOffsetAccount("110130");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(vertexPartialTaxGroup);

        //Save the Invoice Journal
        invoicePage.clickSaveButton();

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedBy("000013");

        //Verify Sales Tax
        String actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("47.74"),
                assertionTotalCalculatedSalesTaxAmount);

        //Validate invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Voucher Number
        String docId = invoicePage.voucherNo();

        //Post the invoice
        invoicePage.postInvoice();

        //Navigate to XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xmlInquiryPage.getDocumentID(docId);
        xmlInquiryPage.clickResponse();

        //Validate that tax amount is present in the log
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>-47.74</TotalTax>"));

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
     * @TestCase CD365-1106
     * @Description - Create an AP VAT for Germany to Germany customer with Over Charge
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void apVatGermanyToGermanyApInvoiceJournalWithOverChargeTest(){
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
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

        invoicePage.setAccount(germanyVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();

        //Voucher Number
        String docId = invoicePage.getInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","299");
        invoicePage.setOffsetAccount("110130");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax("All");

        //Save the Invoice Journal
        invoicePage.clickSaveButton();

        //Clicks on the Invoice tab and selects who approved the invoice
        invoicePage.invoiceApprovedBy("000013");

        //Overcharge Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.updateActualSalesTaxAmount("100.00");
        createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
        String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("100.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOnOKBtn();

        //Validate invoice
        invoicePage.clickOnValidateTab();
        invoicePage.validateInvoiceRegister();

        //Post the invoice
        invoicePage.postInvoice();

        //Navigate to XML inquiry
        homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Select the Correct Response from the list
        xmlInquiryPage.getDocumentID(docId);
        xmlInquiryPage.clickOnFirstRequest();

        //Validate that tax amount is present in the log
        String request = xmlInquiryPage.getLogRequestValue();
        assertTrue(request.contains("<InputAmount>-100</InputAmount>"), "The ExtendedPrice is not present");
    }

    /**
     * @TestCase CD365-1920
     * @Description - Validate Accrual When Similar Charging AP VAT Invoice Journal While Using Ledger Account Type
     * @Author Mario Saint-Fleur
     */
    @Ignore
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void validateAccrualWhenSimilarChargingAPVATInvoiceJournalWhileUsingLedgerAccountTypeTest(){
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;

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

        invoicePage.selectAccountType("Ledger");
        invoicePage.setAccount("200125-001-022");
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String invoiceNumber = invoicePage.getInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setOffsetAccount("200125-002-022");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(allItemSalesTaxGroup);

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("170.81"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();

        //Post the "Invoice Journal"
        invoicePage.postInvoice();

        //Verify Posted Sales Tax
        createPurchaseOrderPage.navigateToSalesTaxPage();
        actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("170.81"),
                "'Total actual sales tax amount' value is not expected");

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and postToJournal
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>-170.81</TotalTax>"),
                "Total tax amount is not as expected");
        assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
    }

    /**
     * @TestCase CD365-2066
     * @Description - Validate error message does not appear when opening posted Invoice Journal AP VAT
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void validateErrorAppearanceForPostedInvoiceJournalTest(){
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        String invoiceJournal = "Vendor invoice journal";

        settingsPage.selectCompany(germanCompanyDEMF);

        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(journal);

        //Verify Vendor Invoice Presence and Posted Message Is Not Present
        invoicePage.selectInvoiceType("Posted");
        invoicePage.clickRefreshButton("1");
        invoicePage.filterInvoiceFromDisplayedList("00112");
        invoicePage.selectInvoiceNumber("00112");
        String invoiceType = invoicePage.verifyInvoiceIsOpen("Vendor invoice journal");
        assertTrue(invoiceType.contains("Vendor invoice journal"), "Expected invoice type to be: " + invoiceJournal + ", but found: " + invoiceType);
        Boolean postedErrorMessage = invoicePage.verifyPostedErrorMessage();
        assertTrue(postedErrorMessage.equals(false), "Expected the posted error message to not display, but currently is displaying");

        allSalesOrdersPage.clickOnCloseSalesOrderPage("2");
        postedErrorMessage = invoicePage.verifyPostedErrorMessage();
        assertTrue(postedErrorMessage.equals(false), "Expected the posted error message to not display, but currently is displaying");
    }

    /**
     * @TestCase CD365-2092
     * @Description - Verify Sign For Total Tax When Creating Invoice Journal VAT
     * @Author Mario Saint-Fleur
     */
    @Test(groups = {"FO_InProgress"}, retryAnalyzer = TestRerun.class)
    public void verifySignForTotalTaxWhenCreatingMultilineInvoiceJournalVATTest(){
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;

        settingsPage.selectCompany(germanCompanyDEMF);
        toggleONOFFAdvanceTaxGroup(true);
        advTaxGroup = true;

        //Create New Invoice Journal
        homePage.clickOnNavigationPanel();
        homePage.selectLeftMenuModule(payable);
        homePage.collapseAll();
        homePage.expandModuleCategory(invoice);
        homePage.selectModuleTabLink(journal);

        invoicePage.clickNewButton();
        invoicePage.setInvoiceName("APInvoice");
        invoicePage.clickJournal();

        //Add invoice line
        invoicePage.setAccount(germanyVendor);
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setOffsetAccount("200125-002-023");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(allItemSalesTaxGroup);

        createPurchaseOrderPage.navigateToSalesTaxPage();
        String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("-170.81"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOk();

        //Delete invoice line
        invoicePage.clickDeleteLineButton();
        invoicePage.clickDeleteYesButton();

        //Add another invoice line
        invoicePage.clickNewLineButton();
        invoicePage.setAccount("DE-001");
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String invoiceNumber = invoicePage.getInvoiceNo();
        invoicePage.setDescription("Invoice");
        invoicePage.setCreditOrDebit("Credit","899");
        invoicePage.setOffsetAccount("200125-002-023");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(allItemSalesTaxGroup);

        //Verify taxes and undercharge
        createPurchaseOrderPage.navigateToSalesTaxPage();
        createPurchaseOrderPage.clickAdjustmentSalesTax();
        invoicePage.enterActualSalesTaxAmountAdjustmentTab("100.00");
        allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
        actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
        assertTrue(actualSalesTaxAmount.equalsIgnoreCase("100.00"),
                "'Total actual sales tax amount' value is not expected");
        allSalesOrdersPage.clickOnOKBtn();

        //Navigate to XML Inquiry
        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax
        xmlInquiryPage.getDocumentID(invoiceNumber);
        xmlInquiryPage.clickOnFirstResponse();
        String response = xmlInquiryPage.getLogResponseValue();
        assertTrue(response.contains("<TotalTax>100.0</TotalTax>"),
                "Total tax amount is not as expected");
        assertTrue(response.contains("<ExtendedPrice>899.0</ExtendedPrice>"),
                "Extended price amount is not as expected");
    }

    /**
     * @TestCase CD365-2516
     * @Description - verify Buyer and Vendor in XML request at header and line level
     * @Author Vivek Olumbe
     */
    @Test(groups = {"FO_VAT_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
    public void verifyBuyerAndVendorInHeaderAndLinesForInvoiceJournalTest(){
        final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
        final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
        final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

        DFinanceHomePage homePage = new DFinanceHomePage(driver);
        DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
        DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
        DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
        DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
        DFinanceXMLInquiryPage xmlInquiryPage;

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

        invoicePage.selectAccountType("Vendor");
        invoicePage.setAccount("DE-001");
        invoicePage.setInvoiceDate();
        invoicePage.setInvoiceNo();
        String docNo = invoicePage.voucherNo();
        invoicePage.setCreditOrDebit("Credit","1000");
        invoicePage.setOffsetAccount("");
        invoicePage.setSaleTax("");
        invoicePage.setItemTax("");

        invoicePage.clickNewLineButton();
        invoicePage.selectAccountType("Ledger");
        invoicePage.setAccount("618200-001-022");
        invoicePage.setInvoiceDate();
        invoicePage.setCreditOrDebit("Debit","800");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(allItemSalesTaxGroup);

        invoicePage.clickNewLineButton();
        invoicePage.selectAccountType("Ledger");
        invoicePage.setAccount("618200-002-022-008-Audio");
        invoicePage.setInvoiceDate();
        invoicePage.setCreditOrDebit("Debit","200");
        invoicePage.setSaleTax(vertexAdvancedTaxGroup);
        invoicePage.setItemTax(allItemSalesTaxGroup);

        //Verify "Actual sales tax amount"
        createPurchaseOrderPage.navigateToSalesTaxPage();
        allSalesOrdersPage.clickOk();

        settingsPage.navigateToDashboardPage();
        xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
                DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

        //Validate total tax, and postToJournal
        xmlInquiryPage.getDocumentID(docNo);
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
                        "\t\t</Buyer>\n" +
                        "\t\t<Vendor>\n" +
                        "\t\t\t<VendorCode classCode=\"DE-001\">DE-001</VendorCode>\n" +
                        "\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                        "\t\t\t\t<StreetAddress1>Rebhuhnweg 45</StreetAddress1>\n" +
                        "\t\t\t\t<City>Berlin</City>\n" +
                        "\t\t\t\t<MainDivision />\n" +
                        "\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                        "\t\t\t\t<Country>DEU</Country>\n" +
                        "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                        "\t\t\t</PhysicalOrigin>\n" +
                        "\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                        "\t\t\t\t<StreetAddress1>Rebhuhnweg 45</StreetAddress1>\n" +
                        "\t\t\t\t<City>Berlin</City>\n" +
                        "\t\t\t\t<MainDivision />\n" +
                        "\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                        "\t\t\t\t<Country>DEU</Country>\n" +
                        "\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                        "\t\t\t</AdministrativeOrigin>\n" +
                        "\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                        "\t\t\t\t<TaxRegistrationNumber>DE124363748</TaxRegistrationNumber>\n" +
                        "\t\t\t</TaxRegistration>\n"),
                "Header Validation Failed");
        assertTrue(request.contains("generalLedgerAccount=\"618200-002-022-008-Audio\">\n" +
                "\t\t\t<Buyer>\n" +
                "\t\t\t\t<Company>DEMF</Company>\n" +
                "\t\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t\t</Destination>\n" +
                "\t\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t\t<MainDivision>BE</MainDivision>\n" +
                "\t\t\t\t\t<PostalCode>10115</PostalCode>\n" +
                "\t\t\t\t\t<Country>deu</Country>\n" +
                "\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t\t</AdministrativeDestination>\n" +
                "\t\t\t</Buyer>"), "Line Level Buyer Validation Failed");
        assertTrue(request.contains("<Vendor>\n" +
                "\t\t\t\t<VendorCode classCode=\"DE-001\">DE-001</VendorCode>\n" +
                "\t\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>Rebhuhnweg 45</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t\t<MainDivision />\n" +
                "\t\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                "\t\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t\t</PhysicalOrigin>\n" +
                "\t\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
                "\t\t\t\t\t<StreetAddress1>Rebhuhnweg 45</StreetAddress1>\n" +
                "\t\t\t\t\t<City>Berlin</City>\n" +
                "\t\t\t\t\t<MainDivision />\n" +
                "\t\t\t\t\t<PostalCode>79539</PostalCode>\n" +
                "\t\t\t\t\t<Country>DEU</Country>\n" +
                "\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
                "\t\t\t\t</AdministrativeOrigin>\n" +
                "\t\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
                "\t\t\t\t\t<TaxRegistrationNumber>DE124363748</TaxRegistrationNumber>\n" +
                "\t\t\t\t</TaxRegistration>\n"), "Line Level Vendor Validation Failed");
    }
}
