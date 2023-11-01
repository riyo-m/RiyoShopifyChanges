package com.vertex.quality.common.utils.selenium;

import com.vertex.quality.common.enums.SpecialCharacter;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexAutomationObject;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.selenium.base.VertexSeleniumUtilities;
import org.openqa.selenium.*;

/**
 * Common functions for anything related to text.
 *
 * This should be declared as an instance variable in VertexAutomationObject
 *
 * @author hho
 */
public class VertexTextUtilities extends VertexSeleniumUtilities
{
	public VertexTextUtilities( final VertexAutomationObject pageObject, final WebDriver driver )
	{
		super(pageObject, driver);
	}

	//Please revisit these and make sure they make sense - the "special case
	//stuff has me wary.  Also make sure the method names make sense, and abbreviate the javadocs
	//Dave G.

	/**
	 * tries to clear the text field and enter the given string into it
	 *
	 * @param loc  the text element's locator
	 * @param text the string to input into the text element
	 *
	 * @author ssalisbury
	 */
	public void enterText( final By loc, final CharSequence text )
	{
		WebElement textElement = base.wait.waitForElementEnabled(loc);
		enterText(textElement, text);
	}

	/**
	 * tries to enter the given string into it, possibly
	 * after clearing the text field
	 *
	 * @param loc              the text element's locator
	 * @param text             the string to input into the text element
	 * @param shouldClearFirst whether the text field should be cleared before the text is entered
	 *
	 * @author ssalisbury
	 */
	public void enterText( final By loc, final CharSequence text, final boolean shouldClearFirst )
	{
		WebElement textElement = base.wait.waitForElementEnabled(loc);
		enterText(textElement, text, shouldClearFirst);
	}

	/**
	 * tries to clear the text element and then input the given string
	 *
	 * @param textField the input element to enter text into
	 * @param text      the string being entered into the text field
	 *
	 * @author ssalisbury
	 */
	public void enterText( final WebElement textField, final CharSequence text )
	{
		enterText(textField, text, true);
	}

	/**
	 * tries to enter the given string into it, possibly
	 * after clearing the text field
	 *
	 * @param textField        the input element to enter text into
	 * @param text             the string being entered into the text field
	 * @param shouldClearFirst whether the text field should be cleared before the text is entered
	 *
	 * @author ssalisbury
	 */
	public void enterText( final WebElement textField, final CharSequence text, final boolean shouldClearFirst )
	{
		if ( text != null )
		{
			if ( shouldClearFirst )
			{
				textField.clear();
				base.waitForPageLoad();
			}

			textField.sendKeys(text);
		}
	}

	/**
	 * Overloads {@link #retrieveTextFieldContents(WebElement)} to fetch the element based on a locator first
	 *
	 * @author dbondi ssalisbury
	 */
	public  String retrieveTextFieldContents( final By loc )
	{
		String textFieldContents = null;

		WebElement textField = base.wait.waitForElementPresent(loc);

		textFieldContents = retrieveTextFieldContents(textField);

		return textFieldContents;
	}

	/**
	 * collects the string stored in the text field Specifically, the string that
	 * shows up in the text box
	 *
	 * @param textField text field to retrieve text from
	 *
	 * @return the string stored/displayed in the text field
	 * Note- returns null if the field cannot be accessed or if its text content can't be extracted
	 * (eg if it doesn't have any text content)
	 *
	 * @author ssalisbury
	 */
	public String retrieveTextFieldContents( final WebElement textField )
	{
		String textFieldContents = null;

		try
		{
			base.wait.waitForElementDisplayed(textField);
		}
		catch ( final TimeoutException e )
		{
			final String missingTextFieldMessage = String.format("%s Field not displayed or not found on %s",
				textField.toString());
			VertexLogger.log(missingTextFieldMessage);
			e.printStackTrace();
		}

		if ( textField != null )
		{
			textFieldContents = base.attribute.getElementAttribute(textField, "value");
		}

		return textFieldContents;
	}

	//probably belongs in a utility class; definitely needs javadoc - Dave G.
	public String getImmediateText( final String jQueryLocator )
	{
		final String script = String.format(
			"return $(\"%s\").contents().filter(function(){ return this.nodeType == 3; })[0].nodeValue;",
			jQueryLocator);

		final String immediateText = (String) base.executeJs(script);

		return immediateText;
	}

	/**
	 * Overloads {@link #pressTab(WebElement)}
	 * to use a By locator to find the element
	 */
	public void pressTab( final By loc )
	{
		final WebElement webElement = base.element.getWebElement(loc);
		pressTab(webElement);
	}

	/**
	 * shifts focus from an element after entering text
	 *
	 * @param element the element which focus should be shifted away from
	 */
	public void pressTab( final WebElement element )
	{
		if ( element != null )
		{
			element.sendKeys(Keys.TAB);
		}
		else
		{
			VertexLogger.log("Element is null", VertexLogLevel.ERROR);
		}
	}

	/**
	 * Overloads {@link #selectAllAndInputText(WebElement, CharSequence)}
	 * to use a By locator to find the element
	 */
	public void selectAllAndInputText( final By by, final CharSequence text )
	{
		final WebElement inputElement = base.element.getWebElement(by);

		selectAllAndInputText(inputElement, text);
	}

	/**
	 * selectAllAndInputText
	 *
	 * @param field the element which will have text entered into it
	 * @param text  of (String)
	 *              !param elementName
	 *              of (String) Currently does not throw an exception if the timeout
	 *              expires, an additional check may be needed in the test to verify
	 *              expected conditions
	 */
	public void selectAllAndInputText( final WebElement field, final CharSequence text )
	{
		if ( field != null )
		{
			field.sendKeys(Keys.CONTROL + "a");
			field.sendKeys(text);
		}
		else
		{
			VertexLogger.log("Element is null", VertexLogLevel.ERROR);
		}
	}

	/**
	 * Clears the contents of a text field
	 *
	 * @param locator locator of the field to clear
	 *
	 * @author dgorecki
	 */
	public void clearText( final By locator )
	{
		final WebElement inputElement = base.element.getWebElement(locator);
		clearText(inputElement);
	}

	/**
	 * Clears the contents of a text field
	 *
	 * @param textField the web element text field to clear
	 *
	 * @author dgorecki
	 */
	public void clearText( final WebElement textField )
	{
		textField.clear();
	}

	/**
	 * verifyText
	 *
	 * @param locator     of (By)
	 * @param text        of (String)
	 * @param elementName of (String) Here both Actual Text And Expected Text are Trimmed,
	 *                    so handle/validate the text accordingly from calling methods
	 */
	public void verifyText( final By locator, final String text, final String elementName )
	{
		try
		{
			final String ActualText = driver
				.findElement(locator)
				.getText()
				.trim();
			if ( ActualText.contains(text.trim()) )
			{
				VertexLogger.log(
					String.format("Both Actual Text: \"%s\" and Expected Text: \"%s\"  are same", text, elementName));
			}
			else
			{
				VertexLogger.log(String.format("Both Actual Text: \"%s\" and Expected Text: \"%s\" are not same", text,
					elementName));
			}
		}
		catch ( final Exception e )
		{
			e.printStackTrace();
		}
	}

	public String getElementText( final By by )
	{
		final WebElement element = base.element.getWebElement(by);

		return getElementText(element);
	}

	public String getElementText( final WebElement element )
	{
		final String text = element.getText();
		return text;
	}

	public String removeNewLineCharacters( final String text )
	{
		String clean_string = text
			.replace('\n', ' ')
			.replace("\r", "");
		return clean_string;
	}

	public String getHiddenText( final WebElement webElement )
	{
		String innertext = webElement.getAttribute("innerText");
		return innertext;
	}

	/**
	 * standardizes the whitespace in a string: no leading or trailing whitespace;
	 * no 'no-break spaces', just normal spaces;
	 *
	 * @param text the text which needs to be purged of unpredictable or weird whitespace characters
	 *
	 * @return the given text but with the whitespace parts simplified
	 *
	 * @author ssalisbury
	 */
	public String cleanseWhitespace( String text )
	{
		String cleanText = text.trim();
		cleanText = cleanText.replace(SpecialCharacter.NON_BREAKING_SPACE.toString(), " ");

		return cleanText;
	}

	public void clickElementAndEnterText( WebElement element, String text )
	{
		base.click.clickElement(element);
		enterText(element, text);
	}

	public void clickElementAndEnterText( By locator, String text )
	{
		WebElement element = base.element.getWebElement(locator);
		clickElementAndEnterText(element, text);
	}

	/**
	 * Utility method to parse through a string and replace all whitespace with a
	 * single space
	 * Helpful when dealing with strings pulled by Selenium from HTML, where a mix
	 * of different line breaks and other whitespace characters may be present
	 *
	 * @param stringToNormalize the string to normalize
	 *
	 * @return a copy of the string where all whitespace characters are replaced
	 * with spaces
	 */
	public String normalizeWhitespace( String stringToNormalize )
	{
		String normalizedString = "";
		char[] chars = stringToNormalize.toCharArray();

		for ( int i = 0 ; i < chars.length ; i++ )
		{
			char temp = chars[i];
			if ( Character.isWhitespace(
				temp) ) //TODO this explicitly doesn't cover non-breaking spaces. we should add checks for those characters
			{
				normalizedString = String.format("%s ", normalizedString);
			}
			else
			{
				normalizedString = normalizedString + temp;
			}
		}
		return normalizedString;
	}

	public void enterTextByIndividualCharacters( By locator, final String textToEnter )
	{
		for ( String character : textToEnter.split("") )
		{
			driver
				.findElement(locator)
				.sendKeys(character);
		}
	}

	//// UNDER REVIEW ////

	/**
	 * Overloads {@link #setTextFieldCarefully(WebElement, CharSequence, boolean)}
	 * to use a By locator to find the element
	 */
	public void setTextFieldCarefully( final By locator, final CharSequence inputText, final boolean isClearable )
	{
		WebElement field = base.wait.waitForElementEnabled(locator);
		setTextFieldCarefully(field, inputText, isClearable);
	}

	/**
	 * Overloads {@link #setTextFieldCarefully(WebElement, CharSequence)}
	 * to use a By locator to find the element
	 */
	public void setTextFieldCarefully( final By locator, final CharSequence inputText )
	{
		WebElement field = base.wait.waitForElementEnabled(locator);
		setTextFieldCarefully(field, inputText);
	}

	/**
	 * replaces the text field's old contents with inputText
	 *
	 * @param field       the text field whose value is being set
	 * @param inputText   the text being written into that text field
	 * @param isClearable whether the text field's old value can be wiped with selenium call field.clear() or
	 *                    the field's old value must be selected with Ctrl-A so that inputText can replace it
	 */
	public void setTextFieldCarefully( final WebElement field, final CharSequence inputText, final boolean isClearable )
	{
		base.wait.waitForElementEnabled(field);

		if ( isClearable )
		{
			enterText(field, inputText);
		}
		else
		{
			selectAllAndInputText(field, inputText);
		}

		pressTab(field);
		base.waitForPageLoad();
	}

	/**
	 * Overloads {@link #setTextFieldCarefully(WebElement, CharSequence, boolean)}
	 * to assume that the field's old contents can be erased with the selenium call WebElement.clear()
	 */
	public void setTextFieldCarefully( final WebElement field, final CharSequence inputText )
	{
		setTextFieldCarefully(field, inputText, true);
	}

	/**
	 * Presses enter on the locator
	 *
	 * @param locator the locator
	 */
	public void pressEnter( final By locator )
	{
		WebElement element = base.wait.waitForElementDisplayed(locator);
		pressEnter(element);
	}

	/**
	 * Overloads {@link #pressEnter(By)} to accept a web element instead
	 *
	 * @param element the web element
	 */
	public void pressEnter( final WebElement element )
	{
		element.sendKeys(Keys.RETURN);
	}

	/**
	 * enters characters one at a time
	 *
	 * @param element     the web element to send keys to
	 * @param textToEnter the text to enter into the element
	 */
	public void enterTextByIndividualCharacters( final WebElement element, final String textToEnter )
	{
		for ( String character : textToEnter.split("") )
		{
			element.sendKeys(character);
		}
	}

	/**
	 * This enters text to an element on the webpage, but if StaleElementReferenceException or ElementNotInteractableException occurs
	 * then it will retry entering that text again.
	 * @param locator the element which should allow for text input
	 * @param textToEnter the text that should be entered
	 */
	public void enterTextIgnoreExceptionsAndRetry(final By locator, final String textToEnter)
	{
		try
		{
			driver.findElement(locator).click();
			driver.findElement(locator).sendKeys(textToEnter);
		}
		catch(StaleElementReferenceException | ElementNotInteractableException e)
		{
			driver.findElement(locator).click();
			driver.findElement(locator).sendKeys(textToEnter);
		}
	}
}
