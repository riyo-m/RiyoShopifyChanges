package com.vertex.quality.connectors.bigcommerce.ui.tests;

import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminHomePage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreCheckoutPage;
import com.vertex.quality.connectors.bigcommerce.ui.pojos.BigCommerceUiAddressPojo;
import com.vertex.quality.connectors.bigcommerce.ui.tests.base.BigCommerceUiBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * end to end tests of the Big Commerce connector's estimate endpoint using the Big Commerce UI
 *
 * @author ssalisbury
 */
public class BigCommerceUiSingleItemEstimateTests extends BigCommerceUiBaseTest
{
	/**
	 * this checks out a single item and verifies that the correct tax is estimated for the order before the order
	 * would be submitted
	 */
	@Test(groups = {"bigCommerce_ui"})
	public void happySingleItemEstimateTest( )
	{
		final String defaultProductName = "[Sample] Orbit Terrarium - Large";
		//this includes the tax on the shipping fee
		final String expectedTaxEstimate = "$6.66";

		final String customerEmail = "thomas.demartinis@vertexinc.com";
		BigCommerceUiAddressPojo shipAddress = new BigCommerceUiAddressPojo("Marc", "Viertel",
			BigCommerceTestDataAddress.US_AZ_ADDRESS_2);
		BigCommerceUiAddressPojo billAddress = new BigCommerceUiAddressPojo("Rosa", "Minski",
			BigCommerceTestDataAddress.US_CA_ADDRESS_2);

		BigCommerceAdminHomePage adminHomePage = signInToHomePage(testStartPage);
		BigCommerceStoreCheckoutPage checkoutPage = checkoutSingleItem(adminHomePage, defaultProductName);

		fillCheckoutPage(checkoutPage, customerEmail, shipAddress, billAddress);
		String taxEstimate = checkoutPage.retrieveOrderTax();
		assertEquals(taxEstimate, expectedTaxEstimate);
	}

	/**
	 * this checks out a single item which is associated with a product code in o series and verifies that the
	 * correct tax is estimated for the order before the order would be submitted
	 */
	@Test(groups = {"bigCommerce_ui"})
	public void singleItemProductCodeEstimateTest( )
	{
		final String productWithCodeName = "[Sample] Dustpan & Brush";
		final String expectedTaxEstimate = "$0.00";

		final String customerEmail = "thomas.demartinis@vertexinc.com";
		BigCommerceUiAddressPojo shipAddress = new BigCommerceUiAddressPojo("Marc", "Viertel",
			BigCommerceTestDataAddress.US_CA_ADDRESS_2);
		BigCommerceUiAddressPojo billAddress = new BigCommerceUiAddressPojo("Rosa", "Minski",
			BigCommerceTestDataAddress.US_PA_ADDRESS_1);

		BigCommerceAdminHomePage adminHomePage = signInToHomePage(testStartPage);
		BigCommerceStoreCheckoutPage checkoutPage = checkoutSingleItem(adminHomePage, productWithCodeName);

		fillCheckoutPage(checkoutPage, customerEmail, shipAddress, billAddress);
		String taxEstimate = checkoutPage.retrieveOrderTax();
		assertEquals(taxEstimate, expectedTaxEstimate);
	}

	/**
	 * this checks out a single item which is associated with a product class code in o series and verifies that the
	 * correct tax is estimated for the order before the order would be submitted
	 */
	@Test(groups = {"bigCommerce_ui"})
	public void singleItemProductClassCodeEstimateTest( )
	{
		final String productWithClassCodeName = "[Sample] Able Brewing System";
		final String expectedTaxEstimate = "$0.00";

		final String customerEmail = "thomas.demartinis@vertexinc.com";
		BigCommerceUiAddressPojo shipAddress = new BigCommerceUiAddressPojo("Marc", "Viertel",
			BigCommerceTestDataAddress.US_PA_ADDRESS_3);
		BigCommerceUiAddressPojo billAddress = new BigCommerceUiAddressPojo("Rosa", "Minski",
			BigCommerceTestDataAddress.US_AZ_ADDRESS_3);

		BigCommerceAdminHomePage adminHomePage = signInToHomePage(testStartPage);
		BigCommerceStoreCheckoutPage checkoutPage = checkoutSingleItem(adminHomePage, productWithClassCodeName);

		fillCheckoutPage(checkoutPage, customerEmail, shipAddress, billAddress);
		String taxEstimate = checkoutPage.retrieveOrderTax();
		assertEquals(taxEstimate, expectedTaxEstimate);
	}

	/**
	 * this checks out a single item and verifies that the correct tax is estimated for the order before the order would
	 * be submitted
	 */
	@Test(groups = {"bigCommerce_ui"})
	public void singleItemCustomerCodeEstimateTest( )
	{
		final String productName = "[Sample] Orbit Terrarium - Large";
		final String expectedTaxEstimate = "$0.00";

		final String customerEmail = "vertexautomation@vertexinc.com";
		BigCommerceUiAddressPojo shipAddress = new BigCommerceUiAddressPojo("Marc", "Viertel",
			BigCommerceTestDataAddress.US_PA_ADDRESS_2);
		BigCommerceUiAddressPojo billAddress = new BigCommerceUiAddressPojo("Rosa", "Minski",
			BigCommerceTestDataAddress.US_AZ_ADDRESS_1);

		BigCommerceAdminHomePage adminHomePage = signInToHomePage(testStartPage);

		BigCommerceStoreCheckoutPage checkoutPage = checkoutSingleItem(adminHomePage, productName);

		fillCheckoutPage(checkoutPage, customerEmail, shipAddress, billAddress);
		String taxEstimate = checkoutPage.retrieveOrderTax();
		assertEquals(taxEstimate, expectedTaxEstimate);
	}
}
