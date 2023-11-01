package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnComponentTypeColumn;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnComponentTaxTypesPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Tests of configuring the mappings between external and O-Series tax types
 * on {@link AribaConnComponentTaxTypesPage}
 *
 * @author ssalisbury
 */

public class AribaConnMenuComponentTaxTypeTests extends AribaConnMenuBaseTest
{
	protected final String defaultComponentTaxTypeName = "AUTOCompTaxType";

	/**
	 * JIRA ticket CARIBA-545
	 */
	@Test(groups = { "ariba_ui", "ariba_regression" })
	public void submitNoNameMappingTest( )
	{
		AribaConnComponentTaxTypesPage componentsPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.ARIBA_COMPONENT_TAX_TYPES_MAINTENANCE);
		final int newComponentMappingIndex = componentsPage.addConfigRow();
		final boolean doesSubmitButtonStartDisabled = !componentsPage.isSubmitButtonEnabled();
		assertTrue(doesSubmitButtonStartDisabled);
		componentsPage.setTaxComponentTextField(newComponentMappingIndex,
			AribaConnComponentTypeColumn.COMPONENT_TAX_TYPE, defaultComponentTaxTypeName);
		final boolean doesSubmitButtonBecomeEnabled = componentsPage.isSubmitButtonEnabled();
		assertTrue(doesSubmitButtonBecomeEnabled);
	}
}
