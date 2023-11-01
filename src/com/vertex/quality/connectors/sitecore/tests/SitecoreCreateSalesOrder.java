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
 * Tests sales orders and go to cart and confirm values
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreCreateSalesOrder extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;

	/**
	 * Tests sales orders and go to cart and confirm values
	 */
	@Test(groups = "sitecore")
	public void createSalesOrderGoToCartTest( )
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

		// fill valid credit card details and proceed next
		final String type = CreditCard.TYPE.text;
		final String name = CreditCard.NAME.text;
		final String number = CreditCard.NUMBER.text;
		final String expireMonth = CreditCard.EXPIRY_MONTH.text;
		final String expireYear = CreditCard.EXPIRY_YEAR.text;
		final String code = CreditCard.CODE.text;

		// set billing address
		final String country = Address.NewYork.country.fullName;
		final String state = Address.NewYork.state.fullName;
		final String city = Address.NewYork.city;
		final String addressLine1 = Address.NewYork.addressLine1;
		final String zip = Address.NewYork.zip5;
		final String phone = CommonDataProperties.PHONE;
		final String countryBilling = Address.NewYork.country.iso2code;

		final String fullAddressStr = String.format("%s %s, %s , %s %s, %s", firstName, lastName, addressLine1, city,
			zip, countryBilling);

		final int quantity = CommonDataProperties.DEFAULT_QUANTITY;

		int totalQuantity = 0;

		// LOGIN AS SITECORE ADMINISTRATOR USER
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

		boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();

		// newly created user login into the store-front
		storeHome = loginAndEmptyShoppingCart(username, password);

		storeHome.findItemAndAdd(SitecoreItemCategory.JEWELRY, SitecoreItem.DiamondPaveEarrings, quantity, quantity);

		totalQuantity += quantity;

		// click shopping cart to move to cart page
		storeHome.clickShoppingCartButton();

		// verify cart quantity after adding the item
		int cartQuantity = storeHome.getCartQuantity();

		assertTrue(cartQuantity == quantity,
			String.format("Expected Cart quantity: %s, but actual quantity: %s", quantity, cartQuantity));

		VertexLogger.log(String.format("Shopping Cart quanity: %s", cartQuantity), getClass());

		Pair<SitecoreItemValues, SitecoreItemValues> diamondVals = storeHome.getValues(SitecoreItem.DiamondPaveEarrings,
			quantity);

		confirmValues(diamondVals, SitecoreItem.DiamondPaveEarrings);

		//get unit price
		final double unitprice = Double.parseDouble(SitecoreItem.DiamondPaveEarrings.getPrice());

		// verify cart total
		double cartTotal = storeHome.getCartTotal();
		final double expectedPrice = quantity * unitprice;
		final double expectedTotal = expectedPrice;
		assertTrue(Double.compare(cartTotal, expectedTotal) == 0,
			String.format("Expected Cart Total: \"%s\", but actual Total: %s", cartTotal, expectedTotal));
		VertexLogger.log(String.format("Cart Total: %s", cartTotal), getClass());

		// update quantity from 1 to 3
		final int updatedQuantity = 3;
		storeHome.updateItemQuantity(SitecoreItem.DiamondPaveEarrings.getName(), Integer.toString(updatedQuantity));

		totalQuantity = updatedQuantity;

		// verify item total
		final double updatedItemTotal = updatedQuantity * unitprice;
		final double actualItemTotal = storeHome.getItemTotal(SitecoreItem.DiamondPaveEarrings.getName());
		assertTrue(Double.compare(actualItemTotal, updatedItemTotal) == 0,
			String.format("Expected Item %s Total: \"%s\", but actual Total: %s",
				SitecoreItem.DiamondPaveEarrings.getName(), actualItemTotal, updatedItemTotal));
		VertexLogger.log(
			String.format("Item \"%s\" Total: %s", SitecoreItem.DiamondPaveEarrings.getName(), actualItemTotal),
			getClass());

		// verify cart total
		final double updatedCartTotal = updatedItemTotal;
		cartTotal = storeHome.getCartTotal();
		assertTrue(Double.compare(cartTotal, updatedCartTotal) == 0,
			String.format("Expected Cart Total: \"%s\", but actual Total: %s", cartTotal, updatedCartTotal));
		VertexLogger.log(String.format("Cart Total: %s", cartTotal), getClass());

		// checkout the item(s)
		checkoutPage = storeHome.clickCheckoutButton();

		//set address
		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1, zip,
			phone);

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		//select shipping method and payment method
		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_AIR, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		//fill out credit details
		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		// verify product summary (i.e. Unit price, Quantity & Item total amount)
		Pair<SitecoreItemValues, SitecoreItemValues> diamondValsCheckout = checkoutPage.getValues(
			SitecoreItem.DiamondPaveEarrings, totalQuantity);

		//confirmValues
		confirmValues(diamondValsCheckout, SitecoreItem.DiamondPaveEarrings);

		//checkout values
		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(
			updatedItemTotal, state, city);

		//confirm checkout values
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
