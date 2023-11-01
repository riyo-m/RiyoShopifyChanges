package com.vertex.quality.connectors.netsuite.common.pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The parent location page
 *
 * @author hho
 */
public abstract class NetsuiteLocation extends NetsuitePage
{
	protected By locationNameTextFieldLocator = By.id("name");
	protected By sublocationDropdownLocator = By.name("inpt_parent");

	public NetsuiteLocation( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Enter the location's name
	 *
	 * @param name the location's name
	 */
	public void enterLocationName( String name )
	{
		text.enterText(locationNameTextFieldLocator, name);
	}

	/**
	 * Select the location's parent
	 *
	 * @param location the location's parent
	 */
	public void selectSublocationOf( String location )
	{
		setDropdownToValue(sublocationDropdownLocator, location);
	}
}
