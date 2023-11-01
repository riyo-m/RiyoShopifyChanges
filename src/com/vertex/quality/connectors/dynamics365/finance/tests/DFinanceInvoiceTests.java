package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Shiva Mothkula
 * All tests related to Invoice creation
 */
@Listeners(TestRerunListener.class)
public class DFinanceInvoiceTests extends DFinanceBaseTest
{
	Boolean advTaxGroup = false;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		advTaxGroup = false;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		//Enable Accounts Receivable
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.toggleAccountsReceivable(true);
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		if (advTaxGroup) {
			toggleONOFFAdvanceTaxGroup(false);
		}
	}

	/*
	 * setting Invoice creation specific configuration
	@Override
	public DFinanceHomePage loadInitialTestPage( )
	{
		DFinanceHomePage homePage = super.loadInitialTestPage();
		configureVertexConnection(homePage, DFINANCE_PO_TRUSTED_ID, DFINANCE_TAX_CALCULATION_URL,
			DFINANCE_ADDRESS_VALIDATION_URL);
		return homePage;
	}

	/**
	 * setting the default configuration

	@Override
	protected void handleLastTestPage( final ITestResult testResult )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		configureVertexConnection(homePage, DFINANCE_DEFAULT_TRUSTED_ID, DFINANCE_TAX_CALCULATION_URL,
			DFINANCE_ADDRESS_VALIDATION_URL);
		super.handleLastTestPage(testResult);
	}
	 */

	/**
	 * @Name Create Invoice(Purchase)
	 * @Description - To create and verify the invoice for the tax related information on a Purchase
	 * Order transaction
	 * CD365-170
	 */
	@Test(groups = { "smoke", "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void invoiceCreationTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);

		String vendorAccount = DFinanceConstantDataResource.VENDOR_ACCOUNT.getData();
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
		DFinanceAllSalesOrdersPage salesOrdersPage = new DFinanceAllSalesOrdersPage(driver);


		final String itemNumber = "A0001";
		final String quantity = "1";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 0.94;

		final int expectedLinesCount = 2;

		//================script implementation========================

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		//Click on "New" option
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		// enter vendor account number
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount);

		// click Ok button
		createPurchaseOrderPage.clickOkButton();

		// click Header link
		createPurchaseOrderPage.clickOnHeader();

		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format("Purchase Order# %s", purchaseOrder));

		// set Sales Tax Group as VertexAP
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		// click Lines link
		createPurchaseOrderPage.clickOnLines();

		// add a line item to the PO
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice, 0);

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

		int actualLinesCount = createPurchaseOrderPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
			String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
				expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = salesOrdersPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line1.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line2.getLineDataMap());

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

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		// navigate to Confirmation page to confirm the PO
		createPurchaseOrderPage.navigateToConfirmationPage();

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "PO Confirmation failed");

		// navigate to Receive --> GENERATE --> ProductReceipt page
		createPurchaseOrderPage.navigateToProductReceiptPage();

		// set product number
		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
		Calendar cal = Calendar.getInstance();
		String productReceiptNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
		VertexLogger.log(String.format("Product Receipt Number: %s", productReceiptNumber));

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "PO Confirmation failed");

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
			String.format("Expected Match Status: %s, but actual Match Status: %s", expectedMatchStatus, matchStatus));

		// Apply prepayment
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();

		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
			String.format("Expected Invoice#: %s, but actual Invoice#: %s", invoiceNumber, displayedInvoiceNumber));

		applyPrepaymentsPage.closeApplyPrepayment();

		// Post Invoice
		createPurchaseOrderPage.clickPostOption();

		// validate invoice posting
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount, invoiceNumber);
		assertTrue(isPosted, String.format("Invoice#: %s posting failed", invoiceNumber));

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

		actualLinesCount = createPurchaseOrderPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
			String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
				expectedLinesCount, actualLinesCount));

		allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = salesOrdersPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
			expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,
					String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
							expectedAllLinesSet, actualAllLinesSet));
		}

		//Verify the "Total calculated sales tax amount" value
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
			"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
			"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();

		String actualSalesTaxCode = openVendorInvoicesPage.getSalesTaxCode(0);
		assertTrue(actualSalesTaxCode.equalsIgnoreCase(salesTaxGroup));

		//Find purchase order and verify if it's found
		allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		boolean isPurchaseOrderFound = allPurchaseOrdersPage.searchPurchaseOrder(purchaseOrder);
		assertTrue(isPurchaseOrderFound, String.format("PO#: %s not found", purchaseOrder));

	}

	/**
	 * @TestCase CD365-292
	 * @Name Create Invoice(Purchase) for Pittsburgh
	 * @Description - To create and verify the invoice for the tax related information on a Purchase Order transaction
	 * @Author Vishwa
	 */
	@Test(groups = {"smoke", "FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void invoiceCreationPittsburghTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		String vendorAccount = DFinanceConstantDataResource.VENDOR_ACCOUNT_Pittsburgh.getData();
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final String itemNumber = "A0001";
		final String quantity = "1";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 0.94;

		final int expectedLinesCount = 2;

		//================script implementation========================

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		//Click on "New" option
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		// enter vendor account number
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount);

		// click Ok button
		createPurchaseOrderPage.clickOkButton();

		// click Header link
		createPurchaseOrderPage.clickOnHeader();

		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format("Purchase Order# %s", purchaseOrder));

		// set Sales Tax Group as VertexAP
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		// click Lines link
		createPurchaseOrderPage.clickOnLines();

		// add a line item to the PO
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice, 0);

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

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

		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line1.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line2.getLineDataMap());

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

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify Administrative Destination and Destination
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Buyer>\n" +
				"\t\t\t<Company>USMF</Company>\n" +
				"\t\t\t<Destination taxAreaId=\"390030000\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>1000 Airport Blvd</StreetAddress1>\n" +
				"\t\t\t\t<City>Pittsburgh</City>\n" +
				"\t\t\t\t<MainDivision>PA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>15231-1001</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
				"\t\t\t\t<City>Atlanta</City>\n" +
				"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t</Buyer>"));
	}

	/**
	 * @TestCase CD365-287
	 * @Name Create Invoice(Purchase) for Pittsburgh
	 * @Description - To create and verify the invoice for the 0 tax with multiple line items related information on a Purchase Order transaction
	 * @Author Vishwa
	 */
	@Test(groups = {"smoke", "FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void invoiceCreationExemptWithMultipleLineItemsTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);

		String vendorAccount = DFinanceConstantDataResource.VENDOR_ACCOUNT_EXEMPT.getData();
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final double expectedTaxAmount = 0.00;

		//================script implementation========================

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		//Click on "New" option
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		// enter vendor account number
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount);

		// click Ok button
		createPurchaseOrderPage.clickOkButton();

		// click Header link
		createPurchaseOrderPage.clickOnHeader();

		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format("Purchase Order# %s", purchaseOrder));

		// set Sales Tax Group as VertexAP
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		// click Lines link
		createPurchaseOrderPage.clickOnLines();

		// add multiple line item to the PO
		createPurchaseOrderPage.fill1STItemsInfo("A0001","1", "11", "1", 0);
		createPurchaseOrderPage.fillItemsInfo("A0002","1", "11", "5", 2);
		createPurchaseOrderPage.fillItemsInfo("C0001","1", "13", "2", 3);

		//createPurchaseOrderPage.addItemLine("A0001", "1", "1", "11", unitPrice, 0);
		//createPurchaseOrderPage.addItemLine("1000", quantity, site, warehouse, unitPrice, 1);

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
	}

	/**
	 * @TestCase CD365-285
	 * @Name Create Invoice(Purchase) for Pittsburgh
	 * @Description - To create and verify the invoice for the 0 tax with multiple line items related information on a Purchase Order transaction
	 * @Author Vishwa
	 */
	@Test(groups = {"smoke", "FO_AP_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void purchaseOrderCreationCaliforniaExemptWithMultipleLineItemsTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);

		String vendorAccount = DFinanceConstantDataResource.VENDOR_ACCOUNT_EXEMPT.getData();
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final String deliveryAddress = "CA Address";

		final double expectedTaxAmount = 0.00;

		//================script implementation========================

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		//Click on "New" option
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		// enter vendor account number
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount);

		// enter Delivery Address
		createPurchaseOrderPage.enterDeliveryAddress(deliveryAddress);

		// click Ok button
		createPurchaseOrderPage.clickOkButton();

		// click Header link
		createPurchaseOrderPage.clickOnHeader();

		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format("Purchase Order# %s", purchaseOrder));

		// set Sales Tax Group as VertexAP
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		// click Lines link
		createPurchaseOrderPage.clickOnLines();

		// add multiple line item to the PO
		//createPurchaseOrderPage.addMultipleItemLine(itemNumber, quantity, site, warehouse, unitPrice);
		createPurchaseOrderPage.fill1STItemsInfo("A0001","1", "11", "1", 0);
		createPurchaseOrderPage.fillItemsInfo("A0002","1", "11", "5", 1);

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
	}

	/**
	 * @TestCase CD365-835
	 * @Name
	 * @Description - Validate Quote call for Sales Order with Invoice and validate Sales Tax for Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"smoke", "FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validatingInvoiceAndQuoteForSalesOrderTest(){

		String itemNumber = "1000";
		String quantity = "30";
		String unitPrice = "2450";
		String site = "2";
		String wareHouse = "22";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		String salesOrderNumber1 = allSalesOrdersPage.getSalesOrderNumber();

		//Add line item to Sales Order
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		//Navigate to Sales Tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		// To fix: This assert statement should be testing the first sales order
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("5,880.00"),
				"'Total calculated sales tax amount' value is not expected");

		//Click on "OK" button
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to "Pick and pack" Tab
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
		allSalesOrdersPage.openPickingList();

		//Click on "OK" button in dialog box
		allSalesOrdersPage.clickOnOKBtn();

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKPopUp();

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKBtn();

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKPopUp();

		//================== Create another Sales order ===================//
		allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.openNewSalesOrderFromSalesOrderPage();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		allSalesOrdersPage.clickOkBTN();

		allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		String salesOrderNumber2 = allSalesOrdersPage.getSalesOrderNumber();

		//Add line item to Sales Order
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, "1", unitPrice, 0);

		//Navigate to Sales Tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Click on "OK" button
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to "Pick and pack" Tab
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
		allSalesOrdersPage.openPickingList();

		//Click on "OK" button in dialog box
		allSalesOrdersPage.clickOnOKBtn();

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKPopUp();

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKBtn();

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKPopUp();

		//Post both invoices
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();

		//Select Invoice account in the Summary Order Column
		allSalesOrdersSecondPage.postedInvoiceAccountAndArrange();

		//Add the second most recent sales order to the Posting Invoice
		allSalesOrdersSecondPage.addASecondInvoiceToPostingInvoice();

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKBtn();

		//Click on "OK" button to post document
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on the Invoice link underneath Journals
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();

		//Click on the Posted Sales Tax tab
		allSalesOrdersSecondPage.clickOnPostedSalesTax();

		//Verify the Sales Tax Amount
		//Verify the "Total calculated sales tax amount" value
		calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("6,076.00"),
				"'Total calculated sales tax amount' value is not expected");
		settingsPage.navigateToDashboardPage();

		//Navigate to Vertex XML Inquiry
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Finds the first Sales Order and confirms that it's a Quote Request Type
		assertTrue(verifyNumberOfResponsesAndRequests(salesOrderNumber1, 4),
				"The appropriate number of Request/Response type is not correct");

		//Finds the second Sales Order and confirms that it's a Quote Request Type
		assertTrue(verifyNumberOfResponsesAndRequests(salesOrderNumber2, 4),
				"The appropriate number of Request/Response type is not correct");

	}

	/**
	 * @TestCase CD365-1030
	 * @Description - Validate Tax on Return Order Invoice is populating correctly for an Order with Multiple Discounts.
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"smoke", "FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validatingTaxOnReturnOrderInvoiceTest(){
		String itemNumber = "0005";
		String quantity = "30";
		String deliveryRemainder = "10";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		settingsPage.selectCompany("USRT");

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Get the new Sales Order Number
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Add Multiple line items with multiple quantities to line item of Sales Order
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersSecondPage.clickPriceAndDiscount();
		allSalesOrdersSecondPage.multilineDiscountPercentage("2");
		allSalesOrdersSecondPage.reasonCode("CustSat");
		allSalesOrdersPage.addLineItem();

		allSalesOrdersSecondPage.fillItemsInfoForUSRT("0008","1",  1);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersPage.addLineItem();

		allSalesOrdersSecondPage.fillItemsInfoForUSRT("0015", "2",  2);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");

		//Navigate to Sales Tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("12.08"),
				calculatedSalesTaxAmount + " : "+"'Total calculated sales tax amount' value is not expected");

		//Click to close Sales Tax
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Click the "Complete" Tab to complete the Sales Order
		allSalesOrdersSecondPage.clickOnComplete();
		String balance1 = allSalesOrdersSecondPage.getBalance();
		allSalesOrdersSecondPage.addPayment(balance1, "1");

		//Modify the Sales Order
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.salesOrderTabName);
		allSalesOrdersPage.modifySalesOrder();

		//Click on the "Update Line" Tab in the Sales Order line table
		allSalesOrdersPage.clickOnUpdateLine();

		//Select delivery remainder
		allSalesOrdersPage.selectDeliveryRemainder();

		//Update quantity
		allSalesOrdersPage.changeQuantity(deliveryRemainder);
		allSalesOrdersPage.clickOnOKBtn();

		//Navigate to Sales Tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		//Verify the "Total calculated sales tax amount" value
		calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("7.01"),
				"'Total calculated sales tax amount' value is not expected");

		//Click on to close Sales Tax
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Click the "Complete" Tab to complete the Sales Order
		allSalesOrdersSecondPage.clickOnComplete();
		allSalesOrdersSecondPage.clickOnSubmit();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to "All Return Orders" Page
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_RETURN_ORDERS);

		//Create a new return order
		allSalesOrdersPage.openNewSalesOrder();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Return Order" screen
		allSalesOrdersPage.clickOkBTN();

		//Get the new Sales Order Number
		String newSalesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Find the recent Sales Order
		allSalesOrdersSecondPage.findSalesOrder();
		allSalesOrdersSecondPage.selectRecentSalesOrder(salesOrderNumber);

		allSalesOrdersPage.clickOnOKBtn();

		//Enter the Return reason code, retail return reason code, and disposition code
		allSalesOrdersSecondPage.retailReturnReasonCode();
		allSalesOrdersSecondPage.returnReason("11", "21");

		//Complete the Return Order
		allSalesOrdersSecondPage.clickOnCompleteJavaScript();
		allSalesOrdersSecondPage.clickOnSubmit();

		//Navigate to "All Sales Orders" Page
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Verify the the most recent order status is "Returned"
		allSalesOrdersPage.searchCreatedSalesOrder(newSalesOrderNumber);
		String expectedType = "Returned order";

		String actualType = allSalesOrdersPage.getOrderType();

		assertTrue(actualType.equals(expectedType),
				"Order type is not equal to what is expected");

		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Journal invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();

		//Click on the Posted Sales Tax tab
		allSalesOrdersSecondPage.clickOnPostedSalesTax();

		//Verify "Posted Sales Tax" is negative of original Sales Order
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("-2.54"),
				"'Total calculated sales tax amount' value is not expected");
	}

	/**
	 * @TestCase CD365-1075
	 * @Description - Verify that Exchange Rate Gain/Loss is not displayed on Free Text Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_General_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void verifyExchangeRateGainAndLossIsNotDisplayedOnFreeTextInvoiceTest(){

		final String customerAccount = "004021";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		settingsPage.selectCompany("USMF");

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.INVOICES,
				DFinanceModulePanelLink.ALL_FREE_TEXT_INVOICES);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();

		//Set the customer account
		allSalesOrdersPage.setCustomerAccountForAllFreeInvoice(customerAccount);

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line item to Sales Order
		allSalesOrdersSecondPage.fillItemsInfoForFreeTextInvoice("110110", "ALL", "1000", 0);
		allSalesOrdersSecondPage.fillItemsInfoForFreeTextInvoice("110110", "ALL", "1200", 1);

		//Verify Sales Tax
		String expectedTaxAmount = "176.00";
		allSalesOrdersPage.openSalesTaxCalculation();

		String actualTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(actualTaxAmount.contains(expectedTaxAmount),
				"'Total calculated sales tax amount' value is not expected");
	}

	/**
	 * @TestCase CD365-185
	 * @Description - Create Sales Order with taxpayer not registered
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"smoke","FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void creatingSalesOrderWithTaxpayerNotRegisteredTest() {
		String itemNumber = "1000";
		String quantity = "30";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany("USMF");

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("Auto_DP");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Get the new Sales Order Number and add 2 line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, "2", "21", quantity, "10", 0);
		allSalesOrdersPage.addLineItem();
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, "2", "21", quantity, "10", 1);
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("9.00"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate total tax amount in the Response
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String request = xmlInquiryPage.getLogResponseValue();
		assertTrue(request.contains("<TotalTax>9.0</TotalTax>"));
	}

	/**
	 * @TestCase CD365-257
	 * @Description - Creating Sales order with 1 Flex field and invoice, while verifying tax area id
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = {"FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validatingFlexFieldParametersForSalesOrderAndVerifyingTaxAreaIdTest() {
		String itemNumber = "1000";
		String quantity = "30";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany(usmfCompany);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("D_US_PA1");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line item and update delivery address
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, "2", "21", quantity, "10", 0);
		allSalesOrdersSecondPage.clickLineDetailsTab("TabPageAddress");
		allSalesOrdersSecondPage.updateDeliveryAddress("TX");

		//Verify the "Total calculated sales tax amount" value
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.verifyTaxAreaIdLineAmount(3);
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("24.75"),
				"'Total calculated sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify the Flex Field Date Field and Tax Area Id is present in the Request
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String request = xmlInquiryPage.getLogResponseValue();
		assertTrue(request.contains("<FlexibleDateField fieldId=\"1\">"));
		assertTrue(request.contains("<Destination taxAreaId=\"441130760\" locationCustomsStatus=\"FREE_CIRCULATION\">"));
	}


	/**
	 * @TestCase CD365-1396
	 * @Description - Verify Posted Sales Tax For Invoice Pool
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyPostedSalesTaxForInvoicePoolTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceInvoicePage invoicePage = new DFinanceInvoicePage(driver);
		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);

		final String payable = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
		final String invoice = DFinanceLeftMenuNames.INVOICES.getData();
		final String register = DFinanceLeftMenuNames.INVOICE_REGISTER.getData();
		final String pool = DFinanceLeftMenuNames.INVOICE_POOL.getData();

		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 3.74;

		settingsPage.selectCompany(usmfCompany);

		//Navigate to general ledger, filter APInvReg and enable include tax option
		homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
				DFinanceModulePanelLink.JOURNAL_NAMES);

		settingsPage.clickOnEditButton();
		settingsPage.filterJournalNameAndEnableAmountSalesTax(true,"APInvReg");
		settingsPage.clickOnSaveButton();

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(D0003, quantity, site, warehouse, unitPrice, 0);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);
		createPurchaseOrderPage.addItemLine(D0001, quantity, site, warehouse, unitPrice, 1);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);

		//Validate Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertEquals(Double.parseDouble(calculatedSalesTaxAmount), expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertEquals(Double.parseDouble(actualSalesTaxAmount), expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		//Confirm Purchase order
		createPurchaseOrderPage.navigateToConfirmationPage();
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(register);
		invoicePage.clickNewButton();
		invoicePage.setInvoiceName("APInvReg");
		invoicePage.clickJournal();
		String docId = invoicePage.voucherNo();

		//Line Detail Information
		invoicePage.setAccount(vendorAccount1001);
		invoicePage.setInvoiceDate();
		invoicePage.setInvoiceNo();
		String getInvoiceNum = invoicePage.getInvoiceNo();
		invoicePage.setDescription("Invoice");
		invoicePage.setCreditOrDebit("Credit","51.74");
		invoicePage.setPurchaseOrderNumber(purchaseOrder);
		invoicePage.setSaleTaxInvoiceRegister(salesTaxGroupVertexAP);
		invoicePage.setItemTaxInvoiceRegister("ALL");

		//Clicks on the Invoice tab and selects who approved the invoice
		invoicePage.invoiceApprovedByInvoiceRegister("000001");

		createPurchaseOrderPage.navigateToSalesTaxPage();
		actualSalesTaxAmount = invoicePage.getActualOrCalculatedSalesTaxAmountInvoice("correctedTaxAmount");

		assertTrue(Double.parseDouble(actualSalesTaxAmount)==(3.11),
				actualSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);

		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		//Validate and post the invoice
		invoicePage.clickOnValidateTab();
		invoicePage.validateInvoiceRegister();
		invoicePage.postInvoice();

		settingsPage.navigateToDashboardPage();
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(payable);
		homePage.collapseAll();
		homePage.expandModuleCategory(invoice);
		homePage.selectModuleTabLink(pool);

		invoicePage.getOrderByID("Voucher",docId);
		invoicePage.clickPurchaseOrder();

		//Update Match Status
		createPurchaseOrderPage.clickUpdateMatchStatusButton();
		final String expectedMatchStatus = "Passed";
		String matchStatus = createPurchaseOrderPage.getMatchStatus();
		assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
				String.format(matchStatusString, expectedMatchStatus, matchStatus));

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, getInvoiceNum);
		assertTrue(isPosted, String.format(invoicePostingFailed, getInvoiceNum));

		//Navigate to purchase order and find the purchase order
		settingsPage.navigateToDashboardPage();
		homePage.navigateToAllPurchaseOrdersPage();
		allPurchaseOrdersPage.searchPurchaseOrder(purchaseOrder);
		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		openVendorInvoicesPage.clickPostedSalesTax("1");
		Double expectedPostedSalesTaxAmount = 3.74;
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedPostedSalesTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);

		settingsPage.navigateToDashboardPage();

		//Navigate to general ledger, filter APInvReg and disable include tax option
		homePage.navigateToPage(DFinanceLeftMenuModule.GENERAL_LEDGER, DFinanceModulePanelCategory.JOURNAL_SETUP,
				DFinanceModulePanelLink.JOURNAL_NAMES);

		settingsPage.clickOnEditButton();
		settingsPage.filterJournalNameAndEnableAmountSalesTax(false,"APInvReg");
		settingsPage.clickOnSaveButton();
	}

	/**
	 * @TestCase CD365-1372
	 * @Description - Validate Destination address for POS return order
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = {"FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
	public void validateDestinationAddressForPOSReturnOrderTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		settingsPage.selectCompany(usrtCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_RETURN_ORDERS);

		allSalesOrdersPage.openNewSalesOrder();

		//Enter "Customer account"
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();

		//Enter the Retail return reason code
		allSalesOrdersSecondPage.enterItem("0001", 1);
		allSalesOrdersSecondPage.retailReturnReasonCode();
		allSalesOrdersSecondPage.retailReturnReasonCode();

		//Complete the Return Order
		allSalesOrdersSecondPage.clickOnCompleteJavaScript();
		allSalesOrdersSecondPage.clickOnSubmit();

		//Update Return Order
		allSalesOrdersPage.clickOnUpdateLine();
		allSalesOrdersPage.clickOnRegistration("11");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnAddRegistrationLine();
		allSalesOrdersPage.clickOnConfirmRegistration();
		allSalesOrdersPage.clickOnCloseBtn("3");

		//Post packing slip
		allSalesOrdersPage.postPackingSlip("2");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumberForReturnOrder();
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompleted = allSalesOrdersPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "Generating Invoice Failed");

		//Journal Invoice and verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("-3.58"),
				assertionTotalCalculatedSalesTaxAmount);

		//Navigate to Vertex XML Inquiry
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"30\">004021</CustomerCode>\n" +
				"\t\t\t<Destination taxAreaId=\"50759991\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>1 Market St</StreetAddress1>\n" +
				"\t\t\t\t<City>San Francisco</City>\n" +
				"\t\t\t\t<MainDivision>CA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>94105-1420</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>91 Apple Dr</StreetAddress1>\n" +
				"\t\t\t\t<City>Exton</City>\n" +
				"\t\t\t\t<MainDivision>PA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>19341</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t</Customer>"));
		assertTrue(request.contains("<ExtendedPrice>-51.44</ExtendedPrice>"));
		assertTrue(request.contains("returnAssistedParametersIndicator=\"true\""));
		assertTrue(request.contains("<FlexibleCodeField fieldId=\"1\">91 Apple Dr\n" +
				"Exton, PA 19341\n" +
				"USA</FlexibleCodeField>"));
		assertTrue(request.contains("<FlexibleCodeField fieldId=\"2\">USA</FlexibleCodeField>"));
		assertTrue(request.contains("<FlexibleCodeField fieldId=\"4\">0</FlexibleCodeField>"));
	}

	/**
	 * @TestCase CD365-155
	 * @Description - Create a sales order validate tax, change quantity and location, then invoice
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = {"RSAT_Coverage"}, retryAnalyzer = TestRerun.class)
	public void createInvoiceAndChangeQuantityAndLocationTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "1000";
		final String quantity = "5";
		final String unitPrice = "100";
		final String site = "2";
		final String warehouse = "21";
		final Double expectedTaxAmount = 120.00;

		settingsPage.selectCompany(usmfCompany);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 1);
		allSalesOrdersSecondPage.fillItemsInfo("D0006", site, warehouse, quantity, unitPrice, 2);

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Update the line items delete an item, change the site/warehouse, and quantity
		allSalesOrdersPage.removeLineItem("D0006");
		allSalesOrdersSecondPage.updateLineItemsInfo("1","13", quantity, unitPrice, "0", 1);
		allSalesOrdersSecondPage.selectLine(3);
		allSalesOrdersSecondPage.updateLineItemsInfo(site, warehouse,"1", unitPrice, "0",2);

		//Validate Sales Tax after updating lines
		allSalesOrdersPage.openSalesTaxCalculation();
		calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == 48.00,
				assertionTotalCalculatedSalesTaxAmount);
		actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == 48.00,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to  XML inquiry and verify tax amount
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>48.0</TotalTax>"));
	}

//	/**
//	 * @TestCase CD365-
//	 * @Description - Verify sales tax in Vendor Invoice Entry
//	 * @Author Mario Saint-Fleur
//	 */
//	@Test(groups = {"FO_AR_regression", "FO_ALL"}, retryAnalyzer = TestRerun.class)
//	public void verifySalesTaxInVendorInvoiceEntryTest() {
//		DFinanceHomePage homePage = new DFinanceHomePage(driver);
//		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
//		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
//		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
//		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = new DFinanceAllPurchaseOrdersPage(driver);
//		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
//
//		final String itemNumber = "C0001";
//		final String quantity = "5";
//		final String unitPrice = "100";
//		final String site = "2";
//		final String warehouse = "21";
//		final Double expectedTaxAmount = 120.00;
//
//		String accountsPayables = DFinanceLeftMenuNames.ACCOUNTS_PAYABLES.getData();
//		String workSpaces = DFinanceLeftMenuNames.WORKSPACES.getData();
//		String vendorInvoiceEntry = DFinanceLeftMenuNames.VENDOR_INVOICE_ENTRY.getData();
//
//		settingsPage.selectCompany(usmfCompany);
//
//		//Navigate to Vendor Invoice Entry
//		homePage.navigateToPageOfModule(accountsPayables, workSpaces, vendorInvoiceEntry);
//
//		//Create vendor and add a line item
//		allPurchaseOrdersPage.clickNewVendorInvoice();
//		createPurchaseOrderPage.setInvoiceAccountNumber("US-101");
//		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
//		allSalesOrdersSecondPage.clickOnSetUpTab();
//		allSalesOrdersSecondPage.selectItemSalesGroupType(allItemSalesTaxGroup);
//		allSalesOrdersPage.setVendorInvoiceTaxGroup(salesTaxGroupVertexAP);
//
//		//Verify sales tax and error message is not displayed
//		allSalesOrdersPage.openSalesTaxCalculation();
//		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
//		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
//				assertionTotalCalculatedSalesTaxAmount);
//		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
//		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
//				assertionTotalActualSalesTaxAmount);
//		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();
//	}

	/** @TestCase CD365-1554
	 * @Description - Validate Sales Order Sales Tax For Partial Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesOrderSalesTaxForPartialInvoiceTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "1000";
		final String quantity = "50";
		final String unitPrice = "100";
		final String site = "2";
		final String warehouse = "21";
		final Double expectedTaxAmount = 800.00;

		settingsPage.selectCompany(usmfCompany);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line items
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickOnDelivery();
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 1);
		allSalesOrdersSecondPage.clickOnDelivery();
		allSalesOrdersSecondPage.changeUnderDelivery("100");

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();

		//Update Quantity for item
		allSalesOrdersPage.clickOnOKPopUp();
		allSalesOrdersPage.clickOnUpdateLine();
		allSalesOrdersPage.selectDeliveryRemainder();
		allSalesOrdersPage.changeQuantity("25");
		allSalesOrdersPage.clickOnOKBtn();

		//Click "Generate picking list"
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Validate Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("600.00"),
				assertionTotalCalculatedSalesTaxAmount);
	}

	/**
	 * @TestCase CD365-1583
	 * @Description - Verify that Tax Registration is present when enabled US to EU
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxRegistrationIsPresentWhenEnabledUSToEUTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		final String quantity = "2";
		final String site = "2";
		final String wareHouse = "21";
		final String itemNumber = "1000";
		final String unitPrice = "150.00";

		final double expectedSalesTaxAmount = 0.00;

		settingsPage.selectCompany(usmfCompany);

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create a new Sales Order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("Test Germany");
		allSalesOrdersPage.clickOkBTN();
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedSalesTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Verify Tax Registration is present
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<TaxRegistration hasPhysicalPresenceIndicator=\"false\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t\t<TaxRegistrationNumber>DE789564231</TaxRegistrationNumber>\n" +
				"\t\t\t\t</TaxRegistration>"));

		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
	}
}