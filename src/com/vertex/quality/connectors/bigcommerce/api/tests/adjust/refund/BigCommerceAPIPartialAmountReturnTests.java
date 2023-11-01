package com.vertex.quality.connectors.bigcommerce.api.tests.adjust.refund;

import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceCurrency;
import com.vertex.quality.connectors.bigcommerce.api.enums.BigCommerceTestData;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceQuoteRequest;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestDocument;
import com.vertex.quality.connectors.bigcommerce.api.interfaces.BigCommerceRequestItem;
import com.vertex.quality.connectors.bigcommerce.api.tests.base.BigCommerceAPIBaseTest;
import com.vertex.quality.connectors.bigcommerce.common.enums.BigCommerceTestDataAddress;
import com.vertex.quality.connectors.bigcommerce.ui.tests.refund.oseries.OSeriesTaxJournalExportTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class BigCommerceAPIPartialAmountReturnTests extends BigCommerceAPIBaseTest
{
	/**
	 * CBC-177
	 * this test case sends a commit request and then get's the invoice id from the response.
	 * then sends an adjustment request for the same invoice id and check the reversal transaction.
	 * Reversal transaction should appear in the log.
	 * Return Sales order with partial amount refund.
	 * 1. with in same month
	 * 2. 1 day less than 3 months
	 * 3. 3 month
	 * 4. 3 months(+1 day)
	 * 5. 6 months out(Tax is paid to jurisdiction)
	 *
	 * @author mayur-kumbhar
	 */
	OSeriesTaxJournalExportTests taxJournalExportTest;

	/**
	 * Return Sales order with partial amount refund with in same month.
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_smoke", "bigCommerce_regression" })
	public void partialAmountWithinSameMonthTest( )
	{
		final int months = 0, oneDay = 0, lastDay = 0;

		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem mediumAmountShirt = apiUtil.buildItem(standardMediumPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem lowAmountShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			mediumAmountShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			lowAmountShirt);

		BigCommerceQuoteRequest quoteRequest = apiUtil.buildQuoteRequest(quote1Id, BigCommerceCurrency.USD, customer1Id,
			doc1);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		String invoiceId = apiUtil.commitNewInvoice(quoteRequest);

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequest(invoiceId, BigCommerceCurrency.USD,
			customer1Id, doc2);
		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with partial amount refund with 1 day less than 3 months
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void partialAmountOneDayLessThreeMonthsTest( )
	{
		int months = -3, oneDay = 0, lastDay = 0;

		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem mediumAmountShirt = apiUtil.buildItem(standardMediumPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem lowAmountShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			mediumAmountShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			lowAmountShirt);

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

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, oneDayLessThanThreeMonths, doc2);

		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_LESS_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with partial amount refund on last day of 3rd month
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void partialAmountOnLastDayOfThirdMonthTest( )
	{
		int months = -3, oneDay = 0, lastDay = 0;

		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem mediumAmountShirt = apiUtil.buildItem(standardMediumPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem lowAmountShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			mediumAmountShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			lowAmountShirt);

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

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, onLastDayOfThirdMonth, doc2);

		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogKeyword(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with partial amount refund with 3 months and one day
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void partialAmountThreeMonthsPlusOneDayTest( )
	{
		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem mediumAmountShirt = apiUtil.buildItem(standardMediumPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem lowAmountShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			mediumAmountShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			lowAmountShirt);

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

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, threeMonthsPlusOneDay, doc2);
		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogForPurge(messageLogResponse, invoiceId);
	}

	/**
	 * Return Sales order with partial amount refund with 6 months
	 */
	@Test(groups = { "bigCommerce_refund", "bigCommerce_regression" })
	public void partialAmountSixMonthsOutTest( )
	{
		BigCommerceTestDataAddress paAddress1 = BigCommerceTestDataAddress.US_PA_ADDRESS_4;
		BigCommerceTestDataAddress paAddress2 = BigCommerceTestDataAddress.US_PA_ADDRESS_2;

		BigCommerceRequestItem mediumAmountShirt = apiUtil.buildItem(standardMediumPriceAmount, false, defaultQuantity,
			item1Id);
		BigCommerceRequestItem lowAmountShirt = apiUtil.buildItem(standardLowPriceAmount, false, defaultQuantity,
			item2Id);

		BigCommerceRequestDocument doc1 = apiUtil.buildDocument(document1Id, paAddress1, paAddress2, false,
			mediumAmountShirt);
		BigCommerceRequestDocument doc2 = apiUtil.buildDocument(document2Id, paAddress1, paAddress2, false,
			lowAmountShirt);

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

		BigCommerceQuoteRequest adjustRequest = apiUtil.buildQuoteRequestWithDate(invoiceId, BigCommerceCurrency.USD,
			customer1Id, sixMonthsOut, doc2);
		Response adjustResponse = apiUtil.sendAdjustRequest(adjustRequest, invoiceId, standardLowPriceAmount);

		String fromDate = apiUtil.currentDateTime(BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ONE_DAY_MONTH.getDayMonth(),
			BigCommerceTestData.REFUND_ZERO_DAY_MONTH.getDayMonth());

		Response messageLogResponse = apiUtil.sendReversalGetRequest(fromDate, invoiceId);
		apiUtil.verifyMessageLogForPurge(messageLogResponse, invoiceId);
	}
}
