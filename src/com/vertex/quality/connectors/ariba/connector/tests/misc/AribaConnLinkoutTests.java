package com.vertex.quality.connectors.ariba.connector.tests.misc;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnExternalVertexLinkOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnBaseTest;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;

import static org.testng.Assert.assertTrue;

/**
 * tests of elements which give the user access to legally-important information
 *
 * @author ssalisbury
 */

public class AribaConnLinkoutTests extends AribaConnBaseTest
{
	/**
	 * JIRA TICKET CARIBA-487
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void footerPrivacyPolicyTest( )
	{
		testExternalLink(testStartPage, AribaConnExternalVertexLinkOption.PRIVACY_POLICY);
	}

	/**
	 * JIRA TICKET CARIBA-487
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void footerTermsOfUseTest( )
	{
		testExternalLink(testStartPage, AribaConnExternalVertexLinkOption.TERMS_OF_USE);
	}

	/**
	 * JIRA TICKET CARIBA-486
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void footerVertexHomepageTest( )
	{
		testExternalLink(testStartPage, AribaConnExternalVertexLinkOption.VERTEX_HOME_PAGE);
	}

	/**
	 * for JIRA subtask CARIBA-438
	 */
//	@Test(groups = { "ariba_ui","ariba_regression" })
	public void copyrightYearTest( )
	{
		final int currYear = ZonedDateTime
			.now(defaultTimeZone)
			.getYear();
		String copyrightNotice = testStartPage.footer.getCopyrightNotice();
		String currYearString = Integer.toString(currYear);
		VertexLogger.log(copyrightNotice);
		boolean isCopyrightYearCurrent = copyrightNotice.contains(currYearString);
		if ( !isCopyrightYearCurrent )
		{
			final String badYearMessage = String.format(
				"Copyright notice has wrong year: the year is %s, but the copyright notice is:\n[%s]", currYearString,
				copyrightNotice);
			VertexLogger.log(badYearMessage, VertexLogLevel.ERROR);
		}
		assertTrue(isCopyrightYearCurrent);
	}

	/**
	 * validates that some link from the Ariba connector site to an external site is visible and loads the
	 * intended external website when clicked
	 *
	 * @param startPage  the page on the Ariba connector site which the test starts on
	 * @param linkOption which external link to validate
	 */
	protected void testExternalLink( final AribaConnBasePage startPage,
		final AribaConnExternalVertexLinkOption linkOption )
	{
		String expectedDestination = linkOption.expectedAddress;

		boolean isLinkPresent = startPage.footer.isLinkPresent(linkOption);
		assertTrue(isLinkPresent);

		startPage.footer.clickExternalVertexLink(linkOption);

		if ( linkOption.opensInNewTab )
		{
			startPage.window.switchToWindow();
		}

		String currURL = startPage.getCurrentUrl();
		boolean linkWorks = expectedDestination.equalsIgnoreCase(currURL);
		assertTrue(linkWorks);
	}
}
