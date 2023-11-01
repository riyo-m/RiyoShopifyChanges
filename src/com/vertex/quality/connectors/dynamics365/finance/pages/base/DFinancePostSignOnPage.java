package com.vertex.quality.connectors.dynamics365.finance.pages.base;

import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceHeaderBar;
import com.vertex.quality.connectors.dynamics365.finance.components.DFinanceLeftNavPanel;
import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceAboutDialog;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * contains functionality shared by all sites except the login page
 *
 * @author ssalisbu
 */
public class DFinancePostSignOnPage extends DFinanceBasePage
{
	public DFinanceHeaderBar headerBar;
	public DFinanceAboutDialog aboutDialog;
	public DFinanceLeftNavPanel navPanel;

	protected final By navigationPanel = By.className("modulesPane-opener");

	//we aren't doing anything with warehouses yet (I don't know if we ever will), and automation shouldn't rely on
	// volatile categories like 'recently used' or 'favorites'
	protected final String defaultLeftMenuModuleGroup = "Modules";

	public DFinancePostSignOnPage( final WebDriver driver )
	{
		super(driver);
		this.headerBar = initializePageObject(DFinanceHeaderBar.class, this);
	}

	/**
	 * opens the left navigation panel
	 * TODO see if this can be refactored to a less weird state without becoming unstable
	 */
	public void clickOnNavigationPanel( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(navigationPanel);
		WebElement clickAgain = wait.waitForElementEnabled(navigationPanel);
		try
		{
			click.clickElement(navigationPanel);
			if ( clickAgain.isDisplayed() )
			{
			//	click.clickElement(navigationPanel);
			}
		}
		catch ( WebDriverException e )
		{
			refreshPage();
			click.javascriptClick(navigationPanel);
		}
		this.navPanel = initializePageObject(DFinanceLeftNavPanel.class, this);
		waitForPageLoad();
	}

	/**
	 * Overloads {@link #navigateToPage(DFinanceLeftMenuModule, DFinanceModulePanelCategory,
	 * DFinanceModulePanelCategory, DFinanceModulePanelLink)}
	 * to assume that the link is directly under one of the primary categories in the module instead of being in a
	 * subcategory
	 */
	public <P extends DFinanceBasePage> P navigateToPage( final DFinanceLeftMenuModule module,
		final DFinanceModulePanelCategory category, final DFinanceModulePanelLink link )
	{
		P newPage = navigateToPage(module, category, null, link);

		return newPage;
	}

	/**
	 * navigates to some page on D365 using the left navigation menu panes
	 *
	 * @param module      which section of the site the page is in
	 * @param category    which category of pages in that module the page is in
	 * @param subCategory which subcategory of pages the page is in under the primary category
	 *                    this may be null if the link is directly under one of the primary categories
	 * @param link        the link to the page
	 * @param <P>         the Page class which describes the page which is being navigated to
	 *
	 * @return a Page object which represents the page which is being navigated to
	 */
	public <P extends DFinanceBasePage> P navigateToPage( final DFinanceLeftMenuModule module,
		final DFinanceModulePanelCategory category, final DFinanceModulePanelCategory subCategory,
		final DFinanceModulePanelLink link )
	{
		P newPage = null;

		//TODO? first check if nav panel is already open?
		refreshPage();
		clickOnNavigationPanel();

		navPanel.expandLeftMenuModuleGroup(defaultLeftMenuModuleGroup);
		navPanel.openLeftMenuModule(module);
		navPanel.expandModuleCategory(category);
		if ( subCategory != null )
		{
			navPanel.expandModuleCategory(subCategory);
		}
		newPage = navPanel.openModuleLink(link);

		return newPage;
	}

	/**
	 * navigates to some page on D365 using the left navigation menu panes
	 *
	 * @param workSpaces   which section of the site the page is in
	 *                    this may be null if the link is directly under one of the primary categories
	 * @param link        the link to the page
	 * @param <P>         the Page class which describes the page which is being navigated to
	 *
	 * @return a Page object which represents the page which is being navigated to
	 */
	public <P extends DFinanceBasePage> P navigateToPage( final DFinanceLeftMenuModule workSpaces, final DFinanceModulePanelLink link )
	{
		P newPage = null;

		refreshPage();
		clickOnNavigationPanel();

		navPanel.expandLeftMenuModuleGroup("Workspaces");
		newPage = navPanel.openWorkspacesLink(link);

		return newPage;
	}
}
