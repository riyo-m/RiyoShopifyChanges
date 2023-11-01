package com.vertex.quality.connectors.saptaxservice.tests.canada.payload.direct;

import com.vertex.quality.common.utils.APIRequestBuilder;
import com.vertex.quality.connectors.saptaxservice.enums.SAPCountry;
import com.vertex.quality.connectors.saptaxservice.enums.SAPRegion;
import com.vertex.quality.connectors.saptaxservice.enums.SAPTaxServiceEndpoints;
import com.vertex.quality.connectors.saptaxservice.pojos.SAPQuoteInput;
import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * Invalid tax codes
 * All test cases tests direct payload with different tax codes
 * SAPTAXS-21
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "direct_payload" })
public class SAPCAInvalidTaxCodeDirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests an invalid tax code with a valid region in a net sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Expected to fail
	 */
	@Test
	public void invalidTaxCodeWithCorrectRegionNetSalesTest( )
	{
		final String taxCode = "1111111";
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.YUKON.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests an invalid tax code with a valid region in a gross purchase order and taxCategory set
	 * to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void invalidTaxCodeWithIncorrectRegionGrossPurchaseTest( )
	{
		final String taxCode = "11111111111111111111111111111111111111111111111111111111111111111111111";
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryWithholding,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.YUKON.getAbbreviation(), taxCode, itemTypeService,
			purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests an invalid tax code with a valid region in a gross sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Expected to fail
	 */
	@Test
	public void invalidTaxCodeWithCorrectRegionGrossSalesTest( )
	{
		final String taxCode = null;
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), "aaa", taxCode, itemTypeService, sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE, quoteInput)
			.addOrReplaceJsonField("items[0]", "taxCode", taxCode)
			.createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}
}
