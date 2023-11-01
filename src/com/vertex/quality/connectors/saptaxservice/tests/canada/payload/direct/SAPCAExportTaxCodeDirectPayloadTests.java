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

/**
 * Tests direct payload for the export tax codes for Canada
 * All test cases tests direct payload with different tax codes
 * SAPTAXS-21
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "direct_payload" })
public class SAPCAExportTaxCodeDirectPayloadTests extends SAPTaxServiceBaseTest
{
	/**
	 * Tests tax code 700 with a correct region in a net purchase order and taxCategory set to
	 * WITHHOLDING
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion, failing for now
	 */
	@Test
	public void taxCode700WithCorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_700.getTaxCode();
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
	 * Tests tax code 700 with an incorrect region in a gross sales order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode700WithIncorrectRegionGrossSalesTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_700.getTaxCode();
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
	 * Tests tax code 701 with a correct region in a gross purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode701WithCorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_701.getTaxCode();
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
	 * Tests tax code 701 with an incorrect region in a net sales order and taxCategory set to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void taxCode701WithIncorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_701.getTaxCode();
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
	 * Tests tax code 520 which is for exports (only works for indirect payloads)
	 * Expected to fail
	 */
	@Test
	public void taxCode520WithCorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_520.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.QUEBEC.getAbbreviation(), taxCode, itemTypeService,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();
		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Tests tax code 702 with an incorrect region in a gross purchase order and taxCategory set to
	 * WITHHOLDING
	 * Expected to fail
	 */
	@Test
	public void taxCode702WithIncorrectRegionGrossPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_702.getTaxCode();
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
	 * Tests tax code 503 which is for exports (only works for indirect payloads)
	 * Expected to fail
	 */
	@Test
	public void taxCode503WithCorrectRegionNetSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_503.getTaxCode();
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
	 * Tests tax code 703 with an incorrect region in a gross sale order and taxCategory set to
	 * PRODUCT_TAXES
	 * Expected to fail
	 */
	@Test
	public void taxCode703WithIncorrectRegionGrossSaleTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_703.getTaxCode();
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
	 * Tests tax code 703 with an incorrect region in a net purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 * Reqs unknown about mismatching saleOrPurchase vs tax code and tax code vs taxCodeRegion,
	 * failing for now
	 */
	@Test
	public void taxCode703WithIncorrectRegionNetPurchaseTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_703.getTaxCode();
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
 * Tests direct payload for the export tax codes for Canada
 * All test cases tests direct payload with different tax codes
 * SAPTAXS-20
 *
 * @author unaqvi
 *
@Test(groups = { "direct_payload" })
public class SAPCATaxCodeDirectPayloadTests extends SAPTaxServiceBaseTest {
	/**
	 * Tests tax code 501 with a correct region in a net purchase order and taxCategory set to
	 * PRODUCT_TAXES
	 */
	@Test
	public void taxCode501WithCorrectRegionNetPurchaseTest() {
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest(taxCategoryProductTaxes,
				SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
				purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
				quoteInput).createRequestAndGetResponse();
		response
				.then()
				.statusCode(badRequestResponseCode);
	}
}