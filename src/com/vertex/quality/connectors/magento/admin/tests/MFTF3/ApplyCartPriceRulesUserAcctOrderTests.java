package com.vertex.quality.connectors.magento.admin.tests.MFTF3;

import com.vertex.quality.connectors.magento.admin.pages.M2AdminCartPriceRulesPage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminHomepage;
import com.vertex.quality.connectors.magento.admin.pages.M2AdminNewAddRulePage;
import com.vertex.quality.connectors.magento.admin.tests.base.MagentoAdminBaseTest;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontPaymentMethodPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontShippingInfoPage;
import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontShoppingCartPage;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * BUNDLE-1369
 *
 * @author alewis
 */
public class ApplyCartPriceRulesUserAcctOrderTests extends MagentoAdminBaseTest
{

	@Test()
	public void correctMessageTest( )
	{
		M2StorefrontPaymentMethodPage paymentMethodPage = putInOrder();

		double inclTaxString = paymentMethodPage.getTotalInclTax();

		paymentMethodPage.clickPlaceOrderButton();

		String slicedDifferenceString = String.format("%.2f", inclTaxString);

		assertTrue(slicedDifferenceString.equals("0.00"));

	}

	protected M2StorefrontPaymentMethodPage putInOrder( )
	{
		M2AdminHomepage homepage = signInToAdminHomepage();
		homepage.navPanel.clickMarketingButton();
		M2AdminCartPriceRulesPage cartPriceRulesPage = homepage.navPanel.clickCardPriceRulesButton();
		M2AdminNewAddRulePage newAddRulePage = cartPriceRulesPage.clickRule();

		M2StorefrontShoppingCartPage shoppingCartPage = openShoppingCartPageExisCust();

		//shoppingCartPage.clickEstimateShippingTax();
		shoppingCartPage.selectState(PA_Number);
		shoppingCartPage.enterZipCode(zipCode);
		shoppingCartPage.clickShippingRateSelector();
		M2StorefrontShippingInfoPage shippingInfoPage = shoppingCartPage.clickProceedToCheckout();

		M2StorefrontPaymentMethodPage paymentMethodPage = shippingInfoPage.clickNextButton();

		if (shippingInfoPage.verifyUpdateAddressButton()){
			shippingInfoPage.clickUpdateAddress();
			shippingInfoPage.clickNextButton();
		}

		paymentMethodPage.applyDiscount("discount");

		return paymentMethodPage;
	}
}