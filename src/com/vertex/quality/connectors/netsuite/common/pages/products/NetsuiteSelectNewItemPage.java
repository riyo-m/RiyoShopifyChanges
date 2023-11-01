package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTableComponent;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemType;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteItemPage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the page where selection of a new item type occurs
 *
 * @author hho
 */
public class NetsuiteSelectNewItemPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	private NetsuiteTableComponent tableComponent;

	protected By tableBodyLocator = By.id("__tab");
	protected By tableHeaderRowLocator = By.id("header");

	protected String itemTypeHeader = "Item type";

	public NetsuiteSelectNewItemPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		tableComponent = new NetsuiteTableComponent(driver, this);
	}

	/**
	 * Selects the item type
	 *
	 * @param itemType the item type to select
	 *
	 * @return the item type page
	 */
	public <T extends NetsuiteItemPage> T selectItemType( NetsuiteItemType itemType )
	{
		String itemTypeName = itemType.getItemType();
		WebElement itemTypeTableCell = tableComponent.getTableCellByIdentifier(tableBodyLocator, tableHeaderRowLocator,
			itemTypeName, itemTypeHeader);
		WebElement itemTypeTableCellButton = tableComponent.findCellButton(itemTypeTableCell, itemTypeName);
		click.clickElement(itemTypeTableCellButton);
		return initializePageObject(getPageClass(getPageTitle()));
	}
}
