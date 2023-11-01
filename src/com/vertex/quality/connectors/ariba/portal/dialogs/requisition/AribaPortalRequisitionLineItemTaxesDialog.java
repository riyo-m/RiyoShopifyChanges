package com.vertex.quality.connectors.ariba.portal.dialogs.requisition;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.portal.dialogs.base.AribaPortalDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * dialog popup that displays a breakdown of a line item's overall tax into its component taxes
 *
 * @author ssalisbury dgorecki osabha
 */
public class AribaPortalRequisitionLineItemTaxesDialog extends AribaPortalDialog
{
	protected final By popupCloseButtonRow = By.className("a-tax-details-popup-button-bottom");
	protected final By popupContainer = By.className("w-dlg-dialog");
	protected final By taxCode = By.className("a-tax-details-popup-taxcodedetails");
	protected final By taxComponentsContainer = By.className("a-tax-details-popup-components");
	protected final By taxComponentsHeaderCell = By.className("w-tbl-hd");
	protected final By taxRow = By.className("tableRow1");
	protected final By totalTaxContainer = By.className("w-arw-summarygroup");
	protected final By totalTaxAmount = By.className("ffp-noedit");
	protected final By taxRowCell = By.className("w-tbl-cell");

	public AribaPortalRequisitionLineItemTaxesDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * closes this dialog popup so that the underlying page can be accessed
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public void closeRequisitionLineItemTaxes( )
	{
		WebElement buttonRow = wait.waitForElementPresent(popupCloseButtonRow);
		WebElement closeButton = wait.waitForElementPresent(By.tagName("button"), buttonRow);
		click.clickElement(closeButton);

		wait.waitForElementNotDisplayed(popupContainer);
	}

	/**
	 * retrieves the line items' tax code from the dialog
	 *
	 * @return the tax code of the line item
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public String getItemTaxCode( )
	{
		wait.waitForElementPresent(taxCode);
		WebElement taxCodeElement = wait.waitForElementDisplayed(taxCode);
		String taxCode = text.getElementText(taxCodeElement);
		return taxCode;
	}

	/**
	 * retrieves the number of components in the taxes on this line item
	 *
	 * @return the number of components in the taxes on this line item
	 *
	 * @author ssalisbury dgorecki
	 */
	public int getItemTaxComponentsCount( )
	{
		wait.waitForElementPresent(taxComponentsContainer);
		WebElement container = wait.waitForElementDisplayed(taxComponentsContainer);

		wait.waitForElementPresent(taxRow);
		wait.waitForElementDisplayed(taxRow);

		List<WebElement> taxRows = wait.waitForAllElementsDisplayed(taxRow, container);

		int taxCount = taxRows.size();
		return taxCount;
	}

	/**
	 * retrieves the total tax on this line item
	 *
	 * @return the total tax on this line item
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public String getItemTotalTax( )
	{
		wait.waitForElementPresent(totalTaxContainer);
		WebElement taxContainerElem = wait.waitForElementDisplayed(totalTaxContainer);

		wait.waitForElementPresent(totalTaxAmount);
		wait.waitForElementDisplayed(totalTaxAmount);

		WebElement taxAmountElem = wait.waitForElementDisplayed(totalTaxAmount, taxContainerElem);

		String taxAmount = text.getElementText(taxAmountElem);

		return taxAmount;
	}

	/**
	 * retrieves the name of a component of this line item's taxes
	 *
	 * @param taxComponentIndex which component's name to retrieve
	 *
	 * @return the name of a component of this line item's taxes
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public String getTaxComponentName( final int taxComponentIndex )
	{
		String columnLabel = "Name";

		int cellIndex = getColumnIndex(columnLabel);

		WebElement taxRow = getTaxRow(taxComponentIndex);

		WebElement cell = wait
			.waitForAllElementsDisplayed(taxRowCell, taxRow)
			.get(cellIndex);

		String componentName = text.getElementText(cell);
		componentName = componentName.trim();

		//ariba adds a space at the end of each line in this description string even though
		//the description string in the Ariba Response XML has no spaces at the ends of lines
		componentName = componentName.replaceAll(" \n", "\n");

		return componentName;
	}

	/**
	 * checks whether a component of this line item's taxes is deductible
	 *
	 * @param taxComponentIndex which component's deductibility to check
	 *
	 * @return whether a component of this line item's taxes is deductible
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public String getTaxComponentIsDeductible( final int taxComponentIndex )
	{
		String columnLabel = "Is Deductible";

		int cellIndex = getColumnIndex(columnLabel);

		WebElement taxRow = getTaxRow(taxComponentIndex);

		WebElement cell = wait
			.waitForAllElementsDisplayed(taxRowCell, taxRow)
			.get(cellIndex);

		String value = text.getElementText(cell);
		return value;
	}

	/**
	 * retrieves the rate of a component of this line item's taxes
	 *
	 * @param taxComponentIndex which component's rate to retrieve
	 *
	 * @return the rate of a component of this line item's taxes
	 *
	 * @author ssalisbury dgorecki osabha
	 */
	public String get_tax_component_rate( final int taxComponentIndex )
	{
		String columnLabel = "Rate";

		int cellIndex = getColumnIndex(columnLabel);

		WebElement taxRow = getTaxRow(taxComponentIndex);

		WebElement cell = wait
			.waitForAllElementsDisplayed(taxRowCell, taxRow)
			.get(cellIndex);

		String value = text.getElementText(cell);
		return value;
	}

	/**
	 * retrieves the portion of this line item's price that this component of this line item's
	 * taxes applies to
	 *
	 * @param taxComponentIndex which component's applicable portion of the line item's price to
	 *                          retrieve
	 *
	 * @return the portion of this line item's price that this component of this line item's
	 * taxes applies to
	 *
	 * @author ssalisbury dgorecki
	 */
	public String getTaxComponentAppliedOn( final int taxComponentIndex )
	{
		String columnLabel = "Applied On";

		int cellIndex = getColumnIndex(columnLabel);

		WebElement taxRow = getTaxRow(taxComponentIndex);

		WebElement cell = wait
			.waitForAllElementsDisplayed(taxRowCell, taxRow)
			.get(cellIndex);

		// need to do this extra step for applied on because Formula String appears
		// in the same cell in the table
		WebElement appliedOnElement = wait.waitForElementDisplayed(By.tagName("div"), cell);
		String value = text.getElementText(appliedOnElement);

		return value;
	}

	/**
	 * retrieves the amount of tax from a component of this line item's taxes
	 *
	 * @param taxComponentIndex which component's amount of taxes to retrieve
	 *
	 * @return the amount of tax from a component of this line item's taxes
	 *
	 * @author ssalisbury dgorecki
	 */
	public String getTaxComponentAmount( final int taxComponentIndex )
	{
		String returnValueColumnLabel = "Amount";

		int returnValueCellIndex = getColumnIndex(returnValueColumnLabel);

		WebElement taxRow = getTaxRow(taxComponentIndex);

		WebElement cell = wait
			.waitForAllElementsDisplayed(taxRowCell, taxRow)
			.get(returnValueCellIndex);

		String value = text.getElementText(cell);
		return value;
	}

	/**
	 * retrieves the row that contains information about a component of this line item's taxes
	 *
	 * @param taxComponentIndex which component's row to retrieve
	 *
	 * @return the row that contains information about a component of this line item's taxes
	 *
	 * @author ssalisbury dgorecki
	 */
	private WebElement getTaxRow( final int taxComponentIndex )
	{
		List<WebElement> taxRows = getTaxRows();
		WebElement taxRow = taxRows.get(taxComponentIndex - 1);
		return taxRow;
	}

	/**
	 * retrieves the rows that contains information about the components of this line item's taxes
	 *
	 * @return the rows that contains information about the components of this line item's taxes
	 *
	 * @author legacyAribaProgrammer, ssalisbury
	 */
	private List<WebElement> getTaxRows( )
	{
		wait.waitForElementPresent(taxComponentsContainer);
		wait.waitForElementDisplayed(taxComponentsContainer);

		wait.waitForElementPresent(taxRow);
		wait.waitForElementDisplayed(taxRow);

		List<WebElement> taxRows = driver
			.findElement(taxComponentsContainer)
			.findElements(taxRow);
		return taxRows;
	}

	/**
	 * identifies which column contains the values described by the given column label
	 *
	 * @param targetColumn the column label which describes the desired values
	 *
	 * @return the index of the column which contains the desired values
	 *
	 * @author ssalisbury dgorecki
	 */
	private int getColumnIndex( final String targetColumn )
	{
		//TODO these could all be consolidated and use an enum now that we're done with python

		WebElement itemsTable = wait.waitForElementDisplayed(taxComponentsContainer);
		List<WebElement> columns = wait.waitForAllElementsDisplayed(taxComponentsHeaderCell, itemsTable);

		int numColumns = columns.size();
		int targetColumnIndex = -1;

		for ( int i = 0 ; i < numColumns ; i++ )
		{
			WebElement column = columns.get(i);
			String columnName = text.getElementText(column);

			if ( targetColumn.equals(columnName) )
			{
				targetColumnIndex = i;
				break;
			}
		}
		return targetColumnIndex;
	}
}
