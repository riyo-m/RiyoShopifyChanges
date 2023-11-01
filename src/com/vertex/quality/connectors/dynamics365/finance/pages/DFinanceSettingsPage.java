package com.vertex.quality.connectors.dynamics365.finance.pages;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.common.utils.properties.ReadProperties;
import com.vertex.quality.connectors.dynamics365.finance.pages.base.DFinanceBasePage;
import com.vertex.quality.connectors.dynamics365.finance.tests.base.DFinanceBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

/**
 * Settings page common methods and object declaration page
 *
 * @author Saidulu Kodadala ssalisbu
 */
public class DFinanceSettingsPage extends DFinanceBasePage
{
	public static ReadProperties readSettingsDetails;
	private String CONFIG_DETAILS_FILE_PATH = CommonDataProperties.CONFIG_DETAILS_FILE_PATH;
	public String SETTINGS_TRUSTED_ID = null;
	public String TAX_CALCULATION_URL = null;
	public String ADDRESS_VALIDATION_URL = null;
	Actions action = new Actions(driver);

	protected By SETTINGS_BARS = By.className("section-page");
	protected By ADDRESS_VALIDATION = By.xpath("//div[@data-dyn-controlname='AddressValidationTabPage']/div[@role='button']");
	protected By TOGGLE = By.className("toggle-value");
	protected By LIST_ALLRESULTS = By.cssSelector("span[id*='AddressValidation_AlwaysListAddressResults_toggle']");
	protected By SAVE_BUTTON = By.xpath("//span[text()='Save']");
	protected By ACCEPTABLE_CONFIDENCE = By.className("alignmentRight");
	protected By NAVIGATE_TO_DASHBOARD = By.id("NavBarDashboard_label");
	protected By SETTINGS_TAB = By.id("navBarSettings_button");
	protected By TRUSTED_ID = By.name("ConnectionInfo_TrustedID");
	protected By TRUSTED_TAX_URL = By.name("ConnectionInfo_TaxCalculationURL");
	protected By TRUSTED_ADDRESS_URL = By.name("ConnectionInfo_AddressValidationURL");
	protected By CLOUD_URL = By.name("ConnectionInfo_PortalURL");
	protected By TEST_CONNECTION = By.cssSelector("[id*='TestConnection_label']");
	protected By MESSAGE_DETAILS_LINK = By.xpath("//span[@title='Message details']");
	protected final String messageDetailsValidLinkClass = "link-content-validLink";

	protected By MESSAGES = By.className("prefixMessage-messageText");
	protected By MESSAGE_CLOSE_ICON = By.cssSelector("button[id*='Dialog-HideButton']");
	protected By SALES_TAX_GROUP = By.name("TaxSettings_VertexSalesTaxGroup");
	protected By DEFAULT_ITEM_TAX_GROUP = By.name("TaxSettings_VertexItemTaxGroup");
	protected By VERTEX_PURCHASE_SALES_TAX_GROUP = By.name("APTaxSettings_VertexPurchTaxGroup1");
	protected By CONSUMER_USE_TAX_GROUP = By.name("APTaxSettings_VertexConsumerTaxGroup1");
	protected By NEW_BUTTON = By.className("New-symbol");
	protected By FILTER_SEARCH_BOX = By.name("QuickFilterControl_Input");
	protected By DEFAULTDISCOUNTCODE = By.name("DiscountsGroup_DefaultDiscountCode");
	protected By ACCOUNT_RECEIVABLE = By.xpath("//div[@data-dyn-controlname='AccountsReceivable']");
	protected By ACCOUNTS_RECEIVABLE_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_EnableVertexAR']//span[@class='toggle-value']");
	protected By ACCOUNTS_RECEIVABLE_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_EnableVertexAR']//span[@class='toggle-box']");
	protected By ACCOUNTS_RECEIVABLE_FETCH_MULTIPLE_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_FetchMultipleRegIds']//span[@class='toggle-value']");
	protected By ACCOUNTS_RECEIVABLE_FETCH_MULTIPLE_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_FetchMultipleRegIds']//span[@class='toggle-box']");
	protected By ACCOUNT_PAYABLE = By.xpath("//div[contains(@id,'AccountsPayable_header')]/button");
	protected By VERTEX_SETTING = By.xpath("//li[@data-dyn-controlname='VertexSettingsTabPage_header']");
	protected By SALES_ORDER_DROPDOWN = By.xpath(
		"//span[text()='Sales order'][@class='quickFilter-listFieldName']");
	protected By REBATE_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_EnableDiscountExclusion']//div/span[2]");
	protected By REBATE_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_EnableDiscountExclusion']//div/span[1]");
	protected By SELECT_COMPANY = By.id("CompanyButton");
	protected By INPUT_COMPANY = By.name("DataArea_id");
	protected By REBATE_DISPLAYED = By.xpath("//div[@data-dyn-controlname='VTXTaxIncludeGroup']");
	protected By ACCRUE_TITLE = By.xpath("//label[@data-dyn-controlname='APTaxSettings_AccrueTax1']//span[@class='toggle-value']");
	protected By ACCRUE_ENABLED = By.xpath("//label[@data-dyn-controlname='APTaxSettings_AccrueTax1']//span[@class='toggle-box']");
	protected By ADVANCED_TAX_TITLE = By.xpath("//label[@data-dyn-controlname='ARTaxSettings_UseAdvancedTaxGroupFunc1']//span[@class='toggle-value']");
	protected By ADVANCED_TAX_ENABLED = By.xpath("//label[@data-dyn-controlname='ARTaxSettings_UseAdvancedTaxGroupFunc1']//span[@class='toggle-box']");
	protected By REQUIRE_MANUAL_EXCHANGE_RATE_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_RequireManualExchRate']//span[@class='toggle-value']");
	protected By REQUIRE_MANUAL_EXCHANGE_RATE_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_RequireManualExchRate']//span[@class='toggle-box']");
	protected By SEND_POSTING_REQUEST_IMMEDIATELY = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_SendPostingRequestImmediately']//span[@class='toggle-box']");
	protected By RETAIN_TAX_OVERRIDE_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_RetainTaxGroupFromItem']//span[@class='toggle-value']");
	protected By RETAIN_TAX_OVERRIDE_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_RetainTaxGroupFromItem']//span[@class='toggle-box']");
	protected By RETAIL_TAB = By.xpath("//div[@data-dyn-controlname='Retail']//div[@aria-level]");
	protected By RETAIL_TAB_NOT_EXPANDED = By.xpath("//div[@data-dyn-controlname='Retail']//div[@aria-level]//button[@aria-expanded=\"false\"]");
	protected By BKUPTX_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_UseBackupTaxGroup']//span[@class='toggle-value']");
	protected By BKUPTX_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_UseBackupTaxGroup']//span[@class='toggle-box']");
	protected By BKUPTX_INTERVAL = By.name("VTXVertexParameters_RetailRetryInterval");
	protected By CANADA_TO_US_DELIVERY_TERM = By.name("VTXVertexParameters_CANDlvTerm");
	protected By US_TO_CANADA_DELIVERY_TERM = By.name("VTXVertexParameters_USDlvTerm");
	protected By ERROR_MSG = By.xpath("//span[@title='Field Commerce Retry Interval must be filled in.']");
	protected By QUOTE_DATE = By.xpath("//label[text()='Date for sales order quote calculation']");
	protected By QUOTE_DATE_INPUT = By.cssSelector("ul[id*='VTXVertexParameters_SOQuoteCalcDate_list']");
	protected By QUOTE_DATE_DROPDOWN = By.xpath("//input[@name='VTXVertexParameters_SOQuoteCalcDate']");
	protected By ADD_CLEANSING_TITLE = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_EnableVertexAddress']//span[@class='toggle-value']");
	protected By ADD_CLEANSING_ENABLED = By.xpath("//label[@data-dyn-controlname='VTXVertexParameters_EnableVertexAddress']//span[@class='toggle-box']");
	protected By AMOUNTS_INCLUDE_SALES_TAX_TITLE = By.xpath("//label[@data-dyn-controlname='SalesTax_LedgerJournalInclTax']//span[@class='toggle-value']");
	protected By AMOUNTS_INCLUDE_SALES_TAX_ENABLED = By.xpath("//label[@data-dyn-controlname='SalesTax_LedgerJournalInclTax']//span[@class='toggle-box']");
	protected By ENABLE_LOGGING_TITLE = By.xpath("//label[@data-dyn-controlname='LoggingCheckBoxControl1']//span[@class='toggle-value']");
	protected By ENABLE_LOGGING_ENABLED = By.xpath("//label[@data-dyn-controlname='LoggingCheckBoxControl1']//span[@class='toggle-box']");

	public DFinanceSettingsPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * select settings page in settings
	 *
	 * @param string settings page to click
	 */
	public void selectSettingsPage( String string )
	{
		waitForPageLoad();
		List<WebElement> field = element.getWebElements(SETTINGS_BARS);
		WebElement start = element.selectElementByText(field, string);
		if ( start != null )
		{
			click.clickElementCarefully(
				start);//fixme this didn't wait for enabled until scott refactored, check that if this function breaks
		}
	}

	/**
	 * select company from top right, USMF OR USRT
	 */
	public void selectCompany( String string )
	{
		waitForPageLoad();
		click.clickElementCarefully(SELECT_COMPANY);
		text.clearText(INPUT_COMPANY);
		text.enterText(INPUT_COMPANY, string);
		text.pressEnter(INPUT_COMPANY);
		waitForPageLoad();
		try
		{
			text.pressEnter(INPUT_COMPANY);
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		waitForPageLoad();
	}

	/**
	 * validate rebate discount section is displayed on All discount > Grand Opening
	 */
	public void validateRebate()
	{
		waitForPageLoad();
		List<WebElement> rebate = driver.findElements(By.xpath("//div[@data-dyn-controlname='VTXTaxIncludeGroup']"));
		if (rebate.isEmpty()) {
			System.out.println("Tax Classification Section is Invisible");
		}else{
			System.out.println("Tax Classification Section is Visible");
		}
		waitForPageLoad();
	}

	/**
	 * set confidence level and set results to on or off
	 *
	 * @param value confidence number
	 * @param b     used to set results
	 *
	 * @return to home page
	 */
	public DFinanceHomePage setConfidenceAndResults( String value, boolean b )
	{
		this.typeInLocator(ACCEPTABLE_CONFIDENCE, value);
		WebElement tog = driver.findElement(TOGGLE);
		WebElement list = driver.findElement(LIST_ALLRESULTS);
		tog.getAttribute("title");
		if ( (b && list
				.getAttribute("aria-checked")
				.equals("false")) || (!b && list
				.getAttribute("title")
				.equals("true")) )
		{
			list.click();
		}
		return clickOnSaveButton();
	}

	/**
	 * toggle rebate button to yes or no
	 */

	public void toggleRebateButton(boolean b )
	{
		expandHeader(ACCOUNT_RECEIVABLE);
		waitForPageLoad();
		WebElement tog = driver.findElement(REBATE_TITLE);
		WebElement list = driver.findElement(REBATE_ENABLED);
		tog.getAttribute("title");
		if ( (b && tog
			.getAttribute("title")
			.equals("No")) || (!b && tog
			.getAttribute("title")
			.equals("Yes")) )
		{
			list.click();
		}
		clickOnSaveButton();
	}

	/**
	 * type content into web element on screen, after clearing
	 *
	 * @param 'locator' web element to type to
	 * @param 'type'    string to write in locator
	 */
	public void typeInLocator( By locator, String type )
	{
		waitForPageLoad();
		text.enterText(locator, type);
	}

	/**
	 * Navigate to home page/Dashboard page
	 *
	 * @return
	 */
	public DFinanceHomePage navigateToDashboardPage( )
	{
		click.javascriptClick(NAVIGATE_TO_DASHBOARD);
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	/**
	 * Enter "Trusted ID"
	 *
	 * @param trustedId
	 */
	public void setTrustedId( String trustedId )
	{
		wait.waitForElementDisplayed(VERTEX_SETTING);
		click.clickElementCarefully(VERTEX_SETTING);
		wait.waitForElementDisplayed(TRUSTED_ID);
		text.enterText(TRUSTED_ID, trustedId);
	}

	/**
	 * Enter "Tax calculation URL"
	 *
	 * @param taxCalculationURL
	 */
	public void setTrustedTaxURL( String taxCalculationURL )
	{
		wait.waitForElementDisplayed(driver.findElement(TRUSTED_TAX_URL));
		text.enterText(TRUSTED_TAX_URL, taxCalculationURL);
	}

	/**
	 * Enter "Address Validation URL"
	 *
	 * @param addressValidationURL
	 */
	public void setTrustedAddressURL( String addressValidationURL )
	{
		wait.waitForElementDisplayed(driver.findElement(TRUSTED_ADDRESS_URL));
		text.enterText(TRUSTED_ADDRESS_URL, addressValidationURL);
		waitForPageLoad();
	}

	/**
	 * Set Second Cloud URL
	 */
	public void setSecondCloudURL( String secondCloudURL )
	{
		wait.waitForElementDisplayed(driver.findElement(CLOUD_URL));
		text.enterText(CLOUD_URL, secondCloudURL);
		waitForPageLoad();
	}

	/**
	 * Click on "Test Connection" button
	 */
	public void clickOnTestConnection( )
	{
		wait.waitForElementEnabled(TEST_CONNECTION);
		click.javascriptClick(TEST_CONNECTION);
		waitForPageLoad();
		wait.waitForAnyElementsDisplayed(MESSAGE_DETAILS_LINK);
	}

	/**
	 * Click on "Message Details" hyperlink
	 */
	public boolean clickOnMessageDetailsLink( )
	{
		boolean linkStatus = false;
		//TODO replace with waitForADisplayedElement()
		List<WebElement> detailsLinks = wait.waitForAnyElementsDisplayed(MESSAGE_DETAILS_LINK);
		//ignores any details links which aren't currently displayed
		detailsLinks.removeIf(link -> !link.isDisplayed());
		if ( detailsLinks.size() > 0 )
		{
			//get details of most recent message
			WebElement messageDetailsLink = detailsLinks.get(0);
			final String linkClasses = attribute.getElementAttribute(messageDetailsLink, "class");
			if ( linkClasses != null && linkClasses.contains(messageDetailsValidLinkClass) )
			{
				linkStatus = true;
				wait.waitForElementEnabled(messageDetailsLink);
				click.clickElement(messageDetailsLink);
			}
			else
			{
				VertexLogger.log("link failed, so no message details could be opened", VertexLogLevel.ERROR);
			}
		}

		return linkStatus;
	}

	/**
	 * "Test Connection" successful information is present and has the text:
	 * Address URL and login are valid.
	 * Sales tax URL and login are valid.
	 *
	 * @return on error, returns an empty string or a string with just newline characters
	 */
	public String getValidationMessage( )
	{
		wait.waitForElementDisplayed(MESSAGES);
		List<WebElement> allElements = element.getWebElements(MESSAGES);

		String result = "";

		for ( WebElement elelement : allElements )
		{
			String result1 = elelement.getAttribute("textContent");
			result += result1 + "\n";
		}
		click.clickElement(MESSAGE_CLOSE_ICON);
		waitForPageLoad();
		return result;
	}

	/**
	 * Verify "Vertex sales tax group" dropdown present
	 *
	 * @return
	 */
	public boolean verifyVertexSalesTaxGroup( )
	{
		wait.waitForElementDisplayed(SALES_TAX_GROUP);
		boolean status = element.isElementDisplayed(SALES_TAX_GROUP);
		return status;
	}

	/**
	 * Verify "Default item tax group" dropdown present
	 *
	 * @return
	 */
	public boolean verifyDefaultItemTaxGroup( )
	{
		wait.waitForElementDisplayed(DEFAULT_ITEM_TAX_GROUP);
		boolean status = element.isElementDisplayed(DEFAULT_ITEM_TAX_GROUP);
		return status;
	}

	/**
	 * Verify "Vertex purchase sales tax group" dropdown present
	 *
	 * @return
	 */
	public boolean verifyVertexPurchaseSalesTaxGroup( )
	{
		wait.waitForElementDisplayed(VERTEX_PURCHASE_SALES_TAX_GROUP);
		boolean status = element.isElementDisplayed(VERTEX_PURCHASE_SALES_TAX_GROUP);
		return status;
	}

	/**
	 * Verify "Vertex consumer use tax group" dropdown present
	 *
	 * @return
	 */
	public boolean verifyVertexConsumerUseTaxGroup( )
	{
		wait.waitForElementDisplayed(VERTEX_PURCHASE_SALES_TAX_GROUP);
		boolean status = element.isElementDisplayed(CONSUMER_USE_TAX_GROUP);
		return status;
	}

	/**
	 * Set "Vertex sales tax group"
	 *
	 * @param vertexSalesTaxGroup
	 */
	public void setVertexSalesTaxGroup( String vertexSalesTaxGroup )
	{
		wait.waitForElementDisplayed(SALES_TAX_GROUP);
		text.enterText(SALES_TAX_GROUP, vertexSalesTaxGroup);
	}

	/**
	 * Click on "Save" option
	 */
	public DFinanceHomePage clickOnSaveButton( )
	{
		click.clickElementCarefully(SAVE_BUTTON);
		DFinanceHomePage homePage = new DFinanceHomePage(driver);
		return homePage;
	}

	//TODO remove from here

	/**
	 * Click On New button
	 *
	 * @return
	 */
	public DFinanceAllSalesOrdersPage clickOnNewButton( )
	{
		wait.waitForElementDisplayed(NEW_BUTTON);
		wait.waitForElementEnabled(NEW_BUTTON);
		click.clickElement(NEW_BUTTON);
		DFinanceAllSalesOrdersPage salesOrderPage = new DFinanceAllSalesOrdersPage(driver);
		return salesOrderPage;
	}

	/**
	 * Enter created sales order
	 *
	 * @param salesOrder
	 *
	 * @return to sales order page
	 */
	public DFinanceAllSalesOrdersPage searchCreatedSalesOrder( String salesOrder )
	{
		wait.waitForElementDisplayed(FILTER_SEARCH_BOX);
		text.enterText(FILTER_SEARCH_BOX, salesOrder);
		click.clickElementCarefully(SALES_ORDER_DROPDOWN);
		waitForPageLoad();
		DFinanceAllSalesOrdersPage salesOrderPage = new DFinanceAllSalesOrdersPage(driver);
		return salesOrderPage;
	}

	/**
	 * Enter discount code in "Default discount code" field
	 *
	 * @param defaultDiscountCode
	 */
	public void setDefaultDiscountCode( String defaultDiscountCode )
	{
		try {
			expandHeader(ACCOUNT_RECEIVABLE);
			wait.waitForElementDisplayed(DEFAULTDISCOUNTCODE);
			wait.waitForElementEnabled(DEFAULTDISCOUNTCODE);
			click.clickElementCarefully(DEFAULTDISCOUNTCODE);
			text.enterText(DEFAULTDISCOUNTCODE, defaultDiscountCode);
		}
		catch(Exception ex)
		{
			VertexLogger.log("Error while attempting to set default Discount code to: "+defaultDiscountCode);
		}
	}

	/**
	 * toggle accrue tax button to yes or no
	 */
	public void toggleAccrueButton(boolean b )
	{
		expandHeader(ACCOUNT_PAYABLE);
		WebElement tog = driver.findElement(ACCRUE_TITLE);
		WebElement list = driver.findElement(ACCRUE_ENABLED);
		tog.getAttribute("title");
		if ( (b && tog
			.getAttribute("title")
			.equals("No")) || (!b && tog
			.getAttribute("title")
			.equals("Yes")) )
		{
			list.click();
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Advanced Tax Button to yes or no
	 */
	public void toggleAdvancedTaxGroup(boolean toggleState )
	{
		WebElement tog = driver.findElement(ADVANCED_TAX_TITLE);
		WebElement list = driver.findElement(ADVANCED_TAX_ENABLED);
		tog.getAttribute("title");
		if ( (toggleState && tog
				.getAttribute("title")
				.equals("No")) || (!toggleState && tog
				.getAttribute("title")
				.equals("Yes")) )
		{
			list.click();
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Require Manual Exchange Rate to yes or no
	 */
	public void toggleRequireManualExchangeRate(boolean toggleState )
	{
		WebElement tog = driver.findElement(REQUIRE_MANUAL_EXCHANGE_RATE_TITLE);
		WebElement list = driver.findElement(REQUIRE_MANUAL_EXCHANGE_RATE_ENABLED);
		tog.getAttribute("title");
		if ( (toggleState && tog
				.getAttribute("title")
				.equals("No")) || (!toggleState && tog
				.getAttribute("title")
				.equals("Yes")))
		{
			list.click();
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Require Manual Exchange Rate to yes or no
	 * @param toggleState
	 */
	public void toggleSendPostingRequestImmediately(boolean toggleState )
	{
		WebElement tog = driver.findElement(SEND_POSTING_REQUEST_IMMEDIATELY);
		WebElement list = driver.findElement(SEND_POSTING_REQUEST_IMMEDIATELY);
		tog.getAttribute("aria-checked");
		if ( (toggleState && tog
				.getAttribute("aria-checked")
				.equals("false")) || (!toggleState && tog
				.getAttribute("aria-checked")
				.equals("true")))
		{
			list.click();
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Enable Vertex For Accounts Receivable to yes or no
	 */
	public void toggleAccountsReceivable(boolean toggleState )
	{
		expandHeader(ACCOUNT_RECEIVABLE);
		WebElement tog = driver.findElement(ACCOUNTS_RECEIVABLE_TITLE);
		WebElement list = driver.findElement(ACCOUNTS_RECEIVABLE_ENABLED);
		tog.getAttribute("title");
		if ( (toggleState && tog
				.getAttribute("title")
				.equals("No")) || (!toggleState && tog
				.getAttribute("title")
				.equals("Yes")))
		{
			click.clickElementIgnoreExceptionAndRetry(list);
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Enable Fetch Multiple Registration IDs For Accounts Receivable to yes or no
	 */
	public void toggleFetchMultipleRegistrationIDs(boolean toggleState )
	{
		expandHeader(ACCOUNT_RECEIVABLE);
		WebElement tog = driver.findElement(ACCOUNTS_RECEIVABLE_FETCH_MULTIPLE_TITLE);
		WebElement list = driver.findElement(ACCOUNTS_RECEIVABLE_FETCH_MULTIPLE_ENABLED);
		tog.getAttribute("title");
		if ( (toggleState && tog
				.getAttribute("title")
				.equals("No")) || (!toggleState && tog
				.getAttribute("title")
				.equals("Yes")))
		{
			click.clickElementIgnoreExceptionAndRetry(list);
		}
		clickOnSaveButton();
	}
	/**
	 * toggle Retain Item Tax Group Override button to yes or no
	 */

	public void toggleRetainTaxGroupOverrideButton(boolean b )
	{
		expandHeader(ACCOUNT_RECEIVABLE);
		WebElement tog = driver.findElement(RETAIN_TAX_OVERRIDE_TITLE);
		WebElement list = driver.findElement(RETAIN_TAX_OVERRIDE_ENABLED);
		tog.getAttribute("title");
		if ( (b && tog
				.getAttribute("title")
				.equals("No")) || (!b && tog
				.getAttribute("title")
				.equals("Yes")) )
		{
			list.click();
		}
		clickOnSaveButton();
	}


	/**
	 * toggle accrue tax button to yes or no
	 */
	public void toggleTxBkUpGrp(boolean b )
	{
		wait.waitForElementDisplayed(RETAIL_TAB);
		expandHeader(RETAIL_TAB);
		try
		{
			Thread.sleep(2000);
		}
		catch ( InterruptedException e )
		{
			e.printStackTrace();
		}
		WebElement tog = driver.findElement(BKUPTX_TITLE);
		WebElement list = driver.findElement(BKUPTX_ENABLED);
		tog.getAttribute("title");
		if ( (b && tog
			.getAttribute("title")
			.equals("No")) || (!b && tog
			.getAttribute("title")
			.equals("Yes")) )
		{
			list.click();
		}
		waitForPageLoad();
	}

	/**
	 * enter retail back up tax interval
	 */
	public void enterInterval( String string )
	{
		if(element.isElementDisplayed(RETAIL_TAB_NOT_EXPANDED)){
			wait.waitForElementDisplayed(RETAIL_TAB);
			expandHeader(RETAIL_TAB);
		}
		wait.waitForElementDisplayed(BKUPTX_INTERVAL);
		click.clickElementCarefully(BKUPTX_INTERVAL);
		text.clearText(BKUPTX_INTERVAL);
		text.enterText(BKUPTX_INTERVAL, string);
		waitForPageLoad();
	}

	/**
	 * Enter "Default delivery term, Canada to US"
	 * @param deliveryTerm
	 */
	public void enterCanadaToUSDeliveryTerm(String deliveryTerm) {
		if(element.isElementDisplayed(RETAIL_TAB_NOT_EXPANDED)){
			wait.waitForElementDisplayed(RETAIL_TAB);
			expandHeader(RETAIL_TAB);
		}

		wait.waitForElementDisplayed(CANADA_TO_US_DELIVERY_TERM);
		text.selectAllAndInputText(CANADA_TO_US_DELIVERY_TERM, deliveryTerm);
		text.pressTab(CANADA_TO_US_DELIVERY_TERM);
	}

	/**
	 * Enter "Default delivery term, US to Canada"
	 * @param deliveryTerm
	 */
	public void enterUSToCanadaDeliveryTerm(String deliveryTerm) {
		if(element.isElementDisplayed(RETAIL_TAB_NOT_EXPANDED)){
			wait.waitForElementDisplayed(RETAIL_TAB);
			expandHeader(RETAIL_TAB);
		}

		wait.waitForElementDisplayed(US_TO_CANADA_DELIVERY_TERM);
		text.selectAllAndInputText(US_TO_CANADA_DELIVERY_TERM, deliveryTerm);
		text.pressTab(US_TO_CANADA_DELIVERY_TERM);
	}

	/**
	 * validate error message
	 */
	public String validateErrorMsg( )
	{
		click.clickElementCarefully(SAVE_BUTTON);
		wait.waitForElementDisplayed(ERROR_MSG);
		String errorMsg = attribute.getElementAttribute(ERROR_MSG, "title");
		return  errorMsg;
	}

	/**
	 * Verify "Date for sales order quote calculation" parameter
	 */
	public boolean verifySalesOrderQuoteCalcParameter()
	{
		return element.isElementDisplayed(QUOTE_DATE);
	}

	/**
	 * This Method changes Sales Order Quote Date
	 * @param date
	 */
	public void changeSalesOrderQuoteDate(String date)
	{
		click.clickElementCarefully(QUOTE_DATE_DROPDOWN);
		WebElement countryUL= wait.waitForElementDisplayed(QUOTE_DATE_INPUT);
		List<WebElement> optionsList =countryUL.findElements(By.tagName("li"));
		for (WebElement li : optionsList) {

			if (li.getText().contains(date)) {
				li.click();
			}
		}
	}

	/**
	 * toggle Address Cleansing button to yes or no
	 */

	public void toggleAddressCleansingButton(boolean b )
	{
		WebElement tog = driver.findElement(ADD_CLEANSING_TITLE);
		WebElement list = driver.findElement(ADD_CLEANSING_ENABLED);
		tog.getAttribute("title");
		if ( (b && tog
				.getAttribute("title")
				.equals("No")) || (!b && tog
				.getAttribute("title")
				.equals("Yes")) )
		{
			list.click();
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Amounts include sales tax yes or no
	 * @param b
	 */

	public void toggleAmountsIncludeSalesTaxButton(boolean b )
	{
		WebElement tog = wait.waitForElementPresent(AMOUNTS_INCLUDE_SALES_TAX_TITLE);
		String isEnabled = tog.getAttribute("title");
		if(b)
		{
			if(isEnabled.equals("No"))
			{
				scroll.scrollElementIntoView(AMOUNTS_INCLUDE_SALES_TAX_ENABLED);
				click.clickElementCarefully(AMOUNTS_INCLUDE_SALES_TAX_ENABLED);
			}
		}
		clickOnSaveButton();
	}

	/**
	 * toggle Enable logging button yes or no
	 * @param loggingState
	 */
	public void toggleEnableLoggingButton(boolean loggingState )
	{
		WebElement tog = driver.findElement(ENABLE_LOGGING_TITLE);
		WebElement list = driver.findElement(ENABLE_LOGGING_ENABLED);

		try {

			tog.getAttribute("title");
			if ((loggingState && tog
					.getAttribute("title")
					.equals("No")) || (!loggingState && tog
					.getAttribute("title")
					.equals("Yes"))) {
				list.click();
			}
			clickOnSaveButton();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			tog.getAttribute("title");
			if ((loggingState && tog
					.getAttribute("title")
					.equals("No")) || (!loggingState && tog
					.getAttribute("title")
					.equals("Yes"))) {
				list.click();
			}
			clickOnSaveButton();
		}
	}

	/**
	 * Filters the Journal Name
	 * @param journalNameType
	 */
	public void filterJournalName(String journalNameType){
		WebElement filterEle = wait.waitForElementEnabled(FILTER);
		text.setTextFieldCarefully(filterEle, journalNameType, false);
		filterEle.sendKeys(Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Filters and enables journal name
	 * @param b
	 * @param journalNameType
	 */
	public void filterJournalNameAndEnableAmountSalesTax(boolean b, String journalNameType){
		filterJournalName(journalNameType);
		toggleAmountsIncludeSalesTaxButton(b);
	}
}