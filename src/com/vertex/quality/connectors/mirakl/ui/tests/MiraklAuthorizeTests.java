package com.vertex.quality.connectors.mirakl.ui.tests;

import com.vertex.quality.connectors.mirakl.ui.base.MiraklUIBaseTest;
import com.vertex.quality.connectors.mirakl.ui.enums.MiraklUIData;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Mirakl connector authorize user tests
 *
 * @author Rohit.Mogane
 */
public class MiraklAuthorizeTests extends MiraklUIBaseTest
{
	/**
	 * To check authorization of user and assert the home page title
	 */
	@Test
	public void authorizeUserTest( )
	{
		loadAuthorizePage();
		assertEquals(authorizeUser(), MiraklUIData.MIRAKL_HOMEPAGE_TITLE.data);
	}
}
