package com.vertex.quality.connectors.dynamics365.finance.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceTaxValidation;
import com.vertex.quality.connectors.dynamics365.finance.enums.*;
import com.vertex.quality.connectors.dynamics365.finance.pages.*;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.apache.commons.math3.analysis.function.Add;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
@Listeners(TestRerunListener.class)
public class AddressCleansingAllAddressesTests extends DFinanceBaseTest
{
	/**
	 * Address Cleansing - Always list all addresses
	 * To verify the "Address Cleansing" functionality, when "Always list all addresses" is set to
	 * "Yes".
	 * Also, to verify the use of "Edit Street" option on Vertex Cleansed Addresses as part of
	 * Vertex Validation Service
	 * Acceptable confidence level = 75
	 * Always list all address results = Yes
	 * CD365-276
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void AddressCleansingAlwaysListAllAddressesTest( )
	{
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage salesOrdersPage;
		DFinanceTaxValidation dFinanceTaxValidation = new DFinanceTaxValidation();

		//================Data Declaration ===========================
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();
		String accountReceivable = DFinanceLeftMenuNames.ACCOUNTS_RECEIVABLE.getData();
		String customers = DFinanceLeftMenuNames.CUSTOMERS.getData();
		String allCustomers = DFinanceLeftMenuNames.ALL_CUSTOMERS.getData();
		String orders = DFinanceLeftMenuNames.ORDERS.getData();
		String allSalesOrders = DFinanceLeftMenuNames.ALL_SALES_ORDERS.getData();
		String customerAccount = defaultCustomerAccount;
		String description = DFinanceConstantDataResource.DESCRIPTION.getData();
		String state = DFinanceConstantDataResource.STATE.getData();

		//================script implementation========================
		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);

		//Click on "Address Validation" from the left pane
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level  and Set Always list all address results
		settingsPage.setConfidenceAndResults("75", true);
		settingsPage.toggleAddressCleansingButton(true);
		settingsPage.clickOnSaveButton();
		homePage = settingsPage.navigateToDashboardPage();

		//Navigate to  All Sales Orders page
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Click on "+" icon available at the "Delivery address" field
		salesOrdersPage = homePage.newAddress(customerAccount);
		salesOrdersPage.newAddressSalesOrder();

		//Enter "Description"
		salesOrdersPage.setDescription(description);

		//Enter 5-digit ZIP/Postal Code
		salesOrdersPage.setZipCode(Address.Spokane.zip5);

		//Enter "Street"
		salesOrdersPage.setStreet(Address.Spokane.addressLine1);

		//Click on "Ok" button
		salesOrdersPage.clickOk();

		//Check the presence of "Edit Street" option above "Recommended Addresses" table on the address pop-up
		boolean editStreetStatus = salesOrdersPage.verifyEditStreet();
		assertTrue(editStreetStatus == true, "'Edit Street' option not present");

		//Delete the #303 from Street Line-1 and enter APT#303 in Street line-2
		salesOrdersPage.clickOnEditStreet();
		salesOrdersPage.updateStreetLine1(Address.Spokane.cleansedAddressLine1);
		salesOrdersPage.updateStreetLine2(Address.Spokane.cleansedAddressLine2);

		//Click on "OK" button from the "Vertex Street Update" pop-up
		salesOrdersPage.clickOnOKButton();

		//Check the "Street Line-1"
		String streetLine1Value = salesOrdersPage.validateStreetLine1();
		dFinanceTaxValidation.textValidationWithStrings(streetLine1Value, Address.Spokane.cleansedAddressLine1, "Street Line1");

		//Check the "Street Line-2"
		String streetLine2Value = salesOrdersPage.validateStreetLine2();
		dFinanceTaxValidation.textValidationWithStrings(streetLine2Value, Address.Spokane.cleansedAddressLine2, "Street Line2");

		//Check the presence of confidence percentage for the addresses on the screen
		String confidenceValue = salesOrdersPage.verifyConfidenceValueStatus();
		dFinanceTaxValidation.textValidation(confidenceValue, "Confindence Value");

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();

		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.setConfidenceAndResults("75", true);

		//Click on the "Save"  button
		settingsPage.clickOnSaveButton();
	}


	/**
	 * Validate Physical Origin with invalid address returns error
	 * CD365-268
	 * @author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validatePhysicalOriginWithInvalidAddressTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;

		//================Data Declaration ===========================
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String setup = DFinanceLeftMenuNames.SETUP.getData();
		String vertex = DFinanceLeftMenuNames.VERTEX.getData();
		String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();

		String salesOrder = "002779";
		String zipcode = "99999";
		String street = "2473 Hackworth Road, #2";
		String city = "Chester";
		String customerDescription = "Incorrect Address";

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		//Click on "Address Validation" from the left pane
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.setConfidenceAndResults("75", true);
		settingsPage.clickOnSaveButton();
		homePage = settingsPage.navigateToDashboardPage();

		//Navigate to  All Sales Order page
		DFinanceAllSalesOrdersPage allSalesOrdersPage = homePage.navigateToPage(
				DFinanceLeftMenuModule.ACCOUNTS_RECEIVABLE, DFinanceModulePanelCategory.ORDERS,
				DFinanceModulePanelLink.ALL_SALES_ORDERS);

		//Search for a current customer
		allSalesOrdersPage.searchCreatedSalesOrder(salesOrder);

		//Select the current customer
		allSalesOrdersPage.clickOnDisplayedSalesOrderNumber();

		//Click on the "Header Tab"
		allSalesOrdersPage.openHeaderTab();

		//Click "+ icon" to add a new address
		allSalesOrdersPage.newDeliveryAddressSalesOrder();

		//Enter "Customer Description"
		allSalesOrdersPage.setDescription(customerDescription);

		//Enter "Customer Country"
		allSalesOrdersPage.setCountry(Address.Birmingham.country.iso3code);

     	//Enter 5-digit ZIP/Postal Code
		allSalesOrdersPage.setZipCode(zipcode);

		//Enter "Street"
		allSalesOrdersPage.setStreet(street);

		//Enter "City"
		allSalesOrdersPage.setCity(city);

		//Enter "State"
		allSalesOrdersPage.setState(Address.Birmingham.state.abbreviation);

		//Click on "Ok" button
		allSalesOrdersPage.clickOk();

		//Get error message for no tax area being found
		System.out.println(allSalesOrdersPage.getErrorMessageNoTaxAreaFoundPopup());

		//Click the "Cancel" button
		allSalesOrdersPage.clickOnCancel();


	}

	/**
	 * @TestCase CD365-267
	 * @Description Validate Physical Origin with no Zip returns Valid Address
	 * @author Mario Saint-Fleur
	 */
	@Test(groups = { "FO_General_regression", "FO_ALL" }, retryAnalyzer = TestRerun.class)
	public void validatePhysicalOriginWithNoZipReturnsValidAddressTest(){
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		DFinanceSettingsPage settingsPage;
		DFinanceAllSalesOrdersPage allSalesOrdersPage = new DFinanceAllSalesOrdersPage(driver);

		//================Data Declaration ===========================
		String tax = DFinanceLeftMenuNames.TAX.getData();
		String VertexTaxParameters = DFinanceLeftMenuNames.VERTEX_TAX_PARAMETERS.getData();
		String addressValidation = DFinanceVertexTaxParametersLeftMenuNames.ADDRESS_VALIDATION.getData();
		final String setup = DFinanceLeftMenuNames.SETUP.getData();
		final String vertex = DFinanceLeftMenuNames.VERTEX.getData();

		String street = "2473 Hackworth Road, #2";
		String customerDescription = "No zip";
		String expectedZipCode = "35214-1909";

		//Navigate to Vertex Settings page
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);

		//Click on "Address Validation" from the left pane
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.setConfidenceAndResults("90", true);
		settingsPage.clickOnSaveButton();
		homePage = settingsPage.navigateToDashboardPage();

		//Navigate to Legal Entities Page
		homePage.navigateToPage(
				DFinanceLeftMenuModule.ORGANIZATION_ADMINISTRATION, DFinanceModulePanelCategory.ORGANIZATIONS,
				DFinanceModulePanelLink.LEGAL_ENTITIES);

		//Click on the "+ Add" button
		allSalesOrdersPage.clickAddNewAddress();

		//Enter "Customer Description"
		allSalesOrdersPage.setDescription(customerDescription);

		//Enter "Customer Country"
		allSalesOrdersPage.setCountry(Address.Birmingham.country.iso3code);

		//Enter "Street"
		allSalesOrdersPage.setStreet(street);

		//Enter "City"
		allSalesOrdersPage.setCity(Address.Birmingham.city);

		//Enter "State"
		allSalesOrdersPage.setState(Address.Birmingham.state.abbreviation);

		//Click on "Ok" button
		allSalesOrdersPage.clickOk();

		//Get the actual zip code
		String actualZipCode = allSalesOrdersPage.getZipCode();

		//Verify the actual zip code is cleansed value
		assertTrue(actualZipCode.equals(expectedZipCode),
				"Actual ZipCode is not the same as the expected ZipCode");

		//Cancel Adding Address
		allSalesOrdersPage.clickOnCancelVertexAddressValidation();
		allSalesOrdersPage.clickOnCancel();

		//Navigate to Vertex Tax Parameters
		homePage.clickOnNavigationPanel();
		homePage.selectLeftMenuModule(tax);
		homePage.collapseAll();
		homePage.expandModuleCategory(setup);
		homePage.expandModuleCategory(vertex);
		settingsPage = homePage.selectModuleTabLink(VertexTaxParameters);
		settingsPage.selectSettingsPage(addressValidation);

		//Set the Acceptable confidence level and Set Always list all address results
		settingsPage.setConfidenceAndResults("75", true);
	}
}
