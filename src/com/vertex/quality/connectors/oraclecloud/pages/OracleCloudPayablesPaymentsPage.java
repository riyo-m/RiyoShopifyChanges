package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Payables invoices page for application
 *
 * @author Tanmay Mody
 */
public class OracleCloudPayablesPaymentsPage extends OracleCloudBasePage
{
    protected By paymentNumberFieldLoc = By.cssSelector("input[id*='value20']");
    protected By searchButtonLoc = By.cssSelector("button[id*='search']");
    protected By searchResultsTableLoc = By.cssSelector("table[summary='Search Results']");
    protected By searchResultsLinesLoc = By.cssSelector("table[summary='Search Results'] tbody tr td span a");

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    protected By actionsButtonLoc = By.cssSelector("div[id$='ATm'][role='presentation']");
    protected By actionsMenuLoc = By.cssSelector("div[id$='popup-container'][data-afr-popupid$='ATm']");
    protected By actionsOptionsLoc = By.cssSelector("table[id$='ScrollContent'] tbody tr[role='menuitem']");

    protected By voidPaymentPopupLoc = By.cssSelector("div[id*='popup-container'][data-afr-popupid*='voidPayment']");
    protected By invoiceActionLoc = By.cssSelector("select[id$='invoiceAction::content']");
    protected By voidPaymentSubmitButtonLoc = By.xpath("//button[contains(@id, 'dialog1::ok')]");

    protected By paymentStatusLoc = By.cssSelector("span[id='_FOpt1:_FOr1:0:_FONSr2:0:MAnt2:2:ap1:outputText4']");
    protected By confirmationMsgPopupLoc = By.cssSelector("div[id*='popup-container']");
    protected By confirmationMsgContentLoc = By.cssSelector("td[id='_FOd1::msgDlg::contentContainer']");

    protected By managePaymentsLoc = By.cssSelector("[id='_FOpt1:_FOr1:0:_FONSr2:0:_FOTRaT:0:RAtl5']");

    protected By payablesTasksTabLoc = By.cssSelector("div[title='Tasks']");
    protected By payablesMenuLoc = By.cssSelector("td[id*='tabb']");
    protected By linksTag = By.tagName("A");

    final String voidTextSelect = "Void";
    public OracleCloudPayablesPaymentsPage( WebDriver driver ) { super(driver); }

    /**
     * Clicks the Actions button at the top of the page to
     * show a dropdown menu of options
     *
     * @return the actions menu
     */
    public WebElement clickActionsButton( )
    {
        WebElement menu;

        wait.waitForElementNotEnabled(blockingPlane);
        wait.waitForElementPresent(actionsButtonLoc);
        scroll.scrollElementIntoView(actionsButtonLoc);
        WebElement button = wait.waitForElementEnabled(actionsButtonLoc);

        click.clickElementCarefully(button);

        try
        {
            menu = wait.waitForElementDisplayed(actionsMenuLoc, 15);
        }
        catch ( TimeoutException e )
        {
            button = wait.waitForElementEnabled(actionsButtonLoc);
            button.click();
            menu = wait.waitForElementDisplayed(actionsMenuLoc);
        }

        return menu;
    }

    /**
     * After clicking Actions to open the dropdown,
     * click one of the options displayed on the menu
     *
     * @param menu the dropdown menu displayed
     * @param expectedText the text of the option desired as it appears on the menu
     */
    public void clickActionFromMenu( WebElement menu, String expectedText )
    {
        List<WebElement> optionsList = wait.waitForAllElementsDisplayed(actionsOptionsLoc, menu);

        WebElement action = element.selectElementByText(optionsList, expectedText);

        wait.waitForElementEnabled(action);

        click.clickElementCarefully(action);

        wait.waitForElementNotDisplayedOrStale(menu, 20);
    }

    /**
     * Clicks the Manage Payments link in
     * the tasks menu
     */
    public void clickManagePaymentLink( ) {
        WebElement menu;

        wait.waitForElementNotEnabled(blockingPlane);
        wait.waitForElementPresent(managePaymentsLoc);
        scroll.scrollElementIntoView(managePaymentsLoc);
        WebElement button = wait.waitForElementEnabled(managePaymentsLoc);

        click.clickElementCarefully(button);
    }

    /**
     * From the actions menu, void the payment
     *
     * @param invoiceAction Select an action to perform
     *
     */
    public void voidPayment(String invoiceAction )
    {
        WebElement menu = clickActionsButton();
        try
        {
            clickActionFromMenu(menu, voidTextSelect);
        }
        catch ( StaleElementReferenceException e )
        {
            menu = clickActionsButton();
            clickActionFromMenu(menu, voidTextSelect);
        }

        WebElement voidPopup = waitForVoidPaymentPopup();
        selectFromDropdown(invoiceAction);

        WebElement submit = driver.findElement(voidPaymentSubmitButtonLoc);
        click.clickElementCarefully(submit);
    }

    /**
     * Clicks on the search button on the page
     */
    public void clickSearchButton( )
    {
        WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc);
        click.clickElementCarefully(searchButton);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * Locates the desired search result on the page
     * from the list of results
     *
     * @param paymentNumber the payment number for the desired result
     * @return WebElement for the desired result
     */
    public WebElement findSearchResult( String paymentNumber )
    {
        WebElement result = null;
        WebElement searchResultsTable = wait.waitForElementEnabled(searchResultsTableLoc);
        List<WebElement> list = wait.waitForAllElementsDisplayed(searchResultsLinesLoc, searchResultsTable);

        for ( WebElement ele : list )
        {
            String resultNum = ele.getText();
            if ( paymentNumber.equals(resultNum) )
            {
                result = ele;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the Final Confirmation message
     */
    public String getConfirmationMessage( )
    {
        wait.waitForElementDisplayed(confirmationMsgPopupLoc);
        WebElement message = wait.waitForElementDisplayed(confirmationMsgContentLoc);
        String confirmationMsg = message.getText();
        return confirmationMsg;
    }

    /**
     * Get the payment status of an invoice
     *
     * @return paymentStatus
     */
    public String getPaymentStatus( )
    {
        scroll.scrollElementIntoView(paymentStatusLoc);
        WebElement status = wait.waitForElementDisplayed(paymentStatusLoc);
        String paymentStatus = status.getText();

        return paymentStatus;
    }

    /**
     * Opens the tasks menu by clicking the Tasks tab
     * on the right side of the screen
     *
     * @return opened menu
     */
    public WebElement openTasksTab( )
    {
        WebElement menu;
        WebElement tasksTab;

        tasksTab = wait.waitForElementEnabled(payablesTasksTabLoc);
        click.clickElement(tasksTab);
        menu = wait.waitForElementDisplayed(payablesMenuLoc);

        return menu;
    }

    /**
     * Select an option from a dropdown menu
     * Object on page must be tagged as "SELECT"
     *
     * @param data         enum containing information for dropdown to select
     * @param selectOption text value of the option to be selected
     */
    public void selectFromDropdown(String selectOption )
    {
        WebElement dropdownWebEle = wait.waitForElementDisplayed(invoiceActionLoc);
        dropdown.selectDropdownByDisplayName(dropdownWebEle, selectOption);
    }

    /**
     * AP Tests
     * Validates the Payment Status by comparing it to the expected status
     *
     * @param page
     * @param expectedTax
     *
     * @return true if taxes match, false if they don't
     */
    public boolean validateTax(String expectedStatus )
    {
        String status = getPaymentStatus();

        VertexLogger.log("Payment status: "+status);

        boolean validated = expectedStatus.equals(status);

        return validated;
    }

    /**
     * Wait for void payment popup to appear after selecting the
     * void option.
     *
     * @return div containing popup container.
     */
    public WebElement waitForVoidPaymentPopup() {
        WebElement popup = wait.waitForElementDisplayed(voidPaymentPopupLoc);
        return popup;
    }

    /**
     * Writes a payment number into the Payment Number search field
     *
     * @param input payment number to input
     * @return the search field
     */
    public WebElement writePaymentNumOperator( String input )
    {
        WebElement searchField = wait.waitForElementEnabled(paymentNumberFieldLoc);
        text.clearText(searchField);
        text.enterText(searchField, input);

        return searchField;
    }

}
