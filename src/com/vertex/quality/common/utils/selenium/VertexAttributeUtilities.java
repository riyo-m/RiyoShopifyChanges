package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Common functions for extracting and getting attributes. This should be declared as an instance
 * variable in VertexAutomationObject
 *
 * @author alewis
 */
public class VertexAttributeUtilities extends VertexSeleniumUtilities
{
	public VertexAttributeUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * gets the specified attribute of an element
	 *
	 * @param element       the element that contains the desired information
	 * @param attributeName the attribute in which that element stores the desired information
	 *
	 * @return the desired information that was stored as that attribute's value
	 * Fail- returns null if the element doesn't possess that attribute
	 *
	 * @author ssalisbury
	 */
	public String getElementAttribute( final WebElement element, final String attributeName )
	{
		String attributeValue = element.getAttribute(attributeName);

		return attributeValue;
	}

	/**
	 * @param by
	 * @param attribute
	 *
	 * @return returns the element's provided attribute value
	 */
	public String getElementAttribute( final By by, final String attribute )
	{
		final WebElement element = base.element.getWebElement(by);

		final String attributeValue = element.getAttribute(attribute);
		return attributeValue;
	}

	/**
	 * Waits for an element's attribute to change its value - if the element's attribute doesn't
	 * change, just wait.  Nte that this will throw an exeption if the attribute does not exist at
	 * all, you should first verify that the attribute exists before checking to see if it changed
	 *
	 * @param locator   the locator of the element whose attribute is being waited on to change
	 * @param attribute the specific element attribute that will change
	 *
	 * @author hho
	 */
	public void waitForElementAttributeChange( final By locator, final String attribute )
	{
		waitForElementAttributeChange(locator, attribute, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Waits for an element's attribute to change its value - if the element's attribute doesn't
	 * change, just wait
	 *
	 * @param locator   the locator of the element whose attribute is being waited on to change
	 * @param attribute the specific element attribute that will change
	 * @param timeout   the number of seconds to wait for the attribute to change
	 *
	 * @author hho
	 */
	public void waitForElementAttributeChange( final By locator, final String attribute, long timeout )
	{
		final WebElement element = base.wait.waitForElementPresent(locator, timeout);
		waitForElementAttributeChange(element, attribute, timeout);
	}

	/**
	 * Waits for an element's attribute to change its value - if the element's attribute doesn't
	 * change, just wait
	 *
	 * @param element   the web element whose attribute is being waited on to change
	 * @param attribute the specific element attribute that will change
	 *
	 * @author hho
	 */
	public void waitForElementAttributeChange( final WebElement element, final String attribute )
	{
		waitForElementAttributeChange(element, attribute, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Waits for an element's attribute to change its value - if the element's attribute doesn't
	 * change, just wait
	 *
	 * @param element          the web element whose attribute is being waited on to change
	 * @param attribute        the specific element attribute that will change
	 * @param timeoutInSeconds the number of seconds to wait for the attribute to change
	 *
	 * @author hho
	 */
	public void waitForElementAttributeChange( final WebElement element, final String attribute, long timeoutInSeconds )
	{
		final String previousValue = element.getAttribute(attribute);

		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isChanged = false;
			final String newAttribute = element.getAttribute(attribute);
			if ( newAttribute != null && !newAttribute.equals(previousValue) )
			{
				isChanged = true;
			}
			return isChanged;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	/**
	 * modifies {@link #tryWaitForElementAttributeChange(WebElement, String, long)}
	 * to use locator instead of webelement
	 */
	public void tryWaitForElementAttributeChange( final By locator, final String attribute, long timeoutInSeconds )
	{
		WebElement element = base.wait.waitForElementPresent(locator);
		tryWaitForElementAttributeChange(element, attribute, timeoutInSeconds);
	}

	/**
	 * modifies this {@link #waitForElementAttributeChange(By, String)} (WebElement, String, long)}
	 * to not fail if the wait times out
	 */
	public void tryWaitForElementAttributeChange( final WebElement element, final String attribute,
		long timeoutInSeconds )
	{
		try
		{
			waitForElementAttributeChange(element, attribute, timeoutInSeconds);
		}
		catch ( Exception e )
		{
		}
	}
}
