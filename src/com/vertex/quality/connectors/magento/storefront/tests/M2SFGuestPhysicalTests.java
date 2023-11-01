package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.common.enums.MagentoData;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontPaymentMethodPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontProductDetailsPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontShoppingCartPage;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.testng.Assert.assertTrue;

/**
 * tests of the correct tax is displayed at each stage of inputting an order in
 * Magento Storefront as a guest for a physical order
 *
 * @author alewis
 */
public class M2SFGuestPhysicalTests extends M2StorefrontBaseTest
{
	/**
	 * test whether the correct tax is displayed in the minicart as guest
	 */
	@Test()
	public void checkTaxOnProductsPageGuestTest( )
	{
		M2StorefrontProductDetailsPage productPage = openProductPageGuest();

		String includeTaxPrice = productPage.getIncludeTaxInMinicart();

		String excludeTaxPrice = productPage.getExcludeTaxInMinicart();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(includeTaxPrice.equals(excludeTaxPrice));
	}

	/**
	 * tests whether correct tax is applied to subtotal displayed on top of page
	 * before shipping/tax info is entered for product bought in PA
	 */
	@Test()
	public void checkTaxOnShoppingCartPageGuestTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		String includingTaxPrice = shoppingCartPage.getIncludingTaxSubtotalFirstItem();

		String excludingTaxPrice = shoppingCartPage.getExcludingTaxSubtotalFirstItem();

		clickLogoButton();
		clearShoppingCart();

		assertTrue(includingTaxPrice.equals(excludingTaxPrice));
	}

	/**
	 * tests whether correct tax is applied to subtotal displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkSubtotalTaxInSummaryGuestTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		String includingTax = shoppingCartPage.getSubtotalIncludingTaxInSummary();
		String excludingTax = shoppingCartPage.getSubtotalExcludingTaxInSummary();

		double includingTaxDouble = Double.parseDouble(includingTax.substring(1));
		double excludingTaxDouble = Double.parseDouble(excludingTax.substring(1));

		double difference = includingTaxDouble / excludingTaxDouble;
		String differenceString = Double.toString(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to shipping displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkTaxOnShippingInSummaryGuestTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		String includingTax = shoppingCartPage.getShippingInclTaxInSummary();
		String excludingTax = shoppingCartPage.getShippingExclTaxInSummary();

		double includingTaxDouble = Double.parseDouble(includingTax.substring(1));
		double excludingTaxDouble = Double.parseDouble(excludingTax.substring(1));

		double difference = includingTaxDouble / excludingTaxDouble;
		String differenceString = Double.toString(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to shipping displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkTaxInBlindInSummaryGuestTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		String salesAndUseTax = shoppingCartPage.getSalesAndUseTax();
		String shippingTaxInBlindTax = shoppingCartPage.getShippingTaxInBlind();

		String subtotalExclTax = shoppingCartPage.getSubtotalExcludingTaxInSummary();
		String shippingExclTax = shoppingCartPage.getShippingExclTaxInSummary();

		double salesAndUseTaxDouble = Double.parseDouble(salesAndUseTax.substring(1));
		double shippingTaxInBlindTaxDouble = Double.parseDouble(shippingTaxInBlindTax.substring(1));
		double subtotalExclTaxDouble = Double.parseDouble(subtotalExclTax.substring(1));
		double shippingExclTaxDouble = Double.parseDouble(shippingExclTax.substring(1));

		double taxAppliedPreShipping = salesAndUseTaxDouble / subtotalExclTaxDouble;
		double taxAppliedPostShipping = shippingTaxInBlindTaxDouble / shippingExclTaxDouble;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String preShippingString = df.format(taxAppliedPreShipping);

		DecimalFormat df2 = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String postShippingString = df2.format(taxAppliedPostShipping);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(preShippingString.equals(MagentoData.PA_TAX_NUMBER2.data));
		assertTrue(postShippingString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests whether correct tax is applied to total displayed in summary for
	 * product bought in PA as guest
	 */
	@Test()
	public void checkTotalTaxInSummaryOnShopCartPageGuestTest( )
	{
		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageGuest();

		String includingTax = shoppingCartPage.getTotalIncludingTaxInSummary();
		String excludingTax = shoppingCartPage.getTotalExcludingTaxInSummary();

		double includingTaxDouble = Double.parseDouble(includingTax.substring(1));
		double excludingTaxDouble = Double.parseDouble(excludingTax.substring(1));

		double difference = includingTaxDouble / excludingTaxDouble;
		String differenceString = Double.toString(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to subtotal displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkSubtotalTaxInPaymentPageGuestTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = openPaymentMethodPageGuest();

		double includingTax = paymentMethodPage.getSubtotalInclTax();
		double excludingTax = paymentMethodPage.getSubtotalExclTax();

		double difference = includingTax / excludingTax;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedDifferenceString = df.format(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to shipping displayed in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkShippingTaxInPaymentPageGuestTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = openPaymentMethodPageGuest();

		double includingTax = paymentMethodPage.getShippingInclTax();
		double excludingTax = paymentMethodPage.getShippingExclTax();

		double difference = includingTax / excludingTax;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedDifferenceString = df.format(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedDifferenceString.equals(MagentoData.PA_TAX_RATE.data));
	}

	/**
	 * tests whether correct tax is applied to sales and use and shipping in the blind in summary for
	 * product bought in PA
	 */
	@Test()
	public void checkBlindTaxInPaymentPageGuestTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = openPaymentMethodPageGuest();

		double salesUseTax = paymentMethodPage.getSalesUseTax();
		double shippingTax = paymentMethodPage.getShippingTaxInBlind();
		double subtotalExclTax = paymentMethodPage.getSubtotalExclTax();
		double shippingExclTax = paymentMethodPage.getShippingExclTax();

		double taxAppliedToSalesUse = salesUseTax / subtotalExclTax;
		double taxAppliedToShipping = shippingTax / shippingExclTax;

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);
		String slicedSalesUseString = df.format(taxAppliedToSalesUse);

		DecimalFormat df2 = new DecimalFormat("#.##");
		df2.setRoundingMode(RoundingMode.CEILING);
		String slicedShippingString = df.format(taxAppliedToShipping);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(slicedSalesUseString.equals(MagentoData.PA_TAX_NUMBER2.data));
		assertTrue(slicedShippingString.equals(MagentoData.PA_TAX_NUMBER.data));
	}

	/**
	 * tests whether correct tax is applied to total displayed in summary for
	 * product bought in PA, guest checkout
	 */
	@Test()
	public void checkTotalTaxInPaymentPageGuestTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = openPaymentMethodPageGuest();

		Double includingTax = paymentMethodPage.getTotalInclTax();
		Double excludingTax = paymentMethodPage.getTotalExclTax();

		double difference = includingTax / excludingTax;
		String differenceString = Double.toString(difference);

		clickLogoButton();
		clearShoppingCart();

		assertTrue(differenceString.equals(MagentoData.PA_TAX_RATE.data));
	}
}