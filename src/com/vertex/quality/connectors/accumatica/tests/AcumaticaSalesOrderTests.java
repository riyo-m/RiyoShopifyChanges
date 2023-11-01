package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaCountry;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaCustomersPage;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaEnableDisableFeaturesPage;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaSalesOrdersPage;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * To create a new Customer Class. Then to create a new Customer Code within that new Customer
 * Class.
 * Then use that newly created customer in Sales Order creation
 *
 * @author saidulu kodadala
 */
public class AcumaticaSalesOrderTests extends AcumaticaBaseTest
{
	/**
	 * To create a Basic Sales Order transaction for the newly created customer (with Address
	 * Cleansing at Sales Order)
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * @author saidulu kodadala
	 */
	@Ignore
	@Test
	public void BasicSalesOrderTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();

		//Navigate to Customers Page
		AcumaticaCustomersPage customersPage = toggleFeaturesPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations(customersPage, "C3ANEWCUST", "C3ANEWCUSTOMER ONE", defaultEmail, true,
			false, "7147 Greenback Ln", "Citrus Heights", Country.USA.iso2code, "CA", "95621");

		//Navigate to Delivery Settings tab
		customersPage.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(customersPage, DEFAULT_SHIP_VIA, true, false, "7147 Greenback Ln",
			"Citrus Heights", AcumaticaCountry.USA.getAcumaticaName(), "CA - CALIFORNIA", "95621");

		//customersPage.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		/*WARN- sometimes there'd be a timing issue where the Customer Id field would somehow be empty when the save
		button was pressed.
		The switch back to the General Info main panel tab (above) was commented out to try to avoid this.
		If this happens again, then of course it isn't due to the main panel tab switch
		 */

		customersPage.clickSaveButton();

		//Navigate Sales Orders page
		AcumaticaSalesOrdersPage salesOrdersPage = customersPage.openMenuPage(AcumaticaGlobalSubMenuOption.SALES_ORDERS,
			AcumaticaLeftPanelLink.SALES_ORDERS);

		validateOrderType(salesOrdersPage);

		this.setSalesOrderPageActions(salesOrdersPage, "C3ANEWCUST", "301CMPNS01", "2", "500");
		this.validateSalesOrderPage(salesOrdersPage, DEFAULT_SHIP_VIA, false, false, "7147 Greenback Ln",
			"Citrus Heights", AcumaticaCountry.USA.getAcumaticaName(), "CA - CALIFORNIA", "95621");
		this.validateVertexPostalAddressPopup();

		this.verifySalesOrderPageConditions(true, true);
		this.validateAddressShippingTab(true, false, "7147 Greenback Ln", "Citrus Heights",
			AcumaticaCountry.USA.getAcumaticaName(), "CA - CALIFORNIA", "95621-5526");
		//fixme actual tax total is "77.50",
		// the current value makes absolutely no sense at all unless it was the result of sloppy refactoring
		// (the expected quantity is 2, so maybe 'mainFormContentValidations' once checked the order quantity)
		final String expectedTaxTotal = "2.00";
		final String expectedOrderTotal = "1,077.50";
		this.mainFormContentValidations(expectedTaxTotal, expectedOrderTotal);
		salesOrdersPage.clickMainPanelTab(AcumaticaMainPanelTab.TAX_DETAILS);//TODO open new page?

		final String expectedLineTotal = "1,000.00";
		//fixme the discount total value is "0.00"
		final String expectedDiscountTotal = "75.00";
		//fixme the actual unshipped amount was "1,077.50",
		// the current value doesn't make sense (the unshipped and unbilled quantities are both 2, so why would
		// the unbilled amount be "1,077.50" while the unshipped amount was "0.00"?)
		final String expectedUnshippedAmount = "0.00";
		final String expectedUnbilledAmount = "1,077.50";
		final String expectedUnpaidBalance = "1,077.50";

		this.validateTotalTabDetails(salesOrdersPage, expectedLineTotal, "0.00", expectedDiscountTotal, "2.00",
			expectedUnshippedAmount, "2.00", expectedUnbilledAmount, "0.00", "0.00", expectedUnpaidBalance);

		//delete created customer record
		this.deleteCreatedCustomerRecord(salesOrdersPage, "C3ANEWCUST");
	}

	/**
	 * To validate the tax changes in Sales Transaction due to the change in quantity & ship-to
	 * address
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void SalesOrder_TaxChangesDueToChangeInQuantityAndShipToAddressTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();

		//Navigate to Customers Page
		AcumaticaCustomersPage customersPage = toggleFeaturesPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations(customersPage, "CHQTYSHPNG", "CHANGEQUANTITY SHPNGADDR", defaultEmail,
			false, false, "7340 HWY 26 West", "Oberlin", Country.USA.iso2code, "LA", "70655");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customersPage.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(customersPage, DEFAULT_SHIP_VIA, true, false, "7340 HWY 26 West",
			"Oberlin", "US - United States of America", "LA - LOUISIANA", "70655");
		customersPage.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		customersPage.clickSaveButton();

		//Navigate Sales Orders page
		AcumaticaSalesOrdersPage salesOrdersPage = customersPage.openMenuPage(AcumaticaGlobalSubMenuOption.SALES_ORDERS,
			AcumaticaLeftPanelLink.SALES_ORDERS);

		validateOrderType(salesOrdersPage);

		this.setSalesOrderPageActions("CHQTYSHPNG", "301CMPNS01", "2", "775");
		salesOrders.clickPlusIconForAddNewRecord();
		salesOrders.setInventoryId("301CMPNS01");
		salesOrders.setQuantity("3");
		salesOrders.setUnitPrice("500");

		salesOrders.clickPlusIconForAddNewRecord();
		salesOrders.setInventoryIdAndWareHouse("FOBD1", "WHOLESALE", "Shipping Charges");
		salesOrders.setQuantity("3");
		salesOrders.setUnitPrice("75");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		this.validateFinancialTab(false, false);

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "7340 HWY 26 West", "Oberlin",
			"US - United States of America", "LA - LOUISIANA", "70655");
		this.mainFormContentValidations("290.66", "3,565.66");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "7340 HWY 26 West", "Oberlin",
			"US - United States of America", "LA - LOUISIANA", "70655");
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("7340 Highway 26", "Oberlin", "USA", "LA", "70655-3407");
		this.selectAction("Confirm");
		this.validateShippingTab(DEFAULT_SHIP_VIA, true, true, "7340 HWY 26 West", "Oberlin",
			"US - United States of America", "LA - LOUISIANA", "70655-3407");

		this.mainFormContentValidations("317.68", "3,592.68");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, true, true, "1000 Log Lodge Ct", "Baraboo",
			"US - United States of America", "WI", "53913");
		common.clickSaveButton();
		this.validateVertexPostalAddressPopup("1000 Log Lodge Ct", "Baraboo", "USA", "WI", "53913-9293");
		this.selectAction("Confirm");

		this.validateShippingTab(DEFAULT_SHIP_VIA, true, true, "1000 Log Lodge Ct", "Baraboo",
			"US - United States of America", "WI - WISCONSIN", "53913-9293");
		this.mainFormContentValidations("232.16", "4,453.16");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("4,221.00", "0.00", "0.00", "232.16", "7.00", "4,453.16", "7.00", "4,453.16", "0.00",
			"0.00", "4,453.16");
		common.clickDeleteButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		customers.setCustomerId("CHQTYSHPNG");
		common.clickDeleteButton();
	}

	/**
	 * To verify the Taxes in Sales Order Transaction for a Tax Exempt Item
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void SalesOrder_TaxExemptItemTest( )
	{
		commonSetup();

		//Navigate to VertexSetup page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("ABARTENDE", "PRNT1", "1", "250");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		String branch = salesOrders.getFinancialInformationBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN - New York"), "Branch name is not displayed");
		String taxZone = salesOrders.getCustomerTaxZoneFromFinancialInformationSection();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax zone is not displayed");

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "38 Radnor Drive", "Newtown Square",
			"US - United States of America", "PA - PENNSYLVANIA", "19073");
		salesOrdersValidation.clearShipViaFromShippingInformationSection();
		common.clickSaveButton();
		this.mainFormContentValidations("22.19", "272.19");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		salesOrdersValidation.setShipViaFromShippingInformationSection(DEFAULT_SHIP_VIA);
		boolean status = salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(false);
		assertFalse(status, "Override Address is not UnTicked");
		boolean acsStatus = salesOrdersValidation.verifyAcsFromShipToInfoSection(false);
		assertFalse(acsStatus, "ACS is not UnTicked");
		common.clickSaveButton();
		this.mainFormContentValidations("0.00", "250.00");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("250.00", "0.00", "0.00", "0.00", "1.00", "250.00", "1.00", "250.00", "0.00", "0.00",
			"250.00");
		common.clickDeleteButton();
	}

	/**
	 * To verify the XML logging of Sales Order transaction
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void SalesOrder_XML_LoggingTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();

		//Navigate to Customers Page
		AcumaticaCustomersPage customersPage = toggleFeaturesPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations(customersPage, "SOXMLCUST", "CUSTOMERFOR-SALESORDERXMLCHECK",
			defaultEmail, false, false, "2495 Iron Point Rd #11", "Folsom", Country.USA.iso2code, "CA", "95630");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertFalse(result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("SOXMLCUST", "301CMPNS01", "2", "575");

		salesOrders.clickPlusIconForAddNewRecord();
		salesOrders.setInventoryIdAndWareHouse("FOBD1", "WHOLESALE", "Shipping");
		salesOrders.setQuantity("2");
		salesOrders.setUnitPrice("45");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		this.validateFinancialTab(false, false);

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "2495 Iron Point Rd #11", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630");
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("2495 Iron Point Rd", "Folsom", "USA", "CA", "95630-8710");
		this.selectAction("Confirm");

		this.mainFormContentValidations("96.10", "1,336.10");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("1,240.00", "0.00", "0.00", "96.10", "4.00", "1,336.10", "4.00", "1,336.10", "0.00",
			"0.00", "1,336.10");
		common.clickDeleteButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		customers.setCustomerId("SOXMLCUST");
		common.clickDeleteButton();

		//FINANCE > Taxes > Configuration (icon) > VERTEX INTEGRATION > Vertex LogHistory (XML validations not yet completed )
	}

	/**
	 * To verify the XML logging of a Sales Order transaction with Discount Amounts at line level
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void SalesOrder_DiscountAmount_LineLevel_XML_LoggingTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();

		//Navigate to Customers Page
		AcumaticaCustomersPage customersPage = toggleFeaturesPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations(customersPage, "DISCUNTXML", "CUSTFOR-SO-DISCOUNT-LINEITEM-XMLCHECK",
			defaultEmail, false, false, "3075B Hansen Way", "Palo Alto", Country.USA.iso2code, "CA", "94304");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertFalse(result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("DISCUNTXML", "301CMPNS01", "1", "945");

		salesOrders.clickPlusIconForAddNewRecord();
		salesOrders.setInventoryIdAndWareHouse("FOBD1", "WHOLESALE", "Shipping Charges");
		salesOrders.setQuantity("1");
		salesOrders.setUnitPrice("65");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		this.validateFinancialTab(false, false);

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "3075B Hansen Way", "Palo Alto",
			"US - United States of America", "CA - CALIFORNIA", "94304");

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("3075B Hansen Way", "Palo Alto", "USA", "CA", "94304-1000");
		this.selectAction("Confirm");
		this.mainFormContentValidations("84.60", "1,024.60");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("940.00", "0.00", "0.00", "84.60", "2.00", "1,024.60", "2.00", "1,024.60", "0.00",
			"0.00", "1,024.60");
		common.clickDeleteButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		customers.setCustomerId("DISCUNTXML");
		common.clickDeleteButton();

		//	FINANCE > Taxes > Configuration (icon) > VERTEX INTEGRATION > Vertex LogHistory (XML validations not yet completed )
	}

	/**
	 * To verify the XML logging of a Sales Order transaction with "Discount Code" at Header level
	 * and "Discount Amounts" at line level
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void SalesOrder_DiscountCode_HeaderLevelAndDiscountAmount_LineLevel_XML_LoggingTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();

		//Navigate to Customers Page
		AcumaticaCustomersPage customersPage = toggleFeaturesPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations(customersPage, "DISCODEXML",
			"CUSTFOR-SO-DISC-CODE DISCOUNT-LINEITEM-XMLCHECK", defaultEmail, false, false, "22833 NE 8th St",
			"Sammamish", Country.USA.iso2code, "WA", "98074");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertFalse(result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.DISCOUNT_CODES);
		salesOrders.clickPlusIconForAddNewRecord();
		discountCodes.clickAddNewRecord();
		discountCodes.setDiscountCode("1Z01DISC");
		discountCodes.setDescription("Document Discount at Header Level TEST");

		//	FINANCE > Taxes > Configuration (icon) > VERTEX INTEGRATION > Vertex LogHistory (XML validations not yet completed )
	}
}
