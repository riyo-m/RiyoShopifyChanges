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
 * Tests sales order creation and update physical origin
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreUpdatePhysicalOriginAddressSOCreationTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreContentEditorPage contentEditorPage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;

	/**
	 * Tests sales order creation and update physical origin
	 */
	@Test(groups = "sitecore")
	public void sOCreationUSAPhysicalOriginAddressCanadaShipToAddressTest( )
	{

		final String firstName = CommonDataProperties.FIRST_NAME;
		final String lastName = CommonDataProperties.LAST_NAME;

		// set company details
		final String companyName = this.COMPANY_NAME;
		final String address1 = Address.Washington.addressLine1;
		final String address2 = Address.Washington.addressLine2;
		final String companyCity = Address.Washington.city;
		final String region = Address.Washington.state.abbreviation;
		final String postalCode = Address.Washington.zip5;
		final String country = Address.Washington.country.fullName;

		// new user creation
		final String username = this.USERNAME;
		final String password = this.PASSWORD;
		final String roleName = this.USER_ROLE;
		final String domain = SitecoreDomain.COMMERCE_USERS.getText();
		final String fulName = CommonDataProperties.FULL_NAME;
		final String email = CommonDataProperties.EMAIL;
		final String comment = CommonDataProperties.COMMENT;

		// set billing address
		final String countryBilling = Address.Anaheim.country.fullName;
		final String state = Address.Anaheim.state.fullName;
		final String city = Address.Anaheim.city;
		final String addressLine1 = Address.Anaheim.addressLine1;
		final String zip = Address.Anaheim.zip5;
		final String phone = CommonDataProperties.PHONE;
		final String countryIso2 = Address.Anaheim.country.iso2code;

		// fill valid credit card details and proceed next
		final String type = CreditCard.TYPE.text;
		final String name = CreditCard.NAME.text;
		final String number = CreditCard.NUMBER.text;
		final String expireMonth = CreditCard.EXPIRY_MONTH.text;
		final String expireYear = CreditCard.EXPIRY_YEAR.text;
		final String code = CreditCard.CODE.text;

		// set company details
		final String companyNameSecond = this.COMPANY_NAME;
		final String address1Second = this.ADDRESS_1;
		final String address2Second = this.ADDRESS_2;
		final String companyCitySecond = this.COMPANY_CITY;
		final String regionSecond = this.COMPANY_REGION;
		final String postalCodeSecond = this.COMPANY_ZIP;
		final String countrySecond = this.COUNTRY;

		final String fullAddressStr = String.format("%s %s, %s , %s %s, %s", firstName, lastName, addressLine1, city,
			zip, countryIso2);

		final int quantity = CommonDataProperties.DEFAULT_QUANTITY;

		// login as admin user into Sitecore admin site
		homePage = logInAsAdminUser();

		// navigate to Content Editor Page
		contentEditorPage = homePage.navigateToContentEditorPage();

		//navigate to vertex settings
		SitecoreVertexSettingsContentPage vertexSettingsPage = contentEditorPage.navigateToVertexSettingsContentPage();

		//set company detials
		vertexSettingsPage.setCompanyDetails(companyName, address1, address2, companyCity, region, postalCode, country);

		//save company detials
		contentEditorPage.saveCompanyDetails();

		// navigate to home page
		homePage.navigateToSitecoreHomePage();

		// Navigate to User Manager page
		userManagerPage = homePage.navigateToUserManagerPage();

		// delete the user if already exist
		userManagerPage.deleteUserIfExist(username, domain);

		// create a new user and validate
		final String validatingMsg = userManagerPage.createNewUser(username, domain, fulName, email, comment, password,
			roleName);
		assertTrue(validatingMsg.contains("The user has been successfully created"), "User creation failed");

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

		double subtotal = storeHome.findItemAndAdd(SitecoreItemCategory.JEWELRY, SitecoreItem.DiamondTennisBracelet,
			quantity, quantity);

		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, countryBilling, state, city, addressLine1,
			zip, phone);

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(fullAddressStr);

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);

		Pair<SitecoreItemValues, SitecoreItemValues> diamondBrackletValues = checkoutPage.getValues(
			SitecoreItem.DiamondTennisBracelet, quantity);

		confirmValues(diamondBrackletValues, SitecoreItem.DiamondTennisBracelet);

		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> checkoutValues = checkoutPage.verifyOrderValues(subtotal,
			state, city);

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
