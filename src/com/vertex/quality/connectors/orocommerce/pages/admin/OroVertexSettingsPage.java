package com.vertex.quality.connectors.orocommerce.pages.admin;

import com.vertex.quality.connectors.orocommerce.pages.base.OroAdminBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the vertex settings page in the system configurations
 * contains all the methods necessary to interact with it
 *
 * @author alewis
 */
public class OroVertexSettingsPage extends OroAdminBasePage
{
	public OroVertexSettingsPage( WebDriver driver )
	{
		super(driver);
	}

	By pageFieldsContLoc = By.id("configuration-options-block");
	By boxClass = By.className("control-group-oro_config_form_field_type");
	By requiredClass = By.className("required");
	By controlSubgroupClass = By.className("control-subgroup");
	By passwordID = By.className("vertex_settings_vertex_o_series___password_value");
	By healthCheckClass = By.className("check-connection-messages");
	By controlsClass = By.className("controls");
	By urlFieldID = By.xpath("//input[@name='vertex_settings[vertex_o_series___api_url][value]']");
	By errorMessage = By.xpath("//div[@class='flash-messages-holder']");
	By closeErrorMessage = By.xpath("//div[@class='flash-messages-holder']//button[@aria-label='Close']");
	protected By checkBoxBarLoc = By.cssSelector("input[type='checkbox']");
	protected By searchBarLoc = By.cssSelector("input[type='search']");
	protected By healthCheckMessage = By.xpath("//div[contains(text(),'Connection established successfully')]");
	By urlFieldClass = By.className("control-group-oro_config_form_field_type");
	By urlFieldXpath = By.xpath("//input[contains(@name,'api_url')][@type='text']");
	By saveSettingsClass = By.className("check-connection-messages");
	By saveSettingButton = By.xpath(".//button[normalize-space(.)='Save settings']");
	By apiURL = By.xpath("((.//div[normalize-space(.)='API URL*'])[2]//following-sibling::div//input)[1]");
	By trustedID = By.xpath("(.//label[text()='Trusted ID']/parent::div/following-sibling::div//input)[1]");
	By healthCheckNewSettingButton = By.xpath(".//button[normalize-space(.)='Health Check (New Settings)']");
	By healthCheckSavedSettingButton = By.xpath(".//button[normalize-space(.)='Health Check (Saved Settings)']");
	By healthCheckMSG = By.xpath(".//div[@class='alert alert-error alert-dismissible alert-icon fade show top-messages']//div");
	By versionValue = By.xpath(".//div[normalize-space(.)='Version']/following-sibling::div");

	/**
	 * enter url while login into admin.
	 *
	 * @param url for admin login.
	 */
	public void enterURL( String url )
	{
		WebElement urlField = wait.waitForElementDisplayed(urlFieldXpath);
		text.enterText(urlField, url);
	}

	/**
	 * Checks the successful message in admin page while health check.
	 *
	 * @return health check message field true or false
	 */
	public boolean checkHealthCheckMessage( )
	{
		WebElement healthStatusMessageField = wait.waitForElementDisplayed(healthCheckMessage);
		boolean checkHealthCheckMessageField = healthStatusMessageField.isDisplayed();
		return checkHealthCheckMessageField;
	}

	/**
	 * change health check url for admin.
	 *
	 * @param url for health check.
	 */
	public void changeURL( String url )
	{

		//        WebElement urlField = wait.waitForElementPresent(urlFieldID);
		//        text.enterText(urlField,url);

		List<WebElement> healthCheckButtons = wait.waitForAllElementsDisplayed(healthCheckClass);

		for ( WebElement healthCheckButton : healthCheckButtons )
		{

			String buttonText = text.getElementText(healthCheckButton);

			if ( buttonText.equals("Health Check (Saved Settings)") )
			{
				click.clickElementCarefully(healthCheckButton);
			}
		}

		//		WebElement passwordField = wait.waitForElementDisplayed(passwordID);
		//		text.enterText(passwordField,"adminorocom!");
		//
		//		WebElement block = wait.waitForElementDisplayed(boxClass);
		//		WebElement box = wait.waitForElementDisplayed(requiredClass,block);
		//
		//		String fieldString = text.getElementText(box);
		//
		//		if (fieldString.equals("API URL")) {
		//			WebElement textField = wait.waitForElementDisplayed(controlSubgroupClass,block);
		//			textField.
		//			textField.getText(textField);
		//		}
	}

	/**
	 * change fake password for admin.
	 */
	public void changePassword( )
	{
		WebElement passwordField = wait.waitForElementDisplayed(passwordID);
		text.enterText(passwordField, "fakePassword");
	}

	/**
	 * Change the health check url to Invalid url in vertex settings page.
	 *
	 * @url change health check url.
	 */
	public void changeHealthCheckURL( String url )
	{

		WebElement urlField = wait.waitForElementPresent(urlFieldID);
		text.enterText(urlField, url);

		List<WebElement> healthCheckButtons = wait.waitForAllElementsDisplayed(healthCheckClass);

		for ( WebElement healthCheckButton : healthCheckButtons )
		{

			String buttonText = text.getElementText(healthCheckButton);

			if ( buttonText.equals("Health Check (Saved Settings)") )
			{
				click.clickElementCarefully(healthCheckButton);
			}
		}
	}

	/**
	 * get version number for admin.
	 *
	 * @return versionNumber of connector.
	 */
	public String getVersionNumber( )
	{
		List<WebElement> controls = wait.waitForAllElementsPresent(controlsClass);
		WebElement versionField = controls.get(0);
		String versionNumber = text.getElementText(versionField);

		return versionNumber;
	}

	/**
	 * check if checkbox present on admin panel.
	 *
	 * @return isCheckBoxPresent boolean value.
	 */
	public boolean checkBoxPresent( )
	{
		Boolean isCheckBoxPresent = false;

		List<WebElement> checkBoxes = wait.waitForAllElementsPresent(checkBoxBarLoc);

		if ( checkBoxes.size() > 1 )
		{
			isCheckBoxPresent = true;
		}

		return isCheckBoxPresent;
	}

	/**
	 * click save new setting after changing health check url admin panel.
	 */
	public void clickSaveNewSettings( )
	{
		List<WebElement> saveSettings = wait.waitForAllElementsPresent(saveSettingsClass);

		for ( WebElement saveSetting : saveSettings )
		{
			String healthCheckText = text.getElementText(saveSetting);

			if ( healthCheckText.contains("Health Check (New Settings)") )
			{
				click.clickElementCarefully(saveSetting);
			}
		}
	}

	//	public String getMessage() {
	//
	//	}

	/**
	 * Checks the error message in vertex settings page is displaying or not
	 *
	 * @return checkErrorField true or false
	 */
	public boolean checkErrorMessageInVertexSettings( )
	{
		WebElement errorMessageField = wait.waitForElementDisplayed(errorMessage);
		boolean checkErrorField = errorMessageField.isDisplayed();
		return checkErrorField;
	}

	/**
	 * close the error message in vertex settings page
	 */
	public void closeErrorMessageInVertexSettings( )
	{
		WebElement errorMessageField = wait.waitForElementPresent(closeErrorMessage);
		click.clickElement(errorMessageField);
	}

	/**
	 * Helps to enter values into API URL field for Oro Health check
	 *
	 * @param url value of URL which is to be set
	 */
	public void enterHealthCheckURL(String url) {
		waitForPageLoad();
		text.enterText(wait.waitForElementPresent(apiURL), url);
	}

	/**
	 * Helps to enter values into Trusted ID field for Oro Commerce Vertex's Setting
	 *
	 * @param id value of Trusted ID which is to be set
	 */
	public void enterTrustedID(String id) {
		waitForPageLoad();
		text.enterText(wait.waitForElementPresent(trustedID), id);
	}

	/**
	 * Saves Vertex settings
	 */
	public void saveSettings() {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementPresent(saveSettingButton));
	}

	/**
	 * Performs health-check with Saved Setting or New Setting based on params
	 *
	 * @param url            value of URL which is to be set
	 * @param isSavedSetting pass true to perform health-check with saved setting else pass false to perform health-check with new setting
	 */
	public void performHealthCheckWithSettings(String url, boolean isSavedSetting) {
		enterHealthCheckURL(url);
		if (isSavedSetting) {
			click.moveToElementAndClick(wait.waitForElementPresent(healthCheckSavedSettingButton));
		} else {
			click.moveToElementAndClick(wait.waitForElementPresent(healthCheckNewSettingButton));
		}
	}

	/**
	 * Performs health-check with Saved Setting or New Setting based on params
	 *
	 * @param url            value of URL which is to be set
	 * @param id             value of URL which is to be set
	 * @param isSavedSetting pass true to perform health-check with saved setting else pass false to perform health-check with new setting
	 */
	public void performHealthCheckWithSettings(String url, String id, boolean isSavedSetting) {
		enterHealthCheckURL(url);
		enterTrustedID(id);
		if (isSavedSetting) {
			click.moveToElementAndClick(wait.waitForElementPresent(healthCheckSavedSettingButton));
		} else {
			click.moveToElementAndClick(wait.waitForElementPresent(healthCheckNewSettingButton));
		}
	}

	/**
	 * Gets health-check success or un success message from UI.
	 *
	 * @return health-check message
	 */
	public String getHealthCheckMessageFromUI() {
		waitForPageLoad();
		return text.getElementText(wait.waitForElementPresent(healthCheckMSG));
	}

	/**
	 * Gets connector version from the UI
	 *
	 * @return configured version's value
	 */
	public String getVersionFromUI() {
		waitForPageLoad();
		return text.getElementText(wait.waitForElementPresent(versionValue));
	}
}
