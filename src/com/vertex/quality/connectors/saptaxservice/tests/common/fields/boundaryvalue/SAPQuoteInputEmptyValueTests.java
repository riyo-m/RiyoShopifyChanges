package com.vertex.quality.connectors.saptaxservice.tests.common.fields.boundaryvalue;

import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Tests empty values for the fields in quote input
 *
 * @author hho
 */
@Test(groups = { "boundary_value" })
public class SAPQuoteInputEmptyValueTests extends SAPTaxServiceBaseTest
{
	@Ignore
	@Test
	public void emptyBusinessPartnerIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "businessPartnerId", "");

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Ignore
	@Test
	public void emptyCashDiscountPercentTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "cashDiscountPercent", "");

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Ignore
	@Test
	public void emptyCompanyIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "companyId", "");

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Ignore
	@Test
	public void emptyCurrencyTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "currency", "");

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be invalid because date is bad format
	 */
	@Ignore
	@Test
	public void emptyDateTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "date", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be valid because grossOrNet is an enum
	 */
	@Ignore
	@Test
	public void emptyGrossOrNetTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "grossOrNet", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	@Ignore
	@Test
	public void emptyIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "id", "");

		response
			.then()
			.statusCode(successResponseCode);
	}

	/**
	 * Should be invalid because the field is an enum
	 */
	@Ignore
	@Test
	public void emptyIsCashDiscountPlannedTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isCashDiscountPlanned", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is an enum
	 */
	@Ignore
	@Test
	public void emptyIsCompanyDeferredTaxEnabledTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isCompanyDeferredTaxEnabled", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is an enum
	 */
	@Ignore
	@Test
	public void emptyIsTraceRequiredTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isTraceRequired", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is an enum
	 */
	@Ignore
	@Test
	public void emptyIsTransactionWithinTaxReportingGroupTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isTransactionWithinTaxReportingGroup", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is an enum
	 */
	@Ignore
	@Test
	public void emptyOperationNatureCodeTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "operationNatureCode", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is an enum
	 */
	@Ignore
	@Test
	public void emptySaleOrPurchaseTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "saleOrPurchase", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid since the field is a type of list
	 */
	@Ignore
	@Test
	public void emptyBPExemptionDetailsTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "businessPartnerExemptionDetails", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is a list type
	 */
	@Ignore
	@Test
	public void emptyLocationsTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "locations", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is a list type
	 */
	@Ignore
	@Test
	public void emptyPartyTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "party", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	/**
	 * Should be invalid because the field is a list type
	 */
	@Ignore
	@Test
	public void emptyItemsTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "items", "");

		response
			.then()
			.statusCode(badRequestResponseCode);
	}
}
