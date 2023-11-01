package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

/**
 * All purchase orders page common methods and object declaration page
 *
 * @author Saidulu Kodadala
 */
public class DFinanceAllPurchaseOrdersPage extends DFinanceBasePage
{
	protected By VENDOR_ACCOUNT_INPUT = By.cssSelector("input[id*='OrderAccount'][id*='PurchCreateOrder']");
	protected By VENDOR_CONTACT_INPUT = By.cssSelector("input[id*='ContactPersonName'][id*='PurchCreateOrder']");
	protected By SEARCH_INPUT = By.cssSelector("input[name='GridFilter_Input'][id*='purchtablelistpage']");
	protected By SELECT_PURCHASE_ORDER_FROM_DROPDOWN = By.xpath("(//li[contains(@class, 'quickFilter-listItem')])[1]");
	protected By INVOICE_TAB = By.xpath("(//div[@data-dyn-controlname='Invoice'])[1]//span[text()='Invoice']");
	protected By INVOICE_AND_DELIVERY_DROPDOWN = By.xpath("//div//button[@aria-label='Invoice and delivery']");
	protected By CUSTOMER_ACCOUNT = By.name("DynamicHeader_AccountNum");
	protected By CUSTOMER_NAME = By.name("Org_Name");
	protected By CUSTOMER_GROUP_NUMBER = By.name("DynamicDetail_CustGroup");

	protected By DELETE_BUTTON = By.cssSelector(".button-label[id*='SystemDefinedDeleteButton']");
	protected By NEW_VENDOR_INVOICE = By.name("NewInvoiceButton");

	public DFinanceAllPurchaseOrdersPage( WebDriver driver )
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Click on new purchase order button
	 */
	public DFinanceCreatePurchaseOrderPage clickNewPurchaseOrderButton( )
	{
		wait.waitForElementEnabled(NEW_BUTTON);
		click.clickElementCarefully(NEW_BUTTON);
		waitForPageLoad();

		DFinanceCreatePurchaseOrderPage createPurchaseOrderPage = initializePageObject(
			DFinanceCreatePurchaseOrderPage.class);

		return createPurchaseOrderPage;
	}

	/**
	 * Set 'Vendor Account Number'
	 *
	 * @param vendorAccuountNumber
	 */
	public void setVendorAccountNumber( String vendorAccuountNumber )
	{
		wait.waitForElementEnabled(VENDOR_ACCOUNT_INPUT);
		text.enterText(VENDOR_ACCOUNT_INPUT, vendorAccuountNumber);
	}

	/**
	 * Verify vendor delivery adderss displayed or not
	 */
	public void verifyDeliveryAddress( )
	{
		wait.waitForElementEnabled(VENDOR_ACCOUNT_INPUT);
		click.clickElement(VENDOR_CONTACT_INPUT);
		text.pressTab(VENDOR_CONTACT_INPUT);
	}

	public boolean searchPurchaseOrder( String invoiceNumber )
	{
		wait.waitForElementDisplayed(SEARCH_INPUT);
		text.enterText(SEARCH_INPUT, invoiceNumber + Keys.ARROW_UP + Keys.ENTER);
		waitForPageLoad();

		By searchResultBy = getPONumberBy(invoiceNumber);
		boolean result = element.isElementDisplayed(searchResultBy);

		waitForPageLoad();
		wait.waitForElementDisplayed(SELECT_PURCHASE_ORDER_FROM_DROPDOWN);
		click.clickElementCarefully(SELECT_PURCHASE_ORDER_FROM_DROPDOWN);
		waitForPageLoad();
		wait.waitForAllElementsDisplayed(searchResultBy);

		click.clickElementCarefully(searchResultBy);
		return result;
	}

	private By getPONumberBy( String invoiceNumber )
	{
		By invoiceNumberBy = By.cssSelector(
			String.format("[id*='PurchTable_PurchIdAdvanced'][title*='%s']", invoiceNumber));
		return invoiceNumberBy;
	}

	/**
	 * Click on delete button
	 */
	public void clickDeleteButton( )
	{
		wait.waitForElementEnabled(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Invoice Tab
	 */
	public void clickOnInvoiceTab(){
		wait.waitForElementDisplayed(INVOICE_TAB);
		click.clickElementCarefully(INVOICE_TAB);
	}

	/**
	 * Clicks on the Invoice and delivery dropdown
	 */
	public void clickInvoiceAndDeliveryDropdown(){
		wait.waitForElementDisplayed(INVOICE_AND_DELIVERY_DROPDOWN);
		click.clickElementCarefully(INVOICE_AND_DELIVERY_DROPDOWN);
	}

	/**
	 * Validates that the Vendor or Customer Established is on
	 * @param vendorState
	 * @param establishedType
	 */
	public boolean validateEstablishedType(boolean vendorState, String establishedType){
		WebElement tog = driver.findElement(By.xpath("//label[@data-dyn-controlname='Vertex_VTX"+establishedType+"Established']//span[@class='toggle-box']"));
		tog.getAttribute("title");
		if ( (vendorState && tog
				.getAttribute("aria-checked")
				.equals("true")))
		{
			vendorState = true;
		}

		return vendorState;
	}

	/**
	 * Enter customer account
	 */
	public void createCustomerAccount(){
		String values = "ABCDEFGIJK_";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = 12;

		for(int i = 0; i < length; i++){
			int index = random.nextInt(values.length());
			char randomChar = values.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();

		wait.waitForElementDisplayed(CUSTOMER_ACCOUNT);
		text.clickElementAndEnterText(CUSTOMER_ACCOUNT, randomString);
	}

	/**
	 * Enter customer name
	 */
	public void enterCustomerName(){
		String values = "ABCDEFGIJK_";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		int length = 12;

		for(int i = 0; i < length; i++){
			int index = random.nextInt(values.length());
			char randomChar = values.charAt(index);
			sb.append(randomChar);
		}

		String randomString = sb.toString();
		wait.waitForElementDisplayed(CUSTOMER_NAME);
		text.clickElementAndEnterText(CUSTOMER_NAME, randomString);
	}

	/**
	 * Enter customer group number
	 * @param customerGroupNumber
	 */
	public void customerGroupNumber(String customerGroupNumber){
		wait.waitForElementDisplayed(CUSTOMER_GROUP_NUMBER);
		text.clickElementAndEnterText(CUSTOMER_GROUP_NUMBER, customerGroupNumber);
	}

	/**
	 * Clicks the New Vendor Invoice
	 */
	public void clickNewVendorInvoice(){
		wait.waitForElementDisplayed(NEW_VENDOR_INVOICE);
		click.clickElementCarefully(NEW_VENDOR_INVOICE);
	}

}
