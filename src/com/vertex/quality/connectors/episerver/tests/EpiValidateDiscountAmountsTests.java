package com.vertex.quality.connectors.episerver.tests;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.CampaignDetails;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.pages.*;
import com.vertex.quality.connectors.episerver.pages.epiCommon.*;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiAdminHomePage;
import com.vertex.quality.connectors.episerver.pages.v323x.EpiOseriesPage;
import com.vertex.quality.connectors.episerver.tests.base.EpiBaseTest;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class EpiValidateDiscountAmountsTests extends EpiBaseTest
{
	@Test(groups = { "smoke" })
	public void episerverDiscountAmountTest( )
	{
		EpiAdminHomePage admindashboardpage;
		EpiStoreFrontHomePage portalhomepage;
		EpiAddressPage addresspage;
		EpiOseriesPage oSeriespage;
		EpiAddAddressPage addaddresspage;
		admindashboardpage = logInAsAdminUser();
		admindashboardpage.validateDashBoardDefaultPage();
		admindashboardpage.clickOnMainMenu("CMS");
		admindashboardpage.clickOnSubMenu("Admin");
		admindashboardpage.selectTabInCmsAdminPage("Admin");
		oSeriespage = admindashboardpage.navigateToOseriespage();
		oSeriespage.validateConnectorTooltip("Vertex O Series");
		oSeriespage.validateVertexOSeriesConnectorStatus();
		EpiOseriesInvocingPage invoicingpage;
		invoicingpage = oSeriespage.clickOnInvoicingTab();
		invoicingpage.checkInvoiceAutomatically(true);
		invoicingpage.clickInvoicingSaveButton();
		oSeriespage.clickRefreshStatusButton();
		oSeriespage.validateConnectorTooltip("Vertex O Series");
		admindashboardpage.clickOnMainMenu("Commerce");
		admindashboardpage.clickOnSubMenu("Marketing");
		EpiOseriesCommercePage CommercePage = new EpiOseriesCommercePage(driver);
		CommercePage.clickEditOptionsLink();
		// compaign name validation
		String actualCompaignName = CommercePage.getCompaignName();
		String expectedComaignName = CampaignDetails.campaignName.text;
		assertEquals(actualCompaignName, expectedComaignName);
		VertexLogger.log(
			String.format("Actual compaign name : %s matched with expetcted comapign name : %s ", actualCompaignName,
				expectedComaignName));
		// Validation of "Available from" date for a Campaign
		String actualCompaignAvailabledate = CommercePage.getSchedulingAndStatusAvailableFromDate();
		Date date1 = null;
		try
		{
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(actualCompaignAvailabledate);
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
		String expectedCompaignAvailableDate = CampaignDetails.AvailableFrom.text;
		Date date2 = null;
		try
		{
			date2 = new SimpleDateFormat("dd/MM/yyyy").parse(expectedCompaignAvailableDate);
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
		assertTrue(date1.before(date2) || date1.equals(date2));
		VertexLogger.log(
			String.format("Actual from date is : %s below the expected from date : %s ", actualCompaignAvailabledate,
				expectedCompaignAvailableDate));
		// Validate expire date
		String actualCompaignExpiresDate = CommercePage.getSchedulingAndStatusExpiresOnDate();
		Date exDate1 = null;
		try
		{
			exDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(actualCompaignExpiresDate);
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
		String expectedCompaignExpiresDate = CampaignDetails.ExpiresOn.text;
		Date exDate2 = null;
		try
		{
			exDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(expectedCompaignExpiresDate);
		}
		catch ( ParseException e )
		{
			e.printStackTrace();
		}
		assertTrue(exDate1.after(exDate2) || exDate1.equals(exDate2));
		VertexLogger.log(
			String.format("Actual expires date is : %s above the expected expire date : %s ", actualCompaignExpiresDate,
				expectedCompaignExpiresDate));
		// Validate scheduling and status
		boolean status = CommercePage.getSchedulingAndStatusActiveStatus();
		assertTrue(status);
		VertexLogger.log(String.format("Scheduling and Status active is : %s", status));
		String actualTargetMarket = CommercePage.getSelectedTargetMarket();
		String expectedTargetMarket = CampaignDetails.TargetMarket.text;
		assertEquals(actualTargetMarket, expectedTargetMarket);
		String actualVisitorGroups = CommercePage.getSelectedVisitorGroups();
		String expectedVisitorGroups = CampaignDetails.VisitorsGroup.text;
		assertEquals(actualVisitorGroups, expectedVisitorGroups);
		CommercePage.clickOnCloseButton();
		CommercePage.clickOnDownArrow();
		admindashboardpage.adminlogout();
		portalhomepage = logInAsCustomer();
		EpiStoreFrontHomePage navigate = new EpiStoreFrontHomePage(driver);
		EpiCartPage cart = new EpiCartPage(driver);
		navigate.navigateToHomePage();
		cart.clearAllItemsInCart();

		// fill the billing address
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addaddresspage = addresspage.clickOnNewButton();
		addaddresspage.enterAnaheimCityAddressDetails();

		// address.clickOnNewAddressSaveButton();
		portalhomepage.clickAddressBookLink();
		// Address validation
		String actualSelectedAddress = addresspage.getAddressFromAddressBook("CALIFORNIA-ADDRESS", true);
		String expectedSelectedAddressName = "CALIFORNIA-ADDRESS";
		assertTrue(actualSelectedAddress.contains(expectedSelectedAddressName));
		VertexLogger.log(
			String.format("Actual address contains expected address name : %s ", expectedSelectedAddressName));
		String expectedSelectedAddressFirstAndLastName = "ADDRFirstCA ADDRLastCA";
		assertTrue(actualSelectedAddress.contains(expectedSelectedAddressName));
		VertexLogger.log(String.format("Actual address contains expected address name : %s ",
			expectedSelectedAddressFirstAndLastName));

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-36921911");

		// Proceed to checkout
		EpiCheckoutPage checkoutpage;
		checkoutpage = cart.proceedToCheckout();
		String productName1 = cart.getItemTitleWithProductCode("SKU-36921911");
		// Select newly added Address from Shipping Address Dropdown
		checkoutpage.selectShippingAddress("CALIFORNIA-ADDRESS");

		// Select newly added Address from Billing Address Dropdown
		checkoutpage.selectBillingAddress("CALIFORNIA-ADDRESS");
		checkoutpage.validateBillingAddressSelected("CALIFORNIA-ADDRESS");

		// Choose Credit card option, enter Credit card details and Select Delivery
		// Method
		checkoutpage.selectCreditCardAndEnterDetails();
		checkoutpage.chooseDeliveryMethodAs("Fast", null);

		// Expected Data for Calculations on Checkout page
		double PRODUCT_1_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_5_UNIT_PRICE.text);
		int DEFAULT_QUANTITY = Integer.parseInt(EpiDataCommon.DefaultAmounts.DEFAULT_QUANTITY.text);
		double PRODUCT_1_PRICE = DEFAULT_QUANTITY * PRODUCT_1_UNIT_PRICE;
		double ITEMS_SUBTOTAL = PRODUCT_1_PRICE;
		double ADD_ORDER_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double DEFAULT_HANDLING_COST = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_HANDLING_COST.text);
		double TAX_COST = Double.parseDouble(EpiDataCommon.Taxes.TAX_11.text);
		double FAST_SHIPPING_TOTAL = Double.parseDouble(EpiDataCommon.DeliveryMethodCosts.FAST_SHIPPING_TOTAL.text);
		double FAST_SHIPPING_SUBTOTAL = Double.parseDouble(
			EpiDataCommon.DeliveryMethodCosts.FAST_SHIPPING_SUB_TOTAL.text);
		double SHIPPING_SUBTOTAL = Double.parseDouble(EpiDataCommon.DeliveryMethodCosts.FAST_SHIPPING_SUB_TOTAL.text);
		double SHIPPING_AND_TAX = FAST_SHIPPING_TOTAL + TAX_COST;
		double CART_TOTAL = ITEMS_SUBTOTAL + SHIPPING_AND_TAX;
		double DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_DISCOUNT.text);
		double SHIPPING_DISCOUNT = Double.parseDouble(EpiDataCommon.DefaultAmounts.DEFAULT_SHIPPING_DISCOUNT.text);
		double SHIPPING_COST = SHIPPING_SUBTOTAL + SHIPPING_DISCOUNT;

		// Validating subtotal for items
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
		assertEquals(actualShippingTotal, SHIPPING_COST);
		VertexLogger.log(String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotal,
			SHIPPING_COST), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

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

		// Validate quantity after order placed
		int actualQuantity = orderconfirmpage.getQuantity(productName1);
		assertEquals(actualQuantity, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantity, DEFAULT_QUANTITY),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate unit price after order placed
		double actualUnitPrice = orderconfirmpage.getUnitPrice(productName1);
		assertEquals(actualUnitPrice, PRODUCT_1_UNIT_PRICE);
		VertexLogger.log(
			String.format(("Actual Unit price after order placed :%s is matched with expected: %s"), actualUnitPrice,
				PRODUCT_1_UNIT_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate price after order placed
		double actualPrice = orderconfirmpage.getPrice(productName1);
		assertEquals(actualPrice, PRODUCT_1_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPrice,
				PRODUCT_1_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate Discount after order placed
		double actualDiscountOrderPlaced = orderconfirmpage.getDiscount(productName1);
		assertEquals(actualDiscountOrderPlaced, DISCOUNT);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualDiscountOrderPlaced, DISCOUNT), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate item total after order placed
		double actualTotalWithOutTax = orderconfirmpage.getTotalWithOutTax(productName1);
		assertEquals(actualTotalWithOutTax, PRODUCT_1_PRICE);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalWithOutTax, PRODUCT_1_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

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

		// Search for Product 2 and Add to Cart
		cart.searchAndAddProductToCart("SKU-39850363");

		// Proceed to checkout
		checkoutpage = cart.proceedToCheckout();
		String productName2 = cart.getItemTitleWithProductCode("SKU-39850363");

		// Select newly added Address from Shipping Address Dropdown
		checkoutpage.selectShippingAddress("CALIFORNIA-ADDRESS");

		// Select newly added Address from Billing Address Dropdown
		checkoutpage.selectBillingAddress("CALIFORNIA-ADDRESS");
		checkoutpage.validateBillingAddressSelected("CALIFORNIA-ADDRESS");

		// Choose Credit card option, enter Credit card details and Select Delivery
		// Method
		checkoutpage.selectCreditCardAndEnterDetails();
		checkoutpage.chooseDeliveryMethodAs("Fast", null);

		// Product2 validations
		double PRODUCT_2_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_8_UNIT_PRICE.text);
		double PRODUCT_2_PRICE = DEFAULT_QUANTITY * PRODUCT_2_UNIT_PRICE;
		double PRODUCT_2_DISCOUT = Double.parseDouble(EpiDataCommon.DiscountPrice.PRODUCT_DISCOUNT.text);
		double ITEMS_SUBTOTAL_PRD2 = PRODUCT_2_PRICE - PRODUCT_2_DISCOUT;
		double ADD_ORDER_DISCOUNT_PRD2 = Double.parseDouble(
			EpiDataCommon.DefaultAmounts.DEFAULT_ADD_ORDER_DISCOUNT.text);
		double TAX_PRD2 = Double.parseDouble(EpiDataCommon.Taxes.TAX_15.text);
		double SHIPPING_AND_TAX_PRD2 = FAST_SHIPPING_SUBTOTAL + TAX_PRD2;
		double SHIPPING_TOTAL_PRD2 = FAST_SHIPPING_SUBTOTAL + DISCOUNT;
		double CART_TOTAL_PRD2 = ITEMS_SUBTOTAL_PRD2 + SHIPPING_AND_TAX_PRD2;
		double SHIPPING_COST_PRD2 = FAST_SHIPPING_SUBTOTAL;
		DecimalFormat df = new DecimalFormat("###.##");
		String strTaxAmount = df.format(SHIPPING_AND_TAX_PRD2);
		SHIPPING_AND_TAX_PRD2 = Double.parseDouble(strTaxAmount);
		String strCartTotal = df.format(CART_TOTAL_PRD2);
		CART_TOTAL_PRD2 = Double.parseDouble(strCartTotal);

		// Validating sub-total for items
		double actualSubTotalForItemsPrd2 = checkoutpage.getSubTotalForYourItems();
		assertEquals(actualSubTotalForItemsPrd2, ITEMS_SUBTOTAL_PRD2);
		VertexLogger.log(
			String.format(("Actual subtotal for items :%s is matched with expected: %s"), actualSubTotalForItemsPrd2,
				ITEMS_SUBTOTAL_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating order level additional discounts
		double actualOrderLevelAddDisPrd2 = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		assertEquals(actualOrderLevelAddDisPrd2, ADD_ORDER_DISCOUNT);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDisPrd2, ADD_ORDER_DISCOUNT), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping and tax amount
		double actualShippingTaxPrd2 = checkoutpage.getShippingAndTaxAmount();
		assertEquals(actualShippingTaxPrd2, SHIPPING_AND_TAX_PRD2);
		VertexLogger.log(
			String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualShippingTaxPrd2,
				SHIPPING_AND_TAX_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// getting shipping and tax as map
		Map<String, Double> shippingTaxAmountsPrd2 = checkoutpage.getShippingAndTaxMapDouble();

		// validating shipping sub_total
		double actualShippingSubtotalPrd2 = shippingTaxAmountsPrd2.get("Shipping Subtotal");
		assertEquals(actualShippingSubtotalPrd2, SHIPPING_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotalPrd2,
				SHIPPING_SUBTOTAL), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping discount
		double actualShippingDiscountPrd2 = shippingTaxAmountsPrd2.get("Shipping Discount");
		assertEquals(actualShippingDiscountPrd2, ADD_ORDER_DISCOUNT_PRD2);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscountPrd2,
				ADD_ORDER_DISCOUNT_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping total
		double actualShippingTotalPrd2 = shippingTaxAmountsPrd2.get("Shipping Total");
		assertEquals(actualShippingTotalPrd2, SHIPPING_TOTAL_PRD2);
		VertexLogger.log(
			String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotalPrd2,
				SHIPPING_TOTAL_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping tax
		double actualTaxPrd1 = shippingTaxAmountsPrd2.get("Tax");
		assertEquals(actualTaxPrd1, TAX_PRD2);
		VertexLogger.log(
			String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTaxPrd1, TAX_PRD2),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating total for cart
		double actualTotalForCartPrd2 = checkoutpage.getTotalForCart();
		assertEquals(actualTotalForCartPrd2, CART_TOTAL_PRD2);
		VertexLogger.log(
			String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCartPrd2,
				CART_TOTAL_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Place order
		orderconfirmpage = checkoutpage.clickPlaceOrderButton();

		// Get Order Number
		String orderNumber2 = orderconfirmpage.getOrderNumber();
		VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber2), EpiCartPage.class);

		// Validate quantity after order placed
		int actualQuantityPrd2 = orderconfirmpage.getQuantity(productName2);
		assertEquals(actualQuantityPrd2, DEFAULT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantityPrd2, DEFAULT_QUANTITY),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate unit price after order placed
		double actualUnitPricePrd2 = orderconfirmpage.getUnitPrice(productName2);
		assertEquals(actualUnitPricePrd2, PRODUCT_2_UNIT_PRICE);
		VertexLogger.log(String.format(("Actual Unit price after order placed :%s is matched with expected: %s"),
			actualUnitPricePrd2, PRODUCT_2_UNIT_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate price after order placed
		double actualPricePrd2 = orderconfirmpage.getPrice(productName2);
		assertEquals(actualPricePrd2, PRODUCT_2_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPricePrd2,
				PRODUCT_2_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate Discount after order placed
		double actualShippingDiscountOrderPlacedPrd2 = orderconfirmpage.getDiscount(productName2);
		assertEquals(actualShippingDiscountOrderPlacedPrd2, PRODUCT_2_DISCOUT);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountOrderPlacedPrd2, PRODUCT_2_DISCOUT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate total without tax after order placed
		double actualTotalWithOutTaxPrd2 = orderconfirmpage.getTotalWithOutTax(productName2);
		assertEquals(actualTotalWithOutTaxPrd2, ITEMS_SUBTOTAL_PRD2);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalWithOutTaxPrd2, ITEMS_SUBTOTAL_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate additional discounts after order placed
		double actualAdditionalDiscountsPrd2 = orderconfirmpage.getAdditionalDiscounts();
		assertEquals(actualAdditionalDiscountsPrd2, ADD_ORDER_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual additional discounts after order placed :%s is matched with expected: %s"),
				actualAdditionalDiscountsPrd2, ADD_ORDER_DISCOUNT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate handling cost after order placed
		double actualHandlingCostPrd2 = orderconfirmpage.getHandlingCost();
		assertEquals(actualHandlingCostPrd2, DEFAULT_HANDLING_COST);
		VertexLogger.log(String.format(("Actual handling cost after order placed :%s is matched with expected: %s"),
			actualHandlingCostPrd2, DEFAULT_HANDLING_COST), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping Sub-total after order placed
		double actualShippingSubtotalAfterOrderPlacedPrd2 = orderconfirmpage.getShippingSubtotal();
		assertEquals(actualShippingSubtotalAfterOrderPlacedPrd2, SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping subtotal after order placed :%s is matched with expected: %s"),
			actualShippingSubtotalAfterOrderPlacedPrd2, SHIPPING_SUBTOTAL),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping discount after order placed
		double actualShippingDiscountAfterOrderPlacedPrd2 = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountAfterOrderPlacedPrd2, ADD_ORDER_DISCOUNT_PRD2);
		VertexLogger.log(String.format(("Actual shipping discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountAfterOrderPlacedPrd2, ADD_ORDER_DISCOUNT_PRD2),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping cost after order placed
		double actualShippingCostAfterOrderPlacedPrd2 = orderconfirmpage.getShippingCost();
		assertEquals(actualShippingCostAfterOrderPlacedPrd2, SHIPPING_COST_PRD2);
		VertexLogger.log(String.format(("Actual shipping cost after order placed :%s is matched with expected: %s"),
			actualShippingCostAfterOrderPlacedPrd2, SHIPPING_COST_PRD2),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate tax cost after order placed
		double actualTaxCostAfterOrderPlacedPrd2 = orderconfirmpage.getTaxCost();
		assertEquals(actualTaxCostAfterOrderPlacedPrd2, TAX_PRD2);
		VertexLogger.log(String.format(("Actual tax cost after order placed :%s is matched with expected: %s"),
			actualTaxCostAfterOrderPlacedPrd2, TAX_PRD2), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate final cost after order placed
		double actualFinalCostAfterOrderPlacedPrd2 = orderconfirmpage.getFinalTotal();
		assertEquals(actualFinalCostAfterOrderPlacedPrd2, CART_TOTAL_PRD2);
		VertexLogger.log(String.format(("Actual final cost after order placed :%s is matched with expected: %s"),
			actualFinalCostAfterOrderPlacedPrd2, CART_TOTAL_PRD2),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Product 3

		// Search for Product and Add to Cart
		cart.searchAndAddProductToCart("SKU-21550365");

		// Proceed to checkout
		checkoutpage = cart.proceedToCheckout();
		String productName3 = cart.getItemTitleWithProductCode("SKU-21550365");
		int PRODUCT_QUANTITY = Integer.parseInt(EpiDataCommon.DefaultAmounts.PRODUCT_QUANTITY.text);
		// Change product quantity
		cart.changeProductQuantity(String.valueOf(PRODUCT_QUANTITY));
		// Select newly added Address from Shipping Address Drop down
		checkoutpage.selectShippingAddress("CALIFORNIA-ADDRESS");

		// Select newly added Address from Billing Address Drop down
		checkoutpage.selectBillingAddress("CALIFORNIA-ADDRESS");
		checkoutpage.validateBillingAddressSelected("CALIFORNIA-ADDRESS");

		// Choose Credit card option, enter Credit card details and Select Delivery
		// Method
		checkoutpage.selectCreditCardAndEnterDetails();
		checkoutpage.chooseDeliveryMethodAs("Fast", null);
		double PRODUCT_3_UNIT_PRICE = Double.parseDouble(EpiDataCommon.ProductPrices.PRODUCT_7_UNIT_PRICE.text);

		double PRODUCT_3_PRICE = PRODUCT_QUANTITY * PRODUCT_3_UNIT_PRICE;
		double ITEMS_SUBTOTAL_PRD3 = PRODUCT_3_PRICE - DISCOUNT;
		double ADDITIONAL_DISCOUNTS = Double.parseDouble(EpiDataCommon.DiscountPrice.ADDITIONAL_DISCOUNT.text);
		double TAX_COST_3 = Double.parseDouble(EpiDataCommon.Taxes.TAX_COST.text);
		double SHIPPING_AND_TAX_PRD3 = FAST_SHIPPING_SUBTOTAL + TAX_COST_3;
		double CART_TOTAL_PRD3 = PRODUCT_3_PRICE + SHIPPING_AND_TAX_PRD3 + ADDITIONAL_DISCOUNTS;
		double SHIPPING_TOTAL_PRD3 = FAST_SHIPPING_SUBTOTAL;
		double SHIIPING_COST_PRD3 = FAST_SHIPPING_SUBTOTAL - DISCOUNT;
		// Validating sub total for items
		double actualSubTotalForItemsPrd3 = checkoutpage.getSubTotalForYourItems();
		assertEquals(actualSubTotalForItemsPrd3, ITEMS_SUBTOTAL_PRD3);
		VertexLogger.log(
			String.format(("Actual subtotal for items :%s is matched with expected: %s"), actualSubTotalForItemsPrd3,
				ITEMS_SUBTOTAL_PRD3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating order level additional discounts
		double actualOrderLevelAddDisPrd3 = checkoutpage.getOrderLevelAdditionalDiscountAmount();
		assertEquals(actualOrderLevelAddDisPrd3, ADDITIONAL_DISCOUNTS);
		VertexLogger.log(String.format(("Actual additional order level discount:%s is matched with expected: %s"),
			actualOrderLevelAddDisPrd3, ADDITIONAL_DISCOUNTS),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping and tax amount
		double actualShippingTaxPrd3 = checkoutpage.getShippingAndTaxAmount();
		assertEquals(actualShippingTaxPrd3, SHIPPING_AND_TAX_PRD3);
		VertexLogger.log(
			String.format(("Actual shipping and tax :%s is matched with expected: %s"), actualShippingTaxPrd3,
				SHIPPING_AND_TAX_PRD3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// getting shipping and tax as map
		Map<String, Double> shippingTaxAmountsPrd3 = checkoutpage.getShippingAndTaxMapDouble();

		// validating shipping sub_total
		double actualShippingSubtotalPrd3 = shippingTaxAmountsPrd3.get("Shipping Subtotal");
		assertEquals(actualShippingSubtotalPrd3, FAST_SHIPPING_SUBTOTAL);
		VertexLogger.log(
			String.format(("Actual shipping subtotal :%s is matched with expected: %s"), actualShippingSubtotalPrd3,
				FAST_SHIPPING_SUBTOTAL), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping discount
		double actualShippingDiscountPrd3 = shippingTaxAmountsPrd3.get("Shipping Discount");
		assertEquals(actualShippingDiscountPrd3, ADD_ORDER_DISCOUNT);
		VertexLogger.log(
			String.format(("Actual shipping discount :%s is matched with expected: %s"), actualShippingDiscountPrd3,
				ADD_ORDER_DISCOUNT), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping total
		double actualShippingTotalPrd3 = shippingTaxAmountsPrd3.get("Shipping Total");
		assertEquals(actualShippingTotalPrd3, SHIPPING_TOTAL_PRD3);
		VertexLogger.log(
			String.format(("Actual shipping total :%s is matched with expected: %s"), actualShippingTotalPrd3,
				SHIPPING_TOTAL_PRD3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating shipping tax
		double actualTaxPrd3 = shippingTaxAmountsPrd3.get("Tax");
		assertEquals(actualTaxPrd3, TAX_COST_3);
		VertexLogger.log(
			String.format(("Actual shipping tax :%s is matched with expected: %s"), actualTaxPrd3, TAX_COST_3),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validating total for cart
		double actualTotalForCartPrd3 = checkoutpage.getTotalForCart();
		assertEquals(actualTotalForCartPrd3, CART_TOTAL_PRD3);
		VertexLogger.log(
			String.format(("Actual total for cart :%s is matched with expected: %s"), actualTotalForCartPrd3,
				CART_TOTAL_PRD3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Place order
		orderconfirmpage = checkoutpage.clickPlaceOrderButton();

		// Get Order Number
		String orderNumber3 = orderconfirmpage.getOrderNumber();
		VertexLogger.log(String.format("Successfully Order Placed, Order# %s", orderNumber3), EpiCartPage.class);

		// Validate quantity after order placed
		int actualQuantityPrd3 = orderconfirmpage.getQuantity(productName3);
		assertEquals(actualQuantityPrd3, PRODUCT_QUANTITY);
		VertexLogger.log(
			String.format(("Actual Quantity :%s is matched with expected: %s"), actualQuantityPrd3, PRODUCT_QUANTITY),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate unit price after order placed
		double actualUnitPricePrd3 = orderconfirmpage.getUnitPrice(productName3);
		assertEquals(actualUnitPricePrd3, PRODUCT_3_UNIT_PRICE);
		VertexLogger.log(String.format(("Actual Unit price after order placed :%s is matched with expected: %s"),
			actualUnitPricePrd3, PRODUCT_3_UNIT_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate price after order placed
		double actualPricePrd3 = orderconfirmpage.getPrice(productName3);
		assertEquals(actualPricePrd3, PRODUCT_3_PRICE);
		VertexLogger.log(
			String.format(("Actual price after order placed :%s is matched with expected: %s"), actualPricePrd3,
				PRODUCT_3_PRICE), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate Discount after order placed
		double actualShippingDiscountOrderPlacedPrd3 = orderconfirmpage.getDiscount(productName3);
		assertEquals(actualShippingDiscountOrderPlacedPrd3, DISCOUNT);
		VertexLogger.log(String.format(("Actual discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountOrderPlacedPrd3, DISCOUNT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate total without tax after order placed
		double actualTotalWithOutTaxPrd3 = orderconfirmpage.getTotalWithOutTax(productName3);
		assertEquals(actualTotalWithOutTaxPrd3, ITEMS_SUBTOTAL_PRD3);
		VertexLogger.log(String.format(("Actual total without tax after order placed :%s is matched with expected: %s"),
			actualTotalWithOutTaxPrd3, ITEMS_SUBTOTAL_PRD3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate additional discounts after order placed
		double actualAdditionalDiscountsPrd3 = orderconfirmpage.getAdditionalDiscounts();
		assertEquals(actualAdditionalDiscountsPrd3, ADDITIONAL_DISCOUNTS);
		VertexLogger.log(
			String.format(("Actual additional discounts after order placed :%s is matched with expected: %s"),
				actualAdditionalDiscountsPrd3, ADDITIONAL_DISCOUNTS),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate handling cost after order placed
		double actualHandlingCostPrd3 = orderconfirmpage.getHandlingCost();
		assertEquals(actualHandlingCostPrd3, DEFAULT_HANDLING_COST);
		VertexLogger.log(String.format(("Actual handling cost after order placed :%s is matched with expected: %s"),
			actualHandlingCostPrd3, DEFAULT_HANDLING_COST), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping Sub-total after order placed
		double actualShippingSubtotalAfterOrderPlacedPrd3 = orderconfirmpage.getShippingSubtotal();
		assertEquals(actualShippingSubtotalAfterOrderPlacedPrd3, FAST_SHIPPING_SUBTOTAL);
		VertexLogger.log(String.format(("Actual shipping subtotal after order placed :%s is matched with expected: %s"),
			actualShippingSubtotalAfterOrderPlacedPrd3, FAST_SHIPPING_SUBTOTAL),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping discount after order placed
		double actualShippingDiscountAfterOrderPlacedPrd3 = orderconfirmpage.getShippingDiscount();
		assertEquals(actualShippingDiscountAfterOrderPlacedPrd3, ADD_ORDER_DISCOUNT);
		VertexLogger.log(String.format(("Actual shipping discount after order placed :%s is matched with expected: %s"),
			actualShippingDiscountAfterOrderPlacedPrd3, ADD_ORDER_DISCOUNT),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate shipping cost after order placed
		double actualShippingCostAfterOrderPlacedPrd3 = orderconfirmpage.getShippingCost();
		assertEquals(actualShippingCostAfterOrderPlacedPrd3, SHIIPING_COST_PRD3);
		VertexLogger.log(String.format(("Actual shipping cost after order placed :%s is matched with expected: %s"),
			actualShippingCostAfterOrderPlacedPrd3, SHIIPING_COST_PRD3),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate tax cost after order placed
		double actualTaxCostAfterOrderPlacedPrd3 = orderconfirmpage.getTaxCost();
		assertEquals(actualTaxCostAfterOrderPlacedPrd3, TAX_COST_3);
		VertexLogger.log(String.format(("Actual tax cost after order placed :%s is matched with expected: %s"),
			actualTaxCostAfterOrderPlacedPrd3, TAX_COST_3), EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Validate final cost after order placed
		double actualFinalCostAfterOrderPlacedPrd3 = orderconfirmpage.getFinalTotal();
		assertEquals(actualFinalCostAfterOrderPlacedPrd3, CART_TOTAL_PRD3);
		VertexLogger.log(String.format(("Actual final cost after order placed :%s is matched with expected: %s"),
			actualFinalCostAfterOrderPlacedPrd3, CART_TOTAL_PRD3),
			EpiGenerateInvoiceAutomaticInvoiceOptionsOnOffTests.class);

		// Delete address available under address book
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		addresspage = portalhomepage.clickAddressBookLink();
		addresspage.deleteAddress("CALIFORNIA-ADDRESS", true);
	}
}
