package com.vertex.quality.connectors.orocommerce.tests.storeFront_v4_1;

import com.vertex.quality.connectors.orocommerce.tests.base.OroStoreFrontBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * a group of smoke tests for the oro commerce store front
 *
 * @author alewis
 */
public class OroStoreFrontHealthCheckTests extends OroStoreFrontBaseTest
{

	/**
	 * to log in and check for the expected home page title
	 *
	 * COROCOM-522
	 */
	@Test(groups = { "oroSmoke","oroCommerce" })
	public void loginTest( )
	{
		String expectedPageTitle = "Default Web Catalog";
//		OroStoreFrontHomePage homePage = signInToStoreFront(testStartPage);
//		String currentPageTitle = homePage.getPageTitle();
//		assertTrue(expectedPageTitle.equals(currentPageTitle));
	}
}
