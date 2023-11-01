package com.vertex.quality.connectors.hybris.pages.admin;

import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Represents the Admin Home Page
 * This page is to perform operations on Admin Home Page - like operations Menus, Header and Footer
 *
 * @author Nagaraju Gampa
 */
public class HybrisAdminHomePage extends HybrisBasePage
{
	public HybrisAdminHomePage( WebDriver driver )
	{
		super(driver);
	}

	protected By MENUPLATFORM = By.id("platform");
	protected By LOGOUT = By.cssSelector("[type='submit'][value='logout']");

	/***
	 * Method to Validate whether Admin Login Successful or not
	 */
	public boolean loginSuccessful( )
	{
		boolean flag = false;
		if ( element.isElementDisplayed(MENUPLATFORM) )
		{
			flag = true;
		}
		return flag;
	}

	/**
	 * @param menuName
	 * @param subMenuName
	 */
	public void navigateTOAdminPages( final String menuName, final String subMenuName )
	{
		final By MENU_LOCATOR = By.cssSelector("#" + menuName + "[class='nav']");
		final By SUBMENU_LOCATOR = By.xpath(
			"//*[@id='nav_" + menuName + "' and not(contains(@style, 'none'))]//a[text()='" + subMenuName + "']");
		// Below locator can be used but Submenunames to be changed as CamelCase in Enum
		// By SUBMENU_LOCATOR = By.linkText(submenuname);
		focus.focusOnElementJavascript(MENU_LOCATOR);
		hover.hoverOverElement(MENU_LOCATOR);
		click.javascriptClick(SUBMENU_LOCATOR);
	}

	/**
	 * @param menu
	 * @param subMenu
	 *
	 * @return returns to extensionspage object
	 */
	public HybrisAdminExtensionsPage navigateToExtensionsPage( String menu, String subMenu )
	{
		navigateTOAdminPages(menu, subMenu);
		final HybrisAdminExtensionsPage extensionsPage = initializePageObject(HybrisAdminExtensionsPage.class);
		return extensionsPage;
	}
}
