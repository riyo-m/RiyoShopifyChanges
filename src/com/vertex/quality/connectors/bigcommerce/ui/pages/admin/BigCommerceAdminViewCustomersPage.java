package com.vertex.quality.connectors.bigcommerce.ui.pages.admin;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.base.BigCommerceAdminPostLoginBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * the page on Big Commerce's Admin site for managing existing customer profiles
 *
 * @author ssalisbury
 */
public class BigCommerceAdminViewCustomersPage extends BigCommerceAdminPostLoginBasePage
{
	protected final By pageBodyIframe = By.id("content-iframe");

	protected final By tableHeader = By.className("table-thead");
	protected final By tableBody = By.className("table-tbody");
	protected final By tableRow = By.className("tableRow");
	protected final By tableCell = By.className("cs-table-cell");

	protected final By actionDropdownOptionButton = By.className("dropdown-menu-item");

	protected final String customerNameColumnName = "Name";
	protected final String actionColumnName = "Action";
	protected final String editButtonText = "Edit";

	public BigCommerceAdminViewCustomersPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * this is used after interacting with the page's contents and before interacting with other parts of the page
	 * like the header or footer (e.g. to leave the page)
	 */
	public void exitPageBodyIframe( )
	{
		window.switchToDefaultContent();
	}

	/**
	 * this opens the details page of the given customer
	 *
	 * @param customerName the name of the customer
	 *
	 * @return the details page of the given customer
	 */
	public BigCommerceAdminEditCustomerPage editCustomerDetails( final String customerName )
	{
		BigCommerceAdminEditCustomerPage customerPage;

		window.waitForAndSwitchToFrame(pageBodyIframe);

		int rowIndex = getCustomerRowIndex(customerName);

		WebElement actionElem = getTableCell(rowIndex, actionColumnName);

		wait.waitForElementEnabled(actionElem);
		click.clickElement(actionElem);

		List<WebElement> actionDropdownOptionElems = wait.waitForAnyElementsDisplayed(actionDropdownOptionButton,
			actionElem);
		WebElement editButtonElem = element.selectElementByText(actionDropdownOptionElems, editButtonText);
		if ( editButtonElem == null )
		{
			//fixme I'm not sure what the best approach is in this failure case. I'm just ending the test for now
			throw new RuntimeException("Couldn't find the button for editing a particular customer");
		}

		wait.waitForElementEnabled(editButtonElem);
		click.clickElement(editButtonElem);

		customerPage = initializePageObject(BigCommerceAdminEditCustomerPage.class);

		return customerPage;
	}

	/**
	 * Overloads {@link #getTableCell(int, int)} to identify the column by the header name displayed over it
	 */
	protected WebElement getTableCell( final int rowIndex, final String columnName )
	{
		int columnIndex = getColumnIndex(columnName);
		WebElement targetCell = getTableCell(rowIndex, columnIndex);
		return targetCell;
	}

	/**
	 * retrieves one of the cells in the customers table
	 *
	 * @param rowIndex    the index of the customer in the table whose cell should be fetched
	 * @param columnIndex the index of the column which the desired cell is in
	 *
	 * @return the cell in the given column for the given customer
	 */
	protected WebElement getTableCell( final int rowIndex, final int columnIndex )
	{
		WebElement targetCell = null;

		WebElement rowsContainer = wait.waitForElementPresent(tableBody);
		List<WebElement> rows = wait.waitForAnyElementsDisplayed(tableRow, rowsContainer);

		if ( rowIndex < rows.size() )
		{
			WebElement row = rows.get(rowIndex);
			List<WebElement> cells = wait.waitForAnyElementsDisplayed(tableCell, row);

			if ( columnIndex < cells.size() )
			{
				targetCell = cells.get(columnIndex);
			}
			else
			{
				VertexLogger.log("too few cells in row");
			}
		}
		else
		{
			VertexLogger.log("too few rows in table");
		}

		return targetCell;
	}

	/**
	 * identifies what index a customer's row has in the table
	 *
	 * @param customerName the customer whose row index should be determined
	 *
	 * @return what index a customer's row has in the table
	 */
	protected int getCustomerRowIndex( final String customerName )
	{
		int customerRowIndex = -1;

		final int nameColumnIndex = getColumnIndex(customerNameColumnName);

		WebElement rowsContainer = wait.waitForElementPresent(tableBody);
		List<WebElement> rows = wait.waitForAnyElementsDisplayed(tableRow, rowsContainer);

		for ( int i = 0 ; i < rows.size() ; i++ )
		{
			WebElement row = rows.get(i);

			List<WebElement> rowCells = wait.waitForAnyElementsDisplayed(tableCell, row);
			if ( nameColumnIndex < rowCells.size() )
			{
				WebElement customerNameCell = rowCells.get(nameColumnIndex);
				String name = text.getElementText(customerNameCell);

				if ( customerName.equals(name) )
				{
					customerRowIndex = i;
					break;
				}
			}
		}

		return customerRowIndex;
	}

	/**
	 * retrieves the index of the column with the given header name
	 *
	 * @param columnName the header name of the column
	 *
	 * @return the index of the column with the given header name
	 */
	protected int getColumnIndex( final String columnName )
	{
		WebElement header = wait.waitForElementPresent(tableHeader);
		List<WebElement> columnHeaders = wait.waitForAnyElementsDisplayed(tableCell, header);
		int columnIndex = element.findElementPositionByText(columnHeaders, columnName);
		return columnIndex;
	}
}
