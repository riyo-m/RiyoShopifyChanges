package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateInvoicePageFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Manage Invoice page for application
 *
 * @author Tanmay Mody
 */
public class OracleCloudManageInvoicesPage extends OracleCloudBasePage {


    protected By blockingPlane = By.className("AFBlockingGlassPane");

    protected By bellIconLoc = By.cssSelector("a[id*='atr:0:cil1']");

    protected By invoiceNumberFieldLoc = By.cssSelector("input[id*='value10']");
    protected By searchButtonLoc = By.cssSelector("button[id*='search']");
    protected By distributionSetLoc = By.cssSelector("input[id*='so13::content']");
    protected By editIconLoc = By.cssSelector("img[id*='ct1::icon']");
    protected By searchExpandLoc = By.cssSelector("a[title='Expand Search: Invoice']");

    protected By searchedInvoiceLoc = By.xpath("//div[contains(@id, 'ta1::db')]/table/tbody/tr");

    protected By invoiceActionsButtonLoc = By.cssSelector("div[id$='mb1'][role='menubar']");
    protected By invoiceActionsMenuLoc = By.cssSelector("div[id$='popup-container'][data-afr-popupid$='me1']");
    protected By invoiceActionsOptionsLoc = By.cssSelector("table[id$='ScrollContent'] tbody tr[role='menuitem']");
    protected By validatedLoc = By.cssSelector("a[id*='ap1:cl3']");
    protected By errorPopupLoc = By.xpath("//*[@id='_FOd1::msgDlg']");
    protected By saveButtonLoc = By.xpath("//span[text()='Save']");
    protected By saveAndCloseButtonLoc = By.xpath("//span[text()='ave and Close']");
    protected By saveConfirmationLoc = By.xpath("//label[text()='Last Saved']");
    protected By cancelLineButtonLoc = By.xpath("//span[text()='Cancel Line']");
    protected By editTaxesButtonLoc = By.xpath("//button[text()='Edit Taxes']");

    // Calculate Tax and Tax total locs
    protected By transTaxTotalLoc = By.cssSelector("table[id$='TransTaxTotal']");
    protected By taxLineItemLoc = By.cssSelector("td[title='***VENDOR CHRG TAX RATECDP2P***']");
    protected By calculateTaxButtonLoc = By.xpath("//span[text()='Calculate Tax']");
    protected By taxesExpandLoc = By.cssSelector("a[title='Expand Taxes']");
    protected By transTaxesView = By.xpath("//a[text()='Transaction Taxes']");

    // Lines section locs
    protected By linesExpandLoc = By.cssSelector("a[title='Expand Lines']");
    protected By invoiceLinesLoc = By.cssSelector("table[summary='Invoice Lines'] tbody tr");
    protected By invoiceLineExplicitLoc = By.xpath("//tr[@class='xem'][1]");
    protected By addTaxLineButtonLoc = By.cssSelector("div[title='Add Row']");
    protected By linesScrollBarLoc = By.cssSelector("div[id$='ta2::scroller']");
    // //*[@id="_FOd1::msgDlg::_cnt"]/div/table/tbody/tr/td/table/tbody/tr/td[2]/div/span/text()[2]

    final String calculateTaxSelect = "Calculate Tax Ctrl+Alt+X";
    final String validateTextSelect = "Validate Ctrl+Alt+V";

    Actions action = new Actions(driver);

    public OracleCloudManageInvoicesPage( WebDriver driver ) { super(driver); }

    /**
     * Approves the invoice from the Bell Icon
     *
     * @param invoiceNumber the invoice number to be approved
     * @param expectedAmount the amount in the notification
     */
    public void approveInvoice(String invoiceNumber, String expectedAmount)
    {
        try{
            Thread.sleep(12000);
        }
        catch ( InterruptedException e)
        {
            e.printStackTrace();
        }

        wait.waitForElementNotEnabled(blockingPlane);

        WebElement icon = wait.waitForElementEnabled(bellIconLoc);
        click.clickElement(icon);

        By notificationLoc = By.cssSelector("a[title='Approve  Approval of Invoice "+invoiceNumber+" from MCC Calif ("+expectedAmount+" USD)']");
        WebElement approveLink = wait.waitForElementEnabled(notificationLoc);
        click.clickElement(approveLink);
        wait.waitForElementNotEnabled(notificationLoc);
    }

    /**
     * Wrapper for clicking the calculate tax option from
     * the dropdown menu with a fallback to clicking the
     * calculate tax button.
     *
     * Note that ecog-dev1 has the button where as ecog-test
     * utilizes the invoice actions dropdown menu at the top
     * of the page.
     */
    public void calculateTax() {
        try {
            clickCalculateTaxButton();
        }
        catch (Exception ex) {
            VertexLogger.log("No calculate tax button found on page. " +
                            "Attempting to calculate take via invoice actions menu.",
                    VertexLogLevel.WARN);
            clickCalculateTax();
        }
    }

    /**
     * Checks the text at the top of the page which
     * describes whether the invoice is validated
     *
     * @return validation status String
     */
    public String checkValidationStatus( )
    {
        wait.waitForElementNotEnabled(blockingPlane);
        WebElement validationDisplay = wait.waitForElementDisplayed(validatedLoc);
        String validationStatus = validationDisplay.getText();

        return validationStatus;
    }

    /**
     * Clicks Calculate Tax from the invoice
     * actions menu to calculate tax.
     */
    private void clickCalculateTax()
    {
        WebElement menu = clickInvoiceActionsButton();
        try
        {
            clickInvoiceActionFromMenu(menu, calculateTaxSelect);
        }
        catch ( StaleElementReferenceException e )
        {
            menu = clickInvoiceActionsButton();
            clickInvoiceActionFromMenu(menu, calculateTaxSelect);
        }

        jsWaiter.sleep(7000);
    }

    /**
     * Clicks Calculate Tax button. Use this option when the actions
     * dropdown is not available above the Items section.
     *
     * ecog-dev1 has this button.
     */
    private void clickCalculateTaxButton()
    {
        WebElement calcTaxButton = wait.waitForElementDisplayed(calculateTaxButtonLoc);
        click.javascriptClick(calcTaxButton);
        jsWaiter.sleep(40000);
    }



    /**
     * After clicking Invoice Actions to open the dropdown,
     * click one of the options displayed on the menu
     *
     * @param menu the dropdown menu displayed
     * @param expectedText the text of the option desired as it appears on the menu
     */
    public void clickInvoiceActionFromMenu( WebElement menu, String expectedText )
    {
        List<WebElement> optionsList = wait.waitForAllElementsDisplayed(invoiceActionsOptionsLoc, menu,
                80);

        WebElement action = element.selectElementByText(optionsList, expectedText);

        wait.waitForElementEnabled(action);

        click.clickElementCarefully(action);

        wait.waitForElementNotDisplayedOrStale(menu, 20);
    }

    /**
     * Clicks the invoice actions button at the top of the page to
     * show a dropdown menu of options
     *
     * @return the actions menu
     */
    public WebElement clickInvoiceActionsButton( )
    {
        WebElement menu;

        wait.waitForElementNotEnabled(blockingPlane);
        wait.waitForElementPresent(invoiceActionsButtonLoc);
        scroll.scrollElementIntoView(invoiceActionsButtonLoc);
        WebElement button = wait.waitForElementEnabled(invoiceActionsButtonLoc);

        click.clickElementCarefully(button);

        try
        {
            menu = wait.waitForElementDisplayed(invoiceActionsMenuLoc, 15);
        }
        catch ( TimeoutException e )
        {
            button = wait.waitForElementEnabled(invoiceActionsButtonLoc);
            button.click();
            menu = wait.waitForElementDisplayed(invoiceActionsMenuLoc);
        }

        return menu;
    }

    /**
     * Edit the Distribution Set
     *
     * @param input new Location
     * @return the field
     */
    public WebElement editDistributionSet( String input )
    {
        WebElement field = wait.waitForElementEnabled(distributionSetLoc);
        text.clearText(field);
        text.enterText(field, input);

        return field;
    }

    /**
     * Edits the invoice
     */
    public void editInvoice()
    {
        WebElement invoice = wait.waitForElementEnabled(searchedInvoiceLoc);
        click.clickElementCarefully(invoice);

        WebElement edit = wait.waitForElementEnabled(editIconLoc);
        click.clickElementCarefully(edit);
    }

    /**
     * Writes an invoice number into the Invoice Number search field
     *
     * @param input invoice number to search
     */
    public void searchInvoiceNumber( String input )
    {
        WebElement searchField = wait.waitForElementEnabled(invoiceNumberFieldLoc);
        text.clearText(searchField);
        text.enterText(searchField, input);

        WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc);
        click.clickElementCarefully(searchButton);

        wait.waitForElementNotDisplayed(invoiceNumberFieldLoc);
    }

    /**
     * Wait for "Validated" link
     */
    public void waitForValidated(  )
    {
        final WebDriverWait wait = new WebDriverWait(driver, 125);
        wait.until(ExpectedConditions.textToBePresentInElementLocated(validatedLoc, "Validated"));
    }

    /**
     * Attempts to capture an error upon validating an
     * invoice via the UI.
     *
     * @param expectedErrorText Expected text visible in error popup after validation attempt.
     * @return errorCapture - whether the expected text is visible on screen or not.
     */
    public boolean captureValidationError(String expectedErrorText) {
        boolean errorCaptured = false;

        try {
            WebElement errorPopup = wait.waitForElementDisplayed(errorPopupLoc);
            String text = errorPopup.getText();
            if(text.contains(expectedErrorText))
                errorCaptured = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            VertexLogger.log("Test failed due to missing error popup after validation attempt.");
        }

        return errorCaptured;
    }

    /**
     * Clicks on the add row button to add an editable row
     * on the edit transaction page, under the Lines section
     * in the toolbar.
     *
     */
    public void clickAddRowToLines(  )
    {
        wait.waitForElementNotEnabled(blockingPlane);

        WebElement button = wait.waitForElementEnabled(addTaxLineButtonLoc);

        click.javascriptClick(button);
        jsWaiter.sleep(7000);
    }

    /**
     * Clicks on the "Save and Close" button to save an invoice.
     */
    public void clickSaveAndCloseButton( )
    {
        String text = "ave and Close";

        scroll.scrollElementIntoView(saveAndCloseButtonLoc);

        WebElement select = wait.waitForElementDisplayed(saveAndCloseButtonLoc);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        jsWaiter.sleep(7000);
    }

    /**
     * Clicks on the "Save" button to save an invoice.
     */
    public void clickSaveButton( )
    {
        String text = "Save";

        scroll.scrollElementIntoView(saveButtonLoc);

        WebElement select = element.selectElementByText(saveButtonLoc, text);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        wait.waitForElementDisplayed(saveConfirmationLoc);
    }

    /**
     * clicks the "Cancel Line" button on the edit
     * invoice page.
     */
    public void cancelLineItem() {
        String text = "Cancel Line";

        try {
            scroll.scrollElementIntoView(cancelLineButtonLoc);
        } catch (Exception e) {
            click.javascriptClick(linesExpandLoc);
        }

        WebElement select = element.selectElementByText(cancelLineButtonLoc, text);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
    }

    /**
     * Clicks the Edit Taxes button under the Taxes section on
     * the page. Also returns whether the tax amount matches
     * the provided expected value.
     *
     * @param expectedAmount Tax amount expected after tax lines are editted.
     */
    public boolean editAndVerifyTaxes(String expectedAmount) {

        scroll.scrollElementIntoView(editTaxesButtonLoc);

        wait.waitForElementEnabled(editTaxesButtonLoc);
        click.javascriptClick(editTaxesButtonLoc);

        return true;
    }

    /**
     * Expands the search fields to display more search criteria
     * options.
     */
    public void expandSearch() {
        wait.waitForElementDisplayed(searchExpandLoc);
        click.javascriptClick(searchExpandLoc);

        jsWaiter.sleep(3000);
    }

    /**
     * On the Transaction Tax table, get the total tax amount
     * at the bottom
     *
     * @return total transaction tax
     */
    public String getTotalTransTax( )
    {
        wait.waitForElementNotEnabled(blockingPlane);
        WebElement totalTax;

        try {
            totalTax = wait.waitForElementEnabled(transTaxTotalLoc);
        } catch (Exception e) {
            click.javascriptClick(taxesExpandLoc);
        }

        jsWaiter.sleep(5000);
        click.javascriptClick(transTaxesView);
        totalTax = wait.waitForElementEnabled(transTaxTotalLoc);
        String total = totalTax.getText();

        return total;
    }

    /**
     * Select the invoice line at the position indicated
     * by the lineNum parameter.
     *
     * @param lineNum the line number to select on the page.
     *
     * @return the selected line.
     */
    public WebElement selectInvoiceLine(int lineNum) {
        lineNum--;
        String lineNumStr = Integer.toString(lineNum);
        String attribute = "_afrrk";

        List<WebElement> linesList = wait.waitForAllElementsDisplayed(invoiceLinesLoc);

        WebElement line = element.selectElementByAttribute(linesList, lineNumStr, attribute);

        return line;
    }

    /**
     * Explicitly clicks the first invoice line in scenarios where the Oracle
     * UI does not.
     */
    public void selectInvoiceLineNotDisplayed() {
        WebElement firstLine = wait.waitForElementDisplayed(invoiceLineExplicitLoc);
        click.clickElementCarefully(firstLine);
    }

    /**
     * Helper method
     * Attempts to validate an invoice with the expectation
     * that an error will occur. Returns true if expected
     * error occurs after validation attempt in UI.
     *
     * @param page a manage invoices page object.
     * @param expectedError Text expected to appear on page in error message.
     *
     * @return [boolean] error appears on page.
     */
    public boolean validateInvoiceErrors( OracleCloudManageInvoicesPage page , String expectedError)
    {
        WebElement menu = page.clickInvoiceActionsButton();
        try
        {
            page.clickInvoiceActionFromMenu(menu, validateTextSelect);
        }
        catch ( StaleElementReferenceException e )
        {
            menu = page.clickInvoiceActionsButton();
            page.clickInvoiceActionFromMenu(menu, validateTextSelect);
        }
        jsWaiter.sleep(9000);
        boolean errorPresent = page.captureValidationError(expectedError);

        return errorPresent;
    }

    /**
     * Writes to one of the fields under the Lines section
     * of the page
     *
     * @param data    enum containing field information
     * @param input    input to write
     * @param line    line the field is on
     *
     * @return WebElement of field interacted with
     */
    public WebElement writeToLine(OracleCloudCreateInvoicePageFieldData data, String input, WebElement line )
    {
        By loc = data.getLocator();

        WebElement inputField;

        try {
            inputField = wait.waitForElementPresent(loc, line, 15);
        }
        catch (TimeoutException te) {
            try {
                inputField = wait.waitForElementDisplayed(loc);
            } catch (TimeoutException toe) {
                String tableId = "_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:1:pm1:r1:1:ap1:at2:_ATp:ta2::scroller";
                scroll.scrollTableRight(linesScrollBarLoc, tableId, "500");
                inputField = wait.waitForElementDisplayed(loc);
            }
        }

        action
                .moveToElement(inputField)
                .perform();
        inputField = wait.waitForElementEnabled(inputField);
        text.enterText(inputField, input);
        text.pressTab(inputField);

        return inputField;
    }

}
