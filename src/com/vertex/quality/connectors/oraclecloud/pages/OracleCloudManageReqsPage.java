package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudReqPagesFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.*;

import java.util.List;

/**
 * ERP section for managing Requisitions within
 * the procurement area of Oracle.
 *
 * @author msalomone
 */
public class OracleCloudManageReqsPage extends OracleCloudBasePage {

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    // Manage Requisitions Search Criteria
    By searchButtonLoc = By.cssSelector("button[id$='q1::search']");
    By requisitionNumberLoc = By.cssSelector("td[id*='SPph::_afrTtxt']");

    // Search Results Items
    By resultsLoc = By.cssSelector("tr[_afrrk]");
    By duplicatedResultLoc = By.xpath("//a[text()='1421']");
    By duplicateIconLoc = By.cssSelector("img[id$='duplicate::icon']");
    By actionsButtonLoc = By.xpath("//a[text()='Actions']");
    By actionsDuplicateLoc = By.xpath("//td[text()='Duplicate']");
    By resultsActionsButtonLoc = By.cssSelector("div[id$='_ATp:ATm'][role='presentation']");
    By resultsActionsMenuLoc = By.cssSelector("div[id$='popup-container'][data-afr-popupid$='ATm']");
    By invoiceActionsOptionsLoc = By.cssSelector("table[id$='ScrollContent'] tbody tr[role='menuitem']");
    By rowSelectedConfirmationLoc = By.xpath("//span[text()='Rows Selected']");

    // Manage Requisitions buttons
    By saveButtonLoc = By.xpath("//span[text()='Save']");
    By submitButtonLoc = By.xpath("//span[text()='Sub']");
    By saveConfirmationLoc = By.xpath("//span[text()='Last Saved']");
    By submitConfirmationLoc = By.xpath("//div[text()='Confirmation']");

    // Submission confirmation popup text
    By submitConTextLoc = By.xpath("//td[text()[contains(.,'was submitted.')]]");

    // Search action menu texts
    String duplicateRequisition = "Duplicate";
    String exportToExcel = "Export to Excel";
    String cancelRequisition = "Cancel Requisition";
    String deleteRequisition = "Delete";
    String editRequisition = "Edit";
    String reassignRequisition = "Reassign";
    String viewDocHistory = "View Document History";

    public OracleCloudManageReqsPage( WebDriver driver ) { super(driver); }

    /**
     * Writes to an information field and tabs to the next field
     *
     * @param data  enum containing field information
     * @param input input to write
     *
     * @return WebElement of field interacted with
     */
    public WebElement inputAndTab(OracleCloudReqPagesFieldData data, String input )
    {
        WebElement field = writeToSearchField(data, input);
        text.pressTab(field);

        return field;
    }

    /**
     * Writes to a search field located on the manage requisitions page.
     *
     * @param data enum containing field information
     * @param inputToField input to write
     * @return WebElement of field interacted with
     */
    public WebElement writeToSearchField( OracleCloudReqPagesFieldData data, String inputToField )
    {
        wait.waitForElementNotEnabled(blockingPlane);

        By loc = data.getLocator();
        WebElement inputField = wait.waitForElementEnabled(loc);

        inputField.sendKeys(inputToField);

        inputField = wait.waitForElementEnabled(loc);

        return inputField;
    }

    /**
     * Clicks the Search button element on the
     * manage requisitions page.
     */
    public void clickSearchButton() {
        WebElement button = wait.waitForElementEnabled(searchButtonLoc);

        click.clickElementCarefully(button);
        wait.waitForElementDisplayed(duplicatedResultLoc); //The first search result.
    }

    /**
     * Clicks the "Save" button on the manage requisitions
     * page. Does not click button's dropdown.
     */
    public void clickSaveRequisition() {
        String text = "Save";

        scroll.scrollElementIntoView(saveButtonLoc);

        WebElement select = element.selectElementByText(saveButtonLoc, text);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        wait.waitForElementDisplayed(saveConfirmationLoc);
    }

    /**
     * Clicks the "Submit" button on the manage requisitions
     * page. Does not click button's dropdown.
     */
    public void clickSubmitRequisition() {
        String text = "Submit";

        scroll.scrollElementIntoView(submitButtonLoc);

        WebElement select = element.selectElementByText(submitButtonLoc, text);
        wait.waitForElementEnabled(select);
        click.clickElementCarefully(select);
        wait.waitForElementDisplayed(submitConfirmationLoc);
    }

    /**
     * Locates the submission confirmation popup box's
     * text. Verifies a requisition was submitted for approval
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
            VertexLogger.log("Could not find confirmation that Requisition was submitted for approval.",
                    VertexLogLevel.ERROR);
        }

        return textMatches;
    }

    /**
     * Duplicates selected requisition via the actions
     * dropdown menu.
     */
    public void duplicateRequisition()
    {
        wait.waitForElementDisplayed(resultsLoc);
        click.clickElementCarefully(resultsLoc);
        wait.waitForElementDisplayed(rowSelectedConfirmationLoc);

        WebElement menu = clickReqSearchActionsButton();
        try
        {
            clickReqActionFromMenu(menu, duplicateRequisition);
        }
        catch ( StaleElementReferenceException e )
        {
            menu = clickReqSearchActionsButton();
            clickReqActionFromMenu(menu, duplicateRequisition);
        }
    }

    /**
     * Clicks the requisition actions button at the bottom of the page to
     * show a dropdown menu of options.
     *
     * @return the actions menu
     */
    private WebElement clickReqSearchActionsButton( )
    {
        WebElement menu;

        wait.waitForElementNotEnabled(blockingPlane);
        wait.waitForElementPresent(resultsActionsButtonLoc);
        scroll.scrollElementIntoView(resultsActionsButtonLoc);
        WebElement button = wait.waitForElementEnabled(resultsActionsButtonLoc);

        click.clickElementCarefully(button);

        try
        {
            menu = wait.waitForElementDisplayed(resultsActionsMenuLoc, 20);
        }
        catch ( TimeoutException e )
        {
            button = wait.waitForElementEnabled(resultsActionsButtonLoc);
            button.click();
            menu = wait.waitForElementDisplayed(resultsActionsMenuLoc);
        }

        return menu;
    }

    /**
     * After clicking the actions button to open the dropdown,
     * click one of the options displayed on the menu.
     *
     * @param menu the dropdown menu displayed
     * @param expectedText the text of the option desired as it appears on the menu
     */
    private void clickReqActionFromMenu( WebElement menu, String expectedText )
    {
        List<WebElement> optionsList = wait.waitForAllElementsDisplayed(invoiceActionsOptionsLoc, menu,
                80);

        String expectedHeader = "Edit Requisition";

        WebElement action = element.selectElementByText(optionsList, expectedText);

        wait.waitForElementEnabled(action);

        click.clickElementCarefully(action);

        wait.waitForElementNotDisplayedOrStale(menu, 20);

        try {
            waitForLoadedPage(expectedHeader);
        } catch (Exception e) {
            click.clickElementCarefully(resultsLoc);
            click.clickElementCarefully(action);
            waitForLoadedPage(expectedHeader);
        }
    }

    /**
     * Locate the Requisition Number field that autofills upon invoice
     * completion and get the number
     *
     * @return the requisition number for that invoice
     */
    public String getRequisitiontionNumber( )
    {
        scroll.scrollElementIntoView(requisitionNumberLoc);
        WebElement transNum = wait.waitForElementDisplayed(requisitionNumberLoc);
        String number = transNum.getText();
        number = number.replaceAll("[^\\d.]", "");

        return number;
    }
}
