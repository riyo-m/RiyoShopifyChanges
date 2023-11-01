package com.vertex.quality.connectors.orocommerce.components.admin;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroTaxCalculationPage;
import com.vertex.quality.connectors.orocommerce.pages.admin.OroVertexSettingsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * represents the system configurations side menu
 * contains all the navigations methods necessary to all the pages within that menu
 *
 * @author alewis
 */
public class configurationsMenu extends VertexComponent
{

	public configurationsMenu( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By menuContLoc = By.className("system-configuration-container");
	protected By searchBarLoc = By.cssSelector("input[type='search']");
	protected By vertexSettingsTabLoc = By.id("vertex_settings_anchor");
	protected By taxCalculationTabLoc = By.id("tax_calculation_anchor");
	protected By xpathTitle = By.xpath("//a[@title='Expand All']");

	/**
	 * locates the vertex settings tab withing the menu and clicks on it
	 * @return new page object of Vertex Settings Page
	 */
	public OroVertexSettingsPage goToVertexSettingsPage( )
	{
		OroVertexSettingsPage vertexSettingsPage;
		WebElement menuParentCont = wait.waitForElementDisplayed(menuContLoc);
		WebElement expandAll = wait.waitForElementDisplayed(xpathTitle);
		click.clickElementCarefully(expandAll);
		WebElement vertexSettingsTab = wait.waitForElementEnabled(vertexSettingsTabLoc, menuParentCont);
		click.clickElementCarefully(vertexSettingsTab);
		vertexSettingsPage = new OroVertexSettingsPage(driver);
		return vertexSettingsPage;
	}

	/**
	 * locates the Tax Calculations tab withing the menu and clicks on it
	 * @return new page object of Tax Calculations Page
	 */
	public OroTaxCalculationPage goToTaxCalculationPage( )
	{
		OroTaxCalculationPage taxCalculationPage;
		WebElement menuParentCont = wait.waitForElementDisplayed(menuContLoc);
		WebElement vertexSettingsTab = wait.waitForElementEnabled(taxCalculationTabLoc, menuParentCont);
		click.clickElementCarefully(vertexSettingsTab);
		taxCalculationPage = new OroTaxCalculationPage(driver);
		return taxCalculationPage;
	}

	/**
	 * locates the search field in the menu
	 * @return webElement representing that field
	 */
	public WebElement findSearchBar( )
	{
		WebElement menuParentCont = wait.waitForElementDisplayed(menuContLoc);
		WebElement searchBar = wait.waitForElementEnabled(searchBarLoc, menuParentCont);
		return searchBar;
	}
}
