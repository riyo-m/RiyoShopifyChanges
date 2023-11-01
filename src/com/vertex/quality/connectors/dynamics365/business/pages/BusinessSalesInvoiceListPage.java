package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the sales invoice list page
 *
 */

public class BusinessSalesInvoiceListPage extends BusinessBasePage
{
	protected By invoiceGridLoc = By.cssSelector("table[id*='BusinessGrid']");
	protected By invoiceRowsLoc = By.cssSelector("table tbody tr");
	protected By invoiceRowSegmentLoc = By.cssSelector("tr td");
	protected By openLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");

	public BusinessSalesInvoiceListPage( WebDriver driver ) { super(driver); }

	/**
	 * Gets an invoice from the list based on row
	 *
	 * @param rowNum
	 *
	 * @return element of the row that contains clickable link and number
	 */
	public WebElement getInvoiceFromList( int rowNum )
	{
		List<WebElement> gridList = wait.waitForAllElementsPresent(invoiceGridLoc);
		WebElement grid = gridList.get(gridList.size() - 1);

		List<WebElement> gridRows = wait.waitForAllElementsPresent(invoiceRowsLoc, grid);
		WebElement itemRow = gridRows.get(rowNum);

		WebElement selectedInvoice = null;
		List<WebElement> segments = wait.waitForAllElementsPresent(invoiceRowSegmentLoc, itemRow);
		for ( WebElement segment : segments )
		{
			try
			{
				WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
				if ( ele
					.getAttribute("title")
					.contains("Open record") )
				{
					selectedInvoice = ele;
				}
			}
			catch ( TimeoutException e )
			{

			}
		}

		return selectedInvoice;
	}

	/**
	 * Gets the number of the invoice in the specified row
	 *
	 * @param rowIndex
	 *
	 * @return the invoice's document number
	 */
	public String getInvoiceNumberByRowIndex( int rowIndex )
	{
		WebElement invoice = getInvoiceFromList(rowIndex);

		String number = invoice.getText();

		return number;
	}

}
