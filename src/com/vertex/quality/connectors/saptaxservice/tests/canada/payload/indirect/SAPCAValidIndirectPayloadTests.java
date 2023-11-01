package com.vertex.quality.connectors.saptaxservice.tests.canada.payload.indirect;

import com.vertex.quality.common.enums.ResponseCodes;
import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCountry;
import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxCode;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxServiceEndpoints;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPQuoteInput;
import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the happy path for indirect payload
 * SAPTAXS-22
 *
 * @author unaqvi
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "indirect_payload" })
public class SAPCAValidIndirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests indirect payload by setting Sale as the transaction and a ship to and a ship from location instead of using
	 * a tax code.
	 * There should only be one tax rate of 5% at the country level, since it is GST only tax for Alberta and the tax
	 * code should be 501
	 */
	@Test
	public void gstSaleIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("105.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("5.0"))
			.body("taxLines[0].totalRate", equalTo("5.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_501.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("GST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.ALBERTA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Sale as the transaction and a ship to and a ship from location instead of using
	 * a tax code.
	 * There should be two tax rates, 5% at the country level, and 7% at the province level since it is GST+PST tax for
	 * BC and the tax code should be 503
	 */
	@Test
	public void gstPstSaleIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("112.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("12.0"))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_503.getTaxCode()))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("GST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo("PST"))
			.body("taxLines[0].taxValues[1].rate", equalTo("7.00"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.BRITISH_COLUMBIA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Sale as the transaction and a ship to and a ship from location instead of using
	 * a tax code.
	 * There should be two tax rates, 5% at the country level, and 9.975% at the province level since it is GST+QST tax
	 * for QC and the tax code should be 507
	 */
	@Test
	public void gstQstSaleIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("114.98"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("14.98"))
			.body("taxLines[0].totalRate", equalTo("14.9750"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_507.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("GST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo("QST"))
			.body("taxLines[0].taxValues[1].rate", equalTo("9.97500"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Sale as the transaction and a ship to and a ship from location instead of using
	 * a tax code.
	 * There should be one tax rate at 15% and the tax code should be 511
	 */
	@Test
	public void hstSaleIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("115.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("15.0"))
			.body("taxLines[0].totalRate", equalTo("15.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_511.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("HST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("15.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.NEW_BRUNSWICK
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests to see if making an export from AB correctly reflects the tax rate for an indirect payload
	 * Sets the shipFrom to Alberta and the shipTo to Pennsylvania
	 */
	@Test
	public void gstSaleExportIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), SAPRegion.PENNSYLVANIA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("0.0"))
			.body("taxLines[0].totalRate", equalTo("0.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_700.getTaxCode()))
			.body("taxLines[0].taxCodeDescription", equalTo("Export (GST)"))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
			.body("taxLines[0].taxValues[0].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.ALBERTA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests to see if making an export from BC correctly reflects the tax rate for an indirect payload
	 * Sets the shipFrom to British Columbia and the shipTo to Pennsylvania
	 */
	@Test
	public void gstPstSaleExportIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), SAPRegion.PENNSYLVANIA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("0.0"))
			.body("taxLines[0].totalRate", equalTo("0.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_701.getTaxCode()))
			.body("taxLines[0].taxCodeDescription", equalTo("Export (GST, PST)"))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
			.body("taxLines[0].taxValues[0].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.BRITISH_COLUMBIA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests to see if making an export from QC correctly reflects the tax rate for an indirect payload
	 * Sets the shipFrom to Quebec and the shipTo to Pennsylvania
	 */
	@Test
	public void gstQstSaleExportIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), SAPRegion.PENNSYLVANIA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("0.0"))
			.body("taxLines[0].totalRate", equalTo("0.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_702.getTaxCode()))
			.body("taxLines[0].taxCodeDescription", equalTo("Export (GST, QST)"))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
			.body("taxLines[0].taxValues[0].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests to see if making an export from NB correctly reflects the tax rate for an indirect payload
	 * Sets the shipFrom to New Brunswick and the shipTo to Pennsylvania
	 */
	@Test
	public void hstSaleExportIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), SAPRegion.PENNSYLVANIA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("0.0"))
			.body("taxLines[0].totalRate", equalTo("0.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_703.getTaxCode()))
			.body("taxLines[0].taxCodeDescription", equalTo("Export (HST)"))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
			.body("taxLines[0].taxValues[0].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.NEW_BRUNSWICK
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Purchase as the transaction and a ship to and a ship from location instead of
	 * using a tax code.
	 * There should only be one tax rate of 5% at the country level, since it is GST only tax for Alberta and the tax
	 * code should be 2
	 */
	@Test
	public void gstPurchaseIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("105.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("5.0"))
			.body("taxLines[0].totalRate", equalTo("5.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_2.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("GST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.ALBERTA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Purchase as the transaction and a ship to and a ship from location instead of
	 * using a tax code.
	 * There should be two tax rates, 5% at the country level, and 7% at the province level since it is GST+PST tax for
	 * BC and the tax code should be 5
	 */
	@Test
	public void gstPstPurchaseIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("112.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("12.0"))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_5.getTaxCode()))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("GST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo("PST"))
			.body("taxLines[0].taxValues[1].rate", equalTo("7.00"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.BRITISH_COLUMBIA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Purchase as the transaction and a ship to and a ship from location instead of
	 * using a tax code.
	 * There should be two tax rates, 5% at the country level, and 9.975% at the province level since it is GST+QST tax
	 * for QC and the tax code should be 11
	 */
	@Test
	public void gstQstPurchaseIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("114.98"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("14.98"))
			.body("taxLines[0].totalRate", equalTo("14.9750"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_11.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("GST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo("QST"))
			.body("taxLines[0].taxValues[1].rate", equalTo("9.97500"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting Purchase as the transaction and a ship to and a ship from location instead of
	 * using a tax code.
	 * There should be one tax rate at 15% and the tax code should be 20
	 */
	@Test
	public void hstPurchaseIndirectPayloadTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("115.0"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("15.0"))
			.body("taxLines[0].totalRate", equalTo("15.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_20.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo("HST"))
			.body("taxLines[0].taxValues[0].rate", equalTo("15.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
			.body("taxLines[0].taxValues[1].rate", equalTo("0.0"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.NEW_BRUNSWICK
				.getFullName()
				.toUpperCase()));
	}
}
