package com.vertex.quality.connectors.salesforce.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Set;

public class SalesForceLightingSetup extends SalesForceBasePage
{
	protected By ICON_SETUP_GEAR = By.xpath(".//lightning-icon/span/lightning-primitive-icon/*[@data-key='setup']");
	protected By LINK_SETUP_CONSOLE = By.xpath(".//ul/li[contains(@id,'setup_app_home')]");

	protected By USER_NAV_ARROW = By.xpath("//*[@id='userNav-arrow']");
	protected By LINK_CLASSIC_SETUP_CONSOLE = By.xpath(".//a[@title='Developer Console (New Window)']");

	protected By TAB_OBJECT_MANAGER = By.xpath(".//ul/li/a/span[text()='Object Manager']");
	protected String SELECT_OBJECT = ".//section/.//div/div/div/table/tbody/tr/th/a[text()='%s']";
	protected By LABEL_NAME = By.xpath(
		".//section/div/div[2]/div/div/div/div[2]/div/div[2]/div/table/thead/tr/th[1]/a");
	protected By SELECT_FIELDS_RELATION = By.xpath(
		".//section/div/div[2]/div/div/div/div[2]/div/div/div[1]/div/ul/li[2]/a[text()='Fields & Relationships']");
	protected String EXISTING_FIELD = "//section/.//table/tbody/tr/td/a/*[text()='%s']";
	protected By NEW_BUTTON = By.xpath(".//div[2]/div/button[text()='New']");

	protected By HEADER_DATA_TYPE = By.xpath(".//form/div/div[2]/div[4]/div[1]/div/div[2]/div[2]/h3");
	protected By RADIO_TEXT = By.xpath(".//label[text()='Text']");
	protected By RADIO_NUMBER = By.xpath(".//label[text()='Number']");
	protected By RADIO_FORMULA = By.xpath(".//label[text()='Formula']");
	protected By RADIO_DATE = By.xpath(".//label[text()='Date']");
	protected By RADIO_CHECKBOX = By.xpath(".//label[text()='Checkbox']");
	protected By NEXT_BUTTON = By.xpath(".//form/div/div[2]/div[5]/div/input[@title='Next']");

	protected By TEXT_FIELDLABEL = By.xpath("//*[@id=\"MasterLabel\"]");
	protected By TEXT_LENGTH = By.xpath(".//*[@id='Length']");
	protected By TEXT_DEC_LENGTH = By.xpath(".//*[@id='digleft']");
	protected By TEXT_DECIMAL = By.xpath(".//*[@id='Scale']");
	protected By NEXT_BUTTON_2 = By.xpath(".//form/div/div[2]/div[1]/div/input[@title='Next']");

	protected By DROPDOWN_DECIMAL = By.xpath(".//*[@id=\"Scale\"]");
	protected By HEADER_FORMULA_DATA_TYPE = By.xpath(".//table/tbody/tr/td/div/div/div[2]/div[2]/h3");
	protected String RETURN_FORMULA_TYPE = ".//table/tbody/tr/td/label[text()='%s']";
	protected By TEXT_FORMULA = By.cssSelector("textarea[id*='CalculatedFormula']");
	protected By CHECKBOX_VISIBLE_ALL = By.xpath(".//*[@title='Visible']");
	protected By CHOOSE_BLANK_AS_EMPTY = By.id("tbaz0");
	protected By CHOOSE_BLANK_AS_ZERO = By.id("tbaz1");

	protected By FIELD_DROPDOWN = By.xpath(".//a[contains(@class, 'rowActions')]/lightning-icon");
	protected By DELETE_FIELD_BUTTON = By.xpath(".//button[@title='delete']");
	protected By BUTTON_SAVE = By.xpath(".//div[2]/div[1]/div/input[@name='save']");

	protected By QUICK_FIND_TEXTBOX = By.xpath("//*[@id='globalQuickfind']");

	protected By INSTALLED_PACKAGES_LINK = By.xpath(".//ul/li[@aria-label='Installed Packages']");

	protected By STORES_LINK = By.xpath("//*/a/mark[@class = 'highlight'][contains(text(),'Stores')]");
	protected By QUICK_FIND_SEARCH_FIELD = By.xpath("//*/div/input[@placeholder= 'Quick Find']");
	protected By QUICK_FIND_ALPINE_LINK = By.xpath("//*/a[contains(text(),'AlpineGroup')]/parent::*");
	protected By ADMINISTRATION_LINK_TEXT = By.xpath("//*/div/div[contains(text(),'Administration')]");
	protected By ADMINISTRATION_DEFAULT_COUNTRY_EDIT_BUTTON = By.xpath(".//button[@title='Edit Default Ship To Country']");
	protected By ADMINISTRATION_DEFAULT_COUNTRY_DROPDOWN = By.xpath(".//button[@name='Default Ship To Country']");
	protected By ADMINISTRATION_DEFAULT_COUNTRY_DROPDOWN_OPTIONS = By.xpath(".//div[@role='listbox']/lightning-base-combobox-item/span/span");
	protected By ADMINISTRATION_SAVE_BUTTON = By.xpath("//*/div/button/span[contains(text(),'Save')]");

	public SalesForceLightingSetup( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Launch Setup Console
	 */
	public void launchClassicSetupConsole( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(USER_NAV_ARROW);
		click.clickElement(USER_NAV_ARROW);
		wait.waitForElementDisplayed(LINK_CLASSIC_SETUP_CONSOLE);
		click.clickElement(LINK_CLASSIC_SETUP_CONSOLE);
		waitForPageLoad();
		window.switchToWindowTextInTitle("Home");
	}

	/**
	 * Launch Setup Console
	 */
	public void launchLightningSetupConsole( )
	{
		refreshPage();
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(ICON_SETUP_GEAR);
		click.clickElement(ICON_SETUP_GEAR);
		Set<String> previousHandles = window.getWindowHandles();
		wait.waitForElementDisplayed(LINK_SETUP_CONSOLE);
		click.clickElementCarefully(LINK_SETUP_CONSOLE);
		window.waitForAndSwitchToNewWindowHandle(previousHandles);

		waitForPageLoad();
	}

	/**
	 * select Object Manager Tool
	 */
	public void selectObjectManager( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(TAB_OBJECT_MANAGER);
		click.clickElement(TAB_OBJECT_MANAGER);
	}

	/**
	 * select Object Manager Item by Name
	 *
	 * @param name
	 */
	private void selectObject( String name )
	{
		waitForPageLoad();
		text.enterText(QUICK_FIND_TEXTBOX, name);
		waitForSalesForceLoaded();
		String ObjectXpath = String.format(SELECT_OBJECT, name);
		WebElement object = null;
		Actions actions = new Actions(driver);
		while ( object == null )
		{
			try
			{
				actions
					.sendKeys(Keys.END)
					.perform();
				object = driver.findElement(By.xpath(ObjectXpath));
			}
			catch ( Exception e )
			{
				actions
					.sendKeys(Keys.END)
					.perform();
			}
			object = driver.findElement(By.xpath(ObjectXpath));
		}
		wait.waitForElementDisplayed(object);
		click.clickElement(object);
	}

	/**
	 * select click Field Relationship area
	 */
	private void selectFieldRelationships( )
	{
		wait.waitForElementDisplayed(SELECT_FIELDS_RELATION);
		click.clickElement(SELECT_FIELDS_RELATION);
	}

	/**
	 * select object in manager
	 *
	 * @param object
	 */
	public void selectObjectInMgr( String object )
	{
		selectObjectManager();
		selectObject(object);
		selectFieldRelationships();
	}

	/**
	 * check field exists
	 *
	 * @param name
	 */
	private boolean checkFieldExists( String name )
	{
		String fieldExist = String.format(EXISTING_FIELD, name);
		boolean fieldDoesExist = false;
		try
		{
			waitForSalesForceLoaded();
			text.enterText(QUICK_FIND_TEXTBOX, name);
			Thread.sleep(1000);
			waitForSalesForceLoaded();
			WebElement field = driver.findElement(By.xpath(fieldExist));
			fieldDoesExist = element.isElementDisplayed(field);
		}
		catch ( Exception e )
		{
		}
		return fieldDoesExist;
	}

	/**
	 * select New
	 */
	private void selectNew( )
	{
		wait.waitForElementDisplayed(NEW_BUTTON);
		click.clickElement(NEW_BUTTON);
	}

	/**
	 * select return value
	 *
	 * @param returnValue
	 */
	private void selectReturnValue( String returnValue )
	{
		waitForSalesForceLoaded();

		driver
			.switchTo()
			.frame(driver.findElement(By.xpath(".//iframe")));
		click.clickElement(HEADER_DATA_TYPE);
		if ( returnValue == "Formula" )
		{
			click.clickElement(RADIO_FORMULA);
		}
		else if ( returnValue == "Number" )
		{
			click.clickElement(RADIO_NUMBER);
		}
		else if ( returnValue == "Date" )
		{
			click.clickElement(RADIO_DATE);
		}
		else if ( returnValue == "Checkbox" )
		{
			click.clickElement(RADIO_CHECKBOX);
		}
		else
		{
			click.clickElement(RADIO_TEXT);
		}
	}

	/**
	 * select Formula return value
	 *
	 * @param returnValue
	 */
	private void selectFormulaReturnValue( String returnValue )
	{
		waitForSalesForceLoaded();

		click.clickElement(HEADER_FORMULA_DATA_TYPE);
		String formulaLookup = String.format(RETURN_FORMULA_TYPE, returnValue);
		click.clickElement(By.xpath(formulaLookup));
	}

	/**
	 * click Next
	 */
	private void clickNext( )
	{
		wait.waitForElementDisplayed(NEXT_BUTTON);
		click.clickElement(NEXT_BUTTON);
	}

	/**
	 * click other Next
	 */
	private void clickNext2( )
	{
		wait.waitForElementDisplayed(NEXT_BUTTON_2);
		click.clickElement(NEXT_BUTTON_2);
	}

	/**
	 * set Name
	 *
	 * @param name
	 */
	private void setName( String name )
	{
		wait.waitForElementDisplayed(TEXT_FIELDLABEL);
		text.enterText(TEXT_FIELDLABEL, name);
	}

	/**
	 * set Length
	 *
	 * @param length
	 */
	private void setLength( String length )
	{
		wait.waitForElementDisplayed(TEXT_LENGTH);
		text.enterText(TEXT_LENGTH, length);
	}

	/**
	 * set Decimal Length
	 *
	 * @param length
	 */
	private void setDecLength( String length )
	{
		wait.waitForElementDisplayed(TEXT_DEC_LENGTH);
		text.enterText(TEXT_DEC_LENGTH, length);
	}

	/**
	 * set Decimal
	 *
	 * @param decimal
	 */
	private void setDecimal( String decimal )
	{
		wait.waitForElementDisplayed(TEXT_DECIMAL);
		text.enterText(TEXT_DECIMAL, decimal);
	}

	/**
	 * set Decimal
	 *
	 * @param decimal
	 */
	private void setDropdownDecimal( String decimal )
	{
		wait.waitForElementDisplayed(DROPDOWN_DECIMAL);
		dropdown.selectDropdownByDisplayName(DROPDOWN_DECIMAL, decimal);
	}

	/**
	 * set Formula
	 *
	 * @param formula
	 */
	private void setFormula( String formula )
	{
		wait.waitForElementDisplayed(TEXT_FORMULA);
		text.enterText(TEXT_FORMULA, formula);
	}

	/**
	 * set Blanks to be treated as Zero or as Empty
	 * @param makeZero
	 */
	private void setTreatBlankAsZero(Boolean makeZero)
	{
		wait.waitForElementDisplayed(CHOOSE_BLANK_AS_EMPTY);
		if(makeZero)
			click.clickElement(CHOOSE_BLANK_AS_ZERO);
		else
			click.clickElement(CHOOSE_BLANK_AS_EMPTY);
	}

	/**
	 * select visible
	 */
	private void selectVisible( )
	{
		wait.waitForElementDisplayed(CHECKBOX_VISIBLE_ALL);
		click.clickElement(CHECKBOX_VISIBLE_ALL);
	}

	/**
	 * click Save
	 */
	private void clickSave( )
	{
		wait.waitForElementDisplayed(BUTTON_SAVE);
		click.clickElement(BUTTON_SAVE);
	}

	/**
	 * create Custom field
	 *
	 * @param name
	 * @param returnValue
	 * @param length
	 */
	public void createCustomField( String name, String returnValue, String length )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			waitForSalesForceLoaded();

			selectReturnValue(returnValue);
			clickNext();
			setName(name);
			if(length!= "")
				setLength(length);
			clickNext2();
			selectVisible();
			clickNext2();
			clickSave();
			waitForPageLoad();
		}
	}

	/**
	 * create Custom field
	 *
	 * @param name
	 * @param returnValue
	 * @param length
	 * @param decimal
	 */
	public void createCustomField( String name, String returnValue, String length, String decimal )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			selectReturnValue(returnValue);
			clickNext();
			setName(name);
			setDecLength(length);
			setDecimal(decimal);
			clickNext2();
			selectVisible();
			clickNext2();
			clickSave();
			waitForPageLoad();
		}
	}

	/**
	 * create Custom field
	 *
	 * @param name
	 * @param returnValue
	 */
	public void createCustomField( String name, String returnValue )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			selectReturnValue(returnValue);
			clickNext();
			setName(name + Keys.TAB);
			clickNext2();
			selectVisible();
			clickNext2();
			clickSave();
			waitForPageLoad();
		}
	}

	/**
	 * create Custom field
	 *
	 * @param name
	 * @param returnValue
	 * @param formula
	 * @param decimal
	 */
	public void createCustomFieldFormula( String name, String returnValue, String formula, String decimal )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			selectReturnValue("Formula");
			clickNext();
			setName(name);
			selectFormulaReturnValue(returnValue);
			setDropdownDecimal(decimal);
			clickNext2();
			if ( !formula.contains("__c") )
			{
				formula = formula+"." + name + "__c";
			}
			setFormula(formula);
			setTreatBlankAsZero(false);
			clickNext2();
			selectVisible();
			clickNext2();
			clickSave();
			try{
				waitForSalesForceLoaded();
			}
			catch(WebDriverException ex){}
		}
	}

	/**
	 * create Custom field
	 *
	 * @param name
	 * @param returnValue
	 * @param returnValue
	 * @param formula
	 */
	public void createCustomFieldFormula( String name, String returnValue, String formula )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			selectReturnValue("Formula");
			clickNext();
			setName(name);
			selectFormulaReturnValue(returnValue);
			clickNext2();
			if ( !formula.contains("__c") )
			{
				formula = formula+"." + name + "__c";
			}
			setFormula(formula);
			setTreatBlankAsZero(false);
			clickNext2();
			selectVisible();
			clickNext2();
			clickSave();
			try{
				waitForSalesForceLoaded();
			}
			catch(WebDriverException ex){}
		}
	}

	/**
	 * Delete custom field
	 *
	 * @param fieldLabel
	 */
	public void deleteCustomField(String fieldLabel)
	{
		if(checkFieldExists(fieldLabel))
		{
			wait.waitForElementDisplayed(FIELD_DROPDOWN);
			click.clickElement(FIELD_DROPDOWN);
			selectFieldDropdownOption("Delete");
			confirmDeleteField();
		}
	}

	/**
	 * Select Field dropdown option
	 * @param action
	 */
	public void selectFieldDropdownOption(String action)
	{
		String locator = String.format(".//a[@role='menuitem' and @title='%s']", action);
		By dropdownOption = By.xpath(locator);
		wait.waitForElementDisplayed(dropdownOption);
		click.clickElement(dropdownOption);
		waitForSalesForceLoaded();
	}

	/**
	 * Confirm field deletion on pop up
	 */
	public void confirmDeleteField()
	{
		wait.waitForElementDisplayed(DELETE_FIELD_BUTTON);
		click.clickElement(DELETE_FIELD_BUTTON);
	}

	/**
	 * Search on the setup page quick find search field
	 *
	 * @param search
	 * */
	public void setQuickSearch(String search){
		click.clickElementCarefully(QUICK_FIND_SEARCH_FIELD);
		text.enterText(QUICK_FIND_SEARCH_FIELD, search);
	}

	/**
	 * Navigates to Installed Packages after quick search
	 */
	public void navigateToInstalledPackages(){
		wait.waitForElementDisplayed(INSTALLED_PACKAGES_LINK);
		click.clickElement(INSTALLED_PACKAGES_LINK);
		waitForSalesForceLoaded(3000);
	}

	/**
	 * Set the country for the B2C storefront
	 *
	 * @param countryName
	 * */
	public void setB2CCountry(String countryName){
		launchLightningSetupConsole();
		setQuickSearch("Stores");
		click.clickElementCarefully(STORES_LINK);
		jsWaiter.sleep(3000);
		wait.waitForElementDisplayed(QUICK_FIND_ALPINE_LINK);
		click.clickElementCarefully(QUICK_FIND_ALPINE_LINK);
		wait.waitForElementDisplayed(ADMINISTRATION_LINK_TEXT);
		click.clickElementCarefully(ADMINISTRATION_LINK_TEXT);
		wait.waitForElementDisplayed(ADMINISTRATION_DEFAULT_COUNTRY_EDIT_BUTTON);
		click.clickElement(ADMINISTRATION_DEFAULT_COUNTRY_EDIT_BUTTON);
		scroll.scrollElementIntoView(ADMINISTRATION_DEFAULT_COUNTRY_DROPDOWN);
		wait.waitForElementDisplayed(ADMINISTRATION_DEFAULT_COUNTRY_DROPDOWN);
		click.clickElementCarefully(ADMINISTRATION_DEFAULT_COUNTRY_DROPDOWN);
		List<WebElement> dropDownOptions = driver.findElements(ADMINISTRATION_DEFAULT_COUNTRY_DROPDOWN_OPTIONS);
		WebElement countryOption = null;
		for(WebElement option: dropDownOptions){
			if(option.getAttribute("title").equalsIgnoreCase(countryName)){
				countryOption = option;
				break;
			}
		}
		wait.waitForElementDisplayed(countryOption);
		click.javascriptClick(countryOption);
		click.clickElementCarefully(ADMINISTRATION_SAVE_BUTTON);
	}
}
