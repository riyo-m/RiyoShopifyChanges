package com.vertex.quality.connectors.ariba.connector.pages.configuration;

import com.vertex.quality.connectors.ariba.connector.enums.AribaConnConnectionPropertiesTextField;
import com.vertex.quality.connectors.ariba.connector.pages.base.AribaConnMenuBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * representation of the page for configuring the connector's access to o series
 *
 * @author ssalisbury
 */
public class AribaConnConnectionPropertiesPage extends AribaConnMenuBasePage
{
	protected final By xmlLogCheckboxLoc = By.id("xmlLogging");
	protected final By oseriesPostCheckboxLoc = By.id("postFlag");

	protected final By errorMessageCont = By.id("errMsg");
	protected final By errorMessage = By.id("status");

	protected By tenantSelect = By.id("tenantSel");

	public AribaConnConnectionPropertiesPage( WebDriver driver )
	{
		super(driver, "Ariba Connector Configuration");
	}

	/**
	 * checks whether the given text field is displayed
	 *
	 * @param field which text field on the page to check
	 *
	 * @return whether the given text field is displayed to the user
	 *
	 * @author ssalisbury
	 */
	public boolean isFieldDisplayed( final AribaConnConnectionPropertiesTextField field )
	{
		final By fieldLoc = field.getLoc();
		boolean isDisplayed = element.isElementDisplayed(fieldLoc);
		return isDisplayed;
	}

	/**
	 * checks whether the given text field can be accessed- both whether it's displayed & whether it's enabled for
	 * interaction
	 *
	 * @param field which text field on the page to check
	 *
	 * @return whether the given text field can be accessed
	 *
	 * @author ssalisbury
	 */
	public boolean isFieldClickable( final AribaConnConnectionPropertiesTextField field )
	{
		final By fieldLoc = field.getLoc();
		boolean isClickable = element.isElementEnabled(fieldLoc);
		return isClickable;
	}

	/**
	 * clicks the given text field
	 *
	 * @param field which text field on the page to click
	 *
	 * @author ssalisbury
	 */
	public void clickTextField( final AribaConnConnectionPropertiesTextField field )
	{
		final By fieldLoc = field.getLoc();
		click.clickElement(fieldLoc);
	}

	/**
	 * Overloads {@link #setTextField(AribaConnConnectionPropertiesTextField, String, boolean)}
	 * to assume that it shouldn't clear the text field before entering text into it
	 */
	public void setTextField( final AribaConnConnectionPropertiesTextField field, final String testURL )
	{
		setTextField(field, testURL, true);
	}

	/**
	 * tries to enter the given string into the given field
	 *
	 * @param field            which text field on the page to enter text into
	 * @param testURL          the string being entered into the given field
	 * @param shouldClearFirst whether the text field should be cleared before text is entered into it
	 *
	 * @author ssalisbury
	 */
	public void setTextField( final AribaConnConnectionPropertiesTextField field, final String testURL,
		final boolean shouldClearFirst )
	{
		final By fieldLoc = field.getLoc();
		text.enterText(fieldLoc, testURL, shouldClearFirst);
	}

	/**
	 * collects the string stored in the given field
	 *
	 * @param field which text field on the page to get the text of
	 *
	 * @return the string stored/displayed in the given field
	 * on error, returns null
	 *
	 * @author ssalisbury
	 */
	public String retrieveTextFieldContents( final AribaConnConnectionPropertiesTextField field )
	{
		String fieldContents = null;

		final By fieldLoc = field.getLoc();
		WebElement fieldElem = wait.waitForElementEnabled(fieldLoc);
		fieldContents = attribute.getElementAttribute(fieldElem, "value");

		return fieldContents;
	}

	/**
	 * checks whether the xml logging checkbox is enabled
	 *
	 * @return whether the xml logging checkbox is enabled
	 *
	 * @author ssalisbury
	 */
	public boolean isXMLLogCheckboxAccessible( )
	{
		boolean isAccessible = false;
		isAccessible = element.isElementEnabled(xmlLogCheckboxLoc);
		return isAccessible;
	}

	/**
	 * determines whether the xml logging checkbox is checked
	 *
	 * @return whether the xml logging checkbox is checked
	 */
	public boolean isXMLLogCheckboxChecked( )
	{
		boolean isChecked = checkbox.isCheckboxChecked(xmlLogCheckboxLoc);
		return isChecked;
	}

	/**
	 * checks the checkbox or unchecks the checkbox
	 * does nothing if the checkbox is already in the
	 * desired 'checked' state
	 *
	 * @param shouldBeChecked whether the checkbox should be
	 *                        checked or whether it should be unchecked
	 */
	public void setXMLLogCheckbox( final boolean shouldBeChecked )
	{
		focus.focusOnElementJavascript(xmlLogCheckboxLoc);
		checkbox.setCheckbox(xmlLogCheckboxLoc, shouldBeChecked);
	}

	/**
	 * checks whether the o-series post checkbox is enabled
	 *
	 * @return whether the o-series post checkbox is enabled
	 *
	 * @author ssalisbury
	 */
	public boolean isOSeriesPostCheckboxAccessible( )
	{
		boolean isAccessible = false;
		isAccessible = element.isElementEnabled(oseriesPostCheckboxLoc);
		return isAccessible;
	}

	/**
	 * determines whether the o-series post checkbox is checked
	 *
	 * @return whether the o-series post checkbox is checked
	 */
	public boolean isOSeriesPostCheckboxChecked( )
	{
		boolean isChecked = checkbox.isCheckboxChecked(oseriesPostCheckboxLoc);
		return isChecked;
	}

	/**
	 * checks the checkbox or unchecks the checkbox
	 * does nothing if the checkbox is already in the
	 * desired 'checked' state
	 *
	 * @param shouldBeChecked whether the checkbox should be
	 *                        checked or whether it should be unchecked
	 */
	public void setOSeriesPostCheckbox( final boolean shouldBeChecked )
	{
		focus.focusOnElementJavascript(oseriesPostCheckboxLoc);
		checkbox.setCheckbox(oseriesPostCheckboxLoc, shouldBeChecked);
	}

	/**
	 * checks whether the update button is  enabled
	 * Note- this first clicks twice on the tenant selector to give the button a chance to refresh,
	 * notice any changes made to the fields on the page, and become enabled
	 *
	 * @return whether the update button is possibly clickable
	 *
	 * @author ssalisbury
	 */
	@Override
	public boolean isSubmitButtonEnabled( )
	{
		boolean isAccessible = false;

		tenantSelector.clickTenantSelector();
		tenantSelector.clickTenantSelector();
		//TODO would waitForPageLoad() be appropriate here?

		isAccessible = super.isSubmitButtonEnabled();
		return isAccessible;
	}

	/**
	 * clicks on the update button to submit the changes to the current tenant's connection
	 * configuration.
	 * Note- this first clicks twice on the tenant selector to give the button a chance to refresh,
	 * notice any changes made to the fields on the page, and become enabled
	 */
	public void submitChanges( )
	{
		wait.waitForElementDisplayed(submitButton);

		tenantSelector.clickTenantSelector();
		tenantSelector.clickTenantSelector();
		//TODO would waitForPageLoad() be appropriate here?

		super.submitChanges();
	}

	/**
	 * checks whether the given submission error message is displayed
	 *
	 * @param errorText which submission error message to check for
	 *
	 * @return whether the given submission error message is displayed to the user
	 *
	 * @author ssalisbury
	 */
	public boolean isErrorMessageDisplayed( final String errorText )
	{
		boolean isDisplayed = false;
		boolean isSomeErrorMessageDisplayed = element.isElementDisplayed(errorMessageCont);
		if ( isSomeErrorMessageDisplayed )
		{
			String messageContents = text.getElementText(errorMessage);
			if ( errorText.equals(messageContents) )
			{
				isDisplayed = true;
			}
		}
		return isDisplayed;
	}

}
