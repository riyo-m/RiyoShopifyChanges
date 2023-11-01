package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.MiraklUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Mirakl connector UI operator tests
 *
 * @author Rohit.Mogane
 */
public class MiraklOperatorTests extends MiraklUIBaseTest
{
	/**
	 * To open operator tab on home page
	 */
	private void openOperatorTab( )
	{
		loadAuthorizePage();
		authorizeUser();
		getOperatorPageTitle();
	}

	/**
	 * to test operator page title
	 */
	@Test
	public void operatorPageTitleTest( )
	{
		openOperatorTab();
		assertEquals(getOperatorPageTitle(), MiraklUIData.MIRAKL_OPERATOR_PAGE_TITLE.data);
	}
}
