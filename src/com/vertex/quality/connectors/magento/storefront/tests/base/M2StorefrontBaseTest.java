package com.vertex.quality.connectors.magento.storefront.tests.base;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.common.tests.MagentoBaseTest;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import org.testng.annotations.Test;

/**
 * currently just stores constants & utility functions that are used by many or
 * all tests
 *
 * @author alewis
 */
@Test()
public abstract class M2StorefrontBaseTest extends MagentoBaseTest
{
	protected String url = MagentoData.STOREFRONT_SIGN_ON_URL.data;
	protected String username = MagentoData.STOREFRONT_USERNAME.data;
	protected String password = MagentoData.STOREFRONT_PASSWORD.data;
	protected String hoodieURL = MagentoData.STOREFRONT_HERO_HOODIE_URL.data;
	protected String homePageTitleText = "Home Page";

	protected String PA_Number = "51";
	protected String zipCode = "19063";

	/**
	 * Strings enter for Shipping Info Page
	 */
	protected String email = "aaron.lewis@vertexinc.com";
	protected String firstName = "Bob";
	protected String lastName = "Saget";
	protected String streetAddress = "525 N Allen St";
	protected String city = "State College";
	protected String phoneNumber = "111-111-1111";

	/**
	 * Opens Magento Storefront
	 *
	 * @return Magento Storefront Homepage
	 */
	protected M2StorefrontHomePage openStorefrontHomepage( )
	{
		driver.get(MagentoData.MAGENTO_QA_2_0_STORE.data);

		M2StorefrontHomePage homePage = new M2StorefrontHomePage(driver);

		homePage.refreshPage();

		return homePage;
	}

	/**
	 * Signs into Magento Storefront
	 *
	 * @return Magento Storefront Homepage
	 */
	protected M2StorefrontHomePage signInToStorefront( )
	{
		M2StorefrontHomePage homePage = openStorefrontHomepage();

		M2StorefrontLoginPage loginPage = homePage.clickSignInButton();

		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		M2StorefrontHomePage signedInHomepage = loginPage.clickSignInButton();

		return signedInHomepage;
	}

	/**
	 * Navigation on Magento Storefront to Product Details Page as Guest
	 *
	 * @return Magento Storefront Product Details Page
	 */
	protected M2StorefrontProductDetailsPage openProductPageGuest( )
	{
		M2StorefrontHomePage homePage = openStorefrontHomepage();

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		driver.get(hoodieURL);

		detailsPage.selectSizeAndColorSmall();

		detailsPage.clickAddToCartButton();

		clickLogoButton();
		clearShoppingCart();

		M2StorefrontGearPage gearPage = homePage.navPanel.clickGearButton();

		M2StorefrontBagsPage bagsPage = gearPage.clickBagsButton();

		M2StorefrontProductDetailsPage productPage = bagsPage.clickProductButton();

		productPage.clickAddToCartButton();

		productPage.clickCartButton();

		return productPage;
	}

	/**
	 * Navigation on Magento Storefront to Product Details Page as existing customer
	 *
	 * @return Magento Storefront Product Details Page
	 */
	protected M2StorefrontProductDetailsPage openProductPageExisCust( )
	{
		M2StorefrontHomePage homePage = signInToStorefront();

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		driver.get(hoodieURL);

		detailsPage.selectSizeAndColor();

		detailsPage.clickAddToCartButton();

		clickLogoButton();
		clearShoppingCart();

		M2StorefrontGearPage gearPage = homePage.navPanel.clickGearButton();

		M2StorefrontBagsPage bagsPage = gearPage.clickBagsButton();

		M2StorefrontProductDetailsPage productPage = bagsPage.clickProductButton();

		productPage.clickAddToCartButton();

		productPage.clickCartButton();

		return productPage;
	}

	/**
	 * Navigation on Magento Storefront to Shopping Cart Page as guest
	 *
	 * @return Magento Storefront Shopping Cart Page
	 */
	protected M2StorefrontShoppingCartPage openShoppingCartPageGuest( )
	{
		M2StorefrontProductDetailsPage productPage = openProductPageGuest();

		M2StorefrontShoppingCartPage shoppingCartPage = productPage.clickViewAndEditButton();

		shoppingCartPage.clickEstimateShippingTax();

		shoppingCartPage.selectState(PA_Number);

		shoppingCartPage.enterZipCode(zipCode);

		shoppingCartPage.clickShippingRateSelector();

		shoppingCartPage.openTaxBlind();

		return shoppingCartPage;
	}

	/**
	 * Navigation on Magento Storefront to Shopping Cart Page as existing customer
	 *
	 * @return Magento Storefront Shopping Cart Page
	 */
	protected M2StorefrontShoppingCartPage openShoppingCartPageExisCust( )
	{
		M2StorefrontProductDetailsPage productPage = openProductPageExisCust();

		M2StorefrontShoppingCartPage shoppingCartPage = productPage.clickViewAndEditButton();

		shoppingCartPage.clickEstimateShippingTax();

		shoppingCartPage.clickShippingRateSelector();

		return shoppingCartPage;
	}

	/**
	 * Navigation to the Payment Method Page
	 *
	 * @return the Storefront Payment Method Page
	 */
	protected M2StorefrontPaymentMethodPage openPaymentMethodPageGuest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		shippingInfoPage.enterEmail(email);

		shippingInfoPage.enterFirstName(firstName);

		shippingInfoPage.enterLastName(lastName);

		shippingInfoPage.enterStreetAddress(streetAddress);

		shippingInfoPage.enterCity(city);

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
	 * Navigation to the Payment Method Page as existing customer
	 *
	 * @return the Storefront Payment Method Page
	 */
	protected M2StorefrontPaymentMethodPage openPaymentMethodPageExisCust( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageExisCust();

		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		if (shoppingCartPage.verifyPopUpLogin()){
			shoppingCartPage.popup.enterUserInfo(username, password);
			shoppingCartPage.clickProceedToCheckout();
			if (shoppingCartPage.verifyPopUpLogin()){
				shoppingCartPage.closePopUpLogin();
				shoppingCartPage.clickProceedToCheckout();
			}
		}

		shippingInfoPage.setFreeShippingAllItems();

		M2StorefrontPaymentMethodPage paymentMethod = shippingInfoPage.clickNextButton();

		paymentMethod.openTaxBlind();

		return paymentMethod;
	}

	/**
	 * Navigation through Guest order input in Magento Storefront
	 *
	 * @return the Storefront Order Number Page
	 */
	protected M2StorefrontOrderNumberPage putInOrderExisCust( )
	{
		M2StorefrontPaymentMethodPage paymentPage = openPaymentMethodPageExisCust();

		paymentPage.selectPaymentMethod();

		M2StorefrontThankYouPage thankYouPage = paymentPage.clickPlaceOrderButton();

		M2StorefrontOrderNumberPage orderNumberPage = thankYouPage.clickOrderNumber();

		return orderNumberPage;
	}

	/**
	 * Puts in order for M2ICMGuestBillisShipPhysicalTests
	 *
	 * @return the thank you page
	 */
	protected M2StorefrontThankYouPage putInOrderForAdmin( )
	{
		M2StorefrontPaymentMethodPage paymentPage = openPaymentMethodPageExisCust();

		paymentPage.selectPaymentMethod();

		M2StorefrontThankYouPage thankYouPage = paymentPage.clickPlaceOrderButton();

		return thankYouPage;
	}

	/**
	 * clears shopping cart before test
	 *
	 * @return the storefront shopping cart page
	 */
	protected M2StorefrontShoppingCartPage clearShoppingCart( )
	{
		M2StorefrontShoppingCartPage sc = new M2StorefrontShoppingCartPage(driver);
		sc.navPanel.clearShoppingCart();
		return sc;
	}

	/**
	 * clears item in shopping cart after first item has been cleared
	 *
	 * @return the storefront shopping cart page
	 */
	protected M2StorefrontShoppingCartPage clearShoppingCartSecondItem( )
	{
		M2StorefrontShoppingCartPage sc = new M2StorefrontShoppingCartPage(driver);
		sc.navPanel.clearSecondShoppingCartItem();
		return sc;
	}

	/**
	 * clears shopping cart from currently inside shopping cart
	 *
	 * @return the storefront shopping info page
	 */
	protected M2StorefrontShippingInfoPage clearShoppingCartFromSC( )
	{
		M2StorefrontShippingInfoPage sc = new M2StorefrontShippingInfoPage(driver);
		sc.navPanel.clearShoppingCartFromSC();
		return sc;
	}

	/**
	 * click Logo button
	 *
	 * @return the storefront home page
	 */
	protected M2StorefrontHomePage clickLogoButton( )
	{
		M2StorefrontHomePage home = new M2StorefrontHomePage(driver);

		home.navPanel.clickLogoButton();

		return home;
	}
}
