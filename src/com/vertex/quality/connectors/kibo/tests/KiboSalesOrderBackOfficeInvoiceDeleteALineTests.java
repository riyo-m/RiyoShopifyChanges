package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboCustomers;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboBackOfficeStorePage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * creates a sales order with invoice form the back office for multiple ProductNames and then deletes
 * one of the ProductNames.
 */
public class KiboSalesOrderBackOfficeInvoiceDeleteALineTests extends KiboTaxCalculationBaseTest
{
	/**
	 * creates a sales order with invoice form the back office for multiple ProductNames and then deletes
	 * one of the ProductNames.
	 */
	@Test(groups = { "kibo_ui" })
	public void kiboSalesOrderMultipleItemsDeleteLineTest( )
	{
		String expectedTax = "$25.20";
		String expectedOrderTotal = "$340.20";

		KiboBackOfficeStorePage maxinePage = navigateToBackOfficeStore();

		selectCustomerAndOpenOrderDetailsDialog(KiboCustomers.Customer1);

		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_KAILAS_PATENT_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_MIRA_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickItemListArrow();
		maxinePage.orderDetailsDialog.selectProduct(KiboProductNames.PRODUCT_IMAGEN_PUMP);
		maxinePage.orderDetailsDialog.selectShippingBaseHomeBase();
		maxinePage.orderDetailsDialog.clickAddButton();
		maxinePage.orderDetailsDialog.clickShippingMethodButton();
		maxinePage.orderDetailsDialog.clickFlatRate();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();

		maxinePage.orderHeader.clickSubmitOrder();

		maxinePage.clickEditDetails();

		maxinePage.orderDetailsDialog.deleteProduct(KiboProductNames.PRODUCT_MIRA_PUMP);
		maxinePage.orderDetailsDialog.clickYesToDeleteProduct();
		maxinePage.orderDetailsDialog.clickOrderSaveButton();

		String actualTax = maxinePage.getTaxAmount();
		boolean isTaxCorrect = expectedTax.equals(actualTax);
		assertTrue(isTaxCorrect);

		String orderTotal = maxinePage.getOrderTotal();
		boolean isOrderTotalCorrect = expectedOrderTotal.equals(orderTotal);
		assertTrue(isOrderTotalCorrect);
	}
}
