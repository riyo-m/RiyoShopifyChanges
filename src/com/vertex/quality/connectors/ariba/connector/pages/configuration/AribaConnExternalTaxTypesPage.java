package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTableFieldType;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnTableMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnExternalTaxType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Ariba External Tax Type Maintenance menu page, which
 * lists the ariba tax types which the connector will process &
 * defines certain properties for each
 *
 * @author ssalisbury
 */
public class AribaConnExternalTaxTypesPage extends AribaConnTableMenuBasePage
{
	protected final By externalTypesTable = By.id("ruleTable");
	protected final By externalTypesTableBody = By.id("ruleTableBody");

	public AribaConnExternalTaxTypesPage( final WebDriver driver )
	{
		super(driver, "Summary Tax Types Maintenance");
	}

	@Override
	protected WebElement getTable( )
	{
		WebElement tableElem = wait.waitForElementPresent(externalTypesTable);
		return tableElem;
	}

	@Override
	protected WebElement getTableBody( )
	{
		WebElement tableBodyElem = wait.waitForElementPresent(externalTypesTableBody);
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

	/**
	 * Alters {@link #getCellTextValue(int, String, AribaConnTableFieldType)} to get the name of an Ariba External Tax
	 * Type
	 */
	public String getAribaExternalTaxType( final int rowInd )
	{
		String externalTypeValue = null;
		ExternalTypeColumns column = ExternalTypeColumns.ARIBA_EXTERNAL_TAX_TYPE;
		externalTypeValue = getCellTextValue(rowInd, column.name, column.type);
		return externalTypeValue;
	}

	/**
	 * Alters {@link #isCheckboxCellChecked(int, String)} to check whether an Ariba External Tax Type represents VAT
	 */
	public boolean isVATRepresented( final int rowInd )
	{
		boolean representsVAT = false;
		ExternalTypeColumns column = ExternalTypeColumns.REPRESENTS_VAT;
		representsVAT = isCheckboxCellChecked(rowInd, column.name);
		return representsVAT;
	}

	/**
	 * Alters {@link #isCheckboxCellChecked(int, String)} to check whether an Ariba External Tax Type represents
	 * Self-Assessed
	 */
	public boolean isSelfAssessedRepresented( final int rowInd )
	{
		boolean representsSelfAssessed = false;
		ExternalTypeColumns column = ExternalTypeColumns.REPRESENTS_SELF_ASSESSED;
		representsSelfAssessed = isCheckboxCellChecked(rowInd, column.name);
		return representsSelfAssessed;
	}

	/**
	 * Alters {@link #setCellTextValue(int, String, AribaConnTableFieldType, String)} to set the name of an Ariba
	 * External
	 * Tax Type
	 */
	public void setAribaExternalTaxType( final int rowInd, final String newExternalType )
	{
		ExternalTypeColumns column = ExternalTypeColumns.ARIBA_EXTERNAL_TAX_TYPE;
		setCellTextValue(rowInd, column.name, column.type, newExternalType);
	}

	/**
	 * Alters {@link #toggleCheckboxCell(int, String, boolean, AribaConnTableFieldType)} to toggle whether a particular
	 * Ariba External Tax Type represents VAT
	 */
	public void toggleVATRepresented( final int rowInd, final boolean shouldBeRepresented )
	{
		ExternalTypeColumns column = ExternalTypeColumns.REPRESENTS_VAT;
		toggleCheckboxCell(rowInd, column.name, shouldBeRepresented, column.type);
	}

	/**
	 * Alters {@link #toggleCheckboxCell(int, String, boolean, AribaConnTableFieldType)} to toggle whether a particular
	 * Ariba External Tax Type represents self-assessed
	 */
	public void toggleSelfAssessedRepresented( final int rowInd, final boolean shouldBeRepresented )
	{
		ExternalTypeColumns column = ExternalTypeColumns.REPRESENTS_SELF_ASSESSED;
		toggleCheckboxCell(rowInd, column.name, shouldBeRepresented, column.type);
	}

	/**
	 * fills in the vendor paid tax account instruction field
	 *
	 * @param rowInd                index of the row the field is on
	 * @param taxAccountInstruction account instructions
	 */
	public void setVendorPaidTaxAccountInstruction( final int rowInd, final String taxAccountInstruction )
	{
		ExternalTypeColumns column = ExternalTypeColumns.VENDOR_PAID_TAX_ACCOUNTING_INSTRUCTIONS;
		setCellTextValue(rowInd, column.name, column.type, taxAccountInstruction);
	}

	/**
	 * takes one or more pojos which describe external tax types and then adds the external tax types
	 * which they describe to the current tenant
	 *
	 * @param externalTypes descriptions of one or more external tax types
	 *
	 * @return the indices of the newly-created tax types in the external tax types table
	 */
	public List<Integer> addFilledExternalTaxTypes( final AribaConnExternalTaxType... externalTypes )
	{
		List<Integer> externalTypeIndices = new ArrayList<>();

		for ( AribaConnExternalTaxType externalType : externalTypes )
		{
			final int newExternalTypeIndex = addConfigRow();
			setAribaExternalTaxType(newExternalTypeIndex, externalType.getExternalTaxTypeName());
			toggleVATRepresented(newExternalTypeIndex, externalType.isRepresentsVAT());
			toggleSelfAssessedRepresented(newExternalTypeIndex, externalType.isRepresentsSelfAssessed());
			if ( !externalType
				.getVendorPaidTaxAccountInstructions()
				.isEmpty() )
			{
				setVendorPaidTaxAccountInstruction(newExternalTypeIndex,
					externalType.getVendorPaidTaxAccountInstructions());
			}

			submitChanges();

			externalTypeIndices.add(newExternalTypeIndex);
		}

		return externalTypeIndices;
	}

	/**
	 * describes the different columns in the table of Ariba tax types that Vertex accepts
	 *
	 * fields are either dropdowns or checkboxes
	 *
	 * @author ssalisbury
	 */
	protected enum ExternalTypeColumns
	{
		ARIBA_EXTERNAL_TAX_TYPE("Ariba External Tax Type", AribaConnTableFieldType.TEXT_FIELD),
		REPRESENTS_VAT("Represents VAT", AribaConnTableFieldType.CHECKBOX),
		REPRESENTS_SELF_ASSESSED("Represents Self-assessed", AribaConnTableFieldType.CHECKBOX),
		VENDOR_PAID_TAX_ACCOUNTING_INSTRUCTIONS("Vendor Paid Tax Account Instruction",
			AribaConnTableFieldType.TEXT_FIELD);
		public final String name;
		public final AribaConnTableFieldType type;

		ExternalTypeColumns( final String columnName, final AribaConnTableFieldType fieldType )
		{
			this.name = columnName;
			this.type = fieldType;
		}
	}
}
