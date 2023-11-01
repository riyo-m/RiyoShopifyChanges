package com.vertex.quality.connectors.accumatica.components;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.accumatica.components.base.AcumaticaBaseComponent;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelLink;
import com.vertex.quality.connectors.accumatica.enums.AcumaticaLeftPanelTab;
import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AcumaticaLeftNavPanel extends AcumaticaBaseComponent
{
	protected final String dropdownExpandedClass = "expanded";

	protected final By leftPanelTabsContainer = By.id("panelL_menuPanel_subModulesBar_ul");
	protected final By leftPanelTabWrapper = By.tagName("li");
	protected final By leftPanelTab = By.className("toolsBtn");

	protected final By leftPanelMenuPane = By.className("menuPanel");
	protected final By leftPanelLinksContainer = By.className("menuTree");
	protected final By leftPanelSectionContainer = By.cssSelector("[class~='tnLI'][class~='folder']");
	protected final By leftPanelSectionLabelContainer = By.className("tnDiv");
	protected final By leftPanelSectionLabel = By.className("treeNode");
	protected final By leftPanelSectionLinksContainer = By.className("tnUL");
	protected final By leftPanelSectionLink = By.className("treeNode");

	protected AcumaticaLeftPanelTab currentLeftPanelTab = AcumaticaLeftPanelTab.SOLE_TAB;
	//TODO selectSubmenuTab() inits this to first relevant entry in enum?
	//	only switch to new left panel tab if the desired link isn't in the current left panel tab

	public AcumaticaLeftNavPanel( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * finds and clicks the desired link in the left menu panel to load a new page
	 *
	 * @param targetLink which link's page should be opened
	 *
	 * @return the page within the module that the given link points to
	 */
	public <M extends AcumaticaPostSignOnPage> M openLeftMenuLink( final AcumaticaLeftPanelLink targetLink )
	{
		M newMenu = null;

		switchToLeftPanelTab(targetLink.getTab());

		//find the link we want
		WebElement targetLinkElem = getLeftMenuLink(targetLink);

		if ( targetLinkElem != null )
		{
			wait.waitForElementEnabled(targetLinkElem);
			targetLinkElem.click();

			Class<? extends AcumaticaPostSignOnPage> linkedPageClass = targetLink.getLinkedPage();

			newMenu = initializePageObject(linkedPageClass);
		}

		return newMenu;
	}

	/**
	 * switches the left navigation panel to the desired tab in that panel
	 *
	 * @param targetTab which tab in the left navigation panel to switch to
	 */
	protected void switchToLeftPanelTab( AcumaticaLeftPanelTab targetTab )
	{
		//if there aren't multiple tabs in the left navigation panel, then the test doesn't need to switch to the right tab (in fact, it can't)
		if ( !targetTab.equals(AcumaticaLeftPanelTab.SOLE_TAB) )
		{
			WebElement leftTab = findLeftPanelTab(targetTab);
			if ( leftTab != null )
			{
				click.clickElementCarefully(leftTab);
				currentLeftPanelTab = targetTab;
			}
			else
			{
				VertexLogger.log(targetTab.getValue());
				throw new RuntimeException("can't switch to possibly invalid left panel tab");
			}
		}
	}

	/**
	 * finds the desired tab in the left navigation panel
	 *
	 * @param targetTab which tab in the left navigation panel should be retrieved
	 *
	 * @return the desired tab in the left navigation panel
	 */
	protected WebElement findLeftPanelTab( AcumaticaLeftPanelTab targetTab )
	{
		final String leftPanelTabTooltipText = targetTab.getValue();
		WebElement leftPanelTabElem = null;

		WebElement leftPanelTabsContainerElem = wait.waitForElementPresent(leftPanelTabsContainer);
		List<WebElement> leftPanelTabWrappers = wait.waitForAnyElementsDisplayed(leftPanelTabWrapper,
			leftPanelTabsContainerElem, DEFAULT_TIMEOUT);

		for ( WebElement leftPanelTabWrapper : leftPanelTabWrappers )
		{
			if ( element.isElementDisplayed(leftPanelTabWrapper) )
			{
				List<WebElement> leftTabs = element.getWebElements(leftPanelTab, leftPanelTabWrapper);
				leftPanelTabElem = element.selectElementByAttribute(leftTabs, leftPanelTabTooltipText, "tooltip");
				if ( leftPanelTabElem != null )
				{
					break;
				}
			}
		}
		return leftPanelTabElem;
	}

	/**
	 * finds a link to another page in the left menu pane
	 *
	 * @param targetLink describes which link to find
	 *                   note- the link must be part of the current module
	 *
	 * @return a link to another page in the left menu pane
	 * the returned link should be displayed and enabled or else become so after a short period
	 * On failure, this returns null
	 */
	protected WebElement getLeftMenuLink( final AcumaticaLeftPanelLink targetLink )
	{
		WebElement targetLinkElem = null;

		String linkSection = targetLink.getSection();
		WebElement targetLinksSection = getLeftMenuLinksSection(linkSection);

		if ( targetLinksSection != null )
		{
			//check that the section, which is a header with a dropdown of links below it, is
			// expanded. this ensures that the links are accessible
			String sectionClasses = attribute.getElementAttribute(targetLinksSection, "class");
			if ( !sectionClasses.contains(dropdownExpandedClass) )
			{
				waitForPageLoad();
				wait.waitForElementEnabled(targetLinksSection);
				targetLinksSection.click();
			}

			WebElement linksContainer = targetLinksSection.findElement(leftPanelSectionLinksContainer);

			List<WebElement> links = wait.waitForAllElementsPresent(leftPanelSectionLink, linksContainer);
			for ( WebElement link : links )
			{
				String linkLabel = link.getText();
				String expectedLinkLabel = targetLink.getLabel();
				boolean isTargetElement = expectedLinkLabel.equals(linkLabel);
				if ( isTargetElement )
				{
					targetLinkElem = link;
					break;
				}
			}
		}

		return targetLinkElem;
	}

	/**
	 * finds a section of the links in the left menu pane
	 *
	 * @param expectedSectionLabel the string displayed above that section of the links
	 *                             Note- this section of links must exist within the current module
	 *
	 * @return an element which contains that section of the links (including the section label/header)
	 * On failure, returns null
	 */
	protected WebElement getLeftMenuLinksSection( final String expectedSectionLabel )
	{
		WebElement targetLinksSection = null;

		List<WebElement> linkSections = getLeftNavLinkFolders();

		linksSectionSearchLABEL:
		for ( WebElement linkSection : linkSections )
		{
			List<WebElement> sectionLabelContainers = element.getWebElements(leftPanelSectionLabelContainer,
				linkSection);
			for ( WebElement sectionLabelContainer : sectionLabelContainers )
			{
				/*some of the elements with this class are containers around the section headers
				while others are containers around the links themselves. However, the containers
				around the links store what the link points to while the containers around the
				section headers don't
				 */
				String dataUrlAttribute = sectionLabelContainer.getAttribute("data-url");
				boolean isSectionHeader = dataUrlAttribute == null;
				if ( isSectionHeader )
				{
					WebElement sectionLabelElem = sectionLabelContainer.findElement(leftPanelSectionLabel);

					String sectionLabel = sectionLabelElem.getText();

					if ( expectedSectionLabel.equals(sectionLabel) )
					{
						targetLinksSection = linkSection;
						break linksSectionSearchLABEL;
					}
				}
			}
		}

		return targetLinksSection;
	}

	/**
	 * finds the elements which contain the sections of links in the currently-displayed version of the left nav panel
	 * the currently displayed set of links/link section varies by submenu, and some submenus have multiple tabs in
	 * their left nav panel
	 *
	 * @return the sections of currently accessible links in the left nav panel
	 */
	protected List<WebElement> getLeftNavLinkFolders( )
	{
		WebElement leftPanelLinksContainerElement = null;

		//why are there more than one?  find a better container
		List<WebElement> menuPanes = wait.waitForAllElementsPresent(leftPanelMenuPane);
		for ( WebElement menuPane : menuPanes )
		{
			boolean isMenuVisible = element.isElementDisplayed(menuPane);
			if ( isMenuVisible )
			{
				leftPanelLinksContainerElement = wait.waitForElementPresent(leftPanelLinksContainer, menuPane);
				break;
			}
		}

		List<WebElement> linksSections = wait.waitForAllElementsPresent(leftPanelSectionContainer,
			leftPanelLinksContainerElement);

		return linksSections;
	}
}
