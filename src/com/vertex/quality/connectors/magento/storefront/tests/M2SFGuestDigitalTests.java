package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.*;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * tests of the correct tax is displayed at each stage of inputting an order in
 * Magento Storefront as a guest same billing and shipping for a digital product
 *
 * @author alewis
 */
public class M2SFGuestDigitalTests extends M2StorefrontBaseTest
{
	protected String virtualURL = MagentoData.STOREFRONT_VIRTUAL_PRODUCT_URL.data;
	protected String downloadableURL = MagentoData.STOREFRONT_DOWNLOADABLE_PRODUCT_URL.data;

	String oneHundredDollars = "$100.00";
	String twoHundredDollars = "$200.00";
	String twoHundredTwelveDollars = "$212.00";
	String twoHundredTwelve = "212.0";
	String twoHundred = "200.0";
	String twelveDollars = "$12.00";
	String twelve = "12.0";
	String MD_Number = "31";
	protected String mdZipCode = MagentoData.MD_ZIPCODE.data;
	String mdUsername = MagentoData.MD_STOREFRONT_USERNAME.data;
	String passwordString = MagentoData.STOREFRONT_PASSWORD.data;

	/**
	 * tests whether the correct tax is displayed in the Shopping Cart page Item section
	 * before address is inputted
	 */
	@Test()
	public void taxOnMinicartItemTest( )
	{
		M2StorefrontProductDetailsPage productDetailsPage = putInVirtualAndDownloadableProducts();

		String inclTaxSubtotal = productDetailsPage.getIncludeTaxCartSubtotal();
		String exclTaxSubtotal = productDetailsPage.getExcludeTaxCartSubtotal();
		String inclTaxFirstItem = productDetailsPage.getIncludeTaxInMinicartFirstItem();
		String exclTaxFirstItem = productDetailsPage.getExcludeTaxInMinicartFirstItem();
		String inclTaxSecondItem = productDetailsPage.getIncludeTaxInMinicartSecondItem();
		String exclTaxSecondItem = productDetailsPage.getExcludeTaxInMinicartSecondItem();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(exclTaxSubtotal.equals(twoHundredDollars));
		assertTrue(inclTaxSubtotal.equals(twoHundredDollars));
		assertTrue(inclTaxFirstItem.equals(oneHundredDollars));
		assertTrue(exclTaxFirstItem.equals(oneHundredDollars));
		assertTrue(inclTaxSecondItem.equals(oneHundredDollars));
		assertTrue(exclTaxSecondItem.equals(oneHundredDollars));
	}

	/**
	 * test whether the correct tax is displayed in the Shopping Cart page Summary section
	 * before address is inputted
	 */
	@Test()
	public void taxShoppingCartBeforeAddressTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPage();

		String inclTaxSubtotalFirst = shoppingCartPage.getIncludingTaxSubtotalFirstItem();
		String exclTaxSubtotalFirst = shoppingCartPage.getExcludingTaxSubtotalFirstItem();
		String inclTaxSubtotalSecond = shoppingCartPage.getIncludingTaxSubtotalSecondItem();
		String exclTaxSubtotalSecond = shoppingCartPage.getExcludingTaxSubtotalSecondItem();

		String inclSubtotalSummary = shoppingCartPage.getSubtotalIncludingTaxInSummary();
		String exclSubtotalSummary = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		String inclTotalSummary = shoppingCartPage.getTotalIncludingTaxInSummary();
		String exclTotalSummary = shoppingCartPage.getTotalExcludingTaxInSummary();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(inclTaxSubtotalFirst.equals(oneHundredDollars));
		assertTrue(exclTaxSubtotalFirst.equals(oneHundredDollars));
		assertTrue(inclTaxSubtotalSecond.equals(oneHundredDollars));
		assertTrue(exclTaxSubtotalSecond.equals(oneHundredDollars));

		assertTrue(inclSubtotalSummary.equals(twoHundredDollars));
		assertTrue(exclSubtotalSummary.equals(twoHundredDollars));
		assertTrue(inclTotalSummary.equals(twoHundredDollars));
		assertTrue(exclTotalSummary.equals(twoHundredDollars));
	}

	/**
	 * test whether the correct tax is displayed in the Shopping Cart page Summary section
	 * after address is inputted
	 */
	@Test()
	public void taxShoppingCartAfterAddressTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = navigateToShoppingCartPageAndAddAddress();

		String inclTaxSubtotalFirst = shoppingCartPage.getIncludingTaxSubtotalFirstItem();
		String exclTaxSubtotalFirst = shoppingCartPage.getExcludingTaxSubtotalFirstItem();
		String inclTaxSubtotalSecond = shoppingCartPage.getIncludingTaxSubtotalSecondItem();
		String exclTaxSubtotalSecond = shoppingCartPage.getExcludingTaxSubtotalSecondItem();

		String inclSubtotalSummary = shoppingCartPage.getSubtotalIncludingTaxInSummary();
		String exclSubtotalSummary = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		String taxAmount = shoppingCartPage.getTaxBlindTotal();
		String salesUseTax = shoppingCartPage.getSalesAndUseTax();
		String inclTotalSummary = shoppingCartPage.getTotalIncludingTaxInSummary();
		String exclTotalSummary = shoppingCartPage.getTotalExcludingTaxInSummary();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(inclTaxSubtotalFirst.equals(oneHundredDollars));
		assertTrue(exclTaxSubtotalFirst.equals(oneHundredDollars));
		assertTrue(inclTaxSubtotalSecond.equals(oneHundredDollars));
		assertTrue(exclTaxSubtotalSecond.equals(oneHundredDollars));

		assertTrue(exclSubtotalSummary.equals(twoHundredDollars));
		assertTrue(inclSubtotalSummary.equals(twoHundredTwelveDollars));
		assertTrue(taxAmount.equals(twelveDollars));
		assertTrue(salesUseTax.equals(twelveDollars));
		assertTrue(inclTotalSummary.equals(twoHundredTwelveDollars));
		assertTrue(exclTotalSummary.equals(twoHundredDollars));
	}

	/**
	 * test whether the correct tax is displayed in the Payment page Summary section
	 */
	@Test()
	public void getTaxOnPaymentPageTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		double inclTaxDouble = paymentMethodPage.getSubtotalInclTax();
		String inclTax = Double.toString(inclTaxDouble);

		double exclTaxDouble = paymentMethodPage.getSubtotalExclTax();
		String exclTax = Double.toString(exclTaxDouble);

		paymentMethodPage.openTaxBlind();

		double salesUseDouble = paymentMethodPage.getSalesUseTax();
		String salesUse = Double.toString(salesUseDouble);

		double inclTaxTotalDouble = paymentMethodPage.getTotalInclTax();
		String inclTaxTotal = Double.toString(inclTaxTotalDouble);

		double exclTaxTotalDouble = paymentMethodPage.getTotalExclTax();
		String exclTaxTotal = Double.toString(exclTaxTotalDouble);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(inclTax.equals(twoHundredTwelve));
		assertTrue(exclTax.equals(twoHundred));
		assertTrue(salesUse.equals(twelve));
		assertTrue(inclTaxTotal.equals(twoHundredTwelve));
		assertTrue(exclTaxTotal.equals(twoHundred));
	}

	/**
	 * test whether the correct tax is displayed in the Order Number page Items Ordered
	 */
	@Test()
	public void getTaxOnStorefrontOrderReviewPageTest( )
	{
		M2StorefrontOrderNumberPage orderNumberPage = navigateToOrderNumberPage();

		double subtotalExclTax = orderNumberPage.getSubtotalExclTaxWholeOrder();
		String subtotalExclString = Double.toString(subtotalExclTax);

		double subtotalInclTax = orderNumberPage.getSubtotalInclTaxWholeOrder();
		String subtotalInclString = Double.toString(subtotalInclTax);

		double grandTotalExclTax = orderNumberPage.getGrandTotalExclTax();
		String exclTaxTotal = Double.toString(grandTotalExclTax);

		String salesUseTax = orderNumberPage.getSalesUseTax();

		String taxTotal = orderNumberPage.getTaxTotal();

		double grandTotalInclTax = orderNumberPage.getGrandTotalInclTax();
		String inclTaxTotal = Double.toString(grandTotalInclTax);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(subtotalExclString.equals(twoHundred));
		assertTrue(subtotalInclString.equals(twoHundredTwelve));
		assertTrue(exclTaxTotal.equals(twoHundred));
		assertTrue(salesUseTax.equals(twelveDollars));
		assertTrue(taxTotal.equals(twelveDollars));
		assertTrue(inclTaxTotal.equals(twoHundredTwelve));
	}

	/**
	 * adds a virtual and downloadable product to the shopping cart
	 *
	 * @return the product details page
	 */
	protected M2StorefrontProductDetailsPage putInVirtualAndDownloadableProducts( )
	{
		driver.get(virtualURL);

		M2StorefrontProductDetailsPage detailsPage = new M2StorefrontProductDetailsPage(driver);

		detailsPage.clickAddToCartButton();

		driver.get(downloadableURL);

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
		M2StorefrontProductDetailsPage productDetailsPage = putInVirtualAndDownloadableProducts();

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
		shoppingCartPage.selectState(MD_Number);
		shoppingCartPage.enterZipCode(mdZipCode);
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

		shoppingCartPage.clickProceedToCheckout();

		shoppingCartPage.popup.enterUserInfo(mdUsername, passwordString);

		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		M2StorefrontPaymentMethodPage paymentMethodPage = new M2StorefrontPaymentMethodPage(driver);

		return paymentMethodPage;
	}

	/**
	 * navigates to Order Number page
	 *
	 * @return the Order Number page
	 */
	protected M2StorefrontOrderNumberPage navigateToOrderNumberPage( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = navigateToPaymentMethodPage();

		paymentMethodPage.selectPaymentMethod();

		M2StorefrontThankYouPage thankYouPage = paymentMethodPage.clickPlaceOrderButton();

		String number = thankYouPage.getOrderNumber();

		M2StorefrontOrderNumberPage numberPage = thankYouPage.clickOrderNumber();

		return numberPage;
	}
}