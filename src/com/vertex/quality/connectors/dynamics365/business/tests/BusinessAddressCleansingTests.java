package com.vertex.quality.connectors.dynamics365.business.tests;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessCompanyInfoDialog;
import com.vertex.quality.connectors.dynamics365.business.enums.BusinessMainMenuNavTabs;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessSalesBasePage;
import com.vertex.quality.connectors.dynamics365.business.tests.base.BusinessBaseTest;
import com.vertex.quality.connectors.salesforce.TestRerun;
import com.vertex.quality.connectors.salesforce.TestRerunListener;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * contains all the test cases for address cleansing
 *
 * @author osabha, cgajes, bhikshapathi
 */
@Listeners(TestRerunListener.class)
public class BusinessAddressCleansingTests extends BusinessBaseTest {
	BusinessAdminHomePage homePage;
	String expectedAddValidPopupMessage = "Vertex address validation requires the following information: \n" +
			"- Street Address, \n" +
			"- Zip Code, \n" +
			"- Country Code (must be set to US)";
	boolean addressCleanse = true;

	@BeforeMethod(alwaysRun = true)
	public void setupTest( )
	{
		addressCleanse = true;
	}

	@AfterMethod(alwaysRun = true)
	public void teardownTest( )
	{
		if (!addressCleanse) {
			homePage.navigateToVertexAdminPage();
			BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
			adminPage.updateAddressCleansingOn();
			adminPage.clickBackAndSaveArrow();
		}
	}

	/**
	 * CDBC-639
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void administrativeOriginWithNoZipReturnsValidAddressTest() {
		String expectedZipCode = "19015";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessManualSetupPage manual_setup = homePage.searchForAndNavigateToManualSetupPage();
		BusinessCompanyInfoDialog companyInfoDialog = manual_setup.searchingForCompany();

		companyInfoDialog.enterWrongZip();
		BusinessManualSetupPage manual_setup1 = companyInfoDialog.closeDialog();
		BusinessCompanyInfoDialog companyInfoDialog1 = manual_setup1.searchingForCompany();
		String actualZipCode = companyInfoDialog1.getZipCodeFromField();

		assertEquals(actualZipCode, expectedZipCode);
	}

	/**
	 * CDBC-1010
	 * Creates a customer with a zip code missing the final 4 digits,
	 * verify the last 4 digits are appended when customer saved
	 * Deletes customer after address is verified
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Smoke"}, retryAnalyzer = TestRerun.class)
	public void createCustomerZipCodeAddressCleansingTest() {
		String customerName = "Naomi Nagata";
		String vertexCustomerClass = "CLASS_TEST";
		String addressLineOne = "2955 Market St";
		String city = "Philadelphia";
		String state = "PA";
		String shortZip = "19104";
		String country = "US";
		String taxAreaCode = "VERTEX";

		String expectedZipCode = "19104-2828";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();

		BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);
		customerCardPage.clickNew();
		customerCardPage.selectBTOBCustomer();

		customerCardPage.enterCustomerName(customerName);
		customerCardPage.enterVertexCustomerCode(vertexCustomerClass);
		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);

		customerCardPage.expandInvoicingSection();
		customerCardPage.enterTaxAreaCode(taxAreaCode);

		customerCardPage.toggleReadOnlyMode();

		String actualZipCode = customerCardPage.getAddressZip();
		assertEquals(actualZipCode, expectedZipCode);
		customerCardPage.deleteDocument();
	}

	/**
	 * CDBC-650
	 * Validate Addresses With Yes Confidence Indicator Setting,
	 * select return the highest % - YES  which is 80%,
	 * return the highest % - NO
	 * return multiple options and select the lowest
	 *
	 * @author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression_Special"}, retryAnalyzer = TestRerun.class)
	public void ValidateAddressesWithYesConfidenceIndicatorSettingTest() {
		String quantity = "1";
		String addressLineOne = "2473 Hackworth Rd";
		String city = "Birmingham";
		String state = "AL";
		String shortZip = "99999";
		String country = "US";
		String itemNumber = "1896-S";
		String customerCode = "InvalidAddress";
		String locationCode = "WEST";

		BusinessSalesBasePage customerCard = new BusinessSalesBasePage(driver);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.updateConfidenceFactor("80");
		adminPage.clickBackAndSaveArrow();
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		customersListPage.searchAndOpenCustomer(customerCode);
		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);
		customerCard.clickBackAndSaveArrow();

		//Select light bulb and type vertex (this sends you to Vertex Admin) and validate the xml address cleanse uses the address above
		homePage.searchForAndNavigateToVertexAdminPage();
		//Address Cleanse XML Verification
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments("InvalidAddress");
		adminPage.filterxml("Address Cleanse Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<StreetAddress1>2473 Hackworth Rd</StreetAddress1>"), "Street Address Validation Failed");
		assertTrue(xmlStr.contains("<City>Birmingham</City>"), "City Validation Failed");
		Assert.assertTrue(xmlStr.contains("<MainDivision>AL</MainDivision>"), "State Validation Failed");
		Assert.assertTrue(xmlStr.contains("<SubDivision>Jefferson</SubDivision>"), "Subdivision Validation Failed");
		Assert.assertTrue(xmlStr.contains("<PostalCode>35214-1909</PostalCode>"), "Postal code Validation Failed");
		customerCard.clickBackAndSaveArrow();
		BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
		BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();

		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
		newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, locationCode, quantity, null, 1);
		newSalesOrder.exitExpandTable();
		String postedOrderNumOf80 = newSalesOrder.getCurrentSalesNumber();

		//Check the xml  for destination shipping
		customerCard.clickBackArrow();
		homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.filterDocuments(postedOrderNumOf80);
		adminPage.filterxml("Tax Calc Response");
		String xmlStr1 = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr1.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"88846\">ADAMSVILLE POLICE JURISDICTION</Jurisdiction>"), "Destination shipping Validation Failed");

		//Change Min Address Cleansing Confidence: 20%
		adminPage.updateConfidenceFactor("20");
		adminPage.clickBackAndSaveArrow();
		customersListPage.navigateToCustomers();
		customersListPage.searchForCustomer(customerCode);

		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);
		customerCard.clickBackAndSaveArrow();

		//Select light bulb and type vertex (this sends you to Vertex Admin) and validate the xml address cleanse uses the address above
		homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.filterDocuments("InvalidAddress");
		adminPage.filterxml("Address Cleanse Response");
		String xmlStrOf20 = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStrOf20.contains("<StreetAddress1>2473 Hackworth Rd</StreetAddress1>"), "Street Address Validation Failed");
		assertTrue(xmlStrOf20.contains("<City>Birmingham</City>"), "City Validation Failed");
		Assert.assertTrue(xmlStrOf20.contains("<MainDivision>AL</MainDivision>"), "State Validation Failed");
		Assert.assertTrue(xmlStrOf20.contains("<SubDivision>Jefferson</SubDivision>"), "Subdivision Validation Failed");
		Assert.assertTrue(xmlStrOf20.contains("<PostalCode>35214-1909</PostalCode>"), "Postal code Validation Failed");
		customerCard.clickBackAndSaveArrow();
		homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
		sales_orders.pageNavMenu.clickNew();

		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
		newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, locationCode, quantity, null, 1);
		newSalesOrder.exitExpandTable();
		String postedOrderNumOf20 = newSalesOrder.getOrderNumber();

		//Check the xml  for destination shipping
		homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.filterDocuments(postedOrderNumOf20);
		adminPage.filterxml("Tax Calc Response");
		String xmlStrOf20TCR = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStrOf20TCR.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"88846\">ADAMSVILLE POLICE JURISDICTION</Jurisdiction>"), "Destination shipping Validation Failed");

		//Change Min Address Cleansing Confidence: 18%
		adminPage.updateConfidenceFactor("18");
		adminPage.clickBackAndSaveArrow();
		adminPage.clickBackAndSaveArrow();
		customersListPage.navigateToCustomers();
		customersListPage.searchForCustomer(customerCode);

		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);

		//Select light bulb and type vertex (this sends you to Vertex Admin) and validate the xml address cleanse uses the address above
		homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.filterDocuments("InvalidAddress");
		adminPage.filterxml("Address Cleanse Response");
		String xmlStrOf18 = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStrOf18.contains("<StreetAddress1>2473 Hackworth Rd</StreetAddress1>"), "Street Address Validation Failed");
		assertTrue(xmlStrOf18.contains("<City>Birmingham</City>"), "City Validation Failed");
		Assert.assertTrue(xmlStrOf18.contains("<MainDivision>AL</MainDivision>"), "State Validation Failed");
		Assert.assertTrue(xmlStrOf18.contains("<SubDivision>Jefferson</SubDivision>"), "Subdivision Validation Failed");
		Assert.assertTrue(xmlStrOf18.contains("<PostalCode>35214-1909</PostalCode>"), "Postal code Validation Failed");
		customerCard.clickBackAndSaveArrow();
		customerCard.clickBackAndSaveArrow();
		homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
		sales_orders.pageNavMenu.clickNew();

		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
		newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, locationCode, quantity, null, 1);
		newSalesOrder.exitExpandTable();
		String postedOrderNumOf18 = newSalesOrder.getOrderNumber();

		//Check the xml  for destination shipping
		homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.filterDocuments(postedOrderNumOf18);
		adminPage.filterxml("Tax Calc Response");
		String xmlStrOf18TCR = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStrOf18TCR.contains("<Jurisdiction jurisdictionLevel=\"DISTRICT\" jurisdictionId=\"88846\">ADAMSVILLE POLICE JURISDICTION</Jurisdiction>"), "Destination shipping Validation Failed");
		adminPage.updateConfidenceFactor("80");
		customerCard.clickBackAndSaveArrow();

		//Post Update customer
		adminPage.clickBackAndSaveArrow();
		customersListPage.navigateToCustomers();
		customersListPage.searchForCustomer(customerCode);

		fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19101", "US");
		customerCard.clickBackAndSaveArrow();
	}

	/**
	 * CDBC-668
	 * Validate addresses with continue calc with Continue to Calc with invalid address cleansing OFF
	 *
	 * @author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void invalidAddressCleansingOFFTest() {
		String quantity = "1";
		String addressLineOne = "Krishe Sapphire";
		String city = "Hyderabad";
		String state = "PA";
		String shortZip = "99999";
		String country = "US";
		String itemNumber = "1896-S";
		String locationCode = "WEST";
		String customerName = "Address CleansePA";

		BusinessSalesBasePage customerCard = new BusinessSalesBasePage(driver);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.updateAddressCleansingOff();
		addressCleanse = false;
		adminPage.clickBackAndSaveArrow();
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		customersListPage.searchAndOpenCustomer(customerName);

		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);
		customerCard.clickBackAndSaveArrow();

		//Post Admin Setup
		homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.updateAddressCleansingOn();
		adminPage.clickBackAndSaveArrow();
		addressCleanse = true;

		//Post Update customer
		customersListPage.navigateToCustomers();
		customersListPage.searchForCustomer(customerName);

		fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US");
		customerCard.clickBackAndSaveArrow();
	}

	/**
	 * CDBC-682
	 * Validate Destination Shipping with invalid address returns error
	 *
	 * @author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
	public void destinationShippingWithInvalidAddressTest() {
		String quantity = "1";
		String addressLineOne = "Krishe Sapphire";
		String city = "Hyderabad";
		String state = "PA";
		String shortZip = "99999";
		String country = "US";
		String itemNumber = "1896-S";
		String customerCode = "Incorrect Address";
		String locationCode = "WEST";
		BusinessSalesBasePage customerCard = new BusinessSalesBasePage(driver);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.updateAddressCleansingOn();
		addressCleanse = true;
		adminPage.clickBackAndSaveArrow();
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		customersListPage.searchAndOpenCustomer(customerCode);
		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);
		customerCard.clickBackArrow();
		String text = customerCard.getNoTaxAreasText();
		assertTrue(text.contains("No tax areas were found during the lookup. The address fields are inconsistent for the specified asOfDate."));

		//Check the xml  for destination shipping
		adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.openXmlLogCategory();
		adminPage.filterDocuments("Incorrect Address");
		adminPage.filterxml("Address Cleanse Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("No tax areas were found during the lookup"), "Destination shipping Validation Failed");
		assertTrue(xmlStr.contains("The address fields are inconsistent for the specified asOfDate"), "Destination shipping Validation Failed");
		customerCard.clickBackAndSaveArrow();
		BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
		BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
		newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, locationCode, quantity, null, 1);
		newSalesOrder.getNoTaxAreasText();
		newSalesOrder.exitExpandTable();
		newSalesOrder.clickBackAndSaveArrow();
		newSalesOrder.dialogBoxClickYes();
		//Post Update customer
		homePage.mainNavMenu.goToNavTab(BusinessMainMenuNavTabs.SALES.value);
		customersListPage.navigateToCustomers();
		customersListPage.searchForCustomer(customerCode);
		fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US");
		customerCard.clickBackAndSaveArrow();
	}

	/**
	 * CDBC-679
	 * Validate addresses with address cleansing OFF
	 *
	 * @author bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void validateAddressWithAddressCleansingOFFTest() {
		String addressLineOne = "2610 E Colonial";
		String city = "Boothwyn";
		String state = "PA";
		String shortZip = "19101";
		String country = "US";
		String customerCode = "Address CleansePA";
		String location = "PALOC";

		BusinessSalesBasePage customerCard = new BusinessSalesBasePage(driver);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = homePage.navigateToVertexAdminPage();
		adminPage.updateAddressCleansingOff();
		adminPage.clickBackAndSaveArrow();
		addressCleanse = false;
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		customersListPage.searchAndOpenCustomer(customerCode);

		fillInCustomerAddressInfo(addressLineOne, null, city, state, shortZip, country);
		customerCard.clickBackAndSaveArrow();
		// Check No XML Vertex Admin
		homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.openXmlLogCategory();
		String xmlName = adminPage.checkNoXml();
		assertNotEquals(xmlName, "Address Cleanse Response");
		adminPage.clickBackAndSaveArrow();
		customersListPage.clearCustomerField();
		customersListPage.searchAndOpenCustomer(customerCode);

		fillInCustomerAddressInfo("2955 Market St. apt 2", null, "Philadelphia", "PA", "19104", "US");
		customerCard.clickBackAndSaveArrow();

		// Company - Administrative Origin
		BusinessManualSetupPage manual_setup = homePage.searchForAndNavigateToManualSetupPage();
		manual_setup.searchForCompany("2");
		fillInCustomerAddressInfo("2610 E Colonial Dr", null, "Boothwyn", "PA", "19104", "US");
		adminPage.clickBackAndSaveArrow();
		// Check No XML Vertex Admin
		homePage.searchForAndNavigateToVertexAdminPage();
		String companyXml = adminPage.checkNoXml();
		assertNotEquals(companyXml, "Address Cleanse Response");
		adminPage.clickBackAndSaveArrow();
		manual_setup.clearSearchField();
		manual_setup.searchForCompany("1");
		fillInCustomerAddressInfo("400 Simpson Dr", null, " Chester Springs", "PA", "19425-9546", "US");
		adminPage.clickBackAndSaveArrow();
		manual_setup.clearSearchField();
		BusinessCompanyInfoDialog locationInfoDialog = manual_setup.navigateToLocations();
		locationInfoDialog.updateBackLocation(location);
		fillInCustomerAddressInfo("2610 E Colonial Dr", null, "Boothwyn", "PA", "19101", "US");
		adminPage.clickBackAndSaveArrow();
		// Check No XML Vertex Admin
		homePage.searchForAndNavigateToVertexAdminPage();
		String locationXml = adminPage.checkNoXml();
		assertNotEquals(locationXml, "Address Cleanse Response");
		adminPage.clickBackAndSaveArrow();
		manual_setup.clearSearchField();
		locationInfoDialog.updateBackLocation(location);
		fillInCustomerAddressInfo("2301 Renaissance Bld", null, "King of Prussia", "PA", "19406", "US");
		adminPage.clickBackAndSaveArrow();
		//Post Admin Setup
		homePage.searchForAndNavigateToVertexAdminPage();
		adminPage.updateAddressCleansingOn();
		adminPage.clickBackAndSaveArrow();

	}

	/**
	 * CDBC-686
	 * Validate Administrative Origin with invalid address returns error
	 *
	 * @authour bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
	public void validateAdministrativeOriginWithInvalidAddressReturnsErrorTest() {
		String customerCode = "AddressCleansePA";
		String itemNumber = "1896-S";
		String itemNumber2 = "1908-S";
		String quantity = "1";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = new BusinessVertexAdminPage(driver);
		BusinessManualSetupPage manual_setup = homePage.searchForAndNavigateToManualSetupPage();
		BusinessCompanyInfoDialog companyInfoDialog = manual_setup.searchForCompany("1");
		fillInCustomerAddressInfo("2610 E Colonial Dr", null, "Chester", "PA", "19720", "US");
		companyInfoDialog.closeDialog();
		companyInfoDialog.closeConfidenceIndicatorPopUp();
		adminPage.refreshPage();
		BusinessSalesOrderListPage sales_orders = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SALES.value, "Sales Orders");
		BusinessSalesOrderPage newSalesOrder = sales_orders.pageNavMenu.clickNew();
		fillInSalesOrderGeneralInfo(newSalesOrder, customerCode);
		newSalesOrder.expandTable();
		fillInItemsTableInfo("Item", itemNumber, null, "MAIN", quantity, null, 1);
		newSalesOrder.activateRow(2);
		fillInItemsTableInfo("Item", itemNumber2, null, "EAST", "1", "99", 2);
		newSalesOrder.activateRow(3);
		newSalesOrder.exitExpandTable();
		adminPage.clickBackAndSaveArrow();
		adminPage.navigateBackToHome();
		// Verify system returns error
		manual_setup = homePage.searchForAndNavigateToManualSetupPage();
		companyInfoDialog = manual_setup.searchForCompany("2");
		fillInCustomerAddressInfo("400 Simpson Dr", null, "Chester Springs", "PA", "19425-9546", "US");
		companyInfoDialog.closeDialog();
	}

	/**
	 * CDBC-678
	 * Validate Physical Origin with invalid address returns error
	 *
	 * @authour bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
	public void validatePhysicalOriginWithInvalidAddressReturnsErrorTest() {
		String location = "PALOC";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessVertexAdminPage adminPage = new BusinessVertexAdminPage(driver);
		BusinessManualSetupPage manual_setup = homePage.searchForAndNavigateToManualSetupPage();
		BusinessCompanyInfoDialog locationInfoDialog = manual_setup.navigatingToLocations();
		locationInfoDialog.searchForLocation(location);
		fillInCustomerAddressInfo("59 n ter", null, "King Of Prussia", "PA", "19406-2772", "US");
		locationInfoDialog.closeLocationCard();
		String errorText=locationInfoDialog.getDialogBoxText();
		assertTrue(errorText.contains("The address cannot be verified. Some of the address information may be missing or incorrect, or it does not exist in the USPS database. This may result in incorrect tax being applied to transactions using this address. Do you want to save the address as entered?"), "Error message Validation Failed");
		locationInfoDialog.clickNoOnPopup();
		locationInfoDialog.searchForLocation(location);
		String expectedZip=locationInfoDialog.getZipCode();
		assertTrue(expectedZip.contains("19406-2772"), "Expected Zip Code Validation Failed");
	}

	/**
	 * CDBC-676
	 * Validate Physical Origin with no zip address returns error
	 *
	 * @authour bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Regression"}, retryAnalyzer = TestRerun.class)
	public void validatePhysicalOriginWithNoZipReturnsValidAddressTest() {
		String location = "PALOC";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessManualSetupPage manual_setup = homePage.searchForAndNavigateToManualSetupPage();
		BusinessCompanyInfoDialog locationInfoDialog = manual_setup.navigatingToLocations();
		locationInfoDialog.searchForLocation(location);
		locationInfoDialog.enterWrongZip();
		locationInfoDialog.closeLocationCard();
		locationInfoDialog.searchForLocation(location);
		String expectedZip=locationInfoDialog.getZipCode();
		assertTrue(expectedZip.contains("19406-2772"), "Expected Zip Code Validation Failed");
		//Select light bulb and type vertex (this sends you to Vertex Admin)
		BusinessVertexAdminPage adminPage = homePage.searchForAndNavigateToVertexAdminPage();
		// Check XML
		adminPage.openXmlLogCategory();
		//Tax Calc Request and Response
		adminPage.filterDocuments("PA location");
		adminPage.filterxml("Address Cleanse Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains(" <StreetAddress1>2301 Renaissance Blvd</StreetAddress1>"), "Updated Street address Validation Failed");
		assertTrue(xmlStr.contains(" <City>King Of Prussia</City>"), "Updated City Validation Failed");
		assertTrue(xmlStr.contains(" <PostalCode>19406-2772</PostalCode>"), "Updated postal code Validation Failed");
		adminPage.clickBackAndSaveArrow();


	}

	/**
	 * CDBC-1011
	 * Verify Error message while updating company address with In valid address
	 *
	 * @authour bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void validateErrorWithInvalidAddressOfCompanyTest() {

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessManualSetupPage manual_setup = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SETUP_EXTENSIONS.value, "Manual Setup");
		BusinessCompanyInfoDialog companyInfoDialog = manual_setup.searchingForCompany();
		fillInCustomerAddressInfo("Krishe Sapphire", null, "Hyderabad", "PA", "99999", "US");
		companyInfoDialog.closeDialog();
		// Verify Error
		String errorText=companyInfoDialog.getTheErrorText();
		assertTrue(errorText.contains("No tax areas were found during the lookup. The address fields are inconsistent for the specified asOfDate. (Street Information=KRISHE SAPPHIRE, Street Information 2=null, Postal Code=99999, City=HYDERABAD, Sub Division=null, Main Division=PA, Country=USA,"), "Error message Validation Failed");
		// revert Back the changes
		companyInfoDialog.revertBackTheChange();
	}
	/**
	 * CDBC-1012
	 * Verify Error message while updating location address with In valid address
	 *
	 * @authour bhikshapathi
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void validateErrorWithInvalidAddressOfLocationTest() {

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);

		BusinessManualSetupPage manual_setup = homePage.mainNavMenu.goToSubNavTab(
				BusinessMainMenuNavTabs.SETUP_EXTENSIONS.value, "Manual Setup");
		BusinessCompanyInfoDialog locationInfoDialog = manual_setup.navigatingToLocations();
		locationInfoDialog.searchForLocation("PALOC");
		fillInCustomerAddressInfo("Krishe Sapphire", null, "Hyderabad", "PA", "99999", "US");
		locationInfoDialog.closeLocationCard();
		// Verify Error
        String errorText=locationInfoDialog.getTheErrorText();
		assertTrue(errorText.contains("No tax areas were found during the lookup. The address fields are inconsistent for the specified asOfDate. (Street Information=KRISHE SAPPHIRE, Street Information 2=null, Postal Code=99999, City=HYDERABAD, Sub Division=null, Main Division=PA, Country=USA,"), "Error message Validation Failed");
		// revert Back the changes
		locationInfoDialog.revertBackTheChange();
	}

	/**
	 * CDBC-1013
	 * This Test Validates Address cleansing popup message
	 */
	@Test(alwaysRun = true, groups = {"D365_Business_Central_Special"}, retryAnalyzer = TestRerun.class)
	public void validateAddressValidationPopupTest()
	{
		String customerName = "AddressValidation Popup";
		String taxAreaCode = "VERTEX";

		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		BusinessCustomerCardPage customerCardPage = customersListPage.clickNewBusinessToBusinessCustomer();

		customerCardPage.enterCustomerName(customerName);
		customerCardPage.expandInvoicingSection();
		customerCardPage.enterTaxAreaCode(taxAreaCode);
		customerCardPage.clickBackArrow();
		assertEquals(customerCardPage.getAddressValidPopupMessage(),expectedAddValidPopupMessage);

	}

	/**
	 * @TestCase CDBC-1405
	 * @Description - Verify Change Of Zip Code For Customer Address In Ship-To Address And Request/Response
	 * @Author Mario Saint-Fleur
	 * */
	@Test(groups = {"D365_Business_Central_Sales_Regression"})
	public void verifyChangeOfZipCodeForCustomerAddressShipTo() {
		String expectedZipCode = "33162-3683";
		String wrongZipCode = "12345";
		String customerCode = "Trey Research";

		BusinessSalesBasePage customerCard = new BusinessSalesBasePage(driver);
		BusinessAdminHomePage homePage = new BusinessAdminHomePage(driver);
		BusinessCustomerCardPage customerCardPage = new BusinessCustomerCardPage(driver);

		BusinessCustomersListPage customersListPage = homePage.navigateToCustomersListPage();
		customersListPage.searchAndOpenCustomer(customerCode);
		customerCardPage.navigateToShipToAddressCustomerCode("MIAMI");

		//Add and verify zip code
		customerCardPage.enterZipCode(wrongZipCode);
		customerCardPage.toggleReadOnlyMode();
		String actualZipCode = customerCardPage.getZipCodeShipToAddress();
		assertTrue(actualZipCode.contains(expectedZipCode), "Address zip code has not been cleansed. Expected: " + actualZipCode + " but found: " + wrongZipCode);
		customerCard.clickBackAndSaveArrow();

		//Verify zip code is correct
		homePage.searchForAndNavigateToVertexAdminPage();
		BusinessVertexAdminPage adminPage = new BusinessVertexAdminPage(driver);
		adminPage.openXmlLogCategory();
		adminPage.filterxml("Address Cleanse Response");
		String xmlStr = adminPage.clickOnFirstLinkAndGetTheXml();
		assertTrue(xmlStr.contains("<PostalCode>33162-3683</PostalCode>"), "Address zip code has not been cleansed. Expected: " + actualZipCode + " but found: " + expectedZipCode);
	}
	@BeforeMethod(alwaysRun = true)
	public void setUpBusinessMgmt(){
		String role="Business Manager";
		homePage = new BusinessAdminHomePage(driver);
		String verifyPage=homePage.verifyHomepageHeader();
		if(!verifyPage.contains(role)){

			//navigate to select role as Business Manager
			homePage.selectSettings();
			homePage.navigateToManagerInSettings(role);
		}
	}
}