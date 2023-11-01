package com.vertex.quality.connectors.hybris.pages.backoffice;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;

public class HybrisBODefaultDeliveryOriginPage extends HybrisBasePage
{
	public HybrisBODefaultDeliveryOriginPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ADDRESS = By.xpath(
		"//div[span[@title='address']]//following-sibling::div//div[@class='z-listcell-content']//span");
	protected By STREETNAME = By.xpath("//div[span[@title='streetname']]//following-sibling::div/input[@type='text']");
	protected By STREETNUMBER = By.xpath(
		"//div[span[@title='streetnumber']]//following-sibling::div/input[@type='text']");
	protected By POSTAL_CODE = By.xpath("//div[span[@title='postalcode']]//following-sibling::div/input[@type='text']");
	protected By TOWN = By.xpath("//div[span[@title='town']]//following-sibling::div/input[@type='text']");
	protected By COUNTRY = By.xpath(
		"//div[span[@title='country']]/following-sibling::div//tr[@class='z-listitem']//span");
	protected By COUNTRY_ISO_CODE = By.xpath(
		"//div[span[@title='isocode']]//following-sibling::div/input[@type='text']");
	protected By COUNTRY_NAME = By.xpath(
		"//td[div[span[@title='isocode']]]/following-sibling::td[//*[@title='name']]//input[@type='text']");
	protected By COUNTRY_SAVE = By.xpath(
		"(//div[contains(text(),'Edit item')]/parent::div/parent::div/following-sibling::div//button[contains(text(),'Save')])[3]");
	protected By ADDRESS_SAVE = By.xpath("(//div[contains(text(),'Edit item')]/parent::div/parent::div/following-sibling::div//button[contains(text(),'Save')])[2]");
	protected By SAVE_CONFIRMATION = By.cssSelector("div[class*='notification-message success']");
	protected By CLOSE_POP_UP = By.cssSelector("[class*='z-window-close']");

	/**
	 * Method to Double Click on Origin Address
	 */
	public void doubleClickOnOriginAddress( )
	{
		hybrisWaitForPageLoad();
		WebElement element = wait.waitForElementPresent(ADDRESS);
		click.performDoubleClick(element);
		hybrisWaitForPageLoad();
	}

	/***
	 * method to set streetName for origin Address
	 *
	 * @param streetName - Set required StreetName
	 */
	public void setStreetName( String streetName )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(STREETNAME);
		text.enterText(STREETNAME, streetName);
	}

	/***
	 * method to set streetNumber for origin Address
	 *
	 * @param streetNumber - Set required StreetNumber
	 */
	public void setStreetNumber( String streetNumber )
	{
		wait.waitForElementDisplayed(STREETNUMBER);
		text.enterText(STREETNUMBER, streetNumber);
	}

	/***
	 * method to set postalCode for origin Address
	 *
	 * @param postalCode - Set required postalCode
	 */
	public void setPostalCode( String postalCode )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(POSTAL_CODE);
		text.enterText(POSTAL_CODE, postalCode);
	}

	/***
	 * method to set town for origin Address
	 *
	 * @param town - Set required Town
	 */
	public void setTown( String town )
	{
		wait.waitForElementDisplayed(TOWN);
		text.enterText(TOWN, town);
	}

	/***
	 * method to set Country for origin Address
	 *
	 * @param country - Set country
	 */
	public void setCountry( String country )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(COUNTRY);
		String[] countrySplit = country.split("\\[");
		String countryName = countrySplit[0].toLowerCase(Locale.ROOT);
		String countryCode = countrySplit[1].toLowerCase(Locale.ROOT);
		countryCode = countryCode.replace("]", "");
		WebElement element = driver.findElement(COUNTRY);
		click.performDoubleClick(element);
		wait.waitForElementDisplayed(COUNTRY_ISO_CODE);
		text.enterText(COUNTRY_ISO_CODE, countryCode);
		wait.waitForElementDisplayed(COUNTRY_NAME);
		text.enterText(COUNTRY_NAME, countryName);
	}

	/***
	 * Method to Save OriginAddressCountry Popup
	 */
	public void saveOriginAddressCountry( )
	{
		hybrisWaitForPageLoad();
		if ( isSaveButtonEnabled() )
		{
			click.clickElement(COUNTRY_SAVE);
			wait.waitForElementDisplayed(SAVE_CONFIRMATION);
			new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SAVE_CONFIRMATION));
		}
		else
		{
			VertexLogger.log("No changes made, Save button is disabled");
		}
	}

	/**
	 * Method to Save OriginAddress Popup
	 */
	public void saveOriginAddress() {
		hybrisWaitForPageLoad();
		if (isSaveButtonEnabled(ADDRESS_SAVE)) {
			click.clickElement(ADDRESS_SAVE);
			wait.waitForElementDisplayed(SAVE_CONFIRMATION);
			new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(SAVE_CONFIRMATION));
		} else {
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
		disabledState = attribute.getElementAttribute(COUNTRY_SAVE, "disabled");
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

	/**
	 * Method to check whether Save button enabled or disabled
	 *
	 * @param saveLocator There are multiple similar save buttons so pass the locator of particular save button to check whether it's enabled or not?
	 * @return true or false based on condition
	 */
	public boolean isSaveButtonEnabled(By saveLocator) {
		boolean elemState = true;
		String disabledState = null;
		disabledState = attribute.getElementAttribute(saveLocator, "disabled");
		if (disabledState == null) {
			elemState = true;
		} else if ( disabledState.equalsIgnoreCase("true")) {
			elemState = false;
		}
		return elemState;
	}

	/***
	 * Method to Close Address Popup
	 */
	public void closeAddressPopUp( )
	{
		hybrisWaitForPageLoad();
		wait.waitForElementDisplayed(CLOSE_POP_UP);
		click.clickElement(CLOSE_POP_UP);
	}

	/***
	 * Method to set New Origin Address
	 *
	 * @param streetName - Enter StreetName of Origin Address
	 * @param streetNumber - Enter StreetNumber of Origin Address
	 * @param postalCode - Enter Postal Code of Origin Address
	 * @param town - Enter Town of Origin Address
	 * @param country - Enter Country of Origin Address

	 */
	public void setOriginAddress( String streetName, String streetNumber, String postalCode, String town,
		String country )
	{
		hybrisWaitForPageLoad();
		setStreetName(streetName);
		setStreetNumber(streetNumber);
		setPostalCode(postalCode);
		setTown(town);
		setCountry(country);
		saveOriginAddressCountry();
		saveOriginAddress();
		closeAddressPopUp();
	}
}
