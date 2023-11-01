package com.vertex.quality.connectors.salesforce.pages.ob2b;

import com.vertex.quality.common.utils.properties.CommonDataProperties;
import com.vertex.quality.connectors.salesforce.pages.SalesForceBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class SalesForceOB2BOrderSite extends SalesForceBasePage
{
	protected By LINK_HOME = By.xpath(".//ul[contains(@class,'cc_navbar-nav')]/li/a[contains(@class, 'gp_home')]");
	protected By SKU_INPUT = By.xpath(".//input[contains(@id,'quickorder[0].sku')]");
	protected By QUANTITY_INPUT = By.xpath(".//input[contains(@id,'quickorder[0].qty')]");
	protected By BUTTON_ADD_TO_CART = By.xpath(".//button[contains(@class, 'addToCart')]");
	protected By BUTTON_ADD_MORE = By.xpath(".//button[contains(@class, 'addMore')]");
	protected By BUTTON_VIEW_CART = By.xpath(".//button[contains(text(),'View Cart')]");
	protected By BUTTON_PROCEED_TO_CHECKOUT = By.xpath(".//button[contains(text(),'Checkout')]");
	protected By BUTTON_CONTINUE = By.xpath(".//*[@value='Continue']");
	protected By ESTIMATED_TAX = By.xpath(".//span[contains(text(), 'Estimated Tax')]/../span[@class='cc_tax']");
	protected By PURCHASE_ORDER = By.id("accountNumber");
	protected By BUTTON_PROCESS_PAYMENT = By.xpath(".//*[@value='Process Payment']");
	protected By PROFILE_IMG = By.xpath(
		"//community_user-user-profile-menu/.//span[contains(@class, 'slds-avatar')]/img");
	protected By LINK_LOGOUT = By.xpath("//community_user-user-profile-menu/div/div/ul/li/a/span[@title='Log Out']");
	protected By LINK_CLEAR_CART = By.xpath(
		".//b2b_buyer_cart-contents/footer/lightning-button/button[text()='Clear Cart']");
	protected By LINK_CANCEL_CHECKOUT = By.xpath(".//b2b_buyer_cart-async-alert/div/h2/a[text()='Cancel Checkout']");
	protected By LINK_CART = By.xpath(".//a[@id='cartHeader']");
	protected By BUTTON_CLEAR_CART = By.xpath("//*[contains(@id,'modal-content')]/div/slot/div/div[2]/button");
	protected By BUTTON_DELETE_ITEM = By.xpath("//button[contains(@class,'deleteItem removeItemButton')]");
	protected By TAX_CALC_ERROR_MESSAGE = By.cssSelector(".messagingSection-Error .alert");

	protected By FIRST_NAME_FIELD = By.id("firstNameField");
	protected By LAST_NAME_FIELD = By.id("lastNameField");
	protected By EMAIL_FIELD = By.id("emailField");
	protected By ADDRESS1_FIELD = By.id("address1");
	protected By COUNTRY_FIELD = By.name("billingAddress.countryCode");
	protected By STATE_FIELD = By.name("billingAddress.stateCode");
	protected By STATE_TEXT_FIELD = By.name("billingAddress.state");
	protected By CITY_FIELD = By.id("city");
	protected By POSTAL_CODE_FIELD = By.id("postalCode");
	protected By COPY_BILLING_ADDRESS = By.xpath(".//a[@class='copyAddress cc_copy_address']");
	protected By COUPON_CODE_FIELD = By.id("addCouponId");

	protected By SHIP_ADDRESS1_FIELD = By.name("shippingAddress.address1");
	protected By SHIP_COUNTRY_FIELD = By.name("shippingAddress.countryCode");
	protected By SHIP_STATE_FIELD = By.name("shippingAddress.stateCode");
	protected By SHIP_STATE_TEXT_FIELD = By.name("shippingAddress.state");
	protected By SHIP_CITY_FIELD = By.name("shippingAddress.city");
	protected By SHIP_POSTAL_CODE_FIELD = By.name("shippingAddress.postalCode");

	protected By ADD_SHIP_GROUP_BUTTON = By.xpath(".//input[contains(@class, 'addItemGroupBtn')]");
	protected By SAVE_SHIP_GROUP_BUTTON = By.xpath(".//input[@value = 'Save']");
	protected By ENTER_SHIPPING_ADDRESS_LINK = By.xpath(".//p[contains(@class, 'edit_addr_section')]/a[contains(@class, 'editAddr')]");

	protected By SHIP_GROUP_NAME_FIELD = By.name("groupName");
	protected By SHIP_GROUP_FIRST_NAME_FIELD = By.name("shipTo.firstName");
	protected By SHIP_GROUP_LAST_NAME_FIELD = By.name("shipTo.lastName");
	protected By SHIP_GROUP_COMPANY_NAME_FIELD = By.name("shipTo.companyName");
	protected By SHIP_GROUP_ADDRESS1_FIELD = By.name("shipTo.addressFirstline");
	protected By SHIP_GROUP_COUNTRY_FIELD = By.name("shipTo.countryISOCode");
	protected By SHIP_GROUP_STATE_FIELD = By.name("shipTo.stateCode");
	protected By SHIP_GROUP_STATE_TEXT_FIELD = By.name("shipTo.state");
	protected By SHIP_GROUP_CITY_FIELD = By.name("shipTo.city");
	protected By SHIP_GROUP_POSTAL_CODE_FIELD = By.name("shipTo.postalCode");
	protected By SHIP_GROUP_PHONE_FIELD = By.name("shipTo.daytimePhone");
	protected By SHIP_GROUP_EMAIL_FIELD = By.name("shipTo.email");

	protected By BILLING_FIRST_NAME_FIELD = By.name("billTo.firstName");
	protected By BILLING_LAST_NAME_FIELD = By.name("billTo.lastName");
	protected By BILLING_COMPANY_NAME_FIELD = By.name("billTo.companyName");
	protected By BILLING_ADDRESS1_FIELD = By.name("billTo.addressFirstline");
	protected By BILLING_COUNTRY_FIELD = By.name("billTo.countryISOCode");
	protected By BILLING_STATE_FIELD = By.name("billTo.stateCode");
	protected By BILLING_STATE_TEXT_FIELD = By.name("billTo.state");
	protected By BILLING_CITY_FIELD = By.name("billTo.city");
	protected By BILLING_POSTAL_CODE_FIELD = By.name("billTo.postalCode");
	protected By BILLING_PHONE_FIELD = By.name("billTo.daytimePhone");
	protected By BILLING_EMAIL_FIELD = By.name("billTo.email");

	protected By ENTER_BILLING_ADDRESS_LINK = By.className("editBillAddr");

	protected By SHIP_GROUP_MOVE_ITEMS_BUTTON = By.xpath(".//input[contains(@class, 'executeBulkMove')]");
	protected By SHIP_GROUP_MOVE_ITEM_BUTTON = By.xpath(".//input[contains(@class, 'executeMove')]");
	protected By MOVE_ITEMS_BUTTON = By.xpath(".//input[contains(@class, 'bulkMoveBtn')]");
	protected By SHIP_GROUP_DROPDOWN = By.name("moveToGroup");

	protected By LLI_DELETE_SHIP_GROUP_LINK = By.xpath(".//a[contains(@class, 'deleteGrp')]");
	protected By LLI_QUANTITY_TO_MOVE = By.name("moveQty");
	protected By LLI_QUANTITY_PLUS_BUTTON = By.xpath(".//input[@value= '+' and contains(@class, 'plus')]");
	protected By LLI_TAX_CALC_ERROR_MESSAGE = By.id("error");

	protected By LLI_FIRST_NAME_FIELD = By.id("buyerFirstName");
	protected By LLI_LAST_NAME_FIELD = By.id("buyerLastName");
	protected By LLI_PHONE_FIELD = By.id("buyerPhone");
	protected By LLI_EMAIL_FIELD = By.id("buyerEmail");

	protected By LLI_ESTIMATED_TAX_FIELD = By.xpath(".//div/span[text()='Estimated Tax']/following-sibling::span[contains(@class, 'cc_value price')]");
	protected By LLI_PROCEED_BUTTON = By.xpath(".//input[@value='Proceed']");
	protected By LLI_ACCEPT_TERMS_CHECKBOX = By.id("termsAccept");

	protected By SHIPPING_METHOD_DROPDOWN = By.id("shippingMethod");
	protected By SHIPPING_AMOUNT = By.className("cc_shipping_charge");
	protected By SHIP_COMPLETE = By.id("estShipping");
	protected By ACCEPT_TERMS = By.id("terms");
	protected By ACCEPT_AND_PROCEED = By.xpath(".//input[@value='Accept Terms and Proceed']");

	protected By BUTTON_PROCEED_TO_PAYMENT = By.xpath(".//*[@value='Proceed To Payment Information']");
	protected By BUTTON_REMOVE_COUPON = By.cssSelector("button#clearCouponBtn");
	protected By BUTTON_APPLY_COUPON = By.cssSelector("button#addCouponBtn");

	public SalesForceOB2BOrderSite( WebDriver driver )
	{
		super(driver);
	}

	/**
	 * Navigate Home
	 */
	public void navigateHome( )
	{
		wait.waitForElementEnabled(LINK_HOME);
		click.javascriptClick(LINK_HOME);
		waitForPageLoad();
	}

	/**
	 * Set Quick Order Item Sku
	 *
	 * @param itemSku
	 */
	public void setQuickOrderItemSku( String itemSku )
	{
		wait.waitForElementDisplayed(SKU_INPUT);
		text.enterText(SKU_INPUT, itemSku);
	}

	/**
	 * Set Quick Order Quantity
	 *
	 * @param itemSku
	 * @param lineNumber
	 */
	public void setQuickOrderSkuLine( String itemSku, int lineNumber )
	{
		String lineNumberQuantity = String.format(".//input[contains(@id,'quickorder[%s].sku')]", lineNumber);
		By lineQuantity = By.xpath(lineNumberQuantity);
		wait.waitForElementDisplayed(lineQuantity);
		text.enterText(lineQuantity, itemSku + Keys.TAB);
	}

	/**
	 * Set Quick Order Quantity
	 *
	 * @param quantity
	 */
	public void setQuickOrderQuantity( String quantity )
	{
		wait.waitForElementDisplayed(QUANTITY_INPUT);
		text.enterText(QUANTITY_INPUT, quantity + Keys.TAB);
	}

	/**
	 * Set Quick Order Quantity
	 *
	 * @param quantity
	 * @param lineNumber
	 */
	public void setQuickOrderQuantityLine( String quantity, int lineNumber )
	{
		String lineNumberQuantity = String.format(".//input[contains(@id,'quickorder[%s].qty')]", lineNumber);
		By lineQuantity = By.xpath(lineNumberQuantity);
		wait.waitForElementDisplayed(lineQuantity);
		text.enterText(lineQuantity, quantity + Keys.TAB);
	}

	/**
	 * click Quick Order Add to Cart
	 */
	public void clickQuickOrderAddToCart( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(BUTTON_ADD_TO_CART);
		click.clickElement(BUTTON_ADD_TO_CART);
		waitForPageLoad();
	}

	/**
	 * click view cart
	 */
	public void clickViewCart( )
	{
		wait.waitForElementDisplayed(BUTTON_VIEW_CART);
		wait.waitForElementEnabled(BUTTON_VIEW_CART);
		click.javascriptClick(BUTTON_VIEW_CART);
		waitForPageLoad();
	}

	/**
	 * click proceed to checkout
	 */
	public void clickProceedToCheckout( )
	{
		refreshPage();
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(BUTTON_PROCEED_TO_CHECKOUT);
		wait.waitForElementEnabled(BUTTON_PROCEED_TO_CHECKOUT);
		try
		{
			click.clickElementCarefully(BUTTON_PROCEED_TO_CHECKOUT);
		}
		catch ( Exception ex )
		{
			click.javascriptClick(BUTTON_PROCEED_TO_CHECKOUT);
		}
		waitForPageLoad();
	}

	public void clickProceedToPaymentButton( )
	{
		wait.waitForElementDisplayed(BUTTON_PROCEED_TO_PAYMENT);
		wait.waitForElementEnabled(BUTTON_PROCEED_TO_PAYMENT);
		click.clickElement(BUTTON_PROCEED_TO_PAYMENT);
		waitForPageLoad();
	}

	public void clickQuickOrderAddMoreButton( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BUTTON_ADD_MORE);
		click.clickElement(BUTTON_ADD_MORE);
		waitForPageLoad();
	}

	/**
	 * set shipping name
	 *
	 * @param first
	 * @param last
	 */
	public void setShippingName( String first, String last )
	{
		wait.waitForElementDisplayed(FIRST_NAME_FIELD);
		text.enterText(FIRST_NAME_FIELD, first);

		wait.waitForElementDisplayed(LAST_NAME_FIELD);
		text.enterText(LAST_NAME_FIELD, last);

		wait.waitForElementDisplayed(EMAIL_FIELD);
		text.enterText(EMAIL_FIELD, CommonDataProperties.EMAIL);
	}

	/**
	 * Set shipping method from dropdown
	 *
	 * @param shippingMethod
	 */
	public void setShippingMethod( String shippingMethod )
	{
		wait.waitForElementDisplayed(SHIPPING_METHOD_DROPDOWN);
		dropdown.selectDropdownByValue(SHIPPING_METHOD_DROPDOWN, shippingMethod);
	}

	/**
	 * fill full Shipping Address
	 *
	 * @param address1
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 */
	public void setFullShippingAddress(String address1, String city, String state, String postalCode, String country)
	{
		setShippingAddress1(address1);
		setShippingCountry(country);
		setShippingCity(city);
		setShippingState(state);
		setShippingPostalCode(postalCode);
	}

	/**
	 * set shipping address 1
	 *
	 * @param address1
	 */
	private void setShippingAddress1( String address1 )
	{
		wait.waitForElementDisplayed(SHIP_ADDRESS1_FIELD);
		text.enterText(SHIP_ADDRESS1_FIELD, address1);
	}

	/**
	 * set shipping country
	 *
	 * @param country
	 */
	private void setShippingCountry( String country )
	{
		wait.waitForElementDisplayed(SHIP_COUNTRY_FIELD);
		dropdown.selectDropdownByDisplayName(SHIP_COUNTRY_FIELD, country);
	}

	/**
	 * set shipping State from dropdown
	 *
	 * @param state
	 */
	private void setShippingState( String state )
	{
		if (element.isElementPresent(SHIP_STATE_FIELD))
		{
			wait.waitForElementDisplayed(SHIP_STATE_FIELD);
			dropdown.selectDropdownByValue(SHIP_STATE_FIELD, state);
		}
		else
		{
			wait.waitForElementDisplayed(SHIP_STATE_TEXT_FIELD);
			text.enterText(SHIP_STATE_TEXT_FIELD, state);
		}
	}

	/**
	 * set shipping City
	 *
	 * @param city
	 */
	private void setShippingCity( String city )
	{
		wait.waitForElementDisplayed(SHIP_CITY_FIELD);
		text.enterText(SHIP_CITY_FIELD, city);
	}

	/**
	 * set shipping Postal Code
	 *
	 * @param postalCode
	 */
	private void setShippingPostalCode( String postalCode )
	{
		wait.waitForElementDisplayed(SHIP_POSTAL_CODE_FIELD);
		text.enterText(SHIP_POSTAL_CODE_FIELD ,postalCode);
	}

	/**
	 * fill full Billing Address
	 *
	 * @param address1
	 * @param city
	 * @param state
	 * @param postalCode
	 * @param country
	 */
	public void setFullAddress( String address1, String city, String state, String postalCode, String country )
	{
		setAddress1(address1);
		setCountry(country);
		setCity(city);
		if ( element.isElementPresent(STATE_FIELD) )
		{
			setState(state);
		}
		else
		{
			setStateText(state);
		}
		setPostalCode(postalCode);
	}

	/**
	 * set billing address 1
	 *
	 * @param address1
	 */
	private void setAddress1( String address1 )
	{
		wait.waitForElementDisplayed(ADDRESS1_FIELD);
		text.enterText(ADDRESS1_FIELD, address1);
	}

	/**
	 * set country
	 *
	 * @param country
	 */
	private void setCountry( String country )
	{
		wait.waitForElementDisplayed(COUNTRY_FIELD);
		dropdown.selectDropdownByDisplayName(COUNTRY_FIELD, country);
	}

	/**
	 * set State
	 *
	 * @param state
	 */
	private void setState( String state )
	{
		wait.waitForElementDisplayed(STATE_FIELD);
		dropdown.selectDropdownByValue(STATE_FIELD, state);
	}

	/**
	 * set State
	 *
	 * @param state
	 */
	private void setStateText( String state )
	{
		wait.waitForElementDisplayed(STATE_TEXT_FIELD);
		text.enterText(STATE_TEXT_FIELD, state);
	}

	/**
	 * set City
	 *
	 * @param city
	 */
	private void setCity( String city )
	{
		wait.waitForElementDisplayed(CITY_FIELD);
		text.enterText(CITY_FIELD, city);
	}

	/**
	 * set Postal Code
	 *
	 * @param postalCode
	 */
	private void setPostalCode( String postalCode )
	{
		wait.waitForElementDisplayed(POSTAL_CODE_FIELD);
		text.enterText(POSTAL_CODE_FIELD, postalCode);
	}

	/**
	 * Set Shipping Group info
	 * @param shipGroupName
	 * @param firstName
	 * @param lastName
	 * @param addressLine1
	 * @param country
	 * @param state
	 * @param city
	 * @param postalCode
	 */
	public void setShippingGroupInfo( String shipGroupName, String firstName, String lastName, String companyName, String addressLine1, String country, String state, String city, String postalCode)
	{
		setShipGroupName(shipGroupName);
		setShipGroupFirstName(firstName);
		setShipGroupLastName(lastName);
		setShipGroupCompanyName(companyName);
		setShipGroupAddress1(addressLine1);
		setShipGroupCountry(country);
		if ( element.isElementPresent(SHIP_GROUP_STATE_FIELD) )
		{
			setShipGroupState(state);
		}
		else
		{
			setShipGroupStateText(state);
		}
		setShipGroupCity(city);
		setShipGroupPostalCode(postalCode);

		setShipGroupPhone(CommonDataProperties.PHONE);
		setShipGroupEmail(CommonDataProperties.EMAIL);
	}

	/**
	 * Set ship group Name
	 * @param shipGroupName
	 */
	private void setShipGroupName(String shipGroupName)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_NAME_FIELD);
		text.enterText(SHIP_GROUP_NAME_FIELD, shipGroupName);
	}

	/**
	 * Set ship group First Name
	 * @param firstName
	 */
	private void setShipGroupFirstName(String firstName)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_FIRST_NAME_FIELD);
		text.enterText(SHIP_GROUP_FIRST_NAME_FIELD, firstName);
	}

	/**
	 * Set ship group Last Name
	 * @param lastName
	 */
	private void setShipGroupLastName(String lastName)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_LAST_NAME_FIELD);
		text.enterText(SHIP_GROUP_LAST_NAME_FIELD, lastName);
	}

	/**
	 * Set ship group Company Name
	 * @param companyName
	 */
	private void setShipGroupCompanyName(String companyName)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_COMPANY_NAME_FIELD);
		text.enterText(SHIP_GROUP_COMPANY_NAME_FIELD, companyName);
	}

	/**
	 * Set ship group address one
	 * @param addressLine1
	 */
	private void setShipGroupAddress1(String addressLine1)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_ADDRESS1_FIELD);
		text.enterText(SHIP_GROUP_ADDRESS1_FIELD, addressLine1);
	}

	/**
	 * Set ship group country
	 * @param country
	 */
	private void setShipGroupCountry(String country)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_COUNTRY_FIELD);
		dropdown.selectDropdownByDisplayName(SHIP_GROUP_COUNTRY_FIELD, country);
		waitForSalesForceLoaded();
	}

	/**
	 * set ship group State
	 *
	 * @param state
	 */
	private void setShipGroupState( String state )
	{
		wait.waitForElementDisplayed(SHIP_GROUP_STATE_FIELD);
		dropdown.selectDropdownByValue(SHIP_GROUP_STATE_FIELD, state);
		waitForSalesForceLoaded();
	}

	/**
	 * set ship group State
	 *
	 * @param state
	 */
	private void setShipGroupStateText( String state )
	{
		wait.waitForElementDisplayed(SHIP_GROUP_STATE_TEXT_FIELD);
		text.enterText(SHIP_GROUP_STATE_TEXT_FIELD, state);
	}

	/**
	 * set ship group City
	 *
	 * @param city
	 */
	private void setShipGroupCity( String city )
	{
		wait.waitForElementDisplayed(SHIP_GROUP_CITY_FIELD);
		text.enterText(SHIP_GROUP_CITY_FIELD, city);
	}

	/**
	 * set ship group Postal Code
	 *
	 * @param postalCode
	 */
	private void setShipGroupPostalCode( String postalCode )
	{
		wait.waitForElementDisplayed(SHIP_GROUP_POSTAL_CODE_FIELD);
		text.enterText(SHIP_GROUP_POSTAL_CODE_FIELD, postalCode);
	}

	/**
	 * set ship group Phone
	 *
	 * @param phone
	 */
	private void setShipGroupPhone( String phone )
	{
		wait.waitForElementDisplayed(SHIP_GROUP_PHONE_FIELD);
		text.enterText(SHIP_GROUP_PHONE_FIELD, phone);
	}

	/**
	 * set ship group Email
	 *
	 * @param email
	 */
	private void setShipGroupEmail( String email )
	{
		wait.waitForElementDisplayed(SHIP_GROUP_EMAIL_FIELD);
		text.enterText(SHIP_GROUP_EMAIL_FIELD, email);
	}

	/**
	 * Set Billing Address info
	 * @param firstName
	 * @param lastName
	 * @param addressLine1
	 * @param country
	 * @param state
	 * @param city
	 * @param postalCode
	 */
	public void enterBillingAddress(String firstName, String lastName, String companyName, String addressLine1, String country, String state, String city, String postalCode)
	{
		clickEnterBillingAddressLink();
		setBillingFirstName(firstName);
		setBillingLastName(lastName);
		setBillingCompanyName(companyName);
		setBillingAddress1(addressLine1);
		setBillingCountry(country);
		if ( element.isElementPresent(BILLING_STATE_FIELD) )
		{
			setBillingState(state);
		}
		else
		{
			setBillingStateText(state);
		}
		setBillingCity(city);
		setBillingPostalCode(postalCode);

		setBillingPhone(CommonDataProperties.PHONE);
		setBillingEmail(CommonDataProperties.EMAIL);
	}

	/**
	 * Set ship group First Name
	 * @param firstName
	 */
	private void setBillingFirstName(String firstName)
	{
		wait.waitForElementDisplayed(BILLING_FIRST_NAME_FIELD);
		text.enterText(BILLING_FIRST_NAME_FIELD, firstName);
	}

	/**
	 * Set ship group Last Name
	 * @param lastName
	 */
	private void setBillingLastName(String lastName)
	{
		wait.waitForElementDisplayed(BILLING_LAST_NAME_FIELD);
		text.enterText(BILLING_LAST_NAME_FIELD, lastName);
	}

	/**
	 * Set ship group Company Name
	 * @param companyName
	 */
	private void setBillingCompanyName(String companyName)
	{
		wait.waitForElementDisplayed(BILLING_COMPANY_NAME_FIELD);
		text.enterText(BILLING_COMPANY_NAME_FIELD, companyName);
	}

	/**
	 * Set ship group address one
	 * @param addressLine1
	 */
	private void setBillingAddress1(String addressLine1)
	{
		wait.waitForElementDisplayed(BILLING_ADDRESS1_FIELD);
		text.enterText(BILLING_ADDRESS1_FIELD, addressLine1);
	}

	/**
	 * Set ship group country
	 * @param country
	 */
	private void setBillingCountry(String country)
	{
		wait.waitForElementDisplayed(BILLING_COUNTRY_FIELD);
		dropdown.selectDropdownByDisplayName(BILLING_COUNTRY_FIELD, country);
		waitForSalesForceLoaded();
	}

	/**
	 * set ship group State
	 *
	 * @param state
	 */
	private void setBillingState( String state )
	{
		wait.waitForElementDisplayed(BILLING_STATE_FIELD);
		dropdown.selectDropdownByValue(BILLING_STATE_FIELD, state);
		waitForSalesForceLoaded();
	}

	/**
	 * set ship group State
	 *
	 * @param state
	 */
	private void setBillingStateText( String state )
	{
		wait.waitForElementDisplayed(BILLING_STATE_TEXT_FIELD);
		text.enterText(BILLING_STATE_TEXT_FIELD, state);
	}

	/**
	 * set ship group City
	 *
	 * @param city
	 */
	private void setBillingCity( String city )
	{
		wait.waitForElementDisplayed(BILLING_CITY_FIELD);
		text.enterText(BILLING_CITY_FIELD, city);
	}

	/**
	 * set ship group Postal Code
	 *
	 * @param postalCode
	 */
	private void setBillingPostalCode( String postalCode )
	{
		wait.waitForElementDisplayed(BILLING_POSTAL_CODE_FIELD);
		text.enterText(BILLING_POSTAL_CODE_FIELD, postalCode);
	}

	/**
	 * set ship group Phone
	 *
	 * @param phone
	 */
	private void setBillingPhone( String phone )
	{
		wait.waitForElementDisplayed(BILLING_PHONE_FIELD);
		text.enterText(BILLING_PHONE_FIELD, phone);
	}

	/**
	 * set ship group Email
	 *
	 * @param email
	 */
	private void setBillingEmail( String email )
	{
		wait.waitForElementDisplayed(BILLING_EMAIL_FIELD);
		text.enterText(BILLING_EMAIL_FIELD, email);
	}

	/**
	 * Click Enter Billing Address Link
	 */
	public void clickEnterBillingAddressLink()
	{
		wait.waitForElementDisplayed(ENTER_BILLING_ADDRESS_LINK);
		click.clickElement(ENTER_BILLING_ADDRESS_LINK);
		waitForPageLoad();
	}
	/**
	 * Click the Use Billing address link
	 */
	public void selectUseBillingAddress( )
	{
		wait.waitForElementDisplayed(COPY_BILLING_ADDRESS);
		click.clickElement(COPY_BILLING_ADDRESS);
	}

	/**
	 * Set Coupon Code
	 *
	 * @param couponCode
	 */
	public void setCouponCode( String couponCode )
	{
		wait.waitForElementDisplayed(COUPON_CODE_FIELD);
		text.enterText(COUPON_CODE_FIELD, couponCode + Keys.ENTER);
		waitForPageLoad();
		// uses explicit wait to ensure enough time for coupon to be applied to cart
		try{
			wait.wait(10000);
		}
		catch (Exception e) {}
		wait.waitForElementPresent(BUTTON_REMOVE_COUPON);
		waitForSalesForceLoaded();
		waitForPageLoad();
	}

	/**
	 * click Remove Coupon button
	 */
	public void clickRemoveCouponButton( )
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		if(element.isElementPresent(BUTTON_REMOVE_COUPON))
		{
			wait.waitForElementDisplayed(BUTTON_REMOVE_COUPON);
			click.clickElement(BUTTON_REMOVE_COUPON);
		}
		waitForPageLoad();
	}

	/**
	 * click Continue button
	 */
	public void clickContinueButton( )
	{
		wait.waitForElementDisplayed(BUTTON_CONTINUE);
		click.clickElement(BUTTON_CONTINUE);
		waitForPageLoad();
	}

	public void acceptTerms( )
	{
		wait.waitForElementDisplayed(SHIP_COMPLETE);
		click.clickElement(SHIP_COMPLETE);

		wait.waitForElementDisplayed(ACCEPT_TERMS);
		click.clickElement(ACCEPT_TERMS);

		wait.waitForElementDisplayed(ACCEPT_AND_PROCEED);
		wait.waitForElementEnabled(ACCEPT_AND_PROCEED);
		click.clickElement(ACCEPT_AND_PROCEED);
		waitForPageLoad();
	}

	public void setPurchaseOrder( String value )
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(PURCHASE_ORDER);
		text.enterText(PURCHASE_ORDER, value);
	}

	/**
	 * Click Edit Address link for matching Shipping Group
	 *
	 * @param shippingGroupNum
	 * @param shippingGroupName
	 */
	public void editShipGroupAddress( String shippingGroupNum, String shippingGroupName)
	{
		String path = String.format(".//div[contains(@class, 'shippingGroupPanel')]//a/span[text()='Shipping Group']/following-sibling::span[contains(text(), '%s of')]/following-sibling::span[text() = '%s']/../../../following-sibling::div//a[contains(@class, 'editAddr')]",
				shippingGroupNum, shippingGroupName);
		By editAddressLink = By.xpath(path);
		wait.waitForElementDisplayed(editAddressLink);
		click.clickElement(editAddressLink);
	}

	/**
	 * Click Bulk Move Items button on shipping group input page
	 */
	public void clickShipGroupMoveItemsButton()
	{
		wait.waitForElementDisplayed(SHIP_GROUP_MOVE_ITEMS_BUTTON);
		click.clickElement(SHIP_GROUP_MOVE_ITEMS_BUTTON);
	}

	/**
	 * Click Move Items button on shipping group input page
	 */
	public void clickShipGroupMoveItemButton()
	{
		wait.waitForElementDisplayed(SHIP_GROUP_MOVE_ITEM_BUTTON);
		click.clickElement(SHIP_GROUP_MOVE_ITEM_BUTTON);
	}

	/**
	 * Click Save button on shipping group Edit Address modal
	 */
	public void clickShipGroupSaveButton()
	{
		wait.waitForElementDisplayed(SAVE_SHIP_GROUP_BUTTON);
		click.clickElement(SAVE_SHIP_GROUP_BUTTON);
		waitForSalesForceLoaded();
	}

	/**
	 * Click Add Shipping group Button
	 */
	public void clickAddShippingGroupButton()
	{
		wait.waitForElementDisplayed(ADD_SHIP_GROUP_BUTTON);
		click.clickElement(ADD_SHIP_GROUP_BUTTON);
	}

	/**
	 * Click Enter a Shipping Address link
	 */
	public void clickEnterShippingAddressLink()
	{
		wait.waitForElementDisplayed(ENTER_SHIPPING_ADDRESS_LINK);
		click.clickElement(ENTER_SHIPPING_ADDRESS_LINK);
	}

	/**
	 * Select item to move based on product name, moves all matching products to single shipping group
	 * @param productSku
	 */
	private void selectCartItemToMoveAll(String productSku)
	{
		waitForPageLoad();
		waitForSalesForceLoaded();
		String productLocator = String.format(".//tr[contains(@class, 'cart_item')]/td/p[text()='%s']/../../td/input[contains(@class, 'bulkmoveSelect')]",
				productSku);
		By productCheckbox = By.xpath(productLocator);
		wait.waitForElementDisplayed(productCheckbox);
		wait.waitForElementEnabled(productCheckbox);
		checkbox.setCheckbox(productCheckbox, true);
	}

	/**
	 * Select item to move based on product name, specify quantity of item to move
	 * @param productSku
	 * @param shippingGroupNum
	 * @param shippingGroupName
	 */
	private void selectCartItemToMoveSpecificQuantity(String productSku, String shippingGroupNum, String shippingGroupName)
	{

			String path = String.format(".//div[contains(@class, 'shippingGroupPanel')]//a/span[text()='Shipping Group']/following-sibling::span[contains(text(), '%s of')]/following-sibling::span[text() = '%s']/../../../following-sibling::div//a[@data-id ='%s']/../../following-sibling::p[@class='cc_move_item']/a[contains(@class, 'moveItem')]",
					shippingGroupNum, shippingGroupName, productSku);
			By moveItemLink = By.xpath(path);
			wait.waitForElementDisplayed(moveItemLink);
			click.clickElement(moveItemLink);
	}

	/**
	 * Set accept terms checkbox
	 */
	public void setAcceptTermsCheckbox()
	{
		wait.waitForElementDisplayed(LLI_ACCEPT_TERMS_CHECKBOX);
		checkbox.setCheckbox(LLI_ACCEPT_TERMS_CHECKBOX, true);
	}

	/**
	 * Set shipping group drowpdown
	 * @param shippingGroup
	 */
	private void setShippingGroupDropdown(String shippingGroup)
	{
		wait.waitForElementDisplayed(SHIP_GROUP_DROPDOWN);
		dropdown.selectDropdownByDisplayName(SHIP_GROUP_DROPDOWN, shippingGroup);
	}

	/**
	 * Moves cart item to new shipping group
	 * @param productSku
	 * @param shippingGroup
	 */
	public void moveCartItem(String productSku, String shippingGroup)
	{
		selectCartItemToMoveAll(productSku);
		clickMoveItemsButton();
		setShippingGroupDropdown(shippingGroup);
	}

	/**
	 * Moves quantity of cart item to new shipping group
	 * @param productSku
	 * @param quantity
	 * @param shippingGroup
	 */
	public void moveCartItemSpecificQuantity(String productSku, String shippingGroupNum, String shippingGroupName, int quantity, String shippingGroup)
	{
		waitForSalesForceLoaded();
		waitForPageLoad();
		selectCartItemToMoveSpecificQuantity(productSku, shippingGroupNum, shippingGroupName);
		setQuantityToMove(quantity);
		setShippingGroupDropdown(shippingGroup);
	}

	/**
	 * Set quantity of cart item to move
	 * @param quantity
	 */
	public void setQuantityToMove(int quantity)
	{
		wait.waitForElementDisplayed(LLI_QUANTITY_TO_MOVE);
		text.clearText(LLI_QUANTITY_TO_MOVE);

		String actualQuantity = wait.waitForElementDisplayed(LLI_QUANTITY_TO_MOVE).getAttribute("value");
		int currQuantity = Integer.parseInt(actualQuantity);
		while (currQuantity < quantity)
		{
			wait.waitForElementDisplayed(LLI_QUANTITY_PLUS_BUTTON);
			click.clickElement(LLI_QUANTITY_PLUS_BUTTON);
			actualQuantity = wait.waitForElementDisplayed(LLI_QUANTITY_TO_MOVE).getAttribute("value");
			currQuantity = Integer.parseInt(actualQuantity);
		}
		waitForSalesForceLoaded();
	}


	/**
	 * click Move Items button on Shipping Details tab
	 */
	public void clickMoveItemsButton()
	{
		wait.waitForElementDisplayed(MOVE_ITEMS_BUTTON);
		click.clickElement(MOVE_ITEMS_BUTTON);
	}

	/**
	 * click Proceed button
	 */
	public void clickProceedButton()
	{
		waitForSalesForceLoaded();
		wait.waitForElementDisplayed(LLI_PROCEED_BUTTON);
		wait.waitForElementEnabled(LLI_PROCEED_BUTTON);
		click.clickElement(LLI_PROCEED_BUTTON);
	}

	/**
	 * click Apply Coupon button
	 */
	public void clickApplyCouponButton()
	{
		wait.waitForElementDisplayed(BUTTON_APPLY_COUPON);
		click.clickElement(BUTTON_APPLY_COUPON);
	}

	/**
	 * click Submit Payment button
	 */
	public void clickSubmitPaymentButton( )
	{
		wait.waitForElementDisplayed(BUTTON_PROCESS_PAYMENT);
		wait.waitForElementEnabled(BUTTON_PROCESS_PAYMENT);
		click.clickElement((BUTTON_PROCESS_PAYMENT));
		waitForPageLoad();
	}

	/**
	 * click user profile
	 */
	public void clickUserProfile( )
	{
		wait.waitForElementDisplayed(PROFILE_IMG);
		wait.waitForElementEnabled(PROFILE_IMG);
		click.javascriptClick((PROFILE_IMG));
	}

	/**
	 * click Logout
	 */
	public void clickLogout( )
	{
		wait.waitForElementDisplayed(LINK_LOGOUT);
		wait.waitForElementEnabled(LINK_LOGOUT);
		click.javascriptClick((LINK_LOGOUT));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * get Estimated Tax field
	 *
	 * @return estimated Tax
	 */
	public String getEstimatedTax( )
	{
		wait.waitForElementDisplayed(ESTIMATED_TAX);
		wait.waitForElementEnabled(ESTIMATED_TAX);
		String sectionText = text.getElementText(ESTIMATED_TAX);
		return sectionText;
	}

	/**
	 * get LLI Estimated Tax field
	 *
	 * @return estimated Tax
	 */
	public String getLLIEstimatedTax( )
	{
		wait.waitForElementDisplayed(LLI_ESTIMATED_TAX_FIELD);
		wait.waitForElementEnabled(LLI_ESTIMATED_TAX_FIELD);
		String sectionText = text.getElementText(LLI_ESTIMATED_TAX_FIELD);
		return sectionText;
	}

	/**
	 * Get Discount Amount (You Save) field
	 *
	 * @param productSku
	 *
	 * @return amount discounted for specified product
	 */
	public String getDiscountAmount( String productSku )
	{
		String selector = String.format(
			".//span[@class='cc_checkout_sku' and text()='%s']/../..//span[contains(text(), 'You Save')]/following-sibling::span",
			productSku);
		By discountAmount = By.xpath(selector);
		wait.waitForElementDisplayed(discountAmount);
		String sectionText = text.getElementText(discountAmount);
		return sectionText;
	}

	/**
	 * Get Shipping Amount field
	 *
	 * @return shipping Amount
	 */
	public String getShippingAmount( )
	{
		wait.waitForElementDisplayed(SHIPPING_AMOUNT);
		wait.waitForElementEnabled(SHIPPING_AMOUNT);
		String shippingAmount = text.getElementText(SHIPPING_AMOUNT);
		return shippingAmount;
	}

	/**
	 * Get tax calculation error message
	 *
	 * @return errorMessage
	 */
	public String getTaxCalcErrorMessage( )
	{
		String errorMessage = null;
		try {
			wait.waitForElementDisplayed(TAX_CALC_ERROR_MESSAGE, DEFAULT_TIMEOUT);
			errorMessage = text.getElementText(TAX_CALC_ERROR_MESSAGE);
		}
		catch(Exception e) {}
		return errorMessage;
	}

	/**
	 * Get tax calculation error message for LLI
	 *
	 * @return errorMessage
	 */
	public String getLLITaxCalcErrorMessage( )
	{
		String errorMessage = null;
		try {
			wait.waitForElementDisplayed(LLI_TAX_CALC_ERROR_MESSAGE, DEFAULT_TIMEOUT);
			errorMessage = text.getElementText(LLI_TAX_CALC_ERROR_MESSAGE);
		}
		catch(Exception e) {}
		return errorMessage;
	}

	/**
	 * click Cancel Checkout link
	 */
	public void cancelCheckout( )
	{
		wait.waitForElementDisplayed(LINK_CANCEL_CHECKOUT);
		wait.waitForElementEnabled(LINK_CANCEL_CHECKOUT);
		click.javascriptClick((LINK_CANCEL_CHECKOUT));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * click Cart link
	 */
	public void navigateToCart( )
	{
		window.switchToWindowTextInTitle("B2B Information Technology Sales");
		wait.waitForElementDisplayed(LINK_CART);
		wait.waitForElementEnabled(LINK_CART);
		click.javascriptClick((LINK_CART));
		waitForPageLoad();
		waitForSalesForceLoaded();
	}

	/**
	 * clear out cart
	 */
	public void clearCart( )
	{
		navigateToCart();

		wait.waitForElementDisplayed(LINK_CLEAR_CART);
		wait.waitForElementEnabled(LINK_CLEAR_CART);
		click.javascriptClick((LINK_CLEAR_CART));
		waitForPageLoad();
		wait.waitForElementDisplayed(BUTTON_CLEAR_CART);
		wait.waitForElementEnabled(BUTTON_CLEAR_CART);
		click.javascriptClick((BUTTON_CLEAR_CART));
		waitForSalesForceLoaded();
	}

	/**
	 * Individually deletes all items in cart
	 */
	public void deleteCartItems( )
	{
		navigateToCart();
		waitForPageLoad();
		// clean up coupon applied to cart
		clickRemoveCouponButton();
		waitForPageLoad();
		try
		{
			while ( element.isElementEnabled(BUTTON_DELETE_ITEM) )
			{
				waitForSalesForceLoaded();
				wait.waitForElementDisplayed(BUTTON_DELETE_ITEM);
				wait.waitForElementEnabled(BUTTON_DELETE_ITEM);
				click.javascriptClick(BUTTON_DELETE_ITEM);
				waitForPageLoad();
				waitForSalesForceLoaded();
			}
		}
		catch ( Exception ex )
		{
		}
	}

	/**
	 * cancel checkout and clear existing cart
	 */
	public void cancelAndClearCheckout( )
	{
		try
		{
			waitForSalesForceLoaded();
			if ( element.isElementDisplayed(LINK_CANCEL_CHECKOUT) )
			{
				cancelCheckout();
				clearCart();
				navigateHome();
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Set Quick Order fields
	 *
	 * @param itemSku
	 * @param quantity
	 */
	public void setQuickOrder( String itemSku, String quantity )
	{
		waitForPageLoad();
		window.switchToWindowTextInTitle("B2B Information Technology Sales");
		setQuickOrderItemSku(itemSku);
		setQuickOrderQuantity(quantity);
		waitForSalesForceLoaded();
	}

	/**
	 * Set Quick Order fields
	 *
	 * @param itemSku
	 * @param quantity
	 * @param lineNumber
	 */
	public void setQuickOrderMultiple( String itemSku, String quantity, int lineNumber )
	{
		waitForPageLoad();
		window.switchToWindow();
		setQuickOrderSkuLine(itemSku, lineNumber);
		setQuickOrderQuantityLine(quantity, lineNumber);
	}

	public void closeStorefront( )
	{
		driver.close();
		window.switchToWindow();
	}

	/**
	 * Set LLI Contact Information
	 * @param firstName
	 * @param lastName
	 * @param phone
	 * @param email
	 */
	public void setLLIContactInfo(String firstName, String lastName, String phone, String email)
	{
		setLLIFirstName(firstName);
		setLLILastName(lastName);
		setLLIPhone(phone);
		setLLIEmail(email);
	}

	/**
	 * Set LLI First Name
	 * @param firstName
	 */
	public void setLLIFirstName(String firstName)
	{
		wait.waitForElementDisplayed(LLI_FIRST_NAME_FIELD);
		text.enterText(LLI_FIRST_NAME_FIELD, firstName);
		waitForSalesForceLoaded();
	}

	/**
	 * Set LLI Last Name
	 * @param lastName
	 */
	public void setLLILastName(String lastName)
	{
		wait.waitForElementDisplayed(LLI_LAST_NAME_FIELD);
		text.enterText(LLI_LAST_NAME_FIELD, lastName);
	}

	/**
	 * Set LLI Phone
	 * @param phone
	 */
	public void setLLIPhone(String phone)
	{
		wait.waitForElementDisplayed(LLI_PHONE_FIELD);
		text.enterText(LLI_PHONE_FIELD, phone);
	}

	/**
	 * Set LLI Email
	 * @param email
	 */
	public void setLLIEmail(String email)
	{
		wait.waitForElementDisplayed(LLI_EMAIL_FIELD);
		text.enterText(LLI_EMAIL_FIELD, email);
	}

	/**
	 * Clean up any remaining shipping groups from previous test runs
	 */
	public void cleanUpShippingGroups()
	{
		while(element.isElementPresent(LLI_DELETE_SHIP_GROUP_LINK))
		{
			wait.waitForElementDisplayed(LLI_DELETE_SHIP_GROUP_LINK);
			click.clickElement(LLI_DELETE_SHIP_GROUP_LINK);
			waitForSalesForceLoaded();
		}
	}
}