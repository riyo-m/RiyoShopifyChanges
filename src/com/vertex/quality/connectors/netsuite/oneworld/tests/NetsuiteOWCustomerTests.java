package com.vertex.quality.connectors.netsuite.oneworld.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteHomepage;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.oneworld.pages.customers.NetsuiteOWCustomerPage;
import com.vertex.quality.connectors.netsuite.oneworld.pages.transactions.NetsuiteOWSalesOrderPage;
import com.vertex.quality.connectors.netsuite.oneworld.tests.base.NetsuiteBaseOWTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests customers and customer classes
 *
 * @author hho, @fixed ravunuri
 */
@SuppressWarnings("Duplicates")
public class NetsuiteOWCustomerTests extends NetsuiteBaseOWTest
{
	/**
	 * Tests that both the "VERTEX CUSTOMER CLASS" and "VERTEX CUSTOMER ID" fields are present
	 * CNSL-54
	 */
	@Test(groups = { "netsuite_ow_smoke", "customer_class", "customer_code" })
	protected void validateVertexCustomerClassAndIdTest( )
	{
		NetsuiteNavigationMenus customersMenu = getCustomerMenu();

		NetsuiteHomepage homepage = signintoHomepageAsOneWorld();
		NetsuiteOWCustomerPage customerPage = homepage.navigationPane.navigateThrough(customersMenu);

		assertTrue(customerPage.isVertexCustomerClassFieldAvailable());
		assertTrue(customerPage.isVertexCustomerIdFieldAvailable());
	}

	/**
	 * Checks that the document's XML contains the customer's customer code
	 * CNSL-56, CNSL-209
	 */
	@Test(groups = { "netsuite_ow_regression", "customer_code" })
	protected void validateCustomerCodeInDocumentXMLTest( )
	{
		String customerId = "TEST AUTOMATION CUSTOMER CODE";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_CUSTOMER_CODE;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.amount("100.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		NetsuiteOWSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerId);
		deleteDocument(previousSalesOrderPage);
	}

	/**
	 * Checks that the document's XML contains the customer's customer class
	 * CNSL-57
	 */
	@Ignore
	@Test(groups = { "netsuite_ow_regression", "customer_class" })
	protected void validateCustomerClassInDocumentXMLTest( )
	{
		String customerClass = "TEST AUTOMATION CUSTOMER CLASS";
		NetsuiteCustomer customer = NetsuiteCustomer.TEST_CUSTOMER_CLASS;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.amount("100.00")
			.quantity("1")
			.build();
		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();

		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		NetsuiteOWSalesOrderPage previousSalesOrderPage = checkDocumentLogs(savedSalesOrderPage, customerClass);
		deleteDocument(previousSalesOrderPage);
	}

	/**
	 * Create a Sales Order with Customer Class Exemption and verify that the tax is exempted (0% Tax) from Vertex.
	 * @author ravunuri
	 * CNSAPI-258
	 */
	@Test(groups = { "netsuite_ow_regression" })
	public void validateVertexCustomerClassExemptionTest ()
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_CLASS_EXEMPT;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.amount("100.00")
			.quantity("1").build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("0.0%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("0.00")
			.total("100.00")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		//Validate that the tax is exempted (0% Tax) from Vertex due to Customer Class - VEXCCLASS.
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, item);
	}

	/**
	 * Create a Sales Order with Customer CODE Exemption and verify that the tax is exempted (0% Tax) from Vertex.
	 * @author ravunuri
	 * CNSL-737
	 */
	@Test(groups = { "netsuite_ow_regression" })
	public void validateVertexCustomerCodeExemptionTest ()
	{
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_CODE_EXEMPT;
		NetsuiteItem item = NetsuiteItem
			.builder(NetsuiteItemName.ACC00002_ITEM)
			.amount("100.00")
			.quantity("1").build();
		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.itemTaxRate("0.0%")
			.itemTaxCode(defaultTaxCode)
			.subtotal("100.00")
			.taxAmount("0.00")
			.total("100.00")
			.build();

		NetsuiteNavigationMenus salesOrderMenu = getSalesOrderMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteOWSalesOrderPage salesOrderPage = setupManagerPage.navigationPane.navigateThrough(salesOrderMenu);
		setupBasicOrder(salesOrderPage, customer, item);
		NetsuiteOWSalesOrderPage savedSalesOrderPage = salesOrderPage.saveOrder();
		//Validate that the tax is exempted (0% Tax) from Vertex due to Customer Code - VEXCCode.
		checkOrderWithTax(savedSalesOrderPage, expectedPricesAfterSaving, item);
	}
}
