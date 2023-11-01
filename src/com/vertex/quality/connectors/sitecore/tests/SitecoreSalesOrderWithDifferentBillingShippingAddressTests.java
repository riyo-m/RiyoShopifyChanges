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

import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Tests sales order with different billing and shipping address
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSalesOrderWithDifferentBillingShippingAddressTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;

	/**
	 * Tests sales order with different billing and shipping address
	 */
	@Test(groups = "sitecore")
	public void salesOrderWithDifferentBillingShippingAddressTest( )
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
		final String country = Address.NewYork.country.fullName;
		final String state = Address.NewYork.state.fullName;
		final String city = Address.NewYork.city;
		final String addressLine1 = Address.NewYork.addressLine1;
		final String zip = Address.NewYork.zip5;
		final String phone = CommonDataProperties.PHONE;

		// set shipping address
		final String shipState = Address.Anaheim.state.fullName;
		final String shipCity = Address.Anaheim.city;
		final String shipAddressLine1 = Address.Anaheim.addressLine1;
		final String shipZip = Address.Anaheim.zip5;

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

		boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();

		// newly created user login into the storefront
		storeHome = loginAndEmptyShoppingCart(username, password);

		storeHome.findItemAndAdd(SitecoreItemCategory.ELECTRONICS, SitecoreItem.BlackBerryPhone, quantity, quantity);

		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1, zip,
			phone);

		// add the shipping address (should not be same as billing address)
		checkoutPage.shippingAddress.selectAddress("New Address");

		checkoutPage.shippingAddress.setAddress(lastName, firstName, email, country, shipState, shipCity,
			shipAddressLine1, shipZip, phone);

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		Map<String, String> billingAddressMap = checkoutPage.orderConfirmation.getBillingAddress();
		VertexLogger.log(String.format("Billing Address Details are as follows...\n%s", billingAddressMap), getClass());

		Map<String, String> shippingAddressMap = checkoutPage.orderConfirmation.getShippingAddress();
		VertexLogger.log(String.format("Shipping Address Details are as follows...\n%s", shippingAddressMap),
			getClass());

		final double unitPrice = Double.parseDouble(SitecoreItem.BlackBerryPhone.getPrice());
		final double expectedPrice = quantity * unitPrice;
		final double subtotal = expectedPrice;

		// verify product summary (i.e. Unit price, Quantity & Item total amount)
		final Pair<SitecoreItemValues, SitecoreItemValues> blackBerryValues = checkoutPage.getValues(
			SitecoreItem.BlackBerryPhone, quantity);

		confirmValues(blackBerryValues, SitecoreItem.BlackBerryPhone);

		final Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(
			subtotal, state, city);

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
