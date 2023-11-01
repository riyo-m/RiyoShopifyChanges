package com.vertex.quality.connectors.ariba.connector.pages.base;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.ariba.connector.enums.AribaConnTableFieldType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * represents one of the website's configuration menu pages which
 * lets the user create a variable number of that menu's type of
 * configurations and displays those configurations in a table
 *
 * @author ssalisbury
 */
public abstract class AribaConnTableMenuBasePage extends AribaConnMenuBasePage
{
	protected final By addRowButton = By.id("addRow");

	protected final By firstColumnHeader = By.className("first-col");

	protected final String deleteMappingButtonName = "Delete";

	public AribaConnTableMenuBasePage( final WebDriver driver, final String title )
	{
		super(driver, title);
	}

	/**
	 * finds the element which contains the entire configuration table on the page
	 *
	 * @return the element which contains the entire configuration table on the page
	 *
	 * @author ssalisbury
	 */
	abstract protected WebElement getTable( );

	/**
	 * finds the element which contains the header row of the configuration table
	 *
	 * @return the element which contains the header row of the configuration table
	 *
	 * @author ssalisbury
	 */
	protected WebElement getTableHeader( )
	{
		WebElement tableHeader = null;
		WebElement tableElem = getTable();
		WebElement headerContainer = wait.waitForElementPresent(By.tagName("thead"), tableElem);
		tableHeader = wait.waitForElementPresent(By.tagName("tr"), headerContainer);
		return tableHeader;
	}

	/**
	 * finds the element which contains the content of the configuration table
	 *
	 * @return the element which contains the content of the configuration table
	 *
	 * @author ssalisbury
	 */
	abstract protected WebElement getTableBody( );

	/**
	 * checks whether the button for adding a new configuration row can possibly be clicked
	 *
	 * @return whether the button for adding a new configuration row can possibly be clicked
	 *
	 * @author ssalisbury
	 */
	public boolean isAddConfigRowButtonEnabled( )
	{
		boolean isAccessible = element.isElementEnabled(addRowButton);
		return isAccessible;
	}

	/**
	 * clicks the button to add a new row of configurations of the connector
	 *
	 * @return the index of the configuration row that was created by this
	 * uses 1-based indexing for more readable tests
	 * on failure, returns 0
	 *
	 * @author ssalisbury
	 */
	public int addConfigRow( )
	{
		int newRowIndex = 0;
		click.clickElementCarefully(addRowButton);

		List<WebElement> rows = getConfigRows();
		newRowIndex = rows.size();
		return newRowIndex;
	}

	/**
	 * gets the rows of the table of configuration changes
	 *
	 * @return a list of the configuration changes
	 * on error, returns an empty list
	 *
	 * @author ssalisbury
	 */
	protected List<WebElement> getConfigRows( )
	{
		List<WebElement> configRows = new ArrayList<>();
		WebElement tableBodyElem = getTableBody();
		configRows = element.getWebElements(By.tagName("tr"), tableBodyElem);
		return configRows;
	}

	/**
	 * checks how many rows there are in the configuration table
	 *
	 * @return how many rows there are in the configuration table
	 *
	 * @author ssalisbury
	 */
	public int getConfigRowCount( )
	{
		int rowCount = -1;
		List<WebElement> rows = getConfigRows();
		rowCount = rows.size();
		return rowCount;
	}

	/**
	 * Overloads {@link #getConfigCell(int, int)} as an adapter between
	 * the test case functions' queries and the table access logic
	 *
	 * Takes parameters testRowInd and columnName,
	 * converts those into a 0-based row index and the index of that column,
	 * and then passes those indices to {@link #getConfigCell(int, int)}
	 *
	 * @param testRowInd a 1-based row index
	 * @param columnName a column name from a test case function
	 */
	protected WebElement getConfigCell( final int testRowInd, final String columnName )
	{
		WebElement configCell = null;

		final int actualRowInd = testRowInd - 1;
		final int columnInd = getColumnIndex(columnName);
		configCell = getConfigCell(actualRowInd, columnInd);

		return configCell;
	}

	/**
	 * finds one cell in a configuration table
	 *
	 * @param rowInd    which configuration row the cell is part of
	 *                  0-based indexing
	 * @param columnInd which column the cell is in
	 *
	 * @return a certain cell in a configuration table
	 *
	 * @author ssalisbury
	 */
	protected WebElement getConfigCell( final int rowInd, final int columnInd )
	{
		WebElement configCell = null;

		List<WebElement> rows = getConfigRows();

		if ( rowInd < rows.size() )
		{
			WebElement row = rows.get(rowInd);

			List<WebElement> rowCells = wait.waitForAnyElementsDisplayed(By.tagName("td"), row);
			rowCells.removeIf(cell -> !element.isElementDisplayed(cell));

			if ( columnInd < rowCells.size() )
			{
				configCell = rowCells.get(columnInd);
			}
			else
			{
				String fewColumnsErrorMessage = String.format(
					"Could not find a cell under column %d in a row with only %d columns", columnInd, rowCells.size());
				VertexLogger.log(fewColumnsErrorMessage, VertexLogLevel.ERROR);
			}
		}
		else
		{
			String fewRowsErrorMessage = String.format("Could not find row %d in a list of only %d rows", rowInd,
				rows.size());
			VertexLogger.log(fewRowsErrorMessage, VertexLogLevel.ERROR);
		}

		return configCell;
	}

	/**
	 * gets one of the values in a row
	 *
	 * @param rowInd     which configuration row is being examined
	 *                   Uses 1-based indexing for more readable tests
	 * @param columnName which part of the configuration row is being examined
	 * @param fieldType  what kind of input field is in this cell
	 *                   only works with dropdowns and text fields
	 *
	 * @return the value of one part of the given configuration row
	 *
	 * @author ssalisbury
	 */
	protected String getCellTextValue( final int rowInd, final String columnName,
		final AribaConnTableFieldType fieldType )
	{
		String configValue = null;

		WebElement configCell = getConfigCell(rowInd, columnName);

		if ( configCell != null )
		{
			WebElement cellInput = wait.waitForElementEnabled(fieldType.getLoc(), configCell);
			if ( fieldType.equals(AribaConnTableFieldType.DROPDOWN) )
			{
				WebElement selectedMappingOption = dropdown.getDropdownSelectedOption(cellInput);
				configValue = text.getElementText(selectedMappingOption);
			}
			else if ( fieldType.equals(AribaConnTableFieldType.TEXT_FIELD) )
			{
				configValue = attribute.getElementAttribute(cellInput, "value");
			}
			else
			{
				final String badFieldTypeMessage = String.format("Cannot get the text value of a field of type %s",
					fieldType.toString());
				VertexLogger.log(badFieldTypeMessage, VertexLogLevel.ERROR);
			}
		}

		return configValue;
	}

	/**
	 * sets one part of a row of configurations to a new value
	 *
	 * @param rowInd       which configuration row is being modified
	 *                     Uses 1-based indexing for more readable tests
	 * @param columnName   which part of the configuration row is being examined
	 * @param fieldType    what kind of input field is in this cell
	 *                     only works with dropdowns and text fields
	 * @param newCellValue the new value for the given part of the given configuration row
	 *
	 * @author ssalisbury
	 */
	protected WebElement setCellTextValue( final int rowInd, final String columnName,
		final AribaConnTableFieldType fieldType, final String newCellValue )
	{
		WebElement cellInput = null;
		WebElement configCell = getConfigCell(rowInd, columnName);

		if ( configCell != null )
		{
			cellInput = wait.waitForElementEnabled(fieldType.getLoc(), configCell);
			if ( fieldType.equals(AribaConnTableFieldType.DROPDOWN) )
			{
				click.clickElement(cellInput);
				dropdown.selectDropdownByDisplayName(cellInput, newCellValue);
			}
			else if ( fieldType.equals(AribaConnTableFieldType.TEXT_FIELD) )
			{
				text.enterText(cellInput, newCellValue);
			}
			else
			{
				final String badFieldTypeMessage = String.format(
					"Cannot change the state of a field of type %s to the text value [%s]", fieldType.toString(),
					newCellValue);
				VertexLogger.log(badFieldTypeMessage, VertexLogLevel.ERROR);
			}
		}
		return cellInput;
	}

	/**
	 * finds the desired cell in the table by passing its parameters rowInd and columnName to
	 * {@link #getConfigCell(int, String)}
	 * then clicks on the input field in that cell
	 */
	protected void clickConfigCell( final int rowInd, final String columnName, final AribaConnTableFieldType fieldType )
	{
		WebElement configCell = getConfigCell(rowInd, columnName);

		if ( configCell != null )
		{
			WebElement cellInput = wait.waitForElementEnabled(fieldType.getLoc(), configCell);
			click.clickElement(cellInput);
		}
	}

	/**
	 * finds the desired cell in the table by passing its parameters rowInd and columnName to
	 * {@link #getConfigCell(int, String)}
	 * then determines whether the checkbox inside that cell is checked
	 *
	 * @return whether the checkbox inside that cell is checked
	 */
	protected boolean isCheckboxCellChecked( final int rowInd, final String columnName )
	{
		boolean isChecked = false;
		final By checkboxInputLoc = AribaConnTableFieldType.CHECKBOX.getLoc();

		WebElement configCell = getConfigCell(rowInd, columnName);
		if ( configCell != null )
		{
			WebElement checkboxElem = wait.waitForElementDisplayed(checkboxInputLoc, configCell);
			isChecked = checkbox.isCheckboxChecked(checkboxElem);
		}

		return isChecked;
	}

	/**
	 * finds the desired cell in the table by passing its @parameters rowInd and columnName to
	 * {@link #getConfigCell(int, String)} and {@link #isCheckboxCellChecked(int, String)}
	 * It then checks whether a checkbox in that cell is checked and, if necessary, changes it to have the desired
	 * state (based on shouldBeChecked)
	 *
	 * @param shouldBeChecked whether the checkbox should or should not be checked after
	 *                        this function finishes
	 * @param fieldType       what kind of input field this cell contains
	 *                        only works with checkboxes
	 */
	protected void toggleCheckboxCell( final int rowInd, final String columnName, final boolean shouldBeChecked,
		final AribaConnTableFieldType fieldType )
	{
		assert fieldType.equals(AribaConnTableFieldType.CHECKBOX);

		boolean isChecked = isCheckboxCellChecked(rowInd, columnName);
		if ( isChecked != shouldBeChecked )
		{
			clickConfigCell(rowInd, columnName, fieldType);
		}
		else
		{
			final String redundantToggleMessage = String.format(
				"The checkbox for %s was already %s checked before the test tried to toggle it to become %s checked",
				columnName, shouldBeChecked ? "" : "not", shouldBeChecked ? "" : "not");
			VertexLogger.log(redundantToggleMessage, VertexLogLevel.INFO);
		}
	}

	/**
	 * finds which column has the given name in its column header
	 *
	 * @param columnName the name of the target column
	 *
	 * @return the 0-based index of the column with the given name in its column header
	 * on failure, returns -1
	 *
	 * @author ssalisbury
	 */
	protected int getColumnIndex( final String columnName )
	{
		int columnInd = -1;

		WebElement tableHeaderElem = getTableHeader();

		List<WebElement> columnHeaders = wait.waitForAnyElementsDisplayed(By.tagName("th"), tableHeaderElem);
		columnHeaders.removeIf(header -> !element.isElementDisplayed(header));
		columnInd = element.findElementPositionByText(columnHeaders, columnName);

		return columnInd;
	}

	/**
	 * deletes the given row of configurations of the connector
	 *
	 * @param rowInd the index of the configuration row that this deletes
	 *               uses 1-based indexing for more readable tests
	 *
	 * @author ssalisbury
	 */
	public void deleteConfigRow( final int rowInd )
	{
		final int actualRowInd = rowInd - 1;

		List<WebElement> rows = getConfigRows();

		if ( actualRowInd <= rows.size() )
		{
			WebElement targetRow = rows.get(actualRowInd);
			List<WebElement> potentialButtons = wait.waitForAllElementsPresent(By.tagName("span"), targetRow);
			WebElement deleteButton = element.selectElementByAttribute(potentialButtons, deleteMappingButtonName,
				"title");
			if ( deleteButton != null )
			{
				click.clickElementCarefully(deleteButton);
			}
		}
		else
		{
			String fewRowsErrorMessage = String.format("Could not find row %d in a list of only %d rows", actualRowInd,
				rows.size());
			VertexLogger.log(fewRowsErrorMessage, VertexLogLevel.ERROR);
		}
	}

	/**
	 * wipes every row of this page's table of configurations
	 *
	 * @author ssalisbury
	 */
	public void deleteAllConfigRows( )
	{
		List<WebElement> rows = getConfigRows();

		for ( WebElement row : rows )
		{
			List<WebElement> potentialButtons = wait.waitForAllElementsPresent(By.tagName("span"), row);
			WebElement deleteButton = element.selectElementByAttribute(potentialButtons, deleteMappingButtonName,
				"title");
			if ( deleteButton != null )
			{
				click.clickElementCarefully(deleteButton);
			}
		}
		// this is checking whether there were any rows to delete, as the fetch happened before the deletion, and the list isn't re-fetched after.
		if ( rows.size() > 0 )
		{
			submitChanges();
			if ( isFailureMessageDisplayed() )
			{
				VertexLogger.log("Failed to delete all of the config rows on this page", VertexLogLevel.ERROR);
			}
		}
	}

	/**
	 * finds the desired dropdown-cell in the table by passing its parameters rowInd and columnName to
	 * {@link #getConfigCell(int, String)}
	 * It then collects the displayed names of all of the options in that cell's dropdown and returns them
	 *
	 * @return the displayed names of all of the options in that cell's dropdown
	 */
	protected List<String> getConfigDropdownOptions( final int rowInd, final String columnName )
	{
		List<String> dropdownOptions = new ArrayList<>();

		WebElement configCell = getConfigCell(rowInd, columnName);
		if ( configCell != null )
		{
			WebElement configDropdown = wait.waitForElementDisplayed(AribaConnTableFieldType.DROPDOWN.getLoc(),
				configCell);
			dropdownOptions = dropdown.getDropdownDisplayOptions(configDropdown);
		}

		return dropdownOptions;
	}
}
