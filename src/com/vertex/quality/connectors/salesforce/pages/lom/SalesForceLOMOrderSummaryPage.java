package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SalesForceLOMOrderSummaryPage extends SalesForceBasePage
{
	protected By FIRST_ORDER_RECORD = By.xpath(".//div[contains(@class, 'slds-split-view')]//div[1]/div/div/ul/li[1]/a[contains(@class, 'splitViewListRecordLink')]");
	protected By DETAILS_TAB = By.xpath(
		".//h1/div[text()='Order Summary']/../../../../../../../../../.././/div/div/div/div/ul/li/a/span[text()='Details']");
	protected By RELATED_TAB = By.xpath(
		".//h1/div[text()='Order Summary']/../../../../../../../../../.././/div/div/div/div/ul/li/a/span[text()='Related']");
	protected By RECENTLY_VIEWED = By.xpath(
		".//div/div/div/div[2]/div/div[1]/div[2]/div[1]/span[@title='Recently Viewed']");

	protected By MORE_ACTIONS_DROPDOWN = By.xpath(
		".//h1/div[text()='Order Summary']/../../../../../../../../../../..//*[contains(@title,'actions')]");
	protected By EDIT_ACTION = By.xpath(".//div/ul/li/a[@title='Edit']");

	protected By CASE_STATUS_DROPDOWN = By.xpath(".//span[text()='Status']/../../div/div/div/div/a");
	protected By CREATE_FULFILMENT_ORDER = By.xpath(".//div/ul/li/a[@title='Create Fulfillment Order']");
	protected By SET_FULFILLED = By.xpath(".//div/ul/li/a[@title='Fulfilled']");

	protected By SAVE_BUTTON = By.xpath(".//div/div/div[2]/div/div/div[2]/button/span[text()='Save']");

	protected By FULFILLMENT_ORDER = By.xpath(
		".//div/div/table/thead/tr/th[@title='Fulfillment Order Number']/../../..//div/a");
	protected By INVOICE = By.xpath(
			".//div/div/table/thead/tr/th[@title='Document Number']/../../..//div/a");

	protected By CANCEL_ITEM_FLOW = By.xpath(".//ol/li/div/.//*[text()='Cancel Item Flow LOM']");
	protected By RETURN_ITEM_FLOW = By.xpath(".//ol/li/div/.//*[text()='LOM Return Item Flow']");
	protected String ITEM_SELECT_XPATH
		= ".//span[text() = 'LOM Return Item Flow']/../../../../../..//table/tbody/tr/th/lightning-primitive-cell-factory/span/div/*[text()='%s']/../../../../.././/input/../label/span[contains(@class, 'slds-checkbox')]";
	protected By ITEM_SELECT_ALL_CHECKBOX =
			By.xpath(".//span[text() = 'LOM Return Item Flow']/../../../../../..//span[text()='Select All']/../span[contains(@class, 'checkbox_faux')]");
	protected By NEXT_BUTTON = By.xpath(".//span[text() = 'LOM Return Item Flow']/../../../../../..//div/lightning-button/button[text()='Next >']");
	protected By SUBMIT_BUTTON = By.xpath(
		".//runtime_commerce_oms-flow-buttons/div/div/lightning-button/button[text()='Submit']");
	protected By FINISH_BUTTON = By.xpath(
		".//runtime_commerce_oms-flow-buttons/div/div/lightning-button/button[text()='Finish']");
	protected By EDIT_BUTTON = By.xpath(".//*[text()='Order Summary']/../../../../..//a[@title='Edit' and @class='forceActionLink']");

	protected By BUTTON_OK = By.xpath(".//*[text()='OK']");

	protected By DISCOUNT_FLOW_SELECT_ALL_PRODUCTS = By.xpath(".//flowruntime-flow//span[text() = 'Discount Flow']/../../../../../..//flowruntime-lwc-body//input[@class='datatable-select-all']/../label/span[@class = 'slds-checkbox_faux']");
	protected By DISCOUNT_FLOW_NEXT_BUTTON = By.xpath(".//flowruntime-screen-field//button[@title='Next >']");
	protected By DISCOUNT_FLOW_SUBMIT_BUTTON = By.xpath(".//flowruntime-screen-field//button[@title='Submit']");

	protected By ORDER_PRODUCT_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Tax_Codes']]//following-sibling::div//span[@class='uiOutputText']");
	protected By ORDER_PRODUCT_VERTEX_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Vertex_Tax_Codes']]//following-sibling::div//span[@class='uiOutputText']");
	protected By ORDER_PRODUCT_INVOICE_TEXT_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Invoice_Text_Codes']]//following-sibling::div//span[@class='uiOutputText']");

	SalesForceLOMPostLogInPage postLogInPage;

	public SalesForceLOMOrderSummaryPage( WebDriver driver )
	{
		super(driver);
		postLogInPage = new SalesForceLOMPostLogInPage(driver);
	}

	/**
	 * Navigate to first order in All Orders
	 */
	public void navigateToFirstOrder( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(FIRST_ORDER_RECORD);
		click.javascriptClick(FIRST_ORDER_RECORD);
		waitForSalesForceLoaded();
	}

	/**
	 * Navigate to Details of order
	 */
	public void navigateToDetails( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(RECENTLY_VIEWED);
		wait.waitForElementDisplayed(DETAILS_TAB);
		click.clickElement(DETAILS_TAB);
		waitForSalesForceLoaded();
	}

	/**
	 * Navigate to Related of order
	 */
	public void navigateToRelated( )
	{
		wait.waitForElementDisplayed(RECENTLY_VIEWED);
		wait.waitForElementDisplayed(RELATED_TAB);
		click.clickElement(RELATED_TAB);
		waitForSalesForceLoaded();
	}

	/**
	 * Cancel Item Flow
	 */
	public void cancelItemFlowLOM( )
	{
		wait.waitForElementDisplayed(CANCEL_ITEM_FLOW);
		click.javascriptClick(CANCEL_ITEM_FLOW);
		waitForSalesForceLoaded();
	}

	/**
	 * Clicks the Return Item Flow button to create credit memo
	 */
	public void initiateReturnItemFlow(){
		waitForSalesForceLoaded();
		scroll.scrollElementIntoView(RETURN_ITEM_FLOW);
		wait.waitForElementDisplayed(RETURN_ITEM_FLOW);
		click.clickElement(RETURN_ITEM_FLOW);
	}

	/**
	 * Go through return item flow and generate credit memo
	 * @param productName
	 * @param quantity
	 * @param reason
	 * @param includeShipping
	 */
	public void returnItem(String productName, String quantity, String reason, boolean includeShipping){
		initiateReturnItemFlow();
		changeItemSelect(productName);
		changeNext();
		setQuantity(productName, quantity);
		setReason(productName, reason);
		if(includeShipping)
			includeShippingInReturn(productName);
		changeNext();
		clickSubmit();
		clickFinish();
	}

	/**
	 * Go through return item flow and generate credit memo
	 * @param products
	 * @param reason
	 */
	public void returnAllItems(List<String> products, String reason, boolean includeShipping){
		initiateReturnItemFlow();
		returnAllItemsSelect();
		changeNext();
		for(String product : products){
			setReason(product, reason);
			if (includeShipping)
				includeShippingInReturn(product);
		}
		changeNext();
		clickSubmit();
		clickFinish();
	}

	/**
	 * select item to change
	 * @param name
	 */
	public void changeItemSelect( String name )
	{
		String selectPath = String.format(ITEM_SELECT_XPATH, name);
		wait.waitForElementDisplayed(By.xpath(selectPath));
		click.javascriptClick(By.xpath(selectPath));
	}

	/**
	 * select checkbox to return all items in order
	 */
	public void returnAllItemsSelect()
	{
		wait.waitForElementDisplayed(ITEM_SELECT_ALL_CHECKBOX);
		click.javascriptClick(ITEM_SELECT_ALL_CHECKBOX);
	}

	/**
	 * click next
	 */
	public void changeNext( )
	{
		wait.waitForElementDisplayed(NEXT_BUTTON);
		click.clickElement(NEXT_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * set quantity for item
	 */
	public void setQuantity( String name, String quantity )
	{
		String selectPath = String.format(
			".//h3[text()='%s']/../../../../../../..//select[contains(@id, 'quantities')]", name);
		wait.waitForElementDisplayed(By.xpath(selectPath));
		dropdown.selectDropdownByDisplayName(By.xpath(selectPath), quantity);
		clickOk();
	}

	/**
	 * set reason for item
	 */
	public void setReason( String name, String reason )
	{
		if ( reason == "" )
		{
			reason = "Unknown";
		}
		String selectPath = String.format(
			".//h3[text()='%s']/../../../../../../..//*[contains(@data-label, 'Reason')]/.//select", name);
		wait.waitForElementDisplayed(By.xpath(selectPath));
		dropdown.selectDropdownByDisplayName(By.xpath(selectPath), reason);
		clickOk();
	}

	/**
	 * set Include shipping checkbox
	 * @param productName
	 */
	public void includeShippingInReturn(String productName)
	{
		String path = String.format(".//h3[text()='%s']/../../../../../../..//*[contains(@data-label, 'Shipping')]/.//lightning-input", productName);
		wait.waitForElementDisplayed(By.xpath(path));
		click.clickElement(By.xpath(path));
		waitForSalesForceLoaded();
	}

	/**
	 * click ok button
	 */
	public void clickOk( )
	{
		if(element.isElementDisplayed(BUTTON_OK)) {
			wait.waitForElementDisplayed(BUTTON_OK);
			click.clickElement(BUTTON_OK);
		}
	}

	/**
	 * click Submit button
	 */
	public void clickSubmit( )
	{
		wait.waitForElementDisplayed(SUBMIT_BUTTON);
		click.javascriptClick(SUBMIT_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * click Finish button
	 */
	public void clickFinish( )
	{
		wait.waitForElementDisplayed(FINISH_BUTTON);
		click.clickElement(FINISH_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * select Item from list
	 *
	 * @param name
	 */
	public void selectItem( String name )
	{
		waitForSalesForceLoaded();
		String selectItem = String.format(".//div/div/table/tbody/tr/th/span/a[@title='%s']", name);
		wait.waitForElementDisplayed(By.xpath(selectItem));
		waitForSalesForceLoaded();
		click.javascriptClick(By.xpath(selectItem));
		waitForSalesForceLoaded();
	}

	/**
	 * Get estimated tax field with currency hardcoded to 'USD'
	 *
	 * @return estimated tax
	 */
	public String getEstimatedTax(){
		return getEstimatedTax("USD");
	}

	/**
	 * get Estimated Tax field with specified currency
	 * @param isoCurrencyCode currency used for order summary
	 *
	 * @return estimated Tax
	 */
	public String getEstimatedTax(String isoCurrencyCode)
	{
		String totalTaxLocator = String.format(".//span[text()='Vertex Tax Total']/../../div/span/span[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_TAX = By.xpath(totalTaxLocator);
		int i = 0;
		while (!element.isElementPresent(TOTAL_TAX) && i < 5)
		{
			refreshPage();
			navigateToDetails();
			waitForSalesForceLoaded();
			i++;
		}
		if (i==0)
		{
			navigateToDetails();
			waitForSalesForceLoaded();
		}
		wait.waitForElementEnabled(TOTAL_TAX);
		String sectionText = text.getElementText(TOTAL_TAX);
		i =0;
		while(sectionText == "$0.00" && i < 5)
		{
			refreshPage();
			navigateToDetails();
			sectionText = text.getElementText(TOTAL_TAX);
			i++;
		}
		return sectionText;
	}

	/**
	 * get Total with Tax field with currency hardcoded to 'USD'
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax()
	{
		return getTotalWithTax("USD");
	}

	/**
	 * get Total with Tax field
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax(String isoCurrencyCode)
	{
		String totalWithTaxLocator = String.format(".//span[text()='Total']/../../div/span/span[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_WITH_TAX = By.xpath(totalWithTaxLocator);
		wait.waitForElementDisplayed(TOTAL_WITH_TAX);
		wait.waitForElementEnabled(TOTAL_WITH_TAX);
		String sectionText = text.getElementText(TOTAL_WITH_TAX);
		return sectionText;
	}

	/**
	 * Click Edit button
	 */
	public void clickEditButton(){
		waitForPageLoad();
		wait.waitForElementDisplayed(EDIT_BUTTON);
		click.javascriptClick(EDIT_BUTTON);
	}

	/**
	 * edit current case
	 */
	private void editCase( )
	{
		waitForSalesForceLoaded();
		if(element.isElementPresent(EDIT_ACTION))
		{
			clickEditButton();
		}
		else{
			wait.waitForElementDisplayed(MORE_ACTIONS_DROPDOWN);
			click.clickElement(MORE_ACTIONS_DROPDOWN);
			waitForSalesForceLoaded();
			wait.waitForElementDisplayed(EDIT_ACTION);
			click.clickElement(EDIT_ACTION);
			waitForSalesForceLoaded();
		}
	}

	/**
	 * Run Discount Flow on Order Summary
	 */
	public void initiateDiscountFlow(){
		scroll.scrollElementIntoView(DISCOUNT_FLOW_SELECT_ALL_PRODUCTS);
		wait.waitForElementDisplayed(DISCOUNT_FLOW_SELECT_ALL_PRODUCTS);
		jsWaiter.sleep(2000);
		click.clickElement(DISCOUNT_FLOW_SELECT_ALL_PRODUCTS);
		waitForSalesForceLoaded();
		clickDiscountFlowNextButton();
	}

	/**
	 * Select discount value and reason for specific product on Discount Flow
	 * @param productName
	 * @param discountValue
	 * @param reason
	 */
	public void selectDiscountValueAndReason(String productName, String discountValue, String reason){

		String discountValueInput = String.format(".//tr//div/h3[contains(text(), '%s')]/../../../../../../..//td[@data-label='Discount Value']//input", productName);
		By discountValuePath = By.xpath(discountValueInput);
		wait.waitForElementDisplayed(discountValuePath);
		text.enterText(discountValuePath, discountValue);
		String discountReasonDropdown = String.format(".//tr//div/h3[contains(text(), '%s')]/../../../../../../..//td[@data-label='Reason']//select", productName);
		By discountReasonDropdownPath = By.xpath(discountReasonDropdown);
		wait.waitForElementDisplayed(discountReasonDropdownPath);
		dropdown.selectDropdownByValue(discountReasonDropdownPath, reason);
	}

	/**
	 *  Click next button on Discount Flow screen
	 */
	public void clickDiscountFlowNextButton(){
		wait.waitForElementDisplayed(DISCOUNT_FLOW_NEXT_BUTTON);
		click.clickElement(DISCOUNT_FLOW_NEXT_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Complete discount flow by clicking next and submit
	 */
	public void completeDiscountFlow(){
		clickDiscountFlowNextButton();
		wait.waitForElementDisplayed(DISCOUNT_FLOW_SUBMIT_BUTTON);
		scroll.scrollElementIntoView(DISCOUNT_FLOW_SUBMIT_BUTTON);
		click.clickElement(DISCOUNT_FLOW_SUBMIT_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * create fulfillment order for current case
	 */
	public void createFulfilmentOrder( )
	{
		waitForSalesForceLoaded();
		editCase();
		wait.waitForElementDisplayed(CASE_STATUS_DROPDOWN);
		click.clickElement(CASE_STATUS_DROPDOWN);
		wait.waitForElementDisplayed(CREATE_FULFILMENT_ORDER);
		click.clickElement(CREATE_FULFILMENT_ORDER);

		click.clickElement(SAVE_BUTTON);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * select Fulfillment order for current case
	 */
	public void selectFulfillmentOrder( )
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		WebElement object = null;

		int i = 0;
		while ( object == null && i < 6 )
		{
			try
			{
				refreshPage();
				navigateToRelated();
				waitForSalesForceLoaded();
				object = driver.findElement(FULFILLMENT_ORDER);
				i++;
			}
			catch ( Exception e )
			{
				VertexLogger.log("waiting for Fulfillment order to display");
				return;
			}
		}

		click.clickElement(FULFILLMENT_ORDER);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * select Invoice for current case
	 */
	public void selectInvoice( )
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		WebElement object = null;

		int i = 0;
		while ( object == null && i < 6 )
		{
			try
			{
				refreshPage();
				navigateToRelated();
				waitForSalesForceLoaded();
				object = driver.findElement(INVOICE);
				i++;
			}
			catch ( Exception e )
			{
				VertexLogger.log("waiting for Fulfillment order to display");
				return;
			}
		}

		click.clickElement(INVOICE);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Gets the value for the product Invoice Text Code on the Order summary product page
	 * */
	public String getProductInvoiceTextCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_INVOICE_TEXT_CODE);
		return taxCode.getText();
	}

	/**
	 * Gets the value for the product Vertex Tax Code on the Order summary product page
	 * */
	public String getProductVertexTaxCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_VERTEX_TAX_CODE);
		return taxCode.getText();
	}

	/**
	 * Gets the value for the product Tax Code on the Order summary product page
	 * */
	public String getProductTaxCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_TAX_CODE);
		return taxCode.getText();
	}

	/**
	 * Navigates to the Product Summary from the Order Summary page
	 * */
	public void navigateToOrderProductSummary(String productName) {
		String productXpath = String.format("//*/tr/th/span/a[@title='%s']",productName);
		WebElement summary = wait.waitForElementDisplayed(By.xpath(productXpath));
		click.clickElementCarefully(summary);
	}

	/**
	 * Navigates back to the Order Summary page from the product summary page
	 * */
	public void navigateToOrderSummary() {
		List<WebElement> orderSummaryButtons = wait.waitForAllElementsDisplayed(By.xpath("//*/div[contains(@class, 'tabBar ')]/ul/li/a/span[@title='OrderSummary']"));
		click.clickElementCarefully(orderSummaryButtons.get(1));
	}
}
