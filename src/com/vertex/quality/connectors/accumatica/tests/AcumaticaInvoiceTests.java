package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * To create an Invoice transaction (with Address Cleansing at Sales Order)
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaInvoiceTests extends AcumaticaBaseTest
{
	/**
	 * To create an Invoice transaction (with Address Cleansing at Sales Order)
	 */
	@Test
	public void InvoiceTest( )
	{
		commonSetup();

		//Navigate to VertexSetup page 
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.TAXES, AcumaticaLeftPanelLink.VERTEX_SETUP);

		//predefined settings from vertex setup page
		vertexSetup.predefinedSettingsFromVertexSetupPage(true, "AU0561", true, false, true);

		//Navigate to Enable/Disable page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.COMMON_SETTINGS,
			AcumaticaLeftPanelLink.ENABLE_DISABLE_FEATURES);
		enabledDisableFeatures.predefinedSettingsFromEnableDisableFeaturesPage(true, true);

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("TGIFRIDAYS", "301CMPNS01", "3", "500");
		this.setSalesOrderPageActions("FOBD1", "301CMPNS01", "1", "75");
		this.validateSalesOrderPage(DEFAULT_SHIP_VIA, false, false, "100 S Clinton St # A", "Athens",
			"US - United States of America", "AL - ALABAMA", "35611");
		this.validateVertexPostalAddressPopup();
		this.verifySalesOrderPageConditions(true, true);
		this.validateAddressShippingTab(true, false, "100 S Clinton St", "Athens", "US - United States of America",
			"AL - ALABAMA", "35611-2665");
		this.mainFormContentValidations("4.00", "1,716.75");
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.TAX_DETAILS);
		this.validateTotalTabDetails("1,575.00", "0.00", "0.00", "4.00", "1,716.75", "4.00", "1,716.75", "0.00", "0.00",
			"1,716.75");
		this.validateAddressShippingTab(true, true, "100 S Clinton St", "Athens", "US - United States of America",
			"AL - ALABAMA", "35611-2665");
		fail();
	}

	/**
	 * To verify the XML logging of a Invoice transaction with "Discount Amounts" at line level
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void CreateInvoice_XML_LoggingTest( )
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
		this.setSalesOrderPageActions("TGIFRIDAYS", "301CMPNS01", "3", "500");

		salesOrders.clickPlusIconForAddNewRecord();
		salesOrders.setInventoryIdAndWareHouse("FOBD1", "WHOLESALE", "Shipping Charges");
		salesOrders.setQuantity("1");
		salesOrders.setUnitPrice("75");

		//Navigate to financial settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.FINANCIAL_SETTINGS);
		String branch = salesOrders.getFinancialInformationBranch();
		assertTrue(branch.equalsIgnoreCase("MAIN - New York"), "Branch name is not displayed");
		String taxZone = salesOrders.getCustomerTaxZoneFromFinancialInformationSection();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Tax zone is not displayed");

		customers.clickSubMenu(AcumaticaMainPanelTab.SHIPPING_SETTINGS);
		this.validateShippingTab(DEFAULT_SHIP_VIA, false, false, "100 S Clinton St # A", "Athens",
			"US - United States of America", "AL - ALABAMA", "35611");

		common.clickSaveButton();

		this.validateVertexPostalAddressPopup("100 S Clinton St", "Athens", "USA", "AL", "35611-2665");
		this.selectAction("Confirm");

		this.validateShippingTab(DEFAULT_SHIP_VIA, true, true, "100 S Clinton St", "Athens",
			"US - United States of America", "AL - ALABAMA", "35611-2665");
		this.mainFormContentValidations("131.40", "1,591.40");

		customers.clickSubMenu(AcumaticaMainPanelTab.TOTALS);
		this.validateTotalsTab("1,460.00", "0.00", "0.00", "131.40", "4.00", "1,591.40", "4.00", "1,591.40", "0.00",
			"0.00", "1,591.40");

		//	FINANCE > Taxes > Configuration (icon) > VERTEX INTEGRATION > Vertex LogHistory (XML validations not yet completed )
	}

	/**
	 * To validate the Tax amounts in "Invoice" and to validate the XML information (32)
	 */
	@Test
	public void Invoice_TaxAmounts_Test( )
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

		//Navigate to customers page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("INVOICMEMO", "CUSTOMERFOR-TAX-CHECK-INVOICE-AND-MEMOS", email, false,
			false, "5200 Westpointe Plaza Drive", "Columbus", Country.USA.iso2code, "OH", "43228");
		VertexLogger.log("Successfully entered customer page values and validations", AcumaticaSalesOrderTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu("Delivery Settings");

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "5200 Westpointe Plaza Drive", "Columbus",
			Country.USA.iso2code, "OH", "43228");
		common.clickSaveButton();

		//Navigate to Invoice page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.SALES_ORDERS, AcumaticaLeftPanelLink.INVOICE);
		//Set basic details to Invoice page
		String orderType = invoice.getOrderType();
		assertTrue(orderType.equalsIgnoreCase("Invoice"), "Order Type is not matching");
		String referenceNbr = invoice.getReferenceNBR();
		assertTrue(referenceNbr.equalsIgnoreCase("<NEW>"), "Reference Nbr is not matching");

		invoice.setCustomer("INVOICMEMO");
		String invoiceBranch = invoice.getBranch();
		assertTrue(invoiceBranch.equalsIgnoreCase("MAIN"), "Branch is not matching");
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
		common.clickSaveButton();
		invoice.isOverrideAddressCheckedFromBranchesPage(false);
	}
}
