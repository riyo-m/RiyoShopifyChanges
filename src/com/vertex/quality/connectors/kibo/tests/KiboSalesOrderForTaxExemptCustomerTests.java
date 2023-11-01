package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this class represents the test case number CKIBO 260
 * The selected Customer is supposed to be exempt by the customer code "Ktccode" set in customer
 * attribute
 * simple sales order for pick up,
 * verification for tax and shipping is expected to be 0.00$ for both
 *
 * @author osabha
 */
public class KiboSalesOrderForTaxExemptCustomerTests extends KiboTaxCalculationBaseTest
{
	@Test(groups = { "kibo_ui" })
	public void kiboSalesOrderForTaxExemptCustomerTest( )
	{
		String expectedOrderTotal = "$50.00";
		String expectedTax = "$0.00";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.tax_exempt_customer);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.orderDetailsDialog.selectPickupHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();

		maxinePage.orderHeader.clickSubmitOrder();

		String orderTotal = maxinePage.getOrderTotal();
		boolean isOrderTotalCorrect = orderTotal.equals(expectedOrderTotal);
		assertTrue(isOrderTotalCorrect);

		String actualTax = maxinePage.getTaxAmount();
		boolean isTaxCorrect = expectedTax.equals(actualTax);
		assertTrue(isTaxCorrect);
	}
}




