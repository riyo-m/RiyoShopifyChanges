package com.vertex.quality.connectors.salesforce.pages.lom;

import com.vertex.quality.connectors.salesforce.data.TestInput;
import com.vertex.quality.connectors.salesforce.enums.Constants.AddressType;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import com.vertex.quality.connectors.salesforce.pages.lb2b.SalesForceB2BOpportunityPage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Common functions for anything related to Salesforce Lightning Accounts Page.
 *
 * @author brenda.johnson
 */
public class SalesForceLOMAccountsPage extends SalesForceBasePage
{
	protected By BUTTON_NEW = By.xpath(".//ul[contains(@class, 'branding-actions')]/li/a/div[@title = 'New']");
	protected By ACCOUNTS_TAB = By.id("Account_Tab");
	protected By NEW_BUTTON = By.name("new");
	protected By ACCOUNT_NAME = By.xpath(".//label[text()='Account Name']/../..//input");
	protected By FIRST_NAME = By.xpath(".//input[@name='firstName']");
	protected By LAST_NAME = By.xpath(".//input[@name='lastName']");
	protected By PARENT_ACCOUNT_NAME_SEARCH = By.xpath(".//input[@name='parentAccount']");
	protected By DELETE_LINK = By.xpath("//span[contains(text(),'Delete')]");
	protected By DELETE_BUTTON = By.xpath("//button[@title='Delete']");
	protected By RESULT_FRAME = By.id("resultsFrame");
	protected By TYPE_DROPDOWN = By.xpath("//span[text()='Type']/../..//a");
	protected By CUSTOMER_CODE_INPUT = By.xpath(".//label[contains(text(),'CustomerCode')]/../div/input");
	protected By CUSTOMER_CLASS_CODE_INPUT = By.xpath(".//label[contains(text(),'CustomerClassCode')]/../div/input");
	protected By TAX_EXEMPT_CHECKBOX = By.xpath(".//span[contains(text(),'IsTaxExempt')]/../following-sibling::div/span/input[contains(@name, IsTaxExempt)]");
	protected By TAX_REGISTRATION_NUMBER = By.xpath(".//input[contains(@name,'VTX_RegistrationNumber')]");
	protected By EXEMPTION_CERTIFICATE_INPUT = By.xpath(".//input[contains(@name,'VTX_ExemptionCertificate')]");

	protected By SHIPPING_STREET_INPUT = By.xpath(
		".//label[contains(text(), 'Shipping Street')]/../div/textarea[@name='street']");
	protected By SHIPPING_CITY_INPUT = By.xpath(
		".//label[contains(text(), 'Shipping City')]/../div/input[@name='city']");
	protected By SHIPPING_STATE_INPUT = By.xpath(
		".//label[contains(text(), 'Shipping State/Province')]/../div/input[@name='province']");
	protected By SHIPPING_ZIP_INPUT = By.xpath(
		".//label[contains(text(), 'Shipping Zip/Postal Code')]/../div/input[@name='postalCode']");
	protected By SHIPPING_COUNTRY_INPUT = By.xpath(
		".//label[contains(text(), 'Shipping Country')]/../div/input[@name='country']");

	protected By BILLING_STREET_INPUT = By.xpath(
		".//label[contains(text(), 'Billing Street')]/../div/textarea[@name='street']");
	protected By BILLING_CITY_INPUT = By.xpath(".//label[contains(text(), 'Billing City')]/../div/input[@name='city']");
	protected By BILLING_STATE_INPUT = By.xpath(
		".//label[contains(text(), 'Billing State/Province')]/../div/input[@name='province']");
	protected By BILLING_ZIP_INPUT = By.xpath(
		".//label[contains(text(), 'Billing Zip/Postal Code')]/../div/input[@name='postalCode']");
	protected By BILLING_COUNTRY_INPUT = By.xpath(
		".//label[contains(text(), 'Billing Country')]/../div/input[@name='country']");

	protected By BILLING_UPDATE_BUTTON = By.xpath(
		".//*[@data-addresstype='billing']/button[@title='Update from Vertex']");
	protected By BILLING_ADDRESS_CONFIRMATION = By.xpath(".//lightning-layout-item[.='Billing Address is correct']");
	protected By SHIPPING_UPDATE_BUTTON = By.xpath(
		".//*[@data-addresstype='shipping']/button[@title='Update from Vertex']");
	protected By SHIPPING_ADDRESS_CONFIRMATION = By.xpath(".//lightning-layout-item[.='Shipping Address is correct']");
	protected By UPDATE_ADDRESS = By.xpath(".//button[text()='Update Address']");
	protected By RETURN_TO_ACCOUNT = By.xpath(".//input[@value='Return to Account']");

	protected By VALIDATE_ADDRESSES_BUTTON = By.cssSelector("#topButtonRow>input[name='validateandupdateaddress']");
	protected By EDIT_BUTTON = By.xpath("//button[@name='Edit']");

	protected By SHIPPING_ADDRESS_VALUE = By.xpath(
		".//records-record-layout-row/slot/records-record-layout-item/div/div/div/span[text()='Shipping Address']/parent::div/following-sibling::div//a");
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

	protected By ACCOUNT_SAVE_BUTTON = By.xpath("//button[@name = 'SaveEdit' and text()='Save']");
	protected By ACCOUNT_DETAILS_TAB = By.xpath("//*[@id='detailTab__item']");

	protected By ACCOUNT_DROPDOWN_ACTIONS = By.xpath(
		"//button[contains(@class,'slds-button slds-button_icon-border-filled')]//lightning-primitive-icon//*[local-name()='svg']");

	protected By RADIO_PERSON = By.xpath(".//*[contains(@id,'brandBand')]/.//label/span[text()='Person Account']");
	protected By BUTTON_NEXT = By.xpath(".//*[contains(@id,'brandBand')]/div/div/div/div/button/span[text()='Next']");

	protected By ACCOUNT_ID = By.xpath(
		".//p[text()='Account Id']/..//records-formula-output/slot/lightning-formatted-text");

	protected By DROPDOWN_ACCOUNT_LIST = By.xpath(".//*[@id=\"split-left\"]/.//*[text()='Show more actions']");
	protected By DROPDOWN_ACCOUNT_NEW = By.xpath(".//div/ul//li/a[@title='New']");

	protected By DROPDOWN_ACCOUNT_OPTIONS = By.xpath(
		".//li[contains(@class,'dropdown')]/lightning-button-menu/button[@type='button']/lightning-primitive-icon");
	protected By DROPDOWN_ACCOUNT_DELETE = By.xpath(".//a[@name='Delete']");

	protected By BUTTON_DELETE = By.xpath(
		".//body/div[4]/div[2]/div/div[2]/div/div[3]/div/button[2]/span[text()='Delete']");

	protected By SELECT_LIST_VIEW = By.xpath(".//*[@title='Select List View']");
	protected By ALL_ACCOUNTS_VIEW = By.xpath(".//div/div/ul/li/a/span[text()='All Accounts']");

	protected By SEARCH_LIST_TEXTBOX = By.xpath(".//input[@name='Account-search-input']");

	SalesForceB2BOpportunityPage opportunityPage;
	SalesForceLOMPostLogInPage postLogInPage;

	public SalesForceLOMAccountsPage( WebDriver driver )
	{
		super(driver);

		opportunityPage = new SalesForceB2BOpportunityPage(driver);
		postLogInPage = new SalesForceLOMPostLogInPage(driver);
	}


	/**
	 * change view to see all accounts
	 */
	public void switchToViewAllAccounts( )
	{
		wait.waitForElementDisplayed(SELECT_LIST_VIEW);
		click.clickElement(SELECT_LIST_VIEW);

		wait.waitForElementDisplayed(ALL_ACCOUNTS_VIEW);
		click.clickElement(ALL_ACCOUNTS_VIEW);
	}

	/**
	 * set search window to search string
	 *
	 * @param stringToSearch
	 */
	public void searchFor( String stringToSearch )
	{
		wait.waitForElementDisplayed(SEARCH_LIST_TEXTBOX);
		text.enterText(SEARCH_LIST_TEXTBOX, stringToSearch);
	}

	/**
	 * get a list of accounts that match search criteria
	 *
	 * @param accountName
	 *
	 * @return list of account names
	 */
	public List<String> listOfMatchingAccounts( String accountName )
	{
		String searchString = String.format(".//ul/li/a/div/span/span[contains(text(), '%s')]", accountName);
		List<WebElement> accountsWebMatch = wait.waitForAllElementsDisplayed(By.xpath(searchString));

		List<String> accountsMatch = new ArrayList<>();
		for ( WebElement account : accountsWebMatch )
		{
			String word = account.getText();
			accountsMatch.add(word);
		}
		return accountsMatch;
	}

	/**
	 * select existing account from list
	 *
	 * @param accountName
	 */
	public void selectExistingAccount( String accountName )
	{
		String selectAccount = String.format("//*[@id=\"split-left\"]/.//ul/li/a/div/span/span[contains(text(),'%s')]",
			accountName);
		wait.waitForElementDisplayed(By.xpath(selectAccount));
		click.clickElement(By.xpath(selectAccount));
	}

	/**
	 * Set Account Name
	 *
	 * @param accountName
	 */
	public void setAccountName( String accountName )
	{
		waitForSalesForceLoaded();
		try
		{
			wait.waitForElementDisplayed(ACCOUNT_NAME, 5);
			text.enterText(ACCOUNT_NAME, accountName);
		}
		catch ( Exception e )
		{
			wait.waitForElementDisplayed(FIRST_NAME);
			text.enterText(FIRST_NAME, accountName);
			text.enterText(LAST_NAME, "VTX");
		}
	}

	/**
	 * Set ParentAccount Name
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
	 * Set CustomerCode
	 *
	 * @param customerClassCode
	 */
	private void setCustomerClassCode( String customerClassCode )
	{
		wait.waitForElementDisplayed(CUSTOMER_CLASS_CODE_INPUT);
		text.enterText(CUSTOMER_CLASS_CODE_INPUT, customerClassCode);
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
	 * @param type
	 * @param customerCode
	 * @param parentAccount
	 */
	public void createAccount( String accountName, String type, String customerCode, String parentAccount )
	{
		postLogInPage.closeOpenTabs();
		postLogInPage.switchToSubAppMenu("Accounts");
		waitForSalesForceLoaded();
		clickNewButton();
		setAccountType("Person");
		setAccountName(accountName);
		selectAccountName(parentAccount);
		selectType(type);
		setCustomerCode(customerCode);
	}

	/**
	 * * To Create New Account by passing few parameters
	 *
	 * @param accountName
	 * @param type
	 * @param customerCode
	 * @param parentAccount
	 * @param customerClassCode
	 */
	public void createAccount( String accountName, String type, String customerCode, String parentAccount,
		String customerClassCode )
	{
		createAccount(accountName, type, customerCode, parentAccount);
		setCustomerClassCode(customerClassCode);
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

	/**
	 * set account type
	 *
	 * @param accountType
	 */
	public void setAccountType( String accountType )
	{
		if(element.isElementPresent(RADIO_PERSON)) {
			if (accountType.contains("Person")) {
				click.clickElement(RADIO_PERSON);
			}
			click.clickElement(BUTTON_NEXT);
		}
	}

	/**
	 * click on the account details tab
	 */
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
	 * Click on Update Address button
	 */
	public void clickUpdateAddressButton( )
	{
		wait.waitForElementDisplayed(UPDATE_ADDRESS_BUTTON);
		click.clickElement(UPDATE_ADDRESS_BUTTON);
		waitForPageLoad();
	}

	/**
	 * Click on Validate destination Billing or Shipping Address
	 *
	 * @param shippingAddress
	 */
	public void clickUpdateAddressWithVertex( Boolean shippingAddress )
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
	 * To get Billing Address Value
	 *
	 * @return
	 */
	public String getBillingAddressValue( )
	{
		wait.waitForElementDisplayed(BILLING_ADDRESS_VALUE);
		String billingAddress = attribute.getElementAttribute(BILLING_ADDRESS_VALUE, "aria-label");
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
		String shippingAddress = attribute.getElementAttribute(SHIPPING_ADDRESS_VALUE, "aria-label");
		return shippingAddress;
	}

	/**
	 * Click on Delete button
	 */
	public void clickDeleteButton( )
	{
		click.clickElement(ACCOUNT_DROPDOWN_ACTIONS);
		wait.waitForElementDisplayed(DELETE_LINK);
		click.clickElement(DELETE_LINK);
		wait.waitForElementDisplayed(DELETE_BUTTON);
		click.clickElement(DELETE_BUTTON);
		waitForPageLoad();
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
	 * To get Customer COde
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
	 * To get Account Id
	 *
	 * @return
	 */
	public String getAccountId( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(ACCOUNT_ID);
		return attribute.getElementAttribute(ACCOUNT_ID, "value");
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
	 * To Edit account created
	 */
	public void clickEditButton( )
	{
		wait.waitForElementDisplayed(EDIT_BUTTON);
		click.clickElement(EDIT_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Click To Create New account created
	 */
	public void clickNewButton( )
	{
		waitForSalesForceLoaded();
		if ( element.isElementDisplayed(BUTTON_NEW) )
		{
			wait.waitForElementDisplayed(BUTTON_NEW);
			click.javascriptClick(BUTTON_NEW);
		}
		else
		{
			wait.waitForElementDisplayed(DROPDOWN_ACCOUNT_LIST);
			click.javascriptClick(DROPDOWN_ACCOUNT_LIST);
			wait.waitForElementDisplayed(DROPDOWN_ACCOUNT_NEW);
			click.javascriptClick(DROPDOWN_ACCOUNT_NEW);
		}
		waitForSalesForceLoaded();
	}

	/**
	 * click on delete account and accept prompt
	 */
	public void deleteAccount( )
	{
		waitForSalesForceLoaded();

		wait.waitForElementDisplayed(DROPDOWN_ACCOUNT_OPTIONS);
		click.clickElement(DROPDOWN_ACCOUNT_OPTIONS);
		wait.waitForElementDisplayed(DROPDOWN_ACCOUNT_DELETE);
		click.javascriptClick(DROPDOWN_ACCOUNT_DELETE);

		wait.waitForElementDisplayed(BUTTON_DELETE);
		click.clickElement(BUTTON_DELETE);

		waitForSalesForceLoaded();
	}

	/**
	 * Set tax exempt flag
	 */
	public void setTaxExemptCheckbox( )
	{
		click.javascriptClick(TAX_EXEMPT_CHECKBOX);
		waitForSalesForceLoaded();
	}

	/**
	 * Set tax registration field
	 * @param registrationNum
	 */
	public void setTaxRegistrationNumber( String registrationNum )
	{
		wait.waitForElementDisplayed(TAX_REGISTRATION_NUMBER);
		text.enterText(TAX_REGISTRATION_NUMBER, registrationNum);
	}

	/**
	 * Set Customer Exemption Certificate
	 * @param certificateNumber
	 */
	public void setCustomerExemptionCertificate( String certificateNumber )
	{
		wait.waitForElementDisplayed(EXEMPTION_CERTIFICATE_INPUT);
		text.enterText(EXEMPTION_CERTIFICATE_INPUT, certificateNumber);
	}
}