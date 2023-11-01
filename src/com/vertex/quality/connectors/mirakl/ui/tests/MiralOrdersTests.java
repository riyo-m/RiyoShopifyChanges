package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.MiraklUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Mirakl connector UI orders tests
 *
 * @author Rohit.Mogane
 */
public class MiralOrdersTests extends MiraklUIBaseTest
{
	/**
	 * To open orders tab on home page
	 */
	private void openOrdersTab( )
	{
		loadAuthorizePage();
		authorizeUser();
		getOrdersPageTitle();
	}

	/**
	 * to test orders page title
	 */
	@Test
	public void ordersPageTitleTest( )
	{
		openOrdersTab();
		assertEquals(getOrdersPageTitle(), MiraklUIData.MIRAKL_ORDERS_PAGE_TITLE.data);
	}
}
