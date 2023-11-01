package com.vertex.quality.connectors.salesforce.pages.lb2b;

import com.vertex.quality.connectors.salesforce.data.TestInput;
import com.vertex.quality.connectors.salesforce.enums.Constants.AddressType;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/**
 * Common functions for anything related to Salesforce Lightning Accounts Page.
 *
 * @author brenda.johnson
 */
public class SalesForceB2BAccountsPage extends SalesForceBasePage
{
	protected By ACCOUNT_MODAL = By.xpath("//div[contains(@class, 'isModal')]");
	protected By ACCOUNTS_TAB = By.id("Account_Tab");
	protected By NEW_BUTTON = By.name("new");
	protected By ACCOUNT_NAME = By.xpath("//*[text()='Account Name']/../..//input[@type='text']");
	protected By PARENT_ACCOUNT_NAME_SEARCH = By.xpath(".//*[text()='Parent Account']/../..//input");
	protected By DELETE_LINK = By.xpath("//span[contains(text(),'Delete')]");
	protected By DELETE_BUTTON = By.xpath("//button[@title='Delete']");
	protected By LOOKUP_ICON = By.className("lookupIcon");
	protected By RESULT_FRAME = By.id("resultsFrame");
	protected By LOOKUP_SEARCH_INPUT = By.id("lksrch");
	protected By TYPE_DROPDOWN = By.xpath("//*[text()='Type']/../..//input");
	protected By CUSTOMER_CODE_INPUT = By.xpath("//*[contains(text(),'CustomerCode')]/../..//input");

	protected By SHIPPING_STREET_INPUT = By.xpath(".//*[text()='Shipping Street']/../..//textarea");
	protected By SHIPPING_CITY_INPUT = By.xpath(".//*[text()='Shipping City']/../..//input[@maxlength='40']");
	protected By SHIPPING_STATE_INPUT = By.xpath(
		".//*[text()='Shipping State/Province']/../..//input[@maxlength='80']");
	protected By SHIPPING_ZIP_INPUT = By.xpath(".//*[text()='Shipping Zip/Postal Code']/../..//input[@maxlength='20']");
	protected By SHIPPING_COUNTRY_INPUT = By.xpath(".//*[text()='Shipping Country']/../..//input[@maxlength='80']");

	protected By BILLING_STREET_INPUT = By.xpath(".//*[text()='Billing Street']/../..//textarea");
	protected By BILLING_CITY_INPUT = By.xpath(".//*[text()='Billing City']/../..//input[@maxlength='40']");
	protected By BILLING_STATE_INPUT = By.xpath(".//*[text()='Billing State/Province']/../..//input[@maxlength='80']");
	protected By BILLING_ZIP_INPUT = By.xpath(".//*[text()='Billing Zip/Postal Code']/../..//input[@maxlength='20']");
	protected By BILLING_COUNTRY_INPUT = By.xpath(".//*[text()='Billing Country']/../..//input[@maxlength='80']");

	protected By VALIDATE_ADDRESSES_LINK = By.xpath("//*[contains(text(),'Validate Addresses')]");
	protected By BILLING_UPDATE_BUTTON = By.xpath(
		".//*[@data-addresstype='billing']/button[@title='Update from Vertex']");
	protected By BILLING_ACCEPT_BUTTON = By.xpath(
		".//*[@data-addresstype='billing']/button[@title='Accept Current Address']");
	protected By BILLING_ADDRESS_CONFIRMATION = By.xpath(".//lightning-layout-item[.='Billing Address is correct']");
	protected By SHIPPING_UPDATE_BUTTON = By.xpath(
		".//*[@data-addresstype='shipping']/button[@title='Update from Vertex']");
	protected By SHIPPING_ACCEPT_BUTTON = By.xpath(
		".//*[@data-addresstype='shipping']/button[@title='Accept Current Address']");
	protected By SHIPPING_ADDRESS_CONFIRMATION = By.xpath(".//lightning-layout-item[.='Shipping Address is correct']");
	protected By UPDATE_ADDRESS = By.xpath(".//button[text()='Update Address']");
	protected By RETURN_TO_ACCOUNT = By.xpath(".//input[@value='Return to Account']");

	protected By EDIT_BUTTON = By.xpath("//button[@name='Edit']");
	protected By QA_EDIT_LINK = By.xpath("//a[@name='Edit']/span[text()='Edit']");

	protected By SHIPPING_ADDRESS_VALUE = By.xpath(
		"//div/slot/force-record-layout-row/slot/force-record-layout-item/div/div/div/span[text()='Shipping Address']/parent::div/following-sibling::div//a");
	protected By BILLING_ADDRESS_VALUE = By.xpath(
		"//div/slot/force-record-layout-row/slot/force-record-layout-item/div/div/div/span[text()='Billing Address']/parent::div/following-sibling::div//a");

	protected By ACCOUNT_OWNER_VALUE = By.xpath(
		"//label[contains(text(),'Account Owner')]//parent::td/following-sibling::td[1]");
	protected By ACCOUNT_NAME_VALUE = By.xpath(
		"//td[contains(text(),'Account Name')]/following-sibling::td//div[@id='acc2_ileinner']");
	protected By ACCOUNT_TYPE_VALUE = By.xpath("//td[contains(text(),'Type')]/following-sibling::td//div");
	protected By CUSTOMER_CODE_VALUE = By.xpath("//td[contains(text(),'CustomerCode')]/following-sibling::td//div");

	protected By COPY_ADDRESS_LINK = By.cssSelector(".pbSubExtra a");
	protected By CLEAR_LOOKUP_SEARCH = By.cssSelector(".clearResults > a");

	protected By LOOKUP_GO_BUTTON = By.name("go");
	protected By UPDATE_ADDRESS_BUTTON = By.cssSelector("input[value='Update Address']");

	protected By SAVE_BUTTON = By.cssSelector(".pbBottomButtons  input[title= 'Save']");
	protected By LOOKUP_ACCOUNT_NAME = By.xpath(
		"//label[contains(text(),'Account Name')]/parent::td/following-sibling::td//input");
	protected By LOOKUP_STREET = By.id("street");
	protected By LOOKUP_CITY = By.id("city");
	protected By LOOKUP_STATE = By.id("state");
	protected By LOOKUP_ZIP = By.id("zip");
	protected By LOOKUP_COUNTRY = By.id("country");

	protected By ALL_ACCOUNTS_OPTION = By.xpath(".//span[contains(text(), 'All Accounts')]");
	protected By ACCOUNT_SEARCH_DROPDOWN = By.xpath(
		".//a[contains(@title,'Select List View')]/.//*[contains(@class, 'slds-icon-utility-down')]");

	protected By ACCOUNT_SAVE_BUTTON = By.xpath(".//button[@name = 'SaveEdit']");
	protected By QA_ACCOUNT_SAVE_BUTTON = By.xpath(".//button[@title = 'Save']");
	protected By ACCOUNT_DETAILS_TAB = By.xpath("//*[@id='detailTab__item']");

	protected By ACCOUNT_DROPDOWN_ACTIONS = By.xpath(
		"//button[contains(@class,'slds-button slds-button_icon-border-filled')]//lightning-primitive-icon//*[local-name()='svg']");
	protected String ACCOUNT_ITEM = ".//div/div/table/tbody/tr/th/span/a[@title='%s']";
	protected By ACCOUNT_SEARCH_INPUT = By.xpath(".//*[@name='Account-search-input']");
	SalesForceB2BOpportunityPage opportunityPage;
	SalesForceLB2BPostLogInPage postLogInPage;

	public SalesForceB2BAccountsPage( WebDriver driver )
	{
		super(driver);

		opportunityPage = new SalesForceB2BOpportunityPage(driver);
		postLogInPage = new SalesForceLB2BPostLogInPage(driver);
	}

	/**
	 * Find Account By Name
	 */
	public void viewAllAccounts( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(ACCOUNT_SEARCH_DROPDOWN);
		click.javascriptClick(ACCOUNT_SEARCH_DROPDOWN);
		click.clickElement(ALL_ACCOUNTS_OPTION);
	}

	/**
	 * Find Account By Name
	 *
	 * @param accountName
	 */
	public void findAccount( String accountName )
	{
		setSearch(accountName);
		selectAccount(accountName);
	}

	/**
	 * Set Search Name
	 *
	 * @param accountName
	 */
	private void setSearch( String accountName )
	{
		wait.waitForElementDisplayed(ACCOUNT_SEARCH_INPUT);
		text.enterText(ACCOUNT_SEARCH_INPUT, accountName + Keys.ENTER);
		waitForPageLoad();
	}

	/**
	 * Select Account Name
	 *
	 * @param accountName
	 */
	private void selectAccount( String accountName )
	{
		By accountLink = By.xpath(String.format(ACCOUNT_ITEM, accountName));
		wait.waitForElementDisplayed(accountLink);
		click.clickElement(accountLink);
		waitForPageLoad();
	}

	/**
	 * Set Account Name
	 *
	 * @param accountName
	 */
	private void setAccountName( String accountName )
	{
		wait.waitForElementDisplayed(ACCOUNT_NAME);
		text.enterText(ACCOUNT_NAME, accountName);
	}

	/**
	 * Set Account Name
	 *
	 * @param accountName
	 */
	private void setParentAccountNameSearch( String accountName )
	{
		wait.waitForElementDisplayed(PARENT_ACCOUNT_NAME_SEARCH);
		text.enterText(PARENT_ACCOUNT_NAME_SEARCH, accountName);
	}

	/**
	 * Click Type
	 */
	private void clickType( )
	{
		wait.waitForElementDisplayed(TYPE_DROPDOWN);
		click.clickElement(TYPE_DROPDOWN);
	}

	/**
	 * Set CustomerCode
	 *
	 * @param customerCode
	 */
	private void setCustomerCode( String customerCode )
	{
		wait.waitForElementDisplayed(CUSTOMER_CODE_INPUT);
		text.enterText(CUSTOMER_CODE_INPUT, customerCode);
	}

	/**
	 * Set Street Value based on AddressType
	 *
	 * @param street
	 * @param addressType
	 */
	private void setStreet( String street, String addressType )
	{
		if ( addressType.equals(AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILLING_STREET_INPUT, street);
		}
		else
		{
			text.enterText(SHIPPING_STREET_INPUT, street);
		}
	}

	/**
	 * Set City Value based on AddressType
	 */
	public void setCity( String city, String addressType )
	{
		if ( addressType.equals(AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILLING_CITY_INPUT, city);
		}
		else
		{
			text.enterText(SHIPPING_CITY_INPUT, city);
		}
	}

	/**
	 * Set State Value based on AddressType
	 *
	 * @param state
	 * @param addressType
	 */
	public void setState( String state, String addressType )
	{
		if ( addressType.equals(AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILLING_STATE_INPUT, state);
		}
		else
		{
			text.enterText(SHIPPING_STATE_INPUT, state);
		}
	}

	/**
	 * Set Zip Value based on AddressType
	 *
	 * @param zip
	 * @param addressType
	 */
	private void setZip( String zip, String addressType )
	{
		if ( addressType.equals(AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILLING_ZIP_INPUT, zip);
		}
		else
		{
			text.enterText(SHIPPING_ZIP_INPUT, zip);
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
		if ( addressType.equals(AddressType.BILLING_ADDRESS.text) )
		{
			text.enterText(BILLING_COUNTRY_INPUT, country);
		}
		else
		{
			text.enterText(SHIPPING_COUNTRY_INPUT, country);
		}
	}

	/**
	 * To Create New Account by passing few parameters
	 *
	 * @param accountName
	 * @param customerCode
	 * @param parentAccount
	 */
	public void createAccount( String accountName, String customerCode, String parentAccount )
	{
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.ACCOUNTS_TAB.text);
		opportunityPage.clickOnNewButton();
		wait.waitForElementDisplayed(ACCOUNT_MODAL);
		setAccountName(accountName);
		selectAccountName(parentAccount);
		setCustomerCode(customerCode);
	}

	/**
	 * To Fill Address based on type of Address
	 *
	 * @param addressType
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public void setAddress( String addressType, String street, String city, String state, String zip, String country )
	{
		setStreet(street, addressType);
		setCity(city, addressType);
		setState(state, addressType);
		setZip(zip, addressType);
		setCountry(country, addressType);
	}

	/**
	 * Click on Save button
	 */
	public void clickAccountSaveButton( )
	{
		wait.waitForElementDisplayed(ACCOUNT_SAVE_BUTTON);
		click.clickElement(ACCOUNT_SAVE_BUTTON);
		waitForSalesForceLoaded();
	}

	public void clickAccountDetailsTab( )
	{
		wait.waitForElementDisplayed(ACCOUNT_DETAILS_TAB);
		new WebDriverWait(driver, 5)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(ACCOUNT_DETAILS_TAB));
		click.clickElement(ACCOUNT_DETAILS_TAB);
		waitForSalesForceLoaded();
	}

	/**
	 * Click on Validate Address button
	 */
	public void clickValidateAddressButton( )
	{
		wait.waitForElementDisplayed(VALIDATE_ADDRESSES_LINK);
		click.clickElement(VALIDATE_ADDRESSES_LINK);
		waitForPageLoad();
	}

	/**
	 * Click on Update Address button
	 */
	public void clickUpdateAddressButton( )
	{
		wait.waitForElementDisplayed(UPDATE_ADDRESS_BUTTON);
		click.clickElement(UPDATE_ADDRESS_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Delete button
	 */
	public void clickDeleteButton( )
	{
		waitForPageLoad();
		click.clickElement(ACCOUNT_DROPDOWN_ACTIONS);
		wait.waitForElementDisplayed(DELETE_LINK);
		click.clickElement(DELETE_LINK);
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Validate Address
	 */
	public void clickValidateAddress( )
	{
		waitForPageLoad();
		click.clickElement(ACCOUNT_DROPDOWN_ACTIONS);
		wait.waitForElementDisplayed(VALIDATE_ADDRESSES_LINK);
		click.clickElement(VALIDATE_ADDRESSES_LINK);
		waitForPageLoad();
		window.switchToDefaultContent();
		wait.waitForElementDisplayed(By.xpath(".//div/iframe"));
		driver
			.switchTo()
			.frame(driver.findElement(By.xpath(".//div/iframe")));
	}

	/**
	 * Click on Validate Billing Address
	 */
	public void clickUpdateAddressWithVertex( Boolean shippingAddress, String expectedStreet )
	{
		waitForSalesForceLoaded();

		if ( shippingAddress )
		{
			wait.waitForElementDisplayed(SHIPPING_UPDATE_BUTTON);
			click.clickElement(SHIPPING_UPDATE_BUTTON);
		}
		else
		{
			wait.waitForElementDisplayed(BILLING_UPDATE_BUTTON);
			click.clickElement(BILLING_UPDATE_BUTTON);
		}
		waitForSalesForceLoaded();

		window.switchToDefaultContent();
		wait.waitForElementDisplayed(By.xpath(".//div/iframe"));
		driver
			.switchTo()
			.frame(driver.findElement(By.xpath(".//div/iframe")));

		wait.waitForElementDisplayed(By.xpath(".//slot/h2/b[text()='Proposed Address']"));
		new WebDriverWait(driver, 5)
			.ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.elementToBeClickable(UPDATE_ADDRESS));
		wait.waitForElementDisplayed(UPDATE_ADDRESS);
		click.javascriptClick(UPDATE_ADDRESS);
		if ( shippingAddress )
		{
			wait.waitForElementDisplayed(SHIPPING_ADDRESS_CONFIRMATION);
		}
		else
		{
			wait.waitForElementDisplayed(BILLING_ADDRESS_CONFIRMATION);
		}
	}

	/**
	 * return to Account Page
	 */
	public void returnToAccount( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(RETURN_TO_ACCOUNT);
		click.clickElement(RETURN_TO_ACCOUNT);
		waitForSalesForceLoaded();
	}

	/**
	 * To get Account Name
	 *
	 * @return
	 */
	public String getAccountName( )
	{
		wait.waitForElementDisplayed(ACCOUNT_NAME_VALUE);
		String accountName = text.getElementText(ACCOUNT_NAME_VALUE);
		return accountName;
	}

	/**
	 * To get Owner Name
	 *
	 * @return
	 */
	public String getOwnerName( )
	{
		wait.waitForElementDisplayed(ACCOUNT_OWNER_VALUE);
		String ownerName = text.getElementText(ACCOUNT_OWNER_VALUE);
		return ownerName;
	}

	/**
	 * To get Account Type
	 *
	 * @return
	 */
	public String getAccountType( )
	{
		wait.waitForElementDisplayed(ACCOUNT_TYPE_VALUE);
		String accountType = text.getElementText(ACCOUNT_TYPE_VALUE);
		return accountType;
	}

	/**
	 * To get Customer Code
	 *
	 * @return
	 */
	public String getCustomerCode( )
	{
		wait.waitForElementDisplayed(CUSTOMER_CODE_VALUE);
		String customerCode = text.getElementText(CUSTOMER_CODE_VALUE);
		return customerCode;
	}

	/**
	 * To get Billing Address Value
	 *
	 * @return
	 */
	public String getBillingAddressValue( )
	{
		wait.waitForElementDisplayed(BILLING_ADDRESS_VALUE);
		String billingAddress = attribute.getElementAttribute(BILLING_ADDRESS_VALUE, "title");
		return billingAddress;
	}

	/**
	 * To get Shipping Address Value
	 *
	 * @return
	 */
	public String getShippingAddressValue( )
	{
		wait.waitForElementDisplayed(SHIPPING_ADDRESS_VALUE);
		String shippingAddress = attribute.getElementAttribute(SHIPPING_ADDRESS_VALUE, "title");
		return shippingAddress;
	}

	/**
	 * Set Account Name
	 *
	 * @param accountName
	 */
	public void selectAccountName( String accountName )
	{
		if ( accountName != null )
		{
			setParentAccountNameSearch(accountName);
			Set<String> allWindows = window.getWindowHandles();
			window.switchToWindow();
			window.switchToFrame(RESULT_FRAME);
			selectParentLookupItem(accountName);
			window.switchToWindowTextInTitle(TestInput.OPP_EDIT_WINDOW_TITLE);
		}
	}

	/**
	 * Set type
	 *
	 * @param type
	 */
	public void selectType( String type )
	{
		if ( type != null )
		{
			clickType();
			Set<String> allWindows = window.getWindowHandles();
			window.switchToWindow();
			selectTypeLookupItem(type);
		}
	}

	/**
	 * Select the Account name from the Account pop up
	 *
	 * @param lookupItem
	 */
	public void selectParentLookupItem( String lookupItem )
	{
		String labelLocator = String.format("//div[contains(@title,'%s')]", lookupItem);
		By lookUpItem = By.xpath(labelLocator);
		wait.waitForElementDisplayed(lookUpItem);
		click.clickElement(lookUpItem);
	}

	/**
	 * Select the Type from the Type pop up
	 *
	 * @param lookupItem
	 */
	public void selectTypeLookupItem( String lookupItem )
	{
		String labelLocator = String.format("//a[contains(@title,'%s')]", lookupItem);
		By lookUpItem = By.xpath(labelLocator);
		wait.waitForElementDisplayed(lookUpItem);
		click.clickElement(lookUpItem);
	}

	/**
	 * Click on Copy Billing Address to Shipping Address link
	 */
	public void clickCopyAddressLink( )
	{
		wait.waitForElementDisplayed(COPY_ADDRESS_LINK);
		click.clickElement(COPY_ADDRESS_LINK);
		waitForPageLoad();
	}

	/**
	 * To Edit account created
	 */
	public void clickEditButton( )
	{
		if ( driver
			.getCurrentUrl()
			.contains("lb2bqa") )
		{
			waitForPageLoad();
			click.clickElement(ACCOUNT_DROPDOWN_ACTIONS);
			wait.waitForElementDisplayed(QA_EDIT_LINK);
			click.clickElement(QA_EDIT_LINK);
		}
		else
		{
			wait.waitForElementDisplayed(EDIT_BUTTON);
			click.clickElement(EDIT_BUTTON);
		}
		waitForSalesForceLoaded();
	}
}
