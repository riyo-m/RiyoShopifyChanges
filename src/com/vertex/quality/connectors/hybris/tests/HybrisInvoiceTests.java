package com.vertex.quality.connectors.hybris.tests;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.enums.CreditCard;
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

import static org.testng.Assert.assertTrue;

/**
 * Test to validate Invoice Request by setting Tax Invoice as False
 *
 * @author Nagaraju Gampa
 */
public class HybrisInvoiceTests extends HybrisBaseTest
{
	/**
	 * Test to validate Invoice Request by setting Tax Invoice as False
	 */
	@Test(groups = { "invoice", "smoke" })
	public void hybrisInvoiceRequestTest( )
	{
		// =================Data declarations=================================
		final String menuBaseCommerce = HybrisBONavTreeOptions.BASE_COMMERCE.getMenuName();
		final String menuBaseStore = HybrisBONavTreeOptions.BASE_STORE.getMenuName();
		final String menuVertex = HybrisBONavTreeOptions.VERTEX.getMenuName();
		final String menuVertexConfiguration = HybrisBONavTreeOptions.VERTEX_CONFIGURATION.getMenuName();
		final String tabVertexCustomization = HybrisBOBaseStoreTabOptions.VERTEX_CUSTOMIZATION.getoption();
		final String powershotProductId = HybrisProductIds.POWERSHOTID.getproductID();
		final String usCountry = Address.Anaheim.country.fullName;
		final String titleMr = CommonDataProperties.TITLE;
		final String fName = CommonDataProperties.FIRST_NAME;
		final String lName = CommonDataProperties.LAST_NAME;
		final String addressLine1 = Address.Anaheim.addressLine1;
		final String city = Address.Anaheim.city;
		final String state = Address.Anaheim.state.fullName;
		final String zip = Address.Anaheim.zip5;
		final String expShippingMethod = HybrisShipmentMethods.STANDARD.name;
		final String cardType = CreditCard.TYPE.text;
		final String cardName = CreditCard.NAME.text;
		final String cardNumber = CreditCard.NUMBER.text;
		final String expMonth = CreditCard.EXPIRY_MONTH.text;
		final String expYear = CreditCard.EXPIRY_YEAR.text;
		final String verificationNumber = CreditCard.CODE.text;
		final float expectedSubTotal = HybrisTestData.SUBTOTAL_AMOUNT;
		final float expectedDeliveryAmt = HybrisTestData.DELIVERY_AMOUNT;
		final float expectedTaxAmt = HybrisTestData.TAX_AMOUNT1;
		float expectedOrderTotalIncludeTax = expectedSubTotal + expectedDeliveryAmt + expectedTaxAmt;
		expectedOrderTotalIncludeTax = VertexCurrencyUtils.getDecimalFormatAmount(expectedOrderTotalIncludeTax);

		// =================Script Implementation=================================
		// login as Backoffice user into Hybris-Backoffice Page
		HybrisBOHomePage boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		HybrisBOBaseStorePage electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce,
			menuBaseStore);

		// set Electronic Store Default Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.setStorefrontExternalTaxCalculation("True");
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);

		electronicsStorePage.setStorefrontEnableTaxInvoicing("False");
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
		checkoutPage.fillShippingAddressDetails(usCountry, titleMr, fName, lName, addressLine1, city, state, zip);
		checkoutPage.clickDeliveryAddressNext();

		// Get and Validate SubTotal, Delivery Amount, Tax and Order Total from Delivery Method page
		float actualSubTotalAmount = checkoutPage.getSubtotal();
		assertTrue(actualSubTotalAmount == expectedSubTotal,
			"ActualSubTotalAmount is not matching with ExpectedSubTotalAmount");

		float actualDeliveryAmount = checkoutPage.getDeliveryAmount();
		assertTrue(actualDeliveryAmount == expectedDeliveryAmt,
			"ActualDeliveryAmount is not matching with ExpectedDeliveryAmount");

		float actualTaxAmount = checkoutPage.getTaxAmount();
		assertTrue(actualTaxAmount == expectedTaxAmt, "ActualTaxAmount is not matching with ExpectedTaxAmount");

		float actualOrderTotalIncludeTax = checkoutPage.getOrderTotalAmount();
		assertTrue(actualOrderTotalIncludeTax == expectedOrderTotalIncludeTax,
			"ActualOrderTotalIncludeTax is not matching with ExpectedOrderTotalIncludeTax");

		// Get and Validate selected Shipping Method
		final String actualShippingMethod = checkoutPage.getShippingMethod();
		assertTrue(expShippingMethod.equalsIgnoreCase(actualShippingMethod),
			"ActualShippingMethod is not matching with ExpectedShippingMethod");
		checkoutPage.clickDeliveryMethodNext();

		// Fill Payment Details and Proceed to Checkout
		checkoutPage.fillPaymentDetails(cardType, cardName, cardNumber, expMonth, expYear, verificationNumber);
		checkoutPage.enableUseDeliveryAddress();
		checkoutPage.clickpaymentBillingAddressNext();

		// Enable Terms And Conditions and Place Order
		checkoutPage.enableTermsConditions();
		final HybrisEStoreOrderConfirmationPage orderConfirmPage = checkoutPage.placeOrder();

		// Get order Number
		final String orderNumber = orderConfirmPage.getOrderNumber();
		assertTrue(orderNumber != null, "Order Number not available '/' Order Number is Blank");
		final String elementMsg = String.format("Order Number is: %s", orderNumber);
		VertexLogger.log(elementMsg);

		/*
		 * //TODO - Place Holder to Implement Logging Functionality after moving all logs to AWS
		 * //login as Admin user into Hybris-Admin Page
		 * HybrisAdminHomePage adminhomepage = logInAsAdminUser();
		 * //navigate to Support Page
		 * boHomePage.navigateToSupportPage(menuPlatform, subMenuSupport);
		 */

		// login as Backoffice user into Hybris-Backoffice Page
		boHomePage = loginBOUser();

		// navigate to BaseStore - Electronic Store Page
		electronicsStorePage = boHomePage.navigateToElectronicsStorePage(menuBaseCommerce, menuBaseStore);

		// set Electronic Store Configuration
		electronicsStorePage.selectElectronicsStore();
		electronicsStorePage.selectTabFromElectronicsStore(tabVertexCustomization);
		electronicsStorePage.setStorefrontEnableTaxInvoicing("True");
		electronicsStorePage.saveElectronicsStoreConfiguration();

		// Logout from Back office
		boHomePage.logoutFromBackOffice();
	}
}
