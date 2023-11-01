package com.vertex.quality.connectors.salesforce.pages.crm;

import com.vertex.quality.connectors.salesforce.data.TestInput;
import com.vertex.quality.connectors.salesforce.enums.Constants.AddressType;
import com.vertex.quality.connectors.salesforce.enums.NavigateMenu;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

/**
 * Common functions for anything related to Salesforce Basic Accounts Page.
 *
 * @author
 */
public class SalesForceCRMAccountsPage extends SalesForceBasePage {
	protected By ACCOUNTS_TAB = By.id("Account_Tab");
	protected By NEW_BUTTON = By.name("new");
	protected By ACCOUNT_NAME = By.name("acc2");
	protected By DELETE_BUTTON = By.name("delete");
	protected By LOOKUP_ICON = By.className("lookupIcon");
	protected By RESULT_FRAME = By.id("resultsFrame");
	protected By LOOKUP_SEARCH_INPUT = By.id("lksrch");
	protected By TYPE_DROPDOWN = By.id("acc6");
	protected By TAX_REGISTRATION_INPUT = By.xpath("//label[contains(text(),'Registration')]/parent::td/following-sibling::td//input");
	protected By CUSTOMER_CODE_INPUT = By.xpath("//label[contains(text(),'CustomerCode')]/parent::td/following-sibling::td//input");
	protected By CUSTOMER_Class_CODE_INPUT = By.xpath(
			"//label[contains(text(),'CustomerClass')]/parent::td/following-sibling::td//input");
	protected By SHIPPING_STREET_INPUT = By.id("acc18street");
	protected By SHIPPING_CITY_INPUT = By.id("acc18city");
	protected By SHIPPING_STATE_INPUT = By.id("acc18state");
	protected By SHIPPING_ZIP_INPUT = By.id("acc18zip");
	protected By SHIPPING_COUNTRY_INPUT = By.id("acc18country");
	protected By TAX_EXEMPT_CHECKBOX = By.xpath(
			"//label[contains(text(),'Tax Exempt')]/parent::td/following-sibling::td//input");
	protected By CUSTOMER_EXEMPT_CERTIFICATE_INPUT = By.xpath(
			"//label[contains(text(),'ExemptionCertificate')]/parent::td/following-sibling::td//input");
	protected By HAS_PHYSICAL_PRESENCE_CHECKBOX = By.xpath(
			"//label[contains(text(),'VTX_HasPhysicalPresenceIndicator')]/parent::td/following-sibling::td//input[@type='checkbox']");
	protected By BILL_TO_CONTACT_INPUT = By.xpath(
			"//label[contains(text(),'Bill To Contact')]/parent::td/following-sibling::td//span[@class='lookupInput']/input");

	protected By BILLING_STREET_INPUT = By.id("acc17street");
	protected By BILLING_CITY_INPUT = By.id("acc17city");
	protected By BILLING_STATE_INPUT = By.id("acc17state");
	protected By BILLING_ZIP_INPUT = By.id("acc17zip");
	protected By BILLING_COUNTRY_INPUT = By.id("acc17country");

	protected By VALIDATE_ADDRESSES_BUTTON = By.xpath(
			".//input[@value='Validate Addresses' and @title='Validate Addresses']");
	protected By EDIT_BUTTON = By.cssSelector("#topButtonRow input[title='Edit']");
	protected By ADDRESS_CLEANSING_DISABLED_HINT = By.xpath(
			".//vertex-vertex-address-validation//slot[text()='Address Validation is not enabled for this org.']");

	protected By SHIPPING_ADDRESS_VALUE = By.xpath("//td[text()='Shipping Address']/following-sibling::td[1]//div");
	protected By BILLING_ADDRESS_VALUE = By.xpath("//td[text()='Billing Address']/following-sibling::td[1]//div");

	protected By BILLING_UPDATE_BUTTON = By.xpath(
			".//*[@data-addresstype='billing']/button[@title='Update from Vertex']");
	protected By BILLING_ADDRESS_CONFIRMATION = By.xpath(".//lightning-layout-item[.='Billing Address is correct']");
	protected By SHIPPING_UPDATE_BUTTON = By.xpath(
			".//*[@data-addresstype='shipping']/button[@title='Update from Vertex']");
	protected By SHIPPING_ADDRESS_CONFIRMATION = By.xpath(".//lightning-layout-item[.='Shipping Address is correct']");
	protected By UPDATE_ADDRESS = By.xpath(".//button[text()='Update Address']");
	protected By RETURN_TO_ACCOUNT = By.xpath(".//input[@value='Return to Account']");

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
	protected By ADDRESS_VALIDATION_CANCEL_BUTTON = By.xpath(".//lightning-button/button[text()='Cancel']");
	protected By ADDRESS_VALIDATION_ERROR_MESSAGE = By.xpath(".//lightning-layout-item/slot[contains(text(), 'ERROR')]");

	protected By SAVE_BUTTON = By.cssSelector(".pbBottomButtons  input[title= 'Save']");
	protected By LOOKUP_ACCOUNT_NAME = By.xpath(
			"//label[contains(text(),'Account Name')]/parent::td/following-sibling::td//input");
	protected By LOOKUP_STREET = By.id("street");
	protected By LOOKUP_CITY = By.id("city");
	protected By LOOKUP_STATE = By.id("state");
	protected By LOOKUP_ZIP = By.id("zip");
	protected By LOOKUP_COUNTRY = By.id("country");

	protected By VIEW_GO_BUTTON = By.xpath(".//form/div/span/span/input[@name='go']");
	protected By VIEW_Q_LINK = By.xpath("//form/div/div/div/a/span[text()='Q']");

	protected By ACCOUNT_SAVE_BUTTON = By.xpath("//div[@class = 'pbHeader']//input[@title = 'Save']");
	protected By ACCOUNT_SAVE_IGNORE_BUTTON = By.xpath(".//input[@class='btn' and @value = 'Save (Ignore Alert)']");

	SalesForceCRMOpportunityPage opportunityPage;
	SalesForceCRMPostLogInPage postLogInPage;

	public SalesForceCRMAccountsPage(WebDriver driver) {
		super(driver);

		opportunityPage = new SalesForceCRMOpportunityPage(driver);
		postLogInPage = new SalesForceCRMPostLogInPage(driver);
	}

	/**
	 * Set Account Name
	 *
	 * @param accountName
	 */
	public void setAccountName(String accountName) {
		wait.waitForElementDisplayed(ACCOUNT_NAME);
		text.enterText(ACCOUNT_NAME, accountName);
	}

	/**
	 * Select Type
	 *
	 * @param type
	 */
	private void setType(String type) {
		wait.waitForElementDisplayed(TYPE_DROPDOWN);
		dropdown.selectDropdownByDisplayName(TYPE_DROPDOWN, type);
	}

	private void setTaxRegistration(String taxRegistration)
	{
		wait.waitForElementDisplayed(TAX_REGISTRATION_INPUT);
		text.enterText(TAX_REGISTRATION_INPUT, taxRegistration);
	}

	/**
	 * Set CustomerCode
	 *
	 * @param customerCode
	 */
	private void setCustomerCode(String customerCode) {
		wait.waitForElementDisplayed(CUSTOMER_CODE_INPUT);
		text.enterText(CUSTOMER_CODE_INPUT, customerCode);
	}

	/**
	 * Set CustomerClassCode
	 *
	 * @param customerClassCode
	 */
	private void setCustomerClassCode(String customerClassCode) {
		wait.waitForElementDisplayed(CUSTOMER_Class_CODE_INPUT);
		text.enterText(CUSTOMER_Class_CODE_INPUT, customerClassCode);
	}

	/**
	 * Set Street Value based on AddressType
	 *
	 * @param street
	 * @param addressType
	 */
	private void setStreet(String street, String addressType) {
		if (addressType.equals(AddressType.BILLING_ADDRESS.text)) {
			text.enterText(BILLING_STREET_INPUT, street);
		} else {
			text.enterText(SHIPPING_STREET_INPUT, street);
		}
	}

	/**
	 * Set City Value based on AddressType
	 */
	public void setCity(String city, String addressType) {
		if (addressType.equals(AddressType.BILLING_ADDRESS.text)) {
			text.enterText(BILLING_CITY_INPUT, city);
		} else {
			text.enterText(SHIPPING_CITY_INPUT, city);
		}
	}

	/**
	 * Set State Value based on AddressType
	 *
	 * @param state
	 * @param addressType
	 */
	public void setState(String state, String addressType) {
		if (addressType.equals(AddressType.BILLING_ADDRESS.text)) {
			text.enterText(BILLING_STATE_INPUT, state);
		} else {
			text.enterText(SHIPPING_STATE_INPUT, state);
		}
	}

	/**
	 * Set Zip Value based on AddressType
	 *
	 * @param zip
	 * @param addressType
	 */
	private void setZip(String zip, String addressType) {
		if (addressType.equals(AddressType.BILLING_ADDRESS.text)) {
			text.enterText(BILLING_ZIP_INPUT, zip);
		} else {
			text.enterText(SHIPPING_ZIP_INPUT, zip);
		}
	}

	/**
	 * Set Country Value based on AddressType
	 *
	 * @param country
	 * @param addressType
	 */
	private void setCountry(String country, String addressType) {
		if (addressType.equals(AddressType.BILLING_ADDRESS.text)) {
			text.enterText(BILLING_COUNTRY_INPUT, country);
		} else {
			text.enterText(SHIPPING_COUNTRY_INPUT, country);
		}
	}

	/**
	 * Set Bill To Contact on account
	 * @param billToName
	 */
	public void setBillToContact(String billToName)
	{
		wait.waitForElementDisplayed(BILL_TO_CONTACT_INPUT);
		text.enterText(BILL_TO_CONTACT_INPUT, billToName);
	}

	/**
	 * Set has physical presence indicator
	 * @param hasPhysicalPresenceIndicator
	 */
	public void setHasPhysicalPresenceIndicator(Boolean hasPhysicalPresenceIndicator)
	{
		wait.waitForElementDisplayed(HAS_PHYSICAL_PRESENCE_CHECKBOX);
		checkbox.setCheckbox(HAS_PHYSICAL_PRESENCE_CHECKBOX, hasPhysicalPresenceIndicator);
		waitForSalesForceLoaded();
	}

	/**
	 * To Create New Account by passing few parameters
	 *
	 * @param accountName
	 * @param type
	 * @param customerCode
	 * @param parentAccount
	 * @param customerClassCode
	 */
	public void createAccount(String accountName, String type, String customerCode, String parentAccount,
							  String customerClassCode)
	{
		createAccount( accountName, type, customerCode, parentAccount,
				customerClassCode, "");
	}

	/**
	 * To Create New Account by passing few parameters
	 *
	 * @param accountName
	 * @param type
	 * @param customerCode
	 * @param parentAccount
	 * @param customerClassCode
	 * @param taxRegistration
	 */
	public void createAccount( String accountName, String type, String customerCode, String parentAccount,
		String customerClassCode, String taxRegistration )
	{
		postLogInPage.clickSalesPageTab(NavigateMenu.Sales.ACCOUNTS_TAB.text);
		clickGoButton();
		clickQLink();
		while ( checkForAccount(accountName) )
		{
			clickDeleteButton();
			postLogInPage.clickSalesPageTab(NavigateMenu.Sales.ACCOUNTS_TAB.text);
			clickGoButton();
			clickQLink();
		}

		opportunityPage.clickOnNewButton();
		setAccountName(accountName);
		selectAccountName(parentAccount);
		setType(type);
		setCustomerCode(customerCode);
		setCustomerClassCode(customerClassCode);
		setTaxRegistration(taxRegistration);
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
	 * Set tax exempt checkbox
	 *
	 * @param taxExempt
	 */
	public void setTaxExemptFlag( Boolean taxExempt )
	{
		wait.waitForElementDisplayed(TAX_EXEMPT_CHECKBOX);
		checkbox.setCheckbox(TAX_EXEMPT_CHECKBOX, taxExempt);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Customer Exemption Certificate
	 */
	public void setCustomerExemptionCertificate(String exemptionCertificate)
	{
		wait.waitForElementDisplayed(CUSTOMER_EXEMPT_CERTIFICATE_INPUT);
		text.enterText(CUSTOMER_EXEMPT_CERTIFICATE_INPUT, exemptionCertificate);
	}

	/**
	 * Click on Save button
	 */
	public void clickAccountSaveButton( )
	{
		wait.waitForElementDisplayed(ACCOUNT_SAVE_BUTTON);
		click.clickElement(ACCOUNT_SAVE_BUTTON);
		clickAccountSaveIgnoreAlertButton();
		waitForPageLoad();
		wait.waitForElementDisplayed(DELETE_BUTTON);
	}

	public Boolean validateAddressButtonExists()
	{
		return element.isElementPresent(VALIDATE_ADDRESSES_BUTTON);
	}

	/**
	 * Click on Validate Address button
	 */
	public void clickValidateAddressButton( )
	{
		wait.waitForElementDisplayed(VALIDATE_ADDRESSES_BUTTON);
		click.clickElement(VALIDATE_ADDRESSES_BUTTON);
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
		if(element.isElementPresent(UPDATE_ADDRESS)) {
			wait.waitForElementDisplayed(By.xpath(".//slot/h2/b[text()='Proposed Address']"));
			new WebDriverWait(driver, 5)
					.ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(UPDATE_ADDRESS));
			wait.waitForElementDisplayed(UPDATE_ADDRESS);
			click.javascriptClick(UPDATE_ADDRESS);
			try {
				waitForSalesForceLoaded();
				if (shippingAddress) {
					wait.waitForElementDisplayed(SHIPPING_ADDRESS_CONFIRMATION, 5);
				} else {
					wait.waitForElementDisplayed(BILLING_ADDRESS_CONFIRMATION, 5);
				}
				waitForSalesForceLoaded();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				refreshPage();
			}
		}
	}

	/**
	 * Checks to ensure address cleansing is disabled
	 */
	public boolean isAddressCleansingDisabled( )
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		boolean disabled = element.isElementPresent(ADDRESS_CLEANSING_DISABLED_HINT);
		return disabled;
	}

	/**
	 * To get address validation message
	 */
	public String getAddressValidationDisabledMessage( )
	{
		waitForPageLoad();
		window.switchToDefaultContent();
		WebElement hintElement = element.getWebElement(ADDRESS_CLEANSING_DISABLED_HINT);
		String hint = text.getHiddenText(hintElement);
		return hint;
	}

	/**
	 * Click on Delete button
	 */
	public void clickDeleteButton( )
	{
		clickEditButton();
		setAccountName("DELETE");
		clickAccountSaveButton();
		try
		{
			wait.waitForElementDisplayed(DELETE_BUTTON);
			click.clickElement(DELETE_BUTTON);
			alert.acceptAlert(DEFAULT_TIMEOUT);
			waitForPageLoad();
		}
		catch(Exception ex)
		{}
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
	 * To click Account Name
	 */
	public void clickAccountName( String itemName )
	{
		if ( itemName.length() > 15 )
		{
			itemName = itemName.substring(0, 15);
		}
		String itemRow = String.format("//a[contains(text(),'%s')]", itemName);
		click.clickElement(By.xpath(itemRow));
		waitForPageLoad();
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
	 * To get Billing Address Value
	 *
	 * @return
	 */
	public String getBillingAddressValue( )
	{
		wait.waitForElementDisplayed(BILLING_ADDRESS_VALUE);
		String billingAddress = text.getElementText(BILLING_ADDRESS_VALUE);
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
		String shippingAddress = text.getElementText(SHIPPING_ADDRESS_VALUE);
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
			Set<String> allWindows = window.getWindowHandles();
			click.clickElement(LOOKUP_ICON);
			window.switchToWindow();
			window.switchToFrame(RESULT_FRAME);
			selectParentLookupItem(accountName);
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
		jsWaiter.sleep(2000);
		wait.waitForElementDisplayed(EDIT_BUTTON);
		click.clickElement(EDIT_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To Edit account created
	 */
	public void clickGoButton( )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(VIEW_GO_BUTTON);
		click.clickElement(VIEW_GO_BUTTON);
		waitForPageLoad();
	}

	/**
	 * To Edit account created
	 */
	public void clickQLink( )
	{
		wait.waitForElementDisplayed(VIEW_Q_LINK);
		click.clickElement(VIEW_Q_LINK);
		waitForPageLoad();
	}

	/**
	 * return to Account Page
	 */
	public void clickReturnToAccount( )
	{
		waitForSalesForceLoaded();
		click.javascriptClick(RETURN_TO_ACCOUNT);
		waitForPageLoad();
	}

	/**
	 * To Edit account created
	 */
	public boolean checkForAccount( String accountName )
	{
		String accountLookup = String.format("//td/div/a/span[contains(text(),'%s')]", accountName);
		waitForSalesForceLoaded(2000);
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
	 * To Delete account created
	 */
	public void deleteAccount( String accountName )
	{
		clickGoButton();
		clickQLink();
		if ( checkForAccount(accountName) )
		{
			clickDeleteButton();
		}
	}

	/**
	 * Get Address Validation Error Message
	 * @return error message on address validation modal
	 */
	public String getAddressValidationErrorMessage()
	{
		String errorMessage;
		WebElement element = wait.waitForElementPresent(ADDRESS_VALIDATION_ERROR_MESSAGE);
		errorMessage = text.getHiddenText(element);
		return errorMessage;
	}

	/**
	 * Click Address Validation Cancel Button
	 */
	public void clickAddressValidationCancelButton()
	{
		wait.waitForElementDisplayed(ADDRESS_VALIDATION_CANCEL_BUTTON);
		click.clickElement(ADDRESS_VALIDATION_CANCEL_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Click Account Save (Ignore Alert) button
	 */
	public void clickAccountSaveIgnoreAlertButton()
	{
		waitForSalesForceLoaded();
		if(element.isElementPresent(ACCOUNT_SAVE_IGNORE_BUTTON))
		{
			wait.waitForElementDisplayed(ACCOUNT_SAVE_IGNORE_BUTTON);
			click.clickElement(ACCOUNT_SAVE_IGNORE_BUTTON);
		}
	}
}
