package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminCustomersPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewProductPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminProductsPage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontPaymentMethodPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontProductDetailsPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontShippingInfoPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontShoppingCartPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Bundle-1300
 *
 * @author alewis
 */
public class CreateOrderWithGroupedProdTests extends MagentoAdminBaseTest
{

	String groupedProductURL = "https://test-ee-latest.vrtx-m2.mediotype.ninja/grouped1.html";

	@Test()
	public void checkConfigCorrectTest( )
	{
		verifyDeleteCreatedProduct();

		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToOrderNumberPageAdmin();

		double exclTaxString = paymentMethodPage.getTotalExclTax();
		double inclTaxString = paymentMethodPage.getTotalInclTax();

		double difference = inclTaxString / exclTaxString;
		String slicedDifferenceString = String.format("%.2f", difference);

		verifyDeleteCreatedProduct();

		assertTrue(slicedDifferenceString.equals("1.10"));
	}

	protected M2AdminNewProductPage navigateToNewProductsPage( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();

		homepage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = homepage.navPanel.clickProductsButton();

		M2AdminNewProductPage newProductPage = productsPage.clickAddProductButtonPickType("Grouped Product");

		newProductPage.fillOutRequiredField("grouped1", "grouped1sku", false, "");

		newProductPage.clickCategoriesButton();

		newProductPage.clickAddProductToGroup();

		newProductPage.clickSaveButton();

		homepage.clickSignOutAdminPage();

		return newProductPage;
	}

	protected M2StorefrontProductDetailsPage putInGroupedProduct( )
	{
		driver.get(groupedProductURL);

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		detailsPage.clickAddToCartButton();

		detailsPage.clickCartButton();

		return detailsPage;
	}

	/**
	 * navigates to Shopping Cart page and enter order address, opens tax blind
	 *
	 * @return the Shopping Cart page
	 */
	protected M2StorefrontShoppingCartPage navigateToShoppingCartPage( )
	{
		M2StorefrontProductDetailsPage productDetailsPage = putInGroupedProduct();

		M2StorefrontShoppingCartPage shoppingCartPage = productDetailsPage.clickViewAndEditButton();

		return shoppingCartPage;
	}

	/**
	 * navigates to Shopping Cart page and enter order address, opens tax blind
	 *
	 * @return the Shopping Cart page
	 */
	protected M2StorefrontShoppingCartPage navigateToShoppingCartPageAndAddAddress( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPage();

		shoppingCartPage.clickEstimateShippingTax();
		shoppingCartPage.selectState("12");
		shoppingCartPage.enterZipCode("91608");
		shoppingCartPage.clickShippingRateSelector();
		shoppingCartPage.openTaxBlind();

		return shoppingCartPage;
	}

	/**
	 * navigates to Payment Method page
	 *
	 * @return the Payment Method page
	 */
	protected M2StorefrontPaymentMethodPage navigateToPaymentMethodPage( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		shippingInfoPage.enterEmail("groupCustomerTest@gmail.com");

		shippingInfoPage.enterFirstName("Group");

		shippingInfoPage.enterLastName("Customer");

		shippingInfoPage.enterStreetAddress("100 Universal City Plaza");

		shippingInfoPage.enterCity("Universal City");

		shippingInfoPage.enterPhoneNumber(phoneNumber);

		M2StorefrontPaymentMethodPage paymentMethod = shippingInfoPage.clickNextButton();

		if (shippingInfoPage.verifyUpdateAddressButton()){
			shippingInfoPage.clickUpdateAddress();
			shippingInfoPage.clickNextButton();
		}

		paymentMethod.openTaxBlind();

		return paymentMethod;
	}

	/**
	 * navigates to Order Number page
	 *
	 * @return the Order Number page
	 */
	protected M2StorefrontPaymentMethodPage navigateToOrderNumberPageAdmin( )
	{
		navigateToNewProductsPage();

		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		//M2StorefrontThankYouPage thankYouPage = paymentMethodPage.clickPlaceOrderButton();

		//String orderNumber = thankYouPage.getOrderNumberGuest( );

		//		M2AdminOrdersPage ordersPage = navigateToOrders();
		//
		//		M2AdminOrderViewInfoPage orderViewInfoPage = ordersPage.clickOrder(orderNumber);

		return paymentMethodPage;
	}

	/**
	 * delete the created product
	 *
	 */
	protected void verifyDeleteCreatedProduct( )
	{
		M2AdminCustomersPage customersPage = new M2AdminCustomersPage(driver);
		M2AdminHomepage homepage = signInToAdminHomepage();

		homepage.navPanel.clickCatalogButton();

		M2AdminProductsPage productsPage = homepage.navPanel.clickProductsButton();

		String message = "A total of 1 record(s) were deleted.";

		productsPage.searchProduct("grouped1");
		if (productsPage.selectProductCheckboxByName("grouped1")){
			productsPage.selectDeleteAction();
			customersPage.checkMessage(message);
		}
		productsPage.clickClearAllButton();
		homepage.clickSignOutAdminPage();
	}
}