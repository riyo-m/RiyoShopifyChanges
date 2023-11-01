package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

/**
 * To create a Sales Order transaction with FRIEIGHT line item (with Address Cleansing at Sales
 * Order)
 *
 * @author Saidulu Kodadala
 */
public class AcumaticaFreightTests extends AcumaticaBaseTest
{
	/**
	 * To create a Sales Order transaction with FREIGHT line item (with Address Cleansing at Sales
	 * Order)
	 */
	@Test
	public void SalesOrderFreightTest( )
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
		salesOrders.clickPencilIconAtCustomer();

		//Customer page actions and validations
		this.customersPageActionsAndValidations("KMARTCUST", "KMARTNEWCUSTOMER", email, false, false,
			"3505 Construction Way", "Winnemucca", Country.USA.iso2code, "NV", "89445");
		VertexLogger.log("Successfully entered customer page values and validations", AcumaticaSalesOrderTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "3505 Construction Way", "Winnemucca",
			"US - United States of America", "CANV - NEVADA", "89445");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();
		common.switchToParentWindow("main");

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("KMARTNEWCUSTOMER", "301CMPNS01", "3", "325");
		this.validateSalesOrderPage(DEFAULT_SHIP_VIA, false, false, "3505 Construction Way", "Winnemucca",
			"US - United States of America", "NV - NEVADA", "89445");
		this.validateVertexPostalAddressPopup();
		this.verifySalesOrderPageConditions(true, true);
		this.validateAddressShippingTab(true, false, "3505 Construction Way", "Winnemucca",
			"US - United States of America", "NV - NEVADA", "89445-3694");
		this.mainFormContentValidations("4.00", "1,282.20");
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.TAX_DETAILS);
		this.validateTotalTabDetails("1,200.00", "0.00", "82.20", "4.00", "1,282.20", "4.00", "1,282.20", "0.00",
			"0.00", "1,282.20");

		//delete created customer record
		this.deleteCreatedCustomerRecord("KMARTNEWCUSTOMER");
	}
}
