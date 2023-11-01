package com.vertex.quality.connectors.netsuite.common.components;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Represents the list component that some netsuite pages have
 *
 * @author hho
 */
public class NetsuiteTableComponent extends NetsuiteComponent
{
	private String focusedInteractableTableRowClass = "uir-machine-row-focused";

	public NetsuiteTableComponent( final WebDriver driver, final NetsuitePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Gets the table cell by its table header index
	 *
	 * @param tableRow         the table row
	 * @param tableHeaderIndex the table cell's header index
	 *
	 * @return the table cell
	 */
	public WebElement getTableCellByHeaderIndex( WebElement tableRow, int tableHeaderIndex )
	{
		WebElement tableCell = null;
		List<WebElement> tableCells = wait.waitForAllElementsPresent(By.tagName("td"), tableRow);
		int numHeaderRowIndices = tableCells.size();
		if ( tableHeaderIndex < numHeaderRowIndices )
		{
			tableCell = tableCells.get(tableHeaderIndex);
		}
		return tableCell;
	}

	/**
	 * Gets the table cell by a unique identifier and its header
	 *
	 * @param tableLocator          the table locator
	 * @param tableHeaderRowLocator the table header row
	 * @param identifier            the identifier (any unique value of a table cell in the row)
	 * @param headerLabel           the header text
	 *
	 * @return the table row
	 */
	public WebElement getTableCellByIdentifier( By tableLocator, By tableHeaderRowLocator, String identifier,
		String headerLabel )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		WebElement tableRow = getTableRowByIdentifier(tableLocator, identifier);
		WebElement specificTableCell = null;
		if ( tableRow != null )
		{
			int tableHeaderIndex = getHeaderIndex(table, tableHeaderRowLocator, headerLabel);
			specificTableCell = getTableCellByHeaderIndex(tableRow, tableHeaderIndex);
		}
		return specificTableCell;
	}

	/**
	 * Gets the table row by a unique identifier
	 *
	 * @param tableLocator the table locator
	 * @param identifier   the identifier (any unique value of a table cell in the row)
	 *
	 * @return the table row
	 */
	public WebElement getTableRowByIdentifier( By tableLocator, String identifier )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		WebElement specificTableRow = null;

		search:
		for ( WebElement tableRow : tableRows )
		{
			List<WebElement> tableCells = tableRow.findElements(By.tagName("td"));

			for ( WebElement tableCell : tableCells )
			{
				String tableCellTextContent = attribute.getElementAttribute(tableCell, "textContent");
				String tableCellText = tableCell.getText();

				if ( (tableCellTextContent != null && tableCellTextContent.equals(identifier)) ||
					 (tableCellText != null && tableCellText.equals(identifier)) )
				{
					specificTableRow = tableRow;
					break search;
				}
			}
		}
		return specificTableRow;
	}

	/**
	 * Gets the table cell by its count and its header
	 *
	 * @param tableLocator          the table locator
	 * @param tableHeaderRowLocator the table header row
	 * @param order                 the table row to get in order
	 * @param headerLabel           the header text
	 *
	 * @return the table row
	 */
	public WebElement getTableCellByCount( By tableLocator, By tableHeaderRowLocator, int order, String headerLabel )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		WebElement tableRow = getTableRowByCount(tableLocator, order);
		WebElement specificTableCell = null;
		if ( tableRow != null )
		{
			int tableHeaderIndex = getHeaderIndex(table, tableHeaderRowLocator, headerLabel);
			specificTableCell = getTableCellByHeaderIndex(tableRow, tableHeaderIndex);
		}
		return specificTableCell;
	}

	/**
	 * Gets the nth table row specified by its order (starts at 1)
	 *
	 * @param tableLocator the table locator
	 * @param order        the table row to get in order
	 *
	 * @return the table row
	 */
	public WebElement getTableRowByCount( By tableLocator, int order )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		int tableHeaderIndex = order;

		if ( tableRows.size() >= tableHeaderIndex )
		{
			return tableRows.get(tableHeaderIndex);
		}

		return null;
	}

	/**
	 * Gets the interactable table cell
	 *
	 * @param tableLocator          the table locator
	 * @param tableHeaderRowLocator the table header row
	 * @param identifier            the identifier (any unique value of a table cell in the row)
	 * @param headerLabel           the header text
	 *
	 * @return the table cell
	 */
	public WebElement getInteractableTableCell( By tableLocator, By tableHeaderRowLocator, String identifier,
		String headerLabel )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		WebElement tableRow = getInteractableTableRow(tableLocator, identifier);
		WebElement specificTableCell = null;
		if ( tableRow != null )
		{
			int tableHeaderIndex = getHeaderIndex(table, tableHeaderRowLocator, headerLabel);
			specificTableCell = getTableCellByHeaderIndex(tableRow, tableHeaderIndex);
		}
		return specificTableCell;
	}

	/**
	 * Gets the interactable table row
	 *
	 * @param tableLocator the table locator
	 * @param identifier   the identifier (any unique value of a table cell in the row)
	 *
	 * @return the table row
	 */
	public WebElement getInteractableTableRow( By tableLocator, String identifier )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		List<WebElement> tableRows = table.findElements(By.xpath("child::tr"));
		// Removing header row
		tableRows.remove(0);
		WebElement specificTableRow = null;

		search:
		for ( WebElement tableRow : tableRows )
		{
			List<WebElement> tableCells = tableRow.findElements(By.tagName("td"));

			for ( WebElement tableCell : tableCells )
			{
				String tableCellTextContent = attribute.getElementAttribute(tableCell, "textContent");
				String tableCellText = tableCell.getText();

				if ( (tableCellTextContent != null && tableCellTextContent.equals(identifier)) ||
					 (tableCellText != null && tableCellText.equals(identifier)) )
				{
					specificTableRow = tableRow;
					break search;
				}
				else
				{
					List<WebElement> tableCellInputs = tableCell.findElements(By.tagName("input"));
					WebElement tableCellInput = element.selectElementByAttribute(tableCellInputs, identifier, "value");
					if ( tableCellInput != null )
					{
						specificTableRow = tableRow;
						break search;
					}
				}
			}
		}
		return specificTableRow;
	}

	/**
	 * Gets the focused table cell
	 *
	 * @param tableLocator          the table locator
	 * @param tableHeaderRowLocator the table header row
	 * @param headerLabel           the header text
	 *
	 * @return the table row
	 */
	public WebElement getFocusedTableCell( By tableLocator, By tableHeaderRowLocator, String headerLabel )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		WebElement tableRow = getFocusedTableRow(tableLocator);
		WebElement specificTableCell = null;
		if ( tableRow != null )
		{
			int tableHeaderIndex = getHeaderIndex(table, tableHeaderRowLocator, headerLabel);
			specificTableCell = getTableCellByHeaderIndex(tableRow, tableHeaderIndex);
		}
		return specificTableCell;
	}

	/**
	 * Gets the focused table row
	 *
	 * @param tableLocator the table locator
	 *
	 * @return the focused table row
	 */
	public WebElement getFocusedTableRow( By tableLocator )
	{
		WebElement table = wait.waitForElementDisplayed(tableLocator);
		WebElement focusedTableRow = wait.waitForElementPresent(By.className(focusedInteractableTableRowClass), table);
		return focusedTableRow;
	}

	/**
	 * Finds a button in the table cell
	 *
	 * @param cell       the table cell
	 * @param buttonText the text on the button
	 *
	 * @return the button in the table cell
	 */
	public WebElement findCellButton( WebElement cell, String buttonText )
	{
		List<WebElement> cellButtons = element.getWebElements(By.tagName("a"), cell);
		WebElement specificCellButton = element.selectElementByText(cellButtons, buttonText);
		return specificCellButton;
	}

	/**
	 * Focuses on the interactable table cell by clicking on it (the table to add items to in an order)
	 *
	 * @param tableCell the table cell element
	 */
	public void focusOnInteractableTableCell( WebElement tableCell )
	{
		Actions actor = new Actions((driver) );
		String focusedItemTableCellClass = "uir-machine-focused-cell";
		String tableCellClass = attribute.getElementAttribute(tableCell, "class");
		if ( tableCellClass != null && !tableCellClass.contains(focusedItemTableCellClass) )
		{
			actor.moveToElement(tableCell).click().perform();
		}
	}

	/**
	 * Focuses on the interactable table row element by clicking on it (the table to add items to in an order)
	 *
	 * @param tableRow the table row element
	 */
	public void focusOnInteractableTableRow( WebElement tableRow )
	{
		String focusedInteractableTableRowClass = "uir-machine-row-focused";
		String tableRowClass = attribute.getElementAttribute(tableRow, "class");
		if ( tableRowClass != null && !tableRowClass.contains(focusedInteractableTableRowClass) )
		{
			click.clickElement(tableRow);
		}
	}

	/**
	 * Gets the header's index in the header row
	 *
	 * @param table                 the table element
	 * @param tableHeaderRowLocator the table header row
	 * @param headerLabel           the header text
	 *
	 * @return the header's index
	 */
	private int getHeaderIndex( WebElement table, By tableHeaderRowLocator, String headerLabel )
	{
		WebElement tableHeaderRow = null;
		List<WebElement> tableHeaderRows = table.findElements(tableHeaderRowLocator);
		int headerIndex = -1;

		if ( tableHeaderRows.size() > 0 )
		{
			tableHeaderRow = tableHeaderRows.get(0);
		}
		else
		{
			tableHeaderRow = wait.waitForElementDisplayed(tableHeaderRowLocator);
		}

		List<WebElement> tableHeaderCells = element.getWebElements(By.tagName("td"), tableHeaderRow);
		headerIndex = element.findElementPositionByAttribute(tableHeaderCells, "data-label", headerLabel);
		if(headerIndex == -1)
		{
			throw new IllegalArgumentException("Error: Header Label \""+headerLabel+"\" Not found in Header Row");
		}
		return headerIndex;
	}
}
