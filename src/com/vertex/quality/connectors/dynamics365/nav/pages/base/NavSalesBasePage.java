package com.vertex.quality.connectors.dynamics365.nav.pages.base;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.nav.components.NavSalesEditPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.nav.components.NavShippingAndBillingComponent;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavPrintPreviewPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavSalesCreditMemoPage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavSalesInvoicePage;
import com.vertex.quality.connectors.dynamics365.nav.pages.NavSalesReturnOrderPage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * represents the base page for all the different pages that edit sales transactions
 * anything from sales quotes to sales orders, creating new or editing an existing one
 *
 * @author bhikshapathi,dpatel
 */
public class NavSalesBasePage extends VertexPage
{
    public NavSalesEditPagesNavMenu salesEditNavMenu;
    public NavShippingAndBillingComponent shippingAndBillingComponent;

    protected By dialogBoxLoc = By.className("ms-nav-content");
    protected By categoryHeadersLocs = By.className("ms-nav-columns-caption");
    protected By mainFrameLoc = By.className("designer-client-frame");

    protected By itemTableConLoc = By.className("hosting-list-last");
    protected By addressFieldLoc = By.xpath("(//div/a[contains(.,'Address')]/../div/input)[3]");
    protected By nameFieldLoc = By.xpath("(//div/a[contains(.,'Name')]/../div/input)[3]");
    protected By cityFieldLoc = By.xpath("(//div/a[contains(.,'City')]/../div/input)[3]");
    protected By stateFieldLoc = By.xpath("(//div/a[contains(.,'State')]/../div/input)[2]");
    protected By zipFieldLoc = By.xpath("(//div/a[contains(.,'ZIP Code')]/../div/input)[3]");
    protected By documentNumberInputLoc = By.cssSelector("input[aria-label='No., (Blank)']");
    protected By commonTable = By.cssSelector("table[id*='_BusinessGrid'] tbody");

    protected By backAndSaveArrowButtonLoc = By.cssSelector("div[class='icon-Dismiss dialog-close']");
    protected By deleteButtonLoc = By.cssSelector("button[title='Delete the information.']");
    protected By editModeToggle = By.cssSelector("button[title='Make changes on the page.']");
    protected By readOnlyModeToggle = By.cssSelector("button[title='Open the page in read-only mode.']");
    protected By itemAutofillBoxLoc = By.className("ms-nav-content-box");
    protected By itemAutofillOptionsLoc = By.cssSelector("a[title*='Select record ']");
    protected By itemNumberCellLoc = By.cssSelector("input[aria-label*='No.,']");
    protected By itemQuantity = By.cssSelector("input[aria-label*='Quantity,']");
    protected By dueDateFieldLoc = By.cssSelector("input[aria-label*='Due Date,']");
    protected By locationCodeOfInvoiceDetails=By.xpath("//a[text()='Location Code' and @class='ms-nav-edit-control-caption']");
    protected By invoiceDetailsShowMore=By.xpath("//button[@aria-label='Invoice Details, Show more']");
    protected By locationFieldLoc = By.cssSelector("[aria-label*='Location Code,']");
    protected By taxAreaCodeFieldLoc = By.cssSelector("[aria-label*='Tax Area Code,']");
    protected By taxRateFieldLoc = By.xpath("//table[@summary='VER_Sales Line Tax Details']/tbody/tr/td[12]/span");
    protected By taxAmountFieldLoc = By.xpath("//table[@summary='VER_Sales Line Tax Details']/tbody/tr/td[13]/span");
    protected By statTaxAmount = By.xpath("//a[text()='VERTEX'][@class]/..//span");
    protected By statNoVertexTaxAmount = By.xpath("//a[text()='ATLANTA, GA'][@class]/..//span");
    protected By taxJurisdictionFieldLoc = By.cssSelector("span[aria-label*='Jurisdiction, ']");
    protected By totalTaxAmountLoc = By.xpath("//div/a[contains(.,'Total Tax (USD)')]/../div/span");
    protected By totalExclTaxAmount= By.xpath("//div/a[contains(.,'Total Excl. Tax (USD)')]/../div/span");
    protected By totalInclTaxAmount= By.xpath("//div/a[contains(.,'Total Incl. Tax (USD)')]/../div/span");
    protected By customerNameLoc = By.cssSelector("input[aria-label*='Customer Name, '][title='Look up value']");
    protected By sellToCustomerNumberLoc = By.cssSelector("span[aria-label*='Sell-to Customer No.,']");
    protected By sellToCustomerNameLoc = By.cssSelector("span[aria-label*='Sell-to Customer Name,']");
    protected By assignToQuantityLoc = By.cssSelector("input[aria-label*='Qty. to Assign,']");
    protected By orderNumberLoc = By.cssSelector("span[aria-label*='Order No.,']");
    protected By quoteNumberLoc = By.xpath("//div/a[contains(.,'Quote No.')]/../div/span");
    protected By documentTypeSelectLoc = By.xpath("//a[text()='Document Type']/..//div/select");
    protected By documentNumberFieldLoc = By.xpath("//a[text()='Document No.']/..//div/input[@type='text']");
    protected By shipToDropdownLoc = By.xpath("//div/select[@title='Default (Sell-to Address)']");
    protected By paymentDiscount = By.xpath("(//div/a[contains(.,'Payment Discount %')]/../div/input)[1]");

    protected By loadingConLoc = By.className("ms-nav-exceptiondialogcontainer");
    protected By tableParentConLoc = By.cssSelector(".spa-view.spa-lookup.no-animations.shown");
    protected By locationcodefield = By.xpath("(//div/a[contains(.,'Location Code')])[3]/../div/input[1]");
    protected By moreOption = By.xpath("(//span[@aria-label='More options'])[1]");
    protected By locCodePopUp = By.xpath("//div[@class='ms-nav-content']");
    protected By alertOkButtonLoc = By.className("walkme-custom-balloon-button");
    protected By tableConLoc = By.className("ms-nav-scrollable");
    protected By itemAssignmentTableConLoc = By.className("ms-nav-worksheetform");
    protected By dialogHeaderCon = By.className("task-dialog-header");
    protected By dialogMessageCon = By.className("dialog-title");
    protected By dialogContentCon = By.className("task-dialog-content-container");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By previewContainer = By.className("ms-nav-pdf-container");
    protected By dropDown = By.xpath("//td[@class='edit-container ms-nav-enumeration edit']/select");
    protected By expandButton = By.xpath("//a[(@title='Collapse the FactBox pane') and (not(@tabindex))]");
    protected By exitExpandButton = By.xpath("//a[(@title='Expand the FactBox pane') and (not(@tabindex))]");
    protected By saveAndCloseButton = By.xpath("//div[@class='icon-Dismiss dialog-close']");
    protected By ok = By.xpath("//span[contains(.,'OK')]");
    protected By no = By.xpath("//button[contains(.,'No')]");
    protected By yes = By.xpath("//button[contains(.,'Yes')]");
    protected By highestValue = By.xpath("(//li/input[@type='radio'])[1]");
    protected By lowestValue = By.xpath("(//li/input[@type='radio'])[2]");
    protected By noTaxDetails = By.xpath("//p[contains(.,'No tax details on current document')]");
    protected By deleteLine = By.xpath("//span[contains(.,'Delete Line')]");
    protected By customerListFieldLoc = By.xpath("(//div/a[contains(.,'Customer Name')]/../div/input)[1]");
    protected By acceptDelete = By.xpath("//div[@class='ms-nav-actionbar-container has-actions']/button/span[contains(.,'Yes')]");
    protected By includeHeaderButtonLoc=By.xpath("//div/a[contains(.,'Include Header')]/../div/input");
    protected By recalculateLinesLoc=By.xpath("//div/a[contains(.,'Recalculate Lines')]/../div/input");
    protected By close = By.xpath("//button[@title='Close'][not(@disabled)]");
    protected By buttonLoc = By.tagName("button");
    protected By tableRowTag = By.tagName("tr");
    protected By tableCellTag = By.tagName("td");
    protected By taxGroupAlert = By.cssSelector("div.ms-nav-edit-control-container.no-caption.staticstringcontrol-container");
    protected By noTaxesDialog = By.cssSelector("div.dialog-title");
    protected By addressCleansePopUpMessage = By.xpath("//p[@class='staticstringcontrol' and not(@tabindex)]");
    protected By radioField=By.xpath("//li/input[@type='radio']");
    protected By revertLink=By.linkText("revert the change");
    protected final int shortTimeout = 5;

    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    String transactionDate = sdf.format(date);

    public NavSalesBasePage( WebDriver driver )
    {
        super(driver);
        this.shippingAndBillingComponent = new NavShippingAndBillingComponent(driver, this);
        this.salesEditNavMenu = new NavSalesEditPagesNavMenu(driver, this);
    }

    /**
     * locates the back arrow and clicks on it to save the changes on the page and close it
     */
    public void clickBackAndSaveArrow( )
    {   window.switchToFrame(mainFrameLoc,30);
        waitForPageLoad();
        WebElement backArrow =wait.waitForElementEnabled(backAndSaveArrowButtonLoc);
        waitForPageLoad();
        click.clickElementCarefully(backArrow);
        wait.waitForElementNotDisplayedOrStale(backArrow, 10);
    }
    /**
     * clicks the delete button and confirms to delete the document
     */
    public void deleteDocument( )
    {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(deleteButtonLoc);
        WebElement deleteButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(deleteButton);
        jsWaiter.waitForLoadAll();
        dialogBoxClickYes();
    }

    /**
     * Select the confidence Indicator
     * @bhikshapathi
     */
    public void selectConfidenceIndicator(String value)
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
        if(value.equals("80")){
            WebElement heighest=wait.waitForElementDisplayed(highestValue);
            click.javascriptClick(heighest);
        }
        if(value.equals("20")){
            WebElement lowest=wait.waitForElementDisplayed(lowestValue);
            click.javascriptClick(lowest);
        }
        WebElement okButton=wait.waitForElementDisplayed(ok);
        click.javascriptClick(okButton);
    }
    /**
     * when in read only mode, clicks the edit button
     * to make the customer card editable
     */
    public void toggleEditMode( )
    {
        WebElement toggle = wait.waitForElementPresent(editModeToggle);
        click.clickElement(toggle);
        wait.waitForElementNotPresent(editModeToggle);
    }

    /**
     * when in edit mode, clicks the read only button
     * to make the customer card read only
     */
    public void toggleReadOnlyMode( )
    {
        WebElement toggle = wait.waitForElementPresent(readOnlyModeToggle);
        click.clickElement(toggle);
        wait.waitForElementNotPresent(readOnlyModeToggle);
    }

    /**
     * Get the sales document number from the current page
     *
     * @return document number as string
     */
    public String getCurrentSalesNumber( )
    {
        String fullTitle = getPageTitle();

        String docTypeAndNumber = fullTitle.substring(fullTitle.indexOf("-") + 1, fullTitle.indexOf("∙") - 1);

        String number = docTypeAndNumber
                .substring(docTypeAndNumber.indexOf("-") + 1)
                .trim();

        return number;
    }

    /**
     * If a sales order was used in creating the current sales edit page,
     * get the order number displayed
     *
     * @return order number as string
     */
    public String getOrderNumber( )
    {
        List<WebElement> fieldList = wait.waitForAllElementsPresent(orderNumberLoc);
        WebElement orderNumberField = fieldList.get(fieldList.size() - 1);
        String orderNum = text.getElementText(orderNumberField);

        return orderNum;
    }

    /**
     * If a sales quote was used in creating the current sales edit page,
     * get the quote number displayed
     * @return quote number as string
     */
    public String getQuoteNumber( )
    {
        List<WebElement> fieldList = wait.waitForAllElementsPresent(quoteNumberLoc);
        WebElement quoteNumberField = fieldList.get(fieldList.size() - 1);
        String quoteNum = text.getElementText(quoteNumberField);

        return quoteNum;
    }

    /**
     * Gets the current customer name, assuming a customer number has already
     * been input
     * @return the customer name as a String
     */
    public String getCurrentCustomerName( )
    {
        List<WebElement> fieldList = wait.waitForAllElementsPresent(customerNameLoc);
        WebElement nameField = fieldList.get(fieldList.size() - 1);
        String custName = nameField.getAttribute("value");

        return custName;
    }
    /**
     * Gets No Tax Areas pop up Text
     * @return noTaxText
     * @author bhikshapathi
     */
    public String getNoTaxAreasText( )
    {
        WebElement dueDateField = wait.waitForElementPresent(noTaxesDialog);
        String noTaxText = dueDateField.getText();
        click.clickElementCarefully(ok);
        return noTaxText;
    }

    /**
     * locates the order due date field and enters today's date in it.
     */
    public void setDueDate( )
    {
        WebElement dueDateField = wait.waitForElementPresent(dueDateFieldLoc);
        text.enterText(dueDateField, transactionDate);
        text.enterText(dueDateField, transactionDate);
    }

    /**
     * clicks on the very first cell in a row to make it interactable
     * @param rowIndex
     */
    public void activateRow( int rowIndex )
    {
        rowIndex--;
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

    /**
     * when activating a row, get the correct cell to click on
     * @param rowIndex row cell is located on
     * @return the cell on the specified row
     */
    public WebElement getRowActivationCell( int rowIndex )
    {
        WebElement cell = null;
        int columnIndex = 0;

        WebElement tableParentCon = wait.waitForElementPresent(itemTableConLoc);
        WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
            cell = rowColumns.get(columnIndex);
        }

        return cell;
    }

    /**
     * locates the type field on the item table and enters the type in
     *
     * @param type
     * @param rowIndex
     */
    public void setItemType( String type, int rowIndex )
    {
        WebElement itemTypeField = getTableCell(rowIndex, 2);
        wait.waitForElementEnabled(itemTypeField);
        scroll.scrollElementIntoView(itemTypeField,PageScrollDestination.BOTTOM);
        click.clickElementCarefully(itemTypeField);
        waitForPageLoad();
        wait.waitForElementDisplayed(dropDown);
        waitForPageLoad();
        dropdown.selectDropdownByDisplayName(dropDown,type);
        waitForPageLoad();
        text.pressTab(itemTypeField);
    }
    /**
     * Returns to the first cell of the table by clicking on the Type cell which is always visible,
     * and pressing tab
     * This is because it is not possible to scroll on the table, to avoid errors when the first
     * cells of the table are not visible and therefore not interactable
     *
     * @param rowIndex
     */
    public void returnToFirstCell( int rowIndex )
    {
        int index = rowIndex - 1;
        WebElement itemTypeField = getTableCell(index, 2);
        wait.waitForElementEnabled(itemTypeField);
        click.clickElement(itemTypeField);
        text.pressTab(itemTypeField);
    }
    /**
     * locates the item number field and enters the item number in it.
     * @param itemNumber desired item number as a string
     * @param rowIndex   which row to write to
     */
    public void enterItemNumber( String itemNumber, int rowIndex )
    {
        String itemNumberXpath = String.format("(//table[contains(@class,'ms-nav-grid-edit')][@summary]//tr)[%s]//td[%s]/input[1]",rowIndex+2,6);
        WebElement itemNumberField = wait.waitForElementPresent(By.xpath(itemNumberXpath));
        if (!element.isElementDisplayed(itemNumberField))
        {
            exitExpandTable();
            expandTable();
        }
        else
        {
            click.clickElementCarefully(itemNumberField);
        }
        waitForPageLoad();
        text.enterText(itemNumberField, itemNumber);
        text.pressTab(itemNumberField);
    }
    /**
     * when entering the item number into the item table,
     * wait for the autofill box to appear and select the item
     * @param itemNumber number to write and select
     * @param rowIndex   which row to write to
     */
    public void setItemNumberInfo( String itemNumber, int rowIndex )
    {
        enterItemNumber(itemNumber, rowIndex);
        WebElement box = wait.waitForElementDisplayed(itemAutofillBoxLoc);
        List<WebElement> optionsList = wait.waitForAllElementsDisplayed(itemAutofillOptionsLoc, box);

        for ( WebElement option : optionsList )
        {
            String foundNumber = text.getElementText(option);
            if ( itemNumber.equals(foundNumber) )
            {
                click.clickElementCarefully(option);
                wait.waitForElementNotDisplayedOrStale(box, 15);
                break;
            }
        }
    }
    /**
     * locates the description field and enters the description to it
     * @param itemDescription
     * @param rowIndex
     */
    public void enterItemDescription( String itemDescription, int rowIndex )
    {
        WebElement descriptionField = getTableCell(rowIndex, 5);
        try
        {
            descriptionField.click();
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(descriptionField);
        }
        text.enterText(descriptionField, itemDescription);
    }
    /**
     * locates the location field and enters the location code to it
     * @param locationCode
     * @param rowIndex
     */
    public void enterLocationCode( String locationCode, int rowIndex )
    {   waitForPageLoad();
        List<WebElement> header = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));
        for (int i = 0; i < header.size(); i++) {
            String contentOfText = header.get(i).getText();
            if (contentOfText.contains("Location Code")) {
                int columnIndex = i + 1;
                rowIndex= rowIndex+1;
                WebElement locationField =driver.findElement(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)[1]"));
                try {
                    scroll.scrollElementIntoView(locationField);
                    locationField.click();
                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(locationField);
                }
                waitForPageLoad();
                text.enterText(locationField, locationCode);
                waitForPageLoad();
            }
        }
    }

    /**
     * locates the reason code field and enters the value to it
     * */

    public void enterReasonCode(String reasonCode, int rowIndex){
        List<WebElement> header = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));
        for (int i = 0; i < header.size(); i++) {
            String columnName = header.get(i).getText();
            if (columnName.contains("Return Reason Code")) {
                int columnIndex = i + 1;
                rowIndex= rowIndex+1;
                WebElement returnReasonField =driver.findElement(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)[1]"));
                try {
                    scroll.scrollElementIntoView(returnReasonField);
                    returnReasonField.click();
                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(returnReasonField);
                }
                returnReasonField.clear();
                text.enterText(returnReasonField, reasonCode);
                returnReasonField.sendKeys(Keys.TAB);
            }
        }
    }


    /**
     * locates the location field and enters the location code to it
     * @param amount
     * @param rowIndex
     */
    public void enterLineAount( String amount, int rowIndex )
    {
        WebElement lineamountField = getTableCell(rowIndex, 16);
        try
        {
            lineamountField.click();
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(lineamountField);
        }
        lineamountField.clear();
        text.enterText(lineamountField, amount);
    }
    /**
     * locates the location field and clears it
     * @param rowIndex
     */
    public void clearLocationCode( int rowIndex )
    {
        waitForPageLoad();
        WebElement locationField = getTableCell(rowIndex, 7);
        try
        {
            locationField.click();
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(locationField);
        }
        locationField.clear();
        waitForPageLoad();
    }
    /**
     * locates the location field and clears it
     * @param rowIndex
     */
    public void deleteLine( int rowIndex )
    {   waitForPageLoad();
        WebElement deleteField = getTableCell(rowIndex, 3);
        try
        {
            deleteField.click();
        }
        catch ( ElementNotInteractableException e )
        {
            click.javascriptClick(deleteField);
        }
        click.clickElementCarefully(deleteLine);
        waitForPageLoad();
        click.clickElementCarefully(acceptDelete);
    }
    /**
     * locates the quantity field and enters the quantity.
     * @param quan quantity of the item selected as a string
     */
    public void enterQuantity( String quan, int rowIndex ) {
        List<WebElement> textHeader = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));
        for (int i = 0; i < textHeader.size(); i++) {
            String textContent = textHeader.get(i).getText();
            if (textContent.equals("Quantity")) {
                int columnIndex = i + 1;
                rowIndex= rowIndex+1;
                WebElement quantityField =driver.findElement(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)[1]"));
                try {
                    click.moveToElementAndClick(quantityField);
                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(quantityField);
                }
                text.clearText(quantityField);
                text.enterText(quantityField, quan);
                text.pressEnter(quantityField);
            }
        }
    }

    /**
     * clicks on the location code field to auto populate the store location code from the selected customer profile.
     */
    public void clickLocationCode( int rowIndex )
    {
        WebElement locationCodeField = getTableCell(rowIndex, 7);
        try
        {
            click.clickElement(locationCodeField);
        }
        catch ( ElementNotInteractableException | NoSuchElementException e )
        { }
    }
    /**
     * locates the tax area code field and enters Vertex in it.
     * presses tab on the current element until it reaches the tax area code element
     * (assumes initial current element is a cell on the table)
     * (scrolling to element or clicking on it then trying to enter text does not bring it into view)
     */
    public void selectTaxAreaCode( )
    {
        waitForPageLoad();
        WebElement currentElement = driver
                .switchTo()
                .activeElement();
        String currentAriaLabel = currentElement.getAttribute("aria-label");

        while ( !currentAriaLabel.contains("Tax Area Code, ") )
        {
            text.pressTab(currentElement);
            currentElement = driver
                    .switchTo()
                    .activeElement();
            currentAriaLabel = currentElement.getAttribute("aria-label");
        }

        WebElement taxAreaCodeField = currentElement;
        click.clickElement(taxAreaCodeField);
        text.enterText(taxAreaCodeField, "VERTEX");
        taxAreaCodeField.sendKeys(Keys.TAB);
    }
    /**
     * locates the tax area code field and enters Vertex in it.
     * @param rowIndex
     * @param tACode
     */
    public void selectTaxAreaCode(String tACode, int rowIndex)
    {
        waitForPageLoad();
        WebElement taxAreaCodeField = getTableCell(rowIndex, 8);
        click.clickElement(taxAreaCodeField);
        text.enterText(taxAreaCodeField, tACode);
        text.pressTab(taxAreaCodeField);
    }
    /**
     * locate the unit price field and enter the price
     * @param price
     * @param rowIndex
     */
    public void enterUnitPrice( int rowIndex, String price )
    {
        waitForPageLoad();
        List<WebElement> textHeader = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));

        for (int i = 0; i < textHeader.size(); i++) {
            String textContent = textHeader.get(i).getText();
            if (textContent.contains("Unit Price Excl. Tax")) {
                int columnIndex = i + 1;
                rowIndex= rowIndex+1;
                WebElement itemNumberField = driver.findElement(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)[1]"));
                itemNumberField.click();
                waitForPageLoad();
                itemNumberField.sendKeys(price);
                text.pressTab(itemNumberField);
                waitForPageLoad();
            }
        }
    }
    /**
     * locate the tax group code field and enter the code
     * @param code
     */
     public void enterTaxGroupCode( String code, int rowIndex ) {

        waitForPageLoad();
        List<WebElement> textHeader = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));

        for (int i = 0; i < textHeader.size(); i++) {

            String textContent = textHeader.get(i).getText();
            if (textContent.contains("Tax Group Code")) {
                int columnIndex = i + 1;
                WebElement taxGroupField = wait.waitForElementEnabled(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)[1]"));
               try {

                    click.clickElementCarefully(taxGroupField);
                    waitForPageLoad();
                   if (code.equalsIgnoreCase("clearText"))
                        {
                       text.clearText(taxGroupField);
                        }
                   else {
                       text.enterText(taxGroupField, code);
                        }
                   text.pressTab(taxGroupField);

                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(taxGroupField);
                }
            }

        }
    }

    /**
     * locate the tax group code field and enter the code
     *@param amount
     * @author bhikshapathi
     */
    public void enterLineAmount( String amount, int rowIndex )
    {
        waitForPageLoad();
        List<WebElement> textHeader = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));

        for (int i = 0; i < textHeader.size(); i++) {

            String textContent = textHeader.get(i).getText();
            if (textContent.contains("Unit Price Excl. Tax")) {
                int columnIndex = i + 1;
                WebElement taxGroupField = wait.waitForElementEnabled(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)[1]"));
                try {

                    click.moveToElementAndClick(taxGroupField);
                    waitForPageLoad();
                    if (amount.equalsIgnoreCase("clearText"))
                    {
                        text.clearText(taxGroupField);
                    }
                    else {
                        text.enterText(taxGroupField, amount);
                    }
                    text.pressTab(taxGroupField);

                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(taxGroupField);
                }
            }

        }
    }
    /**
     * Enters the document number when creating a new document
     * @param docNum
     */
    public void enterDocumentNumber( String docNum )
    {
        WebElement documentNumberField = wait.waitForElementEnabled(documentNumberInputLoc);
        text.enterText(documentNumberField, docNum);
        text.pressEnter(documentNumberField);
    }
    /**
     * Enters customer code
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param customerCode
     */
    public void enterCustomerCode( String customerCode )
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement customerListField = wait.waitForElementPresent(customerListFieldLoc);
        click.clickElementCarefully(customerListField);
        waitForPageLoad();
        customerListField.sendKeys(customerCode);
        text.pressEnter(customerListField);

    }
    /**
     * Enters city
     * For use when manually inputting information/when expecting autofill on customer information
     *@param name
     * @author bhikshapathi
     */
    public void enterCustomerName( String name)
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement cityField = wait.waitForElementPresent(nameFieldLoc);
        text.clearText(cityField);
        text.enterText(cityField,name);
        text.pressEnter(cityField);
    }
    /**
     * Enters address
     * For use when manually inputting information/when expecting autofill on customer information
     *@param address
     * @author bhikshapathi
     */
    public void enterAddress( String address)
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement addressField = wait.waitForElementPresent(addressFieldLoc);
        text.clearText(addressField);
        text.enterText(addressField, address);
        text.pressEnter(addressField);
    }
    /**
     * Enters city
     * For use when manually inputting information/when expecting autofill on customer information
     *@param city
     * @author bhikshapathi
     */
    public void enterCityName( String city)
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement cityField = wait.waitForElementPresent(cityFieldLoc);
        text.clearText(cityField);
        text.enterText(cityField,city);
        text.pressEnter(cityField);
    }
    /**
     * Enters state
     * For use when manually inputting information/when expecting autofill on customer information
     *@param state
     * @author bhikshapathi
     */
    public void enterState( String state)
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement stateField = wait.waitForElementPresent(stateFieldLoc);
        text.clearText(stateField);
        text.enterText(stateField,state);
        text.pressEnter(stateField);
    }
    /**
     * Enters zipcode
     * For use when manually inputting information/when expecting autofill on customer information
     *  @param zip
     * @author bhikshapathi
     */
    public void enterZipCodeValue( String zip)
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement zipField = wait.waitForElementPresent(zipFieldLoc);
        text.clearText(zipField);
        text.enterText(zipField,zip);
        text.pressEnter(zipField);
    }
    /**
     * does the process of selecting a customer for the order
     *
     * @param customerName name of the customer to select from an existing list of customers.
     */
    public void selectCustomer( String customerName, String customerCode )
    {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement customerListArrowButton = wait.waitForElementPresent(customerListFieldLoc);
        text.enterText(customerListArrowButton, customerCode);
        WebElement customerCell = getTableCell(customerName);
        click.clickElement(customerCell);
    }

    /**
     * locates the tax jurisdiction field and gets the value from it
     *
     * @return the jurisdiction as a string
     */
    public String getJurisdiction( )
    {
        WebElement jurisdictionField = wait.waitForElementPresent(taxJurisdictionFieldLoc);

        String jurisdictionText = attribute.getElementAttribute(jurisdictionField, "textContent");

        return jurisdictionText;
    }
    /**
     * locates the tax rate field and gets the value in it.
     *
     * @return the tax rate value as a string
     */
    public String getTaxRate( )
    {
        String actualTaxRate = "";
        waitForPageLoad();
        List<WebElement> taxRateField = wait.waitForAllElementsPresent(taxRateFieldLoc);
        if ( taxRateField.size() <= 0 )
        {
            System.out.println("Unable to locate the tax rate field containers");
        }
        else
        {
            actualTaxRate = attribute.getElementAttribute(taxRateField.get(0), "textContent");
        }
        return actualTaxRate;
    }

    /**
     * locates the tax rate field and gets the value in it.
     * @return the tax rates value as a list of Strings
     * @author bhikshapathi
     */
    public List<String> getMultipleTaxRates( )
    {
        String actualTaxRate = "";
        waitForPageLoad();
        List<WebElement> taxRateField = wait.waitForAllElementsPresent(taxRateFieldLoc);
        List<String> value = new ArrayList<String>();
        if ( taxRateField.size() <= 0 )
        {
            System.out.println("Unable to locate the tax rate field containers");
        }
        else
        {
            for(int j=0;j<taxRateField.size();j++){
                value.add(attribute.getElementAttribute(taxRateField.get(j), "textContent"));
            }
        }
        return value;
    }
    /**
     * locates the tax rate field and gets the value in it.
     * @return the tax amounts value as a list of Strings
     * @author bhikshapathi
     */
    public List<String> getMultipleTaxAmount( )
    {
        String actualTaxRate = "";
        waitForPageLoad();
        List<WebElement> taxRateField = wait.waitForAllElementsPresent(taxAmountFieldLoc);
        List<String> value = new ArrayList<String>();
        if ( taxRateField.size() <= 0 )
        {
            VertexLogger.log("Unable to locate the tax rate field containers");
        }
        else
        {
            for(int j=0;j<taxRateField.size();j++){
                value.add(attribute.getElementAttribute(taxRateField.get(j), "textContent"));
            }
        }
        return value;
    }

    /**
     * Get Stat Tax amount
     * @return
     */
    public String getVertexStatTaxAmount()
    {
        WebElement taxAmountField = wait.waitForElementPresent(statTaxAmount);
        String actualTaxAmount = attribute.getElementAttribute(taxAmountField, "textContent");
        return actualTaxAmount;
    }

    /**
     * Get NonVertex Tax amount
     */
    public String getNoVertexTaxAmount()
    {
        WebElement taxAmountField = wait.waitForElementPresent(statNoVertexTaxAmount);
        String actualTaxAmount = attribute.getElementAttribute(taxAmountField, "textContent");
        return actualTaxAmount;
    }

    /**
     * locates the tax amount field and gets the value from it
     *
     * @return tax amount as a string
     */
    public String getTaxAmount( )
    {
        WebElement taxAmountField = wait.waitForElementPresent(taxAmountFieldLoc);

        String actualTaxAmount = attribute.getElementAttribute(taxAmountField, "textContent");
        return actualTaxAmount;
    }
    /**
     * locates the tax amount field and gets the value from it
     *@return taxable amount as a string
     * @author bhikshapathi
     */
    public String getTaxableAmount( )
    {
        WebElement taxableAmountField = wait.waitForElementPresent(totalInclTaxAmount);
        String actualTaxableAmount = attribute.getElementAttribute(taxableAmountField, "textContent");
        return actualTaxableAmount;
    }
    /**
     * Gets the total tax amount on the sales document
     *
     * @return total tax as string
     */
    public String getTotalTaxAmount( )
    {
        waitForPageLoad();
        List<WebElement> fieldList = wait.waitForAllElementsPresent(totalTaxAmountLoc);

        WebElement field = fieldList.get(fieldList.size() - 1);
        String tax = text.getElementText(field);

        return tax;
    }

    /**
     * locates the sell-to customer number field and gets the value from it
     *
     * @return customer number as a string
     */
    public String getSellToCustomerNumber( )
    {
        WebElement copyPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement numberField = wait.waitForElementDisplayed(sellToCustomerNumberLoc, copyPopup);
        String number = text.getElementText(numberField);

        return number;
    }

    /**
     * locates the sell-to customer name field and gets the value from it
     *
     * @return customer name as a string
     */
    public String getSellToCustomerName( )
    {
        WebElement copyPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement nameField = wait.waitForElementDisplayed(sellToCustomerNameLoc, copyPopup);
        String name = text.getElementText(nameField);

        return name;
    }

    /**
     * locates the exact cell from the items table
     *
     * @param rowIndex    the row the cell is on
     * @param columnIndex the column the cell is on
     *
     * @return cell, as WebElement
     */
    public WebElement getTableCell( int rowIndex, int columnIndex )
    {
        WebElement cell = null;

        List<WebElement> tableCons = wait.waitForAllElementsPresent(itemTableConLoc);
        WebElement tableParentCon = tableCons.get(tableCons.size() - 1);
        WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
            for ( WebElement column : rowColumns )
            {
                column = rowColumns.get(columnIndex);
                try
                {
                    cell = column.findElement(By.tagName("input"));
                }
                catch ( org.openqa.selenium.NoSuchElementException e )
                {
                    cell = column;
                }
                break findingCell;
            }
        }

        return cell;
    }

    /**
     * get's the customer table cell from the customers table
     *
     * @param customerName name of the customer to select
     *
     * @return the webElement of the customer table cell
     */
    public WebElement getTableCell( String customerName )
    {
        WebElement cell = null;
        WebElement tableParentCon = wait.waitForElementPresent(tableParentConLoc);
        WebElement tableCon = wait.waitForElementPresent(tableConLoc, tableParentCon);
        WebElement table = wait.waitForElementPresent(By.tagName("table"), tableCon);
        WebElement tableBody = wait.waitForElementPresent(By.tagName("tbody"), table);
        List<WebElement> rows = wait.waitForAllElementsPresent(By.tagName("tr"), tableBody);
        for ( WebElement row : rows )
        {
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
            cell = element.selectElementByText(rowColumns, customerName);
            if ( cell != null )
            {
                break;//fixme scott added the break-after-find when refactoring, chekc that if function breaks
            }
        }
        return cell;
    }

    /**
     * locates and clicks on the close button for the tax details dialog
     */
    public void closeTaxDetailsDialog( )
    {
        WebElement closeButton = wait.waitForElementDisplayed(close);
        click.moveToElementAndClick(closeButton);
        wait.waitForElementNotDisplayedOrStale(closeButton, 18);
    }
    /**
     * locates and clicks on the Ok button for the tax details dialog
     * @return pop text
     * @author bhikshapathi
     */
    public String noTaxDetailsDialog( )
    {
        WebElement popUp=wait.waitForElementDisplayed(noTaxDetails);
        String pop=popUp.getText();
        WebElement closeButtonContainer = wait.waitForElementPresent(dialogButtonCon);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, closeButtonContainer);
        WebElement closeButton = element.selectElementByText(buttonsList, "OK");

        click.clickElement(closeButton);
        wait.waitForElementNotDisplayedOrStale(closeButton, 15);
        return pop;
    }
    /**
     * On the Vertex Tax Details Document popup, read the header section
     * to ensure the document type and number is correctly displayed
     *
     * @return document header as a string
     */
    public String taxDetailsDocumentReadHeader( )
    {
        WebElement header = wait.waitForElementDisplayed(dialogHeaderCon);
        String headerText = header.getText();

        int firstIndex = headerText.indexOf("∙");
        int secondIndex = headerText.indexOf("∙", firstIndex + 1);
        String docTypeAndNumber = headerText
                .substring(0, secondIndex)
                .trim();

        return docTypeAndNumber;
    }

    /**
     * After posting or converting to an invoice, click "Yes" to
     * open the invoice
     *
     * @return the sales invoice page
     */
    public NavSalesInvoicePage goToInvoice( )
    {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);

        return initializePageObject(NavSalesInvoicePage.class);
    }
    /**
     * After posting or converting to an invoice, click "Yes" to
     * open the invoice
     *
     * @return the sales invoice page
     */
    public NavSalesReturnOrderPage goToPostedMemos( )
    {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);

        return initializePageObject(NavSalesReturnOrderPage.class);
    }
    /**
     * After posting a credit memo, click "Yes" to go to the posted memo
     *
     * @return credit memo page
     */
    public NavSalesCreditMemoPage goToPostedMemo( )
    {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);

        return initializePageObject(NavSalesCreditMemoPage.class);
    }

    /**
     * locate and click on the print Preview button
     *
     * @return the print preview page
     */
    public NavPrintPreviewPage clickPrintPreview( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Preview");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
        wait.waitForElementDisplayed(previewContainer);

        return initializePageObject(NavPrintPreviewPage.class);
    }

    /**
     * Selects the document type and inputs the document number
     * when copying a document
     *
     * @param documentType   the document type to select
     * @param documentNumber the document number to input
     */
    public void fillOutCopyDocumentInformation( String documentType, String documentNumber)
    {
        WebElement copyPopup = wait.waitForElementDisplayed(dialogBoxLoc);

        WebElement docTypeSelect = wait.waitForElementEnabled(documentTypeSelectLoc, copyPopup);
        dropdown.selectDropdownByDisplayName(docTypeSelect, documentType);

        WebElement docNumberField = wait.waitForElementEnabled(documentNumberFieldLoc, copyPopup);
        text.enterText(docNumberField, documentNumber);
        text.pressEnter(docNumberField);
    }


    /**
     * Enables the checkboxes 'Include Header' and 'Recalculate Lines'
     * when copying a document on Credit Memo Page
     */
    public void enableIncludeHeaderRecalculateLines() {
        waitForPageLoad();
        WebElement elementIncludeHeader = wait.waitForElementDisplayed(includeHeaderButtonLoc);
        WebElement elementRecalculateLines = wait.waitForElementDisplayed(recalculateLinesLoc);
        if (elementIncludeHeader.isSelected() && !elementRecalculateLines.isSelected()) {
            click.clickElementCarefully(elementRecalculateLines);
        }
        dialogBoxClickOk();
    }

    /**
     * Disables the checkboxes 'Include Header' and 'Recalculate Lines'
     * when copying a document on Credit Memo Page
     */
    public void disableBothIncludeHeaderRecalculateLines() {
        WebElement elementIncludeHeader = wait.waitForElementDisplayed(includeHeaderButtonLoc);
        WebElement elementRecalculateLines = wait.waitForElementDisplayed(recalculateLinesLoc);
        if (elementIncludeHeader.isSelected() || elementRecalculateLines.isSelected()) {
            click.clickElementCarefully(elementIncludeHeader);
            click.clickElementCarefully(elementRecalculateLines);
        }
        dialogBoxClickOk();
    }

    /**
     * Clicks the header for the Shipping and Billing category
     * to open the section
     */
    public void openShippingAndBillingCategory()
    {
        jsWaiter.waitForLoadAll();
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
            WebElement shippingAndBilling = element.selectElementByText(categoriesList, "Shipping and Billing");
            hover.hoverOverElement(shippingAndBilling);
            click.clickElementCarefully(shippingAndBilling);
            wait.waitForElementDisplayed(shipToDropdownLoc);

    }

    /**
     * Clicks the header for Invoice category
     * to open Invoice details
     */
    public void openInvoiceDetails() {
        jsWaiter.waitForLoadAll();
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement invoiceDetails = element.selectElementByText(categoriesList, "Invoice Details");
        hover.hoverOverElement(invoiceDetails);
        scroll.scrollElementIntoView(invoiceDetails, PageScrollDestination.BOTTOM);
        click.clickElementCarefully(invoiceDetailsShowMore);

    }


    /**
     * Selects Alternate Shipping Addresss from the ship-to dropdown, and selects
     * an alternate shipping address based on the code
     *
     * @param addressCode
     */
    public void selectAlternateShipToAddress( String addressCode )
    {
        WebElement shipToSelect = wait.waitForElementEnabled(shipToDropdownLoc);
        dropdown.selectDropdownByDisplayName(shipToSelect, "Alternate Shipping Address");

        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement dialogContent = wait.waitForElementDisplayed(dialogContentCon, dialog);

        List<WebElement> tablesList = wait.waitForAllElementsPresent(commonTable, dialogContent);
        WebElement tableCon = tablesList.get(tablesList.size() - 1);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(tableRowTag, tableCon);
        int rowIndex = 0;
        findingCell:
        for ( WebElement row : tableRows )
        {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(tableCellTag, row);
            WebElement cellCon = rowColumns.get(2);
            WebElement cell = element.getWebElement(By.tagName("a"), cellCon);
            String cellText = cell.getText();

            if ( cellText.equals(addressCode) )
            {
                click.performDoubleClick(cell);
                break;
            }
            rowIndex++;
        }

        wait.waitForElementNotDisplayedOrStale(dialog, 15);
    }
    /**
     * Selects custom Shipping Addresss from the ship-to dropdown,
     *
     * @author bhikshapathi
     */
    public void selectCustomShipToAddress()
    {
        WebElement shipToSelect = wait.waitForElementEnabled(shipToDropdownLoc);
        dropdown.selectDropdownByDisplayName(shipToSelect, "Custom Address");
        waitForPageLoad();
    }
    /**
     * Selects select Payment Discount Invoice Details
     *
     * @author bhikshapathi
     */
    public void selectPaymentDiscount(String value)
    {
        WebElement payment = wait.waitForElementEnabled(paymentDiscount);
        click.clickElementCarefully(payment);
        text.enterText(payment,value);
        waitForPageLoad();
    }
    /**
     * Reads the text on a dialog box
     *
     * @return the text displayed on a dialog box
     */
    public String dialogBoxReadMessage( )
    {   waitForPageLoad();
        WebElement dialogBox = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement dialogMessage = wait.waitForElementDisplayed(dialogMessageCon, dialogBox);

        String messageTxt = dialogMessage.getAttribute("outerText");
        return messageTxt;
    }

    /**
     * Generalized method, for expand table
     */
    public void expandTable( )
    {
        wait.waitForElementPresent(expandButton);
        click.clickElementCarefully(expandButton);
        jsWaiter.waitForLoadAll();
    }

    /**
     * Generalized method, for exit expand table
     */
    public void exitExpandTable( )
    {
        wait.waitForElementDisplayed(exitExpandButton);
        click.javascriptClick(exitExpandButton);
        jsWaiter.waitForLoadAll();
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "OK"
     */
    public void dialogBoxClickOk( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "OK");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "Yes"
     */
    public void dialogBoxClickYes( )
    {
       try
        {
            wait.waitForElementDisplayed(dialogBoxLoc);
            WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
            WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
            List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
            WebElement button = element.selectElementByText(buttonList, "Yes");
            click.clickElement(button);
            wait.waitForElementNotDisplayedOrStale(button, 15);
        }
        catch (TimeoutException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "Close"
     */
    public void dialogBoxClickClose( )
    {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Close");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }
    /**
     * Generalized method, for clear Location code From Shipping and Billing
     * @author bhikshapathi
     */
    public void clearLocationCodeFromShippingandBilling()
    {
        WebElement locationField=wait.waitForElementPresent(locationcodefield);
        scroll.scrollElementIntoView(locationField);
        click.javascriptClick(locationField);
        locationField.clear();
        waitForPageLoad();
        locationField.sendKeys(Keys.TAB);
        wait.waitForElementDisplayed(locCodePopUp);
        click.javascriptClick(ok);
        waitForPageLoad();
    }
    /**
     * Generalized method, for Update Location code From Shipping and Billing
     * @param locationCode
     * @author bhikshapathi
     */
    public void updateLocationCodeFromShippingandBilling(String locationCode)
    {
        WebElement locationField=wait.waitForElementPresent(locationcodefield);
        scroll.scrollElementIntoView(locationField);
        click.javascriptClick(locationField);
        locationField.clear();
        waitForPageLoad();
        text.enterText(locationField,locationCode);
        locationField.sendKeys(Keys.TAB);
        wait.waitForElementDisplayed(locCodePopUp);
        click.javascriptClick(ok);
        waitForPageLoad();
    }

    /**
     * This method is to get Tax group code Alert Message from statistics Tab
     * @return String of the error
     * @author dpatel
     */
    public String getAlertMessageForTaxGroupCode()
    {
        String alertText ;
        WebElement taxGroupAlertEle = wait.waitForElementPresent(taxGroupAlert);
        alertText = text.getElementText(taxGroupAlertEle);
        //click.clickElementCarefully(ok); Please uncomment once env. issue on 'OK' button of POP-up is resolved
        return alertText;
    }
    /**
     * Generalized method, for Save and close the record
     * @author bhikshapathi
     */
    public void saveAndClose()
    {
        WebElement element=wait.waitForElementDisplayed(saveAndCloseButton);
        click.clickElementCarefully(element);
        waitForPageLoad();
    }
    /**
     * Generalized method, for Pop up close
     * @author bhikshapathi
     */
    public void reCalculateAfterChangeShipToAddress(){
        waitForPageLoad();
        click.clickElementCarefully(yes);
        waitForPageLoad();
    }
    /**
     * CDBC-266
     * This method is to create Expected Alert Message Strings
     * @param documentType Type of document
     * @param documentNo Document Number
     * @return String of the expected alert message
     *@author dpatel
     *@author P.Potdar
     */

    public String createExpectedAlertMessageStrings(String documentType, String documentNo, String lineNo)
    {
        if (documentType.contains("Quote"))
        {
            return "Tax Group Code must have a value in Sales Line: Document Type="+documentType+", Document No.="+documentNo+", Line No.="+ lineNo+". It cannot be zero or empty.\n" +
                    "\n" +
                    "Page Edit - Sales Statistics has to close";
        }
        else {
            return "Tax Group Code must have a value in Sales Line: Document Type=" + documentType + ", Document No.=" + documentNo + ", Line No.=" + lineNo + ". It cannot be zero or empty.\n" +
                    "\n" +
                    "Page Edit - Sales Order Statistics has to close";
        }
    }

    /**
     * This method is to get Message from popup of Address Cleansing
     * @return String of the error
     */
    public String getAddressValidationPopUpMessage()
    {
        String alertText ;
        WebElement addValidPopup = wait.waitForElementPresent(addressCleansePopUpMessage);
        alertText = text.getElementText(addValidPopup);
        return alertText;
    }
    /**
     * This method is to revert changes that caused error
     */
    public void revertError(){
        waitForPageLoad();
        WebElement errorMsg=wait.waitForElementDisplayed(revertLink);
        click.clickElementCarefully(errorMsg);
        waitForPageLoad();

    }

    /**
     * It verifies if Multiple ship to addresses are returned
     * @param noOfLines
     */
    public Boolean verifyPopupList(String noOfLines){
        List<WebElement> radioElement = wait.waitForAllElementsPresent(radioField);
        int size=radioElement.size();
        String listSize=Integer.toString(size);
        if(listSize.equals(noOfLines))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }
}
