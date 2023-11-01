package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.MiraklUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Mirakl connector UI stores tests
 *
 * @author Rohit.Mogane
 */
public class MiraklStoresTests extends MiraklUIBaseTest
{
	/**
	 * To open stores tab on home page.
	 */
	private void openStoresTab( )
	{
		loadAuthorizePage();
		authorizeUser();
		getStoresPageTitle();
	}

	/**
	 * to test stores page title
	 */
	@Test
	public void storesPageTitleTest( )
	{
		openStoresTab();
		assertEquals(getStoresPageTitle(), MiraklUIData.MIRAKL_STORES_PAGE_TITLE.data);
	}
}