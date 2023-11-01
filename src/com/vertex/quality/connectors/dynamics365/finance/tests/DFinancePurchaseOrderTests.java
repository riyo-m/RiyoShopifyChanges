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

import static org.testng.Assert.assertTrue;

/**
 * To check the location error message is not displayed, when
 * "warehouse location is removed" from the purchase order
 *
 * @author SGupta
 */
@Listeners(TestRerunListener.class)
public class DFinancePurchaseOrderTests extends DFinanceBaseTest
{
	DFinanceHomePage homePage;
	DFinanceSettingsPage settingsPage;
	DFinanceInvoicePage invoicePage;
	DFinanceXMLInquiryPage xmlInquiryPage;
	DFinanceCreatePurchaseOrderPage createPurchaseOrderPage;
	DFinanceAllSalesOrdersPage allSalesOrdersPage;
	DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage;
	final String procurementAndSourcing = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING.getData();
	final String procurementAndSourcingParameters = DFinanceLeftMenuNames.PROCUREMENT_AND_SOURCING_PARAMETERS.getData();
	Boolean accountsRec = true;
	Boolean workFlowStatus = false;
	Boolean activateChangeManagement = false;
	Boolean customsStatus = false;
	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		homePage = new DFinanceHomePage(driver);
		invoicePage = new DFinanceInvoicePage(driver);
		xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		settingsPage = new DFinanceSettingsPage(driver);
		allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//================script implementation========================
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.toggleAccrueButton(true);
		accountsRec = true;

		workFlowStatus = false;
		createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(procurementAndSourcing);
		homePage.expandModuleCategory(setup);
		homePage.selectModuleTabLink(procurementAndSourcingParameters);
		createPurchaseOrderPage.activateChangeManagementStatus(false);
		activateChangeManagement = false;
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

		if(customsStatus) {
			//Set Customs status for USMF
			DFinanceLegalEntitiesPage legalEntitiesPage = homePage.navigateToPage(
					DFinanceLeftMenuModule.ORGANIZATION_ADMINISTRATION, DFinanceModulePanelCategory.ORGANIZATIONS,
					DFinanceModulePanelLink.LEGAL_ENTITIES);
			legalEntitiesPage.searchCompany(usmfCompany);
			legalEntitiesPage.clickAddressMoreOptions();
			legalEntitiesPage.clickAddressAdvancedButton();
			legalEntitiesPage.selectCustomsStatus("Free Circulation");

			//Set customs status for vendor 1001
			DFinanceAllVendorsPage allVendorsPage = homePage.navigateToAllVendorsPage();
			allVendorsPage.searchVendor("1001");
			allVendorsPage.clickAddressMoreOptions();
			allVendorsPage.clickAddressAdvancedButton();
			allVendorsPage.selectCustomsStatus("Free Circulation");
		}
	}

	/**
	 * @TestCase CD365-262
	 * @Description - Verify that Exchange Rate Gain/Loss is not displayed on Free Text Invoice
	 * @Author SGupta
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL", "purchase order" }, retryAnalyzer = TestRerun.class)
	public void purchaseOrderCreationTest( )
	{
		String vendorAccount = DFinanceConstantDataResource.VENDOR_ACCOUNT.getData();
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

		// Remove the address
		createPurchaseOrderPage.removeDeliveryAddress();

		// click Lines link
		createPurchaseOrderPage.clickOnLines();

		// add a line item to the PO
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice, 0);

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

		//Click on "Delete" button
		createPurchaseOrderPage.deleteButton();
	}

	/**
	 * Proportional Allocated Charge Test
	 * CD365-1384 CD365-666
	 */
		@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
		public void proportionalAllocatedChargeTest( )
		{
			String vendorAccount = DFinanceConstantDataResource.VENDOR_ACCOUNT_NO.getData();
			String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

			final String itemNumber = "1000";
			final String quantity = "2";
			final String quantity1 = "4";
			final String site = "1";
			final String warehouse = "13";
			final String unitPrice = "899";

			final double expectedTaxAmount = 418.81;

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

			// add second line item to the PO
			createPurchaseOrderPage.addItemLine(itemNumber, quantity1, site, warehouse, unitPrice, 1);

			//Add Proportional Allocated Charge
			allSalesOrdersPage.clickOnFinancials();
			allSalesOrdersPage.selectMaintainCharges();
			allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAP", "All");
			allSalesOrdersPage.closeCharges();

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

			createPurchaseOrderPage.clickOnSalesTaxOkButton();

			//Click on "Delete" button
			createPurchaseOrderPage.deleteButton();
		}

	/**
	 * @TestCase CD365-177
	 * @Description - Create a Purchase Order and Invoice and add/delete lines and change quantity and location
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "Work_In_Progress" }, retryAnalyzer = TestRerun.class)
	public void createPurchaseOrderAndChangeQuantityAndLocationTest(){

		final String quantity = "1";
		final String site = "1";
		final String wareHouse = "13";
		final String item = "D0009";
		final String unitPrice = "12";

		final String customerAccount = "D_US_PA1";
		final double expectedTaxAmount = 11.23;
		final double expectedTaxAmount2 = 8.71;


		settingsPage.selectCompany("USMF");

		//Navigate to All Sales Orders page
		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		allPurchaseOrdersPage.setVendorAccountNumber(customerAccount);

		allSalesOrdersPage.clickOnOKBtn();

		//Add line items
		allSalesOrdersSecondPage.fillFirstItemsInfo("D0006", site, wareHouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(item, site, wareHouse, "10", unitPrice, 1);
		allSalesOrdersSecondPage.fillItemsInfo(item, "1", "13", quantity, unitPrice, 2);

		//Navigate to Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		String purchaseOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Click on "OK" button
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		//Update the line items
		allSalesOrdersPage.removeLineItem("D0006");
		allSalesOrdersSecondPage.updateLineItemsInfo("1","13","5", "12", "0", 1);
		allSalesOrdersSecondPage.selectLine(2);
		allSalesOrdersSecondPage.updateLineItemsInfo("2","21","1", "12", "0",2);
		allSalesOrdersSecondPage.fillItemsInfo(item, site, wareHouse, "3", unitPrice, 3);

		//Navigate to Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();

		//Verify the "Total calculated sales tax amount" value
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount2,
				"'Total calculated sales tax amount' value is not expected");

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
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();

		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format("Expected Invoice#: %s, but actual Invoice#: %s", invoiceNumber, displayedInvoiceNumber));

		applyPrepaymentsPage.closeApplyPrepayment();

		// Post Invoice
		createPurchaseOrderPage.clickPostOption();

		//Navigate to Vertex XML Inquiry
		DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Filter by Purchase Order Number
		xmlInquiryPage.getDocumentID(purchaseOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();

		String response = xmlInquiryPage.getLogResponseValue();

		//Verify tax amount
		assertTrue(response.contains("<TotalTax>8.71</TotalTax>"));
	}

	/**
	 * @TestCase CD365-1404
	 * @Description - Create a Purchase Order for Germany to Us and validate Vendor Established and Customer Established
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
	public void purchaseOrderWithCurrencyConversionTest() {
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final String itemNumber = "D0003";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 3.74;

		final int expectedLinesCount = 1;

		//================script implementation========================

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany("USMF");

		//Click on "New" option
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		// enter vendor account number
		createPurchaseOrderPage.setVendorAccountNumber("104");

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
		createPurchaseOrderPage.addItemLine("1000", quantity, site, warehouse, unitPrice, 1);

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


		// Post Invoice
		createPurchaseOrderPage.clickPostOption();

		// validate invoice posting
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("104", invoiceNumber);
		assertTrue(isPosted, String.format("Invoice#: %s posting failed", invoiceNumber));

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

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

		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

		// search vendor invoice and validate result
		boolean isInvoiceFound = openVendorInvoicesPage.searchVendorInvoice("vendopeninvoiceslistpage", invoiceNumber);
		assertTrue(isInvoiceFound, String.format("Invoice#: %s not found", invoiceNumber));

		// click Posted Sales Tax
		openVendorInvoicesPage.clickInvoiceNumber(invoiceNumber);
		openVendorInvoicesPage.clickVouchersTab();
		openVendorInvoicesPage.clickPostedSalesTax("2");
		calculatedSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);


		//Navigate to XML inquiry
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Select the Correct Response from the list
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();

			assertTrue(request.contains("<Currency isoCurrencyCodeAlpha=\"EUR\" />\n" +
					"\t\t<Buyer>\n" +
					"\t\t\t<Company>USMF</Company>\n" +
					"\t\t\t<Destination taxAreaId=\"390030000\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
					"\t\t\t\t<StreetAddress1>1000 Airport Blvd</StreetAddress1>\n" +
					"\t\t\t\t<City>Pittsburgh</City>\n" +
					"\t\t\t\t<MainDivision>PA</MainDivision>\n" +
					"\t\t\t\t<PostalCode>15231-1001</PostalCode>\n" +
					"\t\t\t\t<Country>USA</Country>\n" +
					"\t\t\t</Destination>\n" +
					"\t\t\t<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
					"\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
					"\t\t\t\t<City>Atlanta</City>\n" +
					"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
					"\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
					"\t\t\t\t<Country>USA</Country>\n" +
					"\t\t\t</AdministrativeDestination>\n" +
					"\t\t</Buyer>\n" +
					"\t\t<Vendor>\n" +
					"\t\t\t<VendorCode classCode=\"104\">104</VendorCode>\n" +
					"\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
					"\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
					"\t\t\t\t<City>Berlin</City>\n" +
					"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
					"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
					"\t\t\t\t<Country>deu</Country>\n" +
					"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
					"\t\t\t</PhysicalOrigin>\n" +
					"\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
					"\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
					"\t\t\t\t<City>Berlin</City>\n" +
					"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
					"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
					"\t\t\t\t<Country>deu</Country>\n" +
					"\t\t\t</AdministrativeOrigin>\n" +
					"\t\t</Vendor>\n"));
		assertTrue(request.contains("\t<Buyer>\n" +
				"\t\t\t\t<Company>USMF</Company>\n" +
				"\t\t\t\t<Destination taxAreaId=\"480410000\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>1356 Rush Rd</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Chehalis</City>\n" +
				"\t\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>98532-8728</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t</Destination>\n" +
				"\t\t\t\t<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Atlanta</City>\n" +
				"\t\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t</Buyer>\n" +
				"\t\t\t<Vendor>\n" +
				"\t\t\t\t<VendorCode classCode=\"104\">104</VendorCode>\n" +
				"\t\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t</AdministrativeOrigin>\n" +
				"\t\t\t</Vendor>\n" +
				"\t\t\t<ChargedTax>0</ChargedTax>\n" +
				"\t\t\t<Purchase purchaseClass=\"ALL\">D0003</Purchase>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"ea\">2</Quantity>\n" +
				"\t\t\t<ExtendedPrice>24</ExtendedPrice>\n" +
				"\t\t\t<FlexibleFields>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"1\">Amtsstrasse 23\n" +
				"10115 Berlin\n" +
				"deu</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"2\">deu</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"4\">0</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"6\">1</FlexibleCodeField>\n" +
				"\t\t\t</FlexibleFields>"));
		assertTrue(request.contains("<Buyer>\n" +
				"\t\t\t\t<Company>USMF</Company>\n" +
				"\t\t\t\t<Destination taxAreaId=\"480410000\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>1356 Rush Rd</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Chehalis</City>\n" +
				"\t\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>98532-8728</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t</Destination>\n" +
				"\t\t\t\t<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Atlanta</City>\n" +
				"\t\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t</Buyer>\n" +
				"\t\t\t<Vendor>\n" +
				"\t\t\t\t<VendorCode classCode=\"104\">104</VendorCode>\n" +
				"\t\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Amtsstrasse 23</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t</AdministrativeOrigin>\n" +
				"\t\t\t</Vendor>\n" +
				"\t\t\t<ChargedTax>0</ChargedTax>\n" +
				"\t\t\t<Purchase purchaseClass=\"ALL\">1000</Purchase>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"ea\">2</Quantity>\n" +
				"\t\t\t<ExtendedPrice>24</ExtendedPrice>\n" +
				"\t\t\t<FlexibleFields>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"1\">Amtsstrasse 23\n" +
				"10115 Berlin\n" +
				"deu</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"2\">deu</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"4\">0</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"6\">1</FlexibleCodeField>\n" +
				"\t\t\t</FlexibleFields>"));

		//Navigate to All Vendors Page
		homePage.navigateToPageOfModule( accountsPayables,vendors,allVendors);

		//Validate that the Vendor Established is set to On
		allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		allPurchaseOrdersPage.clickInvoiceAndDeliveryDropdown();
		assertTrue(allPurchaseOrdersPage.validateEstablishedType(true, "Vendor"));

		//Navigate to All Customers Page
		homePage.navigateToPageOfModule(accountReceivable,customers,allCustomers);

		//Validate that the Customer Established is set to On
		allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		allPurchaseOrdersPage.createCustomerAccount();
		allPurchaseOrdersPage.enterCustomerName();
		allPurchaseOrdersPage.customerGroupNumber("10");
		allPurchaseOrdersPage.clickSaveButton();
		allPurchaseOrdersPage.clickInvoiceAndDeliveryDropdown();
		assertTrue(allPurchaseOrdersPage.validateEstablishedType(true, "Customer"));
	}

	/**
	 * @TestCase CD365-1141
	 * @Description - Verify Unit Of Measure conversion and Administrative Destination for Purchase Order and Vendor Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyUnitOfMeasureConversionAndAdministrativeDestinationForPurchaseOrderToVendorInvoiceTest(){

		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final String itemNumber = "D0003";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 3.74;

		final int expectedLinesCount = 1;

		//================script implementation========================

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany("USMF");

		//Click on "New" option
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();

		// enter vendor account number
		createPurchaseOrderPage.setVendorAccountNumber("US_TX_001");

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
		createPurchaseOrderPage.addItemLine("1000", quantity, site, warehouse, unitPrice, 1);

		//navigate to "Purchase"  --> Sales Tax page and verify Tax Area Id line amount
		createPurchaseOrderPage.navigateToSalesTaxPage();
		allSalesOrdersPage.verifyTaxAreaIdLineAmount(4);

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
		createPurchaseOrderPage.clickOnTab("Invoice");
		createPurchaseOrderPage.navigateToInvoicePage();
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		allSalesOrdersPage.clickOk();

		//Set invoice number
		cal = Calendar.getInstance();
		String invoiceNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

		//Update Match Status
		createPurchaseOrderPage.clickUpdateMatchStatusButton();
		final String expectedMatchStatus = "Passed";
		String matchStatus = createPurchaseOrderPage.getMatchStatus();
		assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
				String.format("Expected Match Status: %s, but actual Match Status: %s", expectedMatchStatus, matchStatus));

		//Apply Prepayments
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();

		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format("Expected Invoice#: %s, but actual Invoice#: %s", invoiceNumber, displayedInvoiceNumber));

		applyPrepaymentsPage.closeApplyPrepayment();

		// Post Invoice
		createPurchaseOrderPage.clickPostOption();

		// validate invoice posting
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("US_TX_001", invoiceNumber);
		assertTrue(isPosted, String.format("Invoice#: %s posting failed", invoiceNumber));

		//navigate to "Purchase"  --> Sales Tax page
		createPurchaseOrderPage.navigateToSalesTaxPage();

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

		//Navigate to XML inquiry
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Validate that the Invoice is "Vendor Invoice Verification" type
		String expectedType = "Invoice Verification";

		//Select the Correct Response from the list
		xmlInquiryPage.getDocumentID(purchaseOrder);
		String actualType = xmlInquiryPage.getDocumentType();
		assertTrue(actualType.equals(expectedType),
				"Document type is not equal to what is expected");
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Quantity unitOfMeasure=\"ea\">2.0</Quantity>"));

		//Select the Correct Request from the list
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
		assertTrue(request.contains(
				"<FlexibleCodeField fieldId=\"1\">123 Government Plz\n" +
				"Sacramento, CA 94279-</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"2\">USA</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"3\">50672940</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"4\">1</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField "
		));

		int expectedAmountLines = 7;
		int actualAmountLines = xmlInquiryPage.financialDimensionAppearanceXML("000002", "140200", "022", "007");
		assertTrue(expectedAmountLines == actualAmountLines, "Expected and actual amount are not the same. Expected: " + expectedAmountLines + ", but found: " + actualAmountLines);
		assertTrue(request.contains("generalLedgerAccount=\"606300\""));
	}


	/**
	 * @TestCase CD365-1262
	 * @Description - Validate Accrual Request For Purchase Order Where Vendor Charges Less Than Vertex
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateAccrualRequestForPurchaseOrderWhenVendorChargesLessTest(){
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final String itemNumber = "D0003";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 3.74;
		settingsPage.selectCompany("USMF");

		//Create new Purchase Order
		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber("US_TX_001");
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format("Purchase Order# %s", purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice, 0);
		createPurchaseOrderPage.addItemLine("1000", quantity, site, warehouse, unitPrice, 1);

		//Validate Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		//Confirm Purchase order
		createPurchaseOrderPage.navigateToConfirmationPage();
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "PO Confirmation failed");

		//Add Product Receipt
		createPurchaseOrderPage.navigateToProductReceiptPage();
		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
		Calendar cal = Calendar.getInstance();
		String productReceiptNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
		VertexLogger.log(String.format("Product Receipt Number: %s", productReceiptNumber));
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "PO Confirmation failed");

		//Verify and set Accrual tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		createPurchaseOrderPage.navigateToInvoicePage();
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		cal = Calendar.getInstance();
		String invoiceNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("2.00");
		createPurchaseOrderPage.clickAdjustmentSalesTax();
		createPurchaseOrderPage.clickApplyActualAmounts();
		allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
		Double expectedAccruedSalesTaxAmount = 1.74;
		assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedAccruedSalesTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Update Match Status
		createPurchaseOrderPage.clickUpdateMatchStatusButton();
		final String expectedMatchStatus = "Passed";
		String matchStatus = createPurchaseOrderPage.getMatchStatus();
		assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
				String.format("Expected Match Status: %s, but actual Match Status: %s", expectedMatchStatus, matchStatus));

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("US_TX_001", invoiceNumber);
		assertTrue(isPosted, String.format("Invoice#: %s posting failed", invoiceNumber));

		//Verify sales tax amount
		createPurchaseOrderPage.navigateToSalesTaxPage();
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		String expectedType = "Accrual";

		//Verify Accrual Request is called, Total tax, and postToJournal is not represent in Response
		xmlInquiryPage.getDocumentID(invoiceNumber);
		String actualType = xmlInquiryPage.getDocumentType();
		assertTrue(actualType.equals(expectedType),
				requestTypeNotExpected);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>0.87</TotalTax>"));
		assertTrue(!response.contains("<AccrualResponse postToJournal=\"false\""));

	}

	/** @TestCase CD365-284
	 * @Description - Verify Purchase Order Class Exemption
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyApPurchaseOrderClassExemptionTest(){
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAP.getData();

		final String itemNumber = "D0003";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 0.00;

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany("USMF");

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber("US_TX_001");
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format("Purchase Order# %s", purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroup);
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice, 0);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("VTXPClass");
		createPurchaseOrderPage.addItemLine("1000", quantity, site, warehouse, unitPrice, 1);
		allSalesOrdersSecondPage.selectItemSalesGroupType("VTXPClass");

		//Validate Sales Tax
		createPurchaseOrderPage.navigateToSalesTaxPage();
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		//Confirm Purchase order
		createPurchaseOrderPage.navigateToConfirmationPage();
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "PO Confirmation failed");

		//Add Product Receipt
		createPurchaseOrderPage.navigateToProductReceiptPage();
		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
		Calendar cal = Calendar.getInstance();
		String productReceiptNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setProductReceiptNumber(productReceiptNumber);
		VertexLogger.log(String.format("Product Receipt Number: %s", productReceiptNumber));
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, "PO Confirmation failed");

		//Verify and set Accrual tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		createPurchaseOrderPage.navigateToInvoicePage();
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		Double expectedAccruedSalesTaxAmount = 0.00;
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedAccruedSalesTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOk();

		//Set invoice number
		cal = Calendar.getInstance();
		String invoiceNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);

		//Update Match Status
		createPurchaseOrderPage.clickUpdateMatchStatusButton();
		final String expectedMatchStatus = "Passed";
		String matchStatus = createPurchaseOrderPage.getMatchStatus();
		assertTrue(matchStatus.equalsIgnoreCase(expectedMatchStatus),
				String.format("Expected Match Status: %s, but actual Match Status: %s", expectedMatchStatus, matchStatus));

		//Apply Prepayments
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format("Expected Invoice#: %s, but actual Invoice#: %s", invoiceNumber, displayedInvoiceNumber));
		applyPrepaymentsPage.closeApplyPrepayment();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("US_TX_001", invoiceNumber);
		assertTrue(isPosted, String.format("Invoice#: %s posting failed", invoiceNumber));

		//Verify sales tax amount
		createPurchaseOrderPage.navigateToSalesTaxPage();
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		createPurchaseOrderPage.clickOnSalesTaxOkButton();

		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify Accrual Request is called, Total tax, and postToJournal is not represent in Response
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));
	}

	/** @TestCase CD365-286
	 * @Description - Create Purchase order with Vendor Class Exemption
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyVendorClassExemptionForPurchaseOrderTest(){

		final String itemNumber = "D0003";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 0.00;

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany(usmfCompany);

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vtxvCodeSalesTaxGroup);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(itemNumber, "1", site, warehouse, unitPrice, 0);
		createPurchaseOrderPage.addItemLine("1000", "5", site, warehouse, unitPrice, 1);
		createPurchaseOrderPage.addItemLine("1000", quantity, site, warehouse, unitPrice, 2);

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
		actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		Double expectedAccruedSalesTaxAmount = 0.00;
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedAccruedSalesTaxAmount,
				assertionTotalAccrualSalesTaxAmount);
		allSalesOrdersPage.clickOk();

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

		//Apply Prepayments
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format(expectedInvoiceNumber, invoiceNumber, displayedInvoiceNumber));
		applyPrepaymentsPage.closeApplyPrepayment();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("VTXVCode", invoiceNumber);
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

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify VTXCode and Total tax amount
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<VendorCode classCode=\"VTXVCode\">VTXVCode</VendorCode>"));
		assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));
	}

	/** @TestCase CD365-283
	 * @Description - Create Purchase order with Purchase Code Exemption
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyPurchaseCodeExemptionForPurchaseOrderTest(){
		final String itemNumber = "VTXPurCode";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = 0.00;

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany(usmfCompany);

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(itemNumber, "1", site, warehouse, unitPrice, 0);
		createPurchaseOrderPage.addItemLine(itemNumber, "5", site, warehouse, unitPrice, 1);

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

		//Verify sales tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		createPurchaseOrderPage.navigateToInvoicePage();
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		Double expectedActualSalesTaxAmount = 0.00;
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.clickOk();

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

		//Apply Prepayments
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format(expectedInvoiceNumber, invoiceNumber, displayedInvoiceNumber));
		applyPrepaymentsPage.closeApplyPrepayment();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("1001", invoiceNumber);
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

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify VTXPurCode and Total tax amount
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Purchase purchaseClass=\"ALL\">VTXPurCode</Purchase>"));
		assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));
	}

	/** @TestCase CD365-171
	 * @Description - Create Purchase Order with Discounts and Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createPurchaseOrderWithDiscountAndInvoiceTest() {
		final String itemNumber = "1001";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";
		final String discount = "15";

		final double expectedTaxAmount = 40.79;

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany(usmfCompany);

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage(itemNumber, site, warehouse, "5", unitPrice, discount, 1);
		allSalesOrdersSecondPage.fillItemsInfoWithDiscountPercentage("L0001","1", "13", "1", "500", "5",2);

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

		//Verify sales tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		createPurchaseOrderPage.navigateToInvoicePage();
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		Double expectedActualSalesTaxAmount = 40.79;
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.clickOk();

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

		//Apply Prepayments
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format(expectedInvoiceNumber, invoiceNumber, displayedInvoiceNumber));
		applyPrepaymentsPage.closeApplyPrepayment();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("1001", invoiceNumber);
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

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify Total tax amount
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>40.79</TotalTax>"));
	}

	/** @TestCase CD365-178
	 * @Description - Create a Purchase order, return partial quantity with shipping invoice and validate the xml
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createPurchaseOrderReturnPartialQuantityAndValidateXMLTest() {
		final String itemNumber = "1001";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		final double expectedTaxAmount = -128.70;

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		settingsPage.selectCompany(usmfCompany);

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.setPurchaseOrderType("Returned order");
		createPurchaseOrderPage.setRmaNumber("1234567");
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "-5", unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo("L0001","1", "13", "-2", "500", 1);
		allSalesOrdersSecondPage.fillItemsInfo("L0001","1", "13", "-1", "500", 2);

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

		//Verify sales tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		createPurchaseOrderPage.isPleaseWaitProcessed();
		createPurchaseOrderPage.navigateToInvoicePage();
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		actualSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.clickOk();

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

		//Apply Prepayments
		createPurchaseOrderPage.clickOnInvoiceTab("Overview");
		DFinanceApplyPrepaymentsPage applyPrepaymentsPage = createPurchaseOrderPage.applyPrepayments();
		String displayedInvoiceNumber = applyPrepaymentsPage.getInvoiceNumber();
		assertTrue(displayedInvoiceNumber.equalsIgnoreCase(invoiceNumber),
				String.format(expectedInvoiceNumber, invoiceNumber, displayedInvoiceNumber));
		applyPrepaymentsPage.closeApplyPrepayment();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("1001", invoiceNumber);
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

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify Tax Registration is not present and Total tax amount
		xmlInquiryPage.getDocumentID(purchaseOrder);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(!request.contains("TaxRegistration"));

		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>-128.7</TotalTax>"));
	}

	/** @TestCase CD365-1539
	 * @Description - Verify Actual Sales Tax amount when invoice is correct when creating PO with Freight Charges
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_Failing" }, retryAnalyzer = TestRerun.class)
	public void verifyActualSalesTaxAmountIsCorrectWhenCreatingPOWithFreightChargesTest() {

		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		settingsPage.selectCompany(usmfCompany);
		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber("1001");
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(item1000, quantity, site, warehouse, unitPrice, 0);
		createPurchaseOrderPage.addItemLine(item1000, quantity, site, warehouse, unitPrice, 0);

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","10","VertexAP", "All");
		allSalesOrdersPage.closeCharges();

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

		//Verify sales tax amount
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("2.00");
		allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
		Double expectedAccruedSalesTaxAmount = 2.52;
		assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedAccruedSalesTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOnOKBtn();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, invoiceNumber);
		assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

	}

	/** @TestCase CD365-1610
	 * @Description - Verify Final Accrual Call Includes Freight Charge And Correct Exchange Rate
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyFinalAccrualCallIncludesFreightChargeAndCorrectExchangeRateTest() {

		final String quantity = "2";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "12";
		final Double expectedTaxAmount = 7.62;

		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
		settingsPage.selectCompany(usmfCompany);

		//Create new Purchase Order
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(item1000, quantity, site, warehouse, unitPrice, 0);

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT", false,"Fixed","50","VertexAP", "All");
		allSalesOrdersPage.closeCharges();

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

		//Verify sales tax amount
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToFinancialSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("2.00");
		allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualAccruedSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
		Double expectedAccruedSalesTaxAmount = 5.62;
		assertTrue(Double.parseDouble(actualAccruedSalesTaxAmount) == expectedAccruedSalesTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.clickOnOKBtn();

		//Post the invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, invoiceNumber);
		assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

		//Verify Posted Sales Tax on the Vendor Invoice
		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify exchange rate for vendor and total tax amount
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Vendor>\n" +
				"\t\t\t\t<VendorCode classCode=\"1001\">1001</VendorCode>\n" +
				"\t\t\t\t<PhysicalOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>711 Louisiana St</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Houston</City>\n" +
				"\t\t\t\t\t<MainDivision>TX</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>77002-2716</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t\t<AdministrativeOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>711 Louisiana St</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Houston</City>\n" +
				"\t\t\t\t\t<MainDivision>TX</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>77002-2716</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t\t</AdministrativeOrigin>\n" +
				"\t\t\t</Vendor>"));

		assertTrue(request.contains("<Purchase purchaseClass=\"ALL\">FREIGHT</Purchase>"));

		assertTrue(request.contains("<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>321 North Street, Gate 2</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Tacoma</City>\n" +
				"\t\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>98401</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t\t</Destination>"));

		assertTrue(request.contains("<Quantity unitOfMeasure=\"ea\">2</Quantity>"));

		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>5.62</TotalTax>"));

	}

	/** @TestCase CD365-1614
	 * @Description - Verify sales tax and sales tax line for PO when AR is disabled
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_Integration" }, priority = 16)
	public void verifySalesTaxAndSalesTaxLineForPOWhenARIsDisabledTest() {
		final String itemNumber = "A0001";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";
		final Double expectedTaxAmount = 0.94;
		final int expectedLinesCount = 2;

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

		//Create new Purchase Order
		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.setSalesOrderTaxGroup(salesTaxGroupVertexAP);
		createPurchaseOrderPage.clickOnLines();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);

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

		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line1.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceInvoicePOLineDetails.Line2.getLineDataMap());

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
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
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, invoiceNumber);
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

		//Verify Total Tax
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>0.94</TotalTax>"));

	}


	/**
	 * @TestCase CD365-1690
	 * @Description - Verify PO confirmation when adding multi-line item then deleting one and confirming
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyPOConfirmationWhenAddingMultiLineItemsDeletingOneLineAndConfirmingTest() {

		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "12";

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		//Create new Purchase Order
		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();

		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine("D0001", quantity, site, warehouse, unitPrice, 0);
		createPurchaseOrderPage.addItemLine("D0006", quantity, site, warehouse, unitPrice, 1);

		//Confirm Purchase order
		createPurchaseOrderPage.navigateToConfirmationPage();
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		boolean isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);

		//Delete one line item
		allSalesOrdersPage.removeLineItem("D0006");

		//Confirm Purchase order
		createPurchaseOrderPage.navigateToConfirmationPage();
		createPurchaseOrderPage.clickOnSalesTaxOkButton();
		isOperationCompleted = createPurchaseOrderPage.messageBarConfirmation(operationCompleted);
		assertTrue(isOperationCompleted, purchaseOrderConfirmationFailed);
	}

	/**
	 * @TestCase CD365-1741
	 * @Description - Verify US-US Open Vendor Invoices
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_AP_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyUSToUSOpenVendorInvoiceTest() {

		final String quantity = "1";
		final String site = "1";
		final String warehouse = "13";

		settingsPage.selectCompany(usmfCompany);

		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

		openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
		openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber("D_US_PA1");

		//Set invoice number
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
		String invoiceNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
		createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

		//Add new line item
		createPurchaseOrderPage.clickOnLines();
		openVendorInvoicesPage.addItemLine("C0001", quantity, site, warehouse,0);
		allSalesOrdersSecondPage.clickLineDetailsTab("AdditionalLineDetails");
		openVendorInvoicesPage.updateDeliveryAddress("Pittsburgh PA Address");

		//Validate Sales Tax
		createPurchaseOrderPage.clickOnInvoiceTab("Financials");
		createPurchaseOrderPage.navigateToSalesTaxPage();
		createPurchaseOrderPage.updateActualSalesTaxAmount("1.80");
		allSalesOrdersPage.clickRecalculateTax("Recalculate tax", "1", true);
		String actualSalesTaxAmount = createPurchaseOrderPage.getActualSalesTaxAmount();
		Double expectedActualSalesTaxAmount = 2.80;
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedActualSalesTaxAmount,
				assertionTotalActualSalesTaxAmount);
		createPurchaseOrderPage.clickAdjustmentSalesTax();
		String actualAccrualSalesTaxAmount = createPurchaseOrderPage.getTotalAccruedTaxAmount();
		Double expectedAccrualSalesTaxAmount = 1.00;
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
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully("D_US_PA1", invoiceNumber);
		assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify that Response does not contain PostToJournal is false
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(!response.contains("postToJournal=false"));
	}

	/**
	 * @TestCase CD365-1981
	 * @Description - Create a Open Vendor Invoice Using VertexCUT
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
	public void createOpenVendorInvoiceWhenUsingVertexCUTTest(){

		final String quantity = "1";
		final String site = "1";
		final String warehouse = "11";
		final double expectedTaxAmount = 2.80;

		settingsPage.selectCompany(usmfCompany);
		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = homePage.navigateToOpenVendorInvoicesPage();

		openVendorInvoicesPage.createNewInvoice("NewVendorInvoice");
		openVendorInvoicesPage.setVendorInvoiceVendorAccountNumber(vendorAccount1001);

		//Set invoice number
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHmmssS");
		String invoiceNumber = dateFormat.format(cal.getTime());
		createPurchaseOrderPage.setInvoiceIdentificationNumber(invoiceNumber);
		createPurchaseOrderPage.setInvoiceDateForVendorInvoice();

		//Add new line item and change delivery address and item/sales tax
		openVendorInvoicesPage.addItemLine("C0001", quantity, site, warehouse,0);
		allSalesOrdersSecondPage.clickLineDetailsTab("AdditionalLineDetails");
		openVendorInvoicesPage.updateDeliveryAddress("Pittsburgh PA Address");
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("VertexCUT");
		openVendorInvoicesPage.setSalesOrderTaxGroup("VertexCUT", "SalesTax");

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
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, invoiceNumber);
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
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				calculatedSalesTaxAmount+" : "+assertionTotalCalculatedSalesTaxAmount);

		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify invoice response tax
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>2.8</TotalTax>"));
	}


	/**
	 * @TestCase CD365-2102
	 * @Description - Verify Posted Sales Tax For PO When Completing Workflow For US
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_VAT_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyPostedSalesTaxForPOWhenCompletingWorkFlowUSTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

		final String itemNumber = "1000";
		final String quantity = "2";
		final String site = "1";
		final String wareHouse = "11";
		final String unitPrice = "240.08";
		final double expectedSalesTaxAmount = 74.90;

		settingsPage.selectCompany(usmfCompany);

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
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, wareHouse, unitPrice, 0);

		//Add maintain charges at header level
		createPurchaseOrderPage.clickOnTab("Purchase");
		createPurchaseOrderPage.clickHeaderMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT", false, "Fixed", "60", "VertexAdva", "All");
		allSalesOrdersPage.closeCharges();

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT", false, "Fixed", "125", "VertexAdva", "All");
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

		//Post Invoice
		createPurchaseOrderPage.clickPostOption();
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, invoiceNumber);
		assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

		//Validate Posted Sales Tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
	}

	/**
	 * @TestCase CD365-2755
	 * @Description - Verify Custom Location Presence When Creating PO For All Address
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" }, retryAnalyzer = TestRerun.class)
	public void verifyPresenceOfCustomLocationForPOOfAllAddressesTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceOpenVendorInvoicesPage openVendorInvoicesPage = new DFinanceOpenVendorInvoicesPage(driver);
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

		final String itemNumber = "1000";
		final String quantity = "2";
		final String site = "1";
		final String wareHouse = "11";
		final String unitPrice = "240.08";
		final double expectedSalesTaxAmount = 0.00;

		settingsPage.selectCompany(usmfCompany);

		//Set Customs status for vendor 1001
		DFinanceAllVendorsPage allVendorsPage = homePage.navigateToAllVendorsPage();
		allVendorsPage.searchVendor("1001");
		allVendorsPage.clickAddressMoreOptions();
		allVendorsPage.clickAddressAdvancedButton();
		allVendorsPage.selectCustomsStatus("Free Trade Zone");

		//Set Customs status for USMF
		DFinanceLegalEntitiesPage legalEntitiesPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ORGANIZATION_ADMINISTRATION, DFinanceModulePanelCategory.ORGANIZATIONS,
				DFinanceModulePanelLink.LEGAL_ENTITIES);
		legalEntitiesPage.searchCompany(usmfCompany);
		legalEntitiesPage.clickAddressMoreOptions();
		legalEntitiesPage.clickAddressAdvancedButton();
		legalEntitiesPage.selectCustomsStatus("Inward Processing Relief");

		customsStatus = true;

		//Create new Purchase Order
		DFinanceAllPurchaseOrdersPage allPurchaseOrdersPage = homePage.navigateToAllPurchaseOrdersPage();
		allPurchaseOrdersPage.clickNewPurchaseOrderButton();
		createPurchaseOrderPage.setVendorAccountNumber(vendorAccount1001);
		createPurchaseOrderPage.clickOkButton();
		createPurchaseOrderPage.clickOnHeader();
		String purchaseOrder = createPurchaseOrderPage.getPurchaseOrderNumber();
		VertexLogger.log(String.format(purchaseOrderNumber, purchaseOrder));
		createPurchaseOrderPage.clickOnLines();
		createPurchaseOrderPage.addItemLine(itemNumber, quantity, site, wareHouse, unitPrice, 0);

		//Add maintain charges at header level
		createPurchaseOrderPage.clickOnTab("Purchase");
		createPurchaseOrderPage.clickHeaderMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT", false, "Fixed", "60", "VertexAP", "All");
		allSalesOrdersPage.closeCharges();

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT", false, "Fixed", "125", "VertexAP", "All");
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
		boolean isPosted = createPurchaseOrderPage.isInvoicePostedSuccessfully(vendorAccount1001, invoiceNumber);
		assertTrue(isPosted, String.format(invoicePostingFailed, invoiceNumber));

		//Validate Posted Sales Tax
		createPurchaseOrderPage.clickOnTab("Invoice");
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		calculatedSalesTaxAmount = createPurchaseOrderPage.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedSalesTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		//Navigate to XML Inquiry and validate locationCustomsStatus and Addresses for Header, Charges and Lines(1st and 2nd) (Temporary Import, Free Circulation, Free Trade Zone
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

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
				"\t\t\t<AdministrativeDestination taxAreaId=\"111211811\" locationCustomsStatus=\"INWARD_PROCESSING_RELIEF\">\n" +
				"\t\t\t\t<StreetAddress1>2 Concourse Pkwy</StreetAddress1>\n" +
				"\t\t\t\t<City>Atlanta</City>\n" +
				"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>30328-5371</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t</Buyer>"));
		assertTrue(request.contains("<Vendor>\n" +
				"\t\t\t<VendorCode classCode=\"1001\">1001</VendorCode>\n" +
				"\t\t\t<PhysicalOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_TRADE_ZONE\">\n" +
				"\t\t\t\t<StreetAddress1>711 Louisiana St</StreetAddress1>\n" +
				"\t\t\t\t<City>Houston</City>\n" +
				"\t\t\t\t<MainDivision>TX</MainDivision>\n" +
				"\t\t\t\t<PostalCode>77002-2716</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1</CurrencyConversion>\n" +
				"\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t<AdministrativeOrigin taxAreaId=\"442011440\" locationCustomsStatus=\"FREE_TRADE_ZONE\">\n" +
				"\t\t\t\t<StreetAddress1>711 Louisiana St</StreetAddress1>\n" +
				"\t\t\t\t<City>Houston</City>\n" +
				"\t\t\t\t<MainDivision>TX</MainDivision>\n" +
				"\t\t\t\t<PostalCode>77002-2716</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t</AdministrativeOrigin>\n" +
				"\t\t</Vendor>"));
		assertTrue(request.contains("<Purchase purchaseClass=\"ALL\">FREIGHT</Purchase>\n" +
				"\t\t\t<Quantity>1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>60</ExtendedPrice>"));
	}
}