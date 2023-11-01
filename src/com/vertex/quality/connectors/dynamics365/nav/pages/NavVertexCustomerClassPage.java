package com.vertex.quality.connectors.dynamics365.nav.pages;

import com.vertex.quality.connectors.dynamics365.business.pages.base.BusinessBasePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.base.NavBasepage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Representation of the Customer Class page
 *
 * @author bhikshapathi
 */
public class NavVertexCustomerClassPage extends NavBasepage
{   protected By dialogBoxLoc = By.className("ms-nav-content-box");
    protected By overlayLoc = By.className("spa-overlay");

    protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
    protected By newButtonLoc = By.cssSelector("a[aria-label='New'][title='Create a new entry.']");
    protected By deleteButtonLoc = By.cssSelector("a[aria-label='Delete'][title='Delete the selected row.']");
    protected By customerClassTableLoc = By.cssSelector("table[id*='_BusinessGrid'] tbody");

    protected By dialogHeaderCon = By.className("task-dialog-header");
    protected By dialogMessageCon = By.className("dialog-title");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");

    protected By buttonLoc = By.tagName("button");
    protected By tableRowTag = By.tagName("tr");
    protected By tableCellTag = By.tagName("td");

    public NavVertexCustomerClassPage( WebDriver driver ) { super(driver); }

    /**
     * Clicks the new button to create a new customer class on the table
     */
    public void clickNewClassButton( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(newButtonLoc);
        WebElement newButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(newButton);
    }

    /**
     * Clicks the delete button to delete the selected row
     * and confirms the deletion
     */
    public void clickAndConfirmDeleteButton( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(deleteButtonLoc);
        WebElement deleteButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(deleteButton);

        dialogBoxClickYes();
    }

    /**
     * Gets the index for the last row of the table by getting the size of the list of all rows
     *
     * @return table length/last row non-zero index
     */
    public int getLastRowIndex( )
    {
        List<WebElement> tablesList = wait.waitForAllElementsPresent(customerClassTableLoc);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);

        int length = tableRows.size();

        return length;
    }

    /**
     * when activating a row, get the correct cell to click on
     *
     * @param rowIndex row cell is located on
     *
     * @return the cell on the specified row
     */
    public WebElement getRowActivationCell( int rowIndex )
    {
        rowIndex--;
        WebElement cell = null;
        int columnIndex = 0;

        List<WebElement> tablesList = wait.waitForAllElementsPresent(customerClassTableLoc);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(tableCellTag, row);
            cell = rowColumns.get(columnIndex);
        }

        return cell;
    }

    /**
     * locates and returns the cell containing the customer class code
     *
     * @param rowIndex row cell is located on
     *
     * @return the cell on the specified row
     */
    public WebElement getCodeCell( int rowIndex )
    {
        rowIndex--;
        WebElement cell = null;
        int columnIndex = 2;

        List<WebElement> tablesList = wait.waitForAllElementsPresent(customerClassTableLoc);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(tableCellTag, row);
            WebElement cellCon = rowColumns.get(columnIndex);
            cell = element.getWebElement(By.tagName("input"), cellCon);
        }

        return cell;
    }

    /**
     * locates and returns the cell containing the options list
     *
     * @param rowIndex row cell is located on
     *
     * @return the cell on the specified row
     */
    public WebElement getOptionsCell( int rowIndex )
    {
        rowIndex--;
        WebElement cell = null;
        int columnIndex = 3;

        List<WebElement> tablesList = wait.waitForAllElementsPresent(customerClassTableLoc);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(tableCellTag, row);
            WebElement cellCon = rowColumns.get(columnIndex);
            cell = element.getWebElement(By.tagName("input"), cellCon);
        }

        return cell;
    }

    /**
     * locates and returns the cell containing the customer class name
     *
     * @param rowIndex row cell is located on
     *
     * @return the cell on the specified row
     */
    public WebElement getNameCell( int rowIndex )
    {
        rowIndex--;
        WebElement cell = null;
        int columnIndex = 4;

        List<WebElement> tablesList = wait.waitForAllElementsPresent(customerClassTableLoc);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(tableCellTag, row);
            WebElement cellCon = rowColumns.get(columnIndex);
            cell = element.getWebElement(By.tagName("input"), cellCon);
        }

        return cell;
    }

    public WebElement getRowByCode( String codeToFind )
    {

        WebElement cell = null;
        int columnIndex = 2;

        List<WebElement> tablesList = wait.waitForAllElementsPresent(customerClassTableLoc);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);
        int rowIndex = 0;
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(tableCellTag, row);
            cell = rowColumns.get(columnIndex);
            String cellText = cell.getText();

            if ( cellText.equals(codeToFind) )
            {
                click.clickElement(cell);
                break;
            }

            rowIndex++;
        }

        return cell;
    }

    /**
     * clicks on the very first cell in a row to make it interactable
     *
     * @param rowIndex
     */
    public void activateRow( int rowIndex )
    {
        WebElement activateCell = getRowActivationCell(rowIndex);
        try
        {
            click.clickElement(activateCell);
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(activateCell);
        }
    }

    public void enterClassCode( int rowIndex, String code )
    {
        WebElement codeCell = getCodeCell(rowIndex);

        click.clickElement(codeCell);

        text.enterText(codeCell, code);
    }

    public void enterClassName( int rowIndex, String name )
    {
        WebElement nameCell = getNameCell(rowIndex);

        click.clickElement(nameCell);

        text.enterText(nameCell, name);
    }

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
        wait.waitForElementNotDisplayedOrStale(backArrow, 10);
    }
}
