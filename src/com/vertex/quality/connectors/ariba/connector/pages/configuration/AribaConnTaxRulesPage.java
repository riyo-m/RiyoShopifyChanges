package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.enums.*;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnTableMenuBasePage;
import com.vertex.quality.connectors.ariba.connector.pojos.AribaConnTaxMappingRule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Config Tax Rules page in the Ariba connector config ui
 */
public class AribaConnTaxRulesPage extends AribaConnTableMenuBasePage
{
	protected final By taxRulesTable = By.id("ruleTable");
	protected final By taxRulesTableBody = By.id("ruleTableBody");

	public AribaConnTaxRulesPage( WebDriver driver )
	{
		super(driver, "Tax Rules");
	}

	@Override
	protected WebElement getTable( )
	{
		WebElement tableElem = wait.waitForElementPresent(taxRulesTable);
		return tableElem;
	}

	@Override
	protected WebElement getTableBody( )
	{
		WebElement tableBodyElem = wait.waitForElementPresent(taxRulesTableBody);
		return tableBodyElem;
	}

	/**
	 * Alters {@link #getCellTextValue(int, String, AribaConnTableFieldType)} to get one of the values in a tax mapping
	 * rule
	 */
	public String getTaxRuleFieldValue( final int rowInd, final AribaConnTaxRuleColumn column )
	{
		String fieldValue = getCellTextValue(rowInd, column.getName(), column.getType());
		return fieldValue;
	}

	/**
	 * Alters {@link #setCellTextValue(int, String, AribaConnTableFieldType, String)} to set one of the values in a tax
	 * mapping rule
	 */
	public void setTaxRuleFieldValue( final int rowInd, final AribaConnTaxRuleColumn column, final String fieldValue )
	{
		setCellTextValue(rowInd, column.getName(), column.getType(), fieldValue);
	}

	/**
	 * takes one or more pojos which describe tax rules and then adds the tax rules
	 * which they describe to the current tenant
	 *
	 * @param taxRules descriptions of one or more tax rules
	 *
	 * @return the indices of the newly-created tax rules in the tax rules table
	 */
	public List<Integer> addFilledTaxRules( final AribaConnTaxMappingRule... taxRules )
	{
		List<Integer> taxRuleIndices = new ArrayList<>();

		for ( AribaConnTaxMappingRule taxRule : taxRules )
		{
			final int newTaxRuleIndex = addConfigRow();

			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.ARIBA_COMPONENT_TAX_TYPE,
				taxRule.getAribaComponentTaxType());
			AribaConnVertexTaxType vertexTaxType = taxRule.getVertexType();
			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.VERTEX_TAX_TYPE, vertexTaxType.getVal());
			AribaConnVertexJurisdictionLevel vertexJurisdictionLevel = taxRule.getJurisdictionLevel();
			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.VERTEX_JURISDICTION_LEVEL,
				vertexJurisdictionLevel.getVal());
			AribaConnVertexVATTaxType vatTaxType = taxRule.getVatType();
			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.VERTEX_VAT_TAX_TYPE, vatTaxType.getVal());
			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.VERTEX_IMPOSITION_TYPE,
				taxRule.getVertexImpositionType());
			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.VERTEX_IMPOSITION_NAME,
				taxRule.getVertexImpositionName());
			setTaxRuleFieldValue(newTaxRuleIndex, AribaConnTaxRuleColumn.VERTEX_JURISDICTION_NAME,
				taxRule.getVertexJurisdictionName());

			submitChanges();

			taxRuleIndices.add(newTaxRuleIndex);
		}

		return taxRuleIndices;
	}

	/**
	 * checks what type of input element is inside the specified field cell
	 *
	 * @param rowInd the row which the field is in
	 * @param column the column which the field is under
	 *
	 * @return what type of input element is inside the specified field cell
	 * if the given field cell doesn't contain any input elements of types that are expected in configuration tables
	 * on this site, then this returns null
	 */
	public AribaConnTableFieldType getTaxRuleFieldActualInputType( final int rowInd,
		final AribaConnTaxRuleColumn column )
	{
		//assumes that it's the default type
		AribaConnTableFieldType actualInputType = column.getType();

		//to wait for at least some element of the expected type to load somewhere on the page
		wait.waitForElementDisplayed(actualInputType.getLoc());

		WebElement fieldCell = getConfigCell(rowInd, column.getName());

		List<WebElement> expectedInputElements = element.getWebElements(actualInputType.getLoc(), fieldCell);
		//if there wasn't any element in the cell of the expected type
		if ( expectedInputElements.isEmpty() )
		{
			//now the default is that there's nothing or some unknown element inside the cell
			actualInputType = null;

			for ( AribaConnTableFieldType inputType : AribaConnTableFieldType.values() )
			{
				List<WebElement> possibleInputElements = element.getWebElements(inputType.getLoc(), fieldCell);
				if ( !possibleInputElements.isEmpty() )
				{
					actualInputType = inputType;
					break;
				}
			}
		}

		return actualInputType;
	}
}
