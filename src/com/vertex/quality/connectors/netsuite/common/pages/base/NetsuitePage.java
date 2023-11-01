package com.vertex.quality.connectors.netsuite.common.pages.base;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.netsuite.common.components.NetsuiteComponent;
import com.vertex.quality.connectors.netsuite.common.enums.NetsuitePageTitles;
import com.vertex.quality.connectors.netsuite.common.tests.base.NetsuiteBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;


/**
 * Represents netsuite pages. Contains common functions to be used throughout netsuite
 *
 * @author hho
 */
public abstract class NetsuitePage extends VertexPage
{
	private NetsuiteComponent component;

	public NetsuitePage( WebDriver driver )
	{
		super(driver);
		component = new NetsuiteComponent(driver, this);
	}

	/**
	 * Clicks on the dropdown list and sets the value of the dropdown list to value
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @param dropdownLoc the dropdown list locator
	 * @param key         the value to set
	 */
	protected void setDropdownToValue( By dropdownLoc, String key )
	{
		component.setDropdownToValue(dropdownLoc, key);
	}

	/**
	 * Clicks on the dropdown list and sets the value of the dropdown list to value
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @param dropdownElement the dropdown list element
	 * @param key             the value to set
	 */
	protected void setDropdownToValue( WebElement dropdownElement, String key )
	{
		component.setDropdownToValue(dropdownElement, key);
	}

	/**
	 * Checks if the value specified by key is in the drop down list
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @param dropdownLoc the dropdown list locator
	 * @param key         the value to check for
	 *
	 * @return if the value is in the drop down list
	 */
	protected boolean isDropdownValueInList( By dropdownLoc, String key )
	{
		return component.isDropdownValueInList(dropdownLoc, key);
	}

	/**
	 * Selects Radio button
	 * @param radioLabel
	 */
	protected void selectRadioButton(String radioLabel)
	{
		component.selectRadioButton(radioLabel);
	}

	/**
	 * Gets any text element that is under a label
	 *
	 * @param parentContainerLocator the parent container of the label and the text
	 * @param labelID                the label's id locator
	 *
	 * @return the text element under the label
	 */
	protected WebElement getTextUnderLabel( By parentContainerLocator, String labelID )
	{
		return component.getTextUnderLabel(parentContainerLocator, labelID);
	}

	/**
	 * Clicks on an item in the special dropdown items list
	 *
	 * @param key the item locator inside the list
	 */
	protected void clickDropdownListMenuItem( String key )
	{
		component.clickDropdownListMenuItem(key);
	}

	/**
	 * Searches for the search term and selects it if found
	 *
	 * @param searchField  the search textfield's locator
	 * @param searchTerm   the search term to input
	 * @param searchButton the search button's locator
	 */
	protected void inputSearchTerm( By searchField, String searchTerm, By searchButton )
	{
		component.inputSearchTerm(searchField, searchTerm, searchButton);
	}

	/**
	 * Finds the button in the menu that first needs to be hovered over
	 *
	 * @param actionHoverLoc    the menu locator
	 * @param actionSubmenusLoc the submenu's locator
	 * @param buttonText        the text of the button to click
	 *
	 * @return the button
	 */
	protected WebElement getElementInHoverableMenu( By actionHoverLoc, By actionSubmenusLoc, String buttonText )
	{
		return component.getElementInHoverableMenu(actionHoverLoc, actionSubmenusLoc, buttonText);
	}

	/**
	 * Gets data from the text field (drop down values are considered a text field)
	 * Most values of textfields in Netsuite are only shown in the value
	 * attribute
	 *
	 * @param textFieldLoc the text field locator
	 *
	 * @return the data from the text field
	 */
	protected String getDataFromTextField( By textFieldLoc )
	{
		return component.getDataFromTextField(textFieldLoc);
	}

	/**
	 * Forcibly clears the given text field
	 *
	 * @param by the locator for the text field
	 */
	protected void forceClear( final By by )
	{
		component.forceClear(by);
	}

	/**
	 * Forcibly clears the given text field
	 *
	 * @param element the text field element
	 */
	protected void forceClear( final WebElement element )
	{
		component.forceClear(element);
	}

	/**
	 * Checks if the alert contains a message
	 *
	 * @param message the message to check for
	 *
	 * @return if the alert contains the message
	 */
	protected boolean doesAlertContain( String message )
	{
		return component.doesAlertContain(message);
	}

	/**
	 * Gets the page class from its title
	 *
	 * @param pageTitle the page title
	 *
	 * @return the page class
	 */
	protected Class<? extends NetsuitePage> getPageClass( String pageTitle )
	{
		return NetsuiteBaseTest.pageFactory.getPageClass();
	}

	/**
	 * Determines which environment
	 *
	 * @return the environment logged into
	 */
	protected boolean isSingleCompany( )
	{
		return getPageTitle().contains("Single Company");
	}

	/**
	 * Edit a record on any Page
	 */
	public void editRecord()
	{
		click.clickElement(By.id("edit"));
		waitForPageLoad();
	}

	/**
	 * Delete a record on any Page
	 */
	public void deleteRecord()
	{
		try {
			WebElement delete = element.getWebElement(By.id("delete"));
				if(delete.isDisplayed()){
					delete.click();
					alert.acceptAlert(5);
					waitForPageLoad();
					driver.navigate().back();
					return;
				}

		}catch(NoSuchElementException e) {

		}

		try {
			hover.hoverOverElement(By.id("spn_ACTIONMENU_d1"));
			jsWaiter.sleep(500);
				click.clickElement(By.xpath("//*[text()='Delete']"));
				alert.acceptAlert(5);
				waitForPageLoad();
				driver.navigate().back();
		}catch (NoSuchElementException e) {

		}
		//Wait for confirmation banner

		waitForPageLoad();
	}

	/**
	 * Keep track of records created during testing
	 * @return
	 */
	public String registerRecord()
	{
		String recordId = null;
		return recordId;
	}
}
