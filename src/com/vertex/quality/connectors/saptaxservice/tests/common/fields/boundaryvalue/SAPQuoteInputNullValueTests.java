package com.vertex.quality.connectors.saptaxservice.tests.common.fields.boundaryvalue;

import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Tests null values for the fields in quote input
 *
 * @author hho
 */
@Test(groups = { "boundary_value" })
public class SAPQuoteInputNullValueTests extends SAPTaxServiceBaseTest
{
	@Test
	@Ignore
	public void nullBusinessPartnerIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "businessPartnerId", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullCashDiscountPercentTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "cashDiscountPercent", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullCompanyIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "companyId", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullCurrencyTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "currency", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be invalid because date is required
	 */
	@Test
	@Ignore
	public void nullDateTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "date", null);

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be valid because grossOrNet has a default value
	 */
	@Test
	@Ignore
	public void nullGrossOrNetTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "grossOrNet", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "id", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullIsCashDiscountPlannedTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isCashDiscountPlanned", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullIsCompanyDeferredTaxEnabledTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isCompanyDeferredTaxEnabled", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullIsTraceRequiredTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isTraceRequired", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullIsTransactionWithinTaxReportingGroupTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isTransactionWithinTaxReportingGroup", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullOperationNatureCodeTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "operationNatureCode", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be invalid because saleOrPurchase is required
	 */
	@Test
	@Ignore
	public void nullSaleOrPurchaseTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "saleOrPurchase", null);

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	@Test
	@Ignore
	public void nullBPExemptionDetailsTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "businessPartnerExemptionDetails", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullLocationsTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "locations", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullPartyTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "party", null);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void nullItemsTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "items", null);

		response
			.then()
			.statusCode(badRequestResponseCode);
	}
}
