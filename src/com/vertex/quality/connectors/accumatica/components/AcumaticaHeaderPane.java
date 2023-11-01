package com.vertex.quality.connectors.accumatica.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.components.base.AcumaticaBaseComponent;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalMenuOption;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaGlobalSubMenuOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents the header portion of almost all Acumatica pages, which contains buttons for
 * high-level navigation between different parts of the site.
 *
 * @author ssalisbury
 */
public class AcumaticaHeaderPane extends AcumaticaBaseComponent
{
	protected final By globalMenuTabsContainer = By.id("panelT_systemsBar_ul");
	protected final By globalMenuTab = By.className("toolsBtn");

	protected final By globalSubMenuTabsContainer = By.id("panelT_modulesBar_ul");
	protected final By globalSubMenuTab = By.className("toolsBtn");

	protected AcumaticaGlobalSubMenuOption currentSubMenu;

	public AcumaticaHeaderPane( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);

		//TODO THIS IS WRONG, when you log back in IT REMEMBERS YOUR LAST SUBMENU IN THAT SYSTEM MENU (at least sometimes)
		// EITHER SCRAP THE MINOR TIME-SAVING LOGIC OR MAKE THIS  CHECK THE ACTIVE SUBMENU
		currentSubMenu = null;//AcumaticaGlobalSubMenuOption.values()[0];
	}

	/**
	 * Navigate to the given submenu, which has links to the pages that are related to one module
	 * of the site's functionality
	 *
	 * @param globalSubMenuTab which module's submenu should be opened
	 *
	 * @author ssalisbury
	 */
	public void switchToSubMenuTab( final AcumaticaGlobalSubMenuOption globalSubMenuTab )
	{
		selectGlobalNavMenuOption(globalSubMenuTab.getGlobalMenu());
		selectGlobalNavSubMenuOption(globalSubMenuTab);
	}

	/**
	 * selects a global tab to start navigating towards something in that tab's large section of the site
	 *
	 * @param globalMenuTab which global menu tab, which represents a large section of the site, should be selected
	 */
	private void selectGlobalNavMenuOption( final AcumaticaGlobalMenuOption globalMenuTab )
	{
		WebElement globalTab = getGlobalMenuTab(globalMenuTab);

		if ( globalTab != null )
		{
			click.clickElementCarefully(globalTab);
		}
		else
		{
			VertexLogger.log(globalMenuTab.getLabel());
			throw new RuntimeException("Cannot click possibly invalid global menu tab");
		}
	}

	/**
	 * retrieves one of the tabs at the top of the site header,
	 * which can change the options displayed in the rest of the header (i.e. submenu tabs)
	 * and so also in the left navigation pane
	 *
	 * @param globalMenu which tab at the top of the site header to get
	 *
	 * @return one of the tabs at the top of the site header
	 * On failure, returns null
	 *
	 * @author ssalisbury
	 */
	protected WebElement getGlobalMenuTab( final AcumaticaGlobalMenuOption globalMenu )
	{
		String expectedText = globalMenu.getLabel();

		WebElement targetMenuTab = null;
		WebElement globalTabsContainerElem = wait.waitForElementPresent(globalMenuTabsContainer);
		List<WebElement> globalMenuTabs = wait.waitForAllElementsDisplayed(globalMenuTab, globalTabsContainerElem);

		targetMenuTab = element.selectElementByText(globalMenuTabs, expectedText);
		return targetMenuTab;
	}

	/**
	 * selects the given submenu tab to navigate towards something within that subsection of the site
	 *
	 * @param globalSubMenuTab which global submenu tab, which represents a subsection of the site, should be selected
	 */
	private void selectGlobalNavSubMenuOption( final AcumaticaGlobalSubMenuOption globalSubMenuTab )
	{
		WebElement subMenuTab = getGlobalSubMenuTab(globalSubMenuTab);
		if ( subMenuTab != null )
		{
			click.clickElementCarefully(subMenuTab);
			currentSubMenu = globalSubMenuTab;
		}
		else
		{
			VertexLogger.log(globalSubMenuTab.getValue());
			throw new RuntimeException("Cannot click invalid global menu tab");
		}
	}

	/**
	 * Gets a submenu tab from the header, which is a link to a 'module', aka a category of menus
	 *
	 * @param expectedSubMenu the module whose tab should be retrieved
	 *
	 * @return the given submenu tab, on failure, returns null
	 *
	 * @author ssalisbury
	 */
	protected WebElement getGlobalSubMenuTab( final AcumaticaGlobalSubMenuOption expectedSubMenu )
	{
		String expectedText = expectedSubMenu.getValue();

		WebElement targetSubMenuTab = null;
		WebElement subMenuTabsContainerElem = wait.waitForElementPresent(globalSubMenuTabsContainer);
		List<WebElement> subMenuTabs = wait.waitForAllElementsPresent(globalSubMenuTab, subMenuTabsContainerElem);

		targetSubMenuTab = element.selectElementByText(subMenuTabs, expectedText);
		return targetSubMenuTab;
	}
}
