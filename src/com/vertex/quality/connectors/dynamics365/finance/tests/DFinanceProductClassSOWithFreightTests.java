package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceColoradoSOLineDetails;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceConstantDataResource;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertTrue;

/**
 * Configure a product and then create Sales Order by using the newly configured product
 *
 * @author Shiva Mothkula
 */
@Listeners(TestRerunListener.class)
public class DFinanceProductClassSOWithFreightTests extends DFinanceBaseTest
{
	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
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
	}

	/**
	 * @TestCase CD365-1294 CD365-1403
	 * @Description - Create Product Class Sales Order With Freight
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_AR_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void createProductClassSalesOrderWithFreightTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersPage salesOrderPage = null;

		//================Data Declaration ===========================
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		final String expectedProductType = "Item";
		final String productName = "Auto Test Product";
		final String itemModelGroup = "STD";
		final String itemGroup = "Services";
		final String storageDimensionGroup = "SiteWH";
		final String trackingDimensionGroup = "None";

		final String expectedInventoryUnit = "ea";
		final String expectedPurchaseUnit = "ea";
		final String expectedSalesUnit = "ea";
		final String expectedBOMUnit = "ea";

		final String salesTaxItemGroupId = "All";
		final String purchaseTaxItemGroupId = "All";

		final String vendor = "US_TX_015";

		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHsS");
		Calendar cal = Calendar.getInstance();
		String random = dateFormat.format(cal.getTime());

		final String productNumber = String.format("Prd_%s", random);
		System.out.println("productNumber: " + productNumber);

		final String customerAccount = "US-004";
		final String salesTaxGroup = DFinanceConstantDataResource.VERTEXAR.getData();

		final String quantity = "1";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 92.00;

		final int expectedLinesCount = 4;

		//================script implementation=======================

		// Navigate to All Customers Page
		DFinanceReleasedProductsPage releasedProductsPage = homePage.navigateToReleasedProductsPage();

		// Configure new Product
		DFinanceNewReleasedProductPage newReleasedProductPage = releasedProductsPage.clickNewCustomerButton();

		// verify product type
		String actualProductType = newReleasedProductPage.getProductType();
		assertTrue(expectedProductType.equalsIgnoreCase(actualProductType),
			String.format("Expected Product Type: %s, but actual Product Type: %s", expectedProductType,
				actualProductType));

		// Enter "Product number" in "IDENTIFICATION" section
		newReleasedProductPage.setProductNumber(productNumber);

		// Enter "Product name" in "IDENTIFICATION" section
		newReleasedProductPage.setProductName(productName);

		//Enter "Item model group" in "REFERENCE GROUPS"
		newReleasedProductPage.setItemModelGroup(itemModelGroup);

		//Enter "Item group"
		newReleasedProductPage.setItemGroup(itemGroup);

		//Enter "Storage dimension group"
		newReleasedProductPage.setStorageDimensionGroup(storageDimensionGroup);

		// Enter "Tracking dimension group"
		newReleasedProductPage.setTrackingDimensionGroup(trackingDimensionGroup);

		// Verify "Inventory unit" in "UNITS OF MEASURES" section
		String actualInventoryUnit = newReleasedProductPage.getInventoryUnit();
		assertTrue(expectedInventoryUnit.equalsIgnoreCase(actualInventoryUnit),
			String.format("Expected Inventory Unit: %s, but actual Inventory Unit: %s", expectedInventoryUnit,
				actualInventoryUnit));

		//Verify "Purchase unit"
		String actualPurchaseUnit = newReleasedProductPage.getPurchaseUnit();
		assertTrue(expectedPurchaseUnit.equalsIgnoreCase(actualPurchaseUnit),
			String.format("Expected Purchase Unit: %s, but actual Purchase Unit: %s", expectedProductType,
				actualPurchaseUnit));

		//Verify  "Sales unit"
		String actualSalesUnit = newReleasedProductPage.getSalesUnit();
		assertTrue(expectedSalesUnit.equalsIgnoreCase(actualSalesUnit),
			String.format("Expected Sales Unit: %s, but actual Sales Unit: %s", expectedSalesUnit, actualSalesUnit));

		//Verify "BOM unit"
		String actualBOMUnit = newReleasedProductPage.getBOMUnit();
		assertTrue(expectedBOMUnit.equalsIgnoreCase(actualBOMUnit),
			String.format("Expected BOM Unit: %s, but actual BOM Unit: %s", expectedBOMUnit, actualBOMUnit));

		//Enter "Item sales tax group" in "SALES TAXATION"
		newReleasedProductPage.setSalesTaxItemGroupId(salesTaxItemGroupId);

		//Enter "Item sales tax group" in "PURCHASE TAXATION"
		newReleasedProductPage.setPurchaseTaxItemGroupId(purchaseTaxItemGroupId);

		//Click "Ok" button
		newReleasedProductPage.clickOkButton();

		// Enter "Vendor" in "Purchase" tab
		releasedProductsPage.setVendorInPurchaseTab(vendor);

		// click Save button
		releasedProductsPage.clickSaveButton();

		//Navigate to  All Sales Orders page
		salesOrderPage = homePage.navigateToAllSalesOrdersPage();

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

		// add line item
		salesOrderPage.addItemLine(productNumber, quantity, site, warehouse, unitPrice);

		// navigate to Charges page
		salesOrderPage.navigateToChargesPage();

		// add Freight charges
		salesOrderPage.addCharges("FREIGHT", false,"Fixed", "150", "VertexAR", "All");

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

		Map<String, String> tempLineMap1 = DFinanceColoradoSOLineDetails.Line13.getLineDataMap();
		tempLineMap1.put("Description", productNumber);

		Map<String, String> tempLineMap2 = DFinanceColoradoSOLineDetails.Line14.getLineDataMap();
		tempLineMap2.put("Description", productNumber);

		expectedAllLinesDataList.add(tempLineMap1);
		expectedAllLinesDataList.add(tempLineMap2);
		expectedAllLinesDataList.add(DFinanceColoradoSOLineDetails.Line11.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceColoradoSOLineDetails.Line12.getLineDataMap());

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

		//Delete SO
		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		//In "ALL SALES ORDERS" search filter enter the above created Sales order number and then search by "Sales Order Number"
		salesOrderPage = settingsPage.searchCreatedSalesOrder(salesOrderNumber);

		//Click on "Sales Order Number" resulted in the Search Result
		salesOrderPage.clickOnDisplayedSalesOrderNumber();

		//Click on "Delete" option from the menu
		salesOrderPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		salesOrderPage.clickDeleteYesButton();

		// Navigate to All Customers Page
		releasedProductsPage = homePage.navigateToReleasedProductsPage();

		// search above created Product
		releasedProductsPage.searchReleasedProduct(productNumber);

		// delete Product
		releasedProductsPage.clickDeleteButton();
		//Click on "Yes" option from the pop-up
		releasedProductsPage.clickDeleteYesButton();
	}
}
