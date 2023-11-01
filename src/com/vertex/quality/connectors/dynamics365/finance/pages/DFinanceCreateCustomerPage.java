package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/**
 * Create Purchase Order page common methods and object declaration page
 *
 * @author Shiva Mothkula
 */
public class DFinanceCreateCustomerPage extends DFinanceBasePage
{
	protected By CUSTOMER_ACCOUNT_INPUT = By.cssSelector("input[name='DynamicHeader_AccountNum'][id*='AccountNum']");
	protected By CUSTOMER_TYPE = By.cssSelector("input[name='partyTypeComboBox'][id*='partyTypeComboBox']");
	protected By CUSTOMER_NAME = By.cssSelector("input[name='Org_Name'][id*='CreateForm']");
	protected By CUSTOMER_GROUP = By.cssSelector("input[name='DynamicDetail_CustGroup'][id*='CreateForm']");
	protected By SALES_TAX_GROUP = By.cssSelector("input[name='DynamicDetail_TaxGroup']");

	protected By COUNTRY = By.cssSelector("input[name='LogisticsPostalAddress_CountryRegionId'][id*='CreateForm']");
	protected By STREET = By.cssSelector("*[name='LogisticsPostalAddress_Street'][id*='CreateForm']");
	protected By CITY = By.cssSelector("input[name='LogisticsPostalAddress_City'][id*='CreateForm']");
	protected By ZIP_CODE = By.cssSelector("input[name='LogisticsPostalAddress_ZipCode'][id*='CreateForm']");
	protected By STATE = By.cssSelector("input[name='LogisticsPostalAddress_State'][id*='CreateForm']");

	protected By DETAILS_HEADING = By.cssSelector("header[id*='DetailsTabPage_header']");
	protected By ADDRESS_HEADING = By.cssSelector("header[id*='AddressTabPage_header']");

	final String TMP_TRXN_ROW_LOCATOR = "[class='rowTemplate listItem'][id*='TaxTmpWorkTrans']";

	public DFinanceCreateCustomerPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Set 'Customer Account Number'
	 *
	 * @param customerAccountNumber
	 */
	public void setCustomerAccountNumber( String customerAccountNumber )
	{
		wait.waitForElementEnabled(CUSTOMER_ACCOUNT_INPUT);
		text.enterText(CUSTOMER_ACCOUNT_INPUT, customerAccountNumber);
		waitForPageLoad();
	}

	public void selectCustomerType( String type )
	{
		wait.waitForElementEnabled(CUSTOMER_TYPE);

		String actualType = attribute.getElementAttribute(CUSTOMER_TYPE, "title");
		if ( actualType != null )
		{
			actualType = actualType.trim();
		}

		if ( !actualType.equalsIgnoreCase(type) )
		{
			click.clickElement(CUSTOMER_TYPE);
			By typeOptionBy = By.xpath(String.format("//a[text()='%s']", type));
			wait.waitForElementEnabled(typeOptionBy);
			click.clickElement(typeOptionBy);
			waitForPageLoad();
		}
		else
		{
			VertexLogger.log(String.format("Customer Type: %s is already selected", type));
		}
	}

	public void setCustomerName( String customerName )
	{
		wait.waitForElementEnabled(CUSTOMER_NAME);
		text.enterText(CUSTOMER_NAME, customerName);
		waitForPageLoad();
	}

	public void setCustomerGroup( String customerGroup )
	{
		wait.waitForElementEnabled(CUSTOMER_GROUP);
		text.enterText(CUSTOMER_GROUP, customerGroup + Keys.TAB);
		waitForPageLoad();
	}

	public String getSalesTaxGroup( )
	{
		attribute.waitForElementAttributeChange(SALES_TAX_GROUP, "title");
		String salesTaxGroup = attribute.getElementAttribute(SALES_TAX_GROUP, "title");

		if ( salesTaxGroup != null )
		{
			salesTaxGroup = salesTaxGroup.trim();
		}
		return salesTaxGroup;
	}

	public String getCountry( )
	{
		wait.waitForElementEnabled(COUNTRY);
		String country = attribute.getElementAttribute(COUNTRY, "title");

		if ( country != null )
		{
			country = country.trim();
		}
		return country;
	}

	public void setCountry( String country )
	{
		wait.waitForElementEnabled(COUNTRY);
		text.enterText(COUNTRY, country + Keys.TAB);
	}

	public void setCity( String city )
	{
		wait.waitForElementEnabled(CITY);
		text.enterText(CITY, city + Keys.TAB);
		waitForPageLoad();
	}

	public void setZipCode( String zipCode )
	{
		wait.waitForElementEnabled(ZIP_CODE);
		text.enterText(ZIP_CODE, zipCode + Keys.TAB);
		waitForPageLoad();
	}

	public void setState( String state )
	{
		wait.waitForElementEnabled(STATE);
		text.enterText(STATE, state + Keys.TAB);
		waitForPageLoad();
	}

	public void setStreet( String street )
	{
		wait.waitForElementEnabled(STREET);
		text.enterText(STREET, street + Keys.TAB);
		waitForPageLoad();
	}

	public void setCustomerDetails( String customerAccount, String customerType, String customerName,
		String customerGroup )
	{
		setCustomerAccountNumber(customerAccount);
		selectCustomerType(customerType);
		setCustomerName(customerName);
		setCustomerGroup(customerGroup);
	}

	public void setCustomerAddressDetails( String street, String city, String state, String country, String zipCode )
	{
		setCountry(country);
		setZipCode(zipCode);
		setState(state);
		setStreet(street);
		setCity(city);
	}
}

