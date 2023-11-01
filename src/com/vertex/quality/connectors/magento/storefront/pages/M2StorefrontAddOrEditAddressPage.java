package com.vertex.quality.connectors.magento.storefront.pages;

import com.vertex.quality.connectors.magento.common.pages.MagentoStorefrontPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Representation of the Add/Edit Address page
 *
 * @author alewis
 */
public class M2StorefrontAddOrEditAddressPage extends MagentoStorefrontPage
{
	protected By phoneNumberId = By.id("telephone");
	protected By streetLineOneId = By.id("street_1");
	protected By cityId = By.id("city");
	protected By stateProvinceId = By.id("region_id");
	protected By zipCodeId = By.id("zip");
	protected By countryId = By.id("country");

	protected By saveAddressButtonClass = By.className("submit");

	public M2StorefrontAddOrEditAddressPage( WebDriver driver ) { super(driver); }

	public void inputPhoneNumber( String phoneNumber )
	{
		WebElement field = wait.waitForElementEnabled(phoneNumberId);

		field.sendKeys(phoneNumber);
	}

	public void inputStreetLine( String street )
	{
		WebElement field = wait.waitForElementEnabled(streetLineOneId);

		field.sendKeys(street);
	}

	public void inputCity( String city )
	{
		WebElement field = wait.waitForElementEnabled(cityId);

		field.sendKeys(city);
	}

	public void selectState( String selectString )
	{
		WebElement field = wait.waitForElementEnabled(stateProvinceId);

		dropdown.selectDropdownByValue(field, selectString);
	}

	public void inputZipCode( String zip )
	{
		WebElement field = wait.waitForElementEnabled(zipCodeId);

		field.sendKeys(zip);
	}

	public void selectCountry( String selectString )
	{
		WebElement field = wait.waitForElementEnabled(countryId);

		dropdown.selectDropdownByValue(field, selectString);
	}

	public M2StorefrontAddressBookPage clickSaveAddressButton( )
	{
		WebElement button = wait.waitForElementEnabled(saveAddressButtonClass);

		click.clickElementCarefully(button);

		M2StorefrontAddressBookPage addressBookPage = initializePageObject(M2StorefrontAddressBookPage.class);

		return addressBookPage;
	}
}
