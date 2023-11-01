package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Bundle-1288
 * Test case for applying the Vertex customer code to a customer
 * and verifying it in an order
 *
 * @author alewis
 */
public class ApplyVertexCustomerCodeTests extends MagentoAdminBaseTest
{
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String firstName = "Naomi";
	protected String lastName = "Nagata";
	protected String email = "naominagata@gmail.com";
	protected String userPassword = "P4ssw0rD";
	protected String stateValue = "12";
	protected String zipCode = "91608";
	protected String countryValue = "US";

	/**
	 * Tests creating a new Californian customer with no code and making an order with that customer
	 * to verify that customer is taxed for the order,
	 * then applying the Vertex customer code to the same customer and making the same order to
	 * verify that customer is now exempt from tax
	 * Delete created customer after test concludes
	 */
	@Test()
	public void applyVertexCustomerCodeTest( )
	{
		verifyCustomer();
		manageAddresses();

		setAccountInformationWithoutCode();
		storefrontLogout();
		checkTaxNotExempt();

		setAccountInformationWithCode();
		storefrontLogout();
		checkTaxExempt();

		deleteCreatedCustomer();
	}

	/**
	 * loads the storefront homepage
	 *
	 * @return storefront homepage
	 */
	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(homepageURL);

		M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);

		String pageTitle = homePage.getPageTitle();
		assertTrue(pageTitle.equals(homePageTitleText));

		return homePage;
	}

	/**
	 * sign into the storefront from the homepage using
	 * an existing account
	 *
	 * @return storefront homepage
	 */
	protected M2StorefrontHomePage storefrontSignIn( )
	{
		M2StorefrontHomePage homepage = openStorefrontHomepage();

		M2StorefrontLoginPage loginPage = homepage.clickSignInButton();

		loginPage.enterUsername(email);

		loginPage.enterPassword(userPassword);

		homepage = loginPage.clickSignInButton();

		return homepage;
	}

	/**
	 * log out of the storefront
	 */
	protected void storefrontLogout( )
	{
		M2StorefrontHomePage homepage = openStorefrontHomepage();

		homepage.logOutOfAccount();
	}

	/**
	 * loads and signs into this configuration site
	 *
	 * @return a representation of the page that loads immediately after
	 * successfully signing into this configuration site
	 */
	protected M2AdminHomepage logIntoMagentoAdmin( )
	{
		driver.get(url);

		M2AdminSignOnPage signOnPage = new M2AdminSignOnPage(driver);

		signOnPage.enterUsername(username);

		signOnPage.enterPassword(password);

		M2AdminHomepage homepage = signOnPage.login();

		return homepage;
	}

	/**
	 * loads this configuration site without logging in
	 * (when reopening site after previously loggin in, will aready be logged in)
	 *
	 * @return a representation of the page that loads immediately after
	 * opening this configuration site
	 */
	protected M2AdminHomepage openMagentoAdmin( )
	{
		driver.get(url);

		M2AdminHomepage homepage = new M2AdminHomepage(driver);

		return homepage;
	}

	/**
	 * tests creating a storefront account from the homepage
	 *
	 * @return storefront my account page for created account
	 */
	protected M2StorefrontMyAccountPage createAnAccount( )
	{
		M2StorefrontHomePage storefrontHomepage = openStorefrontHomepage();

		M2StorefrontCreateAccountPage storefrontCreateAccountPage = storefrontHomepage.clickCreateAnAccountButton();

		storefrontCreateAccountPage.inputFirstName(firstName);
		storefrontCreateAccountPage.inputLastName(lastName);
		storefrontCreateAccountPage.inputEmail(email);
		storefrontCreateAccountPage.inputPassword(userPassword);
		storefrontCreateAccountPage.inputPasswordConfirmation(userPassword);

		M2StorefrontMyAccountPage myAccountPage = storefrontCreateAccountPage.clickCreateAccountButton();

		return myAccountPage;
	}

	/**
	 * tests adding an address for the newly created account
	 *
	 * @return storefront address book page
	 */
	protected M2StorefrontAddressBookPage manageAddresses( )
	{
		String phoneNumber = "6661118888";
		String streetAddress = "100 Universal City Plaza";
		String city = "Universal City";

		M2StorefrontMyAccountPage myAccountPage = createAnAccount();

		M2StorefrontAddOrEditAddressPage addAddressPage = myAccountPage.clickManageAddressesLink();

		addAddressPage.inputPhoneNumber(phoneNumber);
		addAddressPage.inputStreetLine(streetAddress);
		addAddressPage.inputCity(city);
		addAddressPage.selectState(stateValue);
		addAddressPage.inputZipCode(zipCode);
		addAddressPage.selectCountry(countryValue);

		M2StorefrontAddressBookPage addressBookPage = addAddressPage.clickSaveAddressButton();

		return addressBookPage;
	}

	/**
	 * tests logging into the admin page and navigating to the customers
	 * page from the nav panel
	 *
	 * @return admin customers page
	 */
	protected M2AdminCustomersPage loginAndNavigateToCustomersPage( )
	{
		M2AdminHomepage homepage = logIntoMagentoAdmin();

		homepage.closeMessagePopupIfPresent();

		assertTrue(homepage.navPanel.isCustomersButtonVisible());

		homepage.navPanel.clickCustomersButton();

		M2AdminCustomersPage customersPage = homepage.navPanel.clickAllCustomersButton();

		return customersPage;
	}

	/**
	 * tests opening the admin page (already logged into)
	 * and navigating to the customers page from the nav panel
	 *
	 * @return admin customers page
	 */
	protected M2AdminCustomersPage navigateToCustomersPage( )
	{
		M2AdminHomepage homepage = openMagentoAdmin();

		homepage.closeMessagePopupIfPresent();

		assertTrue(homepage.navPanel.isCustomersButtonVisible());

		homepage.navPanel.clickCustomersButton();

		M2AdminCustomersPage customersPage = homepage.navPanel.clickAllCustomersButton();

		return customersPage;
	}

	/**
	 * tests setting the account information for the newly created customer
	 * with NO customer code
	 */
	protected void setAccountInformationWithoutCode( )
	{
		String customerGroupDisplay = "General";
		String ensureCustomerCodeEmpty = "";
		String successMessage = "You saved the customer.";

		M2AdminCustomersPage customersPage = loginAndNavigateToCustomersPage();

		M2AdminCustomerInformationPage customerInformationPage = customersPage.editCustomerByName(firstName, lastName);

		customerInformationPage.clickAccountInformationTab();

		customerInformationPage.selectCustomerGroup(customerGroupDisplay);

		customerInformationPage.inputVertexCustomerCode(ensureCustomerCodeEmpty);

		customersPage = customerInformationPage.clickSaveButton();

		assertTrue(customersPage.checkMessage(successMessage));
	}

	/**
	 * tests setting the account information for the created customer
	 * WITH the Vertex "CAExempt" customer code
	 */
	protected void setAccountInformationWithCode( )
	{
		String customerGroupDisplay = "General";
		String setCustomerCode = "CAExempt";
		String successMessage = "You saved the customer.";

		M2AdminCustomersPage customersPage = navigateToCustomersPage();

		M2AdminCustomerInformationPage customerInformationPage = customersPage.editCustomerByName(firstName, lastName);

		customerInformationPage.clickAccountInformationTab();

		customerInformationPage.selectCustomerGroup(customerGroupDisplay);

		customerInformationPage.inputVertexCustomerCode(setCustomerCode);

		customersPage = customerInformationPage.clickSaveButton();

		assertTrue(customersPage.checkMessage(successMessage));
	}

	/**
	 * tests adding a product to the customer's cart by its SKU code
	 *
	 * @return storefront shopping cart page
	 */
	protected M2StorefrontShoppingCartPage addProductBySku( )
	{
		String sku = "WH12";
		String qty = "1";

		M2StorefrontHomePage homepage = storefrontSignIn();

		homepage.navPanel.emptyCartAndWait();

		M2StorefrontMyAccountPage myAccountPage = homepage.navigateToMyAccount();

		M2StorefrontOrderBySKUPage orderBySKUPage = myAccountPage.clickOrderBySkuButton();

		orderBySKUPage.inputSku(sku);

		orderBySKUPage.inputQuantity(qty);

		M2StorefrontShoppingCartPage shoppingCartPage = orderBySKUPage.clickAddToCartButton();

		return shoppingCartPage;
	}

	/**
	 * tests finalizing the item in the shopping cart after
	 * adding it by its SKU code
	 *
	 * @return storefront shopping cart page
	 */
	protected M2StorefrontShoppingCartPage finalizeShoppingCart( )
	{
		String qty = "1";

		M2StorefrontShoppingCartPage shoppingCartPage = addProductBySku();

		M2StorefrontProductDetailsPage productDetailsPage = shoppingCartPage.clickSpecifyOptions();

		productDetailsPage.selectSizeAndColor();

		productDetailsPage.inputQuantity(qty);

		shoppingCartPage = productDetailsPage.clickUpdateCartButton();

		return shoppingCartPage;
	}

	/**
	 * tests completing the purchase from the shopping cart
	 *
	 * @return storefront payment method page
	 */
	protected M2StorefrontPaymentMethodPage completePurchase( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = finalizeShoppingCart();

		shoppingCartPage.clickEstimateShippingTax();

		shoppingCartPage.selectCountry(countryValue);

		shoppingCartPage.selectState(stateValue);

		shoppingCartPage.enterZipCode(zipCode);

		shoppingCartPage.clickFreeShippingRate();

		shoppingCartPage.clickEstimateShippingTax();

		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		shippingInfoPage.enterFirstName(firstName);

		shippingInfoPage.enterLastName(lastName);

		shippingInfoPage.enterStreetAddress("100 Universal City Plaza");

		shippingInfoPage.enterCity("Universal City");

		shippingInfoPage.enterPhoneNumber(phoneNumber);

		M2StorefrontPaymentMethodPage paymentMethodPage = shippingInfoPage.clickNextButton();

		if (shippingInfoPage.verifyUpdateAddressButton()){
			shippingInfoPage.clickUpdateAddress();
			shippingInfoPage.clickNextButton();
		}

		return paymentMethodPage;
	}

	/**
	 * verifies the tax is not exempt for a customer with
	 * no customer code
	 */
	protected void checkTaxNotExempt( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = completePurchase();

		paymentMethodPage.openTaxBlind();

		double salesUseTaxFound = paymentMethodPage.getSalesUseTax();

		assertTrue(salesUseTaxFound != 0);
	}

	/**
	 * verifies the tax is exempt for a customer with
	 * the Vertex customer code
	 */
	protected void checkTaxExempt( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = completePurchase();

		double taxFound = paymentMethodPage.getTaxNoBlind();

		assertTrue(taxFound == 0);
	}

	/**
	 * deletes the customer created for this test case
	 * from the admin page
	 */
	protected void deleteCreatedCustomer( )
	{
		String message = "A total of 1 record(s) were deleted.";

		M2AdminCustomersPage customersPage = navigateToCustomersPage();

		customersPage.selectCustomerCheckboxByName(firstName, lastName);

		customersPage.selectDeleteAction();

		customersPage.checkMessage(message);
	}

	/**
	 * verifies and deletes the customer to be created for this test case
	 * from the admin page
	 */
	protected void verifyCustomer( )
	{
		String message = "A total of 1 record(s) were deleted.";

		M2AdminCustomersPage customersPage = loginAndNavigateToCustomersPage( );
		M2AdminHomepage homepage = new M2AdminHomepage(driver);
		M2AdminProductsPage productsPage = new M2AdminProductsPage(driver);

		if (customersPage.selectCustomerCheckboxByName(firstName, lastName)){
			customersPage.selectDeleteAction();
			customersPage.checkMessage(message);
		}
		productsPage.clickClearAllButton();
		homepage.clickSignOutAdminPage();
	}
}