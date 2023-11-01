package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTableFieldType;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTaxRuleColumn;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnTaxRulesPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * tests the configuration table on {@link AribaConnTaxRulesPage}
 *
 * @author ssalisbury
 */

public class AribaConnMenuTaxRulesTests extends AribaConnMenuBaseTest
{
	/**
	 * JIRA ticket CARIBA-542
	 */
	@Test(groups = { "ariba_ui","ariba_smoke", "ariba_regression" })
	public void vertexVATTaxTypeFieldTest( )
	{
		final String defaultVertexVATTaxType = "INPUT";

		AribaConnTaxRulesPage taxRulesPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_TAX_RULES);
		taxRulesPage.tenantSelector.selectTenant(tempTenantVariantId);

		final int newRuleIndex = taxRulesPage.addConfigRow();
		AribaConnTaxRuleColumn vatTaxTypeColumn = AribaConnTaxRuleColumn.VERTEX_VAT_TAX_TYPE;

		AribaConnTableFieldType vatTaxFieldInputType = taxRulesPage.getTaxRuleFieldActualInputType(newRuleIndex,
			vatTaxTypeColumn);
		assertEquals(vatTaxFieldInputType, AribaConnTableFieldType.DROPDOWN);

		taxRulesPage.setTaxRuleFieldValue(newRuleIndex, vatTaxTypeColumn, defaultVertexVATTaxType);
		final String preSaveVatTaxTypeValue = taxRulesPage.getTaxRuleFieldValue(newRuleIndex, vatTaxTypeColumn);
		assertEquals(preSaveVatTaxTypeValue, defaultVertexVATTaxType);

		taxRulesPage.submitChanges();
		final String postSaveVatTaxTypeValue = taxRulesPage.getTaxRuleFieldValue(newRuleIndex, vatTaxTypeColumn);
		assertEquals(postSaveVatTaxTypeValue, defaultVertexVATTaxType);
	}
}
