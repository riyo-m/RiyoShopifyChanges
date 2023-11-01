package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Common functions to be used for hovering over elements.
 * This should be declared as an instance variable in VertexAutomationObject
 *
 * @author hho
 */
public class VertexHoverUtilities extends VertexSeleniumUtilities
{
	public VertexHoverUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * Moves the mouse to hover over the element specified by the given locator (will scroll to the
	 * top of the element)
	 *
	 * @param locator the element's locator
	 *
	 * @author hho
	 */
	public void hoverOverElement( final By locator )
	{
		hoverOverElement(locator, base.defaultScrollDestination);
	}

	/**
	 * Moves the mouse to hover over the element specified by the given locator
	 *
	 * @param locator               the element's locator
	 * @param pageScrollDestination the page scroll destination
	 *
	 * @author hho
	 */
	public void hoverOverElement( final By locator, final PageScrollDestination pageScrollDestination )
	{
		final WebElement element = driver.findElement(locator);
		hoverOverElement(element, pageScrollDestination);
	}

	/**
	 * Moves the mouse to hover over the element specified by the given web element (will scroll to
	 * the top of the element)
	 *
	 * @param element the element
	 *
	 * @author hho
	 */
	public void hoverOverElement( final WebElement element )
	{
		hoverOverElement(element, base.defaultScrollDestination);
	}

	/**
	 * Moves the mouse to hover over the element specified by the given web element
	 *
	 * @param element               the element
	 * @param pageScrollDestination the page scroll destination
	 *
	 * @author hho
	 */
	public void hoverOverElement( final WebElement element, final PageScrollDestination pageScrollDestination )
	{
		base.scroll.scrollElementIntoView(element, pageScrollDestination);
		final Actions builder = new Actions(driver);
		builder
			.moveToElement(element)
			.perform();
	}
}
