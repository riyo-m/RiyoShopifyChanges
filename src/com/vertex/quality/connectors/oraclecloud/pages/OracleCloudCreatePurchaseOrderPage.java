package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.PageScrollDestination;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateInvoicePageFieldData;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudPoPagesFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OracleCloudCreatePurchaseOrderPage extends OracleCloudBasePage {

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    // Create Order Buttons
    By createButtonLoc = By.cssSelector("button[id$='commandButton1']");
    By saveButtonLoc = By.xpath("//span[text()='Save']");
    By submitButtonLoc = By.xpath("//span[text()='Sub']");
    By saveConfirmationLoc = By.xpath("//span[text()='Last Saved']");
    By submitConfirmationLoc = By.xpath("//div[text()='Confirmation']");
    By headerLoc = By.cssSelector("td[id*='SPph::_afrTtxt']");

    // Line-related Locs
    By actionsMenuLoc = By.cssSelector("div[id$='_ATp:ATm']");
    String actionsMenuItemLoc = "//td[text()='%s']"; // Use String formatter to specify which item to select.
    By lineTableLoc = By.cssSelector("table[summary='Lines']");
    By lineLoc = By.xpath("//table[@summary='Lines']/tbody/tr");
    String totalTaxString = "a[id*='%d:cl2']";
    String totalAmountString = "span[id$='%d:Total']"; // Use String formatter to insert line number.

    By scrollbarLoc = By.cssSelector("div[id$='Lines::scroller']");

    // Category Name Popup
    By categorySearchLoc = By.cssSelector("a[id*='Category::lovIconId']");
    By popupCategoryNameLoc = By.cssSelector("input[id*='Category::_afrLovInternalQueryId:value00']");
    By popupSearchLoc = By.cssSelector("button[id*='Category::_afrLovInternalQueryId::search']");
    By popupSearchResultLoc = By.cssSelector("div[id*='Category_afrLovInternalTableId::db']");
    By popupOkButtonLoc = By.cssSelector("button[id*='Category::lovDialogId::ok']");

    // PO Charge Account Popup
    By chargeAccPopupLoc = By.cssSelector("div[id$='LinePoChargeKffSPOP::popup-container']");
    By chargeAccPreviewPopupLoc = By.cssSelector("div[id$='LinePoChargeKffKff_hover::popup-container']");
    By interCompanyLoc = By.cssSelector("div[id$='value50::content']");
    By chargeAccOkButtonLoc = By.cssSelector("div[id$='LinePoChargeKffSPOP::popup-container']");

    // Submission confirmation popup text
    By submitConTextLoc = By.xpath("//div[text()[contains(.,'was submitted for approval.')]]");

    public OracleCloudCreatePurchaseOrderPage( WebDriver driver ) { super(driver); }

    /**
     * Inputs necessary data for all fields
     * pertaining to creating a PO.
     *
     * @param style
     * @param procurementBu
     * @param requisitioningBu
     * @param supplier
     * @param defaultShipToLocation
     */
    public void inputOrderDetails(String style, String procurementBu, String requisitioningBu, String supplier,
                                  String defaultShipToLocation)
    {
        if (style != null)
            inputAndTab(OracleCloudPoPagesFieldData.STYLE, style);
        if (procurementBu != null)
            inputAndTab(OracleCloudPoPagesFieldData.PROCUREMENT_BU, procurementBu);
        if (requisitioningBu != null)
            inputAndTab(OracleCloudPoPagesFieldData.REQUISITIONING_BU, requisitioningBu);
        if (supplier != null)
            inputAndTab(OracleCloudPoPagesFieldData.SUPPLIER, supplier);
        if (defaultShipToLocation != null)
            inputAndTab(OracleCloudPoPagesFieldData.DEFAULT_SHIP_TO, defaultShipToLocation);

        jsWaiter.sleep(8000);
        clickCreateOrder();
    }

    /**
     * Creates a line item for a purchase order.
     */
    public void addLine() {
        String action = "Add Row";
        clickActionsMenu(action);
    }

    /**
     * Update information associated with a line item.
     */
    public void inputLineData(int lineIndex, String type, String description, String categoryName, String quantity,
                              String unit, String price, String total, String chargeAcc, String transBusCat,
                              String prodType, String intendedUse) {
        try {
            wait.waitForElementDisplayed(OracleCloudPoPagesFieldData.LINE.getLocator());
        } catch (Exception e) {
            String tableId = "_FOpt1:_FOr1:0:_FONSr2:0:MAt2:0:AP1:AT1:_ATp:Lines::scroller";
            scroll.scrollTableLeft(scrollbarLoc, tableId, "4200");
        }
        if (type != null) {
            writeToGeneralField(OracleCloudPoPagesFieldData.TYPE, type);
        }
        if (description != null) {
            inputAndTab(OracleCloudPoPagesFieldData.DESCRIPTION, description);
        }
        jsWaiter.sleep(10000);
        if (categoryName != null){
            click.clickElementCarefully(categorySearchLoc);
            wait.waitForElementDisplayed(popupCategoryNameLoc); // Salomone - create order matched with PO test
            driver.findElement(popupCategoryNameLoc).sendKeys(categoryName);
            click.clickElementCarefully(popupSearchLoc);
            try{
                Thread.sleep(8000);
            }
            catch ( InterruptedException e)
            {
                e.printStackTrace();
            }
            wait.waitForElementDisplayed(popupSearchResultLoc);
            click.clickElementCarefully(popupSearchResultLoc);
            click.clickElementCarefully(popupOkButtonLoc);
            wait.waitForElementNotEnabled(popupOkButtonLoc);

        }
        if (quantity != null)
            inputAndTab(OracleCloudPoPagesFieldData.QUANTITY, quantity);
        if (unit != null)
            inputAndEnter(OracleCloudPoPagesFieldData.UOM, unit);
        if (price != null) {
            try {
                WebElement priceEle = inputTabAndWait(OracleCloudPoPagesFieldData.AMOUNT_PRICE, price, 150);
                text.pressTab(OracleCloudPoPagesFieldData.AMOUNT_PRICE.getLocator());
                text.pressTab(By.cssSelector(String.format(totalTaxString, lineIndex)));
            } catch (Exception e){
                WebElement priceEle = inputTabAndWait(OracleCloudPoPagesFieldData.PRICE, price, 150);
                text.pressTab(OracleCloudPoPagesFieldData.PRICE.getLocator());
                text.pressTab(By.cssSelector(String.format(totalTaxString, lineIndex)));
            }

            By totalAmountLoc = By.cssSelector(String.format(totalAmountString, lineIndex));
            WebElement totalEle = wait.waitForElementDisplayed(totalAmountLoc);
            jsWaiter.sleep(150);

            try {
                wait.waitForTextInElement(totalAmountLoc, total);
            } catch (Exception e) {
                inputAndTab(OracleCloudPoPagesFieldData.PRICE, price);
            }

            VertexLogger.log("Total price confirmed.", VertexLogLevel.INFO);
        }
        if (chargeAcc != null) {
            WebElement accountEle = element.getWebElement(OracleCloudPoPagesFieldData.PO_CHARGE_ACCOUNT.getLocator());
            scroll.scrollElementIntoView(accountEle, PageScrollDestination.TOP);
            inputAndTab(OracleCloudPoPagesFieldData.PO_CHARGE_ACCOUNT, chargeAcc);
            if(lineIndex == 0)
                wait.waitForElementDisplayed(chargeAccPreviewPopupLoc);
        }
        jsWaiter.sleep(8000);
        if(transBusCat != null)
            inputAndTab(OracleCloudPoPagesFieldData.TRANSACTION_BUS_CATEGORY, transBusCat);
        if (prodType != null)
            selectFromDropdown(OracleCloudPoPagesFieldData.PRODUCT_TYPE, prodType);
        jsWaiter.sleep(10000);
    }

    /**
     * Clicks the "Save" button on the edit purchase orders
     * page. Does not click button's dropdown.
     */
    public void clickSaveOrder() {
        String text = "Save";

        scroll.scrollElementIntoView(saveButtonLoc);

        WebElement select = element.selectElementByText(saveButtonLoc, text);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        jsWaiter.sleep(40000);
        wait.waitForElementDisplayed(saveConfirmationLoc);
    }

    /**
     * Clicks the "Submit" button on the manage purchase orders
     * page. Does not click button's dropdown.
     */
    public void clickSubmitOrder() {
        String text = "Submit";

        scroll.scrollElementIntoView(submitButtonLoc);

        WebElement select = element.selectElementByText(submitButtonLoc, text);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        wait.waitForElementDisplayed(submitConfirmationLoc);
    }

    /**
     * Gets the loc of the specified line item added to the table.
     *
     * @param lineNum the line number displayed on the page
     *
     * @return WebElement for the line
     */
    public WebElement getLineElement( int lineNum )
    {
        String lineNumStr = Integer.toString(lineNum);
        String attribute = "_afrrk";

        WebElement lines = wait.waitForElementDisplayed(lineTableLoc);
        List<WebElement> linesList = wait.waitForAllElementsDisplayed(lineLoc, lines);

        WebElement line = element.selectElementByAttribute(linesList, lineNumStr, attribute);

        return line;
    }

    /**
     * Get the Purchase Order Number generated by Oracle
     *
     * @return the Purchase Order number
     */
    public String getPurchaseOrderNumber( )
    {
        scroll.scrollElementIntoView(headerLoc);
        WebElement transNum = wait.waitForElementDisplayed(headerLoc);
        String number = transNum.getText();
        number = number.replaceAll("[^\\d.]", "");

        return number;
    }

    /**
     * Locates the submission confirmation popup box's
     * text. Verifies an order was submitted for approval
     * successfully by checking for key words.
     *
     * @return textMatches - whether the text contained within
     * the confirmation popup box matches the expected text.
     */
    public boolean verifySubConfirmation() {
        boolean textMatches = false;

        try {
            wait.waitForElementDisplayed(submitConTextLoc);
            textMatches = true;
        } catch (Exception e) {
            VertexLogger.log("Could not find confirmation that PO was submitted for approval.",
                    VertexLogLevel.ERROR);
        }

        return textMatches;
    }

    /**
     * Select the actions menu to perform actions on
     * purchase order line items.
     */
    private void clickActionsMenu(String menuAction) {
        scroll.scrollElementIntoView(actionsMenuLoc);
        WebElement actions = wait.waitForElementDisplayed(actionsMenuLoc);

        click.clickElementCarefully(actions);

        String xpath = String.format(actionsMenuItemLoc, menuAction);
        By actionItem = By.xpath(xpath);

        WebElement actionElement = wait.waitForElementEnabled(actionItem);
        wait.waitForElementEnabled(actionElement);
        click.clickElementCarefully(actionElement);
        wait.waitForElementDisplayed(lineTableLoc);
    }

    /**
     * Fills out a field with the given value, and
     * simulates pressing the tab key.
     *
     * @param data enum containing locator for input field.
     * @param input data to put into given field.
     */
    private WebElement inputAndTab(OracleCloudPoPagesFieldData data, String input) {
        WebElement field = writeToGeneralField(data, input);
        text.pressTab(field);

        return field;
    }

    /**
     * Fills out a field with the given value, waits
     * the provide number of secdons, and simulates
     * pressing the tab key.
     *
     * @param data enum containing locator for input field.
     * @param input data to put into given field.
     * @param time time in seconds to wait after input.
     * @return
     */
    private WebElement inputTabAndWait(OracleCloudPoPagesFieldData data, String input, int time) {
        WebElement field = writeToGeneralField(data, input);
        text.pressTab(field);
        jsWaiter.sleep(time);
        wait.waitForElementDisplayed(data.getLocator());

        return field;
    }

    /**
     * Fills out a field with the given value, and
     * simulates pressing the enter key.
     */
    private void inputAndEnter(OracleCloudPoPagesFieldData data, String input) {
        WebElement field = writeToGeneralField(data, input);
        text.pressEnter(field);
    }

    /**
     *	Writes to a field located on the create (purchase) orders popup page.
     *
     * @param data enum containing field information
     * @param inputToField input to write
     * @return WebElement of field interacted with
     */
    private WebElement writeToGeneralField( OracleCloudPoPagesFieldData data, String inputToField )
    {
        By curLoc = data.getLocator();
        WebElement curEle = element.getWebElement(curLoc);
        try {
            scroll.scrollElementIntoView(curLoc);
        } catch (Exception e) {
            scroll.scrollElementIntoView(curEle, PageScrollDestination.TOP);
        }
        wait.waitForElementNotEnabled(blockingPlane);
        WebElement inputField = wait.waitForElementEnabled(curLoc);
        jsWaiter.sleep(8000);
        try {
            text.enterText(inputField, inputToField);
        } catch (StaleElementReferenceException sere) {
            inputField = wait.waitForElementEnabled(curLoc);
            text.enterText(inputField, inputToField);
        }

        return inputField;
    }

    /**
     * Select an option from a dropdown element.
     * @param data enum containing field info
     * @param option option to select from dropdown menu.
     */
    private void selectFromDropdown(OracleCloudPoPagesFieldData data, String option) {
        By dropdownLoc = data.getLocator();
        WebElement dropdownWebEle = wait.waitForElementDisplayed(dropdownLoc);
        dropdown.selectDropdownByDisplayName(dropdownWebEle, option);
    }

    /**
     * Click the 'Create' button attached to the Create
     * (purchase) Order popup page.
     */
    private void clickCreateOrder() {
        String buttonText = "Create";
        String expectedHeader = "Edit Document";
        scroll.scrollElementIntoView(createButtonLoc);

        WebElement select = element.selectElementByText(createButtonLoc, buttonText);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        waitForLoadedPage(expectedHeader);
    }

    /**
     * Click the Ok button in the PO Charge Account popup
     * window.
     */
    private void clickPoChargeAccOk() {
        String buttonText = "O";
        scroll.scrollElementIntoView(chargeAccOkButtonLoc);

        WebElement select = element.selectElementByText(chargeAccOkButtonLoc, buttonText);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        wait.waitForElementNotDisplayed(chargeAccPopupLoc);
    }

    /**
     * Verifies whether information is entered in an input field.
     * @param data locator for input field.
     */
    protected boolean checkForData(OracleCloudPoPagesFieldData data) {
        boolean populated = false;
        WebElement inputField = wait.waitForElementDisplayed(data.getLocator());
        if(!inputField.getText().equals("")) {
            populated = true;
        }
        return populated;
    }
}
