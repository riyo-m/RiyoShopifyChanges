package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Payables invoices page for application
 *
 * @author cgajes
 */
public class OracleCloudPayablesInvoicesPage extends OracleCloudBasePage
{
	protected By blockingPlane = By.className("AFBlockingGlassPane");

	protected By payablesTasksTabLoc = By.cssSelector("div[title='Tasks']");
	protected By payablesMenuLoc = By.cssSelector("td[id*='tabb']");
	protected By linksTag = By.tagName("A");

	public OracleCloudPayablesInvoicesPage( WebDriver driver ) { super(driver); }

	/**
	 * Opens the tasks menu by clicking the Tasks receivables tab
	 * on the right side of the screen
	 *
	 * @return opened menu
	 */
	public WebElement openTasksTab( )
	{
		WebElement menu;
		WebElement tasksTab;

		tasksTab = wait.waitForElementEnabled(payablesTasksTabLoc);
		click.clickElement(tasksTab);
		menu = wait.waitForElementDisplayed(payablesMenuLoc);

		return menu;
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

		wait.waitForElementEnabled(link);
		wait.waitForElementNotEnabled(blockingPlane);
		click.clickElementCarefully(link);

		try
		{
			wait.waitForElementNotDisplayedOrStale(menu, 15);
		}
		catch ( TimeoutException e )
		{
			click.javascriptClick(link);
			wait.waitForElementNotDisplayedOrStale(menu, 15);
		}

		Class pageClass = page.getPageClass();

		return initializePageObject(pageClass);
	}
}
