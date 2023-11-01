package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SalesForceLOMFulfillmentPage extends SalesForceBasePage
{
	protected By FIRST_ORDER_RECORD = By.xpath(".//div[1]/div/div/ul/li[1]/a[@tabindex=0]");
	protected By DETAILS_TAB = By.xpath(
		".//h1/div[text()='Fulfillment Order']/../../../../../../../../../.././/div/div/div/div/ul/li/a/span[text()='Details']");
	protected By RELATED_TAB = By.xpath("//*/ul[@role='tablist']/li/a[@id='relatedListsTab__item'][last()]");
	protected By RECENTLY_VIEWED = By.xpath(
		".//div/div/div/div[2]/div/div[1]/div[2]/div[1]/span[@title='Recently Viewed']");

	protected By MORE_ACTIONS_DROPDOWN = By.xpath(
		".//h1/div[text()='Fulfillment Order']/../../../../../../../../../../..//*[contains(@title,'actions')]");
	protected By EDIT_ACTION = By.xpath(".//div/ul/li/a[@title='Edit']");

	protected By CASE_STATUS_DROPDOWN = By.xpath(".//h2[contains(text(), 'Edit FO-')]/../..//label[text()='Status']/following-sibling::div//button");
	protected By CREATE_FULFILMENT_ORDER = By.xpath(".//div/ul/li/a[@title='Create Fulfillment Order']");
	protected By SET_FULFILLED = By.xpath(".//*[@data-value='Fulfilled']");

	protected By SAVE_BUTTON = By.xpath(".//li/*[@title='Save']//button[@name='SaveEdit']");
	protected By EDIT_BUTTON = By.xpath(".//*[text()='Fulfillment Order']/../../../../..//button[@name='Edit']");

	protected By FULFILLMENT_ORDER = By.xpath(
		".//div/div/table/thead/tr/th[@title='Fulfillment Order Number']/../../..//div/a");

	protected By INVOICE_ORDER = By.xpath(".//span[text()='Invoice']/../../div/span/div/a");
	protected By ORDER_SUMMARY_TAB = By.xpath("(.//ul/li/a[contains(@class,'tabHeader') and contains(@href, '/r/OrderSummary')])[2]");

	protected String ITEM_SELECT_XPATH
		= ".//table/tbody/tr/th/lightning-primitive-cell-factory/span/div/*[text()='%s']/../../../../.././/input";
	protected By SUBMIT_BUTTON = By.xpath(
		".//runtime_commerce_oms-flow-buttons/div/div/lightning-button/button[text()='Submit']");

	protected By BUTTON_OK = By.xpath(".//*[text()='OK']");

	protected By PRODUCT_LINK = By.xpath(".//*/slot/span[text()='00000001']");
	protected By ORDER_PRODUCT_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Tax_Codes']]//following-sibling::div//span//slot//lightning-formatted-text[@slot='outputField']");
	protected By ORDER_PRODUCT_VERTEX_TAX_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Vertex_Tax_Codes']]//following-sibling::div//span//slot//lightning-formatted-text[@slot='outputField']");
	protected By ORDER_PRODUCT_SUMMARY_FULFILLMENT_ORDER_LINK = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Fulfillment Order']]//following-sibling::div//span//slot//*//a[contains(@href, 'FulfillmentOrder')]/slot/slot/span");
	protected By ORDER_PRODUCT_INVOICE_TEXT_CODE = By.xpath("//div[contains(@class, 'slds-form-element')]/div[contains(@class, 'test-id__field-label-container') and span[text()='Invoice_Text_Codes']]//following-sibling::div//span//slot//lightning-formatted-text[@slot='outputField']");
	protected By FULFILLMENT_ORDER_INVOICE = By.xpath("*//a[contains(@href, 'Invoice')]/slot/slot/span");

	SalesForceLOMPostLogInPage postLogInPage;

	public SalesForceLOMFulfillmentPage( WebDriver driver )
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
	 * select Item from list
	 *
	 * @param name
	 */
	public void selectItem( String name )
	{
		String selectItem = String.format(".//div/div/table/tbody/tr/th/span/a[@title='%s']", name);
		wait.waitForElementDisplayed(By.xpath(selectItem));
		click.clickElement(By.xpath(selectItem));
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
	 * get Estimated Tax field with specific currency code
	 * @param isoCurrencyCode currency used for Order
	 *
	 * @return estimated Tax
	 */
	public String getEstimatedTax(String isoCurrencyCode)
	{
		String totalTaxLocator = String.format(".//div/span[text()='Vertex Tax Total']/../../div/span/slot/*[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_TAX = By.xpath(totalTaxLocator);
		int i = 0;
		while (!element.isElementPresent(TOTAL_TAX) && i < 5)
		{
			i++;
			refreshPage();
			waitForSalesForceLoaded();
		}

		wait.waitForElementEnabled(TOTAL_TAX);
		return text.getElementText(TOTAL_TAX);
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
	 * get Total with Tax field based on specified currency
	 * @param isoCurrencyCode currency used for order
	 *
	 * @return estimated Total Tax
	 */
	public String getTotalWithTax(String isoCurrencyCode)
	{
		String totalWithTaxLocator = String.format(".//span[text()='Total with Tax']/../../div/span/slot//*[contains(text(), '%s')]", isoCurrencyCode);
		By TOTAL_WITH_TAX = By.xpath(totalWithTaxLocator);
		wait.waitForElementDisplayed(TOTAL_WITH_TAX);
		wait.waitForElementEnabled(TOTAL_WITH_TAX);
		return text.getElementText(TOTAL_WITH_TAX);
	}

	/**
	 * edit current case
	 */
	private void editCase( )
	{
		waitForSalesForceLoaded();
		if(element.isElementPresent(EDIT_BUTTON))
		{
			clickEditButton();
		}
		else{
			wait.waitForElementDisplayed(MORE_ACTIONS_DROPDOWN);
			click.javascriptClick(MORE_ACTIONS_DROPDOWN);
			wait.waitForElementDisplayed(EDIT_ACTION);
			click.clickElement(EDIT_ACTION);
		}
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
	 * create fulfillment order for current case
	 */
	public void createInvoiceOrder( )
	{
		waitForPageLoad();
		editCase();
		waitForPageLoad();
		wait.waitForElementDisplayed(CASE_STATUS_DROPDOWN);
		click.clickElement(CASE_STATUS_DROPDOWN);
		wait.waitForElementDisplayed(SET_FULFILLED);
		click.clickElement(SET_FULFILLED);

		click.clickElement(SAVE_BUTTON);
		waitForPageLoad();
		waitForSalesForceLoaded(5000);
		refreshPage();
	}

	/**
	 * select Invoice order for current case
	 */
	public void selectInvoiceOrder( )
	{
		WebElement object = null;

		int i = 0;
		while ( object == null && i < 6 )
		{
			try
			{
				refreshPage();
				navigateToDetails();
				object = driver.findElement(INVOICE_ORDER);
			}
			catch ( Exception e )
			{
				VertexLogger.log("waiting for Invoice order to display");
			}
			i++;
		}
		click.clickElement(INVOICE_ORDER);
		waitForPageLoad();
	}

	/**
	* Navigate back to Order Summary page
	*/
	public void navigateBackToOrderSummary( )
	{
		wait.waitForElementDisplayed(ORDER_SUMMARY_TAB);
		click.clickElement(ORDER_SUMMARY_TAB);
	}

	/**
	 * Navigates to Product Summary page for a Fulfillment Order
	 */
	public void navigateToProductSummary( )
	{
		wait.waitForElementDisplayed(PRODUCT_LINK);
		click.clickElement(PRODUCT_LINK);
	}

	/**
	 * Gets the value for the product Invoice Text Code on the Fulfillment Product summary page
	 * */
	public String getProductInvoiceTextCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_INVOICE_TEXT_CODE);
		System.out.println(taxCode.getText());
		return taxCode.getText();
	}

	/**
	 * Gets the value for the product Vertex Tax Code on the Fulfillment Product summary page
	 * */
	public String getProductVertexTaxCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_VERTEX_TAX_CODE);
		System.out.println(taxCode.getText());
		return taxCode.getText();
	}

	/**
	 * Gets the value for the product Tax Code on the Fulfillment Product summary page
	 * */
	public String getProductTaxCode() {
		WebElement taxCode = wait.waitForElementDisplayed(ORDER_PRODUCT_TAX_CODE);
		System.out.println(taxCode.getText());
		return taxCode.getText();
	}

	/**
	 * Navigate back to Fulfillment Order from the Product Summary page
	 */
	public void toFulfillmentOrderFromProductSummary(){
		wait.waitForElementEnabled(ORDER_PRODUCT_SUMMARY_FULFILLMENT_ORDER_LINK);
		click.clickElementCarefully(ORDER_PRODUCT_SUMMARY_FULFILLMENT_ORDER_LINK);

	}

	/**
	 * Navigates to the invoice associated with a Fulfillment Order
	 */
	public void toFulfillmentOrderInvoice(){
		wait.waitForElementEnabled(FULFILLMENT_ORDER_INVOICE);
		click.clickElementCarefully(FULFILLMENT_ORDER_INVOICE);
	}
}
