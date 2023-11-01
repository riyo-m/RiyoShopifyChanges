package com.vertex.quality.connectors.ariba.connector.tests.base;

import com.vertex.quality.common.enums.DBEnvironmentDescriptors;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.tests.VertexUIBaseTest;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.common.utils.AribaConnUiUtilities;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pages.misc.AribaConnSignOnPage;

import java.util.List;

/**
 * currently just stores constants & utility functions that are used by many or
 * all tests of ariba's configuration site
 *
 * @author ssalisbury
 */
public abstract class AribaConnBaseTest extends VertexUIBaseTest<AribaConnSignOnPage>
{
	protected AribaConnUiUtilities connUtil;
	//stored these here until figure out what to do with them
	//	static final public String oSeriesConnectorDevURL = "http://oseries8-dev.vertexconnectors.com:8095/vertex-ui/";
	//	static final public String oSeriesOnDemandURL = "https://os8atl1.ondemand.vertexinc.com/vertex-ui/";
	protected final String defaultTenantDisplayName = "default";
	protected final String defaultTenantVariantId = defaultTenantDisplayName;
	//this is stating the assumption that the connector UI have only one user account.
	public static final int SOLE_CONFIG_USER_INDEX = 0;
	protected DBEnvironmentDescriptors environmentChoice;

	protected String currConfigSignOnUrl;

	protected String configUsername;
	protected String configPassword;

	@Override
	public AribaConnSignOnPage loadInitialTestPage( )
	{
		AribaConnSignOnPage newPage;
		this.connUtil = new AribaConnUiUtilities(this.driver);
		environmentChoice = connUtil.getEnvironmentTarget();

		newPage = connUtil.loadConfig();
		return newPage;
	}

	/**
	 * checks whether the text value retrieved from interacting with an element
	 * is equal to the expected text for that element
	 *
	 * @param elementDescription a description of that element
	 * @param expectedText       the expected value for that element
	 * @param actualText         the actual value retrieved from that element
	 *
	 * @return whether the value retrieved from interacting with that element
	 * is equal to the default expected value for that element
	 */
	protected boolean validateElementTextValue( final String elementDescription, final String expectedText,
		final String actualText )
	{
		boolean isTextExpected = false;

		isTextExpected = expectedText.equals(actualText);

		if ( !isTextExpected )
		{
			String wrongTextMessage = String.format("%s expects %s but actually loads %s", elementDescription,
				expectedText, actualText);
			VertexLogger.log(wrongTextMessage, getClass());
		}

		return isTextExpected;
	}

	/**
	 * checks whether the correct page on this configuration site has loaded
	 *
	 * @param maybePage      the page that the test expects to be loaded
	 *                       or to be not loaded
	 * @param shouldBeLoaded whether this checks for the given page to be
	 *                       loaded or whether this checks for the given page to not be loaded
	 * @param description    label for the given page
	 *
	 * @return whether the given page on this configuration site has
	 * the expected loaded status, ie whether it is loaded when
	 * shouldBeLoaded is true or whether it isn't loaded when
	 * shouldBeloaded is false
	 */
	protected boolean validatePageLoadExpectedState( final AribaConnBasePage maybePage, final boolean shouldBeLoaded,
		final String description )
	{
		boolean isExpectedLoadState = false;

		boolean isLoaded = maybePage.isCurrentPage();
		isExpectedLoadState = shouldBeLoaded == isLoaded;

		if ( !isExpectedLoadState )
		{
			String badPageLoadMessage = null;
			if ( shouldBeLoaded )
			{
				String actuallyLoadedPageTitle = maybePage.getPageTitle();
				badPageLoadMessage = String.format("%s page was loaded when %s page should have been loaded",
					actuallyLoadedPageTitle, description);
			}
			else
			{
				badPageLoadMessage = String.format("%s page was loaded when it should not have been loaded",
					description);
			}
			VertexLogger.log(badPageLoadMessage, VertexLogLevel.ERROR, getClass());
		}

		return isExpectedLoadState;
	}

	/**
	 * checks if accessing a configuration menu while logged out has gone wrong, logs it if so
	 *
	 * @param configMenuPage   the configuration menu page that the test is attempting to load
	 * @param signOnPage       the sign-on page that the configuration site should redirect to
	 *                         (because the test is not logged in)
	 * @param configMenuOption which configuration menu the test is attempting to access
	 *
	 * @return whether accessing a configuration menu while logged out has gone wrong
	 *
	 * @author ssalisbury
	 */
	protected boolean didConfigMenuRedirectSignOn( final AribaConnMenuBasePage configMenuPage,
		final AribaConnSignOnPage signOnPage, final AribaConnNavConfigurationMenuOption configMenuOption )
	{
		boolean isCorrectPage = false;
		String menuName = configMenuOption.toString();

		String configBadSuccessDescription = String.format("The %s configuration menu", menuName);
		boolean isNotConfigMenu = validatePageLoadExpectedState(configMenuPage, false, configBadSuccessDescription);

		//assumes that clicking on the config menu option didn't actually load a config menu but instead loaded the sign-on page again.
		//Because of this, it uses the existing AribaUiSignOnPage object to verify that the now-loaded page is a sign-on page
		String signOnFailureDescription = String.format("attempting to unsafely access Configuration Menu %s, Sign-on",
			menuName);
		boolean isSignOn = validatePageLoadExpectedState(signOnPage, true, signOnFailureDescription);

		isCorrectPage = isNotConfigMenu && isSignOn;
		return isCorrectPage;
	}
}
