package com.vertex.quality.connectors.hybris.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.hybris.data.HybrisTestData;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOBaseStoreTabOptions;
import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBONavTreeOptions;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisProductIds;
import com.vertex.quality.connectors.hybris.enums.electronics.HybrisShipmentMethods;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOBaseStorePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOHomePage;
import com.vertex.quality.connectors.hybris.pages.backoffice.HybrisBOVertexConfigurationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreCheckOutPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreGuestLoginPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStoreOrderConfirmationPage;
import com.vertex.quality.connectors.hybris.pages.electronics.HybrisEStorePage;
import com.vertex.quality.connectors.hybris.tests.base.HybrisBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Hybris Address Validation Tests
 */
public class HybrisAddressValidationTests extends HybrisBaseTest
{
	/**
	 * Test To verify the "Hybris Pop-up" feature and Use "Suggested Address" option when "Always
	 * Accept Cleansed Address" is set as False
	 *
	 * @author Nagaraju Gampa
	 */
	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisPopupEnabledCleansedAddressTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final String zip9Digit = Address.Berwyn.zip9;
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String expShippingMethod = HybrisShipmentMethods.STANDARD.name;
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

		// Set Always Accept Cleansed Address as False
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "False");
		electronicsStorePage.saveElectronicsStoreConfiguration();

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
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate Address Cleansing popup and Suggested Address
		final boolean addresscleansingpopupdisplayed = checkoutPage.isAddressCleansingPopupDisplayed();
		assertTrue(addresscleansingpopupdisplayed, "Address Cleansing Popup is not displayed, please check...");
		final boolean isusethisaddressbuttondisplayed = checkoutPage.isUseThisAddressButtonDisplayed();
		assertTrue(isusethisaddressbuttondisplayed, "Use This Address Button is NOT displayed, please check...");
		final boolean issubmitasisbuttondisplayed = checkoutPage.isSubmittAsIsButtonDisplayed();
		assertTrue(issubmitasisbuttondisplayed, "Submit As Is Button is NOT displayed, please check...");
		final String cleansedAddress = checkoutPage.getSuggestedAddressFromAddressCleansingPopUp();
		assertTrue(cleansedAddress.contains(addressLine1), "Expected AddressLine1 is not present in Suggested Address");
		assertTrue(cleansedAddress.contains(city), "Expected City is not present in Suggested Address");
		assertTrue(cleansedAddress.contains(zip9Digit), "Expected Cleansed Zip is not present in Suggested Address");
		assertTrue(cleansedAddress.contains(usCountry), "Expected Country is not present in Suggested Address");
		assertTrue(cleansedAddress.contains(state), "Expected State is not present in Suggested Address");

		String elementMsg = String.format("CleansedAddress is: %s", cleansedAddress);
		VertexLogger.log(elementMsg);

		// Validate Original Address on Address Cleansing popup
		final String originalAddress = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
		assertTrue(originalAddress.contains(addressLine1), "Expected AddressLine1 is not present in Original Address");
		assertTrue(originalAddress.contains(city), "Expected City is not present in Original Address");
		assertTrue(originalAddress.contains(zip), "Expected Zip is not present in Original Address");
		assertTrue(originalAddress.contains(usCountry), "Expected Country is not present in Original Address");
		assertTrue(originalAddress.contains(state), "Expected State is not present in Original Address");

		elementMsg = String.format("OriginalAddress is: %s", originalAddress);
		VertexLogger.log(elementMsg);

		// Below line can be used after fixing of an existing defect(user loggedout when clicks on
		// "UseThisAddress" button) in application.
		// checkoutPage.clickUseThisAddress();

		// Below 3 lines are alternate option due to above defect in existing application
		checkoutPage.closeAddressCleansingPopUp();
		checkoutPage.setPostCode(zip9Digit);
		checkoutPage.clickDeliveryAddressNext();
		// Delete above 3 lines after fixing of above defect in application

		// Validate ShipToAddress on Order Summary Page
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressLine1), "Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(city), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(zip9Digit), "Expected Cleansed Zip is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(usCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(state), "Expected State is not present in ShipTo Address");
		elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Get and Validate selected Shipping Method
		final String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(expShippingMethod.equalsIgnoreCase(actualShippingMethod),
			"ActualShippingMethod is not matching with ExpectedShippingMethod");
		checkoutPage.clickDeliveryMethodNext();

		// Fill Credit Card Payment Details and Proceed to Checkout
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpiryMonth,
			creditCardExpiryYear, creditCardCode);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		final String orderNumberMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(orderNumberMsg);

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce, menuBaseStore);

		// Set Always Accept Cleansed Address as True
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);

		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();
	}

	/**
	 * Test To verify the "Hybris Pop-up" feature and Use "Original Address" option when "Always
	 * Accept Cleansed Address" is set as False
	 */

	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisPopupEnabledOriginalAddressTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final String zipcodecleansed = Address.Berwyn.zip9;
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String expShippingMethod = HybrisShipmentMethods.STANDARD.name;
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

		// Set Always Accept Cleansed Address as False
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "False");
		electronicsStorePage.saveElectronicsStoreConfiguration();

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
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate Address Cleansing popup
		boolean addressCleansingPopupDisplayed = checkoutPage.isAddressCleansingPopupDisplayed();
		assertTrue(addressCleansingPopupDisplayed, "Address Cleansing Popup is not displayed, please check...");

		// Close Address Cleansing pop-up and proceed to checkout
		checkoutPage.closeAddressCleansingPopUp();
		checkoutPage.clickDeliveryAddressNext();

		// Validate Address Cleansing popup
		addressCleansingPopupDisplayed = checkoutPage.isAddressCleansingPopupDisplayed();
		assertTrue(addressCleansingPopupDisplayed, "Address Cleansing Popup is not displayed, please check...");

		// Validate Original Address on Address Cleansing popup
		final String originalAddress = checkoutPage.getOriginalAddressFromAddressCleansingPopUp();
		assertTrue(originalAddress.contains(addressLine1), "Expected AddressLine1 is not present in Original Address");
		assertTrue(originalAddress.contains(city), "Expected City is not present in Original Address");
		assertTrue(originalAddress.contains(zip), "Expected Zip is not present in Original Address");
		assertTrue(originalAddress.contains(usCountry), "Expected Country is not present in Original Address");
		assertTrue(originalAddress.contains(state), "Expected State is not present in Original Address");

		String elementMsg = String.format("Original Address is: %s", originalAddress);
		VertexLogger.log(elementMsg);

		// Below 9 lines can be used after fixing of an existing defect(user logged out when clicks
		// on "SubmitAsIs" button) in application.
		// checkoutPage.clickSubmitAsIs();
		// Validate ShipToAddress on Order Summary Page
		// String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		// assertTrue(shipToAddress.contains(addressLine1), "Expected AddressLine1 is not present in
		// ShipTo Address");
		// assertTrue(shipToAddress.contains(city), "Expected City is not present in ShipTo
		// Address");
		// assertTrue(shipToAddress.contains(zip), "Expected Zip is not present in ShipTo Address");
		// assertTrue(shipToAddress.contains(usCountry), "Expected Country is not present in ShipTo
		// Address");
		// assertTrue(shipToAddress.contains(state), "Expected State is not present in ShipTo
		// Address");
		// elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		// VertexLogger.log(elementMsg);

		// Below 11 lines are alternate option due to above defect in existing application
		checkoutPage.closeAddressCleansingPopUp();
		checkoutPage.setPostCode(zipcodecleansed);
		checkoutPage.clickDeliveryAddressNext();
		// Validate ShipToAddress on Order Summary Page
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressLine1), "Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(city), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(zipcodecleansed), "Expected Cleansed Zip is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(usCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(state), "Expected State is not present in ShipTo Address");

		elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Delete above 11 lines after fixing of above defect in application

		// Get and Validate selected Shipping Method
		final String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(expShippingMethod.equalsIgnoreCase(actualShippingMethod),
			"ActualShippingMethod is not matching with ExpectedShippingMethod");
		checkoutPage.clickDeliveryMethodNext();

		// Fill Credit Card Payment Details and Proceed to Checkout
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpiryMonth,
			creditCardExpiryYear, creditCardCode);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		final String orderNumberMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(orderNumberMsg);

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce, menuBaseStore);

		// Set Always Accept Cleansed Address as True
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();
	}

	/**
	 * Test To verify "Hybris Pop-up" is not displayed, when "Always Accept Cleansed Address" = TRUE
	 */
	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisPopupDisableTest( )
	{
		// ==================Data declaration==================

		final String country = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fname = CommonDataProperties.FIRST_NAME;
		final String lname = CommonDataProperties.LAST_NAME;
		final String addressline1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String postalCode = Address.Berwyn.zip5;
		final String postalCode9Digit = Address.Berwyn.zip9;
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String menuNameBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuNameBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		final HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(
			menuNameBaseCommerce, menuNameBaseStore);

		// Set Always Accept Cleansed Address as False
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Always Accept Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

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
		checkoutPage.fillShippingAddressDetails(country, titleMr, fname, lname, addressline1, city, state, postalCode);
		checkoutPage.clickDeliveryAddressNext();

		// Check whether Address Cleansing popup displayed
		final boolean addresscleansingpopupdisplayed = checkoutPage.isAddressCleansingPopupDisplayed();
		assertFalse(addresscleansingpopupdisplayed,
			"Address Cleansing Popup is displayed but shouldn't be displayed here, please check...");

		// Validate the Cleansed Address from ShipTo Section
		String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressline1));
		assertTrue(shipToAddress.contains(city));
		assertTrue(shipToAddress.contains(postalCode9Digit));
		assertTrue(shipToAddress.contains(country));
		assertTrue(shipToAddress.contains(state));

		// Validate the Cleansed Shipping Address from Shipping Method section
		final String shippingAddress = checkoutPage.getShippingAddressFromShippingMethodSection();
		assertTrue(shippingAddress.contains(addressline1));
		assertTrue(shippingAddress.contains(city));
		assertTrue(shippingAddress.contains(postalCode9Digit));
		assertTrue(shippingAddress.contains(country));
		assertTrue(shippingAddress.contains(state));

		// Click next button to move on to Delivery Method
		checkoutPage.clickDeliveryMethodNext();

		// Validate the Cleansed Address from ShipTo Section
		shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressline1));
		assertTrue(shipToAddress.contains(city));
		assertTrue(shipToAddress.contains(postalCode9Digit));
		assertTrue(shipToAddress.contains(country));
		assertTrue(shipToAddress.contains(state));
	}

	/**
	 * Test To verify behavior when Invalid Address is given - No Address Cleansing and No Tax
	 * Calculations
	 * i.e.Test To verify an address which can not be cleansed and can not be resolved to a tax area
	 * should allow transaction to continue with with no tax calculation
	 */
	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisInvalidAddressTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.InvalidAddress.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fname = CommonDataProperties.FIRST_NAME;
		final String lname = CommonDataProperties.LAST_NAME;
		final String addressline1 = Address.InvalidAddress.addressLine1;
		final String city = Address.InvalidAddress.city;
		final String state = Address.InvalidAddress.state.fullName;
		final String zip = Address.InvalidAddress.zip5;
		final String expShippingMethod = HybrisShipmentMethods.STANDARD.name;
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpiryMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardCode = CreditCard.CODE.text;
		final float expectedSubTotalAmount = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		float expectedOrderTotalExcludeTax = expectedSubTotalAmount + expectedDeliveryAmount;
		expectedOrderTotalExcludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalExcludeTax);
		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		final HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		final HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);
		electronicsStorePage.selectElectronicsStore();

		// Set and Save Configurations - External Tax Calculation, Accepts Cleansed Address, etc..
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

		// set Vertex Configuration with TrustedID & save Vertex Configuration
		setVertexConfigurationTrustedID(vertexConfigPage);
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
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fname, lname, addressline1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// validate presence of Tax element - shouldn't be displayed
		final boolean taxDisplayed = checkoutPage.isTaxDisplayed();
		assertTrue(!taxDisplayed, "Tax should not be displayed here but Tax displayed");
		VertexLogger.log("As per the expected output, Tax field should not display. But it is displayed",
			VertexLogLevel.ERROR);

		// Validate the Invalid Address from ShipTo Section
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressline1));
		assertTrue(shipToAddress.contains(city));
		assertTrue(shipToAddress.contains(zip));
		assertTrue(shipToAddress.contains(usCountry));
		assertTrue(shipToAddress.contains(state));

		// Get and Validate SubTotal, Delivery Amount and Order Total from Delivery Method page
		final float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		final float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		final float actualOrderTotalExcludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalExcludeTax == expectedOrderTotalExcludeTax,
			"ActualOrderTotalExcludeTax is not matching with ExpectedOrderTotalIncludeTax");

		// Get and Validate selected Shipping Method
		final String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(actualShippingMethod.contains(expShippingMethod));
		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment card details and Click Payment Next
		checkoutPage.fillPaymentDetails(creditCardType, creditCardName, creditCardNumber, creditCardExpiryMonth,
			creditCardExpiryYear, creditCardCode);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		final String orderNumberMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(orderNumberMsg);
	}

	/**
	 * Test To verify basic checkout functionality with different shipping and billing addresses
	 */
	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisDifferentShippingAndBillingAddressesTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fname = CommonDataProperties.FIRST_NAME;
		final String lname = CommonDataProperties.LAST_NAME;
		final String addressline1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final String zip9Digit = Address.Berwyn.zip9;
		final String deliveryAddressline1 = Address.Anaheim.addressLine1;
		final String deliveryCity = Address.Anaheim.city;
		final String deliveryState = Address.Anaheim.state.fullName;
		final String deliveryZip = Address.Anaheim.zip5;
		final String expShippingMethod = HybrisShipmentMethods.STANDARD.name;
		final String creditCardType = CreditCard.TYPE.text;
		final String creditCardName = CreditCard.NAME.text;
		final String creditCardNumber = CreditCard.NUMBER.text;
		final String creditCardExpiryMonth = CreditCard.EXPIRY_MONTH.text;
		final String creditCardExpiryYear = CreditCard.EXPIRY_YEAR.text;
		final String creditCardCode = CreditCard.CODE.text;
		final float expectedSubTotalAmount = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmount = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmt = HybrisTestData.TAX_AMOUNT;
		float expectedOrderTotalIncludeTax = expectedSubTotalAmount + expectedDeliveryAmount + expectedTaxAmt;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);
		float expectedOrderTotalExcludeTax = expectedSubTotalAmount + expectedDeliveryAmount;
		expectedOrderTotalExcludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalExcludeTax);

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

		// Add Product - PowerShot to Cart
		storeFront.searchAndAddProductToCart(powershotProductId);
		final HybrisEStoreGuestLoginPage eStoreGuestLoginPage = storeFront.proceedToCheckout();

		// Set Electronics Store Guest Credentials and checkout as Guest
		setGuestEmailAndConfirmEmail(eStoreGuestLoginPage);
		final HybrisEStoreCheckOutPage checkoutPage = eStoreGuestLoginPage.checkoutAsGuest();

		// Fill Shipping Address Details and Proceed to Checkout
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fname, lname, addressline1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Delivery Method page
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmt, "ActualTaxAmount is not matching with ExpectedTaxAmount");

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

		// uncheck use delivery address checkbox
		checkoutPage.uncheckUseDeliveryAddress();

		// Fill Delivery Address Details and proceed to checkout
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fname, lname, deliveryAddressline1, deliveryCity,
			deliveryState, deliveryZip);
		checkoutPage.clickpaymentBillingAddressNext();

		// Validate the Cleansed Address from ShipTo Section of Order Summary
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressline1));
		assertTrue(shipToAddress.contains(city));
		assertTrue(shipToAddress.contains(zip9Digit));
		assertTrue(shipToAddress.contains(usCountry));
		assertTrue(shipToAddress.contains(state));

		// Validate the Payment Address from Order Summary Page
		final String paymentAddress = checkoutPage.getPaymentAddressFromOrderSummary();
		assertTrue(paymentAddress.contains(deliveryAddressline1));
		assertTrue(paymentAddress.contains(deliveryCity));
		assertTrue(paymentAddress.contains(deliveryZip));
		assertTrue(paymentAddress.contains(usCountry));
		assertTrue(paymentAddress.contains(deliveryState));

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Final Order Summary
		// page
		actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotalAmount,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmount,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmt, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

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

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		final String orderNumberMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(orderNumberMsg);
	}

	/**
	 * Test To verify the disable address cleansing functionality (Allow Vertex Cleansed Address =
	 * FALSE)
	 */
	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisAddressCleansingDisabledTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final String zip9Digit = Address.Berwyn.zip9;
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// Set Allow Vertex Cleansed Address as False
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "False");
		electronicsStorePage.saveElectronicsStoreConfiguration();

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
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate the Address from ShipTo Section of Order Summary Page
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressLine1), "Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(city), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(zip), "Expected Zip is not present in ShipTo Address");
		assertFalse(shipToAddress.contains(zip9Digit),
			"Cleansed Zip is present in ShipTo Address but it shouldn't be present");
		assertTrue(shipToAddress.contains(usCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(state), "Expected State is not present in ShipTo Address");
		String elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Validate the Shipping Address from Shipping Method section of Order Summary Page
		final String shippingAddress = checkoutPage.getShippingAddressFromShippingMethodSection();
		assertTrue(shippingAddress.contains(addressLine1), "Expected AddressLine1 is not present in Shipping Address");
		assertTrue(shippingAddress.contains(city), "Expected City is not present in Shipping Address");
		assertTrue(shippingAddress.contains(zip), "Expected Zip is not present in Shipping Address");
		assertFalse(shippingAddress.contains(zip9Digit),
			"Cleansed Zip is present in Shipping Address but it shouldn't be present");
		assertTrue(shippingAddress.contains(usCountry), "Expected Country is not present in Shipping Address");
		assertTrue(shippingAddress.contains(state), "Expected State is not present in Shipping Address");
		elementMsg = String.format("ShippingAddress is: %s", shippingAddress);
		VertexLogger.log(elementMsg);

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce, menuBaseStore);

		// Set Allow Vertex Cleansed Address as True
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();
	}

	/**
	 * Test To verify address cleansing functionality (Allow Vertex Cleansed Address = TRUE)
	 */
	@Test(groups = { "address_cleansing", "smoke" })
	public void hybrisAddressCleansingEnabledTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String usCountry = Address.Berwyn.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Berwyn.addressLine1;
		final String city = Address.Berwyn.city;
		final String state = Address.Berwyn.state.fullName;
		final String zip = Address.Berwyn.zip5;
		final String zip9Digit = Address.Berwyn.zip9;
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// Set Allow Vertex Cleansed Address as True
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontElementProperty("Allow Vertex Cleansed Address", "True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

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
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Validate the Address from ShipTo Section of Order Summary Page
		final String shipToAddress = checkoutPage.getShipToAddressFromOrderSummary();
		assertTrue(shipToAddress.contains(addressLine1), "Expected AddressLine1 is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(city), "Expected City is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(zip9Digit), "Cleansed Zip is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(usCountry), "Expected Country is not present in ShipTo Address");
		assertTrue(shipToAddress.contains(state), "Expected State is not present in ShipTo Address");
		String elementMsg = String.format("ShipToAddress is: %s", shipToAddress);
		VertexLogger.log(elementMsg);

		// Validate the Shipping Address from Shipping Method section of Order Summary Page
		final String shippingAddress = checkoutPage.getShippingAddressFromShippingMethodSection();
		assertTrue(shippingAddress.contains(addressLine1), "Expected AddressLine1 is not present in Shipping Address");
		assertTrue(shippingAddress.contains(city), "Expected City is not present in Shipping Address");
		assertTrue(shippingAddress.contains(zip9Digit), "Cleansed Zip is not present in Shipping Address");
		assertTrue(shippingAddress.contains(usCountry), "Expected Country is not present in Shipping Address");
		assertTrue(shippingAddress.contains(state), "Expected State is not present in Shipping Address");
		elementMsg = String.format("ShippingAddress is: %s", shippingAddress);
		VertexLogger.log(elementMsg);
	}
}
