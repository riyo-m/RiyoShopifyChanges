package com.vertex.quality.connectors.dynamics365.finance.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.components.base.DFinanceBaseComponent;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceAboutDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * the bar at the top of pages on D365 Finance
 * It has various buttons & dropdowns
 *
 * @author ssalisbury
 */
public class DFinanceHeaderBar extends DFinanceBaseComponent
{
	protected final By settingsButton = By.id("navBarSettings_button");
	protected final By helpAndSupportButton = By.id("navBarHelpButton");
	protected final By aboutLink = By.id("NavBarAbout");

	public DFinanceHeaderBar( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * click on 'Settings Tab'
	 */
	public void clickOnSettingsDropdown( )
	{
		click.clickElementCarefully(settingsButton);
	}

	/**
	 * click on 'HelpAndSupport Tab'
	 * @Vishwa
	 */
	public void clickOnHelpAndSupport()
	{
		click.clickElementCarefully(helpAndSupportButton);
	}

	/**
	 * click on 'About' option from Settings dropdown
	 */
	public void clickOnAbout( )
	{
		click.clickElementCarefully(aboutLink);
		dFinanceParent.aboutDialog = initializePageObject(DFinanceAboutDialog.class, dFinanceParent);
	}
}
