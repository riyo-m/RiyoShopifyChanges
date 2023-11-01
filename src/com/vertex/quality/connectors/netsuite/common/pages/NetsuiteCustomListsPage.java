package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTableComponent;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteCustomList;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the custom lists page
 *
 * @author hho
 */
public class NetsuiteCustomListsPage extends NetsuitePage
{
	protected By tableHeaderRowLocator = By.id("div__labtab");
	protected By tableLocator = By.id("div__bodytab");
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteTableComponent tableComponent;

	public NetsuiteCustomListsPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		tableComponent = new NetsuiteTableComponent(driver, this);
	}

	/**
	 * Goes to the list page for the custom list type
	 *
	 * @param customList the custom list type
	 *
	 * @return the list page for the custom list type
	 */
	public <T extends NetsuitePage> T list( NetsuiteCustomList customList )
	{
		String headerButton = "List";
		int listTableHeaderIndex = 4;
		String customListTitle = customList.getCustomListTitle();
		WebElement customListTableRow = tableComponent.getTableRowByIdentifier(tableLocator, customListTitle);
		WebElement customListTableCell = tableComponent.getTableCellByHeaderIndex(customListTableRow,
			listTableHeaderIndex);
		WebElement customListTableCellButton = tableComponent.findCellButton(customListTableCell, headerButton);
		click.clickElement(customListTableCellButton);
		return initializePageObject(getPageClass(getPageTitle()));
	}
}
