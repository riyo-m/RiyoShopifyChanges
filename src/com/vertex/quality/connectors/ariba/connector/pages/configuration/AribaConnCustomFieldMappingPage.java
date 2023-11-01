package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnAribaFieldType;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTableFieldType;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnTableMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnCustomFieldMapping;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * the page for mapping various ariba fields to vertex flex fields
 *
 * @author ssalisbury
 */
public class AribaConnCustomFieldMappingPage extends AribaConnTableMenuBasePage
{
	protected final By mappingsTable = By.id("dataTable");

	protected final By errorMessage = By.id("errMsg");

	public AribaConnCustomFieldMappingPage( WebDriver driver )
	{
		super(driver, "Custom Field Mapping");
	}

	@Override
	protected WebElement getTable( )
	{
		WebElement tableElem = wait.waitForElementPresent(mappingsTable);
		return tableElem;
	}

	@Override
	protected WebElement getTableBody( )
	{
		WebElement tableElem = getTable();
		WebElement tableBodyElem = wait.waitForElementPresent(By.tagName("tbody"), tableElem);
		return tableBodyElem;
	}

	/**
	 * Alters {@link #getCellTextValue(int, String, AribaConnTableFieldType)} to get a mapping's Ariba Type
	 */
	public String getAribaType( final int rowInd )
	{
		String aribaTypeValue = null;
		MappingColumns column = MappingColumns.ARIBA_DATA_TYPE;
		aribaTypeValue = getCellTextValue(rowInd, column.name, column.type);
		return aribaTypeValue;
	}

	/**
	 * Alters {@link #getCellTextValue(int, String, AribaConnTableFieldType)} to get a mapping's Ariba Name
	 */
	public String getAribaName( final int rowInd )
	{
		String aribaNameValue = null;
		MappingColumns column = MappingColumns.ARIBA_NAME;
		aribaNameValue = getCellTextValue(rowInd, column.name, column.type);
		return aribaNameValue;
	}

	/**
	 * Alters {@link #getCellTextValue(int, String, AribaConnTableFieldType)} to get a mapping's Vertex Field
	 */
	public String getVertexField( final int rowInd )
	{
		String vertexFieldValue = null;
		MappingColumns column = MappingColumns.VERTEX_FIELD;
		vertexFieldValue = getCellTextValue(rowInd, column.name, column.type);
		return vertexFieldValue;
	}

	/**
	 * Alters {@link #setCellTextValue(int, String, AribaConnTableFieldType, String)} to set a mapping's Ariba Type
	 */
	public void setAribaType( final int rowInd, final AribaConnAribaFieldType newAribaType )
	{
		MappingColumns column = MappingColumns.ARIBA_DATA_TYPE;
		setCellTextValue(rowInd, column.name, column.type, newAribaType.getValue());
	}

	/**
	 * Alters {@link #setCellTextValue(int, String, AribaConnTableFieldType, String)} to set a mapping's Ariba Name
	 */
	public void setAribaName( final int rowInd, final String newAribaName )
	{
		MappingColumns column = MappingColumns.ARIBA_NAME;
		setCellTextValue(rowInd, column.name, column.type, newAribaName);
	}

	/**
	 * Alters {@link #setCellTextValue(int, String, AribaConnTableFieldType, String)} to set a mapping's Vertex Field
	 */
	public void setVertexField( final int rowInd, final String newVertexField )
	{
		MappingColumns column = MappingColumns.VERTEX_FIELD;
		setCellTextValue(rowInd, column.name, column.type, newVertexField);
	}

	//TODO maybe replace usages of this with addFilledConfigRows()

	/**
	 * creates a new custom field mapping, fills in its values, and saves it to the database
	 *
	 * @param aribaFieldType the datatype of the ariba field being mapped
	 * @param aribaFieldName the name of the ariba field being mapped
	 * @param vertexField    the flex field that the ariba field is being mapped to
	 *
	 * @return the index of the new field mapping
	 * 1-based indexing for easier-to-read tests
	 */
	public int addFilledConfigRow( final AribaConnAribaFieldType aribaFieldType, final String aribaFieldName,
		final String vertexField )
	{
		final int newMappingIndex = addConfigRow();
		setAribaType(newMappingIndex, aribaFieldType);
		setAribaName(newMappingIndex, aribaFieldName);
		setVertexField(newMappingIndex, vertexField);
		submitChanges();

		return newMappingIndex;
	}

	/**
	 * takes one or more pojos which describe custom field mappings and then adds the custom field mappings
	 * which they describe to the current tenant
	 *
	 * @param mappings descriptions of one or more custom field mappings
	 *
	 * @return the indices of the newly-created mappings in the custom field mappings table
	 */
	public List<Integer> addFilledConfigRows( final AribaConnCustomFieldMapping... mappings )
	{
		List<Integer> mappingIndices = new ArrayList<>();

		for ( AribaConnCustomFieldMapping mapping : mappings )
		{
			final int newMappingIndex = addConfigRow();
			setAribaType(newMappingIndex, mapping.getAribaDataType());
			setAribaName(newMappingIndex, mapping.getAribaName());
			setVertexField(newMappingIndex, mapping.getVertexField());
			submitChanges();

			mappingIndices.add(newMappingIndex);
		}

		return mappingIndices;
	}

	/**
	 * Alters {@link #getConfigDropdownOptions(int, String)} to identify which Ariba Data Types can be chosen
	 * for a particular mapping (although each mapping has the same ariba data type options)
	 */
	public List<String> getAribaDataTypes( final int rowInd )
	{
		List<String> aribaTypes = getConfigDropdownOptions(rowInd, MappingColumns.ARIBA_DATA_TYPE.name);
		return aribaTypes;
	}

	/**
	 * Alters {@link #getConfigDropdownOptions(int, String)} to identify which Vertex Fields can be chosen for
	 * a particular mapping
	 * This can change from one mapping to another based on what Ariba Data Type is chosen for each mapping
	 */
	public List<String> getVertexFields( final int rowInd )
	{
		List<String> vertexFields = getConfigDropdownOptions(rowInd, MappingColumns.VERTEX_FIELD.name);
		return vertexFields;
	}

	/**
	 * this describes the columns of the table for mapping ariba fields to vertex flex fields
	 *
	 * @author ssalisbury
	 */
	protected enum MappingColumns
	{
		ARIBA_DATA_TYPE("Ariba Data Type", AribaConnTableFieldType.DROPDOWN),
		ARIBA_NAME("Ariba Name", AribaConnTableFieldType.TEXT_FIELD),
		VERTEX_FIELD("Vertex Field", AribaConnTableFieldType.DROPDOWN);

		public final String name;
		public final AribaConnTableFieldType type;

		MappingColumns( final String headerName, final AribaConnTableFieldType fieldType )
		{
			this.name = headerName;
			this.type = fieldType;
		}
	}
}
