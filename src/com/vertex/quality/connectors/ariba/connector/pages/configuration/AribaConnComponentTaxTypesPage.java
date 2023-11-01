package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.dialogs.AribaConnEditExternalTaxTypesDialog;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnComponentTypeColumn;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTableFieldType;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnTableMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnComponentTaxType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Ariba Component Tax Types Maintenance menu page,
 * which configures how different tax types from Ariba should be interpreted by O-Series
 * and how they're categorized in tax-journals
 *
 * @author ssalisbury
 */
public class AribaConnComponentTaxTypesPage extends AribaConnTableMenuBasePage
{
	protected final By componentsTable = By.id("typeTable");
	protected final By componentsTableBody = By.id("typeTableBody");

	public AribaConnEditExternalTaxTypesDialog externalTypesDialog;

	public AribaConnComponentTaxTypesPage( final WebDriver driver )
	{
		super(driver, "Component Tax Types Maintenance");
		this.externalTypesDialog = initializePageObject(AribaConnEditExternalTaxTypesDialog.class, this);
	}

	@Override
	protected WebElement getTable( )
	{
		WebElement tableElem = wait.waitForElementPresent(componentsTable);
		return tableElem;
	}

	@Override
	protected WebElement getTableHeader( )
	{
		WebElement tableHeader = null;
		WebElement tableElem = getTable();
		WebElement headerContainer = wait.waitForElementPresent(By.tagName("thead"), tableElem);
		List<WebElement> headerRows = wait.waitForAllElementsPresent(By.tagName("tr"), headerContainer);

		//the first column header element has the 'first-col' class. this distinguishes the header row
		// with column headers from the one that labels a group of columns as 'Posting Keys'
		for ( WebElement row : headerRows )
		{
			List<WebElement> firstColHeaders = element.getWebElements(firstColumnHeader, row);
			if ( firstColHeaders.size() > 0 )
			{
				tableHeader = row;
				break;
			}
		}
		return tableHeader;
	}

	@Override
	protected WebElement getTableBody( )
	{
		WebElement tableBodyElem = wait.waitForElementPresent(componentsTableBody);
		return tableBodyElem;
	}

	@Override
	protected WebElement setCellTextValue( final int rowInd, final String columnName,
		final AribaConnTableFieldType fieldType, final String newCellValue )
	{
		WebElement cellInput = super.setCellTextValue(rowInd, columnName, fieldType, newCellValue);
		if ( cellInput != null )
		{
			text.pressTab(cellInput);
		}
		return cellInput;
	}

	//TODO get currently selected tax types (get cell in table, get its title, then tokenize by ',' (no spaces))

	/**
	 * Alters {@link #clickConfigCell(int, String, AribaConnTableFieldType)} to open the dialog for editing the external
	 * tax types of a component tax type
	 */
	public void editExternalTaxTypes( final int rowInd )
	{
		AribaConnComponentTypeColumn externalTypesColumn = AribaConnComponentTypeColumn.EXTERNAL_TAX_TYPES;
		clickConfigCell(rowInd, externalTypesColumn.getName(), externalTypesColumn.getType());
	}

	/**
	 * Alters {@link #isCheckboxCellChecked(int, String)} to determine whether a binary field in an Ariba Component Tax
	 * Type is checked
	 */
	public boolean isTaxComponentFieldChecked( final int rowInd, final AribaConnComponentTypeColumn column )
	{
		boolean isChecked = isCheckboxCellChecked(rowInd, column.getName());
		return isChecked;
	}

	/**
	 * Alters {@link #toggleCheckboxCell(int, String, boolean, AribaConnTableFieldType)} to change whether a binary
	 * field
	 * in an Ariba Component Tax Type is checked
	 */
	public void toggleTaxComponentField( final int rowInd, final AribaConnComponentTypeColumn column,
		final boolean shouldBeChecked )
	{
		toggleCheckboxCell(rowInd, column.getName(), shouldBeChecked, column.getType());
	}

	/**
	 * Alters {@link #getCellTextValue(int, String, AribaConnTableFieldType)} to get the text contents of a field in an
	 * Ariba Component Tax Type
	 */
	public String getTaxComponentTextFieldValue( final int rowInd, final AribaConnComponentTypeColumn column )
	{
		String fieldValue = getCellTextValue(rowInd, column.getName(), column.getType());
		return fieldValue;
	}

	/**
	 * Alters {@link #setCellTextValue(int, String, AribaConnTableFieldType, String)} to get the text contents of a
	 * field
	 * in an Ariba Component Tax Type
	 */
	public void setTaxComponentTextField( final int rowInd, final AribaConnComponentTypeColumn column,
		final String newFieldValue )
	{
		setCellTextValue(rowInd, column.getName(), column.getType(), newFieldValue);
	}
	//This is mainly a place holder. waiting on development to make the change in the dialog to avoid drag and drop

	/**
	 * fills in the target tax types in the edit external tax types dialog
	 *
	 * @param rowInd           index of the row to fill
	 * @param column           type of the column to fill
	 * @param externalTaxTypes target tax types to select
	 */
	public void fillExternalTaxTypesField( final int rowInd, final AribaConnComponentTypeColumn column,
		final List<String> externalTaxTypes )
	{
		editExternalTaxTypes(rowInd);
		String type = externalTaxTypes.get(0);
		externalTypesDialog.getSelectedExternalTypes();
		externalTypesDialog.submitChanges();
		externalTypesDialog.closeExternalTypesEditor();
		externalTypesDialog.abortChanges();
		externalTypesDialog.getAvailableExternalTypes();
	}

	/**
	 * takes one or more pojos which describe component tax types and then adds the component tax types
	 * which they describe to the current tenant
	 *
	 * @param componentTypes descriptions of one or more component tax types
	 *
	 * @return the indices of the newly-created tax types in the component tax types table
	 */
	public List<Integer> addFilledComponentTaxTypes( final AribaConnComponentTaxType... componentTypes )
	{
		List<Integer> componentTypeIndices = new ArrayList<>();

		for ( AribaConnComponentTaxType componentType : componentTypes )
		{
			final int newComponentTypeIndex = addConfigRow();

			setTaxComponentTextField(newComponentTypeIndex, AribaConnComponentTypeColumn.COMPONENT_TAX_TYPE,
				componentType.getComponentTaxType());
			setTaxComponentTextField(newComponentTypeIndex,
				AribaConnComponentTypeColumn.SELF_ASSESSES_TAX_ACCOUNT_INSTRUCTION,
				componentType.getSelfAssessedTaxAccountInstruction());
			//TODO ACCESS THE EXTERNAL TAX TYPES AND SELECT THE ONES MATCHING THE LIST
			fillExternalTaxTypesField(newComponentTypeIndex, AribaConnComponentTypeColumn.EXTERNAL_TAX_TYPES,
				componentType.getExternalTaxTypes());
			submitChanges();

			componentTypeIndices.add(newComponentTypeIndex);
		}

		return componentTypeIndices;
	}
}
