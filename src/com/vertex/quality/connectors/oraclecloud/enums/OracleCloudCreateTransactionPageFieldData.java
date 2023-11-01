package com.vertex.quality.connectors.oraclecloud.enums;

import lombok.Getter;
import org.openqa.selenium.By;

/**
 * Holds data for fields on the create transaction page
 * that can be interacted with or hold needed information
 * Due to the nature of the field labeling, makes it easier to find and interact with them
 *
 * @author cgajes
 */
@Getter
public enum OracleCloudCreateTransactionPageFieldData
{
	// General Information
	TRANSACTION_CLASS(By.cssSelector("select[id*='transactionClass']"), "transactionClass", "Invoice"),
	BUSINESS_UNIT_INVOICECREDIT(By.cssSelector("input[id*='fcslov1']"), "fcslov1", null),
	BUSINESS_UNIT_DEBIT(By.cssSelector("input[id*='orgNameId']"), "orgNameId", "Search: Business Unit"),
	TRANSACTION_SOURCE(By.cssSelector("input[id*='batchSourceId']"), "batchSourceId", "Search: Transaction Source"),
	TRANSACTION_TYPE(By.cssSelector("input[id*='transactionTypeId']"), "transactionTypeId", "Search: Transaction Type"),
	CURRENCY(By.cssSelector("select[id*='invoiceCurrencyCodeId']"), "invoiceCurrencyCodeId", null),
	TAX(By.cssSelector("span[id*='inputText134']"), "inputText134", ""),
	// Customer
	BILL_TO_NAME(By.cssSelector("input[id*='billToNameId']"), "billToNameId", "Search: Bill-to Name"),
	SHIP_TO_NAME(By.cssSelector("input[id*='shipToNameId']"), "shipToNameId", "Search: Ship-to Name"),
	BILL_TO_SITE(By.cssSelector("inpur[id*='billToSiteId']"), "billToSiteId", "Search: Bill-to Site"),
	SHIP_TO_SITE(By.cssSelector("inpur[id*='shipToSiteId']"), "shipToSiteId", "Search: Ship-to Site"),
	// Payment
	PAYMENT_TERMS(By.cssSelector("input[id*='paymentTermId']"), "paymentTermId", "Search: Payment Terms"),

	// Invoice/Memo Information
	ITEM(By.cssSelector("input[id*='lovTxtId']"), "lovTxtId", "Search: Item"),
	DESCRIPTION(By.cssSelector("input[id*='descriptionId']"), "descriptionId", null),
	// Line Information
	MEMO_LINE(By.cssSelector("input[id*='memoLineNameId']"), "memoLineNameId", "Search: Memo Line"),
	UOM(By.cssSelector("input[id*='uOMId']"), "uOMId", "Search: UOM"),
	QUANTITY_INVOICECREDIT(By.cssSelector("input[id*='quantity']"), "quantity", null),
	QUANTITY_DEBIT(By.cssSelector("input[id*='qtyDM']"), "qtyDM", null),
	UNIT_PRICE_INVOICECREDIT(By.cssSelector("input[id*='ellingPrice']"), "ellingPrice", null),
	UNIT_PRICE_DEBIT(By.cssSelector("input[id*='priceDM']"), "priceDM", null),
	AMOUNT_INVOICECREDIT(By.cssSelector("span[id*='extendedAmt']"), "extendedAmt", null),
	AMOUNT_DEBIT(By.cssSelector("span[id*='extAmtDM']"), "extAmtDM", null),
	DETAILS(By.cssSelector("input[id*='commandImageLink']"), "commandImageLink", "Details"),
	// Tax Determinants
	TAX_CLASSIFICATION(By.cssSelector("input[id*='taxClassificationCodeId']"), "taxClassificationCodeId",
		"Search: Tax Classification"),
	TRANSACTION_BUSINESS_CATEGORY(By.cssSelector("input[id*='trxBusinessCategoryMirrorId']"),
		"trxBusinessCategoryMirrorId", "Search: Transaction Business Category");

	private By locator;
	private String idOrName;
	private String buttonTitle;

	/**
	 * Sets various points of information for an area on the create transaction page
	 *
	 * @param loc the locator for the field
	 * @param identifier identifying portion of the field's name or ID
	 * @param button     title of any button associated with the field
	 */
	OracleCloudCreateTransactionPageFieldData( final By loc, final String identifier, final String button )
	{
		this.locator = loc;
		this.idOrName = identifier;
		this.buttonTitle = button;
	}
}

