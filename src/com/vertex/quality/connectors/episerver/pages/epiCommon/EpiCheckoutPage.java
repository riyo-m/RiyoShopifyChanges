package com.vertex.quality.connectors.episerver.pages.epiCommon;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.pages.VertexPage;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.connectors.episerver.common.enums.CreditCardDetails;
import com.vertex.quality.connectors.episerver.pages.EpiAddAddressPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EpiCheckoutPage extends VertexPage
{
	public EpiCheckoutPage( WebDriver driver )
	{
		super(driver);
	}

	protected By ADD_NEW_ADDRESS_BUTTON = By.cssSelector(
		"[id*='BillingAddress_AddressId']~button[class$='jsNewAddress']");
	protected By ADD_NEW_ADDRESS_DIALOG = By.id("AddressDialogContent");
	protected By ADD_NEW_SHIP_ADDRESS_BUTTON = By.cssSelector("[id*='Shipments']~button[class$='jsNewAddress']");
	protected By ALTERNATE_SHIP_ADDRESS_LINK = By.cssSelector("#AlternativeAddressButton:not([style*='none']");
	protected By SHIPPING_ADDRESS_DROPDOWN = By.cssSelector("select[id^='Shipments'][id$='Address_AddressId']");
	protected By BILLING_ADDRESS_DROPDOWN = By.id("BillingAddress_AddressId");
	protected By CREDIT_CARD_RADIO_BUTTON = By.cssSelector("[type='radio'][value='GenericCreditCard']");
	protected By CASH_ON_DELIVERY_RADIO_BUTTON = By.cssSelector("[type='radio'][value='CashOnDelivery']");
	protected By AUTHORIZE_PAY_BY_CREDIT_CARD_RADIO_BUTTON = By.cssSelector("[type='radio'][value='Authorize']");
	protected By CREDIT_CARD_NAME = By.id("CreditCardName");
	protected By CREDIT_CARD_NUMBER = By.id("CreditCardNumber");
	protected By CREDIT_CARD_SECURITY_CODE = By.id("CreditCardSecurityCode");
	protected By CREDIT_CARD_EXPIRY_YEAR = By.id("ExpirationYear");
	protected By CREDIT_CARD_EXPIRY_MONTH = By.id("ExpirationMonth");
	protected By SUB_TOTAL_FOR_ITEMS = By.xpath(
		"//tr[td[*[text()='Sub Total For Your Items']]]//*[contains(@class, 'total-price')]/h4");
	protected By SUB_TOTAL = By.xpath("//tr[td[*[text()='Sub Total For Your Items']]]/td[@class='total-price']/h4");
	protected By ADDITIONAL_ORDER_LEVEL_DISCOUNT = By.xpath(
		"//tr[td[*[contains(text(), 'Additional Order Level Discounts')]]]//*[contains(@class, 'total-price')]/h4");
	protected By SHIPPING_AND_TAX = By.xpath(
		"//tr[td[*[text()='Shipping & Tax']]]//*[contains(@class, 'total-price')]/h4");
	protected By SHIPPING_AND_TAX_VALUES_BLOCK = By.xpath(
		"//tr[td[*[text()='Shipping & Tax']]]/td[contains(@class, 'total-price')]");
	protected By SHIPPING_AND_TAX_HEADINGS_BLOCK = By.xpath("//td[*[text()='Shipping & Tax']]");
	protected By PLACE_ORDER_BUTTON = By.xpath("//button[@type='submit' and text()='Place order']");
	protected By ERROR_MSG = By.cssSelector("[class*='alert']:not([style*='none'])");
	protected By ORDER_NUMBER = By.xpath("//*[starts-with(text(), 'Order ID')]");
	protected By TOTAL_FOR_CART = By.xpath(
		"//tr[td[*[text()='Total for cart']]]//*[contains(@class, 'total-price')]/h4");
	protected By SHIP_MULTI_ADDRESS_1 = By.id("CartItems_0__AddressId");
	protected By SHIP_MULTI_ADDRESS_2 = By.id("CartItems_1__AddressId");
	protected By SHIP_MULTI_ADDRESS_3 = By.id("CartItems_2__AddressId");
	protected String ALL_SAVED_BILLING_ADDRESS = ".//select[@id='BillingAddress_AddressId']//option";
	protected String ALL_SAVED_SHIPPING_ADDRESS = ".//select[@name='Shipments[0].Address.AddressId']//option";
	protected By ADD_ALTERNATIVE_ADDRESS_LINK = By.id("AlternativeAddressButton");
	protected By SUB_TOTAL_FOR_YOUR_ITEM = By.xpath("(.//h4[@class='pull-right'])[1]");
	protected By SHIPPING_TOTAL = By.xpath("(.//td[@class='total-price text-right'])[2]/span[3]");
	protected By TAX = By.xpath("(.//td[@class='total-price text-right'])[2]/span[4]");
	protected By COUPON_BOX = By.id("inputCouponCode");
	protected By APPLY_COUPON_BUTTON = By.xpath(".//button[normalize-space(.)='Apply']");
	protected String APPLIED_COUPON = ".//h5[text()='Coupons have been applied:']//following-sibling::ul//label[normalize-space(.)='<<text_replace>>']";
	protected String REMOVE_APPLIED_COUPON = ".//h5[text()='Coupons have been applied:']//following-sibling::ul//label[normalize-space(.)='<<text_replace>>']/following-sibling::a";

	/**
	 * This method is used to Click on Add New Billing Address Button from Checkout
	 * page
	 */
	public EpiAddAddressPage clickAddNewAddressButton( )
	{
		EpiAddAddressPage addaddresspage = new EpiAddAddressPage(driver);
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_BUTTON);
		click.clickElement(ADD_NEW_ADDRESS_BUTTON);
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_DIALOG);
		return addaddresspage;
	}

	/**
	 * This method is used to Click on Add New Shipping Address Button from Checkout
	 * page
	 */
	public EpiAddAddressPage clickAddNewShipAddressButton( )
	{
		EpiAddAddressPage addaddresspage = new EpiAddAddressPage(driver);
		wait.waitForElementDisplayed(ADD_NEW_SHIP_ADDRESS_BUTTON);
		click.clickElement(ADD_NEW_SHIP_ADDRESS_BUTTON);
		wait.waitForElementDisplayed(ADD_NEW_ADDRESS_DIALOG);
		return addaddresspage;
	}

	/**
	 * This method is used to Select Shipping Address from Checkout page
	 *
	 * @return selected Shipping Address
	 */
	public String selectShippingAddress( String shippingAddressName )
	{
		waitForPageLoad();
		if ( element.isElementDisplayed(ALTERNATE_SHIP_ADDRESS_LINK) )
		{
			click.clickElement(ALTERNATE_SHIP_ADDRESS_LINK);
			waitForPageLoad();
		}
		wait.waitForElementDisplayed(SHIPPING_ADDRESS_DROPDOWN);
		WebElement shippingAdress = element.getWebElement(SHIPPING_ADDRESS_DROPDOWN);
		scroll.scrollElementIntoView(shippingAdress);
		dropdown.selectDropdownByValue(SHIPPING_ADDRESS_DROPDOWN, shippingAddressName);
		WebElement selectedShippingAddress = dropdown.getDropdownSelectedOption(SHIPPING_ADDRESS_DROPDOWN);
		return selectedShippingAddress.getText();
	}

	/**
	 * This method is used to Select Billing Address from Checkout page
	 *
	 * @return selected Billing Address
	 */
	public void selectBillingAddress( String billing_address )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BILLING_ADDRESS_DROPDOWN);
		WebElement billingAddressDropdown = element.getWebElement(BILLING_ADDRESS_DROPDOWN);
		scroll.scrollElementIntoView(billingAddressDropdown);
		dropdown.selectDropdownByValue(BILLING_ADDRESS_DROPDOWN, billing_address);
		waitForPageLoad();
	}

	/**
	 * This method is used to get the selected Billing Address from Checkout page
	 *
	 * @return selected Billing Address
	 */
	public String getBillingAddressSelected( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(BILLING_ADDRESS_DROPDOWN);
		WebElement billingAddressDropdown = element.getWebElement(BILLING_ADDRESS_DROPDOWN);
		scroll.scrollElementIntoView(billingAddressDropdown);
		WebElement selected_address = dropdown.getDropdownSelectedOption(BILLING_ADDRESS_DROPDOWN);
		return selected_address.getText();
	}

	/**
	 * This method is used to get the selected Shipping Address from Checkout page
	 *
	 * @return selected Shipping Address
	 */
	public String getShippingAddressSelected( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SHIPPING_ADDRESS_DROPDOWN);
		WebElement selected_address = dropdown.getDropdownSelectedOption(SHIPPING_ADDRESS_DROPDOWN);
		return selected_address.getText();
	}

	/**
	 * This method is used to validate the seletected Billing Address on Checkout
	 * page
	 */
	public void validateBillingAddressSelected( String expectedAddress )
	{
		String selectedAddress = getBillingAddressSelected();
		if ( selectedAddress.equals(null) )
		{
			VertexLogger.log("No value selected for the Billing address from the dropdown", VertexLogLevel.ERROR);
		}
		else if ( selectedAddress.trim() == expectedAddress.trim() )
		{
			VertexLogger.log(String.format("Both expected and selected address are same (i.e. %s)", expectedAddress));
		}

		else
		{
			VertexLogger.log(
				String.format("Expected Billing Address: %s Actual Selected Billing Address: %s are not same",
					expectedAddress, selectedAddress));
		}
	}

	/**
	 * This method is used to choose payment method type
	 *
	 * @param paymentOption option for payment of order
	 */
	public void choosePaymentMethod( String paymentOption )
	{
		waitForPageLoad();
		if ( paymentOption.equalsIgnoreCase("Credit card") )
		{
			click.clickElement(CREDIT_CARD_RADIO_BUTTON);
		}
		else if ( paymentOption.equalsIgnoreCase("Cash on delivery") )
		{
			click.clickElement(CASH_ON_DELIVERY_RADIO_BUTTON);
		}
		else if ( paymentOption.equalsIgnoreCase("Authorize - Pay By Credit Card") )
		{
			click.clickElement(AUTHORIZE_PAY_BY_CREDIT_CARD_RADIO_BUTTON);
		}
		else
		{
			VertexLogger.log(String.format(
				"Provided Payment method: %s is not valid, Please provide a valid method to select(i.e. Credit card/Cash on delivery/Authorize - Pay By Credit Card)",
				paymentOption), VertexLogLevel.ERROR);
		}
		VertexLogger.log(String.format("Payment method: <b><i></b></i> selected successfully", paymentOption));
		waitForPageLoad();
	}

	/**
	 * This method is used to enter Credit card full name from payment section
	 */
	public void setNameOnCard( String name )
	{
		wait.waitForElementDisplayed(CREDIT_CARD_NAME);
		text.enterText(CREDIT_CARD_NAME, name);
	}

	/**
	 * This method is used to enter Credit card number from payment section
	 */
	public void setCardNumber( String cardNumber )
	{
		wait.waitForElementDisplayed(CREDIT_CARD_NUMBER);
		text.enterText(CREDIT_CARD_NUMBER, cardNumber);
	}

	/**
	 * This method is used to enter Credit card security code from payment section
	 */
	public void setSecurityCode( String securityCode )
	{
		wait.waitForElementDisplayed(CREDIT_CARD_SECURITY_CODE);
		text.enterText(CREDIT_CARD_SECURITY_CODE, securityCode);
	}

	/**
	 * This method is used to select Credit card expiration year from payment
	 * section
	 */
	public void selectExpirationYear( String year )
	{
		wait.waitForElementDisplayed(CREDIT_CARD_EXPIRY_YEAR);
		dropdown.selectDropdownByDisplayName(CREDIT_CARD_EXPIRY_YEAR, year);
	}

	/**
	 * This method is used to select Credit card expiration month from payment
	 * section
	 */
	public void selectExpirationMonth( String month )
	{
		wait.waitForElementDisplayed(CREDIT_CARD_EXPIRY_MONTH);
		dropdown.selectDropdownByDisplayName(CREDIT_CARD_EXPIRY_MONTH, month);
	}

	/**
	 * This method is used to enter Credit Card Details in payment section
	 */
	public void selectCreditCardAndEnterDetails( )
	{
		String NAME = CreditCardDetails.NAME.text;
		String CARD_NUMBER = CreditCardDetails.CARD_NUMBER.text;
		String SECURITY_CODE = CreditCardDetails.SECURITY_CODE.text;
		String YEAR = CreditCardDetails.YEAR.text;
		String MONTH = CreditCardDetails.MONTH.text;
		this.setNameOnCard(NAME);
		this.setCardNumber(CARD_NUMBER);
		this.setSecurityCode(SECURITY_CODE);
		this.selectExpirationYear(YEAR);
		this.selectExpirationMonth(MONTH);
	}

	public String getProductXpath( String productName )
	{
		String PRODUCT_XPATH = null;
		if ( productName.contains("\'") )
		{
			String[] productNameArray = productName.split("\'");
			int count = productNameArray.length;
			String txt = "";
			for ( int i = 0 ; i < count ; i++ )
			{
				txt += String.format("contains(text(), '%s')", productNameArray[i]);
				if ( i < count - 1 )
				{
					txt += " and ";
				}
			}
			PRODUCT_XPATH = String.format("//a[%s]/ancestor::*[@class='row']", txt);
			VertexLogger.log(String.format("PRODUCT_XPATH: %s", PRODUCT_XPATH));
		}
		else if ( productName.equals("Windbreaker Jacket") || productName.equals("Winbreaker Jacket") )
		{
			PRODUCT_XPATH = "//a[text()='Windbreaker Jacket' or text()='Winbreaker Jacket']/ancestor::*[@class='row']";
		}
		else if ( element.isElementDisplayed(
			By.xpath(String.format("//a[contains(text(), '%s')]/ancestor::*[@class='row']", productName))) )
		{
			String[] product_name_list = productName.split(" ");
			int count = product_name_list.length;
			String txt = "";

			for ( int i = 0 ; i < count ; i++ )
			{
				txt += String.format("contains(text(), '%s')", product_name_list[i]);
				if ( i < count - 1 )
				{
					txt += " and ";
				}
				PRODUCT_XPATH = String.format("//a[%s]/ancestor::*[@class='row']", txt);
			}
		}
		else
		{
			PRODUCT_XPATH = String.format("//a[contains(text(), '%s')]/ancestor::*[@class='row']", productName);
		}
		return PRODUCT_XPATH;
	}

	/**
	 * This method is used to choose Delivery Method
	 */
	public void chooseDeliveryMethodAs( String deliveryOption, String productName )
	{
		By DELIVERY_METHOD_RADIO_BUTTON = By.xpath("//*[*[@class='jsChangeShipment']]");
		if ( productName != null )
		{
			DELIVERY_METHOD_RADIO_BUTTON = By.xpath(getProductXpath(productName) +
													"/ancestor::*[@class='row shipment-summary-container']//*[*[@class='jsChangeShipment']]");
		}
		List<WebElement> allDeliveryMethodElimentsList = element.getWebElements(DELIVERY_METHOD_RADIO_BUTTON);
		boolean flag = false;
		String methodName = "";
		for ( WebElement deliveryMethodElement : allDeliveryMethodElimentsList )
		{
			methodName = deliveryMethodElement.getText();
			if ( methodName != null )
			{
				methodName = methodName
					.trim()
					.replace("\n", "");
				if ( methodName.contains(deliveryOption) )
				{
					scroll.scrollElementIntoView(deliveryMethodElement);
					deliveryMethodElement.click();
					waitForPageLoad();
					flag = true;
					break;
				}
			}
		}
		waitForPageLoad();
		if ( !flag )
		{
			VertexLogger.log(String.format(
				"Provided Delivery method: %s is not valid Please provide a valid method to select (i.e. Express/Fast/Regular)",
				deliveryOption), VertexLogLevel.ERROR);
		}
		else
		{
			VertexLogger.log(String.format("Delivery method: %s selected successfully", methodName));
		}
	}

	/**
	 * This method is used to get Amount from Checkout page
	 *
	 * @return Amount
	 */
	public Double getAmount( WebElement amountElement )
	{
		wait.waitForElementDisplayed(amountElement);
		String amount = amountElement.getText();
		double amount1 = Double.parseDouble(amount
			.replace("$", "")
			.trim()
			.replace(" ", ""));
		return amount1;
	}

	/**
	 * This method is used to get SubTotalAmount
	 *
	 * @return SubTotalAmount
	 */
	public Double getSubTotalAmount( )
	{
		WebElement SubTotal = element.getWebElement(SUB_TOTAL);
		scroll.scrollElementIntoView(SubTotal);
		Double actualValue = this.getAmount(SubTotal);
		return actualValue;
	}

	/**
	 * This method is used to get SubTotalForItems
	 *
	 * @return SubTotalForItems
	 */
	public Double getSubTotalForYourItems( )
	{
		WebElement SubTotalForYourItems = element.getWebElement(SUB_TOTAL_FOR_ITEMS);
		Double actualSubTotalForYourItems = getAmount(SubTotalForYourItems);
		VertexLogger.log(
			String.format(("Subtotal for your items excluding Dollor symbol :%s"), actualSubTotalForYourItems));
		return actualSubTotalForYourItems;
	}

	/**
	 * This method is used to get AdditionalDiscountAmount
	 *
	 * @return AdditionalDiscountAmount
	 */
	public Double getOrderLevelAdditionalDiscountAmount( )
	{
		WebElement additionalOrderLevelDiscount = element.getWebElement(ADDITIONAL_ORDER_LEVEL_DISCOUNT);
		Double actualValue = getAmount(additionalOrderLevelDiscount);
		return actualValue;
	}

	/**
	 * This method is used to get ShippingAndTaxAmount
	 *
	 * @return ShippingAndTaxAmount
	 */
	public Double getShippingAndTaxAmount( )
	{
		WebElement shippingAndTax = element.getWebElement(SHIPPING_AND_TAX);
		Double actualShippingAndTaxAmount = getAmount(shippingAndTax);
		return actualShippingAndTaxAmount;
	}

	public String[] getShippingAndTaxAmountAsBlock( )
	{
		wait.waitForElementDisplayed(SHIPPING_AND_TAX_VALUES_BLOCK);
		String shippingAndTaxAmount = attribute.getElementAttribute(SHIPPING_AND_TAX_VALUES_BLOCK, "textContent");
		shippingAndTaxAmount = shippingAndTaxAmount.trim();
		String[] amountsList = shippingAndTaxAmount.split("\n");
		return amountsList;
	}

	public String[] getShippingAndTaxHeadingsAsBlock( )
	{
		wait.waitForElementDisplayed(SHIPPING_AND_TAX_VALUES_BLOCK);
		String shippingAndTaxAmount = attribute.getElementAttribute(SHIPPING_AND_TAX_HEADINGS_BLOCK, "textContent");
		shippingAndTaxAmount = shippingAndTaxAmount.trim();
		String[] headingsList = shippingAndTaxAmount.split("\n");
		return headingsList;
	}

	public Map<String, Double> getShippingAndTaxMapDouble( )
	{
		String[] amounts = getShippingAndTaxAmountAsBlock();
		String[] headings = getShippingAndTaxHeadingsAsBlock();
		Map<String, Double> shippingAndTaxMap = IntStream
			.range(0, headings.length)
			.boxed()
			.collect(Collectors.toMap(i -> headings[i].trim(), i -> Double.parseDouble(amounts[i]
				.trim()
				.replace("$", "")
				.trim()
				.replace(" ", ""))));
		return shippingAndTaxMap;
	}

	public Map<String, String> getShippingAndTaxMap( )
	{
		String[] amounts = getShippingAndTaxAmountAsBlock();
		String[] headings = getShippingAndTaxHeadingsAsBlock();
		Map<String, String> shippingAndTaxMap = IntStream
			.range(0, headings.length)
			.boxed()
			.collect(Collectors.toMap(i -> headings[i].trim(), i -> amounts[i]
				.trim()
				.replace("$", "")
				.trim()
				.replace(" ", "")));
		return shippingAndTaxMap;
	}

	/**
	 * This method is used to get TotalForCart
	 *
	 * @return TotalForCart
	 */
	public Double getTotalForCart( )
	{
		WebElement TotalForCart = element.getWebElement(TOTAL_FOR_CART);
		Double actualTotalForCart = getAmount(TotalForCart);
		return actualTotalForCart;
	}

	/**
	 * This method is used to verify Error message while clicking on
	 * PlaceOrderButton
	 */
	public void VerifyErrorMsgByClickOnPlaceOrderButton( )
	{
		wait.waitForElementDisplayed(PLACE_ORDER_BUTTON);
		click.clickElement(PLACE_ORDER_BUTTON);
		waitForPageLoad();
		wait.waitForElementDisplayed(ERROR_MSG);
		if ( element.isElementDisplayed(ERROR_MSG) )
		{
			String error_msg = attribute.getElementAttribute(ERROR_MSG, "text");
			if ( error_msg.equals("Shipping address is required") )
			{
				VertexLogger.log(String.format("Shipping Address is Mandatory"));
			}
			else
			{
				VertexLogger.log(
					String.format("Expected Error message is not displayed. Displayed Error message is: %S", error_msg),
					VertexLogLevel.ERROR);
			}
		}
		else
		{
			VertexLogger.log(String.format("No Error Message displayed"), VertexLogLevel.ERROR);
		}
	}

	/**
	 * This method is used to Click on PlaceOrder Button
	 */
	public EpiOrderConfirmationPage clickPlaceOrderButton( )
	{
		EpiOrderConfirmationPage orderconfirmpage = new EpiOrderConfirmationPage(driver);
		wait.waitForElementDisplayed(PLACE_ORDER_BUTTON);
		click.clickElement(PLACE_ORDER_BUTTON);
		wait.waitForElementDisplayed(ORDER_NUMBER);
		return orderconfirmpage;
	}

	public void selectShipMultiAddress1( String ShippingAddressName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SHIP_MULTI_ADDRESS_1);
		WebElement billingAddressDropdown = element.getWebElement(SHIP_MULTI_ADDRESS_1);
		scroll.scrollElementIntoView(billingAddressDropdown);
		dropdown.selectDropdownByValue(SHIP_MULTI_ADDRESS_1, ShippingAddressName);
		waitForPageLoad();
	}

	public void selectShipMultiAddress2( String ShippingAddressName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SHIP_MULTI_ADDRESS_2);
		WebElement billingAddressDropdown = element.getWebElement(SHIP_MULTI_ADDRESS_2);
		scroll.scrollElementIntoView(billingAddressDropdown);
		dropdown.selectDropdownByValue(SHIP_MULTI_ADDRESS_2, ShippingAddressName);
		waitForPageLoad();
	}

	public void selectShipMultiAddress3( String ShippingAddressName )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SHIP_MULTI_ADDRESS_3);
		WebElement billingAddressDropdown = element.getWebElement(SHIP_MULTI_ADDRESS_3);
		scroll.scrollElementIntoView(billingAddressDropdown);
		dropdown.selectDropdownByValue(SHIP_MULTI_ADDRESS_3, ShippingAddressName);
		waitForPageLoad();
	}

	/**
	 * Checks already existing addresses by address name
	 *
	 * @param addressName saved address' name
	 * @return true or false based on condition
	 */
	public boolean verifyExistingBillingAddress(String addressName) {
		boolean isExists = false;
		waitForPageLoad();
		wait.waitForElementPresent(BILLING_ADDRESS_DROPDOWN);
		int size = element.getWebElements(By.xpath(ALL_SAVED_BILLING_ADDRESS)).size();
		for (int i = 1; i <= size; i++) {
			String nameOfAddress = text.getElementText(By.xpath(ALL_SAVED_BILLING_ADDRESS + "[" + i + "]"));
			if (addressName.equalsIgnoreCase(nameOfAddress)) {
				isExists = true;
				VertexLogger.log("Iterating saved Billing Address option: " + nameOfAddress  + " that is matched with: " + addressName);
				break;
			}
			else {
				VertexLogger.log("Iterating saved Billing Address option: " + nameOfAddress  + " that is not matched with: " + addressName);
			}
		}
		return isExists;
	}

	/**
	 * Checks already existing addresses by address name
	 *
	 * @param addressName saved address' name
	 * @return true or false based on condition
	 */
	public boolean verifyExistingShippingAddress(String addressName) {
		boolean isExists = false;
		waitForPageLoad();
		wait.waitForElementPresent(SHIPPING_ADDRESS_DROPDOWN);
		int size = element.getWebElements(By.xpath(ALL_SAVED_SHIPPING_ADDRESS)).size();
		for (int i = 1; i <= size; i++) {
			String nameOfAddress = text.getElementText(By.xpath(ALL_SAVED_SHIPPING_ADDRESS + "[" + i + "]"));
			if (addressName.equalsIgnoreCase(nameOfAddress)) {
				isExists = true;
				VertexLogger.log("Iterating saved Shipping Address option: " + nameOfAddress  + " that is matched with: " + addressName);
				break;
			}
			else {
				VertexLogger.log("Iterating saved Shipping Address option: " + nameOfAddress  + " that is not matched with: " + addressName);
			}
		}
		return isExists;
	}

	/**
	 * Selects address by address name if it's already exists
	 *
	 * @param addressName address name to be passed
	 */
	public void selectExistingBillingAddressByName(String addressName) {
		waitForPageLoad();
		dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(BILLING_ADDRESS_DROPDOWN), addressName);
		VertexLogger.log("Selected already existed billing address: " + addressName);
	}

	/**
	 * Selects address by address name if it's already exists
	 *
	 * @param addressName address name to be passed
	 */
	public void selectExistingShippingAddressByName(String addressName) {
		waitForPageLoad();
		dropdown.selectDropdownByDisplayName(wait.waitForElementPresent(SHIPPING_ADDRESS_DROPDOWN), addressName);
		waitForPageLoad();
		VertexLogger.log("Selected already existed shipping address: " + addressName);
	}

	/**
	 * Clicks on Add Alternate Address
	 */
	public void clickOnAddAlternateAddress() {
		waitForPageLoad();
		click.javascriptClick(wait.waitForElementPresent(ADD_ALTERNATIVE_ADDRESS_LINK));
		waitForPageLoad();
	}

	/**
	 * Calculates percentage based tax amount or calculates expected tax amount which should be applicable to the order
	 *
	 * @param taxPercent 	 need to pass percentage of percentage based tax
	 * @param isShipIncluded pass true to calculate tax on Item Amount + FREIGHT & false to ignore FREIGHT amount
	 * @return calculated tax amount based on percentage
	 */
	public double calculatePercentageBasedTaxBeforeOrderPlaced(double taxPercent, boolean isShipIncluded) {
		VertexLogger.log("Tax to be calculated on " + taxPercent + "%");
		double calculatedTax = 0;
		double itemTotal = 0;
		double shippingTotal = 0;
		waitForPageLoad();
		wait.waitForElementPresent(PLACE_ORDER_BUTTON);
		WebElement shippingItemTotal = wait.waitForElementPresent(SUB_TOTAL_FOR_YOUR_ITEM);
		WebElement shippingCost = wait.waitForElementPresent(SHIPPING_TOTAL);
		itemTotal = Double.parseDouble(text.getElementText(shippingItemTotal).replace("$", ""));
		VertexLogger.log("Item's total before placing an order = " + itemTotal);
		if (isShipIncluded) {
			shippingTotal = Double.parseDouble(text.getElementText(shippingCost).replace("$", ""));
			VertexLogger.log("Shipping amount to be added in Tax Calculation formula & Shipping cost " + shippingTotal);
		}
		calculatedTax = (itemTotal + shippingTotal) * (taxPercent / 100);
		VertexLogger.log("Calculated or expected tax amount before placing an order: " + calculatedTax);
		return Double.parseDouble(String.format("%.2f", calculatedTax));
	}

	/**
	 * Calculates percentage based tax amount or calculates expected tax amount which should be applicable to the order
	 *
	 * @param taxPercent need to pass percentage of percentage based tax
	 * @return calculated tax amount based on percentage
	 */
	public double calculatePercentageBasedTaxBeforeOrderPlaced(double taxPercent) {
		return calculatePercentageBasedTaxBeforeOrderPlaced(taxPercent, true);
	}

    /**
     * Take actual tax app;ied on the order from the UI/WebElement.
     *
     * @return tax applied to order
     */
    public double getTaxFromUIBeforeOrderPlaced() {
        waitForPageLoad();
        wait.waitForElementPresent(PLACE_ORDER_BUTTON);
        WebElement taxCost = wait.waitForElementPresent(TAX);
        String actualTax = text.getElementText(taxCost).replace("$", "");
        VertexLogger.log("Tax amount from UI or Actual Tax before placing an order: " + actualTax);
        return Double.parseDouble(actualTax);
    }

	/**
	 * Apply single or multiple coupons
	 *
	 * @param coupons name of coupon(s)
	 */
	public void applyCoupon(String... coupons) {
		waitForPageLoad();
		for (String coupon : coupons) {
			text.enterText(wait.waitForElementPresent(COUPON_BOX), coupon);
			click.moveToElementAndClick(wait.waitForElementPresent(APPLY_COUPON_BUTTON));
			waitForPageLoad();
			VertexLogger.log(coupon + " coupon applied");
		}
	}

	/**
	 * Validate applied coupon
	 *
	 * @param coupon coupon name
	 * @return true or false based on condition
	 */
	public boolean verifyAppliedCoupon(String coupon) {
		waitForPageLoad();
		return element.isElementPresent(By.xpath(APPLIED_COUPON.replace("<<text_replace>>", coupon)));
	}

	/**
	 * Removes single or multiple coupons that are applied!
	 *
	 * @param coupons name of coupon(s)
	 */
	public void removeCoupon(String... coupons) {
		waitForPageLoad();
		for (String coupon : coupons) {
			click.javascriptClick(wait.waitForElementPresent(By.xpath(REMOVE_APPLIED_COUPON.replace("<<text_replace>>", coupon))));
			waitForPageLoad();
			VertexLogger.log(coupon + " coupon removed");
		}
	}
}
