package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.connectors.hybris.enums.backoffice.HybrisBOMenuNames;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Class to use BackOffice HomePage and LeftMenu options
 *
 * @author Nagaraju Gampa
 */
public class HybrisBOHomePage extends HybrisBasePage
{
	public HybrisBOHomePage( WebDriver driver )
	{
		super(driver);
	}

	protected By TOOLBAR_RIGHT_CONTAINER = By.cssSelector("[class*='yw-statusToolbar-right']");
	protected By MENU_TOOLBAR_BUTTONS = By.className("z-toolbarbutton");
	String locator = "[title='";
	String open = "opened";

	/**
	 * Method to navigate to Backoffice LeftNavigation
	 *
	 * @param menu select corresponding menu from BackOffice Left Navigation
	 */
	public void navigateToBOPage( final String menu )
	{
		final By menuElementBy = By.cssSelector(locator + menu + "']");
		wait.waitForElementEnabled(menuElementBy);
		click.clickElement(menuElementBy);
		hybrisWaitForPageLoad();
	}

	/**
	 * Method to navigate to Backoffice menu and submenu from LeftNavigation
	 *
	 * @param menu    - select corresponding menu from BackOffice Left Navigation
	 * @param subMenu - select corresponding submenu from BackOffice Left Navigation
	 */
	public void navigateToBOPage( final String menu, final String subMenu )
	{
		final By menuElementBy = By.cssSelector(locator + menu + "']");
		final By submenuElementBy = By.cssSelector(locator + subMenu + "']");
		String isMenuOpened = attribute.getElementAttribute(menuElementBy, "class");
		if ( isMenuOpened.contains(open.trim()) )
		{
			wait.waitForElementEnabled(submenuElementBy);
			click.clickElement(submenuElementBy);
			hybrisWaitForPageLoad();
		}
		else
		{
			wait.waitForElementEnabled(menuElementBy);
			click.clickElement(menuElementBy);
			wait.waitForElementEnabled(submenuElementBy);
			click.clickElement(submenuElementBy);
			hybrisWaitForPageLoad();
		}
	}

	/**
	 * Method to navigate to Backoffice page from LeftNavigation
	 *
	 * @param menu    - select corresponding menu from BackOffice Left Navigation
	 * @param subMenu - select corresponding submenu from BackOffice Left Navigation
	 * @param page    - select corresponding page from BackOffice Left Navigation
	 */
	public void navigateToBOPage( final String menu, final String subMenu, final String page )
	{
		final By menuElementBy = By.cssSelector(locator + menu + "']");
		final By submenuElementBy = By.cssSelector(locator + subMenu + "']");
		final By pageElementBy = By.cssSelector(locator + page + "']");
		String isMenuOpened = attribute.getElementAttribute(menuElementBy, "class");
		wait.waitForElementEnabled(menuElementBy);
		if ( isMenuOpened.contains(open.trim()) )
		{
			wait.waitForElementEnabled(submenuElementBy);
			String isSubmenuOpened = attribute.getElementAttribute(submenuElementBy, "class");
			if ( isSubmenuOpened.contains(open.trim()) )
			{
				wait.waitForElementEnabled(pageElementBy);
				click.clickElement(pageElementBy);
				hybrisWaitForPageLoad();
			}
			else
			{
				wait.waitForElementEnabled(submenuElementBy);
				click.clickElement(submenuElementBy);
				wait.waitForElementEnabled(pageElementBy);
				click.clickElement(pageElementBy);
				hybrisWaitForPageLoad();
			}
		}
		else
		{
			wait.waitForElementEnabled(menuElementBy);
			click.clickElement(menuElementBy);
			wait.waitForElementEnabled(submenuElementBy);
			String isSubmenuOpened = attribute.getElementAttribute(submenuElementBy, "class");
			if ( isSubmenuOpened.contains(open.trim()) )
			{
				wait.waitForElementEnabled(pageElementBy);
				click.clickElement(pageElementBy);
				hybrisWaitForPageLoad();
			}
			else
			{
				wait.waitForElementEnabled(submenuElementBy);
				click.clickElement(submenuElementBy);
				wait.waitForElementEnabled(pageElementBy);
				click.clickElement(pageElementBy);
				hybrisWaitForPageLoad();
			}
		}
	}

	/***
	 * Method to Validate whether BackOffice Login Successful or not
	 */
	public boolean loginSuccessful( )
	{
		boolean flag = false;
		if ( element.isElementDisplayed(TOOLBAR_RIGHT_CONTAINER) )
		{
			flag = true;
		}
		return flag;
	}

	/***
	 * Method to Logout from BackOffice Page
	 */
	public HybrisBOLoginPage logoutFromBackOffice( )
	{
		final WebElement toolbarMenusRightContainer = driver.findElement(TOOLBAR_RIGHT_CONTAINER);
		final List<WebElement> rightMenuButtons = toolbarMenusRightContainer.findElements(MENU_TOOLBAR_BUTTONS);
		WebElement menuItem = null;
		for ( int i = 0 ; i < rightMenuButtons.size() ; i++ )
		{
			menuItem = rightMenuButtons.get(i);
			if ( menuItem
				.getAttribute("title")
				.equals(HybrisBOMenuNames.LOGOUT.getMenuName()) )
			{
				scroll.scrollElementIntoView(menuItem);
				menuItem.click();
			}
		}
		return new HybrisBOLoginPage(driver);
	}

	/***
	 * @param menu
	 *            from left Navigation of Hybris BackOffice
	 * @param subMenu
	 *            from left Navigation of Hybris BackOffice
	 * @return returns to Vertex configuration page
	 */
	public HybrisBOVertexConfigurationPage navigateToConfigurationPage( String menu, String subMenu )
	{
		navigateToBOPage(menu, subMenu);
		hybrisWaitForPageLoad();
		final HybrisBOVertexConfigurationPage vertexConfigPage = initializePageObject(
			HybrisBOVertexConfigurationPage.class);
		return vertexConfigPage;
	}

	/**
	 * @param menu    from left Navigation of Hybris BackOffice
	 * @param subMenu from left Navigation of Hybris BackOffice
	 *
	 * @return returns to BaseStore Electronics Page
	 */
	public HybrisBOBaseStorePage navigateToElectronicsStorePage( String menu, String subMenu )
	{
		hybrisWaitForPageLoad();
		waitForAllMenuOptionsLoad();
		navigateToBOPage(menu, subMenu);
		final HybrisBOBaseStorePage electronicStorePage = initializePageObject(HybrisBOBaseStorePage.class);
		hybrisWaitForPageLoad();
		return electronicStorePage;
	}

	/**
	 * @param menu    from left Navigation of Hybris BackOffice
	 * @param subMenu from left Navigation of Hybris BackOffice
	 * @param page    Page to be selected from Left Navigation of Hybris Backoffice
	 *
	 * @return returns to CronJobs Page
	 */
	public HybrisBOCronJobsPage navigateToCronJobsPage( String menu, String subMenu, String page )
	{
		navigateToBOPage(menu, subMenu, page);
		final HybrisBOCronJobsPage cronJobsPage = initializePageObject(HybrisBOCronJobsPage.class);
		hybrisWaitForPageLoad();
		return cronJobsPage;
	}

	/**
	 * @param menu    from left Navigation of Hybris BackOffice
	 * @param subMenu from left Navigation of Hybris BackOffice
	 *
	 * @return returns to BackOffice Orders Page
	 */
	public HybrisBOOrdersPage navigateToBOOrdersPage( String menu, String subMenu )
	{
		navigateToBOPage(menu, subMenu);
		final HybrisBOOrdersPage ordersPage = initializePageObject(HybrisBOOrdersPage.class);
		return ordersPage;
	}

	/**
	 * @param menu
	 * @param subMenu
	 *
	 * @return returns to Admin Support Page
	 */
	public void navigateToSupportPage( String menu, String subMenu )
	{
		navigateToBOPage(menu, subMenu);
	}

	/**
	 * Waits till all menu option load properly.
	 */
	public void waitForAllMenuOptionsLoad() {
		wait.waitForElementPresent(By.xpath(".//tr[@title='Base Commerce']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Home']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Inbox']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='System']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Catalog']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Multimedia']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='User']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Order']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Price Settings']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Internationalization']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Marketing']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='WCMS']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Cockpit']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Deeplink Urls']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Rule Engine']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Ticket System']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='B2B Approval Process']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Merchandising']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='B2B Commerce']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Vertex']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Personalization']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Subscription']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Entitlements']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='Order Management']"));
		wait.waitForElementPresent(By.xpath(".//tr[@title='yForms']"));
	}
}
