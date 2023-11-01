package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This method  creates a sales order and selects the pickup warehouse and
 * then follows along to verify that shipping cost is 0
 *
 * @author osabha
 */
public class KiboBasicSalesOrderPickUpTests extends KiboTaxCalculationBaseTest
{
	/**
	 * This method  creates a sales order and selects the pickup warehouse and
	 * then follows along to verify that shipping cost is 0
	 * and verifies calculated tax
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboCreateSalesOrderForPickupTest( )
	{
		String checkNumber = "123456789";
		String expectedOrderTotal = "$53.00";
		String expectedTax = "$3.00";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer2);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_TRENCH_PUMP);
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

		fulfillOrderForPickup();

		boolean isFulfilled = maxinePage.fulfillment.checkIfFulfilled();
		assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPayed = maxinePage.payment.checkIfPaid();
		assertTrue(isPayed);
	}
}