package com.vertex.quality.connectors.kibo.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.kibo.components.KiboMainMenuNavPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * this class represents the shipping home base page
 * contains all the methods necessary to interact with the different field and buttons on the page
 *
 * @author osabha
 */
public class KiboHomeBasePage extends VertexPage
{
	public KiboMainMenuNavPanel navPanel;
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
	String streetAddress = "1 citizens bank way";
	String defaultStreetAddress = "1835 Kramer Lane #100";
	String city = "Philadelphia";
	String defaultCity = "Austin";
	String state = "PA";
	String defaultState = "TX";
	String zip = "19148";
	String defaultZip = "78758";

	public KiboHomeBasePage( WebDriver driver )
	{
		super(driver);
		this.navPanel = new KiboMainMenuNavPanel(driver, this);
	}

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
	public void enterStreetAddress( )
	{
		WebElement streetAddressField = getStreetAddressField();
		text.enterText(streetAddressField, streetAddress);
	}

	/**
	 * uses the getter method to locate the street address field and then
	 * enters the defaulted street address for the warehouse after the test case has run
	 */
	public void streetAddressPostCleanup( )
	{
		WebElement streetAddressField = getStreetAddressField();

		streetAddressField.clear();

		streetAddressField.sendKeys(defaultStreetAddress);
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
	public void enterCity( )
	{
		WebElement cityField = getCityField();

		cityField.clear();
		cityField.sendKeys(city);
	}

	/**
	 * uses the getter method to locate the city field and then clears the field and then types in
	 * the default warehouse address
	 * this is a post clean up to restore the default address after the test case has run
	 */
	public void cityPostCleanup( )
	{
		WebElement cityField = getCityField();
		text.enterText(cityField, defaultCity);
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
	public void enterState( )
	{
		WebElement stateField = getStateField();

		stateField.clear();
		stateField.sendKeys(state);
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
	 * uses the getter method to locate the state field and then clears it and types in state
	 * this is a post cleanup method to change back the address to the default after the test case
	 * run
	 */
	public void statePostCleanup( )
	{
		WebElement stateField = getStateField();

		stateField.clear();
		stateField.sendKeys(defaultState);
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
	public void enterZip( )
	{
		WebElement zipField = getZipField();
		zipField.clear();

		zipField.sendKeys(zip);
	}

	/**
	 * uses the getter method to locate the zip code field and then types in the old zip code as
	 * post cleanup
	 * to restore the default address after the test case runs
	 */
	public void zipPostCleanup( )
	{
		WebElement zipField = getZipField();
		text.enterText(zipField, defaultZip);
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
		List<WebElement> confirmButtonContainers = wait.waitForAllElementsPresent(confirmButtonLoc);
		confirmButton = element.selectElementByText(confirmButtonContainers, expectedText);

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
				System.out.println("load mask wasn't present");
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
}
