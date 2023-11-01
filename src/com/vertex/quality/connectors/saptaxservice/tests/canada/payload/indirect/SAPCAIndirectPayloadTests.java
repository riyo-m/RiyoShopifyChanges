package com.vertex.quality.connectors.saptaxservice.tests.canada.payload.indirect;

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
 * Tests indirect payload for Canada
 * SAPTAXS-22
 *
 * @author unaqvi
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "indirect_payload" })
public class SAPCAIndirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests indirect payload by setting valid ship from location and a GST ship to location as a
	 * net sales transaction
	 * Expected to pass
	 */
	@Test
	public void shipFromValidShipToGSTNetSaleTest( )
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
	 * Tests indirect payload by setting invalid ship from location and a GST ship to location as a
	 * gross purchase transaction
	 * Expected to fail
	 */
	@Test
	public void shipFromInvalidShipToGSTGrossPurchaseTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), "test", SAPCountry.CANADA.getCountryAbbreviation(),
			SAPRegion.YUKON.getAbbreviation(), purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests indirect payload by setting valid ship from location and a GST + PST ship to location
	 * as a gross purchase transaction
	 * Expected to pass
	 */
	@Test
	public void shipFromValidShipToGSTPSTGrossPurchaseTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("89.29"))
			.body("totalTax", equalTo("10.71"))
			.body("taxLines[0].totalRate", equalTo("12.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_5.getTaxCode()))
			.body("taxLines[0].taxValues.size()", equalTo(2))
			.body("taxLines[0].taxValues[0].name", equalTo(gst))
			.body("taxLines[0].taxValues[0].rate", equalTo("5.00"))
			.body("taxLines[0].taxValues[0].jurisdiction", equalTo(SAPCountry.CANADA
				.getFullName()
				.toUpperCase()))

			.body("taxLines[0].taxValues[1].name", equalTo(pst))
			.body("taxLines[0].taxValues[1].rate", equalTo("7.00"))
			.body("taxLines[0].taxValues[1].jurisdiction", equalTo(SAPRegion.BRITISH_COLUMBIA.getFullName().toUpperCase()));
	}

	/**
	 * Tests indirect payload by setting invalid ship from location and a GST + PST ship to location
	 * as a net sales transaction
	 * Expected to fail
	 */
	@Test
	public void shipFromInvalidShipToGSTPSTNetSaleTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService, "aa",
			SAPRegion.QUEBEC.getAbbreviation(), SAPCountry.CANADA.getCountryAbbreviation(),
			SAPRegion.BRITISH_COLUMBIA.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests indirect payload by setting valid ship from location and a HST ship to location
	 * as a gross sale transaction
	 * Expected to pass
	 */
	@Test
	public void shipFromValidShipToHSTGrossSaleTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.YUKON.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("86.96"))
			.body("totalTax", equalTo("13.04"))
			.body("taxLines[0].totalRate", equalTo("15.0000"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_511.getTaxCode()))
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
	 * Tests indirect payload by setting invalid ship from location and a HST ship to location
	 * as a net purchase transaction
	 * Expected to fail
	 */
	@Test
	public void shipFromInvalidShipToHSTNetPurchaseTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.PENNSYLVANIA.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.NEW_BRUNSWICK.getAbbreviation(), purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests indirect payload by setting valid ship from location and a GST + QST ship to location
	 * as a net sales transaction
	 * Expected to pass
	 */
	@Test
	public void shipFromValidShipToGSTQSTNetSaleTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.SASKATCHEWAN.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("114.98"))
			.body("subTotal", equalTo("100.0"))
			.body("totalTax", equalTo("14.98"))
			.body("taxLines[0].totalRate", equalTo("14.9750"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_507.getTaxCode()))
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
	 * Tests indirect payload by setting valid ship from location and a GST + QST ship to location
	 * as a gross purchase transaction
	 * Expected to pass
	 */
	@Test
	public void shipFromValidShipToGSTQSTGrossPurchaseTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.SASKATCHEWAN.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(successResponseCode)
			.and()
			.body("total", equalTo("100.0"))
			.body("subTotal", equalTo("86.97"))
			.body("totalTax", equalTo("13.03"))
			.body("taxLines[0].totalRate", equalTo("14.9750"))
			.body("taxLines[0].taxcode", equalTo(SAPTaxCode.TAX_CODE_11.getTaxCode()))
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
	 * Tests indirect payload by setting invalid ship from location and a GST + QST ship to location
	 * as a gross sales transaction
	 * Expected to fail
	 */
	@Test
	public void shipFromInvalidShipToGSTQSTGrossSaleTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.SASKATCHEWAN.getAbbreviation(),
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField("locations[0]", "country", null)
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests indirect payload by setting invalid ship from location and a US ship to location
	 * as a net sales transaction
	 * Failing, functionality not implemented for exports yet
	 */
	@Test
	public void shipFromValidShipToUSNetSaleTest( )
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
	 * Tests indirect payload by setting invalid ship from location and a US ship to location
	 * as a gross purchase transaction
	 * Failing, functionality not implemented for exports yet
	 */
	@Test
	public void shipFromInvalidShipToUSGrossPurchaseTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), SAPRegion.PENNSYLVANIA.getAbbreviation(), purchase,
			gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField("locations[0]", "country", null)
			.addOrReplaceJsonField("locations[0]", "state", null)
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests indirect payload by setting invalid ship from location and an invalid ship to location
	 * as a net sales transaction
	 * Expected to fail
	 */
	@Test
	public void shipFromValidShipToInvalidNetSaleTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeMaterial,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), "test", sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests indirect payload by setting invalid ship from location and an invalid ship to location
	 * as a gross purchase transaction
	 * Expected to fail
	 */
	@Test
	public void shipFromInvalidShipToInvalidNetSaleTest( )
	{
		SAPQuoteInput quoteInput = apiUtil.setupDefaultIndirectPayloadRequest(itemTypeService,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(),
			SAPCountry.UNITED_STATES.getCountryAbbreviation(), "test", purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.removeJsonField("locations[0].country")
			.removeJsonField("locations[0].state")
			.removeJsonField("locations[1].country")
			.removeJsonField("locations[1].state")

			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}
}
