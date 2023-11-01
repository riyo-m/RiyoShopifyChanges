package com.vertex.quality.connectors.sitecore.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.sitecore.common.enums.*;
import com.vertex.quality.connectors.sitecore.pages.SitecoreAdminHomePage;
import com.vertex.quality.connectors.sitecore.pages.SitecoreUserManagerPage;
import com.vertex.quality.connectors.sitecore.pages.SitecoreVertexOSeriesConnectorPage;
import com.vertex.quality.connectors.sitecore.pages.store.SitecoreCheckoutPage;
import com.vertex.quality.connectors.sitecore.pages.store.SitecoreStorefrontHomePage;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreCheckoutAmount;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreItemValues;
import com.vertex.quality.connectors.sitecore.tests.base.SitecoreBaseTest;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests sales order creation and change quantity
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSOCreationChangQuantityShippingAddressTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;

	/**
	 * Tests sales order creation and change quantity
	 */
	@Test(groups = "sitecore")
	public void salesOrderCreationChangeQuantityAndShippingAddressTest( )
	{
		final String firstName = CommonDataProperties.FIRST_NAME;
		final String lastName = CommonDataProperties.LAST_NAME;

		final String username = this.USERNAME;
		final String password = this.PASSWORD;
		final String roleName = this.USER_ROLE;
		final String domain = SitecoreDomain.COMMERCE_USERS.getText();
		final String fulName = CommonDataProperties.FULL_NAME;
		final String email = CommonDataProperties.EMAIL;
		final String comment = CommonDataProperties.COMMENT;

		// set billing address
		final String country = Address.GrandRapids.country.fullName;
		final String state = Address.GrandRapids.state.fullName;
		final String city = Address.GrandRapids.city;
		final String addressLine1 = Address.GrandRapids.addressLine1;
		final String addressLine2 = "";
		final String zip = Address.GrandRapids.zip5;
		final String phone = CommonDataProperties.PHONE;
		final String countryBilling = Address.GrandRapids.country.iso2code;

		// fill valid credit card details and proceed next
		final String type = CreditCard.TYPE.text;
		final String name = CreditCard.NAME.text;
		final String number = CreditCard.NUMBER.text;
		final String expireMonth = CreditCard.EXPIRY_MONTH.text;
		final String expireYear = CreditCard.EXPIRY_YEAR.text;
		final String code = CreditCard.CODE.text;

		// set shipping address
		final String shippingState = Address.Washington.state.fullName;
		final String shippingCity = Address.Washington.city;
		final String shippingAddressLine1 = Address.Washington.addressLine1;
		final String shippingAddressLine2 = Address.Washington.addressLine2;
		final String shippingZip = Address.Washington.zip5;

		// select shipping address (same as billing address)
		final String fullAddressStr = String.format("%s %s, %s , %s %s, %s", firstName, lastName, addressLine1, city,
			zip, countryBilling);

		// login as sitecore user admin
		homePage = logInAsAdminUser();

		// Navigate to User Manager page
		userManagerPage = homePage.navigateToUserManagerPage();

		// delete the user if already exist
		userManagerPage.deleteUserIfExist(username, domain);

		// create a new user and validate
		final String validateMsg = userManagerPage.createNewUser(username, domain, fulName, email, comment, password,
			roleName);
		assertTrue(validateMsg.contains("The user has been successfully created"), "User creation failed");

		// verify Vertex O Series Status (it should be in good)
		homePage.navigateToSitecoreHomePage();
		oSeriesPage = homePage.navigateToVertexOSeriesConnectorPage();

		final String expectedStatus = SitecoreStatus.GOOD.getText();
		// get the current status and validate with expected
		final String actualStatus = oSeriesPage.getVertexOSeriesStatus();

		final boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();

		// newly created user login into the storefront
		storeHome = loginAndEmptyShoppingCart(username, password);

		final double blackBerryUnitPrice = Double.parseDouble(SitecoreItem.BlackBerryPhone.getPrice());
		final int blackBerryQuantity = 5;
		final int blackBerryFinalQuantity = 2;

		storeHome.findItemAndAdd(SitecoreItemCategory.ELECTRONICS, SitecoreItem.BlackBerryPhone, blackBerryQuantity,
			blackBerryQuantity);

		// verify cart quantity
		int cartQuantity = storeHome.getCartQuantity();
		assertTrue(blackBerryQuantity == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding first item)",
				blackBerryQuantity, cartQuantity));
		VertexLogger.log(String.format("Cart Quantity: %s (After adding first item with quantity: %s)", cartQuantity,
			blackBerryQuantity), getClass());

		final double leatherBagUnitPrice = Double.parseDouble(
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets.getPrice());
		final int leatherBagQuantity = 3;
		final int leatherBagFinalQuantity = 2;

		double blackBerrySubtotal = blackBerryQuantity * blackBerryUnitPrice;
		double leatherBagSubtotal = leatherBagQuantity * leatherBagUnitPrice;

		storeHome.findItemAndAdd(SitecoreItemCategory.APPAREL_SHOES,
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets, leatherBagQuantity, leatherBagQuantity);

		// verify cart quantity
		cartQuantity = storeHome.getCartQuantity();
		int expectedCartQty = blackBerryQuantity + leatherBagQuantity;
		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding 2nd item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(String.format("Cart Quantity: %s (After adding 2nd item with quantity: %s)", cartQuantity,
			leatherBagQuantity), getClass());

		// click shopping cart to move to cart page
		storeHome.clickShoppingCartButton();

		// verify first item quantity
		int expectedBlackBerryPhoneQuantity = storeHome.getItemQuantity(SitecoreItem.BlackBerryPhone.getName());
		assertTrue(expectedBlackBerryPhoneQuantity == blackBerryQuantity,
			String.format("Expected Item %s quantity: \"%s\", but actual quantity: %s",
				SitecoreItem.BlackBerryPhone.getName(), blackBerryQuantity, expectedBlackBerryPhoneQuantity));
		VertexLogger.log(String.format("Item \"%s\" quantity: %s", SitecoreItem.BlackBerryPhone.getName(),
			expectedBlackBerryPhoneQuantity), getClass());

		// verify second item quantity
		int expectedLeatherBagQuantity = storeHome.getItemQuantity(
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets.getName());
		assertTrue(expectedLeatherBagQuantity == leatherBagQuantity,
			String.format("Expected Item %s quantity: \"%s\", but actual quantity: %s",
				SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets.getName(), leatherBagQuantity,
				expectedLeatherBagQuantity));
		VertexLogger.log(String.format("Item \"%s\" quantity: %s",
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets.getName(), expectedLeatherBagQuantity),
			getClass());

		// checkout items
		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1, zip,
			phone);
		checkoutPage.clickNextButton();

		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		final String shipFirstName = checkoutPage.billingAddress.getFirstName();
		assertTrue(firstName.equalsIgnoreCase(shipFirstName),
			String.format("Expected First Name: %s, but actual First Name: %s", firstName, shipFirstName));
		VertexLogger.log(String.format("Expected and actual First Name are same (i.e. %s)", firstName), getClass());

		final String shipLastName = checkoutPage.billingAddress.getLastName();
		assertTrue(lastName.equalsIgnoreCase(shipLastName),
			String.format("Expected Last Name: %s, but actual Last Name: %s", lastName, shipLastName));
		VertexLogger.log(String.format("Expected and actual Last Name are same (i.e. %s)", lastName), getClass());

		final String shipline1 = checkoutPage.billingAddress.getAddressLine1();
		assertTrue(addressLine1.equalsIgnoreCase(shipline1),
			String.format("Expected Address Line1: %s, but actual Address Line1: %s", addressLine1, shipline1));
		VertexLogger.log(String.format("Expected and actual Address Line1 are same (i.e. %s)", addressLine1),
			getClass());

		final String shipline2 = checkoutPage.billingAddress.getAddressLine2();
		assertTrue(addressLine2.equalsIgnoreCase(shipline2),
			String.format("Expected Address Line2: %s, but actual Address Line2: %s", addressLine2, shipline2));
		VertexLogger.log(String.format("Expected and actual Address Line2 are same (i.e. %s)", shipline2), getClass());

		final String shipState = checkoutPage.billingAddress.getState();
		assertTrue(state.equalsIgnoreCase(shipState),
			String.format("Expected State: %s, but actual State: %s", state, shipState));
		VertexLogger.log(String.format("Expected and actual State are same (i.e. %s)", state), getClass());

		final String shipCity = checkoutPage.billingAddress.getCity();
		assertTrue(city.equalsIgnoreCase(shipCity),
			String.format("Expected City: %s, but actual City: %s", city, shipCity));
		VertexLogger.log(String.format("Expected and actual City are same (i.e. %s)", city), getClass());

		final String shipZip = checkoutPage.billingAddress.getZipCode();
		assertTrue(zip.equalsIgnoreCase(shipZip), String.format("Expected ZIP: %s, but actual ZIP: %s", zip, shipZip));
		VertexLogger.log(String.format("Expected and actual ZIP code are same (i.e. %s)", zip), getClass());

		final String shipCountry = checkoutPage.billingAddress.getCountry();
		assertTrue(country.equalsIgnoreCase(shipCountry),
			String.format("Expected Country: %s, but actual Countrty: %s", country, shipCountry));
		VertexLogger.log(String.format("Expected and actual Country are same (i.e. %s)", country), getClass());

		checkoutPage.clickNextButton();

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		Pair<SitecoreItemValues, SitecoreItemValues> blackBerryValues = checkoutPage.getValues(
			SitecoreItem.BlackBerryPhone, blackBerryQuantity);
		confirmValues(blackBerryValues, SitecoreItem.BlackBerryPhone);

		Pair<SitecoreItemValues, SitecoreItemValues> leatherBagValues = checkoutPage.getValues(
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets, leatherBagQuantity);
		confirmValues(leatherBagValues, SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets);

		double cartSubTotal = leatherBagSubtotal + blackBerrySubtotal;

		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(
			cartSubTotal, state, city);
		confirmCheckOutValues(checkoutValues);

		storeHome.selectCategory(SitecoreItemCategory.APPAREL_SHOES);
		// click shopping cart to move to cart page
		storeHome.clickShoppingCartButton();

		// update both items quantity
		storeHome.updateItemQuantity(SitecoreItem.BlackBerryPhone.getName(), Integer.toString(blackBerryFinalQuantity));
		storeHome.updateItemQuantity(SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets.getName(),
			Integer.toString(leatherBagFinalQuantity));

		// checkout items
		checkoutPage = storeHome.clickCheckoutButton();

		// select billing address
		checkoutPage.billingAddress.selectAddress(fullAddressStr);

		checkoutPage.shippingAddress.setAddress(firstName, lastName, email, country, shippingState, shippingCity,
			shippingAddressLine1, shippingAddressLine2, shippingZip, phone);

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_AIR, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		// fill valid credit card details and proceed next
		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		// amount)
		blackBerrySubtotal = blackBerryFinalQuantity * blackBerryUnitPrice;

		blackBerryValues = checkoutPage.getValues(SitecoreItem.BlackBerryPhone, blackBerryQuantity);
		confirmValues(blackBerryValues, SitecoreItem.BlackBerryPhone);

		// verify product summary of second item (i.e. Unit price, Quantity & Item total
		// amount)
		leatherBagSubtotal = leatherBagFinalQuantity * leatherBagUnitPrice;

		leatherBagValues = checkoutPage.getValues(SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets,
			leatherBagQuantity);
		confirmValues(leatherBagValues, SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets);

		cartSubTotal = leatherBagSubtotal + blackBerrySubtotal;

		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutPageValues = checkoutPage.verifyOrderValues(
			cartSubTotal, state, city);
		confirmCheckOutValues(checkoutPageValues);

		// confirm order
		final String orderNumber = checkoutPage.orderConfirmation.clickConfirmButton();
		assertTrue(orderNumber != null, "Order number is null");
		VertexLogger.log(String.format("Sales Order Number: %s", orderNumber), getClass());

		// logout from store-front application
		storeHome.storeLogout();

		// delete the above created user in admin site
		logInAsAdminUser();

		homePage.navigateToUserManagerPage();

		userManagerPage.deleteUser(username, domain);
	}
}
