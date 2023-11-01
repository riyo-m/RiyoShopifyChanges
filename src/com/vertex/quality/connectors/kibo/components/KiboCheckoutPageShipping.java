package com.vertex.quality.connectors.kibo.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.kibo.enums.KiboData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * this class represents the shipping section in the checkout page for the Maxine live front store
 * contains all the methods necessary to interact with the different fields of the section to run
 * test cases
 *
 * @author osabha
 */
public class KiboCheckoutPageShipping extends VertexComponent
{
	public KiboCheckoutPageShipping( WebDriver driver, VertexPage parent )
	{
		super(driver, parent);
	}

	protected By firstNameFieldLoc = By.id("firstname");
	protected By lastNameFieldLoc = By.id("lastname");
	protected By addressFieldLoc = By.id("address-line-1");
	protected By cityFieldContainerLoc = By.id("city");
	protected By stateFieldLoc = By.cssSelector("select[id='stateOrProvince']");
	protected By zipLoc = By.id("postal-code");
	protected By phoneNumberFieldLoc = By.id("phonenumber");
	protected By nextButtonContainerLoc = By.id("step-shipping-address");
	protected By nextButtonLoc = By.className("mz-button");
	protected By changeButtonLoc = By.linkText("change");
	protected By shippingAddressDiv = By.id("step-shipping-address");
	protected By addNewAddressButton = By.xpath(".//button[text()='Add new address']");
	protected By changeShippingAddressButton = By.xpath(".//div[@id='step-shipping-address']//button[text()='Change']");
	protected By firstNameBox = By.xpath(".//input[@name='firstname']");
	protected By lastNameBox = By.xpath(".//input[@name='lastname']");
	protected By addressLine1Box = By.xpath(".//input[@name='address-line-1']");
	protected By addressLine2Box = By.xpath(".//input[@name='address-line-2']");
	protected By countryDropdown = By.xpath(".//div[normalize-space(.)='Country *:']/following-sibling::div/select");
	protected By cityBox = By.xpath(".//input[@name='city']");
	protected By stateOrProvinceDropdown = By.xpath(".//div[normalize-space(.)='State *:']/following-sibling::div/select");
	protected By stateOrProvinceBox = By.xpath(".//input[@name='stateOrProvince']");
	protected By postalCodeBox = By.xpath(".//input[@name='postal-code']");
	protected By phoneBox = By.xpath(".//input[@name='shippingphone']");
	protected By addressTypeDropdown = By.xpath(".//div[normalize-space(.)='Address Type *:']/following-sibling::div/select");
	protected By shippingNextButton = By.xpath("(.//button[text()='Next'])[1]");
	String firstName = "Test";
	String lastName = "Automation";
	String streetAddress = "1575 Space Center Drive";
	String city = "Colorado Springs";
	String zip = "80915";
	String phone = "2676704348";

	/**
	 * types in the user name into the firstName field
	 * in the shipping section of the checkout page
	 */
	public void enterFirstName( )
	{
		WebElement firstNameField = wait.waitForElementEnabled(firstNameFieldLoc);
		firstNameField.sendKeys(firstName);
	}

	/**
	 * types in the user lastName  into the lastName field
	 * in the shipping section of the checkout page
	 */
	public void enterLastName( )
	{
		WebElement lastNameField = wait.waitForElementPresent(lastNameFieldLoc);
		lastNameField.clear();
		lastNameField.sendKeys(lastName);
	}

	/**
	 * Types in the street address of the user in the street address field
	 * in the shipping section of the checkout page
	 */
	public void enterAddress( )
	{
		WebElement addressField = wait.waitForElementPresent(addressFieldLoc);
		text.enterText(addressField, streetAddress);
	}

	/**
	 * Types in the city in the city field
	 * in the shipping section of the checkout page
	 */
	public void enterCity( )
	{
		WebElement cityField = wait.waitForElementPresent(cityFieldContainerLoc);
		text.enterText(cityField, city);
	}

	/**
	 * Selects the State from the states list
	 * in the shipping section of the checkout page
	 */
	public void selectState( )
	{
		WebElement fieldContLoc = wait.waitForElementEnabled(stateFieldLoc);
		String coloradoState = "CO";
		click.clickElement(fieldContLoc);

		dropdown.selectDropdownByValue(fieldContLoc, coloradoState);
	}

	/**
	 * Types in the zip code in the zip code field
	 * in the shipping section of the checkout page
	 */
	public void enterZip( )
	{
		WebElement zipField = wait.waitForElementPresent(zipLoc);
		text.enterText(zipField, zip);
	}

	/**
	 * Types in the user phone number in the phone number field
	 * in the shipping section of the checkout page
	 */
	public void enterPhoneNumber( )
	{
		WebElement phoneNumberField = wait.waitForElementPresent(phoneNumberFieldLoc);
		text.enterText(phoneNumberField, phone);
	}

	/**
	 * gets the element of the Next Button
	 * in the shipping section of the checkout page
	 * the button takes the user from shipping section to shipping method section
	 *
	 * @return nextButton element to click
	 */
	protected WebElement getNextButton( )
	{
		WebElement nextButtonContainer = wait.waitForElementPresent(nextButtonContainerLoc);
		WebElement nextButton = wait.waitForElementPresent(nextButtonLoc, nextButtonContainer);

		return nextButton;
	}

	/**
	 * Clicks on the nextButton element to take user to the shipping method section
	 */
	public void clickNextToShippingMethod( )
	{
		WebElement nextButton = getNextButton();
		nextButton.click();
		WebElement changeButtonContainer = wait.waitForElementPresent(nextButtonContainerLoc);
		WebElement changeButton = wait.waitForElementPresent(changeButtonLoc, changeButtonContainer);
		wait.waitForElementDisplayed(changeButton);
		waitForPageLoad();
		VertexLogger.log("clicked on next to shipping method button");
	}

	/**
	 * Clicks on Add New Address button.
	 */
	public void clickChangeAddressAndAddNewAddress() {
		waitForPageLoad();
		wait.waitForElementPresent(shippingAddressDiv);
		if (element.isElementDisplayed(changeShippingAddressButton)) {
			click.moveToElementAndClick(changeShippingAddressButton);
		}
		click.moveToElementAndClick(wait.waitForElementPresent(addNewAddressButton));
		waitForPageLoad();
		wait.waitForElementPresent(firstNameBox);
		wait.waitForElementPresent(lastNameBox);
		wait.waitForElementPresent(addressLine1Box);
		wait.waitForElementPresent(addressLine2Box);
		wait.waitForElementPresent(countryDropdown);
		wait.waitForElementPresent(cityBox);
		wait.waitForElementPresent(stateOrProvinceBox);
		wait.waitForElementPresent(postalCodeBox);
		wait.waitForElementPresent(phoneBox);
		wait.waitForElementPresent(addressTypeDropdown);
	}

	/**
	 * Enter shipping address
	 * All address fields are mandatory.
	 * Kindly follow the sequence to set the address
	 * Here, No need to pass firstname, lastname, phone & address type those are passed directly.
	 * Sequence: Address Line1, Country Name, City, State, Postal Code.
	 * example1: kiboCheckoutPageShipping.enterShippingAddress("1270 York Road", "United States", "Gettysburg", "Pennsylvania", "17325");
	 *
	 * @param address whole address which is to be set
	 */
	public void enterShippingAddress(String... address) {
		if (address.length != 5) {
			VertexLogger.log("Error / Exception occurred Kindly check the logs for more details.");
			Assert.fail("All address parameter are mandatory, Kindly check JavaDoc to set parameters");
		}
		waitForPageLoad();
		text.enterText(wait.waitForElementPresent(firstNameBox), KiboData.CUSTOMER_FIRSTNAME.value);
		text.enterText(wait.waitForElementPresent(lastNameBox), KiboData.CUSTOMER_LASTNAME.value);
		text.enterText(wait.waitForElementPresent(addressLine1Box), address[0]);
		dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(countryDropdown), address[1]);
		WebElement city = wait.waitForElementPresent(cityBox);
		scroll.scrollElementIntoView(city);
		click.performDoubleClick(city);
		text.enterTextIgnoreExceptionsAndRetry(cityBox, address[2]);
		if (element.isElementDisplayed(stateOrProvinceDropdown)) {
			dropdown.selectDropdownByDisplayName(stateOrProvinceDropdown, address[3]);
		} else {
			text.enterText(wait.waitForElementPresent(stateOrProvinceBox), address[3]);
		}
		text.enterText(wait.waitForElementPresent(postalCodeBox), address[4]);
		text.enterText(wait.waitForElementPresent(phoneBox), KiboData.CUSTOMER_PHONE.value);
		dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(addressTypeDropdown), KiboData.CUSTOMER_ADDRESS_TYPE_RESIDENTIAL.value);
	}

	/**
	 * Clicks next button after adding shipping address
	 */
	public void goNextFromShipping() {
		waitForPageLoad();
		click.moveToElementAndClick(wait.waitForElementPresent(shippingNextButton));
		new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(shippingNextButton));
		waitForPageLoad();
	}
}

