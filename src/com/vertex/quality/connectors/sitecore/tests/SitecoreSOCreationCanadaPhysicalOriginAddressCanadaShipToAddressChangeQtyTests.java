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
 * Tests sales order creation with canada address and change quantity of item
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreSOCreationCanadaPhysicalOriginAddressCanadaShipToAddressChangeQtyTests extends SitecoreBaseTest
{

	SitecoreAdminHomePage homePage;
	SitecoreContentEditorPage contentEditorPage;
	SitecoreUserManagerPage userManagerPage;
	SitecoreVertexOSeriesConnectorPage oSeriesPage;
	SitecoreStorefrontHomePage storeHome;
	SitecoreCheckoutPage checkoutPage;

	/**
	 * Tests sales order creation with canada address and change quantity of item
	 */
	@Test(groups = "sitecore")
	public void updatePhysicalOriginAddressToCanadaSOCreationTests( )
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
		final String counrty = "Canada";

		// set company details
		final String companyNameSecond = this.COMPANY_NAME;
		final String address1Second = this.ADDRESS_1;
		final String address2Second = this.ADDRESS_2;
		final String companyCitySecond = this.COMPANY_CITY;
		final String regionSecond = this.COMPANY_REGION;
		final String postalCodeSecond = this.COMPANY_ZIP;
		final String counrtySecond = this.COUNTRY;

		// set billing address
		final String country = Address.Vancouver.country.iso2code;
		final String state = Address.Vancouver.province.fullName;
		final String city = Address.Vancouver.city;
		final String addressLine1 = Address.Vancouver.addressLine1;
		final String addressLine2 = Address.Vancouver.addressLine2;
		final String zip = Address.Vancouver.zip5;
		final String phone = CommonDataProperties.PHONE;

		// fill valid credit card details and proceed next
		final String type = CreditCard.TYPE.text;
		final String name = CreditCard.NAME.text;
		final String number = CreditCard.NUMBER.text;
		final String expireMonth = CreditCard.EXPIRY_MONTH.text;
		final String expireYear = CreditCard.EXPIRY_YEAR.text;
		final String code = CreditCard.CODE.text;

		// login as admin user into Sitecore admin site
		homePage = logInAsAdminUser();
		// navigate to Content Editor Page
		contentEditorPage = homePage.navigateToContentEditorPage();

		SitecoreVertexSettingsContentPage vertexSettingsPage = contentEditorPage.navigateToVertexSettingsContentPage();

		vertexSettingsPage.setCompanyDetails(companyName, address1, address2, companyCity, region, postalCode, counrty);

		contentEditorPage.saveCompanyDetails();

		// navigate to home page
		homePage.navigateToSitecoreHomePage();

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

		final boolean result = actualStatus
			.toUpperCase()
			.contains(expectedStatus.toUpperCase());
		assertTrue(result, "Vertex O Series Connector status is not valid/good");

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();

		// newly created user login into the storefront
		storeHome = loginAndEmptyShoppingCart(username, password);

		final double canonF100CamcorderSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.ELECTRONICS,
			SitecoreItem.CanonF100Camcorder, 5, 5);
		final double diamondBraceletSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.JEWELRY,
			SitecoreItem.DiamondTennisBracelet, 5, 10);
		final double skilletRecipesSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.BOOKS,
			SitecoreItem.BestSkilletRecipes, 5, 15);
		final double leatherBagSubtotal = storeHome.findItemAndAdd(SitecoreItemCategory.APPAREL_SHOES,
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets, 5, 20);

		checkoutPage = storeHome.clickCheckoutButton();

		checkoutPage.billingAddress.setAddress(firstName, lastName, email, country, state, city, addressLine1,
			addressLine2, zip, phone);
		checkoutPage.clickNextButton();

		// select the shipping address same as billing address
		checkoutPage.shippingAddress.selectAddress(firstName, lastName, city, addressLine1, addressLine2, zip, country);
		checkoutPage.clickNextButton();

		checkoutPage.selectShippingAndPaymentMethod(SitecoreShippingMethod.SHIP_ITEMS,
			SitecoreShippingMethod.ShipItems.BY_GROUND, SitecorePaymentMethod.PAY_CARD,
			SitecorePaymentMethod.PayCard.CREDIT_CARD);

		checkoutPage.paymentInfo.fillCreditCardDetails(type, name, number, expireMonth, expireYear, code);
		checkoutPage.clickNextButton();

		// verify product summary of first item (i.e. Unit price, Quantity & Item total
		// amount)
		final Pair<SitecoreItemValues, SitecoreItemValues> canonVals = checkoutPage.getValues(
			SitecoreItem.CanonF100Camcorder, 5);
		final Pair<SitecoreItemValues, SitecoreItemValues> diamondVals = checkoutPage.getValues(
			SitecoreItem.DiamondTennisBracelet, 5);
		final Pair<SitecoreItemValues, SitecoreItemValues> skilletVals = checkoutPage.getValues(
			SitecoreItem.BestSkilletRecipes, 5);
		final Pair<SitecoreItemValues, SitecoreItemValues> hangbagVals = checkoutPage.getValues(
			SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets, 5);

		confirmValues(canonVals, SitecoreItem.CanonF100Camcorder);
		confirmValues(diamondVals, SitecoreItem.DiamondTennisBracelet);
		confirmValues(skilletVals, SitecoreItem.BestSkilletRecipes);
		confirmValues(hangbagVals, SitecoreItem.GenuineLeatherHandbagCellPhoneHolderMoneyPockets);

		// Cart Sub-total
		final double cartSubTotal = canonF100CamcorderSubtotal + diamondBraceletSubtotal + skilletRecipesSubtotal +
									leatherBagSubtotal;

		Pair<SitecoreCheckoutAmount, SitecoreCheckoutAmount> cartValues = checkoutPage.verifyOrderValues(cartSubTotal,
			state, city);
		confirmCheckOutValues(cartValues);

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
			regionSecond, postalCodeSecond, counrtySecond);
		contentEditorPage.saveCompanyDetails();

		// admin logout
		homePage.navigateToSitecoreHomePage();
		homePage.clickLogoutLink();
	}
}
