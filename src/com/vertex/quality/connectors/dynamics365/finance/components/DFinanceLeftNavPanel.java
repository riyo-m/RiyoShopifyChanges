package com.vertex.quality.connectors.dynamics365.finance.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.finance.components.base.DFinanceBaseComponent;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceLeftMenuModule;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelCategory;
import com.vertex.quality.connectors.dynamics365.finance.enums.DFinanceModulePanelLink;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This represents both the popup bar on the left with lists of modules & also the flyout panel of links which appears
 * when you click on a module in that left menu bar
 *
 * Note- if you ever have to leave this without clicking on one of the links, the first click on something outside the
 * nav menu will close the nav menu and shift focus to that other thing (?) but not actually interact with the thing
 * that you clicked on
 *
 * @author ssalisbury
 */
public class DFinanceLeftNavPanel extends DFinanceBaseComponent
{
	protected final By leftMenuModuleGroup = By.className("modulesPane-groupHeading");
	protected final String leftMenuModuleGroupExpandedClass = "modulesPane-isExpanded";
	protected final By leftMenuModule = By.className("modulesPane-module");

	protected final By modulePanelContainer = By.className("modulesFlyout-container");
	protected final String modulePanelCategoryExpandedClass = "modulesFlyout-isExpanded";
	protected final By leftMenuModulesContainer = By.className("modulesList");
	protected final By modulePanelCategory = By.className("modulesFlyout-LinkGroup");
	protected final By modulePanelLink = By.className("modulesFlyout-link");
	protected final By workspacesPanelLink = By.className("modulesPane-link");

	public DFinanceLeftNavPanel( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * expands a dropdown in the left nav menu with a list of the modules in some group (eg. Workspaces, Modules)
	 *
	 * @param groupName the name of the group of modules
	 */
	public void expandLeftMenuModuleGroup( final String groupName )
	{
		WebElement moduleGroup = getLeftMenuModuleGroup(groupName);

		if ( moduleGroup != null )
		{
			String moduleGroupClasses = attribute.getElementAttribute(moduleGroup, "class");

			boolean isExpanded = moduleGroupClasses != null && moduleGroupClasses.contains(
				leftMenuModuleGroupExpandedClass);

			if ( !isExpanded )
			{
				click.clickElementCarefully(moduleGroup);
				attribute.tryWaitForElementAttributeChange(moduleGroup, "class", FIVE_SECOND_TIMEOUT);
			}
		}
	}

	/**
	 * opens the flyout panel of links for a particular module in the left nav panel
	 *
	 * @param module which module's panel to open
	 */
	public void openLeftMenuModule( final DFinanceLeftMenuModule module )
	{
		WebElement moduleElem = getLeftMenuModule(module);
		if ( moduleElem != null )
		{
			String moduleClasses = attribute.getElementAttribute(moduleElem, "class");

			if ( moduleClasses != null && !moduleClasses.contains("isActive") )
			{
				click.javascriptClick(moduleElem);
			}
		}
	}

	/**
	 * expands a dropdown list of links (and subcategories of links) in a module's flyout panel
	 *
	 * @param category which link category/dropdown to expand
	 */
	public void expandModuleCategory( final DFinanceModulePanelCategory category )
	{
		WebElement categoryElem = getModulePanelCategory(category);

		if ( categoryElem != null )
		{
			String categoryClasses = attribute.getElementAttribute(categoryElem, "class");

			boolean isExpanded = categoryClasses != null && categoryClasses.contains(modulePanelCategoryExpandedClass);

			if ( !isExpanded )
			{
				click.clickElementCarefully(categoryElem);
			}
		}
	}

	/**
	 * clicks a link in a module's flyout panel to open a page in D365 Finance
	 *
	 * @param link which link to click on
	 * @param <P>  the Page class which represents the D365 Finance page which is navigated to
	 *
	 * @return some page in D365 Finance
	 * returns null on error
	 */
	public <P extends DFinanceBasePage> P openModuleLink( final DFinanceModulePanelLink link )
	{
		P nextPage = null;

		WebElement linkElem = getModulePanelLink(link);

		if ( linkElem != null )
		{
			click.clickElementCarefully(linkElem);
			nextPage = initializePageObject(link.getLinkedPage());
		}

		return nextPage;
	}

	/**
	 * clicks a link in a module's flyout panel to open a page in D365 Finance
	 *
	 * @param link which link to click on
	 * @param <P>  the Page class which represents the D365 Finance page which is navigated to
	 *
	 * @return some page in D365 Finance
	 * returns null on error
	 */
	public <P extends DFinanceBasePage> P openWorkspacesLink( final DFinanceModulePanelLink link )
	{
		P nextPage = null;

		WebElement linkElem = getWorkspacesPanelLink(link);

		if ( linkElem != null )
		{
			click.clickElementCarefully(linkElem);
			nextPage = initializePageObject(link.getLinkedPage());
		}

		return nextPage;
	}

	/**
	 * finds the dropdown which holds the list of modules in the given group
	 *
	 * @param groupName which module group's dropdown to find
	 *
	 * @return the dropdown which holds the list of modules in the given group
	 * returns null on error
	 */
	protected WebElement getLeftMenuModuleGroup( final String groupName )
	{
		WebElement moduleGroup = null;

		WebElement leftMenuModulesContainerElem = wait.waitForElementPresent(leftMenuModulesContainer);
		List<WebElement> expandedGroups = wait.waitForAnyElementsDisplayed(leftMenuModuleGroup,
			leftMenuModulesContainerElem);

		moduleGroup = element.selectElementByText(expandedGroups, groupName);

		return moduleGroup;
	}

	/**
	 * finds the element which opens the panel of links for some module
	 *
	 * @param module which module's element to find
	 *
	 * @return the element which opens the panel of links for some module
	 * returns null on error
	 */
	protected WebElement getLeftMenuModule( final DFinanceLeftMenuModule module )
	{
		WebElement targetModuleElem = null;

		WebElement leftMenuModulesContainerElem = wait.waitForElementPresent(leftMenuModulesContainer);
		List<WebElement> moduleElems = wait.waitForAnyElementsDisplayed(leftMenuModule, leftMenuModulesContainerElem);

		targetModuleElem = element.selectElementByText(moduleElems, module.getModuleName());

		return targetModuleElem;
	}

	/**
	 * finds the dropdown for some category of links in a module's panel
	 *
	 * @param category which category's dropdown to find
	 *
	 * @return the dropdown for some category of links in a module's panel
	 * returns null on error
	 */
	protected WebElement getModulePanelCategory( final DFinanceModulePanelCategory category )
	{
		WebElement targetCategoryElem = null;

		WebElement modulePanelContainerElem = wait.waitForElementPresent(modulePanelContainer);
		List<WebElement> categoryElems = wait.waitForAnyElementsDisplayed(modulePanelCategory,
			modulePanelContainerElem);

		targetCategoryElem = element.selectElementByText(categoryElems, category.getCategoryName());

		return targetCategoryElem;
	}

	/**
	 * finds the link to some page on D365 Finance
	 *
	 * @param link which link to find
	 *
	 * @return the link to some page on D365 Finance
	 * returns null on error
	 */
	protected WebElement getModulePanelLink( final DFinanceModulePanelLink link )
	{
		WebElement targetLinkElem = null;

		WebElement modulePanelContainerElem = wait.waitForElementPresent(modulePanelContainer);
		List<WebElement> linkElems = wait.waitForAnyElementsDisplayed(modulePanelLink, modulePanelContainerElem);

		targetLinkElem = element.selectElementByText(linkElems, link.getLinkName());

		return targetLinkElem;
	}

	/**
	 * finds the link to some page on D365 Finance
	 *
	 * @param link which link to find
	 *
	 * @return the link to some page on D365 Finance
	 * returns null on error
	 */
	protected WebElement getWorkspacesPanelLink( final DFinanceModulePanelLink link )
	{
		WebElement targetLinkElem = null;

		List<WebElement> linkElems = wait.waitForAllElementsPresent(workspacesPanelLink);

		targetLinkElem = element.selectElementByText(linkElems, link.getLinkName());

		return targetLinkElem;
	}
}
