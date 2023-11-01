package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.SalesForceLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Lightning UI after Login Page.
 *
 * @author brenda.johnson
 */
public class SalesForceLOMPostLogInPage extends SalesForceBasePage
{
	protected By SWITCH_TO_LIGHTING = By.className("switch-to-lightning");

	protected By PROFILE_IMAGE = By.cssSelector(".profileTrigger .uiImage");
	protected By PROFILE_CARD = By.className("oneUserProfileCard");
	protected By SWITCH_TO_LIGHTNING = By.xpath("//a[contains(text(),'Switch to Lightning')]");

	protected By APP_MENU_DROPDOWN = By.xpath(".//div[@role='navigation']//button//div[@class='slds-icon-waffle']");
	protected By APP_MENU_SEARCH = By.xpath(
		"//one-app-launcher-menu/div/one-app-launcher-search-bar/lightning-input/div/div/input");
	protected By SELECTED_APP_MENU = By.xpath("//*[contains(@class, 'slds-is-active')]/a/span");
	protected By LIST_VIEW_DROPDOWN = By.xpath(".//div[contains(@class, 'listviewdisplayswitcher')]");
	protected By SPLIT_VIEW_DROPDOWN_OPTION = By.xpath(".//li[@role='presentation' and @title='Split View']/a[@aria-checked='false']");

	protected By DELETE_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Delete']");

	protected By APP_MENU_SUB_DROPDOWN = By.xpath(
		".//*[contains(@class,'AppNavMenu')]/div/button[@title='Show Navigation Menu']");
	protected String APP_MENU_SUB_OPTION = "//*[@id='navMenuList']/div/ul/li/div/a/span/span[text()='%s']";

	protected By CLOSE_OPEN_TABS = By.xpath(".//button[contains(@title, 'Close ')]/lightning-primitive-icon");

	public SalesForceLOMPostLogInPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on Tab in Salesforce
	 *
	 * @param tabName
	 */
	public void clickVertexPageTab( String tabName )
	{
		waitForPageLoad();
		window.switchToDefaultContent();
		waitForSalesForceLoaded();
		String tabLocator = String.format(".//nav/div/one-app-nav-bar-item-root/a[contains(@title,'%s')]/parent::*",
			tabName);
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.clickElement(By.xpath(tabLocator));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Tab in Vertex
	 */
	public void clickVertexPageTabMenu( String tabName )
	{
		String tabLocator = String.format(".//a[starts-with(@title,'%s')]", tabName);
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.clickElement(By.xpath(tabLocator));
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Vertex Tab in Salesforce
	 *
	 * @param tabName
	 */
	public void clickVertexPageSubTab( String tabName )
	{
		window.switchToDefaultContent();
		waitForPageLoad();
		waitForSalesForceLoaded();
		try {
			driver.switchTo().frame(driver.findElement(By.xpath(".//iframe")));
		} catch (Exception e){}

		String tabLocator = String.format(".//ul[contains(@class, 'nav')]/li[contains(@title,'%s')]", tabName);
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.clickElement(By.xpath(tabLocator));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Check if Sales force loaded in Lighting view , If No switch to Lightning
	 * view
	 */
	public void checkAndSwitchLightningView( )
	{
		waitForPageLoad();
		try
		{
			wait.waitForElementDisplayed(SELECTED_APP_MENU, 10);
		}
		catch ( Exception e )
		{
		}

		waitForPageLoad();
		if ( element.isElementDisplayed(PROFILE_IMAGE) )
		{
			VertexLogger.log("Salesforce is in Lightning View.", SalesForceLogInPage.class);
		}
		else if ( element.isElementDisplayed(SWITCH_TO_LIGHTING) )
		{
			click.clickElement(SWITCH_TO_LIGHTNING);
			waitForPageLoad();
			waitForSalesForceLoaded();
		}
		else
			VertexLogger.log("Salesforce is in unknown View.", SalesForceLogInPage.class);

		closeOpenTabs();
	}

	/**
	 * method to close all open tabs
	 */
	public void closeOpenTabs( )
	{
		waitForSalesForceLoaded();
		while ( element.isElementDisplayed(CLOSE_OPEN_TABS) )
		{
			click.clickElement(CLOSE_OPEN_TABS);
			waitForSalesForceLoaded();
		}
	}

	/**
	 * Method to switch between different applications
	 *
	 * @param appName
	 */
	public void switchToAppMenu( String appName )
	{
		try
		{
			window.switchToDefaultContent();
		}
		catch ( Exception e )
		{
		}

		try
		{
			waitForPageLoad();
			waitForSalesForceLoaded();
		}
		catch ( Exception e )
		{
		}

		String selectedMenuItem = "";
		try
		{
			selectedMenuItem = attribute.getElementAttribute(SELECTED_APP_MENU, "textContent");
		}
		catch ( Exception e )
		{
		}

		if ( selectedMenuItem.equals(appName) )
		{
			VertexLogger.log(selectedMenuItem + " App Menu item is already selected", SalesForceLogInPage.class);
		}
		else
		{
			click.clickElement(APP_MENU_DROPDOWN);
			wait.waitForElementDisplayed(APP_MENU_SEARCH);
			By apMenuItem = By.xpath(
				String.format(".//one-app-launcher-menu-item/a/div/lightning-formatted-rich-text/span/p[text()='%s']",
					appName));
			if ( !appName.equals("Sales"))
			{
				setSearchValue(appName);
				apMenuItem = By.xpath(
					String.format(".//div/lightning-formatted-rich-text/span/p/b[text()='%s']", appName));
			}
			click.clickElement(apMenuItem);
			waitForPageLoad();

			if( appName.equals("Order Management"))
			{
				checkAndSwitchToSplitView();
			}
		}
	}

	/**
	 * switch to sub app menu
	 *
	 * @param appName
	 */
	public void switchToSubAppMenu( String appName )
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		window.switchToDefaultContent();

		waitForPageLoad();
		waitForSalesForceLoaded();
		String selectedMenuItem = "";
		try
		{
			selectedMenuItem = attribute.getElementAttribute(APP_MENU_SUB_DROPDOWN, "textContent");
		}
		catch ( Exception e )
		{
		}

		if ( selectedMenuItem.equals(appName) )
		{
			VertexLogger.log(selectedMenuItem + " App Menu item is already selected", SalesForceLogInPage.class);
		}
		else
		{
			click.javascriptClick(APP_MENU_SUB_DROPDOWN);
			waitForPageLoad();
			waitForSalesForceLoaded();
			By apMenuItem = By.xpath(String.format(APP_MENU_SUB_OPTION, appName));
			wait.waitForElementDisplayed(apMenuItem);
			click.javascriptClick(apMenuItem);
		}
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Click on a Order Tab in Salesforce
	 *
	 * @param tabName
	 */
	public void clickOrderPageTab( String tabName )
	{
		SalesForceLOMAccountsPage accountPage = new SalesForceLOMAccountsPage(driver);
		switchToSubAppMenu("Accounts");
		accountPage.searchFor(tabName + Keys.ENTER);
		accountPage.selectExistingAccount(tabName);
		waitForPageLoad();
	}

	/**
	 * enter Search Value to search thru Apps in Salesforce
	 *
	 * @param searchValue
	 */
	public void setSearchValue( String searchValue )
	{
		text.enterText(APP_MENU_SEARCH, searchValue);
	}

	/**
	 * Check if split view is enabled, if not enable it
	 */
	public void checkAndSwitchToSplitView()
	{
		switchToSubAppMenu("Orders");
		wait.waitForElementDisplayed(LIST_VIEW_DROPDOWN);
		click.clickElement(LIST_VIEW_DROPDOWN);

		if (element.isElementPresent(SPLIT_VIEW_DROPDOWN_OPTION))
		{
			wait.waitForElementDisplayed(SPLIT_VIEW_DROPDOWN_OPTION);
			click.clickElement(SPLIT_VIEW_DROPDOWN_OPTION);
			VertexLogger.log( "Switching to split view", SalesForceLogInPage.class);
		}
		else
		{
			VertexLogger.log( "Already in split view", SalesForceLogInPage.class);
		}

	}
}
