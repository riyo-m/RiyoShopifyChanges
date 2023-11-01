package com.vertex.quality.connectors.saptaxservice.tests.common.transactiontype;

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
 * Tests for gross vs net sales transactions for the API
 *
 * @author hho
 */
@SuppressWarnings("Duplicates")
@Test(groups = { "gross_or_net" })
public class SAPGrossOrNetSalesTests extends SAPTaxServiceBaseTest
{
	/**
	 * Checks if setting the field "grossOrNet" to the value of 'g' would make the transaction
	 * gross. Sets the field "saleOrPurchase" to 's' to make it a sale transaction as well
	 * If the transaction is gross, the response field should have the field "inclusive" set to
	 * true and the total already have tax accounted for
	 * SAPTAXS-15
	 */
	@Test
	public void isGrossSalesTransactionInclusiveTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("inclusive", equalTo("true"))
			.body("total", equalTo("500.0"))
			.body("subTotal", equalTo("476.19"))
			.body("totalTax", equalTo("23.81"));
	}

	/**
	 * Checks if setting the field "grossOrNet" to the value of 'g' would make the transaction
	 * gross. Sets the field "saleOrPurchase" to 'p' to make it a purchase transaction as well
	 * If the transaction is gross, the response field should have the field "inclusive" set to
	 * true and the total already have tax accounted for
	 * SAPTAXS-15
	 */
	@Test
	public void isGrossPurchaseTransactionInclusiveTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_2.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, gross);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("inclusive", equalTo("true"))
			.body("total", equalTo("500.0"))
			.body("subTotal", equalTo("476.19"))
			.body("totalTax", equalTo("23.81"));
	}

	/**
	 * Checks if setting the field "grossOrNet" to the value of 'n' would make the transaction net.
	 * Sets the field "saleOrPurchase" to 's' to make it a purchase transaction as well
	 * If the transaction is net, the response field should have the field "inclusive" set to false
	 * and the total does not have tax accounted for
	 * SAPTAXS-18
	 */
	@Test
	public void isNetSalesTransactionExclusiveTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_501.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			sale, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("inclusive", equalTo("false"))
			.body("total", equalTo("525.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("25.0"));
	}

	/**
	 * Checks if setting the field "grossOrNet" to the value of 'n' would make the transaction net.
	 * Sets the field "saleOrPurchase" to 'p' to make it a purchase transaction as well
	 * If the transaction is net, the response field should have the field "inclusive" set to false
	 * and the total does not have tax accounted for
	 * SAPTAXS-18
	 */
	@Test
	public void isNetPurchaseTransactionExclusiveTest( )
	{
		final String taxCode = SAPTaxCode.TAX_CODE_2.getTaxCode();
		SAPQuoteInput quoteInput = apiUtil.setupDefaultDirectPayloadRequest("PRODUCT_TAXES",
			SAPCountry.CANADA.getCountryAbbreviation(), SAPRegion.ALBERTA.getAbbreviation(), taxCode, itemTypeMaterial,
			purchase, net);

		Response response = new APIRequestBuilder.Builder(baseUrl, SAPTaxServiceEndpoints.QUOTE,
			quoteInput).createRequestAndGetResponse();

		response
			.then()
			.statusCode(ResponseCodes.SUCCESS.getResponseCode())
			.and()
			.body("inclusive", equalTo("false"))
			.body("total", equalTo("525.0"))
			.body("subTotal", equalTo("500.0"))
			.body("totalTax", equalTo("25.0"));
	}
}
