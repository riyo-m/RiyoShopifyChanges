package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaMainPanelTab;
import com.vertex.quality.connectors.accumatica.tests.base.AcumaticaBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * To verify the tax amounts in "Cash Sales" transaction type *
 * Note: At "Cash Sales screen", Tax calculations are on based on branch address only ( not on
 * shipping address)
 *
 * @author saidulu kodadala
 */
public class AcumaticaCashSalesTests extends AcumaticaBaseTest
{
	/**
	 * To verify the tax amounts in "Cash Sales" transaction type
	 * Note: At "Cash Sales screen", Tax calculations are on based on branch
	 * address only ( not on shipping address)(26)
	 */
	@Test
	public void CashSales_TaxAmountsTest( )
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
		this.customersPageActionsAndValidations("1ZCASHSALE", "CUSTOMERFOR-CASHSALE-TAXAMTSCHECK", email, false, false,
			"555 California St 7th floor", "San Francisco", Country.USA.iso2code, "CA", " 94104");
		VertexLogger.log("Successfully entered customer page values and validations",
			AcumaticaAddressValidationTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu(AcumaticaMainPanelTab.DELIVERY_SETTINGS);

		//Validate Delivery Settings details
		customers.setTaxZoneId("VERTEX");
		boolean results = customers.setShippingAddressSameAsMainChecked(true);
		assertTrue(results, "UnTicked Checkbox 'Same as Main' under SHIPPING ADDRESS");
		boolean result = customers.isDeliveryAcsChecked(false);
		assertTrue(!result, "UnTicked Checkbox 'ACS' under SHIPPING ADDRESS");
		customers.clickSubMenu(AcumaticaMainPanelTab.GENERAL_INFO);
		common.clickSaveButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CASH_SALES);

		//validate basic details of cash sales page
		String resultType = cashSales.getType();
		assertTrue(resultType.equalsIgnoreCase("Cash Sale"), "Type is not matching");
		String resultReferenceNbr = cashSales.getReferenceNbr();
		assertTrue(resultReferenceNbr.equalsIgnoreCase("<NEW>"), "Reference NBR is not matching");

		//set customer
		cashSales.setCustomer("1ZCASHSALE");
		String resultLocation = cashSales.getLocation();
		assertTrue(resultLocation.equalsIgnoreCase("MAIN - Primary Location"), "Location is not matching");

		//set new row 
		cashSales.clickPlusIconForAddNewRecord();
		cashSales.setInventoryId("ACCOMODATION");
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
		//click on save button
		common.clickSaveButton();

		String detailTotal = cashSales.getDetailTotal();
		assertTrue(detailTotal.equalsIgnoreCase("1,000.00"), "Detail Total is not matching");
		String taxTotal = cashSales.getTaxTotal();
		assertTrue(taxTotal.equalsIgnoreCase("88.75"), "Tax Total is not matching");
		String balance = cashSales.getBalance();
		assertTrue(balance.equalsIgnoreCase("1,088.75"), "Balance is not matching");
		String paymentAmount = cashSales.getPaymentAmount();
		assertTrue(paymentAmount.equalsIgnoreCase("1,088.75"), "Payment Amount is not matching");

		//switch to Tax Details tab
		customers.clickSubMenu("Tax Details");
		common.clickDeleteButton();

		//Navigate to Menu -> Sub menu ->Left page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE, AcumaticaLeftPanelLink.CUSTOMERS);

		//Set customer
		customers.setCustomerId("1ZCASHSALE");
		common.clickDeleteButton();
	}
}
