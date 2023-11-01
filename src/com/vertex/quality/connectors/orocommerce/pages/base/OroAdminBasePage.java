package com.vertex.quality.connectors.orocommerce.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.components.admin.configurationsMenu;
import com.vertex.quality.connectors.orocommerce.components.admin.leftMainMenu;
import com.vertex.quality.connectors.orocommerce.components.admin.taxesMenu;
import org.openqa.selenium.WebDriver;

/**
 * contains all the common functionality across all the Oro  Admin Pages
 *
 * @author alewis
 */
public class OroAdminBasePage extends VertexPage
{

	public leftMainMenu mainMenu;
	public configurationsMenu configurations;
	public taxesMenu taxes;

	public OroAdminBasePage( WebDriver driver )
	{
		super(driver);
		this.mainMenu = new leftMainMenu(driver, this);
		this.configurations = new configurationsMenu(driver, this);
		this.taxes = new taxesMenu(driver,this);
	}
}
