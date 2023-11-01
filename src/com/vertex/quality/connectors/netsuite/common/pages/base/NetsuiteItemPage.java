package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteItemComponent;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.products.NetsuiteItemListPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the parent item page
 *
 * @author hho
 */
public abstract class NetsuiteItemPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteItemComponent itemComponent;
	protected By itemNameTextFieldLocator = By.id("itemid");
	protected By saveButtonLocator = By.id("btn_multibutton_submitter");
	protected By editButtonLocator = By.id("edit");
	protected By vertexProductClassDropdownLocator = By.name("inpt_custitem_taxproductclass_vt");
	protected By newVertexProductClassButton = By.id("custitem_taxproductclass_vt_popup_new");
	protected By salesPricingTabLocator = By.id("pricingtxt");
	protected By salesDescriptionTextfieldLocator = By.id("salesdescription");

	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");

	protected String delete = "Delete";

	public NetsuiteItemPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		itemComponent = new NetsuiteItemComponent(driver, this);
	}

	/**
	 * Enters the item's name
	 *
	 * @param itemName the item's name
	 */
	public void enterItemName( String itemName )
	{
		text.enterText(itemNameTextFieldLocator, itemName);
	}

	/**
	 * Deletes the item
	 *
	 * @return the item list page
	 */
	public NetsuiteItemListPage delete( )
	{
		WebElement deleteButton = getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
		click.clickElement(deleteButton);
		jsWaiter.sleep(30);
		if ( alert.waitForAlertPresent(DEFAULT_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		return initializePageObject(NetsuiteItemListPage.class);
	}

	/**
	 * Saves the item
	 *
	 * @return the saved item page
	 */
	public abstract <T extends NetsuiteItemPage> T save( );

	/**
	 * Edits the item
	 *
	 * @return the edited item page
	 */
	public abstract <T extends NetsuiteItemPage> T edit( );
}
