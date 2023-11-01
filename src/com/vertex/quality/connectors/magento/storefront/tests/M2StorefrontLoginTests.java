package com.vertex.quality.connectors.magento.storefront.tests;

import com.vertex.quality.connectors.magento.storefront.pages.M2StorefrontHomePage;
import com.vertex.quality.connectors.magento.storefront.tests.base.M2StorefrontBaseTest;
import org.testng.annotations.Test;

/**
 * tests login to Magento Storefront
 *
 * @author alewis
 */
public class M2StorefrontLoginTests extends M2StorefrontBaseTest
{
	/**
	 * tests whether can open storefront homepage
	 */
	@Test()
	public void openStorefrontTest( )
	{
		openStorefrontHomepage();
	}

	/**
	 * tests whether can login to storefront
	 */
	@Test()
	public void loginStorefrontTest()
	{
		M2StorefrontHomePage homepage = signInToStorefront();
	}
}