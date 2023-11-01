package com.vertex.quality.connectors.episerver.pages;

import com.vertex.quality.common.enums.Address;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Episerver Add Address Page - To enter Shipping/Billing Address Details and
 * Save the changes.
 */
public class EpiAddAddressPage extends VertexPage
{
	public EpiAddAddressPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ADDRESS_NAME = By.id("Address_Name");
	protected By ADDRESS_FIRST_NAME = By.id("Address_FirstName");
	protected By ADDRESS_LAST_NAME = By.id("Address_LastName");
	protected By ADDRESS_LINE1 = By.id("Address_Line1");
	protected By ADDRESS_ZIP_CODE = By.id("Address_PostalCode");
	protected By ADDRESS_CITY = By.id("Address_City");
	protected By ADDRESS_REGION = By.id("Address_CountryRegion_Region");
	protected By ADDRESS_COUNTRY = By.id("Address_CountryCode");
	protected By ADDRESS_EMAIL = By.id("Address_Email");
	protected By ADDRESS_SAVE_BUTTON = By.xpath("//*[contains(text(),'Save')]");
	protected By ADDRESS_CLOSE_BUTTON = By.cssSelector("[type='button'][class*='close']");

	/**
	 * This method is used to Enter Name
	 */
	public void setName( String name )
	{
		wait.waitForElementDisplayed(ADDRESS_NAME, 120);
		text.enterText(ADDRESS_NAME, name);
	}

	/**
	 * This method is used to Enter FirstName
	 */
	public void setFirstName( String first_name )
	{
		text.enterText(ADDRESS_FIRST_NAME, first_name);
	}

	/**
	 * This method is used to Enter LastName
	 */
	public void setLastName( String last_name )
	{
		text.enterText(ADDRESS_LAST_NAME, last_name);
	}

	/**
	 * This method is used to Enter Address Line1
	 */
	public void setAddressLine1( String address_line1 )
	{
		text.enterText(ADDRESS_LINE1, address_line1);
	}

	/**
	 * This method is used to Enter City Name
	 */
	public void setCity( String city )
	{
		text.enterText(ADDRESS_CITY, city);
	}

	/**
	 * This method is used to select Region
	 */
	public void selectRegion( String region )
	{
		dropdown.selectDropdownByDisplayName(ADDRESS_REGION, region);
	}

	/**
	 * This method is used to select Country
	 */
	public void selectCountry( String country )
	{
		dropdown.selectDropdownByDisplayName(ADDRESS_COUNTRY, country);
	}

	/**
	 * This method is used to Enter ZipCode
	 */
	public void setZipCode( String zip_code )
	{
		text.enterText(ADDRESS_ZIP_CODE, zip_code);
	}

	/**
	 * This method is used to Enter Address Email
	 */
	public void setAddressEmail( String email )
	{
		scroll.scrollElementIntoView(element.getWebElement(ADDRESS_EMAIL));
		text.enterText(ADDRESS_EMAIL, email);
	}

	/**
	 * This method is used to save changes on "ADD Address Page"
	 */
	public void clickAddSaveAddressButton( )
	{
		wait.waitForElementDisplayed(ADDRESS_SAVE_BUTTON);
		click.clickElement(ADDRESS_SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * This method is used to Fill/Enter Universal City Address Details on "ADD
	 * Address Page"
	 */
	public void enterUCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;

		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.UniversalCity.addressLine1;
		String city = Address.UniversalCity.city;
		String zip = Address.UniversalCity.zip5;
		String country = Address.UniversalCity.country.fullName;
		String state = Address.UniversalCity.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Edison City Address Details on "ADD Address
	 * Page"
	 */
	public void enterEdisonAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.Edison.addressLine1;

		String city = Address.Edison.city;
		String zip = Address.Edison.zip5;
		String country = Address.Edison.country.fullName;
		String state = Address.Edison.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Anaheim City Address Details on "ADD
	 * Address Page"
	 */
	public void enterAnaheimCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.Anaheim.addressLine1;

		String city = Address.Anaheim.city;
		String zip = Address.Anaheim.zip5;
		String country = Address.Anaheim.country.fullName;
		String state = Address.Anaheim.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Durham City Address Details on "ADD Address
	 * Page"
	 */
	public void enterDurhamCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.Durham.addressLine1;

		String city = Address.Durham.city;
		String zip = Address.Durham.zip5;
		String country = Address.Durham.country.fullName;
		String state = Address.Durham.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Tysons City Address Details on "ADD Address
	 * Page"
	 */
	public void enterTysonsCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.Tysons.addressLine1;
		String city = Address.Tysons.city;
		String zip = Address.Tysons.zip5;
		String country = Address.Tysons.country.fullName;
		String state = Address.Tysons.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Grand Rapids City Address Details on "ADD
	 * Address Page"
	 */
	public void enterGrandRapidsCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.GrandRapids.addressLine1;
		String city = Address.GrandRapids.city;
		String zip = Address.GrandRapids.zip5;
		String country = Address.GrandRapids.country.fullName;
		String state = Address.GrandRapids.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Invalid Address Details on "ADD Address
	 * Page"
	 */
	public void enterInvalidAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.InvalidAddress.addressLine1;
		String city = Address.InvalidAddress.city;
		String zip = Address.InvalidAddress.zip5;
		String country = Address.InvalidAddress.country.fullName;
		String state = Address.InvalidAddress.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Folsom City Address Details on "ADD Address
	 * Page"
	 */
	public void enterFolsomCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.Folsom.addressLine1;
		String city = Address.Folsom.city;
		String zip = Address.Folsom.zip5;
		String country = Address.Folsom.country.fullName;
		String state = Address.Folsom.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to Fill/Enter Sammamish City Address Details on "ADD
	 * Address Page"
	 */
	public void enterSammamishCityAddressDetails( )
	{
		String name = CommonDataProperties.FULL_NAME;
		String firstName = CommonDataProperties.FIRST_NAME;
		String lastName = CommonDataProperties.LAST_NAME;
		String email = CommonDataProperties.EMAIL;

		String addressLine1 = Address.Washington.addressLine1;
		String city = Address.Washington.city;
		String zip = Address.Washington.zip5;
		String country = Address.Washington.country.fullName;
		String state = Address.Washington.state.fullName;

		enterAddressDetailsAndSave(name, firstName, lastName, addressLine1, zip, city, state, country, email);
	}

	/**
	 * This method is used to close "Add New Address" Page
	 */
	public void clickOnAddressCloseButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(ADDRESS_CLOSE_BUTTON);
		click.clickElement(ADDRESS_CLOSE_BUTTON);
		waitForPageLoad();
	}

	public void enterAddressDetailsAndSave( String name, String firstName, String lastName, String addressLine1,
		String zip, String city, String state, String country, String email )
	{
		setName(name);
		setFirstName(firstName);
		setLastName(lastName);
		setAddressLine1(addressLine1);
		setZipCode(zip);
		setCity(city);
		selectRegion(state);
		selectCountry(country);
		setAddressEmail(email);
		clickAddSaveAddressButton();
	}
}
