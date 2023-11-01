package com.vertex.quality.connectors.oraclecloud.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.oraclecloud.enums.OracleCloudCreateOrderFieldData;
import com.vertex.quality.connectors.oraclecloud.pages.base.OracleCloudBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

/**
 * Create Order page for application
 * Can create Orders here
 *
 * @author Tanmay Mody
 */

public class OracleCloudCreateOrderPage extends OracleCloudBasePage{

    protected By blockingPlane = By.className("AFBlockingGlassPane");

    protected By customerOkButtonLoc = By.cssSelector("button[id$='partyNameId::lovDialogId::ok']");
    protected By shipToAddressDropdownLoc = By.cssSelector("a[id*='shipToAddress::lovIconId']");
    protected By addressSearchButtonLoc = By.cssSelector("a[id*='shipToAddress::dropdownPopup::popupsearch']");
    protected By popupAddressLoc = By.cssSelector("input[id*='qryId2:value00::content']");
    protected By popupSearchButtonLoc = By.cssSelector("button[id*='qryId2::search']");
    protected By newAddressConfirmationLoc = By.cssSelector("button[id*='shipToAddress::lovDialogId::ok']");

    protected By lineQuantityLoc = By.cssSelector("input[id*='createLineQuantity']");
    protected By addButtonLoc = By.cssSelector("div[id*='addLine']");

    protected By quantityFieldLoc = By.cssSelector("input[id*='lineQuantity']");
    protected By saveButtonLoc = By.xpath("//span[text()='Save']");
    protected By saveConfirmationLoc = By.xpath("//span[text()='Last Saved']");
    protected By totalAmountLoc = By.cssSelector("a[id*='cl4']");

    protected By orderLinesActionsLoc = By.cssSelector("div[id*='m5'][role='presentation']");
    protected By addUnreferencedLineLoc = By.cssSelector("tr[id*='unrefReturnActionMenu']");
    protected By unreferencedItemIdLoc = By.cssSelector("input[id*='0:itemNumberId']");
    protected By addItemLoc = By.cssSelector("button[id*='cAdd']");

    // Used as to temporary work around for unreferenced item until oracle settings are changed
    protected By editPriceLoc = By.cssSelector("img[id*='2:i11:0:cil1::icon']");
    protected By typeLoc = By.cssSelector("select[id*='soc2::content']");
    protected By amountLoc = By.cssSelector("input[id*='it2::content']");
    protected By reasonLoc = By.cssSelector("select[id*='soc1::content']");
    protected By saveAndCloseLoc = By.cssSelector("button[id*='mpa_dialog::ok']");

    // Line Item Locs
    protected By linesScrollBarLoc = By.cssSelector("div[id$='scroller']");
    protected By searchItemIconLoc = By.xpath("//a[@title='Search: ItemNumber']");
    protected By searchItemDialogBoxLoc = By.xpath("//div[text()='Search and Select: Item']");
    protected By searchItemFirstResultLoc = By.xpath("//div[contains(@id, ':Popup1:0:Advan1:0:rstab:_ATp:table1::" +
            "db')]");
    protected By searchItemOkButtonLoc = By.cssSelector("button[id$=':cb1']");

    protected By headerLoc = By.cssSelector("td[id*='SPph::_afrTtxt']");
    public OracleCloudCreateOrderPage( WebDriver driver ) { super(driver); }

    /**
     * Add the item in Order Lines
     *
     * @param item
     */
    public void addItem(String item)
    {
        input(OracleCloudCreateOrderFieldData.SELECT_ITEM, item);

        searchAndSelectLineItemResult();

        jsWaiter.sleep(9000);
        wait.waitForElementEnabled(lineQuantityLoc, 120);
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
        jsWaiter.sleep(15000);

        inputAndCheck(OracleCloudCreateOrderFieldData.SELECT_ITEM,item);


        wait.waitForElementEnabled(lineQuantityLoc);
        click.javascriptClick(addButtonLoc);
        wait.waitForElementNotDisplayed(lineQuantityLoc);

        try {
            wait.waitForElementEnabled(editPriceLoc);
        } catch (Exception ex) {
            VertexLogger.log("Scrolling to search for OM edit price dropdown.", VertexLogLevel.WARN);
            String tableId = "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:AP1:pc1:t1::scroller";
            scroll.scrollTableRight(linesScrollBarLoc, tableId, "1200");
            wait.waitForElementEnabled(editPriceLoc);
        }

        click.clickElementCarefully(editPriceLoc);
        wait.waitForElementDisplayed(saveAndCloseLoc);

        Select type = new Select(driver.findElement(By.id(
                "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:AP1:r15:0:t1:0:soc2::content")));
        type.selectByVisibleText("Discount amount");

        WebElement field = wait.waitForElementEnabled(amountLoc);
        text.clearText(field);
        text.enterText(field, amount);

        Select reason = new Select(driver.findElement(By.id(
                "_FOpt1:_FOr1:0:_FONSr2:0:_FOTsr1:1:AP1:r15:0:t1:0:soc1::content")));
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
     * Clicks OK in the confirmation dialog box
     * to select a customer
     */
    public void clickOkSelectCustomer()
    {
        WebElement okButton = wait.waitForElementEnabled(customerOkButtonLoc);
        click.clickElementCarefully(okButton);
        wait.waitForElementNotDisplayed(customerOkButtonLoc);
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
     * Edit the Ship To Address field
     *
     * @param input address
     */
    public void editShipToAddress( String input )
    {
        WebElement dropdown = wait.waitForElementEnabled(shipToAddressDropdownLoc);
        click.clickElementCarefully(dropdown);

        wait.waitForElementEnabled(addressSearchButtonLoc);
        click.clickElementCarefully(addressSearchButtonLoc);

        WebElement address = wait.waitForElementEnabled(popupAddressLoc);
        text.clearText(address);
        text.enterText(address, input);

        wait.waitForElementEnabled(popupSearchButtonLoc);
        click.clickElementCarefully(popupSearchButtonLoc);

        wait.waitForElementEnabled(newAddressConfirmationLoc);
        click.clickElementCarefully(newAddressConfirmationLoc);
        wait.waitForElementNotDisplayed(newAddressConfirmationLoc);
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
     * Get the Order Number generated by Oracle
     *
     * @return the Order number
     */
    public String getOrderNumber( )
    {
        scroll.scrollElementIntoView(headerLoc);
        WebElement transNum = wait.waitForElementDisplayed(headerLoc);
        String number = transNum.getText();
        number = number.replaceAll("[^\\d.]", "");

        return number;
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
     * Writes to a general information field and tabs to the next field
     *
     * @param data  enum containing field information
     * @param input input to write
     *
     * @return WebElement of field interacted with
     */
    public WebElement inputAndTab( OracleCloudCreateOrderFieldData data, String input )
    {
        WebElement field = writeToGeneralField(data, input);
        text.pressTab(field);

        return field;
    }

    /**
     * Writes to an information field without any further key input.
     *
     * @param data  enum containing field information
     * @param input input to write
     *
     * @return WebElement of field interacted with
     */
    public WebElement input(OracleCloudCreateOrderFieldData data, String input) {
        WebElement field = writeToGeneralField(data, input);

        return field;
    }

    /**
     * Selects the search icon next to the select item input box. Proceeds
     * to select the first result from the dialog box, and confirms by clicking
     * the Ok button.
     */
    public void searchAndSelectLineItemResult() {
        WebElement searchIcon = wait.waitForElementDisplayed(searchItemIconLoc);
        jsWaiter.sleep(10000);
        click.javascriptClick(searchIcon);
        wait.waitForElementDisplayed(searchItemDialogBoxLoc);

        WebElement result = wait.waitForElementDisplayed(searchItemFirstResultLoc);
        click.javascriptClick(result);

        WebElement okButton = wait.waitForElementEnabled(searchItemOkButtonLoc);
        click.javascriptClick(okButton);
    }

    /**
     * Selects a business unit or other field
     * from the business unit or other field drop down menus.
     *
     * @param data enum for OM create order page field.
     * @param selectOption field value to select from drop down.
     */
    public void selectFromDropdown(OracleCloudCreateOrderFieldData data, String selectOption) {
        By dropdownLoc = data.getLocator();
        WebElement dropdownWebEle = wait.waitForElementDisplayed(dropdownLoc);
        dropdown.selectDropdownByDisplayName(dropdownWebEle, selectOption);
    }

    /**
     *	Writes to the general information fields in the top half
     *	of the create transaction page
     *
     * @param data enum containing field information
     * @param inputToField input to write
     * @return WebElement of field interacted with
     */
    public WebElement writeToGeneralField( OracleCloudCreateOrderFieldData data, String inputToField )
    {
        wait.waitForElementNotEnabled(blockingPlane);

        By loc = data.getLocator();
        WebElement inputField = wait.waitForElementEnabled(loc);

        inputField.sendKeys(inputToField);

        inputField = wait.waitForElementEnabled(loc);

        return inputField;
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
