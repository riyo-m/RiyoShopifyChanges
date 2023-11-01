package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

/**
 * Common functions for interacting with checkboxes. This should be declared as an instance
 * variable in VertexAutomationObject
 *
 * @author hho
 */
public class VertexClickUtilities extends VertexSeleniumUtilities
{
	public VertexClickUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * scrolls an element into the viewport* and clicks it *the portion of the
	 * webpage that's currently visible to the user or the selenium browser
	 *
	 * @param elementToClick  the element to be clicked
	 * @param desiredPosition the part of the viewport that selenium scrolls the element to
	 *                        before trying to click it
	 *
	 * @author ssalisbury
	 */
	public void clickElement( final WebElement elementToClick, final PageScrollDestination desiredPosition )
	{
		elementToClick.click();
	}

	/**
	 * Overloads {@link #clickElement(WebElement, PageScrollDestination)}
	 * to find the element with a locator first
	 */
	public void clickElement( final By by, final PageScrollDestination desiredPosition )
	{
		WebElement elementToClick = base.element.getWebElement(by);
		clickElement(elementToClick, desiredPosition);
	}

	/**
	 * scrolls an element into the viewport* and clicks it *the portion of the
	 * webpage that's currently visible to the user or the selenium browser
	 *
	 * @param elementToClick the element to be clicked
	 *
	 * @author ssalisbury
	 */
	public void clickElement( final WebElement elementToClick )
	{
		clickElement(elementToClick, base.defaultScrollDestination);
	}

	public void clickElement( final By locator )
	{
		WebElement elementToClick = base.wait.waitForElementPresent(locator);
		clickElement(elementToClick);
	}

	/**
	 * Performs a double-click on a webelement using the Actions library.  Should be avoided
	 * unless you have a really good reason.
	 *
	 * @param element the element to double click
	 */
	public void performDoubleClick( final WebElement element )
	{
		final Actions action = new Actions(driver);
		action
			.moveToElement(element)
			.doubleClick()
			.build()
			.perform();
	}

	/**
	 * Performs a right-click on a webelement using the Actions library.  Should be avoided
	 * unless you have a really good reason.
	 *
	 * @param element the element to right click
	 */
	public void performRightClick( final WebElement element )
	{
		final Actions action = new Actions(driver);
		action
			.moveToElement(element)
			.contextClick()
			.build()
			.perform();
	}

	/**
	 * Uses javascript to click directly on an HTML element, even if the element
	 * isn't 'visible'.  Uses default scroll behavior.
	 *
	 * @param elem
	 */
	public void javascriptClick( final WebElement elem )
	{
		javascriptClick(elem, base.defaultScrollDestination);
	}

	/**
	 * Uses javascript to click directly on an HTML element, even if the element
	 * isn't 'visible'  Allows specifying scroll behavior
	 *
	 * @param elem   the HTML element that should be clicked on
	 * @param scroll the scroll destination
	 */
	public void javascriptClick( final WebElement elem, PageScrollDestination scroll )
	{
		try
		{
			base.scroll.scrollElementIntoView(elem, scroll);
		}
		catch ( final Exception e )
		{
			e.printStackTrace();
		}

		base.executeJs("arguments[0].click();", elem);
	}

	/**
	 * Uses javascript to click directly on an HTML element, even if the element
	 * isn't 'visible'.  Uses default scroll behavior.
	 *
	 * @param locator
	 */
	public void javascriptClick( final By locator )
	{
		javascriptClick(locator, base.defaultScrollDestination);
	}

	/**
	 * Uses javascript to click directly on an HTML element, even if the element
	 * isn't 'visible'  Allows specifying scroll behavior
	 *
	 * @param locator the HTML element that should be clicked on
	 * @param scroll  the scroll destination
	 */
	public void javascriptClick( final By locator, PageScrollDestination scroll )
	{
		WebElement elem = base.element.getWebElement(locator);
		try
		{
			base.scroll.scrollElementIntoView(elem, scroll);
		}
		catch ( final Exception e )
		{
			e.printStackTrace();
		}

		base.executeJs("arguments[0].click();", elem);
	}

	/**
	 * Use actions to move to the element and perform a click
	 *
	 * @param locator locator of the element
	 */
	public void moveToElementAndClick( final By locator )
	{
		final WebElement element = base.element.getWebElement(locator);

		moveToElementAndClick(element);
	}

	/**
	 * Use actions to move to the element and perform a click
	 *
	 * @param element locator of the element
	 */
	public void moveToElementAndClick( final WebElement element )
	{
		final Actions actions = new Actions(driver);
		actions
			.moveToElement(element)
			.click(element);
		actions.perform();
	}

	public void bringElementToFront( WebElement element )
	{
		base.executeJs("$(arguments[0]).css('zIndex', 9999);", element);
	}

	/**
	 * Overloads {@link #clickElementCarefully(WebElement, boolean)}
	 * to use a By locator to find the element in a container element
	 */
	public void clickElementCarefully( final By locator, final WebElement container, final boolean shouldExpectAlert )
	{
		WebElement elem = base.wait.waitForElementEnabled(locator, container);
		clickElementCarefully(elem, shouldExpectAlert);
	}

	/**
	 * Overloads {@link #clickElementCarefully(WebElement)}
	 * to use a By locator to find the element in a container element
	 */
	public void clickElementCarefully( final By locator, final WebElement container )
	{
		WebElement elem = base.wait.waitForElementEnabled(locator, container);
		clickElementCarefully(elem);
	}

	/**
	 * Overloads {@link #clickElementCarefully(WebElement, boolean)}
	 * to use a By locator to find the element
	 */
	public void clickElementCarefully( final By locator, final boolean shouldExpectAlert )
	{
		WebElement elem = base.wait.waitForElementEnabled(locator);
		clickElementCarefully(elem, shouldExpectAlert);
	}

	/**
	 * Overloads {@link #clickElementCarefully(WebElement)}
	 * to use a By locator to find the element
	 */
	public void clickElementCarefully( final By locator )
	{
		WebElement elem = base.wait.waitForElementEnabled(locator);
		clickElementCarefully(elem);
	}

	/**
	 * this clicks an element on the webpage, but only after making sure that the element is displayed & enabled
	 * Also, this waits afterwards in case clicking the element caused the page to reload something
	 *
	 * @param elem              the element which should be clicked
	 * @param shouldExpectAlert whether an alert will appear and need to be handled after the element is clicked
	 */
	public void clickElementCarefully( final WebElement elem, final boolean shouldExpectAlert )
	{
		base.wait.waitForElementEnabled(elem);
		clickElement(elem);
		if ( shouldExpectAlert )
		{
			base.alert.acceptAlert();
		}
		base.waitForPageLoad();
	}

	/**
	 * Overloads {@link #clickElementCarefully(WebElement, boolean)}
	 * to assume that there won't be an alert
	 */
	public void clickElementCarefully( final WebElement elem )
	{
		clickElementCarefully(elem, false);
	}

	public void clickElementIgnoreExceptionAndRetry(final WebElement elem) {
		try {
			base.wait.waitForElementEnabled(elem);
			clickElement(elem);
		}
		catch (StaleElementReferenceException | ElementNotInteractableException e )
		{
			base.wait.waitForElementEnabled(elem);
			javascriptClick(elem);
		}
	}

	/**
	 * This clicks an element on the webpage, but if StaleElementReferenceException or ElementNotInteractableException occurs
	 * then it will retry finding the element and clicking it again.
	 *
	 * @param locator the element which should be clicked
	 */
	public void clickElementIgnoreExceptionAndRetry(final By locator) {
		try {
			base.wait.waitForElementEnabled(locator);
			driver.findElement(locator).click();
		}
		catch (StaleElementReferenceException | ElementNotInteractableException e )
		{
			base.wait.waitForElementEnabled(locator);
			javascriptClick(locator);
		}
	}
}
