package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnExternalTaxTypesPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests of configuring the processing of external tax types
 * on {@link AribaConnExternalTaxTypesPage}
 *
 * @author ssalisbury
 */

public class AribaConnMenuExternalTaxTypeTests extends AribaConnMenuBaseTest
{
	protected final String defaultExternalTaxTypeName = "AUTOExtTaxType";

	/**
	 * JIRA ticket CARIBA-545
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void submitNoNameMappingTest( )
	{
		AribaConnExternalTaxTypesPage externalTypesPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.ARIBA_EXTERNAL_TAX_TYPES_MAINTENANCE);
		final int newExternalMappingIndex = externalTypesPage.addConfigRow();
		final boolean doesSubmitButtonStartDisabled = !externalTypesPage.isSubmitButtonEnabled();
		assertTrue(doesSubmitButtonStartDisabled);
		externalTypesPage.setAribaExternalTaxType(newExternalMappingIndex, defaultExternalTaxTypeName);
		final boolean doesSubmitButtonBecomeEnabled = externalTypesPage.isSubmitButtonEnabled();
		assertTrue(doesSubmitButtonBecomeEnabled);
	}
}
