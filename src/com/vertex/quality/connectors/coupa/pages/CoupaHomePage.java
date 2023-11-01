package com.vertex.quality.connectors.coupa.pages;

import com.vertex.quality.common.pages.VertexPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
/**
 * This class is used for manipulating the ui on the landing page of
 * the Coupa website
 * */
public class CoupaHomePage extends VertexPage {

    protected By invoices = By.xpath("//a[contains(text(),'Invoices')]");
    protected By setup = By.xpath("//a[contains(text(),'Setup')]");
    protected By vertexCoupaCallOut = By.xpath("//a/span[contains(text(),'Vertex Coupa')]");
    protected By changeEnpointSaveButton = By.xpath("//button/span[contains(text(),'Save')]");
    protected By callOutEditButton = By.xpath("//a[@class=' rollover button ']");
    protected By endpointDropdown = By.id("call_out_endpoint_id");
    protected By createInvoice = By.xpath("//a[@class='btn invCreateBtn']");
    protected By coupaSupplier = By.xpath("//input[@id='invoice_supplier_search']");
    protected By coupaInvoice = By.xpath("//input[@id='invoice_invoice_number']");
    protected By cartButton = By.xpath("//a[@id='cart']");
    protected By searchBox = By.xpath("//body[1]/div[1]/div[4]/div[1]/div[1]/form[1]/div[2]/section[2]/div[2]/div[1]/div[1]/div[1]/a[1]/img[1]");
    protected By addressTo = By.xpath("//tbody/tr[@id='picker_address_row_131']/td[10]/a[1]/span[1]");
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
    protected By autoCompleteRowClass = By.className("ui-autocomplete-row");
    protected By descriptionClass = By.className("acitem");
    protected By taxRateID = By.id("tax_rate_1");
    protected By calculateTaxButtonID = By.id("calculate_taxes_button");
    protected By taxAmountClass = By.className("tax_amount_engine");
    protected By userTaxAmountID = By.id("invoice_header_total_taxes");
    protected By billlingButtonClass = By.className("account_picker_popup_button");
    protected By regionOptionTwoID = By.id("account_segment_2_lv_id_chosen_results_option_2");
    protected By deptOptionOneID = By.id("account_segment_3_lv_id_chosen_results_option_1");
    protected By submitButtonID = By.id("update_invoice");
    protected By creditNoteOptionsClass = By.className("active-result");
    protected By chooseButtonClass = By.className("choose_dynamic_account");
    protected By creditNoteButtonFinalClass = By.className("adjustment-buttons");
    protected By addLineItemID = By.id("add_invoice_line");
    protected By lineLevelTaxID = By.id("s-invoiceLineLevelTaxation");
    protected By taxRateClass = By.className("tax_rate");
    protected By uploadInvoiceButton = By.className("s-bulk_loader");
    protected By editInvoicePresentationButton = By.xpath("//*[@id=\"invoice_presentation_template_row_1\"]/td[4]/a[1]/img");
    protected By summaryChargeCheckbox = By.id("default_invoice_presentation_template_summary_charges_taxable");
    protected By invoicePresentationSaveButton = By.xpath("//*[@id=\"edit_invoice_presentation_template_1\"]/div[9]/button/span");
    protected By orders = By.xpath("/html/body/div[1]/nav[1]/ul/li[4]/a");
    protected By searchPO = By.id("sf_order_header");
    protected By foundPO = By.xpath("//*[@id=\"order_header_row_4197\"]/td[1]/span/a/span");
    protected By invoiceButton = By.xpath("//*[@id=\"order_body\"]/div[4]/div[3]/a");
    protected By editButton = By.xpath("//*/table[@id=\"invoice_header_table_tag\"]/tbody/tr/td[@class=\" table_actions\"]/a/img[@alt=\"Edit\"]");
    protected By addToCartButton = By.xpath("//*[@id=\"add_requisition_line_link\"]/span");
    protected By cartItemName = By.id("requisition_line_description");
    protected By saveCartItem = By.xpath("//*[@id=\"edit-line\"]/div[3]/div/button/span");
    protected By approvalButton = By.id("submit_for_approval_link");
    protected By requisitionItem = By.xpath("//*/div[@id=\"recent_activity_user_452\"]/div");
    protected By bypassButton = By.id("bypass_approvals_and_order");
    protected By firstPO = By.xpath("//*/table[@id=\"order_header_table_tag\"]/tbody/tr/td/span/a");
    protected By invoiceFromPoButton = By.xpath("//div[@class=\"page_button\"]/a[@class=\"s-invoiceButton rollover button \"]");
    protected By invoiceLinesHeader = By.xpath("//div[@class=\"page_button\"]/a[@class=\"s-invoiceButton rollover button \"]");

    public CoupaHomePage(WebDriver driver) {
        super(driver);
    }


    /**
     * click on invoices button
     */
    public void clickInvoice()
    {
        WebElement clickInvoice = wait.waitForElementDisplayed(invoices);
        click.clickElementCarefully(clickInvoice);
    }

    /**
     * click on invoices button
     */
    public void createNewInvoice()
    {
        WebElement newInvoice = wait.waitForElementDisplayed(createInvoice);
        click.clickElementCarefully(newInvoice);
    }

    /**
     * click on cart button
     */
    public void clickCart()
    {
        WebElement cartItem = wait.waitForElementDisplayed(cartButton);
        click.clickElementCarefully(cartItem);
    }

    /**
     * click on ship to address search box
     */
    public void searchBoxSymbol()
    {
        WebElement searchBoxItem = wait.waitForElementDisplayed(searchBox);
        click.clickElementCarefully(searchBoxItem);
    }

    /**
     * click on setup button
     */
    public void clickSetup()
    {
        WebElement setupButton = wait.waitForElementDisplayed(setup);
        click.clickElementCarefully(setupButton);
    }


    /**
     * click on setup page links
     * must match link text exactly
     */
    public void clickSetupPageLink(String pageLink)
    {
        By linkPath = By.xpath("//a[contains(text(),'" + pageLink + "')]");
        WebElement setupButton = wait.waitForElementDisplayed(linkPath);
        click.clickElementCarefully(setupButton);
    }

    /**
     * click on vertex coupa call out
     */
    public void clickVertexCoupaCallOut()
    {
        WebElement vertexCoupa = wait.waitForElementDisplayed(vertexCoupaCallOut);
        click.clickElementCarefully(vertexCoupa);
    }

    /**
     * click on vertex coupa call out edit button
     */
    public void clickEditCallOutButton()
    {
        WebElement editButton = wait.waitForElementDisplayed(callOutEditButton);
        click.clickElementCarefully(editButton);
    }

    /**
     * changes endpoint dropdown value
     */
    public void selectQAEndPoint(String value)
    {
        WebElement endPointDrop = wait.waitForElementDisplayed(endpointDropdown);
        dropdown.selectDropdownByValue(endPointDrop, value);
    }


    /**
     * clicks save button for the new endpoint value
     * on the edit page
     */
    public void clickEditEndpointSaveButton()
    {
        WebElement saveButton = wait.waitForElementDisplayed(changeEnpointSaveButton);
        click.clickElementCarefully(saveButton);
    }

    /**
     * Checks the table in the Call Outs page for the "Vertex Coupa" page, then confirms that the
     * endpoint value is the one that we expect.
     *
     * @param expectedEndpoint the endpoint dropdown value
     * @return A boolean indicating whether the new value has been updated in the table
     * */
    public Boolean confirmEndpointValueChange(String expectedEndpoint) {
        int NAME = 0;
        int ENDPOINT = 1;
        By tableRows = By.className("coupa_datatable_row");
        List<WebElement> table = driver.findElements(tableRows);
        for (WebElement oneRow : table) {
            List<WebElement> columns = oneRow.findElements(By.tagName("td"));
            if(columns.get(NAME).getText().equals("Vertex Coupa")) {
                if(columns.get(ENDPOINT).getText().equals(expectedEndpoint)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Clicks the "load from file" button on the invoices page
     * */
    public void clickLoadFromFileButton(){
        WebElement uploadButton = wait.waitForElementDisplayed(uploadInvoiceButton);
        click.clickElementCarefully(uploadButton);
    }

    /**
     * Clicks an invoice on the invoice page
     *
     * @param invoiceName the name of the invoice you are looking for
     * */
    public void clickInvoiceName(String invoiceName)
    {
        By linkPath = By.xpath("//td/div/a/span[contains(text(),'"+invoiceName+"')]");
        WebElement setupButton = wait.waitForElementDisplayed(linkPath);
        click.clickElementCarefully(setupButton);
    }

    /**
     * Clicks the edit invoice presentation button on
     * the Invoice presentations page
     * */
    public void clickInvoicePresentationEditButton()
    {
        WebElement editButton = wait.waitForElementDisplayed(editInvoicePresentationButton);
        click.clickElementCarefully(editButton);
    }

    /**
     * Clicks summary charges button on the invoice presentation page
     * if it is currently enabled
     * */
    public void uncheckSummaryChargesBox(){
        WebElement invoiceSave = wait.waitForElementDisplayed(invoicePresentationSaveButton);
        WebElement checkbox = wait.waitForElementDisplayed(summaryChargeCheckbox);
        String checkBoxValue = checkbox.getAttribute("checked");
        if(checkBoxValue == "true"){
            click.clickElementCarefully(summaryChargeCheckbox);
        }
        click.clickElementCarefully(invoiceSave);

    }

    /**
     * Clicks summary charges button on the invoice presentation page
     * if it is currently disabled
     * */
    public void checkSummaryChargesBox(){
        WebElement invoiceSave = wait.waitForElementDisplayed(invoicePresentationSaveButton);
        WebElement checkbox = wait.waitForElementDisplayed(summaryChargeCheckbox);
        String checkBoxValue = checkbox.getAttribute("checked");
        if(checkBoxValue == null){
            click.clickElementCarefully(summaryChargeCheckbox);
        }
        click.clickElementCarefully(invoiceSave);

    }

    /**
     * Navigates to the setup page clicks the 'Invoice presentation link'
     * and goes to the edit section
     * */
    public void goToInvoicePresentationEdit(){
        clickSetup();
        clickSetupPageLink("Invoice Presentations");
        clickInvoicePresentationEditButton();
    }

    /**
     * Clicks the orders tab
     * */
    public void clickOrders(){
        WebElement ordersTab = wait.waitForElementDisplayed(orders);
        click.clickElementCarefully(ordersTab);
    }

    /**
     * Navigates to the PO page, selects a PO then
     * selects the invoice button allowing us to fill out
     * an invoice for the selected PO
     * */
    public void navigateToInvoiceFromPO(){

        WebElement searchBox = wait.waitForElementDisplayed(searchPO);
        //4195 is the PO number of a manually created PO for the purpose of this test
        searchBox.sendKeys("4197");
        searchBox.sendKeys(Keys.ENTER);
        //need time for the result to pop up or it attempts to click something that isn't there
        jsWaiter.sleep(2000);
        WebElement purchaseOrder = wait.waitForElementDisplayed(foundPO);
        click.clickElementCarefully(purchaseOrder);

        WebElement invoiceBtn = wait.waitForElementDisplayed(invoiceButton);
        click.clickElementCarefully(invoiceBtn);

    }

    /**
     * Click to edit an invoice that has already
     * been created
     * */
    public void pressEditInvoice(){
        WebElement edit = wait.waitForElementDisplayed(editButton);
        click.clickElementCarefully(edit);
    }

    /**
     * Adds an item to the current users cart
     * */
    public void addToCart(){
        WebElement add = wait.waitForElementDisplayed(addToCartButton);
        click.clickElementCarefully(add);

        WebElement itemName = wait.waitForElementDisplayed(cartItemName);
        click.clickElementCarefully(itemName);
        text.enterText(itemName,"laptop0");
        jsWaiter.sleep(2000);
        itemName.sendKeys(Keys.DOWN);
        itemName.sendKeys(Keys.ENTER);
        jsWaiter.sleep(2000);

        WebElement save = wait.waitForElementDisplayed(saveCartItem);
        click.clickElementCarefully(save);
    }

    /**
     * Sends the current users cart for approval
     * */
    public void approveCart(){
        WebElement sendForApproval = wait.waitForElementDisplayed(approvalButton);
        click.clickElementCarefully(sendForApproval);
    }

    /**
     * navigates to the newest req item and bypasses approval
     * */
    public void confirmRequistionItem(){
        WebElement reqItem = wait.waitForElementDisplayed(requisitionItem);
        click.clickElementCarefully(reqItem);
        WebElement bypass = wait.waitForElementDisplayed(bypassButton);
        click.clickElementCarefully(bypass);
    }

    /**
     * Clicks the newest PO created for invoice testing
     * */
    public void clickFirstPurchaseOrder(){
        WebElement purchaseOrder = wait.waitForElementDisplayed(firstPO);
        click.clickElementCarefully(purchaseOrder);
    }

    /**
     * Clicks the "invoice" button to create an invoice from a PO
     * */
    public void clickInvoiceFromPOButton(){
        WebElement invoiceButton = wait.waitForElementDisplayed(invoiceFromPoButton);
        click.clickElementCarefully(invoiceButton);
    }

    /**
     * Scrolls down to the lines section of an invoice
     * to prevent tests from failing due to view issues
     * */
    public void scrollToLines(){
        WebElement lines = wait.waitForElementDisplayed(invoiceLinesHeader);
        scroll.scrollElementIntoView(lines);
    }

}
