package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceConstantDataResource;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuNames;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceSammamishSOLineDetails;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.assertTrue;

/**
 * Create a new customer and then create Sales Order by using the new created customer
 *
 * @author Shiva Mothkula
 */
@Listeners(TestRerunListener.class)
public class DFinanceCreateCustomerTests extends DFinanceBaseTest
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
	 * Create Customer Test
	 * CD365-1378
	 */
	@Ignore
	@Test(groups = { "RSAT_Coverage"}, retryAnalyzer = TestRerun.class)
	public void createCustomerTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage = new DFinanceSettingsPage(driver);
		DFinanceAllSalesOrdersPage salesOrderPage;

		//================Data Declaration ===========================
		final String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		final String orders = DFinanceLeftMenuNames.ORDERS.getData();
		final String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();

		DateFormat dateFormat = new SimpleDateFormat("ddMMYYHHsS");
		Calendar cal = Calendar.getInstance();
		final String random = dateFormat.format(cal.getTime());
		System.out.println("random: " + random);
		final String customerAccount = String.format("Auto_%s", random);
		final String customerType = "Organization";
		final String customerName = String.format("Auto Test Customer %s", random);
		final String customerGroup = "10";
		String salesTaxGroup = DFinanceConstantDataResource.VERTEXAR.getData();

		String streetAddress = Address.Washington.addressLine1;
		String city = Address.Washington.city;
		String state = Address.Washington.state.abbreviation;
		String zipCode = Address.Washington.zip5;
		String country = Address.Washington.country.iso3code;

		final String itemNumber = "A0001";
		final String quantity = "1";
		final String site = "2";
		final String warehouse = "21";
		final String unitPrice = "1000";
		final double expectedTaxAmount = 101.00;

		final int expectedLinesCount = 4;

		//================script implementation=======================

		// Navigate to All Customers Page
		DFinanceAllCustomersPage allCustomersPage = homePage.navigateToAllCustomersPage();

		// Create a new Customer
		DFinanceCreateCustomerPage createCustomerPage = allCustomersPage.clickNewCustomerButton();

		// set Customer details
		createCustomerPage.setCustomerDetails(customerAccount, customerType, customerName, customerGroup);

		// verify default country value
		String actualCountry = createCustomerPage.getCountry();
		assertTrue(actualCountry.contains(country),
			String.format("Expected Country: %s, but actual Country: %s", country, actualCountry));

		// set Address details
		createCustomerPage.setCustomerAddressDetails(streetAddress, city, state, country, zipCode);

		// click Save button
		createCustomerPage.clickSaveButton();

		//Navigate to  All Sales Orders page
		homePage.navigateToAllSalesOrdersPage();

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

		//Go to "Set up" section
		//salesOrderPage.expandSetupHeaderSection();

		//Set "Sales tax group" -- 'VertexAR'
		salesOrderPage.setSalesOrderTaxGroup(salesTaxGroup);

		//Click on "Lines" option
		salesOrderPage.openLinesTab();

		// add line item
		salesOrderPage.addItemLine(itemNumber, quantity, site, warehouse, unitPrice);

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

		expectedAllLinesDataList.add(DFinanceSammamishSOLineDetails.Line2.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceSammamishSOLineDetails.Line3.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceSammamishSOLineDetails.Line5.getLineDataMap());
		expectedAllLinesDataList.add(DFinanceSammamishSOLineDetails.Line6.getLineDataMap());

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

		//Delete SO
		//Navigate to  All Sales Orders page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(accountReceivable);
		homePage.expandModuleCategory(orders);
		settingsPage = homePage.selectModuleTabLink(allSalesOrders);

		salesOrderPage.searchCreatedSalesOrder(salesOrderNumber);

		//Select the current customer
		salesOrderPage.clickOnDisplayedSalesOrderNumber();
		//Click on "Delete" option from the menu
		salesOrderPage.clickDeleteButton();

		//Click on "Yes" option from the pop-up
		salesOrderPage.clickDeleteYesButton();

		// Navigate to All Customers Page
		allCustomersPage = homePage.navigateToAllCustomersPage();

		// search above created customer
		allCustomersPage.searchCustomerAccount(customerAccount);
		allCustomersPage.clickSelectedCustomerCheckBox();

		// delete customer
		allCustomersPage.clickDeleteButton();
		allCustomersPage.clickDeleteYesButton();
	}
}
