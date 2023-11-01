package com.vertex.quality.connectors.salesforce.pages.cpq;

import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Common functions for anything related to Salesforce Basic Setup Page.
 *
 * @author
 */
public class SalesForceCPQSetupPage extends SalesForceBasePage
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

	public SalesForceCPQSetupPage( WebDriver driver )
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
	 */
	public void NavigateWithLeftMenu( String menuName, String subMenu, String childPage )
	{
		String menuItem = String.format("[id='%s_icon']", menuName);
		String subMenuItem = String.format("[id='%s_icon']", subMenu);
		String childItem = String.format("//div[@id='%s']//a[text()='%s']", subMenu, childPage);

		if ( subMenu == null && childPage == null )
		{
			wait.waitForElementDisplayed(By.cssSelector(menuItem));
			click.clickElement(By.cssSelector(menuItem));
		}
		else if ( !(subMenu == null && childPage == null) )
		{
			wait.waitForElementDisplayed(By.cssSelector(menuItem));
			click.clickElement(By.cssSelector(menuItem));

			wait.waitForElementDisplayed(By.cssSelector(subMenuItem));
			click.clickElement(By.cssSelector(subMenuItem));

			wait.waitForElementDisplayed(By.xpath(childItem));
			click.clickElement(By.xpath(childItem));
			waitForPageLoad();
		}
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
	 * Clicks configure link next to matching package name
	 * @param packageName
	 */
	public void clickPackageConfigureLink(String packageName)
	{
		String configLink = String.format("//a[contains(@title, '%s') and text()='Configure']", packageName);
		wait.waitForElementDisplayed(By.xpath(configLink));
		click.clickElement(By.xpath(configLink));
		waitForPageLoad();
	}
}
