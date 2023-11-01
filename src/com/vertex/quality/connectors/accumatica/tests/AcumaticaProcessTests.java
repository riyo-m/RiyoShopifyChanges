package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.pages.*;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * This class is for Acumatica Process/invoice/order related tests
 *
 * @author Saidulu kodadala
 */
public class AcumaticaProcessTests extends AcumaticaBaseTest
{
	/**
	 * To "Prepare Invoice" using the "Process" orders functionality (34)
	 */
	@Test
	public void ProcessOrders_PrepareInvoiceTest( )
	{
		commonSetup();

		//Navigate to Vertex Setup Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.TAXES);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.VERTEX_SETUP);

		//Switch to main frame (It's actual page for doing an actions on main page)
		common.switchToMainFrame();

		//Implemented predefined settings (common method name)
		String[] branchAndCompanyCode = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);

		//Implemented predefined settings (common method name)
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate to customers page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("PROCESSINV", "CUSTOMER-FOR-FOR-PROCESS-ORDERS-PREPARE-INVOICE", email,
			false, false, "200 SE 7th St", "Topeka", Country.USA.iso2code, "KS", "66603");
		VertexLogger.log("Successfully entered customer page values and validations", AcumaticaSalesOrderTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu("Delivery Settings");

		//Validate Delivery Settings details
		customers.setShipVia(DEFAULT_SHIP_VIA);
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertFalse(result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
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

		customers.clickSubMenu("Financial Settings");
		this.validateFinancialTab(false, false);

		customers.clickSubMenu("Shipping Settings");
		salesOrdersValidation.setShipViaFromShippingInformationSection(DEFAULT_SHIP_VIA);
		boolean status = salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(false);
		assertFalse(status, "Override Address is not UnTicked");
		boolean acsStatus = salesOrdersValidation.verifyAcsFromShipToInfoSection(false);
		assertFalse(acsStatus, "ACS is not UnTicked");
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("200 SE 7th St", "Topeka", "USA", "KS", "66603-3922");
		this.selectAction("Confirm");
		this.mainFormContentValidations("66.34", "791.34");

		customers.clickSubMenu("Tax Details");
	}

	/**
	 * To check the "Credit Memo (CM) Order" functionality in Acumatica (35)
	 */
	@Test
	public void MerchandizeReturns_CreditMemoOrder_XMLValidation( )
	{
		common = new AcumaticaCommonPage(driver);
		vertexSetup = new AcumaticaVertexSetupPage(driver);
		enabledDisableFeatures = new AcumaticaEnableDisableFeaturesPage(driver);
		customers = new AcumaticaCustomersPage(driver);
		salesOrders = new AcumaticaSalesOrdersPage(driver);
		AcumaticaSalesOrdersValidationPage salesOrdersValidation = new AcumaticaSalesOrdersValidationPage(driver);
		//Launch acumatica url and login into application
		logInAsAdminUser();

		//Get the data from enum

		//Navigate to Vertex Setup Page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.TAXES);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.VERTEX_SETUP);

		//Switch to main frame (It's actual page for doing an actions on main page)
		common.switchToMainFrame();

		//Implemented predefined settings (common method name)
		String[] branchAndCompanyCode = vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false,
			true);
		assertTrue(branchAndCompanyCode[0].equalsIgnoreCase(DEFAULT_BRANCH), "Branch column should have New York");
		assertTrue(branchAndCompanyCode[1].equalsIgnoreCase(DEFAULT_COMPANY_CODE),
			"Company Code column should have NewYork01");

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);

		//Implemented predefined settings (common method name)
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("TGIFRIDAYS", "301CMPNS01", "3", "400");

		customers.clickSubMenu("Financial Settings");
		this.validateFinancialTab(false, false);

		customers.clickSubMenu("Shipping Settings");
		salesOrdersValidation.setShipViaFromShippingInformationSection(DEFAULT_SHIP_VIA);
		boolean status = salesOrdersValidation.setOverrideAddressCheckboxFromShipToInfoSection(false);
		assertFalse(status, "Override Address is not UnTicked");
		boolean acsStatus = salesOrdersValidation.verifyAcsFromShipToInfoSection(false);
		assertFalse(acsStatus, "ACS is not UnTicked");
		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("100 S Clinton St", "Athens", "USA", "AL", "35611-2665");
		this.selectAction("Confirm");
		this.mainFormContentValidations("108.00", "1,308.00");

		customers.clickSubMenu("Tax Details");
	}
}
