package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This function runs  a happy path with required fields only.
 * shipping amount and taxes are verified to have the correct values.
 *
 * @author osabha
 */
public class KiboBasicSalesOrderVerifyCalculatedTaxTests extends KiboTaxCalculationBaseTest
{
	/**
	 * runs basic sales order for one item and then collects a payment and fulfills the order.
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboSignInAndRunFullHappyPathTest( )
	{
		//**********************Test-Data********************//
		String checkNumber = "123456789";
		String expectedOrderTotal = "$70.20";
		String expectedTax = "$5.20";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		maxinePage.clickCustomerList();
		maxinePage.selectCustomer(KiboCustomers.Customer1);
		maxinePage.clickEditDetails();
		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickShippingMethodButton();
		maxinePage.orderDetailsDialog.clickFlatRate();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();
		maxinePage.orderHeader.clickSubmitOrder();

		String orderTotal = maxinePage.getOrderTotal();
		boolean isOrderTotalCorrect = orderTotal.equals(expectedOrderTotal);
		assertTrue(isOrderTotalCorrect);

		String actualTax = maxinePage.getTaxAmount();
		boolean isTaxCorrect = expectedTax.equals(actualTax);
		assertTrue(isTaxCorrect);

		fulfillOrderForShipping();

		boolean isFulfilled = maxinePage.fulfillment.checkIfFulfilled();
		Assert.assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPaid = maxinePage.payment.checkIfPaid();
		Assert.assertTrue(isPaid);
	}

	/**
	 * Basic sales order, same billing and shipping , multiple jurisdictions returned
	 * CKIBO-182
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboBasicSalesOrderTest( )
	{
		//**********************Test-Data********************//
		String checkNumber = "123456789";
		String expectedOrderTotal = "$71.50";
		String expectedTax = "$6.50";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer4);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_TRENCH_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickShippingMethodButton();
		maxinePage.orderDetailsDialog.clickFlatRate();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();

		maxinePage.orderHeader.clickSubmitOrder();

		String orderTotal = maxinePage.getOrderTotal();
		boolean isOrderTotalCorrect = orderTotal.equals(expectedOrderTotal);
		assertTrue(isOrderTotalCorrect);

		String actualTax = maxinePage.getTaxAmount();
		boolean isTaxCorrect = expectedTax.equals(actualTax);
		assertTrue(isTaxCorrect);

		fulfillOrderForShipping();

		boolean isFulfilled = maxinePage.fulfillment.checkIfFulfilled();
		Assert.assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPaid = maxinePage.payment.checkIfPaid();
		Assert.assertTrue(isPaid);
	}
}
