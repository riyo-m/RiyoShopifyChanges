package com.vertex.quality.connectors.netsuite.common.pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the parent page for all class pages in Netsuite
 *
 * @author hho
 */
public abstract class NetsuiteClassPage extends NetsuitePage
{
	protected By saveButtonLocator = By.id("btn_multibutton_submitter");
	protected By editButtonLocator = By.id("edit");
	protected By actionsMenu = By.id("spn_ACTIONMENU_d1");
	protected By actionsSubmenu = By.id("div_ACTIONMENU_d1");

	protected String delete = "Delete";
	protected By nameTextField = By.id("name");

	public NetsuiteClassPage( final WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Inputs the given class name
	 *
	 * @param className the class name
	 */
	public void enterClassName( String className )
	{
		text.enterText(nameTextField, className);
	}
}
