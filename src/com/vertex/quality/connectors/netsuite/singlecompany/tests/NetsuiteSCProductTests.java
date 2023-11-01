package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemType;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.products.NetsuiteInventoryItemPage;
import com.vertex.quality.connectors.netsuite.common.pages.products.NetsuiteSelectNewItemPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests products and product classes
 *
 * @author hho, jyareeda
 */
public class NetsuiteSCProductTests extends NetsuiteBaseSCTest
{
	/**
	 * Tests that the "VERTEX PRODUCT CLASS" field is present
	 * CNSL-642
	 */
	@Test(groups = { "netsuite_smoke", "product_class" })
	protected void validateVertexProductClassTest( )
	{
		// To setup the configurations, Signing and navigating to SingleCompany homepage, Setting up General preferences
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		//To fetch required menu items
		NetsuiteNavigationMenus selectItemTypeMenu = getSelectItemTypeMenu();

		//To navigate through the selected menu's
		NetsuiteSelectNewItemPage selectNewItemPage = setupManagerPage.navigationPane.navigateThrough(
			selectItemTypeMenu);

		//Selecting Inventory
		NetsuiteInventoryItemPage inventoryItemPage = selectNewItemPage.selectItemType(NetsuiteItemType.INVENTORY_ITEM);

		//Checks if the Vertex Product Class field is available
		assertTrue(inventoryItemPage.isVertexProductClassFieldAvailable());
	}

	/**
	 * Checks that the document's XML contains the product's product class
	 * CNSL-661
	 */
	@Test(groups = { "netsuite_regression", "product_class" })
	protected void validateProductClassInDocumentXMLTest( )
	{
		// To setup the configurations, Signing and navigating to SingleCompany homepage, Settingup General preferences
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();

		//Variable to pass into the SC SalesOrder page
		String productClass = "TEST AUTOMATION PRODUCT CLASS";
		String location = "San Francisco";

		//Choose customer name
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;

		// Passing values into amount, Quantity in SalesOrder page
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.TEST_PRODUCT_CLASS)
			.amount("10.00")
			.quantity("1")
			.build();
		// Fetch SalesOrderMenu item
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		//To navigate to Transactions > Sales > Enter Sale Order > List menu
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);

		// To select Customer from the dropdown in Sales Order Page.
		setupBasicOrder(salesOrderPage, customer, item);

		// To select Location from the dropdown in Sales Order Page.
		salesOrderPage.selectLocation(location);

		// Saving the order
		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		// To get the previous sales order if any
		NetsuiteSCSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, productClass);
		// Delete the previous sales order
		deleteDocument(salesOrderPage);
	}
}
