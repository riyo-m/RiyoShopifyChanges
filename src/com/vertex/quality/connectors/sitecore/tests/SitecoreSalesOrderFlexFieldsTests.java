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
 * Tests sales order flex fields
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSalesOrderFlexFieldsTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;

	/**
	 * Tests sales order flex fields
	 */
	@Test(groups = "sitecore")
	public void salesOrderTest( )
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
		final String zip = Address.GrandRapids.zip5;
		final String phone = CommonDataProperties.PHONE;
		final String countryBilling = Address.GrandRapids.country.iso2code;

		final String fullAddressStr = String.format("%s %s, %s , %s %s, %s", firstName, lastName, addressLine1, city,
			zip, countryBilling);

		// fill valid credit card details
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
		final String validatinMsg = userManagerPage.createNewUser(username, domain, fulName, email, comment, password,
			roleName);
		assertTrue(validatinMsg.contains("The user has been successfully created"), "User creation failed");

		// verify Vertex O Series Status (it should be in good)
		homePage.navigateToSitecoreHomePage();
		oSeriesPage = homePage.navigateToVertexOSeriesConnectorPage();

		final String expectedStatus = SitecoreStatus.GOOD.getText();
		// get the current status and validate with expected
		final String actualStatus = oSeriesPage.getVertexOSeriesStatus();

		//TODO
		final boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();

		// newly created user login into the storefront
		storeHome = loginAndEmptyShoppingCart(username, password);

		// select category as "Electronics"
		storeHome.selectCategory(SitecoreItemCategory.ELECTRONICS);

		// select item and add to cart
		final double blackBerryPhoneSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.ELECTRONICS,
			SitecoreItem.BlackBerryPhone, quantity, quantity);

		final double eatingWellBookSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.BOOKS,
			SitecoreItem.EatingWellInSeason, quantity, quantity);

		final double leatherBagSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.APPAREL_SHOES,
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets, quantity, quantity);

		final double compaqPentium4DesktopSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.COMPUTERS,
			SitecoreItem.CompaqPentium4Desktop, quantity, quantity);

		final double diamondBraceletSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.JEWELRY,
			SitecoreItem.DiamondTennisBracelet, quantity, quantity);

		// verify cart quantity
		final int expectedCartQuantity = 5;
		final int cartQuantity = storeHome.getCartQuantity();
		assertTrue(expectedCartQuantity == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding first item)",
				expectedCartQuantity, cartQuantity));
		VertexLogger.log(String.format("Cart Quantity: %s (After adding first item with quantity: %s)", cartQuantity,
			expectedCartQuantity), getClass());

		// click shopping cart to move to cart page
		storeHome.clickShoppingCartButton();

		// set text field value for first item (i.e. BlackBerry Phone)
		final String phoneTextFieldValue = "12345AAAAA12345BBBBB12345CCCCC12345DDDDDZZZZZ";
		storeHome.setItemTextFlexField(SitecoreItem.BlackBerryPhone.getName(), phoneTextFieldValue);

		final String eatingWellBookNumberFieldValue = "0.1234567890";
		storeHome.setItemNumberFlexField(SitecoreItem.EatingWellInSeason.getName(), eatingWellBookNumberFieldValue);

		final String leatherBagDateFieldValue = "12/12/2012";
		storeHome.setItemDateFlexField(SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets.getName(),
			leatherBagDateFieldValue);

		final String desktopNumberFieldValue = "12345.678901";
		storeHome.setItemNumberFlexField(SitecoreItem.CompaqPentium4Desktop.getName(), desktopNumberFieldValue);

		final String diamondBraceletNumberFieldValue = "-8.25";
		storeHome.setItemNumberFlexField(SitecoreItem.DiamondTennisBracelet.getName(), diamondBraceletNumberFieldValue);

		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1, zip,
			phone);

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		final Pair<SitecoreItemValues, SitecoreItemValues> diamondBraceletValues = checkoutPage.getValues(
			SitecoreItem.DiamondTennisBracelet, quantity);

		confirmValues(diamondBraceletValues, SitecoreItem.DiamondPaveEarrings);

		final double expectedSubTotal = blackBerryPhoneSubtotal + eatingWellBookSubtotal + leatherBagSubtotal +
										compaqPentium4DesktopSubtotal + diamondBraceletSubtotal;

		final Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(
			expectedSubTotal, state, city);

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
