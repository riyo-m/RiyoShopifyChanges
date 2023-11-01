package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteImportAssistantPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteJobStatusPage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteListSalesOrderPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import java.net.URISyntaxException;

import static org.testng.Assert.assertTrue;

/**
 * Tests for credit memos
 *
 * @author hho
 */
public class NetsuiteSCImportTests extends NetsuiteBaseSCTest
{
	/**
	 * Checks for Vertex tax integration on credit memos.
	 * CNSL-1542
	 */
	@Test(groups = { "netsuite_smoke" })
	protected void runCSVImportTest( )
	{
		String currentDirectory = System.getProperty("user.dir");
		String primaryCSVPath = currentDirectory + "\\resources\\csvfiles\\netsuite\\csvImport_primary_test.csv";
		String itemCSVPath = currentDirectory + "\\resources\\csvfiles\\netsuite\\csvImport_item_test.csv";

		NetsuiteNavigationMenus importCSVMenu = getCSVImportMenu();
		NetsuiteNavigationMenus salesOrderListMenu = getSalesOrderListMenu();

		NetsuiteHomepage homepage = signIntoHomepageAsSingleCompany();
		NetsuiteImportAssistantPage importAssistantPage = homepage.navigationPane.navigateThrough(importCSVMenu);
		importAssistantPage.selectImportType("Transactions");
		importAssistantPage.selectRecordType("Sales Order");
		importAssistantPage.selectUploadType("MULTIPLE FILES TO UPLOAD");
		importAssistantPage.uploadPrimaryFile(primaryCSVPath);
		importAssistantPage.uploadItemsFile(itemCSVPath);
		importAssistantPage.goNext();
		importAssistantPage.goNext();
		importAssistantPage.selectPrimaryFileKey("ExternalId");
		importAssistantPage.selectItemFileKey("ExternalId");
		importAssistantPage.goNext();
		importAssistantPage.goSecondaryNext();
		importAssistantPage.enterRandomMapName();
		importAssistantPage.saveAndRun();

		NetsuiteJobStatusPage jobStatusPage = importAssistantPage.clickAlert();
		NetsuiteListSalesOrderPage listSalesOrderPage = jobStatusPage.navigationPane.navigateThrough(
			salesOrderListMenu);
		NetsuiteSCSalesOrderPage salesOrderPage = listSalesOrderPage.edit("1970");

		String subtotal = salesOrderPage.getOrderSubtotal();
		assertTrue(subtotal.equals("129.99"));

		String shipping = salesOrderPage.getOrderShippingAmount();
		assertTrue(shipping.equals("13.48"));

		String taxAmount = salesOrderPage.getOrderTaxAmount();
		assertTrue(taxAmount.equals("11.84"));

		String total = salesOrderPage.getOrderTotal();
		assertTrue(total.equals(" 155.31"));

		salesOrderPage.deleteOrder();
	}
}
