package com.vertex.quality.connectors.saptaxservice.utils;

import com.vertex.quality.common.enums.*;
import com.vertex.quality.common.pojos.EnvironmentCredentials;
import com.vertex.quality.common.pojos.EnvironmentInformation;
import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.common.utils.SQLConnection;
import com.vertex.quality.common.utils.VertexLogger;
import com.vertex.quality.common.utils.api.VertexAPITestUtilities;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCountry;
import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxCode;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxServiceEndpoints;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPItem;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPLocation;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPQuoteInput;
import io.restassured.response.Response;

/**
 * a utility class for api testing of sap tax service
 *
 * @author ssalisbury
 */
public class SAPTaxServiceAPITestUtilities extends VertexAPITestUtilities
{
	public static final DBConnectorNames CONNECTOR_NAME = DBConnectorNames.SAP_TAX_SERVICE;
	public static final DBEnvironmentNames ENVIRONMENT_NAME = DBEnvironmentNames.QA;
	public static final DBEnvironmentDescriptors ENVIRONMENT_DESCRIPTOR
		= DBEnvironmentDescriptors.SAP_TAX_SERVICE_QUOTE;

	public static final String ID = "123456";
	public static final String DATE = "2018-08-14T04:00:00.000Z";
	public static final String CANADA_CURRENCY = "CAD";
	public static final String SALE = "s";
	public static final String PURCHASE = "p";
	public static final String GROSS = "g";
	public static final String NET = "n";
	public static final String YES = "y";
	public static final String NO = "n";
	public static final String ITEM_TYPE_MATERIAL = "m";
	public static final String ITEM_TYPE_SERVICE = "s";
	public static final String TAX_CATEGORY_PRODUCT_TAXES = "product_taxes";
	public static final String TAX_CATEGORY_WITHHOLDING = "withholding";
	public static final String STRING_VALUE = "string";
	public static final String GST = "GST";
	public static final String PST = "PST";
	public static final String QST = "QST";
	public static final String HST = "HST";
	public static final int SUCCESS_RESPONSE_CODE = ResponseCodes.SUCCESS.getResponseCode();
	public static final int BAD_REQUEST_RESPONSE_CODE = ResponseCodes.BAD_REQUEST.getResponseCode();

	public SAPTaxServiceAPITestUtilities( )
	{
		loadAPIAccessInformation();
	}

	@Override
	protected void loadAPIAccessInformation( )
	{
		try
		{
			EnvironmentInformation environmentInformation = SQLConnection.getEnvironmentInformation(CONNECTOR_NAME,
				ENVIRONMENT_NAME, ENVIRONMENT_DESCRIPTOR);
			EnvironmentCredentials environmentCredentials = SQLConnection.getEnvironmentCredentials(
				environmentInformation);
			baseUrl = environmentInformation.getEnvironmentUrl();
			setBasicAuth(environmentCredentials.getUsername(), environmentCredentials.getPassword());
		}
		catch ( Exception e )
		{
			VertexLogger.log("failed to retrieve the base url and credentials for a test of the  SAP tax service API",
				VertexLogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the basic quote input
	 *
	 * @param jsonPath   the json path to edit in the quote input
	 * @param fieldName  the field in the quote input
	 * @param fieldValue the field's value in the quote input
	 *
	 * @return the response
	 */
	public Response setupDefaultQuoteInputCall( final String jsonPath, final String fieldName, final Object fieldValue )
	{
		SAPItem itemOne = SAPItem
			.builder()
			.taxCodeCountry(SAPCountry.CANADA.getCountryAbbreviation())
			.taxCodeRegion(SAPRegion.ALBERTA.getAbbreviation())
			.taxCode(SAPTaxCode.TAX_CODE_501.getTaxCode())
			.taxCategory("PRODUCT_TAXES")
			.quantity("1")
			.unitPrice("500")
			.itemType(ITEM_TYPE_MATERIAL)
			.build();
		SAPQuoteInput quoteInput = SAPQuoteInput
			.builder()
			.businessPartnerId(STRING_VALUE)
			.cashDiscountPercent(STRING_VALUE)
			.companyId(STRING_VALUE)
			.currency(STRING_VALUE)
			.date(DATE)
			.grossOrNet(NET)
			.id(STRING_VALUE)
			.isCashDiscountPlanned(NO)
			.isCompanyDeferredTaxEnabled(NO)
			.isTraceRequired(YES)
			.isTransactionWithinTaxReportingGroup(YES)
			.Item(itemOne)
			.saleOrPurchase(SALE)
			.build();

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField(jsonPath, fieldName, fieldValue)
			.createRequestAndGetResponse();

		return response;
	}

	/**
	 * Sets up a default direct payload request
	 *
	 * @param taxCategory    the item's tax category
	 * @param taxCodeCountry the item's tax code country
	 * @param taxCodeRegion  the item's tax code region
	 * @param taxCode        the tax code
	 * @param itemType       the item type
	 * @param saleOrPurchase sale or purchase
	 * @param grossOrNet     gross or net
	 *
	 * @return the default direct payload request
	 */
	public SAPQuoteInput setupDefaultDirectPayloadRequest( String taxCategory, String taxCodeCountry,
		final String taxCodeRegion, final String taxCode, final String itemType, final String saleOrPurchase,
		final String grossOrNet )
	{
		SAPItem itemOne = SAPItem
			.builder()
			.taxCategory(taxCategory)
			.taxCodeCountry(taxCodeCountry)
			.taxCodeRegion(taxCodeRegion)
			.taxCode(taxCode)
			.quantity("1")
			.unitPrice("500.00")
			.id("1")
			.itemType(itemType)
			.build();

		return SAPQuoteInput
			.builder()
			.id(ID)
			.date(DATE)
			.saleOrPurchase(saleOrPurchase)
			.grossOrNet(grossOrNet)
			.currency(CANADA_CURRENCY)
			.Item(itemOne)
			.build();
	}

	/**
	 * Sets up a default indirect payload
	 *
	 * @param itemType        the item's type
	 * @param shipFromCountry the origin country
	 * @param shipFromRegion  the origin region
	 * @param shipToCountry   the destination country
	 * @param shipToRegion    the destination region
	 * @param saleOrPurchase  sale or purchase
	 * @param grossOrNet      gross or net
	 *
	 * @return the indirect payload request
	 */
	public SAPQuoteInput setupDefaultIndirectPayloadRequest( final String itemType, final String shipFromCountry,
		String shipFromRegion, String shipToCountry, String shipToRegion, String saleOrPurchase, String grossOrNet )
	{
		SAPItem itemOne = SAPItem
			.builder()
			.quantity("1")
			.unitPrice("100.00")
			.id("1")
			.itemType(itemType)
			.build();
		SAPLocation shipFrom = SAPLocation
			.builder()
			.zipCode("1234")
			.type("SHIP_FROM")
			.country(shipFromCountry)
			.state(shipFromRegion)
			.build();
		SAPLocation shipTo = SAPLocation
			.builder()
			.zipCode("5678")
			.type("SHIP_TO")
			.country(shipToCountry)
			.state(shipToRegion)
			.build();
		return SAPQuoteInput
			.builder()
			.id(ID)
			.date(DATE)
			.saleOrPurchase(saleOrPurchase)
			.grossOrNet(grossOrNet)
			.currency(CANADA_CURRENCY)
			.Item(itemOne)
			.Location(shipFrom)
			.Location(shipTo)
			.build();
	}
}
