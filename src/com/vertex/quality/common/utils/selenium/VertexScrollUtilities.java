package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Common functions for anything related to scrolling to a different part of the webpage.
 * This should be declared as an instance variable in VertexAutomationObject
 *
 * @author hho, ssalisbury
 */
public class VertexScrollUtilities extends VertexSeleniumUtilities
{
	public VertexScrollUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * scrolls the browser page until the given element is in the given position
	 * within the viewport*
	 *
	 * *the portion of the page that's visible to the user or the selenium browser
	 *
	 * @param element         the element that should be user-visible
	 * @param desiredPosition which part of the viewport the element should end up in after the
	 *                        scrolling
	 *
	 * @author ssalisbury
	 */
	public void scrollElementIntoView( final WebElement element, final PageScrollDestination desiredPosition )
	{
		final String scrollDestinationJs = desiredPosition.navigationJavascript;
		base.executeJs(scrollDestinationJs, element);
	}

	/**
	 * scrolls the browser page until the given element is entirely within the
	 * viewport*
	 *
	 * *the part of the page that's visible to the user or the selenium browser
	 *
	 * @param element the element that should be user-visible
	 *
	 * @author ssalisbury
	 */
	public void scrollElementIntoView( final WebElement element )
	{
		scrollElementIntoView(element, base.defaultScrollDestination);
	}

	/**
	 * Overloads {@link #scrollElementIntoView(WebElement)} to first find the element with a locator
	 */
	public void scrollElementIntoView( final By loc )
	{
		WebElement elem = base.wait.waitForElementDisplayed(loc);
		scrollElementIntoView(elem);
	}

	/**
	 * Overloads {@link #scrollElementIntoView(WebElement, PageScrollDestination)} to first find the element with a
	 * locator
	 */
	public void scrollElementIntoView( final By loc, final PageScrollDestination desiredPosition )
	{
		WebElement elem = base.wait.waitForElementDisplayed(loc);
		scrollElementIntoView(elem, desiredPosition);
	}

	/**
	 * Scrolls a table object left to the degree of the provided position.
	 *
	 * @param loc The table's SCROLLBAR locator (not the table locator)
	 * @param tableId The table's entire Id value.
	 * @param desiredPosition A number that represents the degree of scrolling (i.e. 4200)
	 */
	public void scrollTableLeft( final By loc, String tableId, String desiredPosition) {
		base.wait.waitForElementDisplayed(loc);
		String script = "document.getElementById('"+tableId+"').scrollLeft -= "+desiredPosition;
		base.executeJs(script);
	}

	/**
	 * Scrolls a table object right to the degree of the provided position.
	 *
	 * @param loc The table's SCROLLBAR locator (not the table locator)
	 * @param tableId The table's entire Id value.
	 * @param desiredPosition A number that represents the degree of scrolling (i.e. 4200)
	 */
	public void scrollTableRight( final By loc, String tableId, String desiredPosition) {
		base.wait.waitForElementDisplayed(loc);
		String script = "document.getElementById('"+tableId+"').scrollLeft += "+desiredPosition;
		base.executeJs(script);
	}

	/**
	 * Scroll to the bottom of the page
	 * */
	public void scrollBottom(){
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
	}
}
