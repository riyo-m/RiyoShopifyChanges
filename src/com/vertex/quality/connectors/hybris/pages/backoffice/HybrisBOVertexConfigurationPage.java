package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * represents the page to set configurations on VertexConfiguration Page
 *
 * @author Nagaraju Gampa
 */
public class HybrisBOVertexConfigurationPage extends HybrisBasePage
{
	public HybrisBOVertexConfigurationPage( WebDriver driver )
	{
		super(driver);
	}

	protected By VERTEX_CONFIG_TABLE_HEADER_PK = By.xpath(".//th[normalize-space(.)='PK']");
	protected By VERTEX_CONFIG_TABLE_HEADER_ENABLE_LOGS = By.xpath(".//th[normalize-space(.)='Enable Messaging Log']");
	protected By VERTEX_CONFIGURATION_ROW = By.className("z-listitem");
	protected By ADMINISTRATION_TAB = By.cssSelector("li[class*='editorarea'][title = 'Administration']");
	protected By CONFIG_CODE = By.xpath("//span[@title='code']/../following-sibling::div/input[@type='text']");
	protected By EMAIL_SEND_CONFIG = By.xpath(
		"//span[@title='configCollectorEmailFrom']/../following-sibling::div/input[@type='text']");
	protected By EMAIL_RECEIVE_CONFIG = By.xpath(
		"//span[@title='configCollectorEmailTo']/../following-sibling::div/input[@type='text']");
	protected By WS_ENDPOINT_ADDRESS = By.xpath(
		"//span[@title='addressVerificationEndpoint']/../following-sibling::div/input[@type='text']");
	protected By WS_ENDPOINT_TAX = By.xpath(
		"//span[@title='taxCalculationEndpoint']/../following-sibling::div/input[@type='text']");
	protected By CONFIG_SAVE_BUTTON = By.cssSelector(
		"div[class*='toolbar']>div[class*='save-container']>button[type='button']");
	protected By SAVE_CONFIRMATION = By.cssSelector("div[class*='notification-message success']>a[class*='z-a']");
	protected By AUTHENTICATION_TAB = By.xpath(
		"//div[contains(@class,'z-caption-content')]//span[contains(text(),'Authentication')]");
	protected By USERNAME = By.xpath("//span[@title='username']/../following-sibling::div/input[@type='text']");
	protected By PASSWORD = By.cssSelector("input[placeholder='Enter your password'][type='password']");
	protected By TRUSTED_ID = By.cssSelector("input[class*='password'][placeholder = 'Enter your Truested ID']");

	String MSGLOG
		= "//span[@title='messagingLogEnabled']/../following-sibling::div/span//label[contains(text(), '%s')]";

	/***
	 * Method to select VertexConfiguration Row/Heading
	 *
	 * @param rowIndex
	 *            to select Vertex Configuration Heading
	 */
	public void selectVertexConfigurationRow( int rowIndex )
	{
		wait.waitForElementPresent(VERTEX_CONFIG_TABLE_HEADER_PK);
		wait.waitForElementPresent(VERTEX_CONFIG_TABLE_HEADER_ENABLE_LOGS);
		hybrisWaitForPageLoad();
		wait.waitForAllElementsPresent(VERTEX_CONFIGURATION_ROW);
		final List<WebElement> rows = element.getWebElements(VERTEX_CONFIGURATION_ROW);
		final WebElement row = rows.get(rowIndex);
		click.moveToElementAndClick(row);
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(ADMINISTRATION_TAB);
		wait.waitForElementDisplayed(AUTHENTICATION_TAB);
	}

	/***
	 * Method to select Administration Tab
	 */
	public void navigateToAdministrationTab( )
	{
		click.clickElement(ADMINISTRATION_TAB);
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to select Authentication Tab
	 */
	public void selectAuthenticationTab( )
	{
		wait.waitForElementPresent(AUTHENTICATION_TAB);
		click.clickElement(AUTHENTICATION_TAB);
		hybrisWaitForPageLoad();
	}

	/***
	 * Method to set VertexConfigCode
	 * @param configCode - Enter required config code in ConfigCode Textbox
	 */
	public void setVertexConfigCode( String configCode )
	{
		wait.waitForElementDisplayed(CONFIG_CODE);
		text.enterText(CONFIG_CODE, configCode);
	}

	/***
	 * Method to set Send Config Email
	 * @param emailSendConfig - Enter emailSendConfig in SendConfig Textbox
	 */
	public void setEmailToSendConfig( String emailSendConfig )
	{
		wait.waitForElementDisplayed(EMAIL_SEND_CONFIG);
		text.enterText(EMAIL_SEND_CONFIG, emailSendConfig);
	}

	/***
	 * Method to set Receive Config Email
	 * @param emailReceiveConfig - Enter emailReceiveConfig in ReceiveConfig Textbox
	 */
	public void setEmailToReceiveConfig( String emailReceiveConfig )
	{
		wait.waitForElementDisplayed(EMAIL_RECEIVE_CONFIG);
		text.enterText(EMAIL_RECEIVE_CONFIG, emailReceiveConfig);
	}

	/***
	 * Method to set EndPoint for Address Verification
	 * @param addressUrl - Enter addressURL in AddressURL Textbox
	 */
	public void setEndpointAddressVerification( String addressUrl )
	{
		wait.waitForElementDisplayed(WS_ENDPOINT_ADDRESS);
		text.enterText(WS_ENDPOINT_ADDRESS, addressUrl);
	}

	/***
	 * Method to set EndPoint for Tax Calculation
	 * @param taxUrl - Enter taxURL in taxurl textbox
	 */
	public void setEndpointTaxCalculation( String taxUrl )
	{
		wait.waitForElementDisplayed(WS_ENDPOINT_TAX);
		text.enterText(WS_ENDPOINT_TAX, taxUrl);
	}

	/***
	 * Method to set enable/disable messaging log
	 * @param value - Either pass the value as true or false
	 */
	public void setMessagingLog( String value )
	{
		final By MessageLog = By.xpath(String.format(MSGLOG, value));
		click.clickElement(MessageLog);
	}

	/***
	 * Method to save vertex configuration
	 */
	public void saveVertexConfiguration( )
	{
		if ( isSaveButtonEnabled() )
		{
			click.clickElement(CONFIG_SAVE_BUTTON);
			wait.waitForElementDisplayed(SAVE_CONFIRMATION);
			this.validatevertexConfigConfirmationMsg();
			new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SAVE_CONFIRMATION));
		}
		else
		{
			VertexLogger.log("No changes made, Save button is disabled");
		}
	}

	/***
	 * Method to check whether Save button enabled or disabled
	 * @return
	 *        True if SaveButton Enabled
	 * 		False if SaveButton Disabled 
	 */
	public boolean isSaveButtonEnabled( )
	{
		boolean elemState = true;
		String disabledState = null;
		disabledState = attribute.getElementAttribute(CONFIG_SAVE_BUTTON, "disabled");
		if ( disabledState == null )
		{
			elemState = true;
		}
		else if ( disabledState.equalsIgnoreCase("true") )
		{
			elemState = false;
		}
		return elemState;
	}

	/***
	 * Validate vertex confirmation after saving the Config Changes
	 */
	public void validatevertexConfigConfirmationMsg( )
	{
		final String ExpVertexConfigConfirmMsg = "Saved items: VertexConfiguration[8796093071487]";
		final String ActualVertexConfigConfirmMsg = attribute.getElementAttribute(SAVE_CONFIRMATION, "text");
		String elementMsg = String.format("ActualVertexConfigConfirmMsg is: %s", ActualVertexConfigConfirmMsg);
		VertexLogger.log(elementMsg);
		if ( ExpVertexConfigConfirmMsg.contains(ActualVertexConfigConfirmMsg) )
		{
			VertexLogger.log("Expected Message is matching with Actual Message");
		}
		else
		{
			elementMsg = String.format("Expected Message %s is Not matching with Actual Message %s",
				ExpVertexConfigConfirmMsg, ActualVertexConfigConfirmMsg);
			VertexLogger.log(elementMsg);
		}
		wait.waitForElementDisplayed(SAVE_CONFIRMATION);
	}

	/***
	 * Method to set Vertex Config Username
	 * @param username - enter hybris configuration username in username field
	 */
	public void enterVertexConfigurationUsername( String username )
	{
		wait.waitForElementDisplayed(USERNAME);
		text.enterText(USERNAME, username);
		text.pressTab(USERNAME);
	}

	/***
	 * Method to set Vertex Config Password
	 * @param password - enter hybris configuration password in password field
	 */
	public void enterVertexConfigurationPassword( String password )
	{
		wait.waitForElementDisplayed(PASSWORD);
		text.enterText(PASSWORD, password);
		text.pressTab(PASSWORD);
	}

	/***
	 * Method to Clear Vertex Config Username
	 */
	public void clearVertexConfigurationUsername( )
	{
		wait.waitForElementDisplayed(USERNAME);
		text.clearText(USERNAME);
		text.pressTab(USERNAME);
	}

	/***
	 * Method to Clear Vertex Config Password
	 */
	public void clearVertexConfigurationPassword( )
	{
		wait.waitForElementDisplayed(PASSWORD);
		text.clearText(PASSWORD);
		text.pressTab(PASSWORD);
	}

	/***
	 * Method to Clear Vertex Config TrustedID
	 */
	public void clearVertexConfigurationTrustedID( )
	{
		wait.waitForElementDisplayed(TRUSTED_ID);
		text.clearText(TRUSTED_ID);
		text.pressTab(TRUSTED_ID);
	}

	/***
	 * Method to set Vertex Config TrustedID
	 * @param trusted_id - enter hybris configuration trustedid in trustedID field
	 */
	public void setVertexConfigurationTrustedId( String trusted_id )
	{
		wait.waitForElementDisplayed(TRUSTED_ID);
		text.enterText(TRUSTED_ID, trusted_id);
		click.clickElement(USERNAME);
	}
}
