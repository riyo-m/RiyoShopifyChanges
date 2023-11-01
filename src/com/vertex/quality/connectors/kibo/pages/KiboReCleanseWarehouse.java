package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the warehouse page created to
 * perform physical origin address cleansing test cases on
 * contains all the methods necessary to interact with the page
 *
 * @author osabha
 */
public class KiboReCleanseWarehouse extends VertexPage
{
	public KiboMainMenuNavPanel navPanel;

	public KiboReCleanseWarehouse( WebDriver driver )
	{
		super(driver);
		navPanel = new KiboMainMenuNavPanel(driver, this);
	}

	protected By validateAddressBoxLoc = By.className("mozu-c-container--contact-form-address");
	protected By validateAddressButtonConLoc = By.className("mozu-c-modal__toolbar");
	protected By addressBoxConLoc = By.className("mozu-c-minor-headline");
	protected By errorMessageLoc = By.className("message");
	protected By validateAddressButtonLoc = By.tagName("button");
	protected By cityFieldLoc = By.cssSelector("input[name='cityOrTown']");
	protected By stateFieldLoc = By.cssSelector("input[name='stateOrProvince']");
	protected By zipCodeFieldLoc = By.cssSelector("input[name='postalOrZipCode']");
	protected By streetAddressLoc = By.cssSelector("input[name='address1']");
	protected By saveButtonLoc = By.className("mozu-c-btn__cross--primary");
	protected By confirmButtonLoc = By.className("mozu-c-btn__content--primary");
	protected By mainMenuButtonLoc = By.className("mozu-c-nav__hamburger");
	protected By loadMaskLoc = By.className("mozu-c-modal__screen--medium");
	protected By addressFieldLoc = By.cssSelector(
		".mozu-c-container.mozu-c-displayfield.mozu-is-required.mozu-qa-address");
	protected By closeAddressCleansingMSG = By.className("close-icon");
	protected By validatedAddress = By.xpath(".//h3[text()='validated']/following-sibling::div");
	protected By validatedAddressCancel = By.xpath("(.//button[normalize-space(.)='Cancel'])[3]");

	/**
	 * gets the address field for the shipping warehouse
	 *
	 * @return warehouse address field WebElement
	 */
	protected WebElement getAddressField( )
	{
		WebElement addressField = wait.waitForElementPresent(addressFieldLoc);

		return addressField;
	}

	/**
	 * uses the getter  method to locate the warehouse  address field and then clicks on it to open
	 * up the dialog to edit the address
	 */
	public void clickAddressField( )
	{
		WebElement addressField = getAddressField();
		addressField.click();
	}

	/**
	 * getter method to locate the WebElement of the street address field
	 *
	 * @return street address field WebElement
	 */
	protected WebElement getStreetAddressField( )
	{
		WebElement streetAddressField = wait.waitForElementPresent(streetAddressLoc);

		return streetAddressField;
	}

	/**
	 * uses the getter method to locate the street address field and then types in the street
	 * address
	 */
	public void enterStreetAddress( String streetAddress )
	{
		WebElement streetAddressField = getStreetAddressField();
		text.enterText(streetAddressField, streetAddress);
	}

	/**
	 * getter method to locate the city field
	 *
	 * @return city field WebElement
	 */
	protected WebElement getCityField( )
	{
		WebElement cityField = wait.waitForElementPresent(cityFieldLoc);

		return cityField;
	}

	/**
	 * uses the getter method to locate the city field and then types in the city
	 */
	public void enterCity( String city )
	{
		WebElement cityField = getCityField();
		text.enterText(cityField, city);
	}

	/**
	 * getter method to locate the state field
	 *
	 * @return stat field WebElement
	 */
	protected WebElement getStateField( )
	{
		WebElement stateField = wait.waitForElementPresent(stateFieldLoc);

		return stateField;
	}

	/**
	 * uses the getter method to locate the state field and then types in the state
	 */
	public void enterState( String state )
	{
		WebElement stateField = getStateField();
		text.enterText(stateField, state);
	}

	/**
	 * locates the main menu button WebElement
	 *
	 * @return main menu WebElement
	 */
	protected WebElement getMainMenuButton( )
	{
		WebElement mainMenu = wait.waitForElementPresent(mainMenuButtonLoc);

		return mainMenu;
	}

	/**
	 * uses getter method to locate the main menu button and then clicks it
	 */
	public void clickMainMenu( )
	{
		WebElement mainMenu = getMainMenuButton();
		mainMenu.click();
		waitForPageLoad();
	}

	/**
	 * gets the zip code field WebElement
	 *
	 * @return zip code field WebElement
	 */
	protected WebElement getZipField( )
	{
		WebElement zipField = wait.waitForElementPresent(zipCodeFieldLoc);

		return zipField;
	}

	/**
	 * uses the getter method to locate the zip code field and then types in the zip code
	 */
	public void enterZip( String zip )
	{
		WebElement zipField = getZipField();
		text.enterText(zipField, zip);
	}

	/**
	 * getter method to locate the confirm button
	 *
	 * @return confirm button WebElement
	 */
	protected WebElement getConfirmButton( )
	{
		WebElement confirmButton = null;
		String expectedText = "Confirm";
		confirmButton = element.selectElementByText(confirmButtonLoc, expectedText);

		return confirmButton;
	}

	/**
	 * uses the getter method to locate the confirm button and then clicks on it
	 */
	public void clickConfirmButton( )
	{
		WebElement confirmButton = getConfirmButton();
		confirmButton.click();

		List<WebElement> masks = wait.waitForAllElementsPresent(loadMaskLoc);
		for ( WebElement mask : masks )
		{
			try
			{
				wait.waitForElementNotDisplayed(mask);
			}
			catch ( Exception e )
			{
				System.out.println("Load Mask isn't Present");
			}
		}
	}

	/**
	 * getter method to locate the save button on the page
	 *
	 * @return save button WebElement
	 */
	protected WebElement getSaveButton( )
	{
		WebElement saveButton = wait.waitForElementPresent(saveButtonLoc);

		return saveButton;
	}

	/**
	 * uses the getter method to locate save button on the page and then clicks on it
	 */
	public void clickSaveButton( )
	{
		WebElement saveButton = getSaveButton();

		saveButton.click();
	}

	/**
	 * getter method to locate the validate address button from the address dialog
	 *
	 * @return validate address WebElement
	 */
	protected WebElement getValidateAddressButton( )
	{
		WebElement validateAddressButton = null;
		String expectedText = "Validate Address";
		WebElement validateAddressButtonContainer = wait.waitForElementPresent(validateAddressButtonConLoc);

		List<WebElement> validateAddressButtonClasses = wait.waitForAllElementsPresent(validateAddressButtonLoc,
			validateAddressButtonContainer);
		validateAddressButton = element.selectElementByText(validateAddressButtonClasses, expectedText);
		return validateAddressButton;
	}

	/**
	 * uses the getter method to locate the validate address button and then clicks on it
	 */
	public void clickValidateAddress( )
	{
		WebElement validateAddressButton = getValidateAddressButton();
		validateAddressButton.click();
	}

	/**
	 * getter method to locate the validated address box
	 *
	 * @return WebElement of validated address box
	 */
	protected WebElement getValidatedAddress( )
	{
		String expectedText = "VALIDATED";
		WebElement validatedAddressBox = null;
		List<WebElement> addressBoxContainers = wait.waitForAllElementsPresent(addressBoxConLoc);
		WebElement container = element.selectElementByText(addressBoxContainers, expectedText);
		if ( container != null )
		{
			WebElement parent = (WebElement) executeJs("return arguments[0].parentNode;", container);
			validatedAddressBox = wait.waitForElementPresent(validateAddressBoxLoc, parent);
		}

		return validatedAddressBox;
	}

	/**
	 * uses the getter method to locate the validated address box and then clicks on it
	 */
	public void selectValidatedAddress( )
	{
		WebElement validatedAddressBox = getValidatedAddress();
		validatedAddressBox.click();
	}

	/**
	 * locates the error message and then verifies it's content,
	 *
	 * @return true if the error message is the message expected.
	 */
	public String getErrorMessage( )
	{
		WebElement errorMessage = wait.waitForElementPresent(errorMessageLoc);
		String text = errorMessage.getText();

		return text;
	}

    /**
     * Get cleansed or validated address
     *
     * @return validated address
     */
    public String getCleansedAddress() {
        wait.waitForElementPresent(validatedAddress);
        return text.getElementText(validatedAddress);
    }

	/**
	 * Cancel validated address pop-up.
	 */
	public void cancelValidatedAddress() {
		WebElement cancel = wait.waitForElementPresent(validatedAddressCancel);
		click.moveToElementAndClick(cancel);
	}

	/**
	 * Close address-cleansing message pop-up.
	 */
	public void cancelAddressCleansingError() {
		WebElement close = wait.waitForElementPresent(closeAddressCleansingMSG);
		click.moveToElementAndClick(close);
	}
}


