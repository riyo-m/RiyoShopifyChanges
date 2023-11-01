package com.vertex.quality.connectors.netsuite.common.pages.customers;

import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuiteClassPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the customer class popup page
 *
 * @author hho
 */
public class NetsuiteCustomerClassPopupPage extends NetsuiteClassPage
{
	protected By saveButtonLocator = By.id("submitter");
	protected String parentWindowHandle;

	public NetsuiteCustomerClassPopupPage( final WebDriver driver, String parentWindowHandle )
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
