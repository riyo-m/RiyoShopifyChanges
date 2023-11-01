package com.vertex.quality.connectors.ariba.connector.tests.config;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnAccountFieldMappingSourceSelector;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTaxResponseField;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.pages.configuration.AribaConnAccountingFieldMappingPage;
import com.vertex.quality.connectors.ariba.connector.tests.base.AribaConnMenuBaseTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * tests of interacting with the Modify Account Field Mapping
 * configuration menu to set up how the connector translates
 * information about calculated tax amounts in an O Series response
 * message into a format compatible with Ariba's systems
 *
 * @author ssalisbury
 */

public class AribaConnMenuModifyAccountFieldMappingTests extends AribaConnMenuBaseTest
{
	/**
	 * TODO JIRA lacks a story for the corresponding feature
	 * maybe CARIBA-14?
	 *
	 * tests whether each field mapping selector has all of the expected Vertex fields as options
	 *
	 * @author ssalisbury
	 */
	@Test(groups = {"ariba_ui","ariba_regression"})
	public void selectorsContainVertexFieldsTest( )
	{
		AribaConnAccountingFieldMappingPage menuPage = connUtil.loadConfigMenu(testStartPage,
			AribaConnNavConfigurationMenuOption.MODIFY_ACCOUNT_FIELD_MAPPING);
		menuPage.tenantSelector.selectTenant("NewTenant");
		AribaConnAccountFieldMappingSourceSelector[] mappers = AribaConnAccountFieldMappingSourceSelector.values();
		for ( AribaConnAccountFieldMappingSourceSelector mapper : mappers )
		{
			List<String> fieldOptions = menuPage.getSourceFieldOptionNames(mapper);
			boolean fieldOptionsExists = validateVertexFieldOptionsExist(mapper, fieldOptions);
			assertTrue(fieldOptionsExists);

			boolean numFieldOptionsCorrect = validateVertexFieldOptionsCount(mapper, fieldOptions.size());
			assertTrue(numFieldOptionsCorrect);

			boolean areFieldDisplayedNamesCorrect = validateVertexFieldNames(fieldOptions, mapper);

			assertTrue(areFieldDisplayedNamesCorrect);
		}
	}

	/**
	 * checks whether any Vertex fields were successfully retrieved from the
	 * given ariba mapper
	 *
	 * @param mapper       the ariba mapper whose options are being checked
	 * @param vertexFields the results of retrieving the Vertex fields from the
	 *                     given ariba mapper
	 *
	 * @return whether any Vertex fields were successfully retrieved from the
	 * given ariba mapper
	 *
	 * @author ssalisbury
	 */
	protected boolean validateVertexFieldOptionsExist( final AribaConnAccountFieldMappingSourceSelector mapper,
		final List<String> vertexFields )
	{
		boolean vertexFieldsExists = vertexFields != null && vertexFields.size() > 0;
		if ( !vertexFieldsExists )
		{
			String missingVertexFieldsMessage = String.format(
				"No Vertex fields can be retrieved from the ariba mapping selector: %s", mapper.toString());
			VertexLogger.log(missingVertexFieldsMessage, getClass());
		}
		return vertexFieldsExists;
	}

	/**
	 * checks whether the number of Vertex fields retrieved from the given ariba mapper
	 * matches the expected number of Vertex fields
	 *
	 * @param mapper                the ariba mapper whose options are being checked
	 * @param actualNumVertexFields the number of Vertex fields retrieved from the given ariba
	 *                              mapper
	 *
	 * @return whether the given ariba mapper has as many Vertex fields as expected
	 *
	 * @author ssalisbury
	 */
	protected boolean validateVertexFieldOptionsCount( final AribaConnAccountFieldMappingSourceSelector mapper,
		final int actualNumVertexFields )
	{
		AribaConnTaxResponseField[] fields = AribaConnTaxResponseField.values();
		final int expectedNumVertexFields = fields.length;

		boolean numVertexFieldsCorrect = expectedNumVertexFields == actualNumVertexFields;
		if ( !numVertexFieldsCorrect )
		{
			String wrongNumberVertexFieldsMessage = String.format(
				"In ariba mapping selector %s,\n there are %d Vertex fields as options but there should be %d Vertex fields as options",
				mapper.toString(), actualNumVertexFields, fields.length);
			VertexLogger.log(wrongNumberVertexFieldsMessage, getClass());
		}
		return numVertexFieldsCorrect;
	}

	/**
	 * checks whether the given list of Vertex fields contains all of the expected fields
	 * in the expected order for the given ariba mapper
	 *
	 * @param actualFieldNames the actual displayed names of the Vertex field options for the given ariba mapper
	 *                         assumes that this is
	 * @param mapper           the ariba mapper which should contain these Vertex fields
	 *
	 * @return whether the given list of Vertex fields contains all of the expected fields
	 * in the expected order for the given ariba mapper
	 *
	 * @author ssalisbury
	 */
	protected boolean validateVertexFieldNames( final List<String> actualFieldNames,
		final AribaConnAccountFieldMappingSourceSelector mapper )
	{
		boolean areFieldDisplayedNamesCorrect = true;

		AribaConnTaxResponseField[] fields = AribaConnTaxResponseField.values();
		final int numFields = actualFieldNames.size();
		assert numFields == fields.length;
		for ( int f = 0 ; f < numFields && areFieldDisplayedNamesCorrect == true ; f++ )
		{
			String expectedFieldName = fields[f].displayName;
			String actualDisplayedName = actualFieldNames.get(f);

			boolean isFieldNameCorrect = expectedFieldName.equals(actualDisplayedName);
			if ( !isFieldNameCorrect )
			{
				areFieldDisplayedNamesCorrect = false;

				String wrongFieldDisplayedNameMessage = String.format(
					"In ariba mapper dropdown %s,\n Vertex field %s should have displayed name %s but instead has " +
					"displayed name %s", mapper.toString(), fields[f].toString(), expectedFieldName,
					actualDisplayedName);

				VertexLogger.log(wrongFieldDisplayedNameMessage);
			}
		}

		return areFieldDisplayedNamesCorrect;
	}
}
