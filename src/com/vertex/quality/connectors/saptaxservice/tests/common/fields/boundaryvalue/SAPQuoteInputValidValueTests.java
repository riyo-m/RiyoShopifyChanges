package com.vertex.quality.connectors.saptaxservice.tests.common.fields.boundaryvalue;

import com.vertex.quality.connectors.saptaxservice.tests.base.SAPTaxServiceBaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Tests valid values for the fields in quote input
 *
 * @author hho
 */
@Test(groups = { "boundary_value" })
public class SAPQuoteInputValidValueTests extends SAPTaxServiceBaseTest
{
	@Test
	@Ignore
	public void validBusinessPartnerIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "businessPartnerId", stringValue);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validCashDiscountPercentTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "cashDiscountPercent", stringValue);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validCompanyIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "companyId", stringValue);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validCurrencyTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "currency", stringValue);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validDateTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "date", date);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validGrossOrNetTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "grossOrNet", gross);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validIdTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "id", stringValue);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validIsCashDiscountPlannedTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isCashDiscountPlanned", yes);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validIsCompanyDeferredTaxEnabledTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isCompanyDeferredTaxEnabled", yes);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validIsTraceRequiredTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isTraceRequired", yes);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validIsTransactionWithinTaxReportingGroupTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "isTransactionWithinTaxReportingGroup", yes);

		response
			.then()
			.statusCode(successResponseCode);
	}

	@Test
	@Ignore
	public void validOperationNatureCodeTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "operationNatureCode", stringValue);

		response
			.then()
			.statusCode(badRequestResponseCode);
	}

	@Test
	@Ignore
	public void validSaleOrPurchaseTest( )
	{
		Response response = apiUtil.setupDefaultQuoteInputCall("$", "saleOrPurchase", sale);

		response
			.then()
			.statusCode(successResponseCode);
	}
}
