package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * The item search page
 *
 * @author hho
 */
public class NetsuiteItemSearchPage extends NetsuiteSearchPage
{
	protected By itemDescriptionTextField = By.id("Item_DESCRIPTION");
	protected By typeList = By.id("Item_TYPE_fs");

	public NetsuiteItemSearchPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuitePage> T submitSearch( )
	{
		click.clickElement(submitButton);
		return initializePageObject(NetsuiteItemSearchResultsPage.class);
	}

	/**
	 * Inputs a description into the "DESCRIPTION" textfield
	 *
	 * @param searchDescription the description to input
	 */
	public void enterDescription( String searchDescription )
	{
		text.enterText(itemDescriptionTextField, searchDescription);
	}

	/**
	 * Selects the item type
	 *
	 * @param itemTypeKey the item type
	 */
	public void selectItemType( String itemTypeKey )
	{
		selectItem(typeList, itemTypeKey);
	}

	/**
	 * Selects the item in the scrolldown list
	 *
	 * @param itemList the scrolldown list locator
	 * @param item     the item to select
	 */
	private void selectItem( By itemList, String item )
	{
		WebElement itemTypeList = wait.waitForElementPresent(itemList);
		List<WebElement> itemTypes = itemTypeList.findElements(By.tagName("tr"));

		itemSearch:
		for ( WebElement itemType : itemTypes )
		{
			List<WebElement> itemTypeRow = itemType.findElements(By.tagName("td"));

			for ( WebElement itemTypeCell : itemTypeRow )
			{
				List<WebElement> itemTypeLinks = element.getWebElements(By.tagName("a"), itemTypeCell);

				WebElement targetItemLink = element.selectElementByText(itemTypeLinks, item);
				if ( targetItemLink != null )
				{
					click.clickElement(targetItemLink);
					//fixme scott inserted this b/c it seemed like it belonged there, but double-check it first if this function breaks
					break itemSearch;
				}
			}
		}
	}
}
