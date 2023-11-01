package com.vertex.quality.connectors.commercetools.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * a generic representation of a CommerceTools Bill From Page.
 *
 * @author vivek-kumar
 */

public class CommerceToolsBillFromPage extends CommerceToolsBasePage
{
	public CommerceToolsBillFromPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By addressText = By.xpath("//input[@id='text-field-12']");
	protected final By cityText = By.xpath("//input[@id='text-field-14']");
	protected final By USStateCountryCode = By.xpath("//input[@id='text-field-15']");
	protected final By USCountyText = By.xpath("//input[@id='text-field-16']");
	protected final By postalCode = By.xpath("//input[@id='text-field-17']");
	protected final By countryDropDown = By.xpath("//input[@id='select-field-2']");
	protected final By saveButton = By.xpath("//button[@class='save-toolbar-mod__submit___s7RnX']");

	/**
	 * enter the address in the Bill from address field
	 * @param address
	 */
	public void enterAddress( final String address )
	{
		WebElement addressField = wait.waitForElementDisplayed(addressText);
		text.enterText(addressField, address);
	}
	/**
	 * enter the city in the Bill from section.
	 * @param city
	 */
	public void enterCity( final String city )
	{
		WebElement cityField = wait.waitForElementDisplayed(cityText);
		text.enterText(cityField, city);
	}
	/**
	 * enter the country code in the Bill from section.
	 * @param countryCode
	 */
	public void enterCountryCode( final String countryCode )
	{
		WebElement countryField = wait.waitForElementDisplayed(USStateCountryCode);
		text.enterText(countryField, countryCode);
	}
	/**
	 * enter the US county in the Bill from section
	 * @param UScounty
	 */
	public void enterUSCounty( final String UScounty )
	{
		WebElement countryField = wait.waitForElementDisplayed(USCountyText);
		text.enterText(countryField, UScounty);
	}
	/**
	 * enter the postal codes in the Bill from section
	 * @param postcalCode
	 */
	public void enterPostalCode( final String postcalCode )
	{
		WebElement postalCodeField = wait.waitForElementDisplayed(postalCode);
		text.enterText(postalCodeField, postcalCode);
	}
	/**
	 * enter the country in the Bill from section
	 * @param country
	 */
	public void selectCountry( final String country )
	{
		WebElement countryDropDownField = wait.waitForElementDisplayed(countryDropDown);
		text.enterText(countryDropDownField, country);
	}
	/**
	 * click on save button.
	 */
	public void clickSaveButton( )
	{
		WebElement saveButtonField = wait.waitForElementDisplayed(saveButton);
		click.moveToElementAndClick(saveButtonField);
	}
}
