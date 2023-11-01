package com.vertex.quality.connectors.ariba.connector.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavConfigurationMenuOption;
import com.vertex.quality.connectors.ariba.connector.enums.forcomponents.AribaConnNavOption;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * handles interaction with tabs in the navigation menu along the left edge of
 * Ariba's Vertex Configuration page
 *
 * @author ssalisbury
 */
public class AribaConnNavPanel extends VertexComponent
{
	protected final By navButtonContainer = By.className("site-nav__item");
	protected final String expandedDropdownClass = "is-expanded";

	public AribaConnNavPanel( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * checks whether you can possibly click on the navigation button you are checking on
	 *
	 * @param navOption 'the button' that might be possibly clickable
	 *
	 * @return whether 'the button' is possibly clickable
	 *
	 * @author ssalisbury
	 */
	public boolean isNavOptionEnabled( final AribaConnNavOption navOption )
	{
		//wait.tryWaitForElementEnabled(navOption.navButtonLoc, THREE_SECOND_TIMEOUT);
		boolean isNavOptionEnabled = element.isElementEnabled(navOption.navButtonLoc);

		return isNavOptionEnabled;
	}

	/**
	 * checks whether you can possibly click on the button for navigating to a particular configuration menu
	 *
	 * @param menuOption the configuration menu button that might be possibly clickable
	 *
	 * @return whether the configuration menu button is possibly clickable
	 *
	 * @author ssalisbury
	 */
	public boolean isNavMenuOptionEnabled( final AribaConnNavConfigurationMenuOption menuOption )
	{
		//wait.tryWaitForElementEnabled(menuOption.menuLoc, THREE_SECOND_TIMEOUT);
		boolean isMenuOptionEnabled = element.isElementEnabled(menuOption.menuLoc);

		return isMenuOptionEnabled;
	}

	/**
	 * clicks one of the navigation buttons, the ones that should always be visible
	 * on the left side of the screen once the page is loaded
	 *
	 * @param navOption which navigation button you are clicking on
	 *
	 * @return the page on the configuration site which clicking on this navigation button loads, or null
	 *
	 * @author ssalisbury
	 */
	public <C extends AribaConnBasePage> C clickNavOption( final AribaConnNavOption navOption )
	{
		WebElement navButton = wait.waitForElementEnabled(navOption.navButtonLoc);

		navButton.click();

		C maybeNewPage = null;
		if ( navOption.returnPageType != null )
		{
			maybeNewPage = initializePageObject(navOption.returnPageType);
		}

		return maybeNewPage;
	}

	/**
	 * clicks one of the configuration menu option buttons, which are on the left side
	 * of the screen in the Configuration Menu button's dropdown once that button is clicked
	 *
	 * @param menuOption which configuration menu option button you are clicking on
	 *
	 * @return the configuration menu webpage that you are clicking on a link to
	 *
	 * @author ssalisbury
	 */
	public <T extends AribaConnBasePage> T clickNavConfigurationMenuOption(
		final AribaConnNavConfigurationMenuOption menuOption )
	{
		WebElement menuButton = wait.waitForElementEnabled(menuOption.menuLoc);
		click.clickElement(menuButton);

		T configMenu = initializePageObject(menuOption.returnPageType);

		return configMenu;
	}

	/**
	 * waits for a button for one of the nav options to become displayed and enabled
	 *
	 * @param navOption which nav option's button should become displayed and enabled
	 */
	public void waitForNavOptionEnabled( final AribaConnNavOption navOption )
	{
		wait.waitForElementEnabled(navOption.navButtonLoc);
	}

	/**
	 * waits for a button which links to one of the configuration menu options to become displayed and enabled
	 *
	 * @param menuOption which menu option's button should become displayed and enabled
	 */
	public void waitForNavConfigurationMenuOptionEnabled( final AribaConnNavConfigurationMenuOption menuOption )
	{
		wait.waitForElementEnabled(menuOption.menuLoc);
	}

	/**
	 * checks whether the dropdown of configuration menu link buttons is expanded
	 * doesn't wait for things to be displayed
	 *
	 * @return whether the dropdown of configuration menu link buttons is expanded
	 */
	public boolean isConfigurationMenuDropdownExpanded( )
	{
		boolean isDropdownExpanded = false;

		List<WebElement> navButtonContainers = wait.waitForAllElementsPresent(navButtonContainer);

		for ( WebElement navButtonContainer : navButtonContainers )
		{
			if ( element.isElementPresent(AribaConnNavOption.CONFIGURATION_MENU.navButtonLoc, navButtonContainer) )
			{
				final String dropdownContainerClasses = attribute.getElementAttribute(navButtonContainer, "class");
				if ( dropdownContainerClasses != null && dropdownContainerClasses.contains(expandedDropdownClass) )
				{
					isDropdownExpanded = true;
				}
			}
		}

		return isDropdownExpanded;
	}

	/**
	 * waits for the dropdown of configuration menu links to become expanded so that those links can actually be
	 * clicked on
	 */
	public void waitForConfigMenuDropdownExpanded( )
	{
		ExpectedCondition<Boolean> dropdownExpanded = driver ->
		{
			boolean isDropdownExpanded = isConfigurationMenuDropdownExpanded();
			return isDropdownExpanded;
		};

		WebDriverWait waiter = new WebDriverWait(driver, VertexAutomationObject.DEFAULT_TIMEOUT);
		waiter.until(dropdownExpanded);
	}
}
