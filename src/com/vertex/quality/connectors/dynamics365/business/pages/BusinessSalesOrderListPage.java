package com.vertex.quality.connectors.dynamics365.business.pages;

import com.vertex.quality.connectors.dynamics365.business.components.BusinessPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the sales order list page
 *
 * @author osabha, cgajes
 */
public class BusinessSalesOrderListPage extends BusinessBasePage

{
	public BusinessPagesNavMenu pageNavMenu;

	protected By orderGridLoc = By.cssSelector("table[id*='BusinessGrid']");
	protected By orderRowsLoc = By.cssSelector("table tbody tr");
	protected By orderRowSegmentLoc = By.cssSelector("tr td");
	protected By openLink = By.cssSelector("a['title*='Open record '][aria-label*='No,.']");

	public BusinessSalesOrderListPage( WebDriver driver )
	{
		super(driver);
		this.pageNavMenu = new BusinessPagesNavMenu(driver, this);
	}

	/**
	 * Gets an order from the list based on row
	 *
	 * @param rowNum
	 *
	 * @return element of the row that contains clickable link and number
	 */
	public WebElement getOrderFromList( int rowNum )
	{
		rowNum--;
		List<WebElement> gridList = wait.waitForAllElementsPresent(orderGridLoc);
		WebElement grid = gridList.get(gridList.size() - 1);

		List<WebElement> gridRows = wait.waitForAllElementsPresent(orderRowsLoc, grid);
		WebElement itemRow = gridRows.get(rowNum);

		WebElement selectedOrder = null;
		List<WebElement> segments = wait.waitForAllElementsPresent(orderRowSegmentLoc, itemRow);
		for ( WebElement segment : segments )
		{
			try
			{
				WebElement ele = wait.waitForElementDisplayed(By.tagName("a"), segment, 5);
				if ( ele
					.getAttribute("title")
					.contains("Open record") )
				{
					selectedOrder = ele;
				}
			}
			catch ( TimeoutException e )
			{

			}
		}

		return selectedOrder;
	}

	/**
	 * Gets the number of the order in the specified row
	 *
	 * @param rowIndex
	 *
	 * @return the order's document number
	 */
	public String getOrderNumberByRowIndex( int rowIndex )
	{
		WebElement order = getOrderFromList(rowIndex);

		String number = order.getText();

		return number;
	}
	
}





