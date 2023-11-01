package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * To verify the address cleansing functionality for Invalid Address - Ignore & Confirm at Customer
 * Creation level
 * and to verify the address cleansing functionality on Sales Order transaction, when "Auto Accept
 * ACS Yes/No" checkbox Ticked
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaAddressCleansingTests extends AcumaticaBaseTest
{
	/**
	 * To verify the address cleansing functionality for Invalid Address at Customer Creation level
	 * (8)
	 */
	@Test
	public void AddressCleansing_ConfirmInvalidAddressTest( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to VertexSetup page 
		homePage.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("ADRINVALID", "INVALID ADDRCHECK", email, false, false,
			"22833 NE 8th St", "Hyderabad", Country.USA.iso2code, "CA", "99999");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "22833 NE 8th St", "Hyderabad",
			"US - United States of America", "CA - CALIFORNIA", "99999");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Address cleansing 
		this.addressCleansingCommonMethod("Actions", "Address Cleansing", "Sammamish", "WA", "Ignore", "Confirm");

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, true, "22833 NE 8th St", "Sammamish",
			"US - United States of America", "WA - WASHINGTION", "98074-7232");

		//delete created customer record
		common.clickDeleteButton();
	}

	/**
	 * To create a Sales Order transaction with Invalid shipping address and then verifying the
	 * taxes for the "Address Cleansing - Ignore" action
	 */
	@Test
	public void AddressCleansing_IgnoreInvalidAddressTest( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to VertexSetup page 
		homePage.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("ADRINVALID", "INVALID ADDRCHECK", email, false, false,
			"22833 NE 8th St", "Hyderabad", Country.USA.iso2code, "CA", "99999");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "22833 NE 8th St", "Hyderabad",
			"US - United States of America", "CA - CALIFORNIA", "99999");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Address cleansing 
		this.addressCleansingCommonMethod("Actions", "Address Cleansing", "Sammamish", "WA", "Ignore", "Confirm");

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, true, "22833 NE 8th St", "Sammamish",
			"US - United States of America", "WA - WASHINGTION", "98074-7232");

		//delete created customer record
		common.clickDeleteButton();
		fail();
	}

	/**
	 * To verify the address cleansing functionality on Sales Order transaction, when "Auto Accept
	 * ACS Yes/No" checkbox Ticked (21)
	 */
	@Test
	public void AddressCleansing_SalesOrder_AutoAcceptACSYes_DisabledTest( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to VertexSetup page 
		homePage.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, true, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("AUTOACSON1", "AUTOACSFLAGON-SO-NO-DISPLAY-OF-ADDRESSCLEANSING-POP-UP",
			email, false, false, "883 Route 1 Suite 100", "Edison", Country.USA.iso2code, "NJ", "08817");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean isSameAsMainChecked = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(isSameAsMainChecked, "Ticked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertTrue(!result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("AUTOACSON1", "301CMPNS01", "1", "777");
		salesOrders.setDiscountAmount("22");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		String result_orderTotal = salesOrdersValidation.getOrderTotal();
		assertTrue(result_orderTotal.equalsIgnoreCase("1,460.00"), "Order Total is not equal");
		String branch = salesOrders.getFinancialInformationBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN - New York"), "Branch name is not displayed");
		String taxZone = salesOrders.getCustomerTaxZoneFromFinancialInformationSection();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax zone is not displayed");

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "883 Route 1 Suite 100", "Edison",
			"US - United States of America", "NJ - NEW JERSEY", "08817");

		common.clickSaveButton();

		this.validateShippingTab(DEFAULT_SHIP_VIA, true, true, "883 US Highway 1", "Edison",
			"US - United States of America", "NJ - NEW JERSEY", "08817-4677");

		this.mainFormContentValidations("50.02", "805.02");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("755.00", "0.00", "0.00");
		common.clickDeleteButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		customers.setCustomerId("AUTOACSON1");
		common.clickDeleteButton();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);
	}

	/**
	 * To verify the address cleansing functionality on Sales Order transaction,
	 * when "Always Check Address Before Calculating Tax" checkbox is  "UnTicked"(22)
	 */
	@Test
	public void AddressCleansing_SalesOrder_AlwaysCheckAddressBeforeCalculatingTax_DisabledTest( )
	{
		AcumaticaPostSignOnPage homePage = commonSetup();

		//Navigate to VertexSetup page 
		homePage.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", false, true, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, false);

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("ADDRCLSOFF", "ADDRCLSOFF-SO-NO-ADDR-CLEANSING", email, false, false,
			"22833 NE 8th St Suite 100", "Sammamish", Country.USA.iso2code, "WA", "98074");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "Ticked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertFalse(result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("ADDRCLSOFF", "301CMPNS01", "1", "1000");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		String result_orderTotal = salesOrdersValidation.getOrderTotal();
		assertTrue(result_orderTotal.equalsIgnoreCase("1,460.00"), "Order Total is not equal");
		String branch = salesOrders.getFinancialInformationBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN - New York"), "Branch name is not displayed");
		String taxZone = salesOrders.getCustomerTaxZoneFromFinancialInformationSection();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax zone is not displayed");

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "22833 NE 8th St Suite 100", "Sammamish",
			"US - United States of America", "WA - WASHINGTON", "98074");

		common.clickSaveButton();

		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "22833 NE 8th St Suite 100", "Sammamish",
			"US - United States of America", "WA - WASHINGTON", "98074");

		this.mainFormContentValidations("100.00", "1,100.00");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("1,000.00", "0.00", "0.00");
		common.clickDeleteButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		customers.setCustomerId("ADDRCLSOFF");
		common.clickDeleteButton();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);
	}

	/**
	 * To verify the address cleansing functionality at following touch points (23)
	 * 1. Branches - Organization-> Organization Structure -> Configure -> Branches
	 * 2. Business Accounts
	 * 3. Contacts
	 */
	@Test
	public void AddressCleansing_BranchesBusinessAccountsContactsTest( )
	{
		commonSetup();

		//Navigate to VertexSetup page 
		this.navigateVertexPage();

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", false, true, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//set basic details of branches page (General Info)
		this.setBasicDetailsInBranchesPageFromGeneralInfoTab("ATMBRANCH1", "AUTOMATION-BRANCH-TEST",
			"AUTOMATION-BRANCH-TEST", email, "US - United States of America", false,
			"2904 S San Tan Village Pkwy Ste 101", "Gilbert", Country.USA.iso2code, "ARIZONA", "85295");

		//Set delivery settings
		customers.clickSubMenu("Delivery Settings");
		branches.isSameAsMainCheckedFromBranchesPage(true);
		branches.isACSCheckedFromBranchesPage(false);

		//click save button
		common.clickSaveButton();

		//validate postal address popup
		this.validateVertexPostalAddressPopup("2904 S San Tan Village Pkwy", "Gilbert", Country.USA.iso2code, "AZ",
			"85295-0859");
		this.selectAction("Ignore");

		//validate delivery settings after address cleansing
		this.validateDeliverySettings(true, false, "2904 S San Tan Village Pkwy Ste 101", "Gilbert",
			Country.USA.iso2code, "AZ - ARIZONA");

		//address cleansing 
		this.addressCleansing("Actions", "Address Cleasing");
		this.validateVertexPostalAddressPopup("2904 S San Tan Village Pkwy", "Gilbert", Country.USA.iso2code, "AZ",
			"85295-0859");
		this.selectAction("Confirm");

		//validate delivery settings after address cleansing
		this.validateDeliverySettingTabAfterAddressCleansing(true, true, "2904 S San Tan Village Pkwy", "Gilbert",
			Country.USA.iso2code, "AZ - ARIZONA", "85295-0859");

		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		//validate general info tab
		this.validateGeneralInfo(false, "2904 S San Tan Village Pkwy", "Ste 101", "Gilbert",
			"US - United States of America", "AZ - ARIZONA", "85295-0859");
		common.clickDeleteButton();

		//Navigate to Business Accounts page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT,
			AcumaticaLeftPanelLink.BUSINESS_ACCOUNTS);

		//set basic details business account
		this.setBusinessAccountPageDetails("ATMBIZACCT", "ATMN-BUSINESS-ACCT", "Active", "ATMN-BUSINESS-ACCT", email,
			false, "2824 N Ramsey Rd Ste 105", "Coeur D Alene", "US", "IDAHO", "83815");

		//navigate to attributes tab
		customers.clickSubMenu("Attributes");
		businessAccount.setIndustryValue("Banking");
		customers.clickSubMenu("Details");
		common.clickSaveButton();

		//address cleansing
		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("2824 N Ramsey Rd", "Coeur D Alene", Country.USA.iso2code, "ID",
			"83815-9004");
		this.selectAction("Ignore");

		//set basic details business account
		this.setBusinessAccountPageDetails("ATMBIZACCT", "ATMN-BUSINESS-ACCT", "Active", "ATMN-BUSINESS-ACCT", email,
			false, "2824 N Ramsey Rd Ste 105", "Coeur D Alene", "US", "IDAHO", "83815");

		//address cleansing
		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("2824 N Ramsey Rd", "Coeur D Alene", Country.USA.iso2code, "ID",
			"83815-9004");
		this.selectAction("Confirm");
		this.validateDetailsTabAfterAddressCleansing(false, "2824 N Ramsey Rd Ste 105", "Coeur D Alene",
			"US - United States of America", "IDAHO", "83815");
		common.clickDeleteButton();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT, AcumaticaLeftPanelLink.CONTACTS);

		//set up basic details in contacts page
		contacts.clickOnNewRecordIcon();
		String resultContactsId = contacts.getContactId();
		assertTrue(resultContactsId.equalsIgnoreCase("<NEW>"), "Contacts ID is not matching");
		String resultContacts = contacts.getContacts();
		assertTrue(resultContacts.equalsIgnoreCase("Contact"), "Contacts is not matching");
		contacts.selectStatus("Active");
		contacts.setSurName("Prof.");
		contacts.setFirstName("ATMNCONTACTFirst");
		contacts.setLastName("ATMNCONTACTLast");
		contacts.clickPencilIcon();
		contacts.switchToBusinessAccountsWindow("Business Accounts");

		//set basic details business account
		this.setBusinessAccountPageDetails("CNCTBIZACT", "BIZ-ACCT-FOR-NEW-CONTACT-ATMN", "Active",
			"BIZ-ACCT-FOR-NEW-CONTACT-ATMN", email, false, "1568 Highlands Dr NE Ste 120", "Issaquah", "US",
			"WASHINGTION", "98029");

		customers.clickSubMenu("Attributes");
		businessAccount.setIndustryValue("E-Commerce");
		customers.clickSubMenu("Details");
		common.clickSaveButton();
		//verify company name
		String result_Company_Name = businessAccount.getCompanyName();
		assertTrue(result_Company_Name.equalsIgnoreCase("BIZ-ACCT-FOR-NEW-CONTACT-ATMN"),
			"Company name is not matching");
		businessAccount.setMail(email);

		this.validateDetailsTabAfterAddressCleansing(false, "1568 Highlands Dr NE Ste 120", "Issaquah",
			"US - United States of America", "WA - WASHINGTION", "98029");
		common.clickSaveButton();

		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("1568 Highlands Dr NE", "Issaquah", Country.USA.iso2code, "WA",
			"98029-6262");
		this.selectAction("Confirm");

		this.validateDetailsTabAfterAddressCleansing(false, "1568 Highlands Dr NE", "Issaquah", "USA", "WA",
			"98029-6262");
		common.clickDeleteButton();

		businessAccount.setBusinessAccount("CNCTBIZACT");
		common.clickDeleteButton();
	}

	/**
	 * To verify the address cleansing functionality at following touch points (24)
	 * 1. Leads
	 * 2. Opportunities
	 * 3. Account Locations
	 */
	@Test
	public void AddressCleansing_Opportunities_Leads_AccountLocations( )
	{
		commonSetup();

		//Navigate to VertexSetup page 
		this.navigateVertexPage();

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", false, true, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to Leads page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT, AcumaticaLeftPanelLink.LEADS);

		//get lead id 
		String resultLeadId = leads.getLeadId();
		assertTrue(resultLeadId.isEmpty(), "Contacts ID is not matching");
		//get status
		String resultStatus = leads.getStatus();
		assertTrue(resultStatus.equalsIgnoreCase("New"), "Status is not matching");
		//get Reason
		String resultReason = leads.getReason();
		assertTrue(resultReason.equalsIgnoreCase("Assign"), "Reason is not matching");

		//select sur name
		leads.setSurName("Dr.");
		leads.setFirstName("ATMNLEADFirst");
		leads.setLastName("ATMNLEADLast");
		leads.clickPencilIcon();
		leads.switchToBusinessAccountsWindow("Business Accounts");

		//set basic details business account
		this.setBusinessAccountPageDetails("LEADBIZACT", "BIZ-ACCT-FOR-NEW-LEAD-ATMN", "Active",
			"BIZ-ACCT-FOR-NEW-LEAD-ATMN", email, false, "3104 E Palouse Hwy Ste A", "Spokane", "US", "WASHINGTION",
			"99223");

		customers.clickSubMenu("Attributes");
		businessAccount.setIndustryValue("Biotechnology");
		customers.clickSubMenu("Details");
		common.clickSaveButton();

		//verify company name
		String result_Company_Name = businessAccount.getCompanyName();
		assertTrue(result_Company_Name.equalsIgnoreCase("BIZ-ACCT-FOR-NEW-LEAD-ATMN"), "Company name is not matching");

		businessAccount.setMail(email);
		this.validateDetailsTabAfterAddressCleansing(false, " 3104 E Palouse Hwy Ste A", "Spokane",
			"US - United States of America", "WA - WASHINGTION", "99223");

		customers.clickSubMenu("Attributes");
		businessAccount.setIndustryValue("Biotechnology");
		businessAccount.setIndustryValue("Software Solution");
		customers.clickSubMenu("Details");
		common.clickSaveButton();

		//get lead id 
		String result_LeadId = leads.getLeadId();
		assertTrue(result_LeadId.equalsIgnoreCase("ATMNLEADLast ATMNLEADFirst, Dr."), "Contacts ID is not matching");

		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("3104 E Palouse Hwy", "Spokane", Country.USA.iso2code, "WA",
			"99223-5169");
		this.selectAction("Confirm");

		this.validateDetailsTabAfterAddressCleansing(true, "3104 E Palouse Hwy", "Spokane",
			"US - United States of America", "WA - WASHINGTON", "99223-5169");
		common.clickDeleteButton();

		//Navigate to Leads page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT,
			AcumaticaLeftPanelLink.BUSINESS_ACCOUNTS);

		//Search business account 
		businessAccount.setBusinessAccountInSearchBox("LEADBIZACT");
		common.clickDeleteButton();

		//Navigate to Business Account page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT,
			AcumaticaLeftPanelLink.BUSINESS_ACCOUNTS);

		//set basic details business account
		this.setBusinessAccountPageDetails("ATMOPPRACT", "ATMN-BUSINESS-ACCT-FOR-OPPORTUNITIES", "Active",
			"ATMN-BUSINESS-ACCT-FOR-OPPORTUNITIES", email, false, "2682 Pearland Pkwy Ste 120", "Pearland", "US",
			"TEXAS", " 77581");

		//navigate to attributes tab
		customers.clickSubMenu("Attributes");
		businessAccount.setIndustryValue("Banking");
		customers.clickSubMenu("Details");
		common.clickSaveButton();

		//Navigate to Opportunities page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT, AcumaticaLeftPanelLink.OPPORTUNITIES);

		opportunities.clickOnNewRecordIcon();
		//get lead id 
		String result_OpportunitiesID = opportunities.getOpportunitiesId();
		assertTrue(result_OpportunitiesID.equalsIgnoreCase("<NEW>"), "Opportunities ID is not matching");
		//get status
		String result_Status = opportunities.getStatus();
		assertTrue(result_Status.equalsIgnoreCase("New"), "Status is not matching");
		//get Reason
		String result_Reason = opportunities.getReason();
		assertTrue(result_Reason.equalsIgnoreCase("Assign"), "Reason is not matching");
		//get Reason
		String result_Stage = opportunities.getStage();
		assertTrue(result_Stage.equalsIgnoreCase("Prospect"), "Stage is not matching");

		opportunities.setSubject("Testing Address Cleansing at Opportunities");
		opportunities.setClassId("INSIDE");
		//get Class Id
		String result_ClassId = opportunities.getClassId();
		assertTrue(result_ClassId.equalsIgnoreCase("INSIDE -Inside Sales"), "Class Id is not matching");
		//get branch
		String resultBranch = opportunities.getBranch();
		assertTrue(resultBranch.equalsIgnoreCase("MAIN - New York"), "Branch is not matching");
		//get tax zone id 
		String taxZoneId = opportunities.getTaxZoneId();
		assertTrue(taxZoneId.equalsIgnoreCase("VERTEX - Vertex Tax Zone"), "Branch is not matching");

		//set business account
		opportunities.setBusinessAccount("ATMOPPRACT");
		customers.clickSubMenu("Contact Info");

		//validate contact info tab
		String addressLine1 = opportunities.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("2682 Pearland Pkwy Ste 120"), "Address Line1 is not matching");
		String addressLine2 = opportunities.getAddressLine2();
		assertTrue(addressLine2.isEmpty(), "Address Line2 is not matching");
		String resultCity = opportunities.getCity();
		assertTrue(resultCity.equalsIgnoreCase("Pearland"), "City is not matching");
		String resultCountry = opportunities.getCountry();
		assertTrue(resultCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState = opportunities.getState();
		assertTrue(resultState.equalsIgnoreCase("TX - TEXAS"), "State is not matching");
		String resultZipCode = opportunities.getZipCode();
		assertTrue(resultZipCode.equalsIgnoreCase("77581"), "Zip Code is not matching");

		//click on save button
		common.clickSaveButton();
		this.addressCleansing("Actions", "Address Cleansing");
		common.clickDeleteButton();

		//Navigate to Leads page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT,
			AcumaticaLeftPanelLink.BUSINESS_ACCOUNTS);

		//Search business account 
		businessAccount.setBusinessAccountInSearchBox("ATMOPPRACT");
		common.clickDeleteButton();

		//Navigate to Business Account page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT,
			AcumaticaLeftPanelLink.BUSINESS_ACCOUNTS);

		//set basic details business account
		this.setBusinessAccountPageDetails("ATMACTLCTN", "ATMN-BUSINESS-ACCT-FOR-ACCOUNT-LOCATIONS", "Active",
			" ATMN-BUSINESS-ACCT-FOR-OPPORTUNITIES", email, false, "4025 Welsh Rd", "Willow Grove", "US",
			"PENNSYLVANIA", "19090");

		//navigate to attributes tab
		customers.clickSubMenu("Attributes");
		businessAccount.setIndustryValue("Cellular Telephone");
		customers.clickSubMenu("Details");
		common.clickSaveButton();

		//Navigate to Account Locations page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT,
			AcumaticaLeftPanelLink.ACCOUNT_LOCATIONS);

		//Set business account
		accountLocations.setBusinessAccount("ATMACTLCTN");
		//validate contact info tab
		String address_Line1 = accountLocations.getAddressLine1();
		assertTrue(address_Line1.equalsIgnoreCase("4025 Welsh Rd"), "Address Line1 is not matching");
		String address_Line2 = accountLocations.getAddressLine2();
		assertTrue(address_Line2.isEmpty(), "Address Line2 is not matching");
		String result_City = accountLocations.getCity();
		assertTrue(result_City.equalsIgnoreCase("Willow Grove"), "City is not matching");
		String result_Country = accountLocations.getCountry();
		assertTrue(result_Country.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String result_State = accountLocations.getState();
		assertTrue(result_State.equalsIgnoreCase("PA - PENNSYLVANIA"), "State is not matching");
		String result_ZipCode = accountLocations.getZipCode();
		assertTrue(result_ZipCode.equalsIgnoreCase("19090"), "Zip Code is not matching");

		//click on save button
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("4025 Welsh Rd", "Willow Grove", "USA", "PA", "19090-2901");
		this.selectAction("Confirm");
		common.clickDeleteButton();
	}

	/**
	 * To verify the address cleansing functionality at following touch points (25)
	 * 1. Customer Locations
	 * 2. Cash Sales
	 */
	@Test
	public void AddressCleansing_CustomerLocations_CashSales( )
	{
		commonSetup();

		//Navigate to VertexSetup page 
		this.navigateVertexPage();

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", false, true, true);

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);

		//Implemented predefined settings (common method name)
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("CUSTLCTNS", "CUSTOMERFOR-ADDRCLEANSING-AT-ACCTLOCATIONS", email, false,
			false, "2495 Iron Point Rd #11", "Folsom", Country.USA.iso2code, "CA", " 95630");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertTrue(!result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		common.clickSaveButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE,
			AcumaticaLeftPanelLink.CUSTOMER_LOCATIONS);

		//Enter customer id
		customerLocations.setCustomerId("CUSTLCTNS");
		//validate contact info tab
		customerLocations.isSameAsMainChecked(true);
		String address_Line1 = customerLocations.getAddressLine1();
		assertTrue(address_Line1.equalsIgnoreCase("2495 Iron Point Rd #11"), "Address Line1 is not matching");
		String address_Line2 = customerLocations.getAddressLine2();
		assertTrue(address_Line2.equalsIgnoreCase("APT #203"), "Address Line2 is not matching");
		String result_City = customerLocations.getCity();
		assertTrue(result_City.equalsIgnoreCase("Folsom"), "City is not matching");
		String result_Country = customerLocations.getCountry();
		assertTrue(result_Country.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String result_State = customerLocations.getState();
		assertTrue(result_State.equalsIgnoreCase("CA - CALIFORNIA"), "State is not matching");
		String result_ZipCode = customerLocations.getZipCode();
		assertTrue(result_ZipCode.equalsIgnoreCase("95630"), "Zip Code is not matching");
		customerLocations.isAcsChecked(false);

		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("2495 Iron Point Rd", "Folsom", Country.USA.iso2code, "CA", "95630-8710");
		common.closeAddressCleansingPopUp();

		String addressLine1 = customerLocations.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("2495 Iron Point Rd #11"), "Address Line1 is not matching");
		String addressLine2 = customerLocations.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("APT #203"), "Address Line2 is not matching");
		String resultZipCode = customerLocations.getZipCode();
		assertTrue(resultZipCode.equalsIgnoreCase("95630"), "Zip Code is not matching");

		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("2495 Iron Point Rd", "Folsom", Country.USA.iso2code, "CA", "95630-8710");
		this.selectAction("Ignore");

		customerLocations.isSameAsMainChecked(false);
		customerLocations.isAcsChecked(false);

		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("2495 Iron Point Rd", "Folsom", Country.USA.iso2code, "CA", "95630-8710");
		this.selectAction("Confirm");

		//validate contact info tab
		customerLocations.isSameAsMainChecked(false);
		String addressLine_1 = customerLocations.getAddressLine1();
		assertTrue(addressLine_1.equalsIgnoreCase("2495 Iron Point Rd"), "Address Line1 is not matching");
		String addressLine_2 = customerLocations.getAddressLine2();
		assertTrue(addressLine_2.equalsIgnoreCase("Apt 203"), "Address Line2 is not matching");
		String resultCity = customerLocations.getCity();
		assertTrue(resultCity.equalsIgnoreCase("Folsom"), "City is not matching");
		String resultCountry = customerLocations.getCountry();
		assertTrue(resultCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState = customerLocations.getState();
		assertTrue(resultState.equalsIgnoreCase("CA - CALIFORNIA"), "State is not matching");
		String resultZipCode1 = customerLocations.getZipCode();
		assertTrue(resultZipCode1.equalsIgnoreCase("95630-8710"), "Zip Code is not matching");
		customerLocations.isAcsChecked(false);

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Set customer
		customers.setCustomerId("CUSTLCTNS");
		common.clickDeleteButton();

		//Customer page actions and validations
		this.customersPageActionsAndValidations("CASALEADDR", "CUSTOMERFOR-ADDRCLEANSING-AT-CASHSALE", email, false,
			false, "7340 HWY 26 West Ste 1000", "Oberlin", Country.USA.iso2code, "LA", "70655");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean resultsSameAsMain = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(resultsSameAsMain, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean resultAcs = customers.isDeliveryAcsChecked(false);
		assertFalse(resultAcs, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		common.clickSaveButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CASH_SALES);

		//validate basic details of cash sales page
		String resultType = cashSales.getType();
		assertTrue(resultType.equalsIgnoreCase("Cash Sale"), "Type is not matching");
		String resultReferenceNbr = cashSales.getReferenceNbr();
		assertTrue(resultReferenceNbr.equalsIgnoreCase("<NEW>"), "Reference NBR is not matching");

		//set customer
		cashSales.setCustomer("CASALEADDR");
		String resultLocation = cashSales.getLocation();
		assertTrue(resultLocation.equalsIgnoreCase("MAIN - Primary Location"), "Location is not matching");

		//set new row 
		cashSales.clickPlusIconForAddNewRecord();
		cashSales.setInventoryId("301CMPNS01");
		cashSales.setQuantity("1");
		cashSales.setUnitPrice("1000");

		//switch to Financial tab
		customers.clickSubMenu("Financial Details");
		//verify branch in financial tab
		String resultBranch = cashSales.getBranch();
		assertTrue(resultBranch.equalsIgnoreCase("MAIN - New York"), "Branch is not matching");

		//verify Customer tax zone  in financial tab
		String resultCustomerTaxZone = cashSales.getCustomerTaxZone();
		assertTrue(resultCustomerTaxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"),
			"Customer tax zone is not matching");

		//switch to Billing Address tab
		customers.clickSubMenu("Billing Address");
		//validate billing address tab
		cashSales.verifyOverrideFromBillingAddressTab(false);
		cashSales.verifyOverrideFromBillingAddressTab(false);
		String resultAddressLine1 = cashSales.getAddressLine1();
		assertTrue(resultAddressLine1.equalsIgnoreCase("7340 HWY 26 West Ste 1000"), "Address Line1 is not matching");
		String resultAddressLine2 = cashSales.getAddressLine2();
		assertTrue(resultAddressLine2.isEmpty(), "Address Line2 is not matching");
		String resultCity1 = customerLocations.getCity();
		assertTrue(resultCity1.equalsIgnoreCase("Oberlin"), "City is not matching");
		String resultCountry1 = customerLocations.getCountry();
		assertTrue(resultCountry1.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState1 = customerLocations.getState();
		assertTrue(resultState1.equalsIgnoreCase("LA - LOUISIANA"), "State is not matching");
		String resultZipCode2 = customerLocations.getZipCode();
		assertTrue(resultZipCode2.equalsIgnoreCase("70655"), "Zip Code is not matching");

		common.clickSaveButton();
		cashSales.verifyOverrideFromBillingAddressTab(false);
		cashSales.verifyOverrideFromBillingAddressTab(false);

		cashSales.verifyOverrideFromBillingAddressTab(true);
		//Address Cleansing
		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("7340 Highway 26", " Oberlin", Country.USA.iso2code, "LA", "70655-3407");
		this.selectAction("Ignore");

		//verify after address cleansing
		String resultAddressLine11 = cashSales.getAddressLine1();
		assertTrue(resultAddressLine11.equalsIgnoreCase("7340 HWY 26 West Ste 1000"), "Address Line1 is not matching");
		String resultAddressLine21 = cashSales.getAddressLine2();
		assertTrue(resultAddressLine21.isEmpty(), "Address Line2 is not matching");
		String resultCity11 = customerLocations.getCity();
		assertTrue(resultCity11.equalsIgnoreCase("Oberlin"), "City is not matching");
		String resultCountry11 = customerLocations.getCountry();
		assertTrue(resultCountry11.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState11 = customerLocations.getState();
		assertTrue(resultState11.equalsIgnoreCase("LA - LOUISIANA"), "State is not matching");
		String resultZipCode21 = customerLocations.getZipCode();
		assertTrue(resultZipCode21.equalsIgnoreCase("70655"), "Zip Code is not matching");
		cashSales.verifyOverrideFromBillingAddressTab(false);
		cashSales.verifyOverrideFromBillingAddressTab(true);

		//Address Cleansing
		this.addressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("7340 Highway 26", " Oberlin", Country.USA.iso2code, "LA", "70655-3407");
		this.selectAction("Confirm");

		//verify after address cleansing
		String resultAddressLine12 = cashSales.getAddressLine1();
		assertTrue(resultAddressLine12.equalsIgnoreCase("7340 Highway 26"), "Address Line1 is not matching");
		String resultAddressLine22 = cashSales.getAddressLine2();
		assertTrue(resultAddressLine22.equalsIgnoreCase("Ste 1000"), "Address Line2 is not matching");
		String resultCity12 = customerLocations.getCity();
		assertTrue(resultCity12.equalsIgnoreCase("Oberlin"), "City is not matching");
		String resultCountry12 = customerLocations.getCountry();
		assertTrue(resultCountry12.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String resultState12 = customerLocations.getState();
		assertTrue(resultState12.equalsIgnoreCase("LA - LOUISIANA"), "State is not matching");
		String resultZipCode22 = customerLocations.getZipCode();
		assertTrue(resultZipCode22.equalsIgnoreCase("70655-3407"), "Zip Code is not matching");
		cashSales.verifyOverrideFromBillingAddressTab(true);
		cashSales.verifyOverrideFromBillingAddressTab(true);

		common.clickDeleteButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Set customer
		customers.setCustomerId("CASALEADDR");
		common.clickDeleteButton();
	}

	/**
	 * To verify the address cleansing functionality at following touch points (28)
	 * 1. Invoice
	 * 2. Invoice and Memos
	 * 3. Shipments
	 */
	@Test
	public void AddressCleansing_InvoiceMemosShipmentsTest( )
	{
		commonSetup();

		//Navigate to VertexSetup page 
		this.navigateVertexPage();

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);

		//Implemented predefined settings (common method name)
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("INVOICEADR", "CUSTOMERFOR-ADDR-CLEANSING-AT-INVOICE", email, false,
			false, "5200 Westpointe Plaza Drive", "Columbus", Country.USA.iso2code, "OH", "43228");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean resultsSameAsMain = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(resultsSameAsMain, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean resultAcs = customers.isDeliveryAcsChecked(false);
		assertFalse(resultAcs, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		common.clickSaveButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.SALES_ORDERS, AcumaticaLeftPanelLink.INVOICE);
		//Set basic details to Invoice page
		String orderType = invoice.getOrderType();
		assertTrue(orderType.equalsIgnoreCase("Invoice"), "Order Type is not matching");
		String referenceNbr = invoice.getReferenceNBR();
		assertTrue(referenceNbr.equalsIgnoreCase("<NEW>"), "Reference Nbr is not matching");

		invoice.setCustomer("INVOICEADR");
		String branch = invoice.getBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN"), "Branch is not matching");
		invoice.setInventory("ADDESIGN");
		invoice.setQuantity("2");
		invoice.setUnitPrice("500");

		customers.clickSubMenu("Financial Details");
		String finananceDetailsBranch = invoice.getFinanceDetailBranch();
		assertTrue(finananceDetailsBranch.equalsIgnoreCase("MAIN - New York"), "Branch is not matching");
		String customerTaxZone = invoice.getCustomerTaxZone();
		assertTrue(customerTaxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Customer tax zone is not matching");

		customers.clickSubMenu("Billing Address");
		invoice.isOverrideAddressCheckedFromBranchesPage(false);

		String addressLine1 = invoice.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("5200 Westpointe Plaza Drive"), "Address Line1 is not matching");
		String addressLine2 = invoice.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Suite #203"), "Address Line2 is not matching");
		String city = invoice.getCity();
		assertTrue(city.equalsIgnoreCase("Columbus"), "City is not matching");
		String invoiceCountry = invoice.getCountry();
		assertTrue(invoiceCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		String state = invoice.getState();
		assertTrue(state.equalsIgnoreCase("OH - OHIO"), "State is not matching");
		String postalCode = invoice.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("43228"), "Postal code is not matching");

		common.clickSaveButton();
		boolean status = common.isEnabledAddressCleansingOption("Actions", "Address Cleansing");
		assertFalse(status, "Address cleansing option enabled");

		invoice.isOverrideAddressCheckedFromBranchesPage(true);
		status = common.isEnabledAddressCleansingOption("Actions", "Address Cleansing");
		assertTrue(status, "Address cleansing option enabled");

		common.selectAddressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("5200 Westpointe Plaza Dr", "Columbus", "USA", "OH", "43228-9126");
		this.selectAction("Confirm");

		invoice.isOverrideAddressCheckedFromBranchesPage(true);

		addressLine1 = invoice.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("5200 Westpointe Plaza Drive"), "Address Line1 is not matching");
		addressLine2 = invoice.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Suite #203"), "Address Line2 is not matching");
		city = invoice.getCity();
		assertTrue(city.equalsIgnoreCase("Columbus"), "City is not matching");
		invoiceCountry = invoice.getCountry();
		assertTrue(invoiceCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		state = invoice.getState();
		assertTrue(state.equalsIgnoreCase("OH - OHIO"), "State is not matching");
		postalCode = invoice.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("43228-9126"), "Postal code is not matching");
		//Delete created invoice basic details
		common.clickDeleteButton();

		//Navigate to Invoice and Memos page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE,
			AcumaticaLeftPanelLink.INVOICE_AND_MEMOS);
		//Create Invoice and Memos basic details
		orderType = invoiceAndMemo.getOrderType();
		assertTrue(orderType.equalsIgnoreCase("Invoice"), "Order Type is not matching");
		referenceNbr = invoiceAndMemo.getReferenceNBR();
		assertTrue(referenceNbr.equalsIgnoreCase("<NEW>"), "Reference Nbr is not matching");

		invoice.setCustomer("INVOICEADR");
		customers.clickSubMenu("Document Details");
		branch = invoiceAndMemo.getBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN"), "Branch is not matching");
		invoiceAndMemo.setInventory("ADDESIGN");
		invoiceAndMemo.setQuantity("2");
		invoiceAndMemo.setUnitPrice("500");

		customers.clickSubMenu("Financial Details");
		finananceDetailsBranch = invoiceAndMemo.getFinanceDetailBranch();
		assertTrue(finananceDetailsBranch.equalsIgnoreCase("MAIN - New York"), "Branch is not matching");
		customerTaxZone = invoiceAndMemo.getCustomerTaxZone();
		assertTrue(customerTaxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Customer tax zone is not matching");

		customers.clickSubMenu("Billing Address");
		invoiceAndMemo.isOverrideAddressCheckedFromBranchesPage(false);
		invoiceAndMemo.isACSCheckedFromBranchesPage(false);

		addressLine1 = invoiceAndMemo.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("5200 Westpointe Plaza Dr"), "Address Line1 is not matching");
		addressLine2 = invoiceAndMemo.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Ste 203"), "Address Line2 is not matching");
		city = invoiceAndMemo.getCity();
		assertTrue(city.equalsIgnoreCase("Columbus"), "City is not matching");
		invoiceCountry = invoiceAndMemo.getCountry();
		assertTrue(invoiceCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		state = invoiceAndMemo.getState();
		assertTrue(state.equalsIgnoreCase("OH - OHIO"), "State is not matching");
		postalCode = invoiceAndMemo.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("43228"), "Postal code is not matching");

		common.clickSaveButton();
		status = common.isEnabledAddressCleansingOption("Actions", "Address Cleansing");
		assertFalse(status, "Address cleansing option enabled");

		invoiceAndMemo.isOverrideAddressCheckedFromBranchesPage(true);
		status = common.isEnabledAddressCleansingOption("Actions", "Address Cleansing");
		assertTrue(status, "Address cleansing option enabled");

		common.selectAddressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("5200 Westpointe Plaza Dr", "Columbus", "USA", "OH", "43228-9126");
		this.selectAction("Confirm");

		invoice.isOverrideAddressCheckedFromBranchesPage(true);

		addressLine1 = invoiceAndMemo.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("5200 Westpointe Plaza Dr"), "Address Line1 is not matching");
		addressLine2 = invoiceAndMemo.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Ste 203"), "Address Line2 is not matching");
		city = invoiceAndMemo.getCity();
		assertTrue(city.equalsIgnoreCase("Columbus"), "City is not matching");
		invoiceCountry = invoiceAndMemo.getCountry();
		assertTrue(invoiceCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		state = invoiceAndMemo.getState();
		assertTrue(state.equalsIgnoreCase("OH - OHIO"), "State is not matching");
		postalCode = invoiceAndMemo.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("43228-9126"), "Postal code is not matching");
		//Delete created invoice and memo basic details
		common.clickDeleteButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.SALES_ORDERS, AcumaticaLeftPanelLink.SHIPMENTS);
		//Create Shipment basic details
		String shipmentNBR = shipment.getShipmentNBR();
		assertTrue(shipmentNBR.equalsIgnoreCase("<NEW>"), "Shipment NBR is not matching");
		String shipmentType = shipment.getOrderType();
		assertTrue(shipmentType.equalsIgnoreCase("Shipment"), "Type is not matching");

		shipment.setCustomer("INVOICEADR");
		shipment.setWarehouse("WHNORTH");
		customers.clickSubMenu("Shipping Settings");
		shipment.isOverrideAddressCheckedFromBranchesPage(false);
		shipment.isACSCheckedFromBranchesPage(false);

		addressLine1 = shipment.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("5200 Westpointe Plaza Drive"), "Address Line1 is not matching");
		addressLine2 = shipment.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Suite #203"), "Address Line2 is not matching");
		city = shipment.getCity();
		assertTrue(city.equalsIgnoreCase("Columbus"), "City is not matching");
		invoiceCountry = shipment.getCountry();
		assertTrue(invoiceCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		state = shipment.getState();
		assertTrue(state.equalsIgnoreCase("OH - OHIO"), "State is not matching");
		postalCode = shipment.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("43228"), "Postal code is not matching");

		common.clickSaveButton();
		status = common.isEnabledAddressCleansingOption("Actions", "Address Cleansing");
		assertFalse(status, "Address cleansing option enabled");

		invoiceAndMemo.isOverrideAddressCheckedFromBranchesPage(true);
		status = common.isEnabledAddressCleansingOption("Actions", "Address Cleansing");
		assertTrue(status, "Address cleansing option enabled");

		common.selectAddressCleansing("Actions", "Address Cleansing");
		this.validateVertexPostalAddressPopup("5200 Westpointe Plaza Dr", "Columbus", "USA", "OH", "43228-9126");
		this.selectAction("Confirm");

		shipment.isOverrideAddressCheckedFromBranchesPage(true);

		addressLine1 = shipment.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase("5200 Westpointe Plaza Dr"), "Address Line1 is not matching");
		addressLine2 = shipment.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase("Ste 203"), "Address Line2 is not matching");
		city = invoiceAndMemo.getCity();
		assertTrue(city.equalsIgnoreCase("Columbus"), "City is not matching");
		invoiceCountry = shipment.getCountry();
		assertTrue(invoiceCountry.equalsIgnoreCase("US - United States of America"), "Country is not matching");
		state = shipment.getState();
		assertTrue(state.equalsIgnoreCase("OH - OHIO"), "State is not matching");
		postalCode = shipment.getZipCode();
		assertTrue(postalCode.equalsIgnoreCase("43228-9126"), "Postal code is not matching");
		//Delete created invoice and memo basic details
		common.clickDeleteButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);
		customers.setCustomerId("INVOICEADR");
		//Delete created customer id
		common.clickDeleteButton();
	}
}
