package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * This utility class contains functions for waiting for conditions to be true about WebElements.
 * It also includes functions for getting certain elements only once a condition is true.
 *
 * There are three basic states an element can have:
 * 1. Present (is it in the DOM tree at all)
 * 2. Visible (is the element actually visible to the user)
 * 3. Enabled (is the element enabled and can it be interacted with)
 *
 * @author ssalisbury, dgorecki
 */
public class VertexWaitUtilities extends VertexSeleniumUtilities
{
	public VertexWaitUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	//******************** ELEMENT PRESENT ********************//

	/**
	 * Wait for an element present within a specified container element.  This method does not
	 * verify if the element is displayed or enabled.  Uses the default timeout.
	 *
	 * @param locator   the element to wait for
	 * @param container the container to search within
	 *
	 * @return the element once it has been found
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementPresent( final By locator, final WebElement container )
	{
		return waitForElementPresent(locator, container, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element present within a specified container element.  This method does not
	 * verify if the element is displayed or enabled.  Allows setting a custom timeout.
	 *
	 * @param locator          the element to wait for
	 * @param container        the container to search within
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return the element once it has been found
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementPresent( final By locator, final WebElement container, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isPresent = base.element.isElementPresent(locator, container);
			return isPresent;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		return container.findElement(locator);
	}

	/**
	 * Wait for an element present on the page.  This method does not
	 * verify if the element is displayed or enabled.  Uses the default timeout.
	 *
	 * @param locator the element for wait for
	 *
	 * @return the element once it as been found
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementPresent( final By locator )
	{
		return waitForElementPresent(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element present on the page.  This method does not
	 * verify if the element is displayed or enabled.  Uses the default timeout.
	 *
	 * @param locator          the element for wait for
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return the element once it as been found
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementPresent( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isPresent = base.element.isElementPresent(locator);
			return isPresent;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		return driver.findElement(locator);
	}

	/**
	 * Wait for an element to NOT be present.  Uses default timeout
	 *
	 * @param locator the locator for the element that should NOT be present
	 *
	 * @author dgorecki
	 */
	public void waitForElementNotPresent( final By locator )
	{
		waitForElementNotPresent(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to NOT be present.  Allows setting a custm wait time
	 *
	 * @param locator          the locator for the element that should NOT be present
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @author dgorecki
	 */
	public void waitForElementNotPresent( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isPresent;
			isPresent = base.element.isElementPresent(locator);
			return !isPresent;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	/**
	 * Waits for a findElements call to return one or more matching elements.  Does not
	 * guarantee more will not be loaded after the method returns.  Uses default timeout
	 *
	 * @param locator the locator of the elements to wait for
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsPresent( final By locator )
	{
		List<WebElement> elements = waitForAllElementsPresent(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
		return elements;
	}

	/**
	 * Waits for a findElements call to return one or more matching elements.  Does not
	 * guarantee more will not be loaded after the method returns.  Allows setting a custom timeout
	 *
	 * @param locator          the locator of the elements to wait for
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsPresent( final By locator, final long timeoutInSeconds )
	{
		waitForElementPresent(locator, timeoutInSeconds);
		List<WebElement> elements = driver.findElements(locator);
		return elements;
	}

	/**
	 * Waits for a findElements call to return one or more matching elements. withinn a specified container  Does not
	 * guarantee more will not be loaded after the method returns.  Uses default timeout
	 *
	 * @param locator   the locator of the elements to wait for
	 * @param container the WebElement to search within
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsPresent( final By locator, final WebElement container )
	{
		List<WebElement> elements = waitForAllElementsPresent(locator, container,
			VertexAutomationObject.DEFAULT_TIMEOUT);
		return elements;
	}

	/**
	 * Waits for a findElements call to return one or more matching elements. withinn a specified container  Does not
	 * guarantee more will not be loaded after the method returns.  Allows setting a custom timeout
	 *
	 * @param locator          the locator of the elements to wait for
	 * @param container        the WebElement to search within
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsPresent( final By locator, final WebElement container,
		final long timeoutInSeconds )
	{
		waitForElementPresent(locator, container, timeoutInSeconds);
		List<WebElement> elements = container.findElements(locator);
		return elements;
	}

	//******************** ELEMENT VISIBILITY ********************//

	/**
	 * Wait for an element to be displayed on the page.  This method does not
	 * verify if the element is enabled.  Uses the default timeout.
	 *
	 * @param locator the element to wait to be displayed
	 *
	 * @return the element once it as been found and is displayed
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementDisplayed( final By locator )
	{
		return waitForElementDisplayed(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to be displayed on the page.  This method does not
	 * verify if the element is enabled.  Allows specifying a custom timeout
	 *
	 * @param locator          the element to wait to be displayed
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return the element once it as been found and is displayed
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementDisplayed( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isElementDisplayed(locator);
			return isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
		return driver.findElement(locator);
	}

	/**
	 * Wait for an element to be displayed on the page.  This method does not
	 * verify if the element is enabled.  Uses the default timeout.
	 *
	 * @param element the element to wait to be displayed
	 *
	 * @return the element once it as been found and is displayed
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementDisplayed( final WebElement element )
	{
		return waitForElementDisplayed(element, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to be displayed on the page.  This method does not
	 * verify if the element is enabled.  Allows specifying a custom timeout
	 *
	 * @param element          the element to wait to be displayed
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return the element once it as been found and is displayed
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementDisplayed( final WebElement element, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isElementDisplayed(element);
			return isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
		return element;
	}

	/**
	 * Wait for an element withing a container to be displayed on the page.  This method does not
	 * verify if the element is enabled.  Uses the default timeout.
	 *
	 * @param locator   the element to wait to be displayed
	 * @param container the container WebElement to search within
	 *
	 * @return the element once it as been found and is displayed
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementDisplayed( final By locator, final WebElement container )
	{
		return waitForElementDisplayed(locator, container, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to be displayed on the page.  This method does not
	 * verify if the element is enabled.  Allows specifying a custom timeout
	 *
	 * @param locator          the element to wait to be displayed
	 * @param container        the container WebElement to search within
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return the element once it as been found and is displayed
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementDisplayed( final By locator, final WebElement container,
		final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isElementDisplayed(locator, container);
			return isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
		return container.findElement(locator);
	}

	/**
	 * Waits for the first matching element to be displayed and then retrieves
	 * all matching elements.  Does not guarantee more will not be loaded after the method returns.
	 *
	 * @param locator the locator of the elements to wait for
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsDisplayed( final By locator )
	{
		final List<WebElement> elements = waitForAllElementsDisplayed(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
		return elements;
	}

	/**
	 * Waits for the first matching element to be displayed and then retrieves
	 * all matching elements.  Does not guarantee more will not be loaded after the method returns.
	 * Allows specifying a custom timeout
	 *
	 * @param locator          the locator of the elements to wait for
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsDisplayed( final By locator, final long timeoutInSeconds )
	{
		waitForElementDisplayed(locator, timeoutInSeconds);

		final List<WebElement> elements = driver.findElements(locator);
		return elements;
	}

	/**
	 * Waits for the first matching element within a container to be displayed and then retrieves
	 * all matching elements in the container.  Does not guarantee more will not be loaded after
	 * the method returns.
	 *
	 * @param locator   the locator of the elements to wait for
	 * @param container the container WebElement to search within
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsDisplayed( final By locator, final WebElement container )
	{
		final List<WebElement> elements = waitForAllElementsDisplayed(locator, container,
			VertexAutomationObject.DEFAULT_TIMEOUT);
		return elements;
	}

	/**
	 * Waits for the first matching element within a container to be displayed and then retrieves
	 * all matching elements in the container.  Does not guarantee more will not be loaded after
	 * the method returns. Allows specifying a custom timeout
	 *
	 * @param locator          the locator of the elements to wait for
	 * @param container        the container WebElement to search within
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki
	 */
	public List<WebElement> waitForAllElementsDisplayed( final By locator, final WebElement container,
		final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isElementDisplayed(locator, container);
			return isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
		return container.findElements(locator);
	}

	/**
	 * Waits for any matching element to be displayed and then retrieves
	 * all matching elements.
	 * Does not guarantee more will not be loaded after the method returns.
	 * Allows specifying a custom timeout
	 *
	 * @param locator          the locator of the elements to wait for
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki ssalisbury
	 */
	public List<WebElement> waitForAnyElementsDisplayed( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isAnyElementDisplayed(locator);
			return isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
		return base.element.getWebElements(locator);
	}

	/**
	 * Overloads {@link #waitForAnyElementsDisplayed(By, long)}
	 * to provide a default value DEFAULT_TIMEOUT for the parameter timeoutInSeconds
	 */
	public List<WebElement> waitForAnyElementsDisplayed( final By locator )
	{
		List<WebElement> elements = waitForAnyElementsDisplayed(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
		return elements;
	}

	/**
	 * Waits for any matching element within a container to be displayed and then retrieves
	 * all matching elements in the container.
	 * Does not guarantee more will not be loaded after the method returns.
	 * Allows specifying a custom timeout
	 *
	 * @param locator          the locator of the elements to wait for
	 * @param container        the container WebElement to search within
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return a list containing all matching elements, if any
	 *
	 * @author dgorecki ssalisbury
	 */
	public List<WebElement> waitForAnyElementsDisplayed( final By locator, final WebElement container,
		final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isAnyElementDisplayed(locator, container);
			return isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
		return base.element.getWebElements(locator, container);
	}

	/**
	 * Overloads {@link #waitForAnyElementsDisplayed(By, WebElement, long)}
	 * to provide a default value DEFAULT_TIMEOUT for the parameter timeoutInSeconds
	 */
	public List<WebElement> waitForAnyElementsDisplayed( final By locator, final WebElement container )
	{
		List<WebElement> elements = waitForAnyElementsDisplayed(locator, container,
			VertexAutomationObject.DEFAULT_TIMEOUT);
		return elements;
	}

	/**
	 * Overloads {@link #waitForElementNotDisplayed(By, long)}
	 * to use a default value DEFAULT_TIMEOUT for the parameter timeoutInSeconds
	 */
	public void waitForElementNotDisplayed( final By locator )
	{
		waitForElementNotDisplayed(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Waits for a specified element to not be displayed.  Be careful with this one - this is only to be used
	 * if you know the element is still present, but is no longer visible.
	 *
	 * @param locator          the locator for the element
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @author dgorecki
	 */
	public void waitForElementNotDisplayed( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isElementDisplayed(locator);
			return !isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	/**
	 * Waits for a specified element to not be displayed.  Be careful with this one - this is only to be used
	 * if you know the element is still present, but is no longer visible.
	 *
	 * @param element          the element that should not be visible
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @author dgorecki
	 */
	public void waitForElementNotDisplayed( final WebElement element, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed;
			isDisplayed = base.element.isElementDisplayed(element);
			return !isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	/**
	 * Overloads {@link #waitForElementNotDisplayed(WebElement, long)} to use the default timeout length
	 */
	public void waitForElementNotDisplayed( final WebElement element )
	{
		waitForElementNotDisplayed(element, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * waits for the element identified by the given helper function to no longer be displayed
	 *
	 * Performance warning?- maybe shouldn't be used except when stale element exceptions are
	 * occurring and can't be handled more efficiently
	 *
	 * @param elemFinder helper function which retrieves the element in question
	 *                   TLDR: just pass a lambda with the form "() -> {code that returns a WebElement}"
	 *
	 *                   This object could also store a method reference, e.g. a method reference
	 *                   to a class's member method like VertexWaitUtilities::waitForElementPresent
	 */
	public void waitForElementNotDisplayed( final Supplier<WebElement> elemFinder )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayed = false;

			WebElement element = elemFinder.get();
			if ( element != null )
			{
				isDisplayed = element.isDisplayed();
			}

			return !isDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.DEFAULT_TIMEOUT);
		wait.until(condition);
	}

	//******************** ELEMENT ENABLED ********************//

	/**
	 * Overloads {@link #waitForElementEnabled(By, long)} to use the default timeout length
	 */
	public WebElement waitForElementEnabled( final By locator )
	{
		return waitForElementEnabled(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to be enabled (and displayed) on the page. Allows specifying a custom timeout
	 *
	 * @param locator          the element to wait to be enabled (and displayed)
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @return the element once it as been found and is enabled (and displayed)
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementEnabled( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isEnabled;
			isEnabled = base.element.isElementEnabled(locator);
			return isEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		WebElement element = driver.findElement(locator);
		return element;
	}

	/**
	 * Overloads {@link #waitForElementEnabled(By, WebElement, long)} to use the default timeout length
	 */
	public WebElement waitForElementEnabled( final By locator, final WebElement container )
	{
		return waitForElementEnabled(locator, container, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to be enabled (& also displayed) within a container on the page.
	 * Allows specifying a custom timeout
	 *
	 * @param locator          the element to wait to be enabled (and displayed)
	 * @param container        the container WebElement to search within
	 * @param timeoutInSeconds the maximum amount of time to wait in seconds
	 *
	 * @return the element once it as been found and is enabled (and displayed)
	 *
	 * @author dgorecki
	 */
	public WebElement waitForElementEnabled( final By locator, final WebElement container, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isEnabled;
			isEnabled = base.element.isElementEnabled(locator, container);
			return isEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		WebElement element = container.findElement(locator);
		return element;
	}

	/**
	 * Overloads {@link #waitForElementNotEnabled(By, long)} to use the default timeout length
	 */
	public void waitForElementNotEnabled( final By locator )
	{
		waitForElementNotEnabled(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to not be enabled (and displayed) on the page. Allows specifying a custom timeout
	 *
	 * @param locator          the element which should eventually not be enabled (and displayed)
	 * @param timeoutInSeconds the maximum amount of time to wait in seconds
	 *
	 * @author dgorecki ssalisbury
	 */
	public void waitForElementNotEnabled( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isNotEnabled;
			isNotEnabled = !base.element.isElementEnabled(locator);
			return isNotEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	/**
	 * Overloads {@link #waitForElementNotEnabled(By, WebElement, long)} to use the default timeout length
	 */
	public void waitForElementNotEnabled( final By locator, final WebElement container )
	{
		waitForElementNotEnabled(locator, container, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait for an element to not be enabled (& also displayed) within a container on the page.
	 * Allows specifying a custom timeout
	 *
	 * @param locator          the element which should eventually not be enabled (and displayed)
	 * @param container        the container WebElement to search within
	 * @param timeoutInSeconds the maximum amount of time to wait in seconds
	 *
	 * @author dgorecki ssalisbury
	 */
	public void waitForElementNotEnabled( final By locator, final WebElement container, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isNotEnabled;
			isNotEnabled = !base.element.isElementEnabled(locator, container);
			return isNotEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	public WebElement waitForElementEnabled( final WebElement element )
	{
		return waitForElementEnabled(element, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	public WebElement waitForElementEnabled( final WebElement element, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isEnabled;
			isEnabled = base.element.isElementEnabled(element);
			return isEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		return element;
	}

	public List<WebElement> waitForAllElementsEnabled( final By locator )
	{
		return waitForAllElementsEnabled(locator, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	public List<WebElement> waitForAllElementsEnabled( final By locator, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isEnabled;
			isEnabled = base.element.isElementEnabled(locator);
			return isEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		final List<WebElement> elements = driver.findElements(locator);
		return elements;
	}

	public List<WebElement> waitForAllElementsEnabled( final By locator, WebElement container )
	{
		return waitForAllElementsEnabled(locator, container, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	public List<WebElement> waitForAllElementsEnabled( final By locator, WebElement container,
		final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isEnabled;
			isEnabled = base.element.isElementEnabled(locator, container);
			return isEnabled;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);

		final List<WebElement> elements = container.findElements(locator);
		return elements;
	}

	//// UNDER REVIEW ////

	/**
	 * waits for the element identified by the given helper function to be both displayed
	 * and enabled
	 *
	 * Performance warning?- maybe shouldn't be used except when stale element exceptions are
	 * occurring and can't be handled more efficiently
	 *
	 * @param elemFinder helper function that retrieves the element in question
	 *                   TLDR: just pass a lambda with the form "() -> {code that returns a WebElement}"
	 *
	 *                   This object could also store a method reference, e.g. a method reference
	 *                   to a class's member method like VertexWaitUtilities::waitForElementPresent
	 */
	public WebElement waitForElementEnabled( final Supplier<WebElement> elemFinder )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isDisplayedAndEnabled = false;
			WebElement element = elemFinder.get();
			if ( element != null )
			{
				boolean isDisplayed = element.isDisplayed();
				boolean isEnabled = element.isEnabled();
				isDisplayedAndEnabled = isDisplayed && isEnabled;
			}
			return isDisplayedAndEnabled;
		};
		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.DEFAULT_TIMEOUT);
		wait.until(condition);

		WebElement elem = elemFinder.get();
		return elem;
	}

	public void waitForTextInElement( final By locator, final String expectedText )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			WebElement element = waitForElementDisplayed(locator, 1);
			boolean isTextCorrect;
			String currentText = element.getText();
			isTextCorrect = expectedText.equals(currentText);
			return isTextCorrect;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.DEFAULT_TIMEOUT);
		wait.until(condition);
	}

	public void waitForTextInElement( final WebElement element, final String expectedText )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isTextCorrect;
			String currentText = element.getText();
			isTextCorrect = expectedText.equals(currentText);
			return isTextCorrect;
		};

		WebDriverWait wait = new WebDriverWait(driver, VertexAutomationObject.DEFAULT_TIMEOUT);
		wait.until(condition);
	}

	//TODO shouldn't this be in Window Utils. #teamDisagree

	/**
	 * waits for the new window or tab that's being opened (by some action taken
	 * right before this function is called) to come into existence
	 *
	 * @param existingWindowHandles the identifiers of the windows and tabs that existed before the
	 *                              action was taken that produced the expected tab/window (the one
	 *                              that's being switched to)
	 * @param timeoutInSeconds      how long to wait for one new window handle to load
	 */
	public void waitForNewWindowHandle( final Set<String> existingWindowHandles, final long timeoutInSeconds )
	{
		int initialWindowHandles = existingWindowHandles.size();
		int expectedNumberOfWindowHandles = initialWindowHandles + 1;
		//wait for the correct number of windows
		final WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindowHandles));
	}

	/**
	 * waits for an element to stop being displayed
	 * Doesn't break if the element becomes stale
	 *
	 * @param elem             the  WebElement to be NotDisplayed
	 * @param timeoutInSeconds how long to wait until expected condition is met
	 *
	 * @author osabha
	 */
	@Deprecated
	public void waitForElementNotDisplayedIgnoringStale( final WebElement elem, final int timeoutInSeconds )
	{
		final WebDriverWait shortWait = new WebDriverWait(driver, timeoutInSeconds);
		shortWait
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.invisibilityOf(elem));
	}

	/**
	 * waits for an element to stop being displayed
	 * Doesn't break if the element becomes stale
	 *
	 * @param elem             the  WebElement to be NotDisplayed
	 * @param timeoutInSeconds how long to wait until expected condition is met
	 *
	 * @author osabha
	 */
	public void waitForElementNotDisplayedOrStale( final WebElement elem, final int timeoutInSeconds )
	{
		try
		{
			waitForElementNotDisplayed(elem, timeoutInSeconds);
		}
		catch ( StaleElementReferenceException e )
		{
		}
	}

	/**
	 * Modifies {@link #waitForElementEnabled(WebElement, long)}
	 * so that it doesn't crash the test if the wait times out
	 */
	public void tryWaitForElementEnabled( final WebElement elem, final long timeoutInSeconds )
	{
		try
		{
			waitForElementEnabled(elem, timeoutInSeconds);
		}
		catch ( TimeoutException e )
		{
		}
	}

	/**
	 * Modifies {@link #tryWaitForElementEnabled(WebElement, long)}
	 * so that it first fetches the element before waiting for it to be enabled
	 *
	 * Note- this may wait as long as 2 times timeoutInSeconds because it waits for an element matching the locator
	 * to be present and then also waits for that element to become enabled
	 */
	public void tryWaitForElementEnabled( final By loc, final long timeoutInSeconds )
	{
		WebElement elem = waitForElementPresent(loc, timeoutInSeconds);
		tryWaitForElementEnabled(elem, timeoutInSeconds);
	}

	/**
	 * waits for an element to no longer display a given text string
	 *
	 * @param element          the element which should stop displaying a certain text string
	 * @param obsoleteText     the text which the element should stop displaying
	 * @param timeoutInSeconds how long to wait for the element to stop displaying that text
	 */
	public void waitForElementNotHaveObsoleteText( final WebElement element, final String obsoleteText,
		final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isTextChanged;
			String currentText = base.text.getElementText(element);
			isTextChanged = !obsoleteText.equals(currentText);
			return isTextChanged;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}

	/**
	 * Overloads {@link #waitForElementNotHaveObsoleteText(WebElement, String, long)}
	 * to use the default length of time to wait for the element to stop displaying the old text string
	 */
	public void waitForElementNotHaveObsoleteText( final WebElement element, final String obsoleteText )
	{
		waitForElementNotHaveObsoleteText(element, obsoleteText, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Overloads {@link #waitForElementNotDisplayed(By, WebElement, long)} to use the default timeout
	 */
	public void waitForElementNotDisplayed( final By locator, final WebElement container )
	{
		waitForElementNotDisplayed(locator, container, VertexAutomationObject.DEFAULT_TIMEOUT);
	}

	/**
	 * Wait up until a given timeout for an element to not be displayed on the page.
	 *
	 * @param locator          the element to wait to be displayed
	 * @param container        the container WebElement to search within
	 * @param timeoutInSeconds the amount of time to wait in seconds
	 *
	 * @author dgorecki ssalisbury
	 */
	public void waitForElementNotDisplayed( final By locator, final WebElement container, final long timeoutInSeconds )
	{
		final ExpectedCondition<Boolean> condition = driver ->
		{
			boolean isNotDisplayed;
			isNotDisplayed = !base.element.isElementDisplayed(locator, container);
			return isNotDisplayed;
		};

		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(condition);
	}
}


