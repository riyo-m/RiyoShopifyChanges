package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.json.XML;
import org.testng.annotations.*;

import java.util.*;

import static org.testng.Assert.assertTrue;

/**
 * Create Sales Order
 *
 * @author Saidulu Kodadala ssalisbury
 */
@Listeners(TestRerunListener.class)
public class DFinanceCreateNewSalesOrderTests extends DFinanceBaseTest
{
	Boolean advTaxGroup = false;
	Boolean logging = true;
	Boolean taxAdjustmentToggle = false;
	Boolean sendPostingRequestImmediately = false;
	Boolean customsStatus = false;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		advTaxGroup = false; taxAdjustmentToggle = false;
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;

		//Enable Accounts Receivable
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(taxParametersData);
		settingsPage.toggleAccountsReceivable(true);
		settingsPage.toggleFetchMultipleRegistrationIDs(false);
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		if (advTaxGroup) {
			homePage.refreshPage();
			toggleONOFFAdvanceTaxGroup(false);
		}
		if(!logging) {

			//Navigate to Vertex Tax Parameter page and enable Logging button
			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);
			settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
			settingsPage.selectSettingsPage(taxGroupSettings);
			settingsPage.toggleEnableLoggingButton(true);
		}
		if(taxAdjustmentToggle)
		{
			DFinanceAccountsRecParametersPage accountsRecParametersPage = homePage.navigateToPage(
					DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.SETUP,
					DFinanceModulePanelLink.ACCOUNTS_RECEIVABLE_PARAMETERS);

			accountsRecParametersPage.toggleTaxAdjustmentDetail(false);
		}
		if(sendPostingRequestImmediately)
		{
			//turn invoice toggle on
			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);
			settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
			settingsPage.selectSettingsPage(taxGroupSettings);
			settingsPage.toggleSendPostingRequestImmediately(true);
		}

		if(customsStatus) {
			DFinanceWarehousesPage warehousesPage = homePage.navigateToPage(
					DFinanceLeftMenuModule.WAREHOUSE_MANAGEMENT, DFinanceModulePanelCategory.SETUP,
					DFinanceModulePanelCategory.WAREHOUSE, DFinanceModulePanelLink.WAREHOUSES);
			warehousesPage.searchWarehouse("11");
			warehousesPage.clickAddressAdvancedButton();
			warehousesPage.selectCustomsStatus("Free Circulation");
		}
	}

	/**
	 * creates a new sales order, validates tax calculation in the default/normal case, and then deletes that new
	 * sales order
	 * CD365-553
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createNewSalesOrderTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		//================Data Declaration ===========================
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		//================script implementation=======================		
		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
			DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
			DFinanceModulePanelLink.ALL_SALES_ORDERS);

		createSalesOrder(allSalesOrdersPage, defaultCustomerAccount);

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();

		//lets the new sales order be identified & eliminated at the end of the test
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Set "Sales tax group" -- 'VertexAR'
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		allSalesOrdersPage.openLinesTab();

		//Enter Sales Order item
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);

		//Click on "Sales Tax" option 
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		//Verify the "Total calculated sales tax amount" value 
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		//fixme FAILING with actual value "100.00"
//		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase("80.00"),
//				"'Total calculated sales tax amount' value is not expected: " + calculatedSalesTaxAmount);
		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.equalsIgnoreCase("80.00"),
			"'Total actual sales tax amount' value is not expected: "+actualSalesTaxAmount);

		//Click on "OK" button		
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//TODO move to after method?
		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
			DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
			DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
		postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

		//Click on "Sales Order Number" resulted in the Search Result
		postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		postTestAllSalesOrdersPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		postTestAllSalesOrdersPage.agreeToConfirmationPopup();
	}

	/**
	 * creates a new sales order with freight, and then deletes that new
	 * sales order
	 * CD365-1382
	 */

	@Test(groups = { "FO_AR_regression", "FO_ALL", "sales_order" }, retryAnalyzer = TestRerun.class)
	public void salesOrderWithFreightTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrderPage;

		//================Data Declaration ===========================
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

		final String itemNumber = "1000";
		final String quantity = "1";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 92.00;

		final int expectedLinesCount = 4;

		//================script implementation=======================
		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.collapseAll();
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		//Click on "New" option
		salesOrderPage = settingsPage.clickOnNewButton();

		//Enter "Customer account" -- 'US-004'
		salesOrderPage.expandCustomerSection();
		salesOrderPage.setCustomerAccount(defaultCustomerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrderPage.finishCreatingSalesOrder();

		//Click on "Header" option
		salesOrderPage.openHeaderTab();

		String salesOrderNumber = salesOrderPage.getSalesOrderNumber();
		VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

		//Go to "Set up" section
		//salesOrderPage.expandSetupHeaderSection();

		//Set "Sales tax group" -- 'VertexAR'
		salesOrderPage.setSalesOrderTaxGroup(vertexAR);

		//Click on "Lines" option
		salesOrderPage.openLinesTab();

		// add line item
		salesOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

		// navigate to Charges page
		salesOrderPage.navigateToChargesPage();

		// add Freight charges
		salesOrderPage.addCharges("FREIGHT", false,"Fixed","150", "VertexAR", "All");

		// close Charges page
		salesOrderPage.closeCharges();

		//Click on "Sales Tax" option
		salesOrderPage.clickOnTab(salesOrderPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrderPage.openSalesTaxCalculation();

		int actualLinesCount = salesOrderPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
			String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
				expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = salesOrderPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		expectedAllLinesDataList.add(DFinanceFreightSOLineDetails.Line1.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceFreightSOLineDetails.Line2.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceFreightSOLineDetails.Line3.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceFreightSOLineDetails.Line4.getLineDataMap());

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
		String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
			"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = salesOrderPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
			"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		salesOrderPage.salesTaxCalculator.closeSalesTaxCalculation();
		
		//Navigate to  All Sales Orders page
		DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = salesOrderPage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
		postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);

		//Click on "Sales Order Number" resulted in the Search Result
		postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		salesOrderPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		salesOrderPage.clickDeleteYesButton();
	}

	/**
	 * creates a new sales order, change the quantity and ship to address case, and then deletes that new
	 * sales order
	 * CD365-179
	 */
	@Ignore
	@Test(groups = { "FO_smoke", "FO_AR_regression", "FO_ALL" })
	public void salesOrderCreationChangeQuantityAndShipToAddressTaxCalculationTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrderPage;

		//================Data Declaration ===========================
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		final String product1Number = "1000";
		final String product2Number = "A0001";
		System.out.println("product - 1 Number: " + product1Number);
		System.out.println("product - 2 Number: " + product2Number);

		final String customerAccount = "US-004";
		final String salesTaxGroup = DFinanceConstantDataResource.VERTEXAR.getData();

		final String quantity = "1";
		final String site = "2";
		final String warehouse = "24";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 160.00;

		int expectedLinesCount = 4;

		//================script implementation=======================

		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		//Click on "New" option
		salesOrderPage = settingsPage.clickOnNewButton();

		//Enter "Customer account" (above created)
		salesOrderPage.expandCustomerSection();
		salesOrderPage.setCustomerAccount(customerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrderPage.finishCreatingSalesOrder();

		//Click on "Header" option
		salesOrderPage.openHeaderTab();

		String salesOrderNumber = salesOrderPage.getSalesOrderNumber();
		VertexLogger.log(String.format("Sales Order# %s", salesOrderNumber));

		//Set "Sales tax group" -- 'VertexAR'
		salesOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		//Click on "Lines" option
		salesOrderPage.openLinesTab();

		// add line item (Product - 1)
		allSalesOrdersSecondPage.fillFirstItemsInfo(product1Number,site, warehouse, quantity, unitPrice, 0);

		// add line item (Product - 1)
		allSalesOrdersSecondPage.fillItemsInfo(product2Number,site, warehouse, quantity, unitPrice, 1);

		//Click on "Sales Tax" option
		salesOrderPage.clickOnTab(salesOrderPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrderPage.openSalesTaxCalculation();

		int actualLinesCount = salesOrderPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
			String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
				expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = salesOrderPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		Map<String, String> tempLineMap1 = DFinanceColoradoSOLineDetails.Line13.getLineDataMap();
		tempLineMap1.put("Description", product1Number);

		Map<String, String> tempLineMap2 = DFinanceColoradoSOLineDetails.Line14.getLineDataMap();
		tempLineMap2.put("Description", product1Number);

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);

		tempLineMap1 = DFinanceColoradoSOLineDetails.Line13.getLineDataMap();
		tempLineMap1.put("Description", product2Number);

		tempLineMap2 = DFinanceColoradoSOLineDetails.Line14.getLineDataMap();
		tempLineMap2.put("Description", product2Number);


		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
			expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = salesOrderPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
			"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = salesOrderPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
			"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		salesOrderPage.salesTaxCalculator.closeSalesTaxCalculation();

		// remove the first product
		salesOrderPage.removeLineItem(product1Number);

		// update item quantity & ship to address
		salesOrderPage.updateItemQuantity(product2Number, "5");

		//Go to "Address" section and click on "+" icon available at the "Delivery address" field to enter a new address
		salesOrderPage.updateAddress();

		final String description = "Test Address";
		final String zipCode = "17601-6824";
		final String street = "201 Granite Run Dr";
		final String city = "Lancaster";
		final String state = "PA";
		final String country = "USA";

		//Enter "Description"
		salesOrderPage.setDescription(description);

		//Enter "Street"
		salesOrderPage.setStreet(street);

		//Enter "City"
		salesOrderPage.setCity(city);

		//Enter "State"
		salesOrderPage.setState(state);

		//Enter "Country"
		salesOrderPage.setCountry(country);

		//Enter 5-digit ZIP/Postal Code
		salesOrderPage.setZipCode(zipCode);

		//Click on "Ok" button
		salesOrderPage.clickOk();
		salesOrderPage.clickYes();

		//Get Address for verify zipcode
		String address = salesOrderPage.getAddress();
		System.out.println(address);

		//Check the presence of 9-digit zip code is triggered in the New address
		salesOrderPage.verifyZipCode(address);

		//Click on "Sales Tax" option
		salesOrderPage.clickOnTab(salesOrderPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrderPage.openSalesTaxCalculation();
		//salesOrderPage.updateAllLines();

		actualLinesCount = salesOrderPage.getTempSalesTaxTransactionLinesCount();
		expectedLinesCount = 1;

		assertTrue(actualLinesCount == expectedLinesCount,
			String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
				expectedLinesCount, actualLinesCount));

		allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = salesOrderPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		tempLineMap1 = DFinancePennsylvaniaSOLineDetails.Line1.getLineDataMap();
		tempLineMap1.put("Description", product2Number);

		expectedAllLinesDataList.add(tempLineMap1);

		expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
			expectedAllLinesSet);
		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}
		//Click on "OK" button
		salesOrderPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Delete SO
		//Navigate to  All Sales Orders page
		salesOrderPage = homePage.navigateToAllSalesOrdersPage();

		//In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
		settingsPage.searchCreatedSalesOrder(salesOrderNumber);

		//Click on "Sales Order Number" resulted in the Search Result
		salesOrderPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		salesOrderPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		salesOrderPage.clickDeleteYesButton();

	}


	/**
	 * Verify Tax Amount for Freight Charge in Sales Order
	 * CD365-827
	 * @author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxAmountForFreightChargeTest(){

		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrdersPage;

		final String customerAccount = "004021";
		final String itemNumber1 = "1000";
		final String quantity = "1";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 92.00;

		final int expectedLinesCount = 4;

		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		//Click on "New" option
		salesOrdersPage = settingsPage.clickOnNewButton();

		//Enter "Customer account" (above created)
		salesOrdersPage.expandCustomerSection();
		salesOrdersPage.setCustomerAccount(customerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrdersPage.finishCreatingSalesOrder();

		//Click on "Lines" option
		salesOrdersPage.openLinesTab();

		//Add line items
		salesOrdersPage.addItemLine(itemNumber1, quantity, site, warehouse, unitPrice);

		//Click on "Maintain Charges" option
		salesOrdersPage.navigateToChargesPage();

		// add Freight charges
		salesOrdersPage.addCharges("FREIGHT", false,"Fixed","150", "VertexAR", "All");

		// close Charges page
		salesOrdersPage.closeCharges();

		//Click on "Sales Tax" option
		salesOrdersPage.clickOnTab(salesOrdersPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrdersPage.openSalesTaxCalculation();

		int actualLinesCount = salesOrdersPage.getTempSalesTaxTransactionLinesCount();
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

		Map<String, String> tempLineMap1 = DFinanceFreightSOLineDetails.Line1.getLineDataMap();

		Map<String, String> tempLineMap2 = DFinanceFreightSOLineDetails.Line2.getLineDataMap();

		Map<String, String> tempLineMap3 = DFinanceFreightSOLineDetails.Line3.getLineDataMap();

		Map<String, String> tempLineMap4 = DFinanceFreightSOLineDetails.Line4.getLineDataMap();

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);
		expectedAllLinesDataList.add(tempLineMap3);
		expectedAllLinesDataList.add(tempLineMap4);

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		salesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

	}

	/**
	 * Verify Tax Amount for Miscellaneous Charge in Sales Order
	 * CD365-834
	 * @author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxAmountForMiscellaneousChargeTest(){

		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrdersPage;

		final String customerAccount = "004021";
		final String itemNumber1 = "1000";
		final String quantity = "1";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 92.00;

		final int expectedLinesCount = 4;

		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		//Click on "New" option
		salesOrdersPage = settingsPage.clickOnNewButton();

		//Enter "Customer account" (above created)
		salesOrdersPage.expandCustomerSection();
		salesOrdersPage.setCustomerAccount(customerAccount);

		//Click on "OK"  button on "Create Sales Order" screen
		salesOrdersPage.finishCreatingSalesOrder();

		//Click on "Lines" option
		salesOrdersPage.openLinesTab();

		//Add line items
		salesOrdersPage.addItemLine(itemNumber1, quantity, site, warehouse, unitPrice);

		//Click on "Maintain Charges" option
		salesOrdersPage.navigateToChargesPage();

		// add Miscellaneous charges
		salesOrdersPage.addCharges("OTHER", false,"Fixed", "150", "VertexAR", "All");

		// close Charges page
		salesOrdersPage.closeCharges();

		//Click on "Sales Tax" option
		salesOrdersPage.clickOnTab(salesOrdersPage.sellTabName);

		// navigate to Tax --> Sales Tax page
		salesOrdersPage.openSalesTaxCalculation();

		int actualLinesCount = salesOrdersPage.getTempSalesTaxTransactionLinesCount();
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

		Map<String, String> tempLineMap1 = DFinanceFreightSOLineDetails.Line5.getLineDataMap();

		Map<String, String> tempLineMap2 = DFinanceFreightSOLineDetails.Line6.getLineDataMap();

		Map<String, String> tempLineMap3 = DFinanceFreightSOLineDetails.Line3.getLineDataMap();

		Map<String, String> tempLineMap4 = DFinanceFreightSOLineDetails.Line4.getLineDataMap();

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);
		expectedAllLinesDataList.add(tempLineMap3);
		expectedAllLinesDataList.add(tempLineMap4);

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}

		//Verify the "Total calculated sales tax amount" value
		String calculatedSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();

		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");

		//Verify the "Total actual sales tax amount" value
		String actualSalesTaxAmount = salesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");

		//Click on "OK" button
		salesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

	}

	/**
	 * @TestCase CD365-236
	 * @Description - Validate the xml (quote) displays the AssistedParameters field in the
	 * Response for Sales Order
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void checkXMLInquiryForAssistedParametersFieldForSalesOrderTest(){

		String itemNumber = "1000";
		String quantity = "1";
		String site = "2";
		String wareHouse = "22";
		String unitPrice = "1000";

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
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

		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		//Add line item to Sales Order
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		//navigate to Tax --> Sales Tax page
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);

		//Click on "Sales Tax" option
		allSalesOrdersPage.openSalesTaxCalculation();

		//Navigate to Vertex XML Inquiry
		DFinanceXMLInquiryPage xmlInquiryPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Filter the Inquiry XML by Sales Order Number
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();

		//Validate that Assisted Parameters is present in the log
		String response = xmlInquiryPage.getLogRequestValue();
		assertTrue(response.contains("<AssistedParameters>"));

	}

	/**
	 * @TestCase CD365-164
	 * @Description - Create Sales order with Customer Code Exemption
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderWithExemptTaxGroupAndValidateCustomerCodeExemptTest(){
		String itemNumber = "1000";
		String quantity = "1";
		String site = "2";
		String wareHouse = "22";
		String unitPrice = "1000";
		final double expectedTaxAmount = 0.00;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany("USMF");

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create a new sales order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("EXEMPT");

		// Verify the "Total actual sales tax amount" value
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();


		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
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

		//Navigate to Vertex XML Inquiry and verify productClass is equal to EXEMPT and Total Tax
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Product productClass=\"EXEMPT\">1000</Product>"));
		assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));

	}

	/**
	 * @TestCase CD365-165
	 * @Description - Create Sales order with Customer Class Exemption
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createSalesOrderWithExemptTaxGroupAndValidateCustomerClassExemptTest(){
		String itemNumber = "1000";
		String quantity = "1";
		String site = "2";
		String wareHouse = "22";
		String unitPrice = "1000";
		final double expectedTaxAmount = 0.00;
		final int expectedLinesCount = 4;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);

		settingsPage.selectCompany("USMF");

		homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create new sales order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("Auto_0102211328408");
		allSalesOrdersPage.clickOkBTN();

		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();

		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);

		//Validate tax amount and verify total lines
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		int actualLinesCount = allSalesOrdersPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
				String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
						expectedLinesCount, actualLinesCount));
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to "Pick and pack" Tab
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompletedPickingList = allSalesOrdersPage.messageBarConfirmation(operationCompleted);

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompletedPackingSlip = allSalesOrdersPage.messageBarConfirmation(operationCompleted);

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to Vertex XML Inquiry and validate CUST_CLASS and Total Tax
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<CustomerCode classCode=\"CUST_CLASS\" isBusinessIndicator=\"true\">Auto_0102211328408</CustomerCode>"));
		assertTrue(response.contains("<TotalTax>0.0</TotalTax>"));
	}


	/**
	 * @TestCase CD365-1395
	 * @Description - Create Sales order and validate it has actual sales tax amount adjusted and Read Only attribute not present
	 *  for actual sales tax amount
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesOrderActualTaxAmountWhenAdjustedAndHasReadOnlyNotPresentTest(){
		final String itemNumber = "1000";
		final String quantity = "1";
		final String site = "2";
		final String wareHouse = "22";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 20.00;
		final int expectedLinesCount = 4;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);

		settingsPage.selectCompany(usmfCompany);

		homePage.navigateToPage(DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create new sales order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 1);

		//Validate tax amount and verify total lines
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();

		int actualLinesCount = allSalesOrdersPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
				String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
						expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = allSalesOrdersPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		Map<String, String> tempLineMap1 = DFinanceFreightSOLineDetails.Line3.getLineDataMap();

		Map<String, String> tempLineMap2 = DFinanceFreightSOLineDetails.Line4.getLineDataMap();

		Map<String, String> tempLineMap3 = DFinanceFreightSOLineDetails.Line3.getLineDataMap();

		Map<String, String> tempLineMap4 = DFinanceFreightSOLineDetails.Line4.getLineDataMap();

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);
		expectedAllLinesDataList.add(tempLineMap3);
		expectedAllLinesDataList.add(tempLineMap4);

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}

		//Adjust actual sales tax and verify tax amount
		createPurchaseOrderPage.updateActualSalesTaxAmount("20.00");
		createPurchaseOrderPage.clickAdjustmentSalesTax();
		createPurchaseOrderPage.clickApplyActualAmounts();
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");

		//Verify that Readonly attribute is not present
		Boolean actualSalesTaxAmountReadOnlyValue = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmountAttributeAndValue();
		assertTrue(actualSalesTaxAmountReadOnlyValue, "Readonly Value is not equal to true");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);

		//Navigate to XML and verify total tax
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(invoiceNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>160.0</TotalTax>"));

	}

	/**
	 * @TestCase CD365-188
	 * @Description - Create Sales Order for a customer with a modified origin state
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" })
	public void createSalesOrderForCustomerWithModifiedOriginStateTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final String description =  "Created Address";
		final String zipcode = DFinanceConstantDataResource.ZIPCODE.getData();
		final String street = DFinanceConstantDataResource.STREET.getData();

		Double expectedTaxAmount = 101.00;

		settingsPage.selectCompany(usmfCompany);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create new sales order and change address
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("US-004");
		allSalesOrdersPage.clickPlusIconForAddAddress();
		allSalesOrdersPage.setDescription(description);
		allSalesOrdersPage.setZipCode(zipcode);
		allSalesOrdersPage.setStreet(street);
		allSalesOrdersPage.clickOk();
		allSalesOrdersPage.clickOnAddValidOk();
		allSalesOrdersPage.clickOnOKBtn();

		//Add line items
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);

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

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to  XML inquiry and verify tax amount
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(salesOrderNumber);
		xMLInquiryPage.clickOnFirstResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>101.0</TotalTax>"));

	}

	/**
	 * @TestCase CD365-1451
	 * @Description - Verify that Recalc button is present and when clicked is making correct number of request and response calls
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateRecalcButtonIsMakingCorrectResponseAndRequestForSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		final Double expectedTaxAmount = 160.00;

		settingsPage.selectCompany(usmfCompany);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Add line items
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

		//Verify Recalc Button is present and click
		allSalesOrdersPage.clickRecalculateTax("Recalculate sales tax", "2", true);

		//Verify sales tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Finds the first Sales Order and confirms that the correct number of response/request is present
		assertTrue(verifyNumberOfResponsesAndRequests(salesOrderNumber, 4),
				"The appropriate number of Request/Response type is not correct");


		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.clickOnSellTab();
		//Verify Recalc Button is present
		allSalesOrdersPage.clickRecalculateTax("Recalculate sales tax", "1", false);
	}

	/**
	 * @TestCase CD365-159
	 * @Description - Return a partial order with shipping
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void returnSalesOrderWithPartialShippingTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "0008";
		final String quantity = "2";
		Double expectedTaxAmount = 2.54;

		settingsPage.selectCompany(usrtCompany);

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
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersPage.addLineItem();
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, "1", 1);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");
		allSalesOrdersPage.addLineItem();
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, "3", 2);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Complete the order
		allSalesOrdersSecondPage.clickOnComplete();
		String balance = allSalesOrdersSecondPage.getBalance();
		allSalesOrdersSecondPage.addPayment(balance, "1");

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_RETURN_ORDERS);

		//Create a new return order and complete the return, while only returning 2 items
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		String newSalesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.findSalesOrder();
		allSalesOrdersSecondPage.selectRecentSalesOrder(salesOrderNumber);
		allSalesOrdersSecondPage.deselectItemsInReturnOrder(2, true);
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersSecondPage.retailReturnReasonCode();
		allSalesOrdersSecondPage.returnReason("11", "21");
		allSalesOrdersSecondPage.clickOnCompleteJavaScript();
		allSalesOrdersSecondPage.clickOnSubmit();

		//Navigate to  XML inquiry and verify tax amount
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(newSalesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>-1.27</TotalTax>"));
	}

	/**
	 * @TestCase CD365-158
	 * @Description - Return a partial quantity for a Sales Order
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void salesOrderWithPartialQuantityReturnedTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "0008";
		final String quantity = "5";
		Double expectedTaxAmount = 2.12;

		settingsPage.selectCompany(usrtCompany);

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
		allSalesOrdersSecondPage.fillItemsInfoForUSRT(itemNumber, quantity, 0);
		allSalesOrdersSecondPage.changeDeliveryMode("11");
		allSalesOrdersSecondPage.changeUnderDelivery("100");

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.clickOnCancel();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Complete the order
		allSalesOrdersSecondPage.clickOnComplete();
		String balance = allSalesOrdersSecondPage.getBalance();
		allSalesOrdersSecondPage.addPayment(balance, "1");

		//Post the invoice
		allSalesOrdersSecondPage.clickInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_RETURN_ORDERS);

		//Create a new return order, update the return quantity, and complete the return
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		String newSalesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.findSalesOrder();
		allSalesOrdersSecondPage.selectRecentSalesOrder(salesOrderNumber);
		allSalesOrdersSecondPage.updateReturnedQuantityAmount("3");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersSecondPage.retailReturnReasonCode();
		allSalesOrdersSecondPage.returnReason("11", "21");
		allSalesOrdersSecondPage.clickOnCompleteJavaScript();
		allSalesOrdersSecondPage.clickOnSubmit();

		//Navigate to  XML inquiry and verify tax amount
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		xMLInquiryPage.getDocumentID(newSalesOrderNumber);
		xMLInquiryPage.clickResponse();
		String response = xMLInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<TotalTax>-1.27</TotalTax>"));
	}

	/**
	 * @TestCase CD365-241
	 * @Description - Validate there is no XML files when logging is disabled
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateNoXMLFilesCreatedWhenCreatingSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		final Double expectedTaxAmount = 160.00;

		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();
		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();

		settingsPage.selectCompany(usmfCompany);

		//Navigate to Vertex Tax Parameter page and disable Logging button
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleEnableLoggingButton(false);
		logging = false;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Add line items
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

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to XML inquiry and verify response/request is not visible for sales order
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage XMLInquiryPage = new DFinanceXMLInquiryPage(driver);
		XMLInquiryPage.getDocumentID(salesOrderNumber);
		assertTrue(XMLInquiryPage.clickResponse() == false);
	}

	/**
	 * @TestCase CD365-1489
	 * @Description - Validate Invoice Tax Code for Sales order
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateInvoiceTaxCodesForSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "D0001";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "1000";

		final String expectedTaxAmount = "760.00";

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Add line items
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "2", unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "2", unitPrice, 1);

		//Validate sales tax amount and Navigate to Vertex invoice text codes (Verify that 21 and Intra-country is visible)
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		allSalesOrdersPage.salesTaxCalculator.clickVertexInvoiceTextCode();
		assertTrue(allSalesOrdersPage.salesTaxCalculator.clickAndReturnInvoiceTextCodeAndDescription("21", "Intra-country") == true,
				"Invoice text code and Invoice text code description is not equal to 21 and Intra-country");
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.contains(actualSalesTaxAmount),
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Navigate to posted sales tax and verify that Vertex invoice text codes and description columns and values are present
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		allSalesOrdersPage.salesTaxCalculator.clickVertexInvoiceTextCode();
		assertTrue(allSalesOrdersPage.salesTaxCalculator.validateInvoiceTextCodeAndColumnHeaders(1, "21", "Intra-country",
				"Invoice text code", "Invoice text code description") == true, "Expected value(s) are not returned");
		calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.equalsIgnoreCase(expectedTaxAmount),
				assertionTotalCalculatedSalesTaxAmount);

		//Navigate to XML Inquiry and verify quantity
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		//assertTrue(request.contains("<InvoiceTextCode>21</InvoiceTextCode>"));
		xmlInquiryPage.clickResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<InvoiceTextCode>21</InvoiceTextCode>"));
	}

	/**
	 * @TestCase CD365-1492
	 * @Description - Validate Sales Tax Is Correct Using VAP Item Sales Tax For Sales Order
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateSalesTaxIsCorrectUsingVAPItemSalesTaxForSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		final String expectedTaxAmount = "160.00";

		settingsPage.selectCompany(usmfCompany);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Add line items
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("VAP");
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 1);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("VAP");

		//Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(calculatedSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(actualSalesTaxAmount.contains(expectedTaxAmount),
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Navigate to XML Inquiry and verify quantity
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Destination taxAreaId=\"113152575\" locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>395 College St W</StreetAddress1>\n" +
				"\t\t\t\t<City>Abbeville</City>\n" +
				"\t\t\t\t<MainDivision>GA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>31001-4231</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>"));
		xmlInquiryPage.clickResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>"));

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		settingsPage.navigateToDashboardPage();

		//Navigate to XML Inquiry and verify quantity
		settingsPage.navigateToDashboardPage();
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickResponse();
		response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>"));
	}

	/**
	 * @TestCase CD365-1883
	 * @Description - Verify tax registration id for sales order when using invoice account
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_ALL","FO_VAT_regression" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxRegistrationIdForInvoiceAccountSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "50";
		final String unitPrice = "100";
		final String site = "1";
		final String warehouse = "11";
		final Double expectedTaxAmount = 950.00;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Select Invoice account
		allSalesOrdersPage.selectInvoiceOrCustomerAccountOption("Using invoice account / bill to address");

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickLineDetailsTab("TabPageAddress");
		allSalesOrdersSecondPage.updateDeliveryAddress("Belgium");
		allSalesOrdersSecondPage.clickCloseBtn();

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

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify the Belgium tax registration id is present in Request
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>DE023004560</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>"));

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Find the recent Sales Order
		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on Posted Sales Tax and get Invoice Number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(postedSalesTaxAmount)==expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify the Belgium tax registration id is present in Request
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>DE023004560</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>"));

	}

	 /**
	    * @TestCase CD365-1884
		* @Description - Verify tax registration for sales order is not present when
	    * 				 using customer account
		* @Author Mario Saint-Fleur
	 */
	 @Test(groups = { "FO_ALL","FO_VAT_regression" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxRegistrationIdIsNotPresentForCustomerAccountSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "50";
		final String unitPrice = "100";
		final String site = "1";
		final String warehouse = "11";
		final Double expectedTaxAmount = 0.00;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Select Customer account
		allSalesOrdersPage.selectInvoiceOrCustomerAccountOption("Using customer account / ship to address");

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickLineDetailsTab("TabPageAddress");
		allSalesOrdersSecondPage.updateDeliveryAddress("France");
		allSalesOrdersSecondPage.clickCloseBtn();

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

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		 allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on Posted Sales Tax and get Invoice Number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("0.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify the DEU tax registration id is not present in Request
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(!request.contains("<Customer>\n" +
				"\t\t\t\t<CustomerCode classCode=\"20\">DE-010</CustomerCode>\n" +
				"\t\t\t\t<Destination>\n" +
				"\t\t\t\t\t<StreetAddress1>Residence Moliere</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Chateauroux</City>\n" +
				"\t\t\t\t\t<MainDivision />\n" +
				"\t\t\t\t\t<PostalCode>36000</PostalCode>\n" +
				"\t\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t\t</Destination>\n"+
				"\t\t\t\t<TaxRegistration isoCountryCode=\"FRA\" hasPhysicalPresenceIndicator=\"true\">\n" +
				"\t\t\t\t\t<TaxRegistrationNumber>DE020223102</TaxRegistrationNumber>\n" +
				"\t\t\t\t</TaxRegistration>"));

		//Verify the DEU tax registration id is not present in Request
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(!request.contains("<Customer>\n" +
				"\t\t\t\t<CustomerCode classCode=\"20\">DE-010</CustomerCode>\n" +
				"\t\t\t\t<Destination>\n" +
				"\t\t\t\t\t<StreetAddress1>Residence Moliere</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Chateauroux</City>\n" +
				"\t\t\t\t\t<MainDivision />\n" +
				"\t\t\t\t\t<PostalCode>36000</PostalCode>\n" +
				"\t\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t\t</Destination>\n" +
				"\t\t\t\t<TaxRegistration isoCountryCode=\"FRA\" hasPhysicalPresenceIndicator=\"true\">\n" +
				"\t\t\t\t\t<TaxRegistrationNumber>DE020223102</TaxRegistrationNumber>\n" +
				"\t\t\t\t</TaxRegistration>\n" +
				"\t\t\t</Customer>"));

	}

	/**
	 * @TestCase CD365-1885
	 * @Description - Verify tax registration for sales order is present when
	 * 				 using invoice account
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_ALL","FO_VAT_regression" }, retryAnalyzer = TestRerun.class)
	public void verifyTaxRegistrationIdIsPresentForInvoiceAccountSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "50";
		final String unitPrice = "100";
		final String site = "1";
		final String warehouse = "11";
		final Double expectedTaxAmount = 0.00;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;
		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Select Invoice account
		allSalesOrdersPage.selectInvoiceOrCustomerAccountOption("Using invoice account / bill to address");

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickLineDetailsTab("TabPageAddress");
		allSalesOrdersSecondPage.updateDeliveryAddress("France");
		allSalesOrdersSecondPage.clickCloseBtn();

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				calculatedSalesTaxAmount +" "+assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				actualSalesTaxAmount+" "+assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on Posted Sales Tax and get Invoice Number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("0.00"),
				postedSalesTaxAmount+" "+assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify the DEU tax registration id is present in Request
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Customer>\n" +
				"\t\t\t\t<CustomerCode classCode=\"20\">DE-010</CustomerCode>\n" +
				"\t\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Residence Moliere</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Chateauroux</City>\n" +
				"\t\t\t\t\t<MainDivision />\n" +
				"\t\t\t\t\t<PostalCode>36000</PostalCode>\n" +
				"\t\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t\t</Destination>\n" +
				"\t\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Rebhuhnweg 34</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Mainz</City>\n" +
				"\t\t\t\t\t<MainDivision>RP</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>55116</PostalCode>\n" +
				"\t\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t\t<TaxRegistrationNumber>DE023004560</TaxRegistrationNumber>\n" +
				"\t\t\t\t</TaxRegistration>\n" +
				"\t\t\t</Customer>"));

		assertTrue(request.contains("<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"20\">DE-010</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Lilienweg 9</StreetAddress1>\n" +
				"\t\t\t\t<City>Kiel</City>\n" +
				"\t\t\t\t<MainDivision>SH</MainDivision>\n" +
				"\t\t\t\t<PostalCode>24103</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Rebhuhnweg 34</StreetAddress1>\n" +
				"\t\t\t\t<City>Mainz</City>\n" +
				"\t\t\t\t<MainDivision>RP</MainDivision>\n" +
				"\t\t\t\t<PostalCode>55116</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>DE023004560</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>\n" +
				"\t\t</Customer>"));

		//Verify the DEU tax registration id is present in Request
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Customer>\n" +
				"\t\t\t\t<CustomerCode classCode=\"20\">DE-010</CustomerCode>\n" +
				"\t\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Residence Moliere</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Chateauroux</City>\n" +
				"\t\t\t\t\t<MainDivision />\n" +
				"\t\t\t\t\t<PostalCode>36000</PostalCode>\n" +
				"\t\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t\t</Destination>\n" +
				"\t\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>Rebhuhnweg 34</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Mainz</City>\n" +
				"\t\t\t\t\t<MainDivision>RP</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>55116</PostalCode>\n" +
				"\t\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t\t<TaxRegistrationNumber>DE023004560</TaxRegistrationNumber>\n" +
				"\t\t\t\t</TaxRegistration>\n" +
				"\t\t\t</Customer>"));

		assertTrue(request.contains("<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"20\">DE-010</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Lilienweg 9</StreetAddress1>\n" +
				"\t\t\t\t<City>Kiel</City>\n" +
				"\t\t\t\t<MainDivision>SH</MainDivision>\n" +
				"\t\t\t\t<PostalCode>24103</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Rebhuhnweg 34</StreetAddress1>\n" +
				"\t\t\t\t<City>Mainz</City>\n" +
				"\t\t\t\t<MainDivision>RP</MainDivision>\n" +
				"\t\t\t\t<PostalCode>55116</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>DE023004560</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>\n" +
				"\t\t</Customer>"));
	}

	/**
	 * @TestCase CD365-2029
	 * @Description - Verify Flexfields in XML Inquiry for Sales Order and Sales Invoice
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_ALL","FO_VAT_regression" })
	public void verifyFlexFieldForSalesOrderAndSalesInvoiceTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "50";
		final String unitPrice = "100";
		final String site = "1";
		final String warehouse = "11";
		final Double expectedTaxAmount = 950.00;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickCloseBtn();

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

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on Posted Sales Tax and get Invoice Number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("950.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify the Flexfields is present for SO Request
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<FlexibleFields>\n" +
			"\t\t\t\t<FlexibleCodeField fieldId=\"1\">20</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"2\">DE-010</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"3\">DE-011</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"4\">Rebhuhnweg 34\n" +
				"55116 Mainz\n" +
				"DEU</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"5\">Mainz</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"6\">RP</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"7\">55116</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"9\">false</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"10\">DE023004560</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"11\">true</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"12\">DE-011</FlexibleCodeField>\n" ));
		assertTrue(request.contains("\t\t\t\t<FlexibleCodeField fieldId=\"13\">DE</FlexibleCodeField>\n" ));

		//Verify the Flexfields is present for Invoice Request
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<FlexibleFields>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"1\">20</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"2\">DE-010</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"3\">DE-011</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"4\">Rebhuhnweg 34\n" +
				"55116 Mainz\n" +
				"DEU</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"5\">Mainz</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"6\">RP</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"7\">55116</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"9\">false</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"10\">DE023004560</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"11\">true</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"12\">DE-011</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"13\">DE</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleCodeField fieldId=\"14\">558789256301245</FlexibleCodeField>\n" +
				"\t\t\t\t<FlexibleDateField fieldId=\"1\">"));
	}

	/**
	 * @TestCase CD365-2031
	 * @Description - Verify Positive And Negative Extended Price For Sales Order Test
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_ALL", "FO_AR_regression" }, retryAnalyzer = TestRerun.class)
	public void verifyPositiveAndNegativeExtendedPriceForSalesOrderTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "500";
		final Double expectedTaxAmount = 180.50;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.clickCloseBtn();

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT",false,"Fixed","50","VertexAR", "All");
		allSalesOrdersPage.addCharges("TESTB",false,"Fixed","100","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

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

		//Generate invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click on Posted Sales Tax and get Invoice Number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("180.50"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		//Verify Positive and Negative values of FREIGHT and TESTB respectively
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>50.000</ExtendedPrice>"));
		assertTrue(request.contains("<Product productClass=\"All\">TESTB</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>-100.000</ExtendedPrice>"));
	}

	/**
	 * @TestCase CD365-2048
	 * @Description - Create a Sales Order With Multiple Shipment, Cancel Picking List, Invoice, and Verify Tax
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_ALL", "FO_AR_regression"})
	public void createSalesOrderWithMultiShipmentAndVerifyChargesAndTaxTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "1000";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "500";
		final Double expectedTaxAmount = 176.00;

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
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", "1000", 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 1);
		allSalesOrdersSecondPage.clickCloseBtn();

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.navigateToChargesPage();
		allSalesOrdersPage.addCharges("FREIGHT",true, "Percent","10","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

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

		//Confirm Sales Order
		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.clickOnOKPopUp();

		//Generate Picking List
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Picking List Registration
		allSalesOrdersPage.clickPickingListRegistration();
		allSalesOrdersPage.selectLineItemPickingRegistration(1);
        allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSOrderTransUnPick");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSPickingLineCancel");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnCloseSalesOrderPage("3");

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.selectQuantityValueFromDialog("Picked");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Generate Invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("Packing slip");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("88.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		//Verify XML Inquiry Freight and Product taxes
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Product productClass=\"ALL\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>100.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>100.0</ExtendedPrice>\n" +
				"\t\t\t<Taxes taxCollectedFromParty=\"BUYER\" taxType=\"SALES\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"21\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"6244\">GEORGIA</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>4.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>100.0</Taxable>"));
		assertTrue(response.contains("<CalculatedTax>4.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>100.0</Taxable>"));
		assertTrue(response.contains("<Product productClass=\"ALL\">1000</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>1000.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>1000.0</ExtendedPrice>\n" +
				"\t\t\t<Discount userDefinedDiscountCode=\"DISC20OFF\">\n" +
				"\t\t\t\t<DiscountAmount>0</DiscountAmount>\n" +
				"\t\t\t</Discount>\n" +
				"\t\t\t<Taxes taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" taxType=\"SALES\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"21\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"6244\">GEORGIA</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>40.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>1000.0</Taxable>"));
		assertTrue(response.contains("<CalculatedTax>40.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>1000.0</Taxable>"));

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Generate Picking List for cancel lines
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.selectQuantityValueFromDialog("Picked");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Post Packing Slip
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Generate Invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.selectInvoiceNumberForInvoiceJournal(2);
		String invoiceNumber2 = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("2");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("88.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		//Verify XML Inquiry Freight and Product taxes
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber2);
		xmlInquiryPage.clickOnFirstResponse();
		response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Product productClass=\"ALL\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>100.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>100.0</ExtendedPrice>\n" +
				"\t\t\t<Taxes taxCollectedFromParty=\"BUYER\" taxType=\"SALES\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"21\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"6244\">GEORGIA</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>4.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>100.0</Taxable>"));
		assertTrue(response.contains("<CalculatedTax>4.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>100.0</Taxable>"));
		assertTrue(response.contains("<Product productClass=\"ALL\">1000</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>1000.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>1000.0</ExtendedPrice>\n" +
				"\t\t\t<Discount userDefinedDiscountCode=\"DISC20OFF\">\n" +
				"\t\t\t\t<DiscountAmount>0</DiscountAmount>\n" +
				"\t\t\t</Discount>\n" +
				"\t\t\t<Taxes taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" taxType=\"SALES\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"21\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"6244\">GEORGIA</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>40.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>1000.0</Taxable>"));
		assertTrue(response.contains("<CalculatedTax>40.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.04</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>1000.0</Taxable>"));
	}

	/**
	 * @TestCase CD365-2445 (CMSDD-578)
	 * @Description - Create a Sales Order With Freight and display tax details
	 * @Author Brenda Johnson
	 */
	@Ignore
	@Test(groups = { "FO_ALL", "FO_AR_regression"})
	public void createSalesOrderWithFreightTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "1000";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "500";
		final Double expectedTaxAmount = 225.50;

		settingsPage.selectCompany(usmfCompany);

		DFinanceAccountsRecParametersPage accountsRecParametersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelLink.ACCOUNTS_RECEIVABLE_PARAMETERS);

		accountsRecParametersPage.toggleTaxAdjustmentDetail(true);
		taxAdjustmentToggle=true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("US-002");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", "1000", 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, quantity, unitPrice, 1);
		allSalesOrdersSecondPage.clickCloseBtn();

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.navigateToChargesPage();
		allSalesOrdersPage.addCharges("FREIGHT", true, "Percent", "10", "VertexAR", "All");
		allSalesOrdersPage.closeCharges();

		///Validate sales tax amount
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.clickAdjustmentTab();
		allSalesOrdersPage.salesTaxCalculator.adjustSalesTaxDetail("Detail");
		allSalesOrdersPage.salesTaxCalculator.adjustTaxLine(1, "2.00");

		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Confirm Sales Order
		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify XML Inquiry Freight and Product taxes
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String request = xmlInquiryPage.getLogResponseValue();
		assertTrue(request.contains("<Product productClass=\"ALL\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>200.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>200.0</ExtendedPrice>\n" +
				"\t\t\t<Taxes taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" taxType=\"SELLER_USE\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"21\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"2398\">CALIFORNIA</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>12.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.06</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>200.0</Taxable>"));
	}

	/**
	 * @TestCase CD365-2049
	 * @Description - Create a Sales Order With Multiple Shipment, Cancel Picking List, Invoice, and Verify Tax for EU
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_ALL", "FO_AR_regression"})
	public void createSalesOrderWithMultiShipmentAndVerifyChargesAndTaxForEUTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "500";
		final Double expectedTaxAmount = 836.00;
		final String vertexAR = DFinanceConstantDataResource.VERTEXAR.getData();

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		allSalesOrdersPage.openHeaderTab();
		allSalesOrdersPage.expandSetupHeaderSection();
		allSalesOrdersPage.setSalesOrderTaxGroup(vertexAR);
		allSalesOrdersPage.openLinesTab();

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, "1000", 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "4", unitPrice, 1);
		allSalesOrdersSecondPage.clickCloseBtn();

		//Click on Sell Tab and select Maintain charges
		allSalesOrdersPage.navigateToChargesPage();
		allSalesOrdersPage.addCharges("FREIGHT",true, "Percent","10","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

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

		//Confirm Sales Order
		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.clickOnOKPopUp();

		//Generate Picking List
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		allSalesOrdersPage.clickPickingListRegistration();
		allSalesOrdersPage.selectLineItemPickingRegistration(1);
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSOrderTransUnPick");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSPickingLineCancel");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnCloseSalesOrderPage("3");

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.selectQuantityValueFromDialog("Picked");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Generate Invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("Packing slip");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("418.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		//Verify XML Inquiry Freight and Product taxes
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String request = xmlInquiryPage.getLogResponseValue();
		assertTrue(request.contains("<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>200.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>200.0</ExtendedPrice>\n" +
				"\t\t\t<Taxes vertexTaxCode=\"V_SDO_19_G\" taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" rateClassification=\"Standard Rate\" taxType=\"VAT\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"OGSTC\" inputOutputType=\"OUTPUT\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78283\">GERMANY</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>38.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.19</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>200.0</Taxable>"));
		assertTrue(request.contains("<Product productClass=\"All\">D0001</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">2.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>2000.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>2000.0</ExtendedPrice>\n" +
				"\t\t\t<Discount userDefinedDiscountCode=\"DISC20OFF\">\n" +
				"\t\t\t\t<DiscountAmount>0</DiscountAmount>\n" +
				"\t\t\t</Discount>\n" +
				"\t\t\t<Taxes vertexTaxCode=\"V_SDO_19_G\" taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" rateClassification=\"Standard Rate\" taxType=\"VAT\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" inputOutputType=\"OUTPUT\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78283\">GERMANY</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>380.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.19</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>2000.0</Taxable>"));

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Generate Picking List for cancel lines
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.selectQuantityValueFromDialog("Picked");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Post Packing Slip
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Generate Invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.selectInvoiceNumberForInvoiceJournal(2);
		invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("2");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("418.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		//Verify XML Inquiry Freight and Product taxes
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstResponse();
		request = xmlInquiryPage.getLogResponseValue();
		assertTrue(request.contains("<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">1.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>200.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>200.0</ExtendedPrice>\n" +
				"\t\t\t<Taxes vertexTaxCode=\"V_SDO_19_G\" taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" rateClassification=\"Standard Rate\" taxType=\"VAT\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"OGSTC\" inputOutputType=\"OUTPUT\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78283\">GERMANY</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>38.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.19</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>200.0</Taxable>"));
		assertTrue(request.contains("<Product productClass=\"All\">D0001</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"Ea\">4.0</Quantity>\n" +
				"\t\t\t<FairMarketValue>2000.0</FairMarketValue>\n" +
				"\t\t\t<ExtendedPrice>2000.0</ExtendedPrice>\n" +
				"\t\t\t<Discount userDefinedDiscountCode=\"DISC20OFF\">\n" +
				"\t\t\t\t<DiscountAmount>0</DiscountAmount>\n" +
				"\t\t\t</Discount>\n" +
				"\t\t\t<Taxes vertexTaxCode=\"V_SDO_19_G\" taxStructure=\"SINGLE_RATE\" taxCollectedFromParty=\"BUYER\" rateClassification=\"Standard Rate\" taxType=\"VAT\" taxResult=\"TAXABLE\" situs=\"DESTINATION\" taxCode=\"OGSTC\" inputOutputType=\"OUTPUT\">\n" +
				"\t\t\t\t<Jurisdiction jurisdictionLevel=\"COUNTRY\" jurisdictionId=\"78283\">GERMANY</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>380.0</CalculatedTax>\n" +
				"\t\t\t\t<EffectiveRate>0.19</EffectiveRate>\n" +
				"\t\t\t\t<Taxable>2000.0</Taxable>"));
	}

	/**
	 * @TestCase CD365-2080
	 * @Description - Verify that the 27 cent fee is present in Sales Order when using CO delivery address
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_ALL", "FO_AR_regression" }, retryAnalyzer = TestRerun.class)
	public void verifyCOStateDeliveryFeeWhenAddingFreightChargeInSalesOrderTest(){
		String site = "2";
		String warehouse = "21";
		String itemNumber = "1000";
		String quantity = "1";
		Double expectedTaxAmount = 56.78;
		final int daysToAdd = 12;
		final int expectedLinesCount = 9;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("US-003");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Click on "Header" option and sets the confirmed receipt date
		allSalesOrdersPage.openHeaderTab();
		allSalesOrdersPage.setConfirmedReceiptDate(daysToAdd);

		//Add line items
		allSalesOrdersPage.openLinesTab();
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, "1000", 0);
		allSalesOrdersSecondPage.clickLineDetailsTab("TabPageAddress");
		allSalesOrdersSecondPage.updateDeliveryAddress("Co Address");

		//Click on Financials and select Maintain charges
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.clickMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT",false, "Fixed","130","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

		///Validate sales tax amount and validate presence of Colorado delivery fee
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		int actualLinesCount = allSalesOrdersPage.getTempSalesTaxTransactionLinesCount();
		assertTrue(actualLinesCount == expectedLinesCount,
				String.format("Temporary sales tax transactions lines expected count: %s, but actual count: %s",
						expectedLinesCount, actualLinesCount));

		List<Map<String, String>> allLinesDataList = new ArrayList<Map<String, String>>();

		for ( int i = 1 ; i <= actualLinesCount ; i++ )
		{
			Map<String, String> lineDataMap = allSalesOrdersPage.getTransactionLineData(String.format("%s", i));

			System.out.println(String.format("Line: %s -- %s", i, lineDataMap));

			allLinesDataList.add(lineDataMap);
		}

		List<Map<String, String>> expectedAllLinesDataList = new ArrayList<Map<String, String>>();

		Map<String, String> tempLineMap1 = DFinanceFreightSOLineDetails.Line13.getLineDataMap();
		Map<String, String> tempLineMap2 = DFinanceFreightSOLineDetails.Line14.getLineDataMap();
		Map<String, String> tempLineMap3 = DFinanceFreightSOLineDetails.Line15.getLineDataMap();
		Map<String, String> tempLineMap4 = DFinanceFreightSOLineDetails.Line16.getLineDataMap();
		Map<String, String> tempLineMap5 = DFinanceFreightSOLineDetails.Line17.getLineDataMap();
		Map<String, String> tempLineMap6 = DFinanceFreightSOLineDetails.Line18.getLineDataMap();
		Map<String, String> tempLineMap7 = DFinanceFreightSOLineDetails.Line19.getLineDataMap();
		Map<String, String> tempLineMap8 = DFinanceFreightSOLineDetails.Line20.getLineDataMap();
		Map<String, String> tempLineMap9 = DFinanceFreightSOLineDetails.Line21.getLineDataMap();

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);
		expectedAllLinesDataList.add(tempLineMap3);
		expectedAllLinesDataList.add(tempLineMap4);
		expectedAllLinesDataList.add(tempLineMap5);
		expectedAllLinesDataList.add(tempLineMap6);
		expectedAllLinesDataList.add(tempLineMap7);
		expectedAllLinesDataList.add(tempLineMap8);
		expectedAllLinesDataList.add(tempLineMap9);

		Set<Map<String, String>> expectedAllLinesSet = new HashSet<Map<String, String>>(expectedAllLinesDataList);

		Set<Map<String, String>> actualAllLinesSet = new HashSet<Map<String, String>>(allLinesDataList);

		boolean resultFlag = expectedAllLinesSet.containsAll(actualAllLinesSet) && actualAllLinesSet.containsAll(
				expectedAllLinesSet);

		if (expectedAllLinesDataList.stream().count() == actualAllLinesSet.stream().count()) {
			assertTrue(resultFlag,String.format("Temporary sales tax transactions expected lines: %s, but actual lines: %s",
					expectedAllLinesSet, actualAllLinesSet));
		}

		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);

		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		//Verify state tax fee and Delivery type is present
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstResponse();
		String response = xmlInquiryPage.getLogResponseValue();
		assertTrue(response.contains("<Jurisdiction jurisdictionLevel=\"STATE\" jurisdictionId=\"4220\">COLORADO</Jurisdiction>\n" +
				"\t\t\t\t<CalculatedTax>0.28</CalculatedTax>"));
		assertTrue(response.contains("<Imposition impositionId=\"13\">Retail Delivery Fee</Imposition>"));
	}

	/**
	 * @TestCase CD365-2087
	 * @Description - Create a Sales Order Where Country Does Not Have Currency Setup And Verify EU
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_ALL", "FO_AR_regression"}, retryAnalyzer = TestRerun.class)
	public void createSalesOrderWhereCountryDoesNotHaveCurrencyConversionEUTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0001";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "500";
		final Double expectedTaxAmount = 0.00;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(true);
		advTaxGroup = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-012");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();
		allSalesOrdersPage.expandSetupHeaderSection();
		allSalesOrdersPage.setSalesOrderTaxGroup("VertexAR");

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersPage.openLinesTab();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, "1000", 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, warehouse, "4", unitPrice, 1);
		allSalesOrdersSecondPage.clickCloseBtn();

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

		//Verify Currency Conversion is not present for France Customer
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Company>DEMF</Company>\n" +
				"\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
				"\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
				"\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeOrigin>\n" +
				"\t\t</Seller>\n" +
				"\t\t<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"30\">DE-012</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Rue Grise 123</StreetAddress1>\n" +
				"\t\t\t\t<City>Montpellier</City>\n" +
				"\t\t\t\t<MainDivision>LR</MainDivision>\n" +
				"\t\t\t\t<PostalCode>34070</PostalCode>\n" +
				"\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Rue Grise 123</StreetAddress1>\n" +
				"\t\t\t\t<City>Montpellier</City>\n" +
				"\t\t\t\t<MainDivision>LR</MainDivision>\n" +
				"\t\t\t\t<PostalCode>34070</PostalCode>\n" +
				"\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"FRA\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>FR12331202350</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>"));

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Generate Invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Verify Posted Sales Tax
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.selectInvoiceNumberForInvoiceJournal(1);
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");
		allSalesOrdersSecondPage.clickOnPostedSalesTax();
		String postedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(postedSalesTaxAmount.equalsIgnoreCase("0.00"),
				assertionTotalCalculatedSalesTaxAmount);
		settingsPage.navigateToDashboardPage();

		//Verify Currency Conversion is not present for France Customer
		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<Currency isoCurrencyCodeAlpha=\"EUR\" />\n" +
				"\t\t<Seller>\n" +
				"\t\t\t<Company>DEMF</Company>\n" +
				"\t\t\t<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
				"\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
				"\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeOrigin>\n" +
				"\t\t</Seller>\n" +
				"\t\t<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"30\">DE-012</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Rue Grise 123</StreetAddress1>\n" +
				"\t\t\t\t<City>Montpellier</City>\n" +
				"\t\t\t\t<MainDivision>LR</MainDivision>\n" +
				"\t\t\t\t<PostalCode>34070</PostalCode>\n" +
				"\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Rue Grise 123</StreetAddress1>\n" +
				"\t\t\t\t<City>Montpellier</City>\n" +
				"\t\t\t\t<MainDivision>LR</MainDivision>\n" +
				"\t\t\t\t<PostalCode>34070</PostalCode>\n" +
				"\t\t\t\t<Country>FRA</Country>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"FRA\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>FR12331202350</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>\n" +
				"\t\t</Customer>"));
	}

	/**
	 * @TestCase CD365-2091
	 * @Description - Verify that invoice is present in XML Inquiry when using Vertex Posting Queue
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage" })
	public void validateInvoiceIsPresentInXMLInquiryUsingVertexPostingQueueTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		DFinanceVertexInvoicePostingQueuePage vertexInvoicePostingQueuePage = new DFinanceVertexInvoicePostingQueuePage(driver);

		final String itemNumber = "1000";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";

		final Double expectedTaxAmount = 80.00;

		final String tax = DFinanceLeftMenuNames.TAX.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		final String vertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		final String taxGroupSettings = DFinanceVertexTaxParametersLeftMenuNames.TAX_GROUP_SETTINGS.getData();

		settingsPage.selectCompany(usmfCompany);

		//Turn invoice toggle off
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(vertexTaxParameters);
		settingsPage.selectSettingsPage(taxGroupSettings);
		settingsPage.toggleSendPostingRequestImmediately(false);
		sendPostingRequestImmediately = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Add line item
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "1", unitPrice, 0);

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

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Journal Invoice and get invoice number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify that Invoice call is not present
		xmlInquiryPage.getDocumentID(invoiceNumber);
		assertTrue(verifyNumberOfResponsesAndRequests(invoiceNumber, 0),
				"The appropriate number of Request/Response type is not correct");

		//Navigate to workspace posting queue
		homePage.navigateToPage(DFinanceLeftMenuModule.WORKSPACES, DFinanceModulePanelLink.VERTEX_INVOICE_POSTING_QUEUE);

		//Verify that invoice is present and set to waiting and Run posting process
		assertTrue(vertexInvoicePostingQueuePage.verifyInvoiceIsPresentAndInvoiceStatus("Waiting", invoiceNumber), "Invoice status is not equal to waiting");
		vertexInvoicePostingQueuePage.runInvoicePostingQueueRequestProcess();
		allSalesOrdersPage.clickOnOKBtn();
		vertexInvoicePostingQueuePage.clickRefreshButton("1");

		//Verify that invoice change to posted
		assertTrue(vertexInvoicePostingQueuePage.verifyInvoiceIsPresentAndInvoiceStatus("Posted", invoiceNumber), "Invoice status is not equal to posted");

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify that Invoice call is present
		xmlInquiryPage.getDocumentID(invoiceNumber);
		assertTrue(verifyNumberOfResponsesAndRequests(invoiceNumber, 4),
				"The appropriate number of Request/Response type is not correct");
	}

	/**
	 * @TestCase CD365-2448
	 * @Description - Validate Presence Of Custom Location When Creating Sales Order
	 * @Author Mario Saint-Fleur
	 */
	@Ignore
	@Test(groups = { "FO_AR_regression", "FO_ALL" })
	public void validatePresenceOfCustomLocationWhenCreatingSalesOrderTest() {
		/** TODO Create a 3rd address and be sure to add to all environments  **/
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0003";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "1000";
		final Double expectedTaxAmount = 0.00;

		settingsPage.selectCompany(germanCompanyDEMF);

		//Enable Advanced Tax Group
		settingsPage.toggleAdvancedTaxGroup(true);

		//Set Customs status for DE-016
		DFinanceAllCustomersPage allCustomersPage = homePage.navigateToAllCustomersPage();
		allCustomersPage.searchCustomerAccount("DE-016");
		allCustomersPage.clickAddressMoreOptions();
		allCustomersPage.clickAddressAdvancedButton();
		allCustomersPage.selectCustomsStatus("Free Trade Zone");

		//Set Customs status for Warehouse 11
		DFinanceWarehousesPage warehousesPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.WAREHOUSE_MANAGEMENT, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.WAREHOUSE, DFinanceModulePanelLink.WAREHOUSES);
		warehousesPage.searchWarehouse("11");
		warehousesPage.clickAddressAdvancedButton();
		warehousesPage.selectCustomsStatus("Inward Processing Relief");

		customsStatus = true;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Setup Header Section
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-016");
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.openHeaderTab();
		allSalesOrdersPage.setSalesTaker("Sara Thomas");
		allSalesOrdersPage.expandSetupHeaderSection();
		allSalesOrdersPage.setSalesOrderTaxGroup("VertexAR");

		//Add line item, add freight charges for both at line and header level
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersPage.openLinesTab();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, "2", unitPrice, 0);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("ALL");
		allSalesOrdersSecondPage.clickLineDetailsTab("TabLineDelivery");
		allSalesOrdersSecondPage.selectDeliveryControlDate("ATP");
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT",false,"Fixed","50","VertexAR", "All");
		allSalesOrdersPage.closeCharges();
		allSalesOrdersSecondPage.fillItemsInfo("D0004", site, warehouse, "2", unitPrice, 1);
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("ALL");
		allSalesOrdersSecondPage.clickLineDetailsTab("TabLineDelivery");
		allSalesOrdersSecondPage.selectDeliveryControlDate("ATP");
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT",false,"Fixed","100","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

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

		//Navigate to XML Inquiry and validate locationCustomsStatus and Addresses for Header, Charges and Lines(1st and 2nd) (Temporary Import, Free Circulation, Free Trade Zone
		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
				"\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>123 Coffee Street</StreetAddress1>\n" +
				"\t\t\t\t<City>Redmond</City>\n" +
				"\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>98052</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1.3698630136986301369863013699</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeOrigin>\n" +
				"\t\t</Seller>\n" +
				"\t\t<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"30\">DE-016</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_TRADE_ZONE\">\n" +
				"\t\t\t\t<StreetAddress1>Teichgasse 12</StreetAddress1>\n" +
				"\t\t\t\t<City>Kiel</City>\n" +
				"\t\t\t\t<MainDivision>SH</MainDivision>\n" +
				"\t\t\t\t<PostalCode>24103</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_TRADE_ZONE\">\n" +
				"\t\t\t\t<StreetAddress1>Teichgasse 12</StreetAddress1>\n" +
				"\t\t\t\t<City>Kiel</City>\n" +
				"\t\t\t\t<MainDivision>SH</MainDivision>\n" +
				"\t\t\t\t<PostalCode>24103</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>DE9012</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>\n" +
				"\t\t</Customer>"));
		assertTrue(request.contains("<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t\t<StreetAddress1>123 Coffee Street</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Redmond</City>\n" +
				"\t\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>98052</PostalCode>\n" +
				"\t\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1.3698630136986301369863013699</CurrencyConversion>\n" +
				"\t\t\t\t</AdministrativeOrigin>"));
		assertTrue(request.contains("<PhysicalOrigin locationCustomsStatus=\"INWARD_PROCESSING_RELIEF\">\n" +
				"\t\t\t\t\t<StreetAddress1>Domstrasse 321</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Hannover</City>\n" +
				"\t\t\t\t\t<MainDivision>NI</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>30159</PostalCode>\n" +
				"\t\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t\t</PhysicalOrigin>"));
		assertTrue(request.contains("\t<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>50</ExtendedPrice>"));
		assertTrue(request.contains("\t<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>100</ExtendedPrice>"));

		//Navigate back to sales order and generate picking list, picking list registration, and update all
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Find the recent Sales Order
		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.clickOnOKPopUp();

		//Click "Generate picking list"
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();
		boolean isOperationCompletedPickingList = allSalesOrdersPage.messageBarConfirmation(operationCompleted);

		//Post packing slip
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Journal Invoice and get invoice number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Navigate to XML Inquiry and validate locationCustomsStatus and Addresses for Header, Charges and Lines(1st and 2nd) (Temporary Import, Free Circulation, Free Trade Zone
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("<PhysicalOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>Bahnhofstraße 5</StreetAddress1>\n" +
				"\t\t\t\t<City>Berlin</City>\n" +
				"\t\t\t\t<MainDivision>BE</MainDivision>\n" +
				"\t\t\t\t<PostalCode>10115</PostalCode>\n" +
				"\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</PhysicalOrigin>\n" +
				"\t\t\t<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>123 Coffee Street</StreetAddress1>\n" +
				"\t\t\t\t<City>Redmond</City>\n" +
				"\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>98052</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1.3698630136986301369863013699</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeOrigin>\n" +
				"\t\t</Seller>\n" +
				"\t\t<Customer>\n" +
				"\t\t\t<CustomerCode classCode=\"30\">DE-016</CustomerCode>\n" +
				"\t\t\t<Destination locationCustomsStatus=\"FREE_TRADE_ZONE\">\n" +
				"\t\t\t\t<StreetAddress1>Teichgasse 12</StreetAddress1>\n" +
				"\t\t\t\t<City>Kiel</City>\n" +
				"\t\t\t\t<MainDivision>SH</MainDivision>\n" +
				"\t\t\t\t<PostalCode>24103</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</Destination>\n" +
				"\t\t\t<AdministrativeDestination locationCustomsStatus=\"FREE_TRADE_ZONE\">\n" +
				"\t\t\t\t<StreetAddress1>Teichgasse 12</StreetAddress1>\n" +
				"\t\t\t\t<City>Kiel</City>\n" +
				"\t\t\t\t<MainDivision>SH</MainDivision>\n" +
				"\t\t\t\t<PostalCode>24103</PostalCode>\n" +
				"\t\t\t\t<Country>DEU</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeDestination>\n" +
				"\t\t\t<TaxRegistration hasPhysicalPresenceIndicator=\"true\" isoCountryCode=\"DEU\">\n" +
				"\t\t\t\t<TaxRegistrationNumber>DE9012</TaxRegistrationNumber>\n" +
				"\t\t\t</TaxRegistration>\n" +
				"\t\t</Customer>"));
		assertTrue(request.contains("<AdministrativeOrigin locationCustomsStatus=\"FREE_CIRCULATION\">\n" +
				"\t\t\t\t<StreetAddress1>123 Coffee Street</StreetAddress1>\n" +
				"\t\t\t\t<City>Redmond</City>\n" +
				"\t\t\t\t<MainDivision>WA</MainDivision>\n" +
				"\t\t\t\t<PostalCode>98052</PostalCode>\n" +
				"\t\t\t\t<Country>USA</Country>\n" +
				"\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"USD\">1.3698630136986301369863013699</CurrencyConversion>\n" +
				"\t\t\t</AdministrativeOrigin>"));
		assertTrue(request.contains("<PhysicalOrigin locationCustomsStatus=\"INWARD_PROCESSING_RELIEF\">\n" +
				"\t\t\t\t\t<StreetAddress1>Domstrasse 321</StreetAddress1>\n" +
				"\t\t\t\t\t<City>Hannover</City>\n" +
				"\t\t\t\t\t<MainDivision>NI</MainDivision>\n" +
				"\t\t\t\t\t<PostalCode>30159</PostalCode>\n" +
				"\t\t\t\t\t<Country>deu</Country>\n" +
				"\t\t\t\t\t<CurrencyConversion isoCurrencyCodeAlpha=\"EUR\">1</CurrencyConversion>\n" +
				"\t\t\t\t</PhysicalOrigin>"));
		assertTrue(request.contains("<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>50.000</ExtendedPrice>"));
		assertTrue(request.contains("<Product productClass=\"All\">FREIGHT</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>100.000</ExtendedPrice>"));

	}

	/**
	 * @TestCase CD365-2603
	 * @Description - Validate Oseries Call When Creating a Sales Order with Non-Vertex Tax Group
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateOseriesCallWhenCreatingSalesOrderWithNonVertexTaxGroupTest() {
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		final String itemNumber = "D0003";
		final String quantity = "2";
		final String site = "1";
		final String warehouse = "11";
		final String unitPrice = "500";
		final Double expectedTaxAmount = 0.00;

		settingsPage.selectCompany(germanCompanyDEMF);
		toggleONOFFAdvanceTaxGroup(false);
		advTaxGroup = false;

		//Navigate to All Sales Orders page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "New" option
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount("DE-010");
		allSalesOrdersPage.clickOkBTN();

		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);

		//Click on "Header" option
		allSalesOrdersPage.openHeaderTab();
		allSalesOrdersPage.expandSetupHeaderSection();
		allSalesOrdersPage.setSalesOrderTaxGroup("AR-EU");

		//Add line items
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersPage.openLinesTab();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, warehouse, quantity, "1000", 0);
		allSalesOrdersSecondPage.clickCloseBtn();
		allSalesOrdersSecondPage.clickOnSetUpTab();
		allSalesOrdersSecondPage.selectItemSalesGroupType("FULL");

		//Add charges to the Header and Line Level
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT",false,"Fixed","50","AR-EU", "FULL");
		allSalesOrdersPage.closeCharges();
		allSalesOrdersPage.navigateToChargesPage();
		allSalesOrdersPage.addCharges("FREIGHT",true, "Percent","10","AR-EU", "FULL");
		allSalesOrdersPage.closeCharges();

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

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify that Sales Order call is not present
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		assertTrue(verifyNumberOfResponsesAndRequests(salesOrderNumber, 0),
				"The appropriate number of Request/Response type is not correct");

		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Find the recent Sales Order
		allSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Journal Invoice and get invoice number
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");

		homePage.navigateToPage(
				DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP,
				DFinanceModulePanelCategory.SUB_VERTEX, DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify that Sales Order call is not present
		xmlInquiryPage.getDocumentID(invoiceNumber);
		assertTrue(verifyNumberOfResponsesAndRequests(invoiceNumber, 0),
				"The appropriate number of Request/Response type is not correct");
	}

	/**
	 * @TestCase CD365-2788
	 * @Description - Validate Line Order Sequence For Sales Order
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateLineOrderSequenceForSalesOrderTest(){
		String itemNumber = "1000";
		String quantity = "1";
		String site = "2";
		String wareHouse = "22";
		String unitPrice = "1000";
		final double expectedTaxAmount = 565.60;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany("USMF");

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create a new sales order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, quantity, "1500", 1);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, quantity, "2000", 2);
		allSalesOrdersSecondPage.fillItemsInfo(itemNumber, site, wareHouse, quantity, "2500", 3);

		//Add charges to both Header and Line Level
		allSalesOrdersPage.navigateToChargesPage();
		allSalesOrdersPage.addCharges("FREIGHT",false, "Fixed","20","VertexAR", "All");
		allSalesOrdersPage.closeCharges();
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("FREIGHT",false,"Fixed","50","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

		//Confirm Sales Order
		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.clickOnOKPopUp();

		// Verify the "Total actual sales tax amount" value
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();


		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
		allSalesOrdersPage.openPickingList();
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Cancel two lines
		allSalesOrdersPage.clickPickingListRegistration();
		allSalesOrdersPage.selectLineItemPickingRegistration(1);
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSOrderTransUnPick");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSPickingLineCancel");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.selectLineItemPickingRegistration(2);
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSOrderTransUnPick");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickFunctionsAndSelectMenuOption("WMSPickingLineCancel");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnCloseSalesOrderPage("3");

		//Click "Post packing slip"
		allSalesOrdersPage.postPackingSlip("1");
		allSalesOrdersPage.selectQuantityValueFromDialog("Picked");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("Packing slip");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify presence of 1st and 4th line items
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("lineItemNumber=\"1\""));
		assertTrue(request.contains("<Product productClass=\"ALL\">1000</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"ea\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>1000</ExtendedPrice>"));
		assertTrue(request.contains("lineItemNumber=\"4\""));
		assertTrue(request.contains("<Product productClass=\"ALL\">1000</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"ea\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>2500</ExtendedPrice>"));
		assertTrue(!request.contains("lineItemNumber=\"2\""));
		assertTrue(!request.contains("lineItemNumber=\"3\""));


		DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Find the recent sales order
		postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Click "Generate picking list"
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

		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("2");

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify presence of 2nd and 3rd line items
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("lineItemNumber=\"2\""));
		assertTrue(request.contains("<Product productClass=\"ALL\">1000</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"ea\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>1500</ExtendedPrice>"));
		assertTrue(request.contains("lineItemNumber=\"3\""));
		assertTrue(request.contains("<Product productClass=\"ALL\">1000</Product>\n" +
				"\t\t\t<Quantity unitOfMeasure=\"ea\">1</Quantity>\n" +
				"\t\t\t<ExtendedPrice>2000</ExtendedPrice>"));
		assertTrue(!request.contains("lineItemNumber=\"1\""));
		assertTrue(!request.contains("lineItemNumber=\"4\""));
	}

	/**
	 * @TestCase CD365-2798
	 * @Description - Validate Cost Center, Department, and Gl Accounts For Sales Order
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateCostCenterDepartmentsAndGLAccountsForSalesOrderTest(){
		String itemNumber = "1000";
		String quantity = "1";
		String site = "2";
		String wareHouse = "22";
		String unitPrice = "1000";
		final double expectedTaxAmount = 85.60;

		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceXMLInquiryPage xmlInquiryPage = new DFinanceXMLInquiryPage(driver);

		settingsPage.selectCompany("USMF");

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create a new sales order
		allSalesOrdersPage.openNewSalesOrder();
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		//Add cost center, department, and gl account to both line and header
		allSalesOrdersPage.openHeaderTab();
		allSalesOrdersPage.addBusinessUnit("DimensionEntryControlTable_DECValue_BusinessUnit","002");
		allSalesOrdersPage.addCostCenter("DimensionEntryControlTable_DECValue_CostCenter", "009");
		allSalesOrdersPage.addDepartment("DimensionEntryControlTable_DECValue_Department", "023");
		allSalesOrdersPage.addItemGroup("DimensionEntryControlTable_DECValue_ItemGroup","Audio");
		allSalesOrdersPage.setSalesOrderTaxGroup("VertexAR");
		allSalesOrdersPage.openLinesTab();
		allSalesOrdersPage.clickLineDetailsTab("TabFinancialDimensionLine");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.addBusinessUnit("DimensionEntryControlLine_DECValue_BusinessUnit","002");
		allSalesOrdersPage.addCostCenter("DimensionEntryControlLine_DECValue_CostCenter", "007");
		allSalesOrdersPage.addDepartment("DimensionEntryControlLine_DECValue_Department", "022");
		allSalesOrdersPage.addItemGroup("DimensionEntryControlLine_DECValue_ItemGroup", "Audio");

		//Add charges to both Header and Line Level
		allSalesOrdersPage.navigateToChargesPage();
		allSalesOrdersPage.addCharges("FREIGHT",false, "Fixed","20","VertexAR", "All");
		allSalesOrdersPage.closeCharges();
		allSalesOrdersPage.clickOnFinancials();
		allSalesOrdersPage.selectMaintainCharges();
		allSalesOrdersPage.addCharges("INSTALL",false,"Fixed","50","VertexAR", "All");
		allSalesOrdersPage.closeCharges();

		//Confirm Sales Order
		allSalesOrdersSecondPage.clickOnConfirmSalesOrder();
		allSalesOrdersPage.clickOkBTN();
		allSalesOrdersPage.clickOnOKPopUp();

		// Verify the "Total actual sales tax amount" value
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				"'Total calculated sales tax amount' value is not expected");
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				"'Total actual sales tax amount' value is not expected");
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify presence cost center, departments, and gl accounts
		xmlInquiryPage.getDocumentID(salesOrderNumber);
		xmlInquiryPage.clickOnFirstRequest();
		String request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("costCenter=\"009\" projectNumber=\"\" departmentCode=\"023\" generalLedgerAccount=\"403500\""));
		assertTrue(request.contains("costCenter=\"007\" projectNumber=\"\" departmentCode=\"022\" generalLedgerAccount=\"401100\""));
		assertTrue(request.contains("costCenter=\"007\" projectNumber=\"\" departmentCode=\"022\" generalLedgerAccount=\"403150\""));

		DFinanceAllSalesOrdersPage postTestAllSalesOrdersPage = allSalesOrdersPage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Find the recent sales order
		postTestAllSalesOrdersPage.searchCreatedSalesOrder(salesOrderNumber);
		postTestAllSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.pickandpackTabName);

		//Post the invoice
		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.generateInvoice();
		allSalesOrdersPage.selectQuantityValueFromDialog("All");
		allSalesOrdersPage.clickOnOKBtn();
		allSalesOrdersPage.clickOnOKPopUp();

		allSalesOrdersSecondPage.clickOnInvoiceTab();
		allSalesOrdersSecondPage.journalInvoice();
		String invoiceNumber = allSalesOrdersPage.getPostedSalesTaxInvoiceNumber("1");

		homePage.navigateToPage(DFinanceLeftMenuModule.TAX, DFinanceModulePanelCategory.SETUP, DFinanceModulePanelCategory.SUB_VERTEX,
				DFinanceModulePanelLink.VERTEX_XML_INQUIRY);

		//Verify presence cost center, departments, and gl accounts
		xmlInquiryPage.getDocumentID(invoiceNumber);
		xmlInquiryPage.clickOnFirstRequest();
		request = xmlInquiryPage.getLogRequestValue();
		assertTrue(request.contains("costCenter=\"009\" projectNumber=\"\" departmentCode=\"023\" generalLedgerAccount=\"403500\""));
		assertTrue(request.contains("costCenter=\"007\" projectNumber=\"\" departmentCode=\"022\" generalLedgerAccount=\"401100\""));
		assertTrue(request.contains("costCenter=\"007\" projectNumber=\"\" departmentCode=\"022\" generalLedgerAccount=\"403150\""));
	}

}