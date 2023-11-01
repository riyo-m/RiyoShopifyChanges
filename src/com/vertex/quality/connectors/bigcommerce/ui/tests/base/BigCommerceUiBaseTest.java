package com.vertex.quality.connectors.bigcommerce.ui.tests.base;

import com.vertex.quality.common.enums.DBConnectorNames;
import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.DBEnvironmentNames;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminEditCustomerPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminHomePage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminSignOnPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminViewCustomersPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.base.BigCommerceAdminPostLoginBasePage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.*;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.base.BigCommerceStorePreCheckoutBasePage;
import com.vertex.quality.connectors.bigcommerce.ui.pojos.BigCommerceUiAddressPojo;
import org.testng.annotations.Test;

/**
 * this class represents BigCommerce base test, contains all the helper methods used in all the test cases.
 *
 * @author osabha ssalisbury
 */
@Test(groups = { "big_commerce" })
public abstract class BigCommerceUiBaseTest extends VertexUIBaseTest<BigCommerceAdminSignOnPage>
{
	//Note- the "Shipping Origin" aka Physical Origin is defined in the admin site in the page "Shipping"
	// under the navigation category "Store Setup"

	protected String adminLoginUrl;

	protected String adminUsername;
	protected String adminPassword;

	@Override
	public BigCommerceAdminSignOnPage loadInitialTestPage( )
	{
		BigCommerceAdminSignOnPage newPage = null;

		try
		{
			EnvironmentInformation environmentInfo = SQLConnection.getEnvironmentInformation(
				DBConnectorNames.BIG_COMMERCE, DBEnvironmentNames.QA, DBEnvironmentDescriptors.BIG_COMMERCE_UI);
			EnvironmentCredentials credentials = SQLConnection.getEnvironmentCredentials(environmentInfo);
			adminLoginUrl = environmentInfo.getEnvironmentUrl();
			adminUsername = credentials.getUsername();
			adminPassword = credentials.getPassword();

			driver.get(adminLoginUrl);

			newPage = new BigCommerceAdminSignOnPage(driver);
		}
		catch ( Exception e )
		{
			VertexLogger.log("failed to set up a browser-based test of the Big Commerce connector",
				VertexLogLevel.ERROR);
			e.printStackTrace();
		}
		return newPage;
	}

	/**
	 * helper method used to sign in to the admin home page.
	 *
	 * @return new instance of the admin home page.
	 */
	protected BigCommerceAdminHomePage signInToHomePage( final BigCommerceAdminSignOnPage signOnPage )
	{
		signOnPage.enterUsername(adminUsername);

		signOnPage.enterPassword(adminPassword);

		BigCommerceAdminHomePage homepage = signOnPage.clickLoginButton();

		return homepage;
	}

	/**
	 * this sets the given customer's tax exempt code to the given value before the test runs
	 *
	 * @param prevPage      the page which the test had loaded before this operation began
	 * @param customerName  the name of the customer whose tax exempt code will change
	 * @param taxExemptCode the new tax exempt code value for that customer
	 *
	 * @return the customer profile management page after the given customer's tax exempt code has been modified
	 */
	protected BigCommerceAdminViewCustomersPage setupCustomerTaxExemptCode(
		final BigCommerceAdminPostLoginBasePage prevPage, final String customerName, final String taxExemptCode )
	{
		BigCommerceAdminViewCustomersPage initCustomersPage = prevPage.navBar.navigateToCustomersPage();

		BigCommerceAdminEditCustomerPage editCustomerPage = initCustomersPage.editCustomerDetails(customerName);
		editCustomerPage.setTaxExemptCode(taxExemptCode);

		BigCommerceAdminViewCustomersPage finalCustomersPage = editCustomerPage.submitEdits();
		finalCustomersPage.exitPageBodyIframe();
		return finalCustomersPage;
	}

	/**
	 * goes to the storefront site, fills the cart with just one item with the given name, and then goes to check out
	 *
	 * @param adminPage   the starting page on the admin site
	 * @param productName the name of the item which should be put in the cart
	 *
	 * @return the checkout page for the order with just that one item
	 */
	protected BigCommerceStoreCheckoutPage checkoutSingleItem( final BigCommerceAdminPostLoginBasePage adminPage,
		final String productName )
	{
		BigCommerceStoreHomePage storeHomePage = adminPage.navBar.navigateToStorefront();

		BigCommerceStorePreCheckoutBasePage preSearchPage = storeHomePage;
		boolean areProductsAlreadyInCart = !storeHomePage.header.isCartEmpty();
		if ( areProductsAlreadyInCart )
		{
			BigCommerceStoreCartPage cartPage = storeHomePage.header.navigateToCartPage();
			cartPage.clearCart();
			preSearchPage = cartPage;
		}

		BigCommerceStoreSearchResultsPage resultsPage = preSearchPage.header.searchForProduct(productName);
		BigCommerceStoreProductPage productPage = resultsPage.openResultProductPage(productName);

		productPage.addItemToCart();
		BigCommerceStoreCheckoutPage checkoutPage = productPage.itemAddedDialog.proceedToCheckoutPage();

		return checkoutPage;
	}

	/**
	 * fills out the different sections of the checkout page with the provided customer information
	 *
	 * @param checkoutPage  the checkout page which is loaded
	 * @param customerEmail the email which the customer is using
	 * @param shipAddress   the address which the customer wants the order shipped to
	 * @param billAddress   the address which the customer's source of payment is associated with
	 */
	protected void fillCheckoutPage( final BigCommerceStoreCheckoutPage checkoutPage, final String customerEmail,
		final BigCommerceUiAddressPojo shipAddress, final BigCommerceUiAddressPojo billAddress )
	{
		checkoutPage.fillAndSubmitCustomerEmail(customerEmail);
		checkoutPage.fillAndSubmitShippingAddress(shipAddress);
		checkoutPage.fillAndSubmitBillingAddress(billAddress);
	}
}
