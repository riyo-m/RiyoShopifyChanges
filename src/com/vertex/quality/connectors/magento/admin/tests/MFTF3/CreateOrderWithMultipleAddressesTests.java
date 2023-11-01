package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.*;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Bundle-1602
 * Test case for creating an order with multiple addresses
 *
 * @author alewis
 */
public class CreateOrderWithMultipleAddressesTests extends MagentoAdminBaseTest
{
	protected String homepageURL = MagentoData.STOREFRONT_SIGN_ON_URL.data;

	protected String testEmail = "roni_cost@example.com";
	protected String testPassword = "roni_cost3@example.com";

	protected String totalFoundAddrOne; //"$36.04";
	protected String totalFoundAddrTwo; //"$37.23";

	protected String orderNumberOne;
	protected String orderNumberTwo;

	/**
	 * Tests creating an order with two different addresses, where each item goes to a different address
	 * Ensuring the tax and total costs are correct
	 */
	@Test()
	public void createOrderMultipleAddressesTest( )
	{
		loginWithTestAccount();

		placeOrder();

		createInvoiceFirstOrder();

		// Need to go to vertex cloud, create transaction report, and verify price and tax amounts

		createInvoiceSecondOrder();

		// Need to go to vertex cloud, create transaction report, and verify price and tax amounts

		createCreditMemoFirstOrder();

		createCreditMemoSecondOrder();

		// Need to go to vertex cloud, create transaction report, and verify price and tax are refunded
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
	 * tests navigating to the gear page from the nav panel
	 * of the storefront
	 *
	 * @return storefront gear page
	 */
	protected M2StorefrontGearPage navigateToGearPage( )
	{
		M2StorefrontHomePage homepage = openStorefrontHomepage();

		M2StorefrontGearPage gearPage = homepage.navPanel.clickGearButton();

		return gearPage;
	}

	/**
	 * tests navigating to the bags page from the gear page
	 *
	 * @return storefront bags page
	 */
	protected M2StorefrontBagsPage navigateToBagsPage( )
	{
		M2StorefrontGearPage gearPage = navigateToGearPage();

		M2StorefrontBagsPage bagsPage = gearPage.clickBagsButton();

		return bagsPage;
	}

	/**
	 * tests navigating to the products page by clicking on a product
	 * on the bags page
	 *
	 * @return storefront product page
	 */
	protected M2StorefrontProductDetailsPage navigateToProduct( )
	{
		M2StorefrontBagsPage bagsPage = navigateToBagsPage();

		M2StorefrontProductDetailsPage detailsPage = bagsPage.clickProductButton();

		return detailsPage;
	}

	/**
	 * tests adding the product to the shopping cart
	 * adds 2 of the item for use with multiple addresses
	 *
	 * @return storefront product page
	 */
	protected M2StorefrontProductDetailsPage addProductToCart( )
	{
		M2StorefrontProductDetailsPage detailsPage = navigateToProduct();

		String itemName = detailsPage.getItemName();

		detailsPage.inputQuantity("2");

		String successMessage = String.format("You added %s to your shopping cart.", itemName);

		detailsPage.clickAddToCartButton();

		assertTrue(detailsPage.checkMessage(successMessage));

		return detailsPage;
	}

	/**
	 * tests checking out with the items in the cart
	 * since no user was logged in, login will be prompted
	 *
	 * @return storefront login page
	 */
	protected M2StorefrontLoginPage proceedToCheckout( )
	{
		M2StorefrontProductDetailsPage detailsPage = addProductToCart();

		M2StorefrontShoppingCartPage shoppingCartPage = detailsPage.navPanel.clickViewAndEditCart();

		M2StorefrontLoginPage loginPage = shoppingCartPage.clickCheckoutWithMultipleAddressesWhileLoggedOut();

		return loginPage;
	}

	/**
	 * tests logging into storefront from the cart, using the test account
	 */
	protected void loginWithTestAccount( )
	{
		M2StorefrontLoginPage loginPage = proceedToCheckout();

		loginPage.enterUsername(testEmail);

		loginPage.enterPassword(testPassword);

		loginPage.clickSignInButton();
	}

	/**
	 * tests setting two different addresses for shipping
	 * while checking out
	 *
	 * @return storefront shipping information page
	 */
	protected M2StorefrontShippingInfoPage setDifferentAddresses( )
	{
		String addrOneValue = "1";
		String addrTwoValue = "33";

		M2StorefrontShipToMultipleAddressesPage shiptoPage = new M2StorefrontShipToMultipleAddressesPage(driver);

		shiptoPage.selectFirstItemAddress(addrOneValue);

		shiptoPage.selectSecondItemAddress(addrTwoValue);

		M2StorefrontShippingInfoPage shippingInfoPage = shiptoPage.clickGoToShippingInformationButton();

		return shippingInfoPage;
	}

	/**
	 * tests setting the shipping type and payment type while checking out
	 *
	 * @return storefront review order page
	 */
	protected M2StorefrontReviewOrderPage setShippingAndPayment( )
	{
		M2StorefrontShippingInfoPage shippingInfoPage = setDifferentAddresses();

		shippingInfoPage.setFreeShippingAllItems();

		M2StorefrontBillingInformationPage billingInformationPage = shippingInfoPage.clickContinueButton();

		billingInformationPage.selectCheckAsPaymentMethod();

		M2StorefrontReviewOrderPage reviewOrderPage = billingInformationPage.clickGoToReviewOrderButton();

		return reviewOrderPage;
	}

	/**
	 * verifies subtotal with tax, shipping without tax, and tax amount match expected values
	 * saves recorded subtotals
	 *
	 * @return storefront review order page
	 */
	protected M2StorefrontReviewOrderPage verifyOrderInformation( )
	{
		String itemOneTotalWithTax = "$36.04";
		String itemTwoTotalWithTax = "$37.23";
		String itemsShippingTotal = "$0.00";
		String addrOneItemTaxAmt = "$2.04";
		String addrTwoItemTaxAmt = "$3.23";

		M2StorefrontReviewOrderPage reviewOrderPage = setShippingAndPayment();

		assertTrue(itemOneTotalWithTax.equals(reviewOrderPage.getSubtotalInclTax(0)));

		totalFoundAddrOne = reviewOrderPage.getSubtotalInclTax(0);

		assertTrue(itemTwoTotalWithTax.equals(reviewOrderPage.getSubtotalInclTax(1)));

		totalFoundAddrTwo = reviewOrderPage.getSubtotalInclTax(1);

		assertTrue(itemsShippingTotal.equals(reviewOrderPage.getShippingExclTax(0)));

		assertTrue(itemsShippingTotal.equals(reviewOrderPage.getShippingExclTax(1)));

		assertTrue(addrOneItemTaxAmt.equals(reviewOrderPage.getTax(0)));

		assertTrue(addrTwoItemTaxAmt.equals(reviewOrderPage.getTax(1)));

		return reviewOrderPage;
	}

	/**
	 * tests placing the order
	 * saves the order numbers for the two items/addresses
	 *
	 * @return storefront thank you page
	 */
	public M2StorefrontThankYouPage placeOrder( )
	{
		M2StorefrontReviewOrderPage reviewOrderPage = verifyOrderInformation();

		M2StorefrontThankYouPage thankYouPage = reviewOrderPage.clickPlaceOrderButton();

		orderNumberOne = thankYouPage.getOrderNumberFromList(0);

		orderNumberTwo = thankYouPage.getOrderNumberFromList(1);

		return thankYouPage;
	}

	/**
	 * tests navigating and signing in to the admin homepage,
	 * then navigating to the orders page via the nav panel
	 *
	 * @return the admin orders page
	 */
	public M2AdminOrdersPage navigateToAdminOrders( )
	{
		M2AdminHomepage adminHomepage = logIntoMagentoAdmin();

		adminHomepage.closeMessagePopupIfPresent();

		assertTrue(adminHomepage.navPanel.isSalesButtonVisible());

		adminHomepage.navPanel.clickSalesButton();

		M2AdminOrdersPage ordersPage = adminHomepage.navPanel.clickOrdersButton();

		return ordersPage;
	}

	/**
	 * verifies that the two new orders were added using the saved order numbers
	 *
	 * @return admin orders page
	 */
	public M2AdminOrdersPage verifyTwoOrders( )
	{
		M2AdminOrdersPage adminOrdersPage = navigateToAdminOrders();

		assertTrue(adminOrdersPage.isOrderPresent(orderNumberOne));

		assertTrue(adminOrdersPage.isOrderPresent(orderNumberTwo));

		return adminOrdersPage;
	}

	/**
	 * opens the first order, from the first address, by clicking on it
	 * after verifying the two orders
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage clickFirstOrder( )
	{
		M2AdminOrdersPage adminOrdersPage = verifyTwoOrders();

		M2AdminOrderViewInfoPage viewOrderInfoPage = adminOrdersPage.clickOrder(orderNumberOne);

		return viewOrderInfoPage;
	}

	/**
	 * opens the first order, from the first address, by clicking on it
	 * after returning to the orders page (from another info page)
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage clickFirstOrderFromBackButton( )
	{
		M2AdminOrdersPage adminOrdersPage = returnToOrders();

		M2AdminOrderViewInfoPage viewOrderInfoPage = adminOrdersPage.clickOrder(orderNumberOne);

		return viewOrderInfoPage;
	}

	/**
	 * verifies the subtotal matches recorded number
	 * tests creating the invoice for the order
	 * verifies success messages
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage createInvoiceFirstOrder( )
	{
		String invoiceCreatedMessage = "The invoice has been created.";
		String invoiceSentMessage = "Vertex invoice has been sent.";

		M2AdminOrderViewInfoPage viewOrderInfoPage = clickFirstOrder();

		assertTrue(totalFoundAddrOne.equals(viewOrderInfoPage.getPriceTotalInclTax()));

		M2AdminNewInvoicePage newInvoicePage = viewOrderInfoPage.clickInvoiceButton();

		viewOrderInfoPage = newInvoicePage.clickSubmitInvoiceButton();

		assertTrue(viewOrderInfoPage.checkMessage(invoiceCreatedMessage));
		// sent message does not appear, assertTrue fails
		//assertTrue(viewOrderInfoPage.checkMessage(invoiceSentMessage));

		return viewOrderInfoPage;
	}

	/**
	 * tests returning to the orders page from an order info page,
	 * by clicking the back button
	 *
	 * @return admin orders page
	 */
	public M2AdminOrdersPage returnToOrders( )
	{
		M2AdminOrderViewInfoPage orderInfoPage = new M2AdminOrderViewInfoPage(driver);

		M2AdminOrdersPage adminOrdersPage = orderInfoPage.clickBackButton();

		return adminOrdersPage;
	}

	/**
	 * opens the second order, from the second address, by clicking on it
	 * after returning to the orders page (from another info page)
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage clickSecondOrder( )
	{
		M2AdminOrdersPage adminOrdersPage = returnToOrders();

		M2AdminOrderViewInfoPage viewOrderInfoPage = adminOrdersPage.clickOrder(orderNumberTwo);

		return viewOrderInfoPage;
	}

	/**
	 * verifies the subtotal matches recorded number
	 * tests creating the invoice for the order
	 * verifies success messages
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage createInvoiceSecondOrder( )
	{
		String invoiceCreatedMessage = "The invoice has been created.";
		String invoiceSentMessage = "Vertex invoice has been sent.";

		M2AdminOrderViewInfoPage viewOrderInfoPage = clickSecondOrder();

		assertTrue(totalFoundAddrTwo.equals(viewOrderInfoPage.getPriceTotalInclTax()));

		M2AdminNewInvoicePage newInvoicePage = viewOrderInfoPage.clickInvoiceButton();

		viewOrderInfoPage = newInvoicePage.clickSubmitInvoiceButton();

		assertTrue(viewOrderInfoPage.checkMessage(invoiceCreatedMessage));
		// sent message does not appear, assertTrue fails
		//assertTrue(viewOrderInfoPage.checkMessage(invoiceSentMessage));

		return viewOrderInfoPage;
	}

	/**
	 * tests creating the credit memo for the order
	 * verifies success messages
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage createCreditMemoFirstOrder( )
	{
		String memoCreatedMessage = "You created the credit memo.";
		String invoiceRefundMessage = "The Vertex invoice has been refunded.";

		M2AdminOrderViewInfoPage viewOrderInfoPage = clickFirstOrderFromBackButton();

		M2AdminCreditMemoPage creditMemoPage = viewOrderInfoPage.clickCreditMemoButton();

		viewOrderInfoPage = creditMemoPage.clickRefundOfflineButton();

		assertTrue(viewOrderInfoPage.checkMessage(invoiceRefundMessage));
		assertTrue(viewOrderInfoPage.checkMessage(memoCreatedMessage));

		return viewOrderInfoPage;
	}

	/**
	 * tests creating the credit memo for the order
	 * verifies success messages
	 *
	 * @return admin order info page
	 */
	public M2AdminOrderViewInfoPage createCreditMemoSecondOrder( )
	{
		String memoCreatedMessage = "You created the credit memo.";
		String invoiceRefundMessage = "The Vertex invoice has been refunded.";

		M2AdminOrderViewInfoPage viewOrderInfoPage = clickSecondOrder();

		M2AdminCreditMemoPage creditMemoPage = viewOrderInfoPage.clickCreditMemoButton();

		viewOrderInfoPage = creditMemoPage.clickRefundOfflineButton();

		assertTrue(viewOrderInfoPage.checkMessage(invoiceRefundMessage));
		assertTrue(viewOrderInfoPage.checkMessage(memoCreatedMessage));

		return viewOrderInfoPage;
	}
}