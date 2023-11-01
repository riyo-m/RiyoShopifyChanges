package com.vertex.quality.connectors.salesforce.pages.cpq;

import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.enums.Constants;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.crm.SalesForceCRMPostLogInPage;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.testng.Assert.assertEquals;

/**
 * Common functions for anything related to Salesforce Quote Page in CPQ Plugin.
 *
 * @author
 */
public class SalesForceCPQQuotePage extends SalesForceBasePage
{
	protected By CREATE_ORDER_BUTTON = By.xpath("//*[@id='topButtonRow']/input[@name='sbqq__createorderfromquote']");
	protected By LOADING_IMAGE = By.cssSelector("#lineItemView_loading[style*='block']");
	protected By NEW_NEXT_TO_RECENT_OPORTUNITIES = By.cssSelector("input[name = 'new']");

	protected By PRIMARY_CHECKBOX = By.xpath("//label[text()='Primary']/../../following-sibling::td//input");
	protected By OPP_NAME = By.xpath(
		"//label[text()='Opportunity']/../../following-sibling::td//span[@class='lookupInput']//input");
	protected By ACCOUNT_NAME = By.xpath(
		"//label[text()='Account']/../../following-sibling::td//span[@class='lookupInput']//input");
	protected By MASTER_ACCOUNT = By.xpath(
		"//label[contains(text(),'Master')]/../../following-sibling::td//span[@class='lookupInput']//input");
	protected By SUBSCRIPTION_TERM = By.xpath("//label[text()='Subscription Term']/../../following-sibling::td//input");
	protected By DISCOUNT_PERCENT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountPercent')]//parent::td/following-sibling::td/input");
	protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountAmount')]//parent::td/following-sibling::td/input");

	protected By PHYSICAL_LOCATION = By.xpath("//label[text()='Physical Locations']/../..//select");

	protected By AMOUNT = By.id("opp7");
	protected By STAGE = By.id("opp11");
	protected By QUOTE_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");

	protected By PRICEBOOK_DROPDOWN = By.xpath("//*[@id='select']/select");
	protected By PRICEBOOK_DROPDOWN_MODAL = By.id("select");
	protected By PRICEBOOK_SAVE = By.xpath(
		"//*[@id='footer']/paper-button[contains(@class, 'sb-pricebook-dialog')]/*[contains(text(), 'Save')]");
	protected By ADD_PRODUCT_BTN = By.id("mainButton");
	protected By BY_KEY_WORD = By.xpath(
		"//sb-page-container/div/sb-product-lookup/sb-page-header/header/div[1]/div[3]/sb-search-bar/span//*[@id='itemLabel']");
	protected By PRODUCT_SEARCH_BTN = By.xpath(
		"//sb-page-container/div/sb-product-lookup/sb-page-header/header/div//*[@id='buttons']//*[@id='search']");
	protected By PRODUCT_SELECT_BTN = By.xpath("//*[@id='plSelect']/sb-i18n");
	protected By SALES_PRICE = By.cssSelector("input[id*='UnitPrice']");
	protected By QUANTITY = By.cssSelector("input[id*='Quantity']");
	protected By PRODUCT_SAVE_BTN = By.xpath("//*[@id='mainButton' and text()='Save']");
	protected By QUOTE_LINE_QUICK_SAVE_BUTTON = By.xpath("//*[@id='mainButton' and text()='Quick Save']");
	protected By QUOTE_CALCULATE_BUTTON = By.xpath("//*[@id='mainButton' and text()='Calculate']");
	protected By QUOTE_CANCEL_BUTTON = By.xpath("//*[@id='mainButton' and text()='Cancel']");
	protected By BUTTON_EDIT_LINES = By.xpath("//input[@title='Edit Lines']");
	protected By EDIT_QUOTE_BUTTON = By.xpath("//input[@title='Edit']");
	protected By QLE_QUOTE_ID = By.xpath("//*[@id=\"headerSubtitle\"]");
	protected By OPPORTUNITY_NAME = By.xpath(".//table/tbody/tr/td[5]/div/a/span");
	protected By QUOTE_ID_FIRST_LINE = By.xpath(".//table/tbody/tr/td[4]/div/a/span");
	protected By ORDERED_CHECKBOX = By.xpath(".//table//td/span/label[text() = 'Ordered']/../../following-sibling::td/input");

	protected By SHIP_TO_STREET_INPUT = By.xpath(
		".//td/span/label[text()='Ship To Street']/../parent::td/following-sibling::td/textarea");
	protected By SHIP_TO_CITY_INPUT = By.xpath(
		".//td/span/label[text()='Ship To City']/../parent::td/following-sibling::td/input");
	protected By SHIP_TO_STATE_INPUT = By.xpath(
		".//td/span/label[text()='Ship To State']/../parent::td/following-sibling::td/input");
	protected By SHIP_TO_POSTAL_CODE_INPUT = By.xpath(
		".//td/span/label[text()='Ship To Postal Code']/../parent::td/following-sibling::td/input");
	protected By SHIP_TO_COUNTRY_INPUT = By.xpath(
		".//td/span/label[text()='Ship To Country']/../parent::td/following-sibling::td/input");

	protected By BILL_TO_STREET_INPUT = By.xpath(
		".//td/span/label[text()='Bill To Street']/../parent::td/following-sibling::td/textarea");
	protected By BILL_TO_CITY_INPUT = By.xpath(
		".//td/span/label[text()='Bill To City']/../parent::td/following-sibling::td/input");
	protected By BILL_TO_STATE_INPUT = By.xpath(
		".//td/span/label[text()='Bill To State']/../parent::td/following-sibling::td/input");
	protected By BILL_TO_POSTAL_CODE_INPUT = By.xpath(
		".//td/span/label[text()='Bill To Postal Code']/../parent::td/following-sibling::td/input");
	protected By BILL_TO_COUNTRY_INPUT = By.xpath(
		".//td/span/label[text()='Bill To Country']/../parent::td/following-sibling::td/input");

	protected By QUOTE_LINE_TABLE = By.xpath("//div[@class='bRelatedList first']/div//div[@class='pbBody']/table");
	protected By QUOTE_LINE_TABLE_HEADER_ROW = By.className("headerRow");

	protected By QUOTE_NUMBER_LINK = By.xpath("//div/a[contains(text(), 'Q-')]");

	protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");
	protected By VIEW_Q_LINK = By.xpath("//form/div/div/div/a/span[text()='Q']");
	protected By VIEW_OTHER_LINK = By.xpath("//form/div/div/div/a/span[text()='Other']");
	protected By DELETE_BUTTON = By.name("del");

	protected By DELETE_QUOTE_LINK = By.xpath(".//h3[text()='Quotes']/../../../../../../..//table/tbody/tr/td/a[contains(@title, 'Delete ')]");

	protected By ORDER_NUMBER_LINK = By.xpath(".//table/tbody/tr/th/a[starts-with(text(), '000')]");

	protected By TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Tax Codes']/following-sibling::td/div");
	protected By VERTEX_TAX_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Vertex Tax Codes']/following-sibling::td/div");
	protected By INVOICE_TEXT_CODES = By.xpath(".//h3[text()='VAT Return Fields']/../following-sibling::div/table//td[text()='Invoice Text Codes']/following-sibling::td/div");

	SalesForceCRMPostLogInPage postLogInPage = new SalesForceCRMPostLogInPage(driver);
	JavascriptExecutor js;

	public SalesForceCPQQuotePage( WebDriver driver )
	{
		super(driver);
	}


	/**
	 * Click on New button
	 */
	public void clickOnNewButton( )
	{
		wait.waitForElementDisplayed(NEW_NEXT_TO_RECENT_OPORTUNITIES);
		click.clickElement(NEW_NEXT_TO_RECENT_OPORTUNITIES);
		waitForPageLoad();
	}

	/**
	 * Check Primary Checkbox
	 */
	public void checkPrimaryCheckbox( )
	{
		wait.waitForElementDisplayed(PRIMARY_CHECKBOX);
		click.clickElement(PRIMARY_CHECKBOX);
	}

	/**
	 * Set Opportunity name
	 *
	 * @param oppName
	 */
	public void setOppName( String oppName )
	{
		wait.waitForElementDisplayed(OPP_NAME);
		text.enterText(OPP_NAME, oppName);
	}

	/**
	 * Select Available Account Name
	 *
	 * @param account_name
	 */
	public void selectOppAccountName( String account_name )
	{
		wait.waitForElementDisplayed(ACCOUNT_NAME);
		text.enterText(ACCOUNT_NAME, account_name);
	}

	/**
	 * Select Master Account Name
	 *
	 * @param master_account
	 */
	public void setMasterAccount( String master_account )
	{
		wait.waitForElementDisplayed(MASTER_ACCOUNT);
		text.enterText(MASTER_ACCOUNT, master_account);
	}

	/**
	 * Select Subscription Term
	 *
	 * @param term
	 */
	public void SetSubscriptionTerm( String term )
	{
		wait.waitForElementDisplayed(SUBSCRIPTION_TERM);
		text.enterText(SUBSCRIPTION_TERM, term);
	}

	/**
	 * Set discount percent
	 * @param discountPercent
	 */
	public void setDiscountPercent(String discountPercent)
	{
		if (discountPercent != "") {
			wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
			text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
		}
	}

	/**
	 * Set discount amount
	 * @param discountAmount
	 */
	public void setDiscountAmount(String discountAmount)
	{
		if (discountAmount != "") {
			wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
			text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
		}
	}

	/**
	 * Select Price Book by Name
	 *
	 * @param priceBook
	 */
	public void choosePriceBook( String priceBook )
	{
		wait.waitForElementDisplayed(PRICEBOOK_DROPDOWN_MODAL);
		dropdown.selectDropdownByDisplayName(PRICEBOOK_DROPDOWN, priceBook);
		click.javascriptClick(PRICEBOOK_SAVE);
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Add Product button
	 */
	public void clickOnAddProduct( )
	{
		waitForSalesForceLoaded();
		traverseQLEShadowDomAndClickButton("Add Products");
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Set search keyword
	 *
	 * @param productName
	 */
	public void setSearchKeyword( String productName )
	{
		waitForSalesForceLoaded();
		WebElement root1 = driver.findElement(By.xpath("//sb-page-container"));
		WebElement shadowDom1 = expandRootElement(root1);
		WebElement root2 = shadowDom1.findElement(By.tagName("sb-product-lookup"));
		WebElement shadowDom2 = expandRootElement(root2);
		WebElement root3 = shadowDom2.findElement(By.tagName("sb-search-bar"));
		WebElement shadowDom3 = expandRootElement(root3);
		WebElement root4 = shadowDom3.findElement(By.tagName("sb-typeahead"));
		WebElement shadowDom4 = expandRootElement(root4);
		WebElement productSearchInput = shadowDom4.findElement(By.cssSelector("span > iron-input > input[placeholder='Search Products']"));
		text.enterText(productSearchInput, productName);
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Search button
	 */
	public void clickOnProductSearchButton( )
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		WebElement root1 = driver.findElement(By.xpath("//sb-page-container"));
		WebElement shadowDom1 = expandRootElement(root1);
		WebElement root2 = shadowDom1.findElement(By.tagName("sb-product-lookup"));
		WebElement shadowDom2 = expandRootElement(root2);
		WebElement root3 = shadowDom2.findElement(By.tagName("sb-search-bar"));
		WebElement shadowDom3 = expandRootElement(root3);
		WebElement searchButton = shadowDom3.findElement(By.cssSelector("span > div.searchActions > paper-button#search"));
		wait.waitForElementDisplayed(searchButton);
		click.clickElement(searchButton);
		waitForSalesForceLoaded();
	}

	/**
	 * Select the check box displayed in search result
	 */
	public void selectProduct( )
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		WebElement root1 = driver.findElement(By.xpath("//sb-page-container"));
		WebElement shadowDom1 = expandRootElement(root1);
		WebElement root2 = shadowDom1.findElement(By.tagName("sb-product-lookup"));
		WebElement shadowDom2 = expandRootElement(root2);
		WebElement root3 = shadowDom2.findElement(By.tagName("sb-lookup-layout"));
		WebElement shadowDom3 = expandRootElement(root3);
		WebElement root4 = shadowDom3.findElement(By.tagName("sb-table-header"));
		WebElement shadowDom4 = expandRootElement(root4);
		WebElement root5 = shadowDom4.findElement(By.cssSelector("div > sb-group#selection"));
		WebElement shadowDom5 = expandRootElement(root5);
		WebElement root6 = shadowDom5.findElement(By.tagName("sb-table-cell-select"));
		WebElement shadowDom6 = expandRootElement(root6);
		WebElement selectProductCheckbox = shadowDom6.findElement(By.cssSelector("div > paper-checkbox#checkbox"));
		wait.waitForElementDisplayed(selectProductCheckbox);
		click.javascriptClick(selectProductCheckbox);
		waitForPageLoad();
	}

	/**
	 * Click on Select button
	 */
	public void clickOnSelectButton( )
	{
		waitForSalesForceLoaded();
		WebElement root1 = driver.findElement(By.xpath("//sb-page-container"));
		WebElement shadowDom1 = expandRootElement(root1);
		WebElement root2 = shadowDom1.findElement(By.tagName("sb-product-lookup"));
		WebElement shadowDom2 = expandRootElement(root2);
		jsWaiter.sleep(5000);
		WebElement productSelectButton = shadowDom2.findElement(By.cssSelector("sb-page-header > div > paper-button#plSelect"));
		wait.waitForElementDisplayed(productSelectButton);
		click.clickElement(productSelectButton);
		waitForPageLoad();
	}

	/**
	 * Set Sales Price
	 *
	 * @param productName
	 * @param salesPrice
	 */
	public void setSalesPrice( String productName, String salesPrice )
	{
		setFieldToEdit(productName, "SBQQ__ListPrice__c", salesPrice);
	}

	/**
	 * Set Quantity
	 *
	 * @param productName
	 * @param quantity
	 */
	public void setQuantity( String productName, String quantity )
	{
		setFieldToEdit(productName, "SBQQ__Quantity__c", quantity);
	}

	/**
	 * Set Discount Percent
	 *
	 * @param productName
	 * @param discountPercent
	 */
	public void setQuoteLineDiscountPercent( String productName, String discountPercent )
	{
		waitForPageLoad();
		setFieldToEdit(productName, "VTX_DiscountPercent__c", discountPercent);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Discount Amount
	 *
	 * @param productName
	 * @param discountAmount
	 */
	public void setQuoteLineDiscountAmount( String productName, String discountAmount )
	{
		waitForPageLoad();
		setFieldToEdit(productName, "VTX_DiscountAmount__c", discountAmount);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Product Address
	 *
	 * @param productName
	 * @param address1
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public void setProductLineAddress( String productName, String address1, String city, String state, String zip,
		String country )
	{
		setFieldToEdit(productName, "VTX_Address_1__c", address1);
		setFieldToEdit(productName, "VTX_City__c", city);
		setFieldToEdit(productName, "VTX_State__c", state);
		setFieldToEdit(productName, "VTX_Zip__c", zip);
		setFieldToEdit(productName, "VTX_Country__c", country);
	}

	/**
	 * set field to edit on product add page
	 *
	 * @param productName
	 * @param columnName
	 * @param value
	 */
	public void setFieldToEdit( String productName, String columnName, String value )
	{
		WebElement row = traverseQLEShadowDomForRowWithMatchingProductName(productName);
		hover.hoverOverElement(row);
		WebElement rowShadowDom = expandRootElement(row);
		String pencilEditLocator = String.format("div > div[field='%s'] > div > span.pencil", columnName);
		WebElement editPencilSelector = rowShadowDom.findElement(By.cssSelector(pencilEditLocator));
		click.javascriptClick(editPencilSelector);
		waitForSalesForceLoaded();
		String fieldInputLocator = String.format("div > div[field='%s'] > div > div > sb-input", columnName);
		WebElement fieldInputRoot = rowShadowDom.findElement(By.cssSelector(fieldInputLocator));
		WebElement fieldInputShadowDom = expandRootElement(fieldInputRoot);
		WebElement fieldInput = fieldInputShadowDom.findElement(By.cssSelector("iron-input > input"));
		fieldInput.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
		text.selectAllAndInputText(fieldInput, value);
		text.pressEnter(fieldInput);
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Save button of Quote Line Editor
	 * Traverses Shadow DOM introduced as part of Salesforce CPQ 242.2 (Spring 23')
	 */
	public void clickPrdSaveButton( )
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		traverseQLEShadowDomAndClickButton("Save");
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Bypass CPQ QLE page for billing tests
	 * Quote Line Editoe page is not necessary step for Billing test cases
	 */
	public void bypassQLEPageForBilling()
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		String quoteId = getQuoteIdFromUrl();
		driver.get("https://d2e000001fwtvuak-dev-ed.my.salesforce.com/" + quoteId);
	}

	/**
	 * Get SF quote ID from url
	 */
	public String getQuoteIdFromUrl(){
		String fullUrl = driver.getCurrentUrl();
		int startIndex = fullUrl.lastIndexOf("qId=") + 4;
		int endIndex = fullUrl.length();
		String quoteId = fullUrl.substring(startIndex, endIndex);
		return quoteId;
	}

	/**
	 * Re-click Save button on quote line editor page
	 */
	public void clickPrdSaveButtonAgain()
	{
		waitForSalesForceLoaded(5000);
		if(element.isElementDisplayed(PRODUCT_SAVE_BTN)){
			clickPrdSaveButton();
		}
	}

	/**
	 * Click Edit Lines on Quote to get to Product Add/Edit Page
	 */
	public void clickEditLines( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(BUTTON_EDIT_LINES);
		click.clickElement(BUTTON_EDIT_LINES);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Amount
	 *
	 * @param amount
	 */
	public void setAmount( String amount )
	{
		wait.waitForElementDisplayed(AMOUNT);
		text.enterText(AMOUNT, amount);
	}

	/**
	 * Set Stage Name
	 *
	 * @param stageName
	 */
	public void setStage( String stageName )
	{
		wait.waitForElementDisplayed(STAGE);
		dropdown.selectDropdownByDisplayName(STAGE, stageName);
	}

	/**
	 * Click on Save button
	 */
	public void clickQuoteSaveButton( )
	{
		wait.waitForElementDisplayed(QUOTE_SAVE_BUTTON);
		click.clickElement(QUOTE_SAVE_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Click Quick Save button on quote line editor page
	 */
	public void clickQuoteQuickSaveButton( )
	{
		waitForSalesForceLoaded();
		traverseQLEShadowDomAndClickButton("Quick Save");
		waitForSalesForceLoaded();
	}

	/**
	 * Click Calculate button on quote line editor page
	 */
	public void clickQuoteCalculateButton( )
	{
		traverseQLEShadowDomAndClickButton("Calculate");
		waitForSalesForceLoaded();
	}

	/**
	 * Click Cancel button on quote line editor page
	 */
	public void clickQuoteCancelButton( )
	{
		waitForSalesForceLoaded();
		traverseQLEShadowDomAndClickButton("Cancel");
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Create Order button
	 */
	public void createOrder( )
	{
		wait.waitForElementDisplayed(CREATE_ORDER_BUTTON);
		click.clickElement(CREATE_ORDER_BUTTON);
	}

	/**
	 * Check the Ordered checkbox to automatically create Order from Quote
	 */
	public void createOrderViaOrderedCheckbox( )
	{
		setOrderedCheckbox(true);
		clickQuoteSaveButton();
	}

	/**
	 * Set the ordered checkbox on quote
	 * @param isChecked
	 */
	public void setOrderedCheckbox(boolean isChecked)
	{
		wait.waitForElementDisplayed(ORDERED_CHECKBOX);
		checkbox.setCheckbox(ORDERED_CHECKBOX, isChecked);
	}

	/**
	 * Click on Edit button for quote
	 */
	public void clickQuoteEditButton( )
	{
		wait.waitForElementDisplayed(EDIT_QUOTE_BUTTON);
		click.clickElement(EDIT_QUOTE_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * To Create new quote with below parameters
	 *
	 * @param oppName
	 * @param accountName
	 * @param master
	 * @param term
	 */
	public void createNewQuote( String oppName, String accountName, String master, String term )
	{
		createNewQuote(oppName, accountName, master, term, "", "");
	}

	/**
	 * To Create new quote with addition discount parameters
	 *
	 * @param oppName
	 * @param accountName
	 * @param master
	 * @param term
	 * @param discountPercent
	 * @param discountAmount
	 */
	public void createNewQuote( String oppName, String accountName, String master, String term, String discountPercent, String discountAmount )
	{
		postLogInPage.clickSalesPageATab(NavigateMenu.Sales.QUOTES_TAB.text);
		clickOnNewButton();
		waitForSalesForceLoaded();
		checkPrimaryCheckbox();
		setOppName(oppName);
		waitForPageLoad();
		selectOppAccountName(accountName);
		setMasterAccount(master);
		SetSubscriptionTerm(term);
		setDiscountPercent(discountPercent);
		setDiscountAmount(discountAmount);
		clickQuoteSaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * To Create new quote with below parameters
	 *
	 * @param oppName
	 * @param accountName
	 * @param master
	 * @param term
	 */
	public void editNewQuote( String oppName, String accountName, String master, String term )
	{
		waitForSalesForceLoaded();
		checkPrimaryCheckbox();
		setOppName(oppName);
		waitForPageLoad();
		selectOppAccountName(accountName);
		setMasterAccount(master);
		SetSubscriptionTerm(term);
		clickQuoteSaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * Add Product to the Quote created
	 *
	 * @param productName
	 * @param price
	 * @param quantity
	 */
	public void addProductToQuote( String productName, String price, String quantity )
	{
		waitForSalesForceLoaded(6000);
		waitForPageLoad();
		clickOnAddProduct();
		setSearchKeyword(productName);
		clickOnProductSearchButton();
		selectProduct();
		clickOnSelectButton();
		waitForSalesForceLoaded();
		waitForPageLoad();
		setSalesPrice(productName, price);
		setQuantity(productName, quantity);
		waitForSalesForceLoaded();
	}

	/**
	 * Add Product to the Opportunity created
	 *
	 * @param productName
	 * @param price
	 * @param quantity
	 */
	public void updateProductInQuote( String productName, String price, String quantity )
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		jsWaiter.sleep(5000);
		setSalesPrice(productName, price);
		setQuantity(productName, quantity);
	}

	/**
	 * Get Quote id on QLE page
	 * @return
	 */
	public String getQuoteId()
	{
		waitForSalesForceLoaded(5000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String quoteId = js.executeScript("return document.querySelector('#sbPageContainer').shadowRoot.querySelector('div').querySelector('sb-line-editor').shadowRoot.querySelector('#qlEditorContainer').querySelector('#lineEditorPageHeader').querySelector('h2#headerSubtitle').innerHTML;").toString();
		return quoteId;

	}

	/**
	 * get tax amount from product display for specific product
	 *
	 * @param productName
	 */
	public double getProductTaxAmount( String productName )
	{
		waitForSalesForceLoaded();
		jsWaiter.sleep(10000);
		return getProductTaxAmount( productName, true );
	}

	/**
	 * get tax amount from product display for specific product in quote
	 *
	 * @param productName product name to get tax amount for
	 * @param refreshPage boolean decides whether page needs to be refreshed
	 */
	public double getProductTaxAmount( String productName, Boolean refreshPage)
	{
		WebElement tableRow = traverseQLEShadowDomForRowWithMatchingProductName(productName);
		String getTaxValue = "";
		int i = 0;
		while (getTaxValue == "" && i < 5) {
			WebElement rowData = expandRootElement(tableRow);
			getTaxValue = rowData.findElement(By.cssSelector("div>div:nth-child(7)>div")).getText();
			VertexLogger.log("Line Item Tax: " + getTaxValue);
			if (getTaxValue == "" && refreshPage)
				refreshPage();
			i++;
		}
		if(getTaxValue.length() > 4)
			getTaxValue = getTaxValue.substring(4);
		double taxAmount = VertexCurrencyUtils.cleanseCurrencyString(getTaxValue);
		return taxAmount;
	}

	/**
	 * Get index of table row on quote line editor page for specified product name
	 * @param productName product name used to locate table row
	 * @return index of table row corresponding to product name
	 */
	public int getTableRowIndex(String productName){
		List<WebElement> tableRows = traverseQLEShadowDomForTableRows();
		int rowIndex = 0;
		for (WebElement row : tableRows){
			WebElement tableRowShadowDom = expandRootElement(row);
			String name = "";
			try {
				name = tableRowShadowDom.findElement(By.cssSelector("div>div>div:nth-child(3)>div")).getText();
			}
			catch (NoSuchElementException ex){
				ex.printStackTrace();
			}
			if (name.equals(productName)){
				rowIndex = Integer.parseInt(tableRowShadowDom.findElement(By.cssSelector("div>div>div#itemNumber>span")).getText()) - 1;
				break;
			}
		}
		return rowIndex;
	}

	/**
	 * Expand Shadow Root element for any web element
	 * Shadow DOM introduced as part of Salesforce CPQ 242.2 (Spring 23')
	 * @param shadowHost web element right before shadow root, also known as shadow host
	 * @return web element representing expanded shadow root
	 */
	public WebElement expandRootElement(WebElement shadowHost){
		WebElement returnObj = null;
		Object shadowRoot = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadowHost);
		Map<String, Object> shadowRootMap = (Map<String, Object>) shadowRoot;
		String shadowRootKey = (String) shadowRootMap.keySet().toArray()[0];
		String id = (String) shadowRootMap.get(shadowRootKey);
		RemoteWebElement remoteWebElement = new RemoteWebElement();
		remoteWebElement.setParent((RemoteWebDriver) driver);
		remoteWebElement.setId(id);
		returnObj = remoteWebElement;
		return returnObj;
	}

	/**
	 * Traverses multiple levels of QLE shadow DOM to locate the quote line products table
	 * Shadow DOM introduced as part of Salesforce CPQ 242.2 (Spring 23')
	 * @return web element corresponding to start of quote line product table
	 */
	public WebElement traverseQLEShadowDomForTable(){
		waitForSalesForceLoaded();

		WebElement root1 = driver.findElement(By.xpath(".//sb-page-container"));
		WebElement shadowDom1 = expandRootElement(root1);
		jsWaiter.sleep(5000);
		WebElement root2 = shadowDom1.findElement(By.tagName("sb-line-editor"));
		WebElement shadowDom2 = expandRootElement(root2);
		WebElement root3 = shadowDom2.findElement(By.tagName("sb-le-group-layout"));
		WebElement shadowDom3 = expandRootElement(root3);
		WebElement root4 = shadowDom3.findElement(By.tagName("sb-le-group"));
		WebElement shadowDom4 = expandRootElement(root4);
		WebElement root5 = shadowDom4.findElement(By.tagName("sf-standard-table"));
		WebElement shadowDom5 = expandRootElement(root5);
		return shadowDom5;
	}

	/**
	 * Traverses QLE shadow dom to find all quote line rows
	 * @return list of web elements corresponding to each row in quote line product table
	 */
	public List<WebElement> traverseQLEShadowDomForTableRows(){
		waitForSalesForceLoaded();
		WebElement table = traverseQLEShadowDomForTable();
		List<WebElement> rows = table.findElements(By.tagName("sf-le-table-row"));
		return rows;
	}

	/**
	 * Traverses QLE shadow dom to find quote line with specific product name
	 * @param productName product name used to locate proper row in table
	 * @return web element representing the table row containing product name
	 */
	public WebElement traverseQLEShadowDomForRowWithMatchingProductName(String productName){
		waitForSalesForceLoaded();
		int rowNumber = getTableRowIndex(productName);
		List<WebElement> tableRows = traverseQLEShadowDomForTableRows();
		WebElement row = tableRows.get(rowNumber);
		waitForSalesForceLoaded();
		return row;
	}

	/**
	 * Traverses QLE shadow dom to find button with specific name
	 * @param buttonName name of button to click in header of QLE page
	 */
	@SneakyThrows
	public void traverseQLEShadowDomAndClickButton(String buttonName){
		waitForSalesForceLoaded();
		jsWaiter.sleep(5000);
		WebElement root1 = driver.findElement(By.xpath("//sb-page-container"));
		WebElement shadowDom1 = expandRootElement(root1);
		WebElement root2 = shadowDom1.findElement(By.tagName("sb-line-editor"));
		WebElement shadowDom2 = expandRootElement(root2);
		jsWaiter.sleep(5000);
		String cssPathToButton = String.format("div#qlEditorContainer>sb-page-header#lineEditorPageHeader>div#actions>sb-custom-action[name='%s']", buttonName);
		WebElement root3 = shadowDom2.findElement(By.cssSelector(cssPathToButton));
		WebElement shadowDom3 = expandRootElement(root3);
		shadowDom3.findElement(By.id("mainButton")).click();
	}

	/**
	 * Edit Address on quote page based on Address Type
	 *
	 * @param street
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 * @param addressType
	 */
	public void editQuoteAddress( String street, String city, String state, String postalCode, String country,
		String addressType )
	{
		clickQuoteEditButton();
		waitForPageLoad();
		setStreet(street, addressType);
		setCity(city, addressType);
		setState(state, addressType);
		setPostalCode(postalCode, addressType);
		setCountry(country, addressType);
		clickQuoteSaveButton();
	}

	/**
	 * Set Street Value based on AddressType
	 *
	 * @param street
	 * @param addressType
	 */
	private void setStreet( String street, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_STREET_INPUT, street);
		}
		else
		{
			text.enterText(SHIP_TO_STREET_INPUT, street);
		}
	}

	/**
	 * Set City Value based on AddressType
	 *
	 * @param city
	 * @param addressType
	 */
	private void setCity( String city, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_CITY_INPUT, city);
		}
		else
		{
			text.enterText(SHIP_TO_CITY_INPUT, city);
		}
	}

	/**
	 * Set State Value based on AddressType
	 *
	 * @param state
	 * @param addressType
	 */
	private void setState( String state, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_STATE_INPUT, state);
		}
		else
		{
			text.enterText(SHIP_TO_STATE_INPUT, state);
		}
	}

	/**
	 * Set Postal Code Value based on AddressType
	 *
	 * @param postalCode
	 * @param addressType
	 */
	private void setPostalCode( String postalCode, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_POSTAL_CODE_INPUT, postalCode);
		}
		else
		{
			text.enterText(SHIP_TO_POSTAL_CODE_INPUT, postalCode);
		}
	}

	/**
	 * Set Country Value based on AddressType
	 *
	 * @param country
	 * @param addressType
	 */
	private void setCountry( String country, String addressType )
	{
		if ( addressType.equals(Constants.AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILL_TO_COUNTRY_INPUT, country);
		}
		else
		{
			text.enterText(SHIP_TO_COUNTRY_INPUT, country);
		}
	}

	/**
	 * Delete quote line based on product name
	 *
	 * @param productName
	 */
	public void deleteQuoteLine( String productName )
	{
		String quoteLine = String.format(".//a[text()='%s']/../../td/a[text()='Del']", productName);
		wait.waitForElementDisplayed(By.xpath(quoteLine));
		click.clickElement(By.xpath(quoteLine));
		alert.acceptAlert();
		waitForPageLoad();
	}

	/**
	 * After Creating quote, we have to validate the input.
	 *
	 * @param quoteDetail
	 *
	 * @return
	 */
	public String getEachQuoteDetails( String quoteDetail )
	{
		waitForPageLoad();
		String quoteRow = String.format("//td[starts-with(text(), '%s')]/following-sibling::td[1]//div", quoteDetail);
		wait.waitForElementDisplayed(By.id("Name_ileinner"));
		String quoteRowValue = text.getElementText(By.xpath(quoteRow));
		jsWaiter.sleep(3000);
		return quoteRowValue;
	}

	/**
	 * To validate the tax values, we have to remove the $ symbol from the text
	 * displayed
	 *
	 * @param OppDetail
	 *
	 * @return
	 */
	public double getTaxValues( String OppDetail )
	{
		double taxAmount = 0.0;
		String taxValue = getEachQuoteDetails(OppDetail);
		taxAmount = VertexCurrencyUtils.cleanseCurrencyString(taxValue);
		return taxAmount;
	}

	/**
	 * To validate details related to quote line items on quote page
	 *
	 * @param productName
	 * @param quantity
	 * @param unitPrice
	 * @param totalPrice
	 * @param taxValue
	 */
	public void validateQuoteLineDetails( String productName, int quantity, double unitPrice, double totalPrice,
										  double taxValue )
	{
		validateQuoteLineDetails( productName, quantity, unitPrice, totalPrice, taxValue, "Completed");
	}

	/**
	 * To validate details related to quote line items on quote page
	 *
	 * @param productName
	 * @param quantity
	 * @param unitPrice
	 * @param totalPrice
	 * @param taxValue
	 */
	public void validateQuoteLineDetails( String productName, int quantity, double unitPrice, double totalPrice,
		double taxValue, String expectedTaxStatus )
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		jsWaiter.sleep(3000);
		String taxStatus = getQuoteLineDetails(productName, "Tax Status");
		int i = 0;
		while ( !taxStatus.contains(expectedTaxStatus) && i < 5)
		{
			refreshPage();
			taxStatus = getQuoteLineDetails(productName, "Tax Status");
			i++;
		}
		//TODO re-add once tax status works
		// assertEquals(taxStatus, expectedTaxStatus, "Fail after " + i + " loops. Actual Value: " + taxStatus);

		String quantityValue = getQuoteLineDetails(productName, "Quantity");
		quantityValue = quantityValue.replace(".00", "");

		int qty = Integer.parseInt(quantityValue);
		assertEquals(qty, quantity, "Fail. Actual Value: " + qty);

		double salesPriceValue = getPriceValues(productName, "List Unit Price");
		assertEquals(salesPriceValue, unitPrice, "Fail. Actual Value: " + salesPriceValue);

		double totalPriceValue = getPriceValues(productName, "Net Total");
		assertEquals(totalPriceValue, totalPrice, "Fail. Actual Value: " + totalPriceValue);

		double taxAmountValue = getPriceValues(productName, "Tax Amount");
		assertEquals(taxAmountValue, taxValue, "Fail. Actual Value: " + taxAmountValue);

		String productNameValue = getQuoteLineDetails(productName, "Product");
		assertEquals(productNameValue, productName);
	}

	/**
	 * Method to get product details by passing column name and Row index
	 *
	 * @param productName
	 * @param columnName
	 *
	 * @return relevant product details based on row and column
	 */
	private String getQuoteLineDetails( String productName, String columnName )
	{
		int columnIndex = getColumnIndex(columnName);
		String cellValue = getQuoteLineTableCellValue(productName, columnIndex);
		return cellValue;
	}

	/**
	 * Method to validate various prices on quote line item
	 *
	 * @param productName
	 * @param ColumnName
	 *
	 * @return relevant price value based on row and column
	 */
	private double getPriceValues( String productName, String ColumnName )
	{
		String text = getQuoteLineDetails(productName, ColumnName);
		if(text.length() > 4)
			text = text.substring(4);
		double salesPrice = VertexCurrencyUtils.cleanseCurrencyString(text);
		return salesPrice;
	}

	/**
	 * Method to get index of column based on column name
	 *
	 * @param columnName
	 *
	 * @return index of column
	 */
	private int getColumnIndex( String columnName )
	{
		waitForPageLoad();
		int columnIndex = -1;
		wait.waitForElementDisplayed(getQuoteLineTable());
		WebElement tableContainer = getQuoteLineTable();
		WebElement headerRow = tableContainer.findElement(QUOTE_LINE_TABLE_HEADER_ROW);
		wait.waitForElementDisplayed(headerRow);
		List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
		for ( int x = 0 ; x < headerCells.size() ; x++ )
		{
			WebElement column = headerCells.get(x);
			String current_column_label = column.getAttribute("innerText");
			if ( current_column_label.equals(columnName) )
			{
				columnIndex = x;
				break;
			}
		}
		return columnIndex;
	}

	/**
	 * Method to get the text value of specific cell in the quote line table
	 *
	 * @param productName
	 * @param columnIndex
	 *
	 * @return text in cell located by productName and columnIndex
	 */
	private String getQuoteLineTableCellValue( String productName, int columnIndex )
	{
		WebElement cell = null;
		WebElement row = getQuoteLineTableRowByProductName(productName);
		List<WebElement> cells = row.findElements(By.tagName("td"));
		if ( columnIndex == 1){
			cell = row.findElement(By.tagName("th"));
		}
		else
		{
			columnIndex = columnIndex - 1;
			cell = cells.get(columnIndex);
		}

		String cellValue = cell.getText();
		return cellValue;
	}

	/**
	 * Method to return quote line table as web element
	 *
	 * @return quote line table as a WebElement
	 */
	private WebElement getQuoteLineTable( )
	{
		WebElement tableContainer = element.getWebElement(QUOTE_LINE_TABLE);
		return tableContainer;
	}

	/**
	 * Method to find table row associated with specific product
	 *
	 * @param productName
	 *
	 * @return table row with relevant product as a WebElement
	 */
	private WebElement getQuoteLineTableRowByProductName( String productName )
	{
		String quoteRow = String.format(".//tbody/tr/*/a[contains(text(),'%s')]/../parent::tr", productName);
		WebElement tableRow = element.getWebElement(By.xpath(quoteRow));
		return tableRow;
	}

	/**
	 * Method to get quote line product name by passing Product Name
	 *
	 * @param productName
	 *
	 * @return name of the product for specified quote line
	 */
	public void navigateToQuoteLine( String productName )
	{
		waitForSalesForceLoaded();
		WebElement row = getQuoteLineTableRowByProductName(productName);
		WebElement productLink = row.findElement(By.tagName("th"));
		wait.waitForElementDisplayed(productLink);
		click.clickElement(productLink);
	}

	/**
	 * To Edit quote created
	 */
	public void clickGoButton( )
	{
		wait.waitForElementDisplayed(VIEW_GO_BUTTON);
		click.clickElement(VIEW_GO_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To Edit quote created
	 */
	public void clickQLink( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(VIEW_Q_LINK);
		click.clickElement(VIEW_Q_LINK);
		waitForPageLoad();
	}



	/**
	 * To Edit quote created
	 */
	public void clickOtherLink( )
	{
		wait.waitForElementDisplayed(VIEW_OTHER_LINK);
		click.clickElement(VIEW_OTHER_LINK);
		waitForPageLoad();
	}

	/**
	 * Click on Delete button
	 */
	public void clickDeleteButton( )
	{
		jsWaiter.sleep(5000);
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * To check if quote exists
	 *
	 * @param quoteNum
	 *
	 * @return true is quote exists and false if not
	 */
	public boolean checkForQuote( String quoteNum )
	{
		String quoteLookup = String.format("//table/tbody/tr/td/div/a/span[text()='%s']", quoteNum);
		try
		{
			wait.waitForElementDisplayed(By.xpath(quoteLookup), 10);
			click.clickElement(By.xpath(quoteLookup));
			waitForPageLoad();
		}
		catch ( Exception e )
		{
			return false;
		}
		return true;
	}

	/**
	 * To Delete quote created
	 *
	 * @param quoteNum
	 */
	public void deleteQuote( String quoteNum )
	{
		if ( selectQuote(quoteNum) )
		{
			clickDeleteButton();
		}
	}

	public void deleteAllQuotes()
	{
		while(element.isElementDisplayed(DELETE_QUOTE_LINK))
		{
			click.clickElement(DELETE_QUOTE_LINK);
			alert.acceptAlert(DEFAULT_TIMEOUT);
			waitForPageLoad();
			waitForSalesForceLoaded();
		}
	}

	/**
	 * select Quote
	 * @param quoteNum
	 * @return
	 */
	public Boolean selectQuote( String quoteNum )
	{
		clickGoButton();
		clickQLink();
		if(!checkForQuote(quoteNum)){
			try{
				postLogInPage.selectItemsFromRecentItems(quoteNum);
			} catch (Exception e){
				return false;
			}
		}
		return true;
	}

	/**
	 * Go to Order created from Quote
	 */
	public void navigateToOrder( )
	{
		wait.waitForElementDisplayed(ORDER_NUMBER_LINK);
		click.clickElement(ORDER_NUMBER_LINK);
	}

	/**
	 * get Quote with no Opportunity assigned
	 * @return
	 */
	public String getQuoteIdWithNoOpportunity()
	{
		clickGoButton();
		clickOtherLink();
		String quoteId = "";
		if(getQuoteOpportunity() == "")
			quoteId = text.getElementText(QUOTE_ID_FIRST_LINE);
		return quoteId;
	}

	/**
	 * get Quote Opportunity Name
	 * @return
	 */
	public String getQuoteOpportunity()
	{
		String oppName = "";
		try {
			oppName = text.getElementText(OPPORTUNITY_NAME);
		}
		catch(Exception ex){}
		return oppName;
	}

	/**
	 * Navigates back to Order by clicking order number link
	 */
	public void navigateBackToQuote()
	{
		wait.waitForElementDisplayed(QUOTE_NUMBER_LINK);
		click.clickElement(QUOTE_NUMBER_LINK);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * Validate that VAT Return Fields match expected output
	 * Tax Codes, Vertex Tax Codes, Invoice Text Codes
	 *
	 * @param expectedTaxCodes tax codes expected to be returned to SF
	 * @param expectedVertexTaxCodes vertex tax codes expected to be returned to SF
	 * @param expectedInvoiceTextCodes invoice text codes expected to be returned to SF
	 */
	public void validateVATReturnFields(String expectedTaxCodes, String expectedVertexTaxCodes, String expectedInvoiceTextCodes)
	{
		String taxCodes = getTaxCodes();
		String vertexTaxCodes = getVertexTaxCodes();
		String invoiceTextCodes = getInvoiceTextCodes();

		assertEquals(taxCodes, expectedTaxCodes);
		assertEquals(vertexTaxCodes, expectedVertexTaxCodes);
		assertEquals(invoiceTextCodes, expectedInvoiceTextCodes);
	}

	/**
	 * Retrieve tax codes
	 *
	 * @return taxCodes
	 */
	public String getTaxCodes()
	{
		wait.waitForElementDisplayed(TAX_CODES);
		String taxCodes = text.getElementText(TAX_CODES);
		return taxCodes;
	}

	/**
	 * Retrieve vertex tax codes
	 *
	 * @return vertexTaxCodes
	 */
	public String getVertexTaxCodes()
	{
		wait.waitForElementDisplayed(VERTEX_TAX_CODES);
		String vertexTaxCodes = text.getElementText(VERTEX_TAX_CODES);
		return vertexTaxCodes;
	}

	/**
	 * Retrieve Invoice Text Codes
	 *
	 * @return invoiceTextCodes
	 */
	public String getInvoiceTextCodes()
	{
		wait.waitForElementDisplayed(INVOICE_TEXT_CODES);
		String invoiceTextCodes = text.getElementText(INVOICE_TEXT_CODES);
		return invoiceTextCodes;
	}
}
