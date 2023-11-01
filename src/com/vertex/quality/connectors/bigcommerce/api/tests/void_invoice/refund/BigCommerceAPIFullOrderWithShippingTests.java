package com.vertex.quality.connectors.bigcommerce.api.tests.void_invoice.refund;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceTestData;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import com.vertex.quality.connectors.bigcommerce.ui.tests.refund.oseries.OSeriesTaxJournalExportTests;
import com.vertex.quality.connectors.bigcommerce.ui.tests.refund.oseries.OSeriesTaxJournalPurgeTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * CBC-177
 * this test case sends a commit request and then get's the invoice id from the response.
 * then sends an adjustment request for the same invoice id and check the reversal transaction.
 * Reversal transaction should appear in the log.
 * Return Sales order with full order refund.
 * 1. with in same month
 * 2. 1 day less than 3 months
 * 3. 3 month
 * 4. 3 months(+1 day)
 * 5. 6 months out(Tax is paid to jurisdiction)
 *
 * @author rohit-mogane
 */

public class BigCommerceAPIFullOrderWithShippingTests extends BigCommerceAPIBaseTest
{
	OSeriesTaxJournalPurgeTests taxJournalPurgeTest;
	OSeriesTaxJournalExportTests taxJournalExportTest;

	/**
	 * Return Sales order with full order refund with in same month.
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression", "bigCommerce_smoke" })
	public void fullOrderWithinSameMonthTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		Response voidResponse = apiUtil.sendVoidRequest(invoiceId);

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with full order refund with 1 day less than 3 months
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void fullOrderOneDayLessThreeMonthsTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		String oneDayLessThanThreeMonths = apiUtil.currentDateTime(
			BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, oneDayLessThanThreeMonths, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		oneDayLessThanThreeMonths = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response voidResponse = apiUtil.sendVoidRequest(invoiceId);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());
		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with full order refund on last day of 3rd month
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void fullOrderOnLastDayOfThirdMonthTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		String onLastDayOfThirdMonth = apiUtil.currentDateTime(
			BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, onLastDayOfThirdMonth, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		onLastDayOfThirdMonth = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response voidResponse = apiUtil.sendVoidRequest(invoiceId);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with full order refund with 3 months and one day
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void fullOrderThreeMonthsPlusOneDayTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		String threeMonthsPlusOneDay = apiUtil.currentDateTime(
			BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, threeMonthsPlusOneDay, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		String threeMonthsPurge = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		createDriver();
		taxJournalExportTest = new OSeriesTaxJournalExportTests();
		taxJournalExportTest.taxJournalExport(driver, threeMonthsPurge);

		threeMonthsPlusOneDay = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response voidResponse = apiUtil.sendVoidRequest(invoiceId);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogForPurge(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with full order refund with 6 months
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void fullOrderSixMonthsOutTest( )
	{
		BigCommerceRequestItem shirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity, item1Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, BigCommerceTestDataAddress.US_PA_ADDRESS_1,
			BigCommerceTestDataAddress.US_PA_ADDRESS_2, false, shirt);

		String sixMonthsOut = apiUtil.currentDateTime(BigCommerceTestData.REFUND_SIX_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequestWithDate(quote1Id, BigCommerceCurrency.USD,
			customer1Id, sixMonthsOut, doc1);

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		String threeMonthsPurge = apiUtil.currentDateTime(BigCommerceTestData.REFUND_THREE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		createDriver();
		taxJournalExportTest = new OSeriesTaxJournalExportTests();
		taxJournalExportTest.taxJournalExport(driver, threeMonthsPurge);

		sixMonthsOut = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response voidResponse = apiUtil.sendVoidRequest(invoiceId);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogForPurge(messageLogResponse, invoiceId);
	}
}
