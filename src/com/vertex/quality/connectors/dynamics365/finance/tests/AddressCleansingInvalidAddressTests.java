package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceTaxValidation;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class AddressCleansingInvalidAddressTests extends DFinanceBaseTest
{
	DFinanceHomePage homePage;
	DFinanceSettingsPage settingsPage;
	DFinanceInvoicePage invoicePage;
	DFinanceXMLInquiryPage xmlInquiryPage;
	DFinanceCreatePurchaseOrderPage createPurchaseOrderPage;
	DFinanceAllSalesOrdersPage allSalesOrdersPage;
	DFinanceAllSalesOrdersPage salesOrdersPage;
	DFinanceTaxValidation dFinanceTaxValidation;

	Boolean addressCleanse = true;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		homePage = new DFinanceHomePage(driver);
		invoicePage = new DFinanceInvoicePage(driver);
		xmlInquiryPage = new DFinanceXMLInquiryPage(driver);
		createPurchaseOrderPage = new DFinanceCreatePurchaseOrderPage(driver);
		allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);
		settingsPage = new DFinanceSettingsPage(driver);
		dFinanceTaxValidation = new DFinanceTaxValidation();

		addressCleanse = true;

	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		if (!addressCleanse) {
			//Enable Accounts Receivable
			homePage.clickOnNavigationPanel();
			homePage.selectLeftMenuModule(tax);
			homePage.collapseAll();
			homePage.expandModuleCategory(setup);
			homePage.expandModuleCategory(vertex);
			settingsPage = homePage.selectModuleTabLink(taxParametersData);
			settingsPage.toggleAddressCleansingButton(true);
		}
	}

	/**
	 * @TestCase CD365-274
	 * @Description - To verify the "Address Cleansing" functionality with "Invalid Address" and the following
	 *                parameters settings as below Also, to verify the message from Vertex related to Invalid address in
	 *                the following settings Acceptable confidence level = 75 Always list all address results = NO       (8)
	 * @Author Siddhartha Gupta
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void AddressCleansingWithInvalidAddressTest( )
	{
		//================Data Declaration ===========================
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();
		String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		String orders = DFinanceLeftMenuNames.ORDERS.getData();
		String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();
		String customerAccount = defaultCustomerAccount;

		String description = "Address testing 1";
		String zipcode = "98074";
			String street = "5 Woodfield Mall";
		String city = "Hyderabad";
		String state = "CA";
		String county = "KINGS";

		//================script implementation========================
		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);

		//Click on "Address Validation" from the left pane
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.setConfidenceAndResults("75", true);

		//click on save button
		settingsPage.clickOnSaveButton();
		homePage = settingsPage.navigateToDashboardPage();

		//Navigate to  All Sales Order page
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Create sales order
		salesOrdersPage = homePage.newAddress(customerAccount);
		salesOrdersPage.newAddressSalesOrder();

		//Enter "Description"
		salesOrdersPage.setDescription(description);

		//Enter 5-digit ZIP/Postal Code
		salesOrdersPage.setZipCode(zipcode);

		//Enter "Street"
		salesOrdersPage.setStreet(street);

		//Enter "City"
		salesOrdersPage.setCity(city);

		//Enter "State"
		salesOrdersPage.setState(state);

		//Enter "County"
		salesOrdersPage.setCounty(county);

		//Click on "Ok" button
		salesOrdersPage.clickOk();

		//Validation of Error for incorrect address
		String newAddressErrorMessage = salesOrdersPage.getErrorMessage();
		assertTrue(newAddressErrorMessage.contains(
			"No tax areas were found during the lookup"), "Error Message: "+newAddressErrorMessage);
		salesOrdersPage.clickOnCancel();

	}

	/** @TestCase CD365-275
	 * @Description - Validate Invalid Address Continues Tax Calculation with Address Cleansing Off
	 * @Author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validateInvalidAddressTaxCalculationWithAddressCleansingOffTest(){
		String itemNumber = "1000";
		String quantity = "30";
		String unitPrice = "2450";
		String site = "2";
		String wareHouse = "22";
		String street = "2473 Hackworth Road, #2";
		String city = "Philadelphia";
		String state = "DE";
		String customerCountry = "USA";
		String customerDescription = "No zip";
		String zipcode = "12345";
		Double expectedTaxAmount = 0.00;

		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();

		//Set Address Cleansing to off
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
		settingsPage.selectSettingsPage(addressValidation);
		settingsPage.toggleAddressCleansingButton(false);
		addressCleanse = false;
		homePage = settingsPage.navigateToDashboardPage();

		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		allSalesOrdersPage.openNewSalesOrder();

		//Enter "Customer account" and sales order item
		allSalesOrdersPage.setCustomerAccount(defaultCustomerAccount);
		allSalesOrdersPage.clickOkBTN();
		DFinanceAllSalesOrdersSecondPage allSalesOrdersSecondPage = new DFinanceAllSalesOrdersSecondPage(driver);
		String salesOrderNumber = allSalesOrdersPage.getSalesOrderNumber();
		allSalesOrdersSecondPage.fillFirstItemsInfo(itemNumber, site, wareHouse, quantity, unitPrice, 0);

		//Click on the "Line Details Tab"
		allSalesOrdersPage.updateAddress();
		allSalesOrdersPage.setDescription(customerDescription);
		allSalesOrdersPage.setCountry(customerCountry);
		allSalesOrdersPage.setZipCode(zipcode);
		allSalesOrdersPage.setStreet(street);
		allSalesOrdersPage.setCity(city);
		allSalesOrdersPage.setState(state);
		allSalesOrdersPage.clickOk();

		//Navigate to Sales Tax
		allSalesOrdersPage.clickOnTab(allSalesOrdersPage.sellTabName);
		allSalesOrdersPage.openSalesTaxCalculation();
		String calculatedSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getCalculatedSalesTaxAmount();
		assertTrue(Double.parseDouble(calculatedSalesTaxAmount) == expectedTaxAmount,
				assertionTotalCalculatedSalesTaxAmount);
		String actualSalesTaxAmount = allSalesOrdersPage.salesTaxCalculator.getActualSalesTaxAmount();
		assertTrue(Double.parseDouble(actualSalesTaxAmount) == expectedTaxAmount,
				assertionTotalActualSalesTaxAmount);
		allSalesOrdersPage.salesTaxCalculator.closeSalesTaxCalculation();
	}
}