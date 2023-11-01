package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Common functions for anything related to Salesforce Basic Setup Page.
 *
 * @author
 */
public class SalesForceCRMSetupPage extends SalesForceBasePage
{
	protected By QUICK_SEARCH_INPUT = By.id("setupSearch");
	protected By INSTALL_PACKAGE_LINK = By.id("ImportedPackage_font");

	protected By EXISTING_SP_DROPDOWN = By.xpath(
		"//label[contains(text(),'Existing Sales Process')]/parent::td/following-sibling::td//select");
	protected By SP_NAME = By.xpath(
		"//label[contains(text(),'Sales Process Name')]/parent::td/following-sibling::td//input");
	protected By DESCRIPTION = By.xpath(
		"//label[contains(text(),'Description')]/parent::td/following-sibling::td//input");

	protected By SAVE_BUTTON = By.cssSelector("#bottomButtonRow input[name='save']");
	protected By EDIT_RECORD_TYPE = By.name("edit");

	protected By SELECTED_STAGES = By.cssSelector("#duel_select_1 option");
	protected By EXISTING_RECORD_TYPE = By.xpath(
		"//label[contains(text(),'Existing Record Type')]/parent::td/following-sibling::td//select");
	protected By RECORD_TYPE_LABEL = By.xpath(
		"//label[contains(text(),'Record Type Label')]/parent::td/following-sibling::td//input");
	protected By RECORD_TYPE_NAME = By.xpath(
		"//label[contains(text(),'Record Type Name')]/parent::td/following-sibling::td//input");
	protected By SALES_PROCESS_NAME = By.xpath(
		"//label[contains(text(),'Sales Process')]/parent::td/following-sibling::td//select");
	protected By ACTIVE_CHECKBOX = By.xpath(
		"//label[contains(text(),'Active')]/parent::td/following-sibling::td//input");
	protected By SELECT_ALL_PROFILES_CHECKBOX = By.id("selectAllProfiles");
	protected By NEXT_BUTTON = By.cssSelector("input[title = 'Next']");
	protected By DONE_BUTTON = By.cssSelector("input[title = 'Done']");

	protected By ADD_BUTTON = By.cssSelector("img[title='Add']");
	protected By SELECT_PAGE_LAYOUT = By.xpath(
		"//label[contains(text(),'Apply one layout to all profiles')]/parent::td/following-sibling::td//select");
	protected By B2B_CUSTOMER_COMMUNITY = By.xpath("//table/tbody/tr/th/a[text()='B2B Customer Community']");

	protected By ACTIVE_SITE_HOME_PAGE = By.xpath(
		".//table/tbody/tr/th/label[contains(text(), 'Active Site Home Page')]/../..//span/a");
	protected By PREVIEW_BUTTON = By.xpath(".//*[@value='Preview']");

	protected By ICON_SETUP_GEAR = By.xpath(".//lightning-icon/lightning-primitive-icon/*[@data-key='setup']");
	protected By LINK_SETUP_CONSOLE = By.xpath(".//*[contains(@id,'setup_app')]");

	protected By USER_NAV_ARROW = By.xpath("//*[@id='userNav-arrow']");
	protected By LINK_CLASSIC_SETUP_CONSOLE = By.xpath(".//a[@title='Developer Console (New Window)']");

	protected By TAB_OBJECT_MANAGER = By.xpath(".//ul/li/a/span[text()='Object Manager']");
	protected String SELECT_OBJECT = ".//section/.//div/div/div/table/tbody/tr/th/a[text()='%s']";
	protected By LABEL_NAME = By.xpath(
		".//section/div/div[2]/div/div/div/div[2]/div/div[2]/div/table/thead/tr/th[1]/a");
	protected By SELECT_FIELDS_RELATION = By.xpath(
		".//section/div/div[2]/div/div/div/div[2]/div/div/div[1]/div/ul/li[2]/a[text()='Fields & Relationships']");
	protected String EXISTING_FIELD = ".//table/tbody/.//a[text()='%s']";
	protected By NEW_BUTTON = By.xpath(".//*[contains(@value,'New')]");

	protected By HEADER_DATA_TYPE = By.xpath(".//*[@id='head_1_ep']/h3");
	protected By RADIO_TEXT = By.xpath(".//label[text()='Text']");
	protected By RADIO_TEXT_AREA_LONG = By.xpath(".//label[text()='Text Area (Long)']");
	protected By RADIO_PICKLIST = By.xpath(".//label[text()='Picklist']");
	protected By RADIO_NUMBER = By.xpath(".//label[text()='Number']");
	protected By RADIO_FORMULA = By.xpath(".//label[text()='Formula']");
	protected By RADIO_DATE = By.xpath(".//label[text()='Date']");
	protected By RADIO_CHECKBOX = By.xpath(".//label[text()='Checkbox']");
	protected By RADIO_CURRENCY = By.xpath(".//label[text()='Currency']");
	protected By RADIO_PERCENT = By.xpath(".//label[text()='Percent']");

	protected By TEXT_FIELDLABEL = By.xpath("//*[@id=\"MasterLabel\"]");
	protected By RADIO_PICKLIST_VALUES = By.id("picklistTypeLOCAL_PICKLIST");
	protected By TEXT_VALUESL = By.xpath("//*[@id=\"ptext\"]");
	protected By TEXT_LENGTH = By.xpath(".//*[@id='Length']");
	protected By TEXT_DEC_LENGTH = By.xpath(".//*[@id='digleft']");
	protected By TEXT_DECIMAL = By.xpath(".//*[@id='Scale']");
	protected By NEXT_BUTTON_2 = By.xpath(".//form/div/div[2]/div[1]/div/input[@title='Next']");

	protected By DROPDOWN_DECIMAL = By.xpath(".//*[@id=\"Scale\"]");
	protected By HEADER_FORMULA_DATA_TYPE = By.xpath(".//table/tbody/tr/td/div/div/div[2]/div[2]/h3");
	protected String RETURN_FORMULA_TYPE = ".//table/tbody/tr/td/label[text()='%s']";
	protected By TAB_ADVANCED_FORMULA = By.xpath(".//*[@id=\"stageForm\"]/div/div/div/table/tbody/tr/td/div/ul/li/a[@title = 'Advanced Formula Subtab']");
	protected By TEXT_FORMULA = By.cssSelector("textarea[id*='CalculatedFormula']");
	protected By CHECKBOX_VISIBLE_ALL = By.xpath(".//*[@title='Visible']");
	protected By CHOOSE_BLANK_AS_EMPTY = By.id("tbaz0");
	protected By CHOOSE_BLANK_AS_ZERO = By.id("tbaz1");
	protected By TEXT_TAX_ENGINE_FIELD = By.xpath(".//*[@title='Tax Engine']");
	protected By CHECKBOX_ADD_TO_ALL_RECORDS = By.id("p18");

	protected By BUTTON_SAVE = By.xpath(".//input[@name='save' and @type!='hidden']");

	protected By DELETE_FIELD_CHECKBOX = By.cssSelector(".//input[@id='confirmed' and @type='checkbox']");
	protected By DELETE_FIELD_BUTTON = By.xpath(".//input[@title='Delete' and @class='btn']");

	public SalesForceCRMSetupPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * To enter Search keyword
	 *
	 * @param searchKeyword
	 */
	public void setQuickSearchInput( String searchKeyword )
	{
		wait.waitForElementDisplayed(QUICK_SEARCH_INPUT);
		text.enterText(QUICK_SEARCH_INPUT, searchKeyword);
		wait.waitForElementDisplayed(INSTALL_PACKAGE_LINK);
	}

	/**
	 * Click on Installed Package links after search result displayed
	 */
	public void clickInstallPackageLink( )
	{
		wait.waitForElementEnabled(INSTALL_PACKAGE_LINK);
		click.clickElement(INSTALL_PACKAGE_LINK);
		waitForPageLoad();
	}

	/**
	 * Select existing Sales Process to create new Sales Process
	 *
	 * @param existingProcess
	 */
	public void selectExistingSalesProcess( String existingProcess )
	{
		wait.waitForElementDisplayed(EXISTING_SP_DROPDOWN);
		dropdown.selectDropdownByDisplayName(EXISTING_SP_DROPDOWN, existingProcess);
	}

	/**
	 * Enter Process Name to create new Sales Process
	 *
	 * @param processName
	 */
	public void setSalesProcessName( String processName )
	{
		wait.waitForElementDisplayed(SP_NAME);
		text.enterText(SP_NAME, processName);
	}

	/**
	 * Select the existing Record Type
	 *
	 * @param existingRecordType
	 */
	public void selectExistingRecordType( String existingRecordType )
	{
		wait.waitForElementDisplayed(EXISTING_RECORD_TYPE);
		dropdown.selectDropdownByDisplayName(EXISTING_RECORD_TYPE, existingRecordType);
	}

	/**
	 * Enter Record Type label to create new Record Type
	 *
	 * @param recordTypeLabel
	 */
	public void SetRecordTypeLabel( String recordTypeLabel )
	{
		wait.waitForElementDisplayed(RECORD_TYPE_LABEL);
		text.enterText(RECORD_TYPE_LABEL, recordTypeLabel);
	}

	/**
	 * Select the sales Process to create Record Type
	 *
	 * @param salesProcess
	 */
	public void selectSalesProcess( String salesProcess )
	{
		wait.waitForElementDisplayed(SALES_PROCESS_NAME);
		dropdown.selectDropdownByDisplayName(SALES_PROCESS_NAME, salesProcess);
	}

	/**
	 * Check/Uncheck the Active checkbox
	 *
	 * @param check
	 */
	public void setActiveCheckbox( boolean check )
	{
		boolean ischecked = checkbox.isCheckboxChecked(ACTIVE_CHECKBOX);
		if ( !check == ischecked )
		{
			click.clickElement(ACTIVE_CHECKBOX);
		}
	}

	/**
	 * Check the Enable Profile Checkbox
	 */
	public void setEnableForProfile( )
	{
		checkbox.setCheckbox(SELECT_ALL_PROFILES_CHECKBOX, true);
	}

	/**
	 * To click on next button
	 */
	public void clickNextButton( )
	{
		click.clickElement(NEXT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Select Page Layout to create Record Type
	 *
	 * @param pageLayout
	 */
	public void selectPageLayout( String pageLayout )
	{
		dropdown.selectDropdownByDisplayName(SELECT_PAGE_LAYOUT, pageLayout);
		waitForPageLoad();
	}

	/**
	 * Enter Description to create Sales Process and Record Type
	 *
	 * @param description
	 */
	public void setDescription( String description )
	{
		wait.waitForElementDisplayed(DESCRIPTION);
		text.enterText(DESCRIPTION, description);
	}

	/**
	 * Click on Save button to complete creation of Sales Process and Record Type
	 */
	public void clickOnSaveButton( )
	{
		wait.waitForElementEnabled(SAVE_BUTTON);
		click.clickElement(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To edit Record type,Click on Edit button
	 */
	public void clickOnEditButton( )
	{
		wait.waitForElementEnabled(EDIT_RECORD_TYPE);
		click.clickElement(EDIT_RECORD_TYPE);
		waitForPageLoad();
	}

	/**
	 * To retrive Recors Type name
	 *
	 * @return
	 */
	public String getRecordTypeName( )
	{
		click.clickElement(RECORD_TYPE_NAME);
		String recordTypeName = attribute.getElementAttribute(RECORD_TYPE_NAME, "value");
		return recordTypeName;
	}

	/**
	 * Navigate through Menu exists on Left hand side of setup page
	 *
	 * @param menuName
	 * @param subMenu
	 * @param childPage
	 * @param childSubMenu
	 */
	public void NavigateWithLeftMenu( String menuName, String subMenu, String childPage, String childSubMenu)
	{
		NavigateWithLeftMenu( menuName, subMenu, childPage );
		String childSubMenuItem = String.format("//div[@id='%s']//a[text()='%s']/..//a[text()='%s']", subMenu, childPage, childSubMenu);

		wait.waitForElementDisplayed(By.xpath(childSubMenuItem));
		click.clickElement(By.xpath(childSubMenuItem));
	}

	/**
	 * Navigate through Menu exists on Left hand side of setup page
	 *
	 * @param menuName
	 * @param subMenu
	 * @param childPage
	 */
	public void NavigateWithLeftMenu( String menuName, String subMenu, String childPage )
	{
		String childItem = String.format("//div[@id='%s']//a[text()='%s']", subMenu, childPage);

		if(!element.isElementDisplayed(By.xpath(childItem))) {
			NavigateWithMenuSelector( menuName, subMenu );
		}

		wait.waitForElementDisplayed(By.xpath(childItem));
		click.clickElement(By.xpath(childItem));
		waitForPageLoad();
	}

	/**
	 * Navigate through Menu exists on Left hand side of setup page
	 *
	 * @param menuName
	 * @param subMenu
	 */
	public void NavigateWithMenuSelector( String menuName, String subMenu )
	{
		String menuItem = String.format(".//*[@id='%s']/a", menuName);
		String subMenuItem = String.format(".//*[@id='%s']/a", subMenu);

		if(menuName.contains("_"))
			menuItem = menuItem.replace("/a", "");
		if(subMenu.contains("_"))
			subMenuItem = subMenuItem.replace("/a", "");

		waitForSalesForceLoaded();
		if ( subMenu == null )
		{
			wait.waitForElementDisplayed(By.xpath(menuItem));
			click.clickElement(By.xpath(menuItem));
		}
		else if ( !(subMenu == null) )
		{
			if(!element.isElementDisplayed(By.xpath(subMenuItem))) {
				wait.waitForElementDisplayed(By.xpath(menuItem));
				click.clickElement(By.xpath(menuItem));
			}
			wait.waitForElementDisplayed(By.xpath(subMenuItem));
			click.clickElement(By.xpath(subMenuItem));
		}

		waitForPageLoad();
	}

	/**
	 * clickSortLink
	 */
	public void clickSortLink( String sortValue )
	{
		String selectorSortValue = ".//div/div/div/a/span[text()='%s']";
		By selectorLink = By.xpath(String.format(selectorSortValue, sortValue));
		wait.waitForElementDisplayed(selectorLink);
		click.clickElement(selectorLink);
		waitForPageLoad();
	}

	/**
	 * selectOptionInList
	 * @param optionName
	 */
	public void selectOptionInList( String optionName )
	{
		String selectorSortValue = ".//*[@id=\"bodyCell\"]/div/div/div/table/tbody/tr/th/a[text()='%s']";
		By selectorLink = By.xpath(String.format(selectorSortValue, optionName));
		wait.waitForElementDisplayed(selectorLink);
		click.clickElement(selectorLink);
		waitForPageLoad();
	}

	/**
	 * checkListItemExists
	 * @param name
	 * @return
	 */
	public Boolean checkListItemExists( String name)
	{
		String valueList = String.format("//*[contains(@id,'PicklistMasterActive')]/table/tbody/tr/td[text()='%s']", name);
		return element.isElementPresent(By.xpath(valueList));
	}

	/**
	 * checkActivateForItem
	 * @param name
	 */
	public Boolean checkActivateForItem( String name )
	{
		String valueList = String.format(".//th[text()= '%s']/../td/a[contains(@title, 'Activate')]", name);
		return element.isElementPresent(By.xpath(valueList));
	}

	/**
	 * clickActivateForItem
	 * @param name
	 */
	public void clickActivateForItem( String name )
	{
		String valueList = String.format(".//th[text()= '%s']/../td/a[contains(@title, 'Activate')]", name);
		click.clickElement(By.xpath(valueList));
	}

	/**
	 * addNewPicklistValue
	 * @param value
	 */
	public void addNewPicklistValue( String value)
	{
		selectNew();
		setValue(value);
		checkBoxAddRecordTypes();
		clickSave();
	}

	/**
	 * setValue
	 * @param value
	 */
	public void setValue( String value)
	{
		wait.waitForElementDisplayed(TEXT_TAX_ENGINE_FIELD);
		click.clickElement(TEXT_TAX_ENGINE_FIELD);
		text.enterText(TEXT_TAX_ENGINE_FIELD, value);
	}

	/**
	 * checkBoxAddRecordTypes
	 */
	public void checkBoxAddRecordTypes()
	{
		wait.waitForElementDisplayed(CHECKBOX_ADD_TO_ALL_RECORDS);
		click.clickElement(CHECKBOX_ADD_TO_ALL_RECORDS);
	}

	/**
	 * SelectObject
	 *
	 * @param objectName
	 */
	public void SelectObject(String objectName)
	{
		String menuItem = String.format(".//div/div/table/tbody/tr/th/a[text()='%s']", objectName);

		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(By.xpath(menuItem));
		click.clickElement(By.xpath(menuItem));
	}

	/**
	 * Navigate to the B2B Order Entry Site
	 */
	public void navigateToB2BSite( )
	{
		wait.waitForElementDisplayed(B2B_CUSTOMER_COMMUNITY);
		click.clickElement(B2B_CUSTOMER_COMMUNITY);
		waitForPageLoad();

		wait.waitForElementDisplayed(ACTIVE_SITE_HOME_PAGE);
		click.clickElement(ACTIVE_SITE_HOME_PAGE);

		wait.waitForElementDisplayed(PREVIEW_BUTTON);
		click.clickElement(PREVIEW_BUTTON);
	}

	/**
	 * To Add few stage values while creating Sales Process, We have to clear the
	 * selected values list
	 */
	public void removeSelectedStageValues( )
	{
		List<WebElement> selectedValues = element.getWebElements(SELECTED_STAGES);

		while ( !selectedValues
			.get(0)
			.getText()
			.contains("None") )
		{
			click.clickElement(selectedValues.get(0));
			waitForPageLoad();
			click.clickElement(By.id("duel_select_0_left"));
			waitForPageLoad();
			selectedValues = element.getWebElements(SELECTED_STAGES);
			waitForPageLoad();
		}
	}

	/**
	 * To Add stages from available to seleccted list
	 *
	 * @param stage
	 */
	public void selectStage( String stage )
	{
		String stageValue = String.format("//select[@id='duel_select_0']//option[contains(text(),'%s')]", stage);
		click.clickElement(By.xpath(stageValue));
		click.clickElement(ADD_BUTTON);
	}

	/**
	 * To delete, Click on Delete link against the Sales Process
	 *
	 * @param processName
	 */
	public void deleteSaleProcesses( String processName )
	{
		click.clickElement(By.xpath("//a[contains(text(),'Del') and contains(@title,'" + processName + "')]"));
		alert.acceptAlert(DEFAULT_TIMEOUT);
	}

	/**
	 * To edit Record Type
	 *
	 * @param recordTypeLabel
	 */
	public void editRecordType( String recordTypeLabel )
	{
		click.clickElement(By.xpath("//a[contains(text(),'Edit') and contains(@title,'" + recordTypeLabel + "')]"));
		setActiveCheckbox(false);
	}

	/**
	 * To Delete, Click on Delete link against Record type from table
	 *
	 * @param recordTypeLabel
	 */
	public void deleteRecordType( String recordTypeLabel )
	{
		click.clickElement(By.xpath("//a[contains(text(),'Del') and contains(@title,'" + recordTypeLabel + "')]"));
		waitForPageLoad();
	}

	/**
	 * To Delete, Click on Delete link against Field label from table
	 *
	 * @param fieldLabel
	 */
	public void deleteCustomField( String fieldLabel )
	{
		String locator = String.format("//a[contains(@title, '%s')]/following-sibling::a[contains(text(),'Del')]", fieldLabel);
		By deleteLink = By.xpath(locator);
		wait.waitForElementDisplayed(deleteLink);
		click.clickElement(deleteLink);

		setDeleteCustomFieldCheckbox();
		clickCustomFieldDeleteButton();
	}

	/**
	 * To Delete, Click on Delete link against Field label from table
	 */
	public void setDeleteCustomFieldCheckbox( )
	{
		window.switchToWindowTextInTitle("Confirm Custom Field Delete");
		try{
			executeJs("document.querySelector('input#confirmed').click();");
		}
		catch (Exception e){}
		waitForPageLoad();
	}

	/**
	 * Click the Delete button on custom field deletion pop up
	 */
	public void clickCustomFieldDeleteButton()
	{
		wait.waitForElementDisplayed(DELETE_FIELD_BUTTON);
		click.clickElement(DELETE_FIELD_BUTTON);

		try{
			window.switchToWindowTextInTitle("Custom Object");
			waitForPageTitleContains("Custom Object");
		}
		catch (Exception e){
			window.switchToWindowTextInTitle("Fields");
			waitForPageTitleContains("Fields");
		}
	}


	/**
	 * Click on Done button, to complete delete process
	 */
	public void clickDoneButton( )
	{
		click.clickElement(DONE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Validate SalesProcess/Record Type after deleted
	 *
	 * @param processName
	 *
	 * @return
	 */
	public Boolean validateProcess( String processName )
	{
		waitForPageLoad();
		String processRow = String.format(
			"//div[@class='listRelatedObject setupBlock']//table//tr//th//a[contains(text(),'%s')]", processName);
		Boolean isDisplayed = element.isElementDisplayed(By.xpath(processRow));
		return isDisplayed;
	}

	/**
	 * This method is to Get Version details based on column name
	 *
	 * @param packageName
	 * @param detailColumnName
	 *
	 * @return
	 */
	public String getPackageDetail( String packageName, String detailColumnName )
	{
		String cellValue = "";

		String stageValue = String.format("//*[text()='%s']/ancestor::tr[contains(@class, 'dataRow')]", packageName);
		boolean isExists = element.isElementDisplayed(By.xpath(stageValue));

		if ( isExists )
		{
			String headerValue = stageValue + "/preceding-sibling::tr[@class='headerRow']/th";
			List<WebElement> headerList = element.getWebElements(By.xpath(headerValue));

			List<String> headerNameList = new ArrayList<String>();

			for ( WebElement element : headerList )
			{
				String headerName = element.getText();
				headerNameList.add(headerName);
			}

			int columnIndex = headerNameList.indexOf(detailColumnName);

			if ( columnIndex > 0 )
			{
				columnIndex = columnIndex + 1;
				By dataCell = (By.xpath(stageValue + "/*[" + columnIndex + "]"));
				cellValue = attribute.getElementAttribute(dataCell, "textContent");
			}
		}
		else
		{
		}

		return cellValue;
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
		String ObjectXpath = String.format(SELECT_OBJECT, name);
		click.clickElement(LABEL_NAME);
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
			fieldDoesExist = element.isElementPresent(By.xpath(fieldExist));
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
		else if ( returnValue == "Text Area (Long)" )
		{
			click.clickElement(RADIO_TEXT_AREA_LONG);
		}
		else if ( returnValue == "Picklist" )
		{
			click.clickElement(RADIO_PICKLIST);
		}
		else if ( returnValue == "Currency" )
		{
			click.clickElement(RADIO_CURRENCY);
		}
		else if ( returnValue == "Percent" )
		{
			click.clickElement(RADIO_PERCENT);
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
	 * set Values
	 *
	 * @param values
	 */
	private void setValues( String values )
	{
		click.clickElement(RADIO_PICKLIST_VALUES);
		wait.waitForElementDisplayed(TEXT_VALUESL);
		text.enterText(TEXT_VALUESL, values);
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
		if(element.isElementPresent(TAB_ADVANCED_FORMULA))
		{
			click.clickElement(TAB_ADVANCED_FORMULA);
			waitForSalesForceLoaded();
		}
		wait.waitForElementDisplayed(TEXT_FORMULA);
		text.enterText(TEXT_FORMULA, formula);
	}

	/**
	 * set Blanks to be treated as Zero or as Empty
	 * @param makeZero
	 */
	private void setTreatBlankAsZero(Boolean makeZero)
	{

		if(element.isElementPresent(CHOOSE_BLANK_AS_EMPTY))
		{
			if (makeZero)
				click.clickElement(CHOOSE_BLANK_AS_ZERO);
			else
				click.clickElement(CHOOSE_BLANK_AS_EMPTY);
		}
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
	 * @param values
	 */
	public void createCustomFieldPicklist( String name, String returnValue, String values )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			waitForSalesForceLoaded();

			selectReturnValue(returnValue);
			clickNext();
			setName(name);
			setValues(values);
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
			waitForPageLoad();
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
		createCustomFieldFormula( name, returnValue, formula, false);
	}

	/**
	 * create Custom field
	 *
	 * @param name
	 * @param returnValue
	 * @param returnValue
	 * @param formula
	 */
	public void createCustomFieldFormula( String name, String returnValue, String formula, boolean formulaProvided )
	{
		if ( !checkFieldExists(name) )
		{
			selectNew();
			selectReturnValue("Formula");
			clickNext();
			setName(name);
			selectFormulaReturnValue(returnValue);
			clickNext2();
			if ( !formula.contains("__c") && !formulaProvided)
			{
				formula = formula+"." + name + "__c";
			}
			setFormula(formula);
			setTreatBlankAsZero(false);
			clickNext2();
			selectVisible();
			clickNext2();
			clickSave();
			waitForPageLoad();
		}
	}
}
