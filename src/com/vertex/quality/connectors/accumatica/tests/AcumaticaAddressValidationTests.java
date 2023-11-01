package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaLoginPage;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To verify the acumatica address validations
 *
 * @author saidulu kodadala
 */
public class AcumaticaAddressValidationTests extends AcumaticaBaseTest
{
	/**
	 * To configuration of acumatica enable disable connector settings
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void Enable_DisableConnector( )
	{
		commonSetup();

		//Navigate to VertexSetup page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		String[] branchAndCompanyCode = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(false, false);

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("REDMOND", "ENABLEDISABLE  CONNECTOR", email, false, false,
			"2495 Iron Point Rd #11", "Folsom", Country.USA.iso2code, "CA", "95630");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "2495 Iron Point Rd #11", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("REDMOND", "301CMPNS01", "1", "500");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		this.validateFinancialTab(false, false);

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "2495 Iron Point Rd #11", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630");
		common.clickSaveButton();
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "2495 Iron Point Rd #11", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630");
		this.mainFormContentValidations("0.00", "500.00");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("500.00", "0.00", "0.00", "0.00", "1.00", "500.00", "1.00", "500.00", "0.00", "0.00",
			"500.00");
		common.clickDeleteButton();

		common.signOutFromAcumatica();

		String userName = returnUsername();
		String password = returnPassword();
		AcumaticaLoginPage loginPage
			= launchLoginPage();//TODO eliminate this because 'signOutFromAcumatica()' should have returned an AcumaticaLoginPage
		logInAsUser(loginPage, userName, password, "Vertex");

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//Error#0 --- The requested resource is not available. Vertex feature is not Enabled
		String errorMessage = vertexSetup.getErrorMessage();
		assertTrue(errorMessage.equalsIgnoreCase("Error #0"), "Error Message is not displayed");

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//predefined settings from vertex setup page
		String[] branchAndCompanyCode1 = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode1[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode1[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("REDMOND", "301CMPNS01", "1", "500");

		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		this.validateFinancialTab(false, false);

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "2495 Iron Point Rd #11", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630");
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("2495 Iron Point Rd", "Folsom", "USA", "CA", "95630-8710");
		this.selectAction("Confirm");
		this.validateShippingTab(DEFAULT_SHIP_VIA, true, true, "2495 Iron Point Rd", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630-8710");
		this.mainFormContentValidations("38.75", "538.75");

		customers.clickSubMenu(AcumaticaMainPanelTab.TAX_DETAILS);
		//this.validateTaxDetailsTab();

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("500.00", "0.00", "0.00", "38.75", "1.00", "538.75", "1.00", "538.75", "0.00", "0.00",
			"538.75");
		common.clickDeleteButton();

		common.signOutFromAcumatica();
		AcumaticaLoginPage loginPage2
			= launchLoginPage();//TODO eliminate this because 'signOutFromAcumatica()' should have returned an AcumaticaLoginPage
		logInAsUser(loginPage2, userName, password, "Vertex");

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		String[] branchAndCompanyCode2 = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode2[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode2[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");
		vertexSetup.setConnectorActiveCheckbox(false);
		common.clickSaveButton();

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(false, false);

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("REDMOND", "301CMPNS01", "1", "1000");

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "2495 Iron Point Rd #11", "Folsom",
			"US - United States of America", "CA - CALIFORNIA", "95630");
		common.clickSaveAndAcceptAlert();
		common.clickSaveAndAcceptAlert();
		common.clickDeleteButton();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		vertexSetup.setConnectorActiveCheckbox(true);
		common.clickSaveButton();

		//Navigate to Customers Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		customers.setCustomerId("REDMOND");
		common.clickDeleteButton();
	}
}
