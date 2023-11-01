package com.vertex.quality.connectors.dynamics365.business.pages.base;


import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.dynamics365.business.components.BusinessSalesEditPagesNavMenu;
import com.vertex.quality.connectors.dynamics365.business.components.BusinessShippingAndBillingComponent;
import com.vertex.quality.connectors.dynamics365.business.pages.*;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseCreditMemoPage;
import com.vertex.quality.connectors.dynamics365.business.pages.accountpayable.BusinessPurchaseInvoicePage;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * represents the base page for all the different pages that edit sales transactions
 * anything from sales quotes to sales orders, creating new or editing an existing one
 *
 * @author osabha, cgajes, K.Bhikshapathi, Shruti
 */
public class BusinessSalesBasePage extends VertexPage {
    public BusinessSalesEditPagesNavMenu salesEditNavMenu;
    public BusinessShippingAndBillingComponent shippingAndBillingComponent;

    protected By dialogBoxLoc = By.xpath("//*[@class=\"ms-nav-content\" and not(@tabindex)]");
    protected By categoryHeadersLocs = By.className("caption-text");

    protected By itemTableConLoc = By.className("hosting-list-last");
    protected By customerListFieldLoc = By.xpath("//a[contains(@aria-label,'Customer Name')]/../input");
    protected By addressFieldLoc = By.xpath("//div[@controlname='Ship-to Address']//input");
    protected By cityFieldLoc = By.xpath("//div[@controlname='Ship-to City']//input");
    protected By stateFieldLoc = By.xpath("//div[@controlname='Ship-to County']//input");
    protected By zipFieldLoc = By.xpath("//div[@controlname='Ship-to Post Code']//input");
    protected By countryFieldLoc = By.xpath("//div[@controlname='Ship-to Country/Region Code']//input");
    protected By documentNumberInputLoc = By.cssSelector("input[aria-label='No., (Blank)']");
    protected By commonTable = By.cssSelector("table[id*='BusinessGrid'] tbody");

    protected By backAndSaveArrowButtonLoc = By.cssSelector("i[data-icon-name='Back']");
    protected By deleteButtonLoc = By.cssSelector("button[title='Delete the information.']");
    protected By editModeToggle = By.cssSelector("button[title='Make changes on the page.']");
    protected By readOnlyModeToggle = By.cssSelector("button[title='Open the page in read-only mode.']");
    protected By itemAutofillBoxLoc = By.className("ms-nav-content-box");
    protected By itemAutofillOptionsLoc = By.cssSelector("a[title*='Select record ']");
    protected By itemNumberCellLoc = By.cssSelector("input[aria-label*='No.,']");
    protected By itemQuantity = By.cssSelector("input[aria-label*='Quantity,']");
    protected By dueDateFieldLoc = By.xpath("//a[contains(@aria-label,'Due Date')]/../input");
    protected By locationFieldLoc = By.cssSelector("[aria-label*='Location Code,']");
    protected By taxAreaCodeFieldLoc = By.cssSelector("[aria-label*='Tax Area Code,']");
    protected By taxRateFieldLoc = By.xpath("//table/caption[text()='VER_Sales Line Tax Details']/../tbody/tr/td[13]/span");
    protected String taxAmountFieldInPurchaseLoc = "//table/caption[text()='VER_Purch Line Tax Details']/../tbody/tr/td[%s]/span";
    protected By taxAmountFieldLoc = By.xpath("//td[@controlname='Total Tax']/span");
    protected By statTaxAmountFieldLoc = By.xpath("//div[@controlname='TaxAmount']/div/span");
    protected By taxJurisdictionFieldLoc = By.xpath("//td[@controlname='Jurisdiction']/span");
    protected By totalTaxAmountLoc = By.xpath("//a[contains(text(),'Total Tax (USD)')]/..//span");
    protected By totalExclTaxAmount = By.xpath("//a[contains(text(),'Total Excl. Tax (USD)')]/..//span");
    protected By customerNameLoc = By.cssSelector("input[aria-label*='Customer Name, '][title='Look up value']");
    protected By sellToCustomerNumberLoc = By.xpath("//div[@controlname='SellToCustNo']//span");
    protected By sellToCustomerNameLoc = By.xpath("//div[@controlname='SellToCustName']//span");
    protected By assignToQuantityLoc = By.xpath("//td[@controlname='Qty. to Assign']//input");
    protected By orderNumberLoc = By.xpath("//div[@controlname='Order No.']/div/span");
    protected By quoteNumberLoc = By.xpath("//div[@controlname='Quote No.']//span");
    protected By documentTypeSelectLoc = By.xpath("//div[@controlname='DocumentType']//select");
    protected By documentNumberFieldLoc = By.xpath("//div[@controlname='DocumentNo']//input");
    protected By shipToDropdownLoc = By.xpath("//div[@controlname='ShippingOptions']//select");
    protected By shipmentMethodCodeToDropdownLoc = By.xpath("//div[@controlname='Shipment Method Code']//input");
    protected By impositionFieldVal = By.xpath("//td[@controlname='Imposition']//span");
    protected By shipToCodeDropdownLoc = By.xpath("//div[@controlname='Ship-to Code']//a[@title='Choose a value for Code']");
    protected By includeHeaderLoc = By.xpath("//div[@controlname='IncludeHeader_Options']//div[@role='checkbox']");

    protected By loadingConLoc = By.className("ms-nav-exceptiondialogcontainer");
    protected By tableParentConLoc = By.cssSelector(".spa-view.spa-lookup.no-animations.shown");
    protected By locationcodefield = By.xpath("//div[contains(@controlname,'Location Code')]//input[@type='text']");
    protected By moreOption = By.xpath("(//span[@aria-label='More options'])[1]");
    protected By locCodePopUp = By.xpath("//main[@class='ms-nav-content']");
    protected By alertOkButtonLoc = By.className("walkme-custom-balloon-button");
    protected By tableConLoc = By.className("ms-nav-scrollable");
    protected By itemAssignmentTableConLoc = By.className("ms-nav-worksheetform");
    protected By dialogHeaderCon = By.className("task-dialog-header");
    protected By dialogMessageCon = By.className("dialog-content");
    protected By dialogContentCon = By.className("task-dialog-content-container");
    protected By dialogButtonCon = By.className("ms-nav-actionbar-container");
    protected By previewContainer = By.className("ms-nav-pdf-container");
    protected By expandButton = By.xpath("//button[(@title='Enter focus mode for this part')]");
    protected By exitExpandButton = By.xpath("//button[@title='Exit focus mode']");
    protected By ok = By.xpath("//span[contains(.,'OK')]");
    protected By yes = By.xpath("//form[@controlname='Dialog']//span[contains(.,'Yes')]");
    protected By highestValue = By.xpath("(//li/input[@type='radio'])[1]");
    protected By lowestValue = By.xpath("(//li/input[@type='radio'])[2]");
    protected By noTaxDetails = By.xpath("//p[contains(.,'No tax details on current document')]");
    protected By deleteLine = By.xpath("//span[contains(.,'Delete Line')]");
    protected By acceptDelete = By.xpath("//div[@class='ms-nav-actionbar-container has-actions']/button/span[contains(.,'Yes')]");
    protected By messageConLoc = By.cssSelector(".ms-nav-content-box.message-dialog");
    protected By buttonLoc = By.tagName("button");
    protected By tableRowTag = By.tagName("tr");
    protected By tableCellTag = By.tagName("td");
    protected By taxGroupAlert = By.cssSelector("div.ms-nav-edit-control-container.no-caption.staticstringcontrol-container");
    protected By alertMsg = By.cssSelector("div.ms-nav-logical-dialog.logical-dialog-nocaption");
    protected By noTaxesDialog = By.xpath("//p[contains(text(), 'No tax areas were found during the lookup. The address fields are inconsistent for the specified asOfDate.')]");
    protected By cutomer = By.xpath("//input[@aria-label='Name, ']");
    protected By ascending = By.xpath("//span[text()='Ascending']");
    protected By qtyToAssign = By.xpath("//td[@controlname='Qty. to Assign']");
    protected By itemCharge = By.cssSelector("span[aria-label='Item Charge']");
    protected By suggestItem = By.cssSelector("span[aria-label='Suggest Item Charge Assignment']");
    protected By tableField = By.xpath("//form[@controlname='VER_Sales Line Tax Details']//th");
    protected By quantityFieldLoc = By.xpath("//table/caption[text()='VER_Sales Line Tax Details']/..//td[6]/span");
    protected By close = By.xpath("//button[not(contains(@class,'ms-nav-hidden'))]//span[text()='Close']");
    protected By dropDown = By.xpath("//td[@class='edit-container ms-nav-enumeration edit']/select");
    protected By docType = By.xpath("//div[contains(@class,'title') and not(@tabindex) and @role='heading' and @aria-level='2']");
    protected By errorDescription = By.xpath("//td[@controlname='Description']//span");
    protected By tableHeaders = By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th");
    protected By taxAreaCode = By.xpath("//a[contains(@title,'value for Tax Area Code')]");
    protected By shipToDropdownInPurchasing = By.xpath("//div[contains(@controlname,'Ship')]//select");
    protected By customerNoDropDown = By.xpath("//div[@controlname='Sell-to Customer No.']//input");
    protected By shipToCodeDropdown = By.xpath("//div[@controlname='Ship-to Code']//input");
    protected By invoiceDetailsTaxAreaCode = By.xpath("//div[@controlname='Tax Area Code']//div//input");
    protected By vendorFieldLoc = By.xpath("//a[contains(@aria-label,'Vendor Name')]/../input");
    protected By customerNoField= By.xpath("//div[@controlName='Customer No.']//input");
    protected By vendorInvoiceNoLoc = By.xpath("//div[@controlname='Vendor Invoice No.']//input");
    protected By tableHeadersLoc = By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th");
    protected By showMore = By.xpath("(//button[contains(@aria-label, 'Shipping')])");
    protected By shipToCodeForAlternateVendor= By.xpath("//div[@controlname='Order Address Code']//input");
    protected By OkButton = By.xpath("//button/span[text()='OK']");
    protected By searchIcon = By.xpath("//div/i[@data-icon-name='Search']");
    protected By searchInputField=By.xpath("//input[contains(@aria-label,'Search CRONUS USA, Inc., Purchasing,')and not(@tabindex='-1')]");
    protected By firstElementClick = By.xpath("//td[@controlname='No.']/a[contains(@title,'Open record') and not(@tabindex='-1')]");
    protected By postingDateFieldLoc = By.xpath("//div[@controlname='Posting Date']//input");
    protected By enableReturnOriginalQuantity=By.xpath("//div[@controlname='OriginalQuantity']//div[@aria-checked='false']");
    protected By getPostedDocumentLinesToReverse=By.xpath("//span[contains(text(),'Get Posted Document Lines')]");
    protected By documentNoHeading=By.xpath("(//th[@abbr='Document No.'])[1]");
    protected By documentHeadingArrow=By.xpath("(//th[@abbr='Document No.'][1]/div/a)[2]");
    protected By documentFilter=By.xpath("(//span[@class='ms-nav-ctxmenu-title'])[3]");
    protected By documentSearch=By.xpath("//div/p[contains(.,'Only show lines where \"Document No.\" is')]/../../../div/div/input[1]");
    protected By okay=By.xpath("(//button/span[contains(.,'OK')])[2]");
    protected By vendorCreditMemoLoc=By.xpath("//div[@controlname='Vendor Cr. Memo No.']//input");
    protected By vertexStatTaxAmountLoc=By.xpath("//div[@controlname='VertexTaxAmount']//span");
    protected By moreOptionsInServiceLines=By.xpath("//div[contains(@class, 'ms-nav-band-header')]//span[@aria-label='More options']");
    protected By linesActions=By.xpath("//div[contains(@class,'ms-nav-band-header')]//button[@aria-haspopup='true']//span[@aria-label]");
    protected By lineButtonLoc = By.xpath("//span[@aria-label='Line']");
    protected By lineActionsMenu=By.xpath("//div[contains(@class,'secondary-row')]//span[@aria-label]");
    protected By amountIncludingTax=By.xpath("//td[@controlname='Amount Including VAT']//span");
    protected By TABLE_HEADER_ROW = By.xpath("//table//caption[contains(text(),'Line Tax Details')]/..//thead");
    protected By TABLE = By.xpath("//table//caption[contains(text(),'Line Tax Details')]/..");

    protected By numberInputButton=By.xpath("//a[text()='No.']//..//div//input");
    protected By selectLineFilterOption=By.xpath("//select[@title='All']");
    protected By selectServiceTaxLines=By.xpath("//table//caption[text()='VER_Service Line Tax Details']//..//tbody//tr");
    protected By taxAmountFieldList=By.xpath("(//table//caption[text()='VER_Service Line Tax Details']//..//tbody//tr//td[last()-1])");
    protected By actualSalesTax = By.xpath("//div[@controlname='VendorTax']//input");
    protected By actualSalesTaxWithNoInput = By.xpath("//div[@controlname='VendorTax']//span");
    protected By accrualSalesTax = By.xpath("//div[@controlname='TotalAccruedTax']//span");
    protected By calculatedSalesTax = By.xpath("//div[@controlname='VertexTax']//span");
    protected By recalculateTaxAmount = By.xpath("//span[text()='Recalculate Tax']");
    protected By lineDiscountPercentage = By.xpath("//td[@controlname='Line Discount %']");
    protected By currentSalesOrderNumber = By.xpath("//div[@role='heading' and contains(text(), '∙')]");
    protected By headingField = By.xpath("//table/caption[contains(text(),'Line Tax Details')]/..//td[7]/span");
    protected By refreshLink = By.linkText("Refresh (F5)");
    protected By qtyToShip = By.xpath("//td[@controlname=\"Qty. to Ship\"]//input");
    protected By qtyToInvoice = By.xpath("//td[@controlname=\"Qty. to Invoice\"]//input");
    Actions action=new Actions(driver);
    protected final int shortTimeout = 5;


    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
    String transactionDate = sdf.format(date);

    public BusinessSalesBasePage(WebDriver driver) {
        super(driver);
        this.shippingAndBillingComponent = new BusinessShippingAndBillingComponent(driver, this);
        this.salesEditNavMenu = new BusinessSalesEditPagesNavMenu(driver, this);
    }

    /**
     * locates the back arrow and clicks on it to save the changes on the page and close it
     */
    public void clickBackAndSaveArrow() {
        List<WebElement> arrowsList = wait.waitForAllElementsPresent(backAndSaveArrowButtonLoc);
        WebElement backArrow = arrowsList.get(arrowsList.size() - 1);
        wait.waitForElementEnabled(backArrow);
        click.clickElementIgnoreExceptionAndRetry(backArrow);
        waitForPageLoad();
    }

    /**
     * locates the back arrow and clicks on it to save the changes on the page and close it
     *
     * @author bhikshapathi
     */
    public void clickBackArrow() {
        List<WebElement> arrowsList = wait.waitForAllElementsPresent(backAndSaveArrowButtonLoc);
        WebElement backArrow = arrowsList.get(arrowsList.size() - 1);
        wait.waitForElementEnabled(backArrow);
        try {
            click.javascriptClick(backArrow);
        } catch (ElementNotInteractableException e) {
        }
    }

    /**
     * clicks the delete button and confirms to delete the document
     */
    public void deleteDocument() {
        List<WebElement> buttonList = wait.waitForAllElementsPresent(deleteButtonLoc);
        WebElement deleteButton = buttonList.get(buttonList.size() - 1);
        click.clickElement(deleteButton);
        jsWaiter.waitForLoadAll();
        dialogBoxClickYes();
    }

    /**
     * Select the confidence Indicator
     *
     * @bhikshapathi
     */
    public void selectConfidenceIndicator(String value) {
        List<WebElement> arrowsList = wait.waitForAllElementsPresent(backAndSaveArrowButtonLoc);
        WebElement backArrow = arrowsList.get(arrowsList.size() - 1);
        wait.waitForElementEnabled(backArrow);
        try {
            click.clickElement(backArrow);
        } catch (ElementNotInteractableException e) {
        }
        if (value.equals("80")) {
            WebElement heighest = wait.waitForElementDisplayed(highestValue);
            click.javascriptClick(heighest);
        }
        if (value.equals("20")) {
            WebElement lowest = wait.waitForElementDisplayed(lowestValue);
            click.javascriptClick(lowest);
        }
        WebElement okButton = wait.waitForElementDisplayed(ok);
        click.javascriptClick(okButton);
    }

    /**
     * when in read only mode, clicks the edit button
     * to make the customer card editable
     */
    public void toggleEditMode() {
        WebElement toggle = wait.waitForElementPresent(editModeToggle);
        click.clickElement(toggle);
        wait.waitForElementNotPresent(editModeToggle);
    }

    /**
     * when in edit mode, clicks the read only button
     * to make the customer card read only
     */
    public void toggleReadOnlyMode() {
        WebElement toggle = wait.waitForElementPresent(readOnlyModeToggle);
        click.performDoubleClick(toggle);

        if (element.isElementDisplayed(dialogBoxLoc)) {
            dialogBoxClickYes();
        }
    }

    /**
     * Get the sales document number from the current page
     *
     * @return document number as string
     */
    public String getCurrentSalesNumber() {
        waitForPageLoad();
        String salesOrderNumber = attribute.getElementAttribute(currentSalesOrderNumber, "textContent");
        String docTypeAndNumber = salesOrderNumber.substring(salesOrderNumber.indexOf(0) + 1, salesOrderNumber.indexOf("∙") - 1);
        String number = docTypeAndNumber
                .substring(docTypeAndNumber.indexOf(0) + 1)
                .trim();
        
        return number;
    }

    /**
     * Get the sales document number from the current page
     *
     * @return customer name as string
     * @author bhikshapathi
     */
    public String getCustomerName() {
        String fullTitle = getPageTitle();
        String value[] = fullTitle.split("-");
        String name[] = value[2].split("∙");
        return name[1];
    }

    /**
     * If a sales order was used in creating the current sales edit page,
     * get the order number displayed
     *
     * @return order number as string
     */
    public String getOrderNumber() {
        List<WebElement> fieldList = wait.waitForAllElementsPresent(orderNumberLoc);
        WebElement orderNumberField = fieldList.get(fieldList.size() - 1);
        String orderNum = text.getElementText(orderNumberField);

        return orderNum;
    }

    /**
     * If a sales quote was used in creating the current sales edit page,
     * get the quote number displayed
     *
     * @return quote number as string
     */
    public String getQuoteNumber() {
        List<WebElement> fieldList = wait.waitForAllElementsPresent(quoteNumberLoc);
        WebElement quoteNumberField = fieldList.get(fieldList.size() - 1);
        String quoteNum = text.getElementText(quoteNumberField);

        return quoteNum;
    }

    /**
     * Gets No Tax Areas pop up Text
     *
     * @return noTaxText
     * @author bhikshapathi
     */
    public String getNoTaxAreasText() {
        waitForPageLoad();
        WebElement dueDateField = wait.waitForElementPresent(noTaxesDialog);
        String noTaxText = dueDateField.getText();
        if(element.isElementDisplayed(ok)) {
            click.clickElementCarefully(ok);
        }
        return noTaxText;
    }

    /**
     * locates the order due date field and enters today's date in it.
     */
    public void setDueDate() {
        WebElement dueDateField = wait.waitForElementPresent(dueDateFieldLoc);
        text.enterText(dueDateField, transactionDate);
        text.enterText(dueDateField, transactionDate);
    }

    /**
     * clicks on the very first cell in a row to make it interactable
     *
     * @param rowIndex
     */
    public void activateRow(int rowIndex) {
        //rowIndex--;
        waitForPageLoad();
        String firstRowXpath = String.format("//caption[text()='Lines']/../tbody/tr[%s]/td[1]", rowIndex);
        WebElement firstRow = wait.waitForElementDisplayed(By.xpath(firstRowXpath));
        //WebElement activateCell = getRowActivationCell(rowIndex);
       click.clickElementIgnoreExceptionAndRetry(firstRow);
       waitForPageLoad();
    }

    /**
     * clicks on the very first cell in a row to make it interactable in the service order test
     *
     * @param rowIndex
     * @param rowNum
     */
    public void activateRowForServiceOrder(int rowIndex, int rowNum) {
        //rowIndex--;
        waitForPageLoad();
        String firstRowXpath = String.format("(//caption[text()='Lines']/../tbody/tr[%s]/td[1])[%s]", rowIndex, rowNum);
        WebElement firstRow = wait.waitForElementPresent(By.xpath(firstRowXpath));
        //WebElement activateCell = getRowActivationCell(rowIndex);
        try {
            click.clickElementCarefully(firstRow);
        } catch (ElementNotInteractableException e) {
            click.javascriptClick(firstRow);
        }
    }

    /**
     * when activating a row, get the correct cell to click on
     *
     * @param rowIndex row cell is located on
     * @return the cell on the specified row
     */
    public WebElement getRowActivationCell(int rowIndex) {
        WebElement cell = null;
        int columnIndex = 0;

        WebElement tableParentCon = wait.waitForElementPresent(itemTableConLoc);
        WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
        waitForPageLoad();
        List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
        findingCell:
        for (WebElement row : tableRows) {
            row = tableRows.get(rowIndex);
            waitForPageLoad();
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
    public void setItemType(String type, int rowIndex) {
        waitForPageLoad();
        WebElement itemTypeField = getTableCell(rowIndex, 2);
        if(element.isElementPresent(By.xpath("(//th[@abbr='Line No.'])[1]"))){
            itemTypeField = getTableCell(rowIndex, 4);
        }
        wait.waitForElementEnabled(itemTypeField);

        click.clickElementIgnoreExceptionAndRetry(itemTypeField);
        text.setTextFieldCarefully(itemTypeField, type, false);
        waitForPageLoad();
        String value = itemTypeField.getAttribute("value");
        if (!type.equals(value)) {
            jsWaiter.sleep(5000);
            itemTypeField = getTableCell(rowIndex, 2);
            if(element.isElementPresent(By.xpath("(//th[@abbr='Line No.'])[1]"))){
                itemTypeField = getTableCell(rowIndex, 4);
            }
            waitForPageLoad();
            click.clickElementIgnoreExceptionAndRetry(itemTypeField);
            text.setTextFieldCarefully(itemTypeField, type, false);
        }
        itemTypeField.sendKeys(Keys.TAB);
    }


    /**
     * Returns to the first cell of the table by clicking on the Type cell which is always visible,
     * and pressing tab
     * This is because it is not possible to scroll on the table, to avoid errors when the first
     * cells of the table are not visible and therefore not interactable
     *
     * @param rowIndex
     */
    public void returnToFirstCell(int rowIndex) {
        int index = rowIndex - 1;

        WebElement itemTypeField = getTableCell(index, 2);
        wait.waitForElementEnabled(itemTypeField);
        click.clickElement(itemTypeField);
        text.pressTab(itemTypeField);
    }

    /**
     * locates the item number field and enters the item number in it.
     *
     * @param itemNumber desired item number as a string
     * @param rowIndex   which row to write to
     */
    public void enterItemNumber(String itemNumber, int rowIndex) {
        WebElement itemNumberField = getTableCell(rowIndex, 4);
        if(element.isElementPresent(By.xpath("(//th[@abbr='Line No.'])[1]"))){
            itemNumberField = getTableCell(rowIndex, 5);
        }
        click.clickElementIgnoreExceptionAndRetry(itemNumberField);
        waitForPageLoad();
        String currValue = itemNumberField.getAttribute("value");
        if(currValue != null && currValue.isEmpty()) {
            text.enterText(itemNumberField, Keys.BACK_SPACE);
        }
        text.enterText(itemNumberField, itemNumber);
        text.pressTab(By.cssSelector("body"));
    }

    /**
     * when entering the item type into the service item sheet
     * @param itemType
     * @param rowIndex
     */
    public void enterItemTypeForService(String itemType, int rowIndex){
        WebElement itemTypeField = getTableCell(rowIndex, 2);
        wait.waitForElementEnabled(itemTypeField);
        click.clickElementIgnoreExceptionAndRetry(itemTypeField);
        waitForPageLoad();
        String iType = String.format("//option[text()='%s']", itemType);
        click.clickElementCarefully(By.xpath(iType));
    }

    /**
     * when entering the item number into the item table,
     * wait for the autofill box to appear and select the item
     *
     * @param itemNumber number to write and select
     * @param rowIndex   which row to write to
     */
    public void setItemNumberInfo(String itemNumber, int rowIndex) {
        enterItemNumber(itemNumber, rowIndex);
        WebElement box = wait.waitForElementDisplayed(itemAutofillBoxLoc);
        List<WebElement> optionsList = wait.waitForAllElementsDisplayed(itemAutofillOptionsLoc, box);

        for (WebElement option : optionsList) {
            String foundNumber = text.getElementText(option);
            if (itemNumber.equals(foundNumber)) {
                click.clickElementCarefully(option);
                wait.waitForElementNotDisplayedOrStale(box, 15);
                break;
            }
        }
    }

    /**
     * locates the description field and enters the description to it
     *
     * @param itemDescription
     * @param rowIndex
     */
    public void enterItemDescription(String itemDescription, int rowIndex) {
        WebElement descriptionField = getTableCell(rowIndex, 6);
        click.javascriptClick(descriptionField);
        text.setTextFieldCarefully(descriptionField, itemDescription);
        text.pressTab(By.cssSelector("body"));      // force scroll to next field
    }

    /**
     * locates the location field and enters the location code to it
     *
     * @param locationCode
     * @param rowIndex
     * @author bhikshapathi
     */
    public void enterLocationCode(String locationCode, int rowIndex) {
        WebElement locationField;
        String docType = getDocumentType();
        String docTypeMain = docType.substring(0, 5);
        if (docTypeMain.equalsIgnoreCase("S-RET") || docTypeMain.equalsIgnoreCase("P-RET")) {
            locationField = getTableCell(rowIndex, 8);
        } else {
            locationField = getTableCell(rowIndex, 7);
        }
        try {
            scroll.scrollElementIntoView(locationField);
            locationField.click();

        } catch (ElementNotInteractableException e) {
            click.javascriptClick(locationField);
        }

        text.setTextFieldCarefully(locationField, locationCode, false);
        text.pressEnter(locationField);

        text.pressTab(By.cssSelector("body"));      // force scroll to next field
    }

    /**
     * locates the location field and enters the location code to it
     *
     * @param amount
     * @param rowIndex
     * @author bhikshapathi
     */
    public void enterLineAmount(String amount, int rowIndex) {
        WebElement lineamountField = getTableCell(rowIndex, 16);
        try {
            lineamountField.click();
        } catch (ElementNotInteractableException e) {
            click.javascriptClick(lineamountField);
        }
        lineamountField.clear();
        text.enterText(lineamountField, amount);
    }

    /**
     * locates the line discount % field and enters the discount for a product in it
     *
     * @param discount
     * @param rowIndex
     */
    public void enterDiscountPercent(String discount, int rowIndex) {
        // scroll table to display line discount column
        String script = "document.querySelector('.ms-nav-grid-edit').closest('div').nextElementSibling.scrollLeft = 1000";
        executeJs(script);

        WebElement lineDiscountField = getTableCell(rowIndex, 15);
        action.moveToElement(lineDiscountField).click(lineDiscountField).perform();
        WebElement lineDiscountElement = wait.waitForElementPresent(lineDiscountPercentage);
        action.moveToElement(lineDiscountElement).click(lineDiscountElement).perform();
        text.setTextFieldCarefully(lineDiscountField, discount, false);
    }

    /**
     * locates the location field and clears it
     *
     * @param rowIndex
     * @author bhikshapathi
     */
    public void clearLocationCode(int rowIndex) {
        WebElement locationField = getTableCell(rowIndex, 6);
        try {
            locationField.click();
        } catch (ElementNotInteractableException e) {
            click.javascriptClick(locationField);
        }
        locationField.clear();
    }

    /**
     * locates the location field and clears it
     *
     * @param rowIndex
     * @author bhikshapathi
     */
    public void deleteLine(int rowIndex) {
        waitForPageLoad();
        WebElement deleteField = getTableCell(rowIndex, 3);
        try {
            deleteField.click();
        } catch (ElementNotInteractableException e) {
            click.javascriptClick(deleteField);
        }
        click.clickElementCarefully(deleteLine);
        waitForPageLoad();
        click.clickElementCarefully(acceptDelete);
    }

    /**
     * locates the quantity field and enters the quantity.
     *
     * @param quan quantity of the item selected as a string
     */
    public void enterQuantity(String quan, int rowIndex) {
        List<WebElement> textHeader = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));
        for (int i = 0; i < textHeader.size(); i++) {
            String textContent = textHeader.get(i).getAttribute("abbr");
            if (textContent.equals("Quantity")) {
                int columnIndex = i + 1;
                rowIndex = rowIndex + 1;
                By fieldLoc = By.xpath(String.format("(//table[contains(@id,'BusinessGrid')][not(@tabindex)]//td[%s])[%s]/input", columnIndex, rowIndex));
                WebElement quantityField = wait.waitForElementPresent(fieldLoc);
                action.moveToElement(quantityField).click().perform();
                // if the quantity field is not visible, then we use TAB to scroll
                // use counter to prevent infinite loop
                int maxIterations = textHeader.size();
                do{
                    text.pressTab(By.cssSelector("body"));
                    waitForPageLoad();
                    maxIterations--;
                }while(!element.isElementDisplayed(quantityField) && maxIterations > 0);

                click.clickElementIgnoreExceptionAndRetry(quantityField);
                text.selectAllAndInputText(quantityField, quan);
                quantityField.sendKeys(Keys.TAB);
                break;
            }
        }

    }
    /**
     * Gets the amount including tax field value
     *
     * @return amountIncludingTax
     */
    public String amountTax(){
        WebElement amountIncludingTaxes = wait.waitForElementPresent(amountIncludingTax);
        try {
            action.moveToElement(amountIncludingTaxes).click(amountIncludingTaxes).perform();
        }catch (Exception e){
            click.javascriptClick(amountIncludingTaxes);
        }
        String amountIncludingTax=attribute.getElementAttribute(amountIncludingTaxes,"title");
        return amountIncludingTax;

    }

    /**
     * clicks on the location code field to auto populate the store location code from the selected customer profile.
     */
    public void clickLocationCode(int rowIndex) {
        WebElement locationCodeField = getTableCell(rowIndex, 6);
        try {
            click.clickElement(locationCodeField);
        } catch (ElementNotInteractableException | NoSuchElementException e) {

        }
    }

    /**
     * locates the tax area code field and enters Vertex in it.
     * presses tab on the current element until it reaches the tax area code element
     * (assumes initial current element is a cell on the table)
     * (scrolling to element or clicking on it then trying to enter text does not bring it into view)
     */
    public void selectTaxAreaCode() {
        WebElement currentElement = driver
                .switchTo()
                .activeElement();
        String currentAriaLabel = currentElement.getAttribute("aria-label");

        while (!currentAriaLabel.contains("Tax Area Code, ")) {
            text.pressTab(currentElement);
            currentElement = driver
                    .switchTo()
                    .activeElement();
            currentAriaLabel = currentElement.getAttribute("aria-label");
        }
        WebElement taxAreaCodeField = currentElement;
        click.clickElement(taxAreaCodeField);
        text.enterText(taxAreaCodeField, "VERTEX");
        taxAreaCodeField.sendKeys(Keys.ENTER);
    }


    /**
     * locate the unit price field and enter the price
     *
     * @param price
     */
    public void enterUnitPrice(String price, int rowIndex) {
        waitForPageLoad();
        List<WebElement> textHeader = driver.findElements(tableHeaders);

        for (int i = 0; i < textHeader.size(); i++) {

            String textContent = textHeader.get(i).getAttribute("abbr");
            if (textContent.contains("Unit Price Excl. Tax") || textContent.contains("Direct Unit Cost Excl. Tax")) {
                int columnIndex = i + 1;
                WebElement priceField = wait.waitForElementPresent(By.xpath("//tr[" + rowIndex + "]/td[" + columnIndex + "]/input[1]"));
                try {
                    click.moveToElementAndClick(priceField);
                    waitForPageLoad();
                    text.enterText(priceField, price);
                    text.pressTab(priceField);

                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(priceField);
                }
            }

        }
    }

    /**
     * locate the tax group code field and enter the code
     *
     * @param code
     * @param rowIndex
     */

    /**
     * locate the tax group code field and enter the code
     *
     * @param code
     */
    public void enterTaxGroupCode(String code, int rowIndex) {

        waitForPageLoad();
        List<WebElement> textHeader = driver.findElements(tableHeadersLoc);

        for (int i = 0; i < textHeader.size(); i++) {

            String textContent = textHeader.get(i).getAttribute("abbr");
            if (textContent.contains("Tax Group Code")) {
                int columnIndex = i + 1;
                WebElement taxGroupField = wait.waitForElementPresent(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)"));
                click.clickElementIgnoreExceptionAndRetry(taxGroupField);
                text.clearText(taxGroupField);
                text.enterText(taxGroupField, code);
                text.pressEnter(taxGroupField);
            }

        }
    }

    /**
     * locate the tax group code field and enters the code
     *
     * @param code
     * @param rowIndex
     */
    public void enterTaxAreaCode(String code, int rowIndex) {

        waitForPageLoad();
        List<WebElement> textHeader = driver.findElements(tableHeadersLoc);

        for (int i = 0; i < textHeader.size(); i++) {

            String textContent = textHeader.get(i).getAttribute("abbr");
            if (textContent.contains("Tax Area Code")) {
                int columnIndex = i + 1;
                WebElement taxAreaField = wait.waitForElementPresent(By.xpath("(//tr[" + rowIndex + "]/td[" + columnIndex + "]/input)"));
                action.moveToElement(taxAreaField).click(taxAreaField).perform();
                try {

                    click.clickElementCarefully(taxAreaField);
                } catch (ElementNotInteractableException e) {
                    click.javascriptClick(taxAreaField);
                }
                text.clearText(taxAreaField);
                text.enterText(taxAreaField, code);
                text.pressEnter(taxAreaField);
            }

        }
    }

    /**
     * locate the tax group code field and enter the code
     *
     * @param amount
     * @author bhikshapathi
     */
    public void enterLineAmount(String amount) {
        WebElement currentElement = driver
                .switchTo()
                .activeElement();
        String currentAriaLabel = currentElement.getAttribute("aria-label");

        while (!currentAriaLabel.contains("Line Amount Excl. Tax, ")) {
            text.pressTab(currentElement);
            currentElement = driver
                    .switchTo()
                    .activeElement();
            currentAriaLabel = currentElement.getAttribute("aria-label");
        }

        WebElement lineAmountField = currentElement;
        click.clickElement(lineAmountField);
        lineAmountField.clear();
        text.enterText(lineAmountField, amount);
    }

    /**
     * locates the quantity to assign field on the table and clicks on it,
     * locates the quantity to assign field on the popup and enters the number to assign
     *
     * @param qtyToAssign
     */
    public void assignToQuantity(String qtyToAssign, int rowIndex) {

        List<WebElement> textHeader = driver.findElements(By.xpath("//table[contains(@class,'ms-nav-grid-edit')][not(@summary)]//th"));
        for (int i = 0; i < textHeader.size(); i++) {
            String textContent = textHeader.get(i).getAttribute("abbr");
            if (textContent.equals("Qty. to Assign")) {
                int columnIndex = i + 1;
                By qtyXpath = By.xpath(String.format("(//table[contains(@id,'BusinessGrid')][not(@tabindex)]//td[%s])[%s]", columnIndex, rowIndex));

                // if the quantity field is not visible, then we use TAB to scroll
                // use counter to prevent infinite loop
                int maxIterations = textHeader.size();
                do{
                    text.pressTab(By.cssSelector("body"));
                    waitForPageLoad();
                    maxIterations--;
                }while(!element.isElementDisplayed(qtyXpath) && maxIterations > 0);

                WebElement quantityField = wait.waitForElementPresent(qtyXpath);

                action.moveToElement(quantityField).perform();
                click.clickElementIgnoreExceptionAndRetry(quantityField);

                WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
                WebElement tableCon = wait.waitForElementDisplayed(itemAssignmentTableConLoc, dialog);
                WebElement assignField = wait.waitForElementDisplayed(assignToQuantityLoc, tableCon);
                click.clickElement(assignField);
                text.enterText(assignField, qtyToAssign);
                text.pressEnter(assignField);
                dialogBoxClickClose();
                break;
            }
        }
    }

    /**
     * Enters the document number when creating a new document
     *
     * @param docNum
     */
    public void enterDocumentNumber(String docNum) {
        WebElement documentNumberField = wait.waitForElementEnabled(documentNumberInputLoc);
        text.enterText(documentNumberField, docNum);
        text.pressEnter(documentNumberField);
    }

    /**
     * select the tax Area code value
     *
     * @param code     indicates tax area code
     * @param rowIndex the row the cell is on
     */
    public void setTaxAreaCode(String code, int rowIndex) {
        rowIndex = rowIndex - 1;
        WebElement taxAreaField = getTableCell(rowIndex, 11);
        wait.waitForElementEnabled(taxAreaField);
        click.clickElementCarefully(taxAreaField);
        waitForPageLoad();
        String taxAreaCode = String.format("//a[text()='%s']", code);
        click.clickElementIgnoreExceptionAndRetry(By.xpath(taxAreaCode));
        text.pressTab(taxAreaField);
    }


    /**
     * Enters customer code
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param customerCode
     */
    public void enterCustomerCode(String customerCode) {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement customerListField = wait.waitForElementPresent(customerListFieldLoc);
        text.enterText(customerListField, customerCode);
        text.pressEnter(customerListField);
    }

    /**
     * Enters address
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param address
     * @author bhikshapathi
     */
    public void enterAddress(String address) {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement addressField = wait.waitForElementPresent(addressFieldLoc);
        text.clearText(addressField);
        text.enterText(addressField, address);
        text.pressEnter(addressField);
    }

    /**
     * Enters city
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param city
     * @author bhikshapathi
     */
    public void enterCityName(String city) {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement cityField = wait.waitForElementPresent(cityFieldLoc);
        text.clearText(cityField);
        text.enterText(cityField, city);
        text.pressEnter(cityField);
    }

    /**
     * Enters state
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param state
     * @author bhikshapathi
     */
    public void enterState(String state) {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement stateField = wait.waitForElementPresent(stateFieldLoc);
        text.clearText(stateField);
        text.enterText(stateField, state);
        text.pressEnter(stateField);
    }

    /**
     * Enters zipcode
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param zip
     * @author bhikshapathi
     */
    public void enterZipCodeValue(String zip) {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement zipField = wait.waitForElementPresent(zipFieldLoc);
        text.clearText(zipField);
        text.enterText(zipField, zip);
        text.pressEnter(zipField);
    }

    /**
     * Enters zipcode
     * For use when manually inputting information/when expecting autofill on customer information
     *
     * @param country
     * @author bhikshapathi
     */
    public void enterCountry(String country) {
        handlePopUpMessage(alertOkButtonLoc, shortTimeout);
        WebElement countryField = wait.waitForElementPresent(countryFieldLoc);
        text.clearText(countryField);
        text.enterText(countryField, country);
        text.pressEnter(countryField);
    }

    /**
     * does the process of selecting a customer for the order
     *
     * @param customerName name of the customer to select from an existing list of customers.
     */
    public void selectCustomer(String customerName, String customerCode) {
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
    public String getJurisdiction() {
        waitForPageLoad();
        WebElement jurisdictionField = wait.waitForElementPresent(taxJurisdictionFieldLoc);

        String jurisdictionText = attribute.getElementAttribute(jurisdictionField, "textContent");

        return jurisdictionText;
    }

    /**
     * locates the tax rate field and gets the value in it.
     *
     * @return the tax rate value as a string
     */
    public String getTaxRate() {
        String actualTaxRate = "";
        waitForPageLoad();
        List<WebElement> taxRateField = wait.waitForAllElementsPresent(taxRateFieldLoc);
        if (taxRateField.size() <= 0) {
            System.out.println("Unable to locate the tax rate field containers");
        } else {
            actualTaxRate = attribute.getElementAttribute(taxRateField.get(0), "textContent");
        }
        return actualTaxRate;
    }

    /**
     * locates the tax rate field and gets the value in it.
     *
     * @return the tax rates value as a list of Strings
     * @author bhikshapathi
     */
    public List<String> getMultipleTaxRates() {
        String actualTaxRate = "";
        waitForPageLoad();
        List<WebElement> taxRateField = wait.waitForAllElementsPresent(taxRateFieldLoc);
        List<String> value = new ArrayList<String>();
        if (taxRateField.size() <= 0) {
            System.out.println("Unable to locate the tax rate field containers");
        } else {
            for (int j = 0; j < taxRateField.size(); j++) {
                //actualTaxRate = attribute.getElementAttribute(taxRateField.get(j), "textContent");
                value.add(attribute.getElementAttribute(taxRateField.get(j), "textContent"));
            }
        }
        return value;
    }

    /**
     * locates the tax amount field and gets the value in it.
     * @param fieldType
     *
     * @return the tax amounts value as a list of Strings
     * @author bhikshapathi
     */
    public List<String> getMultipleTaxAmount(String fieldType) {
        if (fieldType.equals("Purchase")) {
            fieldType = "Purch";
        }
        String actualTaxRate = "";
        waitForPageLoad();
        int locationOfColumn = getColumnIndex("Tax Amount");
        String formattedString = String.format("//table/caption[text()='VER_"+fieldType+" Line Tax Details']/../tbody/tr/td[%s]/span", locationOfColumn);
        List<WebElement> taxRateField = wait.waitForAllElementsPresent(By.xpath(formattedString));
        List<String> value = new ArrayList<String>();
        if (taxRateField.size() <= 0) {
            VertexLogger.log("Unable to locate the tax rate field containers");
        } else {
            for (int j = 0; j < taxRateField.size(); j++) {
                value.add(attribute.getElementAttribute(taxRateField.get(j), "textContent"));
            }
        }
        return value;
    }


    /**
     * Method to get index of column based on column name
     *
     * @param columnName
     *
     * @return index of column
     */
    private int getColumnIndex( String columnName )
    {
        waitForPageLoad();
        int columnIndex = 1;
        wait.waitForElementDisplayed(getDetailTable());
        WebElement tableContainer = getDetailTable();
        WebElement headerRow = tableContainer.findElement(TABLE_HEADER_ROW);
        wait.waitForElementDisplayed(headerRow);
        List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
        for ( int x = 0 ; x < headerCells.size() ; x++ )
        {
            WebElement column = headerCells.get(x);
            String current_column_label = column.getAttribute("abbr");
            if ( current_column_label.equals(columnName) )
            {
                columnIndex = x+1;
                break;
            }
        }
        return columnIndex;
    }

    /**
     * Method to return order product table as web element
     *
     * @return order product table as a WebElement
     */
    private WebElement getDetailTable( )
    {
        WebElement tableContainer = element.getWebElement(TABLE);
        return tableContainer;
    }

    /**
     * locates the quantity field and gets the value in it.
     *
     * @return the quantity value as a list of Strings
     */
    public List<String> getMultipleQuantities() {
        waitForPageLoad();
        int locationOfColumn = getColumnIndex("Quantity");
        By quantityFields = By.xpath(String.format("//table/caption[contains(text(),'Line Tax Details')]/..//td[%s]/span", locationOfColumn));
        List<WebElement> quantityField = wait.waitForAllElementsPresent(quantityFields);
        List<String> value = new ArrayList<String>();
        if (quantityField.size() <= 0) {
            VertexLogger.log("Unable to locate the quantity field containers");
        } else {
            for (int i = 0; i < quantityField.size(); i++) {
                value.add(attribute.getElementAttribute(quantityField.get(i), "textContent"));
            }
        }
        return value;
    }

    /**
     * Get the tax amount for each line in the Service Line Tax Details
     * @param amountOfTaxValidation
     * @return - return the string values for each line
     */
    public List<String> getTaxAmountForEachServiceLine(String... amountOfTaxValidation){
        List <WebElement> serviceTaxLines = wait.waitForAllElementsPresent(selectServiceTaxLines);
        WebElement taxAmountFieldSelected = wait.waitForElementPresent(taxAmountFieldLoc);

        List <String> taxAmountForLines = new ArrayList<String>();
        List <String> addEachTaxValue  = new ArrayList<String>();

        for(int i = 0; i < serviceTaxLines.size(); i++){
            click.clickElementCarefully(serviceTaxLines.get(i));
            action.moveToElement(taxAmountFieldSelected).perform();
            String taxValue = attribute.getElementAttribute(taxAmountFieldLoc, "textContent");
            addEachTaxValue.add(taxValue);

            taxAmountForLines.add(taxValue);
        }
        return taxAmountForLines;
    }

    /**
     * Returns the string
     * @param stringValue
     */
    public String[] toString(String[] stringValue){
        return stringValue;
    }

    /**
     * locates the tax amount field and gets the value from it
     *
     * @return tax amount as a string
     */
    public String getTaxAmount() {
        WebElement taxAmountField = wait.waitForElementPresent(taxAmountFieldLoc);
        String actualTaxAmount = attribute.getElementAttribute(taxAmountField, "textContent");
        return actualTaxAmount;
    }

    /**
     * locates the tax amount field and gets the value from it
     *
     * @return taxable amount as a string
     * @author bhikshapathi
     */
    public String getTaxableAmount() {
        WebElement taxableAmountField = wait.waitForElementPresent(totalExclTaxAmount);
        String actualTaxableAmount = attribute.getElementAttribute(taxableAmountField, "textContent");
        return actualTaxableAmount;
    }

    /**
     * In Statistics window, locates the tax amount field and gets the value from it
     *
     * @return taxable amount as a string
     */
    public String getStatisticsTaxAmount() {
        WebElement statTaxAmountField = wait.waitForElementPresent(statTaxAmountFieldLoc);
        String actualTaxAmount = attribute.getElementAttribute(statTaxAmountField, "textContent");
        return actualTaxAmount;
    }

    /**
     * Gets the total tax amount on the sales document
     *
     * @return total tax as string
     */
    public String getTotalTaxAmount() {
        jsWaiter.sleep(20000);
        waitForPageLoad();
        List<WebElement> fieldList = wait.waitForAllElementsPresent(totalTaxAmountLoc);

        WebElement field = fieldList.get(fieldList.size() - 1);
        String tax = text.getElementText(field);
        try {
            wait.waitForElementDisplayed(field);
            tax = text.getElementText(field);
        } catch (Exception e) {
        }
        return tax;
    }

    /**
     * It verify Fields from Vertex Tax Details Table
     *
     * @param fieldName
     * @return
     */
    public boolean verifyVertexTaxDetailsField(String fieldName) {
        waitForPageLoad();
        List<WebElement> fieldEle = wait.waitForAllElementsPresent(tableField);
        boolean result = false;
        for (int i = 0; i < fieldEle.size(); i++) {
            String fieldString = fieldEle.get(i).getAttribute("textContent");
            if (fieldString.contains(fieldName)) {
                result = true;
                break;
            }
        }
        return result;
    }


    /**
     * locates the sell-to customer number field and gets the value from it
     *
     * @return customer number as a string
     */
    public String getSellToCustomerNumber() {
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
    public String getSellToCustomerName() {
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
     * @return cell, as WebElement
     */
    public WebElement getTableCell(int rowIndex, int columnIndex) {
        WebElement cell = null;

        List<WebElement> tableCons = wait.waitForAllElementsPresent(itemTableConLoc);
        WebElement tableParentCon = tableCons.get(tableCons.size() - 1);
        WebElement tableCon = wait.waitForElementPresent(By.tagName("tbody"), tableParentCon);
        List<WebElement> tableRows = wait.waitForAllElementsPresent(By.tagName("tr"), tableCon);
        findingCell:
        for (WebElement row : tableRows) {
            row = tableRows.get(rowIndex);
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
            for (WebElement column : rowColumns) {
                column = rowColumns.get(columnIndex);
                try {
                    cell = column.findElement(By.tagName("input"));
                } catch (org.openqa.selenium.NoSuchElementException e) {
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
     * @return the webElement of the customer table cell
     */
    public WebElement getTableCell(String customerName) {
        WebElement cell = null;
        WebElement tableParentCon = wait.waitForElementPresent(tableParentConLoc);
        WebElement tableCon = wait.waitForElementPresent(tableConLoc, tableParentCon);
        WebElement table = wait.waitForElementPresent(By.tagName("table"), tableCon);
        WebElement tableBody = wait.waitForElementPresent(By.tagName("tbody"), table);
        List<WebElement> rows = wait.waitForAllElementsPresent(By.tagName("tr"), tableBody);
        for (WebElement row : rows) {
            List<WebElement> rowColumns = wait.waitForAllElementsPresent(By.tagName("td"), row);
            cell = element.selectElementByText(rowColumns, customerName);
            if (cell != null) {
                break;//fixme scott added the break-after-find when refactoring, chekc that if function breaks
            }
        }
        return cell;
    }

    /**
     * locates and clicks on the close button for the tax details dialog
     */
    public void closeTaxDetailsDialog() {
        wait.waitForElementDisplayed(close);
        try{
            click.clickElementCarefully(close);
        }catch (Exception ex){
            VertexLogger.log(ex.toString());
            click.javascriptClick(close);
        }
    }

    /**
     * locates and clicks on the Ok button for the tax details dialog
     *
     * @return pop text
     * @author bhikshapathi
     */
    public String noTaxDetailsDialog() {
        WebElement popUp = wait.waitForElementDisplayed(noTaxDetails);
        String pop = popUp.getText();
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
    public String taxDetailsDocumentReadHeader() {
        waitForPageLoad();
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
    public BusinessSalesInvoicePage goToInvoice() {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElementIgnoreExceptionAndRetry(yesButton);
        return initializePageObject(BusinessSalesInvoicePage.class);
    }

    /**
     * After posting a credit memo, click "Yes" to go to the posted memo
     *
     * @return credit memo page
     */
    public BusinessSalesCreditMemoPage goToPostedMemo() {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        //wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);

        return initializePageObject(BusinessSalesCreditMemoPage.class);
    }

    /**
     * locate and click on the print Preview button
     *
     * @return the print preview page
     */
    public BusinessPrintPreviewPage clickPrintPreview() {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsPresent(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Preview");
        click.clickElement(button);
        wait.waitForElementDisplayed(previewContainer);

        return initializePageObject(BusinessPrintPreviewPage.class);
    }

    /**
     * Selects the document type and inputs the document number
     * when copying a document
     *
     * @param documentType   the document type to select
     * @param documentNumber the document number to input
     */
    public void fillOutCopyDocumentInformation(String documentType, String documentNumber) {
        WebElement copyPopup = wait.waitForElementDisplayed(dialogBoxLoc);

        WebElement docTypeSelect = wait.waitForElementEnabled(documentTypeSelectLoc, copyPopup);
        dropdown.selectDropdownByDisplayName(docTypeSelect, documentType);

        WebElement docNumberField = wait.waitForElementEnabled(documentNumberFieldLoc, copyPopup);
        text.enterText(docNumberField, documentNumber);
        text.pressEnter(docNumberField);
    }

    /**
     * Toggles the Include Header option when copying a document
     * @param option boolean of option to select
     *
     */
    public void toggleIncludeHeader(boolean option) {
        WebElement includeHeaderOption = wait.waitForElementEnabled(includeHeaderLoc);

        boolean currentOption = Boolean.parseBoolean(includeHeaderOption.getAttribute("aria-checked"));

        if (currentOption != option) {
            click.clickElementCarefully(includeHeaderOption);
        }
    }
    /**
     * Clicks the header for the Shipping and Billing category
     * to open the section
     */
    public void openShippingAndBillingCategory() {
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);

        WebElement shippingAndBilling = element.selectElementByText(categoriesList, "Shipping and Billing");

        click.clickElement(shippingAndBilling);

        if(element.isElementDisplayed(shipToDropdownLoc)){
            wait.waitForElementDisplayed(shipToDropdownLoc);
        }
    }

    /**
     * Selects Alternate Shipping Addresss from the ship-to dropdown, and selects
     * an alternate shipping address based on the code
     *
     * @param addressCode
     */
    public void selectAlternateShipToAddress(String addressCode) {
        WebElement shipToSelect = wait.waitForElementEnabled(shipToDropdownLoc);
        dropdown.selectDropdownByDisplayName(shipToSelect, "Alternate Shipping Address");

        if(element.isElementDisplayed(shipToCodeDropdownLoc))
            click.clickElementIgnoreExceptionAndRetry(shipToCodeDropdownLoc);
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        String addressXpath = String.format("//a[text()='%s']", addressCode);
        WebElement addressEle = wait.waitForElementDisplayed(By.xpath(addressXpath));
        click.clickElementCarefully(addressEle);
        wait.waitForElementNotDisplayedOrStale(addressEle, 15);
        wait.waitForElementNotDisplayedOrStale(dialog, 15);
    }

    /**
     * Selects custom Shipping Addresss from the ship-to dropdown,
     *
     * @author bhikshapathi
     */
    public void selectCustomShipToAddress() {
        WebElement shipToSelect = wait.waitForElementEnabled(shipToDropdownLoc);
        dropdown.selectDropdownByDisplayName(shipToSelect, "Custom Address");
        waitForPageLoad();
    }

    /**
     * Selects Shipping Method Code from the Shipment Method Code dropdown
     * @param shipmentMethodCode
     *
     * @author Mario Saint-Fleur
     */
    public void selectShipmentMethodCode(String shipmentMethodCode) {
        WebElement shipmentToSelect = wait.waitForElementEnabled(shipmentMethodCodeToDropdownLoc);
        text.enterText(shipmentToSelect, shipmentMethodCode);
        waitForPageLoad();
    }

    /**
     * Get the imposition value
     *
     * @author Mario Saint-Fleur
     */
    public String getImpositionValue(){
        wait.waitForElementPresent(impositionFieldVal);
        String impositionValue = attribute.getElementAttribute(impositionFieldVal, "title");

        return impositionValue;
    }

    /**
     * Reads the text on a dialog box
     *
     * @return the text displayed on a dialog box
     */
    public String dialogBoxReadMessage() {
        waitForPageLoad();
        WebElement dialogBox = wait.waitForElementPresent(dialogBoxLoc);
        WebElement dialogMessage = wait.waitForElementPresent(dialogMessageCon, dialogBox);

        String messageTxt = dialogMessage.getAttribute("outerText");
        return messageTxt;
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "OK"
     */
    public void dialogBoxClickOk() {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "OK");
        click.clickElementIgnoreExceptionAndRetry(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "Yes"
     */
    public void dialogBoxClickYes() {
        waitForPageLoad();
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Yes");
        click.clickElementIgnoreExceptionAndRetry(button);
        wait.waitForElementNotDisplayedOrStale(button, 30);
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "Close"
     */
    public void dialogBoxClickClose() {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "Close");
        click.clickElement(button);

        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Generalized method, should work on any popup box
     * Selects "No"
     */
    public void dialogBoxClickNo() {
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "No");
        click.clickElement(button);
        wait.waitForElementNotDisplayedOrStale(button, 15);
    }

    /**
     * Generalized method, for expand table
     *
     * @author bhikshapathi
     */
    public void expandTable() {
        wait.waitForElementPresent(expandButton);
        click.javascriptClick(expandButton);
        jsWaiter.waitForLoadAll();

    }

    /**
     * Generalized method, for exit expand table
     *
     * @author bhikshapathi
     */
    public void exitExpandTable() {
        wait.waitForElementDisplayed(exitExpandButton);
        click.javascriptClick(exitExpandButton);
        jsWaiter.waitForLoadAll();
    }

    /**
     * Switch on the Return Original Quantity toggle button
     */
    public void enableReturnOriginalQuantity(){
        if(element.isElementDisplayed(enableReturnOriginalQuantity)){
            click.clickElementCarefully(enableReturnOriginalQuantity);
            waitForPageLoad();
        }
    }

    /**
     * click posted Documents line to reverse in Process tab
     */
    public void selectPostedDocumentLinesToReverse(){
        WebElement clickGetPosted = wait.waitForElementPresent(getPostedDocumentLinesToReverse);
        click.clickElementCarefully(clickGetPosted);
        waitForPageLoad();
    }

    /**
     * Generalized method, for clear Location code From Shipping and Billing
     *
     * @author bhikshapathi
     */
    public void clearLocationCodeFromShippingandBilling() {
        WebElement locationField = wait.waitForElementPresent(locationcodefield);
        scroll.scrollElementIntoView(locationField);
        click.javascriptClick(locationField);
        locationField.clear();
        waitForPageLoad();
        locationField.sendKeys(Keys.TAB);
        wait.waitForElementDisplayed(locCodePopUp);
        click.clickElementCarefully(ok);
        waitForPageLoad();
    }

    /**
     * Generalized method, for Update Location code From Shipping and Billing
     *
     * @param locationCode
     * @author bhikshapathi
     */
    public void updateLocationCodeFromShippingandBilling(String locationCode) {
        WebElement locationField = wait.waitForElementPresent(locationcodefield);
        scroll.scrollElementIntoView(locationField);
        click.javascriptClick(locationField);
        if(!element.getWebElement(locationcodefield).getAttribute("value").equals(""))
        { locationField.clear();
           }
        wait.waitForElementEnabled(locationcodefield);
        scroll.scrollElementIntoView(locationField);
        click.javascriptClick(locationField);
        locationField.sendKeys(locationCode);
        locationField.sendKeys(Keys.TAB);
        wait.waitForElementDisplayed(locCodePopUp);
        click.javascriptClick(ok);
        waitForPageLoad();
    }

    /**
     * This method is to get Tax group code Alert Message from statistics Tab
     *
     * @return String of the error
     * @author dpatel
     */
    public String getAlertMessageForTaxGroupCode() {
        String alertText;
        WebElement taxGroupAlertEle = wait.waitForElementPresent(taxGroupAlert);
        alertText = text.getElementText(taxGroupAlertEle);
        click.clickElementCarefully(ok);
        return alertText;
    }

    /**
     * CDBC-266
     * This method is to create Expected Alert Message Strings
     *
     * @param documentType Type of document
     * @param documentNo   Document Number
     * @return String of the expected alert message
     * @author dpatel
     * @author P.Potdar
     */

    public String createExpectedAlertMessageStrings(String documentType, String documentNo, String lineNo) {
        if (documentType.contains("Quote")) {
            return "Tax Group Code must have a value in Sales Line: Document Type=" + documentType + ", Document No.=" + documentNo + ", Line No.=" + lineNo + ". It cannot be zero or empty.\n" +
                    "\n" +
                    "Page Edit - Sales Statistics has to close.";
        } else {
            return "Tax Group Code must have a value in Sales Line: Document Type=" + documentType + ", Document No.=" + documentNo + ", Line No.=" + lineNo + ". It cannot be zero or empty.\n" +
                    "\n" +
                    "Page Edit - Sales Order Statistics has to close.";
        }
    }


    /**
     * This method is to get Tax group code Alert Message from statistics Tab
     *
     * @return String of the error
     * @author dpatel
     */
    public String getAddressValidPopupMessage() {
        String alertText;
        WebElement addValidPopup = wait.waitForElementPresent(taxGroupAlert);
        alertText = text.getElementText(addValidPopup);
        if (element.isElementPresent(ok)) {
            click.clickElementCarefully(ok);
        } else {
            WebElement messageCon = wait.waitForElementPresent(messageConLoc);
            List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, messageCon);
            WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
            click.clickElement(yesButton);
            wait.waitForElementNotDisplayedOrStale(yesButton, shortTimeout);
        }
        return alertText;
    }

    /**
     * This method is to get document type from page title
     *
     * @return String of doc type
     */
    public String getDocumentType() {
        WebElement quantityField = driver.findElement(docType);
        String documentType = quantityField.getText();
        return documentType;
    }

    /**
     * This method is to get alert message from window
     *
     * @return String of error description
     */
    public String getAlertMessage() {
        wait.waitForElementDisplayed(errorDescription);
        String errormsg = attribute.getElementAttribute(errorDescription, "title");
        return errormsg;
    }

    /**
     * This method is to create expected error message String
     *
     * @param docType Type of document
     * @param docNo   Document Number
     * @param lineNo  line Number of Table
     * @return String of the expected alert message
     */
    public String expectedMessages(String docType, String docNo, String lineNo) {
        return "Tax Group Code must have a value in Sales Line: Document Type=" + docType + ", Document No.=" + docNo + ", Line No.=" + lineNo + ". It cannot be zero or empty.";
    }

    /**
     * Enters the vendor code
     * For use when manually inputting information on vendor
     *
     * @param vendorCode
     */
    public void enterVendorCode(String vendorCode) {
        WebElement vendorLoc = wait.waitForElementPresent(vendorFieldLoc);
        text.enterText(vendorLoc, vendorCode);
        text.pressEnter(vendorLoc);
    }

    /**
     * Enters the vendor invoice No
     * For use when manually inputting information on vendor
     *
     * @param vendorInvoice
     */
    public void enterVendorInvoiceNo(String vendorInvoice) {
        WebElement vendorInvoiceLoc = wait.waitForElementPresent(vendorInvoiceNoLoc);
        text.enterText(vendorInvoiceLoc, vendorInvoice);
        text.pressEnter(vendorInvoiceLoc);
    }

    /**
     * This method is verify if Tax area code field exists
     *
     * @param nonVertexTaxAreaCode no vertex tax area code
     * @param expectedTax          tax
     * @return boolean true if TAC and value exists
     */
    public boolean isNonVertexTaxAreaCodePresent(String nonVertexTaxAreaCode, String expectedTax) {

        String tacXpath = String.format("//a[text()='%s']/..//span", nonVertexTaxAreaCode);
        WebElement taxAreaCodeField = wait.waitForElementDisplayed(By.xpath(tacXpath));
        String taxValue = taxAreaCodeField.getText();
        boolean isPresent = element.isElementDisplayed(taxAreaCodeField);
        if (isPresent && taxValue.equals(expectedTax))
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    /**
     * Selects Location from the ship-to dropdown in Shipping and Payment
     */
    public void selectLocationInShipToAddress() {
        WebElement shipToInShipping = wait.waitForElementEnabled(shipToDropdownInPurchasing);
        dropdown.selectDropdownByDisplayName(shipToInShipping, "Location");
        waitForPageLoad();
    }

    /**
     * Selects Customer Address from the ship-to dropdown in Shipping and Payment
     */
    public void selectCustomerInShipToAddress() {
        WebElement shipTo = wait.waitForElementEnabled(shipToDropdownInPurchasing);
        dropdown.selectDropdownByDisplayName(shipTo, "Customer Address");
        waitForPageLoad();
    }

    /**
     * Selects Custom Address from the ship-to dropdown in Shipping and Payment category in Purchasing Documents
     */
    public void selectCustomInShipToAddressForPurchase() {
        WebElement shipTo = wait.waitForElementEnabled(shipToDropdownInPurchasing);
        dropdown.selectDropdownByDisplayName(shipTo, "Custom Address");
        waitForPageLoad();
    }

    /**
     * Selects Alternate Vendor Address from the ship-to dropdown in Shipping and Payment
     */
    public void selectAlternateVendorAddInShipToAddress() {
        WebElement shipToInShipping = wait.waitForElementEnabled(shipToDropdownInPurchasing);
        dropdown.selectDropdownByDisplayName(shipToInShipping, "Alternate Vendor Address");
        waitForPageLoad();
    }
    /**
     * Selects Alternate Vendor Code from dropdown in Shipping and Payment
     * @param code
     */
        public void selectVendorCode(String code){
            WebElement shipToCode=wait.waitForElementEnabled(shipToCodeForAlternateVendor);
            click.javascriptClick(shipToCode);
            shipToCode.clear();
            waitForPageLoad();
            text.enterText(shipToCode,code);
            shipToCode.sendKeys(Keys.TAB);
            waitForPageLoad();
        }

    /**
     * Clicks the header for the Shipping and Payment category in Purchase Documents
     * to open the section
     */
    public void openShippingAndPaymentCategory() {
        List<WebElement> headers = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement shippingPayment = element.selectElementByText(headers, "Shipping and Payment");
        scroll.scrollElementIntoView(shippingPayment);
        click.clickElementCarefully(shippingPayment);
        wait.waitForElementDisplayed(shipToDropdownInPurchasing);
    }

    /**
     * Clicks the header for the Invoice Details category in Purchase Documents
     * to open the section
     */
    public void openInvoiceDetailsCategory() {
        List<WebElement> headers = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement invoiceDetails = element.selectElementByText(headers, "Invoice Details");
        scroll.scrollElementIntoView(invoiceDetails);
        click.clickElementCarefully(invoiceDetails);
    }

    /**
     * Clicks the header for the given category to open the section
     * @param categoryName
     */
    public void openCategory(String categoryName) {
        List<WebElement> categoriesList = wait.waitForAllElementsPresent(categoryHeadersLocs);
        WebElement category = element.selectElementByText(categoriesList, categoryName);
        scroll.scrollElementIntoView(category);
        click.clickElementIgnoreExceptionAndRetry(category);
    }

    /**
     * Enters the Tax Area Code for the Invoice Details
     * @param taxAreaCode
     */
    public void enterTaxAreaCodeInvoiceDetails(String taxAreaCode){
        wait.waitForElementDisplayed(invoiceDetailsTaxAreaCode);
        click.clickElementCarefully(invoiceDetailsTaxAreaCode);
        text.setTextFieldCarefully(invoiceDetailsTaxAreaCode, taxAreaCode, false);
    }

    /**
     * Clicks the dropdown for the Customer to select customerNo in Shipping and Payment category
     */
    public void selectCustomerNoInShippingAndBilling(String customerNo) {
        WebElement customerField = wait.waitForElementDisplayed(customerNoDropDown);
        scroll.scrollElementIntoView(customerField);
        click.clickElementCarefully(customerField);
        text.enterText(customerField, customerNo);
        text.pressTab(customerField);
    }

    /**
     * Clicks the dropdown for the Ship-to Code in Shipping and Payment category for Customer Address
     */
    public void selectShipToCodeForCustomerAddress(String shipToCode) {
        WebElement shipToCodeField = wait.waitForElementDisplayed(shipToCodeDropdown);
        scroll.scrollElementIntoView(shipToCodeField);
        click.clickElementCarefully(shipToCodeField);
        text.enterText(shipToCodeField, shipToCode);
        text.pressTab(shipToCodeField);

    }

    /**
     * Generalized method, for loading fields
     *
     * @Clicks Show more in Shipping and Payment
     */
    public void clickShowMore() {
        if(element.isElementPresent(showMore)) {
            wait.waitForElementPresent(showMore);
            click.javascriptClick(showMore);
        }else if(element.isElementDisplayed(By.xpath("//button[@aria-label='Invoice Details, Show more']"))){
            wait.waitForElementPresent(By.xpath("//button[@aria-label='Invoice Details, Show more']"));
            click.clickElementCarefully(By.xpath("//button[@aria-label='Invoice Details, Show more']"));
        }
        jsWaiter.waitForLoadAll();

    }

    /**
     * Generalized method, for getting alert text
     */
    public String getAlertMessagesInAP() {
        String alertText;
        WebElement alertEle = wait.waitForElementPresent(alertMsg);
        alertText = text.getElementText(alertEle);
        return alertText;
    }

    /**
     * Method for clicking OK in dialog box for Purchase Order
     */
    public void dialogBoxClickOkInAP() {
        waitForPageLoad();
        WebElement dialog = wait.waitForElementDisplayed(dialogBoxLoc);
        WebElement buttonCon = wait.waitForElementDisplayed(dialogButtonCon, dialog);
        List<WebElement> buttonList = wait.waitForAllElementsDisplayed(buttonLoc, buttonCon);
        WebElement button = element.selectElementByText(buttonList, "OK");
        click.clickElementIgnoreExceptionAndRetry(button);
        waitForPageLoad();
    }

    /**
     * Method for clicking OK in dialog box after changing location code
     */
    public void dialogBogClickOkOnLocationChange() {
        waitForPageLoad();
        By okButtonLoc = By.xpath("(//main[@class=\"ms-nav-content\" and not(@tabindex)]//*[contains(@class, \"ms-nav-actionbar-container\")]//button)/span[text()='OK']");
        WebElement button = wait.waitForElementEnabled(okButtonLoc);
        action.moveToElement(button).click().perform();
        waitForPageLoad();
    }

   /**
     * Gets a document from the list pages of doc types
     * @param documentNo
     */
    public void searchForPurchaseDocuments(String documentNo){
        waitForPageLoad();
        WebElement search=wait.waitForElementEnabled(searchIcon);
        click.clickElementCarefully(search);
        WebElement searchField=wait.waitForElementEnabled(searchInputField);
        text.enterText(searchField,documentNo);
        text.pressEnter(searchField);
        WebElement doc=wait.waitForElementDisplayed(firstElementClick);
        click.clickElementCarefully(doc);
        waitForPageLoad();

    }

    /**
     * After posting or converting to an invoice, click "Yes" to
     * open the invoice
     *
     * @return the purchase invoice page
     */
    public BusinessPurchaseInvoicePage goToPostedPurchaseInvoice() {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        return initializePageObject(BusinessPurchaseInvoicePage.class);
    }

    /**
     * filters the document number
     * @param postedInvoiceNo
     */
    public void filterDocuments(String postedInvoiceNo) {
        WebElement documentOpenMenu=wait.waitForElementDisplayed(documentNoHeading);
        click.clickElementCarefully(documentOpenMenu);
        waitForPageLoad();
        WebElement documentMenuArrow=wait.waitForElementDisplayed(documentHeadingArrow);
        click.javascriptClick(documentMenuArrow);
        click.clickElementCarefully(documentFilter);
        WebElement docSearch =wait.waitForElementDisplayed(documentSearch);
        text.enterText(docSearch,postedInvoiceNo);
        click.clickElementCarefully(okay);
        waitForPageLoad();
    }

    /**
     * Enters the vendor creditMemo No
     * For use when manually inputting information on vendor
     *
     * @param creditMemo
     */
    public void enterCreditMemoNo(String creditMemo)
    {
        WebElement creditMemoField=wait.waitForElementPresent(vendorCreditMemoLoc);
        text.setTextFieldCarefully(creditMemoField, creditMemo, false);
        text.pressEnter(vendorCreditMemoLoc);
    }
    /**
     * Opens the posted Credit Memo
     *
     * @return the posted purchase credit memo page
     */
    public BusinessPurchaseCreditMemoPage goToPostedPurchaseCreditMemo() {
        WebElement dialogPopup = wait.waitForElementDisplayed(dialogBoxLoc);
        List<WebElement> buttonsList = wait.waitForAllElementsPresent(buttonLoc, dialogPopup);
        WebElement yesButton = element.selectElementByText(buttonsList, "Yes");
        click.clickElement(yesButton);
        return initializePageObject(BusinessPurchaseCreditMemoPage.class);
    }
    /**
     * Locates the Vertex Tax field in statistics page
     *
     * @return the tax amount in Statistics
     */
        public String getAPStatTaxAmount(){
            WebElement vertexStatField=wait.waitForElementPresent(vertexStatTaxAmountLoc);
            String actualStatTaxAmount = attribute.getElementAttribute(vertexStatField, "textContent");
            return actualStatTaxAmount;
        }

    /**
     * Enters the Customer no in service management
     * For use when manually inputting information on customer
     *
     * @param customerNo
     */
    public void enterCustomerNo(String customerNo) {
        WebElement customerNoLoc = wait.waitForElementDisplayed(customerNoField);
        click.clickElementCarefully(customerNoLoc);
        text.enterText(customerNoLoc, customerNo);
        waitForPageLoad();
        String firstRow=String.format("//tr[contains(@class, 'thm')]//td[@controlname='2']/span[text()='%s']",customerNo);
        wait.waitForElementDisplayed(By.xpath(firstRow));
        click.clickElementCarefully(By.xpath(firstRow));
       }

    /**
     * Fills in the No. for the Quote or Order by creating a random set of numbers
     */
    public void fillInNumberForService(){

        Random random = new Random();
        int randomNumbers = random.nextInt(10000);

        String stringNum = String.valueOf(randomNumbers);

        wait.waitForElementDisplayed(numberInputButton);
        click.clickElementCarefully(numberInputButton);
        text.setTextFieldCarefully(numberInputButton, stringNum, false);
    }

    /**
     * Selects the dropdown option for the Service Lines Filter
     * @param selectOption
     * @param optionFiltered
     */
    public void selectAndFilterLinesFilter(String optionFiltered, String selectOption){
        WebElement serviceLinesFilter = wait.waitForElementDisplayed(By.xpath("//select[@title='"+optionFiltered+"']"));
        click.clickElementCarefully(serviceLinesFilter);

        WebElement selectedOption = wait.waitForElementDisplayed(By.xpath("//option[@title='"+selectOption+"']"));
        click.clickElementCarefully(selectedOption);
    }

    /**
     * Selects the line with the intended value
     * @param selectedLine
     */
    public void selectLineWithServiceItemNumber(String selectedLine){
        WebElement serviceLine =  wait.waitForElementDisplayed(By.xpath("(//td//span[text()='"+selectedLine+"'])"));
        click.clickElementCarefully(serviceLine);

    }

    /**
     * Clicks and enters the actual sales tax amount
     * @param actualSalesTaxAmount
     */
    public void clickAndEnterActualSalesTaxAmount(String actualSalesTaxAmount){
        WebElement clickActualSalesTax = wait.waitForElementDisplayed(actualSalesTax);
        text.setTextFieldCarefully(clickActualSalesTax, actualSalesTaxAmount, false);
    }

    /**
     * Click Recalculate Tax Button
     */
    public void clickRecalculateTax(){
        wait.waitForElementDisplayed(recalculateTaxAmount);
        click.javascriptClick(recalculateTaxAmount);
    }

    /**
     * Gets actual sales tax amount
     */
    public String getActualSalesTaxAmount(){
        wait.waitForElementDisplayed(actualSalesTax);
        String getActualSalesTaxAmount = attribute.getElementAttribute(actualSalesTax, "value");

        return getActualSalesTaxAmount;
    }

    /**
     * Gets actual sales tax amount without input xpath
     */
    public String getActualSalesTaxAmountWithoutInput(){
        wait.waitForElementDisplayed(actualSalesTaxWithNoInput);
        String getActualSalesTaxAmount = attribute.getElementAttribute(actualSalesTaxWithNoInput, "title");

        return getActualSalesTaxAmount;
    }

    public String verifyActualTaxIsReadOnly(){
        wait.waitForElementDisplayed(actualSalesTaxWithNoInput);
        String actualSalesTaxReadOnlyVal = attribute.getElementAttribute(actualSalesTaxWithNoInput, "aria-readonly");

        return actualSalesTaxReadOnlyVal;
    }

    /**
     * Gets accrual sales tax amount
     */
    public String getAccrualSalesTaxAmount(){
        wait.waitForElementDisplayed(accrualSalesTax);
        String getAccrualSalesTaxAmount = attribute.getElementAttribute(accrualSalesTax, "title");
        Double getAccrualValue = Double.parseDouble(getAccrualSalesTaxAmount);
        if(getAccrualValue > 0){
            getAccrualSalesTaxAmount = attribute.getElementAttribute(accrualSalesTax, "title");
        }else{
            jsWaiter.sleep(5000);
            getAccrualSalesTaxAmount = attribute.getElementAttribute(accrualSalesTax, "title");
        }

        return getAccrualSalesTaxAmount;
    }

    /**
     * Gets calculated sales tax amount
     */
    public String getCalculatedSalesTaxAmount(){
        wait.waitForElementDisplayed(calculatedSalesTax);
        String getCalculatedSalesTaxAmount = attribute.getElementAttribute(calculatedSalesTax, "title");

        return getCalculatedSalesTaxAmount;
    }

    /**
     * Clicks the refresh button
     */
    public void clickRefreshButton(){
        wait.waitForElementDisplayed(refreshLink);
        click.clickElementCarefully(refreshLink);
    }

    /**
     * Gets Qty to ship value
     */
    public String getQtyToShip(){
        jsWaiter.sleep(5000);
        wait.waitForElementPresent(qtyToShip);
        String getQtyToShip = attribute.getElementAttribute(qtyToShip, "value");

        return getQtyToShip;
    }

    /**
     * Gets Qty to invoice value
     */
    public String getQtyToInvoice(){
        wait.waitForElementPresent(qtyToInvoice);
        String getQtyToInvoice = attribute.getElementAttribute(qtyToInvoice, "value");

        return getQtyToInvoice;
    }
}
