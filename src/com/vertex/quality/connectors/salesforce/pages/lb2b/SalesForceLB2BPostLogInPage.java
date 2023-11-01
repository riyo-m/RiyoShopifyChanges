package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.SalesForceLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Common functions for anything related to Salesforce Lightning UI after Login Page.
 *
 * @author brenda.johnson
 */
public class SalesForceLB2BPostLogInPage extends SalesForceBasePage
{
	protected By SWITCH_TO_LIGHTING = By.className("switch-to-lightning");

	protected By PROFILE_IMAGE = By.cssSelector(".profileTrigger .uiImage");
	protected By PROFILE_CARD = By.className("oneUserProfileCard");
	protected By SWITCH_TO_LIGHTNING = By.xpath("//a[contains(text(),'Switch to Lightning')]");

	protected By APP_MENU_DROPDOWN = By.xpath(".//div[@role='navigation']//button//div[@class='slds-icon-waffle']");
	protected By APP_MENU_SEARCH = By.xpath(
		"//one-app-launcher-menu/div/one-app-launcher-search-bar/lightning-input/div/div/input");
	protected By SELECTED_APP_MENU = By.xpath(".//*[contains(@class, 'appName')]/span");
	protected By APP_MENU_VIEW_ALL_BUTTON = By.xpath(".//button[@aria-label='View All Applications']");

	protected By SETUP_DROPDOWN = By.xpath("//*/ul/li[7]/div/div/div[1]/div/div/a/div/lightning-icon");
	protected By SET_UP_LINK = By.id("all_setup_home");
	protected By DELETE_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Delete']");
	protected By APP_MENU_SUB_DROPDOWN = By.xpath(
		".//*[contains(@class,'AppNavMenu')]/div/button[@title='Show Navigation Menu']");
	protected String APP_MENU_SUB_OPTION = "//*[@id='navMenuList']/div/ul/li/div/a/span/span[text()='%s']";
	protected By NEW_OPPORTUNITY_BUTTON = By.xpath("//*/div/div[1]/div[3]/div/ul/li/a/div");
	protected By CLOSE_OPEN_TABS = By.xpath(".//button[contains(@title, 'Close ')]/lightning-primitive-icon");
	protected By CLOSE_TAB_TWO = By.xpath("(.//button[contains(@title, 'Close ')]/lightning-primitive-icon)[2]");
	protected By CLOSE_TAB_THREE = By.xpath("(.//button[contains(@title, 'Close ')]/lightning-primitive-icon)[3]");
	protected String TAB_NAME = "(.//span[contains(@class, 'title')])[%s]";

	public SalesForceLB2BPostLogInPage( WebDriver driver )
	{
		super(driver);
	}


	public void clickSetup(){
		wait.waitForElementEnabled(SETUP_DROPDOWN);
		click.clickElementCarefully(SETUP_DROPDOWN);

		wait.waitForElementEnabled(SET_UP_LINK);
		click.clickElementCarefully(SET_UP_LINK);
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
	 * Click on Tab in Salesforce
	 *
	 * @param tabName
	 */
	public void clickVertexPageTab( String tabName )
	{
		waitForPageLoad();
		window.switchToDefaultContent();
		waitForSalesForceLoaded();
		waitForPageLoad();
		String tabLocator = String.format(".//nav/div/one-app-nav-bar-item-root/a[contains(@title,'%s')]/parent::*",
			tabName);
		try
		{
			wait.waitForElementDisplayed(By.xpath(tabLocator));
		}
		catch ( Exception e )
		{
		}
		click.clickElement(By.xpath(tabLocator));
		waitForPageLoad();
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
		waitForSalesForceLoaded();
		String tabLocator = String.format(".//ul[contains(@class, 'nav')]/li[contains(@title,'%s')]", tabName);

		try
		{
			wait.waitForElementDisplayed(By.xpath(tabLocator), 10);
		}
		catch(Exception e)
		{
			driver
					.switchTo()
					.frame(driver.findElement(By.xpath(".//iframe")));
		}

		click.clickElement(By.xpath(tabLocator));
	}

	/**
	 * Check if Sales force loaded in Lighting view , If No switch to Lightning
	 * view
	 */
	public void checkAndSwitchLightningView( )
	{
		waitForPageLoad();
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(SELECTED_APP_MENU);
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
		int openTabCount = 0;
		while ( element.isElementDisplayed(CLOSE_OPEN_TABS))
		{
			openTabCount = element.getWebElements(CLOSE_OPEN_TABS).size();
			if (getTabName(1).equals("Lauren Boyle | Contact") && getTabName(2).equals("QA_B2B_VAT TestAutomation | Contact") && openTabCount >= 3){
				try{
					wait.waitForElementDisplayed(CLOSE_TAB_THREE);
					click.clickElement(CLOSE_TAB_THREE);
				} catch (Exception ex){
					ex.printStackTrace();
					break;
				}
			}
			else if(openTabCount >= 3){
				click.clickElement(CLOSE_OPEN_TABS);
			}
			else{
				break;
			}
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
			e.printStackTrace();
		}

		try
		{
			waitForPageLoad();
			waitForSalesForceLoaded();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
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
		}
	}

	/**
	 * Click on a Sales Tab in Salesforce
	 */
	public void clickSalesPageTab( String tabName )
	{
		String tabLocator = String.format(".//one-app-nav-bar-item-root/a/span[contains(text(),'%s')]/parent::*",
			tabName);
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		new WebDriverWait(driver, 5)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(By.xpath(tabLocator)));
		click.javascriptClick(By.xpath(tabLocator));
		waitForSalesForceLoaded();
	}

	/**
	 * Click on a Commerce Tab in Salesforce
	 */
	public void clickCommercePageTab( String tabName )
	{
		String tabLocator = String.format(".//*[@role='tablist']/.//ul/li/a/.//span[contains(text(),'%s')]/parent::*",
			tabName);
		try
		{
			wait.waitForElementDisplayed(By.xpath(tabLocator));
		}
		catch ( Exception e )
		{
			switchToSubAppMenu("Contacts");
			new SalesForceB2BContactsPage(driver).clickContact(tabName);
			wait.waitForElementDisplayed(By.xpath(tabLocator));
		}
		new WebDriverWait(driver, 5)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(By.xpath(tabLocator)));
		click.javascriptClick(By.xpath(tabLocator));
		waitForPageLoad();
	}

	/**
	 * enter Search Value to search thru Apps in Salesforce
	 *
	 * @param searchValue
	 */
	public void setSearchValue( String searchValue )
	{
		wait.waitForElementDisplayed(APP_MENU_SEARCH);
		text.enterText(APP_MENU_SEARCH, searchValue);
	}

	/**
	 * switch to sub app menu
	 *
	 * @param appName
	 */
	public void switchToSubAppMenu( String appName )
	{
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
			VertexLogger.log(selectedMenuItem + " App Sub Menu item is already selected", SalesForceLogInPage.class);
		}
		else
		{
			click.clickElement(APP_MENU_SUB_DROPDOWN);
			By apMenuItem = By.xpath(String.format(APP_MENU_SUB_OPTION, appName));
			wait.waitForElementDisplayed(apMenuItem);
			click.javascriptClick(apMenuItem);
		}
		waitForPageLoad();
	}

	/**
	 * Get tab name
	 * @param tabNum tab number to return name for
	 *
	 * @return name of specified tab number
	 */
	public String getTabName(int tabNum){
		String tabLocator = String.format(TAB_NAME, tabNum);
		By tabNumPath = By.xpath(tabLocator);
		wait.waitForElementDisplayed(tabNumPath);
		return text.getElementText(tabNumPath);

	}

	/**
	 * Set All Apps View search value
	 *
	 * @param searchValue
	 */
	public void setAllAppsMenuSearchValue(String searchValue){
		wait.waitForElementDisplayed(APP_MENU_SEARCH);
		text.enterText(APP_MENU_SEARCH, searchValue);
		waitForSalesForceLoaded();
	}

	/**
	 * Select App from All Apps view
	 *
	 * @param appName
	 */
	public void selectAppFromAllAppsView(String appName){
		String locator = String.format(".//div[contains(@class, 'slds-app-launcher__tile')]//p/mark[text()='%s']", appName);
		By app = By.xpath(locator);
		wait.waitForElementDisplayed(app);
		click.clickElement(app);
		waitForSalesForceLoaded();
	}
}
