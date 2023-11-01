package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.pages.KiboReturnPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * this class represents test case  /
 * The test case creates a sales order
 * a  return is performed and then refund check/STORE Credit is issued with the correct
 * amount and all the numbers are verified through all the test case steps
 *
 * @author osabha
 */
public class KiboBackOfficeReturnOrderTests extends KiboTaxCalculationBaseTest
{
	/**
	 * CKIBO 288
	 * creates a sales order for one item with quantity of ten,
	 * then verifies the calculated tax and then returns 4 items of the order for and issues a
	 * refund for them.
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboCreateOrderAndReturnTest( )
	{
		//**********************Test-Data********************//
		String checkNumber = "123456798";
		String qtyToReturn = "4";
		String returnType1 = "Refund";
		String reportedIssue = "No Longer Wanted";
		String number = "10";
		String refundAmount = "$259.20";
		String expectedOrderTotal = "$664.20";
		String expectedTax = "$49.20";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_MIRANDA_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.enterQuantity(number);
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
		assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPaid = maxinePage.payment.checkIfPaid();
		assertTrue(isPaid);

		maxinePage.returns.clickReturnsButton();
		maxinePage.returns.clickReturnLineCheckBox(KiboProductNames.PRODUCT_MIRANDA_PUMP);
		maxinePage.returns.enterReportedIssue(reportedIssue, KiboProductNames.PRODUCT_MIRANDA_PUMP);
		maxinePage.returns.enterQtyToReturn(qtyToReturn, KiboProductNames.PRODUCT_MIRANDA_PUMP);
		maxinePage.returns.enterReturnType(KiboProductNames.PRODUCT_MIRANDA_PUMP, returnType1);

		KiboReturnPage returnPage = maxinePage.returns.clickCreateReturn();
		returnPage.clickIssueRefundButton();

		returnPage.refundDialog.clickReturnedItemCheckBox();
		returnPage.refundDialog.uncheckShippingHandling();
		returnPage.refundDialog.enterCheckNumber();
		returnPage.refundDialog.enterNewCheckValue(refundAmount);
		returnPage.refundDialog.clickIssueRefundButton();

		boolean isRefunded = returnPage.verifyRefunded();
		assertTrue(isRefunded);
	}

	/**
	 * CKIBO 198
	 * creates a sales order for pick up ( no shipping cost)
	 * after fulfilling the order, it issues a return for the full amount.
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboCreateOrderForPickupAndReturnTest( )
	{
		//**********************Test-Data********************//
		String returnType1 = "Refund";
		String reportedIssue = "Damaged";
		String refundAmount = "$53.00";
		String expectedOrderTotal = "$53.00";
		String expectedTax = "$3.00";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

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

		fulfillOrderForPickup();

		boolean isFulfilled = maxinePage.fulfillment.checkIfFulfilled();
		assertTrue(isFulfilled);

		maxinePage.returns.clickReturnsButton();
		maxinePage.returns.clickReturnLineCheckBox(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.returns.enterReportedIssue(reportedIssue, KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.returns.enterReturnType(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP, returnType1);

		KiboReturnPage returnPage = maxinePage.returns.clickCreateReturn();
		returnPage.clickIssueRefundButton();

		returnPage.refundDialog.clickReturnedItemCheckBox();
		returnPage.refundDialog.uncheckShippingHandling();
		returnPage.refundDialog.enterNewStoreCreditValue(refundAmount);
		returnPage.refundDialog.clickIssueRefundButton();

		boolean isRefunded = returnPage.verifyRefunded();
		assertTrue(isRefunded);
	}

	/**
	 * CKIBO 284
	 * creates a sales order with three different items all for pickup,
	 * after order is fulfilled and paid it issues a refund for two of the three
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboMultipleItemsPickupAndReturnTest( )
	{
		//**********************Test-Data********************//
		String returnType1 = "Refund";
		String checkNumber = "123456798";
		String reportedIssue2 = "Missing Parts";
		String reportedIssue3 = "Other";
		String refundAmount = "$116.60";
		String expectedOrderTotal = "$180.20";
		String expectedTax = "$10.20";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_MIRANDA_PUMP);
		maxinePage.orderDetailsDialog.selectPickupHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_KAILAS_PATENT_PUMP);
		maxinePage.orderDetailsDialog.selectPickupHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
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

		boolean isPaid = maxinePage.payment.checkIfPaid();
		assertTrue(isPaid);

		maxinePage.returns.clickReturnsButton();
		maxinePage.returns.clickReturnLineCheckBox(KiboProductNames.PRODUCT_KAILAS_PATENT_PUMP);
		maxinePage.returns.enterReportedIssue(reportedIssue2, KiboProductNames.PRODUCT_KAILAS_PATENT_PUMP);
		maxinePage.returns.enterReturnType(KiboProductNames.PRODUCT_KAILAS_PATENT_PUMP, returnType1);
		maxinePage.returns.clickReturnLineCheckBox(KiboProductNames.PRODUCT_TRENCH_PUMP);
		maxinePage.returns.enterReportedIssue(reportedIssue3, KiboProductNames.PRODUCT_TRENCH_PUMP);
		maxinePage.returns.enterReturnType(KiboProductNames.PRODUCT_TRENCH_PUMP, returnType1);

		KiboReturnPage returnPage = maxinePage.returns.clickCreateReturn();
		returnPage.clickIssueRefundButton();

		returnPage.refundDialog.clickReturnedItemCheckBox();
		returnPage.refundDialog.uncheckShippingHandling();
		returnPage.refundDialog.enterCheckNumber();
		returnPage.refundDialog.enterNewCheckValue(refundAmount);
		returnPage.refundDialog.clickIssueRefundButton();

		boolean isRefunded = returnPage.verifyRefunded();
		assertTrue(isRefunded);
	}

	/**
	 * Test Case CKIBO 297
	 * back office return order, partial amount after fulfilment
	 * verify tax calculation, fulfilled and returned statuses
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboPartialReturnTest( )
	{
		//**********************Test-Data**********************//
		String checkNumber = "123456798";
		String returnType1 = "Refund";
		String amountToRefund = "$50.00";
		String reportedIssue2 = "Different Expectations";
		String expectedOrderTotal = "$15.00";
		String expectedTax = "$5.20";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

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
		assertTrue(isFulfilled);

		payForTheOrder(checkNumber);

		boolean isPaid = maxinePage.payment.checkIfPaid();
		assertTrue(isPaid);

		maxinePage.returns.clickReturnsButton();
		maxinePage.returns.clickReturnLineCheckBox(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.returns.enterReportedIssue(reportedIssue2, KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.returns.enterReturnType(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP, returnType1);

		KiboReturnPage returnPage = maxinePage.returns.clickCreateReturn();
		returnPage.clickIssueRefundButton();

		returnPage.refundDialog.clickReturnedItemCheckBox();
		returnPage.refundDialog.enterRefundAmount(amountToRefund);
		returnPage.refundDialog.enterCheckNumber();
		returnPage.refundDialog.enterNewCheckValue(amountToRefund);
		returnPage.refundDialog.clickIssueRefundButton();

		boolean isRefunded = returnPage.verifyRefunded();
		assertTrue(isRefunded);
	}
}
