package com.vertex.quality.connectors.magento.admin.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoAdminPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * M2AdminConfigPage where configurations are set
 *
 * @author alewis
 */
public class M2AdminConfigPage extends MagentoAdminPage
{
	By salesPrimaryID = By.id("system_config_tabs");
	By salesPrimaryClass = By.className("config-nav-block");
	By salesSecondaryClass = By.className("admin__page-nav-title");
	String salesButtonText = "SALES";
	By salesTertiaryClass = By.className("admin__page-nav-items");
	By salesQuaternaryClass = By.className("admin__page-nav-item");
	String taxButtonText = "Tax";
	String shippingMethodsButtonText = "Delivery Methods";
	protected By shippingSettings = By.xpath(".//span[text()='Shipping Settings']");
	protected By saveConfigButton = By.id("save");

	public M2AdminConfigPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Clicks the SalesTab
	 *
	 * @return the Configurations Page
	 */
	public M2AdminConfigPage clickSalesTab( )
	{
		wait.waitForElementDisplayed(findSalesTab());
		WebElement vertexServicesButton = findSalesTab();

		if ( vertexServicesButton != null )
		{
			scroll.scrollElementIntoView(vertexServicesButton);
			vertexServicesButton.click();
		}
		else
		{
			String errorMsg = "Sales Tab not found";
			throw new RuntimeException(errorMsg);
		}

		return initializePageObject(M2AdminConfigPage.class);
	}

	/**
	 * Locates the SalesTab
	 *
	 * @return the Sales Button
	 */
	protected WebElement findSalesTab( )
	{
		WebElement primaryClass = wait.waitForElementPresent(salesPrimaryID);
		List<WebElement> primaryButtons = primaryClass.findElements(salesPrimaryClass);
		WebElement salesTabButton = element.selectElementByNestedLabel(primaryButtons, salesSecondaryClass,
			salesButtonText);
		return salesTabButton;
	}

	/**
	 * Tests to see if the SalesTab
	 *
	 * @return a boolean if the Sales Tab is present
	 */
	public boolean isSalesTabVisible( )
	{
		WebElement salesTab = findSalesTab();

		boolean isTabPresent = salesTab != null && salesTab.isDisplayed();

		return isTabPresent;
	}

	/**
	 * clicks the Tax Tab
	 *
	 * @return the Sales Tax Configuration Page
	 */
	public M2AdminSalesTaxConfigPage clickTaxTab( )
	{
		wait.waitForElementDisplayed(findTaxTab());
		WebElement taxButton = findTaxTab();

		if ( taxButton != null )
		{
			wait.waitForElementDisplayed(taxButton);
			click.clickElement(taxButton);
		}
		else
		{
			String errorMsg = "Tax Tab not found";
			throw new RuntimeException(errorMsg);
		}

		return initializePageObject(M2AdminSalesTaxConfigPage.class);
	}

	/**
	 * clicks the Shipping Method Tab
	 *
	 * @return the Sales Shipping Method Configuration Page
	 */
	public M2AdminSalesShippingMethodsConfigPage clickShippingMethodTab( )
	{
		WebElement shippingMethodButton = findShippingMethodTab();

		if ( shippingMethodButton != null )
		{
			scroll.scrollElementIntoView(shippingMethodButton);
			click.clickElementCarefully(shippingMethodButton);
		}
		else
		{
			String errorMsg = "shippingMethod Tab not found";
			throw new RuntimeException(errorMsg);
		}

		return initializePageObject(M2AdminSalesShippingMethodsConfigPage.class);
	}

	/**
	 * locates the tax tab
	 *
	 * @return the Tax Tab
	 */
	protected WebElement findTaxTab( )
	{
		WebElement primaryClass = wait.waitForElementPresent(salesPrimaryID);
		List<WebElement> primaryButtons = wait.waitForAllElementsPresent(salesPrimaryClass, primaryClass);
		WebElement taxTabButton = null;

		for ( WebElement button : primaryButtons )
		{
			WebElement secondaryButton = wait.waitForElementPresent(salesSecondaryClass, button);
			String buttonText = secondaryButton.getText();
			if ( salesButtonText.equals(buttonText) )
			{
				WebElement tertiaryClass = wait.waitForElementPresent(salesTertiaryClass, button);
				List<WebElement> tertiaryButtons = wait.waitForAllElementsPresent(salesQuaternaryClass, tertiaryClass);
				taxTabButton = element.selectElementByText(tertiaryButtons, taxButtonText);
				if ( taxTabButton != null )
				{
					break;
				}
			}
		}

		return taxTabButton;
	}

	/**
	 * locates the Shipping Methods tab
	 *
	 * @return the Shipping Method Tab
	 */
	protected WebElement findShippingMethodTab( )
	{
		WebElement primaryClass = wait.waitForElementPresent(salesPrimaryID);
		List<WebElement> primaryButtons = wait.waitForAllElementsPresent(salesPrimaryClass, primaryClass);
		WebElement shippingMethodTabButton = null;

		for ( WebElement button : primaryButtons )
		{
			WebElement secondaryButton = wait.waitForElementPresent(salesSecondaryClass, button);
			String buttonText = text.getElementText(secondaryButton);
			if ( salesButtonText.equals(buttonText) )
			{
				WebElement tertiaryClass = wait.waitForElementPresent(salesTertiaryClass, button);
				List<WebElement> tertiaryButtons = wait.waitForAllElementsPresent(salesQuaternaryClass, tertiaryClass);
				shippingMethodTabButton = element.selectElementByText(tertiaryButtons, shippingMethodsButtonText);
				if ( shippingMethodTabButton != null )
				{
					break;
				}
			}
		}

		return shippingMethodTabButton;
	}

	/**
	 * test to see if the tax tab is visible
	 *
	 * @return if the Tax tab is present
	 */
	public boolean isTaxTabVisible( )
	{
		WebElement taxTab = findTaxTab();

		boolean isTabPresent = taxTab != null && taxTab.isDisplayed();

		return isTabPresent;
	}

	/**
	 * Navigates to Shipping Settings
	 *
	 * @return M2AdminShippingSettingsPage
	 */
	public M2AdminShippingSettingsPage goToShippingSettingsTab() {
		waitForSpinnerToBeDisappeared();
		click.moveToElementAndClick(wait.waitForElementPresent(shippingSettings));
		waitForSpinnerToBeDisappeared();
		return new M2AdminShippingSettingsPage(driver);
	}

	/**
	 * Saves all configurations
	 */
	public void saveConfigurations() {
		waitForSpinnerToBeDisappeared();
		click.moveToElementAndClick(wait.waitForElementPresent(saveConfigButton));
		waitForSpinnerToBeDisappeared();
	}
}