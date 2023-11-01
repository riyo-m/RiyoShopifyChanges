package com.vertex.quality.connectors.netsuite.common.pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the parent of the class list pages
 *
 * @author hho
 */
public abstract class NetsuiteClassListPage extends NetsuiteListPage
{
	protected By createNewClassButtonLocator = By.id("new");

	public NetsuiteClassListPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Creates a new class of the current type
	 *
	 * @return the new class page
	 */
	public <T extends NetsuiteClassPage> T createNewClass( )
	{
		click.clickElement(createNewClassButtonLocator);
		return initializePageObject(getPageClass(getPageTitle()));
	}

	/**
	 * Checks if a class name is present in the list
	 *
	 * @param className the class name
	 *
	 * @return if a class name is present in the list
	 */
	public boolean isClassNameAvailable( String className )
	{
		WebElement classTableRow = tableComponent.getTableRowByIdentifier(tableLocator, className);
		return classTableRow != null;
	}
}
