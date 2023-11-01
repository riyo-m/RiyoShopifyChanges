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
 * Tests direct payload for the purchase tax codes for Canada
 * All test cases tests direct payload with different tax codes
 * SAPTAXS-21
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "direct_payload" })
public class SAPCAPurchaseTaxCodeDirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests tax code 2 with a correct region in a net purchase order and taxCategory set to
	 * WITHHOLDING
	 * Expected to pass
	 */
	@Test
	public void taxCode2WithCorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_2.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.YUKON.getAbbreviation(), taxCode, itemTypeMaterial,
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
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.YUKON
																	   .getFullName()
																	   .toUpperCase() + " TERRITORY"));
	}

	/**
	 * Tests tax code 2 with an incorrect region in a gross sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode2WithIncorrectRegionGrossSalesTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_2.getTaxCode();
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
	 * Tests tax code 5 with a correct region in a gross purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 * Expected to pass
	 */
	@Test
	public void taxCode5WithCorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_5.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), taxCode,
			itemTypeService, purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("500.0"))
			.body("subTotal", equalTo("446.43"))
			.body("totalTax", equalTo("53.57"))
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
	 * Tests tax code 5 with an incorrect region in a net sales order and taxCategory set to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void taxCode5WithIncorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_5.getTaxCode();
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
	 * Tests tax code 11 with a correct region in a net sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode11WithCorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_11.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeService,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	//TODO LOG A BUG, RETURNING INCORRECT TAX AND REGION IF TAXCODEREGION IS LEFT EMPTY OR NULL OR NOT INCLUDED AT ALL

	/**
	 * Tests tax code 11 with an incorrect region in a gross purchase order and taxCategory set to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void taxCode11WithIncorrectRegionGrossPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_11.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 20 with a correct region in a net sale order and taxCategory set to
	 * WITHHOLDING
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode20WithCorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_20.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NOVA_SCOTIA.getAbbreviation(), taxCode,
			itemTypeService, sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 20 with an incorrect region in a gross sale order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode20WithIncorrectRegionGrossSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_20.getTaxCode();
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
	 * Tests tax code 20 with an incorrect region in a net purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode20WithIncorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_20.getTaxCode();
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
	public void taxCode11WithQCAsTaxCodeRegionTest( ) {
		final String taxCode = SAPTaxCode.TAX_CODE_11.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
				SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode,
				itemTypeMaterial, purchase, gross);

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
	public void taxCode11WithNullAsTaxCodeRegionTest( ) {
		final String taxCode = SAPTaxCode.TAX_CODE_11.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
				SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NULL.getAbbreviation(), taxCode,
				itemTypeMaterial, purchase, gross);

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
