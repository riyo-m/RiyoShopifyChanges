package com.vertex.quality.connectors.netsuite.common.pages.products;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteClassPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Vertex product class popup page
 *
 * @author hho
 */
public class NetsuiteProductClassPopupPage extends NetsuiteClassPage
{
	protected By saveButtonLocator = By.id("submitter");
	protected String parentWindowHandle;

	public NetsuiteProductClassPopupPage( final WebDriver driver, String parentWindowHandle )
	{
		super(driver);
		this.parentWindowHandle = parentWindowHandle;
	}

	/**
	 * Saves the current class
	 */
	public void save( )
	{
		click.clickElement(saveButtonLocator);
		window.switchToParentWindow(parentWindowHandle);
	}
}
