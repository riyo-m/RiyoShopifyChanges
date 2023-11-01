package com.vertex.quality.connectors.episerver.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Episerver Administration Oseries Settings page - contains all re-usable
 * methods specific to this page.
 */
public class EpiOseriesSettingsPage extends VertexPage
{
	public EpiOseriesSettingsPage( WebDriver driver )
	{
		super(driver);
	}

	protected By USER_NAME = By.id("FullRegion_UserNameTextBox");
	protected By PASSWORD = By.id("FullRegion_PasswordTextBox");
	protected By TRUSTED_ID = By.id("FullRegion_TrustedIdTextBox");
	protected By OSERIES_CALC_ENDPOINT_URL = By.id("FullRegion_CalculationEndpointTextBox");
	protected By OSERIES_ADDR_LOOKUP_ENDPOINT_URL = By.id("FullRegion_AddressEndpointTextBox");

	/**
	 * This method is used to switch to Region frame
	 */
	public void switchToRegionFrame( )
	{
		driver
			.switchTo()
			.defaultContent();
		By region = By.id("FullRegion_InfoFrame");
		wait.waitForElementPresent(region);
		window.switchToFrame(region, 60);
	}

	/**
	 * This method is used to retrieve and validate the fields on Settings page
	 */
	public Map<String, String> getFieldValueAndReadOnlyStatusOfSettingsTab( String field_name )
	{
		By USER_NAME_LABLE = By.xpath(String.format("//*[contains(text(), '%s')]", field_name));
		Map<String, String> map = new HashMap<String, String>();
		switchToRegionFrame();
		wait.waitForElementDisplayed(USER_NAME_LABLE);

		String actual_field_value = null;
		String actual_field_status = null;

		if ( field_name == "Username" )
		{
			actual_field_value = attribute.getElementAttribute(USER_NAME, "value");
			actual_field_status = attribute.getElementAttribute(USER_NAME, "readonly");
		}
		else if ( field_name == "Password" )
		{
			actual_field_value = attribute.getElementAttribute(PASSWORD, "value");
			actual_field_status = attribute.getElementAttribute(PASSWORD, "readonly");
		}
		else if ( field_name == "TrustedID" )
		{
			actual_field_value = attribute.getElementAttribute(TRUSTED_ID, "value");
			actual_field_status = attribute.getElementAttribute(TRUSTED_ID, "readonly");
		}
		else if ( field_name == "O Series Calculation Endpoint" )
		{
			actual_field_value = attribute.getElementAttribute(OSERIES_CALC_ENDPOINT_URL, "value");
			actual_field_status = attribute.getElementAttribute(OSERIES_CALC_ENDPOINT_URL, "readonly");
		}
		else if ( field_name == "O Series Address Lookup Endpoint" )
		{
			actual_field_value = attribute.getElementAttribute(OSERIES_ADDR_LOOKUP_ENDPOINT_URL, "value");
			actual_field_status = attribute.getElementAttribute(OSERIES_ADDR_LOOKUP_ENDPOINT_URL, "readonly");
		}
		else
		{
			VertexLogger.log("Given Field Name is incorrect", VertexLogLevel.ERROR);
		}
		map.put("ACTUAL_VALUE", actual_field_value);
		map.put("ACTUAL_STATUS", actual_field_status);
		return map;
	}

	/**
	 * This method is used to retrieve and validate the UserName field on Settings
	 * page
	 */
	public void validateValueAndReadOnlyStatusOfUsername( String filed_name, String expected_value,
		String expected_status )
	{
		Map<String, String> map = this.getFieldValueAndReadOnlyStatusOfSettingsTab(filed_name);
		assertEquals(map.get("ACTUAL_VALUE"), expected_value);
		VertexLogger.log(String.format("Expected value: %s matched with Actual value : %s", expected_value,
			map.get("ACTUAL_VALUE")));
		assertEquals(map.get("ACTUAL_STATUS"), expected_status);
		VertexLogger.log(String.format("Expected status: %s matched with Actual status : %s", expected_status,
			map.get("ACTUAL_STATUS")));
	}

	/**
	 * This method is used to retrieve and validate the Password field on Settings
	 * page
	 */
	public void validateReadOnlyStatusOfPassword( String field_name, String expected_status )
	{
		Map<String, String> map = this.getFieldValueAndReadOnlyStatusOfSettingsTab(field_name);
		assertEquals(map.get("ACTUAL_STATUS"), expected_status);
		VertexLogger.log(String.format("Expected status : %s matched with Actual status : %s", expected_status,
			map.get("ACTUAL_STATUS")));
	}

	/**
	 * This method is used to retrieve and validate the TrustedID field on Settings
	 * page
	 */
	public void validateValueAndReadOnlyStatusOfTrustedID( String field_name, String expected_value,
		String expected_status )
	{
		Map<String, String> map = this.getFieldValueAndReadOnlyStatusOfSettingsTab(field_name);
		assertEquals(map.get("ACTUAL_VALUE"), expected_value);
		VertexLogger.log(String.format("Expected value: %s matched with Actual value : %s", expected_value,
			map.get("ACTUAL_VALUE")));
		assertEquals(map.get("ACTUAL_STATUS"), expected_status);
		VertexLogger.log(String.format("Expected status: %s  matched with Actual status : %s", expected_status,
			map.get("ACTUAL_STATUS")));
	}

	/**
	 * This method is used to retrieve and validate the TAXCALCENDPOINTURL field on
	 * Settings page
	 */
	public void validateValueAndReadOnlyStatusOfCALCENDPOINTURL( String field_name, String expected_value,
		String expected_status )
	{
		Map<String, String> map = this.getFieldValueAndReadOnlyStatusOfSettingsTab(field_name);
		assertEquals(map.get("ACTUAL_VALUE"), expected_value);
		VertexLogger.log(String.format("Expected value : %s matched with Actual value : %s", expected_value,
			map.get("ACTUAL_VALUE")));
		assertEquals(map.get("ACTUAL_STATUS"), expected_status);
		VertexLogger.log(String.format("Expected status : %s matched with Actual status : %s", expected_status,
			map.get("ACTUAL_STATUS")));
	}

	/**
	 * This method is used to retrieve and validate the ADDRESSENDPOINTURL field on
	 * Settings page
	 */
	public void validateValueAndReadOnlyStatusOfADDRESSENDPOINTURL( String field_name, String expected_value,
		String expected_status )
	{
		Map<String, String> map = this.getFieldValueAndReadOnlyStatusOfSettingsTab(field_name);
		assertEquals(map.get("ACTUAL_VALUE"), expected_value);
		VertexLogger.log(String.format("Expected value : %s  matched with Actual value : %s", expected_value,
			map.get("ACTUAL_VALUE")));
		assertEquals(map.get("ACTUAL_STATUS"), expected_status);
		VertexLogger.log(String.format("Expected status: %s  matched with Actual status : %s", expected_status,
			map.get("ACTUAL_STATUS")));
	}
}
