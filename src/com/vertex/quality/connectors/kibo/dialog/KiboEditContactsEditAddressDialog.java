package com.vertex.quality.connectors.kibo.dialog;

import com.vertex.quality.common.dialogs.VertexDialog;
import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the Edit Address pop up dialog that sprouts out from the edit contact
 * dialog, by clicking edit shipping or billing address
 * contains all the methods to interact with the dialog fields
 *
 * @author osabha
 */
public class KiboEditContactsEditAddressDialog extends VertexDialog
{
	public KiboEditContactsEditAddressDialog( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By okButtonLoc = By.className("x-btn-action-primary-medium");
	protected By saveButtonContainerLoc = By.className("taco-address-editor");
	protected By saveButtonLoc = By.className("x-btn-action-primary-toolbar-medium");
	protected By useThisButtonLoc = By.className("x-btn-action-primary-medium");
	protected By validAddressFieldLoc = By.className("x-form-display-field");
	protected By errorMessageLoc = By.className("x-form-display-field");
	protected By validateButtonLoc = By.className("x-btn-action-toolbar-medium");
	protected By zipFieldLoc = By.cssSelector("input[name='postalOrZipCode']");
	protected By cityFieldLoc = By.cssSelector("input[name='cityOrTown']");

	/**
	 * to enter the city into the city field ( clears field first)
	 *
	 * @param city String of the city name
	 */
	public void enterCity( String city )
	{
		WebElement cityField = wait.waitForElementPresent(cityFieldLoc);
		text.enterText(cityField, city);
	}

	/**
	 * to enter the zip code into the zip code field ( clears field first)
	 *
	 * @param zip String of the zip code
	 */
	public void enterZip( String zip )
	{
		WebElement zipField = wait.waitForElementPresent(zipFieldLoc);
		text.enterText(zipField, zip);
	}

	/**
	 * getter method to locate the validate button
	 *
	 * @return validate button WebElement
	 */
	protected WebElement getValidateButton( )
	{
		WebElement validateButton = null;
		String expectedText = "Validate";
		List<WebElement> validateButtonContainers = wait.waitForAllElementsPresent(validateButtonLoc);
		validateButton = element.selectElementByText(validateButtonContainers, expectedText);
		return validateButton;
	}

	/**
	 * uses the getter method to locate the validate button and then clicks on it
	 */
	public void clickValidateButton( )
	{
		WebElement validateButton = getValidateButton();
		validateButton.click();
	}

	/**
	 * to verify that an error message has been displayed, unable to validate address
	 *
	 * @return true if error message displayed, or else false.
	 */
	public String getErrorMessage( )
	{
		WebElement errorMessageField = wait.waitForElementPresent(errorMessageLoc);
		String text = errorMessageField.getText();

		return text;
	}

	/**
	 * to close out the error message by clicking on Ok button
	 */
	public void clickOkToCloseErrorMessage( )
	{
		String expectedText = "OK";
		List<WebElement> okButtonContainers = wait.waitForAllElementsPresent(okButtonLoc);
		WebElement okButtonContainer = element.selectElementByText(okButtonContainers, expectedText);
		if ( okButtonContainer != null )
		{
			click.clickElement(okButtonContainer);
		}
	}

	/**
	 * to verify if the address have been validated
	 *
	 * @return true if validated
	 */
	public String getValidatedAddress( )
	{
		WebElement validAddressField = wait.waitForElementDisplayed(validAddressFieldLoc);

		String address = validAddressField.getText();
		address.trim();

		return address;
	}

	/**
	 * clicks on the Use this button to close the validated address message
	 */
	public void clickUseThis( )
	{
		String expectedText = "Use This";
		List<WebElement> useThisButtons = wait.waitForAllElementsPresent(useThisButtonLoc);
		WebElement button = element.selectElementByText(useThisButtons, expectedText);
		if ( button != null )
		{
			button.click();
		}
	}

	/**
	 * locates the save button on the edit address dialog and then clicks on it
	 */
	public void clickSaveButton( )
	{
		WebElement saveButtonContainer = wait.waitForElementPresent(saveButtonContainerLoc);
		WebElement saveButton = wait.waitForElementPresent(saveButtonLoc, saveButtonContainer);

		saveButton.click();
	}
}
