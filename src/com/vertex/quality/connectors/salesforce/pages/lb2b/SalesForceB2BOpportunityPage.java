package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.salesforce.data.TestInput;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Common functions for anything related to Salesforce Lightning Opportunity Page.
 *
 * @author brenda.johnson
 */
public class SalesForceB2BOpportunityPage extends SalesForceBasePage
{

	protected By NEW_NEXT_TO_RECENT_OPORTUNITIES = By.xpath(
		"//*[@id='brandBand_1']/div/div/div/div/div[1]/div[1]/div[2]/ul/li[1]/a[@title = 'New']");
	protected By OPPORTUNITY_NAME = By.id("opp3");

	protected By ACCOUNT_NAME_LOOKUP = By.id("opp4_lkwgt");

	protected By PHYSICAL_LOCATION = By.xpath("//label[text()='Physical Locations']/../..//select");

	protected By AMOUNT = By.id("opp7");
	protected By CLOSE_DATE = By.cssSelector(".dateFormat>a");
	protected By STAGE = By.id("opp11");
	protected By DELIVERY_TERM = By.xpath(
		"//label[contains(text(),'Delivery Term')]//parent::td/following-sibling::td//select");
	protected By OPPORTUNITY_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");

	protected By RESULT_FRAME = By.id("resultsFrame");
	protected By ADD_PRODUCT_BTN = By.name("addProd");
	protected By BY_KEY_WORD = By.id("search");
	protected By PRODUCT_SEARCH_BTN = By.id("save_filter_PricebookEntry");
	protected By PRODUCT_SELECT_BTN = By.name("edit");
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

	protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");
	protected By VIEW_Q_LINK = By.xpath("//form/div/div/div/a/span[text()='Q']");
	protected By VIEW_ALL_LINK = By.xpath("//form/div/div/div/a/span[text()='All']");

	SalesForceLB2BPostLogInPage postLogInPage = new SalesForceLB2BPostLogInPage(driver);

	public SalesForceB2BOpportunityPage( WebDriver driver )
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
	 * Select Available Account Name
	 *
	 * @param account_name
	 */
	public void selectOppAccountName( String account_name )
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
			waitForPageLoad();
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
		wait.waitForElementDisplayed(PHYSICAL_LOCATION);
		dropdown.selectDropdownByDisplayName(PHYSICAL_LOCATION, physicalLocationName);
	}

	/**
	 * Select Delivery Term Value
	 *
	 * @param deliveryTerm
	 */
	public void selectDeliveryTerm( String deliveryTerm )
	{
		wait.waitForElementDisplayed(DELIVERY_TERM);
		dropdown.selectDropdownByDisplayName(DELIVERY_TERM, deliveryTerm);
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
		wait.waitForElementDisplayed(ADD_PRODUCT_BTN);
		click.clickElement(ADD_PRODUCT_BTN);
		waitForPageLoad();
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

	/**
	 * To Create new Opportunity with below parameters
	 *
	 * @param OpportunityName
	 * @param accountName
	 * @param amount
	 * @param location
	 * @param stageName
	 */
	public void createOpportunity( String OpportunityName, String accountName, String amount, String location,
		String stageName, String deliveryTerm )
	{
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
		clickGoButton();
		clickQLink();
		while ( checkForOpp(OpportunityName) )
		{
			deleteOpportunity();
			postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
			clickGoButton();
			clickQLink();
		}
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.OPPORTUNITIES_TAB.text);
		clickOnNewButton();
		setOpportunityName(OpportunityName);
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
		String OpportunityRow = String.format("//td[starts-with(text(), '%s')]/following-sibling::td[1]//div",
			opportunityDetail);
		wait.waitForElementDisplayed(By.xpath(OpportunityRow));
		String OpportunityRowValue = text.getElementText(By.xpath(OpportunityRow));
		return OpportunityRowValue;
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
	 * Click on Opportunity name from Recent items
	 *
	 * @param oppName
	 */
	public void clickOnOppame( String oppName )
	{
		String OpportunityRow = String.format("//span[contains(text(),'%s')]", oppName);
		click.clickElement(By.xpath(OpportunityRow));
		waitForPageLoad();
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
		String accountLookup = String.format("//table/tbody/tr/td/div/a/span[text()='%s']", oppName);
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
	 * Click on Delete button, To delete the opportunity
	 */
	public void deleteOpportunityFromLogstab( )
	{
		wait.waitForElementDisplayed(DELETE_OPPORT);
		click.clickElement(DELETE_OPPORT);
		alert.acceptAlert(DEFAULT_TIMEOUT);
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
}
