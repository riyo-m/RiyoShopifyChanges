package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.SalesForceLogInPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Basic UI after Login Page.
 *
 * @author
 */
public class SalesForceCRMPostLogInPage extends SalesForceBasePage
{
	protected By LEX_DIALOG = By.id("tryLexDialog");
	protected By LEX_DIALOG_CLOSE = By.id("tryLexDialogX");

	protected By SWITCH_TO_LIGHTING = By.className("switch-to-lightning");

	protected By PROFILE_IMAGE = By.cssSelector(".profileTrigger .uiImage");
	protected By PROFILE_CARD = By.className("oneUserProfileCard");
	protected By SEARCH_FIELD = By.id("phSearchInput");
	protected By SWITCH_TO_CLASSIC = By.xpath("//a[contains(text(),'Switch to Salesforce Classic')]");

	protected By APP_MENU_DROPDOWN = By.id("tsidLabel");
	protected By APP_MENU_ITEMS_BLOCK = By.id("tsid-menuItems");
	protected By SELECTED_APP_MENU = By.id("tsidLabel");
	protected By SEARCH_BUTTON = By.id("phSearchButton");
	protected By VERIFY_SEARCH_RESULT = By.xpath(
		"//div[@id='searchResultsWarningMessageBox']//table//tr//td//div[contains(text(),'No matches found')]");

	protected By SET_UP_LINK = By.id("setupLink");
	protected By DELETE_BUTTON = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Delete']");

	public SalesForceCRMPostLogInPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on Tab in Vertex
	 */
	public void clickVertexPageTab( String tabName )
	{
		String tabLocator = String.format(".//*[@title='%s' or starts-with(@id, '%s')]", tabName,
			tabName.replace(" ", ""));
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.clickElement(By.xpath(tabLocator));
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
	 * Check if Sales force CRM loaded in Lighting view , If Yes switch to Classic
	 * view
	 */
	public void checkAndSwitchClassicView( )
	{
		waitForPageLoad();
		if ( element.isElementDisplayed(SWITCH_TO_LIGHTING) )
		{
			VertexLogger.log("Salesforce CRM is in Classic View.", SalesForceLogInPage.class);
		}
		else if ( element.isElementDisplayed(PROFILE_IMAGE) )
		{
			click.clickElement(PROFILE_IMAGE);
			wait.waitForElementDisplayed(PROFILE_CARD);
			click.clickElement(SWITCH_TO_CLASSIC);
			waitForSalesForceLoaded();
		}
	}

	/**
	 * Close LEX dialog, if appears after user login and while switching with app
	 * menu [Top Right corner of every page]
	 */
	public void closeLightningExperienceDialog( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(SELECTED_APP_MENU);

		if ( element.isElementDisplayed(LEX_DIALOG) )
		{
			VertexLogger.log("Lightning Experience dialog appeared", SalesForceLogInPage.class);
			click.javascriptClick(LEX_DIALOG_CLOSE);
			waitForSalesForceLoaded();
		}
	}

	/**
	 * Method to switch between different applications
	 *
	 * @param appName
	 */
	public void switchToCRMAppMenu( String appName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SELECTED_APP_MENU);
		String selectedMenuItem = attribute.getElementAttribute(SELECTED_APP_MENU, "textContent");
		if ( selectedMenuItem.equals(appName) )
		{
			VertexLogger.log(selectedMenuItem + " App Menu item is already selected", SalesForceLogInPage.class);
		}
		else
		{
			click.clickElement(APP_MENU_DROPDOWN);
			wait.waitForElementDisplayed(APP_MENU_ITEMS_BLOCK);
			By apMenuItem = By.xpath(String.format("//a[text()='%s']", appName));
			click.clickElement(apMenuItem);
			waitForPageLoad();
		}
	}

	/**
	 * To Click on SetUp link
	 */
	public void clickSetupLink( )
	{
		click.clickElement(SET_UP_LINK);
		waitForPageLoad();
	}

	/**
	 * Click on Tab in Vertex
	 *
	 * @param tabName
	 */
	public void clickSalesPageATab( String tabName )
	{
		waitForSalesForceLoaded();
		String tabLocator = String.format("//a[@title='%s Tab']", tabName);
		wait.waitForElementDisplayed(By.xpath(tabLocator));
		click.clickElement(By.xpath(tabLocator));
		waitForPageLoad();
	}

	/**
	 * Click on Tab in Vertex
	 *
	 * @param tabName
	 */
	public void clickSalesPageTab( String tabName )
	{
		waitForPageLoad();
		String tabLocator = String.format(tabName + "_Tab");
		wait.waitForElementDisplayed(By.id(tabLocator));
		click.clickElement(By.id(tabLocator));
		waitForPageLoad();
	}


	/**
	 * To Select the items from Recent Items table
	 *
	 * @param itemName
	 */
	public void selectItemsFromRecentItems( String itemName )
	{
		refreshPage();
		waitForPageLoad();
		if ( itemName.length() > 15 )
		{
			itemName = itemName.substring(0, 15);
		}
		String itemRow = String.format("//span[contains(text(),'%s')]", itemName);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();
	}

	/**
	 * To Select and delete the items from Recent Items table
	 *
	 * @param itemName
	 */
	public void removeItemsFromRecentItems( String itemName )
	{
		selectItemsFromRecentItems(itemName);
		deleteItem();
	}

	/**
	 * To Verify Search Result
	 */
	public boolean verifySearchResult( )
	{
		waitForPageLoad();
		boolean getSearchResult = element.isElementDisplayed(VERIFY_SEARCH_RESULT);
		return getSearchResult;
	}

	/**
	 * method to click delete button
	 */
	public void deleteItem()
	{
		waitForPageLoad();
		click.clickElement(DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * To delete the items from Search
	 *
	 * @param itemName
	 */
	public void removeItemsFromSearch( String itemName )
	{
		wait.waitForElementDisplayed(SEARCH_FIELD);
		click.clickElement(SEARCH_FIELD);
		text.enterText(SEARCH_FIELD, itemName);
		click.clickElement(SEARCH_BUTTON);
		waitForPageLoad();
		if ( verifySearchResult() )
		{
			VertexLogger.log("No records found to delete");
		}
		else
		{
			String itemRow = String.format("//table[@class='list']//a[contains(text(),'%s')]", itemName);
			click.clickElement(By.xpath(itemRow));
			deleteItem();
		}
	}
}
