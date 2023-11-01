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
 * Tests sales order with invalid address
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSalesOrderWithInvalidAddressTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;

	/**
	 * Tests sales order with invalid address
	 */
	@Test(groups = "sitecore")
	public void salesOrderWithInvalidAddressTest( )
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
		final String country = Address.InvalidAddress.country.fullName;
		final String state = Address.InvalidAddress.state.fullName;
		final String city = Address.InvalidAddress.city;
		final String addressLine1 = Address.InvalidAddress.addressLine1;
		final String zip = Address.InvalidAddress.zip5;
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

		final double unitPrice = Double.parseDouble(SitecoreItem.EatingWellInSeason.getPrice());
		final double expectedPrice = quantity * unitPrice;
		final double subtotal = expectedPrice;

		storeHome.findItemAndAdd(SitecoreItemCategory.BOOKS, SitecoreItem.EatingWellInSeason, quantity, quantity);

		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1, zip,
			phone);

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		final Pair<SitecoreItemValues, SitecoreItemValues> eatingWellValues = checkoutPage.getValues(
			SitecoreItem.EatingWellInSeason, quantity);
		confirmValues(eatingWellValues, SitecoreItem.EatingWellInSeason);

		final Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(
			subtotal, state, city);
		confirmCheckOutValues(checkoutValues);

		// confirm order
		String orderNumber = checkoutPage.orderConfirmation.clickConfirmButton();
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
