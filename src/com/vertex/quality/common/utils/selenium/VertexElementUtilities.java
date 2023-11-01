package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all helper methods for directly interacting with or determining state of WebElements
 *
 * @author hho, dgorecki
 */
public class VertexElementUtilities extends VertexSeleniumUtilities
{
	public VertexElementUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * Checks to see if an element can be found for the specified locator.  Uses findElement,
	 * so wil only check the first match if multiple are present.  Does not do any waiting,
	 * primarily because it is used by the wait utilities
	 *
	 * @param locator the locator for the element
	 *
	 * @return true if the element is present in the DOM tree, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementPresent( By locator )
	{
		boolean isPresent;

		try
		{
			driver.findElement(locator);
			isPresent = true;
		}
		catch ( Exception e )
		{
			isPresent = false;
		}
		return isPresent;
	}

	/**
	 * Checks to see if an element can be found for the specified locator., within a container element  Uses
	 * findElement,
	 * so wil only check the first match if multiple are present.  Does not do any waiting,
	 * primarily because it is used by the wait utilities
	 *
	 * @param locator   the locator for the element
	 * @param container the container WebElement to search within
	 *
	 * @return true if the element is present in the DOM tree, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementPresent( By locator, WebElement container )
	{
		boolean isPresent;

		try
		{
			container.findElement(locator);
			isPresent = true;
		}
		catch ( Exception e )
		{
			isPresent = false;
		}
		return isPresent;
	}

	/**
	 * Checks to see if the first element for the specified locator is not only present
	 * but also visible/displayed.
	 * Uses findElement, so wil only check the first match if multiple are present.  Does not do any waiting,
	 * primarily because it is used by the wait utilities
	 *
	 * @param locator the locator for the element
	 *
	 * @return true if the element is present in the DOM tree and is displayed/visible, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementDisplayed( By locator )
	{
		boolean isDisplayed;
		try
		{
			boolean isPresent = isElementPresent(locator);
			if ( isPresent )
			{
				WebElement element = driver.findElement(locator);
				isDisplayed = element.isDisplayed();
			}
			else
			{
				isDisplayed = false;
			}
		}
		catch ( Exception e )
		{
			isDisplayed = false;
		}
		return isDisplayed;
	}

	/**
	 * Checks to see if the first element for the specified locator in the specified container
	 * is not only present but also visible/displayed.
	 * Uses findElement, so will only check the first match if multiple are present.  Does not do any waiting,
	 * primarily because it is used by the wait utilities
	 *
	 * @param locator   the locator for the element
	 * @param container the container to limit the query to
	 *
	 * @return true if the element is present in the DOM tree and is displayed/visible, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementDisplayed( By locator, WebElement container )
	{
		boolean isDisplayed;
		boolean isPresent = isElementPresent(locator, container);
		if ( isPresent )
		{
			WebElement element = container.findElement(locator);
			isDisplayed = element.isDisplayed();
		}
		else
		{
			isDisplayed = false;
		}
		return isDisplayed;
	}

	/**
	 * Checks to see if any element for the specified locator is not only present, but also visible/displayed.
	 * Is slower than isElementDisplayed because it uses findElements() and a stream function.
	 * Does not do any waiting, primarily because it is used by the wait utilities
	 *
	 * @param locator the locator for the element
	 *
	 * @return true if the element is present in the DOM tree and is displayed/visible, false otherwise
	 *
	 * @author dgorecki ssalisbury
	 */
	public boolean isAnyElementDisplayed( By locator )
	{
		boolean isDisplayed;
		boolean isPresent = isElementPresent(locator);
		if ( isPresent )
		{
			List<WebElement> elements = getWebElements(locator);
			//checks whether any of the elements are displayed
			isDisplayed = elements
				.stream()
				.anyMatch(WebElement::isDisplayed);
		}
		else
		{
			isDisplayed = false;
		}
		return isDisplayed;
	}

	/**
	 * Checks to see if any element for the specified locator is not only present in the specified
	 * container, but also visible/displayed.
	 * Is slower than isElementDisplayed because it uses findElements() and a stream function.
	 * Does not do any waiting, primarily because it is used by the wait utilities
	 *
	 * @param locator   the locator for the element
	 * @param container the container to limit the query to
	 *
	 * @return true if the element is present in the container and is displayed/visible, false otherwise
	 *
	 * @author dgorecki ssalisbury
	 */
	public boolean isAnyElementDisplayed( By locator, WebElement container )
	{
		boolean isDisplayed;
		boolean isPresent = isElementPresent(locator, container);
		if ( isPresent )
		{
			List<WebElement> elements = getWebElements(locator, container);
			//checks whether any of the elements are displayed
			isDisplayed = elements
				.stream()
				.anyMatch(WebElement::isDisplayed);
		}
		else
		{
			isDisplayed = false;
		}
		return isDisplayed;
	}

	/**
	 * Checks to see if the specified element is visible/displayed.  Assumes it is present because the argument
	 * is an actual WebElement. Does not do any waiting, primarily because it is used by the wait utilities
	 *
	 * @param element the element to check
	 *
	 * @return true if the element is displayed/visible, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementDisplayed( WebElement element )
	{
		boolean isDisplayed;

		isDisplayed = element.isDisplayed();

		return isDisplayed;
	}

	/**
	 * Checks to see if an element for the specified locator is not only present,
	 * but also enabled and displayed.
	 * Uses findElement, so will only check the first match if multiple are present.
	 * Does not do any waiting, primarily because it is used by the wait utilities
	 *
	 * @param locator the locator for the element
	 *
	 * @return true if the element is enabled, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementEnabled( By locator )
	{
		boolean isEnabled;
		boolean isDisplayed = isElementDisplayed(locator);

		if ( isDisplayed )
		{
			WebElement element = driver.findElement(locator);
			isEnabled = element.isEnabled();
		}
		else
		{
			isEnabled = false;
		}
		return isEnabled;
	}

	/**
	 * Checks to see if an element for the specified locator is not only present
	 * in the specified container, but also enabled and displayed.
	 * Uses findElement, so will only check the first match if multiple are present.
	 * Does not do any waiting, primarily because it is used by the wait utilities
	 *
	 * @param locator   the locator for the element
	 * @param container the container to limit the query to
	 *
	 * @return true if the element is enabled, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementEnabled( By locator, WebElement container )
	{
		boolean isEnabled;
		boolean isDisplayed = isElementDisplayed(locator, container);

		if ( isDisplayed )
		{
			WebElement element = container.findElement(locator);
			isEnabled = element.isEnabled();
		}
		else
		{
			isEnabled = false;
		}
		return isEnabled;
	}

	/**
	 * Checks to see if the specified element is enabled and displayed.
	 * Assumes it is present because the argument is an actual WebElement.
	 * Does not do any waiting, primarily because it is used by the wait utilities
	 *
	 * @param element the element to check
	 *
	 * @return true if the element is enabled, false otherwise
	 *
	 * @author dgorecki
	 */
	public boolean isElementEnabled( WebElement element )
	{
		boolean isEnabled;
		isEnabled = element.isEnabled();

		return isEnabled;
	}

	/**
	 * Retrieve a WebElement for a locator. Does not wait, and does not do any error handling
	 *
	 * @param locator the locator for the element to retrieve
	 *
	 * @return the matching WebElement
	 *
	 * @author dgorecki
	 */
	public WebElement getWebElement( By locator )
	{
		WebElement element = driver.findElement(locator);
		return element;
	}

	/**
	 * Retrieve a WebElement within a container element for a locator. Does not wait, and does not do any error handling
	 *
	 * @param locator   the locator for the element to retrieve
	 * @param container the container to limit the query to
	 *
	 * @return the matching WebElement
	 *
	 * @author dgorecki
	 */
	public WebElement getWebElement( By locator, WebElement container )
	{
		WebElement element = container.findElement(locator);
		return element;
	}

	/**
	 * Retrieve a list of matching WebElements for a locator. Does not wait, and does not do any error handling
	 *
	 * @param locator the locator for the elements to retrieve
	 *
	 * @return a list of the matching WebElement
	 *
	 * @author dgorecki
	 */
	public List<WebElement> getWebElements( By locator )
	{
		List<WebElement> elements = driver.findElements(locator);
		return elements;
	}

	/**
	 * Retrieve a list of matching WebElements withing a container element for a locator. Does not wait, and does not do
	 * any error handling
	 *
	 * @param locator   the locator for the elements to retrieve
	 * @param container the container to limit the query to
	 *
	 * @return a list of the matching WebElement
	 *
	 * @author dgorecki
	 */
	public List<WebElement> getWebElements( By locator, WebElement container )
	{
		List<WebElement> elements = container.findElements(locator);
		return elements;
	}

	/**
	 * finds a button which displays the given name
	 *
	 * @param buttonText the name displayed by the desired button
	 *
	 * @return a button which displays the given name
	 *
	 * @author legacyAribaProgrammer ssalisbury
	 */
	public WebElement getButtonByText( final String buttonText )
	{
		List<WebElement> buttons = base.wait.waitForAllElementsEnabled(By.tagName("button"));

		WebElement buttonElement = null;

		for ( WebElement button : buttons )
		{
			String rawText = base.text.getHiddenText(button);
			if ( rawText != null )
			{
				String cleanText = rawText.trim();
				if ( buttonText.equals(cleanText) )
				{
					buttonElement = button;
					break;
				}
			}
		}
		return buttonElement;
	}

	//// UNDER REVIEW ////

	/**
	 * checks, as thoroughly as possible, whether an element is actually visible to the user or the automated test
	 *
	 * @param elem element which may be visible
	 *
	 * @return whether the element is visible
	 */
	protected boolean isElementVisible( final WebElement elem )
	{
		boolean isVisible = true;

		if ( elem == null )
		{
			isVisible = false;
		}
		else
		{
			if ( !elem.isDisplayed() )
			{
				isVisible = false;
			}
			else
			{
				final String style = elem.getAttribute("style");
				final String notDisplayedSubstring = ".*display:\\s*\\n*\\s*none;.*";

				if ( style != null && style.matches(notDisplayedSubstring) )
				{
					isVisible = false;
				}
				else
				{
					final String opaqueSubstring = ".*opacity:\\s*\\n*\\s*0;.*";
					if ( style != null && style.matches(opaqueSubstring) )
					{
						isVisible = false;
					}
					else
					{
						//TODO add any other ways of identifying that an element isn't actually visible
					}
				}
			}
		}

		return isVisible;
	}

	/**
	 * javascript that checks, as thoroughly as possible, whether an element is actually visible
	 * to the user or the automated test
	 *
	 * checks the element and each of its ancestor elements (i.e. parent, grandparent, etc.) to
	 * see if they're entirely in the viewport and if their styles indicate that they may actually
	 * be visible to the user
	 */
	protected final String isJsDisplayedScript = "function isDisplayedInViewport (elem) {\n" +
												 "    var dde = document.documentElement\n" + "	 \n" +
												 "    let isWithinViewport = true\n" +
												 "    while (elem.parentNode && elem.parentNode.getBoundingClientRect) {\n" +
												 "        const elemDimension = elem.getBoundingClientRect()\n" +
												 "        const elemComputedStyle = window.getComputedStyle(elem)\n" +
												 "        const viewportDimension = {\n" +
												 "            width: dde.clientWidth,\n" +
												 "            height: dde.clientHeight\n" + "        }\n" + "	 \n" +
												 "        isWithinViewport = isWithinViewport &&\n" +
												 "                           (elemComputedStyle.display !== 'none' &&\n" +
												 "                            elemComputedStyle.visibility === 'visible' &&\n" +
												 "                            parseFloat(elemComputedStyle.opacity, 10) > 0 &&\n" +
												 "                            elemDimension.bottom > 0 &&\n" +
												 "                            elemDimension.right > 0 &&\n" +
												 "                            elemDimension.top < viewportDimension.height &&\n" +
												 "                            elemDimension.left < viewportDimension.width)\n" +
												 "	 \n" + "        elem = elem.parentNode\n" + "    }\n" +
												 "	 \n" + "    return isWithinViewport\n" +
												 "} return isDisplayedInViewport(argument[0]);";

	/**
	 * uses javascript to check, as thoroughly as possible, whether an element is actually visible
	 * to the user or the automated test
	 *
	 * checks the element and each of its ancestor elements (i.e. parent, grandparent, etc.) to
	 * see if they're entirely in the viewport and if their styles indicate that they may actually
	 * be visible to the user
	 *
	 * @param elem element which may be visible
	 *
	 * @return whether the element is visible
	 */
	protected boolean isElementDisplayedJavascript( final WebElement elem )
	{
		boolean isVisible = true;

		if ( elem == null )
		{
			isVisible = false;
		}
		else
		{
			Boolean jsIsVisible = (Boolean) base.executeJs(isJsDisplayedScript, elem);
			isVisible = jsIsVisible.booleanValue();
		}

		return isVisible;
	}

	/**
	 * finds the displayed element with the desired displayed text out of a list of elements
	 *
	 * @param elements    a list of elements, one of which might be the desired element
	 * @param displayText the text which the desired element displays
	 *
	 * @return the displayed element with the desired displayed text
	 */
	public WebElement selectElementByText( final List<WebElement> elements, final String displayText )
	{
		WebElement targetElement = null;
		for ( WebElement potentialElement : elements )
		{
			if ( isElementDisplayed(potentialElement) )
			{
				String rawText = base.text.getElementText(potentialElement);
				if ( rawText != null )
				{
					String cleanText = base.text.cleanseWhitespace(rawText);
					if ( displayText.equals(cleanText) )
					{
						targetElement = potentialElement;
						break;
					}
				}
			}
		}
		if ( targetElement == null )
		{
			final String missingElementMessage = String.format("can't find element displaying %s", displayText);
			VertexLogger.log(missingElementMessage, VertexLogLevel.ERROR);
		}
		return targetElement;
	}

	/**
	 * finds the displayed element with the desired displayed text out of a list of elements
	 *
	 * @param elements      a list of elements, one of which might be the desired element
	 * @param containedText the text which the desired element displays
	 *
	 * @return the displayed element with the desired displayed text
	 *
	 * @author osabha
	 */
	public WebElement selectElementByContainedText( final List<WebElement> elements, final String containedText )
	{
		WebElement targetElement = null;
		for ( WebElement potentialElement : elements )
		{
			if ( isElementDisplayed(potentialElement) )
			{
				String rawText = base.text.getElementText(potentialElement);
				if ( rawText != null )
				{
					String cleanText = base.text.cleanseWhitespace(rawText);
					if ( cleanText.contains(containedText) )
					{
						targetElement = potentialElement;
						break;
					}
				}
			}
		}
		if ( targetElement == null )
		{
			final String missingElementMessage = String.format("can't find element displaying %s", containedText);
			VertexLogger.log(missingElementMessage, VertexLogLevel.ERROR);
		}
		return targetElement;
	}






	/**
	 * Overloads {@link #selectElementByText(List, String)} to fetch the elements based on a locator first
	 *
	 * @author dbondi ssalisbury
	 */
	public WebElement selectElementByText( final By locator, final String text )
	{
		final List<WebElement> elements = base.wait.waitForAnyElementsDisplayed(locator);
		WebElement targetElement = selectElementByText(elements, text);
		return targetElement;
	}

	/**
	 * Overloads {@link #selectElementByAttribute(List, String, String, boolean)}
	 * to assume that the desired element doesn't need to be displayed
	 */
	public WebElement selectElementByAttribute( final List<WebElement> elements, final String value,
		final String attribute )
	{
		WebElement targetElement = selectElementByAttribute(elements, value, attribute, false);
		return targetElement;
	}

	/**
	 * finds the element with the desired value for the given attribute out of a list of elements
	 *
	 * @param elements        a list of elements, one of which might be the desired element
	 * @param value           the string which the desired element has for the given attribute
	 * @param attribute       which of the elements' attributes should be examined
	 * @param mustBeDisplayed whether the desired element must be displayed
	 *
	 * @return the element with the desired value for the given attribute
	 */
	public WebElement selectElementByAttribute( final List<WebElement> elements, final String value,
		final String attribute, final boolean mustBeDisplayed )
	{
		WebElement targetElement = null;
		for ( WebElement potentialElement : elements )
		{
			//rules out the current element only if the desired element must be displayed and the current element isn't
			final boolean doesntNeedToBeDisplayed = !mustBeDisplayed;
			if ( doesntNeedToBeDisplayed || isElementDisplayed(potentialElement) )
			{
				String rawValue = base.attribute.getElementAttribute(potentialElement, attribute);
				if ( value.equals(rawValue) )
				{
					targetElement = potentialElement;
					break;
				}
			}
		}
		if ( targetElement == null )
		{
			final String missingElementMessage = String.format(
				"can't find element with attribute %s having the value %s", attribute, value);
			VertexLogger.log(missingElementMessage, VertexLogLevel.ERROR);
		}
		return targetElement;
	}

	/**
	 * Overloads {@link #selectElementByNestedLabel(List, By, String, boolean)} to assume that each element in the given
	 * list
	 * contains at least one displayed label
	 */
	public WebElement selectElementByNestedLabel( final List<WebElement> elements, final By nestedLabel,
		final String labelText )
	{
		WebElement targetElement = selectElementByNestedLabel(elements, nestedLabel, labelText, true);
		return targetElement;
	}

	/**
	 * finds a label element inside each of the given elements & picks the given element which contains a label with the
	 * desired text
	 *
	 * @param elements                   a list of elements, one of which might be the desired element
	 * @param nestedLabel                the description of the nested label element
	 * @param labelText                  the text which the desired element's nested label displays
	 * @param areLabelsInsideEachElement whether one or more nested labels are displayed inside each of the elements
	 *                                   in the given list of elements
	 *
	 * @return the element with a nested label that displays the desired text
	 */
	public WebElement selectElementByNestedLabel( final List<WebElement> elements, final By nestedLabel,
		final String labelText, final boolean areLabelsInsideEachElement )
	{
		WebElement targetElement = null;

		//if this can't rely on finding at least one label inside each list-element, it can't wait inside each
		// list-element for a label to be displayed there. In that case, it at least waits until some labels have
		// loaded somewhere on the page
		if ( !areLabelsInsideEachElement )
		{
			base.wait.waitForAnyElementsDisplayed(nestedLabel);
		}

		labelSearch:
		for ( WebElement potentialElement : elements )
		{
			if ( isElementDisplayed(potentialElement) )
			{
				//finds the nested labels inside the element
				List<WebElement> labels = new ArrayList<>();
				if ( areLabelsInsideEachElement )
				{
					labels = base.wait.waitForAnyElementsDisplayed(nestedLabel, potentialElement);
				}
				else
				{
					labels = getWebElements(nestedLabel, potentialElement);
				}

				//checks if any of them display the desired text
				for ( WebElement label : labels )
				{
					if ( isElementDisplayed(label) )
					{
						String rawText = base.text.getElementText(label);
						if ( rawText != null )
						{
							String cleanText = base.text.cleanseWhitespace(rawText);
							if ( labelText.equals(cleanText) )
							{
								targetElement = potentialElement;
								break labelSearch;
							}
						}
					}
				}
			}
		}
		if ( targetElement == null )
		{
			final String missingElementMessage = String.format("can't find element with label displaying %s",
				labelText);
			VertexLogger.log(missingElementMessage, VertexLogLevel.ERROR);
		}
		return targetElement;
	}

	/**
	 * finds the element with the desired displayed text out of a list of elements
	 * and returns its position in that list
	 *
	 * @param elements      a list of elements, one of which might be the desired element
	 * @param displayedText the text which the desired element displays
	 *
	 * @return the position of the element with the desired displayed text among the other elements
	 * returns -1 on error
	 */
	public int findElementPositionByText( final List<WebElement> elements, final String displayedText )
	{
		int elementPosition = -1;

		for ( int i = 0 ; i < elements.size() ; i++ )
		{
			WebElement elem = elements.get(i);
			final String rawText = base.text.getElementText(elem);
			if ( rawText != null )
			{
				final String cleanText = base.text.cleanseWhitespace(rawText);
				if ( displayedText.equals(cleanText) )
				{
					elementPosition = i;
					break;
				}
			}
		}
		if ( elementPosition == -1 )
		{
			final String missingElementMessage = String.format("can't find position of element with label %s",
				displayedText);
			VertexLogger.log(missingElementMessage, VertexLogLevel.ERROR);
		}
		return elementPosition;
	}

	/**
	 * finds the element with the desired value for a given attribute out of a list of elements
	 * and returns its position in that list
	 *
	 * @param elements      a list of elements, one of which might be the desired element
	 * @param attributeType the attribute by which the desired element can be distinguished from the rest
	 * @param value         the value of that attribute which the desired element has
	 *
	 * @return the position of the element with the desired value for the given attribute among the other elements
	 * returns -1 on error
	 */
	public int findElementPositionByAttribute( final List<WebElement> elements, final String attributeType,
		final String value )
	{
		int elementPosition = -1;

		for ( int i = 0 ; i < elements.size() ; i++ )
		{
			WebElement elem = elements.get(i);
			final String rawValue = base.attribute.getElementAttribute(elem, attributeType);
			if ( value.equals(rawValue) )
			{
				elementPosition = i;
				break;
			}
		}
		if ( elementPosition == -1 )
		{
			final String missingElementMessage = String.format(
				"can't find position of element with value %s for attribute %s", value, attributeType);
			VertexLogger.log(missingElementMessage, VertexLogLevel.ERROR);
		}
		return elementPosition;
	}
}
