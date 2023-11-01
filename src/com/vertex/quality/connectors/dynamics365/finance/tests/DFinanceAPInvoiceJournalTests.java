package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/** Verify that accural is occuring correctly for AP invoices when it's enabled
 * @author SGupta
 */
@Listeners(TestRerunListener.class)
public class DFinanceAPInvoiceJournalTests extends DFinanceBaseTest
{
	//================Data Declaration ===========================

	final String tax = DFinanceLeftMenuNames.TAX.getData();
	final String setup = DFinanceLeftMenuNames.SETUP.getData();
	final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
	final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
	final String xmlInq = DFinanceLeftMenuNames.VERTEX_XML_INQUIRY.getData();

	DFinanceHomePage homePage;
	DFinanceSettingsPage settingsPage;
	DFinanceInvoicePage invoicePage;
	DFinanceXMLInquiryPage xmlInquiryPage;
	DFinanceCreatePurchaseOrderPage createPurchaseOrderPage;
	DFinanceAllSalesOrdersPage allSalesOrdersPage;

	Boolean accountsRec = true;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		homePage = new DFinanceHomePage(driver);
		invoicePage = new DFinanceInvoicePage(driver);
		xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		settingsPage = new DFinanceSettingsPage(driver);

		accountsRec = true;

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.toggleAccrueButton(true);
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		if (!accountsRec) {
			//Enable Accounts Receivable
			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.collapseAll();
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);
			settingsPage = homePage.selectModuleTabLink(taxParametersData);
			settingsPage.toggleAccountsReceivable(true);
		}
	}

	/**
	 * creates a new invoice journal, validates accural for the AP
	 * CD365-609
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void APInvoiceJournalTest()
	{
		//================Data Declaration ===========================

		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		//Disable Accounts Receivable
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		accountsRec = false;
		settingsPage.toggleAccountsReceivable(false);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		//===========Enter Line details ===============================
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String getInvoiceNum = invoicePage.getInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTax("VertexAP");
		invoicePage.setItemTax("ALL");

		//Save the Invoice Journal
		invoicePage.clickSaveButton();

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedBy("000001");

		//===========Change tax for Accural ===============================
		String docId = invoicePage.voucherNo();
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("10.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		createPurchaseOrderPage.clickOkButton();

		//===========POST the invoice ===============================
		invoicePage.postInvoice();

		//===========Validate XML ===============================
		//Navigate to  XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Select the Correct Response from the list
		xmlInquiryPage.getDocumentID(getInvoiceNum);
		assertTrue(
				xmlInquiryPage.verifyExistenceOfDocument("Request", "Accrual", getInvoiceNum),
				"'Requested Type' is not expected");
	}

	@Ignore
	@Test(groups = { "RSAT_Coverage"})
	/**
	 * APInvoice Tax Included Test
	 * CD365-1376
	 */
	public void APInvoiceTaxIncludedTest( )
	{
		//================Data Declaration ===========================

		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		final String ledger = DFinanceLeftMenuNames.GENERAL_LEDGER.getData();
		final String ledger_setup = DFinanceLeftMenuNames.JOURNAL_SETUP.getData();
		final String journal_names = DFinanceLeftMenuNames.JOURNAL_NAMES.getData();

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(ledger);
		homePage.collapseAll();
		homePage.expandModuleCategory(ledger_setup);
		homePage.selectModuleTabLink(journal_names);

		invoicePage.clickAPInvoice();
		invoicePage.toggleAmountIncludeTaxButton(true);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		//===========Enter Line details ===============================
		invoicePage.setAccount("1001");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTax("VertexAP");
		invoicePage.setItemTax("ALL");
		String findId = invoicePage.voucherNo();
		invoicePage.clickSaveButton();

		//===========Validate XML ===============================
		//Navigate to  XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(findId);
		xmlInquiryPage.clickOnFirstResponse();

		String response = invoicePage.getLogResponseValue();
		assertTrue(response.contains("taxIncludedIndicator=\"true\""));

		//===========Delete Invoice ===============================

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);
		invoicePage.deleteInvoice();
	}

	/**
	 * @TestCase CD365-1038
	 * @Description -  Validate that Invoice Register is Sending Vendor Invoice Verification
	 *                 Request/Response for Exflow After Posting Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void invoiceRegisterAndInvoiceApprovalVerificationTest(){

		//Data declaration for page navigation
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

		final int expectedLinesCount = 2;

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
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTaxInvoiceRegister("VertexAP");
		invoicePage.setItemTaxInvoiceRegister("ALL");

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		//Validate the invoice
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();

		//Verify "Actual sales tax amount"
		allSalesOrdersPage.openSalesTaxCalculation();
		String actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("65.05"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Post the invoice
		invoicePage.postInvoice();

		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate that the Invoice is "Vendor Invoice Verification" type
		xmlInquiryPage.getDocumentID(docId);
		assertTrue(xmlInquiryPage.verifyExistenceOfDocument("Request", "Invoice Verification", docId),
				"Document type is not equal to what is expected");

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

		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		String actualSalesTaxAmount1 = createPurchaseOrderPage.getActualSalesTaxAmount();
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

		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line3.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line4.getLineDataMap());

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,
					String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
							expectedAllLinesSet, actualAllLinesSet));
		}

		assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("65.05"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Post the "Invoice approval"
		invoicePage.postInvoice();

		//Navigate to XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);


		assertTrue(xmlInquiryPage.verifyExistenceOfDocument("Request", "Invoice approval", docId),
				"Document type is not equal to what is expected");

		//Select the Correct Response from the list
		xmlInquiryPage.getDocumentID(docId);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogRequestValue();
		assertTrue(response.contains("<TotalTax>65.05</TotalTax>"));
	}

	/** @TestCase CD365-1150
	 *  @Description - Validate the Administrative Destination for Invoice Register/Approval
	 *  @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = {"FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyAdministrativeDestinationForInvoiceRegisterAndInvoiceApprovalTest(){
		//Data declaration for page navigation
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

		settingsPage.selectCompany(usmfCompany);

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
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTaxInvoiceRegister("VertexAP");
		invoicePage.setItemTaxInvoiceRegister("ALL");

		//Over Charge Actual Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("100.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("100.00"),
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOnOKBtn();

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		//Validate the invoice
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();

		//Post the invoice
		invoicePage.postInvoice();

		//Navigate to "XML Inquiry page"
		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate that the Invoice is "Vendor Invoice Verification" type
		xmlInquiryPage.getDocumentID(docId);
		assertTrue(xmlInquiryPage.verifyExistenceOfDocument("Request", "Invoice Verification", docId),
				"Document type is not equal to what is expected");

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
		assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("100.00"),
				"'Total calculated sales tax amount' value is not expected");

		//Over Charge Actual Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("100.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("100.00"),
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOnOKBtn();

		//Set the Account Num
		invoicePage.setAccountNum("200125-001-022");

		//Post the "Invoice approval"
		invoicePage.postInvoice();

		//Navigate to XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Select the Correct Response from the list
		xmlInquiryPage.getDocumentID(docId);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<ExtendedPrice>833.95</ExtendedPrice>"), "Updated Sales Tax Not Present");
		assertTrue(request.contains("<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
				"\t\t\t\t<City>Atlanta</City>\n" +
				"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>"
		));
	}

	/**
	 * @TestCase CD365-1149
	 * @Description - Validate the Administrative Destination for Invoice Journal
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = {"FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyAdministrativeDestinationForApInvoiceJournalTest(){
		final String vendorAccount = "US-111";

		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String taxParametersData = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		settingsPage.selectCompany("USMF");

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		invoicePage.setAccount(vendorAccount);
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","299");
		invoicePage.setOffsetAccount("110130");
		invoicePage.setSaleTax("VertexAP");
		invoicePage.setItemTax("ALL");

		//Save the Invoice Journal
		invoicePage.clickSaveButton();

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedBy("000001");

		//Verify Sales Tax
		String actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("21.64"),
				"'Total calculated sales tax amount' value is not expected: "+actualSalesTaxAmount);

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
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Atlanta</City>\n" +
				"\t\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t\t</AdministrativeDestination>"
		));
		assertTrue(request.contains("returnAssistedParametersIndicator=\"true\""));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"1\">"));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"2\">2</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"3\">US-111</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"4\">331 Tenth Street\n" +
				"Minneapolis, MN 55425 \n" +
				"</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"5\">001</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"8\">001</FlexibleCodeField>\n" +
				"\t\t\t</FlexibleFields>"
		));
	}

	/** @TestCase CD365-1415
	 *  @Description - Verify tax for change in address in Invoice Register and Approval
	 *  @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyTaxForChangeInAddressInInvoiceRegisterAndApprovalTest(){
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

		settingsPage.selectCompany(usmfCompany);

		//Navigate to general ledger, filter APInvReg and enable include tax option
		homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
				DFinanceModulePanelLink.JOURNAL_NAMES);

		settingsPage.clickOnEditButton();
		settingsPage.filterJournalNameAndEnableAmountSalesTax(true,"APInvReg");
		settingsPage.clickOnSaveButton();

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
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTaxInvoiceRegister(salesTaxGroupVertexAP);
		invoicePage.setItemTaxInvoiceRegister(allItemSalesTaxGroup);

		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		invoicePage.clickDeliveryAddressAndSelectAddress("4");
		allSalesOrdersPage.clickOk();

		invoicePage.clickDeliveryAddressAndSelectAddress("4");
		allSalesOrdersPage.clickOk();

		//Verify "Actual sales tax amount"
		String actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");;

		//Verify sales tax with new address and post
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("70.12"),
				"'Total calculated sales tax amount' value is not expected: "+actualSalesTaxAmount);

		invoicePage.postInvoice();

		//Find voucher in approval and verify sales tax is same as updated invoice register with new address
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

		String actualSalesTaxAmount1 = invoicePage.getActualSalesTaxAmountInvoiceApproval();
		assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("70.12"),
				"'Total calculated sales tax amount' value is not expected");

		//Navigate to general ledger, filter APInvReg and disable include tax option
		homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
				DFinanceModulePanelLink.JOURNAL_NAMES);

		settingsPage.clickOnEditButton();
		settingsPage.filterJournalNameAndEnableAmountSalesTax(false,"APInvReg");
		settingsPage.clickOnSaveButton();
		//TODO add verification of Journal entry
	}

	/** @TestCase CD365-1416
	 *  @Description - Verify tax adjustment and vendor for Invoice Approval And Register
	 *  @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = {"RSAT_Coverage"})
	public void verifyTaxAdjustmentAndVendorForInvoiceApprovalAndRegisterTest(){
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

		settingsPage.selectCompany(usmfCompany);

		//Navigate to general ledger, filter APInvReg and enable include tax option
		homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
				DFinanceModulePanelLink.JOURNAL_NAMES);

		settingsPage.clickOnEditButton();
		settingsPage.filterJournalNameAndEnableAmountSalesTax(false,"APInvReg");
		settingsPage.clickOnSaveButton();

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
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String invoiceNumber = invoicePage.getInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTaxInvoiceRegister(salesTaxGroupVertexAP);
		invoicePage.setItemTaxInvoiceRegister(allItemSalesTaxGroup);
		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		//Validate the invoice
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();

		//Verify "Actual sales tax amount"
		String actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("65.05"),
					"'Total calculated sales tax amount' value is not expected: "+actualSalesTaxAmount);

		invoicePage.postInvoice();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(approval);

		//Create a new invoice
		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvApp");
		invoicePage.clickJournal();
		invoicePage.clickFindVouchers();
		invoicePage.getVoucherId(docId);
		invoicePage.clickSelectBtn();
		invoicePage.clickOkBtn();

		//verify sales tax amount
		invoicePage.setSalesAndItemTaxGroupInvoiceApproval(salesTaxGroupVertexAP, allItemSalesTaxGroup);
		allSalesOrdersPage.openSalesTaxCalculation();
		actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("50.04"),
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Post the "Invoice approval"
		invoicePage.postInvoice();

		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

		//Search vendor invoice and validate result
		boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
		assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

		openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
		openVendorInvoicesPage.clickVouchersTab();
		openVendorInvoicesPage.clickPostedSalesTax("2");
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("65.05"),
				assertionTotalCalculatedSalesTaxAmount);

		settingsPage.navigateToDashboardPage();

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify that vendor is present in the XML Inquiry
		xmlInquiryPage.getDocumentID(docId);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Vendor>\n" +
				"\t\t\t<VendorCode classCode=\"US-111\">US-111</VendorCode>\n" +
				"\t\t\t<PhysicalOrigin taxAreaId=\"240530650\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>331 10th Ave SE</StreetAddress1>\n" +
				"\t\t\t\t<City>Minneapolis</City>\n" +
				"\t\t\t\t<MainDivision>MN</MainDivision>\n" +
				"\t\t\t\t<PostalCode>55414-1921</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t<AdministrativeOrigin taxAreaId=\"240530650\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>331 10th Ave SE</StreetAddress1>\n" +
				"\t\t\t\t<City>Minneapolis</City>\n" +
				"\t\t\t\t<MainDivision>MN</MainDivision>\n" +
				"\t\t\t\t<PostalCode>55414-1921</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeOrigin>\n" +
				"\t\t</Vendor>"));

		assertTrue(request.contains("returnAssistedParametersIndicator=\"true\""));
		assertTrue(request.contains("<FlexibleFields>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"1\">0</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"3\">0</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"4\">331 10th Ave SE\n" +
				"Minneapolis, MN 55414-19</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"5\">001</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"8\">001</FlexibleCodeField>\n"
		));
	}

	/**
	 * @TestCase CD365-1615
	 * @Description - Verify sales tax and sales tax line for AP Invoice Journal when AR is disabled
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_Integration"}, retryAnalyzer = TestRerun.class, priority = 17)
	public void verifySalesTaxAndSalesTaxLineForAPInvoiceJournalWhenARIsDisabledTest(){
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		final int expectedLinesCount = 2;
		final Double expectedTaxAmount = 0.94;

		settingsPage.selectCompany(usmfCompany);

		//Disable Accounts Receivable
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		accountsRec = false;
		settingsPage.toggleAccountsReceivable(false);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		//Create New Invoice Journal
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","13");
		invoicePage.setSaleTax("VertexAP");
		invoicePage.setItemTax("ALL");
		invoicePage.clickSaveButton();

		//Verify Sales Tax lines
		createPurchaseOrderPage.navigateToSalesTaxPage();
		int actualLinesCount = createPurchaseOrderPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
				String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
						expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = createPurchaseOrderPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line8.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line7.getLineDataMap());

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,
					String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
							expectedAllLinesSet, actualAllLinesSet));
		}

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		invoicePage.invoiceApprovedBy("000001");

		//Validate invoice and Post
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();
		String docId = invoicePage.voucherNo();
		invoicePage.postInvoice();

		//Navigate to XML inquiry
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Select the Correct Response from the list
		xmlInquiryPage.getDocumentID(docId);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>0.94</TotalTax>"));
	}

	/**
	 * @TestCase CD365-1810
	 * @Description - Verify invoice register/approval accrual for under charge
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_Not_Supported_Yet"})
	public void invoiceRegisterAndInvoiceApprovalAccrualVerificationUnderChargedTest(){

		//Data declaration for page navigation
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(register);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvReg");
		invoicePage.clickJournal();

		//Line Detail Information
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		//Get the invoice number
		String docId = invoicePage.getInvoiceNo();

		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Debit","899");
		invoicePage.setSaleTaxInvoiceRegister("VertexAP");
		invoicePage.setItemTaxInvoiceRegister("ALL");

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedByInvoiceRegister("000001");


		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("0.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
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
		xmlInquiryPage.getDocumentID(docId);
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>0.0</TotalTax>"),
				"Document type is not equal to what is expected to be <TotalTax>0.0</TotalTax>");

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

		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		String actualSalesTaxAmount1 = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount1.equalsIgnoreCase("0.00"),
				"Actual sales tax amount is not correct");

		//Post the "Invoice approval"
		invoicePage.postInvoice();
		//TODO confirm Invoice posted
	}

	/**
	 * @TestCase CD365-1904
	 * @Description - Validate calculated sales tax after under charging amount invoice journal test
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validateCalculatedSalesTaxAfterUnderChargingAmountInvoiceJournalTest(){
		final String vendorAccount = "US-111";

		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		Double expectedTaxAmount = 65.05;

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		settingsPage.selectCompany(usmfCompany);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		invoicePage.setAccount(vendorAccount);
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setOffsetAccount("110130");
		invoicePage.setSaleTax(salesTaxGroupVertexAP);
		invoicePage.setItemTax(allItemSalesTaxGroup);

		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("10.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("10.00"),
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		String calculatedSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("taxAmountJournal1");
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
	}

	/**
	 * @TestCase CD365-1907
	 * @Description - Verify sales tax for invoice approval after undercharging invoice register
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifySalesTaxForInvoiceApprovalAfterUnderchargingInvoiceRegisterTest(){

		//Data declaration for page navigation
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

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
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String invoiceNumber = invoicePage.getInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTaxInvoiceRegister("VertexAP");
		invoicePage.setItemTaxInvoiceRegister("ALL");

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("20.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("20.00"),
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Validate the invoice
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();

		//Post the invoice
		invoicePage.postInvoice();

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

		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

		//Search vendor invoice and validate result
		boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
		assertTrue(isInvoiceFound, String.format(invoiceNumberNotFound, invoiceNumber));

		openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
		openVendorInvoicesPage.clickVouchersTab();
		openVendorInvoicesPage.clickPostedSalesTax("1");
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("68.96"),
				assertionTotalCalculatedSalesTaxAmount);

		settingsPage.navigateToDashboardPage();
		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate Accrual call, total tax, and postToJournal
		String expectedType = "Accrual request";
		xmlInquiryPage.getDocumentID(invoiceNumber);
		String actualType = xmlInquiryPage.getDocumentType();
		assertTrue(actualType.equals(expectedType),
				"Document type is not equal to what is expected");
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>48.96</TotalTax>"),
				"Document type is not equal to what is expected");
		assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
	}

	/**
	 * @TestCase CD365-1917
	 * @Description - Verify Accrual For Invoice Register And Approval When Under Charging
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyAccrualForInvoiceRegisterAndApprovalWhenUnderChargingTest(){

		//Data declaration for page navigation
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String approval = DFinanceLeftMenuNames.INVOICE_APPROVAL.getData();

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
		invoicePage.setAccount("US-111");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String invoiceNumber = invoicePage.getInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setSaleTaxInvoiceRegister("VertexAP");
		invoicePage.setItemTaxInvoiceRegister("ALL");

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("20.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("20.00"),
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Validate the invoice
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();

		//Post the invoice
		invoicePage.postInvoice();

		//Verify Posted Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("70.12"),
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
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("68.52"),
				"'Total actual sales tax amount' value is not expected");

		settingsPage.navigateToDashboardPage();
		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate total tax, and postToJournal
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>50.12</TotalTax>"),
				"Total tax amount is not as expected");
		assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
	}

	/**
	 * @TestCase CD365-1919
	 * @Description - Validate Accrual When Under Charging AP Invoice Injournal While Using Project Account Type
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validateAccrualWhenUnderChargingAPInvoiceJournalWhileUsingProjectAccountTypeTest(){
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		settingsPage.selectCompany(usmfCompany);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		invoicePage.selectAccountType("Project");
		invoicePage.setAccount("000057");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String invoiceNumber = invoicePage.getInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","899");
		invoicePage.setOffsetAccount("200125");
		invoicePage.setSaleTax(salesTaxGroupVertexAP);
		invoicePage.setItemTax(allItemSalesTaxGroup);

		//Verify "Actual sales tax amount"
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("10.00");
		createPurchaseOrderPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getUpdatedActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("-10.00"),
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Post the "Invoice Journal"
		invoicePage.postInvoice();

		//Verify Posted Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("-62.93"),
				"'Total actual sales tax amount' value is not expected");

		settingsPage.navigateToDashboardPage();
		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate total tax, and postToJournal
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>-52.93</TotalTax>"),
				"Total tax amount is not as expected");
		assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));
	}

	/**
	 * @TestCase CD365-2065
	 * @Description - Validate error message does not appear when opening posted Invoice Journal
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validateErrorAppearanceForPostedInvoiceJournalTest(){
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		String invoiceJournal = "Vendor invoice journal";

		settingsPage.selectCompany(usmfCompany);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		//Verify Vendor Invoice Presence and Posted Message Is Not Present
		invoicePage.selectInvoiceType("Posted");
		invoicePage.filterInvoiceFromDisplayedList("00460");
		invoicePage.clickApplyButton();
		invoicePage.selectInvoiceNumber("00460");
		String invoiceType = invoicePage.verifyInvoiceIsOpen("Vendor invoice journal");
		assertTrue(invoiceType.contains("Vendor invoice journal"), "Expected invoice type to be: " + invoiceJournal + ", but found: " + invoiceType);
		Boolean postedErrorMessage = invoicePage.verifyPostedErrorMessage();
		assertTrue(postedErrorMessage.equals(false), "Expected the posted error message to not display, but currently is displaying");

		allSalesOrdersPage.clickOnCloseSalesOrderPage("2");
		postedErrorMessage = invoicePage.verifyPostedErrorMessage();
		assertTrue(postedErrorMessage.equals(false), "Expected the posted error message to not display, but currently is displaying");
	}

	/**
	 * @TestCase CD365-2067
	 * @Description - Verify Sales Tax Group configured to Vendor defaults when creating AP Invoice Journal
	 * @Author Vivek Olumbe
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyVendorDefaultSalesTaxGroupTest( )
	{
		//================Data Declaration ===========================
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		final String ledger = DFinanceLeftMenuNames.GENERAL_LEDGER.getData();
		final String ledger_setup = DFinanceLeftMenuNames.JOURNAL_SETUP.getData();
		final String journal_names = DFinanceLeftMenuNames.JOURNAL_NAMES.getData();
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();

		//================script implementation========================

		// Enable Advanced Tax Group
		settingsPage.toggleAdvancedTaxGroup(true);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(ledger);
		homePage.collapseAll();
		homePage.expandModuleCategory(ledger_setup);
		homePage.selectModuleTabLink(journal_names);

		invoicePage.clickAPInvoice();
		invoicePage.toggleAmountIncludeTaxButton(true);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		//===========Enter Line details and verify default tax group ===============================
		invoicePage.setAccount("1001");
		String saleTaxGroup = invoicePage.getSaleTaxGroup();
		assertEquals(saleTaxGroup, salesTaxGroupVertexAP);
		invoicePage.clickSaveButton();


		//===========Delete Invoice ===============================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);
		invoicePage.deleteInvoice();


		//===========Disable Advanced Tax Group  ===============================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleAdvancedTaxGroup(false);
	}

	/**
	 * @TestCase CD365-2068
	 * @Description - Verify changing offset account does not clear tax group fields
	 * @Author Vivek Olumbe
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifySaleTaxGroupsAfterEnteringOffsetAccountTest( )
	{
		//================Data Declaration ===========================
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();
		final String ledger = DFinanceLeftMenuNames.GENERAL_LEDGER.getData();
		final String ledger_setup = DFinanceLeftMenuNames.JOURNAL_SETUP.getData();
		final String journal_names = DFinanceLeftMenuNames.JOURNAL_NAMES.getData();
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();

		//================script implementation========================

		// Enable Advanced Tax Group
		settingsPage.toggleAdvancedTaxGroup(true);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(ledger);
		homePage.collapseAll();
		homePage.expandModuleCategory(ledger_setup);
		homePage.selectModuleTabLink(journal_names);

		invoicePage.clickAPInvoice();
		invoicePage.toggleAmountIncludeTaxButton(true);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();

		//===========Enter Line details and verify item sales tax group ===============================
		invoicePage.setAccount("1001");
		invoicePage.setItemTax(allItemSalesTaxGroup);
		invoicePage.setOffsetAccount("110130");

		String saleTaxGroup = invoicePage.getSaleTaxGroup();
		assertEquals(saleTaxGroup, salesTaxGroupVertexAP);
		String itemSalesTaxGroup = invoicePage.getItemSalesTaxGroup();
		assertEquals(itemSalesTaxGroup, allItemSalesTaxGroup);
		String offsetAccount = invoicePage.getOffsetAccount();
		assertEquals(offsetAccount, "110130");
		invoicePage.clickSaveButton();


		//===========Delete Invoice ===============================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);
		invoicePage.deleteInvoice();


		//===========Disable Advanced Tax Group  ===============================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleAdvancedTaxGroup(false);
	}

	/**
	 * @TestCase CD365-2515
	 * @Description - Verify tax drivers for ledger account in XML request
	 * @Author Vivek Olumbe
	 */
	@Test(groups = {"FO_AP", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyCostCenterAndDepartmentCodeLedgerAccountTest(){
		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String journal = DFinanceLeftMenuNames.INVOICE_JOURNAL.getData();

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);

		settingsPage.selectCompany(usmfCompany);

		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvoice");
		invoicePage.clickJournal();
		String journalNo = invoicePage.getJournalNumber();

		invoicePage.selectAccountType("Vendor");
		invoicePage.setAccount("1001");
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String voucherNo1 = invoicePage.voucherNo();
		invoicePage.setCreditOrDebit("Credit","800");
		invoicePage.setOffsetAccount("606300-001-022-008-Audio-000002");
		invoicePage.setSaleTax(salesTaxGroupVertexAP);
		invoicePage.setItemTax(allItemSalesTaxGroup);

		//Send tax calculation request
		createPurchaseOrderPage.navigateToSalesTaxPage();
		allSalesOrdersPage.clickOk();

		//Verify Offset Ledger Account
		settingsPage.navigateToDashboardPage();
		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(voucherNo1);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("costCenter=\"008\""), "Cost Center Validation Failed");
		assertTrue(request.contains("departmentCode=\"022\""), "Department Code Validation Failed");
		assertTrue(request.contains("generalLedgerAccount=\"606300-001-022-008-Audio-000002\""), "General Ledger Account Validation Failed");

		//Navigate to invoice journal
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(journal);
		invoicePage.filterInvoiceFromDisplayedList(journalNo);
		invoicePage.clickJournal();

		invoicePage.clickNewLineButton();
		invoicePage.selectAccountType("Vendor");
		invoicePage.setAccount("1002");
		invoicePage.setInvoiceDate();
		invoicePage.setCreditOrDebit("Credit","100");
		invoicePage.setSaleTax("");
		invoicePage.setItemTax("");
		String voucherNo2 = invoicePage.voucherNo();

		invoicePage.clickNewLineButton();
		invoicePage.selectAccountType("Ledger");
		invoicePage.setAccount("618200-001-023-009-Audio");
		invoicePage.setInvoiceDate();
		invoicePage.setCreditOrDebit("Debit","100");
		invoicePage.setSaleTax(salesTaxGroupVertexAP);
		invoicePage.setItemTax(allItemSalesTaxGroup);


		//Verify Ledger Account
		settingsPage.navigateToDashboardPage();
		xmlInquiryPage = homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(voucherNo2);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("costCenter=\"009\""), "Cost Center Validation Failed");
		assertTrue(request.contains("departmentCode=\"023\""), "Department Code Validation Failed");
		assertTrue(request.contains("generalLedgerAccount=\"618200-001-023-009-Audio\""), "General Ledger Account Validation Failed");
	}
}