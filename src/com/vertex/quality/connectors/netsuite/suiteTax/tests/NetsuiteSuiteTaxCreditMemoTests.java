package com.vertex.quality.connectors.netsuite.suiteTax.tests;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomer;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemName;
import com.vertex.quality.connectors.netsuite.common.pages.NetsuiteSetupManagerPage;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteItem;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuiteNavigationMenus;
import com.vertex.quality.connectors.netsuite.common.pojos.NetsuitePrices;
import com.vertex.quality.connectors.netsuite.suiteTax.pages.transactions.NetsuiteAPICreditMemoPage;
import com.vertex.quality.connectors.netsuite.suiteTax.tests.base.NetsuiteBaseAPITest;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests 'credit memos > Tax only adjustment' for Europe, UK countries
 *
 * @author ravunuri
 */
public class NetsuiteSuiteTaxCreditMemoTests extends NetsuiteBaseAPITest
{
	/**
	 * Checks for Vertex Tax only adjustment for "UK (Intra UK-UK)" imports
	 * CNSAPI-1339
	 */
	@Test(groups = {"suite_tax_regression"})
	protected void validateTaxOnlyAdjustmentUKTest( )
	{
		String expectedRequestType = "DistributeTaxRequest";
		String vertexTaxResult = "Success";
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_UK;

		NetsuiteItem itemOne = NetsuiteItem
			.builder(NetsuiteItemName.TAX_ONLY_ADJUSTMENT_ITEM)
			.quantity("1").amount("10.00")
			.build();

		String location = "Germany : Germany";

		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
			.builder()
			.subtotal("10.00").taxAmount("0.00").total("10.00")
			.build();

		NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteAPICreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);
		setupBasicOrder(creditMemoPage, customer, itemOne);
		creditMemoPage.selectLocation(location);

		NetsuiteAPICreditMemoPage savedCreditMemoPage = creditMemoPage.saveOrder();

		checkBasicOrderAmounts(savedCreditMemoPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedCreditMemoPage, expectedPricesAfterSaving, itemOne);

		//Verify the VERTEX DISTRIBUTE TAX checkbox is checked
		savedCreditMemoPage.getDistributeTaxCheckboxStatus();

		//Go to Vertex Call details to Verify 'DistributeTaxRequest' is posted to Vertex.
		String vertexCallDetailsText = savedCreditMemoPage.getCallDetailstext();
		assertTrue(vertexCallDetailsText.contains(expectedRequestType));
		assertTrue(vertexCallDetailsText.contains(vertexTaxResult));
	}

	/**
	 * Checks for Vertex Tax only adjustment for "Germany (EU-Europe)" imports
	 * CNSAPI-1340
	 */
	@Test(groups = {"suite_tax_regression"})
	protected void validateTaxOnlyAdjustmentEUTest( )
	{
		String expectedRequestType = "DistributeTaxRequest";
		String vertexTaxResult = "Success";
		NetsuiteCustomer customer = NetsuiteCustomer.CUSTOMER_GERMANY;

		NetsuiteItem itemOne = NetsuiteItem
				.builder(NetsuiteItemName.TAX_ONLY_ADJUSTMENT_ITEM)
				.quantity("1").amount("10.00")
				.build();

		String location = "Germany : German";

		NetsuitePrices expectedPricesAfterSaving = NetsuitePrices
				.builder()
				.subtotal("10.00").taxAmount("0.00").total("10.00")
				.build();

		NetsuiteNavigationMenus creditMemoMenu = getCreditMemoMenu();
		NetsuiteSetupManagerPage setupManagerPage = configureSettings();
		NetsuiteAPICreditMemoPage creditMemoPage = setupManagerPage.navigationPane.navigateThrough(creditMemoMenu);
		setupBasicOrder(creditMemoPage, customer, itemOne);
		creditMemoPage.selectLocation(location);

		NetsuiteAPICreditMemoPage savedCreditMemoPage = creditMemoPage.saveOrder();

		checkBasicOrderAmounts(savedCreditMemoPage, expectedPricesAfterSaving);
		checkOrderWithTax(savedCreditMemoPage, expectedPricesAfterSaving, itemOne);

		//Verify the VERTEX DISTRIBUTE TAX checkbox is checked
		savedCreditMemoPage.getDistributeTaxCheckboxStatus();

		//Go to Vertex Call details to Verify 'DistributeTaxRequest' is posted to Vertex.
		String vertexCallDetailsText = savedCreditMemoPage.getCallDetailstext();
		assertTrue(vertexCallDetailsText.contains(expectedRequestType));
		assertTrue(vertexCallDetailsText.contains(vertexTaxResult));
//		savedCreditMemoPage.editOrder();
//		savedCreditMemoPage.deleteMemo();
	}
}
