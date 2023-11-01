package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.*;
import com.vertex.quality.connectors.episerver.pages.*;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EpiCheckoutBlankInvalidShipToAddressTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverCheckoutBlankAndInvalidShipToAddressTest( )
	{
		// DecimalFormat df = new DecimalFormat("#0.00");
		EpiAdminHomePage admindashboardpage;
		EpiStoreFrontHomePage portalhomepage;
		EpiOseriesPage oSeriespage;
		EpiAddressPage addresspage;

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

		// Disable Invoice Option - Invoice Automatically And Validate the Connector
		// Status
		EpiOseriesInvocingPage invoicingpage;
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(false);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		String actualinvoicestatus = oSeriespage.getVertexOseriesConnectorStatus();
		boolean invoiceresult = actualinvoicestatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(invoiceresult, "Vertex O Series Connector status is not valid/good");

		// Logout from Admin Portal
		admindashboardpage.adminlogout();

		// Login to StoreFront Portal as Customer Login
		portalhomepage = logInAsCustomer();
		portalhomepage.navigateToHomePage();

		// Clear Items from Cart if exists
		EpiCartPage cart = new EpiCartPage(driver);
		cart.clearAllItemsInCart();

		// Search and Add Product to Cart
		String productcode = ProductCodes.SKU21320040.text;
		cart.searchAndAddProductToCart(productcode);
		String productName = cart.getItemTitleWithProductCode(productcode);

		// Proceed to checkout
		EpiCheckoutPage checkoutpage;
		checkoutpage = cart.proceedToCheckout();

		// Select NewYork Address from Billing Address Dropdown
		String state = Address.NewYork.state.fullName;
		checkoutpage.selectBillingAddress(state);
		checkoutpage.validateBillingAddressSelected(state);

		// TODO
		/*
		 * //Select Shipping Address
		 * checkoutpage.selectShippingAddress("Select address");
		 */

		// Choose Payment Option and Enter Credit Card Details
		String Creditcardmethod = PaymentMethodType.CREDIT_CARD.text;
		checkoutpage.choosePaymentMethod(Creditcardmethod);
		checkoutpage.selectCreditCardAndEnterDetails();

		// Choose Delivery Method
		String Deliverymethod = DeliveryMethodType.EXPRESS.text;
		checkoutpage.chooseDeliveryMethodAs(Deliverymethod, null);

		// Expected Data for Calculations on Checkout page
		double PRODUCT_2_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_2_UNIT_PRICE.text);
		double DEFAULT_QUANTITY = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_QUANTITY.text);
		double PRODUCT_2_PRICE = DEFAULT_QUANTITY * PRODUCT_2_UNIT_PRICE;
		double ITEMS_SUBTOTAL = PRODUCT_2_PRICE;
		double ADD_ORDER_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double DEFAULT_HANDLING_COST = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_HANDLING_COST.text);
		double TAX_COST = Double.parseDouble(EpiDataCommon.Taxes.TAX_0.text);
		double SHIPPING_SUBTOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.EXPRESS_SHIPPING_SUB_TOTAL.text);
		double SHIPPING_AND_TAX = SHIPPING_SUBTOTAL + TAX_COST;
		double CART_TOTAL = ITEMS_SUBTOTAL + SHIPPING_AND_TAX;
		double SHIPPING_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_DISCOUNT.text);
		double SHIPPING_COST = SHIPPING_SUBTOTAL + SHIPPING_DISCOUNT;
		double SHIPPING_TOTAL_AMOUNT = SHIPPING_COST;
		double TAX_AMOUNT = TAX_COST;
		double PRODUCT_ADD_DISCOUNT_TOTAL = PRODUCT_2_PRICE - ADD_ORDER_DISCOUNT;

		// Validating Subtotal for items
		Double actualSubTotalForItems = checkoutpage.getSubTotalForYourItems();
		Double expectedSubTotalForItems = ITEMS_SUBTOTAL;
		assertEquals(actualSubTotalForItems, expectedSubTotalForItems);
		VertexLogger.log(
			String.format(("Actual subtotal for items :%s is matched with expected: %s"), actualSubTotalForItems,
				expectedSubTotalForItems));

		// Validating order level additional discounts
		Double actualOrderLevelAddDis = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		Double expectedOrderLevelAddDis = ADD_ORDER_DISCOUNT;
		assertEquals(actualOrderLevelAddDis, expectedOrderLevelAddDis);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDis, expectedOrderLevelAddDis));

		// Validating shipping and tax amount
		Double actualShippingTax = checkoutpage.getShippingAndTaxAmount();
		Double expectedShippingTax = SHIPPING_AND_TAX;
		assertEquals(actualShippingTax, expectedShippingTax);
		VertexLogger.log(String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualShippingTax,
			expectedShippingTax));

		// getting shipping and tax as map
		Map<String, String> shippingTaxAmounts = checkoutpage.getShippingAndTaxMap();

		// validating shipping sub_total
		String actualShippingSubtotal = shippingTaxAmounts
			.get("Shipping Subtotal")
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double actualShippingSubtotal1 = Double.parseDouble(actualShippingSubtotal);
		Double expectedShippingSubtotal = SHIPPING_SUBTOTAL;
		assertEquals(actualShippingSubtotal1, expectedShippingSubtotal);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotal1,
				expectedShippingSubtotal));

		// Validating shipping discount
		String actualShippingDiscount = shippingTaxAmounts
			.get("Shipping Discount")
			.replace("- $", "")
			.trim()
			.replace(" ", "");
		Double actualShippingDiscount1 = Double.parseDouble(actualShippingDiscount);
		Double expectedShippingDiscount = SHIPPING_DISCOUNT;
		assertEquals(actualShippingDiscount1, expectedShippingDiscount);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscount1,
				expectedShippingDiscount));

		// Validating shipping total
		String actualShippingTotal = shippingTaxAmounts
			.get("Shipping Total")
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double actualShippingTotal1 = Double.parseDouble(actualShippingTotal);
		Double expectedShippingTotal = SHIPPING_TOTAL_AMOUNT;
		assertEquals(actualShippingTotal1, expectedShippingTotal);
		VertexLogger.log(String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotal1,
			expectedShippingTotal));

		// Validating shipping tax
		String actualTax = shippingTaxAmounts
			.get("Tax")
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double actualTax1 = Double.parseDouble(actualTax);
		Double expectedTax = TAX_AMOUNT;
		assertEquals(actualTax1, expectedTax);
		VertexLogger.log(
			String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTax1, expectedTax));

		// Validating total for cart
		Double actualTotalForCart = checkoutpage.getTotalForCart();
		Double expectedTotalForCart = CART_TOTAL;
		assertEquals(actualTotalForCart, expectedTotalForCart);
		VertexLogger.log(String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCart,
			expectedTotalForCart));

		// Validate Error message by Clicking on Place order Button
		checkoutpage.VerifyErrorMsgByClickOnPlaceOrderButton();

		// Select NewYork Address from Billing Address Dropdown
		checkoutpage.selectBillingAddress(state);
		checkoutpage.validateBillingAddressSelected(state);

		// Add New Shipping Address
		EpiAddAddressPage addaddresspage;
		addaddresspage = checkoutpage.clickAddNewShipAddressButton();
		addaddresspage.enterInvalidAddressDetails();
		addaddresspage.clickOnAddressCloseButton();

		// Choose Payment Option and Enter Credit Card Details
		checkoutpage.choosePaymentMethod(Creditcardmethod);
		checkoutpage.selectCreditCardAndEnterDetails();

		// Choose Delivery Method
		checkoutpage.chooseDeliveryMethodAs(Deliverymethod, null);

		// Validating Subtotal for items
		Double actualSubTotalForItems1 = checkoutpage.getSubTotalForYourItems();
		Double expectedSubTotalForItems1 = ITEMS_SUBTOTAL;
		assertEquals(actualSubTotalForItems1, expectedSubTotalForItems1);
		VertexLogger.log(
			String.format(("Actual subtotal for items :%s is matched with expected: %s"), actualSubTotalForItems1,
				expectedSubTotalForItems1));

		// Validating order level additional discounts
		Double actualOrderLevelAddDis1 = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		Double expectedOrderLevelAddDis1 = ADD_ORDER_DISCOUNT;
		assertEquals(actualOrderLevelAddDis1, expectedOrderLevelAddDis1);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDis1, expectedOrderLevelAddDis1));

		// Validating shipping and tax amount
		Double actualShippingTax1 = checkoutpage.getShippingAndTaxAmount();
		Double expectedShippingTax1 = SHIPPING_AND_TAX;
		assertEquals(actualShippingTax1, expectedShippingTax1);
		VertexLogger.log(String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualShippingTax1,
			expectedShippingTax1));

		// getting shipping and tax as map
		Map<String, String> shippingTaxAmounts1 = checkoutpage.getShippingAndTaxMap();

		// validating shipping sub_total
		String actualShippingSubtotal2 = shippingTaxAmounts1
			.get("Shipping Subtotal")
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double actualShippingSubtotal3 = Double.parseDouble(actualShippingSubtotal2);
		Double expectedShippingSubtota2 = SHIPPING_SUBTOTAL;
		assertEquals(actualShippingSubtotal3, expectedShippingSubtota2);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotal3,
				expectedShippingSubtota2));

		// Validating shipping discount
		String actualShippingDiscount2 = shippingTaxAmounts
			.get("Shipping Discount")
			.replace("- $", "")
			.trim()
			.replace(" ", "");
		Double actualShippingDiscount3 = Double.parseDouble(actualShippingDiscount2);
		Double expectedShippingDiscount2 = SHIPPING_DISCOUNT;
		assertEquals(actualShippingDiscount3, expectedShippingDiscount2);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscount3,
				expectedShippingDiscount2));

		// Validating shipping total
		String actualShippingTotal2 = shippingTaxAmounts
			.get("Shipping Total")
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double actualShippingTotal3 = Double.parseDouble(actualShippingTotal2);
		Double expectedShippingTotal2 = SHIPPING_TOTAL_AMOUNT;
		assertEquals(actualShippingTotal3, expectedShippingTotal2);
		VertexLogger.log(String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotal3,
			expectedShippingTotal2));

		// Validating shipping tax
		String actualTax2 = shippingTaxAmounts
			.get("Tax")
			.replace("$", "")
			.trim()
			.replace(" ", "");
		Double actualTax3 = Double.parseDouble(actualTax2);
		Double expectedTax2 = TAX_AMOUNT;
		assertEquals(actualTax3, expectedTax2);
		VertexLogger.log(
			String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTax3, expectedTax2));

		// Validating total for cart
		Double actualTotalForCart2 = checkoutpage.getTotalForCart();
		Double expectedTotalForCart2 = CART_TOTAL;
		assertEquals(actualTotalForCart2, expectedTotalForCart2);
		VertexLogger.log(String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCart2,
			expectedTotalForCart2));

		// Place Order
		EpiOrderConfirmationPage orderconfirmpage;
		orderconfirmpage = checkoutpage.clickPlaceOrderButton();

		// Get Order Number
		String orderNumber = orderconfirmpage.getOrderNumber();
		VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber));

		// Validate quantity after order placed
		int actualQuantity = orderconfirmpage.getQuantity(productName);
		int expectedQuantity = (int) DEFAULT_QUANTITY;
		assertEquals(actualQuantity, expectedQuantity);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantity, expectedQuantity));

		// Validate unitprice after order placed
		Double actualUnitPrice = orderconfirmpage.getUnitPrice(productName);
		Double expectedUnitPrice = PRODUCT_2_UNIT_PRICE;
		assertEquals(actualUnitPrice, expectedUnitPrice);
		VertexLogger.log(
			String.format(("Actual Unit price after order placed :%s is matched with expected: %s"), actualUnitPrice,
				expectedUnitPrice));

		// Validate price after order placed
		Double actualPrice = orderconfirmpage.getPrice(productName);
		Double expectedPrice = PRODUCT_2_PRICE;
		assertEquals(actualPrice, expectedPrice);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPrice,
				expectedPrice));

		// Validate Discount after order placed
		Double actualShippingDiscountOrderPlaced = orderconfirmpage.getShippingDiscount();
		Double expectedShippingDiscountOrderPlaced = SHIPPING_DISCOUNT;
		assertEquals(actualShippingDiscountOrderPlaced, expectedShippingDiscountOrderPlaced);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountOrderPlaced, expectedShippingDiscountOrderPlaced));

		// Validate total without tax after order placed
		Double actualTotalWithOutTax = orderconfirmpage.getTotalWithOutTax(productName);
		Double expectedTotalWithOutTax = PRODUCT_ADD_DISCOUNT_TOTAL;
		assertEquals(actualTotalWithOutTax, expectedTotalWithOutTax);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalWithOutTax, expectedTotalWithOutTax));

		// Validate additional discounts after order placed
		Double actualAdditionalDiscounts = orderconfirmpage.getAdditionalDiscounts();
		Double expectedAdditionalDiscounts = ADD_ORDER_DISCOUNT;
		assertEquals(actualAdditionalDiscounts, expectedAdditionalDiscounts);
		VertexLogger.log(
			String.format(("Actual additional discounts after order placed :%s is matched with expected: %s"),
				actualAdditionalDiscounts, expectedAdditionalDiscounts));

		// Validate handling cost after order placed
		Double actualHandlingCost = orderconfirmpage.getHandlingCost();
		Double expectedHandlingCost = DEFAULT_HANDLING_COST;
		assertEquals(actualHandlingCost, expectedHandlingCost);
		VertexLogger.log(String.format(("Actual handling cost after order placed :%s is matched with expected: %s"),
			actualHandlingCost, expectedHandlingCost));

		// Validate shipping Subtotal after order placed
		Double actualShippingSubtotalAfterOrderPlaced = orderconfirmpage.getShippingSubtotal();
		Double expectedShippingSubtotalAfterOrderPlaced = SHIPPING_SUBTOTAL;
		assertEquals(actualShippingSubtotalAfterOrderPlaced, expectedShippingSubtotalAfterOrderPlaced);
		VertexLogger.log(String.format(("Actual shipping subtotal after order placed :%s is matched with expected: %s"),
			actualShippingSubtotalAfterOrderPlaced, expectedShippingSubtotalAfterOrderPlaced));

		// Validate shipping discount after order placed
		Double actualShippingDiscountAfterOrderPlaced = orderconfirmpage.getShippingDiscount();
		Double expectedShippingDiscountAfterOrderPlaced = SHIPPING_DISCOUNT;
		assertEquals(actualShippingDiscountAfterOrderPlaced, expectedShippingDiscountAfterOrderPlaced);
		VertexLogger.log(String.format(("Actual shipping discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountAfterOrderPlaced, expectedShippingDiscountAfterOrderPlaced));

		// Validate shipping cost after order placed
		Double actualShippingCostAfterOrderPlaced = orderconfirmpage.getShippingCost();
		Double expectedShippingCostAfterOrderPlaced = SHIPPING_COST;
		assertEquals(actualShippingCostAfterOrderPlaced, expectedShippingCostAfterOrderPlaced);
		VertexLogger.log(String.format(("Actual shipping cost after order placed :%s is matched with expected: %s"),
			actualShippingCostAfterOrderPlaced, expectedShippingCostAfterOrderPlaced));

		// Validate tax cost after order placed
		Double actualTaxCostAfterOrderPlaced = orderconfirmpage.getTaxCost();
		Double expectedTaxCostAfterOrderPlaced = TAX_AMOUNT;
		assertEquals(actualTaxCostAfterOrderPlaced, expectedTaxCostAfterOrderPlaced);
		VertexLogger.log(String.format(("Actual tax cost after order placed :%s is matched with expected: %s"),
			actualTaxCostAfterOrderPlaced, expectedTaxCostAfterOrderPlaced));

		// Validate final cost after order placed
		Double actualFinalCostAfterOrderPlaced = orderconfirmpage.getFinalTotal();
		Double expectedFinalCostAfterOrderPlaced = CART_TOTAL;
		assertEquals(actualFinalCostAfterOrderPlaced, expectedFinalCostAfterOrderPlaced);
		VertexLogger.log(String.format(("Actual final cost after order placed :%s is matched with expected: %s"),
			actualFinalCostAfterOrderPlaced, expectedFinalCostAfterOrderPlaced));

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("INVALIDSHIPPINGADDR", true);

		// logout from Episerver portal
		portalhomepage.portallogout();
	}
}
