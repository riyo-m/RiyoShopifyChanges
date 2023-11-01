package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemType;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pages.products.NetsuiteInventoryItemPage;
import com.vertex.quality.connectors.netsuite.common.pages.products.NetsuiteSelectNewItemPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests products and product classes
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWProductTests extends NetsuiteBaseOWTest
{
	/**
	 * Tests that the "VERTEX PRODUCT CLASS" field is present
	 * CNSL-58
	 */
	@Test(groups = { "netsuite_ow_smoke", "product_class" })
	protected void validateVertexProductClassTest( )
	{
		System.out.println("Starting Test CNSL-58");
		NetsuiteNavigationMenus selectItemTypeMenu = getSelectItemTypeMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSelectNewItemPage selectNewItemPage = setupManagerPage.navigationPane.navigateThrough(
			selectItemTypeMenu);
		NetsuiteInventoryItemPage inventoryItemPage = selectNewItemPage.selectItemType(NetsuiteItemType.INVENTORY_ITEM);
		assertTrue(inventoryItemPage.isVertexProductClassFieldAvailable());
		System.out.println("Ending Test CNSL-58");
	}

	/**
	 * Checks that the document's XML contains the product's product class
	 * CNSL-223
	 */
	@Test(groups = { "netsuite_ow_regression", "product_class" })
	protected void validateProductClassInDocumentXMLTest( )
	{
		String productClass = "TEST AUTOMATION PRODUCT CLASS";
		String location = "01: San Francisco";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.TEST_PRODUCT_CLASS)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);
		salesOrderPage.selectLocation(location);
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		NetsuiteOWSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, productClass);
		deleteDocument(previousSalesOrderPage);
	}

	/**
	 * Verify that the we are removing special characters from long item names
	 * CNSL-664
	 */
	@Test(groups = {"netsuite_ow_regression", "product_class" })
	protected void TrimSpecialCharsTest()
	{
		//Arrange
		String location = "01: San Francisco";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_AUTOMATION_CUSTOMER_1;

		NetsuiteItem testItem = NetsuiteItem
				.builder(NetsuiteItemName.ITEM_WITH_LONG_NAME)
				.quantity("1")
				.amount("100.00")
				.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		//Act
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, testItem);

		salesOrderPage.selectLocation(location);

		//Assert
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();

		//Clean up
		deleteDocument(savedSalesOrderPage);

	}

	/**
	 * Validates that Freight Location Code is passed when the first thing listed is a description item
	 * CNSL-1452
	 */
	@Test(groups = {"netsuite_ow_regression", "product_class" })
	protected void freightLocationCodeTest()
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_3M;
		NetsuiteItem descriptionItem = NetsuiteItem
				.builder(NetsuiteItemName.DESCRIPTION_ITEM)
				.build();

		NetsuiteItem item = NetsuiteItem
				.builder(NetsuiteItemName.ACC00002_ITEM)
				.quantity("1")
				.amount("100.00")
				.build();

		//Get menus
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		//Execute test
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer,descriptionItem, item);

		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		deleteDocument(savedSalesOrderPage);

	}

}
