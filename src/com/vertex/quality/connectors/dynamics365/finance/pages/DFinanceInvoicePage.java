package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.dialogs.DFinanceTransactionSalesTaxDialog;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.testng.Assert.assertTrue;

public class DFinanceInvoicePage extends DFinanceBasePage
{
	public DFinanceInvoicePage( WebDriver driver ){super(driver);}

	protected By JOURNAL_NUMBER = By.xpath("//input[@aria-label='Journal batch number']");
	protected By NAME = By.xpath("//div[contains(@class,'dyn-activeRowCell')]//input[@aria-label='Name']");
	protected By DESCRIPTION = By.xpath("//div[contains(@class,'dyn-activeRowCell')]//input[@aria-label='Description']");
	protected By NEW_BUTTON = By.name("SystemDefinedNewButton");
	protected By NEW_LINE_BUTTON = By.xpath("//button[@name='Add']");
	protected By DELETE_LINE_BUTTON = By.xpath("//button[@name='Delete']");
	protected By ITEM_SALES_TAX_GROUP = By.name("LineSalesTax_TaxItemGroup");
	protected By ACCOUNT = By.xpath("//div[@data-dyn-controlname='LedgerJournalTrans_AccountNum']//input");
	protected By INVOICE_DATE = By.name("DocumentDateGrid");
	protected By LINE_DESCRIPTION = By.xpath("//input[contains(@id,'LedgerJournalTrans_Txt')]");
	protected By CREDIT = By.xpath("//input[contains(@id,'LedgerJournalTrans_AmountCurCredit')]");
	protected By SALE_TAX_GRP = By.xpath("//input[contains(@id,'LedgerJournalTrans_TaxGroup')]");
	protected By OFFSET_ACCOUNT = By.xpath("//div[@data-dyn-controlname='LedgerJournalTrans_OffsetAccount']//input");
	protected By OFFSET_ACCOUNT_INVOICE_APPROVAL = By.xpath("//div[@data-dyn-controlname='LedgerJournalTrans_AccountNum']//input");
	protected By SALE_TAX_GRP_INVOICE_REGISTER = By.xpath("//input[contains(@id,'LedgerJournalTrans_TaxGroup')]");
	protected By ITEM_TAX_GRP = By.xpath("//input[contains(@id,'LedgerJournalTrans_TaxItemGroup')]");
	protected By ITEM_TAX_GRP_INVOICE_REGISTER = By.xpath("//input[contains(@id,'LedgerJournalTrans_TaxItemGroup')]");
	protected By INVOICE_NO = By.xpath("//input[@aria-label='Invoice']");
	protected By SAVE_BUTTON = By.xpath("//form[@data-dyn-form-name='LedgerJournalTransVendInvoice']//button[@name='SystemDefinedSaveButton']");
	protected By ACTUAL_TAX_AMOUNT = By.xpath("//input[@name='correctedTaxAmount']");
	protected By ACTUAL_SALES_TAX_AMOUNT_ADJUSTMENT = By.xpath("(//input[@aria-label='Actual sales tax amount'])[2]");
	protected By ACTUAL_TAX_AMOUNT_APPROVAL = By.xpath("//div[contains(@id,'taxAmountReverse')]//input");
	protected By CLICK_CALENDER = By.xpath("//div[@data-dyn-controlname=\"DocumentDateGrid\"]//div[contains(@class,'dyn-date-picker')]");
	protected By SELECT_TODAY = By.xpath("//button[@aria-label='Today']");
	protected By INVOICE_TAB = By.xpath("//li[@data-dyn-controlname='InvoiceTab_header']/span");
	protected By LIST_TAB = By.xpath("//li[@data-dyn-controlname='OverViewTab_header']/span");
	protected By APPROVED_BY = By.name("Approve_Approver_PersonnelNumber");
	protected By INVOICE_REGISTER_APPROVED_BY = By.name("LedgerJournalTrans_Approver_PersonnelNumber");
	protected By VALIDATE_TAB = By.xpath("//button[@name='buttonCheckJournal']");
	protected By VALIDATE_INVOICE_REGISTER = By.xpath("//button[@name='CheckJournal']");
	protected By FIND_VOUCHERS_TAB = By.name("buttonFetchVouchers");
	protected By FILTER = By.cssSelector("input[name*='Filter']");
	protected By VOUCHER_ID = By.name("SourceQuickFilter_Input");
	protected By VOUCHER_ID_DROPDOWN = By.xpath("//div//li[contains(@class, 'flyout-menuItem')]");
	protected By FIND_VOUCHER_SELECT_BUTTON = By.xpath("//span[text()='Select']");
	protected By OK_BTN = By.xpath("//button[@data-dyn-controlname='OkButton']");

	protected By POST = By.xpath("//button[@data-dyn-controlname='PostJournal']");
	protected By COMPLETE_MSG = By.xpath("//span[@title='Operation completed'][@class=\"messageBar-message\"]");
	protected By VOUCHER_NO = By.xpath("//input[contains(@id,'LedgerJournalTrans_Voucher')]");
	protected By XML_PAGE = By.xpath("//h1[contains(text(), 'Vertex XML Inquiry')]");
	protected By ACCRUAL = By.xpath("(//input[@value='Accrual request']/ancestor::div[@class=\"fixedDataTableCellGroupLayout_cellGroupWrapper\"])[1]");
	protected By AP_INVOICE = By.xpath("//input[@value='APInvoice']");
	protected By EDIT_BUTTON = By.xpath("//button[@data-dyn-controlname='SystemDefinedViewEditButton']");
	protected By AMOUNT_TAX_TITLE = By.xpath("//label[@data-dyn-controlname='SalesTax_LedgerJournalInclTax']//span[@class='toggle-value']");
	protected By AMOUNT_TAX_TOGGLE = By.xpath("//label[@data-dyn-controlname='SalesTax_LedgerJournalInclTax']//span[@class='toggle-box']");
	protected By AMOUNT_SAVE_BUTTON = By.name("SystemDefinedSaveButton");
	protected By SELECT_ROW = By.xpath("//div[@class='public_fixedDataTableCell_cellContent']//div[contains(@class, 'hoverMarkingColumn')]");
	protected By DELETE_BUTTON = By.name("SystemDefinedDeleteButton");
	protected By YES_BUTTON = By.name("Yes");
	protected By LOG_RESPONSE = By.name("XMLContent");
	private final String DOC_ID = "//div[@data-dyn-rowid='1']//input[contains(@title,'%s')]";
	protected By PURCHASE_ORDER_INVOICE_REGISTER = By.xpath("//input[contains(@id,'PurchIdRange')]");
	protected By APPLY = By.name("LedgerJournalTrans_Voucher_ApplyFilters");
	protected By PURCHASE_ORDER = By.xpath("//span[text()='Purchase order']");
	protected By DELIVERY_ADDRESS_INPUT= By.name("LedgerJournalTrans_DeliveryPostalAddress_Location_Description");
	protected By GENERAL_TAB = By.xpath("//span[@text()='General']");
	protected By JOURNAL_NAME = By.name("LedgerJournalTable_JournalName");
	protected By PITTSBURGH_ADDRESS = By.xpath("//input[@title='Pittsburgh PA Address']/../..");
	protected By DELIVERY_ADDRESS = By.name("LogisticsLocationSelectLine");
	protected By ACCOUNT_TYPE = By.xpath("//input[contains(@id, 'LedgerJournalTrans_AccountType')]/following-sibling::div");
	protected By SHOW_POSTED_OR_NOT_POSTED = By.name("AllOpenPostedField");
	protected By JOURNAL_BATCH_NUMBER = By.xpath("//div[text()='Journal batch number']");
	protected By JOURNAL_BATCH_NUMBER_INPUT = By.xpath("//input[contains(@name, 'LedgerJournalTable')]");
	protected By POSTED_JOURNAL_ERROR_MESSAGE = By.xpath("//span[text()='Journal has already been posted and, consequently, is not open']");

	Actions action = new Actions(driver);

	/**
	 * Enter "AP Account"
	 *
	 * @param apAccount
	 */
	public void setInvoiceName( String apAccount )
	{
		wait.waitForElementEnabled(NAME);
		text.enterText(NAME, apAccount);
		text.pressTab(NAME);
		text.pressTab(DESCRIPTION);
	}

	/**
	 * Click on "Ok" button to create quotation
	 *
	 * @return project quotation page
	 */
	public void clickJournal( )
	{
		wait.waitForElementDisplayed(JOURNAL_NUMBER);
		click.clickElement(JOURNAL_NUMBER);
		text.pressEnter(JOURNAL_NUMBER);
		waitForPageLoad();
	}


	/**
	 * Click on NEW button to create invoice journal
	 */
	public void clickNewButton( )
	{
		wait.waitForElementDisplayed(NEW_BUTTON);
		click.clickElement(NEW_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Adds a new invoice journal line
	 */
	public void clickNewLineButton(){
		wait.waitForElementDisplayed(NEW_LINE_BUTTON);
		click.clickElementCarefully(NEW_LINE_BUTTON);
	}

	/**
	 * Adds a new invoice journal line
	 */
	public void clickDeleteLineButton(){
		waitForPageLoad();
		wait.waitForElementDisplayed(DELETE_LINE_BUTTON);
		click.clickElementCarefully(DELETE_LINE_BUTTON);
	}


	/**
	 * Enters the actual sales tax amount value
	 * @param actualSalesTaxAmount
	 */
	public void enterActualSalesTaxAmountAdjustmentTab(String actualSalesTaxAmount){
		waitForPageLoad();
		wait.waitForElementDisplayed(ACTUAL_SALES_TAX_AMOUNT_ADJUSTMENT);
		WebElement taxAmountField = wait.waitForElementPresent(ACTUAL_SALES_TAX_AMOUNT_ADJUSTMENT);
		action.moveToElement(taxAmountField).click().perform();
		text.setTextFieldCarefully(ACTUAL_SALES_TAX_AMOUNT_ADJUSTMENT, actualSalesTaxAmount, false);
		taxAmountField.sendKeys(Keys.TAB);
	}

	/**
	 * Set account
	 * @param account
	 */
	public void setAccount(String account)
	{
		wait.waitForElementDisplayed(ACCOUNT);
		text.setTextFieldCarefully(ACCOUNT, account,false);
		text.pressTab(ACCOUNT);
		waitForPageLoad();
	}

	/**
	 * Set invoice date
	 */
	public void setInvoiceDate()
	{
		waitForPageLoad();
		driver.manage().window().maximize();
		click.clickElementIgnoreExceptionAndRetry(CLICK_CALENDER);
		waitForPageLoad();
		click.clickElementIgnoreExceptionAndRetry(SELECT_TODAY);
		driver.manage().window().setSize(new Dimension(1024,768));
		waitForPageLoad();
	}

	/**
	 * Set Description
	 * @param desc
	 */
	public void setDescription(String desc)
	{
		wait.waitForElementDisplayed(LINE_DESCRIPTION);
		click.clickElementCarefully(LINE_DESCRIPTION);
		text.enterText(LINE_DESCRIPTION, desc);
		text.pressTab(LINE_DESCRIPTION);
		waitForPageLoad();
	}

	/**
	 * Set credit amount
	 * @param inputType
	 * @param credit
	 */
	public void setCreditOrDebit(String inputType, String credit)
	{
		waitForPageLoad();
		Actions action = new Actions(driver);
		WebElement creditOrDebit = wait.waitForElementPresent(By.xpath("//input[contains(@id,'LedgerJournalTrans_AmountCur"+inputType+"')]"));
		action.moveToElement(creditOrDebit).click(creditOrDebit).perform();
		text.enterText(creditOrDebit, credit);
		text.pressTab(creditOrDebit);
		waitForPageLoad();
	}

	/**
	 * Set Offset Account
	 * @param offsetAccount
	 */
	public void setOffsetAccount(String offsetAccount)
	{
		Actions action = new Actions(driver);
		WebElement offset_Ele = wait.waitForElementPresent(OFFSET_ACCOUNT);
		action.moveToElement(offset_Ele).click(offset_Ele).perform();
		text.setTextFieldCarefully(offset_Ele, offsetAccount,false);
		waitForPageLoad();
	}
	/**
	 * Get Offset Account
	 * @return the offset account
	 */
	public String getOffsetAccount()
	{
		WebElement offset_Ele = wait.waitForElementPresent(OFFSET_ACCOUNT);
		String offsetAccount = offset_Ele.getAttribute("value");
		return offsetAccount;
	}

	/**
	 * Set Account Num for Invoice Approval
	 * @param offsetAccount
	 */
	public void setAccountNum(String offsetAccount)
	{
		wait.waitForElementDisplayed(OFFSET_ACCOUNT_INVOICE_APPROVAL);
		text.setTextFieldCarefully(OFFSET_ACCOUNT_INVOICE_APPROVAL,offsetAccount,false);
		waitForPageLoad();
	}

	/**
	 * Set sale tax group
	 * @param saleTax
	 */
	public void setSaleTax(String saleTax)
	{
		WebElement salesTaxGroupEle = wait.waitForElementPresent(SALE_TAX_GRP);
		action.moveToElement(salesTaxGroupEle).click(salesTaxGroupEle).perform();
		text.setTextFieldCarefully(SALE_TAX_GRP,saleTax,false);
		waitForPageLoad();
	}

	/**
	 * Get sale tax group
	 * @return sale tax group
	 */
	public String getSaleTaxGroup(){
		WebElement salesTaxGroupEle = wait.waitForElementPresent(SALE_TAX_GRP);
		String saleTaxGroup = salesTaxGroupEle.getAttribute("value");
		return saleTaxGroup;
	}

	/**
	 * Set sale tax group
	 * @param saleTax
	 */
	public void setSaleTaxInvoiceRegister(String saleTax)
	{
		WebElement salesEle = wait.waitForElementPresent(SALE_TAX_GRP_INVOICE_REGISTER);
		action.moveToElement(salesEle).click(salesEle).perform();
		text.setTextFieldCarefully(SALE_TAX_GRP_INVOICE_REGISTER, saleTax,false);
		waitForPageLoad();
	}

	/**
	 * Set item tax group
	 * @param itemTax
	 */
	public void setItemTax(String itemTax)
	{
		waitForPageLoad();
		Actions action = new Actions(driver);
		WebElement itemTaxGroup_Ele = wait.waitForElementPresent(ITEM_TAX_GRP);
		action.moveToElement(itemTaxGroup_Ele).click(itemTaxGroup_Ele).perform();
		text.setTextFieldCarefully(itemTaxGroup_Ele, itemTax,false);
		waitForPageLoad();
	}

	/**
	 * Get sale tax group
	 * @return sale tax group
	 */
	public String getItemSalesTaxGroup(){
		WebElement itemTaxGroup_Ele = wait.waitForElementPresent(ITEM_TAX_GRP);
		String saleTaxGroup = itemTaxGroup_Ele.getAttribute("value");
		return saleTaxGroup;
	}

	/**
	 * Set item tax group
	 * @param itemTax
	 */
	public void setItemTaxInvoiceRegister(String itemTax)
	{
		click.clickElement(ITEM_TAX_GRP_INVOICE_REGISTER);
		text.selectAllAndInputText(ITEM_TAX_GRP_INVOICE_REGISTER, itemTax);
		text.pressTab(ITEM_TAX_GRP_INVOICE_REGISTER);

	}

	/**
	 * Change actual tax for accrual
	 * @param actualTax
	 */
	public void changeActualTax(String actualTax)
	{
		wait.waitForElementDisplayed(ACTUAL_TAX_AMOUNT);
		click.clickElementCarefully(ACTUAL_TAX_AMOUNT);
		waitForPageLoad();
		text.clearText(ACTUAL_TAX_AMOUNT);
		text.enterText(ACTUAL_TAX_AMOUNT, actualTax);
		click.clickElementCarefully(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Set invoice number
	 */
	public void setInvoiceNo()
	{
		wait.waitForElementDisplayed(INVOICE_NO);
		Random objGenerator = new Random();
		int randomNumber = objGenerator.nextInt(1000000);
		text.enterText(INVOICE_NO, "INV" + randomNumber);
		waitForPageLoad();
	}

	/**
	 * Set invoice number
	 * @param invoiceNo
	 */
	public void setInvoiceNo(String invoiceNo)
	{
		wait.waitForElementDisplayed(INVOICE_NO);
		text.enterText(INVOICE_NO, invoiceNo);
		waitForPageLoad();
	}

	/**
	 * Get Invoice Number
	 */
	public String getInvoiceNo(){
		wait.waitForElementDisplayed(INVOICE_NO);
		WebElement invoiceNo = driver.findElement(INVOICE_NO);
		String invoiceNumber = invoiceNo.getAttribute("value");
		return invoiceNumber;
	}

	/**
	 * Get the voucher no and print
	 */
	public String voucherNo( )
	{
		wait.waitForElementPresent(VOUCHER_NO);
		WebElement titleNo = driver.findElement(VOUCHER_NO);
		String voucherNO = titleNo.getAttribute("value");
		return voucherNO;
	}

	/**
	 * Get journal number
	 *
	 * @return journal number
	 */
	public String getJournalNumber () {
		WebElement journalLoc = wait.waitForElementPresent(JOURNAL_NUMBER);
		String journalNo = journalLoc.getAttribute("value").trim();
		return journalNo;
	}

	/**
	 * Clicks the Invoice tab and selects who approved the Invoice
	 * @param personnelNum
	 */
	public void invoiceApprovedBy(String personnelNum){
		click.clickElementCarefully(INVOICE_TAB);
		wait.waitForElementDisplayed(APPROVED_BY);
		click.clickElementCarefully(APPROVED_BY);
		text.enterText(APPROVED_BY, personnelNum);
		text.pressTab(APPROVED_BY);
		click.clickElementCarefully(LIST_TAB);
	}

	/**
	 * Clicks the approved by option on the Invoice register page
	 * @param personnelNum
	 */
	public void invoiceApprovedByInvoiceRegister(String personnelNum){
		wait.waitForElementDisplayed(INVOICE_REGISTER_APPROVED_BY);
		click.clickElementCarefully(INVOICE_REGISTER_APPROVED_BY);
		text.enterText(INVOICE_REGISTER_APPROVED_BY, personnelNum);
		text.pressTab(INVOICE_REGISTER_APPROVED_BY);
	}

	/**
	 * Clicks the Validate Tab in the Invoice register page
	 */
	public void clickOnValidateTab(){
		wait.waitForElementEnabled(VALIDATE_TAB);
		click.javascriptClick(VALIDATE_TAB);
		waitForPageLoad();
	}

	/**
	 * Clicks on Validate in the dropdown navigation
	 */
	public void validateInvoiceRegister(){
		wait.waitForElementDisplayed(VALIDATE_INVOICE_REGISTER);
		click.clickElementCarefully(VALIDATE_INVOICE_REGISTER);
		WebElement validate = wait.waitForElementDisplayed(VALIDATE_TAB);
		hover.hoverOverElement(validate);
		wait.waitForElementDisplayed(COMPLETE_MSG,60);
	}

	/**
	 * Clicks the find vouchers tab
	 */
	public void clickFindVouchers(){
		wait.waitForElementDisplayed(FIND_VOUCHERS_TAB);
		click.clickElementCarefully(FIND_VOUCHERS_TAB);
	}

	/**
	 * Gets the recent Voucher by its voucher ID
	 * @param voucherId
	 */
	public void getVoucherId(String voucherId){

		By voucherCheckBox = By.xpath("//input[contains(@title, "+voucherId+")]//ancestor::div//input[contains(@id,'SourceList_AccountNum')]");
		click.clickElementCarefully(VOUCHER_ID);
		WebElement filterEle = wait.waitForElementEnabled(FILTER);
		text.enterText(filterEle,voucherId);
		List <WebElement> listItems = wait.waitForAllElementsDisplayed(VOUCHER_ID_DROPDOWN);
		click.clickElementCarefully(listItems.get(2));

		wait.waitForElementPresent(voucherCheckBox);
		click.javascriptClick(voucherCheckBox);
	}

	/**
	 * Clicks the select link
	 */
	public void clickSelectBtn(){
		wait.waitForElementDisplayed(FIND_VOUCHER_SELECT_BUTTON);
		click.clickElementCarefully(FIND_VOUCHER_SELECT_BUTTON);
	}

	/**
	 * Clicks on the Ok button
	 */
	public void clickOkBtn(){
		wait.waitForElementDisplayed(OK_BTN);
		click.javascriptClick(OK_BTN);
	}

	/**
	 * click save button
	 */
	public void clickSaveButton( )
	{
		wait.waitForElementDisplayed(SAVE_BUTTON);
		click.clickElementCarefully(SAVE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * POST the invoice
	 */
	public void postInvoice( )
	{
		waitForPageLoad();
		click.clickElementCarefully(POST);
		jsWaiter.sleep(30000);
		wait.waitForElementDisplayed(COMPLETE_MSG);
		waitForPageLoad();
	}

	/**
	 * Find REQUEST TYPE and verify accrual displayed
	 */
	public boolean requestType( )
	{
		wait.waitForElementDisplayed(ACCRUAL,5);
		return element.isElementDisplayed(ACCRUAL);
	}

	/**
	 * Click on AP Invoice and edit
	 */
	public void clickAPInvoice( )
	{
		wait.waitForElementDisplayed(AP_INVOICE);
		click.clickElement(AP_INVOICE);
		wait.waitForElementEnabled(EDIT_BUTTON);
		click.clickElementCarefully(EDIT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * toggle Amounts include sales tax button to yes or no
	 * @param togglePosition
	 */

	public void toggleAmountIncludeTaxButton(boolean togglePosition)
	{
		WebElement tog = driver.findElement(AMOUNT_TAX_TITLE);
		WebElement list = driver.findElement(AMOUNT_TAX_TOGGLE);
		tog.getAttribute("title");
		if ( (togglePosition && tog
			.getAttribute("title")
			.equals("No")) || (!togglePosition && tog
			.getAttribute("title")
			.equals("Yes")) )
		{
			list.click();
		}
		click.clickElement(AMOUNT_SAVE_BUTTON);
	}

	/**
	 * find the correct response from the xml and click it
	 * @param findId
	 */

	public void selectResponseXML(String findId )
	{
		WebElement doc = driver.findElement(By.xpath(String.format(DOC_ID, findId)));
		doc.click();

	}

	/**
	 * find the value in the selected log
	 */
	public String getLogResponseValue( )
	{
		if(!element.isElementDisplayed(LOG_RESPONSE))
			driver.manage().window().maximize();
		wait.waitForElementDisplayed(LOG_RESPONSE);
		click.clickElementCarefully(LOG_RESPONSE);
		String responseDetails = driver.findElement(By.name("XMLContent")).getAttribute("value");
		return responseDetails;
	}

	/**
	 * delete the newly created invoice
	 */
	public void deleteInvoice( )
	{
		wait.waitForElementDisplayed(SELECT_ROW);
		click.clickElementCarefully(SELECT_ROW);
		click.clickElementCarefully(DELETE_BUTTON);
		wait.waitForElementEnabled(YES_BUTTON);
		click.clickElementCarefully(YES_BUTTON);
	}

	/**
	 * Retrieves the actual sales tax amount from the invoice
	 * @param taxType
	 * @returns the sales tax amount
	 */
	public String getActualOrCalculatedSalesTaxAmountInvoice(String taxType){
		WebElement selectedTaxType = wait.waitForElementDisplayed(By.xpath("//input[@name='"+taxType+"']"));
		String actualSalesTaxAmount = attribute.getElementAttribute(selectedTaxType, "value");

		return actualSalesTaxAmount;
	}

	/**
	 * Retrieves the actual sales tax amount from the invoice approval
	 * @returns the sales tax amount
	 */
	public String getActualSalesTaxAmountInvoiceApproval(){
		WebElement tax= wait.waitForElementPresent(ACTUAL_TAX_AMOUNT_APPROVAL);
		action.moveToElement(tax).click(tax).perform();
		String actualSalesTaxAmount = attribute.getElementAttribute(ACTUAL_TAX_AMOUNT_APPROVAL, "title");
		return actualSalesTaxAmount;
	}

	/**
	 * Enters the Purchase Order Number into the Purchase Order field
	 * @param purchaseOrderNumber - the Purchase Order Number of the order created
	 */
	public void setPurchaseOrderNumber(String purchaseOrderNumber){
		wait.waitForElementDisplayed(PURCHASE_ORDER_INVOICE_REGISTER);
		text.clickElementAndEnterText(PURCHASE_ORDER_INVOICE_REGISTER, purchaseOrderNumber);
	}

	/**
	 * Clicks the Purchase Order button
	 */
	public void clickPurchaseOrder(){
		wait.waitForElementDisplayed(PURCHASE_ORDER);
		click.clickElementCarefully(PURCHASE_ORDER);
	}

	/**
	 * Clicks the address dropdown
	 * @param siteSelection
	 */
	public void clickDeliveryAddressAndSelectAddress(String siteSelection){

		WebElement selectLocation = wait.waitForElementDisplayed(DELIVERY_ADDRESS);
		click.clickElementCarefully(selectLocation);

		By siteSelectionOption = By.xpath("//input[@title="+siteSelection+"]/../..");
		By siteSelectionOptionSelected = By.xpath("//input[contains(@title," + siteSelection + "'Click to follow link')]/../..");

		if(element.isElementDisplayed(siteSelectionOption)) {
			click.clickElementCarefully(siteSelectionOption);
		} else if (element.isElementDisplayed(siteSelectionOptionSelected)) {
			click.clickElementCarefully(siteSelectionOptionSelected);
		}
	}

	/**
	 * Sets the item and sales tax for invoice approval
	 * @param salesTaxGroup
	 * @param itemSalesTax
	 */
	public void setSalesAndItemTaxGroupInvoiceApproval(String salesTaxGroup, String itemSalesTax){
		List <WebElement> salesTaxGroups = wait.waitForAllElementsPresent(SALE_TAX_GRP);
		action.moveToElement(salesTaxGroups.get(0)).click(salesTaxGroups.get(0)).sendKeys(salesTaxGroup).perform();
		text.setTextFieldCarefully(salesTaxGroups.get(0),salesTaxGroup,false);
		text.pressTab(salesTaxGroups.get(0));

		List <WebElement> itemTaxGroups = wait.waitForAllElementsDisplayed(ITEM_TAX_GRP_INVOICE_REGISTER);
		text.setTextFieldCarefully(itemTaxGroups.get(0),itemSalesTax,false);
	}

	/**
	 * Selects the account type
	 * @param accountType
	 */
	public void selectAccountType(String accountType){
		WebElement accountTypeEl = wait.waitForElementPresent(ACCOUNT_TYPE);
		action.moveToElement(accountTypeEl).click().perform();

		By accountTypeLoc = By.xpath(String.format("//li[text()='%s']", accountType));

		wait.waitForElementDisplayed(accountTypeLoc);
		click.clickElementCarefully(accountTypeLoc);
	}

	/**
	 * Selects to either show, posted, or not posted invoices
	 * @param invoiceType
	 */
	public void selectInvoiceType(String invoiceType){
		wait.waitForElementDisplayed(SHOW_POSTED_OR_NOT_POSTED);
		click.clickElementCarefully(SHOW_POSTED_OR_NOT_POSTED);

		WebElement clickInvoiceType = wait.waitForElementDisplayed(By.xpath("//li[text()='"+invoiceType+"']"));
		click.clickElementCarefully(clickInvoiceType);
	}

	/**
	 * Filters by invoice number
	 * @param invoiceNumber
	 */
	public void filterInvoiceFromDisplayedList(String invoiceNumber){
		wait.waitForElementPresent(JOURNAL_BATCH_NUMBER);
		click.clickElementCarefully(JOURNAL_BATCH_NUMBER);

		wait.waitForElementDisplayed(JOURNAL_BATCH_NUMBER_INPUT);
		text.setTextFieldCarefully(JOURNAL_BATCH_NUMBER_INPUT, invoiceNumber, false);
		text.pressEnter(JOURNAL_BATCH_NUMBER_INPUT);
	}

	/**
	 * Selects the invoice displayed
	 * @param invoiceNumber
	 */
	public void selectInvoiceNumber(String invoiceNumber){
		//WebElement clickInvoiceNumber = wait.waitForElementPresent(By.xpath("//input[@value='"+invoiceNumber+"']"));
		WebElement element2 = element.getWebElement(By.xpath("//input[@value='"+invoiceNumber+"']"));
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		element2.sendKeys(keysPressed);
	}

	/**
	 * Verifies that the Invoice is open
	 * @param invoiceType
	 */
	public String verifyInvoiceIsOpen(String invoiceType){
		jsWaiter.sleep(5000);
		String invoiceTypeDisplayed = attribute.getElementAttribute(By.xpath("//span[text()='"+invoiceType+"']"), "textContent");
		return invoiceTypeDisplayed;
	}

	/**
	 * Verifies if the error message for posted invoice is displayed
	 */
	public boolean verifyPostedErrorMessage(){
		jsWaiter.sleep(5000);
		boolean errorMessageDisplayed = false;

		if(element.isElementDisplayed(POSTED_JOURNAL_ERROR_MESSAGE)){
			errorMessageDisplayed = true;
		}

		return errorMessageDisplayed;
	}
}
