package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaCustomerClassesPage;
import com.vertex.quality.connectors.accumatica.pages.AcumaticaEnableDisableFeaturesPage;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * To create a new Customer Class in Acumatica
 *
 * @author saidulu kodadala
 */
public class AcumaticaCustomerTests extends AcumaticaBaseTest
{
	/**
	 * Configure Customer Class
	 *
	 * TODO JIRA lacks a story for the corresponding feature
	 *
	 * @author sai ssalisbury
	 */
	@Ignore
	@Test
	public void ConfigureCustomerClassTest( )
	{
		AcumaticaEnableDisableFeaturesPage toggleFeaturesPage = standardTestSetup();

		//Navigate to Menu -> Sub menu ->Left page
		AcumaticaCustomerClassesPage customerClassesPage = toggleFeaturesPage.openMenuPage(
			AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMER_CLASSES);

		//Implemented customer class page actions (common method)
		customerClassesPage.customerClassPageActions("ATMNCLSNEW", "AUTOMATIONCLASSNEW", "US", "VERTEX",
			DEFAULT_SHIP_VIA, "Disabled", "Mailing Settings");
		//TODO where's the verification?
	}

	/**
	 * To create a new Customer Class. Then to create a new Customer Code within that new Customer
	 * Class.
	 * Then use that newly created customer in Sales Order creation
	 *
	 * @author saidulu kodadala
	 */
	@Test
	public void ConfigureCustomerCodeTest( )
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

		//Navigate to Customer Classes Page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE,
			AcumaticaLeftPanelLink.CUSTOMER_CLASSES);
		customerClass.customerClassPageActions("EMC2CLASS1", "EMC2AUTOMATIONCLASSNEW", Country.USA.iso2code, "VERTEX",
			"UPS", "Disabled", "Mailing Settings");
		VertexLogger.log("Successfully created customer class", AcumaticaSalesOrderTests.class);

		//Navigate to Customers page
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.CUSTOMERS);

		//Customer page actions and validations
		this.customersPageActionsAndValidations("EMC2CUST11", "EMC2AUTOMATIONCUSTOMER", email, true, false,
			"7147 Greenback Ln", "Citrus Heights", Country.USA.iso2code, "CA", "95621");
		VertexLogger.log("Successfully entered customer page values and validations", AcumaticaSalesOrderTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "7147 Greenback Ln", "Citrus Heights",
			Country.USA.iso2code, "CA", "95621");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate Sales Orders page
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.SALES_ORDERS);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.SALES_ORDERS);
		this.validateOrderType();
		this.setSalesOrderPageActions("EMC2CUST11", "301CMPNS01", "2", "500");
		this.validateSalesOrderPage(DEFAULT_SHIP_VIA, true, true, "7147 Greenback Ln", "Citrus Heights",
			"US - United States of America", "CA - CALIFORNIA", "95621");
		this.validateVertexPostalAddressPopup();
		this.verifySalesOrderPageConditions(true, true);
		this.validateAddressShippingTab(true, true, "7147 Greenback Ln", "Citrus Heights",
			"US - United States of America", "CA - CALIFORNIA", "95621-5526");
		this.mainFormContentValidations("2.00", "2.00");
		salesOrders.clickMainPanelTab(AcumaticaMainPanelTab.TAX_DETAILS);
		this.validateTotalTabDetails("1000.00", "0.00", "77.50", "2.00", "1,077.50", "2.00", "1,077.50", "0.00", "0.00",
			"1,077.50");

		//delete created customer record
		this.deleteCreatedCustomerRecord("EMC2CUST11");

		//delete created customer class record
		this.deleteCreatedCustomerClassesRecord("EMC2CLASS1");
	}
}
