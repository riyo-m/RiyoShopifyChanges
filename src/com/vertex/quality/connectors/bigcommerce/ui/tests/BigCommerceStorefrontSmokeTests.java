package com.vertex.quality.connectors.bigcommerce.ui.tests;

import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminHomePage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreHomePage;
import com.vertex.quality.connectors.bigcommerce.ui.tests.base.BigCommerceUiBaseTest;
import org.testng.annotations.Test;

/**
 * this class represents all the smoke test cases for Big Commerce ui.
 *
 * @author osabha
 */
@Test(groups = { "bigCommerce_smoke" })
public class BigCommerceStorefrontSmokeTests extends BigCommerceUiBaseTest
{
	/**
	 * smoke test to navigate to the live store and create a happy path transaction.
	 */
	@Test
	public void storeAccessibleTest( )
	{
		BigCommerceAdminHomePage homePage = signInToHomePage(testStartPage);
		BigCommerceStoreHomePage storePage = homePage.navBar.navigateToStorefront();
		storePage.header.clickShopAll();
	}
}
