package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.common.enums.Status;
import com.vertex.quality.connectors.episerver.pages.*;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverAutomaticInvoiceOptionTest( )
	{
		EpiAdminHomePage admindashboardpage;
		EpiStoreFrontHomePage portalhomepage;
		EpiAddressPage addresspage;
		EpiOseriesPage oSeriespage;

		// login as Admin user into EpiserverAdmin Page
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();

		// navigate to Vertex O Series Page
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();

		// Validate OSeries Connector Status
		String expectedStatus = Status.GOOD.text;
		String actualstatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean connectorresult = actualstatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(connectorresult, "Vertex O Series Connector status is not valid/good");
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Enable Invoice Option - Invoice Automatically And Validate the Connector
		// Status
		EpiOseriesInvocingPage invoicingpage;
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(true);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		String actualinvoicestatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean invoiceresult = actualinvoicestatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(invoiceresult, "Vertex O Series Connector status is not valid/good");
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Logout from Admin portal
		admindashboardpage.adminlogout();

		// Login into Episerver Customer Portal with Customer Credentials
		portalhomepage = logInAsCustomer();

		EpiCartPage cart = new EpiCartPage(driver);
		portalhomepage.navigateToHomePage();
		// Clear all items from Cart
		cart.clearAllItemsInCart();

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-21320040");

		// Proceed to checkout
		EpiCheckoutPage checkoutpage;
		checkoutpage = cart.proceedToCheckout();
		// Get product name with item code
		String productName1 = cart.getItemTitleWithProductCode("SKU-21320040");
		// Add New Billing Address
		EpiAddAddressPage addaddresspage;
		addaddresspage = checkoutpage.clickAddNewAddressButton();
		addaddresspage.enterUCityAddressDetails();
		// addaddresspage.clickOnAddressCloseButton();

		// Select newly added Address from Shipping Address Drop-down
		checkoutpage.selectShippingAddress("INVOICEONCAADDR");

		// Select newly added Address from Billing Address Drop-down
		checkoutpage.selectBillingAddress("INVOICEONCAADDR");
		checkoutpage.validateBillingAddressSelected("INVOICEONCAADDR");

		// Choose Credit card option, enter Credit card details and Select Delivery
		// Method
		checkoutpage.choosePaymentMethod("Credit card");
		checkoutpage.selectCreditCardAndEnterDetails();

		// Choose Delivery Method
		checkoutpage.chooseDeliveryMethodAs("Regular", null);

		// Expected Data for Calculations on Checkout page
		double PRODUCT_1_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_2_UNIT_PRICE.text);
		int DEFAULT_QUANTITY = Integer.parseInt(EpiDataCommon.DefaultAmounts.DEFAULT_QUANTITY.text);
		double PRODUCT_1_PRICE = DEFAULT_QUANTITY * PRODUCT_1_UNIT_PRICE;
		double ITEMS_SUBTOTAL = PRODUCT_1_PRICE;
		double ADD_ORDER_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double DEFAULT_HANDLING_COST = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_HANDLING_COST.text);
		double TAX_COST = Double.parseDouble(EpiDataCommon.Taxes.TAX_14.text);
		double REGULAT_SHIPPING_TOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.REGULAR_SHIPPING_SUB_TOTAL.text);
		double SHIPPING_AND_TAX = REGULAT_SHIPPING_TOTAL + TAX_COST;
		double CART_TOTAL = ITEMS_SUBTOTAL + SHIPPING_AND_TAX;
		double SHIPPING_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double REGULAR_SHIPPING_COST = REGULAT_SHIPPING_TOTAL + SHIPPING_DISCOUNT;
		double REGULAR_SHIPPING_SUBTOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.REGULAR_SHIPPING_SUB_TOTAL.text);
		double REGULAR_SHIPPING_TOTAL = REGULAR_SHIPPING_SUBTOTAL;

		// Validating subtotal for items
		double actualSubTotalForItemsPrd1 = checkoutpage.getSubTotalForYourItems();
		assertEquals(actualSubTotalForItemsPrd1, ITEMS_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual subtotal for items :%s is matched with expected: %s"), actualSubTotalForItemsPrd1,
				ITEMS_SUBTOTAL));

		// Validating order level additional discounts
		double actualOrderLevelAddDisPrd1 = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		assertEquals(actualOrderLevelAddDisPrd1, ADD_ORDER_DISCOUNT);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDisPrd1, ADD_ORDER_DISCOUNT));

		// Validating shipping and tax amount
		double actualShippingTaxPrd1 = checkoutpage.getShippingAndTaxAmount();
		assertEquals(actualShippingTaxPrd1, SHIPPING_AND_TAX);
		VertexLogger.log(
			String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualShippingTaxPrd1,
				SHIPPING_AND_TAX));

		// getting shipping and tax as map
		Map<String, Double> shippingTaxAmounts = checkoutpage.getShippingAndTaxMapDouble();

		// validating shipping sub_total
		double actualShippingSubtotal = shippingTaxAmounts.get("Shipping Subtotal");
		assertEquals(actualShippingSubtotal, REGULAR_SHIPPING_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotal,
				REGULAR_SHIPPING_SUBTOTAL));

		// Validating shipping discount
		double actualShippingDiscount = shippingTaxAmounts.get("Shipping Discount");
		assertEquals(actualShippingDiscount, SHIPPING_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscount,
				SHIPPING_DISCOUNT));

		// Validating shipping total
		double actualShippingTotal = shippingTaxAmounts.get("Shipping Total");
		assertEquals(actualShippingTotal, REGULAR_SHIPPING_TOTAL);
		VertexLogger.log(String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotal,
			REGULAR_SHIPPING_TOTAL));

		// Validating shipping tax
		double actualTax = shippingTaxAmounts.get("Tax");
		assertEquals(actualTax, TAX_COST);
		VertexLogger.log(String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTax, TAX_COST));

		// Validating total for cart
		double actualTotalForCart = checkoutpage.getTotalForCart();
		assertEquals(actualTotalForCart, CART_TOTAL);
		VertexLogger.log(
			String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCart, CART_TOTAL));

		// Place order
		EpiOrderConfirmationPage orderconfirmpage;
		orderconfirmpage = checkoutpage.clickPlaceOrderButton();

		// Get Order Number
		String orderNumber = orderconfirmpage.getOrderNumber();
		VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber));

		// Validate quantity after order placed
		int actualQuantityPrd1 = orderconfirmpage.getQuantity(productName1);
		assertEquals(actualQuantityPrd1, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantityPrd1, DEFAULT_QUANTITY));

		// Validate unit price after order placed
		double actualUnitPricePrd1 = orderconfirmpage.getUnitPrice(productName1);
		assertEquals(actualUnitPricePrd1, PRODUCT_1_UNIT_PRICE);
		VertexLogger.log(String.format(("Actual Unit price after order placed :%s is matched with expected: %s"),
			actualUnitPricePrd1, PRODUCT_1_UNIT_PRICE));

		// Validate price after order placed
		double actualPricePrd1 = orderconfirmpage.getPrice(productName1);
		assertEquals(actualPricePrd1, PRODUCT_1_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPricePrd1,
				PRODUCT_1_PRICE));

		// Validate shipping Discount after order placed
		double actualShippingDiscountOrderPlacedPrd1 = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountOrderPlacedPrd1, SHIPPING_DISCOUNT);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountOrderPlacedPrd1, SHIPPING_DISCOUNT));

		// Validate shipping total after order placed
		double actualTotalPrd1 = orderconfirmpage.getTotalWithOutTax(productName1);
		assertEquals(actualTotalPrd1, ITEMS_SUBTOTAL);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalPrd1, ITEMS_SUBTOTAL));

		// Validate additional discounts after order placed
		double actualAdditionalDiscountsPrd1 = orderconfirmpage.getAdditionalDiscounts();
		assertEquals(actualAdditionalDiscountsPrd1, ADD_ORDER_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual additional discounts after order placed :%s is matched with expected: %s"),
				actualAdditionalDiscountsPrd1, ADD_ORDER_DISCOUNT));

		// Validate handling cost after order placed
		double actualHandlingCost = orderconfirmpage.getHandlingCost();
		assertEquals(actualHandlingCost, DEFAULT_HANDLING_COST);
		VertexLogger.log(String.format(("Actual handling cost after order placed :%s is matched with expected: %s"),
			actualHandlingCost, DEFAULT_HANDLING_COST));

		// Validate shipping Sub total after order placed
		double actualShippingSubtotalAfterOrderPlacedPrd1 = orderconfirmpage.getShippingSubtotal();
		assertEquals(actualShippingSubtotalAfterOrderPlacedPrd1, REGULAR_SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping subtotal after order placed :%s is matched with expected: %s"),
			actualShippingSubtotalAfterOrderPlacedPrd1, REGULAR_SHIPPING_SUBTOTAL));

		// Validate shipping discount after order placed
		double actualShippingDiscountAfterOrderPlacedPrd1 = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountAfterOrderPlacedPrd1, SHIPPING_DISCOUNT);
		VertexLogger.log(String.format(("Actual shipping discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountAfterOrderPlacedPrd1, SHIPPING_DISCOUNT));

		// Validate shipping cost after order placed
		double actualShippingCostAfterOrderPlacedPrd1 = orderconfirmpage.getShippingCost();
		assertEquals(actualShippingCostAfterOrderPlacedPrd1, REGULAR_SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping cost after order placed :%s is matched with expected: %s"),
			actualShippingCostAfterOrderPlacedPrd1, REGULAR_SHIPPING_SUBTOTAL));

		// Validate tax cost after order placed
		double actualTaxCostAfterOrderPlacedPrd1 = orderconfirmpage.getTaxCost();
		assertEquals(actualTaxCostAfterOrderPlacedPrd1, TAX_COST);
		VertexLogger.log(String.format(("Actual tax cost after order placed :%s is matched with expected: %s"),
			actualTaxCostAfterOrderPlacedPrd1, TAX_COST));

		// Validate final cost after order placed
		double actualFinalCostAfterOrderPlacedPrd1 = orderconfirmpage.getFinalTotal();
		assertEquals(actualFinalCostAfterOrderPlacedPrd1, CART_TOTAL);
		VertexLogger.log(String.format(("Actual final cost after order placed :%s is matched with expected: %s"),
			actualFinalCostAfterOrderPlacedPrd1, CART_TOTAL));

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("INVOICEONCAADDR", true);
		portalhomepage.portallogout();
		// login as Admin user into EpiserverAdmin Page
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();

		// navigate to Vertex O Series Page
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();

		// Validate OSeries Connector Status
		String actualstatus1 = oSeriespage.getVertexOseriesConnectorStatus();
		boolean connectorresult1 = actualstatus1
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(connectorresult1, "Vertex O Series Connector status is not valid/good");
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Disable Invoice Option - Invoice Automatically And Validate the Connector
		// Status
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(false);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		String actualinvoicestatus1 = oSeriespage.getVertexOseriesConnectorStatus();
		boolean invoiceresult1 = actualinvoicestatus1
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(invoiceresult1, "Vertex O Series Connector status is not valid/good");
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Logout from Admin portal
		admindashboardpage.adminlogout();

		// Login into Episerver Customer Portal with Customer Credentials
		portalhomepage = logInAsCustomer();

		portalhomepage.navigateToHomePage();

		// Clear all items from Cart
		cart.clearAllItemsInCart();

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-21320040");

		checkoutpage = cart.proceedToCheckout();
		// Add New Billing Address
		addaddresspage = checkoutpage.clickAddNewAddressButton();
		addaddresspage.enterUCityAddressDetails();
		// addaddresspage.clickOnAddressCloseButton();

		checkoutpage.selectShippingAddress("INVOICEONCAADDR");
		// Select billing address from billing address dropdown
		checkoutpage.selectBillingAddress("INVOICEONCAADDR");
		checkoutpage.validateBillingAddressSelected("INVOICEONCAADDR");
		checkoutpage.selectCreditCardAndEnterDetails();
		String productName2 = cart.getItemTitleWithProductCode("SKU-21320040");
		checkoutpage.chooseDeliveryMethodAs("Regular", null);

		// Validating subtotal for items
		double actualSubTotalForItemsProduct2 = checkoutpage.getSubTotalForYourItems();
		assertEquals(actualSubTotalForItemsProduct2, ITEMS_SUBTOTAL);
		VertexLogger.log(String.format(("Actual subtotal for items :%s is matched with expected: %s"),
			actualSubTotalForItemsProduct2, ITEMS_SUBTOTAL));

		// Validating order level additional discounts
		double actualOrderLevelAddDisPrd2 = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		assertEquals(actualOrderLevelAddDisPrd2, ADD_ORDER_DISCOUNT);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDisPrd2, ADD_ORDER_DISCOUNT));

		// Validating shipping and tax amount
		double actualShippingTaxPrd2 = checkoutpage.getShippingAndTaxAmount();
		assertEquals(actualShippingTaxPrd2, SHIPPING_AND_TAX);
		VertexLogger.log(
			String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualOrderLevelAddDisPrd2,
				SHIPPING_AND_TAX));

		// getting shipping and tax as map
		Map<String, Double> shippingTaxAmountsPrd2 = checkoutpage.getShippingAndTaxMapDouble();

		// validating shipping sub_total
		double actualShippingSubtotalPrd2 = shippingTaxAmountsPrd2.get("Shipping Subtotal");
		assertEquals(actualShippingSubtotalPrd2, REGULAR_SHIPPING_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotalPrd2,
				REGULAR_SHIPPING_SUBTOTAL));

		// Validating shipping discount
		double actualShippingDiscountPrd2 = shippingTaxAmountsPrd2.get("Shipping Discount");
		assertEquals(actualShippingDiscountPrd2, SHIPPING_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscountPrd2,
				SHIPPING_DISCOUNT));

		// Validating shipping total
		double actualShippingTotalPrd2 = shippingTaxAmountsPrd2.get("Shipping Total");
		assertEquals(actualShippingTotalPrd2, REGULAR_SHIPPING_TOTAL);
		VertexLogger.log(
			String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotalPrd2,
				REGULAR_SHIPPING_TOTAL));

		// Validating shipping tax
		double actualTaxPrd2 = shippingTaxAmountsPrd2.get("Tax");
		assertEquals(actualTaxPrd2, TAX_COST);
		VertexLogger.log(
			String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTaxPrd2, TAX_COST));

		// Validating total for cart
		double actualTotalForCartPrd2 = checkoutpage.getTotalForCart();
		assertEquals(actualTotalForCartPrd2, CART_TOTAL);
		VertexLogger.log(
			String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCartPrd2,
				CART_TOTAL));

		// Place order
		orderconfirmpage = checkoutpage.clickPlaceOrderButton();

		// Validate quantity of after order placed
		int actualQuantityPrd2 = orderconfirmpage.getQuantity(productName2);
		assertEquals(actualQuantityPrd2, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantityPrd2, DEFAULT_QUANTITY));

		// Validate unitprice after order placed
		double actualUnitPricePrd2 = orderconfirmpage.getUnitPrice(productName2);
		assertEquals(actualUnitPricePrd2, PRODUCT_1_UNIT_PRICE);
		VertexLogger.log(String.format(("Actual Unit price after order placed :%s is matched with expected: %s"),
			actualUnitPricePrd2, PRODUCT_1_UNIT_PRICE));

		// Validate price after order placed
		double actualPricePrd2 = orderconfirmpage.getPrice(productName2);
		assertEquals(actualPricePrd2, PRODUCT_1_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPricePrd2,
				PRODUCT_1_PRICE));

		// Validate Discount after order placed
		double actualShippingDiscountOrderPlacedPrd2 = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountOrderPlacedPrd2, SHIPPING_DISCOUNT);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountOrderPlacedPrd2, SHIPPING_DISCOUNT));

		// Validate total without tax after order placed
		double actualTotalWithOutTaxPrd2 = orderconfirmpage.getTotalWithOutTax(productName2);
		assertEquals(actualTotalWithOutTaxPrd2, ITEMS_SUBTOTAL);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalWithOutTaxPrd2, ITEMS_SUBTOTAL));

		// Validate additional discounts after order placed
		double actualAdditionalDiscountsPrd2 = orderconfirmpage.getAdditionalDiscounts();
		assertEquals(actualAdditionalDiscountsPrd2, ADD_ORDER_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual additional discounts after order placed :%s is matched with expected: %s"),
				actualAdditionalDiscountsPrd2, ADD_ORDER_DISCOUNT));

		// Validate handling cost after order placed
		double actualHandlingCostPrd2 = orderconfirmpage.getHandlingCost();
		assertEquals(actualHandlingCostPrd2, DEFAULT_HANDLING_COST);
		VertexLogger.log(String.format(("Actual handling cost after order placed :%s is matched with expected: %s"),
			actualHandlingCostPrd2, DEFAULT_HANDLING_COST));

		// Validate shipping Sub-total after order placed
		double actualShippingSubtotalAfterOrderPlacedPrd2 = orderconfirmpage.getShippingSubtotal();
		assertEquals(actualShippingSubtotalAfterOrderPlacedPrd2, REGULAR_SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping subtotal after order placed :%s is matched with expected: %s"),
			actualShippingSubtotalAfterOrderPlacedPrd2, REGULAR_SHIPPING_SUBTOTAL));

		// Validate shipping discount after order placed
		double actualShippingDiscountAfterOrderPlacedPrd2 = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountAfterOrderPlacedPrd2, SHIPPING_DISCOUNT);
		VertexLogger.log(String.format(("Actual shipping discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountAfterOrderPlacedPrd2, SHIPPING_DISCOUNT));

		// Validate shipping cost after order placed
		double actualShippingCostAfterOrderPlacedPrd2 = orderconfirmpage.getShippingCost();
		assertEquals(actualShippingCostAfterOrderPlacedPrd2, REGULAR_SHIPPING_COST);
		VertexLogger.log(String.format(("Actual shipping cost after order placed :%s is matched with expected: %s"),
			actualShippingCostAfterOrderPlacedPrd2, REGULAR_SHIPPING_COST));

		// Validate tax cost after order placed
		double actualTaxCostAfterOrderPlacedPrd2 = orderconfirmpage.getTaxCost();
		assertEquals(actualTaxCostAfterOrderPlacedPrd2, TAX_COST);
		VertexLogger.log(String.format(("Actual tax cost after order placed :%s is matched with expected: %s"),
			actualTaxCostAfterOrderPlacedPrd2, TAX_COST));

		// Validate final cost after order placed
		double actualFinalCostAfterOrderPlacedPrd2 = orderconfirmpage.getFinalTotal();
		assertEquals(actualFinalCostAfterOrderPlacedPrd2, CART_TOTAL);
		VertexLogger.log(String.format(("Actual final cost after order placed :%s is matched with expected: %s"),
			actualFinalCostAfterOrderPlacedPrd2, CART_TOTAL));

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("INVOICEONCAADDR", true);
	}
}
