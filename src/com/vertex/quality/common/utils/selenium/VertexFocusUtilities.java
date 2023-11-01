package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * this class represents all the methods used to focus on WebElements
 * This should be declared as an instance variable in VertexAutomationObject
 *
 * @author omars, hho, ssalisbury
 */
public class VertexFocusUtilities extends VertexSeleniumUtilities
{
	public VertexFocusUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	//******************* FOCUS - REVIEW **********************//

	/**
	 * Overloads {@link #focusOnElementJavascript(WebElement)} to find the element with a locator first
	 */
	public void focusOnElementJavascript( final By locator )
	{
		WebElement elem = base.wait.waitForElementDisplayed(locator);
		focusOnElementJavascript(elem);
	}

	/**
	 * Puts the browser's focus on an element, which should also move the viewport to include it
	 *
	 * Uses JavaScript!
	 *
	 * @param elem the element to focus on
	 *
	 * @author hho, omar5, ssalisbury
	 */
	public void focusOnElementJavascript( final WebElement elem )
	{
		final String focus_script = "arguments[0].focus()";

		base.executeJs(focus_script, elem);
	}
}
