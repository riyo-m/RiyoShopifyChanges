package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.enums.NetsuiteItemType;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents the item search page
 *
 * @author hho
 */
public class NetsuiteProductSearchPage extends NetsuiteSearchPage
{
	protected By submitSearchButtonLocator = By.id("submitter");
	protected By itemDescriptionTextField = By.id("Item_DESCRIPTION");
	protected By itemListLocator = By.id("Item_TYPE_fs");

	public NetsuiteProductSearchPage( final WebDriver driver )
	{
		super(driver);
	}

	@Override
	public <T extends NetsuitePage> T submitSearch( )
	{
		click.clickElement(submitSearchButtonLocator);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Inputs a description into textfield
	 *
	 * @param itemDescription the description to input
	 */
	public void enterDescription( String itemDescription )
	{
		text.enterText(itemDescriptionTextField, itemDescription);
	}

	/**
	 * Selects the item in the scroll-down list
	 *
	 * @param itemType the item to select
	 */
	public void selectItemType( NetsuiteItemType itemType )
	{
		WebElement itemTypeList = wait.waitForElementPresent(itemListLocator);
		List<WebElement> itemTypeCells = wait.waitForAllElementsDisplayed(By.tagName("td"), itemTypeList);

		for ( WebElement itemTypeCell : itemTypeCells )
		{
			List<WebElement> itemTypeLinks = wait.waitForAllElementsPresent(By.tagName("a"), itemTypeCell);

			WebElement itemTypeLink = element.selectElementByText(itemTypeLinks, itemType.getItemType());
			if ( itemTypeLink != null )
			{
				click.clickElement(itemTypeLink);
				break;
			}
		}
	}
}
