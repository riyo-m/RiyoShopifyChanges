package com.vertex.quality.connectors.hybris.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

public class HybrisBasePage extends VertexPage
{
	public SoftAssert softAssertion;

	public HybrisBasePage( WebDriver driver )
	{
		super(driver);

		softAssertion = new SoftAssert();

		waitForPageLoad();
	}

	protected By LOADING_ICON = By.className("loading-icon");
	protected By PROGRESS_METER = By.className("progressmeter");
	protected By LOADING_INDICATOR = By.className("z-loading-indicator");

	/***
	 * Method to waitforPageLoad in Hybris
	 * i.e. In Hybris, waitforpageload behavior is strange and consistant, because sometimes
	 * Progressbar is displayed as part of pageload
	 * and some other times, Loading icon is displayed as part of pageload
	 * So to avoid this issue, creating a generic method which can be used in multiple locations in Hybris connector.
	 */
	public void hybrisWaitForPageLoad( )
	{
		waitForPageLoad();
		wait.waitForElementNotDisplayed(LOADING_ICON);
		wait.waitForElementNotDisplayed(PROGRESS_METER);
		wait.waitForElementNotDisplayed(LOADING_INDICATOR);
		waitForPageLoad();
	}
}
