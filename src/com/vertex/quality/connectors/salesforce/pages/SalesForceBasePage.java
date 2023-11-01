package com.vertex.quality.connectors.salesforce.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Basic Address Page.
 *
 * @author brendaj
 */
public class SalesForceBasePage extends VertexPage
{
	protected By SELECTED_APP_MENU = By.xpath("//*[contains(@class, 'active')]/a");
	protected By LOADING_IMAGE = By.cssSelector("#lineItemView_loading[style*='block']");
	protected By LOADING_TEXT = By.xpath(".//*[text()='Loading...']");

	// Set this variable to enable Basic Authentication in all SF connectors
	protected final boolean BASIC_AUTH_ENABLED = false;

	public SalesForceBasePage( WebDriver driver )
	{
		super(driver);
		driver
			.manage()
			.window()
			.setSize(new Dimension(1024, 768));
		waitForPageLoad();
	}

	/**
	 * Click Edit Lines on Quote to get to Product Add/Edit Page
	 */
	public void waitForLoadingImage( )
	{
		wait.waitForElementNotPresent(LOADING_IMAGE);
	}

	/**
	 * Wait for Tab in Salesforce to display
	 */
	public void waitForSalesForceLoaded( )
	{
		try
		{
			Thread.sleep(1000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		waitForPageLoad();
		waitForLoadingImage();
		// wait.waitForElementDisplayed(SELECTED_APP_MENU);
	}


	/**
	 * Explicit wait based on milliseconds
	 * @param milliSeconds
	 */
	public void waitForSalesForceLoaded(long milliSeconds)
	{
		try
		{
			Thread.sleep(milliSeconds);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		waitForPageLoad();
		waitForLoadingImage();
	}

	/**
	 * Remove Currency Sub-String from Price
	 * @param price
	 * @return price without currency
	 */
	public String trimCurrencySubstring(String price)
	{
		String trimCurrency = price.replace("USD ", "");
		return trimCurrency;
	}


}