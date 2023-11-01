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
 * Tests sales order xml verification
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSalesOrderQuoteXmlVerificationTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;

	/**
	 * Tests sales order xml verification
	 *
	 * @author Shiva Mothkula, Daniel Bondi
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
		final String country = Address.Durham.country.fullName;
		final String state = Address.Durham.state.fullName;
		final String city = Address.Durham.city;
		final String addressLine1 = Address.Durham.addressLine1;
		final String zip = Address.Durham.zip5;
		final String phone = CommonDataProperties.PHONE;
		final String countryBilling = Address.GrandRapids.country.iso2code;

		final String fullAddressStr = String.format("%s %s, %s , %s %s, %s", firstName, lastName, addressLine1, city,
			zip, countryBilling);

		// fill valid credit card details and proceed next
		final String type = CreditCard.TYPE.text;
		final String name = CreditCard.NAME.text;
		final String number = CreditCard.NUMBER.text;
		final String expireMonth = CreditCard.EXPIRY_MONTH.text;
		final String expireYear = CreditCard.EXPIRY_YEAR.text;
		final String code = CreditCard.CODE.text;

		final int quantity = CommonDataProperties.DEFAULT_QUANTITY;

		// login as sitecore admin user
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

		String expectedStatus = SitecoreStatus.GOOD.getText();
		// get the current status and validate with expected
		String actualStatus = oSeriesPage.getVertexOSeriesStatus();

		boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();

		// newly created user login into the storefront
		storeHome = loginAndEmptyShoppingCart(username, password);

		final double canonCamcorderSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.ELECTRONICS,
			SitecoreItem.CanonF100Camcorder, quantity, quantity);

		// verify cart quantity
		int cartQuantity = storeHome.getCartQuantity();
		int expectedCartQty = quantity;
		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding first item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(String.format("Cart Quantity: %s (After adding first item with quantity: %s)", cartQuantity,
			expectedCartQty), getClass());

		final double adobePhotoshop7Subtotal = storeHome.findItemAndAdd(SitecoreItemCategory.COMPUTERS,
			SitecoreItem.AdobePhotoshopElements7, quantity, quantity);

		// verify cart quantity
		cartQuantity = storeHome.getCartQuantity();
		expectedCartQty = expectedCartQty + quantity;

		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding 2nd item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(
			String.format("Cart Quantity: %s (After adding 2nd item with quantity: %s)", cartQuantity, expectedCartQty),
			getClass());

		final double bestGrillingReceipesSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.BOOKS,
			SitecoreItem.BestGrillingRecipes, quantity, quantity);

		// verify cart quantity
		cartQuantity = storeHome.getCartQuantity();
		expectedCartQty = quantity + expectedCartQty;
		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding 3rd item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(
			String.format("Cart Quantity: %s (After adding 3rd item with quantity: %s)", cartQuantity, expectedCartQty),
			getClass());

		final double diamondHeartSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.JEWELRY,
			SitecoreItem.BlackWhiteDiamondHeart, quantity, quantity);

		// verify cart quantity
		cartQuantity = storeHome.getCartQuantity();
		expectedCartQty = quantity + expectedCartQty;
		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding 4th item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(
			String.format("Cart Quantity: %s (After adding 4th item with quantity: %s)", cartQuantity, expectedCartQty),
			getClass());

		// click shopping cart to move to cart page
		storeHome.clickShoppingCartButton();

		// verify first item quantity (canonCamcorder)
		int expectedQuantityCanon = storeHome.getItemQuantity(SitecoreItem.CanonF100Camcorder.getName());
		assertTrue(expectedQuantityCanon == quantity,
			String.format("Expected Item %s quantity: \"%s\", but actual quantity: %s",
				SitecoreItem.CanonF100Camcorder.getName(), expectedQuantityCanon, quantity));
		VertexLogger.log(
			String.format("Item \"%s\" quantity: %s", SitecoreItem.CanonF100Camcorder.getName(), expectedQuantityCanon),
			getClass());

		// verify second item quantity (Adobe Photoshop Elements 7)
		int expectedQuantityAdobe = storeHome.getItemQuantity(SitecoreItem.AdobePhotoshopElements7.getName());
		assertTrue(expectedQuantityAdobe == quantity,
			String.format("Expected Item %s quantity: \"%s\", but actual quantity: %s",
				SitecoreItem.AdobePhotoshopElements7.getName(), expectedQuantityAdobe, quantity));
		VertexLogger.log(String.format("Item \"%s\" quantity: %s", SitecoreItem.AdobePhotoshopElements7.getName(),
			expectedQuantityAdobe), getClass());

		// verify third item quantity (Best Grilling Recipes)
		int expectedQuantityGrill = storeHome.getItemQuantity(SitecoreItem.BestGrillingRecipes.getName());
		assertTrue(expectedQuantityGrill == quantity,
			String.format("Expected Item %s quantity: \"%s\", but actual quantity: %s",
				SitecoreItem.BestGrillingRecipes.getName(), expectedQuantityGrill, quantity));
		VertexLogger.log(String.format("Item \"%s\" quantity: %s", SitecoreItem.BestGrillingRecipes.getName(),
			expectedQuantityGrill), getClass());

		// verify second item quantity (Black & White Diamond Heart)
		int expectedQuantityHeart = storeHome.getItemQuantity(SitecoreItem.BlackWhiteDiamondHeart.getName());
		assertTrue(expectedQuantityHeart == quantity,
			String.format("Expected Item %s quantity: \"%s\", but actual quantity: %s",
				SitecoreItem.BlackWhiteDiamondHeart.getName(), expectedQuantityHeart, quantity));
		VertexLogger.log(String.format("Item \"%s\" quantity: %s", SitecoreItem.BlackWhiteDiamondHeart.getName(),
			expectedQuantityHeart), getClass());

		// checkout items
		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1, zip,
			phone);

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		// select shipping method and proceed next
		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		Pair<SitecoreItemValues, SitecoreItemValues> canonValues = checkoutPage.getValues(
			SitecoreItem.CanonF100Camcorder, quantity);
		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		confirmValues(canonValues, SitecoreItem.CanonF100Camcorder);

		Pair<SitecoreItemValues, SitecoreItemValues> adobePhotoshop7Values = checkoutPage.getValues(
			SitecoreItem.AdobePhotoshopElements7, quantity);
		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		confirmValues(adobePhotoshop7Values, SitecoreItem.AdobePhotoshopElements7);

		Pair<SitecoreItemValues, SitecoreItemValues> grillingReceipesValues = checkoutPage.getValues(
			SitecoreItem.BestGrillingRecipes, quantity);
		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		confirmValues(grillingReceipesValues, SitecoreItem.BestGrillingRecipes);

		Pair<SitecoreItemValues, SitecoreItemValues> diamondHeartValues = checkoutPage.getValues(
			SitecoreItem.BlackWhiteDiamondHeart, quantity);
		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		confirmValues(diamondHeartValues, SitecoreItem.BlackWhiteDiamondHeart);

		// Cart Sub-total
		final double cartSubTotal = canonCamcorderSubtotal + adobePhotoshop7Subtotal + bestGrillingReceipesSubtotal +
									diamondHeartSubtotal;

		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(
			cartSubTotal, state, city);
		confirmCheckOutValues(checkoutValues);

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
