package com.vertex.quality.connectors.hybris.pages.electronics;

import com.vertex.quality.common.enums.VertexLogLevel;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.misc.VertexCurrencyUtils;
import com.vertex.quality.connectors.hybris.pages.HybrisBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents the Electronics Store Checkout page
 * It includes operations on "Shipping Address, Shipping Method, Payment and Billing Address
 * Sections"
 * All above sections are included in single page since 70% of the "functionalities and locators"
 * are same from each section
 *
 * @author Nagaraju Gampa
 */
public class HybrisElectronicsStoreCheckOutPage extends HybrisBasePage
{
	public HybrisElectronicsStoreCheckOutPage( WebDriver driver )
	{
		super(driver);
	}

	protected By SHIPPING_COUNTRY_DROPDOWN = By.id("address.country");
	protected By SHIPPING_TITLE_DROPDOWN = By.id("address.title");
	protected By SHIPPING_FIRST_NAME = By.id("address.firstName");
	protected By SHIPPING_LAST_NAME = By.id("address.surname");
	protected By SHIPPING_ADDRESS_LINE1 = By.id("address.line1");
	protected By SHIPPING_CITY = By.id("address.townCity");
	protected By SHIPPING_STATE_DROPDOWN = By.id("address.region");
	protected By SHIPPING_POST_CODE = By.id("address.postcode");
	protected By SHIPPING_PHONE = By.id("address.phone");
	protected By SHIPPINGNEXT_BUTTON = By.id("addressSubmit");
	protected By ORDER_SUMMERY_HEADING = By.className("checkout-summary-headline");
	protected By SHIPPING_METHOD = By.id("delivery_method");
	protected By DELIVERY_METHOD_NEXT = By.id("deliveryMethodSubmit");
	protected By CARD_TYPE = By.id("card_cardType");
	protected By CARDNAME = By.id("card_nameOnCard");
	protected By CARD_NUMBER = By.id("card_accountNumber");
	protected By CARD_EXPIRY_MONTH = By.id("ExpiryMonth");
	protected By CARD_EXPIRY_YEAR = By.id("ExpiryYear");
	protected By CARD_VERIFICATION_NUMBER = By.id("card_cvNumber");
	protected By USEDELIVERYADDRESS_CHECKBOX = By.id("useDeliveryAddress");
	protected By PAYMENT_METHOD_NEXT = By.cssSelector("[class$='checkout-next']");
	protected By ORDER_SUMMERY_TAX = By.cssSelector("[class='subtotals ']>.tax>span");
	protected By ORDER_SUBTOTAL_AMOUNT = By.cssSelector("[class='subtotals ']>.subtotal>span");
	protected By SUBTOTAL_FINAL_REVIEW = By.cssSelector("[class='subtotals dark']>.subtotal > span");
	protected By ORDER_DELIVERY_AMOUNT = By.cssSelector("[class='subtotals ']>.shipping>span");
	protected By DELIVERY_AMOUNT_FINAL_REVIEW = By.cssSelector("[class='subtotals dark']>.shipping > span");
	protected By ORDER_TOTAL_AMOUNT = By.cssSelector("[class='subtotals ']>.totals>span");
	protected By REVIEW_ORDERTOTAL_AMOUNT_WITHOUT_TAX = By.cssSelector("[class='subtotals dark']>.totals>span");
	protected By TERMS_CONDITIONS = By.id("Terms1");
	protected By PLACEORDER = By.id("placeOrder");
	protected By PLACEORDER_HEADING = By.className("checkout-success__body__headline");
	protected By ADDRESS_CLEANSING_POP_UP_TITLE = By.xpath(
		"//h4[contains(@class,'suggested_address_title') and contains(text(),'Verify Your address')]");
	protected By USE_SUGGESTED_ADDRESS_BUTTON = By.cssSelector("[class='positive use_address']");
	protected By SUBMIT_AS_IS_BUTTON = By.cssSelector("[class='form use_address']");
	protected By SUGGESTEDADDRESS_ON_ADDRESSCLEANSINGPOPUP = By.xpath(
		"//h4[contains(@class,'suggested_address_title') and contains(text(),'Verify Your address')]//following-sibling::div/ul");
	protected By ORIGINALADDRESS_ON_ADDRESSCLEANSINGPOPUP = By.xpath(
		"//h4[contains(@class,'suggested_address_title') and contains(text(),'keep your original address')]//following-sibling::div/ul");
	protected By SHIP_TO_ADDRESS = By.className("address");
	protected By SHIPPING_ADDRESS_FROM_SHIPPING_METHOD = By.xpath(
		"//*[*[@class='checkout-shipping-items-header']]/span");
	protected By PAYMENT_ADDRESS = By.xpath(
		"//li[@class='checkout-order-summary-list-heading']//div[contains(text(),'Visa')]");
	protected By ADDRESS_CLEANSING_POP_UP = By.id("cboxClose");
	protected By SHIPPING_POST_CODE_INPUT = By.cssSelector("input[id='address.postcode']");
	protected By SHIPPING_NEXT_BUTTON = By.id("addressSubmit");
	protected By ORDER_SUMMARY_PRODUCT_LIST_CONTAINER = By.className("checkout-order-summary-list");
	protected By ORDER_SUMMARY_PRODUCT_NAMES = By.className("checkout-order-summary-list-items");
	protected By ADDTOCART_PRODUCTPAGE = By.id("addToCartButton");
	protected By ADDTOCART_INPUT = By.id("pdpAddtoCartInput");

	/***
	 * Method to select country
	 *
	 * @param country
	 *            - select required country from shipping dropdown
	 */
	public void selectCountry( String country )
	{
		wait.waitForElementDisplayed(SHIPPING_COUNTRY_DROPDOWN);
		dropdown.selectDropdownByDisplayName(SHIPPING_COUNTRY_DROPDOWN, country);
		wait.waitForElementDisplayed(SHIPPING_TITLE_DROPDOWN);
	}

	/***
	 * Method to select title from the drop down
	 *
	 * @param title
	 *            - Select required title from Title dropdown
	 */
	public void selectTitle( String title )
	{
		wait.waitForElementDisplayed(SHIPPING_TITLE_DROPDOWN);
		dropdown.selectDropdownByDisplayName(SHIPPING_TITLE_DROPDOWN, title);
	}

	/***
	 * method to set first name
	 *
	 * @param firstName
	 *            - Set required firstname
	 */
	public void setFirstName( String firstName )
	{
		wait.waitForElementDisplayed(SHIPPING_FIRST_NAME);
		text.enterText(SHIPPING_FIRST_NAME, firstName);
	}

	/***
	 * method to set last name
	 *
	 * @param lastName
	 *            - Set required last name
	 */
	public void setLastName( String lastName )
	{
		wait.waitForElementDisplayed(SHIPPING_LAST_NAME);
		text.enterText(SHIPPING_LAST_NAME, lastName);
	}

	/***
	 * method to set address line1
	 *
	 * @param addressLine1
	 *            - Set required addressline1
	 */
	public void setAddressLine_1( String addressLine1 )
	{
		wait.waitForElementDisplayed(SHIPPING_ADDRESS_LINE1);
		text.enterText(SHIPPING_ADDRESS_LINE1, addressLine1);
	}

	/***
	 * method to enter city
	 *
	 * @param city
	 *            - Set required City
	 */
	public void setCity( String city )
	{
		wait.waitForElementDisplayed(SHIPPING_CITY);
		text.enterText(SHIPPING_CITY, city);
	}

	/***
	 * Method to select state
	 *
	 * @param state
	 *            - Select required state from ShippingState dropdown
	 */
	public void selectState( String state )
	{
		wait.waitForElementDisplayed(SHIPPING_STATE_DROPDOWN);
		dropdown.selectDropdownByDisplayName(SHIPPING_STATE_DROPDOWN, state);
	}

	/***
	 * method to enter postal/zip code
	 *
	 * @param postCode
	 *            - Set required postal/zip code
	 */
	public void setPostCode( String postCode )
	{
		waitForPageLoad();
		final WebElement SHIPPING_POST_CODE_INPUT_ELE = element.getWebElement(SHIPPING_POST_CODE);
		scroll.scrollElementIntoView(SHIPPING_POST_CODE_INPUT_ELE);
		wait.waitForElementDisplayed(SHIPPING_POST_CODE);
		focus.focusOnElementJavascript(SHIPPING_POST_CODE);
		wait.waitForElementEnabled(SHIPPING_POST_CODE);
		text.enterText(SHIPPING_POST_CODE, postCode);
	}

	/***
	 * Method to enter phone number
	 *
	 * @param phone
	 *            - Set required phone number
	 */
	public void setPhoneNumber( String phone )
	{
		wait.waitForElementDisplayed(SHIPPING_PHONE);
		text.enterText(SHIPPING_PHONE, phone);
	}

	/***
	 * Method to fill shipping address details
	 *
	 * @param country - Required country to be selected
	 * @param title - Required title to be selected
	 * @param firstName - Enter firstName of Shipping Address
	 * @param lastName - Enter lastName of Shipping Address
	 * @param addressLine1 - Enter addressLine1 of Shipping Address
	 * @param city - Enter city of Shipping Address
	 * @param state - Select required state from State dropdown
	 * @param postCode - Enter postCode of Shipping Address
	 */
	public void fillShippingAddressDetails( String country, String title, String firstName, String lastName,
		String addressLine1, String city, String state, String postCode )
	{
		selectCountry(country);
		selectTitle(title);
		setFirstName(firstName);
		setLastName(lastName);
		setAddressLine_1(addressLine1);
		setCity(city);
		selectState(state);
		setPostCode(postCode);
	}

	/***
	 * Method to click the next button of Delivery Address
	 */
	public void clickDeliveryAddressNext( )
	{
		final WebElement SHIPPING_NEXT_BUTTON_ELE = element.getWebElement(SHIPPINGNEXT_BUTTON);
		scroll.scrollElementIntoView(SHIPPING_NEXT_BUTTON_ELE);
		click.clickElement(SHIPPINGNEXT_BUTTON);
		waitForPageLoad();
		isOrderSummaryPageDisplayed();
	}

	/***
	 * Method to validate whether order summary page displayed
	 */
	public void isOrderSummaryPageDisplayed( )
	{
		if ( element.isElementDisplayed(ORDER_SUMMERY_HEADING) )
		{
			final String heading = attribute.getElementAttribute(ORDER_SUMMERY_HEADING, "text");
			VertexLogger.log(heading + " displayed");
		}
		else
		{
			VertexLogger.log("Order Summary Page not displayed", VertexLogLevel.WARN);
		}
	}

	/***
	 * Method to get selected Shipping Method
	 */
	public String getShippingMethod( )
	{
		final WebElement ele = dropdown.getDropdownSelectedOption(SHIPPING_METHOD);
		final String selectedOption = ele.getText();
		return selectedOption;
	}

	/***
	 * Method to select ShippingMethod
	 *
	 * @param shippingMethodValue - Select required Shipping Method Value from ShippingMethod dropdown
	 */
	public void selectShippingMethod( String shippingMethodValue )
	{
		wait.waitForElementDisplayed(SHIPPING_METHOD);
		dropdown.selectDropdownByValue(SHIPPING_METHOD, shippingMethodValue);
	}

	/***
	 * Method to click on Next button from Delivery Method page
	 */
	public void clickDeliveryMethodNext( )
	{
		wait.waitForElementDisplayed(DELIVERY_METHOD_NEXT);
		click.clickElement(DELIVERY_METHOD_NEXT);
		waitForPageLoad();
	}

	/***
	 * Method to select Card Type
	 *
	 * @param cardType - Select Type of the Creditcard from cardtype dropdown
	 */
	public void selectCardType( String cardType )
	{
		wait.waitForElementDisplayed(CARD_TYPE);
		dropdown.selectDropdownByDisplayName(CARD_TYPE, cardType);
	}

	/***
	 * method to set Card Name
	 *
	 * @param cardName - Enter name of the Creditcard
	 */
	public void setCardName( String cardName )
	{
		wait.waitForElementDisplayed(CARDNAME);
		text.enterText(CARDNAME, cardName);
	}

	/***
	 * method to set Card Number
	 *
	 * @param cardNumber - Enter Credit Card Number
	 */
	public void setCardNumber( String cardNumber )
	{
		wait.waitForElementDisplayed(CARD_NUMBER);
		text.enterText(CARD_NUMBER, cardNumber);
	}

	/***
	 * Method to select Card Expiry Month
	 *
	 * @param expMonth - Select Expiry month of Credit Card
	 */
	public void selectExpiryMonth( String expMonth )
	{
		wait.waitForElementDisplayed(CARD_EXPIRY_MONTH);
		dropdown.selectDropdownByDisplayName(CARD_EXPIRY_MONTH, expMonth);
	}

	/***
	 * Method to select Card Expiry Year
	 *
	 * @param expYear - Select Expiry Year of the Credit Card
	 */
	public void selectExpiryYear( String expYear )
	{
		wait.waitForElementDisplayed(CARD_EXPIRY_YEAR);
		dropdown.selectDropdownByDisplayName(CARD_EXPIRY_YEAR, expYear);
	}

	/***
	 * method to set Verification Number
	 *
	 * @param verificationNumber - Enter CVV or Verification Number
	 */
	public void setVerificationNumber( String verificationNumber )
	{
		wait.waitForElementDisplayed(CARD_VERIFICATION_NUMBER);
		text.enterText(CARD_VERIFICATION_NUMBER, verificationNumber);
	}

	/***
	 * Method to fill CreditCard payment Details
	 *
	 * @param cardType - Select Type of the Credit Card
	 * @param cardName - Enter Name of the Credit Card
	 * @param cardNumber - Enter Number of the Credit Card
	 * @param expMonth - Select Expiry month of Credit Card
	 * @param expYear - Select Expiry year of Credit Card
	 * @param verificationNumber - Enter CVV or Verification Number
	 */
	public void fillPaymentDetails( String cardType, String cardName, String cardNumber, String expMonth,
		String expYear, String verificationNumber )
	{
		selectCardType(cardType);
		setCardName(cardName);
		setCardNumber(cardNumber);
		selectExpiryMonth(expMonth);
		selectExpiryYear(expYear);
		setVerificationNumber(verificationNumber);
	}

	/***
	 * method to enable or select UseDeliveryAddress CheckBox
	 */
	public void enableUseDeliveryAddress( )
	{
		wait.waitForElementDisplayed(USEDELIVERYADDRESS_CHECKBOX);
		checkbox.setCheckbox(USEDELIVERYADDRESS_CHECKBOX, true);
	}

	/***
	 * method to disable or unselect UseDeliveryAddress CheckBox
	 */
	public void uncheckUseDeliveryAddress( )
	{
		wait.waitForElementDisplayed(USEDELIVERYADDRESS_CHECKBOX);
		checkbox.setCheckbox(USEDELIVERYADDRESS_CHECKBOX, false);
	}

	/***
	 * Method to click the PaymentMethod & Billing Address next button
	 */
	public void clickpaymentBillingAddressNext( )
	{
		final WebElement PAYMENTNEXTBUTTON_ELE = element.getWebElement(PAYMENT_METHOD_NEXT);
		scroll.scrollElementIntoView(PAYMENTNEXTBUTTON_ELE);
		click.clickElement(PAYMENT_METHOD_NEXT);
		waitForPageLoad();
	}

	/***
	 * Method to check whether Tax element/field is displayed
	 *
	 * @return True if TaxAmount is displayed
	 */
	public boolean isTaxDisplayed( )
	{
		return element.isElementDisplayed(ORDER_SUMMERY_TAX);
	}

	/***
	 * Method to get Tax Amount from Order Summary page
	 *
	 * @return Tax Amount in Float
	 */
	public float getTaxAmount( )
	{
		float result = 0;
		String taxAmountStr = null;
		if ( this.isTaxDisplayed() )
		{
			taxAmountStr = attribute
				.getElementAttribute(ORDER_SUMMERY_TAX, "text")
				.trim();
			double taxTotalAmt = VertexCurrencyUtils.cleanseCurrencyString(taxAmountStr);
			VertexLogger.log("Tax field is displayed and the tax amount: " + taxAmountStr);
			result = Float.parseFloat(String.valueOf(taxTotalAmt));
		}
		else
		{
			VertexLogger.log("Tax field is not displayed", VertexLogLevel.ERROR);
		}
		return result;
	}

	/***
	 * Method to get SubTotal Amount from Order Summary page
	 *
	 * @return SubTotal Amount in Float
	 */
	public float getSubtotal( )
	{
		float result = 0;
		if ( element.isElementDisplayed(ORDER_SUBTOTAL_AMOUNT) )
		{
			final String orderSubTotalStr = attribute
				.getElementAttribute(ORDER_SUBTOTAL_AMOUNT, "textContent")
				.trim();
			double orderSubTotalAmt = VertexCurrencyUtils.cleanseCurrencyString(orderSubTotalStr);
			VertexLogger.log("Order Sub Total Amount: " + orderSubTotalStr);
			result = Float.parseFloat(String.valueOf(orderSubTotalAmt));
		}
		return result;
	}

	/***
	 * Method to get SubTotal from Review Section of Order Summary Page
	 *
	 * @return FinalReviewSubTotal Amount in Float
	 */
	public float getFinalReviewSubtotal( )
	{
		float result = 0;
		if ( element.isElementDisplayed(SUBTOTAL_FINAL_REVIEW) )
		{
			final String finalReviewSubTotalAmount = attribute
				.getElementAttribute(SUBTOTAL_FINAL_REVIEW, "textContent")
				.trim();
			VertexLogger.log("Final Review Sub Total Amount: " + finalReviewSubTotalAmount);
			result = Float.parseFloat(finalReviewSubTotalAmount.replace("$", ""));
		}
		return result;
	}

	/***
	 * Method to get Delivery Amount from Order Summary page
	 *
	 * @return Delivery Amount in Float
	 */
	public float getDeliveryAmount( )
	{
		float result = 0;
		if ( element.isElementDisplayed(ORDER_DELIVERY_AMOUNT) )
		{
			final String orderDeliveryAmtStr = attribute
				.getElementAttribute(ORDER_DELIVERY_AMOUNT, "textContent")
				.trim();
			double orderDeliveryAmt = VertexCurrencyUtils.cleanseCurrencyString(orderDeliveryAmtStr);
			VertexLogger.log("Delivery Amount: " + orderDeliveryAmtStr);
			result = Float.parseFloat(String.valueOf(orderDeliveryAmt));
		}
		return result;
	}

	/***
	 * Method to get Delivery Amount from Review Section of Order Summary Page
	 *
	 * @return Final Review Delivery Amount in Float
	 */
	public float getFinalReviewDeliveryAmount( )
	{
		float result = 0;
		if ( element.isElementDisplayed(DELIVERY_AMOUNT_FINAL_REVIEW) )
		{
			final String deliveryAmount = attribute
				.getElementAttribute(DELIVERY_AMOUNT_FINAL_REVIEW, "textContent")
				.trim();
			VertexLogger.log("Final Review Delivery Amount: " + deliveryAmount);
			result = Float.parseFloat(deliveryAmount.replace("$", ""));
		}
		return result;
	}

	/***
	 * Method to get Order Total Amount from Order Summary page
	 *
	 * @return Order Total Amount in Float
	 */
	public float getOrderTotalAmount( )
	{
		float result = 0;
		if ( element.isElementDisplayed(ORDER_TOTAL_AMOUNT) )
		{
			final String orderTotalAmtStr = attribute
				.getElementAttribute(ORDER_TOTAL_AMOUNT, "textContent")
				.trim();
			double orderTotalAmt = VertexCurrencyUtils.cleanseCurrencyString(orderTotalAmtStr);
			VertexLogger.log("Order Toatl Amount: " + orderTotalAmtStr);
			result = Float.parseFloat(String.valueOf(orderTotalAmt));
		}
		return result;
	}

	/***
	 * Method to get Order Total Amount Excluding Tax from Review Section of Order Summary page
	 *
	 * @return Order Total Amount Excluding Tax in Float
	 */
	public float getOrderTotalAmountExcludeTax( )
	{
		float result = 0;
		if ( element.isElementDisplayed(REVIEW_ORDERTOTAL_AMOUNT_WITHOUT_TAX) )
		{
			final String reviewOrderTotalAmount = attribute
				.getElementAttribute(REVIEW_ORDERTOTAL_AMOUNT_WITHOUT_TAX, "textContent")
				.trim();
			VertexLogger.log("Final Review Delivery Amount: " + reviewOrderTotalAmount);
			result = Float.parseFloat(reviewOrderTotalAmount.replace("$", ""));
		}
		return result;
	}

	/***
	 * Method to Enable Terms and Conditions Checkbox
	 */
	public void enableTermsConditions( )
	{
		checkbox.setCheckbox(TERMS_CONDITIONS, true);
	}

	/***
	 * Method to Place Order
	 */
	public HybrisElectronicsStoreOrderConfirmationPage placeOrder( )
	{
		String heading = "";
		wait.waitForElementDisplayed(PLACEORDER);
		click.clickElement(PLACEORDER);
		waitForPageLoad();
		if ( element.isElementDisplayed(PLACEORDER_HEADING) )
		{
			heading = attribute.getElementAttribute(PLACEORDER_HEADING, "textContent");
			VertexLogger.log(heading + " displayed");
		}
		else
		{
			VertexLogger.log("Thank you for your order is not displayed", VertexLogLevel.WARN);
		}
		final HybrisElectronicsStoreOrderConfirmationPage orderconfirmpage = initializePageObject(
			HybrisElectronicsStoreOrderConfirmationPage.class);
		return orderconfirmpage;
	}

	/***
	 * Method to check whether Address Cleansing Popup displayed
	 *
	 * @return flag - True if Address Cleansing popup displayed
	 * @return flag - False if Address Cleansing popup is not displayed
	 */
	public boolean isAddressCleansingPopupDisplayed( )
	{
		boolean flag = false;
		if ( element.isElementDisplayed(ADDRESS_CLEANSING_POP_UP_TITLE) )
		{
			flag = true;
			VertexLogger.log("Address Cleansing pop-up displayed");
		}
		else
		{
			flag = false;
			VertexLogger.log("Address Cleansing pop-up NOT displayed");
		}
		return flag;
	}

	/***
	 * Method to Check IsUseAddressButton Displayed
	 *
	 * @return flag - True if UseThisAddress Button displayed on Address Cleansing popup
	 * @return flag - False if UseThisAddress Button is Not displayed on Address Cleansing popup
	 */
	public boolean isUseThisAddressButtonDisplayed( )
	{
		boolean flag = false;
		if ( element.isElementDisplayed(USE_SUGGESTED_ADDRESS_BUTTON) )
		{
			flag = true;
			VertexLogger.log("Use Address Button displayed");
		}
		else
		{
			flag = false;
			VertexLogger.log("Use Address Button NOT displayed", VertexLogLevel.ERROR);
		}
		return flag;
	}

	/***
	 * Method to Check IsSubmitAsIsButton Displayed
	 *
	 * @return flag - True if SubmitAsIs Button displayed on Address Cleansing popup
	 * @return flag - False if SubmitAsIs Button is Not displayed on Address Cleansing popup
	 */
	public boolean isSubmittAsIsButtonDisplayed( )
	{
		boolean flag = false;
		if ( element.isElementDisplayed(SUBMIT_AS_IS_BUTTON) )
		{
			flag = true;
			VertexLogger.log("Use Address Button displayed");
		}
		else
		{
			flag = false;
			VertexLogger.log("Use Address Button NOT displayed", VertexLogLevel.ERROR);
		}
		return flag;
	}

	/***
	 * Method to Get Suggested Address which will be shown on Addresscleansing Popup
	 * i.e. Suggested address will be returned by Oseries and shown on AddressCleansing popup
	 *
	 * @return Suggested Address from Address Cleansing popup
	 */
	public String getSuggestedAddressFromAddressCleansingPopUp( )
	{
		wait.waitForElementDisplayed(ADDRESS_CLEANSING_POP_UP_TITLE);
		final String suggestedAddress = attribute
			.getElementAttribute(SUGGESTEDADDRESS_ON_ADDRESSCLEANSINGPOPUP, "textContent")
			.trim();
		VertexLogger.log("Suggested Address is:" + suggestedAddress);
		return suggestedAddress;
	}

	/***
	 * Method to Get Original Address From Addresscleansing Popup
	 *
	 * @return Original Address from Address Cleansing popup
	 */
	public String getOriginalAddressFromAddressCleansingPopUp( )
	{
		wait.waitForElementDisplayed(ADDRESS_CLEANSING_POP_UP_TITLE);
		final String originalAddress = attribute
			.getElementAttribute(ORIGINALADDRESS_ON_ADDRESSCLEANSINGPOPUP, "textContent")
			.trim();
		VertexLogger.log("Suggested Address is:" + originalAddress);
		return originalAddress;
	}

	/***
	 * Method to Check IsOriginal Address Displayed
	 */
	public void isOriginalAddressDisplayed( )
	{
		if ( element.isElementDisplayed(SUBMIT_AS_IS_BUTTON) )
		{
			VertexLogger.log("Use Address Button displayed");
		}
		else
		{
			VertexLogger.log("Use Address Button NOT displayed", VertexLogLevel.ERROR);
		}
	}

	/***
	 * Method to get ship to address from order summary
	 *
	 * @return ShipTo Address
	 */
	public String getShipToAddressFromOrderSummary( )
	{
		final String shipToAddress = attribute
			.getElementAttribute(SHIP_TO_ADDRESS, "textContent")
			.trim();
		return shipToAddress;
	}

	/***
	 * Method to get shipping address from Shipping Method Section
	 *
	 * @return Shipping Address
	 */
	public String getShippingAddressFromShippingMethodSection( )
	{
		String shippingAddress = "";
		shippingAddress = attribute
			.getElementAttribute(SHIPPING_ADDRESS_FROM_SHIPPING_METHOD, "textContent")
			.trim();
		return shippingAddress;
	}

	/***
	 * Method to get Payment address from Order Summary Page
	 *
	 * @return Payment Address
	 */
	public String getPaymentAddressFromOrderSummary( )
	{
		String paymentAddress = "";
		paymentAddress = attribute
			.getElementAttribute(PAYMENT_ADDRESS, "textContent")
			.trim();
		return paymentAddress;
	}

	/***
	 * Method to click use this address button in address cleansing pop up
	 */
	public void clickUseThisAddress( )
	{
		wait.waitForElementDisplayed(USE_SUGGESTED_ADDRESS_BUTTON);
		click.clickElement(USE_SUGGESTED_ADDRESS_BUTTON);
		waitForPageLoad();
	}

	/***
	 * Method to click Submit As Is button in address cleansing pop up
	 */
	public void clickSubmitAsIs( )
	{
		wait.waitForElementDisplayed(SUBMIT_AS_IS_BUTTON);
		click.clickElement(SUBMIT_AS_IS_BUTTON);
		waitForPageLoad();
	}

	/***
	 * Method to close address cleansing pop-up
	 */
	public void closeAddressCleansingPopUp( )
	{
		wait.waitForElementDisplayed(ADDRESS_CLEANSING_POP_UP);
		click.clickElement(ADDRESS_CLEANSING_POP_UP);
		waitForPageLoad();
		final WebElement ele = element.getWebElement(SHIPPING_POST_CODE_INPUT);
		scroll.scrollElementIntoView(ele);
		wait.waitForElementDisplayed(SHIPPING_POST_CODE_INPUT);
		hover.hoverOverElement(SHIPPING_POST_CODE_INPUT);
		wait.waitForElementEnabled(SHIPPING_POST_CODE_INPUT);
		final WebElement shipNextButton = element.getWebElement(SHIPPING_NEXT_BUTTON);
		scroll.scrollElementIntoView(shipNextButton);
		wait.waitForElementEnabled(SHIPPING_NEXT_BUTTON);
	}

	/***
	 * Navigate to Product from Order Summary Page
	 * @param productName - corresponding product to be selected
	 *
	 */
	public void clickOnProductFromOrderSummary( String productName )
	{
		wait.waitForElementDisplayed(ORDER_SUMMARY_PRODUCT_LIST_CONTAINER);
		WebElement productListContainer = driver.findElement(ORDER_SUMMARY_PRODUCT_LIST_CONTAINER);
		List<WebElement> productNames = productListContainer.findElements(ORDER_SUMMARY_PRODUCT_NAMES);
		WebElement product = null;
		for ( int i = 0 ; i < productNames.size() ; i++ )
		{
			product = productNames.get(i);
			if ( product
				.getText()
				.contains(productName) )
			{
				product.click();
			}
		}
	}

	/***
	 * Method to change the Quantity by selecting product Name
	 * @param productName - corresponding product image to be selected
	 * @param quantity - quantity to change for the corresponding product
	 *
	 */
	public void changeQuantityForProduct( String productName, String quantity )
	{
		clickOnProductFromOrderSummary(productName);
		wait.waitForElementDisplayed(ADDTOCART_PRODUCTPAGE);
		text.enterText(ADDTOCART_INPUT, quantity);
	}

	/***
	 * Method - Adding product to Cart
	 *
	 */
	public HybrisElectronicsStorePage addProductToCart( )
	{
		click.clickElement(ADDTOCART_PRODUCTPAGE);
		final HybrisElectronicsStorePage storeFront = initializePageObject(HybrisElectronicsStorePage.class);
		return storeFront;
	}
}
