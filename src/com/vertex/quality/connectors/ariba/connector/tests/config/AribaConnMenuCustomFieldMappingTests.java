package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnAribaFieldType;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnCustomFieldMappingPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaUtilities;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests of interacting with the {@link AribaConnCustomFieldMappingPage}
 * to configure mappings from custom ariba fields to Vertex flex fields
 *
 * @author ssalisbury
 */

public class AribaConnMenuCustomFieldMappingTests extends AribaConnMenuBaseTest
{

	@Test(groups = { "ariba_ui","ariba_smoke","ariba_regression" })
	public void mappingButtonsAccessibleTest( )
	{
		AribaConnCustomFieldMappingPage mappingPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);

		assertTrue(mappingPage.isAddConfigRowButtonEnabled());
		assertTrue(mappingPage.isSubmitButtonEnabled());
	}

	@Test(groups = {"ariba_ui", "ariba_regression" })
	public void createAndModifyMappingTest( )
	{
		final String testAribaName = "test currency";
		final String testVertexField = "Flex Numeric Field 3";

		AribaConnCustomFieldMappingPage mappingPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);

		assertTrue(mappingPage.isAddConfigRowButtonEnabled());

		checkMappingToCustomField(mappingPage, tempTenantDisplayName, AribaConnAribaFieldType.MONEY, testAribaName,
			testVertexField);

		int remainingMappings = mappingPage.getConfigRowCount();
		assertEquals(remainingMappings, 1);
	}

	/**
	 * for JIRA subtask CARIBA-412
	 * https://vertexsmb.atlassian.net/browse/CARIBA-412
	 *
	 * tests the usability of the Usage flex field on the 'Modify Custom Field Mappings' configuration menu
	 * when the default tenant is selected
	 */
	@Test(groups = {"ariba_ui", "ariba_regression" })
	public void mappingUsageFieldTest( )
	{
		final AribaConnAribaFieldType aribaFieldType = AribaConnAribaFieldType.STRING;
		final String aribaFieldName = "AUTOAribaUsageField";
		final String vertexField = "Usage";

		AribaConnCustomFieldMappingPage mappingPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);

		checkMappingToCustomField(mappingPage, defaultTenantDisplayName, aribaFieldType, aribaFieldName, vertexField);
		checkMappingToCustomField(mappingPage, tempTenantDisplayName, aribaFieldType, aribaFieldName, vertexField);
	}

	/**
	 * for JIRA subtask CARIBA-412
	 * https://vertexsmb.atlassian.net/browse/CARIBA-412
	 * tests the usability of the UsageClass flex field option on the 'Modify Custom Field Mappings' configuration menu
	 * This checks that both for the default tenant and for a newly-created tenant
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void mappingUsageClassFieldTest( )
	{
		final AribaConnAribaFieldType aribaFieldType = AribaConnAribaFieldType.STRING;
		final String aribaFieldName = "AUTOAribaUsageField";
		final String vertexField = "UsageClass";

		AribaConnCustomFieldMappingPage mappingPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);

		checkMappingToCustomField(mappingPage, defaultTenantDisplayName, aribaFieldType, aribaFieldName, vertexField);
		checkMappingToCustomField(mappingPage, tempTenantDisplayName, aribaFieldType, aribaFieldName, vertexField);
	}

	/**
	 * JIRA ticket CARIBA-523
	 */
	@Test(groups = { "ariba_ui","ariba_regression" })
	public void flexTextFieldNamingConsistencyTest( )
	{
		final String vertexTextFieldFormat = "Flex Text Field \\d\\d?";
		final int expectedVertexTextFieldCount = 0;

		AribaConnCustomFieldMappingPage mappingPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_CUSTOM_FIELD_MAPPING);
		AribaUtilities utils = new AribaUtilities(driver);
		utils.switchTenant("NewTenant");
		final int mappingIndex = mappingPage.addConfigRow();

		//retrieves the different Vertex text field options that can be mapped to from the Boolean ariba data type
		mappingPage.setAribaType(mappingIndex, AribaConnAribaFieldType.BOOLEAN);
		List<String> booleanVertexFields = mappingPage.getVertexFields(mappingIndex);
		List<String> booleanVertexTextFields = new ArrayList<>(booleanVertexFields);
		booleanVertexTextFields.removeIf(vertexField -> !vertexField.matches(vertexTextFieldFormat));
		int booleanVertexTextFieldCount = booleanVertexTextFields.size();

		//retrieves the different Vertex text field options that can be mapped to from the String ariba data type
		mappingPage.setAribaType(mappingIndex, AribaConnAribaFieldType.STRING);
		List<String> stringVertexFields = mappingPage.getVertexFields(mappingIndex);
		List<String> stringVertexTextFields = new ArrayList<>(stringVertexFields);
		stringVertexTextFields.removeIf(vertexField -> !vertexField.matches(vertexTextFieldFormat));
		int stringVertexTextFieldCount = stringVertexTextFields.size();

		//checks that there are the expected number of vertex text fields that can be mapped to from each ariba data type
		assertEquals(booleanVertexTextFieldCount, expectedVertexTextFieldCount, booleanVertexFields.toString());
		assertEquals(stringVertexTextFieldCount, expectedVertexTextFieldCount, stringVertexFields.toString());
	}

	/**
	 * checks that a mapping can be created and saved for a given vertex flex field
	 * and that the mapping isn't changed after being submitted
	 *
	 * @param mappingPage    the page where the custom field mappings are configured
	 * @param tenantName     which tenant the mapping should be created for
	 * @param aribaFieldType the data type of the ariba field which is being mapped to the given vertex field
	 * @param aribaFieldName the name of the ariba field which is being mapped to the given vertex field
	 * @param vertexField    the vertex field that should be mapped to
	 */
	protected void checkMappingToCustomField( final AribaConnCustomFieldMappingPage mappingPage,
		final String tenantName, final AribaConnAribaFieldType aribaFieldType, final String aribaFieldName,
		final String vertexField )
	{
		final String aribaFieldTypeVal = aribaFieldType.getValue();

		mappingPage.tenantSelector.selectTenant(tenantName);

		final int newMappingIndex = mappingPage.addFilledConfigRow(aribaFieldType, aribaFieldName, vertexField);
		assertTrue(newMappingIndex > 0);

		String actualAribaType = mappingPage.getAribaType(newMappingIndex);
		assertEquals(actualAribaType, aribaFieldTypeVal);
		String actualAribaName = mappingPage.getAribaName(newMappingIndex);
		assertEquals(actualAribaName, aribaFieldName);
		String actualVertexField = mappingPage.getVertexField(newMappingIndex);
		assertEquals(actualVertexField, vertexField);

		mappingPage.deleteConfigRow(newMappingIndex);
		mappingPage.submitChanges();
	}
}
