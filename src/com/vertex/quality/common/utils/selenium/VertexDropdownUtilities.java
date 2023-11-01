package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Common functions for interacting with dropDowns. This should be declared as an instance
 * variable in VertexAutomationObject
 *
 * @author hho, osabha
 */
public class VertexDropdownUtilities extends VertexSeleniumUtilities
{
	public VertexDropdownUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	/**
	 * Selects the option at the given index in the dropdown specified by the given
	 * locator
	 *
	 * @param locator the dropdown element's locator
	 * @param index   the index of the option that's being selected from the dropdown. Index begins at 0
	 *
	 * @author ssalisbury
	 */
	public void selectDropdownByIndex( final By locator, final int index )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		selectDropdownByIndex(selectorElem, index);
	}

	/**
	 * Selects the option at the given index in the dropdown specified by the given
	 * locator
	 *
	 * @param element the dropdown element's locator as a web element
	 * @param index   the index of the option that's being selected from the dropdown. Index begins at 0
	 *
	 * @author ssalisbury, hho
	 */
	public void selectDropdownByIndex( final WebElement element, final int index )
	{
		final Select dropdown = new Select(element);
		dropdown.selectByIndex(index);
	}

	/**
	 * Selects the option with the given value in the dropdown specified by the
	 * given locator
	 *
	 * @param locator the dropdown element's locator
	 * @param value   the 'value' name of the option that's being selected from the
	 *                dropdown. "value" is an HTML attribute.
	 *
	 * @author ssalisbury
	 */
	public void selectDropdownByValue( final By locator, final String value )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		selectDropdownByValue(selectorElem, value);
	}

	/**
	 * Selects the option with the given value in the dropdown specified by the
	 * given locator
	 *
	 * @param element the dropdown element's locator as a web element
	 * @param value   the 'value' name of the option that's being selected from the
	 *                dropdown. "value" is an HTML attribute.
	 *
	 * @author ssalisbury, hho
	 */
	public void selectDropdownByValue( final WebElement element, final String value )
	{
		final Select dropdown = new Select(element);
		dropdown.selectByValue(value);
	}

	/**
	 * Selects the option with the given displayed name in the dropdown specified by
	 * the given locator
	 *
	 * @param locator     the dropdown element's locator
	 * @param displayName the displayed name of the option that's being selected from the
	 *                    dropdown
	 *
	 * @author ssalisbury
	 */
	public void selectDropdownByDisplayName( final By locator, final String displayName )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		selectDropdownByDisplayName(selectorElem, displayName);
	}

	/**
	 * Selects the option with the given displayed name in the dropdown specified by
	 * the given locator
	 *
	 * @param element     the dropdown element's locator as a web element
	 * @param displayName the displayed name of the option that's being selected from the
	 *                    dropdown
	 *
	 * @author ssalisbury, hho
	 */
	public void selectDropdownByDisplayName( final WebElement element, final String displayName )
	{
		final Select dropdown = new Select(element);
		dropdown.selectByVisibleText(displayName);
	}

	/**
	 * Collects and returns the options in the dropdown specified by the given
	 * locator
	 *
	 * @param locator the dropdown element's locator
	 *
	 * @return the options in the dropdown as a list
	 *
	 * @author ssalisbury
	 */
	public List<WebElement> getDropdownOptions( final By locator )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		return getDropdownOptions(selectorElem);
	}

	/**
	 * Collects and returns the display options inside the given dropdown element
	 *
	 * @param dropdown the dropdown element
	 *
	 * @return the options in the dropdown as a list
	 *
	 * @author dgorecki ssalisbury
	 */
	public List<String> getDropdownDisplayOptions( final WebElement dropdown )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(dropdown);
		List<WebElement> dropdownOptions = getDropdownOptions(selectorElem);

		List<String> displayOptions = new ArrayList<>();

		for ( WebElement option : dropdownOptions )
		{
			if ( base.element.isElementDisplayed(option) )
			{
				String optionText = base.text.getElementText(option);
				displayOptions.add(optionText);
			}
		}
		return displayOptions;
	}

	/**
	 * Overloads {@link #getDropdownDisplayOptions(WebElement)} to find the dropdown element with a locator first
	 */
	public List<String> getDropdownDisplayOptions( final By locator )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		List<String> displayOptions = getDropdownDisplayOptions(selectorElem);
		return displayOptions;
	}

	/**
	 * Collects and returns the value attribute strings of the options in the given dropdown element
	 *
	 * @param dropdown the dropdown element
	 *
	 * @return a list of the value attribute strings of the options in the dropdown
	 * if an option element doesn't have a value attribute, then there will be a null entry in the list for that option
	 *
	 * @author dgorecki ssalisbury
	 */
	public List<String> getDropdownValueOptions( final WebElement dropdown )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(dropdown);
		List<WebElement> dropdownOptions = getDropdownOptions(selectorElem);

		List<String> optionValues = new ArrayList<>();

		for ( WebElement option : dropdownOptions )
		{
			String optionValue = base.attribute.getElementAttribute(option, "value");
			optionValues.add(optionValue);
		}
		return optionValues;
	}

	/**
	 * Overloads {@link #getDropdownValueOptions(WebElement)} to find the dropdown element with a locator first
	 */
	public List<String> getDropdownValueOptions( final By locator )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		List<String> optionValues = getDropdownValueOptions(selectorElem);
		return optionValues;
	}

	/**
	 * Collects and returns the options in the dropdown specified by the given
	 * locator
	 *
	 * @param element the dropdown element's locator as a web element
	 *
	 * @return the options in the dropdown as a list
	 *
	 * @author ssalisbury, hho
	 */
	public List<WebElement> getDropdownOptions( final WebElement element )
	{
		final Select dropdown = new Select(element);
		return dropdown.getOptions();
	}

	/**
	 * collects and returns the default selected option in the dropdown specified
	 * by the given locator
	 *
	 * @param locator the dropdown element's locator
	 *
	 * @return the default selected option for the dropdown
	 *
	 * @author ssalisbury, hho
	 */
	public WebElement getDropdownSelectedOption( final By locator )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		return getDropdownSelectedOption(selectorElem);
	}

	/**
	 * collects and returns the default selected option in the dropdown specified
	 * by the given locator
	 *
	 * @param element the dropdown element's locator as a web element
	 *
	 * @return the default selected option for the dropdown
	 *
	 * @author ssalisbury, hho
	 */
	public WebElement getDropdownSelectedOption( final WebElement element )
	{
		final Select dropdown = new Select(element);
		return dropdown.getFirstSelectedOption();
	}

	/**
	 * deselects all of the options in the dropdown specified by the given locator
	 *
	 * @param locator the dropdown element's locator
	 *
	 * @author ssalisbury
	 */
	public void clearDropdownSelection( final By locator )
	{
		final WebElement selectorElem = base.wait.waitForElementDisplayed(locator);
		clearDropdownSelection(selectorElem);
	}

	/**
	 * deselects all of the options in the dropdown specified by the given locator
	 *
	 * @param element the dropdown element's locator as a web element
	 *
	 * @author ssalisbury, hho
	 */
	public void clearDropdownSelection( final WebElement element )
	{
		final Select dropdown = new Select(element);
		dropdown.deselectAll();
	}
}
