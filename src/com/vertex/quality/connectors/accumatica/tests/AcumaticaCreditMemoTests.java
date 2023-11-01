package com.vertex.quality.connectors.accumatica.tests;

import com.vertex.quality.common.enums.Country;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
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
public class AcumaticaCreditMemoTests extends AcumaticaBaseTest
{
	/**
	 * To validate the Tax amounts in "Invoice and Memos" and to validate the XML information (33)
	 */
	@Test
	public void CreditMemo_TaxAmountsTest( )
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
		this.customersPageActionsAndValidations("INVMEMOS22", "CUSTOMERFOR-TAX-CHECK-INVOICE-AND-MEMOS-TWOTWO", email,
			false, false, "5200 Westpointe Plaza Drive", "Columbus", Country.USA.iso2code, "OH", "43228");
		VertexLogger.log("Successfully entered customer page values and validations", AcumaticaSalesOrderTests.class);

		//Navigate to Delivery Settings tab
		customers.clickSubMenu("Delivery Settings");

		//Validate Delivery Settings details
		this.validateDeliverySettingsDetails(DEFAULT_SHIP_VIA, true, false, "5200 Westpointe Plaza Drive", "Columbus",
			Country.USA.iso2code, "OH", "43228");
		common.clickSaveButton();

		//Navigate to Invoice and Memos page
		postSignOn.openMenuPage(AcumaticaGlobalSubMenuOption.ACCOUNTS_RECEIVABLE,
			AcumaticaLeftPanelLink.INVOICE_AND_MEMOS);

		//Create Invoice and Memos basic details
		String orderType = invoiceAndMemo.getOrderType();
		assertTrue(orderType.equalsIgnoreCase("Invoice"), "Order Type is not matching");
		String referenceNbr = invoiceAndMemo.getReferenceNBR();
		assertTrue(referenceNbr.equalsIgnoreCase("<NEW>"), "Reference Nbr is not matching");

		invoice.setCustomer("INVMEMOS22");
		customers.clickSubMenu("Document Details");
		String invoiceBranch = invoiceAndMemo.getBranch();
		assertTrue(invoiceBranch.equalsIgnoreCase("MAIN"), "Branch is not matching");
		invoiceAndMemo.setInventory("301CMPNS01");
		invoiceAndMemo.setQuantity("1");
		invoiceAndMemo.setUnitPrice("500");

		customers.clickSubMenu("Financial Details");
		String financeDetailsBranch = invoiceAndMemo.getFinanceDetailBranch();
		assertTrue(financeDetailsBranch.equalsIgnoreCase("MAIN - New York"), "Branch is not matching");
		String customerTaxZone = invoiceAndMemo.getCustomerTaxZone();
		assertTrue(customerTaxZone.equalsIgnoreCase("VERTEX - Vertex TaxZone"), "Customer tax zone is not matching");

		common.clickSaveButton();
	}
}
