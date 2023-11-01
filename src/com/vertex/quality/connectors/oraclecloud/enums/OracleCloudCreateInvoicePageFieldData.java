package com.vertex.quality.connectors.oraclecloud.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * Holds data for fields on the create invoice page
 * that can be interacted with or hold needed information
 * Due to the nature of the field labeling, makes it easier to find and interact with them
 *
 * @author cgajes
 */
@Getter
public enum OracleCloudCreateInvoicePageFieldData
{
	// INVOICE HEADER
	BUSINESS_UNIT(By.cssSelector("input[id$='ic2::content']"), "ic2", "Search: Business Unit"),
	SUPPLIER(By.cssSelector("input[id$='ic3::content']"), "ic3", "Search: Supplier"),
	SUPPLIER_NUMBER(By.cssSelector("tr[id$='p13'] td[2]"), null, null),
	SUPPLIER_SITE(By.cssSelector("input[id$='ic4::content']"), "ic4", "Search: Supplier Site"),
	LEGAL_ENTITY(By.cssSelector("input[id$='legalEntityNameId::content']"), "legalEntityNameId",
		"Search: Legal Entity"),
	NUMBER(By.cssSelector("input[id$='i2::content']"), "i2", null),
	AMOUNT_UNIT(By.cssSelector("select[id$='soc1::content']"), "soc1", null),
	AMOUNT_NUMBER(By.cssSelector("input[id$='i3::content']"), "i3", null),
	INV_TYPE(By.cssSelector("select[id$='so1::content']"), "so1", null),
	DATE(By.cssSelector("input[id$='id2::content']"), "id2", "Select Date"),
	PAYMENT_TERMS(By.cssSelector("input[id$='so3::content']"), "0:so3", "Search: Payment Terms"),
	TERMS_DATE(By.cssSelector("input[id$='id5::content']"), "id5", "Select Date"),

	// LINES
	LINE_MATCHING(By.cssSelector("select[id$='so3']"), "ap1:so3", null),
	LINE_TYPE(By.cssSelector("select[id$='so12::content']"), "so12", null),
	LINE_AMOUNT(By.cssSelector("input[id$='i26::content']"), "i26", null),
	DISTRIBUTION_SET(By.cssSelector("input[id$='so13::content']"), "so13", "Search: Distribution Set"),
	DISTRIBUTION_COMBINATION(By.cssSelector("input[id$='kf1CS::content']"), "kf1CS", null),
	ACCOUNTING_DATE(By.cssSelector("input[id$='id1::content']"), "id1", "Select Date"),
	SHIPTO_LOC(By.cssSelector("input[id$='ic28::content']"), "ic28", "Search: Ship-to Location"),

	// PAY IN FULL POPUP
	PAYMENT_PROCESS_PROFILE(By.cssSelector("input[id$='paymentProfileNameId::content']"), "paymentProfileNameId",
			null),
	PAYMENT_DOCUMENT(By.cssSelector("input[id$='paymentDocumentNameId::content']"), "paymentDocumentNameId",
			null),
	PAYMENT_NUMBER(By.cssSelector("input[id*='it1']"),"it1",null),

	// INVOICE WITHOUT PO
	// Only to be used in Supplier Portal
	SUPPLIER_SITE_PO(By.cssSelector("input[id*='vendorSiteCodeId']"),"vendorSiteCodeId","Search: Supplier Site"),
	NUMBER_PO(By.cssSelector("input[id*='invNum']"),"invNum",null),
	DATE_PO(By.cssSelector("input[id$='id1::content']"), "id1", "Select Date"),
	EMAIL_PO(By.cssSelector("input[id*='interContactEmail']"), "interContactEmail", null),
	ITEM_SHIP_TO_LOCATION(By.cssSelector("input[id*='shipToLocationId']"),"shipToLocationId", null),
	ITEM_AMOUNT(By.cssSelector("input[id*='itemAmount']"),"itemAmount",null);

	private By locator;
	private String idOrName;
	private String buttonTitle;

	/**
	 * Sets various points of information for an area on the create invoice page
	 *
	 * @param loc        the locator for the field
	 * @param identifier identifying portion of the field's name or ID
	 * @param button     title of any button associated with the field
	 */
	OracleCloudCreateInvoicePageFieldData( final By loc, final String identifier, final String button )
	{
		this.locator = loc;
		this.idOrName = identifier;
		this.buttonTitle = button;
	}
}
