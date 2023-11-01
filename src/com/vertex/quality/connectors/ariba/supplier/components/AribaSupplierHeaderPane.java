package com.vertex.quality.connectors.ariba.supplier.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.supplier.components.base.AribaSupplierBaseComponent;
import com.vertex.quality.connectors.ariba.supplier.pages.AribaSupplierInboxPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the header pane of the supplier homepage
 * it includes the methods necessary to interact with it's element's.
 *
 * @author osabha
 */
public class AribaSupplierHeaderPane extends AribaSupplierBaseComponent
{
	public AribaSupplierHeaderPane( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	public By mainMenu = By.className("w-tablist");
	public By mainMenuItem = By.className("w-tabitem");

	/**
	 * This Method is to get the desired main menu item to click on
	 *
	 * @param menuItem name of the tab desired to click on
	 */
	protected WebElement getMenuButton( String menuItem )
	{
		WebElement inboxButton = null;

		WebElement mainMenuList = wait.waitForElementPresent(mainMenu);

		List<WebElement> menuList = wait.waitForAllElementsPresent(mainMenuItem, mainMenuList);

		for ( int i = 0 ; i < menuList.size() && inboxButton == null ; i++ )
		{
			WebElement thisMenuItem = menuList.get(i);
			String expectedText = thisMenuItem.getText();
			if ( menuItem.equals(expectedText) )
			{
				inboxButton = thisMenuItem;
			}
		}

		return inboxButton;
	}

	/**
	 * locates and clicks on a inbox button from the main menu items
	 *
	 * @param inboxButtonLabel button label
	 *
	 * @return new instance of the supplier inbox page
	 */
	public AribaSupplierInboxPage clickInboxButton( String inboxButtonLabel )
	{
		WebElement inboxButton = getMenuButton(inboxButtonLabel);
		click.clickElement(inboxButton);
		return initializePageObject(AribaSupplierInboxPage.class);
	}
}
