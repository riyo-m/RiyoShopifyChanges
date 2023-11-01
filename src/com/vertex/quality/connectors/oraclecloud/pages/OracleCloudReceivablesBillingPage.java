package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Billing page for application
 * Can create or search for AR invoices from here
 *
 * @author cgajes
 */
public class OracleCloudReceivablesBillingPage extends OracleCloudBasePage
{
	protected By blockingPlane = By.className("AFBlockingGlassPane");

	protected By receivablesTasksTabLoc = By.cssSelector("div[title='Tasks']");
	protected By receivablesSearchTabLoc = By.cssSelector("div[title='Search: Transactions']");
	protected By receivablesMenuLoc = By.cssSelector("td[id*='tabb']");
	protected By searchByTransactionNumberLoc = By.cssSelector("input[id*='value00']");
	protected By searchButtonLoc = By.cssSelector("button[id*='search']");

	protected By linksTag = By.tagName("A");

	protected String manageHeader = "Manage";

	public OracleCloudReceivablesBillingPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Checks whether the receivables menu has been successfully opened
	 * via clicking one of the tabs on the right side of the screen
	 *
	 * @return whether or not the menu is displayed
	 */
	public boolean checkOpenMenu( )
	{
		boolean open = false;

		if ( element.isElementDisplayed(receivablesMenuLoc) )
		{
			open = true;
		}

		return open;
	}

	/**
	 * Opens the tasks menu by clicking the Tasks receivables tab
	 * on the right side of the screen
	 *
	 * @return opened menu
	 */
	public WebElement openTasksTab( )
	{
		WebElement tasksTab = wait.waitForElementDisplayed(receivablesTasksTabLoc);
		wait.waitForElementEnabled(tasksTab);
		tasksTab.click();

		WebElement menu = wait.waitForElementDisplayed(receivablesMenuLoc);

		return menu;
	}

	/**
	 * Opens the search menu by clicking the Search receivables tab
	 * on the right side of the screen
	 *
	 * @return opened menu
	 */
	public WebElement openSearchTab( )
	{
		WebElement searchTab = wait.waitForElementDisplayed(receivablesSearchTabLoc);
		wait.waitForElementEnabled(searchTab);
		searchTab.click();

		WebElement menu = wait.waitForElementDisplayed(receivablesMenuLoc);

		return menu;
	}

	/**
	 * After opening the Search receivables tab,
	 * enter a value into the transaction number search bar
	 *
	 * @param menu      search receivables menu
	 * @param searchFor value to input into search
	 */
	public void searchTransactionNumber( WebElement menu, String searchFor )
	{
		wait.waitForElementDisplayed(menu);
		WebElement searchBar = wait.waitForElementEnabled(searchByTransactionNumberLoc, menu);
		text.clearText(searchBar);
		text.enterText(searchBar, searchFor);
	}

	/**
	 * Clicks the search button on the search receivable menu
	 *
	 * @param menu search receivables menu
	 */
	public OracleCloudManageTransactionsPage clickSearch( WebElement menu )
	{

		wait.waitForElementDisplayed(menu);
		WebElement searchBtn = wait.waitForElementEnabled(searchButtonLoc);
		click.clickElementCarefully(searchBtn);

		waitForLoadedPage(manageHeader);

		OracleCloudManageTransactionsPage page = new OracleCloudManageTransactionsPage(driver);
		return page;
	}

	/**
	 * Clicks on a link on the opened receivables tab menu to navigate to another page
	 *
	 * @param page to navigate to
	 *
	 * @return initialized page that has been navigated to
	 */
	public <T extends OracleCloudBasePage> T clickMenuLink( OracleCloudPageNavigationData page, WebElement menu )
	{
		List<WebElement> panelLinkList = wait.waitForAllElementsDisplayed(linksTag, menu);
		WebElement link = element.selectElementByText(panelLinkList, page.getPageName());

		wait.waitForElementNotEnabled(blockingPlane);
		wait.waitForElementEnabled(link);
		link.click();

		try
		{
			wait.waitForElementNotDisplayedOrStale(menu, 60);
		}
		catch ( TimeoutException e )
		{
			panelLinkList = wait.waitForAllElementsDisplayed(linksTag, menu);
			link = element.selectElementByText(panelLinkList, page.getPageName());
			wait.waitForElementEnabled(link);
			link.click();
		}

		Class pageClass = page.getPageClass();

		return initializePageObject(pageClass);
	}
}
