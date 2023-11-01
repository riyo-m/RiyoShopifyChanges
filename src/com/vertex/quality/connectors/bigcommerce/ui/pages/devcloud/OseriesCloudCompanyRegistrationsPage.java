package com.vertex.quality.connectors.bigcommerce.ui.pages.devcloud;

import com.vertex.quality.connectors.bigcommerce.common.utils.BigCommerceAPITestUtilities;
import com.vertex.quality.connectors.bigcommerce.ui.enums.BigCommerceAdminData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.InputStream;
import java.util.Locale;

/**
 * this class represents the state and territories of USA to activate on vertex cloud.
 *
 * @author rohit.mogane
 */
public class OseriesCloudCompanyRegistrationsPage extends OseriesCloudBasePage
{
	public OseriesCloudCompanyRegistrationsPage( WebDriver driver )
	{
		super(driver);
	}

	protected final By readyToSaveButton = By.xpath("//a[@role='button']");
	protected final By saveConfigurationButton = By.xpath("//a[@class='btn btn-primary pull-right ng-scope']");
	protected final By confirmConfiguration = By.xpath("//button[@ng-click='ok()']");
	protected final String countryTab = "//*[@id='<<country>>-switch']//parent::label";
	protected final By vatInput = By.xpath("//*[@id='vat-{$index}']");
	protected final By saveButton = By.xpath("//*[@id='vat-{$index}']//following::button[1]");
	protected final String stateRadio = "//*[@for='<<country>>-checkbox']";
	protected final By editStateLink = By.xpath("(//a[contains(text(),'Edit')])[4]");
	protected final By quickSearchInput = By.xpath("//*[@id='quick-search']");
	protected final By goLiveButton = By.xpath("//*[contains(text(),'Go Live')]");
	protected final By yesGoLiveButton = By.xpath("//*[contains(text(),'Yes, Go Live')]");
	protected final By checkStatusButton = By.xpath("//a[contains(text(),'Check Status')]");

	/**
	 * this method click on the ready to save button to save the activated states.
	 */
	public void clickReadyToSaveButton( )
	{
		WebElement readyToSaveButtonField = wait.waitForElementEnabled(readyToSaveButton);
		click.clickElement(readyToSaveButtonField);
	}

	/**
	 * this method click on save configuration to save the configuration for new company and activated states
	 */
	public void clickSaveConfigurationButton( )
	{
		WebElement saveConfigurationButtonField = wait.waitForElementEnabled(saveConfigurationButton);
		click.clickElement(saveConfigurationButtonField);
	}

	/**
	 * this method confirm the saved configuration.
	 */
	public void clickConfirmConfiguration( )
	{
		WebElement confirmConfigurationField = wait.waitForElementEnabled(confirmConfiguration);
		click.clickElement(confirmConfigurationField);
	}

	/**
	 * this method will get registration details for company from JSON data file
	 *
	 * @param country return JSON based on country value
	 *
	 * @return stateDetails JSONArray based on country provided in parameter
	 */
	public JSONArray getRegistrations( String country )
	{
		InputStream companyRegistrationInput = BigCommerceAPITestUtilities.class.getResourceAsStream(
			BigCommerceAdminData.COMPANY_REGISTRATIONS.data);
		JSONTokener inputToken = new JSONTokener(companyRegistrationInput);
		JSONObject companyRegObj = new JSONObject(inputToken);
		JSONObject usStates = (JSONObject) companyRegObj.get(country);
		JSONArray stateDetails = (JSONArray) usStates.get(BigCommerceAdminData.STATES.data);
		return stateDetails;
	}

	/**
	 * this method is to activate US and Canada states
	 *
	 * @param country String US or CA to activate states
	 */
	public void activateStates( String country )
	{
		JSONArray stateDetails = getRegistrations(country);

		for ( int i = 0 ; i < stateDetails.length() ; i++ )
		{
			JSONObject states = (JSONObject) stateDetails.get(i);
			String stateXPath = stateRadio.replace("<<country>>", states
				.get("name")
				.toString()
				.toLowerCase(Locale.ROOT)
				.replace(' ', '-'));
			final By stateBy = By.xpath(stateXPath);
			WebElement stateField = wait.waitForElementDisplayed(stateBy);
			click.clickElement(stateField);
		}
	}

	/**
	 * this method is to activate International states
	 *
	 * @param country String IT to activate states
	 */
	public void activateITStates( String country )
	{
		JSONArray stateDetails = getRegistrations(country);

		for ( int i = 0 ; i < stateDetails.length() ; i++ )
		{
			JSONObject states = (JSONObject) stateDetails.get(i);
			String searchState = states
				.get("name")
				.toString();
			WebElement quickSearchField = wait.waitForElementDisplayed(quickSearchInput);
			text.clearText(quickSearchField);
			text.clickElementAndEnterText(quickSearchField, searchState);
			String stateXPath = stateRadio.replace("<<country>>", states
				.get("name")
				.toString()
				.toLowerCase(Locale.ROOT)
				.replace(' ', '-'));
			final By stateBy = By.xpath(stateXPath);
			WebElement stateField = wait.waitForElementDisplayed(stateBy);
			click.clickElement(stateField);
			String vatNumber = states
				.get("vat")
				.toString();
			WebElement vatInputElement = wait.waitForElementDisplayed(vatInput);
			text.clickElementAndEnterText(vatInputElement, vatNumber);
			WebElement saveButtonElement = wait.waitForElementDisplayed(saveButton);
			click.clickElement(saveButtonElement);
			wait.waitForElementDisplayed(editStateLink);
		}
	}

	/**
	 * this method is to switch tab between US, Canada and International
	 *
	 * @param country String US, CA, IT
	 */
	public void switchCountryTab( String country )
	{
		WebElement countryTabField = wait.waitForElementEnabled(By.xpath(countryTab.replace("<<country>>", country)));
		click.clickElement(countryTabField);
	}

	/**
	 * this method is to go live company after saving configuration
	 */
	public void goLive( )
	{
		jsWaiter.sleep(60000);
		refreshPage();
		WebElement goLiveField = wait.waitForElementEnabled(goLiveButton);
		click.clickElement(goLiveField);
		WebElement yesGoLiveField = wait.waitForElementEnabled(yesGoLiveButton);
		click.clickElement(yesGoLiveField);
		WebElement checkStatusField = wait.waitForElementEnabled(checkStatusButton);
		click.clickElement(checkStatusField);
	}
}
