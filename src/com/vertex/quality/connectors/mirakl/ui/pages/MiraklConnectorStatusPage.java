package com.vertex.quality.connectors.mirakl.ui.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Connector status page of Mirakl connector
 *
 * @author rohit.mogane
 */
public class MiraklConnectorStatusPage extends VertexPage
{
	public MiraklConnectorStatusPage( WebDriver driver )
	{
		super(driver);
	}

	protected By selectOperatorDropDown = By.xpath("//*[@data-testid='user-profile-select']");
	protected By databaseStatusText = By.xpath("//*[contains(text(),'Database Status')]//following::span");
	protected By connectorVersionText = By.xpath("//*[contains(text(),'Connector Version')]//following::div[2]");
	protected By allConnectionsText = By.xpath("//*[text()='All connections are running smoothly.']");
	protected By connectorStatusTab = By.xpath("//a[@data-testid='status-link']");
	protected By operatorTab = By.xpath("//a[@data-testid='operator-link']");
	protected By marketplaceTab = By.xpath("//a[@data-testid='marketplace-link']");
	protected By storesTab = By.xpath("//a[@data-testid='stores-link']");
	protected By ordersTab = By.xpath("//a[@data-testid='orders-link']");
	protected By pageTitle = By.xpath("//*[@data-testid='page-title']");
	protected By miraklConnection = By.xpath("//*[contains(text(),'Mirakl Connection')]//following::span");

	/**
	 * To get database status text
	 *
	 * @return string for database status
	 */
	public String getDatabaseStatusText( )
	{
		WebElement databaseStatusElement = wait.waitForElementDisplayed(databaseStatusText);
		return text.getElementText(databaseStatusElement);
	}

	/**
	 * to get connector version text
	 *
	 * @return string for connector version
	 */
	public String getConnectorVersionText( )
	{
		WebElement connectorVersionElement = wait.waitForElementDisplayed(connectorVersionText);
		return text.getElementText(connectorVersionElement);
	}

	/**
	 * to get all connections status text
	 *
	 * @return string for all connections status
	 */
	public String getAllConnectionsText( )
	{
		WebElement allConnectionsElement = wait.waitForElementDisplayed(allConnectionsText);
		return text.getElementText(allConnectionsElement);
	}

	/**
	 * to click connector status tab
	 */
	public void clickConnectorStatusTab( )
	{
		WebElement connectorsTabElement = wait.waitForElementDisplayed(connectorStatusTab);
		click.javascriptClick(connectorsTabElement);
		waitForPageLoad();
	}

	/**
	 * to click operator tab
	 */
	public void clickOperatorsTab( )
	{
		WebElement operatorsTabElement = wait.waitForElementDisplayed(connectorStatusTab);
		click.javascriptClick(operatorsTabElement);
		waitForPageLoad();
	}

	/**
	 * to click marketplace tab
	 */
	public void clickMarketplaceTab( )
	{
		WebElement marketPlaceTabElement = wait.waitForElementDisplayed(marketplaceTab);
		click.javascriptClick(marketPlaceTabElement);
		waitForPageLoad();
	}

	/**
	 * to click stores tab
	 */
	public void clickStoresTab( )
	{
		WebElement storesTabElement = wait.waitForElementDisplayed(storesTab);
		click.javascriptClick(storesTabElement);
		waitForPageLoad();
	}

	/**
	 * to click orders tab
	 */
	public void clickOrdersTab( )
	{
		WebElement ordersTabElement = wait.waitForElementDisplayed(ordersTab);
		click.javascriptClick(ordersTabElement);
		waitForPageLoad();
	}

	/**
	 * to get title of page
	 *
	 * @return string title of page
	 */
	public String getPageTitle( )
	{
		WebElement pageTitleElement = wait.waitForElementDisplayed(pageTitle);
		return text.getElementText(pageTitleElement);
	}

	/**
	 * to get status of mirakl connection
	 *
	 * @return String for mirakl connection status
	 */
	public String getMiraklConnectionStatus( )
	{
		WebElement miraklConnectionsElement = wait.waitForElementDisplayed(miraklConnection);
		return text.getElementText(miraklConnectionsElement);
	}
}
