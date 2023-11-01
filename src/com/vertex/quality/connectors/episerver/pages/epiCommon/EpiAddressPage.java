package com.vertex.quality.connectors.episerver.pages.epiCommon;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.EpiDataCommon;
import com.vertex.quality.connectors.episerver.pages.EpiAddAddressPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class EpiAddressPage extends VertexPage
{
	public EpiAddressPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ADDRESS_BLOCK = By.cssSelector("#addressBook>[class='row address-block']");
	protected By NEW_ADDRESS_BUTTON = By.linkText("New");
	protected By ADD_NEW_ADDRESS_HEADING = By.xpath("//h2[contains(text(), 'Add a new address')]");
	protected By ADD_NEW_BILLING_ADDRESS_BUTTON = By.xpath(".//h3[text()='Billing address']//following-sibling::div//button[text()='Add new address']");
	protected By NEW_ADDRESS_NAME = By.id("Address_Name");
	protected By NEW_ADDRESS_FIRST_NAME = By.id("Address_FirstName");
	protected By NEW_ADDRESS_LAST_NAME = By.id("Address_LastName");
	protected By NEW_ADDRESS_LINE1 = By.id("Address_Line1");
	protected By NEW_ADDRESS_ZIP_CODE = By.id("Address_PostalCode");
	protected By NEW_ADDRESS_CITY = By.id("Address_City");
	protected By NEW_ADDRESS_REGION_DROPDOWN = By.xpath(".//select[@id='Address_CountryRegion_Region']");
	protected By NEW_ADDRESS_REGION_BOX = By.xpath(".//input[@id='Address_CountryRegion_Region']");
	protected By NEW_ADDRESS_COUNTRY = By.id("Address_CountryCode");
	protected By NEW_ADDRESS_CONTACT = By.id("Address_DaytimePhoneNumber");
	protected By NEW_ADDRESS_EMAIL = By.id("Address_Email");
	protected By PREFERRED_SHIPPING = By.id("Address_ShippingDefault");
	protected By PREFERRED_BILLING = By.id("Address_BillingDefault");
    protected By SAVE_ADDRESS_BUTTON = By.xpath(".//h2[normalize-space(.)='Add a new address']/following-sibling::button[text()='Save']");

	/**
	 * This method is used to Delete the Specified Address from Address Page
	 */
	public void deleteAddress( String addressName, boolean mandatory )
	{
		VertexLogger.log("Delete address started");
		By ADDRESS_DELETE_BUTTON = null;
		List<WebElement> addressElementsList = element.getWebElements(ADDRESS_BLOCK);
		VertexLogger.log("Address Delete elements list");
		String fullAddressText = "";
		boolean flag = false;
		for ( WebElement addressElement : addressElementsList )
		{
			fullAddressText = addressElement.getText();
			VertexLogger.log("full address text");
			if ( fullAddressText.contains(addressName) )
			{
				ADDRESS_DELETE_BUTTON = By.xpath(String.format(
					"//h3[*[@value='%s']]/ancestor::*[@class='row address-block']//*[@type='submit' and text()='Delete']",
					addressName));
				VertexLogger.log(String.format("ADDRESS_DELETE_BUTTON: %s ", ADDRESS_DELETE_BUTTON));
				wait.waitForElementDisplayed(ADDRESS_DELETE_BUTTON);
				click.clickElement(ADDRESS_DELETE_BUTTON);
				alert.acceptAlert(DEFAULT_TIMEOUT);
				flag = true;
				break;
			}
		}
		if ( flag )
		{
			VertexLogger.log(String.format("Address: %s deleted successfully", addressName));
		}
		else
		{
			if ( mandatory )
			{
				VertexLogger.log(
					String.format("Please check the Address Book, Provided address: %s is not available", addressName),
					VertexLogLevel.ERROR);
			}
			VertexLogger.log(String.format("Address: %s is not available in the Address Book", addressName),
				VertexLogLevel.ERROR);
		}
		window.switchToDefaultContent();
	}

	/**
	 * This method is used to Click on New Button from AddressPage to Add the new
	 * Address
	 */
	public EpiAddAddressPage clickOnNewButton( )
	{
		EpiAddAddressPage addaddresspage = new EpiAddAddressPage(driver);
		wait.waitForElementDisplayed(NEW_ADDRESS_BUTTON);
		click.clickElement(NEW_ADDRESS_BUTTON);
		waitForPageLoad();
		return addaddresspage;
	}

	/**
	 * This method is used to Click on New Button from AddressPage to Add the new
	 * Address
	 */
	public EpiAddAddressPage clickMultiAddressAddButton( String productName )
	{
		EpiAddAddressPage addaddresspage = new EpiAddAddressPage(driver);
		By SHIP_MULTI_ADDRESS1_ADD_BUTTON = By.xpath(String.format(
			"//*[@class='row\'][*[strong[text()='Item']]/*[contains(text(), '%s')]]//button[text()='Add new address']",
			productName));
		wait.waitForElementDisplayed(SHIP_MULTI_ADDRESS1_ADD_BUTTON);
		click.clickElement(SHIP_MULTI_ADDRESS1_ADD_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_HEADING);
		return addaddresspage;
	}

	/**
	 * This method is used to Delete the Specified Address from Address Page
	 */
	public void deleteAddressFromAddressBook( String addressName, boolean mandatory )
	{
		EpiStoreFrontHomePage portalhomepage = new EpiStoreFrontHomePage(driver);
		portalhomepage.navigateToHomePage();
		portalhomepage.clickUserIcon();
		portalhomepage.clickAddressBookLink();
		this.deleteAddress(addressName, mandatory);
	}

	/**
	 * This method is used to Get the Specified Address from AddressBookPage
	 */
	public String getAddressFromAddressBook( String addressName, boolean mandatory )
	{
		List<WebElement> addressElementsList = element.getWebElements(ADDRESS_BLOCK);
		VertexLogger.log("Address Delete elements list");
		String fullAddressText = "";
		By ADDRESS_NAME = null;
		String selectedAddress = null;
		boolean flag = false;
		for ( WebElement addressElement : addressElementsList )
		{
			fullAddressText = addressElement.getText();
			VertexLogger.log("full_address_text");
			if ( fullAddressText.contains(addressName) )
			{
				ADDRESS_NAME = By.xpath(
					String.format("//h3[*[@value='%s']]/ancestor::*[@class='row address-block']", addressName));
				VertexLogger.log(String.format("ADDRESS_NAME: %s ", ADDRESS_NAME));
				wait.waitForElementDisplayed(ADDRESS_NAME);
				selectedAddress = attribute.getElementAttribute(ADDRESS_NAME, "textContent");
				flag = true;
			}
		}
		if ( flag )
		{
			VertexLogger.log(String.format("Selecterd Address: %s ", addressName));
		}
		else
		{
			if ( mandatory )
			{
				VertexLogger.log(
					String.format("Please check the Address Book, Provided address: %s is not available", addressName),
					VertexLogLevel.ERROR);
			}
			VertexLogger.log(String.format("Address: %s is not available in the Address Book", addressName),
				VertexLogLevel.ERROR);
		}
		window.switchToDefaultContent();
		return selectedAddress;
	}

	/**
	 * Add new address with preferred shipping & billing addresses
	 * sequence must be followed to set address: Name, Line1, Zip code, City, Region, Country
	 * example: addressPage.addNewAddress("Gettysburg PA", "1270 York Road", "17325", "Gettysburg", "United States");
	 *
	 * @param address address details
	 */
	public void addNewAddress(String... address) {
		addNewAddress(true, true, address);
	}

	/**
	 * Add new address with custom shipping & billing preferences
	 * sequence must be followed to set address: Name, Line1, Zip code, City, Region, Country
	 * example: addressPage.addNewAddress(true, false, "Gettysburg PA", "1270 York Road", "17325", "Gettysburg", "United States");
	 *
	 * @param isShipping true to set preferred shipping, false to not to set preferred shipping
	 * @param isBilling  true to set preferred billing, false to not to set preferred billing
	 * @param address    address details
	 */
	public void addNewAddress(boolean isShipping, boolean isBilling, String... address) {
		if (address.length != 6) {
			VertexLogger.log("Parameters must be in sequence & all mandatory parameters must be set, Kindly check logs & JavaDoc to avoid failures");
			Assert.fail("Failed due to wrong parameters");
		}
		waitForPageLoad();
		if (!element.isElementPresent(ADD_NEW_ADDRESS_HEADING) && element.isElementPresent(ADD_NEW_BILLING_ADDRESS_BUTTON)) {
			click.javascriptClick(wait.waitForElementPresent(ADD_NEW_BILLING_ADDRESS_BUTTON));
			waitForPageLoad();
		}
		wait.waitForElementPresent(ADD_NEW_ADDRESS_HEADING);
		dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(NEW_ADDRESS_COUNTRY), address[5]);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_NAME), address[0]);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_FIRST_NAME), EpiDataCommon.DefaultContactDetails.CUSTOMER_FIRST_NAME.text);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_LAST_NAME), EpiDataCommon.DefaultContactDetails.CUSTOMER_LAST_NAME.text);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_LINE1), address[1]);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_ZIP_CODE), address[2]);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_CITY), address[3]);
		if (element.isElementPresent(NEW_ADDRESS_REGION_DROPDOWN)) {
			dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(NEW_ADDRESS_REGION_DROPDOWN), address[4]);
		} else {
			text.enterText(wait.waitForElementPresent(NEW_ADDRESS_REGION_BOX), address[4]);
		}
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_CONTACT), EpiDataCommon.DefaultContactDetails.CUSTOMER_CONTACT_NO.text);
		text.enterText(wait.waitForElementPresent(NEW_ADDRESS_EMAIL), EpiDataCommon.DefaultContactDetails.CUSTOMER_EMAIL_ID.text);
		if (isShipping) {
			click.javascriptClick(wait.waitForElementPresent(PREFERRED_SHIPPING));
			VertexLogger.log("Set preferred shipping address");
		}
		else {
			VertexLogger.log("Not set preferred shipping address");
		}
		if (isBilling) {
			click.javascriptClick(wait.waitForElementPresent(PREFERRED_BILLING));
			VertexLogger.log("Set preferred billing address");
		}
		else {
			VertexLogger.log("Not set preferred billing address");
		}
        click.javascriptClick(wait.waitForElementPresent(SAVE_ADDRESS_BUTTON));
		VertexLogger.log("Added the address: " + Arrays.toString(address));
	}
}
