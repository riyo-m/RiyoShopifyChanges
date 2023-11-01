package com.vertex.quality.connectors.saptaxservice.tests.canada.payload.direct;

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
 * Contains the happy path tests for direct payload taxcodes
 * All test cases tests direct payload with different tax codes
 * SAPTAXS-21
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "direct_payload" })
public class SAPCAValidTaxCodeDirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests direct payload setting Sale as the transaction, Alberta for the province and the tax
	 * code as 501.
	 * There should only be one tax rate of 5% at the country level, since it is GST only tax
	 */
	@Test
	public void gstSaleDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("525.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("25.0"))
			.body("taxLines[0].totalRate", equalTo("5.0000"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
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
	 * Tests direct payload setting Sale as the transaction, British Columbia for the province and
	 * the tax code as 503.
	 * There should be two tax rates of 5% at the country level and 7% at the provincial level
	 * since it is GST+PST tax
	 */
	@Test
	public void gstPstSaleDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_503.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), taxCode,
			itemTypeMaterial, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("560.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("60.0"))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo(pst))
			.body("taxLines[0].taxValues[1].rate", equalTo("7.00"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.BRITISH_COLUMBIA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests direct payload setting Sale as the transaction, Quebec for the province and the tax
	 * code as 507.
	 * There should be one tax rate of 5% at the country level and 9.975% at the provincial level
	 * since it is GST+QST tax
	 */
	@Test
	public void gstQstSaleDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_507.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("574.88"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("74.88"))
			.body("taxLines[0].totalRate", equalTo("14.9750"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo(qst))
			.body("taxLines[0].taxValues[1].rate", equalTo("9.97500"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests direct payload setting Sale as the transaction, New Brunswick for the province and the
	 * tax code as 511.
	 * There should one tax rates of 15% since it is a HST tax
	 */
	@Test
	public void hstSaleDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_511.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), taxCode,
			itemTypeMaterial, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("575.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("75.0"))
			.body("taxLines[0].totalRate", equalTo("15.0000"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(hst))
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
	 * Tests to see if making an export for direct payload fails correctly
	 * Sets the taxCodeRegion to Alberta and the taxCode to 507, which is for exports
	 */
	@Test
	public void gstSaleExportDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_507.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests to see if making an export for direct payload fails correctly
	 * Sets the taxCodeRegion to British Columbia and the taxCode to 501, which is for exports
	 */
	@Test
	public void gstPstSaleExportDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), taxCode,
			itemTypeMaterial, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests to see if making an export for direct payload fails correctly
	 * Sets the taxCodeRegion to Quebec and the taxCode to 520, which is for exports
	 */
	@Test
	public void gstQstSaleExportDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_520.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests to see if making an export for direct payload fails correctly
	 * Sets the taxCodeRegion to New Brunswick and the taxCode to 503, which is for exports
	 */
	@Test
	public void hstSaleExportDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_503.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), taxCode,
			itemTypeMaterial, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests direct payload setting Purchase as the transaction, Alberta for the province and the
	 * tax code as 2.
	 * There should only be one tax rate of 5% at the country level, since it is GST only tax
	 */
	@Test
	public void gstPurchaseDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_2.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("525.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("25.0"))
			.body("taxLines[0].totalRate", equalTo("5.0000"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
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
	 * Tests direct payload setting Purchase as the transaction, British Columbia for the province
	 * and the tax code as 5.
	 * There should be two tax rates of 5% at the country level and 7% at the provincial level
	 * since it is GST+PST tax
	 */
	@Test
	public void gstPstPurchaseDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_5.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), taxCode,
			itemTypeMaterial, purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("560.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("60.0"))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo(pst))
			.body("taxLines[0].taxValues[1].rate", equalTo("7.00"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.BRITISH_COLUMBIA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests direct payload setting Purchase as the transaction, Quebec for the province and the tax
	 * code as 11.
	 * There should be two tax rates of 5% at the country level and 9.975% at the provincial level
	 * since it is GST+QST tax
	 */
	@Test
	public void gstQstPurchaseDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_11.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("574.88"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("74.88"))
			.body("taxLines[0].totalRate", equalTo("14.9750"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))
			.body("taxLines[0].taxValues[1].name", equalTo(qst))
			.body("taxLines[0].taxValues[1].rate", equalTo("9.97500"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests direct payload setting Purchase as the transaction, New Brunswick for the province and
	 * the tax code as 20.
	 * There should one tax rate of 15% since it is a HST tax
	 */
	@Test
	public void hstPurchaseDirectPayloadTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_20.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), taxCode,
			itemTypeMaterial, purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("total", equalTo("575.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("75.0"))
			.body("taxLines[0].totalRate", equalTo("15.0000"))
			.body("taxLines[0].taxcode", equalTo(taxCode))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(hst))
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
