package com.vertex.quality.connectors.kibo.tests;

import com.vertex.quality.connectors.kibo.enums.KiboProductCategory;
import com.vertex.quality.connectors.kibo.enums.KiboProductNames;
import com.vertex.quality.connectors.kibo.pages.KiboStoreFrontCartPage;
import com.vertex.quality.connectors.kibo.pages.KiboStoreFrontCheckoutPage;
import com.vertex.quality.connectors.kibo.pages.KiboStoreFrontPage;
import com.vertex.quality.connectors.kibo.tests.base.KiboTaxCalculationBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * this test case class creates a sales order without invoice
 * Same billing and shipping Addresses
 * multiple lines different ProductNames
 *
 * @author osabha
 */
public class KiboSalesOrderWithoutInvoiceTests extends KiboTaxCalculationBaseTest
{
	@Test(groups = { "kibo_ui" })
	public void kiboSalesOrderWithoutInvoiceTest( )
	{
		//*******************Test-Data******************************//
		String expectedTax = "$17.57";

		KiboStoreFrontPage maxineLivePage = navigateToStoreFront();

		KiboStoreFrontCartPage cartPage = maxineLivePage.addProductToCart(KiboProductCategory.BAGS,
			KiboProductNames.LEATHER_DAMASK_CLUTCH);
		KiboStoreFrontPage maxineLivePage1 = cartPage.clickMaxineHeading();

		KiboStoreFrontCartPage cartPage1 = maxineLivePage1.addProductToCart(KiboProductCategory.SHOES,
			KiboProductNames.MEREDITH_ANGELA_MIRANDA_PUMP);
		KiboStoreFrontPage maxineLivePage2 = cartPage1.clickMaxineHeading();

		KiboStoreFrontCartPage cartPage2 = maxineLivePage2.addProductToCart(KiboProductCategory.ACCESSORIES,
			KiboProductNames.LEATHER_DRIVING_CAP);
		KiboStoreFrontCheckoutPage checkoutPage = cartPage2.goToCheckoutPage();

		checkoutPage.shipping.enterFirstName();
		checkoutPage.shipping.enterLastName();
		checkoutPage.shipping.enterAddress();
		checkoutPage.shipping.enterCity();
		checkoutPage.shipping.selectState();
		checkoutPage.shipping.enterZip();
		checkoutPage.shipping.enterPhoneNumber();
		checkoutPage.shipping.clickNextToShippingMethod();

		checkoutPage.shippingMethod.selectFlatRateShipping();
		checkoutPage.shippingMethod.clickNextToPayment();

		checkoutPage.payment.clickCheckByMail();
		checkoutPage.payment.clickBillingSameAsShippingAddress();

		String actualTax = checkoutPage.orderSummary.getTaxAmount();
		assertEquals(expectedTax, actualTax);
	}
}
