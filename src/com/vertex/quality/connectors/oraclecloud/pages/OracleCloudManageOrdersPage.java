package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateOrderFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Manage Order page for application
 * Can edit Orders here
 *
 * @author Tanmay Mody
 */

public class OracleCloudManageOrdersPage extends OracleCloudBasePage {

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    protected By orderNumberFieldLoc = By.cssSelector("input[id*='value10']");
    protected By searchButtonLoc = By.cssSelector("button[id*='search']");
    protected By searchedOrderLoc = By.cssSelector("a[id*='cl1']");
    protected By actionsDropdownLoc = By.cssSelector("div[id*='APVIEW1:mb1::oc']");
    protected By editActionLoc = By.cssSelector("tr[id*='APVIEW1:editAction']");

    protected By orderLinesActionsLoc = By.cssSelector("div[id*='m5'][role='presentation']");
    protected By addUnreferencedLineLoc = By.cssSelector("tr[id*='unrefReturnActionMenu']");
    protected By unreferencedItemIdLoc = By.cssSelector("input[id*='0:itemNumberId']");
    protected By addItemLoc = By.cssSelector("button[id*='cAdd']");
    protected By lineQuantityLoc = By.cssSelector("input[id*='createLineQuantity']");
    protected By addButtonLoc = By.cssSelector("div[id*='addLine']");
    protected By quantityFieldLoc = By.cssSelector("input[id*='lineQuantity']");

    protected By contractEndDateLoc = By.cssSelector("input[id*='2:id4']");
    protected By lineTypeLoc = By.cssSelector("select[id*='2:soc6']");
    protected By orangeDropdownLoc = By.cssSelector("button[id*='2:gearIconCreate']");
    protected By deleteLoc = By.cssSelector("tr[id*='2:deleteLine']");
    protected By deleteConfirmLoc = By.cssSelector("button[id*='deleteWarningDlg::ok']");
    protected By saveButtonLoc = By.xpath("//span[text()='Save']");
    protected By saveConfirmationLoc = By.xpath("//span[text()='Last Saved']");
    protected By totalAmountLoc = By.cssSelector("a[id*='cl4']");

    // Used as to temporary work around for unreferenced item until oracle settings are changed
    protected By editPriceLoc = By.cssSelector("img[id*='1:i11:0:cil1::icon']");
    protected By typeLoc = By.cssSelector("select[id*='soc2::content']");
    protected By amountLoc = By.cssSelector("input[id*='it2::content']");
    protected By reasonLoc = By.cssSelector("select[id*='soc1::content']");
    protected By saveAndCloseLoc = By.cssSelector("button[id*='mpa_dialog::ok']");

    // Line Item Locs
    protected By linesScrollBarLoc = By.cssSelector("div[id$='scroller']");

    public OracleCloudManageOrdersPage( WebDriver driver ) { super(driver); }

    /**
     * Add the item in Order Lines
     *
     * @param item
     */
    public void addItem(String item)
    {
        inputAndCheck(OracleCloudCreateOrderFieldData.SELECT_ITEM,item);

        wait.waitForElementEnabled(lineQuantityLoc);
        click.clickElementCarefully(addButtonLoc);
        wait.waitForElementNotDisplayed(lineQuantityLoc);
    }


    /**
     * Add the item as unreferenced in Order Lines
     *
     * @param item
     */
    public void addItemAsUnreferenced(String item, String amount)
    {
        inputAndCheck(OracleCloudCreateOrderFieldData.SELECT_ITEM,item);

        wait.waitForElementEnabled(lineQuantityLoc);
        click.clickElementCarefully(addButtonLoc);
        wait.waitForElementNotDisplayed(lineQuantityLoc);

        try {
            wait.waitForElementEnabled(editPriceLoc);
        } catch (Exception ex) {
            VertexLogger.log("Scrolling to search for OM edit price dropdown.", VertexLogLevel.DEBUG);
            String tableId = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:3:AP1:pc1:t1::scroller";
            scroll.scrollTableRight(linesScrollBarLoc, tableId, "500");
            wait.waitForElementEnabled(editPriceLoc);
        }

        click.clickElementCarefully(editPriceLoc);
        wait.waitForElementDisplayed(saveAndCloseLoc);

        Select type = new Select(driver.findElement(By.id(
                "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:3:AP1:r15:0:t1:0:soc2::content")));
        type.selectByVisibleText("Discount amount");

        WebElement field = wait.waitForElementEnabled(amountLoc);
        text.clearText(field);
        text.enterText(field, amount);

        Select reason = new Select(driver.findElement(By.id(
                "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:3:AP1:r15:0:t1:0:soc1::content")));
        reason.selectByVisibleText("Sales negotiation");

        click.clickElementCarefully(saveAndCloseLoc);
        wait.waitForElementNotDisplayed(saveAndCloseLoc);
    }

    /**
     * Add the Unreferenced Return Line item in Order Lines
     *
     * @param item
     */
    public void addUnreferencedReturnLineItem(String item)
    {
        wait.waitForElementEnabled(orderLinesActionsLoc);
        click.clickElementCarefully(orderLinesActionsLoc);

        wait.waitForElementEnabled(addUnreferencedLineLoc);
        click.clickElementCarefully(addUnreferencedLineLoc);

        wait.waitForElementEnabled(unreferencedItemIdLoc);

        inputAndCheck(OracleCloudCreateOrderFieldData.SELECT_UNREFERENCED_ITEM,item);

        wait.waitForElementEnabled(addItemLoc);
        click.clickElementCarefully(addItemLoc);
        wait.waitForElementNotDisplayed(addItemLoc);
    }

    /**
     * Helper method
     * Checks the written input on a header field
     *
     * @param data          enum containing field information
     * @param expectedInput the text expected to be in the field
     *
     * @return whether the input found matches the input expected
     */
    public boolean checkInput( OracleCloudCreateOrderFieldData data, String expectedInput )
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
     * Clicks on the "Save" button to save an invoice
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
     * Deletes the item from the line item table.
     */
    public void deleteItemAsUnreferenced()
    {
        WebElement orangeDropdown = wait.waitForElementPresent(orangeDropdownLoc);
        scroll.scrollElementIntoView(orangeDropdown);

        String tableId = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:3:AP1:pc1:t1::scroller";
        scroll.scrollTableRight(linesScrollBarLoc, tableId, "500");
        wait.waitForElementDisplayed(orangeDropdown);
        click.clickElementCarefully(orangeDropdown);

        WebElement delete = wait.waitForElementEnabled(deleteLoc);
        click.clickElementCarefully(delete);

        WebElement confirm = wait.waitForElementEnabled(deleteConfirmLoc);
        click.clickElementCarefully(confirm);
        wait.waitForElementNotDisplayed(confirm);
    }

    /**
     * Deletes the item
     */
    public void deleteUnreferencedLineItem()
    {

        text.pressTab(contractEndDateLoc);
        text.pressTab(lineTypeLoc);

        WebElement orangeDropdown = wait.waitForElementPresent(orangeDropdownLoc);
        scroll.scrollElementIntoView(orangeDropdown);
        click.clickElementCarefully(orangeDropdown);

        WebElement delete = wait.waitForElementEnabled(deleteLoc);
        click.clickElementCarefully(delete);

        WebElement confirm = wait.waitForElementEnabled(deleteConfirmLoc);
        click.clickElementCarefully(confirm);
        wait.waitForElementNotDisplayed(confirm);
    }

    /**
     * Edits the order
     */
    public void editOrder()
    {
        WebElement invoice = wait.waitForElementEnabled(searchedOrderLoc);
        click.clickElementCarefully(invoice);

        WebElement action = wait.waitForElementEnabled(actionsDropdownLoc);
        click.clickElementCarefully(action);

        WebElement edit = wait.waitForElementEnabled(editActionLoc);
        click.clickElementCarefully(edit);
    }

    /**
     * Edit the Quantity field
     *
     * @param input quantity
     * @return the field
     */
    public WebElement editQuantity( String input )
    {
        WebElement field = wait.waitForElementEnabled(quantityFieldLoc);
        text.clearText(field);
        text.enterText(field, input);

        return field;
    }

    /**
     * Get the total amount from the top
     *
     * @return total
     */
    public String getTotalTransTax( )
    {
        wait.waitForElementNotEnabled(blockingPlane);
        WebElement totalTax = wait.waitForElementEnabled(totalAmountLoc);
        String total = totalTax.getText();

        return total;
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
    public WebElement inputAndCheck( OracleCloudCreateOrderFieldData data, String input )
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
     * Writes an order number into the Order Number search field
     *
     * @param input order number to search
     */
    public void searchOrderNumber( String input )
    {
        WebElement searchField = wait.waitForElementEnabled(orderNumberFieldLoc);
        text.clearText(searchField);
        text.enterText(searchField, input);

        WebElement searchButton = wait.waitForElementEnabled(searchButtonLoc);
        click.clickElementCarefully(searchButton);

        wait.waitForElementNotDisplayed(orderNumberFieldLoc);
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
    public WebElement writeToHeaderField(OracleCloudCreateOrderFieldData data, String inputToField )
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
