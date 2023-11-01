package com.vertex.quality.connectors.accumatica.pages;

import com.vertex.quality.connectors.accumatica.pages.base.AcumaticaPostSignOnPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author saidulu kodadala
 * Sales Orders page actions/ methods
 */
public class AcumaticaSalesOrdersValidationPage extends AcumaticaPostSignOnPage
{
	protected By SHIPPING_VIA = By.id("ctl00_phG_tab_t5_formF_edShipVia_text");
	protected By OVERRIDE_ADDRESS_CHECK_BOX_FROM_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_chkOverrideAddress");
	protected By ACS_CHECK_BOX_FROM_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_chkIsValidated");
	protected By ADDRESS_LINE_1_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_edAddressLine1");
	protected By ADDRESS_LINE_2_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_edAddressLine2");
	protected By CITY_FROM_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_edCity");
	protected By COUNTRY_FROM_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_edCountryID_text");
	protected By STATE_FROM_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_edState_text");
	protected By POSTAL_CODE_SHIP_TO_INFO = By.id("ctl00_phG_tab_t5_formB_edPostalCode");
	protected By ORDERED_QUANTITY = By.id("ctl00_phF_form_edOrderQty");
	protected By TAX_TOTAL = By.id("ctl00_phF_form_edCuryTaxTotal");
	protected By ORDER_TOTAL = By.id("ctl00_phF_form_edCuryOrderTotal");
	protected By LINE_TOTAL = By.id("ctl00_phG_tab_t9_formG_edCuryLineTotal");
	protected By MISC_TOTAL = By.id("ctl00_phG_tab_t9_formG_edCuryMiscTot");
	protected By DISCOUNT_TOTAL = By.id("ctl00_phG_tab_t9_formG_edCuryDiscTot");
	protected By TAX_TOTAL_FROM_ORDER_TOTALS = By.id("ctl00_phG_tab_t9_formG_edCuryTaxTotal");
	protected By UNSHIPPED_QUANTITY = By.id("ctl00_phG_tab_t9_formG_edOpenOrderQty");
	protected By UNSHIPPED_AMOUNT = By.id("ctl00_phG_tab_t9_formG_edCuryOpenOrderTotal");
	protected By UNBILLED_QUANTITY = By.id("ctl00_phG_tab_t9_formG_edUnbilledOrderQty");
	protected By UNBILLED_AMOUNT = By.id("ctl00_phG_tab_t9_formG_edCuryUnbilledOrderTotal");
	protected By PAYMENT_TOTAL = By.id("ctl00_phG_tab_t9_formG_edCuryPaymentTotal");
	protected By PRE_AUTHORIZED_AMOUNT = By.id("ctl00_phG_tab_t9_formG_edCuryCCPreAuthAmount1");
	protected By UNPAID_BALANCE = By.id("ctl00_phG_tab_t9_formG_edCuryUnpaidBalance1");
	protected By CURRENCY = By.id("ctl00_phF_form_edCury_cury_text");
	protected By ORDER_NUMBER = By.cssSelector("[id*='phF_form_edOrderNbr_text']");

	public AcumaticaSalesOrdersValidationPage( WebDriver driver )
	{
		super(driver);
	}

	/***
	 * Get ship via
	 * @return
	 */
	public String shipViaFromShippingInformationSection( )
	{
		waitForPageLoad();
		wait.waitForElementDisplayed(SHIPPING_VIA);
		String shipVia = attribute.getElementAttribute(SHIPPING_VIA, "value");
		return shipVia;
	}

	/***
	 * Enter ship via
	 * @param shipVia
	 */
	public void setShipViaFromShippingInformationSection( final String shipVia )
	{
		text.setTextFieldCarefully(SHIPPING_VIA, shipVia, false);
	}

	/***
	 * Clear ship via
	 */
	public void clearShipViaFromShippingInformationSection( )
	{
		waitForPageLoad();
		wait.waitForElementEnabled(SHIPPING_VIA);
		text.clearText(SHIPPING_VIA);
		text.pressTab(SHIPPING_VIA);
		waitForPageLoad();
	}

	/**
	 * Verify 'override address' check box
	 *
	 * @param expectedState
	 */
	public boolean setOverrideAddressCheckboxFromShipToInfoSection( final boolean expectedState )
	{
		wait.waitForElementDisplayed(OVERRIDE_ADDRESS_CHECK_BOX_FROM_SHIP_TO_INFO);
		boolean isChecked = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECK_BOX_FROM_SHIP_TO_INFO);
		if ( isChecked != expectedState )
		{
			click.clickElementCarefully(OVERRIDE_ADDRESS_CHECK_BOX_FROM_SHIP_TO_INFO);
		}
		boolean resultStatus = checkbox.isCheckboxChecked(OVERRIDE_ADDRESS_CHECK_BOX_FROM_SHIP_TO_INFO);
		return resultStatus;
	}

	/**
	 * Verify 'ACS' check box from ship to Info section
	 *
	 * @param flag
	 */
	public boolean verifyAcsFromShipToInfoSection( final boolean flag )
	{
		wait.waitForElementDisplayed(ACS_CHECK_BOX_FROM_SHIP_TO_INFO);
		boolean status = checkbox.isCheckboxChecked(ACS_CHECK_BOX_FROM_SHIP_TO_INFO);
		if ( status != flag )
		{
			//fixme at least some of the time, this won't do anything because the checkbox is disabled by something else
			click.clickElement(ACS_CHECK_BOX_FROM_SHIP_TO_INFO);
			waitForPageLoad();
		}
		boolean resultStatus = checkbox.isCheckboxChecked(ACS_CHECK_BOX_FROM_SHIP_TO_INFO);
		return resultStatus;
	}

	/**
	 * Get address Line1 from ship to info section
	 *
	 * @return
	 */
	public String getAddressLine1FromShipToInfoSection( )
	{
		wait.waitForElementDisplayed(ADDRESS_LINE_1_SHIP_TO_INFO);
		String addressLine1 = attribute.getElementAttribute(ADDRESS_LINE_1_SHIP_TO_INFO, "value");
		addressLine1 = text.cleanseWhitespace(addressLine1);
		return addressLine1;
	}

	/***
	 * Get address Line1 from ship to info section
	 * @return
	 */
	public String getAddressLine2FromShipToInfoSection( )
	{
		wait.waitForElementDisplayed(ADDRESS_LINE_2_SHIP_TO_INFO);
		String addressLine2 = attribute.getElementAttribute(ADDRESS_LINE_2_SHIP_TO_INFO, "value");
		addressLine2 = text.cleanseWhitespace(addressLine2);
		return addressLine2;
	}

	/***
	 * Enter Address line1 from ship to info section
	 * @param addressLine1
	 */
	public void setAddressLine1FromShipToInfoSection( final String addressLine1 )
	{
		text.setTextFieldCarefully(ADDRESS_LINE_1_SHIP_TO_INFO, addressLine1);
	}

	/***
	 * Get city from ship to info section
	 * @return
	 */
	public String getCityFromShipToInfoSection( )
	{
		wait.waitForElementDisplayed(CITY_FROM_SHIP_TO_INFO);
		String city = attribute.getElementAttribute(CITY_FROM_SHIP_TO_INFO, "value");
		city = text.cleanseWhitespace(city);
		return city;
	}

	/***
	 * Enter city from ship to info section
	 * @param city
	 */
	public void enterCityFromShipToInfoSection( final String city )
	{
		text.setTextFieldCarefully(CITY_FROM_SHIP_TO_INFO, city);
	}

	/***
	 * Get country from ship to info section
	 * @return
	 */
	public String getCountryFromShipToInfoSection( )
	{
		wait.waitForElementDisplayed(COUNTRY_FROM_SHIP_TO_INFO);
		String country = attribute.getElementAttribute(COUNTRY_FROM_SHIP_TO_INFO, "value");
		country = text.cleanseWhitespace(country);
		return country;
	}

	/***
	 * Get State from ship to info section
	 * @return
	 */
	public String getStateFromShipToInfoSection( )
	{
		wait.waitForElementDisplayed(STATE_FROM_SHIP_TO_INFO);
		String state = attribute.getElementAttribute(STATE_FROM_SHIP_TO_INFO, "value");
		state = text.cleanseWhitespace(state);
		return state;
	}

	/***
	 * Enter state from ship to info section
	 * @param state
	 */
	public void enterStateFromShipToInfoSection( final String state )
	{
		text.setTextFieldCarefully(STATE_FROM_SHIP_TO_INFO, state);
	}

	/***
	 * Get postal code from ship to info section
	 * @return
	 */
	public String getPostalCodeFromShipToInfoSection( )
	{
		wait.waitForElementDisplayed(POSTAL_CODE_SHIP_TO_INFO);
		String zipCode = attribute.getElementAttribute(POSTAL_CODE_SHIP_TO_INFO, "value");
		zipCode = text.cleanseWhitespace(zipCode);
		return zipCode;
	}

	/***
	 * Enter postal code
	 * @param zipCode
	 */
	public void setPostalCodeFromShipToInfoSection( final String zipCode )
	{
		text.setTextFieldCarefully(POSTAL_CODE_SHIP_TO_INFO, zipCode);
	}

	/***
	 * Get order quantity
	 * @return
	 */
	public String getOrderQuantity( )
	{
		wait.waitForElementDisplayed(ORDERED_QUANTITY);
		String orderQunatity = attribute.getElementAttribute(ORDERED_QUANTITY, "value");
		orderQunatity = text.cleanseWhitespace(orderQunatity);
		return orderQunatity;
	}

	/***
	 * Get tax total
	 * @return
	 */
	public String getTaxTotal( )
	{
		wait.waitForElementDisplayed(TAX_TOTAL);
		String taxTotal = attribute.getElementAttribute(TAX_TOTAL, "value");
		taxTotal = text.cleanseWhitespace(taxTotal);
		return taxTotal;
	}

	//TODO move to Sales Order Page? unless it also shows up elsewhere

	/**
	 * Get order total
	 *
	 * @return
	 */
	public String getOrderTotal( )
	{
		wait.waitForElementDisplayed(ORDER_TOTAL);
		String orderTotal = attribute.getElementAttribute(ORDER_TOTAL, "value");
		orderTotal = text.cleanseWhitespace(orderTotal);
		return orderTotal;
	}

	/***
	 * Get line total
	 * @return
	 */
	public String getLineTotal( )
	{
		wait.waitForElementDisplayed(LINE_TOTAL);
		String lineTotal = attribute.getElementAttribute(LINE_TOTAL, "value");
		lineTotal = text.cleanseWhitespace(lineTotal);
		return lineTotal;
	}

	/***
	 * Get misc total
	 * @return
	 */
	public String getMiscTotal( )
	{
		wait.waitForElementDisplayed(MISC_TOTAL);
		String miscTotal = attribute.getElementAttribute(MISC_TOTAL, "value");
		miscTotal = text.cleanseWhitespace(miscTotal);
		return miscTotal;
	}

	/***
	 * Get discount total
	 * @return
	 */
	public String getDiscountTotal( )
	{
		wait.waitForElementDisplayed(DISCOUNT_TOTAL);
		String discountTotal = attribute.getElementAttribute(DISCOUNT_TOTAL, "value");
		discountTotal = text.cleanseWhitespace(discountTotal);
		return discountTotal;
	}

	/***
	 * Get total tax
	 * @return
	 */
	public String getTotalTax( )
	{
		wait.waitForElementDisplayed(TAX_TOTAL_FROM_ORDER_TOTALS);
		String totalTax = attribute.getElementAttribute(TAX_TOTAL_FROM_ORDER_TOTALS, "value");
		totalTax = text.cleanseWhitespace(totalTax);
		return totalTax;
	}

	/***
	 * Get un-shipped quantity
	 * @return
	 */
	public String getUnshippedQuantity( )
	{
		wait.waitForElementDisplayed(UNSHIPPED_QUANTITY);
		String unshippedQuantity = attribute.getElementAttribute(UNSHIPPED_QUANTITY, "value");
		unshippedQuantity = text.cleanseWhitespace(unshippedQuantity);
		return unshippedQuantity;
	}

	/***
	 * Get un-shipped amount
	 * @return
	 */
	public String getUnshippedAmount( )
	{
		wait.waitForElementDisplayed(UNSHIPPED_AMOUNT);
		String unshippedAmount = attribute.getElementAttribute(UNSHIPPED_AMOUNT, "value");
		unshippedAmount = text.cleanseWhitespace(unshippedAmount);
		return unshippedAmount;
	}

	/***
	 * Get un-billed quantity
	 * @return
	 */
	public String getUnbilledQuantity( )
	{
		wait.waitForElementDisplayed(UNBILLED_QUANTITY);
		String unbilledQuantity = attribute.getElementAttribute(UNBILLED_QUANTITY, "value");
		unbilledQuantity = text.cleanseWhitespace(unbilledQuantity);
		return unbilledQuantity;
	}

	/***
	 * Get un-billed amount
	 * @return
	 */
	public String getUnbilledAmount( )
	{
		wait.waitForElementDisplayed(UNBILLED_AMOUNT);
		String unbilledAmount = attribute.getElementAttribute(UNBILLED_AMOUNT, "value");
		unbilledAmount = text.cleanseWhitespace(unbilledAmount);
		return unbilledAmount;
	}

	/***
	 * Get payment total
	 * @return
	 */
	public String getPaymentTotal( )
	{
		wait.waitForElementDisplayed(PAYMENT_TOTAL);
		String paymentTotal = attribute.getElementAttribute(PAYMENT_TOTAL, "value");
		paymentTotal = text.cleanseWhitespace(paymentTotal);
		return paymentTotal;
	}

	/***
	 * Get pre-authorized amount
	 * @return
	 */
	public String getPreAuthorizedAmount( )
	{
		wait.waitForElementDisplayed(PRE_AUTHORIZED_AMOUNT);
		String preAuthourizedAount = attribute.getElementAttribute(PRE_AUTHORIZED_AMOUNT, "value");
		preAuthourizedAount = text.cleanseWhitespace(preAuthourizedAount);
		return preAuthourizedAount;
	}

	/***
	 * Get un-paid balance
	 * @return
	 */
	public String getUnPaidBalance( )
	{
		wait.waitForElementDisplayed(UNPAID_BALANCE);
		String unPaidBalance = attribute.getElementAttribute(UNPAID_BALANCE, "value");
		unPaidBalance = text.cleanseWhitespace(unPaidBalance);
		return unPaidBalance;
	}

	/***
	 * Get order number
	 * @return
	 */
	public String getOrderNumber( )
	{
		wait.waitForElementDisplayed(ORDER_NUMBER);
		String orderNumber = attribute.getElementAttribute(ORDER_NUMBER, "value");
		orderNumber = text.cleanseWhitespace(orderNumber);
		return orderNumber;
	}
}
