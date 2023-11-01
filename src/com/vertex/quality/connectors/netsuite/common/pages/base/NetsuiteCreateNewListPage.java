package com.vertex.quality.connectors.netsuite.common.pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the list page that creates something
 *
 * @author hho
 */
public abstract class NetsuiteCreateNewListPage extends NetsuiteListPage
{
	public NetsuiteCreateNewListPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Creates a new instance of the given page type
	 *
	 * @return the new page
	 */
	public <T extends NetsuitePage> T createNew( )
	{
		By createNew = By.id("new");
		click.clickElement(createNew);
		return initializePageObject(getPageClass(getPageTitle()));
	}
}
