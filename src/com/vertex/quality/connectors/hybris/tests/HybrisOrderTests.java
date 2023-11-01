package com.vertex.quality.connectors.hybris.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.data.HybrisTestData;
import com.vertex.quality.connectors.hybris.enums.admin.HybrisAdminOrdersPageTabNames;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOOriginAddress;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductNames;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisShipmentMethods;
import com.vertex.quality.connectors.hybris.pages.backoffice.*;
import com.vertex.quality.connectors.hybris.pages.electronics.*;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertTrue;

public class HybrisOrderTests extends HybrisBaseTest
{
	/**
	 * Test To Validate the Tax by Changing the Order Quantity and Change ShipTo Address
	 *
	 * @author Nagaraju Gampa
	 */
	@Test(groups = { "order", "smoke" })
	public void hybrisChangeOrderQuantityAndShipToAddressTest( )
	{
		// =================Data declarations=================================
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String menuOrder = HybrisBONavTreeOptions.ORDER.getMenuName();
		final String menuOrders = HybrisBONavTreeOptions.ORDERS.getMenuName();
		final String positionsPricesTab = HybrisAdminOrdersPageTabNames.POSITIONS_PRICES.getTabName();
		final String tmaxp3200ProductID = HybrisProductIds.TMAXP3200ID.getproductID();
		final String tmaxp3200ProductName = HybrisProductNames.TMAXP3200NAME.getproductName();
		final String monopad100ID = HybrisProductIds.MONOPODID.getproductID();
		final String folsomCountry = Address.Folsom.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String folsomAddressLine1 = Address.Folsom.addressLine1;
		final String folsomCity = Address.Folsom.city;
		final String folsomState = Address.Folsom.state.fullName;
		final String folsomZip5 = Address.Folsom.zip5;
		final String folsomZip9 = Address.Folsom.zip9;
		final String expShippingMethod = "STANDARD DELIVERY";
		final String shippingMethodPremiumValue = "premium-net";
		final float expectedSubTotalAmount = HybrisTestData.MONOPAD_TMAX_SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmount = HybrisTestData.TAX_AMOUNT2;
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmount;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		final float expectedPremiumDeliveryAmount = HybrisTestData.PREMIUM_DELIVERY_AMOUNT;
		final float expectedTaxAmount1 = HybrisTestData.TAX_AMOUNT3;
		float expectedOrderTotalIncludeTax1 = expectedSubTotalAmount + expectedPremiumDeliveryAmount +
											  expectedTaxAmount1;
		expectedOrderTotalIncludeTax1 = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax1);

		final String tmaxProductQuantity = "5";
		final String newyorkCountry = Address.NewYork.country.fullName;
		final String newyorkAddressLine1 = Address.NewYork.addressLine1;
		final String newyorkCity = Address.NewYork.city;
		final String newyorkState = Address.NewYork.state.fullName;
		final String newyorkZip5 = Address.NewYork.zip5;
		final String newyorkZip9 = Address.NewYork.zip9;
		final float expectedSubTotalAmountAfterIncreaseQuantity = HybrisTestData.SUBTOTAL_AMOUNT_BY_INCREASE_QUANTITY;
		final float expectedTaxAmountAfterIncreaseQuantity = HybrisTestData.TAX_AMOUNT4;
		final float expectedTaxAmountAfterIncreaseQuantity1 = HybrisTestData.TAX_AMOUNT5;
		float expectedOrderTotalIncludeTaxAfterIncreaseQuantity = expectedSubTotalAmountAfterIncreaseQuantity +
																  expectedDeliveryAmount +
																  expectedTaxAmountAfterIncreaseQuantity;
		expectedOrderTotalIncludeTaxAfterIncreaseQuantity = VertexCurrencyUtils.getDecimalFormatAmount(
			expectedOrderTotalIncludeTaxAfterIncreaseQuantity);
		float expectedOrderTotalIncludeTaxAfterIncreaseQuantity1 = expectedSubTotalAmountAfterIncreaseQuantity +
																   expectedPremiumDeliveryAmount +
																   expectedTaxAmountAfterIncreaseQuantity1;
		expectedOrderTotalIncludeTaxAfterIncreaseQuantity1 = VertexCurrencyUtils.getDecimalFormatAmount(
			expectedOrderTotalIncludeTaxAfterIncreaseQuantity1);
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardVerificationNumber = CreditCard.CODE.text;
		final String expinclTaxState = HybrisTestData.INCL_TAXSTATE1;
		final String expinclTaxCountry = HybrisTestData.INCL_TAXCOUNTRY1;
		final String expinclTaxDistrict = HybrisTestData.INCL_TAXDISTRICT1;
		final String expDeliveryCost = HybrisTestData.PREMIUM_DELIVERY_COST;
		final String expTotalPrice = HybrisTestData.TOTAL_PRICE1;
		final String expInclTax = HybrisTestData.INCL_TAX1;

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to vertex configuration
		final HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// set Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID
		setVertexConfigurationTrustedID(vertexConfigPage);

		// save Vertex Configuration & Logout from Back office
		vertexConfigPage.saveVertexConfiguration();
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		HybrisEStorePage storeFront = launchB2CPage();

		//Get Cart Quantity and Remove All Items from Cart
		int cartQuantity = storeFront.getCartQuantity();
		VertexLogger.log(String.format("Cart Quantity is: %s", cartQuantity));
		if ( cartQuantity > 0 )
		{
			HybrisEStoreCartPage cartPage = storeFront.navigateToCart();
			cartPage.removeItemsFromCart();
		}

		// Add Products to Cart and Proceed to Checkout
		storeFront.searchAndAddProductToCart(tmaxp3200ProductID);
		storeFront.continueShoppingFromCart();
		storeFront.searchAndAddProductToCart(monopad100ID);
		HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(folsomCountry, titleMr, fName, lName, folsomAddressLine1, folsomCity,
			folsomState, folsomZip5);
		checkoutPage.clickDeliveryAddressNext();

		// Validate ShipToAddress on Order Summary Page
		String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(folsomAddressLine1),
			"Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(folsomCity), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(folsomZip9), "Expected Cleansed Zip is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(folsomCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(folsomState), "Expected State is not present in ShipTo Address");
		String elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Get and Validate selected Shipping Method
		String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Delivery Method page
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		final float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"actualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		//Select Shipping Method as PremiumDelivery and click on Next
		checkoutPage.selectShippingMethod(shippingMethodPremiumValue);
		checkoutPage.clickDeliveryMethodNext();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total for Premium Delivery Method from Payment Method page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		final float actualDeliveryAmount1 = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount1 == expectedPremiumDeliveryAmount,
			"ActualDeliveryAmount1 is not matching with expectedPremiumDeliveryAmount");

		float actualTaxAmount1 = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount1 == expectedTaxAmount1, "ActualTaxAmount1 is not matching with ExpectedTaxAmount1");

		final float actualOrderTotalIncludeTax1 = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax1 == expectedOrderTotalIncludeTax1,
			"actualOrderTotalIncludeTax1 is not matching with ExpectedOrderTotalIncludeTax1");

		//Increase TMaxP3200 Product Quantity to 4 and Proceed to checkout
		checkoutPage.changeQuantityForProduct(tmaxp3200ProductName, tmaxProductQuantity);
		storeFront = checkoutPage.addProductToCart();
		eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill NewYork Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(newyorkCountry, titleMr, fName, lName, newyorkAddressLine1, newyorkCity,
			newyorkState, newyorkZip5);
		checkoutPage.clickDeliveryAddressNext();

		// Validate ShipToAddress on Order Summary Page
		shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(newyorkAddressLine1),
			"Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(newyorkCity), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(newyorkZip9), "Expected Cleansed Zip is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(newyorkCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(newyorkState), "Expected State is not present in ShipTo Address");
		elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Get and Validate selected Shipping Method
		actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Delivery Method page
		float actualSubTotalAmountAfterIncreaseQuantity = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmountAfterIncreaseQuantity == expectedSubTotalAmountAfterIncreaseQuantity,
			"ActualSubTotalAmountAfterIncreaseQuantity is not matching with ExpectedSubTotalAmountAfterIncreaseQuantity");

		actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmountAfterIncreaseQuantity = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmountAfterIncreaseQuantity == expectedTaxAmountAfterIncreaseQuantity,
			"ActualTaxAmountAfterIncreaseQuantity is not matching with ExpectedTaxAmountAfterIncreaseQuantity");

		float actualOrderTotalIncludeTaxAfterIncreaseQuantity = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTaxAfterIncreaseQuantity == expectedOrderTotalIncludeTaxAfterIncreaseQuantity,
			"actualOrderTotalIncludeTaxAfterIncreaseQuantity is not matching with ExpectedOrderTotalIncludeTaxAfterIncreaseQuantity");

		//Select Shipping Method as PremiumDelivery and click on Next
		checkoutPage.selectShippingMethod(shippingMethodPremiumValue);
		checkoutPage.clickDeliveryMethodNext();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total for Premium Delivery Method from Payment Method page
		actualSubTotalAmountAfterIncreaseQuantity = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmountAfterIncreaseQuantity == expectedSubTotalAmountAfterIncreaseQuantity,
			"ActualSubTotalAmountAfterIncreaseQuantity is not matching with ExpectedSubTotalAmountAfterIncreaseQuantity");

		float actualPremiumDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualPremiumDeliveryAmount == expectedPremiumDeliveryAmount,
			"actualPremiumDeliveryAmount is not matching with expectedPremiumDeliveryAmount");

		actualTaxAmountAfterIncreaseQuantity = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmountAfterIncreaseQuantity == expectedTaxAmountAfterIncreaseQuantity1,
			"actualTaxAmountAfterIncreaseQuantity is not matching with ExpectedTaxAmountAfterIncreaseQuantity");

		actualOrderTotalIncludeTaxAfterIncreaseQuantity = checkoutPage.getOrderTotalAmount();
		assertTrue(
			actualOrderTotalIncludeTaxAfterIncreaseQuantity == expectedOrderTotalIncludeTaxAfterIncreaseQuantity1,
			"actualOrderTotalIncludeTaxAfterIncreaseQuantity is not matching with ExpectedOrderTotalIncludeTaxAfterIncreaseQuantity1");

		// Fill Payment card details
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpMonth,
			creditCardExpiryYear, creditCardVerificationNumber);

		// check use delivery address checkbox
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Final Review section of Order Summary
		actualSubTotalAmountAfterIncreaseQuantity = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmountAfterIncreaseQuantity == expectedSubTotalAmountAfterIncreaseQuantity,
			"ActualSubTotalAmountAfterIncreaseQuantity is not matching with ExpectedSubTotalAmountAfterIncreaseQuantity");

		actualPremiumDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualPremiumDeliveryAmount == expectedPremiumDeliveryAmount,
			"actualPremiumDeliveryAmount is not matching with expectedPremiumDeliveryAmount");

		actualTaxAmountAfterIncreaseQuantity = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmountAfterIncreaseQuantity == expectedTaxAmountAfterIncreaseQuantity1,
			"actualTaxAmountAfterIncreaseQuantity is not matching with ExpectedTaxAmountAfterIncreaseQuantity");

		actualOrderTotalIncludeTaxAfterIncreaseQuantity = checkoutPage.getOrderTotalAmount();
		assertTrue(
			actualOrderTotalIncludeTaxAfterIncreaseQuantity == expectedOrderTotalIncludeTaxAfterIncreaseQuantity1,
			"actualOrderTotalIncludeTaxAfterIncreaseQuantity is not matching with ExpectedOrderTotalIncludeTaxAfterIncreaseQuantity1");

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available OR Order Number is Blank");
		elementMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(elementMsg);

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to Orders Page
		final HybrisBOOrdersPage ordersPage = boHomePage.navigateToBOOrdersPage(menuOrder, menuOrders);

		// Search and Open Order Number
		ordersPage.orderNumberSearch(orderNumber);
		ordersPage.selectOrderNumberFromSearchResults();
		ordersPage.navigateToTabOnOrderPage(positionsPricesTab);

		// Get and Validate Incl.Tax Vales from Positions
		final Map<String, String> inclTaxValuesMap = ordersPage.getInclTaxValues();
		final String actualInclTaxState = inclTaxValuesMap.get("INCL.TAXSTATE");
		final String actualInclTaxCountry = inclTaxValuesMap.get("INCL.TAXCOUNTRY");
		final String actualInclTaxDistrict = inclTaxValuesMap.get("INCL.TAXDISTRICT");

		assertTrue(actualInclTaxState == expinclTaxState, "actualInclTaxState is not matching with expinclTaxState");
		assertTrue(actualInclTaxCountry == expinclTaxCountry,
			"actualInclTaxCountry is not matching with expinclTaxCountry");
		assertTrue(actualInclTaxDistrict == expinclTaxDistrict,
			"actualInclTaxDistrict is not matching with expinclTaxDistrict");

		// Get and Validate DeliveryCost, Total Price, Incl.Tax values from Positions
		final String actualDeliveryCost = ordersPage.getDeliveryCost();
		assertTrue(actualDeliveryCost == expDeliveryCost, "actualDeliveryCost is not matching with expDeliveryCost");
		final String actualTotalPrice = ordersPage.getTotalPrice();
		assertTrue(actualTotalPrice == expTotalPrice, "actualTotalPrice is not matching with expTotalPrice");
		final String actualInclTax = ordersPage.getInclTax();
		assertTrue(actualInclTax == expInclTax, "actualInclTax is not matching with expInclTax");

		// Logout from Back office
		boHomePage.logoutFromBackOffice();
	}

	/**
	 * Test To Validate the effect of Discount Amounts on Taxes in SalesOrder Transactions
	 *
	 * @author Nagaraju Gampa
	 */
	@Test(groups = { "order", "smoke" })
	public void hybrisDiscountsAtOrderLevelTest( )
	{
		// =================Data declarations=================================
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String menuOrder = HybrisBONavTreeOptions.ORDER.getMenuName();
		final String menuOrders = HybrisBONavTreeOptions.ORDERS.getMenuName();
		final String positionsPricesTab = HybrisAdminOrdersPageTabNames.POSITIONS_PRICES.getTabName();
		final String promotionEngineResultsTab = HybrisAdminOrdersPageTabNames.PROMOTION_ENG_RESULTS.getTabName();
		final String dslr330ProductID = HybrisProductIds.DSLRA330ID.getproductID();
		final float expectedSubTotalAmount = HybrisTestData.DSLRA330_SUBTOTAL_AMOUNT;
		final float expectedOrderDiscountAmount = HybrisTestData.DSLRA330_DISCOUNT_AMOUNT;
		final float expectedOrderTotalWithDiscount = HybrisTestData.DSLRA330_SUBTOTAL_AMOUNT;
		final String paloAltoCountry = Address.PaloAlto.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String paloAltoAddressLine1 = Address.PaloAlto.addressLine1;
		final String paloAltoCity = Address.PaloAlto.city;
		final String paloAltoState = Address.PaloAlto.state.fullName;
		final String paloAltoZip5 = Address.PaloAlto.zip5;
		final String paloAltoZip9 = Address.PaloAlto.zip9;
		final String expShippingMethod = "STANDARD DELIVERY";
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmount = HybrisTestData.TAX_AMOUNT6;
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmount;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		final String shippingMethodPremiumValue = "premium-net";
		final float expectedPremiumDeliveryAmount = HybrisTestData.PREMIUM_DELIVERY_AMOUNT;
		final float expectedTaxAmount1 = HybrisTestData.TAX_AMOUNT7;
		float expectedOrderTotalIncludeTax1 = expectedSubTotalAmount + expectedPremiumDeliveryAmount +
											  expectedTaxAmount1;
		expectedOrderTotalIncludeTax1 = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax1);
		float expectedFinalReviewOrderTotalExcludeTax = expectedSubTotalAmount + expectedPremiumDeliveryAmount;
		expectedFinalReviewOrderTotalExcludeTax = VertexCurrencyUtils.getDecimalFormatAmount(
			expectedFinalReviewOrderTotalExcludeTax);
		float expectedFinalReviewOrderTotalIncludeTax = expectedSubTotalAmount + expectedPremiumDeliveryAmount +
														expectedTaxAmount1;
		expectedFinalReviewOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(
			expectedFinalReviewOrderTotalIncludeTax);
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardVerificationNumber = CreditCard.CODE.text;
		final float expectedOrderConfirmationItemPrice = HybrisTestData.DSLRA330_ITEM_PRICE;
		final float expectedOrderConfirmationTotal = HybrisTestData.DSLRA330_ITEM_PRICE;
		final float expectedOrderConfirmationSubTotal = HybrisTestData.DSLRA330_SUBTOTAL_AMOUNT;
		final float expectedOrderConfirmationOrderDiscount = HybrisTestData.DSLRA330_DISCOUNT_AMOUNT;
		final float expectedOrderConfirmationShippingAmt = HybrisTestData.PREMIUM_DELIVERY_AMOUNT;
		final float expectedOrderConfirmationTaxAmt = HybrisTestData.TAX_AMOUNT7;
		float expectedOrderConfirmationOrderTotalAmount =
			expectedOrderConfirmationItemPrice - expectedOrderConfirmationOrderDiscount +
			expectedOrderConfirmationShippingAmt + expectedOrderConfirmationTaxAmt;
		expectedOrderConfirmationOrderTotalAmount = VertexCurrencyUtils.getDecimalFormatAmount(
			expectedOrderConfirmationOrderTotalAmount);
		final String expectedSavedAmountText = "You saved $20.00 on your order";
		final String expectedReceivedPromotionText = "Buy over $200.00 get $20.00 discount on cart";
		final String expinclTaxState = HybrisTestData.DSLR_DISCOUNT_INCL_TAXSTATE;
		final String expinclTaxCountry = HybrisTestData.DSLR_DISCOUNT_INCL_TAXCOUNTRY;
		final String expinclTaxDistrict = HybrisTestData.DSLR_DISCOUNT_INCL_TAXDISTRICT;
		final String expDeliveryCost = HybrisTestData.PREMIUM_DELIVERY_COST;
		final String expTotalPrice = HybrisTestData.DSLR_DISCOUNT_TOTAL_PRICE;
		final String expInclTax = HybrisTestData.DSLR_DISCOUNT_INCL_TAX;
		final String expPromotionCode = "order_threshold_fixed_discount";
		final String expPromotionName = "Get total $20 off all orders over $200";
		final String expPromotionDescription = "Buy over $200.00 get $20.00 discount on cart";
		final String expPromotionAppliedDiscount = "20.0";

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to vertex configuration
		final HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// set Vertex Administration Configuration
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.navigateToAdministrationTab();
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID
		setVertexConfigurationTrustedID(vertexConfigPage);

		// save Vertex Configuration & Logout from Back office
		vertexConfigPage.saveVertexConfiguration();
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		HybrisEStorePage storeFront = launchB2CPage();

		//Get Cart Quantity and Remove All Items from Cart
		int cartQuantity = storeFront.getCartQuantity();
		VertexLogger.log(String.format("Cart Quantity is: %s", cartQuantity));
		if ( cartQuantity > 0 )
		{
			HybrisEStoreCartPage cartPage = storeFront.navigateToCart();
			cartPage.removeItemsFromCart();
		}

		// Add Products to Cart and Proceed to Checkout
		storeFront.searchAndAddProductToCart(dslr330ProductID);
		HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Get and Validate SubTotal, Discount, Order Total From Order Summary page
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		float actualOrderDiscountAmount = checkoutPage.getOrderDiscount();
		assertTrue(actualOrderDiscountAmount == expectedOrderDiscountAmount,
			"actualOrderDiscountAmount is not matching with expectedOrderDiscountAmount");

		final float actualOrderTotalWithDiscount = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalWithDiscount == expectedOrderTotalWithDiscount,
			"actualOrderTotalWithDiscount is not matching with expectedOrderTotalWithDiscount");

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(paloAltoCountry, titleMr, fName, lName, paloAltoAddressLine1,
			paloAltoCity, paloAltoState, paloAltoZip5);
		checkoutPage.clickDeliveryAddressNext();

		// Validate ShipToAddress on Order Summary Page
		String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(paloAltoAddressLine1),
			"Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(paloAltoCity), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(paloAltoZip9), "Expected Cleansed Zip is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(paloAltoCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(paloAltoState), "Expected State is not present in ShipTo Address");
		String elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Get and Validate selected Shipping Method
		String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Delivery Method page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		actualOrderDiscountAmount = checkoutPage.getOrderDiscount();
		assertTrue(actualOrderDiscountAmount == expectedOrderDiscountAmount,
			"actualOrderDiscountAmount is not matching with expectedOrderDiscountAmount");

		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"actualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		//Select Shipping Method as PremiumDelivery and click on Next
		checkoutPage.selectShippingMethod(shippingMethodPremiumValue);
		checkoutPage.clickDeliveryMethodNext();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total for Premium Delivery Method from Payment Method page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		actualOrderDiscountAmount = checkoutPage.getOrderDiscount();
		assertTrue(actualOrderDiscountAmount == expectedOrderDiscountAmount,
			"actualOrderDiscountAmount is not matching with expectedOrderDiscountAmount");

		final float actualDeliveryAmount1 = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount1 == expectedPremiumDeliveryAmount,
			"ActualDeliveryAmount1 is not matching with expectedPremiumDeliveryAmount");

		float actualTaxAmount1 = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount1 == expectedTaxAmount1, "ActualTaxAmount1 is not matching with ExpectedTaxAmount1");

		final float actualOrderTotalIncludeTax1 = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax1 == expectedOrderTotalIncludeTax1,
			"actualOrderTotalIncludeTax1 is not matching with ExpectedOrderTotalIncludeTax1");

		// Fill Payment card details
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpMonth,
			creditCardExpiryYear, creditCardVerificationNumber);

		// check use delivery address checkbox
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Get and Validate SubTotal, Order Discount, Delivery Amount, Order Total from Final Review section of
		// Order Summary
		final float actualFinalReviewSubTotal = checkoutPage.getFinalReviewSubtotal();
		assertTrue(actualFinalReviewSubTotal == expectedSubTotalAmount,
			"ActualFinalReviewSubTotal is not matching with ExpectedFinalReviewSubTotalAmount");

		float actualFinalReviewOrderDiscountAmount = checkoutPage.getFinalReviewOrderDiscount();
		assertTrue(actualFinalReviewOrderDiscountAmount == expectedOrderDiscountAmount,
			"actualFinalReviewOrderDiscountAmount is not matching with expectedOrderDiscountAmount");

		final float actualFinalReviewPremiumDeliveryAmt = checkoutPage.getFinalReviewDeliveryAmount();
		assertTrue(actualFinalReviewPremiumDeliveryAmt == expectedPremiumDeliveryAmount,
			"actualFinalReviewPremiumDeliveryAmt is not matching with ExpectedFinalReviewPremiumDeliveryAmount");

		final float actualFinalReviewOrderTotalAmt = checkoutPage.getOrderTotalAmountExcludeTax();
		assertTrue(actualFinalReviewOrderTotalAmt == expectedFinalReviewOrderTotalExcludeTax,
			"ActualFinalReviewOrderTotalAmt is not matching with expectedFinalReviewOrderTotalExcludeTax");

		// Get and Validate SubTotal, Order Discount, Delivery Amount, Tax and Order Total from Final Review Order
		// Summary page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		actualOrderDiscountAmount = checkoutPage.getOrderDiscount();
		assertTrue(actualOrderDiscountAmount == expectedOrderDiscountAmount,
			"actualOrderDiscountAmount is not matching with expectedOrderDiscountAmount");

		float actualPremiumDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualPremiumDeliveryAmount == expectedPremiumDeliveryAmount,
			"ActualPremiumDeliveryAmount is not matching with ExpectedDeliveryAmount");

		actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount1, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedFinalReviewOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available OR Order Number is Blank");
		elementMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(elementMsg);

		//Get and Validate Price, Total, SubTotal, Order Discount, Delivery Amount, Tax and Order Total from Order Confirmation Page
		float actualOrderConfirmationItemPrice = orderConfirmPage.getItemPrice();
		assertTrue(actualOrderConfirmationItemPrice == expectedOrderConfirmationItemPrice,
			"ActualOrderConfirmationItemPrice is not matching with ExpectedOrderConfirmationItemPrice");

		float actualOrderConfirmationTotal = orderConfirmPage.getTotal();
		assertTrue(actualOrderConfirmationTotal == expectedOrderConfirmationTotal,
			"ActualOrderConfirmationTotal is not matching with ExpectedOrderConfirmationTotal");

		float actualOrderConfirmationSubTotal = orderConfirmPage.getSubTotal();
		assertTrue(actualOrderConfirmationSubTotal == expectedOrderConfirmationSubTotal,
			"ActualOrderConfirmationSubTotal is not matching with ExpectedOrderConfirmationSubTotal");

		float actualOrderConfirmationOrderDiscount = orderConfirmPage.getOrderDiscount();
		assertTrue(actualOrderConfirmationOrderDiscount == expectedOrderConfirmationOrderDiscount,
			"ActualOrderConfirmationOrderDiscount is not matching with ExpectedOrderConfirmationOrderDiscount");

		float actualOrderConfirmationShippingAmt = orderConfirmPage.getShippingAmount();
		assertTrue(actualOrderConfirmationShippingAmt == expectedOrderConfirmationShippingAmt,
			"ActualOrderConfirmationShippingAmt is not matching with ExpectedOrderConfirmationShippingAmt");

		float actualOrderConfirmationTaxAmt = orderConfirmPage.getTax();
		assertTrue(actualOrderConfirmationTaxAmt == expectedOrderConfirmationTaxAmt,
			"ActualOrderConfirmationTaxAmt is not matching with ExpectedOrderConfirmationTaxAmt");

		float actualOrderConfirmationOrderTotalAmount = orderConfirmPage.getOrderTotal();
		assertTrue(actualOrderConfirmationOrderTotalAmount == expectedOrderConfirmationOrderTotalAmount,
			"ActualOrderConfirmationOrderTotalAmount is not matching with ExpectedOrderConfirmationOrderTotalAmount");

		String actualSavedAmountText = orderConfirmPage.getSavedAmountText();
		assertTrue(expectedSavedAmountText.equalsIgnoreCase(actualSavedAmountText),
			"ActualSavedAmountText is not matching with ExpectedSavedAmountText");

		String actualReceivedPromotionText = orderConfirmPage.getReceivedPromotionText();
		assertTrue(expectedReceivedPromotionText.equalsIgnoreCase(actualReceivedPromotionText),
			"ActualReceivedPromotionText is not matching with ExpectedReceivedPromotionText");

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to Orders Page
		final HybrisBOOrdersPage ordersPage = boHomePage.navigateToBOOrdersPage(menuOrder, menuOrders);

		// Search and Open Order Number
		ordersPage.orderNumberSearch(orderNumber);
		ordersPage.selectOrderNumberFromSearchResults();
		ordersPage.navigateToTabOnOrderPage(positionsPricesTab);

		// Get and Validate Incl.Tax Vales from Positions
		final Map<String, String> inclTaxValuesMap = ordersPage.getInclTaxValues();
		final String actualInclTaxState = inclTaxValuesMap.get("INCL.TAXSTATE");
		final String actualInclTaxCountry = inclTaxValuesMap.get("INCL.TAXCOUNTRY");
		final String actualInclTaxDistrict = inclTaxValuesMap.get("INCL.TAXDISTRICT");

		assertTrue(actualInclTaxState == expinclTaxState, "actualInclTaxState is not matching with expinclTaxState");
		assertTrue(actualInclTaxCountry == expinclTaxCountry,
			"actualInclTaxCountry is not matching with expinclTaxCountry");
		assertTrue(actualInclTaxDistrict == expinclTaxDistrict,
			"actualInclTaxDistrict is not matching with expinclTaxDistrict");

		// Get and Validate DeliveryCost, Total Price, Incl.Tax values from Positions
		final String actualDeliveryCost = ordersPage.getDeliveryCost();
		assertTrue(actualDeliveryCost == expDeliveryCost, "actualDeliveryCost is not matching with expDeliveryCost");
		final String actualTotalPrice = ordersPage.getTotalPrice();
		assertTrue(actualTotalPrice == expTotalPrice, "actualTotalPrice is not matching with expTotalPrice");
		final String actualInclTax = ordersPage.getInclTax();
		assertTrue(actualInclTax == expInclTax, "actualInclTax is not matching with expInclTax");

		//Navigate To Promotion Engine Results Tab and Validate the PromotionCode and PromotionDiscounts
		ordersPage.navigateToTabOnOrderPage(promotionEngineResultsTab);
		final String actualPromotionCode = ordersPage.getPromotionCode();
		assertTrue(expPromotionCode.equalsIgnoreCase(actualPromotionCode),
			"ActualPromotionCode is not matching with ExpectedPromotionCode");
		final String actualPromotionName = ordersPage.getPromotionName();
		assertTrue(expPromotionName.equalsIgnoreCase(actualPromotionName),
			"ActualPromotionName is not matching with ExpectedPromotionName");
		final String actualPromotionDescription = ordersPage.getPromotionDescription();
		assertTrue(expPromotionDescription.equalsIgnoreCase(actualPromotionDescription),
			"ActualPromotionDescription is not matching with ExpectedPromotionDescription");
		final String actualPromotionAppliedDiscount = ordersPage.getAppliedDiscount();
		assertTrue(expPromotionAppliedDiscount.equalsIgnoreCase(actualPromotionAppliedDiscount),
			"ActualPromotionAppliedDiscount is not matching with ExpectedPromotionAppliedDiscount");

		// Logout from Back office
		boHomePage.logoutFromBackOffice();
	}

	/**
	 * Test To Update the Physical Origin Address and then checking the Sales Tax amounts in Sales
	 * Order transaction
	 *
	 * @author Nagaraju Gampa
	 */
	@Test(groups = { "order", "smoke" })
	public void hybrisUpdatePhysicalOriginAddressTest( )
	{
		// =================Data declarations=================================
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String propertiesTab = HybrisBOBaseStoreTabOptions.PROPERTIES.getoption();
		final String locationsTab = HybrisBOBaseStoreTabOptions.LOCATIONS.getoption();
		final String vertexCustomizationTab = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String originTysonStreetName = HybrisBOOriginAddress.TYSONS_ADDRESS.stName;
		final String originTysonStreetNumber = HybrisBOOriginAddress.TYSONS_ADDRESS.stNumber;
		final String originTysonPostalCode = HybrisBOOriginAddress.TYSONS_ADDRESS.postalCode;
		final String originTysonTown = HybrisBOOriginAddress.TYSONS_ADDRESS.town;
		final String originTysonCountry = HybrisBOOriginAddress.TYSONS_ADDRESS.country;
		final String originEastonStreetName = HybrisBOOriginAddress.EASTON_ADDRESS.stName;
		final String originEastonStreetNumber = HybrisBOOriginAddress.EASTON_ADDRESS.stNumber;
		final String originEastonPostalCode = HybrisBOOriginAddress.EASTON_ADDRESS.postalCode;
		final String originEastonTown = HybrisBOOriginAddress.EASTON_ADDRESS.town;
		final String originEastonCountry = HybrisBOOriginAddress.EASTON_ADDRESS.country;
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.Anaheim.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Anaheim.addressLine1;
		final String city = Address.Anaheim.city;
		final String state = Address.Anaheim.state.fullName;
		final String zip = Address.Anaheim.zip5;
		final String expShippingMethodName = HybrisShipmentMethods.STANDARD.name;
		final float expectedSubTotalAmount = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmount = HybrisTestData.TAX_AMOUNT1;
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmount;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpiryMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardCode = CreditCard.CODE.text;

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTabFromElectronicsStore(vertexCustomizationTab);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();
		electronicsStorePage.selectTabFromElectronicsStore(locationsTab);

		//navigate Default Delivery Origin page and Set new Origin Address
		HybrisBODefaultDeliveryOriginPage deliveryOriginPage
			= electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
		deliveryOriginPage.doubleClickOnOriginAddress();
		deliveryOriginPage.setOriginAddress(originTysonStreetName, originTysonStreetNumber, originTysonPostalCode,
			originTysonTown, originTysonCountry);
		deliveryOriginPage.closeAddressPopUp();

		//Navigate to Properties Tab
		electronicsStorePage.selectTabFromElectronicsStore(propertiesTab);
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to vertex configuration
		final HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// Navigate to Vertex Configuration and Set Configurations
		boHomePage.navigateToConfigurationPage(menuVertex, menuVertexConfiguration);
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID
		setVertexConfigurationTrustedID(vertexConfigPage);

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		final HybrisEStorePage storeFront = launchB2CPage();

		//Get Cart Quantity and Remove All Items from Cart
		int cartQuantity = storeFront.getCartQuantity();
		VertexLogger.log(String.format("Cart Quantity is: %s", cartQuantity));
		if ( cartQuantity > 0 )
		{
			HybrisEStoreCartPage cartPage = storeFront.navigateToCart();
			cartPage.removeItemsFromCart();
		}

		// Add Product - PowerShot to Cart
		storeFront.searchAndAddProductToCart(powershotProductId);
		final HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		final HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Get and Validate selected Shipping Method
		final String actualShippingMethodName = checkoutPage.getShippingMethod();
		assertTrue(expShippingMethodName.equalsIgnoreCase(actualShippingMethodName),
			"ActualShippingMethod is not matching with ExpectedShippingMethod");

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Delivery Method page
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment card details
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpiryMonth,
			creditCardExpiryYear, creditCardCode);

		// check use delivery address checkbox and proceed to checkout
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available OR Order Number is Blank");
		final String elementMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(elementMsg);

		//Get and Validate SubTotal, Shipping, Tax and Order Total from Order Confirmation Page
		float actualOrderConfirmationSubTotal = orderConfirmPage.getSubTotal();
		assertTrue(actualOrderConfirmationSubTotal == expectedSubTotalAmount,
			"ActualOrderConfirmationSubTotal is not matching with expectedSubTotalAmount");

		float actualOrderConfirmationShippingAmt = orderConfirmPage.getShippingAmount();
		assertTrue(actualOrderConfirmationShippingAmt == expectedDeliveryAmount,
			"ActualOrderConfirmationShippingAmt is not matching with expectedDeliveryAmount");

		float actualOrderConfirmationTaxAmt = orderConfirmPage.getTax();
		assertTrue(actualOrderConfirmationTaxAmt == expectedTaxAmount,
			"ActualOrderConfirmationTaxAmt is not matching with ExpectedOrderConfirmationTaxAmt");

		float actualOrderConfirmationOrderTotalAmount = orderConfirmPage.getOrderTotal();
		assertTrue(actualOrderConfirmationOrderTotalAmount == expectedOrderTotalIncludeTax,
			"ActualOrderConfirmationOrderTotalAmount is not matching with ExpectedOrderConfirmationOrderTotalAmount");

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce, menuBaseStore);

		// Navigate to Electronics Store - Locations Tab
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(locationsTab);

		//navigate Default Delivery Origin page and Set new Origin Address
		deliveryOriginPage = electronicsStorePage.doubleClickOnDefaultDeliveryOrigin();
		deliveryOriginPage.doubleClickOnOriginAddress();
		deliveryOriginPage.setOriginAddress(originEastonStreetName, originEastonStreetNumber, originEastonPostalCode,
			originEastonTown, originEastonCountry);
		deliveryOriginPage.closeAddressPopUp();

		//Navigate to Properties Tab
		electronicsStorePage.selectTabFromElectronicsStore(propertiesTab);
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.saveElectronicsStoreConfiguration();
	}

	/**
	 * Test To verify that deliveryTerm field is set to "SUP" only for the US to Canada shipping
	 * transactions
	 *
	 * @author Nagaraju Gampa
	 */
	@Test(groups = { "order", "smoke" })
	public void hybrisDeliveryTermSetToSUPForUSToCanadaShippingTest( )
	{
		// =================Data declarations=================================
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String vertexCustomizationTab = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String monopad100ID = HybrisProductIds.MONOPODID.getproductID();
		final String quantity = "3";
		final String canadaCountry = Address.Northbrook.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Northbrook.addressLine1;
		final String city = Address.Northbrook.city;
		final String province = Address.Northbrook.province.fullName;
		final String zip = Address.Northbrook.zip5;
		final String expShippingMethodName = HybrisShipmentMethods.STANDARDCANADA.name;
		final float expectedSubTotalAmount = HybrisTestData.SUBTOTAL_AMOUNT_FOR_CANADA_ADDRESS;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT_FOR_CANADA;
		final float expectedTaxAmount = HybrisTestData.TAX_AMOUNT8;
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmount;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		float expectedOrderTotalExcludeTax = expectedSubTotalAmount + expectedDeliveryAmount;
		expectedOrderTotalExcludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalExcludeTax);
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpiryMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardCode = CreditCard.CODE.text;

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTabFromElectronicsStore(vertexCustomizationTab);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// navigate to vertex configuration
		final HybrisBOVertexConfigurationPage vertexConfigPage = boHomePage.navigateToConfigurationPage(menuVertex,
			menuVertexConfiguration);

		// Navigate to Vertex Configuration and Set Configurations
		boHomePage.navigateToConfigurationPage(menuVertex, menuVertexConfiguration);
		vertexConfigPage.selectVertexConfigurationRow(0);
		vertexConfigPage.setMessagingLog("True");

		// set Vertex Configuration with TrustedID & Save Vertex Configuration
		setVertexConfigurationTrustedID(vertexConfigPage);
		vertexConfigPage.saveVertexConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		final HybrisEStorePage storeFront = launchB2CPage();

		//Get Cart Quantity and Remove All Items from Cart
		int cartQuantity = storeFront.getCartQuantity();
		VertexLogger.log(String.format("Cart Quantity is: %s", cartQuantity));
		if ( cartQuantity > 0 )
		{
			HybrisEStoreCartPage cartPage = storeFront.navigateToCart();
			cartPage.removeItemsFromCart();
		}

		// Add Product - MonoPod100 to Cart, increase the quantity and proceed to checkout
		storeFront.searchAndSelectProduct(monopad100ID);
		storeFront.updateProductQuantity(quantity);
		storeFront.addProductToCart();
		final HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		final HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(canadaCountry, titleMr, fName, lName, addressLine1, city, province,
			zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate ShipTo Section of Order Summary
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressLine1));
		assertTrue(shipToAddress.contains(city));
		assertTrue(shipToAddress.contains(zip));
		assertTrue(shipToAddress.contains(canadaCountry));
		assertTrue(shipToAddress.contains(province));

		// Get and Validate selected Shipping Method
		final String actualShippingMethodName = checkoutPage.getShippingMethod();
		assertTrue(expShippingMethodName.equalsIgnoreCase(actualShippingMethodName),
			"ActualShippingMethod is not matching with ExpectedShippingMethod");

		// Get and Validate SubTotal, Delivery Amount and Order Total from Delivery Method page
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalInclude is not matching with ExpectedOrderTotalIncludeTax");

		checkoutPage.clickDeliveryMethodNext();

		// Get and Validate SubTotal, Delivery Amount and Order Total from Payment Method page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalInclude is not matching with ExpectedOrderTotalIncludeTax");

		// Fill Payment card details
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpiryMonth,
			creditCardExpiryYear, creditCardCode);

		// check use delivery address checkbox and proceed to checkout
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Get and Validate SubTotal, Delivery Amount, Order Total from Final Review section of
		// Order Summary
		final float actualFinalReviewSubTotal = checkoutPage.getFinalReviewSubtotal();
		assertTrue(actualFinalReviewSubTotal == expectedSubTotalAmount,
			"ActualFinalReviewSubTotal is not matching with ExpectedFinalReviewSubTotalAmount");

		final float actualFinalReviewDeliveryAmt = checkoutPage.getFinalReviewDeliveryAmount();
		assertTrue(actualFinalReviewDeliveryAmt == expectedDeliveryAmount,
			"ActualFinalReviewDeliveryAmt is not matching with ExpectedFinalReviewDeliveryAmount");

		final float actualFinalReviewOrderTotalAmt = checkoutPage.getOrderTotalAmountExcludeTax();
		assertTrue(actualFinalReviewOrderTotalAmt == expectedOrderTotalExcludeTax,
			"ActualFinalReviewOrderTotalAmt is not matching with expectedOrderTotalExcludeTax");

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Final Order
		// Summary page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmount, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available OR Order Number is Blank");
		final String elementMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(elementMsg);

		/*
		 * //TODO - Place Holder to Implement Logging Functionality after moving all logs to AWS
		 * //login as Admin user into Hybris-Admin Page
		 * HybrisAdminHomePage adminhomepage = logInAsAdminUser();
		 * //navigate to Support Page
		 * boHomePage.navigateToSupportPage(menuPlatform, subMenuSupport);
		 */
	}
}