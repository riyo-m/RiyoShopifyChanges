package com.vertex.quality.connectors.netsuite.common.pages;

import com.vertex.quality.connectors.netsuite.common.components.NetsuiteNavigationPane;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteTableComponent;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Represents the installed bundles page
 *
 * @author hho, jyareeda
 */
public class NetsuiteInstalledBundlesPage extends NetsuitePage
{
	public NetsuiteNavigationPane navigationPane;
	protected NetsuiteTableComponent tableComponent;
	protected By tableHeadings = By.id("div__labtab");
	protected By tableBody = By.id("div__bodytab");

	public NetsuiteInstalledBundlesPage( WebDriver driver )
	{
		super(driver);
		navigationPane = new NetsuiteNavigationPane(driver, this);
		tableComponent = new NetsuiteTableComponent(driver, this);
	}

	/**
	 * Gets the bundle id of the bundle with the specified identifier
	 *
	 * @param identifier the unique identifier for the bundle
	 *
	 * @return the bundle id
	 */
	public String getBundleId( String identifier )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(tableBody, tableHeadings, identifier,
			"Bundle ID");
		return tableCell.getText();
	}

	/**
	 * Gets the status (true or false) for the bundle if displayed or not
	 *
	 * @param identifier the unique identifier for the bundle
	 *
	 * @return the status
	 */
	public boolean isBundleDisplayed( String identifier )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(tableBody, tableHeadings, identifier,
			"Bundle ID");
		return tableCell.isDisplayed();
	}

	/**
	 * Gets the version number for the bundle
	 *
	 * @param identifier the unique identifier for the bundle
	 *
	 * @return the version number
	 */
	public String getVersionNumber( String identifier )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(tableBody, tableHeadings, identifier, "Version");
		return attribute.getElementAttribute(tableCell, "textContent");
	}

	/**
	 * Gets the status (true or false) for the bundle
	 *
	 * @param identifier the unique identifier for the bundle
	 *
	 * @return the status
	 */
	public boolean isStatusOkay( String identifier )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(tableBody, tableHeadings, identifier, "Status");

		WebElement checkElement = tableCell.findElement(By.tagName("img"));

		String checkText = checkElement.getAttribute("src");

		return checkText != null && checkText.contains("checkMark");
	}

	/**
	 * Gets the installation date for the bundle
	 *
	 * @param identifier the unique identifier for the bundle
	 *
	 * @return the installation date
	 */
	public String getInstalledOn( String identifier )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(tableBody, tableHeadings, identifier,
			"Installed On");
		return tableCell.getText();
	}

	/**
	 * Gets the last update date
	 *
	 * @param identifier the unique identifier for the bundle
	 *
	 * @return the last update date
	 */
	public String getLastUpdate( String identifier )
	{
		WebElement tableCell = tableComponent.getTableCellByIdentifier(tableBody, tableHeadings, identifier,
			"Last Update");
		return attribute.getElementAttribute(tableCell, "textContent");
	}
}
