package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTableComponent;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteSortCriteria;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parent list page for Netsuite
 */
public abstract class NetsuiteListPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteTableComponent tableComponent;
	protected By tableHeaderRowLocator = By.id("div__labtab");
	protected By tableLocator = By.id("div__bodytab");
	protected By totalResultsLocator = By.id("uir_totalcount");
	protected By nextPageButtonLocator = By.className("navig-next");
	protected By quickSortDropdownLoc = By.name("inpt_quicksort");

	protected String editViewHeading = "Edit | View";
	protected String edit = "Edit";
	protected String view = "View";

	public NetsuiteListPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		tableComponent = new NetsuiteTableComponent(driver, this);
	}

	/**
	 * Gets the number of results
	 *
	 * @return the number of results
	 */
	public String getTotalCountOnCurrentPage( )
	{
		WebElement searchResultsElement = wait.waitForElementPresent(totalResultsLocator);
		String results = searchResultsElement.getText();
		return results.replaceAll("\\D+", "");
	}

	/**
	 * Edits the specific result
	 *
	 * @param identifier the identifying element of the row
	 *
	 * @return the specific result page
	 */
	public <T extends NetsuitePage> T edit( final String identifier )
	{
		return goToPage(identifier, edit);
	}

	/**
	 * Edits the specific result
	 *
	 * @param rowOrder the row's order in the table
	 *
	 * @return the specific result page
	 */
	public <T extends NetsuitePage> T edit( final int rowOrder )
	{
		return goToPage(rowOrder, edit);
	}

	/**
	 * Views the specific result
	 *
	 * @param identifier the identifying element of the row
	 *
	 * @return the specific result page
	 */
	public <T extends NetsuitePage> T view( final String identifier )
	{
		return goToPage(identifier, view);
	}

	/**
	 * Views the specific result
	 *
	 * @param rowOrder the row's order in the table
	 *
	 * @return the specific result page
	 */
	public <T extends NetsuitePage> T view( final int rowOrder )
	{
		return goToPage(rowOrder, view);
	}

	/**
	 * Sorts the list page by the sort criteria
	 *
	 * @param sortCriteria the sort criteria
	 */
	public void sortBy( NetsuiteSortCriteria sortCriteria )
	{
		setDropdownToValue(quickSortDropdownLoc, sortCriteria.getSortCriteria());
		waitForPageLoad();
	}

	/**
	 * Goes to the page after clicking on the specified button
	 *
	 * @param tableRowIdentifier the row's identifier
	 * @param buttonText         the button text
	 *
	 * @return the page
	 */
	private <T extends NetsuitePage> T goToPage( final String tableRowIdentifier, final String buttonText )
	{
		WebElement cell = tableComponent.getTableCellByIdentifier(tableLocator, tableHeaderRowLocator,
			tableRowIdentifier, editViewHeading);
		while ( cell == null )
		{
			click.clickElement(nextPageButtonLocator);
			cell = tableComponent.getTableCellByIdentifier(tableLocator, tableHeaderRowLocator, tableRowIdentifier,
				editViewHeading);
		}
		WebElement cellButton = tableComponent.findCellButton(cell, buttonText);
		click.clickElement(cellButton);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Goes to the page after clicking on the specified button
	 *
	 * @param tableRowOrder the row's order
	 * @param buttonText    the button text
	 *
	 * @return the page
	 */
	private <T extends NetsuitePage> T goToPage( final int tableRowOrder, final String buttonText )
	{
		WebElement cell = tableComponent.getTableCellByCount(tableLocator, tableHeaderRowLocator, tableRowOrder,
			editViewHeading);
		while ( cell == null )
		{
			click.clickElement(nextPageButtonLocator);
			cell = tableComponent.getTableCellByCount(tableLocator, tableHeaderRowLocator, tableRowOrder,
				editViewHeading);
		}
		WebElement cellButton = tableComponent.findCellButton(cell, buttonText);
		click.clickElement(cellButton);
		return initializePageObject(getPageClass(getPageTitle()));
	}
}
