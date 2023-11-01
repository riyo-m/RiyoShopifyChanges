package com.vertex.quality.connectors.dynamics365.finance.enums;

/**
 * @author Saidulu kodadala
 * Common data file (This data used for across test cases)
 */
public enum DFinanceConstantDataResource
{
	D365HOMEPAGE_TITLE("Finance and Operations"),
	CA("CA"),
	VERTEXAR("VertexAR"),
	VERTEXAP("VertexAP"),
	VERTEXADVA("VertexAdva"),
	VTXPARTIAL("VTXPartial"),
	VTXPCLASS("VTXPClass"),
	VTXNORECOV("VTXNoRecov"),
	VTXVCODE("VTXVCode"),
	ALL("ALL"),
	PURCHASE_ORDER_NUMBER("Purchase Order# %s"),
	DESCRIPTION("Test123"),
	ZIPCODE("98073"),
	STREET("22833 NE 8th St"),
	CITY("Sammamish"),
	STATE("WA"),
	COUNTRY("KING"),
	VENDOR_ACCOUNT("US_TX_015"),
	VENDOR_ACCOUNT_Pittsburgh("D_US_PA1"),
	GERMANY_VENDOR_ACCOUNT_1("DE_TX_001"),
	BRITISH_VENDOR_ACCOUNT_1("GB_SI_000002"),
	FRANCE_VENDOR_ACCOUNT_1("FR_SI_000001"),
	GERMAN_COMPANY_DEMF("DEMF"),
	BRITISH_COMPANY_GBSI("GBSI"),
	US_RETAIL_COMPANY_USRT("USRT"),
	US_COMPANY_USMF("USMF"),
	VENDOR_ACCOUNT_EXEMPT("VTXVCode"),
	VENDOR_ACCOUNT_NO("1001"),
	USE_TAX_RECEIVABLE("Use Tax Receivable"),
	OTHER_RECEIVABLES("Other Receivables"),
	VAT_TAX_PAYABLE("VAT Tax Payable"),
	VAT_CODE_SALES_TAX_CODE("VAT_CODE"),
	V_SDO_19_G_SALES_TAX_CODE("V_SDO_19_G"),
	V_RC_G("V_RC_G"),
	ACCRUAL_REQUEST_TYPE("Accrual"),
	PURCHASE_ORDER_REQUEST_TYPE("Purchase order"),
	ITEM1_D0001("D0001"),
	ITEM2_D0003("D0003"),
	ITEM3_1000("1000"),
	QUANTITY_2("2"),
	ASSERTION_TOTAL_CALCULATED_TAX_AMOUNT_IS_NOT_EXPECTED("'Total calculated sales tax amount' value is not expected"),
	ASSERTION_TOTAL_ACTUAL_TAX_AMOUNT_IS_NOT_EXPECTED("'Total actual sales tax amount' value is not expected"),
	ASSERTION_TOTAL_ACCRUAL_TAX_IS_NOT_EXPECTED("'Total Accrual sales tax amount' value is not expected"),
	PO_CONFIRMATION_FAILED("PO Confirmation failed"),
	PRODUCT_RECEIPT_NUMBER("Product Receipt Number: %s"),
	MATCH_STATUS("Expected Match Status: %s, but actual Match Status: %s"),
	INVOICE_POSTING_FAILED("Invoice#: %s posting failed"),
	INVOICE_NUMBER_NOT_FOUND("Invoice#: %s not found"),
	EXPECTED_INVOICE_NUMBER("Expected Invoice#: %s, but actual Invoice#: %s"),
	DOCUMENT_TYPE_IS_NOT_EXPECTED("Document type is not equal to what is expected"),
	REQUEST_TYPE_IS_NOT_EXPECTED("'Requested Type' is not expected"),
	OPERATION_COMPLETED_MESSAGE("Operation completed"),
	COMMODITY_CODE_TRUNCATED("The Commodity code number is more than 8 characters. Please note that only 8 will be processed"),;

	String dataName;

	DFinanceConstantDataResource( String data )
	{
		this.dataName = data;
	}

	/**
	 * Get the data from enum
	 *
	 * @return
	 */
	public String getData( )
	{
		return dataName;
	}
}
