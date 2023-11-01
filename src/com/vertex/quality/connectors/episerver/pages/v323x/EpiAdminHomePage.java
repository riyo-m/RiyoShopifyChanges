package com.vertex.quality.connectors.episerver.pages.v323x;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.CMSAdminPages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Episerver Administration Home Page - contains all re-usable methods &
 * Navigations specific to this page.
 */
public class EpiAdminHomePage extends VertexPage
{
	public EpiAdminHomePage( WebDriver driver )
	{
		super(driver);
	}

	protected By DASHBOARD_PAGE = By.linkText("Dashboard");
	protected By ADMIN_MENU_FRAME_ID = By.id("FullRegion_AdminMenu");
	protected By USER_ICON = By.cssSelector(
		"a[class^='epi-navigation-global_user'][title = 'vertexadmin@vertexinc.com']");
	protected By LOGOUT_LINK = By.cssSelector("[class*='logout']");
	protected By SITE_NAVIGATION_MENU = By.className("epi-quickNavigator-dropdown");
	protected String SITE_NAVIGATION_MENU_OPTION = ".//ul[@id='epi-quickNavigator-menu']//a[text()='<<text_replace>>']";
	protected String CMS_HEADER_MENU = ".//a[normalize-space(.)='<<text_replace>>']";

	protected String CMS_HEADER_MENU_SVG = "//*[local-name()='svg' and @data-icon='<<text_replace>>']";



	/**
	 * This method is used to navigate to Menu on EpiSever Admin Portal
	 */
	public void clickOnMainMenu( String menu )
	{
		window.switchToDefaultContent();
		WebElement navigationMenu = wait.waitForElementPresent(SITE_NAVIGATION_MENU);
		click.javascriptClick(navigationMenu);
		WebElement menuOption = wait.waitForElementPresent(By.xpath(SITE_NAVIGATION_MENU_OPTION.replace("<<text_replace>>", menu)));
		click.javascriptClick(menuOption);
		waitForPageLoad();
		VertexLogger.log("Clicked Menu option: " + menu);
	}

	/**
	 * This method is used to navigate to SubMenu on Epi Sever Admin Portal
	 */
	public void clickOnSubMenu( String submenu )
	{
		WebElement cmsMenu = wait.waitForElementDisplayed(By.xpath(CMS_HEADER_MENU.replace("<<text_replace>>", submenu)));
		click.clickElement(cmsMenu);
		waitForPageLoad();
		VertexLogger.log("Clicked submenu option: " + submenu);
	}

	/**
	 * This method is used to navigate to SubMenu on Epi Sever Admin Portal
	 */
	public void clickOnSubMenuSVG( String submenu )
	{
		WebElement cmsMenu = wait.waitForElementDisplayed(By.xpath(CMS_HEADER_MENU_SVG.replace("<<text_replace>>", submenu)));

		click.clickElement(cmsMenu);
		waitForPageLoad();
		VertexLogger.log("Clicked submenu SVG option: " + submenu);
	}

	/**
	 * This method is used to navigate to Page on Episever CMS Admin Page of Admin
	 * Portal
	 */
	public void selectTabInCmsAdminPage( String tab_name )
	{
		By TAB_XPATH = By.xpath(String.format("//*[@class='epi-tabView-tab' and text()='%s']", tab_name));
		if ( element.isElementDisplayed(ADMIN_MENU_FRAME_ID) )
		{
			window.switchToFrame(ADMIN_MENU_FRAME_ID, 60);
		}

		if ( element.isElementDisplayed(TAB_XPATH) )
		{
			click.clickElement(TAB_XPATH);
			waitForPageLoad();
			VertexLogger.log(String.format("Selected Tab: <b> '%s'</b>", tab_name));
		}
		else
		{
			VertexLogger.log(String.format("Specified Tab: '%s' is not found", tab_name));
		}
	}

	/**
	 * This method is used to Validate the Default Dashboard page
	 */
	public void validateDashBoardDefaultPage( )
	{
		if ( element.isElementDisplayed(DASHBOARD_PAGE) )
		{
			String TitleHighlight = attribute.getElementAttribute(DASHBOARD_PAGE, "aria-pressed");
			if ( TitleHighlight == "true" )
			{
				VertexLogger.log("Dashboard Page is highlighted");
			}
			else
			{
				VertexLogger.log("Dashboard Page is NOT highlighted");
			}
		}
		else
		{
			VertexLogger.log("Dashboard Page is NOT displayed");
		}
	}

	/**
	 * This method is used to navigate to Page in CMSADMINLEFTNAVIGATION
	 *
	 * @return True/False
	 */
	private boolean navigateToPageFromCMSAdminLeftNavigation( String menu_name, String page_name )
	{
		boolean flag = false;
		By PAGE_XPATH = By.xpath(String.format(
			"//*[contains(@class, 'epi-navigation-standard')][a[contains(text(), '%s')]]" +
			"//a[contains(@class, 'epi-navigation-global_user') and contains(text(), '%s')]", menu_name, page_name));
		this.expandTabMenuIfClosed(menu_name);
		if ( element.isElementDisplayed(PAGE_XPATH) )
		{
			flag = true;
			click.clickElement(PAGE_XPATH);
			waitForPageLoad();
			driver
				.switchTo()
				.defaultContent();
		}
		else
		{
			VertexLogger.log(String.format("Specified Page: '%s' is not found", page_name));
		}
		if ( !flag )
		{
			VertexLogger.log(String.format("Specified Page: '%s' is not found", page_name), VertexLogLevel.ERROR);
		}
		waitForPageLoad();
		return flag;
	}

	/**
	 * This method is used to expand left navigation if it is closed
	 */
	private void expandTabMenuIfClosed( String menu_name )
	{
		By MENU_TUPLE = By.xpath(
			String.format("//*[contains(@class, 'epi-navigation-standard')][a[contains(text(), '%s')]]", menu_name));
		if ( element.isElementDisplayed(MENU_TUPLE) )
		{
			String is_closed_attribute = attribute.getElementAttribute(MENU_TUPLE, "class");
			if ( is_closed_attribute.contains("closed") )
			{
				click.clickElement(MENU_TUPLE);
				waitForPageLoad();
				VertexLogger.log(String.format("Provided Menu: <b>'%s'</b> is expanded", menu_name));
			}
			else
			{
				VertexLogger.log(String.format("Provided Menu: <b>'%s'</b> is already expanded", menu_name));
			}
		}
		else
		{
			VertexLogger.log(String.format("Specified Menu: '%s' is not found", menu_name));
		}
	}

	/**
	 * This method is used to navigate to "Vertex O Series" page
	 *
	 * @return EpiOseriesPage object after successful navigation
	 */
	public EpiOseriesPage navigateToOseriespage( )
	{
		EpiOseriesPage oSeriespage = null;
		if ( navigateToPageFromCMSAdminLeftNavigation(CMSAdminPages.TOOLS.text, CMSAdminPages.VERTEX_O_SERIES.text) )
		{
			waitForPageLoad();
			oSeriespage = initializePageObject(EpiOseriesPage.class);
		}
		return oSeriespage;
	}

	/**
	 * This method is used to click on User Icon and expand its section
	 */
	public void clickUserIcon( )
	{
		click.clickElement(USER_ICON);
		waitForPageLoad();
	}

	/**
	 * This method is used to logout from Admin Site
	 */
	public void adminlogout( )
	{
		window.switchToDefaultContent();
		clickUserIcon();
		wait.waitForElementDisplayed(LOGOUT_LINK);
		click.clickElement(LOGOUT_LINK);
		waitForPageLoad();

		if ( element.isElementDisplayed(LOGOUT_LINK) )
		{
			click.clickElement(LOGOUT_LINK);
		}
		waitForPageLoad();
	}
}
