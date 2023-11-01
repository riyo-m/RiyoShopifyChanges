package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteClassPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the product class creation page
 *
 * @author hho
 */
public class NetsuiteProductClassPage extends NetsuiteClassPage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteProductClassPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}

	/**
	 * Saves the current class
	 *
	 * @return the saved class page
	 */
	public NetsuiteProductClassPage save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(NetsuiteProductClassPage.class);
	}

	/**
	 * Edits the saved class
	 *
	 * @return the edit class page
	 */
	public NetsuiteProductClassPage edit( )
	{
		click.clickElement(editButtonLocator);
		return initializePageObject(NetsuiteProductClassPage.class);
	}

	/**
	 * Deletes the current class
	 *
	 * @return the class list page
	 */
	public NetsuiteProductClassListPage delete( )
	{
		WebElement deleteButton = getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
		click.clickElement(deleteButton);
		if ( alert.waitForAlertPresent(TWO_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		return initializePageObject(NetsuiteProductClassListPage.class);
	}
}
