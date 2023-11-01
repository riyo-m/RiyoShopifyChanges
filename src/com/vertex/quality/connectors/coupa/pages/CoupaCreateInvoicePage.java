package com.vertex.quality.connectors.coupa.pages;

import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.connectors.coupa.tests.utils.CoupaUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * represents the Create Invoice page of the Coupa environment
 *
 * @author alewis
 */
public class CoupaCreateInvoicePage extends VertexPage {

    protected By userName = By.xpath("//input[@id='user_login']");
    protected By passWord = By.xpath("//input[@id='user_password']");
    protected By coupaSignInButton = By.id("login_button");
    protected By invoices = By.xpath("//a[contains(text(),'Invoices')]");
    protected By createInvoice = By.xpath("//a[@class='btn invCreateBtn']");
    protected By coupaSupplier = By.xpath("//input[@id='supplier_1']");
    protected By coupaInvoice = By.xpath("//input[@id='invoice_invoice_number']");
    protected By cartButton = By.xpath("//a[@id='cart']");
    protected By searchBox = By.xpath("//body[1]/div[1]/div[4]/div[1]/div[1]/form[1]/div[2]/section[2]/div[2]/div[1]/div[1]/div[1]/a[1]/img[1]");
    protected By addressTo = By.xpath("//tbody/tr[@id='picker_address_row_131']/td[10]/a[1]/span[1]");
    protected By addLineItem = By.xpath("//span[contains(text(),'Add Line')]");
    protected By itemName = By.xpath("//input[@id='requisition_line_description']");
    protected By supplierName = By.xpath("//input[@id='requisition_line_supplier_search']");
    protected By unitPrice = By.xpath("//input[@id='requisition_line_unit_price_amount']");
    protected By quantity = By.xpath("//input[@id='requisition_line_quantity']");
    protected By shipping = By.xpath("//span[contains(text(),'Standard')]");
    protected By approvalSubmitButton = By.xpath("//body[1]/div[1]/div[4]/div[1]/div[1]/form[1]/div[3]/button[1]/span[1]");
    protected By itemSave = By.xpath("//body[1]/div[1]/div[4]/div[1]/div[1]/form[1]/section[1]/div[3]/div[3]/div[1]/section[1]/div[1]/div[3]/div[1]/div[1]/div[3]/div[1]/button[1]/span[1]");
    protected By billingClass = By.className("account_picker_popup_button");
    protected By accountClass = By.className("chosen-single");
    protected By lineItemID = By.id("requisition_line_description");
    protected By autoCompleteRowClass = By.id("supplier_1_opt0");
    protected By autoCompleteClass = By.className("ui-autocomplete");
    protected By descriptionClass = By.className("acitem");
    protected By quantityClass = By.className("quantity_field_input");
    protected By taxRateID = By.id("tax_rate_1");
    protected By calculateTaxButtonID = By.id("calculate_taxes_button");
    protected By taxAmountClass = By.xpath("//*/div[@class=\"summaryTaxLineSection s-summaryTaxLineSection\"]//*/span[@class=\"tax_amount_engine\"]");
	protected By totalTaxAmountClass = By.xpath("//*/div[@class=\"totals__taxes s-totalsTaxes\"]/span[@id=\"totalTaxes\"]");
	protected By firstVertexTaxAmountClass = By.xpath("//*/div[@class=\"taxSection s-taxSection\"]/div/span/span/span[@class=\"tax_amount_engine\"]");
	protected By userTaxAmountID = By.id("invoice_header_total_taxes");
    protected By billlingButtonClass = By.className("account_picker_popup_button");
    protected By bypassParentClass = By.className("ui-dialog-buttonset");
    protected By bypassButtonClass= By.className("ui-button-text");
    protected By companyOptionOneID = By.id("account_segment_1_lv_id_chosen_results");
    protected By regionOptionTwoID = By.id("account_segment_2_lv_id_chosen_results_option_2");
    protected By deptOptionOneID = By.id("account_segment_3_lv_id_chosen_results_option_1");
    protected By accountOptionOneID = By.id("account_segment_4_lv_id_chosen_results_option_1");
    protected By submitButtonID = By.id("update_invoice");
    protected By createDropDownID = By.id("invoice_create_drop");
    protected By createCreditNoteID = By.id("create_credit_note_link");
    protected By creditNoteOptionsClass = By.className("active-result");
    protected By chooseButtonClass = By.className("choose_dynamic_account");
    protected By creditNoteButtonFinalClass = By.className("adjustment-buttons");
    protected By addLineItemID = By.id("add_invoice_line");
    protected By lineLevelTaxID = By.id("s-invoiceLineLevelTaxation");
    protected By taxRateClass = By.className("tax_rate");
    protected By partialCreditNoteID = By.id("complete_correction_false");
    protected By firstSupplierOptionClass = By.className("sprite-accept_small");
    protected By deleteItemClass = By.className("sprite-delete");
    protected By errorClass = By.className("error_head");
    protected By cartCommodityClass = By.className("commodity_selector");
    protected By supplierSelectionMenuButton = By.xpath("//*/tr[@id=\"picker_remit_to_address_row_1200\"]/td/a");
    protected By shippingAddressButton = By.className("pick_address_link");
    protected By shippingAddressInput = By.id("sf_picker_address");
    protected By shippingAddressSubmit = By.xpath("//*[@id=\"picker_address_row_1196\"]/td[10]/a");
    protected By invoiceFailedContinueButton = By.xpath("//*/div/div/button/span[contains(text(),'Submit')]");
    protected By chooseInvoiceFileButton = By.id("data_source_file");
    protected By startUploadButton = By.xpath("//button/span[contains(text(),'Start Upload')]");
    protected By doneUploadButton =By.xpath("//a/span[contains(text(),'Done')]");
    protected By vertexCalculatedInvoiceTax = By.className("s-taxEngineAmt");
    protected By shippingCostTexbox = By.xpath("/html/body/div[1]/div[5]/div/div/div[5]/form/section[2]/div/section/div/div[2]/div[2]/div[1]/div[1]/span[2]/input[4]");
    protected By handlingCostTextbox = By.xpath("/html/body/div[1]/div[5]/div/div/div[5]/form/section[2]/div/section/div/div[2]/div[2]/div[2]/div[1]/span[2]/input[4]");
    protected By miscCostTextbox = By.xpath("/html/body/div[1]/div[5]/div/div/div[5]/form/section[2]/div/section/div/div[2]/div[2]/div[3]/div[1]/span[2]/input[4]");
    protected By generalTaxCostTextbox = By.xpath("/html/body/div[1]/div[5]/div/div/div[5]/form/section[2]/div/section/div/div[2]/div[3]/div/span[4]/input[1]");
    protected By headerLevelCalculatedTax = By.xpath("//*[@id=\"summary\"]/div/div[2]/div[3]/div/span[4]/span/span");
    protected By addLineItemCartInput = By.className("acitem.classifiable.ui-autocomplete-input");
    protected By creditNoteTax = By.xpath("//*[@id=\"summary\"]/div/div[2]/div[3]/div/span[4]/span/span");
    protected By editInvoicePrice = By.xpath("/html/body/div[1]/div[5]/div/div/div[5]/form/section[2]/div/div[3]/div[1]/div[1]/span[5]/div/input");
    protected By confirmationText = By.xpath("//*[@id=\"flash_container\"]/div/div[3]/div");

    public CoupaCreateInvoicePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Fills in the base form for an invoice
     * */
    public void fillDefaultInvoice(String invoiceNumber, String shipTo){
        enterInvoiceSupplierMenuSelect("Coupa Test US Supplier");
        enterInvoiceNumber(invoiceNumber);
        selectShippingAddress(shipTo);

        enterItemName("laptop0");
        editQuantity("1");

        selectBillingButton();
    }

    /**
     * Enter invoice number
     *
     * @param invoiceNumber
     * */
    public void enterInvoiceNumber(String invoiceNumber){
        WebElement invoiceField = wait.waitForElementDisplayed(coupaInvoice);
        text.enterText(invoiceField,invoiceNumber);
    }

    /**
     * Enters the item being purchased in an invoice
     *
     * @param item
     * */
    public void enterItemName(String item){
        WebElement description = wait.waitForElementDisplayed(descriptionClass);
        text.enterText(description,item);
        WebElement autoCompleteRowTwo = wait.waitForElementDisplayed(autoCompleteRowClass);
        click.clickElementCarefully(autoCompleteRowTwo);
    }

    /**
     * Adds a general tax rate to the invoice
     * */
    public void addSupplierTaxRate(String taxPercent){
        WebElement taxRate = wait.waitForElementDisplayed(taxRateID);
        text.clearText(taxRate);
        text.enterText(taxRate,taxPercent);
    }

    /**
     * Bypasses the need for approval on an invoice
     * */
    public void bypassApproval() {
        WebElement bypassParent = wait.waitForElementDisplayed(bypassParentClass);
        WebElement bypass = wait.waitForElementDisplayed(bypassButtonClass,bypassParent);
        click.clickElementCarefully(bypass);
    }

    /**
     * Enters the supplier for an invoice
     * and selects the option in the pop-up
     * if this supplier has a duplicate
     * */
    public void enterInvoiceSupplierMenuSelect(String supplier){
        WebElement coupaSupplierField = wait.waitForElementDisplayed(coupaSupplier);
        text.enterText(coupaSupplierField,supplier);
        jsWaiter.sleep(2000);
        WebElement autoCompleteRow = wait.waitForElementDisplayed(autoCompleteRowClass);
        click.clickElementCarefully(autoCompleteRow);
        jsWaiter.sleep(2000);
        WebElement supplierSelectionButton = wait.waitForElementDisplayed(supplierSelectionMenuButton);
        click.clickElementCarefully(supplierSelectionButton);
    }

    /**
     * Enters an invoice supplier which will not
     * resort in a pop-up
     * */
    public void enterInvoiceSupplierNoMenu(String supplier){
        WebElement coupaSupplierField = wait.waitForElementDisplayed(coupaSupplier);
        text.enterText(coupaSupplierField,supplier);
        jsWaiter.sleep(2000);
        coupaSupplierField.sendKeys(Keys.DOWN);
        coupaSupplierField.sendKeys(Keys.ENTER);
    }


    /**
     * Enters the specified shipping address for an invoice
     * */
    public void selectShippingAddress(String address){
        WebElement shippingButton = wait.waitForElementDisplayed(shippingAddressButton);
        click.clickElementCarefully(shippingButton);
        WebElement shippingInput =wait.waitForElementDisplayed(shippingAddressInput);
        text.enterText(shippingInput, address);
        shippingInput.sendKeys(Keys.ENTER);
        WebElement shippingAddress = wait.waitForElementDisplayed(shippingAddressSubmit);
        click.clickElementCarefully(shippingAddress);
    }


    /**
     * Gets the message on the approval window
     *
     * @return message
     */
    public String getMessageOnApproval() {
        WebElement error = wait.waitForElementDisplayed(errorClass);

        String message = text.getElementText(error);

        return message;
    }

    /**
     * Gets Vertex tax value
     *
     * @return vertex Tax
     */
    public String getVertexTaxValue() {
        WebElement vertexTaxAmount = wait.waitForElementDisplayed(taxAmountClass);
        String vertexTaxString = text.getElementText(vertexTaxAmount);

        return vertexTaxString;
    }

    public String getVertexTaxValueTwo() {
        WebElement vertexTaxAmount = wait.waitForElementDisplayed(totalTaxAmountClass);
        String vertexTaxString = text.getElementText(vertexTaxAmount);

        return vertexTaxString;
    }

    /**
     * Gets first vertex tax value listed
     *
     * @return tax value
     */
    public String getFirstVertexTaxValue() {
        WebElement vertexTaxAmount = wait.waitForElementDisplayed(firstVertexTaxAmountClass);
        String vertexTaxString = text.getElementText(vertexTaxAmount);

        return vertexTaxString;
    }

    /**
     * gets the tax that the user manual enters in Coupa
     */
    public String getUserTaxValue() {
        WebElement userTaxAmount = wait.waitForElementDisplayed(userTaxAmountID);
        String userTaxString = text.getElementText(userTaxAmount);

        return userTaxString;
    }


    /**
     * enters a supplier and line item with line level tax
     */
    public void enterSupplierLineLevel() {
        fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
        setLineLevelTax();
        WebElement calculateTaxButton = wait.waitForElementDisplayed(calculateTaxButtonID);
        click.clickElementCarefully(calculateTaxButton);
    }

    /**
     * enters a supplier without charged tax
     */
    public void enterSupplierNoChargedTax() {
        fillDefaultInvoice(CoupaUtils.randomInvoiceNumber(), "gettysburg");
    }

    /**
     * select billing button and add account number
     */
    public void selectBillingButton() {
        WebElement billingButton = wait.waitForElementDisplayed(billingClass);
        click.clickElementCarefully(billingButton);

        List<WebElement> fields = wait.waitForAllElementsDisplayed(accountClass);

        WebElement companyField = fields.get(0);
        click.clickElementCarefully(companyField);
        WebElement companyOptionOne = wait.waitForElementDisplayed(companyOptionOneID);
        click.clickElementCarefully(companyOptionOne);

        List<WebElement> regionFieldXXXX = wait.waitForAllElementsDisplayed(By.className("chosen-scopes"));
        WebElement regionField = regionFieldXXXX.get(1);
        //WebElement regionField = fields.get(1);
        click.clickElementCarefully(regionField);
        WebElement regionOptionTwo = wait.waitForElementDisplayed(regionOptionTwoID);
        click.clickElementCarefully(regionOptionTwo);

        WebElement deptField = fields.get(2);
        click.clickElementCarefully(deptField);
        WebElement deptOptionOne = wait.waitForElementDisplayed(deptOptionOneID);
        click.clickElementCarefully(deptOptionOne);

        WebElement accountField = fields.get(3);
        click.clickElementCarefully(accountField);
        WebElement accountOptionOne = wait.waitForElementDisplayed(accountOptionOneID);
        click.clickElementCarefully(accountOptionOne);

        WebElement chooseButton = wait.waitForElementDisplayed(chooseButtonClass);
        click.clickElementCarefully(chooseButton);
    }


    //TODO figure out why this gets stuck after pressing enter
    /**
     * adds a second line item, a monitor
     */
    public void addLineItem() {
        WebElement addLineItem = wait.waitForElementDisplayed(addLineItemID);
        click.clickElementCarefully(addLineItem);

        List<WebElement> lineItems = wait.waitForAllElementsDisplayed(descriptionClass);
        WebElement lineItem = lineItems.get(1);
        text.enterText(lineItem,"Monitof0");
        jsWaiter.sleep(1000);
        lineItem.sendKeys(Keys.DOWN);
        lineItem.sendKeys(Keys.ENTER);

    }

    /**
     * click on submit for approval
     */
    public void submitForApprovalButton()
    {
        WebElement approvalSubmit = wait.waitForElementDisplayed(approvalSubmitButton);
        click.clickElementCarefully(approvalSubmit);
    }

    /**
     * clicks the 'submit' button to submit the invoice
     */
    public void submitButton() {
        WebElement submitButton = wait.waitForElementDisplayed(submitButtonID);
        click.clickElementCarefully(submitButton);
    }

    /**
     * enters the credit note info for a full refund
     */
    public void createNewCreditNoteFull() {
        WebElement createDropDown = wait.waitForElementDisplayed(createDropDownID);
        click.clickElementCarefully(createDropDown);

        WebElement createCreditNote = wait.waitForElementDisplayed(createCreditNoteID);
        click.clickElementCarefully(createCreditNote);

        WebElement creditField = wait.waitForElementDisplayed(accountClass);
        click.clickElementCarefully(creditField);

        List<WebElement> selectInvoicesToCredit = wait.waitForAllElementsDisplayed(creditNoteOptionsClass);
        WebElement selectInvoiceToCredit = selectInvoicesToCredit.get(0);
        click.clickElementCarefully(selectInvoiceToCredit);

        List<WebElement> bypassButtons = wait.waitForAllElementsDisplayed(bypassButtonClass);
        WebElement continueButton = bypassButtons.get(0);
        click.clickElementCarefully(continueButton);

        List<WebElement> finalButtons = wait.waitForAllElementsDisplayed(creditNoteButtonFinalClass);
        WebElement createButton = finalButtons.get(0);
        click.clickElementCarefully(createButton);

        //submitButton();
    }

    /**
     * enters the credit note info for a partial refund
     */
    public void createNewCreditNotePartial() {
        WebElement createDropDown = wait.waitForElementDisplayed(createDropDownID);
        click.clickElementCarefully(createDropDown);

        WebElement createCreditNote = wait.waitForElementDisplayed(createCreditNoteID);
        click.clickElementCarefully(createCreditNote);

        WebElement creditField = wait.waitForElementDisplayed(accountClass);
        click.clickElementCarefully(creditField);

        List<WebElement> selectInvoicesToCredit = wait.waitForAllElementsDisplayed(creditNoteOptionsClass);
        WebElement selectInvoiceToCredit = selectInvoicesToCredit.get(0);
        click.clickElementCarefully(selectInvoiceToCredit);

        List<WebElement> bypassButtons = wait.waitForAllElementsDisplayed(bypassButtonClass);
        WebElement continueButton = bypassButtons.get(0);
        click.clickElementCarefully(continueButton);

        WebElement partialCreditNoteButton = wait.waitForElementDisplayed(partialCreditNoteID);
        click.clickElementCarefully(partialCreditNoteButton);

        List<WebElement> finalButtons = wait.waitForAllElementsDisplayed(creditNoteButtonFinalClass);
        WebElement createButton = finalButtons.get(0);
        click.clickElementCarefully(createButton);
    }

    /**
     * removes second line item
     */
    public void removeLineItem() {
        WebElement deleteItemButton = wait.waitForElementDisplayed(deleteItemClass);
        click.clickElementCarefully(deleteItemButton);
    }

    /**
     * add line item
     */
    public void addLineItemValue()
    {
        WebElement lineItemValue = wait.waitForElementDisplayed(addLineItem);
        click.clickElementCarefully(lineItemValue);
    }

    /**
     * clicks line level taxation and enter tax rate
     */
    public void setLineLevelTax() {
        WebElement lineLevelTax = wait.waitForElementDisplayed(lineLevelTaxID);
        click.clickElementCarefully(lineLevelTax);

        List<WebElement> taxRates = wait.waitForAllElementsDisplayed(taxRateClass);
        WebElement taxRateOne = taxRates.get(0);
        text.enterText(taxRateOne, "6");

        WebElement taxRateTwo = taxRates.get(0);
        text.enterText(taxRateTwo,"6");
    }

    /**
     * Sets line level tax without adding any taxation on the line item
     * */
    public void setLineLevelTaxNoUserTaxRate() {
        WebElement lineLevelTax = wait.waitForElementDisplayed(lineLevelTaxID);
        click.clickElementCarefully(lineLevelTax);
    }

    /**
     * enters the number of the credit note
     */
    public void enterCreditNoteNumber() {
        WebElement creditNoteNumber = wait.waitForElementDisplayed(coupaInvoice);
        text.enterText(creditNoteNumber,"Aaron Credit Note 2");
    }

    /**
     * clicks the calculate tax button
     */
    public void clickCalculateTax() {
        WebElement calcTaxButton = wait.waitForElementDisplayed(calculateTaxButtonID);
        click.clickElementCarefully(calcTaxButton);
    }

    /**
     * adds commodity
     */
    public void addCommodity() {
        WebElement commodityExpandButton = wait.waitForElementDisplayed(cartCommodityClass);
        click.clickElementCarefully(commodityExpandButton);
        text.enterText(commodityExpandButton,"Lap");
        commodityExpandButton.sendKeys(Keys.DOWN);
        commodityExpandButton.sendKeys(Keys.ENTER);
    }


    /**
     * Fills in a line level invoice with no vendor tax and submits
     *
     * @param invoiceNumber the invoice number to be added
     * */
    public void fillInvoiceNoVendorTax(String invoiceNumber){
        WebElement lineLevelTax = wait.waitForElementDisplayed(lineLevelTaxID);
        click.clickElementCarefully(lineLevelTax);

        fillDefaultInvoice(invoiceNumber, "gettysburg");

        WebElement calcTaxButton = wait.waitForElementDisplayed(calculateTaxButtonID);
        click.clickElementCarefully(calcTaxButton);
        //wait for the tax calculation
        jsWaiter.sleep(3000);
        WebElement submitButton = wait.waitForElementDisplayed(submitButtonID);
        click.clickElementCarefully(submitButton);

        WebElement invoiceFailedContinue = wait.waitForElementDisplayed(invoiceFailedContinueButton);
        click.clickElementCarefully(invoiceFailedContinue);


    }

    /**
     * uploads a .csv file to submit an invoice
     *
     * Currently you must change the name of the invoice in the csv file
     * as they have to be unique
     * */
    public void doBulkInvoiceUpload(){
        WebElement chooseFile = wait.waitForElementDisplayed(chooseInvoiceFileButton);
        chooseFile.sendKeys("C:\\ConnectorDev\\ConnectorQuality\\resources\\csvfiles\\coupa\\invoice_header_list.csv");
        WebElement startUpload = wait.waitForElementDisplayed(startUploadButton);
        click.clickElementCarefully(startUpload);
        WebElement doneButton = wait.waitForElementDisplayed(doneUploadButton);
        click.clickElementCarefully(doneButton);
    }
    /**
     * Gets the vertex calculated tax after clicking on a specific invoice
     * on the invoice page
     *
     * @return the tax amount calculated by vertex
     * */
    public String getVertexCalculatedInvoiceTax(){
        WebElement vertCalcTax = wait.waitForElementDisplayed(vertexCalculatedInvoiceTax);
        return vertCalcTax.getText();
    }

    /**
     * Header level shipping cost
     *
     * @param cost of shipping
     */
    public void enterShippingCost(String cost){
        WebElement shippingCost = wait.waitForElementDisplayed(shippingCostTexbox);
        text.enterText(shippingCost,cost);
    }

    /**
     * Header level handling cost
     * @param cost of handling
     */
    public void enterHandlingCost(String cost){
        WebElement handlingCost = wait.waitForElementDisplayed(handlingCostTextbox);
        text.enterText(handlingCost,cost);
    }

    /**
     * Header level misc cost
     *
     * @param cost of misc
     */
    public void enterMiscCost(String cost){
        WebElement miscCost = wait.waitForElementDisplayed(miscCostTextbox);
        text.enterText(miscCost,cost);
    }

    /**
     * Submits a header level invoice with user
     * entered tax for shipping, handling, misc and general
     *
     * @return the calculated tax from vertex
     * */
    public String headerLevelWhenUserEntersTax() {
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();
        fillDefaultInvoice(invoiceNumber, "gettysburg");

        enterShippingCost("5");
        enterHandlingCost("5");
        enterMiscCost("5");
        addSupplierTaxRate("10");

        WebElement generalCost = wait.waitForElementDisplayed(generalTaxCostTextbox);
        text.clearText(generalCost);
        //if you don't wait here the textbox will reset to 100 and you get 100101.5
        jsWaiter.sleep(1000);
        text.enterText(generalCost,"101.5");

        clickCalculateTax();

        WebElement calculatedTax = wait.waitForElementDisplayed(headerLevelCalculatedTax);
        return calculatedTax.getText();
    }

    /**
     * submits a header level invoice with no user entered tax
     * but with shipping, handling, and misc costs
     *
     * @return the Vertex calculated tax
     * */
    public String headerLevelNoUserEnteredTaxWithMiscCosts() {
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();

        fillDefaultInvoice(invoiceNumber, "gettysburg");

        enterShippingCost("5");
        enterHandlingCost("5");
        enterMiscCost("5");

        clickCalculateTax();

        WebElement calculatedTax = wait.waitForElementDisplayed(headerLevelCalculatedTax);
        return calculatedTax.getText();
    }

    /**
     * adds an invoice number to an invoice
     * */
    public void addInvoiceNumber(){
        String invoiceNumber = CoupaUtils.randomInvoiceNumber();

        WebElement invoiceField = wait.waitForElementDisplayed(coupaInvoice);
        text.enterText(invoiceField,invoiceNumber);
    }
    /**
     * alternate method incase we need to keep track of an invoice number
     *
     * @param invoiceNumber
     * */
    public void addInvoiceNumber(String invoiceNumber){
        WebElement invoiceField = wait.waitForElementDisplayed(coupaInvoice);
        text.enterText(invoiceField,invoiceNumber);
    }

    /**
     * Edits the price of an invoice item
     *
     * @param price the new price for this item
     * */
    public void editItemPrice(String price){
        WebElement priceInput = wait.waitForElementDisplayed(editInvoicePrice);
        click.clickElementCarefully(priceInput);
        text.enterText(priceInput,price);
    }

    /**
     * Edits the quantity of an item ordered in an
     * invoice
     *
     * @param quantity the new quantity for this item
     * */
    public void editQuantity(String quantity){
        WebElement quantityBox = wait.waitForElementDisplayed(quantityClass);
        text.enterText(quantityBox,quantity);
    }

    /**
     * Gets the confirmation message after submitting an invoice
     * and returning to the invoice screen
     *
     * @return the text that appears when an invoice is sent for approval
     * */
    public String getConfirmationText(){
        WebElement confirmation = wait.waitForElementDisplayed(confirmationText);
        return confirmation.getText();
    }
}