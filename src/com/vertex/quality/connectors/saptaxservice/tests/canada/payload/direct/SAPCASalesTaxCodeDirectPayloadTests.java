package com.vertex.quality.connectors.saptaxservice.tests.canada.payload.direct;

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
 * Tests direct payload for the sales tax codes for Canada
 * All test cases tests direct payload with different tax codes
 * SAPTAXS-21
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "direct_payload" })
public class SAPCASalesTaxCodeDirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests tax code 501 with a correct region in a net purchase order and taxCategory set to
	 * WITHHOLDING
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion, failing for now
	 */
	@Test
	public void taxCode501WithCorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.YUKON.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 501 with an incorrect region in a gross sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode501WithIncorrectRegionGrossSalesTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), taxCode,
			itemTypeService, sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 503 with a correct region in a gross purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode503WithCorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_503.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), taxCode,
			itemTypeService, purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 503 with an incorrect region in a net sales order and taxCategory set to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void taxCode503WithIncorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_503.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEWFOUNDLAND_LABRADOR.getAbbreviation(), taxCode,
			itemTypeMaterial, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField("items[0]", "taxCodeRegion", null)
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 507 with a correct region in a net sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Expected to pass
	 */
	@Test
	public void taxCode507WithCorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_507.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeService,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
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
	 * Tests tax code 507 with an incorrect region in a gross purchase order and taxCategory set to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void taxCode507WithIncorrectRegionGrossPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_507.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField("items[0]", "taxCodeRegion", "")
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 511 with a correct region in a net sale order and taxCategory set to
	 * WITHHOLDING
	 * Expected to pass
	 */
	@Test
	public void taxCode511WithCorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_511.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NOVA_SCOTIA.getAbbreviation(), taxCode,
			itemTypeService, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
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
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.NOVA_SCOTIA
				.getFullName()
				.toUpperCase()));
	}

	/**
	 * Tests tax code 511 with an incorrect region in a gross sale order and taxCategory set to
	 * PRODUCT_TAXES
	 * Expected to fail
	 */
	@Test
	public void taxCode511WithIncorrectRegionGrossSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_511.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField("items[0]", "taxCodeRegion", stringValue)
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 511 with an incorrect region in a net purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode511WithIncorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_511.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.SASKATCHEWAN.getAbbreviation(), taxCode,
			itemTypeService, purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}
	/**
	 *  Testing direct payload request with a Tax Code of 11 and QC as the Tax Code Region
	 */
	@Test
	public void taxCode507WithQCAsTaxCodeRegionTest( ) {
		final String taxCode = SAPTaxCode.TAX_CODE_507.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
				SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode,
				itemTypeMaterial, sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
				quoteInput).createRequestAndGetResponse();
		response
				.then()
				.statusCode(successResponseCode)
				.and()
				.body("total", equalTo("500.0"))
				.body("subTotal", equalTo("434.88"))
				.body("totalTax", equalTo("65.12"))
				.body("country", equalTo("CA"))
				.body("taxLines[0].id", equalTo("1"))
				.body("taxLines[0].totalRate", equalTo("14.9750"))
				.body("taxLines[0].taxcode", equalTo(taxCode))
				.body("taxLines[0].taxValues.size()", equalTo(2))

				.body("taxLines[0].taxValues[0].name", equalTo(gst))
				.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
				.body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
				.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA.getFullName().toUpperCase()))

				.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
				.body("taxLines[0].taxValues[1].name", equalTo(qst))
				.body("taxLines[0].taxValues[1].rate", equalTo("9.97500"))
				.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC.getFullName().toUpperCase()));
	}


	/**
	 *  Testing direct payload request with a Tax Code of 11 and Null as the Tax Code Region
	 */
	@Test
	public void taxCode507WithNullAsTaxCodeRegionTest( ) {
		final String taxCode = SAPTaxCode.TAX_CODE_507.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
				SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NULL.getAbbreviation(), taxCode,
				itemTypeMaterial, sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
				quoteInput).createRequestAndGetResponse();
		response
				.then()
				.statusCode(successResponseCode)
				.and()
				.body("total", equalTo("500.0"))
				.body("subTotal", equalTo("434.88"))
				.body("totalTax", equalTo("65.12"))
				.body("country", equalTo("CA"))
				.body("taxLines[0].id", equalTo("1"))
				.body("taxLines[0].totalRate", equalTo("14.9750"))
				.body("taxLines[0].taxcode", equalTo(taxCode))
				.body("taxLines[0].taxValues.size()", equalTo(2))

				.body("taxLines[0].taxValues[0].name", equalTo(gst))
				.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
				.body("taxLines[0].taxValues[0].level", equalTo("COUNTRY"))
				.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA.getFullName().toUpperCase()))

				.body("taxLines[0].taxValues[1].level", equalTo("PROVINCE"))
				.body("taxLines[0].taxValues[1].name", equalTo(qst))
				.body("taxLines[0].taxValues[1].rate", equalTo("9.97500"))
				.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.QUEBEC.getFullName().toUpperCase()));
	}
}
