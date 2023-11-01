package com.vertex.quality.connectors.accumatica.tests;

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
public class AcumaticaTransactionsTests extends AcumaticaBaseTest
{
	/**
	 * To verify the tax amounts in "Cash Entry Transactions
	 * Note: At "Transactions screen", Tax calculations are on based on branch
	 * address only ( not on shipping address) (30)
	 */
	@Test
	public void Transactions_CashEntry_TaxAmountsTest( )
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
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT);
		postSignOn.leftNavPanel.openLeftMenuLink(
			AcumaticaLeftPanelLink.TRANSACTIONS);//fixme this is actually on the Cash Management submenu, not the Customer Management one

		transactions.clickOnNewRecordIcon();
		String transType = transactions.getTansType();
		assertTrue(transType.equalsIgnoreCase("Cash Entry"), "Opportunity ID is not matching");
		String status = transactions.getStatus();
		assertTrue(status.equalsIgnoreCase("Balanced"), "Status is not matching");
		String referenceNBR = transactions.getReferenceNbr();
		assertTrue(referenceNBR.equalsIgnoreCase("<NEW>"), "Reason is not matching");

		transactions.setDiscritption("Testing Cash Entry Transactions");
		transactions.setCashAccount("100000");
		String cashAccount = transactions.getCashAccount();
		assertTrue(cashAccount.equalsIgnoreCase("100000 - Petty Cash"), "Cash Account is not matching");

		transactions.setEntryType("PETTYEXP");
		String entryType = transactions.getEntryType();
		assertTrue(entryType.equalsIgnoreCase("Entity Type = PETTYEXP - Office Expense"), "Entry Type is not matching");
		transactions.setDocumentRef("Testing Cash Entry Transactions");
		String transactionBranch = transactions.getBranch();
		assertTrue(transactionBranch.equalsIgnoreCase("MAIN"), "Branch is not matching");
		transactions.setInventoryId("CARRENTAL");
		transactions.setPrice("500");
		customers.clickSubMenu("Financial Details");
		String taxZone = transactions.getTaxZone();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex Tax Zone"), "Tax zone id is not matching");
		common.clickSaveButton();
		String amount = transactions.getAmount();
		assertTrue(amount.equalsIgnoreCase("544.38"), "Amount is not matching");
		String total = transactions.getTotal();
		assertTrue(total.equalsIgnoreCase("44.38"), "Total is not matching");
	}

	/**
	 * To verify the "Release" functionality of Cash Transactions
	 * Note: At "Transactions screen", Tax calculations are on based on branch address only ( not on
	 * shipping address)
	 * Note: Workflow - 30 (Transactions(Cash Entry) - Tax Amounts Validation) includes the XML
	 * validation of Transactions.
	 * Workflow-31 ( Release - Cash Transactions) includes the "Release" of Transaction.
	 * After Release, it can not be deleted. It can only be "revered".
	 * After reverse it can be deleted as part of cleanup. (31)
	 */
	@Test
	public void Release_CashTransactionsTest( )
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
		postSignOn.header.switchToSubMenuTab(AcumaticaGlobalSubMenuOption.CUSTOMER_MANAGEMENT);
		postSignOn.leftNavPanel.openLeftMenuLink(AcumaticaLeftPanelLink.TRANSACTIONS);

		transactions.clickOnNewRecordIcon();
		String transType = transactions.getTansType();
		assertTrue(transType.equalsIgnoreCase("Cash Entry"), "Opportunity ID is not matching");
		String status = transactions.getStatus();
		assertTrue(status.equalsIgnoreCase("Balanced"), "Status is not matching");
		String referenceNBR = transactions.getReferenceNbr();
		assertTrue(referenceNBR.equalsIgnoreCase("<NEW>"), "Reason is not matching");

		transactions.setDiscritption("Testing Cash Entry Transactions");
		transactions.setCashAccount("100000");
		String cashAccount = transactions.getCashAccount();
		assertTrue(cashAccount.equalsIgnoreCase("100000 - Petty Cash"), "Cash Account is not matching");

		transactions.setEntryType("PETTYEXP");
		String entryType = transactions.getEntryType();
		assertTrue(entryType.equalsIgnoreCase("Entity Type = PETTYEXP - Office Expense"), "Entry Type is not matching");
		transactions.setDocumentRef("Testing Cash Entry Transactions");
		String transactionBranch = transactions.getBranch();
		assertTrue(transactionBranch.equalsIgnoreCase("MAIN"), "Branch is not matching");
		transactions.setInventoryId("CARRENTAL");
		transactions.setPrice("500");
		customers.clickSubMenu("Financial Details");
		String taxZone = transactions.getTaxZone();
		assertTrue(taxZone.equalsIgnoreCase("VERTEX - Vertex Tax Zone"), "Tax zone id is not matching");
		common.clickSaveButton();
		String amount = transactions.getAmount();
		assertTrue(amount.equalsIgnoreCase("544.38"), "Amount is not matching");
		String total = transactions.getTotal();
		assertTrue(total.equalsIgnoreCase("44.38"), "Total is not matching");
		status = transactions.getStatus();
		assertTrue(status.equalsIgnoreCase("Balanced"), "Status is not matching");
		common.selectAddressCleansing("Actions", "Release");
		status = transactions.getStatus();
		assertTrue(status.equalsIgnoreCase("Released"), "Status is not matching");
		common.selectAddressCleansing("Actions", "Reverse");
		amount = transactions.getAmount();
		assertTrue(amount.equalsIgnoreCase("-544.38"), "Amount is not matching");
		total = transactions.getTotal();
		assertTrue(total.equalsIgnoreCase("-44.38"), "Total is not matching");
		status = transactions.getStatus();
		assertTrue(status.equalsIgnoreCase("Balanced"), "Status is not matching");

		//delete created Transactions basic details
		common.clickDeleteButton();
	}
}
