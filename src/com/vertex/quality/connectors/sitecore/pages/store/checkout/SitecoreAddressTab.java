package com.vertex.quality.connectors.sitecore.pages.store.checkout;

import com.vertex.quality.connectors.sitecore.pages.base.SitecoreBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents address tab in sitecore checkout page
 *
 * @author Shiva Mothkula, Daniel Bondi
 */
public class SitecoreAddressTab extends SitecoreBasePage
{

	protected By addressDropdownShipping = By.className("shipping-address-control");
	protected By addressDropdownBilling = By.className("billing-address-control");
	protected By firstName = By.id("FirstName");
	protected By lastName = By.id("LastName");
	protected By email = By.id("Email");
	protected By company = By.id("Company");
	protected By country = By.name("Country");
	protected By state = By.id("State");
	protected By city = By.id("City");
	protected By address1 = By.id("Address1");
	protected By address2 = By.id("Address2");
	protected By zipPostalCode = By.id("ZipPostalCode");
	protected By phoneNumber = By.id("PhoneNumber");
	protected By nextButton = By.className("btn-success");

	public SitecoreAddressTab( final WebDriver driver )
	{
		super(driver);
	}

	//TODO make address enum

	/**
	 * This method is used to select the address from the drop-down (select
	 * "New Address" to provide a new address which is not available in the
	 * drop-down)
	 *
	 * @param address Address option text to select from the drop-down
	 */
	public void selectAddress( final String address )
	{
		if ( element.isElementPresent(addressDropdownShipping) )
		{
			dropdown.selectDropdownByDisplayName(addressDropdownShipping, address);
		}
		else if ( element.isElementPresent(addressDropdownBilling) )
		{
			dropdown.selectDropdownByDisplayName(addressDropdownBilling, address);
		}
		clickNextButton();
		waitForPageLoad();
	}

	/**
	 * This method is used to set the user first name of address
	 *
	 * @param firstNameText of users
	 */
	public void setFirstName( final String firstNameText )
	{
		text.enterText(firstName, firstNameText);
	}

	/**
	 * This method is used to set the user last name
	 *
	 * @param lastNameText of users on address
	 */
	public void setLastName( final String lastNameText )
	{
		text.enterText(lastName, lastNameText);
	}

	/**
	 * This method is used to set the Email address.
	 *
	 * @param emailText of user to set
	 */
	public void setEmail( final String emailText )
	{
		text.enterText(email, emailText);
	}

	/**
	 * This method is used to set the user company
	 *
	 * @param companyText for address
	 */
	public void setCompany( final String companyText )
	{
		text.enterText(company, companyText);
	}

	/**
	 * This method is used to select the country from the drop-down
	 *
	 * @param countryText for address
	 */
	public void selectCountry( final String countryText )
	{
		dropdown.selectDropdownByDisplayName(country, countryText);
	}

	/**
	 * This method is used to set the user first name
	 *
	 * @param stateText for address
	 */
	public void setState( final String stateText )
	{
		text.enterText(state, stateText);
	}

	/**
	 * This method is used to set the city
	 *
	 * @param cityText for address
	 */
	public void setCity( final String cityText )
	{
		text.enterText(city, cityText);
	}

	/**
	 * This method is used to set the address line-1
	 *
	 * @param addressLine1 for address
	 */
	public void setAddressLine1( final String addressLine1 )
	{
		text.enterText(address1, addressLine1);
	}

	/**
	 * This method is used to set the address line-2
	 *
	 * @param addressLine2 for address
	 */
	public void setAddressLine2( final String addressLine2 )
	{
		text.enterText(address2, addressLine2);
	}

	/**
	 * This method is used to set the ZIP/Postal code
	 *
	 * @param zipCode for address
	 */
	public void setZipCode( final String zipCode )
	{
		text.enterText(zipPostalCode, zipCode);
	}

	/**
	 * This method is used to set the Phone number
	 *
	 * @param phoneNumberText for address
	 */
	public void setPhoneNumber( final String phoneNumberText )
	{
		text.enterText(phoneNumber, phoneNumberText);
	}

	/**
	 * This method is used to get the user first name
	 *
	 * @return gets first name of the user
	 */
	public String getFirstName( )
	{
		String fNameText = text.retrieveTextFieldContents(firstName);
		if ( fNameText != null )
		{
			fNameText = fNameText.trim();
		}
		return fNameText;
	}

	/**
	 * This method is used to get the last name
	 *
	 * @return lastName of user
	 */
	public String getLastName( )
	{
		String lastNameText = text.retrieveTextFieldContents(lastName);
		if ( lastNameText != null )
		{
			lastNameText = lastNameText.trim();
		}
		return lastNameText;
	}

	/**
	 * This method is used to get the Email address
	 *
	 * @return email of user
	 */
	public String getEmail( )
	{
		String emailText = text.retrieveTextFieldContents(email);
		if ( emailText != null )
		{
			emailText = emailText.trim();
		}
		return emailText;
	}

	/**
	 * This method is used to get the company name.
	 *
	 * @return company of user
	 */
	public String getCompany( )
	{
		String companyText = text.retrieveTextFieldContents(company);
		if ( companyText != null )
		{
			companyText = companyText.trim();
		}
		return companyText;
	}

	/**
	 * This method is used to get the country
	 *
	 * @return country of user
	 */
	public String getCountry( )
	{
		WebElement countryElement = dropdown.getDropdownSelectedOption(country);
		String countryText = text.getElementText(countryElement);
		if ( countryText != null )
		{
			countryText = countryText.trim();
		}
		return countryText;
	}

	/**
	 * This method is used get the state
	 *
	 * @return state of user
	 */
	public String getState( )
	{
		String stateText = text.retrieveTextFieldContents(state);
		if ( stateText != null )
		{
			stateText = stateText.trim();
		}
		return stateText;
	}

	/**
	 * This method is used to get the city
	 *
	 * @return city of user
	 */
	public String getCity( )
	{
		String cityText = text.retrieveTextFieldContents(city);
		if ( cityText != null )
		{
			cityText = cityText.trim();
		}
		return cityText;
	}

	/**
	 * This method is used to get the address line-1
	 *
	 * @return address line 1 of user
	 */
	public String getAddressLine1( )
	{
		String addressLine1 = text.retrieveTextFieldContents(address1);
		if ( addressLine1 != null )
		{
			addressLine1 = addressLine1.trim();
		}
		return addressLine1;
	}

	/**
	 * This method is used to get the address line-2
	 *
	 * @return address line 2 of user
	 */
	public String getAddressLine2( )
	{
		String addressLine2 = text.retrieveTextFieldContents(address2);
		if ( addressLine2 != null )
		{
			addressLine2 = addressLine2.trim();
		}
		return addressLine2;
	}

	/**
	 * This method is used to get the ZIP/Postal code
	 *
	 * @return zip code of user
	 */
	public String getZipCode( )
	{
		String zip = text.retrieveTextFieldContents(zipPostalCode);
		if ( zip != null )
		{
			zip = zip.trim();
		}
		return zip;
	}

	/**
	 * This method is used to get the phone number
	 *
	 * @return phone number of user
	 */
	public String getPhoneNumber( )
	{
		String phone = text.retrieveTextFieldContents(phoneNumber);
		if ( phone != null )
		{
			phone = phone.trim();
		}
		return phone;
	}

	//TODO make paramaters enum

	/**
	 * This overloads {@link #setAddress(String, String, String, String, String, String, String, String, String,
	 * String)} to assume that there's no value for addressLine2
	 */
	public void setAddress( final String firstName, final String lastName, final String email, final String country,
		final String state, final String city, final String addressLine1, final String zip, final String phone )
	{

		setAddress(firstName, lastName, email, country, state, city, addressLine1, "", zip, phone);
		clickNextButton();
	}

	/**
	 * This method is used to set the address
	 *
	 * @param firstName    for address to set
	 * @param lastName     for address to set
	 * @param email        for address to set
	 * @param country      for address to set
	 * @param state        for address to set
	 * @param city         for address to set
	 * @param addressLine1 for address to set
	 * @param addressLine2 for address to set
	 * @param zip          for address to set
	 * @param phone        for address to set
	 */
	public void setAddress( final String firstName, final String lastName, final String email, final String country,
		final String state, final String city, final String addressLine1, final String addressLine2, final String zip,
		final String phone )
	{

		final String newAddressText = "New Address";
		selectAddress(newAddressText);
		waitForPageLoad();

		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		selectCountry(country);
		setState(state);
		setCity(city);
		setAddressLine1(addressLine1);
		setAddressLine2(addressLine2);
		setZipCode(zip);
		setPhoneNumber(phone);
	}

	/**
	 * select address based on parameters which are concatenated
	 *
	 * @param firstName    for address to select
	 * @param lastName     for address to select
	 * @param city         for address to select
	 * @param addressLine1 for address to select
	 * @param addressLine2 for address to select
	 * @param zip          for address to select
	 * @param country      for address to select
	 */
	public void selectAddress( final String firstName, final String lastName, final String city,
		final String addressLine1, final String addressLine2, final String zip, final String country )
	{
		final String address = firstName + " " + lastName + ", " + addressLine1 + " " + addressLine2 + ", " + city +
							   " " + zip + ", " + country;
		selectAddress(address);
	}

	/**
	 * This method is used to click the next method. This is common across all the
	 * different tabs available in this page.
	 */
	public void clickNextButton( )
	{
		WebElement nextBtnElement = element.getWebElement(nextButton);
		scroll.scrollElementIntoView(nextBtnElement);
		click.clickElement(nextBtnElement);
		final String loadingText = "Loading...";
		List<WebElement> buttons = null;
		try
		{
			buttons = wait.waitForAnyElementsDisplayed(By.tagName("button"));
		}
		catch ( Exception e )
		{

		}
		WebElement loadingButton = null;
		//search for leading button
		if ( buttons != null )
		{
			for ( WebElement btn : buttons )
			{
				try
				{
					String btnText = text.getElementText(btn);
					if ( btnText.equals(loadingText) )
					{
						loadingButton = btn;
						break;
					}
				}
				catch ( StaleElementReferenceException e )
				{
					//Loading element disappeared in time it took looking for loading button
					//so loading button webelment is stale and caused error
					break;
					//ignore exception
				}
			}
		}
		//loading button has been found
		if ( loadingButton != null )
		{
			try
			{
				wait.waitForElementNotDisplayed(loadingButton);
			}
			catch ( StaleElementReferenceException e )
			{
				//loading button element became stale and is no longer on page
				//ignore exception
			}
		}
	}
}
