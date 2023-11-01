package com.vertex.quality.connectors.bigcommerce.ui.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.bigcommerce.ui.enums.BigCommerceAdminNavCategory;
import com.vertex.quality.connectors.bigcommerce.ui.pages.admin.BigCommerceAdminViewCustomersPage;
import com.vertex.quality.connectors.bigcommerce.ui.pages.storefront.BigCommerceStoreHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

/**
 * the navigation bar on the left side of pages in the big commerce admin site
 *
 * @author ssalisbury
 */
public class BigCommerceAdminLeftNavBar extends VertexComponent
{
	//if it's loaded, it's a button in the left end of the header which opens the (previously completely collapsed)
	// left nav bar below it
	protected final By navBarOpenButton = By.className("cp-mobile-nav-menu");

	protected final By navBarCollapseToggleButton = By.className("cp-nav-header-toggleNav");

	protected final By viewStorefrontButton = By.id("nav-viewStore");
	protected final By homeButton = By.id("nav-dashboard");
	protected final By searchField = By.id("nav-search");

	protected final By navMenusContainer = By.className("cp-nav-base");

	protected final By navOptionButton = By.className("cp-nav-link");

	protected final String viewCustomerButtonText = "View";

	public BigCommerceAdminLeftNavBar( final WebDriver driver, final VertexPage parent )
	{
		super(driver, parent);
	}

	/**
	 * opens the storefront by clicking on a button in the navigation bar
	 *
	 * @return new instance of the storefront homepage
	 */
	public BigCommerceStoreHomePage navigateToStorefront( )
	{
		ensureNavBarExpanded();

		Set<String> previousHandles = window.getWindowHandles();
		click.clickElementCarefully(viewStorefrontButton);
		window.waitForAndSwitchToNewWindowHandle(previousHandles);

		BigCommerceStoreHomePage storePage = initializePageObject(BigCommerceStoreHomePage.class);

		return storePage;
	}

	/**
	 * loads the page on the admin site for managing existing customer profiles
	 *
	 * @return the page on the admin site for managing existing customer profiles
	 */
	public BigCommerceAdminViewCustomersPage navigateToCustomersPage( )
	{
		final BigCommerceAdminNavCategory customersCategory = BigCommerceAdminNavCategory.CUSTOMERS;
		selectNavCategory(customersCategory);

		WebElement customerOptionsContainerElem = wait.waitForElementDisplayed(
			customersCategory.getOptionsContainerLoc());
		List<WebElement> customerNavOptions = wait.waitForAnyElementsDisplayed(navOptionButton,
			customerOptionsContainerElem);

		WebElement viewCustomersNavButton = element.selectElementByText(customerNavOptions, viewCustomerButtonText);
		if ( viewCustomersNavButton == null )
		{
			throw new RuntimeException("couldn't find the link to the View Customers page");
		}

		wait.waitForElementEnabled(viewCustomersNavButton);
		click.clickElement(viewCustomersNavButton);

		BigCommerceAdminViewCustomersPage customersPage = initializePageObject(BigCommerceAdminViewCustomersPage.class);
		return customersPage;
	}

	/**
	 * this navigates through the left menu bar so that the options for the given navigation category are displayed
	 *
	 * @param category which category of navigation options should be navigated to
	 */
	protected void selectNavCategory( final BigCommerceAdminNavCategory category )
	{
		ensureNavBarExpanded();

		final By categoryButton = category.getButtonLoc();
		wait.waitForElementEnabled(categoryButton);
		click.clickElement(categoryButton);
	}

	/**
	 * this waits until one of the buttons is displayed which can control the visibility of the left navigation bar
	 * Clarifying note- this is technically an instance variable
	 */
	final ExpectedCondition<By> someNavBarVisibilityControlButtonDisplayedCondition = driver ->
	{
		By displayedButtonLoc = null;

		//tries to find the collapse toggle button displayed inside the nav bar
		List<WebElement> toggleButtons = element.getWebElements(navBarCollapseToggleButton);
		toggleButtons.removeIf(buttonElem -> !buttonElem.isDisplayed());

		int numDisplayedToggles = toggleButtons.size();
		if ( numDisplayedToggles > 1 )
		{
			VertexLogger.log(
				"Multiple toggle buttons were displayed for the collapsing/expanding of the navigation bar",
				VertexLogLevel.WARN);
		}
		else if ( numDisplayedToggles == 1 )
		{
			displayedButtonLoc = navBarCollapseToggleButton;
		}
		else
		{
			//if that toggle-button isn't displayed, then it assumes the nav bar is fully collapsed and so it looks
			// for the button that's displayed in the header which will open the nav bar
			List<WebElement> openerButtons = element.getWebElements(navBarOpenButton);
			openerButtons.removeIf(buttonElem -> !buttonElem.isDisplayed());

			int numDisplayedOpenerButtons = openerButtons.size();
			if ( numDisplayedOpenerButtons > 1 )
			{
				VertexLogger.log("Multiple buttons were displayed for the opening of the navigation bar",
					VertexLogLevel.WARN);
			}
			else if ( numDisplayedOpenerButtons == 1 )
			{
				displayedButtonLoc = navBarOpenButton;
			}
		}

		return displayedButtonLoc;
	};

	/**
	 * this expands the navigation bar in the cases where the nav bar is collapsed so that the nav bar's contents
	 * can be interacted with
	 */
	protected void ensureNavBarExpanded( )
	{
		WebDriverWait waiter = new WebDriverWait(driver, DEFAULT_TIMEOUT);
		By initiallyDisplayedNavButton = waiter.until(someNavBarVisibilityControlButtonDisplayedCondition);

		if ( navBarOpenButton.equals(initiallyDisplayedNavButton) )
		{
			wait.waitForElementEnabled(navBarOpenButton);
			click.clickElement(navBarOpenButton);

			wait.waitForElementDisplayed(navMenusContainer);
		}
		else if ( navBarCollapseToggleButton.equals(initiallyDisplayedNavButton) )
		{
			//When the nav bar is automatically partially collapsed because the window size is too limited, this moves
			// the mouse to the navigation bar's toggle icon, which temporarily expands the navigation bar in that case
			boolean isBarCollapsed = !element.isElementDisplayed(navMenusContainer);

			if ( isBarCollapsed )
			{
				hover.hoverOverElement(navBarCollapseToggleButton);
				waitForPageLoad();

				//When the nav bar has been manually partially collapsed by clicking the nav bar's collapse toggle button,
				// this manually expands the navigation bar again by clicking the nav bar's collapse toggle button again
				boolean isBarStillCollapsed = !element.isElementDisplayed(navMenusContainer);
				if ( isBarStillCollapsed )
				{
					wait.waitForElementEnabled(navBarCollapseToggleButton);
					click.clickElement(navBarCollapseToggleButton);

					wait.waitForElementDisplayed(navMenusContainer);
				}
			}
		}
		else
		{
			VertexLogger.log("the wrong locator was returned by the waiter in ensureNavBarExpanded()",
				VertexLogLevel.ERROR);
		}
	}
}
