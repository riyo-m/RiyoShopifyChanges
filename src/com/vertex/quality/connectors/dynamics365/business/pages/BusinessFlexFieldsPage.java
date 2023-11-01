package com.vertex.quality.connectors.dynamics365.business.pages;


import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import org.openqa.selenium.*;


import java.util.List;

/**
 * Representation of the flex fields page
 *
 * @author cgajes
 */
public class BusinessFlexFieldsPage extends BusinessBasePage
{
	protected By dialogBoxLoc = By.className("ms-nav-content-box");
	protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
	protected By errorMessageLoc = By.className("ms-nav-validationicon-error");

	protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
	protected By editModeToggle = By.cssSelector("button[title='Make changes on the page.']");
	protected By flexFieldSourceLoc = By.xpath("//td[@controlname='Source']//select");
	protected By flexFieldTypeLoc = By.xpath("//td[@controlname='Type']//select");
	protected By flexFieldIdLoc = By.xpath("//td[@controlname='Field ID']//input");
	protected By flexFieldValueLoc =   By.xpath("//div[contains(@controlname,'Value')]//../input");
	protected By flexFieldValueDateLoc = By.cssSelector(
		"input[title='Type the date in the format M/d/yyyy']");

	protected By deleteButtonLoc = By.cssSelector("i[data-icon-name='Delete']");
	protected By backButtonLoc = By.cssSelector("button[title='Back']");
	protected By buttonLoc = By.tagName("button");
	protected By headers=By.xpath("//div[@controlname='VER_Flex Fields List Part']//table[not(@data-focus-zone)]//th");
	protected By showMoreLoc = By.cssSelector("a[title='Show more options']");
	protected By optionsLoc=By.xpath("//div[@class='ms-nav-scrollable']//li//span");
	protected By dateValueField=By.xpath("//table[contains(@id,'BusinessGrid')]//td[@controlname='ValueConstantDate']/input");

	public BusinessFlexFieldsPage( WebDriver driver ) { super(driver); }

	/**
	 * locates the back arrow and clicks on it to save the changes on the page and close it
	 */
	public void clickBackAndSaveArrow( )
	{
		List<WebElement> arrowsList = wait.waitForAllElementsPresent(backAndSaveArrowButtonLoc);
		WebElement backArrow = arrowsList.get(arrowsList.size() - 1);
		wait.waitForElementEnabled(backArrow);
		try
		{
			click.clickElement(backArrow);
		}
		catch ( ElementNotInteractableException e )
		{

		}
		wait.waitForElementNotDisplayedOrStale(backArrow, 5);
	}

	/**
	 * Selects the source for a new flex field
	 *
	 * @param option
	 */
	public void selectFlexFieldSource( String option )
	{
		WebElement sourceEle = wait.waitForElementEnabled(flexFieldSourceLoc, 10);
		dropdown.selectDropdownByDisplayName(sourceEle, option);
		text.pressTab(sourceEle);
	}

	/**
	 * Selects the type for a new flex field
	 *
	 * @param option
	 */
	public void selectFlexFieldType( String option )
	{
		WebElement typeEle = wait.waitForElementEnabled(flexFieldTypeLoc, 10);
		dropdown.selectDropdownByDisplayName(typeEle, option);
		text.pressTab(typeEle);
	}

	/**
	 * Inputs the id for a new flex field
	 * Must be unique
	 *
	 * @param inputId
	 */
	public void inputFlexFieldId( String inputId )
	{
		WebElement idField = wait.waitForElementEnabled(flexFieldIdLoc, 10);
		text.enterText(idField, inputId);
		text.pressTab(idField);

	}

	/**
	 * Inputs the value for a new flex field
	 *
	 * @param inputValue
	 */
	public void inputFlexFieldValue( String inputValue )
	{
		List<WebElement> valueFieldLists = wait.waitForAllElementsPresent(flexFieldValueLoc, 15);
		for ( WebElement valueField : valueFieldLists )
		{
			try
			{
				click.clickElement(valueField);
				text.selectAllAndInputText(valueField, inputValue);
				text.pressTab(valueField);
				break;
			}
			catch ( ElementNotInteractableException e )
			{

			}
		}
	}
	/**
	 * Inputs the value for a new flex field
	 * @param flexSource
	 * @param flexType
	 * @param inputValue
	 */
	public void enterFlexValue(String flexSource, String flexType, String inputValue) {
		String value;
		WebElement valueField=null;
		List<WebElement> flexHeaders = driver.findElements(headers);
		for (int i = 0; i < flexHeaders.size(); i++) {
			String header = flexHeaders.get(i).getAttribute("abbr");
			if (header.contains(flexType) && header.contains("Constant") && flexSource.contains("Constant")) {
				value = String.format("//table[contains(@id,'BusinessGrid')]//td[@controlname='ValueConstant%s']/input", flexType);
				valueField = wait.waitForElementPresent(By.xpath(value));
				click.javascriptClick(valueField);

			}
			if(header.contains(flexType) && !header.contains("Constant") && !flexSource.contains("Constant")){
				value = String.format("//table[contains(@id,'BusinessGrid')]//td[@controlname='Value (%s)']/input", flexType);
				valueField = wait.waitForElementDisplayed(By.xpath(value));
			}
		}

		try {
			click.clickElementCarefully(valueField);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waitForPageLoad();
		text.enterText(valueField, inputValue);
		text.pressTab(valueField);

	}

	/**
	 * For constant flex source with type as date inputs the value
	 * @param date
	 * */
	public void inputFlexFieldValueDate(String date){
		WebElement constantDateField=wait.waitForElementPresent(dateValueField);
		click.javascriptClick(constantDateField);
		try {
			click.clickElementCarefully(constantDateField);
		} catch (Exception e) {
			e.printStackTrace();
		}
		text.enterText(constantDateField,date);
	}

	/**
	 * Gets the date value displayed in the input field for a flex field entry
	 */
	public String getDateFieldValue(){
		String loc= String.format("//tr[1]/td[@controlname='ValueConstantDate']/span");
		WebElement valueField = wait.waitForElementPresent(By.xpath(loc));
		String dateValue = attribute.getElementAttribute(valueField,"title");
		return dateValue;
	}

	/**
	 * Gets the error message that appears when inputting an invalid value
	 *
	 * @return error message
	 */
	public String getErrorMessage( )
	{
		WebElement error = wait.waitForElementDisplayed(errorMessageLoc);
		String errorText = error.getAttribute("aria-label");

		return errorText;
	}

	/**
	 * Deletes the flex field and returns to the admin page
	 *
	 * @return admin page
	 */
	public BusinessVertexAdminPage deleteFlexField( )
	{
		List<WebElement> deleteButtons = wait.waitForAllElementsPresent(deleteButtonLoc);
		WebElement deleteButton = deleteButtons.get(deleteButtons.size() - 1);
		click.clickElement(deleteButton);

		WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
		WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
		List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
		WebElement button = element.selectElementByText(buttonList, "Yes");
		click.clickElement(button);

		wait.waitForElementNotDisplayedOrStale(button, 15);

		return initializePageObject(BusinessVertexAdminPage.class);
	}

	/**
	 * when opening an existing flex field, ensure the toggle to edit mode
	 * button is disabled
	 *
	 * @return if field disabled return true, if enabled return false
	 */
	public boolean checkEditToggleDisabled( )
	{
		boolean isDisabled = false;
		WebElement toggle = wait.waitForElementPresent(editModeToggle);

		String classText = toggle.getAttribute("class");
		if ( classText.contains("is-disabled") )
		{
			isDisabled = true;
		}

		return isDisabled;
	}

	/**
	 * deletes the row from row level delete option
	 * @param rowIndex
	 */
	public void deleteRowWithMoreOptions(int rowIndex) {
		String moreOptionsButton=String.format("//div[@controlname='VER_Flex Fields List Part']//tbody/tr[%s]//a[@title='Show more options']",rowIndex);
		WebElement moreOptionsIcon= wait.waitForElementPresent(By.xpath(moreOptionsButton));
		click.clickElement(moreOptionsIcon);
		List<WebElement> moreOptions = wait.waitForAllElementsPresent(optionsLoc);
		WebElement deleteLine = element.selectElementByText(moreOptions, "Delete Line");
		click.clickElementCarefully(deleteLine);
	}
}
