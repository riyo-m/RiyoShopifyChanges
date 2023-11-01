package com.vertex.quality.connectors.netsuite.common.pages.customers;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteClassPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the customer class creation page
 *
 * @author hho
 */
public class NetsuiteCustomerClassPage extends NetsuiteClassPage
{
	public NetsuiteNavigationPane navigationPane;

	public NetsuiteCustomerClassPage( final WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
	}

	/**
	 * Saves the current class
	 *
	 * @return the saved class page
	 */
	public NetsuiteCustomerClassPage save( )
	{
		click.clickElement(saveButtonLocator);
		return initializePageObject(NetsuiteCustomerClassPage.class);
	}

	/**
	 * Edits the saved class
	 *
	 * @return the edit class page
	 */
	public NetsuiteCustomerClassPage edit( )
	{
		click.clickElement(editButtonLocator);
		return initializePageObject(NetsuiteCustomerClassPage.class);
	}

	/**
	 * Deletes the current class
	 *
	 * @return the class list page
	 */
	public NetsuiteCustomerClassListPage delete( )
	{
		WebElement deleteButton = getElementInHoverableMenu(actionsMenu, actionsSubmenu, delete);
		click.clickElement(deleteButton);
		if ( alert.waitForAlertPresent(TWO_SECOND_TIMEOUT) )
		{
			alert.acceptAlert();
		}
		return initializePageObject(NetsuiteCustomerClassListPage.class);
	}
}
