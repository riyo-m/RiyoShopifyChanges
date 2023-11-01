package com.vertex.quality.connectors.dynamics365.business.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.dynamics365.business.pojo.BusinessAddressPojo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the billing and shipping component on the edit sales page
 *
 * @author osabha, cgajes
 */
public class BusinessShippingAndBillingComponent extends VertexComponent
{
	public BusinessShippingAndBillingComponent( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By shippingAndBillingComponentConLoc = By.className("caption-text");
	protected By shipToFieldConLoc = By.xpath("//div[@controlname='ShippingOptions']//select");
	protected By shipToAddressFieldsConLoc = By.className("ms-nav-band-container");
	protected By shipToAddressFieldsLoc = By.cssSelector("div[controlname]");
	protected By cityFieldLoc = By.xpath("//div[@controlname='Ship-to City']//input");
	protected By countryFieldLoc = By.cssSelector("input[aria-label*='Country/Region,']");
	protected By stateFieldLoc = By.xpath("//div[@controlname='Ship-to County']//input");
	protected By zipFieldLoc = By.xpath("//div[@controlname='Ship-to Post Code']//input");
	protected By line1FieldLoc = By.xpath("//div[@controlname='Ship-to Address']//input");
	protected By countriesListContLoc = By.cssSelector(".ms-nav-lookupform.ms-nav-noCommandBar");
	protected By countriesListLoc = By.cssSelector("a[aria-label*='Code,']");

	/**
	 * locates the header of billing and shipping section and clicks on it to expand it
	 */
	public void expandShippingAndBillingSection( )
	{
		String expectedText = "Shipping and Billing";
		WebElement shippingAndBillingTitleButton = element.selectElementByText(shippingAndBillingComponentConLoc,
			expectedText);
		if ( shippingAndBillingTitleButton != null )
		{
			click.clickElement(shippingAndBillingTitleButton);
		}
	}

	/**
	 * locates the ship to field and clicks on it
	 * then selects the customer address passed in.
	 *
	 * @param customerAddress
	 */
	public void selectShipToCustomAddress( String customerAddress )
	{
		expandShippingAndBillingSection();
		waitForPageLoad();
		WebElement shipToField = wait.waitForElementDisplayed(shipToFieldConLoc);
		scroll.scrollElementIntoView(shipToField);
		dropdown.selectDropdownByDisplayName(shipToField, customerAddress);
	}

	/**
	 * fills in the address into the ship to address fields.
	 *
	 * @param address
	 */
	public void fillInShippingAddress( BusinessAddressPojo address )
	{
		/**
		 WebElement addressFieldCon = null;
		 String expectedText = "ShippingOptions";
		 List<WebElement> conList = wait.waitForAllElementsPresent(shipToAddressFieldsConLoc);
		 conLooping:
		 for ( WebElement con : conList )
		 {
		 List<WebElement> elements = con.findElements(shipToAddressFieldsLoc);
		 for ( WebElement elem : elements )
		 {
		 String attributeText = elem.getAttribute("controlname");
		 if ( expectedText.equals(attributeText) )
		 {
		 addressFieldCon = con;
		 break conLooping;
		 }
		 }
		 } */
		waitForPageLoad();
		setLine1(address.getLine1());
		setCity(address.getCity());
		//setCountry(address.getCountry());
		setState(address.getState());
		setZip(address.getZip_code());
	}

	/**
	 * enters address line1 into the address line1 field
	 *
	 * @param addressLine1
	 * @param con
	 */
	public void setLine1( String addressLine1, WebElement con )
	{
		WebElement addressLine1Field = wait.waitForElementPresent(line1FieldLoc, con);
		text.enterText(addressLine1Field, addressLine1);
	}

	/**
	 * enters address line1 into the address line1 field
	 *
	 * @param addressLine1
	 */
	public void setLine1( String addressLine1 )
	{
		WebElement addressLine1Field = wait.waitForElementPresent(line1FieldLoc);
		text.enterText(addressLine1Field, addressLine1);
	}

	/**
	 * enters city into city field
	 *
	 * @param city
	 * @param con
	 */
	public void setCity( String city, WebElement con )
	{
		WebElement cityField = wait.waitForElementPresent(cityFieldLoc, con);
		text.enterText(cityField, city);
	}

	/**
	 * enters city into city field
	 *
	 * @param city
	 */
	public void setCity( String city )
	{
		WebElement cityField = wait.waitForElementPresent(cityFieldLoc);
		text.enterText(cityField, city);
	}

	/**
	 * enters address country into country field
	 *
	 * @param country
	 * @param con
	 */
	public void setCountry( String country, WebElement con )
	{
		WebElement countryToSelect = null;
		WebElement countryField = wait.waitForElementPresent(countryFieldLoc, con);
		text.enterText(countryField, country);
		WebElement listCon = wait.waitForElementPresent(countriesListContLoc);
		List<WebElement> list = wait.waitForAllElementsPresent(countriesListLoc, listCon);
		countryToSelect = element.selectElementByText(list, country);
		if ( countryToSelect != null )
		{
			click.clickElement(countryToSelect);
		}
	}

	/**
	 * enters address state into state field
	 *
	 * @param state
	 * @param con
	 */
	public void setState( String state, WebElement con )
	{
		WebElement stateField = wait.waitForElementPresent(stateFieldLoc, con);
		text.enterText(stateField, state);
	}

	/**
	 * enters address state into state field
	 *
	 * @param state
	 */
	public void setState( String state )
	{
		WebElement stateField = wait.waitForElementPresent(stateFieldLoc);
		text.enterText(stateField, state);
	}

	/**
	 * enters address zip code into zip field
	 *
	 * @param zip
	 * @param con
	 */
	public void setZip( String zip, WebElement con )
	{
		WebElement zipField = wait.waitForElementPresent(zipFieldLoc, con);
		text.enterText(zipField, zip);
	}

	/**
	 * enters address zip code into zip field
	 *
	 * @param zip
	 */
	public void setZip( String zip )
	{
		WebElement zipField = wait.waitForElementPresent(zipFieldLoc);
		text.enterText(zipField, zip);
	}
}
