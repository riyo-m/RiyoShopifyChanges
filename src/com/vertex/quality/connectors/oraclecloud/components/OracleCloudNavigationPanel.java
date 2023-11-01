package com.vertex.quality.connectors.oraclecloud.components;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.components.base.OracleCloudComponent;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPageNavigationData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Handles interaction with the navigation panel on the upper left hand corner,
 * such as opening it and clicking links inside
 * (work in progress)
 *
 * @author cgajes
 */
public class OracleCloudNavigationPanel extends OracleCloudComponent
{
	protected By navButtonLocator = By.cssSelector("a[title='Navigator'][class*='TabletNavigatorIcon']");
	protected By navPanelLocator = By.cssSelector(
		"div[id*='pt1:_UISmmp2::popup-container'][data-afr-popupid='pt1:_UISmmp2']");
	protected By navPanelLinkLocator = By.xpath("//*[@id='pt1:_UISnvr:0:nv_npgl1']");
	protected By navPanelLinkBackupLocator = By.cssSelector("div[id*='pt1:_UISnvr:0:nv_npgl1']");
	protected By navPanelReceivablesLocator = By.xpath("//div[@title='Receivables']");
	protected By navPanelPayablesLocator = By.xpath("//div[@title='Payables']");
	protected By navPanelProcurementLocator = By.xpath("//div[@title='Procurement']");
	protected By billingLocator = By.xpath("//a[@title='Billing']");
	protected By invoicesLocator = By.xpath("//a[contains(@id, 'payables_invoices')]");
	protected By paymentsLocator = By.xpath("//a[contains(@id, 'payables_payments')]");
	protected By paymentsDashboardLocator = By.xpath("//a[contains(@id, 'payables_dashboard')]");
	protected By purchaseOrdersLocator = By.xpath("//a[contains(@id, 'procurement_PurchaseOrders')]");
	protected By purchaseReqsLocator = By.xpath("//a[contains(@id, 'purchase_requisitions')]");
	protected By navPanelOrderManagementLocator = By.xpath("//div[@title='Order Management']");
	protected By orderManagementLocator = By.xpath("//a[contains(@id, 'order_management_order_management')]");
	protected By navPanelEnterpriseLocator = By.xpath("//div[@title='My Enterprise']");
	protected By setupMaintenanceLocator = By.xpath("//a[contains(@id, 'MyEnterprise_setup_and_maintenance')]");
	protected By navPanelToolsLocator = By.xpath("//div[@title='Tools']");
	protected By scheduledProcessesLocator = By.xpath("//a[contains(@id, 'scheduled_processes')]");
	protected By panelLinksTag = By.tagName("A");

	public OracleCloudNavigationPanel( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	public boolean checkPanelOpen( )
	{
		boolean open = false;
		if ( element.isElementDisplayed(navPanelLocator) )
		{
			open = true;
		}

		return open;
	}

	/**
	 * Clicks on the navigation panel button in the upper left corner
	 * Will either open or close the navigation panel
	 *
	 * @return WebElement for panel (null if panel was closed)
	 */
	public WebElement clickNavigationButton( )
	{
		WebElement panel;
		WebElement navButton = wait.waitForElementDisplayed(navButtonLocator);
		click.clickElement(navButton);

		if ( checkPanelOpen() )
		{
			panel = wait.waitForElementDisplayed(navPanelLocator);
		}
		else
		{
			panel = null;
		}

		return panel;
	}

	/**
	 * Clicks on any one of the links on the navigation panel to
	 * attempt navigation to that page
	 *
	 * @return page being navigated to
	 *
	 * TODO Michael Salomone - investigate and clean up this method
	 */
	public <T extends OracleCloudBasePage> T clickPanelLink( OracleCloudPageNavigationData page )
	{
		By navPanelMenuLoc = getMenuLoc(page);
		By navPanelPageLoc = getPageLoc(page);

		jsWaiter.sleep(9000);
		try
		{
			List<WebElement> panelLinkList = wait.waitForAllElementsDisplayed(navPanelLinkLocator);
		}
		catch ( WebDriverException wde )
		{
			VertexLogger.log("Could not find default nav panel locator. Resorting to backup locator.",
				VertexLogLevel.WARN);
			List<WebElement> panelLinkList = wait.waitForAllElementsDisplayed(navPanelLinkBackupLocator);
		}

		WebElement menuTab = wait.waitForElementDisplayed(navPanelMenuLoc);
		click.clickElement(menuTab);

		WebElement pageLink = wait.waitForElementDisplayed(navPanelPageLoc);
		click.clickElement(pageLink);

		wait.waitForElementNotPresent(navPanelLocator);

		Class pageClass = page.getPageClass();

		return initializePageObject(pageClass);
	}

	/**
	 * Retrieve the xpath to the appropriate menu given a page.
	 *
	 * @return The By selector for the navigation pane menu.
	 *
	 * @author msalomone
	 */
	private By getMenuLoc( OracleCloudPageNavigationData page )
	{
		By menuLoc = null;
		switch ( page )
		{
			case PAYABLES_INVOICES:
				menuLoc = navPanelPayablesLocator;
				break;
			case PAYABLES_PAYMENTS:
				menuLoc = navPanelPayablesLocator;
				break;
			case PAYABLES_PAYMENTS_DASHBOARD:
				menuLoc = navPanelPayablesLocator;
				break;
			case PROCUREMENT_PURCHASE_ORDERS:
				menuLoc = navPanelProcurementLocator;
				break;
			case PROCUREMENT_PURCHASE_REQUISITIONS:
				menuLoc = navPanelProcurementLocator;
				break;
			case ORDERMANAGEMENT_OM:
				menuLoc = navPanelOrderManagementLocator;
				break;
			case ENTERPRISE_SETUP_MAINTENANCE:
				menuLoc = navPanelEnterpriseLocator;
				break;
			default:
				menuLoc = navPanelReceivablesLocator;
				break;
		}
		return menuLoc;
	}

	/**
	 * Retrieve the xpath to the appropriate menu given a page.
	 *
	 * @return The By selector for the navigation pane menu.
	 *
	 * @author msalomone
	 */
	private By getPageLoc( OracleCloudPageNavigationData page )
	{
		By pageLoc = null;
		switch ( page )
		{
			case PAYABLES_INVOICES:
				pageLoc = invoicesLocator;
				break;
			case PAYABLES_PAYMENTS:
				pageLoc = paymentsLocator;
				break;
			case PAYABLES_PAYMENTS_DASHBOARD:
				pageLoc = paymentsDashboardLocator;
				break;
			case PROCUREMENT_PURCHASE_ORDERS:
				pageLoc = purchaseOrdersLocator;
				break;
			case PROCUREMENT_PURCHASE_REQUISITIONS:
				pageLoc = purchaseReqsLocator;
				break;
			case ORDERMANAGEMENT_OM:
				pageLoc = orderManagementLocator;
				break;
			case ENTERPRISE_SETUP_MAINTENANCE:
				pageLoc = setupMaintenanceLocator;
				break;
			default:
				pageLoc = billingLocator;
				break;
		}
		return pageLoc;
	}
}