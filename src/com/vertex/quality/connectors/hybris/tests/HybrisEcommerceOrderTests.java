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
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisShipmentMethods;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOBaseStorePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOOrdersPage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreCheckOutPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreGuestLoginPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreOrderConfirmationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStorePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Test to validate Hybris Order Tests
 *
 * @author Nagaraju Gampa
 */
public class HybrisEcommerceOrderTests extends HybrisBaseTest
{
	/**
	 * Test To Verify Basic Checkout
	 */
	@Test(groups = { "order", "smoke" })
	public void hybrisBasicCheckoutTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String menuOrder = HybrisBONavTreeOptions.ORDER.getMenuName();
		final String menuOrders = HybrisBONavTreeOptions.ORDERS.getMenuName();
		final String positionsPricesTab = HybrisAdminOrdersPageTabNames.POSITIONS_PRICES.getTabName();
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
		final String cardType = CreditCard.TYPE.text;
		final String cardName = CreditCard.NAME.text;
		final String cardNumber = CreditCard.NUMBER.text;
		final String expMonth = CreditCard.EXPIRY_MONTH.text;
		final String expYear = CreditCard.EXPIRY_YEAR.text;
		final String verificationNumber = CreditCard.CODE.text;
		final float expectedSubTotal = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmt = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmt = HybrisTestData.TAX_AMOUNT1;
		float expectedOrderTotalIncludeTax = expectedSubTotal + expectedDeliveryAmt + expectedTaxAmt;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		final String expinclTaxState = HybrisTestData.INCL_TAXSTATE;
		final String expinclTaxCountry = HybrisTestData.INCL_TAXCOUNTRY;
		final String expinclTaxDistrict = HybrisTestData.INCL_TAXDISTRICT;
		final String expDeliveryCost = HybrisTestData.STANDARD_DELIVERY_COST;
		final String expTotalPrice = HybrisTestData.TOTAL_PRICE;
		final String expInclTax = HybrisTestData.INCL_TAX;

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Configuration
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

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		final HybrisEStorePage storeFront = launchB2CPage();

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
		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment Details and Proceed to Checkout
		checkoutPage.fillPaymentDetails(cardType, cardName, cardNumber, expMonth, expYear, verificationNumber);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Get and Validate SubTotal
		final float actualSubTotal = checkoutPage.getSubtotal();
		assertTrue(actualSubTotal == expectedSubTotal, "ActualSubTotal is not matching with ExpectedSubTotal");

		// Get and Validate Delivery Amount
		final float actualDeliveryAmt = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmt == expectedDeliveryAmt,
			"ActualDeliveryAmt is not matching with ExpectedDeliveryAmt");

		// Get and Validate Tax Amount
		final float actualTaxAmt = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmt == expectedTaxAmt, "ActualTaxAmt is not matching with ExpectedTaxAmt");

		// Get and Validate Order Total Amount Including Tax
		final float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		final String elementMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(elementMsg);
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");

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
		assertTrue(expinclTaxState.equalsIgnoreCase(actualInclTaxState),
			"actualInclTaxState is not matching with expinclTaxState");
		assertTrue(expinclTaxCountry.equalsIgnoreCase(actualInclTaxCountry),
			"actualInclTaxCountry is not matching with expinclTaxCountry");
		assertTrue(expinclTaxDistrict.equalsIgnoreCase(actualInclTaxDistrict),
			"actualInclTaxDistrict is not matching with expinclTaxDistrict");

		// Get and Validate DeliveryCost, Total Price, Incl.Tax values from Positions
		final String actualDeliveryCost = ordersPage.getDeliveryCost();
		assertTrue(expDeliveryCost.equalsIgnoreCase(actualDeliveryCost),
			"actualDeliveryCost is not matching with expDeliveryCost");
		final String actualTotalPrice = ordersPage.getTotalPrice();
		assertTrue(expTotalPrice.equalsIgnoreCase(actualTotalPrice),
			"actualTotalPrice is not matching with expTotalPrice");
		final String actualInclTax = ordersPage.getInclTax();
		assertTrue(expInclTax.equalsIgnoreCase(actualInclTax), "actualInclTax is not matching with expInclTax");

		// Logout from Back office
		boHomePage.logoutFromBackOffice();
	}

	/**
	 * Test To Verify Basic Checkout with XML Elements (Note: Validation of logs is put on hold)
	 * i.e Enable Tax Invoicing is True
	 */
	@Test(groups = { "order", "smoke" })
	public void hybrisBasicCheckoutAllXMLElementsTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.Anaheim.country.fullName;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Anaheim.addressLine1;
		final String city = Address.Anaheim.city;
		final String state = Address.Anaheim.state.fullName;
		final String zip = Address.Anaheim.zip5;
		final String titleMr = CommonDataProperties.TITLE;
		final String expShippingMethod = "STANDARD DELIVERY";
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpiryMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardCode = CreditCard.CODE.text;
		final float expectedSubTotalAmount = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmount = HybrisTestData.TAX_AMOUNT1;
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmount;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		float expectedFinalReviewOrderTotalExcludeTax = expectedSubTotalAmount + expectedDeliveryAmount;
		expectedFinalReviewOrderTotalExcludeTax = VertexCurrencyUtils.getDecimalFormatAmount(
			expectedFinalReviewOrderTotalExcludeTax);

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		final HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Configuration
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

		// save Vertex Configuration
		vertexConfigPage.saveVertexConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();

		// launch Electronics store front page
		final HybrisEStorePage storeFront = launchB2CPage();

		// Add Product - PowerShot to Cart and Proceed to Checkout
		storeFront.searchAndAddProductToCart(powershotProductId);
		final HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		final HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		// checkoutpage.fillShippingAddressDeatils(usCountry, "Mr.", firstName, lastName,
		// addressLine1, city, state, zip);
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

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

		// Get and Validate selected Shipping Method
		final String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));
		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment card details
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpiryMonth,
			creditCardExpiryYear, creditCardCode);

		// check use delivery address checkbox
		checkoutPage.enableUseDeliveryAddress();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Payment Method page
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
		assertTrue(actualFinalReviewOrderTotalAmt == expectedFinalReviewOrderTotalExcludeTax,
			"ActualFinalReviewOrderTotalAmt is not matching with expectedOrderTotalExcludeTax");

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Final Review Order
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
