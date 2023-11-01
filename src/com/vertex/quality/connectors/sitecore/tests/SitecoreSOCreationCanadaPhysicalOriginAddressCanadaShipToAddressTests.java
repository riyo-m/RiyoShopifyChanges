package com.vertex.quality.connectors.sitecore.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.sitecore.common.enums.*;
import com.vertex.quality.connectors.sitecore.pages.*;
import com.vertex.quality.connectors.sitecore.pages.store.SitecoreCheckoutPage;
import com.vertex.quality.connectors.sitecore.pages.store.SitecoreStorefrontHomePage;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreCheckoutAmount;
import com.vertex.quality.connectors.sitecore.pojos.SitecoreItemValues;
import com.vertex.quality.connectors.sitecore.tests.base.SitecoreBaseTest;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests sales order creation with canada address
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSOCreationCanadaPhysicalOriginAddressCanadaShipToAddressTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreContentEditorPage contentEditorPage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;

	/**
	 * Tests sales order creation with canada address
	 */
	@Test(groups = "sitecore")
	public void updatePhysicalOriginAddressAndShipToCanadaAddressSOCreationTests( )
	{

		final String firstName = CommonDataProperties.FIRST_NAME;
		final String lastName = CommonDataProperties.LAST_NAME;

		// new user creation
		final String username = this.USERNAME;
		final String password = this.PASSWORD;
		final String roleName = this.USER_ROLE;
		final String domain = SitecoreDomain.COMMERCE_USERS.getText();
		final String fulName = CommonDataProperties.FULL_NAME;
		final String email = CommonDataProperties.EMAIL;
		final String comment = CommonDataProperties.COMMENT;

		// set company details
		final String companyName = this.COMPANY_NAME;
		final String address1 = "1250 Rene Levesque Blvd. West";
		final String address2 = "Suite 3715";
		final String companyCity = "Montreal";
		final String region = "QC";
		final String postalCode = "h4B 4W8";
		final String country = "Canada";

		// set company details
		final String companyNameSecond = this.COMPANY_NAME;
		final String address1Second = this.ADDRESS_1;
		final String address2Second = this.ADDRESS_2;
		final String companyCitySecond = this.COMPANY_CITY;
		final String regionSecond = this.COMPANY_REGION;
		final String postalCodeSecond = this.COMPANY_ZIP;
		final String countrySecond = this.COUNTRY;

		// set billing address
		final String countryBilling = Address.Toronto.country.fullName;
		final String state = Address.Toronto.state.fullName;
		final String city = Address.Toronto.city;
		final String addressLine1 = Address.Toronto.addressLine1;
		final String addressLine2 = Address.Toronto.addressLine2;
		final String zip = Address.Toronto.zip5;
		final String phone = CommonDataProperties.PHONE;
		final String countryIso2 = Address.Toronto.country.iso2code;

		// fill valid credit card details and proceed next
		final String type = CreditCard.TYPE.text;
		final String name = CreditCard.NAME.text;
		final String number = CreditCard.NUMBER.text;
		final String expireMonth = CreditCard.EXPIRY_MONTH.text;
		final String expireYear = CreditCard.EXPIRY_YEAR.text;
		final String code = CreditCard.CODE.text;

		final String fullAddressStr = String.format("%s %s, %s , %s %s, %s", firstName, lastName, addressLine1, city,
			zip, countryIso2);

		final int quantity = 5;

		// login as admin user into Sitecore admin site
		homePage = logInAsAdminUser();

		// navigate to Content Editor Page
		contentEditorPage = homePage.navigateToContentEditorPage();

		SitecoreVertexSettingsContentPage vertexSettingsPage = contentEditorPage.navigateToVertexSettingsContentPage();

		vertexSettingsPage.setCompanyDetails(companyName, address1, address2, companyCity, region, postalCode, country);

		contentEditorPage.saveCompanyDetails();

		// navigate to home page
		homePage.navigateToSitecoreHomePage();

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

		final double compaqP4DesktopUnitPrice = Double.parseDouble(SitecoreItem.CompaqPentium4Desktop.getPrice());
		final double compaqP4DesktopSubtotal = quantity * compaqP4DesktopUnitPrice;

		storeHome.findItemAndAdd(SitecoreItemCategory.COMPUTERS, SitecoreItem.CompaqPentium4Desktop, quantity,
			quantity);

		// verify cart quantity
		int cartQuantity = storeHome.getCartQuantity();
		int expectedCartQty = quantity;
		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding 1st item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(String.format("Cart Quantity: %s (After adding %s item with quantity: %s)", cartQuantity,
			SitecoreItem.CompaqPentium4Desktop, quantity), getClass());

		final double diamondHeartUnitPrice = Double.parseDouble(SitecoreItem.BlackWhiteDiamondHeart.getPrice());
		final double diamondHeartSubtotal = quantity * diamondHeartUnitPrice;

		storeHome.findItemAndAdd(SitecoreItemCategory.JEWELRY, SitecoreItem.BlackWhiteDiamondHeart, quantity, quantity);

		// verify cart quantity
		cartQuantity = storeHome.getCartQuantity();
		expectedCartQty = quantity + expectedCartQty;
		assertTrue(expectedCartQty == cartQuantity,
			String.format("Expected Cart quanity: %s, but actual Cart quantity: %s (after adding 2nd item)",
				expectedCartQty, cartQuantity));
		VertexLogger.log(String.format("Cart Quantity: %s (After adding %s item with quantity: %s)", cartQuantity,
			SitecoreItem.BlackWhiteDiamondHeart, quantity), getClass());

		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, countryBilling, state, city, addressLine1,
			addressLine2, zip, phone);

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		Pair<SitecoreItemValues, SitecoreItemValues> diamondHeartValues = checkoutPage.getValues(
			SitecoreItem.BlackWhiteDiamondHeart, quantity);
		Pair<SitecoreItemValues, SitecoreItemValues> compaqValues = checkoutPage.getValues(
			SitecoreItem.CompaqPentium4Desktop, quantity);
		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		// amount)

		confirmValues(diamondHeartValues, SitecoreItem.BlackWhiteDiamondHeart);
		confirmValues(compaqValues, SitecoreItem.CompaqPentium4Desktop);

		// Cart Sub-total
		double cartSubTotal = compaqP4DesktopSubtotal + diamondHeartSubtotal;

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

		// navigate to home page
		homePage.navigateToSitecoreHomePage();

		// navigate to Content Editor Page
		contentEditorPage = homePage.navigateToContentEditorPage();

		vertexSettingsPage = contentEditorPage.navigateToVertexSettingsContentPage();

		vertexSettingsPage.setCompanyDetails(companyNameSecond, address1Second, address2Second, companyCitySecond,
			regionSecond, postalCodeSecond, countrySecond);

		contentEditorPage.saveCompanyDetails();

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();
	}
}
