package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Test Case CKIBO-194
 * Creating a sales oder from the back office with invoice
 * same billing and shipping Addresses
 * multiple lines 4 same and different ProductNames
 *
 * @author osabha
 */
public class KiboBackOfficeSalesOrderWithInvoiceTests extends KiboTaxCalculationBaseTest
{
	@Test(groups = { "kibo_ui" })
	public void kiboCreateSalesOrderTest( )
	{
		String checkNumber = "123456789";
		String expectedTax = "$25.60";
		String quantity = "2";
		String expectedOrderTotal = "$605.60";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_KAILAS_PATENT_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_MIRA_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.enterQuantity(quantity);
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_IMAGEN_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickShippingMethodButton();
		maxinePage.orderDetailsDialog.clickFlatRate();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();
		maxinePage.orderHeader.clickSubmitOrder();

		String actualTax = maxinePage.getTaxAmount();
		boolean isTaxCorrect = expectedTax.equals(actualTax);
		assertTrue(isTaxCorrect);

		String orderTotal = maxinePage.getOrderTotal();
		boolean isOrderTotalCorrect = orderTotal.equals(expectedOrderTotal);
		assertTrue(isOrderTotalCorrect);

		fulfillOrderForShipping();

		boolean isFulfilled = maxinePage.fulfillment.checkIfFulfilled();
		assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPayed = maxinePage.payment.checkIfPaid();
		assertTrue(isPayed);
	}
}
