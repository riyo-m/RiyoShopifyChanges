package com.vertex.quality.connectors.accumatica.tests.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaCreditVerification;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.*;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * This class Acumatica Base test for basic actions/methods for login application
 *
 * @author saidulu kodadala
 */
@Test(groups = { "acumatica" })
public abstract class AcumaticaBaseTest extends VertexUIBaseTest
{
	public String DEFAULT_SHIP_VIA = "UPS";
	public String DEFAULT_BRANCH = "New York";
	public String DEFAULT_COMPANY_CODE = "NewYork01";
	public String defaultCustomerClassId = "DEFAULT";
	public String defaultCustomerClass = "DEFAULT - Customer within NY Area";
	public final String defaultEmail = "ConnectorTestAutomation@vertexinc.com";

	public static ReadProperties readEnvUrls;
	public static ReadProperties readCredentials;
	public static ReadProperties readConfig;

	public String TEST_CREDENTIALS_FILE_PATH;
	public String ENV_PROP_FILE_PATH;
	public String CONFIG_DETAILS_FILE_PATH;

	public String ACUMATICA_USERNAME = null;
	public String ACUMATICA_PASSWORD = null;
	public String ACUMATICA_URL = null;

	public String TAX_AREA_LOOKUP_ENDPOINT_URL = null;
	public String TAX_CALCULATION_ENDPOINT_URL = null;
	public String VERTEX_USERNAME = null;
	public String VERTEX_PASSWORD = null;
	public String TRUSTED_ID = null;
	public String VERTEX_TAXZONEID = null;
	public String email = null;

	public AcumaticaPostSignOnPage postSignOn;
	public AcumaticaCommonPage common;
	public AcumaticaVertexSetupPage vertexSetup;
	public AcumaticaEnableDisableFeaturesPage enabledDisableFeatures;
	public AcumaticaCustomersPage customers;
	public AcumaticaSalesOrdersPage salesOrders;
	public AcumaticaSalesOrdersValidationPage salesOrdersValidation;
	public AcumaticaItemClassesPage itemClass;
	public AcumaticaStockItemsPage stockItems;
	public AcumaticaCustomerClassesPage customerClass;
	public AcumaticaDiscountCodesPage discountCodes;
	public AcumaticaCustomizationProjectsPage customizationProjects;
	public AcumaticaCashSalesPage cashSales;
	public AcumaticaBranchesPage branches;
	public AcumaticaBusinessAccountsPage businessAccount;
	public AcumaticaContactsPage contacts;
	public AcumaticaOpportunitiesPage opportunities;
	public AcumaticaTransactionsPage transactions;
	public AcumaticaInvoicesPage invoice;
	public AcumaticaInvoicesAndMemosPage invoiceAndMemo;
	public AcumaticaLeadsPage leads;
	public AcumaticaAccountLocationsPage accountLocations;
	public AcumaticaCustomerLocationsPage customerLocations;
	public AcumaticaShipmentsPage shipment;

	/***
	 * Get the data from resources folder -> property file
	 */
	@BeforeClass(alwaysRun = true)
	public void initSetup( )
	{
		ENV_PROP_FILE_PATH = ".\\resources\\properties\\EnvironmentURL.properties";
		TEST_CREDENTIALS_FILE_PATH = ".\\resources\\properties\\TestCredentials.properties";
		CONFIG_DETAILS_FILE_PATH = ".\\resources\\properties\\ConfigurationSettings.properties";

		if ( new File(TEST_CREDENTIALS_FILE_PATH).exists() )
		{
			readCredentials = new ReadProperties(TEST_CREDENTIALS_FILE_PATH);
		}
		else
		{
			VertexLogger.log("Test Credentials properties file is not found", VertexLogLevel.ERROR,
				AcumaticaBaseTest.class);
		}

		if ( new File(ENV_PROP_FILE_PATH).exists() )
		{
			readEnvUrls = new ReadProperties(ENV_PROP_FILE_PATH);
		}
		else
		{
			VertexLogger.log("Environment details properties file is not found", VertexLogLevel.ERROR,
				AcumaticaBaseTest.class);
		}

		if ( new File(CONFIG_DETAILS_FILE_PATH).exists() )
		{
			readConfig = new ReadProperties(CONFIG_DETAILS_FILE_PATH);
		}
		else
		{
			VertexLogger.log("Test Config properties file is not found", VertexLogLevel.ERROR, AcumaticaBaseTest.class);
		}

		ACUMATICA_USERNAME = readCredentials.getProperty("TEST.CREDENTIALS.ACUMATICA.USERNAME");
		ACUMATICA_PASSWORD = readCredentials.getProperty("TEST.CREDENTIALS.ACUMATICA.PASSWORD");
		ACUMATICA_URL = readEnvUrls.getProperty("TEST.ENV.ACUMATICA.URL");
		TAX_AREA_LOOKUP_ENDPOINT_URL = readConfig.getProperty("TEST.VERTEX.ACUMATICA.TAX_AREA_LOOKUP_ENDPOINT_URL");
		TAX_CALCULATION_ENDPOINT_URL = readConfig.getProperty("TEST.VERTEX.ACUMATICA.TAX_CALCULATION_ENDPOINT_URL");
		VERTEX_USERNAME = readConfig.getProperty("TEST.VERTEX.ACUMATICA.USERNAME");
		VERTEX_PASSWORD = readConfig.getProperty("TEST.VERTEX.ACUMATICA.PASSWORD");
		TRUSTED_ID = readConfig.getProperty("TEST.VERTEX.ACUMATICA.TRUSTED_ID");
		VERTEX_TAXZONEID = readConfig.getProperty("TEST.VERTEX.ACUMATICA.VERTEX_TAX_ZONE_ID");

		email = readConfig.getProperty("TEST.VERTEX.AUTOMATION.EMAIL");
	}

	protected AcumaticaPostSignOnPage commonSetup( )
	{
		AcumaticaPostSignOnPage homePage = null;

		common = new AcumaticaCommonPage(driver);
		vertexSetup = new AcumaticaVertexSetupPage(driver);
		enabledDisableFeatures = new AcumaticaEnableDisableFeaturesPage(driver);
		customers = new AcumaticaCustomersPage(driver);
		salesOrders = new AcumaticaSalesOrdersPage(driver);
		salesOrdersValidation = new AcumaticaSalesOrdersValidationPage(driver);
		itemClass = new AcumaticaItemClassesPage(driver);
		stockItems = new AcumaticaStockItemsPage(driver);
		customerClass = new AcumaticaCustomerClassesPage(driver);
		discountCodes = new AcumaticaDiscountCodesPage(driver);
		customizationProjects = new AcumaticaCustomizationProjectsPage(driver);
		cashSales = new AcumaticaCashSalesPage(driver);
		branches = new AcumaticaBranchesPage(driver);
		businessAccount = new AcumaticaBusinessAccountsPage(driver);
		contacts = new AcumaticaContactsPage(driver);
		opportunities = new AcumaticaOpportunitiesPage(driver);
		transactions = new AcumaticaTransactionsPage(driver);
		invoice = new AcumaticaInvoicesPage(driver);
		invoiceAndMemo = new AcumaticaInvoicesAndMemosPage(driver);
		leads = new AcumaticaLeadsPage(driver);
		accountLocations = new AcumaticaAccountLocationsPage(driver);
		customerLocations = new AcumaticaCustomerLocationsPage(driver);
		shipment = new AcumaticaShipmentsPage(driver);

		vertexSetup.TAX_AREA_LOOKUP_ENDPOINT_URL = TAX_AREA_LOOKUP_ENDPOINT_URL;
		vertexSetup.TAX_CALCULATION_ENDPOINT_URL = TAX_CALCULATION_ENDPOINT_URL;
		vertexSetup.USERNAME = VERTEX_USERNAME;
		vertexSetup.PASSWORD = VERTEX_PASSWORD;
		vertexSetup.TRUSTED_ID = TRUSTED_ID;
		vertexSetup.VERTEX_TAXZONEID = VERTEX_TAXZONEID;

		homePage = logInAsAdminUser();

		return homePage;
	}

	/**
	 * Launch acumatica login page
	 */
	protected AcumaticaLoginPage launchLoginPage( )
	{
		AcumaticaLoginPage loginPage = null;

		driver.get(ACUMATICA_URL);

		loginPage = new AcumaticaLoginPage(driver);

		return loginPage;
	}

	/**
	 * Common method for enter valid user name and password ,login into acumatica
	 * application.
	 */
	protected AcumaticaPostSignOnPage logInAsAdminUser( )
	{
		AcumaticaPostSignOnPage homePage = null;

		AcumaticaLoginPage loginPage = launchLoginPage();
		homePage = logInAsUser(loginPage, ACUMATICA_USERNAME, ACUMATICA_PASSWORD, "Vertex");

		return homePage;
	}

	/**
	 * opens acumatica & performs the basic user configuration steps that most test cases start with
	 *
	 * @return the page class for the last configuration page that was accessed
	 */
	protected AcumaticaEnableDisableFeaturesPage standardTestSetup( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to Vertex Setup Page
		AcumaticaVertexSetupPage vertexSetupPage = homePage.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES,
			AcumaticaLeftPanelLink.VERTEX_SETUP);

		//Implemented predefined settings
		String[] branchAndCompanyCode = vertexSetupPage.predefinedSettingsFromVertexSetupPage(true, "AU0561", true,
			false, true);
		//TODO ask mike bottos whether these are derived from the settings, then update jira task description if so
		assertTrue(branchAndCompanyCode[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");
		//Page navigation
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = vertexSetupPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.COMMON_SETTINGS, AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);

		//Implemented predefined settings (common method name)
		toggleFeaturesPage.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		return toggleFeaturesPage;
	}

	/**
	 * Enter user name , password and click on 'login' button
	 *
	 * @param login    the login page for the site that's being logged into
	 * @param username the username to sign in as
	 * @param password the corresponding password for the username
	 */
	protected AcumaticaPostSignOnPage logInAsUser( final AcumaticaLoginPage login, final String username,
		final String password, final String company )
	{
		AcumaticaPostSignOnPage homePage = null;

		login.setUsername(username);
		login.setPassword(password);
		login.setCompany(company);

		homePage = login.clickLoginButton();

		return homePage;
	}

	protected String returnUsername( )
	{
		return ACUMATICA_USERNAME;
	}

	protected String returnPassword( )
	{
		return ACUMATICA_PASSWORD;
	}

	/**
	 * Customers page actions and validations
	 *
	 * @param customerID
	 * @param customerName
	 * @param email
	 * @param isApplyOverdueCharges
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void customersPageActionsAndValidations( final String customerID, final String customerName,
		final String email, final boolean isApplyOverdueCharges, final boolean isAcs, final String addressLine1,
		final String city, final String country, final String state, final String zipCode )
	{
		customers.setCustomerId(customerID);
		customers.setCustomerName(customerName);
		customers.selectStatus("Active");
		String companyName = customers.getCompanyName();
		assertEquals(companyName, customerName, "Given strings are not equal");
		customers.customerClassStatus(defaultCustomerClassId);
		customers.setEmail(email);
		customers.isApplyOverdueChargesChecked(isApplyOverdueCharges);
		customers.isMainAcsChecked(isAcs);
		customers.setAddressLine1(addressLine1);
		customers.setCity(city);
		customers.setCountry(country);
		customers.setState(state);
		customers.setZipCode(zipCode);
		customers.selectCreditVerification("Disabled");
	}

	protected void customersPageActionsAndValidations( final AcumaticaCustomersPage customersPage,
		final String customerID, final String customerName, final String email, final boolean isApplyOverdueCharges,
		final boolean isAcs, final String addressLine1, final String city, final String country, final String state,
		final String zipCode )
	{
		customersPage.setCustomerId(customerID);
		customersPage.setCustomerName(customerName);
		customersPage.selectStatus("Active");
		String companyName = customersPage.getCompanyName();
		assertEquals(companyName, customerName, "Given strings are not equal");
		customersPage.customerClassStatus(defaultCustomerClassId);
		customersPage.setEmail(email);
		customersPage.isApplyOverdueChargesChecked(isApplyOverdueCharges);
		customersPage.isMainAcsChecked(isAcs);
		customersPage.setAddressLine1(addressLine1);
		customersPage.setCity(city);
		customersPage.setCountry(country);
		customersPage.setState(state);
		customersPage.setZipCode(zipCode);
		customersPage.selectCreditVerification(AcumaticaCreditVerification.DISABLED);
	}

	/**
	 * Validate delivery settings details
	 *
	 * @param shipVia
	 * @param isSameAsMain
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void validateDeliverySettingsDetails( String shipVia, boolean isSameAsMain, boolean isAcs,
		String addressLine1, String city, String country, String state, String zipCode )
	{
		customers.setTaxZoneId("VERTEX");
		boolean results = customers.setShippingAddressSameAsMainChecked(isSameAsMain);
		assertEquals(results, isSameAsMain, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(isAcs);
		assertEquals(result, isAcs, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		String addressLine = customers.getAddressLine1();
		assertTrue(addressLine.equalsIgnoreCase(addressLine1), "Given strings are not equal");
		String addressLine2 = customers.getAddressLine1();
		assertTrue(addressLine2.isEmpty(), "Given strings are not equal");
		String city_result = customers.getCity();
		assertTrue(city_result.equalsIgnoreCase(city), "Given strings are not equal");
		String country_result = customers.getCountry();
		assertTrue(country_result.equalsIgnoreCase(country), "Given strings are not equal");
		String state_result = customers.getState();
		assertTrue(state_result.equalsIgnoreCase(state), "Given strings are not equal");
		String zipCode_result = customers.getZipCode();
		assertEquals(zipCode_result, zipCode, "Given strings are not equal");
	}

	protected void validateDeliverySettingsDetails( final AcumaticaCustomersPage customersPage, String shipVia,
		boolean isSameAsMain, boolean isAcs, String addressLine1, String city, String country, String state,
		String zipCode )
	{
		customersPage.setTaxZoneId("VERTEX");
		boolean results = customersPage.setShippingAddressSameAsMainChecked(isSameAsMain);
		assertEquals(results, isSameAsMain, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customersPage.isDeliveryAcsChecked(isAcs);

		assertEquals(result, isAcs, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		String addressLine = customersPage.getAddressLine1();
		assertTrue(addressLine.equalsIgnoreCase(addressLine1), "Given strings are not equal");
		String addressLine2 = customersPage.getAddressLine2();
		assertTrue(addressLine2.isEmpty(), "Given strings are not equal");
		String city_result = customersPage.getCity();
		assertTrue(city_result.equalsIgnoreCase(city), "Given strings are not equal");
		String country_result = customersPage.getCountry();
		assertTrue(country_result.equalsIgnoreCase(country), "Given strings are not equal");
		String state_result = customersPage.getState();
		assertTrue(state_result.equalsIgnoreCase(state), "Given strings are not equal");
		String zipCode_result = customersPage.getZipCode();
		assertTrue(zipCode.equalsIgnoreCase(zipCode_result), "Given strings are not equal");
	}

	/**
	 * Validate totals tab
	 *
	 * @param lineTotal
	 * @param miscTotal
	 * @param discountTotal
	 * @param taxTotal
	 * @param unshippedQuantity
	 * @param unshippedAmount
	 * @param unbilledQuantity
	 * @param unbilledAmount
	 * @param paymentTotal
	 * @param preAuthorizedAmount
	 * @param unpaidBalance
	 */
	protected void validateTotalsTab( String lineTotal, String miscTotal, String discountTotal, String taxTotal,
		String unshippedQuantity, String unshippedAmount, String unbilledQuantity, String unbilledAmount,
		String paymentTotal, String preAuthorizedAmount, String unpaidBalance )
	{
		String lineTotal_result = salesOrdersValidation.getLineTotal();
		assertTrue(lineTotal_result.equalsIgnoreCase(lineTotal), "Line total is not matching");
		String miscTotal_result = salesOrdersValidation.getMiscTotal();
		assertTrue(miscTotal_result.equalsIgnoreCase(miscTotal), "Misc total is not matching");
		String discountTotal_result = salesOrdersValidation.getDiscountTotal();
		assertTrue(discountTotal_result.equalsIgnoreCase(discountTotal), "Discount total is not matching");
		String taxTotal_result = salesOrdersValidation.getTotalTax();
		assertTrue(taxTotal_result.equalsIgnoreCase(unshippedQuantity), "Unshipped Quantity is not matching");
		String unshippedQuantity_result = salesOrdersValidation.getUnshippedQuantity();
		assertTrue(unshippedQuantity_result.equalsIgnoreCase(unshippedQuantity), "Unshipped Quantity is not matching");
		String unshippedAmount_result = salesOrdersValidation.getUnshippedAmount();
		assertTrue(unshippedAmount_result.equalsIgnoreCase(unshippedAmount), "Unshipped Amount is not matching");
		String unbilledQuantity_result = salesOrdersValidation.getUnbilledQuantity();
		assertTrue(unbilledQuantity_result.equalsIgnoreCase(unbilledQuantity), "Unbilled Quantity is not matching");
		String unbilledAmount_result = salesOrdersValidation.getUnbilledAmount();
		assertTrue(unbilledAmount_result.equalsIgnoreCase(unbilledAmount), "Unshipped Amount is not matching");
		String paymentTotal_result = salesOrdersValidation.getPaymentTotal();
		assertTrue(paymentTotal_result.equalsIgnoreCase(paymentTotal), "Payment Total is not matching");
		String preAuthorizedAmount_result = salesOrdersValidation.getPreAuthorizedAmount();
		assertTrue(preAuthorizedAmount_result.equalsIgnoreCase(preAuthorizedAmount),
			"Pre authorized amount is not matching");
		String unpaidBalance_result = salesOrdersValidation.getUnPaidBalance();
		assertTrue(unpaidBalance_result.equalsIgnoreCase(unpaidBalance), "Unpaid Balance is not matching");
	}

	/**
	 * Validate order type
	 */
	protected void validateOrderType( )
	{
		String orderType = salesOrders.getOrderType();
		assertTrue(orderType.equalsIgnoreCase("SO"), "Order Type is not matching");
	}

	protected void validateOrderType( AcumaticaSalesOrdersPage salesOrdersPage )
	{
		String orderType = salesOrdersPage.getOrderType();
		assertTrue(orderType.equalsIgnoreCase("SO"), "Order Type is not matching");
	}

	/**
	 * Set sales order page actions
	 *
	 * @param customer
	 * @param inventoryId
	 * @param quantity
	 * @param unitPrice
	 */
	protected void setSalesOrderPageActions( String customer, String inventoryId, String quantity, String unitPrice )
	{
		salesOrders.setCustomer(customer);
		salesOrders.clickPlusIconForAddNewRecord();
		salesOrders.setInventoryId(inventoryId);
		salesOrders.setQuantity(quantity);
		salesOrders.setUnitPrice(unitPrice);
	}

	protected void setSalesOrderPageActions( AcumaticaSalesOrdersPage salesOrdersPage, String customer,
		String inventoryId, String quantity, String unitPrice )
	{
		salesOrdersPage.setCustomer(customer);
		salesOrdersPage.clickPlusIconForAddNewRecord();
		salesOrdersPage.setInventoryId(inventoryId);
		salesOrdersPage.setQuantity(quantity);
		salesOrdersPage.setUnitPrice(unitPrice);
	}

	/**
	 * Validate financial tab
	 *
	 * @param overrideAddress_False
	 * @param acs_False
	 */
	protected void validateFinancialTab( boolean overrideAddress_False, boolean acs_False )
	{
		String branch = salesOrders.getFinancialInformationBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN - New York"), "Branch name is not matching");
		String taxZone = salesOrders.getCustomerTaxZoneFromFinancialInformationSection();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax zone is not matching");
		boolean status = salesOrders.verifyOrrideAddressFromBillToInfoSection(overrideAddress_False);
		assertEquals(status, overrideAddress_False, "Override Address is not UnTicked");
		boolean acsStatus = salesOrders.verifyAcsFromBillToInfoSection(overrideAddress_False);
		assertEquals(acsStatus, acs_False, "ACS is not UnTicked");
	}

	/**
	 * Validate shipping tab
	 *
	 * @param shipVia
	 * @param overrideAddress_False
	 * @param acs_False
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param postalCode
	 */
	protected void validateShippingTab( String shipVia, boolean overrideAddress_False, boolean acs_False,
		String addressLine1, String city, String country, String state, String postalCode )
	{
		salesOrdersValidation.setShipViaFromShippingInformationSection(shipVia);
		boolean status = salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(overrideAddress_False);
		assertEquals(status, overrideAddress_False, "Override Address is not UnTicked");
		boolean acsStatus = salesOrdersValidation.verifyAcsFromShipToInfoSection(acs_False);
		assertEquals(acsStatus, acs_False, "ACS is not UnTicked");
		String addressLine = salesOrdersValidation.getAddressLine1FromShipToInfoSection();
		assertEquals(addressLine, addressLine1, "Address Line1 is not matching");
		String addressLine2 = salesOrdersValidation.getAddressLine2FromShipToInfoSection();
		assertTrue(addressLine2.isEmpty(), "Given strings are not equal");
		String city_result = salesOrdersValidation.getCityFromShipToInfoSection();
		assertTrue(city_result.equalsIgnoreCase(city), "City is not matching");
		String country_result = salesOrdersValidation.getCountryFromShipToInfoSection();
		assertTrue(country_result.equalsIgnoreCase(country), "Country is not matching");
		String state_result = salesOrdersValidation.getStateFromShipToInfoSection();
		assertTrue(state_result.equalsIgnoreCase(state), "State is not matching");
		String postalCode_result = salesOrdersValidation.getPostalCodeFromShipToInfoSection();
		assertTrue(postalCode_result.equalsIgnoreCase(postalCode), "Postal code is not matching");
	}

	/**
	 * Validate content from main
	 *
	 * @param taxTotal
	 * @param orderTotal
	 */
	protected void mainFormContentValidations( String taxTotal, String orderTotal )
	{
		String result_TaxTotal = salesOrdersValidation.getTaxTotal();
		assertTrue(result_TaxTotal.equalsIgnoreCase(taxTotal));
		String orderTotal_result = salesOrdersValidation.getOrderTotal();
		assertTrue(orderTotal_result.equalsIgnoreCase(orderTotal));
	}

	/**
	 * select action
	 *
	 * @param actionItem
	 */
	protected void selectAction( String actionItem )
	{
		common.selectOptionFromActionDropDown(actionItem);
		common.clickOkButton();
	}

	/**
	 * Address cleansing action method
	 *
	 * @param actionsDropDown
	 * @param addressCleansing
	 */
	protected void addressCleansing( String actionsDropDown, String addressCleansing )
	{
		common.selectAddressCleansing(actionsDropDown, addressCleansing);
		common.clickOkButton();
	}

	/**
	 * validate vertex postal address popup
	 *
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param postalCode
	 */
	protected void validateVertexPostalAddressPopup( String addressLine1, String city, String country, String state,
		String postalCode )
	{
		String resultAddressLine1 = common.getVertexPostalAddressPopup(addressLine1);
		assertTrue(resultAddressLine1.equalsIgnoreCase(addressLine1), "Given strings are not equal");
		String resultCity = common.getVertexPostalAddressPopup(city);
		assertTrue(resultCity.equalsIgnoreCase(city), "Given strings are not equal");
		String resultCountry = common.getVertexPostalAddressPopup(country);
		assertTrue(resultCountry.equalsIgnoreCase(country), "Given strings are not equal");
		String resultState = common.getVertexPostalAddressPopup(state);
		assertTrue(resultState.equalsIgnoreCase(state), "Given strings are not equal");
		String resultPostalCode = common.getVertexPostalAddressPopup(postalCode);
		assertTrue(resultPostalCode.equalsIgnoreCase(postalCode), "Given strings are not equal");
	}

	/**
	 * set basic details of business account page
	 *
	 * @param businesAccount
	 * @param businessAccountName
	 * @param isActive
	 * @param companyName
	 * @param email
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void setBusinessAccountPageDetails( String businesAccount, String businessAccountName, String isActive,
		String companyName, String email, boolean isAcs, String addressLine1, String city, String country, String state,
		String zipCode )
	{
		AcumaticaBusinessAccountsPage businessAccount = new AcumaticaBusinessAccountsPage(driver);
		businessAccount.clickOnNewRecordIcon();
		businessAccount.setBusinessAccount(businesAccount);
		businessAccount.setBusinessAccountName(businessAccountName);
		businessAccount.selectStatus(isActive);
		String resultCompanyName = businessAccount.getCompanyName();
		assertTrue(resultCompanyName.equalsIgnoreCase(companyName), "Company name is not matching");
		businessAccount.setMail(email);
		businessAccount.isAcsChecked(isAcs);
		businessAccount.setAddressLine1(addressLine1);
		businessAccount.setCity(city);
		businessAccount.setCountry(country);
		businessAccount.setState(state);
		businessAccount.setZipCode(zipCode);
	}

	/**
	 * validate general info tab
	 *
	 * @param isAcs
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void validateGeneralInfo( boolean isAcs, String addressLine1, String addressLine2, String city,
		String country, String state, String zipCode )
	{
		AcumaticaSalesOrdersValidationPage salesOrdersValidation = new AcumaticaSalesOrdersValidationPage(driver);
		boolean acsStatus = salesOrdersValidation.verifyAcsFromShipToInfoSection(isAcs);
		assertEquals(acsStatus, isAcs, "ACS is not UnTicked");
		String resultAddressLine1 = salesOrdersValidation.getAddressLine1FromShipToInfoSection();
		assertTrue(resultAddressLine1.equalsIgnoreCase(addressLine1), "Address Line1 is not matching");
		String resultAddressLine2 = salesOrdersValidation.getAddressLine2FromShipToInfoSection();
		assertTrue(resultAddressLine2.equalsIgnoreCase(addressLine2), "Given strings are not equal");
		String city_result = salesOrdersValidation.getCityFromShipToInfoSection();
		assertTrue(city_result.equalsIgnoreCase(city), "City is not matching");
		String country_result = salesOrdersValidation.getCountryFromShipToInfoSection();
		assertTrue(country_result.equalsIgnoreCase(country), "Country is not matching");
		String state_result = salesOrdersValidation.getStateFromShipToInfoSection();
		assertTrue(state_result.equalsIgnoreCase(state), "State is not matching");
		String postalCode_result = salesOrdersValidation.getPostalCodeFromShipToInfoSection();
		assertTrue(postalCode_result.equalsIgnoreCase(zipCode), "Postal code is not matching");
	}

	/**
	 * Navigate to VertexSetPage
	 */
	protected void navigateVertexPage( )
	{
		AcumaticaCommonPage common = new AcumaticaCommonPage(driver);

		common.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);
	}

	/**
	 * Set up Branches page basic details
	 *
	 * @param branchId
	 * @param branchName
	 * @param companyName
	 * @param email
	 * @param defaultCountry
	 * @param isACS
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void setBasicDetailsInBranchesPageFromGeneralInfoTab( String branchId, String branchName,
		String companyName, String email, String defaultCountry, boolean isACS, String addressLine1, String city,
		String country, String state, String zipCode )
	{
		AcumaticaBranchesPage branches = new AcumaticaBranchesPage(driver);
		AcumaticaCustomersPage customers = new AcumaticaCustomersPage(driver);
		branches.setBranchID(branchId);
		branches.setBranchName(branchName);
		branches.selectActiveCheckBox(true);
		String resultCompanyName = branches.getCompanyName();
		assertTrue(resultCompanyName.equalsIgnoreCase(companyName), "Company name is not equal");
		customers.setEmail(email);
		branches.setDefaultCountry(defaultCountry);
		branches.isACSCheckedFromBranchesPage(isACS);
		customers.setAddressLine1(addressLine1);
		customers.setCity(city);
		customers.setCountry(country);
		customers.setState(state);
		customers.setZipCode(zipCode);
	}

	/**
	 * validate delivery settings after address cleansing
	 *
	 * @param isSameAsMain
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 */
	protected void validateDeliverySettings( boolean isSameAsMain, boolean isAcs, String addressLine1, String city,
		String country, String state )
	{
		AcumaticaBranchesPage branches = new AcumaticaBranchesPage(driver);
		AcumaticaCustomersPage customers = new AcumaticaCustomersPage(driver);

		branches.isSameAsMainCheckedFromBranchesPage(isSameAsMain);
		branches.isACSCheckedFromBranchesPage(isAcs);
		String resultaddressLine1 = customers.getAddressLine1();
		assertTrue(resultaddressLine1.equalsIgnoreCase(addressLine1), "AddressLine1  is not equal");
		String resultCity = customers.getCity();
		assertTrue(resultCity.equalsIgnoreCase(city), "city  is not equal");
		String resultCountry = customers.getCountry();
		assertTrue(resultCountry.equalsIgnoreCase("US - United States of America"), "Country  is not equal");
		String resultState = customers.getState();
		assertTrue(resultState.equalsIgnoreCase(state), "State  is not equal");
	}

	/**
	 * validate details tab
	 *
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void validateDetailsTabAfterAddressCleansing( boolean isAcs, String addressLine1, String city,
		String country, String state, String zipCode )
	{
		AcumaticaBusinessAccountsPage businessAccount = new AcumaticaBusinessAccountsPage(driver);
		businessAccount.isAcsChecked(isAcs);
		String resultAddress_Line1 = businessAccount.getAddressLine1();
		assertTrue(resultAddress_Line1.equalsIgnoreCase(addressLine1), "AddressLine1 is not matching");
		String resultCity = businessAccount.getCity();
		assertTrue(resultCity.equalsIgnoreCase(city), "City is not matching");
		String result_Country = businessAccount.getCountry();
		assertTrue(result_Country.equalsIgnoreCase(country), "Country is not matching");
		String resultState = businessAccount.getState();
		assertTrue(resultState.equalsIgnoreCase(state), "State is not matching");
		String resultZipCode = businessAccount.getZipCode();
		assertTrue(resultZipCode.equalsIgnoreCase(zipCode), "Zipcode is not matching");
	}

	/**
	 * validate delivery settings tab
	 *
	 * @param isSameAsMain
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void validateDeliverySettingTabAfterAddressCleansing( boolean isSameAsMain, boolean isAcs,
		String addressLine1, String city, String country, String state, String zipCode )
	{
		AcumaticaCustomersPage customers = new AcumaticaCustomersPage(driver);
		AcumaticaBranchesPage branches = new AcumaticaBranchesPage(driver);
		branches.isSameAsMainCheckedFromBranchesPage(isSameAsMain);
		branches.isACSCheckedFromBranchesPage(isAcs);
		String result_addressLine1 = customers.getAddressLine1();
		assertTrue(result_addressLine1.equalsIgnoreCase(addressLine1), "AddressLine1  is not equal");
		String result_city = customers.getCity();
		assertTrue(result_city.equalsIgnoreCase(city), "city  is not equal");
		String resultCountry = customers.getCountry();
		assertTrue(resultCountry.equalsIgnoreCase(country), "Country  is not equal");
		String result_state = customers.getState();
		assertTrue(result_state.equalsIgnoreCase(state), "State  is not equal");
	}

	/**
	 * validate totals tab
	 *
	 * @param lineTotal
	 * @param miscTotal
	 * @param discountTotal
	 */
	protected void validateTotalsTab( String lineTotal, String miscTotal, String discountTotal )
	{
		AcumaticaSalesOrdersValidationPage salesOrdersValidation = new AcumaticaSalesOrdersValidationPage(driver);
		String lineTotal_result = salesOrdersValidation.getLineTotal();
		assertTrue(lineTotal_result.equalsIgnoreCase(lineTotal), "Line total is not matching");
		String miscTotal_result = salesOrdersValidation.getMiscTotal();
		assertTrue(miscTotal_result.equalsIgnoreCase(miscTotal), "Misc total is not matching");
		String discountTotal_result = salesOrdersValidation.getDiscountTotal();
		assertTrue(discountTotal_result.equalsIgnoreCase(discountTotal), "Discount total is not matching");
	}

	/**
	 * validate vertex postal address
	 */
	protected void validateVertexPostalAddressPopup( )
	{
		//TODO what's this doing? can it be fixed to pom?
		AcumaticaCommonPage common = new AcumaticaCommonPage(driver);
		common.getVertexPostalAddressPopup("7147 Greenback Ln");
		common.getVertexPostalAddressPopup("Citrus Heights");
		common.getVertexPostalAddressPopup("USA");
		common.getVertexPostalAddressPopup("CA");
		common.getVertexPostalAddressPopup("95621-5526");
		common.clickPopupOkButton();
	}

	/**
	 * Address cleansing data validation
	 *
	 * @param actionsDropDown
	 * @param addressCleansing
	 * @param city
	 * @param state
	 * @param actionIgnore
	 * @param actionConfirm
	 */
	protected void addressCleansingCommonMethod( String actionsDropDown, String addressCleansing, String city,
		String state, String actionIgnore, String actionConfirm )
	{
		AcumaticaCustomersPage customers = new AcumaticaCustomersPage(driver);
		AcumaticaCommonPage common = new AcumaticaCommonPage(driver);

		common.selectAddressCleansing(actionsDropDown, addressCleansing);
		customers.setCity(city);
		common.selectAddressCleansing(actionsDropDown, addressCleansing);
		customers.setState(state);
		this.addressCleansing(actionsDropDown, addressCleansing);
		this.validateVertexPostalAddressPopup();
		common.closeAddressCleansingPopUp();
		this.addressCleansing(actionsDropDown, addressCleansing);
		this.validateVertexPostalAddressPopup();
		this.selectAction(actionIgnore);
		this.addressCleansing(actionsDropDown, addressCleansing);
		this.validateVertexPostalAddressPopup();
		this.selectAction(actionConfirm);
		common.isEnabledAddressCleansingOption(actionsDropDown, addressCleansing);
	}

	/**
	 * Validate sales order page
	 *
	 * @param shipVia
	 * @param isApplyOverdueCharges
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param postalCode
	 */
	protected void validateSalesOrderPage( String shipVia, boolean isApplyOverdueCharges, boolean isAcs,
		String addressLine1, String city, String country, String state, String postalCode )
	{
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		String branch = salesOrders.getFinancialInformationBranch();
		assertEquals("MAIN - New York", branch, "Branch name is not matching");
		String taxZone = salesOrders.getCustomerTaxZoneFromFinancialInformationSection();
		assertEquals("VERTEX - Vertex TaxZone", taxZone, "Tax zone is not matching");
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		salesOrdersValidation.setShipViaFromShippingInformationSection(shipVia);
		this.validateAddressShippingTab(isApplyOverdueCharges, isAcs, addressLine1, city, country, state, postalCode);
		common.clickSaveButton();
	}

	protected void validateSalesOrderPage( AcumaticaSalesOrdersPage salesOrdersPage, String shipVia,
		boolean isApplyOverdueCharges, boolean isAcs, String addressLine1, String city, String country, String state,
		String postalCode )
	{
		salesOrdersPage.clickMainPanelTab(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		String branch = salesOrdersPage.getFinancialInformationBranch();
		final String expectedBranch = "MAIN - New York";
		assertEquals(expectedBranch, branch, "Branch name is not matching");
		String taxZone = salesOrdersPage.getCustomerTaxZoneFromFinancialInformationSection();
		assertEquals("VERTEX - Vertex TaxZone", taxZone, "Tax zone is not matching");
		salesOrdersPage.clickMainPanelTab(AcumaticaMainPanelTab.SHIPPING_SETTINGS);//TODO make this return a Page object
		salesOrdersValidation.setShipViaFromShippingInformationSection(
			shipVia);//TODO replace this with an instance of some page
		this.validateAddressShippingTab(isApplyOverdueCharges, isAcs, addressLine1, city, country, state, postalCode);
		salesOrdersPage.clickSaveButton(true);
	}

	/**
	 * Validate address shipping tab
	 *
	 * @param isApplyOverdueCharges
	 * @param isAcs
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param postalCode
	 */
	protected void validateAddressShippingTab( boolean isApplyOverdueCharges, boolean isAcs, String addressLine1,
		String city, String country, String state, String postalCode )
	{
		salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(isApplyOverdueCharges);
		salesOrdersValidation.verifyAcsFromShipToInfoSection(isAcs);
		String addressLine = salesOrdersValidation.getAddressLine1FromShipToInfoSection();
		assertEquals(addressLine, addressLine1, "Address Line1 is not matching");
		String city_result = salesOrdersValidation.getCityFromShipToInfoSection();
		assertEquals(city_result, city, "City is not matching");
		String country_result = salesOrdersValidation.getCountryFromShipToInfoSection();
		assertEquals(country_result, country, "Country is not matching");
		String state_result = salesOrdersValidation.getStateFromShipToInfoSection();
		assertEquals(state_result, state, "State is not matching");
		String postalCode_result = salesOrdersValidation.getPostalCodeFromShipToInfoSection();
		assertEquals(postalCode_result, postalCode, "Postal code is not matching");
	}

	/**
	 * Verify sales order page conditions
	 *
	 * @param isApplyOverdueCharges
	 * @param isAcs
	 */
	protected void verifySalesOrderPageConditions( boolean isApplyOverdueCharges, boolean isAcs )
	{
		boolean results = salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(isApplyOverdueCharges);
		assertEquals(results, isApplyOverdueCharges, "UnTicked Checkbox 'Apply Overdue Charges' ");
		boolean result = salesOrdersValidation.verifyAcsFromShipToInfoSection(isAcs);
		assertEquals(result, isAcs, "Ticked Checkbox 'ACS' ");
	}

	/**
	 * Validate total tab details
	 *
	 * @param lineTotal
	 * @param miscTotal
	 * @param discountTotal
	 * @param unshippedQuantity
	 * @param unshippedAmount
	 * @param unbilledQuantity
	 * @param unbilledAmount
	 * @param paymentTotal
	 * @param preAuthorizedAmount
	 * @param unpaidBalance
	 */
	protected void validateTotalTabDetails( String lineTotal, String miscTotal, String discountTotal,
		String unshippedQuantity, String unshippedAmount, String unbilledQuantity, String unbilledAmount,
		String paymentTotal, String preAuthorizedAmount, String unpaidBalance )
	{
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.TOTALS);
		String lineTotal_result = salesOrdersValidation.getLineTotal();
		assertEquals(lineTotal_result, lineTotal, "Line total is not matching");
		String miscTotal_result = salesOrdersValidation.getMiscTotal();
		assertEquals(miscTotal_result, miscTotal, "Misc total is not matching");
		String discountTotal_result = salesOrdersValidation.getDiscountTotal();
		assertEquals(discountTotal_result, discountTotal, "Discount total is not matching");
		String unshippedQuantity_result = salesOrdersValidation.getUnshippedQuantity();
		assertEquals(unshippedQuantity_result, unshippedQuantity, "Unshipped Quantity is not matching");
		String unshippedAmount_result = salesOrdersValidation.getUnshippedAmount();
		assertEquals(unshippedAmount_result, unshippedAmount, "Unshipped Amount is not matching");
		String unbilledQuantity_result = salesOrdersValidation.getUnbilledQuantity();
		assertEquals(unbilledQuantity_result, unbilledQuantity, "Unbilled Quantity is not matching");
		String unbilledAmount_result = salesOrdersValidation.getUnbilledAmount();
		assertEquals(unbilledAmount_result, unbilledAmount, "Unshipped Amount is not matching");
		String paymentTotal_result = salesOrdersValidation.getPaymentTotal();
		assertEquals(paymentTotal_result, paymentTotal, "Payment Total is not matching");
		String preAuthorizedAmount_result = salesOrdersValidation.getPreAuthorizedAmount();
		assertEquals(preAuthorizedAmount_result, preAuthorizedAmount, "Pre authorized amount is not matching");
		String unpaidBalance_result = salesOrdersValidation.getUnPaidBalance();
		assertEquals(unpaidBalance_result, unpaidBalance, "Unpaid Balance is not matching");
		common.clickDeleteButton();
		common.switchToParentPage();
	}

	protected void validateTotalTabDetails( final AcumaticaSalesOrdersPage salesOrdersPage, String lineTotal,
		String miscTotal, String discountTotal, String unshippedQuantity, String unshippedAmount,
		String unbilledQuantity, String unbilledAmount, String paymentTotal, String preAuthorizedAmount,
		String unpaidBalance )
	{
		salesOrdersPage.clickMainPanelTab(AcumaticaMainPanelTab.TOTALS);
		String lineTotal_result = salesOrdersValidation.getLineTotal();
		assertEquals(lineTotal_result, lineTotal, "Line total is not matching");
		String miscTotal_result = salesOrdersValidation.getMiscTotal();
		assertEquals(miscTotal_result, miscTotal, "Misc total is not matching");
		String discountTotal_result = salesOrdersValidation.getDiscountTotal();
		assertEquals(discountTotal_result, discountTotal, "Discount total is not matching");
		String unshippedQuantity_result = salesOrdersValidation.getUnshippedQuantity();
		assertEquals(unshippedQuantity_result, unshippedQuantity, "Unshipped Quantity is not matching");
		String unshippedAmount_result = salesOrdersValidation.getUnshippedAmount();
		assertEquals(unshippedAmount_result, unshippedAmount, "Unshipped Amount is not matching");
		String unbilledQuantity_result = salesOrdersValidation.getUnbilledQuantity();
		assertEquals(unbilledQuantity_result, unbilledQuantity, "Unbilled Quantity is not matching");
		String unbilledAmount_result = salesOrdersValidation.getUnbilledAmount();
		assertEquals(unbilledAmount_result, unbilledAmount, "Unshipped Amount is not matching");
		String paymentTotal_result = salesOrdersValidation.getPaymentTotal();
		assertEquals(paymentTotal_result, paymentTotal, "Payment Total is not matching");
		String preAuthorizedAmount_result = salesOrdersValidation.getPreAuthorizedAmount();
		assertEquals(preAuthorizedAmount_result, preAuthorizedAmount, "Pre authorized amount is not matching");
		String unpaidBalance_result = salesOrdersValidation.getUnPaidBalance();
		assertEquals(unpaidBalance_result, unpaidBalance, "Unpaid Balance is not matching");
		common.clickDeleteButton();
		common.switchToParentPage();
	}

	/**
	 * Delete created customer record
	 *
	 * @param customerId
	 */
	protected void deleteCreatedCustomerRecord( String customerId )
	{
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);
		customers.setCustomerId(customerId);
		common.clickDeleteButton();
		common.switchToMainFrame();
	}

	protected void deleteCreatedCustomerRecord( final AcumaticaPostSignOnPage acumaticaPage, String customerId )
	{
		AcumaticaCustomersPage customersPage = acumaticaPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);
		customersPage.setCustomerId(customerId);
		common.clickDeleteButton();
		common.switchToMainFrame();
	}

	/**
	 * Validate Delivery Settings details
	 *
	 * @param isSameAsMain_True
	 * @param acs_False
	 */
	protected void validateDeliverySettingsDetails( boolean isSameAsMain_True, boolean acs_False )
	{
		customers.setTaxZoneId("VERTEX");
		boolean results = customers.setShippingAddressSameAsMainChecked(isSameAsMain_True);
		assertEquals(results, isSameAsMain_True, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(acs_False);
		assertEquals(result, acs_False, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
	}

	/**
	 * Set up Branches page basic details
	 *
	 * @param branchId
	 * @param addressLine1
	 * @param city
	 * @param country
	 * @param state
	 * @param zipCode
	 */
	protected void setBasicDetailsInBranchesPageFromGeneralInfoTab( String branchId, String addressLine1, String city,
		String country, String state, String zipCode )
	{
		branches.setBranchID(branchId);
		branches.selectActiveCheckBox(true);
		customers.setAddressLine1(addressLine1);
		customers.setCity(city);
		customers.setCountry(country);
		customers.setState(state);
		customers.setZipCode(zipCode);
	}

	/**
	 * common method for delete created customer classes record
	 */
	protected void deleteCreatedCustomerClassesRecord( String customerClassId )
	{
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMER_CLASSES);
		common.switchToMainFrame();
		customerClass.enterClassId(customerClassId);
		common.clickDeleteButton();
	}
}
