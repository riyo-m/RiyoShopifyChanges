package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.data.TestInput;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Common functions for anything related to Salesforce Basic Opportunity Page.
 *
 * @author
 */
public class SalesForceCRMOpportunityPage extends SalesForceBasePage
{
	protected By NEW_NEXT_TO_RECENT_OPORTUNITIES = By.cssSelector("input[name = 'new']");
	protected By OPPORTUNITY_NAME = By.id("opp3");
	protected By ACCOUNT_NAME = By.id("opp4");
	protected By ACCOUNT_NAME_LOOKUP = By.id("opp4_lkwgt");

	protected By PHYSICAL_LOCATION = By.xpath("//label[text()='Physical Locations']/../..//select");

	protected By AMOUNT = By.id("opp7");
	protected By CLOSE_DATE = By.cssSelector(".dateFormat>a");
	protected By STAGE = By.id("opp11");
	protected By VTX_CURRENCYISOCODE = By.xpath(
		"//label[contains(text(),'CurrencyISOCode')]//parent::td/following-sibling::td/input");
	protected By DELIVERY_TERM = By.xpath(
		"//label[contains(text(),'Delivery Term')]//parent::td/following-sibling::td//select");
	protected By DISCOUNT_PERCENT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountPercent')]//parent::td/following-sibling::td/input");
	protected By DISCOUNT_AMOUNT_INPUT = By.xpath(
			"//label[contains(text(),'DiscountAmount')]//parent::td/following-sibling::td/input");
	protected By HAS_PHYSICAL_PRESENCE = By.xpath(
			"//label[contains(text(),'HasPhysicalPresence')]//parent::td/following-sibling::td/input");
	protected By TAX_REGISTRATION_INPUT = By.xpath(
			"//label[contains(text(),'Registration')]/parent::td/following-sibling::td//input");
	protected By CUSTOMER_EXEMPT_CERTIFICATE_INPUT = By.xpath(
			"//label[contains(text(),'ExemptionCertificate')]/parent::td/following-sibling::td//input");
	protected By OPPORTUNITY_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");

	protected By PHYSICAL_ORIGIN_STREET_INPUT = By.xpath(
			"//label[contains(text(),'Physical Origin Address 1')]//parent::td/following-sibling::td/input");
	protected By PHYSICAL_ORIGIN_CITY_INPUT = By.xpath(
			"//label[contains(text(),'Physical Origin City')]//parent::td/following-sibling::td/input");
	protected By PHYSICAL_ORIGIN_STATE_INPUT = By.xpath(
			"//label[contains(text(),'Physical Origin State')]//parent::td/following-sibling::td/input");
	protected By PHYSICAL_ORIGIN_ZIP_INPUT = By.xpath(
			"//label[contains(text(),'Physical Origin Postal Code')]//parent::td/following-sibling::td/input");
	protected By PHYSICAL_ORIGIN_COUNTRY_INPUT = By.xpath(
			"//label[contains(text(),'Physical Origin Country')]//parent::td/following-sibling::td/input");

	protected By PRICE_BOOK_DROPDOWN = By.id("p1");
	protected By PRICE_BOOK_SAVE = By.xpath("//*[@id='bottomButtonRow']/input[1]");
	protected By RESULT_FRAME = By.id("resultsFrame");
	protected By ADD_PRODUCT_BTN = By.cssSelector("input[name='addProd']");
	protected By BY_KEY_WORD = By.id("search");
	protected By PRODUCT_SEARCH_BTN = By.id("save_filter_PricebookEntry");
	protected By PRODUCT_SELECT_BTN = By.name("edit");
	protected By PRODUCT_SELECT_ALL_CHECKBOX = By.id("allBox");
	protected By SALES_PRICE = By.cssSelector("input[id*='UnitPrice']");
	protected By QUANTITY = By.cssSelector("input[id*='Quantity']");
	protected By DATE = By.cssSelector("input[id*='ServiceDate']");
	protected By LINE_DESCRIPTION = By.cssSelector("input[id*='Description']");
	protected By PRODUCT_SAVE_BTN = By.name("save");
	protected By DELETE_OPPORT = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Delete']");
	protected By EDIT_OPPORT = By.xpath("//td[@id = 'topButtonRow']//input[@title= 'Edit']");
	protected By PRODUCT_TABLE = By.cssSelector("div[id*='RelatedLineItemList_body'] table");
	protected By PRODUCT_TABLE_HEADER_ROW = By.className("headerRow");
	protected By PRODUCT_TABLE_ROW = By.cssSelector("tbody>tr[class*='dataRow']");
	protected By CLEAR_LOOKUP_SEARCH = By.cssSelector(".clearResults > a");
	protected By LOADING_IMAGE = By.cssSelector("#lineItemView_loading[style*='block']");
	protected By OPPORTUNITY_NAME_LINK = By.xpath("//td[text()='Opportunity']/following-sibling::td/div/a");
	protected By SUPPRESS_TAX_CALLOUT_CHECKBOX = By.xpath("//label[contains(text(),'Suppress Tax Callout')]//parent::td/following-sibling::td/input");

	protected By OPPORTUNITY_PRODUCT_DETAILS = By.xpath("//h2[contains(@class,'mainTitle')]");
	protected By PRODUCT_DETAILS_TAXAMOUNT = By.xpath(".//table/tbody/tr/td[text()='Tax Amount']/following-sibling::td/div");
	protected By PRODUCT_DETAILS_TOTALWITHTAX = By.xpath(".//table/tbody/tr/td[text()='Total with Tax']/following-sibling::td/div");
	protected By PRODUCT_DETAILS_TAXDETAILS = By.xpath(".//table/tbody/tr/td[text()='Tax Details']/following-sibling::td/div");
	protected By ITEM_TAX_DETAILS_COUNT = By.xpath(".//span[@class='listTitle' and text()= 'Vertex Item Tax Details']/span[@class='count']");

	protected By PRODUCT_TAX_DETAIL_TABLE = By.cssSelector("div[id*='_body'] table");
	protected By PRODUCT_TAX_DETAIL_TABLE_HEADER_ROW = By.className("headerRow");
	protected By PRODUCT_TAX_DETAIL_TABLE_ROW = By.cssSelector("tbody>tr[class*='dataRow']");

	protected By CREATE_QUOTE_BUTTON = By.xpath("//tbody/tr/td/input[@value='New Quote']");
	protected By DELETE_QUOTE = By.xpath(".//table/tbody/tr/td/a[contains(@title, 'Delete - Record 1 - Q')]");

	protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");
	protected By VIEW_Q_LINK = By.xpath("//form/div/div/div/a/span[text()='Q']");
	protected By VIEW_ALL_LINK = By.xpath("//form/div/div/div/a/span[text()='All']");

	SalesForceCRMPostLogInPage postLogInPage = new SalesForceCRMPostLogInPage(driver);

	public SalesForceCRMOpportunityPage( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Click on New button
	 */
	public void clickOnNewButton( )
	{
		wait.waitForElementDisplayed(NEW_NEXT_TO_RECENT_OPORTUNITIES);
		click.javascriptClick(NEW_NEXT_TO_RECENT_OPORTUNITIES);
		waitForPageLoad();
	}

	/**
	 * Set Opportunity name
	 *
	 * @param opportunityName
	 */
	public void setOpportunityName( String opportunityName )
	{
		wait.waitForElementDisplayed(OPPORTUNITY_NAME);
		text.enterText(OPPORTUNITY_NAME, opportunityName);
	}

	/**
	 * Set CurrencyISOCode
	 *
	 * @param VTX_CurrencyISOCode
	 */
	public void setVTX_CurrencyISOCode( String VTX_CurrencyISOCode )
	{
		wait.waitForElementDisplayed(VTX_CURRENCYISOCODE);
		text.enterText(VTX_CURRENCYISOCODE, VTX_CurrencyISOCode);
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
	 * Select Available Account Name via Lookup
	 *
	 * @param account_name
	 */
	public void selectOppAccountNameLookup( String account_name )
	{
		click.clickElement(ACCOUNT_NAME_LOOKUP);
		waitForPageLoad();
		window.switchToWindow();
		waitForPageLoad();
		window.switchToFrame(RESULT_FRAME);
		waitForPageLoad();
		if ( element.isElementDisplayed(CLEAR_LOOKUP_SEARCH) )
		{
			click.clickElement(CLEAR_LOOKUP_SEARCH);
			waitForSalesForceLoaded();
			selectParentLookupItem(account_name);
		}
		else
		{
			selectParentLookupItem(account_name);
			window.switchToWindowTextInTitle(TestInput.OPP_EDIT_WINDOW_TITLE);
		}
	}

	/**
	 * Select the Account name from the Account pop up
	 *
	 * @param lookupItem
	 */
	public void selectParentLookupItem( String lookupItem )
	{
		By lookUpItem = By.xpath("//a[contains(text(),'" + lookupItem + "')]");
		wait.waitForElementDisplayed(lookUpItem);
		click.clickElement(lookUpItem);
	}

	/**
	 * Select Physical location
	 *
	 * @param physicalLocationName
	 */
	public void selectPhysicalLocation( String physicalLocationName )
	{
		if(element.isElementDisplayed(PHYSICAL_LOCATION)) {
			wait.waitForElementDisplayed(PHYSICAL_LOCATION);
			dropdown.selectDropdownByDisplayName(PHYSICAL_LOCATION, physicalLocationName);
		}
	}

	/**
	 * Set has physical presence checkbox
	 * @param hasPhysicalPresence
	 */
	public void setHasPhysicalPresence ( Boolean hasPhysicalPresence)
	{
		boolean ischecked = checkbox.isCheckboxChecked(HAS_PHYSICAL_PRESENCE);
		if ( !hasPhysicalPresence == ischecked )
		{
			click.clickElement(HAS_PHYSICAL_PRESENCE);
		}
	}

	/**
	 * Set tax registration number
	 * @param taxRegistration
	 */
	public void setTaxRegistration(String taxRegistration)
	{
		wait.waitForElementDisplayed(TAX_REGISTRATION_INPUT);
		text.enterText(TAX_REGISTRATION_INPUT, taxRegistration);
	}

	/**
	 * Select Delivery Term Value
	 *
	 * @param deliveryTerm
	 */
	public void selectDeliveryTerm( String deliveryTerm )
	{
		if (deliveryTerm != "") {
			wait.waitForElementDisplayed(DELIVERY_TERM);
			dropdown.selectDropdownByDisplayName(DELIVERY_TERM, deliveryTerm);
		}
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
	 * Select link next to CLose Date textbox
	 */
	public void setCloseDate( )
	{
		wait.waitForElementDisplayed(CLOSE_DATE);
		click.clickElement(CLOSE_DATE);
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
	public void clickOpportunitySaveButton( )
	{
		wait.waitForElementDisplayed(OPPORTUNITY_SAVE_BUTTON);
		click.clickElement(OPPORTUNITY_SAVE_BUTTON);
		waitForPageLoad();
		// As Tax values are not updating properly,added page refresh.
		refreshPage();
	}

	/**
	 * Click on Add Product button
	 */
	public void clickOnAddProduct( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(ADD_PRODUCT_BTN);
		wait.waitForElementEnabled(ADD_PRODUCT_BTN);
		click.clickElement(ADD_PRODUCT_BTN);
		waitForSalesForceLoaded();
	}

	public void setPriceBook( String priceBook )
	{
		waitForSalesForceLoaded();
		if(element.isElementPresent(PRICE_BOOK_DROPDOWN))
		{
			wait.waitForElementDisplayed(PRICE_BOOK_DROPDOWN);
			dropdown.selectDropdownByDisplayName(PRICE_BOOK_DROPDOWN, priceBook);
			click.clickElement(PRICE_BOOK_SAVE);
		}
	}

	/**
	 * Set search keyword
	 *
	 * @param productName
	 */
	public void setSearchKeyword( String productName )
	{
		wait.waitForElementDisplayed(BY_KEY_WORD);
		text.enterText(BY_KEY_WORD, productName);
	}

	/**
	 * Set physical origin Street
	 *
	 * @param street
	 */
	private void setPhysicalOriginStreet(String street) {
		wait.waitForElementDisplayed(PHYSICAL_ORIGIN_STREET_INPUT);
		text.enterText(PHYSICAL_ORIGIN_STREET_INPUT, street);
	}

	/**
	 * Set physical origin City
	 *
	 * @param city
	 */
	public void setPhysicalOriginCity(String city) {
		wait.waitForElementDisplayed(PHYSICAL_ORIGIN_CITY_INPUT);
		text.enterText(PHYSICAL_ORIGIN_CITY_INPUT, city);
	}

	/**
	 * Set physical origin State
	 *
	 * @param state
	 */
	public void setPhysicalOriginState(String state) {
		wait.waitForElementDisplayed(PHYSICAL_ORIGIN_STATE_INPUT);
		text.enterText(PHYSICAL_ORIGIN_STATE_INPUT, state);
	}

	/**
	 * Set physical origin Zip
	 *
	 * @param zip
	 */
	private void setPhysicalOriginZip(String zip) {
		wait.waitForElementDisplayed(PHYSICAL_ORIGIN_ZIP_INPUT);
		text.enterText(PHYSICAL_ORIGIN_ZIP_INPUT, zip);
	}

	/**
	 * Set physical origin Country
	 *
	 * @param country
	 */
	private void setPhysicalOriginCountry(String country) {
		wait.waitForElementDisplayed(PHYSICAL_ORIGIN_COUNTRY_INPUT);
		text.enterText(PHYSICAL_ORIGIN_COUNTRY_INPUT, country);
	}

	/**
	 * Set physical origin address
	 *
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public void setPhysicalOriginAddress(String street, String city, String state, String zip, String country )
	{
		setPhysicalOriginStreet(street);
		setPhysicalOriginCity(city);
		setPhysicalOriginState(state);
		setPhysicalOriginZip(zip);
		setPhysicalOriginCountry(country);
	}

	/**
	 * Click on Search button
	 */
	public void clickOnProductSearchButton( )
	{
		wait.waitForElementDisplayed(PRODUCT_SEARCH_BTN);
		click.clickElement(PRODUCT_SEARCH_BTN);
		waitForPageLoad();
	}

	/**
	 * Select the check box displayed in search result
	 *
	 * @param productName
	 */
	public void selectProduct( String productName )
	{
		waitForPageLoad();
		wait.waitForElementNotPresent(LOADING_IMAGE);
		By CHECK_PRODUCT_NAME = By.xpath("//tbody/tr[td[contains(@class, 'PRODUCT_NAME')][//*[text()='" + productName +
										 "']]]//input[@type='checkbox']");
		wait.waitForElementDisplayed(CHECK_PRODUCT_NAME);
		click.clickElement(CHECK_PRODUCT_NAME);
		waitForPageLoad();
	}

	/**
	 * Click on Select All Checkbox
	 */
	public void clickOnSelectAllCheckBox( )
	{
		wait.waitForElementDisplayed(PRODUCT_SELECT_ALL_CHECKBOX);
		click.clickElement(PRODUCT_SELECT_ALL_CHECKBOX);

		// handles alert that appears when more than 50 products are selected at once
		try
		{
			alert.acceptAlert(DEFAULT_TIMEOUT);
		}
		catch ( NoAlertPresentException e )
		{
			e.printStackTrace();
		}

		waitForPageLoad();
	}

	/**
	 * Click on Select button
	 */
	public void clickOnSelectButton( )
	{
		wait.waitForElementDisplayed(PRODUCT_SELECT_BTN);
		click.clickElement(PRODUCT_SELECT_BTN);
		waitForPageLoad();
	}

	/**
	 * Set Sales Price
	 *
	 * @param salesPrice
	 */
	public void setSalesPrice( String salesPrice )
	{
		wait.waitForElementDisplayed(SALES_PRICE);
		text.enterText(SALES_PRICE, salesPrice);
	}

	/**
	 * Set Quantity
	 *
	 * @param quantity
	 */
	public void setQuantity( String quantity )
	{
		wait.waitForElementDisplayed(QUANTITY);
		text.enterText(QUANTITY, quantity);
	}

	/**
	 * Set Quantity
	 *
	 * @param quantity
	 */
	public void setQuantityForAllProducts( String quantity )
	{
		List<WebElement> allOptions = driver.findElements(By.xpath("//*[contains(@id, 'Quantity')]"));
		wait.waitForElementDisplayed(allOptions.get(1));
		for ( WebElement we : allOptions )
		{
			text.enterText(we, quantity);
		}
	}

	/**
	 * Set current date
	 */
	public void setCurrentDate( )
	{
		wait.waitForElementDisplayed(DATE);
		String currentDate = "";
		text.enterText(DATE, currentDate);
	}

	/**
	 * Set line description
	 *
	 * @param description
	 */
	public void setLineDescription( String description )
	{
		wait.waitForElementDisplayed(LINE_DESCRIPTION);
		text.enterText(LINE_DESCRIPTION, description);
	}

	/**
	 * Set discount percent
	 * @param discountPercent
	 */
	public void setDiscountPercent(String discountPercent)
	{
		wait.waitForElementDisplayed(DISCOUNT_PERCENT_INPUT);
		text.enterText(DISCOUNT_PERCENT_INPUT, discountPercent);
	}

	/**
	 * Set discount amount
	 * @param discountAmount
	 */
	public void setDiscountAmount(String discountAmount)
	{
		wait.waitForElementDisplayed(DISCOUNT_AMOUNT_INPUT);
		text.enterText(DISCOUNT_AMOUNT_INPUT, discountAmount);
	}

    /**
     * Set Exemption Certificate Number
     * @param exemptionCertificate
     */
    public void setExemptionCertificate(String exemptionCertificate)
    {
        wait.waitForElementDisplayed(CUSTOMER_EXEMPT_CERTIFICATE_INPUT);
        text.enterText(CUSTOMER_EXEMPT_CERTIFICATE_INPUT, exemptionCertificate);
    }

	/**
	 * Click on Save button of Add Product page
	 */
	public void clickPrdSaveButton( )
	{
		wait.waitForElementDisplayed(PRODUCT_SAVE_BTN);
		click.clickElement(PRODUCT_SAVE_BTN);
		waitForPageLoad();
		// As Tax values are not updating properly, added page refresh.
		refreshPage();
	}

	public void clickCreateQuote( )
	{
		wait.waitForElementDisplayed(CREATE_QUOTE_BUTTON);
		click.clickElement(CREATE_QUOTE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To Create new Opportunity with below parameters
	 *
	 * @param opportunityName
	 * @param accountName
	 * @param amount
	 * @param location
	 * @param stageName
	 */
	public void createOpportunity( String opportunityName, String accountName, String amount, String location,
		String stageName, String deliveryTerm )
	{
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
		clickGoButton();
		clickQLink();
		while ( checkForOpp(opportunityName) )
		{
			deleteOpportunity();
			postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
			clickGoButton();
			clickQLink();
		}
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
		clickOnNewButton();
		setOpportunityName(opportunityName);
		waitForPageLoad();
		selectOppAccountName(accountName);
		setAmount(amount);
		selectPhysicalLocation(location);
		setCloseDate();
		setStage(stageName);
		selectDeliveryTerm(deliveryTerm);
		clickOpportunitySaveButton();
	}

	/**
	 * To Create new Opportunity with Additional Parameters
	 *
	 * @param opportunityName
	 * @param accountName
	 * @param amount
	 * @param location
	 * @param stageName
	 * @param deliveryTerm
	 * @param currencyISOCode
	 */
	public void createCRMOpportunity( String opportunityName, String accountName, String amount, String location,
		String stageName, String deliveryTerm, String currencyISOCode )
	{
		createCRMOpportunity(opportunityName, accountName, amount, location, stageName, deliveryTerm, currencyISOCode, "", "");
	}

	/**
	 * To Create new Opportunity with Additional Discount Parameters
	 *
	 * @param opportunityName
	 * @param accountName
	 * @param amount
	 * @param location
	 * @param stageName
	 * @param deliveryTerm
	 * @param currencyISOCode
	 * @param discountPercent
	 * @param discountAmount
	 */
	public void createCRMOpportunity( String opportunityName, String accountName, String amount, String location,
									  String stageName, String deliveryTerm, String currencyISOCode , String discountPercent, String discountAmount)
	{
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
		clickOnNewButton();
		setOpportunityName(opportunityName);
		waitForPageLoad();
		selectOppAccountName(accountName);
		setAmount(amount);
		selectPhysicalLocation(location);
		setCloseDate();
		setStage(stageName);
		setVTX_CurrencyISOCode(currencyISOCode);
		setDiscountPercent(discountPercent);
		setDiscountAmount(discountAmount);
		selectDeliveryTerm(deliveryTerm);
		clickOpportunitySaveButton();
	}

	/**
	 * To create new CPQ opportunity
	 *
	 * @param opportunityName
	 * @param accountName
	 * @param stageName
	 */
	public void createCPQOpportunity( String opportunityName, String accountName, String stageName )
	{
		createCPQOpportunity(opportunityName, accountName, stageName, "");
	}

	/**
	 * To create new CPQ opportunity with additional parameter
	 *
	 * @param opportunityName
	 * @param accountName
	 * @param stageName
	 * @param currencyISOCode
	 */
	public void createCPQOpportunity( String opportunityName, String accountName, String stageName,
		String currencyISOCode )
	{
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);

		clickGoButton();
		clickQLink();
		while ( checkForOpp(opportunityName) )
		{
			deleteOpportunity();
			postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
			clickGoButton();
			clickQLink();
		}
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
		clickOnNewButton();
		setOpportunityName(opportunityName);
		waitForPageLoad();
		selectOppAccountName(accountName);
		setCloseDate();
		setStage(stageName);
		setVTX_CurrencyISOCode(currencyISOCode);
		clickOpportunitySaveButton();
		waitForSalesForceLoaded();
	}

	/**
	 * Add Product to the Opportunity created
	 *
	 * @param productName
	 * @param price
	 * @param quantity
	 * @param description
	 */
	public void addProductToOpportunity( String productName, String price, String quantity, String description )
	{
		clickOnAddProduct();
		setPriceBook("Standard");
		setSearchKeyword(productName);
		clickOnProductSearchButton();
		selectProduct(productName);
		clickOnSelectButton();
		setSalesPrice(price);
		setQuantity(quantity);
		setCurrentDate();
		setLineDescription(description);
		clickPrdSaveButton();
		waitForPageLoad();
		refreshPage();
	}

	/**
	 * Add Product to the Opportunity created
	 *
	 * @param quantity
	 */
	public void addAllProductToOpportunity( String quantity )
	{
		waitForPageLoad();
		clickOnAddProduct();
		clickOnSelectAllCheckBox();
		clickOnSelectButton();
		setQuantityForAllProducts(quantity);
		clickPrdSaveButton();
	}
	//endregion

	/**
	 * Add Product to the Opportunity created
	 *
	 * @param productName
	 * @param price
	 * @param quantity
	 * @param description
	 */
	public void addProductToCPQOpportunity( String productName, String price, String quantity,
											String description )
	{
		addProductToCPQOpportunity( "Standard", productName, price, quantity, description);
	}

	/**
	 * Add Product to the Opportunity created
	 *
	 * @param priceBook
	 * @param productName
	 * @param price
	 * @param quantity
	 * @param description
	 */
	public void addProductToCPQOpportunity( String priceBook, String productName, String price, String quantity,
		String description )
	{
		clickOnAddProduct();
		setPriceBook(priceBook);
		setSearchKeyword(productName);
		clickOnProductSearchButton();
		selectProduct(productName);
		clickOnSelectButton();
		setSalesPrice(price);
		setQuantity(quantity);
		setCurrentDate();
		setLineDescription(description);
		clickPrdSaveButton();
	}

	/**
	 * After Creating Opportunity, we have to validate the input.
	 *
	 * @param opportunityDetail
	 *
	 * @return
	 */
	public String getEachOpportunityDetails( String opportunityDetail )
	{
		String opportunityRow = String.format("//td[starts-with(text(), '%s')]/following-sibling::td[1]//div",
			opportunityDetail);
		wait.waitForElementDisplayed(By.xpath(opportunityRow));
		String oppurtunityRowValue = text.getElementText(By.xpath(opportunityRow));
		return oppurtunityRowValue;
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
		String taxValue = getEachOpportunityDetails(OppDetail);
		taxAmount = VertexCurrencyUtils.cleanseCurrencyString(taxValue);
		return taxAmount;
	}

	/**
	 * To clickGoButton
	 */
	public void clickGoButton( )
	{
		wait.waitForElementDisplayed(VIEW_GO_BUTTON);
		click.javascriptClick(VIEW_GO_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To clickAllLink
	 */
	public void clickAllLink( )
	{
		wait.waitForElementDisplayed(VIEW_ALL_LINK);
		click.javascriptClick(VIEW_ALL_LINK);
		waitForPageLoad();
	}

	/**
	 * To clickQLink
	 */
	public void clickQLink( )
	{
		wait.waitForElementDisplayed(VIEW_Q_LINK);
		click.javascriptClick(VIEW_Q_LINK);
		waitForPageLoad();
	}

	/**
	 * To checkForOpp
	 * @param oppName
	 */
	public boolean checkForOpp( String oppName )
	{
		String accountLookup = String.format("//td/div/a/span[contains(text(),'%s')]", oppName);
		try
		{
			wait.waitForElementDisplayed(By.xpath(accountLookup), 10);
			click.clickElement(By.xpath(accountLookup));
			waitForPageLoad();
		}
		catch ( Exception e )
		{
			return false;
		}
		return true;
	}

	/**
	 * Click on Edit button, To edit the opportunity
	 */
	public void editOpportunity( )
	{
		click.clickElement(EDIT_OPPORT);
		waitForPageLoad();
	}

	/**
	 * Click on Delete button, To delete the opportunity
	 */
	public void deleteOpportunity( )
	{
		try {
			editOpportunity();
			setOpportunityName("DELETE");
			clickOpportunitySaveButton();

			wait.waitForElementDisplayed(DELETE_OPPORT);
			click.clickElement(DELETE_OPPORT);
			alert.acceptAlert(DEFAULT_TIMEOUT);
		}catch(Exception ex){}
		waitForPageLoad();
		window.switchToWindowTextInTitle(TestInput.SALESFORCE_CRM_WINDOW_TITLE);
	}

	/**
	 * Delete Product from Opportunity
	 *
	 * @param productName
	 */
	public void deleteProductOfOpportunity( String productName )
	{
		String productRow = String.format("//a[contains(text(),'Del') and contains(@title,'%s')]", productName);
		click.clickElement(By.xpath(productRow));
		alert.acceptAlert(DEFAULT_TIMEOUT);
		waitForPageLoad();
	}

	/**
	 * Edit Product from Opportunity
	 *
	 * @param productName
	 */
	public void editProductOfOpportunity( String productName )
	{
		String productRow = String.format("//a[contains(text(),'Edit') and contains(@title,'%s')]", productName);
		click.clickElement(By.xpath(productRow));
		waitForPageLoad();
	}

	/**
	 * Select Delivery Term in Edit Product under Opprotunity
	 *
	 * @param deliveryTerm
	 */
	public void editProductInOpportunity( String deliveryTerm )
	{
		wait.waitForElementDisplayed(DELIVERY_TERM);
		dropdown.selectDropdownByDisplayName(DELIVERY_TERM, deliveryTerm);
		waitForPageLoad();
	}

	/**
	 * Method to get product details name by passing Row index
	 */
	public String getProductDetailsName( int rowNumber )
	{
		String cellValue = null;
		WebElement row = getProductTableRowByIndex(rowNumber);
		List<WebElement> cells = row.findElements(By.tagName("a"));
		WebElement cell = cells.get(2);
		cellValue = cell.getText();

		return cellValue;
	}

	/**
	 * Method to get product details by passing column name and Row index
	 */
	public String getProductDetails( int rowNumber, String ColumnName )
	{
		int columnIndex = getColumnIndex(ColumnName);
		String cellValue = getProductTableCellValue(rowNumber, columnIndex);
		return cellValue;
	}

	public double getPriceValues( int rowNumber, String ColumnName )
	{
		String text = getProductDetails(rowNumber, ColumnName);
		String noCurrency = trimCurrencySubstring(text);
		double salesPrice = VertexCurrencyUtils.cleanseCurrencyString(noCurrency);
		return salesPrice;
	}

	private int getColumnIndex( String ColumnName )
	{
		waitForPageLoad();
		int columnIndex = -1;
		wait.waitForElementDisplayed(getProductTable());
		WebElement tableContainer = getProductTable();
		WebElement headerRow = tableContainer.findElement(PRODUCT_TABLE_HEADER_ROW);
		wait.waitForElementDisplayed(headerRow);
		List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
		for ( int x = 0 ; x < headerCells.size() ; x++ )
		{
			WebElement column = headerCells.get(x);
			String current_column_label = column.getAttribute("innerText");
			if ( current_column_label.equals(ColumnName) )
			{
				columnIndex = x;
				break;
			}
		}
		return columnIndex;
	}

	private WebElement getProductTable( )
	{
		WebElement tableContainer = element.getWebElement(PRODUCT_TABLE);
		return tableContainer;
	}

	private String getProductTableCellValue( int rowNumber, int ColumnIndex )
	{
		String cellValue = null;
		WebElement row = getProductTableRowByIndex(rowNumber);
		List<WebElement> cells = row.findElements(By.tagName("td"));
		if ( ColumnIndex == 1 )
		{
			ColumnIndex = ColumnIndex;
		}
		else
		{
			ColumnIndex = ColumnIndex - 1;
		}
		WebElement cell = cells.get(ColumnIndex);
		cellValue = cell.getText();
		return cellValue;
	}

	private WebElement getProductTableRowByIndex( int rowIndex )
	{
		WebElement tableContainer = getProductTable();
		List<WebElement> rows = tableContainer.findElements(PRODUCT_TABLE_ROW);
		return rows.get(rowIndex);
	}

	/**
	 * Method to Click the Product in Products (Standard) in Opportunity Page
	 * Satya
	 *
	 * @param itemName
	 */
	public void ClickOnProductNameLink( String itemName )
	{
		String itemRow = String.format("//a[contains(text(),'%s')]", itemName);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();
	}

	/**
	 * Method to Validate Opportunity Product Detail Page
	 * Satya
	 *
	 * @param expectedOppProdDetails
	 * @param expectedOppProdDetailsTaxAmt
	 * @param expectedOppProdDetailsTotalTax
	 * @param expectedOppProdTaxDetails
	 */
	public void getTaxAmountsAndDetails( String expectedOppProdDetails, double expectedOppProdDetailsTaxAmt,
		double expectedOppProdDetailsTotalTax, String expectedOppProdTaxDetails )
	{
		WebElement OppProdDetails = wait.waitForElementPresent(OPPORTUNITY_PRODUCT_DETAILS);
		String ActualOppProdDetailsText = OppProdDetails.getText();
		assertTrue(expectedOppProdDetails.equalsIgnoreCase(ActualOppProdDetailsText));

		WebElement OppProdDetailsTaxAmt = wait.waitForElementPresent(PRODUCT_DETAILS_TAXAMOUNT);
		String ActualOppProdDetailsTaxAmtText = trimCurrencySubstring(OppProdDetailsTaxAmt.getText());
		double salesPrice = VertexCurrencyUtils.cleanseCurrencyString(ActualOppProdDetailsTaxAmtText);
		assertEquals(expectedOppProdDetailsTaxAmt, salesPrice);

		WebElement OppProdDetailsTotalTax = wait.waitForElementPresent(PRODUCT_DETAILS_TOTALWITHTAX);
		String ActualOppProdDetailsTotalTaxText = trimCurrencySubstring(OppProdDetailsTotalTax.getText());
		double actualSalesPrice1 = VertexCurrencyUtils.cleanseCurrencyString(ActualOppProdDetailsTotalTaxText);
		assertEquals(actualSalesPrice1, expectedOppProdDetailsTotalTax);

		WebElement OppProdTaxDetails = wait.waitForElementPresent(PRODUCT_DETAILS_TAXDETAILS);
		String ActualOppProdDetailsTaxDetailsText = OppProdTaxDetails.getText();
		ActualOppProdDetailsTaxDetailsText = ActualOppProdDetailsTaxDetailsText.replaceAll("\\s", "");
		expectedOppProdTaxDetails = expectedOppProdTaxDetails.replaceAll("\\s", "");
		assertTrue(expectedOppProdTaxDetails.contains(ActualOppProdDetailsTaxDetailsText), "Actual: "+ActualOppProdDetailsTaxDetailsText);
	}

	/**
	 * To Click on Recent Opportunity in Opportunity Product Detail Page
	 *
	 * @param itemName
	 *
	 * @Satya
	 */
	public void clickOpportunityItemsFromRecentItems( String itemName )
	{
		try
		{
			if ( itemName.length() > 15 )
			{
				itemName = itemName.substring(0, 15);
			}
			String itemRow = String.format("//span[contains(text(),'%s')]", itemName);
			wait.waitForElementDisplayed(By.xpath(itemRow), 10);
			click.clickElement(By.xpath(itemRow));
			waitForPageLoad();
		}
		catch ( Exception ex )
		{
			try
			{
				clickGoButton();
				clickQLink();
				if ( checkForOpp(itemName) )
					return;
			}
			catch ( Exception e )
			{
				clickAllLink();
				if ( checkForOpp(itemName) )
					return;
				else
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Navigate back to opportunity from opportunity product
	 */
	public void navigateBackToOpportunity()
	{
		wait.waitForElementDisplayed(OPPORTUNITY_NAME_LINK);
		click.clickElement(OPPORTUNITY_NAME_LINK);
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * To Delete Current Quote in Opportunity Product Detail Page
	 *
	 * @Brenda.Johnson
	 */
	public void deleteCurrentQuote( )
	{
		while ( element.isElementDisplayed(DELETE_QUOTE) )
		{
			click.clickElement(DELETE_QUOTE);
			alert.acceptAlert(DEFAULT_TIMEOUT);
			waitForSalesForceLoaded();
			waitForPageLoad();
		}
	}

	/**
	 * Set Suppress Tax Callout checkbox
	 * @param shouldBeChecked
	 */
	public void setSuppressTaxCalloutCheckbox( boolean shouldBeChecked)
	{
		wait.waitForElementDisplayed(SUPPRESS_TAX_CALLOUT_CHECKBOX);
		checkbox.setCheckbox(SUPPRESS_TAX_CALLOUT_CHECKBOX, shouldBeChecked);
	}
}
