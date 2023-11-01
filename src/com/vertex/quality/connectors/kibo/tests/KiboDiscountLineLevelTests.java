package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboDiscounts;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * This test creates a basic sales order with an existing customer info,
 * different shipping and billing Addresses for this customer.
 * applying discount on the item selected .
 * then verifying the order total and calculated tax amount.
 *
 * @author osabha
 */
public class KiboDiscountLineLevelTests extends KiboTaxCalculationBaseTest
{
	/**
	 * runs a basic sales order and selects a discount
	 * verified discount is applied and verified tax calculations
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboDiscountLineLevelTest( )
	{
		//**********************Test-Data********************//
		String expectedOrderTotal = "$54.00";
		String expectedTax = "$4.00";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_GETTA_PATENT_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickShippingMethodButton();
		maxinePage.orderDetailsDialog.clickFlatRate();
		maxinePage.orderDetailsDialog.clickDiscountListArrow();
		maxinePage.orderDetailsDialog.selectDiscount(KiboDiscounts.DISCOUNT1);
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
