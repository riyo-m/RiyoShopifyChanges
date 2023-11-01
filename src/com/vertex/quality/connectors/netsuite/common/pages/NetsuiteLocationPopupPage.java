package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteLocation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the location popup page
 *
 * @author hho
 */
public class NetsuiteLocationPopupPage extends NetsuiteLocation
{
	protected String parentWindowHandle;
	protected By saveButtonLocator = By.id("submitter");
	protected By cancelButtonLocator = By.id("cancel");

	public NetsuiteLocationPopupPage( final WebDriver driver, String parentWindowHandle )
	{
		super(driver);
		this.parentWindowHandle = parentWindowHandle;
	}

	/**
	 * Saves the location and returns to the parent page
	 */
	public void save( )
	{
		click.clickElement(saveButtonLocator);
		window.switchToParentWindow(parentWindowHandle);
	}

	/**
	 * Cancels the creation of the location and returns to the parent page
	 */
	public void cancel( )
	{
		click.clickElement(cancelButtonLocator);
		window.switchToParentWindow(parentWindowHandle);
	}
}
