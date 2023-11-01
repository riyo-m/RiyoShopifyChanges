package com.vertex.quality.connectors.oraclecloud.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base page for Oracle ERP Cloud connector
 *
 * @author cgajes
 */
public abstract class OracleCloudBasePage extends VertexPage
{

	protected By headerTagLoc = By.tagName("H1");

	public OracleCloudBasePage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Generalized check to make sure the page that has been navigated to is correct
	 * Retrieves header text to compare with expected text for the page
	 *
	 * @return header text of the current page
	 */
	public String checkCorrectPage( )
	{
		String textAttribute = "innerText";
		WebElement header = wait.waitForElementPresent(headerTagLoc);
		String foundHeaderText = attribute.getElementAttribute(header, textAttribute);

		if ( foundHeaderText == null || foundHeaderText == "" )
		{
			String errorMsg = "Header text not found!";
			throw new RuntimeException(errorMsg);
		}

		return foundHeaderText;
	}

	/**
	 * Check page header against expected header to ensure correct page has loaded in
	 * After certain actions are taken on certain pages, user must wait for a slightly new page to be loaded in.
	 * It is technically the same page as before, so waitForTitleContains will not work,
	 * and waitForPageLoad sometimes does not wait long enough
	 *
	 * @param headerTextExpected the page header expected to be shown on the new page
	 */
	public void waitForLoadedPage( String headerTextExpected )
	{
		final WebDriverWait wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(headerTagLoc, headerTextExpected));
	}

	/**
	 * Check page header against expected header to ensure correct page has loaded in
	 * After certain actions are taken on certain pages, user must wait for a slightly new page to be loaded in.
	 * It is technically the same page as before, so waitForTitleContains will not work,
	 * and waitForPageLoad sometimes does not wait long enough.
	 *
	 * This definition allows the user to specify the timeout time.
	 *
	 * @param headerTextExpected the page header expected to be shown on the new page
	 * @param timeout (int) Time waited for page to load in seconds
	 */
	public void waitForLoadedPage(String headerTextExpected, int timeout) {
		final WebDriverWait wait = new WebDriverWait(driver, timeout);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(headerTagLoc, headerTextExpected));
	}

	/**
	 * Find the element that is currently active and return it
	 *
	 * @return active element
	 */
	public WebElement findActiveElement( )
	{
		WebElement ele = driver
			.switchTo()
			.activeElement();
		return ele;
	}
}
