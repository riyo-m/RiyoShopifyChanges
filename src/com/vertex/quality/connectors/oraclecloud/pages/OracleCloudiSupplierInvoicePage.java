package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateInvoicePageFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Navigate through the iSupplier Invoice page
 *
 * @author Tanmay Mody
 */

public class OracleCloudiSupplierInvoicePage extends OracleCloudBasePage{

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    By homeIconLoc = By.cssSelector("[id*='IShome::icon']");
    By supplierPortalLoc = By.cssSelector("a[id$='portal_0']");
    By createInvoiceWoPoLoc = By.cssSelector("a[id$='cl18']");

    By fresnoSupplierLoc = By.xpath("//div[contains(@id, 'afrLovInternalTableId::db')]/table/tbody/tr[1]");
    By okButtonSupplierSiteSelect = By.cssSelector("button[id*='lovDialogId::ok']");

    By addItemLoc = By.cssSelector("img[id*='itemAppsTable:_ATp:create::icon']");
    By shipToLocationLoc = By.cssSelector("input[id*='shipToLocationId']");

    By invoiceActionsButtonLoc = By.cssSelector("div[id$='mb1'][role='menubar']");
    By invoiceActionsMenuLoc = By.cssSelector("div[id$='popup-container'][data-afr-popupid$='me1']");
    By invoiceActionsOptionsLoc = By.cssSelector("table[id$='ScrollContent'] tbody tr[role='menuitem']");

    By taxAmountLoc = By.xpath("//div[contains(@id, 'table1::db')]/table/tbody/tr/td[2]/div//table/tbody/tr/td[9]");
    By editTaxLoc = By.cssSelector("input[id*='inputText4::content']");

    By summaryLoc = By.cssSelector("span[id*='inputText4::content']");
    By summaryEnabledLoc = By.cssSelector("input[id*='inputText4::content']");
    By submitButtonLoc = By.cssSelector("button[id*='cb1']");
    By okButtonLoc = By.cssSelector("button[id$='msgDlg::cancel']");
    By doneButtonLoc = By.cssSelector("div[id*='ctb3']");
    By calculateTaxButtonLoc = By.xpath("//span[text()='Calculate Tax']");

    final String calculateTaxSelect = "Calculate Tax Ctrl+Alt+X";

    public OracleCloudiSupplierInvoicePage(WebDriver driver ) { super(driver); }

    /**
     * Checks the written input on a header field
     *
     * @param data          enum containing field information
     * @param expectedInput the text expected to be in the field
     *
     * @return whether the input found matches the input expected
     */
    public boolean checkInput( OracleCloudCreateInvoicePageFieldData data, String expectedInput )
    {
        boolean correct = false;
        wait.waitForElementNotEnabled(blockingPlane);

        By loc = data.getLocator();

        WebElement field = wait.waitForElementEnabled(loc);

        String actualInput = field.getAttribute("value");

        if ( expectedInput.equals(actualInput) )
        {
            correct = true;
        }

        return correct;
    }

    /**
     * Click on the add Item button to add an editable item
     */
    public void clickAddItem()
    {
        wait.waitForElementNotEnabled(blockingPlane);

        WebElement button = wait.waitForElementEnabled(addItemLoc);

        click.clickElement(button);
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

        try {
            wait.waitForElementEnabled(summaryLoc, 15);
        } catch(TimeoutException toe) {
            wait.waitForElementDisplayed(summaryEnabledLoc);
        }
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
     * Clicks "Create Invoice Without PO" Link
     */
    public void clickCreateInvoiceWithoutPo()
    {
        WebElement link = wait.waitForElementEnabled(createInvoiceWoPoLoc);
        click.clickElementCarefully(link);
        waitForLoadedPage("Create Invoice Without PO");
    }

    /**
     * Clicks Home icon after signing in
     */
    public void clickHome()
    {
        WebElement homeButton = wait.waitForElementEnabled(homeIconLoc);
        click.clickElementCarefully(homeButton);
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
     * Clicks OK in the confirmation dialog box
     * when payment details are filled
     */
    public void clickOk()
    {
        WebElement okButton = wait.waitForElementEnabled(okButtonLoc);
        click.clickElementCarefully(okButton);
    }

    /**
     * Clicks Supplier Portal icon
     */
    public void clickSupplierPortal()
    {
        WebElement supplierPortal = wait.waitForElementEnabled(supplierPortalLoc);
        click.clickElementCarefully(supplierPortal);
        waitForLoadedPage("Supplier Portal");
    }

    /**
     * Edit the Tax amount after calculation
     *
     * @param input new Tax Amount
     * @return the field
     */
    public WebElement editTax( String input )
    {
        WebElement field = wait.waitForElementDisplayed(taxAmountLoc);
        click.clickElementCarefully(field);
        WebElement amount = wait.waitForElementDisplayed(editTaxLoc);
        text.clearText(amount);
        text.enterText(amount, input);

        return field;
    }

    /**
     * Generates an invoice number using the test type String and
     * a randomly generated set of numbers (invoice numbers must be unique)
     * Concatenates the test type and random number into one String, starting with "AUTOTST" to
     * signify it is an automated test
     *
     * @param testName short String describing what the test is doing (i.e. "iSupplier")
     * @return the invoice number
     */
    public String generateInvoiceNumber( String testName )
    {
        Random rnd = new Random();
        int number = rnd.nextInt(100000);

        String invoiceNumber = String.format("AUTOTST_%1$s_%2$s", testName, number);

        return invoiceNumber;
    }

    /**
     * Writes to a header field and checks to make sure the input was correctly written
     * Due to Oracle occasionally deselecting input fields
     *
     * @param data  enum containing field information
     * @param input input to write
     *
     * @return WebElement of field interacted with
     */
    public WebElement inputAndCheck( OracleCloudCreateInvoicePageFieldData data, String input )
    {
        WebElement field = writeToHeaderField(data, input);
        text.pressEnter(field);

        while ( !checkInput(data, input) )
        {
            field = writeToHeaderField(data, input);
            text.pressEnter(field);
        }

        return field;
    }

    /**
     * AP Tests
     * Input all required header information for the accounts payable invoice
     *
     * @param page
     * @param shipToLocation
     * @param amount
     */
    public void itemRequiredInfo( OracleCloudiSupplierInvoicePage page, String shipToLocation, String amount)
    {
        wait.waitForElementEnabled(shipToLocationLoc);
        page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.ITEM_SHIP_TO_LOCATION, shipToLocation);

        page.inputAndCheck(OracleCloudCreateInvoicePageFieldData.ITEM_AMOUNT, amount);
    }

    /**
     * Selects the correct line item from a pop-up box
     */
    public void selectFresnoSite()
    {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        jsWaiter.sleep(20000);
        WebElement link = wait.waitForElementEnabled(fresnoSupplierLoc); // Salomone - iSupplier Equal calc
        click.clickElementCarefully(link); // Salomone - isupplier zero calc and Equal calc
        WebElement okLink = wait.waitForElementEnabled(okButtonSupplierSiteSelect);
        click.clickElementCarefully(okLink);
        wait.waitForElementNotDisplayed(okButtonSupplierSiteSelect);
    }

    /**
     * Select an option from a dropdown menu
     * Object on page must be tagged as "SELECT"
     *
     * @param data         enum containing information for dropdown to select
     * @param selectOption text value of the option to be selected
     */
    public void selectFromDropdown( OracleCloudCreateInvoicePageFieldData data, String selectOption )
    {
        By dropdownLoc = data.getLocator();
        WebElement dropdownWebEle = wait.waitForElementDisplayed(dropdownLoc);
        dropdown.selectDropdownByDisplayName(dropdownWebEle, selectOption);
    }

    /**
     * Submits the invoice to be approved
     */
    public void submitInvoice()
    {
        WebElement submitButton = wait.waitForElementEnabled(submitButtonLoc);
        click.clickElementCarefully(submitButton);

        WebElement doneButton = wait.waitForElementEnabled(doneButtonLoc);
        click.clickElementCarefully(doneButton);
    }

    /**
     * Writes to the invoice header fields in the top half
     * of the create invoice page
     *
     * @param data         enum containing field information
     * @param inputToField input to write
     *
     * @return WebElement of field interacted with
     */
    public WebElement writeToHeaderField( OracleCloudCreateInvoicePageFieldData data, String inputToField )
    {
        wait.waitForElementNotEnabled(blockingPlane);

        By loc = data.getLocator();
        WebElement inputField = wait.waitForElementEnabled(loc);

        inputField.clear();
        inputField.sendKeys(inputToField);

        inputField = wait.waitForElementEnabled(loc);

        return inputField;
    }
}
