package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.MiraklUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Mirakl connector UI marketplace tests
 *
 * @author Rohit.Mogane
 */
public class MiraklMarketplaceTests extends MiraklUIBaseTest
{
	/**
	 * To open marketplace tab on home page
	 */
	private void openMarketplaceTab( )
	{
		loadAuthorizePage();
		authorizeUser();
		getMarketplacePageTitle();
	}

	/**
	 * to test marketplace page title
	 */
	@Test
	public void marketplacePageTitleTest( )
	{
		openMarketplaceTab();
		assertEquals(getMarketplacePageTitle(), MiraklUIData.MIRAKL_MARKETPLACE_PAGE_TITLE.data);
	}
}
