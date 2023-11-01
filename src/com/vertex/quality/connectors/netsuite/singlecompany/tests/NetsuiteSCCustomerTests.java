package com.vertex.quality.connectors.netsuite.singlecompany.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.customers.NetsuiteSCCustomerPage;
import com.vertex.quality.connectors.netsuite.singlecompany.pages.transactions.NetsuiteSCSalesOrderPage;
import com.vertex.quality.connectors.netsuite.singlecompany.tests.base.NetsuiteBaseSCTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests customer and customer classes
 *
 * @author hho, jyareeda
 */
public class NetsuiteSCCustomerTests extends NetsuiteBaseSCTest
{
	/**
	 * Tests that both the "VERTEX CUSTOMER CLASS" and "VERTEX CUSTOMER ID" fields are present
	 * CNSL-54
	 */
	@Test(groups = { "netsuite_smoke", "customer_class", "customer_code" })
	protected void validateVertexCustomerClassAndIdTest( )
	{
		// To naviage to Customers menu in SingleCompany account
		NetsuiteNavigationMenus customersMenu = getCustomerMenu();

		NetsuiteHomepage homepage = signIntoHomepageAsSingleCompany();
		NetsuiteSCCustomerPage customerPage = homepage.navigationPane.navigateThrough(customersMenu);

		// To verify Vertex Customer class field and Id's are available
		assertTrue(customerPage.isVertexCustomerClassFieldAvailable());
		assertTrue(customerPage.isVertexCustomerIdFieldAvailable());
	}

	/**
	 * Checks that the document's XML contains the customer's customer code
	 * CNSL-648
	 */
	@Test(groups = { "netsuite_regression", "customer_code" })
	protected void validateCustomerCodeInDocumentXMLTest( )
	{
		String customerId = "TEST AUTOMATION CUSTOMER CODE";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_CUSTOMER_CODE;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();

		// To naviage to SalesOrder menu in SingleCompany account
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		//Passing values for the order
		setupBasicOrder(salesOrderPage, customer, item);
		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		// Checking document logs and delete previous sales order document
		NetsuiteSCSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId);
		deleteDocument(previousSalesOrderPage);
	}

	/**
	 * Checks that the document's XML contains the customer's customer class
	 * CNSL-647
	 */
	@Test(groups = { "netsuite_regression", "customer_class" })
	protected void validateCustomerClassInDocumentXMLTest( )
	{
		String customerClass = "TEST AUTOMATION CUSTOMER CLASS";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_CUSTOMER_CLASS;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.STANDARD_TEST_ITEM_1)
			.amount("10.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteSCSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);
		NetsuiteSCSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		NetsuiteSCSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerClass);
		deleteDocument(previousSalesOrderPage);
	}
}
