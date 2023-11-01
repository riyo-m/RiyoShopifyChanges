package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the flex fields page
 *
 * @author bhikshapathi
 */
public class NavFlexFieldsPage extends NavBasepage
{protected By dialogBoxLoc = By.className("ms-nav-content-box");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By errorMessageLoc = By.className("ms-nav-validationicon-error");
    protected By errorMessageText = By.xpath("//p[contains(@id,'validation_paragraph')]");
    protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
    protected By editModeToggle = By.cssSelector("button[title='Make changes on the page.']");
    protected By flexFieldSourceLoc = By.xpath("//div/a[contains(.,'Flex Field Source')]/../div/select");
    protected By flexFieldTypeLoc = By.xpath("//div/a[contains(.,'Flex Field Type')]/../div/select");
    protected By flexFieldIdLoc = By.xpath("//div/a[contains(.,'Flex Field ID')]/../div/input");
    protected By flexFieldValueLoc = By.xpath("//div/a[contains(.,'Value')]/../div/input[@tabindex='0']");
    protected By flexFieldValueLocForConstant= By.xpath("//div/a[contains(.,'Value')]/../div/input[@title='Type the date in the format M/d/yyyy']");
    protected By flexFieldValueDateLoc = By.cssSelector(
            "input[aria-label*='Value,'][title='Type the date in the format M/d/yyyy']");

    protected By deleteButtonLoc = By.cssSelector("i[data-icon-name='Delete']");
    protected By backButtonLoc = By.cssSelector("button[title='Back']");
    protected By buttonLoc = By.tagName("button");

    public NavFlexFieldsPage( WebDriver driver ) { super(driver); }

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
        String attribute=typeEle.getAttribute("title");
        while(!attribute.equalsIgnoreCase(option)){
            attribute=typeEle.getAttribute("title");
        }
    }

    /**
     * Inputs the id for a new flex field
     * Must be unique
     *
     * @param inputId
     */
    public void inputFlexFieldId( String inputId )
    {
        WebElement idField = wait.waitForElementEnabled(flexFieldIdLoc, 15);
        click.clickElementCarefully(idField);
        text.enterText(idField, inputId);
        text.pressEnter(idField);
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
            if (element.isElementDisplayed(valueField))
            {
                try
                {
                    click.clickElement(valueField);
                    text.setTextFieldCarefully(valueField, inputValue);
                    text.pressEnter(valueField);
                    break;
                }
                catch ( ElementNotInteractableException e )
                {

                }
            }

        }
    }
    /**
     * Inputs the date into the value field for a new flex field
     * when using a constant value
     *
     * @param date
     */
    public void inputFlexFieldConstantValueDate( String date )
    {
        WebElement valueField = wait.waitForElementEnabled(flexFieldValueDateLoc);

        try
        {
            click.clickElement(valueField);
            text.enterText(valueField, date);
            text.pressEnter(valueField);
        }
        catch ( ElementNotInteractableException e )
        {

        }
    }
    /**
     * Get the current date value displayed in the input field
     *
     * @return the String value in the input field
     */
    public String getDateFieldInput( )
    {
        wait.waitForElementDisplayed(flexFieldValueDateLoc);
        WebElement valueField = wait.waitForElementEnabled(flexFieldValueDateLoc);

        try
        {
            click.clickElement(valueField);
        }
        catch ( ElementNotInteractableException e )
        {

        }

        String txt = valueField.getAttribute("value");

        return txt;
    }

    /**
     * Gets the error message that appears when inputting an invalid value
     *
     * @return error message
     */
    public String getErrorMessage( )
    {
        wait.waitForElementDisplayed(errorMessageLoc);
        WebElement error = wait.waitForElementPresent(errorMessageText);
        String errorText = error.getText();
        return errorText;
    }

    /**
     * Deletes the flex field and returns to the admin page
     *
     * @return admin page
     */
    public NavVertexAdminPage deleteFlexField( )
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

        return initializePageObject(NavVertexAdminPage.class);
    }

    /**
     * Clicks the back button to close the flex field page and return to the admin page
     *
     * @return Vertex admin page
     */
    public NavVertexAdminPage closeFlexFieldsPage( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(backButtonLoc);
        WebElement backButton = buttonList.get(buttonList.size() - 1);

        click.clickElement(backButton);

        return initializePageObject(NavVertexAdminPage.class);
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
}
