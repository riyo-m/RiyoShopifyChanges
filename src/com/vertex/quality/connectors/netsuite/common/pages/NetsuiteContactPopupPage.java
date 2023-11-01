package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the contact popup page
 */
public class NetsuiteContactPopupPage extends NetsuitePage
{
	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");

	protected String parentWindowHandle;
	protected String delete = "Delete";

	public NetsuiteContactPopupPage( final WebDriver driver, String parentWindowHandle )
	{
		super(driver);
		this.parentWindowHandle = parentWindowHandle;
	}

	/**
	 * Deletes the contact
	 */
	public void delete( )
	{
		WebElement deleteButton = getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
		click.clickElement(deleteButton);
		if ( alert.waitForAlertPresent(TWO_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		window.switchToParentWindow(parentWindowHandle);
	}
}
