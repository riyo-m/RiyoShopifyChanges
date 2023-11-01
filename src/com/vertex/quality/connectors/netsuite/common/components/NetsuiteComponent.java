package com.vertex.quality.connectors.netsuite.common.components;

import com.vertex.quality.common.components.VertexComponent;
import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.connectors.netsuite.common.pages.base.NetsuitePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.*;

import java.util.List;

/**
 * Represents all netsuite components
 * All other common and uncommon Netsuite components extend this
 *
 * @author hho
 */
public class NetsuiteComponent extends VertexComponent
{
	protected final By innerPopupDiv = By.id("inner_popup_div");
	protected final By dropDownParentContainerLocator = By.className("uir-tooltip");
	protected final By dropDownContainerLocator = By.className("uir-tooltip-content");
	protected final By dropDownClassLocator = By.className("dropdownDiv");
	protected final By inputReadOnlyClassLocator = By.className("inputreadonly");
	protected String dropdownDiv = "dropdownDiv";
	protected final By outerDiv = By.xpath("//div[@id='popup_outerdiv']");
	protected final By menuLocator = By.xpath("//div[@class='uir-tooltip-content']/div[@class='dropdownDiv']");
	protected final By menuItemLocator = By.xpath("//div[@class='uir-tooltip-content']/div[@class='dropdownDiv']/div");
	protected final By radioMenu = By.className("uir-onoff");
	protected final By radioLabel = By.id("filetype_fs_lbl");
	protected final By radioLabelButton = By.id("filetype_fs_inp");

	public NetsuiteComponent( final WebDriver driver, final NetsuitePage parent )
	{
		super(driver, parent);
	}

	/**
	 * Clicks on the dropdown list and sets the value of the dropdown list to value
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @param dropdownLoc the dropdown list locator
	 * @param key         the value to set
	 */
	public void setDropdownToValue( By dropdownLoc, String key )
	{
		WebElement dropdownElement = wait.waitForElementPresent(dropdownLoc);
		setDropdownToValue(dropdownElement, key);
	}

	/**
	 * Clicks on the dropdown list and sets the value of the dropdown list to value
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @param dropdownElement the dropdown list element
	 * @param key             the value to set
	 */
	public void setDropdownToValue( WebElement dropdownElement, String key )
	{
		String className = dropdownElement.getAttribute("class");
		if(!className.contains("active"))
		{
			click.clickElement(dropdownElement);
		}

		WebElement dropdownList = getDropdownListContainer();
		List<WebElement> dropdowns = wait.waitForAllElementsDisplayed(By.tagName("div"), dropdownList);

		WebElement dropdown = element.selectElementByText(dropdowns, key);
		if ( dropdown != null )
		{
			click.clickElement(dropdown);
		}
	}

	/**
	 * Used to get the web element that contains the drop down list items
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @return the web element that contains the drop down list items
	 */
	private WebElement getDropdownListContainer( )
	{
		WebElement dropDownElement = null;

		List<WebElement> tooltips = wait.waitForAllElementsPresent(dropDownParentContainerLocator);

		search:
		for ( WebElement tooltip : tooltips )
		{
			List<WebElement> tooltipsContent = element.getWebElements(dropDownContainerLocator, tooltip);

			for ( WebElement tooltipContent : tooltipsContent )
			{
				List<WebElement> dropdownListContainer = element.getWebElements(dropDownClassLocator, tooltipContent);

				dropDownElement = element.selectElementByAttribute(dropdownListContainer, dropdownDiv, "class");
				if ( dropDownElement != null )
				{
					break search;
				}
			}
		}
		return dropDownElement;
	}

	/**
	 * Checks if the value specified by key is in the drop down list
	 * Used because Netsuite does not use select elements for its dropdowns
	 *
	 * @param dropdownLoc the dropdown list locator
	 * @param key         the value to check for
	 */
	public boolean isDropdownValueInList( By dropdownLoc, String key )
	{
		boolean isDropDownValueInList = false;
		WebElement dropdownElement = wait.waitForElementPresent(dropdownLoc);
		click.clickElement(dropdownElement);

		WebElement dropdownList = getDropdownListContainer();
		List<WebElement> dropdownOptions = wait.waitForAllElementsDisplayed(By.tagName("div"), dropdownList);

		WebElement dropdownOption = element.selectElementByText(dropdownOptions, key);
		if ( dropdownOption != null )
		{
			isDropDownValueInList = true;
		}

		click.clickElement(dropdownElement);
		return isDropDownValueInList;
	}

	/**
	 * Selects Radio button on page
	 *
	 * @param label
	 */
	public void selectRadioButton(String label) {
		List<WebElement> radioRows = element.getWebElements(radioMenu);

		for (WebElement radioRow: radioRows) {
			WebElement radioLabelElement = element.getWebElement(radioLabel, radioRow);
			String radioLabelText = text.getElementText(radioLabelElement);
			if (label.equals(radioLabelText)) {
				WebElement radioButtonElement = element.getWebElement(radioLabelButton, radioRow);
				click.clickElement(radioButtonElement);
				break;
			}
		}
	}

	/**
	 * Gets any text element that is under a label
	 *
	 * @param parentContainerLocator the parent container of the label and the text
	 * @param labelID                the label's id locator
	 *
	 * @return the text element under the label
	 */
	public WebElement getTextUnderLabel( By parentContainerLocator, String labelID )
	{
		WebElement correctParentContainerElement = null;
		List<WebElement> parentContainerElements = wait.waitForAllElementsPresent(parentContainerLocator);

		for ( WebElement parentContainerElement : parentContainerElements )
		{
			List<WebElement> labelElements = wait.waitForAllElementsPresent(By.tagName("span"), parentContainerElement);

			WebElement labelElement = element.selectElementByAttribute(labelElements, labelID, "id");
			if ( labelElement != null )
			{
				correctParentContainerElement = parentContainerElement;
				break;
			}
		}

		WebElement textElement = wait.waitForElementDisplayed(inputReadOnlyClassLocator, correctParentContainerElement);
		return textElement;
	}

	/**
	 * Clicks on an item in the special dropdown items list
	 *
	 * @param key the item locator inside the list
	 */
	public void clickDropdownListMenuItem( String key )
	{
		WebElement menuItem;
		// Determine which type of dropdown menu we're interacting with
		if(element.isElementPresent(outerDiv) )
		{
			menuItem = getDropdownListItem(key);
		}else{
			menuItem = getAltDropdownListItem(key);
		}
		wait.waitForElementDisplayed(menuItem);
		click.clickElementCarefully(menuItem);
	}

	/**
	 * Gets the item web element inside the special dropdown items list
	 *
	 * @param key the item web element's value
	 *
	 * @return the item web element
	 */
	protected WebElement getDropdownListItem( String key )
	{
		WebElement dropDownMenuItem = null;

		// Netsuite uses preloaded detached div elements when rendering pop ups to improve performance
		// These detached divs may share their ID, Class, or both with active elements causing unexpected search behaviour
		By outer = By.xpath("//div[@id='popup_outerdiv']");
		By inner = By.xpath("//div[@id='inner_popup_div']/table/tbody");
		WebElement outerPopup = wait.waitForElementDisplayed(outer);

		// Debounce dropdown search results
		jsWaiter.sleep(1000);

		dropDownMenuItem = new WebDriverWait(driver, 5 )
				.until( (driver) ->
				{	// Grab list option by comparing innerText
					WebElement dropDownMenu  = driver.findElement(new ByChained(outer, inner) );
					List<WebElement> options = dropDownMenu.findElements(By.xpath("//tr/td[2]") );

					for (WebElement child: options ) {
						if(child.getText().equals(key) ){
						return child;
						}
					}
						return null;
				});
			return dropDownMenuItem;
	}

	/**
	 * Selects option from alternative dropdown menu primarily found in Suite Tax
	 * @param key
	 * @return
	 */
	protected WebElement getAltDropdownListItem( String key)
	{
		WebElement dropDownMenuItem = null;
		// Debounce dropdown search results
		jsWaiter.sleep(250);

		dropDownMenuItem = new WebDriverWait(driver, 5 )
				.until( (driver) ->
				{	// Grab list option by comparing innerText
					List<WebElement> options = driver.findElements(menuItemLocator);

					for (WebElement child: options ) {
						if(child.getText().equals(key) ){
							return child;
						}
					}
					return null;
				});
		return dropDownMenuItem;
	}

	/**
	 * Searches for the search term and selects it if found
	 *
	 * @param searchField  the search textfield's locator
	 * @param searchTerm   the search term to input
	 * @param searchButton the search button's locator
	 */
	public void inputSearchTerm( By searchField, String searchTerm, By searchButton )
	{
		text.enterText(searchField, searchTerm);
		click.clickElement(searchButton);
		attribute.waitForElementAttributeChange(innerPopupDiv, "innerText", DEFAULT_TIMEOUT);

		WebElement searchTermElement = getDropdownListItem(searchTerm);
		click.clickElement(searchTermElement);
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
	public WebElement getElementInHoverableMenu( By actionHoverLoc, By actionSubmenusLoc, String buttonText )
	{
		WebElement actionHover = wait.waitForElementPresent(actionHoverLoc);
		hover.hoverOverElement(actionHover, PageScrollDestination.VERT_CENTER);

		WebElement deleteButton = null;

		WebElement actionsSubmenu = wait.waitForElementDisplayed(actionSubmenusLoc);
		List<WebElement> hyperLinks = wait.waitForAllElementsDisplayed(By.tagName("a"), actionsSubmenu);

		deleteButton = element.selectElementByNestedLabel(hyperLinks, By.tagName("span"), buttonText);
		return deleteButton;
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
	public String getDataFromTextField( By textFieldLoc )
	{
		String valueAttribute = "value";
		WebElement textField = wait.waitForElementPresent(textFieldLoc);
		scroll.scrollElementIntoView(textField);

		String value = textField.getAttribute(valueAttribute);
		return value;
	}

	/**
	 * Forcibly clears the given text field
	 *
	 * @param by the locator for the text field
	 */
	public void forceClear( final By by )
	{
		WebElement element = wait.waitForElementPresent(by);
		forceClear(element);
	}

	/**
	 * Forcibly clears the given text field
	 *
	 * @param element the text field element
	 */
	public void forceClear( final WebElement element )
	{
		element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
	}

	/**
	 * Checks if the alert contains a message
	 *
	 * @param message the message to check for
	 *
	 * @return if the alert contains the message
	 */
	public boolean doesAlertContain( String message )
	{
		boolean doesAlertContain = false;

		String alertText = alert.getAlertText();

		if ( alertText != null && alertText.contains(message) )
		{
			doesAlertContain = true;
		}

		return doesAlertContain;
	}
}
