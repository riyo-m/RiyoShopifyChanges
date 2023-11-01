package com.vertex.quality.connectors.shopify.ui.pages.StoreFront;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.connectors.shopify.ui.pages.ShopifyPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StoreFrontContactDetailsPage extends ShopifyPage
{
	/**
	 * Parameterized constructor of the class that helps to initialize the object & to access the parents.
	 *
	 * @param driver Object of WebDriver
	 */
	public StoreFrontContactDetailsPage( final WebDriver driver )
	{
		super(driver);
	}

	protected By emailField = By.id("email");
	protected By countryFields = By.name("countryCode");
	protected By firstNameFields = By.xpath("(//input[@name='firstName'])[1]");
	protected By lastNameField = By.xpath("(//input[@name='lastName'])[1]");
	protected By addressField = By.xpath("(//input[@name='address1'])[1]");
	protected By cityFields = By.xpath("(//input[@name='city'])[1]");
	protected By stateField = By.xpath("//select[@name='zone']");
	protected By postalCodeField = By.xpath("(//input[@name='postalCode'])[1]");

	public void enterEmailField( String email )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(emailField), email);
	}

	public void selectCountryField( String country )
	{
		waitForPageLoad();
		dropdown.selectDropdownByDisplayName(countryFields, country);
	}

	public void enterFirstNameField( String firstName )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(firstNameFields), firstName);
	}

	public void enterLastNameField( String lastName )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(lastNameField), lastName);
	}

	public void enterAddressField( String address )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(addressField), address);
	}

	public void enterCityField( String city )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(cityFields), city);
	}

	public void selectStateField( String state )
	{
		waitForPageLoad();
		dropdown.selectDropdownByDisplayName(stateField, state);
	}

	public void enterPostalCodeField( String postalCode )
	{
		waitForPageLoad();
		text.enterText(wait.waitForElementDisplayed(postalCodeField), postalCode);
	}
	public void enterContactDetails(){
		enterEmailField("rptest@sp.com");
		selectCountryField("United States");
		enterFirstNameField("Riyo");
		enterLastNameField("M");
		enterAddressField("33rd Street");
		enterCityField("Queens");
		selectStateField("New York");
		enterPostalCodeField("11427");

	}
	public void enterColoradoCAddressDetails(){
		enterEmailField("rptest@sp.com");
		selectCountryField(Address.ColoradoSprings.country.fullName);
		enterFirstNameField("Riyo");
		enterLastNameField("M");
		enterAddressField(Address.ColoradoSprings.addressLine1);
		enterCityField(Address.ColoradoSprings.city);
		selectStateField(Address.ColoradoSprings.state.fullName);
		enterPostalCodeField(Address.ColoradoSprings.zip5);

	}
}
