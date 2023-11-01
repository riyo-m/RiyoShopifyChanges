package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.pages.*;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class EpiMultipleShipmentsAndFreightsOnOrderFormTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverMultipleShipmentsAndFreightsOnOrderFormTest( )
	{
		EpiAdminHomePage admindashboardpage;
		EpiStoreFrontHomePage portalhomepage;
		EpiAddressPage addresspage;
		EpiOseriesPage oSeriespage;
		EpiAddAddressPage addaddresspage;

		// login as Admin user into EpiserverAdmin Page
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();

		// navigate to Vertex O Series Page
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();

		// Validate OSeries Connector Status
		oSeriespage.validateConnectorTooltip("Vertex O Series");
		oSeriespage.validateVertexOSeriesConnectorStatus();

		// Enable Invoice Option - Invoice Automatically And Validate the Connector
		// Status
		EpiOseriesInvocingPage invoicingpage;
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(true);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		oSeriespage.validateConnectorTooltip("Vertex O Series");

		// Logout from Admin portal
		admindashboardpage.adminlogout();

		// Login into Epi-server Customer Portal with Customer Credentials
		portalhomepage = logInAsCustomer();

		EpiCartPage cart = new EpiCartPage(driver);
		portalhomepage.navigateToHomePage();

		// Clear all items from Cart
		cart.clearAllItemsInCart();

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-42382780");

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-21320040");

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-38543689");

		// Proceed to checkout
		EpiCheckoutPage checkoutpage;
		checkoutpage = cart.proceedToCheckout();

		// Check presence of ship to multiple addresses
		addaddresspage = invoicingpage.clickShipMultipleAddressButton();

		// Adding address 1 for product 1
		invoicingpage.clickMultiAddress1AddButton();
		addaddresspage.enterFolsomCityAddressDetails();
		addaddresspage.clickOnAddressCloseButton();

		// Adding address 2 for product 2
		invoicingpage.clickMultiAddress2AddButton();
		addaddresspage.enterDurhamCityAddressDetails();
		addaddresspage.clickOnAddressCloseButton();

		// Adding address 3 for product 3
		invoicingpage.clickMultiAddress3AddButton();
		addaddresspage.enterTysonsCityAddressDetails();
		addaddresspage.clickOnAddressCloseButton();

		// select address for various products
		checkoutpage.selectShipMultiAddress1("FIRSTSHIPINGADDRCA");
		checkoutpage.selectShipMultiAddress2("SECONDSHIPINGADDRNC");
		checkoutpage.selectShipMultiAddress3("THIRDSHIPINGADDRVA");
		invoicingpage.clickContinueButton();

		// Choose Delivery Method as regular for product 2
		String productName2 = cart.getItemTitleWithProductCode("SKU-42382780");
		checkoutpage.chooseDeliveryMethodAs("Regular", productName2);

		// Choose Delivery Method as fast for product 3
		String productName3 = cart.getItemTitleWithProductCode("SKU-21320040");

		checkoutpage.chooseDeliveryMethodAs("Fast", productName3);

		// Choose Delivery Method as express for product 4
		String productName4 = cart.getItemTitleWithProductCode("SKU-38543689");
		checkoutpage.chooseDeliveryMethodAs("Express", productName4);

		// Select newly added Address from Billing Address Drop-down
		checkoutpage.selectBillingAddress("FIRSTSHIPINGADDRCA");
		checkoutpage.validateBillingAddressSelected("INVOICEONCAADDR");

		// Choose Credit card option, enter Credit card details and Select Delivery
		// Method
		checkoutpage.choosePaymentMethod("Credit card");
		checkoutpage.selectCreditCardAndEnterDetails();

		// Expected Data for Calculations on Checkout page
		double PRODUCT_2_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_3_UNIT_PRICE.text);
		double PRODUCT_3_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_2_UNIT_PRICE.text);
		double PRODUCT_4_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_4_UNIT_PRICE.text);

		double DEFAULT_QUANTITY = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_QUANTITY.text);

		double PRODUCT_2_PRICE = DEFAULT_QUANTITY * PRODUCT_2_UNIT_PRICE;
		double PRODUCT_3_PRICE = DEFAULT_QUANTITY * PRODUCT_3_UNIT_PRICE;
		double PRODUCT_4_PRICE = DEFAULT_QUANTITY * PRODUCT_4_UNIT_PRICE;
		double DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_DISCOUNT.text);

		double ITEM_TOTAL_PRD2 = PRODUCT_2_PRICE - DISCOUNT;
		double ITEM_TOTAL_PRD3 = PRODUCT_3_PRICE - DISCOUNT;
		double ITEM_TOTAL_PRD4 = PRODUCT_4_PRICE - DISCOUNT;

		double ITEMS_SUBTOTAL = PRODUCT_2_PRICE + PRODUCT_3_PRICE + PRODUCT_4_PRICE;
		double ADD_ORDER_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double DEFAULT_HANDLING_COST = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_HANDLING_COST.text);
		double TAX_COST = Double.parseDouble(EpiDataCommon.Taxes.TAX_8.text);
		double FAST_SHIPPING_SUBTOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.FAST_SHIPPING_SUB_TOTAL.text);
		double REGULAR_SHIPPING_SUBTOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.REGULAR_SHIPPING_SUB_TOTAL.text);
		double EXPRESS_SHIPPING_SUBTOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.EXPRESS_SHIPPING_SUB_TOTAL.text);
		double SHIPPING_SUBTOTAL = FAST_SHIPPING_SUBTOTAL + REGULAR_SHIPPING_SUBTOTAL + EXPRESS_SHIPPING_SUBTOTAL;
		double SHIPPING_AND_TAX = SHIPPING_SUBTOTAL + TAX_COST;
		double CART_TOTAL = ITEMS_SUBTOTAL + SHIPPING_AND_TAX;
		double SHIPPING_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double SHIPPING_COST = SHIPPING_SUBTOTAL + SHIPPING_DISCOUNT;

		// Validating sub-total for item
		double actualSubTotalForItems = checkoutpage.getSubTotalForYourItems();
		assertEquals(actualSubTotalForItems, ITEMS_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual subtotal for items :%s is matched with expected: %s"), actualSubTotalForItems,
				ITEMS_SUBTOTAL), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating order level additional discounts
		double actualOrderLevelAddDis = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		assertEquals(actualOrderLevelAddDis, ADD_ORDER_DISCOUNT);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDis, ADD_ORDER_DISCOUNT), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping and tax amount
		double actualShippingTax = checkoutpage.getShippingAndTaxAmount();
		assertEquals(actualShippingTax, SHIPPING_AND_TAX);
		VertexLogger.log(
			String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualOrderLevelAddDis,
				SHIPPING_AND_TAX), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// getting shipping and tax as map
		Map<String, Double> shippingTaxAmounts = checkoutpage.getShippingAndTaxMapDouble();

		// validating shipping sub_total
		double actualShippingSubtotal = shippingTaxAmounts.get("Shipping Subtotal");
		assertEquals(actualShippingSubtotal, SHIPPING_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotal,
				SHIPPING_SUBTOTAL), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping discount
		double actualShippingDiscount = shippingTaxAmounts.get("Shipping Discount");
		assertEquals(actualShippingDiscount, SHIPPING_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscount,
				SHIPPING_DISCOUNT), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping total
		double actualShippingTotal = shippingTaxAmounts.get("Shipping Total");
		assertEquals(actualShippingTotal, SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotal,
			SHIPPING_SUBTOTAL), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping tax
		double actualTax = shippingTaxAmounts.get("Tax");
		assertEquals(actualTax, TAX_COST);
		VertexLogger.log(String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTax, TAX_COST),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating total for cart
		double actualTotalForCart = checkoutpage.getTotalForCart();
		assertEquals(actualTotalForCart, CART_TOTAL);
		VertexLogger.log(
			String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCart, CART_TOTAL),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Place order
		EpiOrderConfirmationPage orderconfirmpage;
		orderconfirmpage = checkoutpage.clickPlaceOrderButton();

		// Get Order Number
		String orderNumber = orderconfirmpage.getOrderNumber();
		VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber), EpiCartPage.class);
		// get product quantity for product

		// Validate quantity after order placed of three products
		// product1
		int actualQuantity = orderconfirmpage.getQuantity(productName2);
		assertEquals(actualQuantity, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantity, DEFAULT_QUANTITY),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate quantity after order placed product 2
		int actualQuantityPrd2 = orderconfirmpage.getQuantity(productName3);
		assertEquals(actualQuantityPrd2, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantityPrd2, DEFAULT_QUANTITY),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate quantity after order placed product 2
		int actualQuantityPrd3 = orderconfirmpage.getQuantity(productName4);
		assertEquals(actualQuantityPrd3, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantityPrd3, DEFAULT_QUANTITY),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate unit price after order placed product 3
		double actualUnitPrice = orderconfirmpage.getUnitPrice(productName3);
		assertEquals(actualUnitPrice, PRODUCT_3_UNIT_PRICE);
		VertexLogger.log(
			String.format(("Actual Unit price after order placed :%s is matched with expected: %s"), actualUnitPrice,
				PRODUCT_3_UNIT_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate unit price after order placed product 2
		double actualUnitPricePrd2 = orderconfirmpage.getUnitPrice(productName2);
		assertEquals(actualUnitPricePrd2, PRODUCT_2_UNIT_PRICE);
		VertexLogger.log(String.format(("Actual Unit price after order placed :%s is matched with expected: %s"),
			actualUnitPricePrd2, PRODUCT_2_UNIT_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate unit price after order placed product 4
		double actualUnitPricePrd4 = orderconfirmpage.getUnitPrice(productName4);
		assertEquals(actualUnitPricePrd4, PRODUCT_4_UNIT_PRICE);
		VertexLogger.log(String.format(("Actual Unit price after order placed :%s is matched with expected: %s"),
			actualUnitPricePrd4, PRODUCT_4_UNIT_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate price after order placed
		double actualPrice3 = orderconfirmpage.getPrice(productName3);
		assertEquals(actualPrice3, PRODUCT_3_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPrice3,
				PRODUCT_3_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate price after order placed
		double actualPrice2 = orderconfirmpage.getPrice(productName2);
		assertEquals(actualPrice2, PRODUCT_2_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPrice2,
				PRODUCT_2_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate price after order placed
		double actualPrice4 = orderconfirmpage.getPrice(productName4);
		assertEquals(actualPrice4, PRODUCT_4_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPrice4,
				PRODUCT_4_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate Discount after order placed
		double actualShippingDiscountOrderPlaced = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountOrderPlaced, SHIPPING_DISCOUNT);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountOrderPlaced, SHIPPING_DISCOUNT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate total after order placed
		double actualTotalPrd2 = orderconfirmpage.getTotalWithOutTax(productName2);
		assertEquals(actualTotalPrd2, ITEM_TOTAL_PRD2);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalPrd2, ITEM_TOTAL_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate total after order placed
		double actualTotalPrd3 = orderconfirmpage.getTotalWithOutTax(productName3);
		assertEquals(actualTotalPrd3, ITEM_TOTAL_PRD3);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalPrd3, ITEM_TOTAL_PRD3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);
		// Validate total after order placed
		double actualTotalPrd4 = orderconfirmpage.getTotalWithOutTax(productName4);
		assertEquals(actualTotalPrd4, ITEM_TOTAL_PRD4);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalPrd2, ITEM_TOTAL_PRD4), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate additional discounts after order placed
		double actualAdditionalDiscounts = orderconfirmpage.getAdditionalDiscounts();
		assertEquals(actualAdditionalDiscounts, ADD_ORDER_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual additional discounts after order placed :%s is matched with expected: %s"),
				actualAdditionalDiscounts, ADD_ORDER_DISCOUNT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate handling cost after order placed
		double actualHandlingCost = orderconfirmpage.getHandlingCost();
		assertEquals(actualHandlingCost, DEFAULT_HANDLING_COST);
		VertexLogger.log(String.format(("Actual handling cost after order placed :%s is matched with expected: %s"),
			actualHandlingCost, DEFAULT_HANDLING_COST), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping Sub-total after order placed
		double actualShippingSubtotalAfterOrderPlaced = orderconfirmpage.getShippingSubtotal();
		assertEquals(actualShippingSubtotalAfterOrderPlaced, SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping subtotal after order placed :%s is matched with expected: %s"),
			actualShippingSubtotalAfterOrderPlaced, SHIPPING_SUBTOTAL),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping discount after order placed
		double actualShippingDiscountAfterOrderPlaced = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountAfterOrderPlaced, SHIPPING_DISCOUNT);
		VertexLogger.log(String.format(("Actual shipping discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountAfterOrderPlaced, SHIPPING_DISCOUNT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping cost after order placed
		double actualShippingCostAfterOrderPlaced = orderconfirmpage.getShippingCost();
		assertEquals(actualShippingCostAfterOrderPlaced, SHIPPING_COST);
		VertexLogger.log(String.format(("Actual shipping cost after order placed :%s is matched with expected: %s"),
			actualShippingCostAfterOrderPlaced, SHIPPING_COST),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate tax cost after order placed
		double actualTaxCostAfterOrderPlaced = orderconfirmpage.getTaxCost();
		assertEquals(actualTaxCostAfterOrderPlaced, TAX_COST);
		VertexLogger.log(String.format(("Actual tax cost after order placed :%s is matched with expected: %s"),
			actualTaxCostAfterOrderPlaced, TAX_COST), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate final cost after order placed
		double actualFinalCostAfterOrderPlaced = orderconfirmpage.getFinalTotal();
		assertEquals(actualFinalCostAfterOrderPlaced, CART_TOTAL);
		VertexLogger.log(String.format(("Actual final cost after order placed :%s is matched with expected: %s"),
			actualFinalCostAfterOrderPlaced, CART_TOTAL), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("FIRSTSHIPINGADDRCA", true);

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("SECONDSHIPINGADDRNC", true);

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("THIRDSHIPINGADDRVA", true);
	}
}
